package pe.com.sigbah.web.controller.gestion_almacenes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DetalleProductoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoIngresoBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.LoteProductoBean;
import pe.com.sigbah.common.bean.OrdenIngresoBean;
import pe.com.sigbah.common.bean.ProductoBean;
import pe.com.sigbah.common.bean.ProductoIngresoBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.ExportarArchivo;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.LogisticaService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.gestion_almacenes.Reporteador;

/**
 * @className: OrdenIngresoController.java
 * @description: 
 * @date: 17 de jun. de 2017
 * @author: SUMERIO.
 */
@Controller
@RequestMapping("/gestion-almacenes/orden-ingreso")
public class OrdenIngresoController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Autowired 
	private LogisticaService logisticaService;
	
	@Autowired 
	private GeneralService generalService;
	
	/**
	 * @param model 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String inicio(Model model) {
        try {
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);

        	model.addAttribute("lista_anio", generalService.listarAnios());
        	
        	List<ItemBean> listaDdi = generalService.listarDdi(new ItemBean(usuarioBean.getIdDdi()));
        	model.addAttribute("lista_ddi", listaDdi);
        	
        	if (!Utils.isEmpty(listaDdi) && listaDdi.size() == Constantes.ONE_INT) {
        		model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
        	}
        	        	
        	model.addAttribute("lista_tipo_movimiento", generalService.listarTipoMovimiento(new ItemBean(Constantes.TWO_INT, Constantes.ONE_INT)));
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar_orden_ingreso";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarOrdenIngreso", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarOrdenIngreso(HttpServletRequest request, HttpServletResponse response) {
		List<OrdenIngresoBean> lista = null;
		try {			
			OrdenIngresoBean ordenIngresoBean = new OrdenIngresoBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(ordenIngresoBean, request.getParameterMap());			
			lista = logisticaService.listarOrdenIngreso(ordenIngresoBean);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param codigo 
	 * @param model
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/mantenimientoOrdenIngreso/{codigo}", method = RequestMethod.GET)
    public String mantenimientoControlCalidad(@PathVariable("codigo") Integer codigo, Model model) {
        try {
        	OrdenIngresoBean ordenIngreso = new OrdenIngresoBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	if (!isNullInteger(codigo)) {
        		
        		ordenIngreso = logisticaService.obtenerRegistroOrdenIngreso(codigo);
        		
        		if (!isNullInteger(ordenIngreso.getIdMedioTransporte())) {
        			ItemBean item = new ItemBean();
        			item.setIcodigo(usuarioBean.getIdDdi());
        			item.setIcodigoParam2(ordenIngreso.getIdMedioTransporte());
        			model.addAttribute("lista_empresa_transporte", generalService.listarEmpresaTransporte(item));
        		}        		
        		if (!isNullInteger(ordenIngreso.getIdEmpresaTransporte())) {
        			model.addAttribute("lista_chofer", generalService.listarChofer(new ItemBean(ordenIngreso.getIdEmpresaTransporte())));
        		}

        	} else {

        		StringBuilder correlativo = new StringBuilder();
        		correlativo.append(usuarioBean.getCodigoDdi());
        		correlativo.append(Constantes.SEPARADOR);
        		correlativo.append(usuarioBean.getCodigoAlmacen());
        		correlativo.append(Constantes.SEPARADOR);
        		
        		OrdenIngresoBean parametros = new OrdenIngresoBean();
        		String anioActual = generalService.obtenerAnioActual();
        		parametros.setCodigoAnio(anioActual);
        		parametros.setCodigoDdi(usuarioBean.getCodigoDdi());
        		parametros.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametros.setTipoOrigen("I");
        		OrdenIngresoBean respuestaCorrelativo = logisticaService.obtenerCorrelativoOrdenIngreso(parametros);
      
        		correlativo.append(respuestaCorrelativo.getNroOrdenIngreso());
        		
        		ordenIngreso.setNroOrdenIngreso(correlativo.toString());        		
        		
        		ControlCalidadBean parametroAlmacenActivo = new ControlCalidadBean();
        		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
        		List<ControlCalidadBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
        		if (!isEmpty(listaAlmacenActivo)) {
        			ordenIngreso.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
        			ordenIngreso.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
        			ordenIngreso.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
        			ordenIngreso.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
        			ordenIngreso.setCodigoMes(listaAlmacenActivo.get(0).getCodigoMes());
        		}
        		
        		ordenIngreso.setIdDdi(usuarioBean.getIdDdi());
        		ordenIngreso.setCodigoDdi(usuarioBean.getCodigoDdi());
        		ordenIngreso.setNombreDdi(usuarioBean.getNombreDdi());
        	}
        	
        	if (!Utils.isNullInteger(usuarioBean.getIdDdi())) {
        		model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
        	}
        	
        	model.addAttribute("ordenIngreso", getParserObject(ordenIngreso));
        	
        	model.addAttribute("lista_tipo_movimiento", generalService.listarTipoMovimiento(new ItemBean(Constantes.TWO_INT, Constantes.ONE_INT)));

        	model.addAttribute("lista_estado", generalService.listarEstado(new ItemBean(null, Constantes.FOUR_INT)));
        	
        	model.addAttribute("lista_orden_compra", logisticaService.listarOrdenCompra());
        	
        	model.addAttribute("lista_nro_control_calidad", logisticaService.listarNroControlCalidad(new ControlCalidadBean(usuarioBean.getCodigoDdi())));
        	
        	model.addAttribute("lista_proveedor", generalService.listarProveedor(new ItemBean()));
        	
        	model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean()));
        	
        	model.addAttribute("lista_medio_transporte", generalService.listarMedioTransporte(new ItemBean()));
        	
        	model.addAttribute("lista_personal", generalService.listarPersonal(new ItemBean(usuarioBean.getIdDdi())));
        	
        	model.addAttribute("lista_producto", generalService.listarCatologoProductos(new ProductoBean(null, Constantes.FIVE_INT)));
        	
        	model.addAttribute("lista_tipo_documento", generalService.listarTipoDocumento(new ItemBean()));
     
        	model.addAttribute("lista_categoria", generalService.listarCategoria(new ItemBean(Constantes.THREE_INT)));
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_orden_ingreso";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarEmpresaTransporte", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarEmpresaTransporte(HttpServletRequest request, HttpServletResponse response) {
		List<ItemBean> lista = null;
		try {			
			ItemBean item = new ItemBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(item, request.getParameterMap());
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	item.setIcodigo(usuarioBean.getIdDdi());
			lista = generalService.listarEmpresaTransporte(item);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/grabarOrdenIngreso", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarOrdenIngreso(HttpServletRequest request, HttpServletResponse response) {
		OrdenIngresoBean ordenIngreso = null;
		try {			
			OrdenIngresoBean ordenIngresoBean = new OrdenIngresoBean();
			
			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(ordenIngresoBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	ordenIngresoBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	ordenIngreso = logisticaService.grabarOrdenIngreso(ordenIngresoBean);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return ordenIngreso;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarLoteProducto", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarLoteProducto(HttpServletRequest request, HttpServletResponse response) {
		List<LoteProductoBean> lista = null;
		try {			
			LoteProductoBean lote = new LoteProductoBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(lote, request.getParameterMap());
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	lote.setIdDdi(usuarioBean.getIdDdi());
        	lote.setIdAlmacen(usuarioBean.getIdAlmacen());
			lista = logisticaService.listarLoteProductos(lote);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProductoOrdenIngreso", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProductoOrdenIngreso(HttpServletRequest request, HttpServletResponse response) {
		List<ProductoIngresoBean> lista = null;
		try {			
			ProductoIngresoBean producto = new ProductoIngresoBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(producto, request.getParameterMap());			
			lista = logisticaService.listarProductoIngreso(producto);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/grabarProductoOrdenIngreso", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarProductoOrdenIngreso(HttpServletRequest request, HttpServletResponse response) {
		ProductoIngresoBean producto = null;
		try {			
			ProductoIngresoBean productoIngresoBean = new ProductoIngresoBean();

			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);			
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Convierte los vacios en nulos en los decimales
			BigDecimalConverter con_decimal = new BigDecimalConverter(null);
			beanUtilsBean.getConvertUtils().register(con_decimal, BigDecimal.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(productoIngresoBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	productoIngresoBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			producto = logisticaService.grabarProductoIngreso(productoIngresoBean);
			
			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return producto;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/eliminarProductoOrdenIngreso", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarProductoOrdenIngreso(HttpServletRequest request, HttpServletResponse response) {
		ProductoIngresoBean producto = null;
		try {			
			String[] arrIdDetalleControlCalidad = request.getParameter("arrIdDetalleIngreso").split(Constantes.UNDERLINE);
			for (String codigo : arrIdDetalleControlCalidad) {				
				ProductoIngresoBean productoControlCalidadBean = new ProductoIngresoBean(getInteger(codigo));

				// Retorno los datos de session
	        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
	        	
	        	productoControlCalidadBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
				producto = logisticaService.eliminarProductoIngreso(productoControlCalidadBean);				
			}

			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return producto;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarDocumentoOrdenIngreso", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarDocumentoOrdenIngreso(HttpServletRequest request, HttpServletResponse response) {
		List<DocumentoIngresoBean> lista = null;
		try {			
			DocumentoIngresoBean documento = new DocumentoIngresoBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(documento, request.getParameterMap());			
			lista = logisticaService.listarDocumentoIngreso(documento);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/grabarDocumentoOrdenIngreso", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarDocumentoOrdenIngreso(HttpServletRequest request, HttpServletResponse response) {
		DocumentoIngresoBean documento = null;
		try {			
			DocumentoIngresoBean documentoIngresoBean = new DocumentoIngresoBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(documentoIngresoBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	documentoIngresoBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	documento = logisticaService.grabarDocumentoIngreso(documentoIngresoBean);
			
        	documento.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return documento;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/eliminarDocumentoOrdenIngreso", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarDocumentoOrdenIngreso(HttpServletRequest request, HttpServletResponse response) {
		DocumentoIngresoBean documento = null;
		try {			
			String[] arrIdDocumentoIngreso = request.getParameter("arrIdDocumentoIngreso").split(Constantes.UNDERLINE);
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
			for (String codigo : arrIdDocumentoIngreso) {				
				DocumentoIngresoBean documentoIngresoBean = new DocumentoIngresoBean(getInteger(codigo));

	        	documentoIngresoBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
	        	documento = logisticaService.eliminarDocumentoIngreso(documentoIngresoBean);				
			}

			documento.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return documento;
	}
	
	/**
	 * @param codigoAnio 
	 * @param codigoDdi 
	 * @param codigoAlmacen
	 * @param codigoMovimiento 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarExcel/{codigoAnio}/{codigoDdi}/{codigoAlmacen}/{codigoMovimiento}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("codigoAnio") String codigoAnio, 
								@PathVariable("codigoDdi") String codigoDdi, 
								@PathVariable("codigoAlmacen") String codigoAlmacen,
								@PathVariable("codigoMovimiento") String codigoMovimiento, 
								HttpServletResponse response) {
	    try {
	    	OrdenIngresoBean ordenIngresoBean = new OrdenIngresoBean();
	    	ordenIngresoBean.setCodigoAnio(verificaParametro(codigoAnio));
	    	ordenIngresoBean.setCodigoDdi(verificaParametro(codigoDdi));
	    	ordenIngresoBean.setCodigoAlmacen(verificaParametro(codigoAlmacen));
	    	ordenIngresoBean.setCodigoMovimiento(verificaParametro(codigoMovimiento));
			List<OrdenIngresoBean> lista = logisticaService.listarOrdenIngreso(ordenIngresoBean);
	    	
			String file_name = "OrdenIngreso";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			response.resetBuffer();
            response.setContentType(Constantes.MIME_APPLICATION_XLS);
            response.setHeader("Content-Disposition", "attachment; filename="+file_name);            
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "private");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setDateHeader("Expires", 1);
            
		    Reporteador rep = new Reporteador();
		    HSSFWorkbook wb = rep.generaReporteExcelOrdenIngreso(lista);
	    	
		    // Captured backflow
	    	OutputStream out = response.getOutputStream();
	    	wb.write(out); // We write in that flow
	    	out.flush(); // We emptied the flow
	    	out.close(); // We close the flow
	    	
	    	return Constantes.COD_EXITO_GENERAL;   	
	    } catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	return Constantes.COD_ERROR_GENERAL;
	    } 
	}
	
	/**
	 * @param codigo 
	 * @param request 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarPdf/{codigo}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarPdf(@PathVariable("codigo") Integer codigo, HttpServletRequest request, HttpServletResponse response) {
	    try {
			List<DetalleProductoControlCalidadBean> lista = logisticaService.listarDetalleProductoControlCalidad(codigo);
			if (isEmpty(lista)) {
				return Constantes.COD_VALIDACION_GENERAL;
			}			
			DetalleProductoControlCalidadBean producto = lista.get(0);

			ExportarArchivo printer = new ExportarArchivo();
			StringBuilder jasperFile = new StringBuilder();
			jasperFile.append(getPath(request));
			jasperFile.append(File.separator);
			jasperFile.append(Constantes.REPORT_PATH_ALMACENES);
			if (producto.getFlagTipoProducto().equals(Constantes.ONE_STRING)) {
				jasperFile.append("Control_Calidad_Alimentaria.jrxml");
			} else {
				jasperFile.append("Control_Calidad_No_Alimentaria.jrxml");
			}
			
			Map<String, Object> parameters = new HashMap<String, Object>();

			// Agregando los parámetros del reporte
			StringBuilder logo_indeci_path = new StringBuilder();
			logo_indeci_path.append(getPath(request));
			logo_indeci_path.append(File.separator);
			logo_indeci_path.append(Constantes.IMAGE_INDECI_REPORT_PATH);
			parameters.put("P_LOGO_INDECI", logo_indeci_path.toString());			
			StringBuilder logo_wfp_path = new StringBuilder();
			logo_wfp_path.append(getPath(request));
			logo_wfp_path.append(File.separator);
			logo_wfp_path.append(Constantes.IMAGE_WFP_REPORT_PATH);
			parameters.put("P_LOGO_WFP", logo_wfp_path.toString());			
			StringBuilder logo_check_path = new StringBuilder();
			logo_check_path.append(getPath(request));
			logo_check_path.append(File.separator);
			logo_check_path.append(Constantes.IMAGE_CHECK_REPORT_PATH);
			parameters.put("P_LOGO_CHECK", logo_check_path.toString());			
			StringBuilder logo_check_min_path = new StringBuilder();
			logo_check_min_path.append(getPath(request));
			logo_check_min_path.append(File.separator);
			logo_check_min_path.append(Constantes.IMAGE_CHECK_REPORT_PATH);
			parameters.put("P_LOGO_CHECK_MIN", logo_check_min_path.toString());			
			parameters.put("P_NRO_CONTROL_CALIDAD", producto.getNroControlCalidad());
			parameters.put("P_DDI", producto.getNombreDdi());			
			parameters.put("P_ALMACEN", producto.getNombreAlmacen());
			parameters.put("P_FECHA_EMISION", producto.getFechaEmision());
			parameters.put("P_PROVEEDOR", producto.getProveedorDestino());
			parameters.put("P_NRO_ORDEN_COMPRA", producto.getNroOrdenCompra());
			parameters.put("P_CONCLUSIONES", producto.getConclusiones());
			parameters.put("P_RECOMENDACIONES", producto.getRecomendaciones());

			byte[] array = printer.exportPdf(jasperFile.toString(), parameters, lista);
			InputStream input = new ByteArrayInputStream(array);
	        
	        String file_name = "Reporte_Control_Calidad";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_PDF);
	    	
	        response.resetBuffer();
            response.setContentType(Constantes.MIME_APPLICATION_PDF);
            response.setHeader("Content-Disposition", "attachment; filename="+file_name);            
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "private");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setDateHeader("Expires", 1);
			
			byte[] buffer = new byte[4096];
	    	int n = 0;

	    	OutputStream output = response.getOutputStream();
	    	while ((n = input.read(buffer)) != -1) {
	    	    output.write(buffer, 0, n);
	    	}
	    	output.close();

	    	return Constantes.COD_EXITO_GENERAL;
	    } catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	return Constantes.COD_ERROR_GENERAL;
	    } 
	}
	
}

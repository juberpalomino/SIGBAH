package pe.com.sigbah.web.controller.gestion_almacenes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

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
import pe.com.sigbah.common.bean.DocumentoIngresoBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.LoteProductoBean;
import pe.com.sigbah.common.bean.OrdenIngresoBean;
import pe.com.sigbah.common.bean.ProductoBean;
import pe.com.sigbah.common.bean.ProductoIngresoBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.LogisticaService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.gestion_almacenes.ReporteOrdenIngreso;

/**
 * @className: OrdenIngresoController.java
 * @description: 
 * @date: 17 de jun. de 2017
 * @author: Junior Huaman Flores.
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
	 * @param indicador 
	 * @param model 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio/{indicador}", method = RequestMethod.GET)
    public String inicio(@PathVariable("indicador") String indicador, Model model) {
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
        	
        	model.addAttribute("indicador", indicador);
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
    public String mantenimientoOrdenIngreso(@PathVariable("codigo") Integer codigo, Model model) {
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
        		parametros.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
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
        	
			ordenIngresoBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        	ordenIngresoBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	ordenIngreso = logisticaService.grabarOrdenIngreso(ordenIngresoBean);
        	ordenIngreso.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
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
			
			ReporteOrdenIngreso reporte = new ReporteOrdenIngreso();
		    HSSFWorkbook wb = reporte.generaReporteExcelOrdenIngreso(lista);
			
			response.resetBuffer();
            response.setContentType(Constantes.MIME_APPLICATION_XLS);
            response.setHeader("Content-Disposition", "attachment; filename="+file_name);            
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "private");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setDateHeader("Expires", 1);
            
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
	    	
	    	OrdenIngresoBean ordenIngreso = logisticaService.obtenerRegistroOrdenIngreso(codigo);
	    	ProductoIngresoBean producto = new ProductoIngresoBean();
	    	producto.setIdIngreso(codigo);
	    	List<ProductoIngresoBean> listaProducto = logisticaService.listarProductoIngreso(producto);
	    	
	    	DocumentoIngresoBean documento = new DocumentoIngresoBean();
	    	documento.setIdIngreso(codigo);
	    	List<DocumentoIngresoBean> listaDocumento = logisticaService.listarDocumentoIngreso(documento);	    	

	    	StringBuilder file_path = new StringBuilder();
	    	file_path.append(getPath(request));
	    	file_path.append(File.separator);
	    	file_path.append(Constantes.UPLOAD_PATH_FILE_TEMP);
	    	file_path.append(File.separator);
	    	file_path.append(Calendar.getInstance().getTime().getTime());
	    	file_path.append(Constantes.EXTENSION_FORMATO_PDF);
	    	
	    	String file_name = "Orden_Ingreso";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_PDF);
			
			ReporteOrdenIngreso reporte = new ReporteOrdenIngreso();
			reporte.generaPDFReporteIngresos(file_path.toString(), ordenIngreso, listaProducto, listaDocumento);
			
			response.resetBuffer();
            response.setContentType(Constantes.MIME_APPLICATION_PDF);
            response.setHeader("Content-Disposition", "attachment; filename="+file_name);            
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "private");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setDateHeader("Expires", 1);
	    	
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos = convertPDFToByteArrayOutputStream(file_path.toString());
			
			// Captured backflow
	        OutputStream out = response.getOutputStream();
	        baos.writeTo(out); // We write in that flow
	        out.flush(); // We emptied the flow
	    	out.close(); // We close the flow
	    	
	    	File file_temp = new File(file_path.toString());
    		if (file_temp.delete()) {
    			LOGGER.info("[exportarPdf] "+file_temp.getName()+" se borra el archivo temporal.");
    		} else {
    			LOGGER.info("[exportarPdf] "+file_temp.getName()+" no se logró borrar el archivo temporal.");
    		}
    		
	    	return Constantes.COD_EXITO_GENERAL;
	    } catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	return Constantes.COD_ERROR_GENERAL;
	    } 
	}
	
}

package pe.com.sigbah.web.controller.gestion_almacenes;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
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

import pe.com.sigbah.common.bean.CierreStockBean;
import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.StockAlmacenProductoBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.LogisticaService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.gestion_almacenes.ReporteCierreStock;

/**
 * @className: CierreStockController.java
 * @description: 
 * @date: 17 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Controller
@RequestMapping("/gestion-almacenes/cierre-stock")
public class CierreStockController extends BaseController {

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
        	
        	model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
        	
        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar_cierre_stock";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarCierreStock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarCierreStock(HttpServletRequest request, HttpServletResponse response) {
		List<CierreStockBean> lista = null;
		try {			
			CierreStockBean cierreStockBean = new CierreStockBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(cierreStockBean, request.getParameterMap());			
			lista = logisticaService.listarCierreStock(cierreStockBean);
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
	@RequestMapping(value = "/mantenimientoCierreStock/{codigo}", method = RequestMethod.GET)
    public String mantenimientoCierreStock(@PathVariable("codigo") Integer codigo, Model model) {
        try {
        	CierreStockBean cierreStock = new CierreStockBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	if (!isNullInteger(codigo)) {
        		
        		cierreStock = logisticaService.obtenerRegistroCierreStock(codigo);

        	} else {

        		StringBuilder correlativo = new StringBuilder();
        		correlativo.append(usuarioBean.getCodigoDdi());
        		correlativo.append(Constantes.SEPARADOR);
        		correlativo.append(usuarioBean.getCodigoAlmacen());
        		correlativo.append(Constantes.SEPARADOR);
        		
        		CierreStockBean parametros = new CierreStockBean();
        		String anioActual = generalService.obtenerAnioActual();
        		parametros.setCodigoAnio(anioActual);
        		parametros.setCodigoDdi(usuarioBean.getCodigoDdi());
        		parametros.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametros.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
//        		CierreStockBean respuestaCorrelativo = logisticaService.obtenerCorrelativoCierreStock(parametros);
      
//        		correlativo.append(respuestaCorrelativo.getNroCartilla());
        		
        		cierreStock.setNroCartilla(correlativo.toString());        		
        		
        		ControlCalidadBean parametroAlmacenActivo = new ControlCalidadBean();
        		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
        		List<ControlCalidadBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
        		if (!isEmpty(listaAlmacenActivo)) {
        			cierreStock.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
        			cierreStock.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
        			cierreStock.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
        			cierreStock.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
        		}
        		
            	cierreStock.setIdDdi(usuarioBean.getIdDdi());
        		cierreStock.setCodigoDdi(usuarioBean.getCodigoDdi());
        		cierreStock.setNombreDdi(usuarioBean.getNombreDdi());
        		cierreStock.setIdEstado(Constantes.COD_GENERADO);
        		cierreStock.setNombreEstado(Constantes.DES_GENERADO);
        	}
        	
        	model.addAttribute("cierreStock", getParserObject(cierreStock));

        	model.addAttribute("lista_personal", generalService.listarPersonal(new ItemBean(usuarioBean.getIdDdi())));
        	
        	StockAlmacenProductoBean stockAlmacenProductoBean = new StockAlmacenProductoBean();
        	stockAlmacenProductoBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        	stockAlmacenProductoBean.setIdDdi(usuarioBean.getIdDdi());
        	stockAlmacenProductoBean.setIdAlmacen(usuarioBean.getIdAlmacen());
        	model.addAttribute("lista_producto", logisticaService.listarStockAlmacenProducto(stockAlmacenProductoBean));

        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_cierre_stock";
    }

	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/grabarCierreStock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarCierreStock(HttpServletRequest request, HttpServletResponse response) {
		CierreStockBean cierreStock = null;
		try {			
			CierreStockBean cierreStockBean = new CierreStockBean();
			
			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(cierreStockBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	cierreStockBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			cierreStock = logisticaService.grabarCierreStock(cierreStockBean);
			cierreStock.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return cierreStock;
	}
	
//	/**
//	 * @param request
//	 * @param response
//	 * @return objeto en formato json
//	 */
//	@RequestMapping(value = "/listarProductoCierreStock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Object listarProductoCierreStock(HttpServletRequest request, HttpServletResponse response) {
//		List<ProductoCierreStockBean> lista = null;
//		try {			
//			ProductoCierreStockBean producto = new ProductoCierreStockBean();			
//			// Copia los parametros del cliente al objeto
//			BeanUtils.populate(producto, request.getParameterMap());			
//			lista = logisticaService.listarProductoCierreStock(producto);
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			return getBaseRespuesta(null);
//		}
//		return lista;
//	}
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @return objeto en formato json
//	 */
//	@RequestMapping(value = "/grabarProductoCierreStock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Object grabarProductoCierreStock(HttpServletRequest request, HttpServletResponse response) {
//		ProductoCierreStockBean producto = null;
//		try {			
//			ProductoCierreStockBean productoCierreStockBean = new ProductoCierreStockBean();
//
//			// Convierte los vacios en nulos en los enteros
//			IntegerConverter con_integer = new IntegerConverter(null);			
//			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
//			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
//			// Convierte los vacios en nulos en los decimales
//			BigDecimalConverter con_decimal = new BigDecimalConverter(null);
//			beanUtilsBean.getConvertUtils().register(con_decimal, BigDecimal.class);
//			// Copia los parametros del cliente al objeto
//			beanUtilsBean.populate(productoCierreStockBean, request.getParameterMap());
//			
//			// Retorno los datos de session
//        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
//        	
//        	productoCierreStockBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
//        	productoCierreStockBean.setUsuarioRegistro(usuarioBean.getUsuario());
//			
//			producto = logisticaService.grabarProductoCierreStock(productoCierreStockBean);
//			
//			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));				
//
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			return getBaseRespuesta(null);
//		}
//		return producto;
//	}
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @return objeto en formato json
//	 */
//	@RequestMapping(value = "/eliminarProductoCierreStock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Object eliminarProductoCierreStock(HttpServletRequest request, HttpServletResponse response) {
//		ProductoCierreStockBean producto = null;
//		try {			
//			String[] arrIdDetalleCierreStock = request.getParameter("arrIdDetalleCierreStock").split(Constantes.UNDERLINE);
//			// Retorno los datos de session
//        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
//        	
//			for (String codigo : arrIdDetalleCierreStock) {				
//				ProductoCierreStockBean productoCierreStockBean = new ProductoCierreStockBean(getInteger(codigo));
//
//	        	productoCierreStockBean.setUsuarioRegistro(usuarioBean.getUsuario());
//				
//				producto = logisticaService.eliminarProductoCierreStock(productoCierreStockBean);				
//			}
//
//			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				
//
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			return getBaseRespuesta(null);
//		}
//		return producto;
//	}
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @return objeto en formato json
//	 */
//	@RequestMapping(value = "/procesarProductosCierreStock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Object procesarProductosCierreStock(HttpServletRequest request, HttpServletResponse response) {
//		ProductoCierreStockBean producto = null;
//		try {
//			ProductoCierreStockBean productoCierreStockBean = new ProductoCierreStockBean();
//			
//			// Copia los parametros del cliente al objeto
//			BeanUtils.populate(productoCierreStockBean, request.getParameterMap());	
//			
//			// Retorno los datos de session
//        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
//
//        	productoCierreStockBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
//	        productoCierreStockBean.setUsuarioRegistro(usuarioBean.getUsuario());
//				
//			producto = logisticaService.procesarProductosCierreStock(productoCierreStockBean);				
//
//			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				
//
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			return getBaseRespuesta(null);
//		}
//		return producto;
//	}
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @return objeto en formato json
//	 */
//	@RequestMapping(value = "/listarStockAlmacenProductoLote", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Object listarStockAlmacenProductoLote(HttpServletRequest request, HttpServletResponse response) {
//		List<StockAlmacenProductoLoteBean> lista = null;
//		try {			
//			StockAlmacenProductoLoteBean stockAlmacenProductoLote = new StockAlmacenProductoLoteBean();			
//			// Copia los parametros del cliente al objeto
//			BeanUtils.populate(stockAlmacenProductoLote, request.getParameterMap());
//			stockAlmacenProductoLote.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
//			lista = logisticaService.listarStockAlmacenProductoLote(stockAlmacenProductoLote);
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			return getBaseRespuesta(null);
//		}
//		return lista;
//	}
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @return objeto en formato json
//	 */
//	@RequestMapping(value = "/actualizarAjusteProductoCierreStock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Object actualizarAjusteProductoCierreStock(HttpServletRequest request, HttpServletResponse response) {
//		ProductoCierreStockBean producto = null;
//		try {
//			ProductoCierreStockBean productoCierreStockBean = new ProductoCierreStockBean();
//			
//			// Copia los parametros del cliente al objeto
//			BeanUtils.populate(productoCierreStockBean, request.getParameterMap());	
//			
//			// Retorno los datos de session
//        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
//
//        	productoCierreStockBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
//	        productoCierreStockBean.setUsuarioRegistro(usuarioBean.getUsuario());
//				
//			producto = logisticaService.actualizarAjusteProductoCierreStock(productoCierreStockBean);				
//
//			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				
//
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			return getBaseRespuesta(null);
//		}
//		return producto;
//	}
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @return objeto en formato json
//	 */
//	@RequestMapping(value = "/procesarAjusteProductoCierreStock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Object procesarAjusteProductoCierreStock(HttpServletRequest request, HttpServletResponse response) {
//		ProductoCierreStockBean producto = null;
//		try {
//			ProductoCierreStockBean productoCierreStockBean = new ProductoCierreStockBean();
//			
//			// Copia los parametros del cliente al objeto
//			BeanUtils.populate(productoCierreStockBean, request.getParameterMap());	
//			
//			// Retorno los datos de session
//        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
//
//        	productoCierreStockBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
//	        productoCierreStockBean.setUsuarioRegistro(usuarioBean.getUsuario());
//				
//			producto = logisticaService.procesarProductosCierreStock(productoCierreStockBean);				
//
//			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				
//
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			return getBaseRespuesta(null);
//		}
//		return producto;
//	}
	
	/**
	 * @param codigoAnio 
	 * @param idAlmacen 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarExcel/{codigoAnio}/{idAlmacen}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("codigoAnio") String codigoAnio, 
								@PathVariable("idAlmacen") Integer idAlmacen,
								HttpServletResponse response) {
	    try {
	    	CierreStockBean cierreStockBean = new CierreStockBean();
			cierreStockBean.setCodigoAnio(verificaParametro(codigoAnio));
			cierreStockBean.setIdAlmacen(idAlmacen);
			List<CierreStockBean> lista = logisticaService.listarCierreStock(cierreStockBean);
	    	
			String file_name = "CierreStock";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteCierreStock reporte = new ReporteCierreStock();
		    HSSFWorkbook wb = reporte.generaReporteExcelCierreStock(lista);
			
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
//			List<DetalleProductoCierreStockBean> lista = logisticaService.listarDetalleProductoCierreStock(codigo);
//			if (isEmpty(lista)) {
//				return Constantes.COD_VALIDACION_GENERAL; // Sin registros asociados
//			}			
//			DetalleProductoCierreStockBean producto = lista.get(0);
//
//			ExportarArchivo printer = new ExportarArchivo();
//			StringBuilder jasperFile = new StringBuilder();
//			jasperFile.append(getPath(request));
//			jasperFile.append(File.separator);
//			jasperFile.append(Constantes.REPORT_PATH_ALMACENES);
//			if (producto.getFlagTipoProducto().equals(Constantes.ONE_STRING)) {
//				jasperFile.append("Control_Calidad_Alimentaria.jrxml");
//			} else {
//				jasperFile.append("Control_Calidad_No_Alimentaria.jrxml");
//			}
//			
//			Map<String, Object> parameters = new HashMap<String, Object>();
//
//			// Agregando los parámetros del reporte
//			StringBuilder logo_indeci_path = new StringBuilder();
//			logo_indeci_path.append(getPath(request));
//			logo_indeci_path.append(File.separator);
//			logo_indeci_path.append(Constantes.IMAGE_INDECI_REPORT_PATH);
//			parameters.put("P_LOGO_INDECI", logo_indeci_path.toString());			
//			StringBuilder logo_wfp_path = new StringBuilder();
//			logo_wfp_path.append(getPath(request));
//			logo_wfp_path.append(File.separator);
//			logo_wfp_path.append(Constantes.IMAGE_WFP_REPORT_PATH);
//			parameters.put("P_LOGO_WFP", logo_wfp_path.toString());			
//			StringBuilder logo_check_path = new StringBuilder();
//			logo_check_path.append(getPath(request));
//			logo_check_path.append(File.separator);
//			logo_check_path.append(Constantes.IMAGE_CHECK_REPORT_PATH);
//			parameters.put("P_LOGO_CHECK", logo_check_path.toString());			
//			StringBuilder logo_check_min_path = new StringBuilder();
//			logo_check_min_path.append(getPath(request));
//			logo_check_min_path.append(File.separator);
//			logo_check_min_path.append(Constantes.IMAGE_CHECK_REPORT_PATH);
//			parameters.put("P_LOGO_CHECK_MIN", logo_check_min_path.toString());			
//			parameters.put("P_NRO_CONTROL_CALIDAD", producto.getNroCierreStock());
//			parameters.put("P_DDI", producto.getNombreDdi());			
//			parameters.put("P_ALMACEN", producto.getNombreAlmacen());
//			parameters.put("P_FECHA_EMISION", producto.getFechaEmision());
//			parameters.put("P_TIPO_CONTROL", producto.getTipoCierreStock());
//			parameters.put("P_ALMACEN_ORIGEN_DESTINO", producto.getNombreAlmacen());
//			parameters.put("P_PROVEEDOR", producto.getProveedorDestino());
//			parameters.put("P_NRO_ORDEN_COMPRA", producto.getNroOrdenCompra());
//			parameters.put("P_CONCLUSIONES", producto.getConclusiones());
//			parameters.put("P_RECOMENDACIONES", producto.getRecomendaciones());
//
//			byte[] array = printer.exportPdf(jasperFile.toString(), parameters, lista);
//			InputStream input = new ByteArrayInputStream(array);
//	        
//	        String file_name = "Reporte_Control_Calidad";
//			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_PDF);
//	    	
//	        response.resetBuffer();
//            response.setContentType(Constantes.MIME_APPLICATION_PDF);
//            response.setHeader("Content-Disposition", "attachment; filename="+file_name);            
//			response.setHeader("Pragma", "no-cache");
//			response.setHeader("Cache-Control", "no-store");
//			response.setHeader("Pragma", "private");
//			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
//			response.setDateHeader("Expires", 1);
//			
//			byte[] buffer = new byte[4096];
//	    	int n = 0;
//
//	    	OutputStream output = response.getOutputStream();
//	    	while ((n = input.read(buffer)) != -1) {
//	    	    output.write(buffer, 0, n);
//	    	}
//	    	output.close();

	    	return Constantes.COD_EXITO_GENERAL;
	    } catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	return Constantes.COD_ERROR_GENERAL;
	    } 
	}
	
}
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

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ProductoStockAlmacenBean;
import pe.com.sigbah.common.bean.StockAlmacenBean;
import pe.com.sigbah.common.bean.StockAlmacenLoteBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.ExportarArchivo;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.LogisticaService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.gestion_almacenes.ReporteStockAlmacen;

/**
 * @className: StockAlmacenController.java
 * @description: 
 * @date: 17 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Controller
@RequestMapping("/gestion-almacenes/stock-almacen")
public class StockAlmacenController extends BaseController {

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
        	
        	model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
        	        	
        	model.addAttribute("lista_categoria", generalService.listarCategoria(new ItemBean(Constantes.THREE_INT)));
        	
        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar_stock_almacen";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarStockAlmacen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarStockAlmacen(HttpServletRequest request, HttpServletResponse response) {
		List<StockAlmacenBean> lista = null;
		try {			
			StockAlmacenBean stockAlmacenBean = new StockAlmacenBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(stockAlmacenBean, request.getParameterMap());
			stockAlmacenBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
			lista = logisticaService.listarStockAlmacen(stockAlmacenBean);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param tipoOrigen 
	 * @param idAlmacen 
	 * @param idDdi 
	 * @param idProducto 
	 * @param model
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/mantenimientoStockAlmacen/{tipoOrigen}/{idAlmacen}/{idDdi}/{idProducto}", method = RequestMethod.GET)
    public String mantenimientoStockAlmacen(@PathVariable("tipoOrigen") String tipoOrigen,
    										@PathVariable("idAlmacen") Integer idAlmacen,
    										@PathVariable("idDdi") Integer idDdi,
    										@PathVariable("idProducto") Integer idProducto,
    										Model model) {
        try {
        	StockAlmacenBean stockAlmacen = new StockAlmacenBean();

        	if (!isNullInteger(idProducto)) {
        		StockAlmacenBean params = new StockAlmacenBean();
        		params.setTipoOrigen(tipoOrigen);
        		params.setIdAlmacen(idAlmacen);
        		params.setIdDdi(idDdi);
        		params.setIdProducto(idProducto);
        		stockAlmacen = logisticaService.obtenerRegistroStockAlmacen(params);
        	}
        	
        	model.addAttribute("stockAlmacen", getParserObject(stockAlmacen));

        	model.addAttribute("lista_envase", generalService.listarEnvase(new ItemBean()));
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_stock_almacen";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/actualizarStockAlmacen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object actualizarStockAlmacen(HttpServletRequest request, HttpServletResponse response) {
		StockAlmacenBean stockAlmacen = null;
		try {			
			StockAlmacenBean stockAlmacenBean = new StockAlmacenBean();
			
			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);			
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Convierte los vacios en nulos en los decimales
			BigDecimalConverter con_decimal = new BigDecimalConverter(null);
			beanUtilsBean.getConvertUtils().register(con_decimal, BigDecimal.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(stockAlmacenBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
			stockAlmacenBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        	stockAlmacenBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	stockAlmacen = logisticaService.actualizarStockAlmacen(stockAlmacenBean);
        	stockAlmacen.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return stockAlmacen;
	}

	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/obtenerProductoStockAlmacen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object obtenerProductoStockAlmacen(HttpServletRequest request, HttpServletResponse response) {
		ProductoStockAlmacenBean producto = null;
		try {			
			StockAlmacenBean stockAlmacen = new StockAlmacenBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(stockAlmacen, request.getParameterMap());
			stockAlmacen.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
			producto = logisticaService.obtenerProductoStockAlmacen(stockAlmacen);
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
	@RequestMapping(value = "/listarStockAlmacenLote", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarStockAlmacenLote(HttpServletRequest request, HttpServletResponse response) {
		List<StockAlmacenLoteBean> lista = null;
		try {			
			StockAlmacenLoteBean stockAlmacenLote = new StockAlmacenLoteBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(stockAlmacenLote, request.getParameterMap());
			stockAlmacenLote.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
			lista = logisticaService.listarStockAlmacenLote(stockAlmacenLote);
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
	@RequestMapping(value = "/actualizarStockAlmacenLote", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object actualizarStockAlmacenLote(HttpServletRequest request, HttpServletResponse response) {
		StockAlmacenLoteBean stock = null;
		try {			
			StockAlmacenLoteBean stockAlmacenLoteBean = new StockAlmacenLoteBean();

			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);			
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Convierte los vacios en nulos en los decimales
			BigDecimalConverter con_decimal = new BigDecimalConverter(null);
			beanUtilsBean.getConvertUtils().register(con_decimal, BigDecimal.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(stockAlmacenLoteBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	stockAlmacenLoteBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	stock = logisticaService.actualizarStockAlmacenLote(stockAlmacenLoteBean);
			
        	stock.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return stock;
	}
	
	/**
	 * @param idAlmacen 
	 * @param codigoCategoria 
	 * @param nombreProducto
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarExcel/{codigoAnio}/{codigoMes}/{nombreProducto}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("idAlmacen") Integer idAlmacen, 
								@PathVariable("codigoMes") String codigoCategoria, 
								@PathVariable("nombreProducto") String nombreProducto,
								HttpServletResponse response) {
	    try {
	    	StockAlmacenBean stockAlmacenBean = new StockAlmacenBean();
	    	stockAlmacenBean.setIdAlmacen(idAlmacen);
	    	stockAlmacenBean.setCodigoCategoria(verificaParametro(codigoCategoria));
	    	stockAlmacenBean.setNombreProducto(nombreProducto);
	    	stockAlmacenBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
	    	
			List<StockAlmacenBean> lista = logisticaService.listarStockAlmacen(stockAlmacenBean);
	    	
			String file_name = "StockAlmacen";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteStockAlmacen reporte = new ReporteStockAlmacen();
		    HSSFWorkbook wb = reporte.generaReporteExcelStockAlmacen(lista);
			
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
	 * @param tipoOrigen 
	 * @param idAlmacen 
	 * @param idDdi 
	 * @param idProducto 
	 * @param request 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarPdf/{tipoOrigen}/{idAlmacen}/{idDdi}/{idProducto}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarPdf(@PathVariable("tipoOrigen") String tipoOrigen,
							  @PathVariable("idAlmacen") Integer idAlmacen,
							  @PathVariable("idDdi") Integer idDdi,
							  @PathVariable("idProducto") Integer idProducto, 
							  HttpServletRequest request, 
							  HttpServletResponse response) {
		try {
			StockAlmacenBean params = new StockAlmacenBean();
    		params.setTipoOrigen(tipoOrigen);
    		params.setIdAlmacen(idAlmacen);
    		params.setIdDdi(idDdi);
    		params.setIdProducto(idProducto);
    		StockAlmacenBean stockAlmacen = logisticaService.obtenerRegistroStockAlmacen(params);

    		StockAlmacenLoteBean paramsLote = new StockAlmacenLoteBean();
    		paramsLote.setTipoOrigen(tipoOrigen);
    		paramsLote.setIdAlmacen(idAlmacen);
    		paramsLote.setIdDdi(idDdi);
    		paramsLote.setIdProducto(idProducto);    		
    		List<StockAlmacenLoteBean> lista = logisticaService.listarStockAlmacenLote(paramsLote);

			ExportarArchivo printer = new ExportarArchivo();
			StringBuilder jasperFile = new StringBuilder();
			jasperFile.append(getPath(request));
			jasperFile.append(File.separator);
			jasperFile.append(Constantes.REPORT_PATH_ALMACENES);
			jasperFile.append("Control_Calidad_No_Alimentaria.jrxml");
			
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
			parameters.put("P_NRO_CONTROL_CALIDAD", stockAlmacen.getNroKardex());
//			parameters.put("P_DDI", stockAlmacen.getNombreDdi());			
//			parameters.put("P_ALMACEN", stockAlmacen.getNombreAlmacen());
//			parameters.put("P_FECHA_EMISION", stockAlmacen.getFechaEmision());
//			parameters.put("P_TIPO_CONTROL", stockAlmacen.getTipoControlCalidad());
//			parameters.put("P_ALMACEN_ORIGEN_DESTINO", stockAlmacen.getNombreAlmacen());
//			parameters.put("P_PROVEEDOR", stockAlmacen.getProveedorDestino());
//			parameters.put("P_NRO_ORDEN_COMPRA", stockAlmacen.getNroOrdenCompra());
//			parameters.put("P_CONCLUSIONES", stockAlmacen.getConclusiones());
//			parameters.put("P_RECOMENDACIONES", stockAlmacen.getRecomendaciones());

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

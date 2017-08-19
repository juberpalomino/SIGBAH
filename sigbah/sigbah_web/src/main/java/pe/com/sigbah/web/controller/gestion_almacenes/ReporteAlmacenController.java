package pe.com.sigbah.web.controller.gestion_almacenes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.sql.Types;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.OrdenSalidaBean;
import pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean;
import pe.com.sigbah.common.bean.ProductoSalidaBean;
import pe.com.sigbah.common.bean.ProyectoManifiestoBean;
import pe.com.sigbah.common.bean.StockProductoKardexBean;
import pe.com.sigbah.common.bean.StockProductoLoteBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.LogisticaService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.gestion_almacenes.ReporteAlmacen;

/**
 * @className: ReporteController.java
 * @description: 
 * @date: 17 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Controller
@RequestMapping("/gestion-almacenes/reporte-almacen")
public class ReporteAlmacenController extends BaseController {

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
        	
        	model.addAttribute("lista_mes", generalService.listarMeses(new ItemBean()));
        	
        	StockProductoKardexBean stockProductoKardexBean = new StockProductoKardexBean();
        	stockProductoKardexBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        	stockProductoKardexBean.setIdDdi(usuarioBean.getIdDdi());
        	stockProductoKardexBean.setIdAlmacen(usuarioBean.getIdAlmacen());
        	model.addAttribute("lista_producto", logisticaService.listarStockProductoKardex(stockProductoKardexBean));
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar_reportes_almacen";
    }
	
	/**
	 * @param tipoReporte 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarTipoMovimiento", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarTipoMovimiento(@RequestParam(value="tipoReporte") String tipoReporte) {
		List<ItemBean> lista = null;
		try {			
			if (tipoReporte.equals(Constantes.ONE_STRING)) { // Reporte de Proyectos de Manifiesto 
				lista = generalService.listarTipoMovimiento(new ItemBean(Constantes.TWO_INT, Constantes.TWO_INT));
			} else if (tipoReporte.equals(Constantes.TWO_STRING)) { // Reporte de Ordenes de Salida  
				lista = generalService.listarTipoMovimientoPm();
			} else if (tipoReporte.equals(Constantes.THREE_STRING)) { // Reporte de Ordenes de Ingreso  
				lista = generalService.listarTipoMovimiento(new ItemBean(Constantes.TWO_INT, Constantes.ONE_INT));
			} else if (tipoReporte.equals(Constantes.FOUR_STRING)) { // Reporte de Guias de Remision  
				lista = generalService.listarTipoMovimiento(new ItemBean(Constantes.TWO_INT, Constantes.TWO_INT));
			}
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
	@RequestMapping(value = "/listarStockProductoLote", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarStockProductoLote(HttpServletRequest request, HttpServletResponse response) {
		List<StockProductoLoteBean> lista = null;
		try {		
			StockProductoLoteBean stockProductoLote = new StockProductoLoteBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(stockProductoLote, request.getParameterMap());
			stockProductoLote.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
			lista = logisticaService.listarStockProductoLote(stockProductoLote);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param tipoReporte 
	 * @param anio 
	 * @param mesInicio 
	 * @param mesFin  
	 * @param tipoMovimiento 
	 * @param flagProducto 
	 * @param codigoProducto 
	 * @param nroLote 
	 * @param request 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarPdf", method = RequestMethod.GET)
	@ResponseBody
	public String exportarPdf(@RequestParam("tipoReporte") Integer tipoReporte,
							  @RequestParam("anio") String anio,
							  @RequestParam("mesInicio") String mesInicio,
							  @RequestParam("mesFin") String mesFin,							  
							  @RequestParam("tipoMovimiento") Integer tipoMovimiento,
							  @RequestParam("flagProducto") String flagProducto,
							  @RequestParam("codigoProducto") Integer codigoProducto,
							  @RequestParam("nroLote") String nroLote,
							  HttpServletRequest request, 
							  HttpServletResponse response) {
	    try {
	    	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
	    	
	    	StringBuilder file_path = new StringBuilder();
	    	file_path.append(getPath(request));
	    	file_path.append(File.separator);
	    	file_path.append(Constantes.UPLOAD_PATH_FILE_TEMP);
	    	file_path.append(File.separator);
	    	file_path.append(Calendar.getInstance().getTime().getTime());
	    	file_path.append(Constantes.EXTENSION_FORMATO_PDF);
	    	
	    	String file_name = null;
	    	ReporteAlmacen reporte = new ReporteAlmacen();
	    	
	    	if (tipoReporte.equals(Constantes.ONE_INT)) { // Reporte de Proyectos de Manifiesto
	    		file_name = "Reporte_Proyectos_Manifiesto";
	    		if (flagProducto.equals(Constantes.ZERO_STRING)) { // No incluye productos
	    			ProyectoManifiestoBean proyectoManifiestoBean = new ProyectoManifiestoBean();
	    			proyectoManifiestoBean.setCodigoAnio(anio);
	    			proyectoManifiestoBean.setCodigoMesInicio(mesInicio);
	    			proyectoManifiestoBean.setCodigoMesFin(mesFin);
	    			proyectoManifiestoBean.setIdAlmacen(usuarioBean.getIdAlmacen());
	    			proyectoManifiestoBean.setIdMovimiento(tipoMovimiento);
	    			proyectoManifiestoBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
	    			List<ProyectoManifiestoBean> listaProyectoManifiesto = logisticaService.listarReporteProyectoManifiesto(proyectoManifiestoBean);
	    			reporte.generaPDFReporteProyectoManifiesto(file_path.toString(), listaProyectoManifiesto);
	    		} else { // Si incluye productos
	    			ProductoProyectoManifiestoBean productoProyectoManifiestoBean = new ProductoProyectoManifiestoBean();
	    			productoProyectoManifiestoBean.setCodigoAnio(anio);
	    			productoProyectoManifiestoBean.setCodigoMesInicio(mesInicio);
	    			productoProyectoManifiestoBean.setCodigoMesFin(mesFin);
	    			productoProyectoManifiestoBean.setIdAlmacen(usuarioBean.getIdAlmacen());
	    			productoProyectoManifiestoBean.setIdMovimiento(tipoMovimiento);
	    			productoProyectoManifiestoBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
	    			List<ProductoProyectoManifiestoBean> listaDetalleProyectoManifiesto = logisticaService.listarReporteDetalleProyectoManifiesto(productoProyectoManifiestoBean);
	    			reporte.generaPDFReporteDetalleProyectoManifiesto(file_path.toString(), listaDetalleProyectoManifiesto);
	    		}
	    	} else if (tipoReporte.equals(Constantes.TWO_INT)) { // Reporte de Ordenes de Salida
	    		file_name = "Reporte_Ordenes_Salida";
	    		if (flagProducto.equals(Constantes.ZERO_STRING)) { // No incluye productos
	    			OrdenSalidaBean ordenSalidaBean = new OrdenSalidaBean();
	    			ordenSalidaBean.setCodigoAnio(anio);
	    			ordenSalidaBean.setCodigoMesInicio(mesInicio);
	    			ordenSalidaBean.setCodigoMesFin(mesFin);
	    			ordenSalidaBean.setIdAlmacen(usuarioBean.getIdAlmacen());
	    			ordenSalidaBean.setIdMovimiento(tipoMovimiento);
	    			ordenSalidaBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
	    			List<OrdenSalidaBean> listaOrdenSalida = logisticaService.listarReporteOrdenSalida(ordenSalidaBean);
	    			reporte.generaPDFReporteOrdenSalida(file_path.toString(), listaOrdenSalida);
	    		} else { // Si incluye productos
	    			ProductoSalidaBean productoSalidaBean = new ProductoSalidaBean();
	    			productoSalidaBean.setCodigoAnio(anio);
	    			productoSalidaBean.setCodigoMesInicio(mesInicio);
	    			productoSalidaBean.setCodigoMesFin(mesFin);
	    			productoSalidaBean.setIdAlmacen(usuarioBean.getIdAlmacen());
	    			productoSalidaBean.setIdMovimiento(tipoMovimiento);
	    			productoSalidaBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
	    			List<ProductoSalidaBean> listaDetalleOrdenSalida = logisticaService.listarReporteDetalleOrdenSalida(productoSalidaBean);
	    			reporte.generaPDFReporteDetalleOrdenSalida(file_path.toString(), listaDetalleOrdenSalida);
	    		}
			} else if (tipoReporte.equals(Constantes.THREE_INT)) { // Reporte de Ordenes de Ingreso
				    		
			} else if (tipoReporte.equals(Constantes.FOUR_INT)) { // Reporte de Guias de Remision
				
			} else if (tipoReporte.equals(Constantes.FIVE_INT)) { // Reporte de Kardex y Bincard
				
			}
	    	
	    	file_name = file_name.concat(Constantes.EXTENSION_FORMATO_PDF);

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
	
	/**
	 * @param tipoReporte 
	 * @param anio 
	 * @param mesInicio 
	 * @param mesFin  
	 * @param tipoMovimiento 
	 * @param flagProducto 
	 * @param codigoProducto 
	 * @param nroLote 
	 * @param request 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarExcel", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@RequestParam("tipoReporte") Integer tipoReporte,
							  	@RequestParam("anio") String anio,
							  	@RequestParam("mesInicio") String mesInicio,
							  	@RequestParam("mesFin") String mesFin,							  
							  	@RequestParam("tipoMovimiento") Integer tipoMovimiento,
							  	@RequestParam("flagProducto") String flagProducto,
							  	@RequestParam("codigoProducto") Integer codigoProducto,
							  	@RequestParam("nroLote") String nroLote,
							  	HttpServletRequest request, 
							  	HttpServletResponse response) {
	    try {
//	    	List<ProgramacionAlimentoBean> listaProgramacionAlimento = programacionRequerimientoService.listarProgramacionAlimento(idProgramacion, arrIdProducto);
			
			String file_name = "ProgramacionAlimento";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteAlmacen reporte = new ReporteAlmacen();
//		    HSSFWorkbook wb = reporte.generaReporteExcelAlimento(listaProgramacionAlimento, arrIdProducto, arrNombreProducto, arrUnidadProducto);
			
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
//	    	wb.write(out); // We write in that flow
	    	out.flush(); // We emptied the flow
	    	out.close(); // We close the flow
	    	
	    	return Constantes.COD_EXITO_GENERAL;  
	    } catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	return Constantes.COD_ERROR_GENERAL;
	    } 
	}

}
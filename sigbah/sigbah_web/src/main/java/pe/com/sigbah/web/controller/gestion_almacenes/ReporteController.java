package pe.com.sigbah.web.controller.gestion_almacenes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import pe.com.sigbah.common.bean.ItemBean;
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
@RequestMapping("/gestion-almacenes/reporte")
public class ReporteController extends BaseController {

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
	 * @param anio 
	 * @param mesInicio 
	 * @param mesFin 
	 * @param tipoReporte 
	 * @param tipoMovimiento 
	 * @param flagProducto 
	 * @param codigoProducto 
	 * @param request 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarPdf/{anio}/{mesInicio}/{mesFin}/{tipoReporte}/{tipoMovimiento}/{flagProducto}/{codigoProducto}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarPdf(@PathVariable("anio") String anio,
							  @PathVariable("mesInicio") String mesInicio,
							  @PathVariable("mesFin") String mesFin,
							  @PathVariable("tipoReporte") Integer tipoReporte,
							  @PathVariable("tipoMovimiento") Integer tipoMovimiento,
							  @PathVariable("flagProducto") String flagProducto,
							  @PathVariable("codigoProducto") Integer codigoProducto,
							  HttpServletRequest request, 
							  HttpServletResponse response) {
	    try {
	    	
//	    	OrdenSalidaBean ordenSalida = logisticaService.obtenerRegistroOrdenSalida(codigo, anio);
//	    	ProductoSalidaBean producto = new ProductoSalidaBean();
//	    	producto.setIdSalida(codigo);
//	    	List<ProductoSalidaBean> listaProducto = logisticaService.listarProductoSalida(producto);
//	    	
//	    	DocumentoSalidaBean documento = new DocumentoSalidaBean();
//	    	documento.setIdSalida(codigo);
//	    	documento.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
//	    	List<DocumentoSalidaBean> listaDocumento = logisticaService.listarDocumentoSalida(documento);	    	

	    	StringBuilder file_path = new StringBuilder();
	    	file_path.append(getPath(request));
	    	file_path.append(File.separator);
	    	file_path.append(Constantes.UPLOAD_PATH_FILE_TEMP);
	    	file_path.append(File.separator);
	    	file_path.append(Calendar.getInstance().getTime().getTime());
	    	file_path.append(Constantes.EXTENSION_FORMATO_PDF);
	    	
	    	String file_name = "Orden_Salida";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_PDF);
			
			ReporteAlmacen reporte = new ReporteAlmacen();
//			reporte.generaPDFReporteSalidas(file_path.toString(), ordenSalida, listaProducto, listaDocumento);
			
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
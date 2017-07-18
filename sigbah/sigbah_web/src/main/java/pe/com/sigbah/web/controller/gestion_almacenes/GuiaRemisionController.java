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

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.GuiaRemisionBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.LogisticaService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.gestion_almacenes.ReporteGuiaRemision;

/**
 * @className: GuiaRemisionController.java
 * @description: 
 * @date: 17 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Controller
@RequestMapping("/gestion-almacenes/guia-remision")
public class GuiaRemisionController extends BaseController {

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
        	
        	model.addAttribute("lista_mes", generalService.listarMeses(new ItemBean()));
        	
        	model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
        	        	
        	model.addAttribute("lista_tipo_movimiento", generalService.listarTipoMovimiento(new ItemBean(Constantes.TWO_INT, Constantes.TWO_INT)));
        	
        	GuiaRemisionBean guiaRemision = new GuiaRemisionBean();
        	
        	ControlCalidadBean parametroAlmacenActivo = new ControlCalidadBean();
    		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
    		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
    		List<ControlCalidadBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
    		if (!isEmpty(listaAlmacenActivo)) {
    			guiaRemision.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
    			guiaRemision.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
    			guiaRemision.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
    			guiaRemision.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
    			guiaRemision.setCodigoMes(listaAlmacenActivo.get(0).getCodigoMes());
    		}
    		
    		model.addAttribute("guiaRemision", getParserObject(guiaRemision));
        	
        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar_guia_remision";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarGuiaRemision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarGuiaRemision(HttpServletRequest request, HttpServletResponse response) {
		List<GuiaRemisionBean> lista = null;
		try {			
			GuiaRemisionBean guiaRemisionBean = new GuiaRemisionBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(guiaRemisionBean, request.getParameterMap());			
			lista = logisticaService.listarGuiaRemision(guiaRemisionBean);
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
	@RequestMapping(value = "/mantenimientoGuiaRemision/{codigo}", method = RequestMethod.GET)
    public String mantenimientoGuiaRemision(@PathVariable("codigo") Integer codigo, Model model) {
        try {
        	GuiaRemisionBean guiaRemision = new GuiaRemisionBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);

        	if (!isNullInteger(codigo)) {        		
        		guiaRemision = logisticaService.obtenerRegistroGuiaRemision(codigo);
        	}
        	
        	model.addAttribute("guiaRemision", getParserObject(guiaRemision));

        	model.addAttribute("lista_estado", generalService.listarEstado(new ItemBean(null, Constantes.FOUR_INT)));
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_guia_remision";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/anularGuiaRemision", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object anularGuiaRemision(HttpServletRequest request, HttpServletResponse response) {
		GuiaRemisionBean guiaRemision = null;
		try {			
			GuiaRemisionBean guiaRemisionBean = new GuiaRemisionBean();
			
			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(guiaRemisionBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	guiaRemisionBean.setUsuarioRegistro(usuarioBean.getUsuario());
        	guiaRemisionBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
			
        	logisticaService.anularGuiaRemision(guiaRemisionBean);
        	
        	guiaRemision = logisticaService.insertarGuiaRemision(guiaRemisionBean);
        	
        	guiaRemision = logisticaService.obtenerRegistroGuiaRemision(guiaRemision.getIdGuiaRemision());
        	guiaRemision.setIdEstado(Constantes.ESTADO_ACTIVO);
        	guiaRemision.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return guiaRemision;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/actualizarGuiaRemision", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object actualizarGuiaRemision(HttpServletRequest request, HttpServletResponse response) {
		GuiaRemisionBean guiaRemision = null;
		try {			
			GuiaRemisionBean guiaRemisionBean = new GuiaRemisionBean();
			
			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(guiaRemisionBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
			guiaRemisionBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        	guiaRemisionBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	guiaRemision = logisticaService.actualizarGuiaRemision(guiaRemisionBean);
        	guiaRemision.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return guiaRemision;
	}
	
	/**
	 * @param codigoAnio 
	 * @param codigoMes 
	 * @param idAlmacen
	 * @param codigoMovimiento 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarExcel/{codigoAnio}/{codigoMes}/{idAlmacen}/{codigoMovimiento}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("codigoAnio") String codigoAnio, 
								@PathVariable("codigoMes") String codigoMes, 
								@PathVariable("idAlmacen") Integer idAlmacen,
								@PathVariable("codigoMovimiento") String codigoMovimiento, 
								HttpServletResponse response) {
	    try {
	    	GuiaRemisionBean guiaRemisionBean = new GuiaRemisionBean();
	    	guiaRemisionBean.setCodigoAnio(verificaParametro(codigoAnio));
	    	guiaRemisionBean.setCodigoMes(verificaParametro(codigoMes));
	    	guiaRemisionBean.setIdAlmacen(idAlmacen);
	    	guiaRemisionBean.setCodigoMovimiento(codigoMovimiento);
	    	
			List<GuiaRemisionBean> lista = logisticaService.listarGuiaRemision(guiaRemisionBean);
	    	
			String file_name = "GuiaRemision";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteGuiaRemision reporte = new ReporteGuiaRemision();
		    HSSFWorkbook wb = reporte.generaReporteExcelGuiaRemision(lista);
			
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
//	@RequestMapping(value = "/exportarPdf/{codigo}", method = RequestMethod.GET)
//	@ResponseBody
//	public String exportarPdf(@PathVariable("codigo") Integer codigo, HttpServletRequest request, HttpServletResponse response) {
//	    try {
//	    	
//	    	GuiaRemisionBean guiaRemision = logisticaService.obtenerRegistroGuiaRemision(codigo);
//	    	ProductoGuiaRemisionBean producto = new ProductoGuiaRemisionBean();
//	    	producto.setIdGuiaRemision(codigo);
//	    	List<ProductoGuiaRemisionBean> listaProducto = logisticaService.listarProductoGuiaRemision(producto);
//	    	
//	    	DocumentoGuiaRemisionBean documento = new DocumentoGuiaRemisionBean();
//	    	documento.setIdGuiaRemision(codigo);
//	    	List<DocumentoGuiaRemisionBean> listaDocumento = logisticaService.listarDocumentoGuiaRemision(documento);	    	
//
//	    	StringBuilder file_path = new StringBuilder();
//	    	file_path.append(getPath(request));
//	    	file_path.append(File.separator);
//	    	file_path.append(Constantes.UPLOAD_PATH_FILE_TEMP);
//	    	file_path.append(File.separator);
//	    	file_path.append(Calendar.getInstance().getTime().getTime());
//	    	file_path.append(Constantes.EXTENSION_FORMATO_PDF);
//	    	
//	    	String file_name = "Proyecto_Manifiesto";
//			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_PDF);
//			
//			ReporteGuiaRemision reporte = new ReporteGuiaRemision();
//			reporte.generaPDFReporteGuiaRemision(file_path.toString(), guiaRemision, listaProducto, listaDocumento);
//			
//			response.resetBuffer();
//            response.setContentType(Constantes.MIME_APPLICATION_PDF);
//            response.setHeader("Content-Disposition", "attachment; filename="+file_name);            
//			response.setHeader("Pragma", "no-cache");
//			response.setHeader("Cache-Control", "no-store");
//			response.setHeader("Pragma", "private");
//			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
//			response.setDateHeader("Expires", 1);
//	    	
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			baos = convertPDFToByteArrayOutputStream(file_path.toString());
//			
//			// Captured backflow
//	        OutputStream out = response.getOutputStream();
//	        baos.writeTo(out); // We write in that flow
//	        out.flush(); // We emptied the flow
//	    	out.close(); // We close the flow
//	    	
//	    	File file_temp = new File(file_path.toString());
//    		if (file_temp.delete()) {
//    			LOGGER.info("[exportarPdf] "+file_temp.getName()+" se borra el archivo temporal.");
//    		} else {
//    			LOGGER.info("[exportarPdf] "+file_temp.getName()+" no se logró borrar el archivo temporal.");
//    		}
//    		
//	    	return Constantes.COD_EXITO_GENERAL;
//	    } catch (Exception e) {
//	    	LOGGER.error(e.getMessage(), e);
//	    	return Constantes.COD_ERROR_GENERAL;
//	    } 
//	}
	
}

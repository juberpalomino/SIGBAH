package pe.com.sigbah.web.controller.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import pe.com.sigbah.common.util.Constantes;

/**
 * @className: ArchivoController.java
 * @description: Clase generica para la carga y descarga de ficheros en Alfresco.
 * @date: 29 de jun. de 2017
 * @author: SUMERIO.
 */
@Controller
@RequestMapping("/common/archivo")
public class ArchivoController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ManageAlfresco manageAlfresco;
	
	@Autowired
	private ServletContext context;
	
	/**
	 * @param servletContext
	 */
	public void setServletContext(ServletContext servletContext) {
	    this.context = servletContext;
	}	
	
	/**
	 * @param request
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/cargarArchivo", method = RequestMethod.POST)
	@ResponseBody
	public String cargarArchivo(MultipartHttpServletRequest request, HttpServletResponse response) {
		String alfrescoId = null;
		try {			
			StringBuilder path = new StringBuilder();
			path.append(getPath(request));
			path.append(File.separator);
			path.append(getPropertyValue("params.file.resources"));
			path.append(File.separator);
			path.append(getPropertyValue("params.file.upload"));
			
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());

			StringBuilder file_name = new StringBuilder();
			int pos_file_name = mpf.getOriginalFilename().lastIndexOf(Constantes.PUNTO);
			file_name.append(mpf.getOriginalFilename().substring(0, pos_file_name));
			file_name.append(Constantes.UNDERLINE);
			file_name.append(Calendar.getInstance().getTime().getTime());
			file_name.append(mpf.getOriginalFilename().substring(pos_file_name));
			
			
			StringBuilder file_doc = new StringBuilder();
			file_doc.append(path.toString());
			file_doc.append(File.separator);
			file_doc.append(file_name.toString());
			
			mpf.transferTo(new File(file_doc.toString()));

			String contentType = request.getContentType();
			
			String uploadDirectory = getPropertyValue(request.getParameter("uploadDirectory"));
			
			alfrescoId = manageAlfresco.uploadFile(file_doc.toString(), uploadDirectory, contentType);
			
			alfrescoId = StringUtils.trimToEmpty(alfrescoId);
			
			if (alfrescoId.equals(Constantes.CODIGO_ERROR_401) ||
				alfrescoId.equals(Constantes.CODIGO_ERROR_403) ||
				alfrescoId.equals(Constantes.CODIGO_ERROR_404) ||
				alfrescoId.equals(Constantes.CODIGO_ERROR_500)) {
				throw new Exception();
			}
			
			File file_temp = new File(file_doc.toString());
    		if (file_temp.delete()){
    			LOGGER.info("[almacenCargarArchivo] "+file_temp.getName()+" se borra el archivo temporal.");
    		} else {
    			LOGGER.info("[almacenCargarArchivo] "+file_temp.getName()+" no se logró borrar el archivo temporal.");
    		}
				
			LOGGER.info("[almacenCargarArchivo] Se guardo en Alfresco.");

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
	    	return Constantes.COD_ERROR_GENERAL;
		}
		return alfrescoId;
	}
	
	/**
	 * @param codigo 
	 * @param nombre 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarArchivo/{codigo}/{nombre}/", method = RequestMethod.GET)
	@ResponseBody
	public String exportarArchivo(@PathVariable("codigo") String codigo, @PathVariable("nombre") String nombre, HttpServletResponse response) {
	    try {			
	    	LOGGER.info("[exportarArchivo] Archivo: "+nombre);
	    	
			String urlString = manageAlfresco.downloadFile(codigo);
			
			response.resetBuffer();
            response.setContentType(Constantes.MIME_APPLICATION_PDF);
            response.setHeader("Content-Disposition", "attachment; filename="+nombre);            
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "private");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setDateHeader("Expires", 1);
           
	    	URL url = new URL(urlString);

	    	URLConnection connection = url.openConnection();

	    	InputStream input = connection.getInputStream();
	    	
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
	
	/**
	 * Método que lee una propiedad y retorna la ruta de la web principal.
	 * @param key - Llave de codigo de valor;
	 * @return El valor de la propiedad correspondiente a la llave.
	 */
	private String getPropertyValue(String key) {
		Properties properties = new Properties();
		String directorio = null;
		InputStream inputStream = null;
		try {
			inputStream = context.getResourceAsStream(Constantes.FICHERO_CONFIGURACION);
			properties.load(inputStream);
			if (inputStream == null) {
				throw new FileNotFoundException("Archivo properties '" + Constantes.FICHERO_CONFIGURACION +"' no se encuentra en el classpath");
			}
			directorio = properties.getProperty(key);
		} catch (IOException e) {			
			LOGGER.error(e.getMessage(), e);
		} finally {
		    if (inputStream != null) {
		    	try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
		    }
		}
		return directorio;
	}
	
}
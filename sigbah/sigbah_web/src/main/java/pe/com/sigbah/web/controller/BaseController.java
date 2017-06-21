package pe.com.sigbah.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import pe.com.sigbah.common.bean.BaseOutputBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;

/**
 * @className: BaseController.java
 * @description: Controlador genérico.
 * @date: 30/04/2015 00:56:36
 * @author: SUMERIO
 */
public class BaseController implements Serializable {

	private static final long serialVersionUID = 6674829455049670947L;
	protected transient final Log LOGGER = LogFactory.getLog(getClass());
	protected transient UsuarioBean usuarioBean = null;
	protected transient BaseOutputBean baseOutputBean;
	private static final String FICHERO_PROPERTIES = "/WEB-INF/i18n/config.properties";
	
	@Autowired
	private ServletContext context;
	
	/**
	 * @param servletContext
	 */
	public void setServletContext(ServletContext servletContext) {
	    this.context = servletContext;
	}
	
	/**
	 * Devuelve el RequestAttributes.
	 * @return Devuelve el RequestAttributes.
	 */
	public RequestAttributes context() {
		return RequestContextHolder.currentRequestAttributes();
	}
	
	/**
	 * Verifica si la cadena esta vacía
	 * @param campo - valor del parámetro a evaluar, tipo String
	 * @return true si es vacío o nulo y false lo contrario
	 */
	public static boolean isNullOrEmpty(String campo) { 
	    return campo == null || campo.trim().length() == 0;
	}
	
	/**
	 * Verifica si la cadena esta vacía
	 * @param campo - valor del parámetro a evaluar, tipo Object
	 * @return true si es vacío o nulo y false lo contrario
	 */
	public static boolean isNull(Object campo) {
		if (campo == null) {
			return true;
		} else {
			return false;
		}
	}	
	
	/**
	 * Verifica si la lista esta vacía
	 * @param coll - Lista parámetro a evaluar, tipo Collection.
	 * @return true si es vacío o nulo y false lo contrario.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection coll) {
	    return (coll == null || coll.isEmpty());
	}
	
	/**
	 * Verifica si el entero es nulo o con valor 0 por defecto.
	 * @param campo - valor del parámetro a evaluar, tipo Integer.
	 * @return true si es vacío o nulo y false lo contrario
	 */
	public static boolean isNullInteger(Integer campo) {
		if (!isNull(campo) && campo > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Verifica si el entero es nulo o con valor 0 por defecto.
	 * @param campo - valor del parámetro a evaluar, tipo Long.
	 * @return true si es vacío o nulo y false lo contrario
	 */
	public static boolean isNullLong(Long campo) {
		if (!isNull(campo) && campo > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Convierte PDF a ByteArrayOutputStream primero tienes que crear el objeto InputStream.
	 * @param fileName - Nombre de reporte, tipo String.
	 * @return retorna el objeto tipo ByteArrayOutputStream.
	 */
	public static ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {		 
		InputStream inputStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try { 
			inputStream = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream(); 
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baos;
	}
	
	/**
	 * Retorna la ruta de los recursos del proyecto.
	 * @param request - Nombre de reporte, tipo HttpServletRequest.
	 * @return retorna la ruta de los recursos del proyecto.
	 */
	@SuppressWarnings("deprecation")
	public static String getPath(HttpServletRequest request) {	
		String path = request.getContextPath();
    	String split_path[] = path.split("/");
        return request.getRealPath(split_path[0]);
	}
	
	/**
	 * Retorna el valor parseado.
	 * @param campo - Valor del parámetro a evaluar, tipo String.
	 * @return valor - Valor entero sino retorna nulo.
	 */
	public static Integer getInteger(String campo) {
		Integer valor = null;
		if (!isNullOrEmpty(campo)) {
			valor =	Integer.parseInt(campo);	
		}
		return valor; 	
	}
	
	/**
	 * Retorna el valor parseado.
	 * @param campo - Valor del parámetro a evaluar, tipo String.
	 * @return date - Valor long sino retorna nulo.
	 */
	public static Long getLong(String campo) {
		Long valor = null;
		if (!isNullOrEmpty(campo)) {
			valor =	Long.parseLong(campo);	
		}
		return valor; 	
	}
	
	/**
	 * Retorna el valor parseado.
	 * @param campo - Valor del parámetro a evaluar, tipo String.
	 * @return date - Valor long sino retorna nulo.
	 */
	public static BigDecimal getBigDecimal(String campo) {
		BigDecimal valor = null;
		if (!isNullOrEmpty(campo)) {
			valor = new BigDecimal(campo);
		}
		return valor; 	
	}
	
	/**
	 * Retorna el valor de la fecha formateada.
	 * @param campo - Valor del parámetro a evaluar, tipo String.
	 * @return date - Valor de fecha sino retorna nulo.
	 */
	public static Date getDate(String campo) {
		Date fecha = null;
		if (!isNullOrEmpty(campo)) {
			fecha =	DateUtil.obtenerFechaParseada(Constantes.FORMATO_FECHA, campo);	
		}
		return fecha; 	
	}
	
	/**
	 * Retorna el valor parseado.
	 * @param campo - Valor del parámetro a evaluar, tipo Object.
	 * @return valor - Valor de la cadena.
	 */
	public static String getString(Object campo) {
		if (campo != null) {
			if (campo instanceof Integer) {
				return String.valueOf((Integer) campo);
			} else if (campo instanceof Long) {
				return String.valueOf((Long) campo);
			} else if (campo instanceof BigDecimal) {
				return String.valueOf((BigDecimal) campo);
			} else {
				return (String) campo;
			}
		}
		return Constantes.EMPTY; 	
	}
	
	/**
	 * 
	 * @param tiempo 
	 * @return obtiene el tiempo que demoro un proceso
	 */
	public static String obtenerTiempoTranscurrido(long tiempo) {
		String segundos = String.valueOf((int) (tiempo / 1000) % 60);
		String minutos = String.valueOf((int) ((tiempo / (1000 * 60)) % 60));
		String horas = String.valueOf((int) ((tiempo / (1000 * 60 * 60)) % 24));
		return horas.concat(":").concat(minutos).concat(":").concat(segundos);
	}

	/**
	 * Obtener mensaje i18n con Locale.Default
	 * 
	 * @param messageSource
	 * @param mensaje
	 * @return Retorna el mensaje del archivo properties.
	 */
	public static String getMensaje(MessageSource messageSource, String mensaje) {
		return messageSource.getMessage(mensaje, null, Locale.getDefault());
	}

	/**
	 * Obtener mensaje i18n con Locale.Default y parametros
	 * 
	 * @param messageSource
	 * @param param
	 * @param mensaje
	 * @return Retorna el mensaje del archivo properties.
	 */
	public static String getMensaje(MessageSource messageSource, Object[] param, String mensaje) {
		return messageSource.getMessage(mensaje, param, Locale.getDefault());
	}
	
	/**
	 * Método que lee una propiedad y retorna la ruta de la web principal.
	 * @param key - Llave de codigo de valor;
	 * @return El valor de la propiedad correspondiente a la llave.
	 */
	public String getPropiedad(String key) {
		Properties properties = new Properties();
		String directorio = null;
		InputStream inputStream = null;
		try {
			inputStream = context.getResourceAsStream(FICHERO_PROPERTIES);
			properties.load(inputStream);
			if (inputStream == null) {
				throw new FileNotFoundException("Archivo properties '" + FICHERO_PROPERTIES +"' no se encuentra en el classpath");
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

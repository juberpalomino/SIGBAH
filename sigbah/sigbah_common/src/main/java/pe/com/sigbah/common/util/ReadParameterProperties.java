package pe.com.sigbah.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Resumen.
 * Objeto : ReadParameterProperties
 * Descripción : Clase para leer los parametros del proyecto desde archivo properties.
 * Fecha de Creación : 10/11/2015.
 * Autor : Hernando Huamán.
 */
public final class ReadParameterProperties {
	
	private static Log log = LogFactory.getLog(ReadParameterProperties.class);
	private static final String FICHERO_PROPERTIES = "config.properties";
	
	/**
	 * Método que lee una propiedad y retorna la ruta de la web principal.
	 * @return El valor de la propiedad correspondiente a la llave.
	 */
	public final static String getRutaWebPrincipal() {
		Properties properties = new Properties();
		String directorio = null;
		try {
			InputStream inputStream = ReadParameterProperties.class.getClassLoader().getResourceAsStream(FICHERO_PROPERTIES);
			properties.load(inputStream);
			if (inputStream == null) {
				throw new FileNotFoundException("Archivo properties '" + FICHERO_PROPERTIES +"' no se encuentra en el classpath");
			}			
			directorio = properties.getProperty("url.diana");
		} catch (IOException ex){
			log.error(Constantes.DIVISOR_ERROR_4 + ex);
		}
		return directorio;
	}
	
	/**
	 * Método que lee una propiedad y retorna la ruta de la web principal.
	 * @param key - Llave de codigo de valor;
	 * @return El valor de la propiedad correspondiente a la llave.
	 */
	public final static String getPropiedad(String key) {
		Properties properties = new Properties();
		String directorio = null;
		try {
			InputStream inputStream = ReadParameterProperties.class.getClassLoader().getResourceAsStream(FICHERO_PROPERTIES);
			properties.load(inputStream);
			if (inputStream == null) {
				throw new FileNotFoundException("Archivo properties '" + FICHERO_PROPERTIES +"' no se encuentra en el classpath");
			}
			directorio = properties.getProperty(key);
		} catch (IOException ex){
			log.error(Constantes.DIVISOR_ERROR_4 + ex);
		}
		return directorio;
	}
	
}

package pe.com.sigbah.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Service;

import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;

/**
 * Resumen.
 * Objeto : GenericServiceImpl.
 * Descripción : Clase de implementación de servicios genéricos
 * Fecha de creación : 19/07/2014 15:00:00.
 * @author Hernando Huaman
 */

@Service("iGenericService")
public class GenericServiceImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	/**O
	 * Devuelve el error formateado con el nombre del método.
	 * @param nombre - Nombre del método donde se genero el error, tipo String
	 * @param nivel - Nivel donde se genero el error, tipo String
	 * @param nombreClase - Nombre de la clase, tipo String
	 * @param mensaje - Mensaje del error, tipo String
	 * @return Mensaje formateado.
	 */
	public String getGenerarError(String nombre, String nivel, String nombreClase, String mensaje) {
		StringBuffer error = new StringBuffer();
		if(nivel.equals(Constantes.NIVEL_APP_CONSTROLLER)) {
			error.append(Constantes.DIVISOR_ERROR_4);
		}
		error.append(Constantes.DIVISOR_ERROR_1);
		error.append(nombreClase);
		error.append(" - ");
		error.append(nombre);
		error.append(Constantes.DIVISOR_ERROR_2);
		if(nivel.equals(Constantes.NIVEL_APP_DAO)) {
			error.append(Constantes.DIVISOR_ERROR_3);
		}
		error.append(mensaje);
		return error.toString();
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

}

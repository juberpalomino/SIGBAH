package pe.com.sigbah.web.controller;

import org.springframework.http.HttpStatus;

/**
 * @className: RestUtil.java
 * @description: 
 * @date: 17 de may. de 2017
 * @author: SUMERIO.
 */
public class RestUtil {
	
	/**
	 * @param status
	 * @return indicador
	 */
	public static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }

}

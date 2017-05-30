package pe.com.sigbah.service;

import java.util.List;

import pe.com.sigbah.common.bean.UbigeoBean;

/**
 * @className: IMaestroService.java
 * @description: 
 * @date: 15 de jun. de 2016
 * @author: SUMERIO.
 */
public interface IMaestroService {
	
	/**
	 * @param ubigeo
	 * @return Lista de ubigeos.
	 * @throws Exception
	 */
	public abstract List<UbigeoBean> listarUbigeo(UbigeoBean ubigeo) throws Exception;

	
}

package pe.com.sigbah.service;

import java.util.List;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DonacionesBean;

/**
 * @className: DonacionService.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_DONACION.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public interface DonacionService {

	/**
	 * @param donacionesBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<DonacionesBean> listarDonaciones(DonacionesBean donacionesBean) throws Exception;
	
	
	/**
	 * @param controlCalidadBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract DonacionesBean actualizarDonaciones(DonacionesBean controlCalidadBean) throws Exception;
	/**
	 * @param donacionesBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract DonacionesBean insertarDonaciones(DonacionesBean donacionesBean) throws Exception;
	
}

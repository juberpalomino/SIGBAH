package pe.com.sigbah.service;

import java.util.List;

import pe.com.sigbah.common.bean.ControlCalidadBean;

/**
 * @className: LogisticaService.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_LOGISTICA.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public interface LogisticaService {
	
	/**
	 * @param controlCalidadBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ControlCalidadBean> listarControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception;

}

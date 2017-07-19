package pe.com.sigbah.dao;

import java.util.List;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.EmergenciaBean;
import pe.com.sigbah.common.bean.ListaRespuestaEmergenciaBean;

/**
 * @className: ProgramacionDao.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_PROGRAMACION.
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
public interface ProgramacionDao {

	
	/**
	 * @param emergenciaBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<EmergenciaBean> listarEmergencia(EmergenciaBean emergenciaBean) throws Exception;
	
	/**
	 * @param idEmergencia
	 * @param codAnio
	 * @return
	 * @throws Exception
	 */
	public abstract ListaRespuestaEmergenciaBean obtenerRegistroEmergencia(Integer idEmergencia, String codAnio) throws Exception;
	
	
}

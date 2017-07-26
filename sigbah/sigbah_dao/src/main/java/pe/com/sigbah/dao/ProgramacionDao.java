package pe.com.sigbah.dao;

import java.util.List;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.EmergenciaBean;
import pe.com.sigbah.common.bean.ListaRespuestaEmergenciaBean;
import pe.com.sigbah.common.bean.RequerimientoBean;
import pe.com.sigbah.common.bean.UbigeoIneiBean;

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

	/**
	 * @param requerimientoBean
	 * @return
	 * @throws Exception 
	 */
	public abstract List<RequerimientoBean> listarRequerimiento(RequerimientoBean requerimientoBean)throws Exception;

	/**
	  * @param codAnio
	 * @return
	 * @throws Exception 
	 */
	public abstract RequerimientoBean obtenerRequerimiento(Integer codigo) throws Exception;
	
	
	/**
	 * @param parametros
	 * @return
	 * @throws Exception
	 */
	public abstract RequerimientoBean obtenerCorrelativoRequerimiento(RequerimientoBean parametros) throws Exception;
	
	/**
	 * @param requerimientoBean
	 * @return
	 * @throws Exception
	 */
	public abstract RequerimientoBean insertarRegistroRequerimiento(RequerimientoBean requerimientoBean) throws Exception;
	
	/**
	 * @param requerimientoBean
	 * @return
	 * @throws Exception
	 */
	public abstract RequerimientoBean actualizarRegistroRequerimiento(RequerimientoBean requerimientoBean) throws Exception;

	/**
	 * @param requerimientoBean
	 * @return
	 * @throws Exception 
	 */
	public abstract List<EmergenciaBean> listarEmergenciasActivas(RequerimientoBean requerimientoBean) throws Exception;

	/**
	 * @param emergenciaBean
	 * @return
	 */
	public abstract EmergenciaBean pasarDistritos(EmergenciaBean emergenciaBean)throws Exception;

	/**
	 * @param ubigeoBean
	 * @return
	 */
	public abstract List<UbigeoIneiBean> listarUbigeoInei(UbigeoIneiBean ubigeoBean)throws Exception;

	/**
	 * @param ubigeoBean
	 * @return
	 */
	public abstract EmergenciaBean pasarDistritosUbigeo(EmergenciaBean emergenciaBean)throws Exception;

}

package pe.com.sigbah.service;

import java.util.List;

import pe.com.sigbah.common.bean.EstadoProgramacionBean;
import pe.com.sigbah.common.bean.ProgramacionAlmacenBean;
import pe.com.sigbah.common.bean.ProgramacionBean;
import pe.com.sigbah.common.bean.RacionOperativaBean;
import pe.com.sigbah.common.bean.RequerimientoBean;

/**
 * @className: ProgramacionRequerimientoService.java
 * @description: 
 * @date: 29/07/2017
 * @author: Junior Huaman Flores.
 */
public interface ProgramacionRequerimientoService {

	/**
	 * @param programacionBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ProgramacionBean> listarProgramacion(ProgramacionBean programacionBean) throws Exception;

	/**
	 * @param programacionBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProgramacionBean obtenerCorrelativoProgramacion(ProgramacionBean programacionBean) throws Exception;

	/**
	 * @param idProgramacion
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProgramacionBean obtenerRegistroProgramacion(Integer idProgramacion) throws Exception;
	
	/**
	 * @param requerimientoBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<RequerimientoBean> listarRequerimiento(RequerimientoBean requerimientoBean) throws Exception;
	
	/**
	 * @param racionOperativaBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<RacionOperativaBean> listarRacionOperativa(RacionOperativaBean racionOperativaBean) throws Exception;

	/**
	 * @param estadoProgramacionBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract EstadoProgramacionBean grabarEstadoProgramacion(EstadoProgramacionBean estadoProgramacionBean) throws Exception;

	/**
	 * @param estadoProgramacionBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<EstadoProgramacionBean> listarEstadoProgramacion(EstadoProgramacionBean estadoProgramacionBean) throws Exception;

	/**
	 * @param programacionAlmacenBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ProgramacionAlmacenBean> listarProgramacionAlmacen(ProgramacionAlmacenBean programacionAlmacenBean) throws Exception;

}

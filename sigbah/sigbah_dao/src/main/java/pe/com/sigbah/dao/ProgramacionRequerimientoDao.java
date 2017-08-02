package pe.com.sigbah.dao;

import java.util.List;

import pe.com.sigbah.common.bean.DetalleProgramacionAlimentoBean;
import pe.com.sigbah.common.bean.EstadoProgramacionBean;
import pe.com.sigbah.common.bean.ProductoAlimentoBean;
import pe.com.sigbah.common.bean.ProgramacionAlimentoBean;
import pe.com.sigbah.common.bean.ProgramacionAlmacenBean;
import pe.com.sigbah.common.bean.ProgramacionBean;
import pe.com.sigbah.common.bean.RacionOperativaBean;
import pe.com.sigbah.common.bean.RequerimientoBean;

/**
 * @className: ProgramacionRequerimientoDao.java
 * @description: 
 * @date: 29/07/2017
 * @author: Junior Huaman Flores.
 */
public interface ProgramacionRequerimientoDao {
	
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
	 * @param programacionBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProgramacionBean grabarProgramacion(ProgramacionBean programacionBean) throws Exception;
	
	/**
	 * @param programacionAlmacenBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ProgramacionAlmacenBean> listarProgramacionAlmacen(ProgramacionAlmacenBean programacionAlmacenBean) throws Exception;
	
	/**
	 * @param programacionAlmacenBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProgramacionAlmacenBean grabarProgramacionAlmacen(ProgramacionAlmacenBean programacionAlmacenBean) throws Exception;

	/**
	 * @param programacionAlmacenBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProgramacionAlmacenBean eliminarProgramacionAlmacen(ProgramacionAlmacenBean programacionAlmacenBean) throws Exception;
	
	/**
	 * @param idRacionOperativa
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<RacionOperativaBean> listarProgramacionRacionOperativa(Integer idRacionOperativa) throws Exception;
	
	/**
	 * @param racionOperativaBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract RacionOperativaBean actualizarProgramacionRacionOperativa(RacionOperativaBean racionOperativaBean) throws Exception;
	
	/**
	 * @param idProgramacion
	 * @param idRacionOperativa 
	 * @param arrIdProducto 
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract DetalleProgramacionAlimentoBean obtenerDetalleProgramacionAlimento(Integer idProgramacion, Integer idRacionOperativa, List<Integer> arrIdProducto) throws Exception;
	
	/**
	 * @param productoAlimentoBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProductoAlimentoBean actualizarDetalleProgramacionAlimento(ProductoAlimentoBean productoAlimentoBean) throws Exception;
	
	/**
	 * @param programacionAlimentoBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProgramacionAlimentoBean eliminarDetalleProgramacionAlimento(ProgramacionAlimentoBean programacionAlimentoBean) throws Exception;
	
	/**
	 * @param programacionBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProgramacionBean eliminarProgramacionAlimento(ProgramacionBean programacionBean) throws Exception;

}

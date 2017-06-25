package pe.com.sigbah.dao;

import java.util.List;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.OrdenCompraBean;

/**
 * @className: LogisticaDao.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_LOGISTICA.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public interface LogisticaDao {
	
	/**
	 * @param controlCalidadBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ControlCalidadBean> listarControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception;
	
	/**
	 * @param controlCalidadBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ControlCalidadBean obtenerCorrelativo(ControlCalidadBean controlCalidadBean) throws Exception;
	
	/**
	 * @param controlCalidadBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ControlCalidadBean> listarAlmacenActivo(ControlCalidadBean controlCalidadBean) throws Exception;

	/**
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<OrdenCompraBean> listarOrdenCompra() throws Exception;
	
	/**
	 * @param controlCalidadBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract ControlCalidadBean insertarRegistroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception;

	/**
	 * @param controlCalidadBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract ControlCalidadBean actualizarRegistroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception;
	
	/**
	 * @param idControlCalidad
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ControlCalidadBean obtenerRegistroControlCalidad(Integer idControlCalidad) throws Exception;
	
}

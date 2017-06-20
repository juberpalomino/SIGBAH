package pe.com.sigbah.dao.gestion_almacenes;

import java.util.List;

import pe.com.sigbah.common.bean.ItemBean;

/**
 * @className: ControlCalidadDao.java
 * @description: 
 * @date: 19 de jun. de 2017
 * @author: SUMERIO.
 */
public interface ControlCalidadDao {
	
	/**
	 * @return Lista de años.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarAnios() throws Exception;

}

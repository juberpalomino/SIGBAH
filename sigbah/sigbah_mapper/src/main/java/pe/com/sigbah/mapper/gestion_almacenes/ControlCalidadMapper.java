package pe.com.sigbah.mapper.gestion_almacenes;

import java.util.List;

import pe.com.sigbah.common.bean.ItemBean;

/**
 * @className: MaestroMapper.java
 * @description: 
 * @date: 9 de may. de 2017
 * @author: SUMERIO.
 */
public interface ControlCalidadMapper {
	
	/**
	 * @param item
	 * @return Lista de años.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarAnios(ItemBean item) throws Exception;

}

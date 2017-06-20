package pe.com.sigbah.service.gestion_almacenes;

import java.util.List;

import pe.com.sigbah.common.bean.ItemBean;

/**
 * @className: ControlCalidadService.java
 * @description: 
 * @date: 19 de jun. de 2017
 * @author: SUMERIO.
 */
public interface ControlCalidadService {
	
	/**
	 * @return Lista de años.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarAnios() throws Exception;

}

package pe.com.sigbah.mapper;

import java.util.List;

import pe.com.sigbah.common.bean.UbigeoBean;

/**
 * @className: MaestroMapper.java
 * @description: 
 * @date: 9 de may. de 2017
 * @author: SUMERIO.
 */
public interface MaestroMapper {
	
	/**
	 * @param ubigeo 
	 * @return Lista de ubigeos.
	 * @throws Exception
	 */
	public abstract List<UbigeoBean> listarUbigeo(UbigeoBean ubigeo) throws Exception;

}

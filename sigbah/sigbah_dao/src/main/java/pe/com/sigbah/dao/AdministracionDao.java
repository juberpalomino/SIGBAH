package pe.com.sigbah.dao;

import pe.com.sigbah.common.bean.DetalleUsuarioBean;
import pe.com.sigbah.common.bean.UsuarioBean;

/**
 * @className: AdministracionDao.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_ADMINISTRACION.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public interface AdministracionDao {
	
	/**
	 * @param usuarioBean
	 * @return datos del usuario.
	 * @throws Exception
	 */
	public abstract DetalleUsuarioBean obtenerDatosUsuario(UsuarioBean usuarioBean) throws Exception;

}

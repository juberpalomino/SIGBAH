package pe.com.sigbah.service;

import java.util.List;

import pe.com.sigbah.common.bean.AlmacenBean;
import pe.com.sigbah.common.bean.DetalleUsuarioBean;
import pe.com.sigbah.common.bean.EstadoUsuarioBean;
import pe.com.sigbah.common.bean.ModuloBean;
import pe.com.sigbah.common.bean.UsuarioBean;

/**
 * @className: AdministracionService.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_ADMINISTRACION.
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
public interface AdministracionService {
	
	/**
	 * @param usuarioBean
	 * @return Datos del usuario.
	 * @throws Exception
	 */
	public abstract DetalleUsuarioBean obtenerDatosUsuario(UsuarioBean usuarioBean) throws Exception;
	
	/**
	 * @param idUsuario
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ModuloBean> listarModuloUsuario(Integer idUsuario) throws Exception;
	
	/**
	 * @param idUsuario
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<AlmacenBean> listarAlmacenUsuario(Integer idUsuario) throws Exception;
	
	/**
	 * @param estadoUsuarioBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<EstadoUsuarioBean> listarEstadoUsuario(EstadoUsuarioBean estadoUsuarioBean) throws Exception;

}

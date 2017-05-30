//package pe.com.sigbah.service;
//
//import java.util.List;
//
//import pe.com.sigbah.model.bean.sernanp.ModuloBean;
//import pe.com.sigbah.model.bean.sernanp.PerfilBean;
//import pe.com.sigbah.model.bean.sernanp.UsuarioBean;
//
///**
// * @className: IUsuarioService.java
// * @description: 
// * @date: 17 de jun. de 2016
// * @author: SUMERIO.
// */
//public interface IUsuarioService {
//	
//	/**
//	 * @param clave
//	 * @return
//	 * @throws Exception
//	 */
//	public abstract UsuarioBean obtenerDatosUsuario(String clave) throws Exception;
//	
//	/**
//	 * @param perfil
//	 * @return
//	 * @throws Exception 
//	 */
//	public abstract List<PerfilBean> listarPerfiles(PerfilBean perfil) throws Exception;
//	
//	/**
//	 * @param perfil
//	 * @return
//	 * @throws Exception 
//	 */
//	public abstract void grabarPerfil(PerfilBean perfil) throws Exception;
//
//	/**
//	 * @param usuario
//	 * @return
//	 * @throws Exception 
//	 */
//	public abstract List<UsuarioBean> listarUsuarios(UsuarioBean usuario) throws Exception;
//	
//	/**
//	 * @param usuario
//	 * @throws Exception 
//	 */
//	public abstract void grabarUsuario(UsuarioBean usuario) throws Exception;
//	
//	/**
//	 * @param modulo
//	 * @return
//	 * @throws Exception 
//	 */
//	public abstract List<ModuloBean> listarModulo(ModuloBean modulo) throws Exception;
//
//}

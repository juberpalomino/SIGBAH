//package pe.com.sigbah.web.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.servlet.ModelAndView;
//
//import pe.com.sigbah.common.bean.ModuloBean;
//import pe.com.sigbah.common.bean.PerfilBean;
//import pe.com.sigbah.common.bean.UsuarioBean;
//import pe.com.sigbah.common.util.Constantes;
//
///**
// * @className: SeguridadController.java
// * @description: 
// * @date: 13 de jul. de 2016
// * @author: SUMERIO.
// */
//@Controller
//public class SeguridadController extends BaseController {
//
//	private static final long serialVersionUID = 1L;
//	
//	@Autowired 
//	private IUsuarioService iUsuarioService;
//	
//	/**
//	 * Metodo que retorna a la Generacion de Usuarios.
//	 * @return - Retorna a la vista JSP.
//	 */
//	@RequestMapping("/usuarios")
//    public ModelAndView usuarios() {
//		ModelAndView model = new ModelAndView("mnt_usuarios");
//		try {
//			List<PerfilBean> lis_perfil = new ArrayList<PerfilBean>();		
//			lis_perfil = iUsuarioService.listarPerfiles(new PerfilBean(Constantes.CODIGO_ROL_ACTIVO));			
//			model.addObject("lis_perfil", lis_perfil);	
//		} catch (Exception e) {
//			log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//									  Constantes.NIVEL_APP_CONSTROLLER, 
//									  this.getClass().getName(), e.getMessage()));
//		}		
//		return model;
//    }
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/listarUsuarios", method = RequestMethod.GET, produces = "application/json")
//	@ResponseBody
//	public List<UsuarioBean> listarUsuarios(HttpServletRequest request, HttpServletResponse response) {
//		List<UsuarioBean> lista = null;
//		try {			
//			UsuarioBean usuario = new UsuarioBean();			
//			// Copia los parametros del cliente al objeto
//			BeanUtils.populate(usuario, request.getParameterMap());			
//			lista = iUsuarioService.listarUsuarios(usuario);
//		} catch (Exception e) {
//			log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//									  Constantes.NIVEL_APP_CONSTROLLER, 
//									  this.getClass().getName(), e.getMessage()));
//		}
//		return lista;
//	}
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/grabarUsuario", method = RequestMethod.POST)
//	@ResponseBody
//	public String grabarUsuario(HttpServletRequest request, HttpServletResponse response) {
//		String estado = null;
//		try {			
//			UsuarioBean usuario = new UsuarioBean();			
//			// Copia los parametros del cliente al objeto
//			BeanUtils.populate(usuario, request.getParameterMap());	
//			iUsuarioService.grabarUsuario(usuario);
//			estado = Constantes.ACCION_CORRECTA_JSON;
//		} catch (Exception e) {
//			log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//									  Constantes.NIVEL_APP_CONSTROLLER, 
//									  this.getClass().getName(), e.getMessage()));
//		}
//		return estado;
//	}
//	
//	/**
//	 * Metodo que retorna a la Generación de Perfiles y Usuarios.
//	 * @return - Retorna a la vista JSP.
//	 */
//	@RequestMapping("/perfiles")
//    public ModelAndView perfiles() {
//		ModelAndView model = new ModelAndView("mnt_perfiles");
//		try {
//			// Retorno los datos de session
//			usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
//			
//			ModuloBean pri_modulo = new ModuloBean();
//			pri_modulo.setCod_rol(usuarioBean.getCod_rol());
//			pri_modulo.setNiv_modulo(Constantes.TWO_INT);
//			List<ModuloBean> lis_pri_modulo = iUsuarioService.listarModulo(pri_modulo);
//			model.addObject("lis_pri_modulo", lis_pri_modulo);
//			
//			ModuloBean seg_modulo = new ModuloBean();
//			seg_modulo.setCod_rol(usuarioBean.getCod_rol());
//			seg_modulo.setNiv_modulo(Constantes.THREE_INT);
//			List<ModuloBean> lis_seg_modulo = iUsuarioService.listarModulo(seg_modulo);
//			model.addObject("lis_seg_modulo", lis_seg_modulo);
//		} catch (Exception e) {
//			log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//									  Constantes.NIVEL_APP_CONSTROLLER, 
//									  this.getClass().getName(), e.getMessage()));
//		}		
//		return model;
//    }
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/listarPerfiles", method = RequestMethod.GET, produces = "application/json")
//	@ResponseBody
//	public List<PerfilBean> listarPerfiles(HttpServletRequest request, HttpServletResponse response) {
//		List<PerfilBean> lista = null;
//		try {			
//			PerfilBean perfil = new PerfilBean();	
//			// Copia los parametros del cliente al objeto
//			BeanUtils.populate(perfil, request.getParameterMap());			
//			lista = iUsuarioService.listarPerfiles(perfil);
//		} catch (Exception e) {
//			log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//									  Constantes.NIVEL_APP_CONSTROLLER, 
//									  this.getClass().getName(), e.getMessage()));
//		}
//		return lista;
//	}
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/grabarPerfil", method = RequestMethod.POST)
//	@ResponseBody
//	public String grabarPerfil(HttpServletRequest request, HttpServletResponse response) {
//		String estado = null;
//		try {			
//			PerfilBean perfil = new PerfilBean();		
//			// Copia los parametros del cliente al objeto
//			BeanUtils.populate(perfil, request.getParameterMap());
//			// Retorno los datos de session
//			usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
//			perfil.setCod_rol_act(usuarioBean.getCod_rol());
//			iUsuarioService.grabarPerfil(perfil);
//			estado = Constantes.ACCION_CORRECTA_JSON;
//		} catch (Exception e) {
//			log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//									  Constantes.NIVEL_APP_CONSTROLLER, 
//									  this.getClass().getName(), e.getMessage()));
//		}
//		return estado;
//	}
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/listarModulo", method = RequestMethod.GET, produces = "application/json")
//	@ResponseBody
//	public List<ModuloBean> listarModulo(HttpServletRequest request, HttpServletResponse response) {
//		List<ModuloBean> lista = null;
//		try {			
//			ModuloBean modulo = new ModuloBean();	
//			// Copia los parametros del cliente al objeto
//			BeanUtils.populate(modulo, request.getParameterMap());			
//			lista = iUsuarioService.listarModulo(modulo);
//		} catch (Exception e) {
//			log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//									  Constantes.NIVEL_APP_CONSTROLLER, 
//									  this.getClass().getName(), e.getMessage()));
//		}
//		return lista;
//	}
//
//}

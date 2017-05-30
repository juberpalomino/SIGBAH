package pe.com.sigbah.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.ReadParameterProperties;

/**
 * @className: LoginController.java
 * @description: Clase para manejar el login.
 * @date: 10/05/2015 22:36:29
 * @author: SUMERIO
 */
@Controller
public class LoginController extends BaseController {

	private static final long serialVersionUID = 1L;

//	@Autowired 
//	private IUsuarioService iUsuarioService;	 

	/**
	 * @param token - Clave dinamica a procesar. 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping("/pass/{token}")
	public String principal(@PathVariable("token") String token) {
		String destino = "principal";
		try {		
//			UsuarioBean usuarioBean = new UsuarioBean();
//			usuarioBean = iUsuarioService.obtenerDatosUsuario(token);
//			ModuloBean pri_modulo = new ModuloBean();
//			pri_modulo.setCod_rol(usuarioBean.getCod_rol());
//			pri_modulo.setNiv_modulo(Constantes.TWO_INT);
//			pri_modulo.setFla_rol_mod(Constantes.ESTADO_ACTIVO);
//			List<ModuloBean> lis_pri_modulo = iUsuarioService.listarModulo(pri_modulo);
//			ModuloBean seg_modulo = new ModuloBean();
//			seg_modulo.setCod_rol(usuarioBean.getCod_rol());
//			seg_modulo.setNiv_modulo(Constantes.THREE_INT);
//			seg_modulo.setFla_rol_mod(Constantes.ESTADO_ACTIVO);
//			List<ModuloBean> lis_seg_modulo = iUsuarioService.listarModulo(seg_modulo);
//			context().setAttribute("usuarioBean", usuarioBean, RequestAttributes.SCOPE_SESSION);
//			context().setAttribute("lis_pri_modulo", lis_pri_modulo, RequestAttributes.SCOPE_SESSION);
//			context().setAttribute("lis_seg_modulo", lis_seg_modulo, RequestAttributes.SCOPE_SESSION);
		} catch (Exception e) {
			log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
									  Constantes.NIVEL_APP_CONSTROLLER,
									  this.getClass().getName(), e.getMessage()));
		}
		return destino;
	}

	/**
	 * @return - Retorna al login maestro.
	 */
	@RequestMapping(value = "/login/close", method = RequestMethod.GET)
    public ModelAndView getCerrarSession() {
		context().removeAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
		context().removeAttribute("lis_pri_modulo", RequestAttributes.SCOPE_SESSION);
		context().removeAttribute("lis_seg_modulo", RequestAttributes.SCOPE_SESSION);
        return new ModelAndView("redirect:" + ReadParameterProperties.getRutaWebPrincipal());
    }

}

package pe.com.sigbah.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import pe.com.sigbah.common.util.ReadParameterProperties;

/**
 * @className: MainController.java
 * @description: Clase principal de sistema de tesoreria.
 * @date: 28/04/2015 21:45:13
 * @author: SUMERIO
 */
@Controller
@RequestMapping("/main")
public class MainController extends BaseController {

	private static final long serialVersionUID = 1L;

	/**
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getHome() {
		
		/*
		// BORRAR CODIGO EN DESARROLLO y QA
		ModelAndView model = new ModelAndView("login");
		UsuarioBean usuarioBean = new UsuarioBean();
		usuarioBean.setCod_usuario(1405);
		usuarioBean.setCod_rol(getInteger(ReadParameterProperties.getPropiedad("perfil.adm.ing.gastos"))); // Admin Ingresos y Gastos
		usuarioBean.setDes_usuario("ingresosgastos");
		usuarioBean.setDes_area("Oficina de Administración");
		context().setAttribute("usuarioBean", usuarioBean, RequestAttributes.SCOPE_SESSION);
		return model;		
		*/
	
		context().removeAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
		context().removeAttribute("lis_modulo", RequestAttributes.SCOPE_SESSION);
        return new ModelAndView("redirect:" + ReadParameterProperties.getRutaWebPrincipal());
	}	
	
	/**
	 * @param name - Nombre de la pagina asociado.
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping("/page/{name}")
    public ModelAndView getPage(@PathVariable("name") String name) {
		ModelAndView model = new ModelAndView(name);	
		return model;
    }
	
}

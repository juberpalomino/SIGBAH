package pe.com.sigbah.web.controller.seguridad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;

import pe.com.sigbah.common.bean.DetalleUsuarioBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.service.AdministracionService;
import pe.com.sigbah.web.controller.common.BaseController;

/**
 * @className: LoginController.java
 * @description: Clase para manejar el login.
 * @date: 10/05/2015 22:36:29
 * @author: SUMERIO
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	private static final long serialVersionUID = 1L;

	@Autowired 
	private AdministracionService administracionService;

	/**
	 * @param request
	 * @param model
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(method = RequestMethod.GET)
    public String doShowForm(HttpServletRequest request, Model model) {
        UsuarioBean usuario = new UsuarioBean();
        model.addAttribute("usuario", usuario);
        return "login";
    }
    
    /**
     * @param usuario
     * @param result
     * @param request
     * @param response
     * @param model 
	 * @return - Retorna a la vista JSP.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String doProcessForm(@ModelAttribute("usuario") UsuarioBean usuario, BindingResult result, 
    							HttpServletRequest request, HttpServletResponse response, Model model) {
    	String destino = "login";
        boolean isAccessOk = true;
        
        if (usuario == null) {
            destino = "login";
            isAccessOk = false;
        }

        if (!result.hasErrors() && isAccessOk) {
            isAccessOk = false;
            
            try {
            	DetalleUsuarioBean detalleUsuarioBean = administracionService.obtenerDatosUsuario(usuario);
            	
            	if (detalleUsuarioBean != null && !isEmpty(detalleUsuarioBean.getDatosUsuario())) {
            		usuario = detalleUsuarioBean.getDatosUsuario().get(0);
            	}
               
            } catch (Exception e) {
            	LOGGER.error(e.getMessage(), e);
            	model.addAttribute("base", getBaseRespuesta(null));
                usuario = null;
            }    
            
            if (usuario != null) {
                context().setAttribute("usuarioBean", usuario, RequestAttributes.SCOPE_SESSION);
                destino = "forward:/principal/inicio";
            }
        }
        return destino;
    }

}

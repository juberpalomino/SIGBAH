package pe.com.sigbah.web.controller.seguridad;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;

import pe.com.sigbah.common.bean.BaseOutputBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.web.controller.common.BaseController;

/**
 * @className: PrincipalController.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Controller
@RequestMapping("/principal")
public class PrincipalController extends BaseController {

	private static final long serialVersionUID = 1L;
	
//	@Autowired 
//	private AdministracionService administracionService;	

	
	/**
	 * @param model 
	 * @param request 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio", method = {RequestMethod.GET, RequestMethod.POST})
    public String inicio(Model model, HttpServletRequest request) {
        try {

        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
            
        	LOGGER.info("[inicio] "+usuarioBean);

        	model.addAttribute("base", new BaseOutputBean(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "principal";
    }
	
	/**
	 * @param model 
	 * @param request 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/resourceNotFound", method = RequestMethod.GET)
    public String resourceNotFound(Model model, HttpServletRequest request) {
        return "resourceNotFound";
    }
	
	/**
	 * @param model 
	 * @param request 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/uncaughtException", method = RequestMethod.GET)
    public String uncaughtException(Model model, HttpServletRequest request) {
        return "uncaughtException";
    }

}

package pe.com.sigbah.web.controller.seguridad;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pe.com.sigbah.common.bean.BaseOutputBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.web.controller.common.BaseController;

/**
 * @className: PrincipalController.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
@Controller
@RequestMapping("/principal")
public class PrincipalController extends BaseController {

	private static final long serialVersionUID = 1L;
	
//	@Autowired 
//	private AdministracionService administracionService;	
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * @param model 
	 * @param request 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio", method = {RequestMethod.GET, RequestMethod.POST})
    public String inicio(Model model, HttpServletRequest request) {
        try {

        	// Retorno los datos de session
//            UsuarioBean usuario = Utilidades.getInstancia().loadUserFromSession(request);

        	model.addAttribute("base", new BaseOutputBean(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	baseOutputBean = new BaseOutputBean();
			baseOutputBean.setCodigoRespuesta(Constantes.COD_ERROR_GENERAL);
			baseOutputBean.setMensajeRespuesta(getMensaje(messageSource, "msg.error.errorOperacion"));
        	model.addAttribute("base", baseOutputBean);
        }
        return "principal";
    }

}

package pe.com.sigbah.web.controller.donaciones;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.web.controller.common.BaseController;

/**
 * @className: DonacionesController.java
 * @description: 
 * @date: 22/06/2017
 * @author: PC.
 */
@Controller
@RequestMapping("/donaciones/registro-donaciones")
public class DonacionesController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	/**
	 * @param model 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String inicio(Model model) {
        try {
        	// Retorno los datos de session
//        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
//
//        	model.addAttribute("lista_anio", generalService.listarAnios());
//        	model.addAttribute("lista_ddi", generalService.listarDdi(new ItemBean(usuarioBean.getIdDdi())));
//        	model.addAttribute("lista_almacen", generalService.listarAnios());
        
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar-donaciones";
    }

}

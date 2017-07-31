package pe.com.sigbah.web.controller.programacion_bah;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.com.sigbah.common.bean.DeeBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.ProgramacionService;
import pe.com.sigbah.web.controller.common.BaseController;

/**
 * @className: EmergenciaController.java
 * @description: 
 * @date: 13 jul. 2017
 * @author: whr.
 */

@Controller
@RequestMapping("/programacion-bath/decreto")
public class DEEController extends BaseController {
	
private static final long serialVersionUID = 1L;
	
	@Autowired 
	private ProgramacionService programacionService;
	
	@Autowired 
	private GeneralService generalService;
	
	@RequestMapping(value = "/inicio/{indicador}", method = RequestMethod.GET)
    public String inicio(@PathVariable("indicador") String indicador, Model model) {
        try {
        	// Retorno los datos de session
//        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
//        	
        	model.addAttribute("lista_anio", generalService.listarAnios());
        	
        	model.addAttribute("lista_mes", generalService.listarMeses(new ItemBean()));
    	
        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "decreto-estado";
    }

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listarDee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarDee(HttpServletRequest request, HttpServletResponse response) {
		List<DeeBean> lista = null;
		try {			
			DeeBean deeBean = new DeeBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(deeBean, request.getParameterMap());			
			
//			usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
			
			lista = programacionService.listarDee(deeBean); 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
}

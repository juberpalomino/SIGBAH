package pe.com.sigbah.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pe.com.sigbah.common.util.Constantes;

/**
 * @className: ProductoController.java
 * @description: 
 * @date: 23 de jun. de 2017
 * @author: Administrador.
 */
@Controller
@RequestMapping("/producto")
public class ProductoController extends BaseController {

	private static final long serialVersionUID = 1L;
	
//	@Autowired 
//	private IMaestroService iMaestroService;
	
	/**
	 * @param name - Nombre de la pagina asociado.
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio", method = RequestMethod.POST)
    public String goInicio(HttpServletRequest request, Model model) {
        try {
        	
            
        } catch (Exception e) {
        	log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
					  Constantes.NIVEL_APP_CONSTROLLER, 
					  this.getClass().getName(), e.getMessage()));
        }
        return "opc_mnt_producto";
    }
	
}

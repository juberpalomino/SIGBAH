package pe.com.sigbah.web.controller.seguridad;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.web.controller.common.Utilidades;
import pe.com.sigbah.web.filter.ServletUtility;

/**
 * @className: LogoutController.java
 * @description: 
 * @date: 22 de jun. de 2017
 * @author: SUMERIO.
 */
@Controller
@RequestMapping("/logout")
public class LogoutController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param model 
	 * @param request 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio", method = {RequestMethod.GET, RequestMethod.POST})
    public String inicio(Model model, HttpServletRequest request) {
		UsuarioBean usuario = Utilidades.getInstancia().loadUserFromSession(request);
        String destino = "login";
        // esto es para limpiar la session
        if (usuario != null) {
           usuario.setPassword(null);
           usuario.setUsuario(null);
        }      
        ServletUtility.getInstancia().invalidateSession(request);
        return destino;
    }

}

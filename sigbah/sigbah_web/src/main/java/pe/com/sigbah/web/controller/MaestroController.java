package pe.com.sigbah.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.com.sigbah.common.bean.BaseOutputBean;
import pe.com.sigbah.common.bean.UbigeoBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.service.IMaestroService;

/**
 * @className: MaestroController.java
 * @description: 
 * @date: 23 de jun. de 2017
 * @author: Administrador.
 */
@Controller
@RequestMapping("/maestro")
public class MaestroController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Autowired 
	private IMaestroService iMaestroService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * @param request 
	 * @param model 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String goInicio(HttpServletRequest request, Model model) {
        try {
//        	model.addAttribute("lis_maestro", iMaestroService.listarUbigeo(new UbigeoBean()));
            
        } catch (Exception e) {
        	LOGGER.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
					  Constantes.NIVEL_APP_CONSTROLLER, 
					  this.getClass().getName(), e.getMessage()));
        }
        return "maestro";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto
	 */
	@RequestMapping(value = "/listarMaestros", method = RequestMethod.GET)
	@ResponseBody
	public Object listarMaestros(HttpServletRequest request, HttpServletResponse response) {
		List<UbigeoBean> lista = null;
		try {			
			UbigeoBean ubigeo = new UbigeoBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(ubigeo, request.getParameterMap());
			
			if (ubigeo.getCoddpto() == null) {
				
			}
			
			lista = iMaestroService.listarUbigeo(ubigeo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			baseOutputBean = new BaseOutputBean();
			baseOutputBean.setCodigoRespuesta(Constantes.COD_ERROR_GENERAL);
			baseOutputBean.setMensajeRespuesta(getMensaje(messageSource, "msg.error.errorOperacion"));
			return baseOutputBean;
		}
		return lista;
	}
	
}

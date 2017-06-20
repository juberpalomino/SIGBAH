package pe.com.sigbah.web.controller.gestion_almacenes;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.com.sigbah.common.bean.BaseOutputBean;
import pe.com.sigbah.common.bean.UbigeoBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.service.IMaestroService;
import pe.com.sigbah.service.gestion_almacenes.ControlCalidadService;
import pe.com.sigbah.web.controller.BaseController;

/**
 * @className: ControlCalidadController.java
 * @description: 
 * @date: 17 de jun. de 2017
 * @author: SUMERIO.
 */
@Controller
@RequestMapping("/gestion-almacenes/control-calidad")
public class ControlCalidadController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Autowired 
	private IMaestroService iMaestroService;
	
	@Autowired 
	private ControlCalidadService controlCalidadService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * @param model 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String inicio(Model model) {
        try {
//        	model.addAttribute("lis_maestro", iMaestroService.listarUbigeo(new UbigeoBean()));
        	
//        	System.out.println(getPropiedad("url.diana"));
        	
        	model.addAttribute("lista_anio", controlCalidadService.listarAnios());

        	model.addAttribute("base", new BaseOutputBean(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	baseOutputBean = new BaseOutputBean();
			baseOutputBean.setCodigoRespuesta(Constantes.COD_ERROR_GENERAL);
			baseOutputBean.setMensajeRespuesta(getMensaje(messageSource, "msg.error.errorOperacion"));
        	model.addAttribute("base", baseOutputBean);
        }
        return "listar_control_calidad";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarControlCalidad", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarControlCalidad(HttpServletRequest request, HttpServletResponse response) {
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
	
	/**
	 * @param codigo 
	 * @param model
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/mantenimientoControlCalidad/{codigo}", method = RequestMethod.GET)
    public String mantenimientoControlCalidad(@PathVariable("codigo") Integer codigo, Model model) {
        try {
//        	model.addAttribute("lis_maestro", iMaestroService.listarUbigeo(new UbigeoBean()));
        	
//        	System.out.println(getPropiedad("url.diana"));
        	
        	if (!isNullInteger(codigo)) {
        		
        		System.out.println(codigo);
        		
        	} else {
        		
        		System.out.println("nuevo");
        		
        	}
        	

        	model.addAttribute("base", new BaseOutputBean(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	baseOutputBean = new BaseOutputBean();
			baseOutputBean.setCodigoRespuesta(Constantes.COD_ERROR_GENERAL);
			baseOutputBean.setMensajeRespuesta(getMensaje(messageSource, "msg.error.errorOperacion"));
        	model.addAttribute("base", baseOutputBean);
        }
        return "mantenimiento_control_calidad";
    }
	
}

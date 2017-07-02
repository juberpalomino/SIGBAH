package pe.com.sigbah.web.controller.donaciones;

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
import org.springframework.web.context.request.RequestAttributes;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.DonacionService;
import pe.com.sigbah.web.controller.common.BaseController;

import pe.com.sigbah.common.bean.DonacionesBean;


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
	@Autowired 
	private DonacionService donacionService;
	
	@Autowired 
	private GeneralService generalService;
	/**
	 * @param model 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String inicio(Model model) {
        try {        
        	// Retorno los datos de session
           	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);

        	model.addAttribute("lista_anio", generalService.listarAnios());
        	
        	List<ItemBean> listaDdi = generalService.listarDdi(new ItemBean(usuarioBean.getIdDdi()));
        	model.addAttribute("lista_ddi", listaDdi);
        	
        	model.addAttribute("lista_estado", generalService.listarEstadoDonacion(new ItemBean()));
        	
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar-donaciones";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarDonaciones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarControlCalidad(HttpServletRequest request, HttpServletResponse response) {
		List<DonacionesBean> lista = null;
		try {			
			DonacionesBean donacionesBean = new DonacionesBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(donacionesBean, request.getParameterMap());			
			lista = donacionService.listarDonaciones(donacionesBean);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param codigo 
	 * @param model
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/mantenimientoDonaciones/{codigo}", method = RequestMethod.GET)
    public String mantenimientoDonaciones(@PathVariable("codigo") Integer codigo, Model model) {
        try {
        //	ControlCalidadBean controlCalidad = new ControlCalidadBean();
        	
        	
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_donaciones";
    }
	
	/**
	 * @param model 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/ingreso", method = RequestMethod.GET)
    public String ingreso(Model model) {
        try {        
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);

        	model.addAttribute("lista_anio", generalService.listarAnios());
        	
        	List<ItemBean> listaDdi = generalService.listarDdi(new ItemBean(usuarioBean.getIdDdi()));
        	model.addAttribute("lista_ddi", listaDdi);
        	
        	if (!Utils.isEmpty(listaDdi) && listaDdi.size() == Constantes.ONE_INT) {
        		model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
        	}
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar-donaciones-ingreso";
    }
	
	
	
	
	

}

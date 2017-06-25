package pe.com.sigbah.web.controller.gestion_almacenes;

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
import pe.com.sigbah.service.LogisticaService;
import pe.com.sigbah.web.controller.common.BaseController;

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
	private LogisticaService logisticaService;
	
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
        	
        	String codigoDdi = getString(usuarioBean.getIdDdi());
        	List<ItemBean> listaDdi = generalService.listarDdi(new ItemBean(codigoDdi));
        	model.addAttribute("lista_ddi", listaDdi);
        	
        	if (!Utils.isEmpty(listaDdi) && listaDdi.size() == Constantes.ONE_INT) {
        		model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(codigoDdi)));
        	}
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
//        	baseOutputBean = new BaseOutputBean();
//			baseOutputBean.setCodigoRespuesta(Constantes.COD_ERROR_GENERAL);
//			baseOutputBean.setMensajeRespuesta(getMensaje(messageSource, "msg.error.errorOperacion"));
        	model.addAttribute("base", getBaseRespuesta(null));
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
		List<ControlCalidadBean> lista = null;
		try {			
			ControlCalidadBean controlCalidadBean = new ControlCalidadBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(controlCalidadBean, request.getParameterMap());			
			lista = logisticaService.listarControlCalidad(controlCalidadBean);
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
	@RequestMapping(value = "/mantenimientoControlCalidad/{codigo}", method = RequestMethod.GET)
    public String mantenimientoControlCalidad(@PathVariable("codigo") Integer codigo, Model model) {
        try {
        	ControlCalidadBean controlCalidad = new ControlCalidadBean();
        	
        	if (!isNullInteger(codigo)) {
        		
        		controlCalidad.setIdControlCalidad(codigo);
        		
        		
        		
        		model.addAttribute("lista_chofer", generalService.listarChofer(new ItemBean(usuarioBean.getIdDdi())));
        		
        	} else {
        		
        		// Retorno los datos de session
            	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);

        		StringBuilder correlativo = new StringBuilder();
        		correlativo.append(usuarioBean.getCodigoDdi());
        		correlativo.append(Constantes.SEPARADOR);
        		correlativo.append(usuarioBean.getCodigoAlmacen());
        		correlativo.append(Constantes.SEPARADOR);
        		
        		ControlCalidadBean parametros = new ControlCalidadBean();
        		String anioActual = generalService.obtenerAnioActual();
        		parametros.setCodigoAnio(anioActual);
        		parametros.setCodigoDdi(usuarioBean.getCodigoDdi());
        		parametros.setIdAlmacen(usuarioBean.getIdAlmacen());        		
        		ControlCalidadBean respuestaCorrelativo = logisticaService.obtenerCorrelativo(parametros);
      
        		correlativo.append(respuestaCorrelativo.getNroControlCalidad());
        		
        		controlCalidad.setNroControlCalidad(correlativo.toString());        		
        		controlCalidad.setIdDdi(usuarioBean.getIdDdi());
        		controlCalidad.setNombreDdi(usuarioBean.getNombreDdi());
        		
        		ControlCalidadBean parametroAlmacenActivo = new ControlCalidadBean();
        		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
        		List<ControlCalidadBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
        		if (!isEmpty(listaAlmacenActivo)) {
        			controlCalidad.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
        			controlCalidad.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
        			controlCalidad.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
        			controlCalidad.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
        		}
        		
        		if (!Utils.isNullInteger(usuarioBean.getIdDdi())) {
            		model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
            	}
        		
        	}
        	
        	model.addAttribute("controlCalidad", getParserObject(controlCalidad));

        	model.addAttribute("lista_estado", generalService.listarEstado(new ItemBean(Constantes.FOUR_INT)));
        	
        	model.addAttribute("lista_orden_compra", logisticaService.listarOrdenCompra());
        	
        	model.addAttribute("lista_tipo_control", generalService.listarTipoControlCalidad(new ItemBean()));
        	
        	model.addAttribute("lista_personal", generalService.listarPersonal(new ItemBean(usuarioBean.getIdDdi())));
        	
        	model.addAttribute("lista_proveedor", generalService.listarProveedor(new ItemBean()));
        	
        	ItemBean parametroEmpresaTransporte = new ItemBean();
        	parametroEmpresaTransporte.setIcodigo(usuarioBean.getIdDdi());
        	parametroEmpresaTransporte.setIcodigoParam2(Constantes.ONE_INT);
        	model.addAttribute("lista_empresa_transporte", generalService.listarEmpresaTransporte(parametroEmpresaTransporte));
     
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_control_calidad";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarChofer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarChofer(HttpServletRequest request, HttpServletResponse response) {
		List<ItemBean> lista = null;
		try {			
			ItemBean item = new ItemBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(item, request.getParameterMap());			
			lista = generalService.listarChofer(item);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/grabarControlCalidad", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarControlCalidad(HttpServletRequest request, HttpServletResponse response) {
		ControlCalidadBean controlCalidad = null;
		try {			
			ControlCalidadBean controlCalidadBean = new ControlCalidadBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(controlCalidadBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	controlCalidadBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			if (!isNullInteger(controlCalidadBean.getIdControlCalidad())) {
				
				controlCalidad = logisticaService.actualizarRegistroControlCalidad(controlCalidadBean);
				controlCalidad.setMensajeRespuesta(getMensaje(messageSource, "Los datos se grabaron satisfactoriamente."));
				
			} else {
			
				controlCalidad = logisticaService.insertarRegistroControlCalidad(controlCalidadBean);
			
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return controlCalidad;
	}
	
}

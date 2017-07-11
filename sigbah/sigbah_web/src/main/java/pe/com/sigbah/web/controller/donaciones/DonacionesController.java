package pe.com.sigbah.web.controller.donaciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.IntegerConverter;
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
import pe.com.sigbah.common.bean.DocumentoControlCalidadBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ProductoBean;
import pe.com.sigbah.common.bean.ProductoControlCalidadBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.DonacionService;
import pe.com.sigbah.service.LogisticaService;
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
	
	@Autowired 
	private LogisticaService logisticaService;
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
        	ControlCalidadBean controlCalidad = new ControlCalidadBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	if (!isNullInteger(codigo)) {
        		
        		controlCalidad = logisticaService.obtenerRegistroControlCalidad(codigo);
        		
//        		model.addAttribute("lista_chofer", generalService.listarChofer(new ItemBean(controlCalidad.getIdEmpresaTransporte())));
//        		
//        		List<ProductoControlCalidadBean> listaAlimentarios = new ArrayList<ProductoControlCalidadBean>(); // Cambiar
//
//            	model.addAttribute("listaAlimentarios", getParserObject(listaAlimentarios));
//        		model.addAttribute("listaNoAlimentarios", getParserObject(listaAlimentarios));
        		
        	} else {

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
        		//ControlCalidadBean respuestaCorrelativo = logisticaService.obtenerCorrelativoControlCalidad(parametros);
        	      
        	//	correlativo.append(respuestaCorrelativo.getNroControlCalidad());
        		
        		controlCalidad.setNroControlCalidad(correlativo.toString());        		
        		
        		ControlCalidadBean parametroAlmacenActivo = new ControlCalidadBean();
        		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
        		List<ControlCalidadBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
        		if (!isEmpty(listaAlmacenActivo)) {
        			controlCalidad.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
        			controlCalidad.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
        			controlCalidad.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
        			controlCalidad.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
        			controlCalidad.setCodigoMes(listaAlmacenActivo.get(0).getCodigoMes());
        		}
        		
            	controlCalidad.setIdDdi(usuarioBean.getIdDdi());
        		controlCalidad.setCodigoDdi(usuarioBean.getCodigoDdi());
        		controlCalidad.setNombreDdi(usuarioBean.getNombreDdi());
        	}
        	
        	if (!Utils.isNullInteger(usuarioBean.getIdDdi())) {
        		model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
        	}
        	
        	model.addAttribute("controlCalidad", getParserObject(controlCalidad));

        	model.addAttribute("lista_estado", generalService.listarEstado(new ItemBean(null, Constantes.THREE_INT)));
        	
        	model.addAttribute("lista_orden_compra", logisticaService.listarOrdenCompra());
        	
        	model.addAttribute("lista_tipo_control", generalService.listarTipoControlCalidad(new ItemBean()));
        	
        	model.addAttribute("lista_personal", generalService.listarPersonal(new ItemBean(usuarioBean.getIdDdi())));
        	
        	model.addAttribute("lista_proveedor", generalService.listarProveedor(new ItemBean()));
        	  
        	ItemBean parametroEmpresaTransporte = new ItemBean();
        	parametroEmpresaTransporte.setIcodigo(usuarioBean.getIdDdi());
        	parametroEmpresaTransporte.setIcodigoParam2(Constantes.ONE_INT);
        	model.addAttribute("lista_empresa_transporte", generalService.listarEmpresaTransporte(parametroEmpresaTransporte));
        	
        	model.addAttribute("lista_producto", generalService.listarCatologoProductos(new ProductoBean(null, Constantes.FIVE_INT)));
        	
        	model.addAttribute("lista_tipo_documento", generalService.listarTipoDocumento(new ItemBean()));
     
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_donaciones";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarDocumentoControlCalidad", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarDocumentoControlCalidad(HttpServletRequest request, HttpServletResponse response) {
		List<DocumentoControlCalidadBean> lista = null;
		try {			
			DocumentoControlCalidadBean documento = new DocumentoControlCalidadBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(documento, request.getParameterMap());			
			lista = logisticaService.listarDocumentoControlCalidad(documento);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
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
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarDocumentoDonacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarDocumentoDonacion(HttpServletRequest request, HttpServletResponse response) {
		List<DocumentoControlCalidadBean> lista = null;
		try {			
			DocumentoControlCalidadBean documento = new DocumentoControlCalidadBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(documento, request.getParameterMap());			
			lista = logisticaService.listarDocumentoControlCalidad(documento);
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
	@RequestMapping(value = "/grabarDonaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarDonaciones(HttpServletRequest request, HttpServletResponse response) {
		DonacionesBean controlCalidad = null;
		try {			
			DonacionesBean donacionesBean = new DonacionesBean();
			
			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(donacionesBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	 
        	donacionesBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			if (!isNullInteger(donacionesBean.getIdDonacion())) {				
				controlCalidad = donacionService.actualizarDonaciones(donacionesBean);
				controlCalidad.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));				
			} else {			
				controlCalidad = donacionService.insertarDonaciones(donacionesBean);			
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return controlCalidad;
	}
	
	
	
	

}

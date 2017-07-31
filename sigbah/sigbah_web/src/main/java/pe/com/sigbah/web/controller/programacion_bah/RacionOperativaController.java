package pe.com.sigbah.web.controller.programacion_bah;

import java.util.Calendar;
import java.util.Date;
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

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ListaRespuestaRequerimientoBean;
import pe.com.sigbah.common.bean.ProductoBean;
import pe.com.sigbah.common.bean.ProductoRacionBean;
import pe.com.sigbah.common.bean.RacionBean;
import pe.com.sigbah.common.bean.RequerimientoBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;
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
@RequestMapping("/programacion-bath/racion")
public class RacionOperativaController extends BaseController {
	
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
        	
        	model.addAttribute("lista_racion", generalService.listarRacion(new ItemBean()));
//        	        	
//        	model.addAttribute("lista_tipo_movimiento", generalService.listarTipoMovimiento(new ItemBean(Constantes.TWO_INT, Constantes.TWO_INT)));
//        	
//        	OrdenSalidaBean ordenSalida = new OrdenSalidaBean();
//        	
//        	ControlCalidadBean parametroAlmacenActivo = new ControlCalidadBean();
//    		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
//    		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
//    		List<ControlCalidadBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
//    		if (!isEmpty(listaAlmacenActivo)) {
//    			ordenSalida.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
//    			ordenSalida.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
//    			ordenSalida.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
//    			ordenSalida.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
//    			ordenSalida.setCodigoMes(listaAlmacenActivo.get(0).getCodigoMes());
//    		}
//    		
//    		model.addAttribute("ordenSalida", getParserObject(ordenSalida));
//        	
        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "racion-operativa";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listarRaciones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarRaciones(HttpServletRequest request, HttpServletResponse response) {
		List<RacionBean> lista = null;
		try {			
			RacionBean racionBean = new RacionBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(racionBean, request.getParameterMap());			
			
			usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
			racionBean.setIdDdi(Integer.parseInt(usuarioBean.getCodigoDdi()));
			lista = programacionService.listarRaciones(racionBean); 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	@RequestMapping(value = "/exportarExcel/{codAnio}/{codMesRacion}/{tipoRacion}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("codAnio") String codAnio, 
								@PathVariable("codMesRacion") String codMesRacion, 
								@PathVariable("tipoRacion") String tipoRacion, 
								HttpServletResponse response) {
	    try {
	    	RacionBean racionBean = new RacionBean();
	    	racionBean.setCodAnio(verificaParametro(codAnio));
	    	racionBean.setCodMesRacion(verificaParametro(codMesRacion));
	    	racionBean.setTipoRacion(verificaParametro(tipoRacion));
	    	
			List<RacionBean> lista = programacionService.listarRaciones(racionBean);
	    	
			String file_name = "Reporte_Racion";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			return Constantes.COD_EXITO_GENERAL;   	
	    } catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	return Constantes.COD_ERROR_GENERAL;
	    } 
	}
	
//	@RequestMapping(value = "/mantenimientoRacion/{codigo}/{codigoAnio}", method = RequestMethod.GET)
//    public String mantenimientoEmergencia(@PathVariable("codigo") Integer codigo,
//    									  @PathVariable("codigoAnio") String codigoAnio,
//    									  Model model) {
//        try {
//        	ListaRespuestaEmergenciaBean detalle = new ListaRespuestaEmergenciaBean();
//        	
//        	// Retorno los datos de session
//        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
//        	
//        	detalle = programacionService.obtenerRegistroEmergencia(codigo,  codigoAnio);
//    		model.addAttribute("cabecera", getParserObject(detalle.getLstCabecera().get(0)));
//    		model.addAttribute("lista_localidad", getParserObject(detalle.getLstLocalidad()));
//    		model.addAttribute("lista_alimentaria", getParserObject(detalle.getLstAlimentaria()));
//    		model.addAttribute("lista_no_alimentaria", getParserObject(detalle.getLstNoAlimentaria()));
//    		
//
//            
//        } catch (Exception e) {
//        	LOGGER.error(e.getMessage(), e);
//        	model.addAttribute("base", getBaseRespuesta(null));
//        }
//        return "mantenimiento_emergencias-sinpad";
//    }
	
	
	/**
	 * @param codigo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/copiarRacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    public Object  copiarRacion(HttpServletRequest request, HttpServletResponse response) {
		RacionBean racion = null;
		try {
        	RacionBean racionBean = new RacionBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	 // Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(racionBean, request.getParameterMap());
        
			racionBean.setUsuarioRegistro(usuarioBean.getNombreUsuario());
			racionBean.setIdDdi(usuarioBean.getIdDdi());
        	racion = programacionService.copiarRacion(racionBean);
        	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return racion;
    }
	
	/**
	 * @param codigo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/mantenimientoRacion/{codigo}", method = RequestMethod.GET)
    public String mantenimientoRacion(@PathVariable("codigo") Integer codigo, Model model) {
        try {
        	RacionBean racion = new RacionBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        
        	ListaRespuestaRequerimientoBean respuestaEdicion = new ListaRespuestaRequerimientoBean();
        	if (!isNullInteger(codigo)) {// editar
        		
//        		respuestaEdicion = programacionService.obtenerRequerimiento(usuarioBean.getCodigoAnio(),usuarioBean.getCodigoDdi(),codigo); 
//        		model.addAttribute("requerimiento", getParserObject(respuestaEdicion.getLstCabecera().get(0)));
//        		model.addAttribute("lista_requerimiento", getParserObject(respuestaEdicion.getLstDetalle()));
        	  		
        	} else {//nuevo

        		RacionBean parametros = new RacionBean();
        		String anioActual = generalService.obtenerAnioActual();
        		parametros.setCodAnio(anioActual);
        		parametros.setIdDdi(usuarioBean.getIdDdi());   
        		
        		RacionBean respuestaCorrelativo = programacionService.obtenerCorrelativoRacion(parametros);
        		racion.setCodRacion(respuestaCorrelativo.getCodRacion());   		
        		Date fecha_hora = Calendar.getInstance().getTime();
        		racion.setFechaRacion(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));
        		
    			model.addAttribute("racion", getParserObject(racion));
        	}
	
        	model.addAttribute("lista_racion", generalService.listarRacion(new ItemBean()));
        	model.addAttribute("lista_producto", generalService.listarCatologoProductos(new ProductoBean(null, Constantes.FIVE_INT)));//whr consultar
        	
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_racion";
    }
	
	
	@RequestMapping(value = "/grabarRacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarRacion(HttpServletRequest request, HttpServletResponse response) {
		RacionBean racion = null;
		try {			
			RacionBean racionBean = new RacionBean();

	        // Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(racionBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	String anioActual = generalService.obtenerAnioActual();
        	
        	racionBean.setUsuarioRegistro(usuarioBean.getNombreUsuario());
			racionBean.setIdDdi(usuarioBean.getIdDdi());
			racionBean.setCodAnio(anioActual);
			
			if (!isNullInteger(racionBean.getIdRacionOpe())) {				
				racion = programacionService.actualizarRegistroRacion(racionBean);				
			} else {			
				racion = programacionService.insertarRegistroRacion(racionBean);			
			}
			racion.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return racion;
	}
	
	
	@RequestMapping(value = "/grabarProducto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarProducto(HttpServletRequest request, HttpServletResponse response) {
		ProductoRacionBean producto = null;
		try {			
			ProductoRacionBean productoBean = new ProductoRacionBean();

	        // Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(productoBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	String anioActual = generalService.obtenerAnioActual();
        	
        	productoBean.setUsuarioRegistro(usuarioBean.getNombreUsuario());
        	productoBean.setFkIdProducto(usuarioBean.getIdDdi());
        	productoBean.setCodAnio(anioActual);
        	productoBean.setIdRacion(32);//whr consultar por idrracion, deberia devolver al grabar racion o llamr independientemente el 
        	
           
            
			if (!isNullInteger(productoBean.getIdDetaRacion())) {				
				producto = programacionService.actualizarRegistroProducto(productoBean);				
			} else {			
				producto = programacionService.insertarRegistroProducto(productoBean);			
			} 
			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return producto;
	}
}

package pe.com.sigbah.web.controller.gestion_almacenes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import pe.com.sigbah.common.bean.BaseOutputBean;
import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoProyectoManifiestoBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ManifiestoVehiculoBean;
import pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean;
import pe.com.sigbah.common.bean.ProyectoManifiestoBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.LogisticaService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.gestion_almacenes.ReporteProyectoManifiesto;

/**
 * @className: ProyectoManifiestoController.java
 * @description: 
 * @date: 17 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Controller
@RequestMapping("/gestion-almacenes/proyecto-manifiesto")
public class ProyectoManifiestoController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Autowired 
	private LogisticaService logisticaService;
	
	@Autowired 
	private GeneralService generalService;
	
	/**
	 * @param indicador 
	 * @param model 
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio/{indicador}", method = RequestMethod.GET)
    public String inicio(@PathVariable("indicador") String indicador, Model model) {
        try {
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	model.addAttribute("lista_anio", generalService.listarAnios());
        	
        	model.addAttribute("lista_mes", generalService.listarMeses(new ItemBean()));
        	
        	model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
        	        	
        	model.addAttribute("lista_tipo_movimiento", generalService.listarTipoMovimientoPm());
        	
        	ProyectoManifiestoBean proyectoManifiesto = new ProyectoManifiestoBean();
        	
        	ControlCalidadBean parametroAlmacenActivo = new ControlCalidadBean();
    		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
    		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
    		List<ControlCalidadBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
    		if (!isEmpty(listaAlmacenActivo)) {
    			proyectoManifiesto.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
    			proyectoManifiesto.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
    			proyectoManifiesto.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
    			proyectoManifiesto.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
    			proyectoManifiesto.setCodigoMes(listaAlmacenActivo.get(0).getCodigoMes());
    		}
    		
    		model.addAttribute("proyectoManifiesto", getParserObject(proyectoManifiesto));
        	
        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar_proyecto_manifiesto";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProyectoManifiesto", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProyectoManifiesto(HttpServletRequest request, HttpServletResponse response) {
		List<ProyectoManifiestoBean> lista = null;
		try {			
			ProyectoManifiestoBean proyectoManifiestoBean = new ProyectoManifiestoBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(proyectoManifiestoBean, request.getParameterMap());			
			lista = logisticaService.listarProyectoManifiesto(proyectoManifiestoBean);
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
	@RequestMapping(value = "/mantenimientoProyectoManifiesto/{codigo}", method = RequestMethod.GET)
    public String mantenimientoProyectoManifiesto(@PathVariable("codigo") Integer codigo, Model model) {
        try {
        	ProyectoManifiestoBean proyectoManifiesto = new ProyectoManifiestoBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	String anioActual = generalService.obtenerAnioActual();
        	
        	if (!isNullInteger(codigo)) {
        		
        		proyectoManifiesto = logisticaService.obtenerRegistroProyectoManifiesto(codigo);

        	} else {

        		StringBuilder correlativo = new StringBuilder();
        		correlativo.append(usuarioBean.getCodigoDdi());
        		correlativo.append(Constantes.SEPARADOR);
        		correlativo.append(usuarioBean.getCodigoAlmacen());
        		correlativo.append(Constantes.SEPARADOR);
        		
        		ProyectoManifiestoBean parametros = new ProyectoManifiestoBean();        		
        		parametros.setCodigoAnio(anioActual);
        		parametros.setCodigoDdi(usuarioBean.getCodigoDdi());
        		parametros.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametros.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        		ProyectoManifiestoBean respuestaCorrelativo = logisticaService.obtenerCorrelativoProyectoManifiesto(parametros);
      
        		correlativo.append(respuestaCorrelativo.getNroProyectoManifiesto());
        		
        		proyectoManifiesto.setNroProyectoManifiesto(correlativo.toString());        		
        		
        		ControlCalidadBean parametroAlmacenActivo = new ControlCalidadBean();
        		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
        		List<ControlCalidadBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
        		if (!isEmpty(listaAlmacenActivo)) {
        			proyectoManifiesto.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
        			proyectoManifiesto.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
        			proyectoManifiesto.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
        			proyectoManifiesto.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
        			proyectoManifiesto.setCodigoMes(listaAlmacenActivo.get(0).getCodigoMes());
        		}
        		
        		proyectoManifiesto.setIdDdi(usuarioBean.getIdDdi());
        		proyectoManifiesto.setCodigoDdi(usuarioBean.getCodigoDdi());
        		proyectoManifiesto.setNombreDdi(usuarioBean.getNombreDdi());
        	}
        	
        	if (!Utils.isNullInteger(usuarioBean.getIdDdi())) {
        		model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
        	}
        	
        	model.addAttribute("proyectoManifiesto", getParserObject(proyectoManifiesto));
        	
        	model.addAttribute("lista_tipo_movimiento", generalService.listarTipoMovimientoPm());

        	model.addAttribute("lista_estado", generalService.listarEstado(new ItemBean(null, Constantes.FOUR_INT)));
        	
        	model.addAttribute("lista_programacion", generalService.listarProgramacion());

        	model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean()));
   	
        	model.addAttribute("lista_tipo_documento", generalService.listarTipoDocumento(new ItemBean()));
     
        	model.addAttribute("lista_categoria", generalService.listarCategoria(new ItemBean(Constantes.THREE_INT)));
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_proyecto_manifiesto";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/grabarProyectoManifiesto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarProyectoManifiesto(HttpServletRequest request, HttpServletResponse response) {
		ProyectoManifiestoBean proyectoManifiesto = null;
		try {			
			ProyectoManifiestoBean proyectoManifiestoBean = new ProyectoManifiestoBean();
			
			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(proyectoManifiestoBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
			proyectoManifiestoBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        	proyectoManifiestoBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	proyectoManifiesto = logisticaService.grabarProyectoManifiesto(proyectoManifiestoBean);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return proyectoManifiesto;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/verificarProductosProgramacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object verificarProductosProgramacion(HttpServletRequest request, HttpServletResponse response) {
		Integer indicador = null;
		try {			
			indicador = logisticaService.verificarProductosProgramacion(getInteger(request.getParameter("idProyectoManifiesto")));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return indicador;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/procesarProyectoManifiesto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object procesarProyectoManifiesto(HttpServletRequest request, HttpServletResponse response) {
		BaseOutputBean indicador = null;
		try {			
			ProyectoManifiestoBean proyectoManifiestoBean = new ProyectoManifiestoBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(proyectoManifiestoBean, request.getParameterMap());
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	proyectoManifiestoBean.setUsuarioRegistro(usuarioBean.getNombreUsuario());
			logisticaService.procesarManifiestoProducto(proyectoManifiestoBean);
			indicador = getBaseRespuesta(Constantes.COD_EXITO_GENERAL);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return indicador;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProductoProyectoManifiesto", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProductoProyectoManifiesto(HttpServletRequest request, HttpServletResponse response) {
		List<ProductoProyectoManifiestoBean> lista = null;
		try {			
			ProductoProyectoManifiestoBean producto = new ProductoProyectoManifiestoBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(producto, request.getParameterMap());			
			lista = logisticaService.listarProductoProyectoManifiesto(producto);
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
	@RequestMapping(value = "/grabarProductoProyectoManifiesto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarProductoProyectoManifiesto(HttpServletRequest request, HttpServletResponse response) {
		ProductoProyectoManifiestoBean producto = null;
		try {			
			ProductoProyectoManifiestoBean productoProyectoManifiestoBean = new ProductoProyectoManifiestoBean();

			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);			
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Convierte los vacios en nulos en los decimales
			BigDecimalConverter con_decimal = new BigDecimalConverter(null);
			beanUtilsBean.getConvertUtils().register(con_decimal, BigDecimal.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(productoProyectoManifiestoBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	productoProyectoManifiestoBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			producto = logisticaService.grabarProductoProyectoManifiesto(productoProyectoManifiestoBean);
			
			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return producto;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/eliminarProductoProyectoManifiesto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarProductoProyectoManifiesto(HttpServletRequest request, HttpServletResponse response) {
		ProductoProyectoManifiestoBean producto = null;
		try {			
			String[] arrIdDetalleProyectoManifiesto = request.getParameter("arrIdDetalleProyectoManifiesto").split(Constantes.UNDERLINE);
			for (String codigo : arrIdDetalleProyectoManifiesto) {				
				ProductoProyectoManifiestoBean productoControlCalidadBean = new ProductoProyectoManifiestoBean(getInteger(codigo));

				// Retorno los datos de session
	        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
	        	
	        	productoControlCalidadBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
				producto = logisticaService.eliminarProductoProyectoManifiesto(productoControlCalidadBean);				
			}

			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return producto;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarManifiestoVehiculo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarManifiestoVehiculo(HttpServletRequest request, HttpServletResponse response) {
		List<ManifiestoVehiculoBean> lista = null;
		try {			
			ManifiestoVehiculoBean vehiculo = new ManifiestoVehiculoBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(vehiculo, request.getParameterMap());			
			lista = logisticaService.listarManifiestoVehiculo(vehiculo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param idProyectoManifiesto 
	 * @param arrFlagVehiculo 
	 * @param arrIdTipoCamion 
	 * @param arrVolumen 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/procesarManifiestoVehiculo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object procesarManifiestoVehiculo(@RequestParam(value="idProyectoManifiesto") Integer idProyectoManifiesto,
											 @RequestParam(value="arrFlagVehiculo[]") List<String> arrFlagVehiculo,
											 @RequestParam(value="arrIdTipoCamion[]") List<Integer> arrIdTipoCamion,
											 @RequestParam(value="arrVolumen[]") List<BigDecimal> arrVolumen) {
		ManifiestoVehiculoBean manifiesto = new ManifiestoVehiculoBean();
		try {
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
			for (int i = 0; i < arrIdTipoCamion.size(); i++) {
				ManifiestoVehiculoBean registroManifiestoVehiculo = new ManifiestoVehiculoBean();
				registroManifiestoVehiculo.setIdProyectoManifiesto(idProyectoManifiesto);
				registroManifiestoVehiculo.setFlagVehiculo(arrFlagVehiculo.get(i));
				registroManifiestoVehiculo.setIdTipoCamion(arrIdTipoCamion.get(i));
				registroManifiestoVehiculo.setVolumen(arrVolumen.get(i));				
				registroManifiestoVehiculo.setUsuarioRegistro(usuarioBean.getNombreUsuario());
				logisticaService.procesarManifiestoVehiculo(registroManifiestoVehiculo);
			}
			manifiesto.setCodigoRespuesta(Constantes.COD_EXITO_GENERAL);
        	manifiesto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return manifiesto;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarDocumentoProyectoManifiesto", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarDocumentoProyectoManifiesto(HttpServletRequest request, HttpServletResponse response) {
		List<DocumentoProyectoManifiestoBean> lista = null;
		try {			
			DocumentoProyectoManifiestoBean documento = new DocumentoProyectoManifiestoBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(documento, request.getParameterMap());
			documento.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
			lista = logisticaService.listarDocumentoProyectoManifiesto(documento);
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
	@RequestMapping(value = "/grabarDocumentoProyectoManifiesto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarDocumentoProyectoManifiesto(HttpServletRequest request, HttpServletResponse response) {
		DocumentoProyectoManifiestoBean documento = null;
		try {			
			DocumentoProyectoManifiestoBean documentoProyectoManifiestoBean = new DocumentoProyectoManifiestoBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(documentoProyectoManifiestoBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	documentoProyectoManifiestoBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	documento = logisticaService.grabarDocumentoProyectoManifiesto(documentoProyectoManifiestoBean);
			
        	documento.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return documento;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/eliminarDocumentoProyectoManifiesto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarDocumentoProyectoManifiesto(HttpServletRequest request, HttpServletResponse response) {
		DocumentoProyectoManifiestoBean documento = null;
		try {			
			String[] arrIdDocumentoProyectoManifiesto = request.getParameter("arrIdDocumentoProyectoManifiesto").split(Constantes.UNDERLINE);
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
			for (String codigo : arrIdDocumentoProyectoManifiesto) {				
				DocumentoProyectoManifiestoBean documentoProyectoManifiestoBean = new DocumentoProyectoManifiestoBean(getInteger(codigo));

	        	documentoProyectoManifiestoBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
	        	documento = logisticaService.eliminarDocumentoProyectoManifiesto(documentoProyectoManifiestoBean);				
			}

			documento.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return documento;
	}
	
	/**
	 * @param codigoAnio 
	 * @param codigoMes 
	 * @param idAlmacen
	 * @param codigoMovimiento 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarExcel/{codigoAnio}/{codigoMes}/{idAlmacen}/{codigoMovimiento}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("codigoAnio") String codigoAnio, 
								@PathVariable("codigoMes") String codigoMes, 
								@PathVariable("idAlmacen") Integer idAlmacen,
								@PathVariable("codigoMovimiento") String codigoMovimiento, 
								HttpServletResponse response) {
	    try {
	    	ProyectoManifiestoBean proyectoManifiestoBean = new ProyectoManifiestoBean();
	    	proyectoManifiestoBean.setCodigoAnio(verificaParametro(codigoAnio));
	    	proyectoManifiestoBean.setCodigoMes(verificaParametro(codigoMes));
	    	proyectoManifiestoBean.setIdAlmacen(idAlmacen);
	    	proyectoManifiestoBean.setCodigoMovimiento(codigoMovimiento);
	    	
			List<ProyectoManifiestoBean> lista = logisticaService.listarProyectoManifiesto(proyectoManifiestoBean);
	    	
			String file_name = "ProyectoManifiesto";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteProyectoManifiesto reporte = new ReporteProyectoManifiesto();
		    HSSFWorkbook wb = reporte.generaReporteExcelProyectoManifiesto(lista);
			
			response.resetBuffer();
            response.setContentType(Constantes.MIME_APPLICATION_XLS);
            response.setHeader("Content-Disposition", "attachment; filename="+file_name);            
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "private");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setDateHeader("Expires", 1);
            
		    // Captured backflow
	    	OutputStream out = response.getOutputStream();
	    	wb.write(out); // We write in that flow
	    	out.flush(); // We emptied the flow
	    	out.close(); // We close the flow
	    	
	    	return Constantes.COD_EXITO_GENERAL;   	
	    } catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	return Constantes.COD_ERROR_GENERAL;
	    } 
	}
	
	/**
	 * @param codigo
	 * @param request 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarPdf/{codigo}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarPdf(@PathVariable("codigo") Integer codigo, HttpServletRequest request, HttpServletResponse response) {
	    try {
	    	
	    	ProyectoManifiestoBean proyectoManifiesto = logisticaService.obtenerRegistroProyectoManifiesto(codigo);
	    	
	    	ProductoProyectoManifiestoBean producto = new ProductoProyectoManifiestoBean();
	    	producto.setIdProyectoManifiesto(codigo);
	    	List<ProductoProyectoManifiestoBean> listaProducto = logisticaService.listarProductoProyectoManifiesto(producto);
	    	
	    	List<ManifiestoVehiculoBean> listaVehiculo = logisticaService.listarManifiestoVehiculo(new ManifiestoVehiculoBean(codigo));

	    	StringBuilder file_path = new StringBuilder();
	    	file_path.append(getPath(request));
	    	file_path.append(File.separator);
	    	file_path.append(Constantes.UPLOAD_PATH_FILE_TEMP);
	    	file_path.append(File.separator);
	    	file_path.append(Calendar.getInstance().getTime().getTime());
	    	file_path.append(Constantes.EXTENSION_FORMATO_PDF);
	    	
	    	String file_name = "Proyecto_Manifiesto";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_PDF);
			
			ReporteProyectoManifiesto reporte = new ReporteProyectoManifiesto();
			reporte.generaPDFReporteProyectoManifiesto(file_path.toString(), proyectoManifiesto, listaProducto, listaVehiculo);
			
			response.resetBuffer();
            response.setContentType(Constantes.MIME_APPLICATION_PDF);
            response.setHeader("Content-Disposition", "attachment; filename="+file_name);            
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "private");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setDateHeader("Expires", 1);
	    	
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos = convertPDFToByteArrayOutputStream(file_path.toString());
			
			// Captured backflow
	        OutputStream out = response.getOutputStream();
	        baos.writeTo(out); // We write in that flow
	        out.flush(); // We emptied the flow
	    	out.close(); // We close the flow
	    	
	    	File file_temp = new File(file_path.toString());
    		if (file_temp.delete()) {
    			LOGGER.info("[exportarPdf] "+file_temp.getName()+" se borra el archivo temporal.");
    		} else {
    			LOGGER.info("[exportarPdf] "+file_temp.getName()+" no se logró borrar el archivo temporal.");
    		}
    		
	    	return Constantes.COD_EXITO_GENERAL;
	    } catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	return Constantes.COD_ERROR_GENERAL;
	    } 
	}
	
}

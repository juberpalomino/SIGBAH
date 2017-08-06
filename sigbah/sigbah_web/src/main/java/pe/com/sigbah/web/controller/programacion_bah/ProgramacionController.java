package pe.com.sigbah.web.controller.programacion_bah;

import java.io.OutputStream;
import java.math.BigDecimal;
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

import pe.com.sigbah.common.bean.DocumentoProgramacionBean;
import pe.com.sigbah.common.bean.EstadoProgramacionBean;
import pe.com.sigbah.common.bean.EstadoUsuarioBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ProductoAlimentoBean;
import pe.com.sigbah.common.bean.ProductoNoAlimentarioBean;
import pe.com.sigbah.common.bean.ProductoNoAlimentarioProgramacionBean;
import pe.com.sigbah.common.bean.ProgramacionAlimentoBean;
import pe.com.sigbah.common.bean.ProgramacionAlmacenBean;
import pe.com.sigbah.common.bean.ProgramacionBean;
import pe.com.sigbah.common.bean.ProgramacionNoAlimentarioBean;
import pe.com.sigbah.common.bean.RacionOperativaBean;
import pe.com.sigbah.common.bean.RequerimientoBean;
import pe.com.sigbah.common.bean.ResumenStockAlimentoBean;
import pe.com.sigbah.common.bean.ResumenStockNoAlimentarioBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.service.AdministracionService;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.ProgramacionRequerimientoService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.programacion_bah.ReporteProgramacion;

/**
 * @className: ProgramacionController.java
 * @description: 
 * @date: 29/07/2017
 * @author: Junior Huaman Flores.
 */

@Controller
@RequestMapping("/programacion-bah/programacion")
public class ProgramacionController extends BaseController {
	
private static final long serialVersionUID = 1L;
	
	@Autowired 
	private ProgramacionRequerimientoService programacionRequerimientoService;
	
	@Autowired 
	private GeneralService generalService;
	
	@Autowired 
	private AdministracionService administracionService;
	
	/**
	 * @param indicador
	 * @param model
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/inicio/{indicador}", method = RequestMethod.GET)
    public String inicio(@PathVariable("indicador") String indicador, Model model) {
        try {
        	model.addAttribute("lista_anio", generalService.listarAnios());
        	
        	model.addAttribute("lista_mes", generalService.listarMeses(new ItemBean()));
        	
        	model.addAttribute("lista_fenomeno", generalService.listarFenomeno(new ItemBean()));
        	
        	model.addAttribute("lista_estado", generalService.listarEstado(new ItemBean(null, Constantes.ONE_INT)));

        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar_programacion";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProgramacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProgramacion(HttpServletRequest request, HttpServletResponse response) {
		List<ProgramacionBean> lista = null;
		try {			
			ProgramacionBean programacionBean = new ProgramacionBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(programacionBean, request.getParameterMap());			
			lista = programacionRequerimientoService.listarProgramacion(programacionBean);
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
	@RequestMapping(value = "/mantenimientoProgramacion/{codigo}", method = RequestMethod.GET)
    public String mantenimientoProgramacion(@PathVariable("codigo") Integer codigo, Model model) {
        try {
        	ProgramacionBean programacion = new ProgramacionBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	if (!isNullInteger(codigo)) {
        		
        		programacion = programacionRequerimientoService.obtenerRegistroProgramacion(codigo);
        		
        	} else {

        		ProgramacionBean parametros = new ProgramacionBean();
        		String anioActual = generalService.obtenerAnioActual();
        		parametros.setCodigoAnio(anioActual);
        		parametros.setCodigoDdi(usuarioBean.getCodigoDdi());
        		parametros.setIdDdi(usuarioBean.getIdDdi());        		
        		ProgramacionBean respuestaCorrelativo = programacionRequerimientoService.obtenerCorrelativoProgramacion(parametros);
        		programacion.setNroProgramacion(respuestaCorrelativo.getNroProgramacion());
            	programacion.setIdDdi(usuarioBean.getIdDdi());
        		programacion.setCodigoDdi(usuarioBean.getCodigoDdi());
        		programacion.setNombreDdi(usuarioBean.getNombreDdi());
        		programacion.setCodigoAnio(anioActual);
        	}
        	
        	model.addAttribute("programacion", getParserObject(programacion));
        	
        	RequerimientoBean requerimientoBean = new RequerimientoBean();
        	requerimientoBean.setCodAnio(usuarioBean.getCodigoAnio());
        	requerimientoBean.setIdDdi(usuarioBean.getIdDdi());
        	model.addAttribute("lista_requerimiento", programacionRequerimientoService.listarRequerimiento(requerimientoBean));
        	
        	model.addAttribute("lista_racion", programacionRequerimientoService.listarRacionOperativa(new RacionOperativaBean()));
        	
        	model.addAttribute("lista_dee", generalService.listarDee(new ItemBean()));

        	model.addAttribute("lista_region", generalService.listarRegion(new ItemBean()));
        	
        	List<ItemBean> listaAlmacen = null;
        	if (usuarioBean.getCodigoDdi().equals(Constantes.CODIGO_DDI_INDECI_CENTRAL)) {
        		listaAlmacen = generalService.listarAlmacen(new ItemBean());
        	} else {
        		listaAlmacen = generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi()));
        	}
        	model.addAttribute("listaAlmacen", getParserObject(listaAlmacen));
        	
        	model.addAttribute("lista_categoria", generalService.listarCategoria(new ItemBean(Constantes.TWO_INT)));

        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_programacion";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/grabarProgramacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarProgramacion(HttpServletRequest request, HttpServletResponse response) {
		ProgramacionBean programacion = null;
		try {			
			ProgramacionBean programacionBean = new ProgramacionBean();
			
			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(programacionBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	programacionBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			programacion = programacionRequerimientoService.grabarProgramacion(programacionBean);			
			programacion.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return programacion;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProgramacionAlmacen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProgramacionAlmacen(HttpServletRequest request, HttpServletResponse response) {
		List<ProgramacionAlmacenBean> lista = null;
		try {			
			ProgramacionAlmacenBean programacionAlmacenBean = new ProgramacionAlmacenBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(programacionAlmacenBean, request.getParameterMap());			
			lista = programacionRequerimientoService.listarProgramacionAlmacen(programacionAlmacenBean);
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
	@RequestMapping(value = "/grabarProgramacionAlmacen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarProgramacionAlmacen(HttpServletRequest request, HttpServletResponse response) {
		ProgramacionAlmacenBean programacionAlmacen = null;
		try {			
			ProgramacionAlmacenBean programacionAlmacenBean = new ProgramacionAlmacenBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(programacionAlmacenBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	programacionAlmacenBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	programacionAlmacen = programacionRequerimientoService.grabarProgramacionAlmacen(programacionAlmacenBean);			
        	programacionAlmacen.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return programacionAlmacen;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/eliminarProgramacionAlmacen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarProgramacionAlmacen(HttpServletRequest request, HttpServletResponse response) {
		ProgramacionAlmacenBean programacionAlmacen = null;
		try {			
			String[] arrIdDetalleControlCalidad = request.getParameter("arrIdDetalleProgramacionAlmacen").split(Constantes.UNDERLINE);
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
			
			for (String codigo : arrIdDetalleControlCalidad) {				
				ProgramacionAlmacenBean programacionAlmacenBean = new ProgramacionAlmacenBean(getInteger(codigo));

				programacionAlmacenBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
				programacionAlmacen = programacionRequerimientoService.eliminarProgramacionAlmacen(programacionAlmacenBean);				
			}

			programacionAlmacen.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return programacionAlmacen;
	}
	
	/**
	 * @param idRacionOperativa 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProgramacionRacionOperativa", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProgramacionRacionOperativa(@RequestParam(value="idRacionOperativa") Integer idRacionOperativa) {
		List<RacionOperativaBean> lista = null;
		try {		
			lista = programacionRequerimientoService.listarProgramacionRacionOperativa(idRacionOperativa);
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
	@RequestMapping(value = "/actualizarProgramacionRacionOperativa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object actualizarProgramacionRacionOperativa(HttpServletRequest request, HttpServletResponse response) {
		RacionOperativaBean racionOperativa = null;
		try {			
			RacionOperativaBean racionOperativaBean = new RacionOperativaBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(racionOperativaBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	racionOperativaBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	racionOperativa = programacionRequerimientoService.actualizarProgramacionRacionOperativa(racionOperativaBean);			
        	racionOperativa.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return racionOperativa;
	}
	
	/**
	 * @param idProgramacion 
	 * @param arrIdProducto 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProgramacionAlimento", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProgramacionAlimento(@RequestParam(value="idProgramacion") Integer idProgramacion,
											 @RequestParam(value="arrIdProducto[]") List<Integer> arrIdProducto) {
		List<ProgramacionAlimentoBean> lista = null;
		try {		
			lista = programacionRequerimientoService.listarProgramacionAlimento(idProgramacion, arrIdProducto);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param idProgramacion 
	 * @param idRacionOperativa 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarResumenStockAlimento", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarResumenStockAlimento(@RequestParam(value="idProgramacion") Integer idProgramacion,
									  @RequestParam(value="idRacionOperativa") Integer idRacionOperativa) {
		List<ResumenStockAlimentoBean> lista = null;
		try {		
			lista = programacionRequerimientoService.listarResumenStockAlimento(idProgramacion, idRacionOperativa);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param idProgramacionUbigeo 
	 * @param arrIdProducto 
	 * @param arrUnidad 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/actualizarDetalleProgramacionAlimento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object actualizarDetalleProgramacionAlimento(@RequestParam(value="idProgramacionUbigeo") Integer idProgramacionUbigeo,
														@RequestParam(value="arrIdProducto[]") List<Integer> arrIdProducto,
														@RequestParam(value="arrUnidad[]") List<BigDecimal> arrUnidad) {
		ProductoAlimentoBean productoAlimento = null;
		try {
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	if (!isEmpty(arrIdProducto)) {
        		for (int i = 0; i < arrIdProducto.size(); i++) {
		        	ProductoAlimentoBean productoAlimentoBean = new ProductoAlimentoBean();
		        	productoAlimentoBean.setIdProgramacionUbigeo(idProgramacionUbigeo);
		        	productoAlimentoBean.setIdProducto(arrIdProducto.get(i));
		        	productoAlimentoBean.setUnidad(arrUnidad.get(i));
		        	productoAlimentoBean.setUsuarioRegistro(usuarioBean.getUsuario());				
		        	productoAlimento = programacionRequerimientoService.actualizarDetalleProgramacionAlimento(productoAlimentoBean);
        		}
        	} else {
        		productoAlimento = new ProductoAlimentoBean();
        		productoAlimento.setCodigoRespuesta(Constantes.COD_EXITO_GENERAL);
        	}
	        	
        	productoAlimento.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return productoAlimento;
	}
	
	/**
	 * @param arrIdDetalleProgramacionUbigeo 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/eliminarDetalleProgramacionAlimento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarDetalleProgramacionAlimento(@RequestParam(value="arrIdDetalleProgramacionUbigeo[]") List<Integer> arrIdDetalleProgramacionUbigeo) {
		ProgramacionAlimentoBean programacionAlimento = null;
		try {			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
			
			for (Integer idProgramacionUbigeo : arrIdDetalleProgramacionUbigeo) {				
				ProgramacionAlimentoBean programacionAlimentoBean = new ProgramacionAlimentoBean(idProgramacionUbigeo);

				programacionAlimentoBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
				programacionAlimento = programacionRequerimientoService.eliminarDetalleProgramacionAlimento(programacionAlimentoBean);				
			}

			programacionAlimento.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return programacionAlimento;
	}
	
	/**
	 * @param idProgramacion 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/eliminarProgramacionAlimento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarProgramacionAlimento(@RequestParam(value="idProgramacion") Integer idProgramacion) {
		ProgramacionBean programacion = null;
		try {			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
			
			ProgramacionBean programacionBean = new ProgramacionBean(idProgramacion);

			programacionBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
			programacion = programacionRequerimientoService.eliminarProgramacionAlimento(programacionBean);				

			programacion.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return programacion;
	}
	
	/**
	 * @param idProgramacion 
	 * @param arrIdProducto 
	 * @param arrNombreProducto 
	 * @param arrUnidadProducto 
	 * @param response 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/exportarExcelAlimento", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcelAlimento(@RequestParam(value="idProgramacion") Integer idProgramacion,
										@RequestParam(value="arrIdProducto[]") List<Integer> arrIdProducto,
			 							@RequestParam(value="arrNombreProducto[]") List<String> arrNombreProducto,
			 							@RequestParam(value="arrUnidadProducto[]") List<BigDecimal> arrUnidadProducto,
										HttpServletResponse response) {
		try {
	    	
			List<ProgramacionAlimentoBean> listaProgramacionAlimento = programacionRequerimientoService.listarProgramacionAlimento(idProgramacion, arrIdProducto);
			
			String file_name = "ProgramacionAlimento";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteProgramacion reporte = new ReporteProgramacion();
		    HSSFWorkbook wb = reporte.generaReporteExcelAlimento(listaProgramacionAlimento, arrIdProducto, arrNombreProducto, arrUnidadProducto);
			
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
	 * @param idProgramacion 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProductoNoAlimentarioProgramacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProductoNoAlimentarioProgramacion(@RequestParam(value="idProgramacion") Integer idProgramacion) {
		List<ProductoNoAlimentarioProgramacionBean> lista = null;
		try {						
			lista = programacionRequerimientoService.listarProductoNoAlimentarioProgramacion(idProgramacion);
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
	@RequestMapping(value = "/grabarProductoNoAlimentarioProgramacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarProductoNoAlimentarioProgramacion(HttpServletRequest request, HttpServletResponse response) {
		ProductoNoAlimentarioProgramacionBean producto = null;
		try {			
			ProductoNoAlimentarioProgramacionBean productoNoAlimentarioProgramacionBean = new ProductoNoAlimentarioProgramacionBean();

			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);			
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Convierte los vacios en nulos en los decimales
			BigDecimalConverter con_decimal = new BigDecimalConverter(null);
			beanUtilsBean.getConvertUtils().register(con_decimal, BigDecimal.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(productoNoAlimentarioProgramacionBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	productoNoAlimentarioProgramacionBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			producto = programacionRequerimientoService.grabarProductoNoAlimentarioProgramacion(productoNoAlimentarioProgramacionBean);
			
			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return producto;
	}
	
	/**
	 * @param arrIdDetalleProductoNoAlimentario
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/eliminarProductoNoAlimentarioProgramacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarProductoNoAlimentarioProgramacion(@RequestParam(value="arrIdDetalleProductoNoAlimentario[]") List<Integer> arrIdDetalleProductoNoAlimentario) {
		ProductoNoAlimentarioProgramacionBean producto = null;
		try {
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
			
			for (Integer codigo : arrIdDetalleProductoNoAlimentario) {				
				ProductoNoAlimentarioProgramacionBean productoNoAlimentarioProgramacionBean = new ProductoNoAlimentarioProgramacionBean(codigo);

				productoNoAlimentarioProgramacionBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
				producto = programacionRequerimientoService.eliminarProductoNoAlimentarioProgramacion(productoNoAlimentarioProgramacionBean);				
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
	@RequestMapping(value = "/actualizarProgramacionNoAlimentario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object actualizarProgramacionNoAlimentario(HttpServletRequest request, HttpServletResponse response) {
		ProductoNoAlimentarioProgramacionBean productoNoAlimentarioProgramacion = null;
		try {			
			ProductoNoAlimentarioProgramacionBean productoNoAlimentarioProgramacionBean = new ProductoNoAlimentarioProgramacionBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(productoNoAlimentarioProgramacionBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	productoNoAlimentarioProgramacionBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	productoNoAlimentarioProgramacion = programacionRequerimientoService.actualizarProgramacionNoAlimentario(productoNoAlimentarioProgramacionBean);			
        	productoNoAlimentarioProgramacion.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return productoNoAlimentarioProgramacion;
	}
	
	/**
	 * @param idProgramacion 
	 * @param arrIdProducto 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProgramacionNoAlimentario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProgramacionNoAlimentario(@RequestParam(value="idProgramacion") Integer idProgramacion,
											   	  @RequestParam(value="arrIdProducto[]") List<Integer> arrIdProducto) {
		List<ProgramacionNoAlimentarioBean> lista = null;
		try {		
			lista = programacionRequerimientoService.listarProgramacionNoAlimentario(idProgramacion, arrIdProducto);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param idProgramacion 
	 * @param idRacionOperativa 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarResumenStockNoAlimentario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarResumenStockNoAlimentario(@RequestParam(value="idProgramacion") Integer idProgramacion,
									  		   	  @RequestParam(value="idRacionOperativa") Integer idRacionOperativa) {
		List<ResumenStockNoAlimentarioBean> lista = null;
		try {		
			lista = programacionRequerimientoService.listarResumenStockNoAlimentario(idProgramacion, idRacionOperativa);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param idProgramacionUbigeo 
	 * @param arrIdProducto 
	 * @param arrUnidad 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/actualizarDetalleProgramacionNoAlimentario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object actualizarDetalleProgramacionNoAlimentario(@RequestParam(value="idProgramacionUbigeo") Integer idProgramacionUbigeo,
														  	 @RequestParam(value="arrIdProducto[]") List<Integer> arrIdProducto,
														  	 @RequestParam(value="arrUnidad[]") List<BigDecimal> arrUnidad) {
		ProductoNoAlimentarioBean productoNoAlimentario = null;
		try {
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	if (!isEmpty(arrIdProducto)) {
        		for (int i = 0; i < arrIdProducto.size(); i++) {
		        	ProductoNoAlimentarioBean productoNoAlimentarioBean = new ProductoNoAlimentarioBean();
		        	productoNoAlimentarioBean.setIdProgramacionUbigeo(idProgramacionUbigeo);
		        	productoNoAlimentarioBean.setIdProducto(arrIdProducto.get(i));
		        	productoNoAlimentarioBean.setUnidad(arrUnidad.get(i));
		        	productoNoAlimentarioBean.setUsuarioRegistro(usuarioBean.getUsuario());				
		        	productoNoAlimentario = programacionRequerimientoService.actualizarDetalleProgramacionNoAlimentario(productoNoAlimentarioBean);
        		}
        	} else {
        		productoNoAlimentario = new ProductoNoAlimentarioBean();
        		productoNoAlimentario.setCodigoRespuesta(Constantes.COD_EXITO_GENERAL);
        	}
	        	
        	productoNoAlimentario.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return productoNoAlimentario;
	}
	
	/**
	 * @param arrIdDetalleProgramacionUbigeo 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/eliminarDetalleProgramacionNoAlimentario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarDetalleProgramacionNoAlimentario(@RequestParam(value="arrIdDetalleProgramacionUbigeo[]") List<Integer> arrIdDetalleProgramacionUbigeo) {
		ProgramacionNoAlimentarioBean programacionNoAlimentario = null;
		try {			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
			
			for (Integer idProgramacionUbigeo : arrIdDetalleProgramacionUbigeo) {				
				ProgramacionNoAlimentarioBean programacionNoAlimentarioBean = new ProgramacionNoAlimentarioBean(idProgramacionUbigeo);

				programacionNoAlimentarioBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
				programacionNoAlimentario = programacionRequerimientoService.eliminarDetalleProgramacionNoAlimentario(programacionNoAlimentarioBean);				
			}

			programacionNoAlimentario.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return programacionNoAlimentario;
	}
	
	/**
	 * @param idProgramacion 
	 * @param arrIdProducto 
	 * @param arrNombreProducto 
	 * @param arrUnidadProducto 
	 * @param response 
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/exportarExcelNoAlimentario", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcelNoAlimentario(@RequestParam(value="idProgramacion") Integer idProgramacion,
											 @RequestParam(value="arrIdProducto[]") List<Integer> arrIdProducto,
											 @RequestParam(value="arrNombreProducto[]") List<String> arrNombreProducto,
											 @RequestParam(value="arrUnidadProducto[]") List<BigDecimal> arrUnidadProducto,
											 HttpServletResponse response) {
		try {
	    	
			List<ProgramacionNoAlimentarioBean> listaProgramacionNoAlimentario = programacionRequerimientoService.listarProgramacionNoAlimentario(idProgramacion, arrIdProducto);
			
			String file_name = "ProgramacionNoAlimentario";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteProgramacion reporte = new ReporteProgramacion();
		    HSSFWorkbook wb = reporte.generaReporteExcelNoAlimentario(listaProgramacionNoAlimentario, arrIdProducto, arrNombreProducto, arrUnidadProducto);
			
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
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarDocumentoProgramacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarDocumentoProgramacion(HttpServletRequest request, HttpServletResponse response) {
		List<DocumentoProgramacionBean> lista = null;
		try {			
			DocumentoProgramacionBean documento = new DocumentoProgramacionBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(documento, request.getParameterMap());			
			lista = programacionRequerimientoService.listarDocumentoProgramacion(documento);
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
	@RequestMapping(value = "/grabarDocumentoProgramacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarDocumentoProgramacion(HttpServletRequest request, HttpServletResponse response) {
		DocumentoProgramacionBean documento = null;
		try {			
			DocumentoProgramacionBean documentoProgramacionBean = new DocumentoProgramacionBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(documentoProgramacionBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	documentoProgramacionBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	documento = programacionRequerimientoService.grabarDocumentoProgramacion(documentoProgramacionBean);
			
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
	@RequestMapping(value = "/eliminarDocumentoProgramacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarDocumentoProgramacion(HttpServletRequest request, HttpServletResponse response) {
		DocumentoProgramacionBean documento = null;
		try {			
			String[] arrIdDetalleProgramacion = request.getParameter("arrIdDocumentoProgramacion").split(Constantes.UNDERLINE);
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
			for (String codigo : arrIdDetalleProgramacion) {				
				DocumentoProgramacionBean documentoProgramacionBean = new DocumentoProgramacionBean(getInteger(codigo));

	        	documentoProgramacionBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
	        	documento = programacionRequerimientoService.eliminarDocumentoProgramacion(documentoProgramacionBean);				
			}

			documento.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				

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
	@RequestMapping(value = "/listarEstadoProgramacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarEstadoProgramacion(HttpServletRequest request, HttpServletResponse response) {
		List<EstadoProgramacionBean> lista = null;
		try {			
			EstadoProgramacionBean estado = new EstadoProgramacionBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(estado, request.getParameterMap());			
			lista = programacionRequerimientoService.listarEstadoProgramacion(estado);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}	
	
	/**
	 * @param codigoAnio 
	 * @param codigoMes 
	 * @param idDdi
	 * @param idFenomeno 
	 * @param idEstado 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarExcel/{codigoAnio}/{codigoMes}/{idDdi}/{idFenomeno}/{idEstado}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("codigoAnio") String codigoAnio, 
								@PathVariable("codigoMes") String codigoMes, 
								@PathVariable("idDdi") Integer idDdi,
								@PathVariable("idFenomeno") Integer idFenomeno,
								@PathVariable("idEstado") Integer idEstado,
								HttpServletResponse response) {
	    try {
	    	ProgramacionBean programacionBean = new ProgramacionBean();
			programacionBean.setCodigoAnio(verificaParametro(codigoAnio));
			programacionBean.setCodigoMes(verificaParametro(codigoMes));
			programacionBean.setIdDdi(idDdi);
			programacionBean.setIdFenomeno(idFenomeno);
			programacionBean.setIdEstado(idEstado);
			List<ProgramacionBean> lista = programacionRequerimientoService.listarProgramacion(programacionBean);
	    	
			String file_name = "ListaProgramacion";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteProgramacion reporte = new ReporteProgramacion();
		    HSSFWorkbook wb = reporte.generaReporteExcelProgramacion(lista);
			
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
	
//	/**
//	 * @param codigo 
//	 * @param request 
//	 * @param response
//	 * @return Objeto.
//	 */
//	@RequestMapping(value = "/exportarPdf/{codigo}", method = RequestMethod.GET)
//	@ResponseBody
//	public String exportarPdf(@PathVariable("codigo") Integer codigo, HttpServletRequest request, HttpServletResponse response) {
//	    try {
//			List<DetalleProductoProgramacionBean> lista = programacionRequerimientoService.listarDetalleProductoProgramacion(codigo);
//			if (isEmpty(lista)) {
//				return Constantes.COD_VALIDACION_GENERAL; // Sin registros asociados
//			}			
//			DetalleProductoProgramacionBean producto = lista.get(0);
//
//			ExportarArchivo printer = new ExportarArchivo();
//			StringBuilder jasperFile = new StringBuilder();
//			jasperFile.append(getPath(request));
//			jasperFile.append(File.separator);
//			jasperFile.append(Constantes.REPORT_PATH_ALMACENES);
//			if (producto.getFlagTipoProducto().equals(Constantes.ONE_STRING)) {
//				jasperFile.append("Control_Calidad_Alimentaria.jrxml");
//			} else {
//				jasperFile.append("Control_Calidad_No_Alimentaria.jrxml");
//			}
//			
//			Map<String, Object> parameters = new HashMap<String, Object>();
//
//			// Agregando los parámetros del reporte
//			StringBuilder logo_indeci_path = new StringBuilder();
//			logo_indeci_path.append(getPath(request));
//			logo_indeci_path.append(File.separator);
//			logo_indeci_path.append(Constantes.IMAGE_INDECI_REPORT_PATH);
//			parameters.put("P_LOGO_INDECI", logo_indeci_path.toString());			
//			StringBuilder logo_wfp_path = new StringBuilder();
//			logo_wfp_path.append(getPath(request));
//			logo_wfp_path.append(File.separator);
//			logo_wfp_path.append(Constantes.IMAGE_WFP_REPORT_PATH);
//			parameters.put("P_LOGO_WFP", logo_wfp_path.toString());			
//			StringBuilder logo_check_path = new StringBuilder();
//			logo_check_path.append(getPath(request));
//			logo_check_path.append(File.separator);
//			logo_check_path.append(Constantes.IMAGE_CHECK_REPORT_PATH);
//			parameters.put("P_LOGO_CHECK", logo_check_path.toString());			
//			StringBuilder logo_check_min_path = new StringBuilder();
//			logo_check_min_path.append(getPath(request));
//			logo_check_min_path.append(File.separator);
//			logo_check_min_path.append(Constantes.IMAGE_CHECK_REPORT_PATH);
//			parameters.put("P_LOGO_CHECK_MIN", logo_check_min_path.toString());			
//			parameters.put("P_NRO_CONTROL_CALIDAD", producto.getNroProgramacion());
//			parameters.put("P_DDI", producto.getNombreDdi());			
//			parameters.put("P_ALMACEN", producto.getNombreAlmacen());
//			parameters.put("P_FECHA_EMISION", producto.getFechaEmision());
//			parameters.put("P_TIPO_CONTROL", producto.getTipoProgramacion());
//			parameters.put("P_ALMACEN_ORIGEN_DESTINO", producto.getNombreAlmacen());
//			parameters.put("P_PROVEEDOR", producto.getProveedorDestino());
//			parameters.put("P_NRO_ORDEN_COMPRA", producto.getNroOrdenCompra());
//			parameters.put("P_CONCLUSIONES", producto.getConclusiones());
//			parameters.put("P_RECOMENDACIONES", producto.getRecomendaciones());
//
//			byte[] array = printer.exportPdf(jasperFile.toString(), parameters, lista);
//			InputStream input = new ByteArrayInputStream(array);
//	        
//	        String file_name = "Reporte_Control_Calidad";
//			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_PDF);
//	    	
//	        response.resetBuffer();
//            response.setContentType(Constantes.MIME_APPLICATION_PDF);
//            response.setHeader("Content-Disposition", "attachment; filename="+file_name);            
//			response.setHeader("Pragma", "no-cache");
//			response.setHeader("Cache-Control", "no-store");
//			response.setHeader("Pragma", "private");
//			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
//			response.setDateHeader("Expires", 1);
//			
//			byte[] buffer = new byte[4096];
//	    	int n = 0;
//
//	    	OutputStream output = response.getOutputStream();
//	    	while ((n = input.read(buffer)) != -1) {
//	    	    output.write(buffer, 0, n);
//	    	}
//	    	output.close();
//
//	    	return Constantes.COD_EXITO_GENERAL;
//	    } catch (Exception e) {
//	    	LOGGER.error(e.getMessage(), e);
//	    	return Constantes.COD_ERROR_GENERAL;
//	    } 
//	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/obtenerEstadosProgramacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object obtenerEstadosProgramacion(HttpServletRequest request, HttpServletResponse response) {
		List<EstadoUsuarioBean> lista = null;
		try {			
			// Retorno los datos de session
           	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
           	EstadoUsuarioBean estadoUsuarioBean = new EstadoUsuarioBean();
           	estadoUsuarioBean.setIdUsuario(usuarioBean.getIdUsuario());
           	estadoUsuarioBean.setNombreModulo(Constantes.MODULO_PROGRAMACION);
			lista = administracionService.listarEstadoUsuario(estadoUsuarioBean);
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
	@RequestMapping(value = "/grabarEstadoProgramacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarEstadoProgramacion(HttpServletRequest request, HttpServletResponse response) {
		EstadoProgramacionBean producto = null;
		try {
			EstadoProgramacionBean estadoProgramacionBean = new EstadoProgramacionBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(estadoProgramacionBean, request.getParameterMap());	
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);

        	estadoProgramacionBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
			producto = programacionRequerimientoService.grabarEstadoProgramacion(estadoProgramacionBean);				

			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return producto;
	}
	
}

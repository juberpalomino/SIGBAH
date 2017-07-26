package pe.com.sigbah.web.controller.programacion_bah;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import pe.com.sigbah.common.bean.EmergenciaBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.RequerimientoBean;
import pe.com.sigbah.common.bean.UbigeoBean;
import pe.com.sigbah.common.bean.UbigeoIneiBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.ProgramacionService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.programacion_bah.ReporteEmergencia;
import pe.com.sigbah.web.report.programacion_bah.ReporteRequerimiento;

/**
 * @className: EmergenciaController.java
 * @description: 
 * @date: 13 jul. 2017
 * @author: whr.
 */

@Controller
@RequestMapping("/programacion-bath/requerimiento")
public class RequerimientoEdanController extends BaseController {
	
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
        	
        	model.addAttribute("lista_ddi", generalService.listarDdi(new ItemBean()));
        	
        	model.addAttribute("lista_fenomeno", generalService.listarFenomeno(new ItemBean()));
      	
        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "requerimiento-edan";
    }

	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listarRequerimientos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarRequerimientos(HttpServletRequest request, HttpServletResponse response) {
		List<RequerimientoBean> lista = null;
		try {			
			RequerimientoBean requerimientoBean = new RequerimientoBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(requerimientoBean, request.getParameterMap());			
			lista = programacionService.listarRequerimiento(requerimientoBean); 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	@RequestMapping(value = "/exportarExcel/{codAnio}/{codMes}/{codFenomeno}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("codAnio") String codAnio, 
								@PathVariable("codMes") String codMes, 
								@PathVariable("codFenomeno") Integer codFenomeno, 
								HttpServletResponse response) {
	    try {
	    	RequerimientoBean requerimientoBean = new RequerimientoBean();
	    	requerimientoBean.setCodAnio(verificaParametro(codAnio));
	    	requerimientoBean.setCodMes(verificaParametro(codMes));
	    	
	    	requerimientoBean.setIdFenomeno(codFenomeno);
			List<RequerimientoBean> lista = programacionService.listarRequerimiento(requerimientoBean);
	    	
			String file_name = "Reporte_Requerimiento";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteRequerimiento reporte = new ReporteRequerimiento();
		    HSSFWorkbook wb = reporte.generaReporteExcelRequerimiento(lista);
			
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
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/mantenimientoRequerimiento/{codigo}", method = RequestMethod.GET)
    public String mantenimientoRequerimiento(@PathVariable("codigo") Integer codigo, Model model) {
        try {
        	RequerimientoBean requerimiento = new RequerimientoBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	
        	
        	model.addAttribute("lista_region", generalService.listarRegion(new ItemBean()));
//        	model.addAttribute("lista_requiere", generalService.listarRequiere(new ItemBean()));
        	model.addAttribute("lista_fenomeno", generalService.listarFenomeno(new ItemBean()));
        	
        	if (!isNullInteger(codigo)) {//para nuevo codigo=0
        		
        		requerimiento = programacionService.obtenerRequerimiento(codigo);
//        		
//        		model.addAttribute("lista_chofer", generalService.listarChofer(new ItemBean(requerimiento.getIdEmpresaTransporte())));
        		
        	} else {//para editar codigo=1

        		StringBuilder correlativo = new StringBuilder();
        		correlativo.append(usuarioBean.getCodigoDdi());
        		correlativo.append(Constantes.SEPARADOR);
        		correlativo.append(usuarioBean.getIdDdi());
        		correlativo.append(Constantes.SEPARADOR);
        		
        		RequerimientoBean parametros = new RequerimientoBean();
        		String anioActual = generalService.obtenerAnioActual();
        		parametros.setCodAnio(anioActual);
        		parametros.setIdDdi(usuarioBean.getIdDdi());   
        		parametros.setCodDdi(usuarioBean.getCodigoDdi()); 
        		RequerimientoBean respuestaCorrelativo = programacionService.obtenerCorrelativoRequerimiento(parametros);
      
//        		correlativo.append(respuestaCorrelativo.getNumRequerimiento());
        		
        		requerimiento.setCodAnio(anioActual);
        		requerimiento.setNumRequerimiento(respuestaCorrelativo.getNumRequerimiento());        		
        		Date fecha_hora = Calendar.getInstance().getTime();
    			requerimiento.setFechaRequerimiento(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));
    			requerimiento.setCodMes(DateUtil.getMesActual()+""); 
    			requerimiento.setCodDdi(usuarioBean.getCodigoDdi());
    			requerimiento.setFkIdeDdi(usuarioBean.getIdDdi()); 
    			requerimiento.setIdDdi(usuarioBean.getIdDdi()); 
    			requerimiento.setCodRequerimiento(respuestaCorrelativo.getCodRequerimiento());
//        		RequerimientoBean parametroAlmacenActivo = new RequerimientoBean();
//        		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
//        		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
//        		List<RequerimientoBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
//        		if (!isEmpty(listaAlmacenActivo)) {
//        			requerimiento.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
//        			requerimiento.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
//        			requerimiento.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
//        			requerimiento.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
//        			requerimiento.setCodigoMes(listaAlmacenActivo.get(0).getCodigoMes());
//        		}
//        		
//            	requerimiento.setIdDdi(usuarioBean.getIdDdi());
//        		requerimiento.setCodigoDdi(usuarioBean.getCodigoDdi());
//        		requerimiento.setNombreDdi(usuarioBean.getNombreDdi());
        	}
        	
//        	model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean()));
//        	
        	model.addAttribute("requerimiento", getParserObject(requerimiento));
//
//        	model.addAttribute("lista_estado", generalService.listarEstado(new ItemBean(null, Constantes.FOUR_INT)));
//        	
//        	model.addAttribute("lista_orden_compra", logisticaService.listarOrdenCompra());
//        	
//        	model.addAttribute("lista_tipo_control", generalService.listarTipoControlCalidad(new ItemBean()));
//        	
//        	model.addAttribute("lista_personal", generalService.listarPersonal(new ItemBean(usuarioBean.getIdDdi())));
//        	
//        	model.addAttribute("lista_proveedor", generalService.listarProveedor(new ItemBean()));
//        	
//        	ItemBean parametroEmpresaTransporte = new ItemBean();
//        	parametroEmpresaTransporte.setIcodigo(usuarioBean.getIdDdi());
//        	parametroEmpresaTransporte.setIcodigoParam2(Constantes.ONE_INT);
//        	model.addAttribute("lista_empresa_transporte", generalService.listarEmpresaTransporte(parametroEmpresaTransporte));
//        	
//        	model.addAttribute("lista_producto", generalService.listarCatologoProductos(new ProductoBean(null, Constantes.FIVE_INT)));
//        	
//        	model.addAttribute("lista_tipo_documento", generalService.listarTipoDocumento(new ItemBean()));
//     
        	model.addAttribute("lista_anio", generalService.listarAnios());
        	model.addAttribute("lista_mes", generalService.listarMeses(new ItemBean()));
        	model.addAttribute("lista_departamento", generalService.listarDepartamentos(new UbigeoBean()));
        	
//        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_requerimiento";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/grabarRequerimiento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarControlCalidad(HttpServletRequest request, HttpServletResponse response) {
		RequerimientoBean requerimiento = null;
		try {			
			RequerimientoBean requerimientoBean = new RequerimientoBean();

	        // Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(requerimientoBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	requerimientoBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			if (!isNullInteger(requerimientoBean.getIdRequerimiento())) {				
				requerimiento = programacionService.actualizarRegistroRequerimiento(requerimientoBean);				
			} else {			
				requerimiento = programacionService.insertarRegistroRequerimiento(requerimientoBean);			
			}
			requerimiento.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return requerimiento;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listarProvincia", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProvincia(HttpServletRequest request, HttpServletResponse response) {
		List<UbigeoBean> lista = null;
		try {			
			UbigeoBean ubigeoBean = new UbigeoBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(ubigeoBean, request.getParameterMap());
			lista = generalService.listarProvincia(ubigeoBean);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listarEmergenciasActivas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarEmergenciasActivas(HttpServletRequest request, HttpServletResponse response) {
		List<EmergenciaBean> lista = null;
		try {			
			RequerimientoBean requerimientoBean = new RequerimientoBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(requerimientoBean, request.getParameterMap());			
			lista = programacionService.listarEmergenciasActivas(requerimientoBean); 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param codAnio
	 * @param codMes
	 * @param codDpto
	 * @param codProvincia
	 * @param idFenomeno
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/exportarExcelEmergenciasActivas/{codAnio}/{codMes}/{codDpto}/{codProvincia}/{idFenomeno}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcelEmergenciasActivas(  @PathVariable("codAnio") String codAnio, 
													@PathVariable("codMes") String codMes, 
													@PathVariable("codDpto") String codDpto, 
													@PathVariable("codProvincia") String codProvincia, 
													@PathVariable("idFenomeno") Integer idFenomeno, 
													HttpServletResponse response) {
	    try {
	    	RequerimientoBean requerimientoBean = new RequerimientoBean();
	    	requerimientoBean.setCodAnio(verificaParametro(codAnio));
	    	requerimientoBean.setCodMes(verificaParametro(codMes));
	    	requerimientoBean.setCodDpto(verificaParametro(codDpto));
	    	requerimientoBean.setCodProvincia(verificaParametro(codProvincia));
	    	requerimientoBean.setIdFenomeno(idFenomeno);
			List<EmergenciaBean> lista = programacionService.listarEmergenciasActivas(requerimientoBean);
	    	
			String file_name = "EmergenciasporDistrito";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteEmergencia reporte = new ReporteEmergencia();
		    HSSFWorkbook wb = reporte.generaReporteExcelEmergencia(lista);
			
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
	 * @param codigoAnio
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pasarDistritos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object pasarDistritos(HttpServletRequest request, HttpServletResponse response) {
		EmergenciaBean emergencia = null;
		try {			
			EmergenciaBean emergenciaBean = new EmergenciaBean();

			BeanUtils.populate(emergenciaBean, request.getParameterMap());			
        	emergencia = programacionService.pasarDistritos(emergenciaBean); 

			emergencia.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return emergencia;
    }
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listarUbigeoInei", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarUbigeoInei(HttpServletRequest request, HttpServletResponse response) {
		List<UbigeoIneiBean> lista = null;
		try {			
			UbigeoIneiBean ubigeoBean = new UbigeoIneiBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(ubigeoBean, request.getParameterMap());			
			lista = programacionService.listarUbigeoInei(ubigeoBean); 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pasarDistritosUbigeo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object pasarDistritosUbigeo(HttpServletRequest request, HttpServletResponse response) {
		EmergenciaBean ubigeo = null;
		try {			
			EmergenciaBean ubigeoBean = new EmergenciaBean();

			BeanUtils.populate(ubigeoBean, request.getParameterMap());			
			ubigeo = programacionService.pasarDistritosUbigeo(ubigeoBean); 

			ubigeo.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return ubigeo;
    }
	
	
}

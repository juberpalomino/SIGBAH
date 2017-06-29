package pe.com.sigbah.web.controller.gestion_almacenes;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoControlCalidadBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ProductoControlCalidadBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.report.gestion_almacenes.Reporteador;
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
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	if (!isNullInteger(codigo)) {
        		
        		controlCalidad = logisticaService.obtenerRegistroControlCalidad(codigo);
        		
        		model.addAttribute("lista_chofer", generalService.listarChofer(new ItemBean(controlCalidad.getIdEmpresaTransporte())));
        		
        		List<ProductoControlCalidadBean> listaAlimentarios = new ArrayList<ProductoControlCalidadBean>(); // Cambiar

            	model.addAttribute("listaAlimentarios", getParserObject(listaAlimentarios));
        		model.addAttribute("listaNoAlimentarios", getParserObject(listaAlimentarios));
        		
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
        		ControlCalidadBean respuestaCorrelativo = logisticaService.obtenerCorrelativo(parametros);
      
        		correlativo.append(respuestaCorrelativo.getNroControlCalidad());
        		
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

        	model.addAttribute("lista_estado", generalService.listarEstado(new ItemBean(null, Constantes.FOUR_INT)));
        	
        	model.addAttribute("lista_orden_compra", logisticaService.listarOrdenCompra());
        	
        	model.addAttribute("lista_tipo_control", generalService.listarTipoControlCalidad(new ItemBean()));
        	
        	model.addAttribute("lista_personal", generalService.listarPersonal(new ItemBean(usuarioBean.getIdDdi())));
        	
        	model.addAttribute("lista_proveedor", generalService.listarProveedor(new ItemBean()));
        	
        	ItemBean parametroEmpresaTransporte = new ItemBean();
        	parametroEmpresaTransporte.setIcodigo(usuarioBean.getIdDdi());
        	parametroEmpresaTransporte.setIcodigoParam2(Constantes.ONE_INT);
        	model.addAttribute("lista_empresa_transporte", generalService.listarEmpresaTransporte(parametroEmpresaTransporte));
        	
        	model.addAttribute("lista_producto", generalService.listarCatologoProductos(new ItemBean(Constantes.FIVE_INT)));
        	
        	model.addAttribute("lista_tipo_documento", generalService.listarTipoDocumento(new ItemBean()));
     
        	
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
	@RequestMapping(value = "/grabarControlCalidad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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
				controlCalidad.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));				
			} else {			
				controlCalidad = logisticaService.insertarRegistroControlCalidad(controlCalidadBean);			
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return controlCalidad;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProductoControlCalidad", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProductoControlCalidad(HttpServletRequest request, HttpServletResponse response) {
		List<ProductoControlCalidadBean> lista = null;
		try {			
			ProductoControlCalidadBean producto = new ProductoControlCalidadBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(producto, request.getParameterMap());			
			lista = logisticaService.listarProductoControlCalidad(producto);
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
	@RequestMapping(value = "/grabarProductoControlCalidad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarProductoControlCalidad(HttpServletRequest request, HttpServletResponse response) {
		ProductoControlCalidadBean producto = null;
		try {			
			ProductoControlCalidadBean productoControlCalidadBean = new ProductoControlCalidadBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(productoControlCalidadBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	productoControlCalidadBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			producto = logisticaService.grabarProductoControlCalidad(productoControlCalidadBean);
			
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
	@RequestMapping(value = "/eliminarProductoControlCalidad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarProductoControlCalidad(HttpServletRequest request, HttpServletResponse response) {
		ProductoControlCalidadBean producto = null;
		try {			
			String[] arrIdDetalleControlCalidad = request.getParameter("arrIdDetalleControlCalidad").split(Constantes.UNDERLINE);
			for (String codigo : arrIdDetalleControlCalidad) {				
				ProductoControlCalidadBean productoControlCalidadBean = new ProductoControlCalidadBean(getInteger(codigo));

				// Retorno los datos de session
	        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
	        	
	        	productoControlCalidadBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
				producto = logisticaService.eliminarProductoControlCalidad(productoControlCalidadBean);				
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
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/grabarDocumentoControlCalidad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarDocumentoControlCalidad(HttpServletRequest request, HttpServletResponse response) {
		DocumentoControlCalidadBean documento = null;
		try {			
			DocumentoControlCalidadBean documentoControlCalidadBean = new DocumentoControlCalidadBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(documentoControlCalidadBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	documentoControlCalidadBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	documento = logisticaService.grabarDocumentoControlCalidad(documentoControlCalidadBean);
			
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
	@RequestMapping(value = "/eliminarDocumentoControlCalidad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarDocumentoControlCalidad(HttpServletRequest request, HttpServletResponse response) {
		DocumentoControlCalidadBean documento = null;
		try {			
			String[] arrIdDetalleControlCalidad = request.getParameter("arrIdDocumentoControlCalidad").split(Constantes.UNDERLINE);
			for (String codigo : arrIdDetalleControlCalidad) {				
				DocumentoControlCalidadBean documentoControlCalidadBean = new DocumentoControlCalidadBean(getInteger(codigo));

				// Retorno los datos de session
	        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
	        	
	        	documentoControlCalidadBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
	        	documento = logisticaService.eliminarDocumentoControlCalidad(documentoControlCalidadBean);				
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
	 * @param codigoDdi 
	 * @param codigoAlmacen
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarExcel/{codigoAnio}/{codigoDdi}/{codigoAlmacen}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("codigoAnio") String codigoAnio, @PathVariable("codigoDdi") String codigoDdi, 
								@PathVariable("codigoAlmacen") String codigoAlmacen, HttpServletResponse response) {
	    try {
	    	ControlCalidadBean controlCalidadBean = new ControlCalidadBean();
			controlCalidadBean.setCodigoAnio(codigoAnio);
			controlCalidadBean.setCodigoDdi(codigoDdi);
			controlCalidadBean.setCodigoAlmacen(codigoAlmacen);
			List<ControlCalidadBean> lista = logisticaService.listarControlCalidad(controlCalidadBean);
	    	
			String file_name = "Reporte_Control_Calidad";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			response.resetBuffer();
            response.setContentType(Constantes.MIME_APPLICATION_XLS);
            response.setHeader("Content-Disposition", "attachment; filename=" + file_name);            
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "private");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setDateHeader("Expires", 1);
            
		    Reporteador rep = new Reporteador();
		    HSSFWorkbook wb = rep.generaReporteExcelControlCalidad(lista);
	    	
		    // Captured backflow
	    	OutputStream out = response.getOutputStream();
	    	wb.write(out); // We write in that flow
	    	out.flush(); // We emptied the flow
	    	out.close(); // We close the flow
	    	
	    	return Constantes.ACCION_CORRECTA_JSON;	    	
	    } catch (Exception e) {
	    	LOGGER.error(e.getMessage(), e);
	    	return Constantes.ACCION_FALLIDA_JSON;
	    } 
	}
	
}

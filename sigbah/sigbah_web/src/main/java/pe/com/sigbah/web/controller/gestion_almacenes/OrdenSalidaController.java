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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoSalidaBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.OrdenSalidaBean;
import pe.com.sigbah.common.bean.ProductoSalidaBean;
import pe.com.sigbah.common.bean.ProyectoManifiestoBean;
import pe.com.sigbah.common.bean.UbigeoBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.LogisticaService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.gestion_almacenes.ReporteOrdenSalida;

/**
 * @className: OrdenSalidaController.java
 * @description: 
 * @date: 17 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Controller
@RequestMapping("/gestion-almacenes/orden-salida")
public class OrdenSalidaController extends BaseController {

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
        	        	
        	model.addAttribute("lista_tipo_movimiento", generalService.listarTipoMovimiento(new ItemBean(Constantes.TWO_INT, Constantes.TWO_INT)));
        	
        	OrdenSalidaBean ordenSalida = new OrdenSalidaBean();
        	
        	ControlCalidadBean parametroAlmacenActivo = new ControlCalidadBean();
    		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
    		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
    		List<ControlCalidadBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
    		if (!isEmpty(listaAlmacenActivo)) {
    			ordenSalida.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
    			ordenSalida.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
    			ordenSalida.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
    			ordenSalida.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
    			ordenSalida.setCodigoMes(listaAlmacenActivo.get(0).getCodigoMes());
    		}
    		
    		model.addAttribute("ordenSalida", getParserObject(ordenSalida));
        	
        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar_orden_salida";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarOrdenSalida", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarOrdenSalida(HttpServletRequest request, HttpServletResponse response) {
		List<OrdenSalidaBean> lista = null;
		try {			
			OrdenSalidaBean ordenSalidaBean = new OrdenSalidaBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(ordenSalidaBean, request.getParameterMap());
			
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
			
        	ordenSalidaBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        	ordenSalidaBean.setIdDdi(usuarioBean.getIdDdi());
        	ordenSalidaBean.setCodigoDdi(usuarioBean.getCodigoDdi());
			
			lista = logisticaService.listarOrdenSalida(ordenSalidaBean);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	/**
	 * @param codigo 
	 * @param anio 
	 * @param model
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping(value = "/mantenimientoOrdenSalida/{codigo}/{anio}", method = RequestMethod.GET)
    public String mantenimientoOrdenSalida(@PathVariable("codigo") Integer codigo, @PathVariable("anio") String anio, Model model) {
        try {
        	OrdenSalidaBean ordenSalida = new OrdenSalidaBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	String anioActual = generalService.obtenerAnioActual();
        	
        	if (!isNullInteger(codigo)) {
        		
        		ordenSalida = logisticaService.obtenerRegistroOrdenSalida(codigo, anio);
        		
        		if (!isNullInteger(ordenSalida.getIdMedioTransporte())) {
        			ItemBean item = new ItemBean();
        			item.setIcodigo(usuarioBean.getIdDdi());
        			item.setIcodigoParam2(ordenSalida.getIdMedioTransporte());
        			model.addAttribute("lista_empresa_transporte", generalService.listarEmpresaTransporte(item));
        		}        		
        		if (!isNullInteger(ordenSalida.getIdEmpresaTransporte())) {
        			model.addAttribute("lista_chofer", generalService.listarChofer(new ItemBean(ordenSalida.getIdEmpresaTransporte())));
        		}
        	} else {

        		StringBuilder correlativo = new StringBuilder();
        		correlativo.append(usuarioBean.getCodigoDdi());
        		correlativo.append(Constantes.SEPARADOR);
        		correlativo.append(usuarioBean.getCodigoAlmacen());
        		correlativo.append(Constantes.SEPARADOR);
        		
        		OrdenSalidaBean parametros = new OrdenSalidaBean();        		
        		parametros.setCodigoAnio(anioActual);
        		parametros.setCodigoDdi(usuarioBean.getCodigoDdi());
        		parametros.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametros.setCodigoAlmacen(usuarioBean.getCodigoAlmacen());
        		parametros.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        		OrdenSalidaBean respuestaCorrelativo = logisticaService.obtenerCorrelativoOrdenSalida(parametros);
      
        		correlativo.append(respuestaCorrelativo.getNroOrdenSalida());
        		
        		ordenSalida.setNroOrdenSalida(correlativo.toString());        		
        		
        		ControlCalidadBean parametroAlmacenActivo = new ControlCalidadBean();
        		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
        		List<ControlCalidadBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
        		if (!isEmpty(listaAlmacenActivo)) {
        			ordenSalida.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
        			ordenSalida.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
        			ordenSalida.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
        			ordenSalida.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
        			ordenSalida.setCodigoMes(listaAlmacenActivo.get(0).getCodigoMes());
        		}
        		
        		ordenSalida.setIdDdi(usuarioBean.getIdDdi());
        		ordenSalida.setCodigoDdi(usuarioBean.getCodigoDdi());
        		ordenSalida.setNombreDdi(usuarioBean.getNombreDdi());
        	}
        	
        	if (!Utils.isNullInteger(usuarioBean.getIdDdi())) {
        		model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
        	}
        	
        	model.addAttribute("ordenSalida", getParserObject(ordenSalida));
        	
        	model.addAttribute("lista_tipo_movimiento", generalService.listarTipoMovimiento(new ItemBean(Constantes.TWO_INT, Constantes.TWO_INT)));

        	model.addAttribute("lista_estado", generalService.listarEstado(new ItemBean(null, Constantes.FOUR_INT)));
        	
        	ProyectoManifiestoBean proyectoManifiestoBean = new ProyectoManifiestoBean();
        	proyectoManifiestoBean.setCodigoAnio(anioActual);
        	proyectoManifiestoBean.setIdDdi(usuarioBean.getIdDdi());
        	proyectoManifiestoBean.setIdAlmacen(usuarioBean.getIdAlmacen());
        	model.addAttribute("lista_proyecto_manifiesto", logisticaService.listarManifiesto(proyectoManifiestoBean));
        	
        	model.addAttribute("lista_personal", generalService.listarPersonal(new ItemBean(usuarioBean.getIdDdi())));
        	
        	model.addAttribute("lista_ddi", generalService.listarDdi(new ItemBean()));
        	
        	model.addAttribute("lista_medio_transporte", generalService.listarMedioTransporte(new ItemBean()));
        	
        	model.addAttribute("lista_region", generalService.listarRegion(new ItemBean()));
        	
        	model.addAttribute("lista_departamento", generalService.listarDepartamentos(new UbigeoBean()));
        	
        	model.addAttribute("lista_tipo_documento", generalService.listarTipoDocumento(new ItemBean()));
     
        	model.addAttribute("lista_categoria", generalService.listarCategoria(new ItemBean(Constantes.THREE_INT)));
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_orden_salida";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarAlmacenDestino", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarAlmacenDestino(HttpServletRequest request, HttpServletResponse response) {
		List<ItemBean> lista = null;
		try {			
			ItemBean item = new ItemBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(item, request.getParameterMap());
			lista = generalService.listarAlmacen(item);
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
	@RequestMapping(value = "/listarResponsableRecepcion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarResponsableRecepcion(HttpServletRequest request, HttpServletResponse response) {
		List<ItemBean> lista = null;
		try {			
			ItemBean item = new ItemBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(item, request.getParameterMap());
			lista = generalService.listarPersonal(item);
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
	@RequestMapping(value = "/listarAlmacenExtRegion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarAlmacenExtRegion(HttpServletRequest request, HttpServletResponse response) {
		List<ItemBean> lista = null;
		try {			
			ItemBean item = new ItemBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(item, request.getParameterMap());
			lista = generalService.listarAlmacenExternoRegion(item);
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
	@RequestMapping(value = "/listarPersonalExtRegion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarPersonalExtRegion(HttpServletRequest request, HttpServletResponse response) {
		List<ItemBean> lista = null;
		try {			
			ItemBean item = new ItemBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(item, request.getParameterMap());
			lista = generalService.listarPersonalExternoRegion(item);
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
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarDistrito", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarDistrito(HttpServletRequest request, HttpServletResponse response) {
		List<UbigeoBean> lista = null;
		try {			
			UbigeoBean ubigeoBean = new UbigeoBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(ubigeoBean, request.getParameterMap());
			lista = generalService.listarDistrito(ubigeoBean);
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
	@RequestMapping(value = "/listarAlmacenExtLocal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarAlmacenExtLocal(HttpServletRequest request, HttpServletResponse response) {
		List<ItemBean> lista = null;
		try {			
			ItemBean item = new ItemBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(item, request.getParameterMap());
			lista = generalService.listarAlmacenExternoLocal(item);
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
	@RequestMapping(value = "/listarPersonalExtLocal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarPersonalExtLocal(HttpServletRequest request, HttpServletResponse response) {
		List<ItemBean> lista = null;
		try {			
			ItemBean item = new ItemBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(item, request.getParameterMap());
			lista = generalService.listarPersonalExternoLocal(item);
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
	@RequestMapping(value = "/grabarOrdenSalida", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarOrdenSalida(HttpServletRequest request, HttpServletResponse response) {
		OrdenSalidaBean ordenSalida = null;
		try {			
			OrdenSalidaBean ordenSalidaBean = new OrdenSalidaBean();
			
			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(ordenSalidaBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
			ordenSalidaBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        	ordenSalidaBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	ordenSalida = logisticaService.grabarOrdenSalida(ordenSalidaBean);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return ordenSalida;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProductoOrdenSalida", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProductoOrdenSalida(HttpServletRequest request, HttpServletResponse response) {
		List<ProductoSalidaBean> lista = null;
		try {			
			ProductoSalidaBean producto = new ProductoSalidaBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(producto, request.getParameterMap());			
			lista = logisticaService.listarProductoSalida(producto);
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
	@RequestMapping(value = "/grabarProductoOrdenSalida", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarProductoOrdenSalida(HttpServletRequest request, HttpServletResponse response) {
		ProductoSalidaBean producto = null;
		try {			
			ProductoSalidaBean productoSalidaBean = new ProductoSalidaBean();

			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);			
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Convierte los vacios en nulos en los decimales
			BigDecimalConverter con_decimal = new BigDecimalConverter(null);
			beanUtilsBean.getConvertUtils().register(con_decimal, BigDecimal.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(productoSalidaBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	productoSalidaBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			producto = logisticaService.grabarProductoSalida(productoSalidaBean);
			
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
	@RequestMapping(value = "/eliminarProductoOrdenSalida", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarProductoOrdenSalida(HttpServletRequest request, HttpServletResponse response) {
		ProductoSalidaBean producto = null;
		try {			
			String[] arrIdDetalleControlCalidad = request.getParameter("arrIdDetalleSalida").split(Constantes.UNDERLINE);
			for (String codigo : arrIdDetalleControlCalidad) {				
				ProductoSalidaBean productoControlCalidadBean = new ProductoSalidaBean(getInteger(codigo));

				// Retorno los datos de session
	        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
	        	
	        	productoControlCalidadBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
				producto = logisticaService.eliminarProductoSalida(productoControlCalidadBean);				
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
	@RequestMapping(value = "/listarDocumentoOrdenSalida", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarDocumentoOrdenSalida(HttpServletRequest request, HttpServletResponse response) {
		List<DocumentoSalidaBean> lista = null;
		try {			
			DocumentoSalidaBean documento = new DocumentoSalidaBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(documento, request.getParameterMap());
			documento.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
			lista = logisticaService.listarDocumentoSalida(documento);
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
	@RequestMapping(value = "/grabarDocumentoOrdenSalida", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarDocumentoOrdenSalida(HttpServletRequest request, HttpServletResponse response) {
		DocumentoSalidaBean documento = null;
		try {			
			DocumentoSalidaBean documentoSalidaBean = new DocumentoSalidaBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(documentoSalidaBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	documentoSalidaBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
        	documento = logisticaService.grabarDocumentoSalida(documentoSalidaBean);
			
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
	@RequestMapping(value = "/eliminarDocumentoOrdenSalida", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarDocumentoOrdenSalida(HttpServletRequest request, HttpServletResponse response) {
		DocumentoSalidaBean documento = null;
		try {			
			String[] arrIdDocumentoSalida = request.getParameter("arrIdDocumentoSalida").split(Constantes.UNDERLINE);
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
			for (String codigo : arrIdDocumentoSalida) {				
				DocumentoSalidaBean documentoSalidaBean = new DocumentoSalidaBean(getInteger(codigo));

	        	documentoSalidaBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
	        	documento = logisticaService.eliminarDocumentoSalida(documentoSalidaBean);				
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
	 * @param idMovimiento 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarExcel/{codigoAnio}/{codigoMes}/{idAlmacen}/{idMovimiento}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("codigoAnio") String codigoAnio, 
								@PathVariable("codigoMes") String codigoMes, 
								@PathVariable("idAlmacen") Integer idAlmacen,
								@PathVariable("idMovimiento") Integer idMovimiento, 
								HttpServletResponse response) {
	    try {
	    	OrdenSalidaBean ordenSalidaBean = new OrdenSalidaBean();
	    	ordenSalidaBean.setCodigoAnio(verificaParametro(codigoAnio));
	    	ordenSalidaBean.setCodigoMes(verificaParametro(codigoMes));
	    	ordenSalidaBean.setIdAlmacen(idAlmacen);
	    	ordenSalidaBean.setIdMovimiento(idMovimiento);
	    	
	    	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
			
        	ordenSalidaBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        	ordenSalidaBean.setIdDdi(usuarioBean.getIdDdi());
        	ordenSalidaBean.setCodigoDdi(usuarioBean.getCodigoDdi());	    	
	    	
			List<OrdenSalidaBean> lista = logisticaService.listarOrdenSalida(ordenSalidaBean);
	    	
			String file_name = "OrdenSalida";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteOrdenSalida reporte = new ReporteOrdenSalida();
		    HSSFWorkbook wb = reporte.generaReporteExcelOrdenSalida(lista);
			
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
	 * @param anio 
	 * @param request 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarPdf/{codigo}/{anio}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarPdf(@PathVariable("codigo") Integer codigo, @PathVariable("anio") String anio, HttpServletRequest request, HttpServletResponse response) {
	    try {
	    	
	    	OrdenSalidaBean ordenSalida = logisticaService.obtenerRegistroOrdenSalida(codigo, anio);
	    	ProductoSalidaBean producto = new ProductoSalidaBean();
	    	producto.setIdSalida(codigo);
	    	List<ProductoSalidaBean> listaProducto = logisticaService.listarProductoSalida(producto);
	    	
	    	DocumentoSalidaBean documento = new DocumentoSalidaBean();
	    	documento.setIdSalida(codigo);
	    	documento.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
	    	List<DocumentoSalidaBean> listaDocumento = logisticaService.listarDocumentoSalida(documento);	    	

	    	StringBuilder file_path = new StringBuilder();
	    	file_path.append(getPath(request));
	    	file_path.append(File.separator);
	    	file_path.append(Constantes.UPLOAD_PATH_FILE_TEMP);
	    	file_path.append(File.separator);
	    	file_path.append(Calendar.getInstance().getTime().getTime());
	    	file_path.append(Constantes.EXTENSION_FORMATO_PDF);
	    	
	    	String file_name = "Orden_Salida";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_PDF);
			
			ReporteOrdenSalida reporte = new ReporteOrdenSalida();
			reporte.generaPDFReporteSalidas(file_path.toString(), ordenSalida, listaProducto, listaDocumento);
			
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

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

import pe.com.sigbah.common.bean.CartillaInventarioBean;
import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.EstadoCartillaInventarioBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ProductoCartillaInventarioBean;
import pe.com.sigbah.common.bean.StockAlmacenProductoBean;
import pe.com.sigbah.common.bean.StockAlmacenProductoLoteBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.LogisticaService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.gestion_almacenes.ReporteCartillaInventario;

/**
 * @className: CartillaInventarioController.java
 * @description: 
 * @date: 17 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Controller
@RequestMapping("/gestion-almacenes/cartilla-inventario")
public class CartillaInventarioController extends BaseController {

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
        	
        	model.addAttribute("lista_almacen", generalService.listarAlmacen(new ItemBean(usuarioBean.getIdDdi())));
        	
        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "listar_cartilla_inventario";
    }
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarCartillaInventario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarCartillaInventario(HttpServletRequest request, HttpServletResponse response) {
		List<CartillaInventarioBean> lista = null;
		try {			
			CartillaInventarioBean cartillaInventarioBean = new CartillaInventarioBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(cartillaInventarioBean, request.getParameterMap());			
			lista = logisticaService.listarCartillaInventario(cartillaInventarioBean);
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
	@RequestMapping(value = "/mantenimientoCartillaInventario/{codigo}", method = RequestMethod.GET)
    public String mantenimientoCartillaInventario(@PathVariable("codigo") Integer codigo, Model model) {
        try {
        	CartillaInventarioBean cartillaInventario = new CartillaInventarioBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	if (!isNullInteger(codigo)) {
        		
        		cartillaInventario = logisticaService.obtenerRegistroCartillaInventario(codigo);

        	} else {

        		StringBuilder correlativo = new StringBuilder();
        		correlativo.append(usuarioBean.getCodigoDdi());
        		correlativo.append(Constantes.SEPARADOR);
        		correlativo.append(usuarioBean.getCodigoAlmacen());
        		correlativo.append(Constantes.SEPARADOR);
        		
        		CartillaInventarioBean parametros = new CartillaInventarioBean();
        		String anioActual = generalService.obtenerAnioActual();
        		parametros.setCodigoAnio(anioActual);
        		parametros.setCodigoDdi(usuarioBean.getCodigoDdi());
        		parametros.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametros.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        		CartillaInventarioBean respuestaCorrelativo = logisticaService.obtenerCorrelativoCartillaInventario(parametros);
      
        		correlativo.append(respuestaCorrelativo.getNroCartilla());
        		
        		cartillaInventario.setNroCartilla(correlativo.toString());        		
        		
        		ControlCalidadBean parametroAlmacenActivo = new ControlCalidadBean();
        		parametroAlmacenActivo.setIdAlmacen(usuarioBean.getIdAlmacen());
        		parametroAlmacenActivo.setTipo(Constantes.CODIGO_TIPO_ALMACEN);
        		List<ControlCalidadBean> listaAlmacenActivo = logisticaService.listarAlmacenActivo(parametroAlmacenActivo);
        		if (!isEmpty(listaAlmacenActivo)) {
        			cartillaInventario.setCodigoAnio(listaAlmacenActivo.get(0).getCodigoAnio());
        			cartillaInventario.setIdAlmacen(listaAlmacenActivo.get(0).getIdAlmacen());
        			cartillaInventario.setCodigoAlmacen(listaAlmacenActivo.get(0).getCodigoAlmacen());
        			cartillaInventario.setNombreAlmacen(listaAlmacenActivo.get(0).getNombreAlmacen());
        		}
        		
            	cartillaInventario.setIdDdi(usuarioBean.getIdDdi());
        		cartillaInventario.setCodigoDdi(usuarioBean.getCodigoDdi());
        		cartillaInventario.setNombreDdi(usuarioBean.getNombreDdi());
        		cartillaInventario.setIdEstado(Constantes.COD_GENERADO);
        		cartillaInventario.setNombreEstado(Constantes.DES_GENERADO);
        	}
        	
        	model.addAttribute("cartillaInventario", getParserObject(cartillaInventario));

        	model.addAttribute("lista_personal", generalService.listarPersonal(new ItemBean(usuarioBean.getIdDdi())));
        	
        	StockAlmacenProductoBean stockAlmacenProductoBean = new StockAlmacenProductoBean();
        	stockAlmacenProductoBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        	stockAlmacenProductoBean.setIdDdi(usuarioBean.getIdDdi());
        	stockAlmacenProductoBean.setIdAlmacen(usuarioBean.getIdAlmacen());
        	model.addAttribute("lista_producto", logisticaService.listarStockAlmacenProducto(stockAlmacenProductoBean));

        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_cartilla_inventario";
    }

	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/grabarCartillaInventario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarCartillaInventario(HttpServletRequest request, HttpServletResponse response) {
		CartillaInventarioBean cartillaInventario = null;
		try {			
			CartillaInventarioBean cartillaInventarioBean = new CartillaInventarioBean();
			
			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(cartillaInventarioBean, request.getParameterMap());

			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	cartillaInventarioBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			cartillaInventario = logisticaService.grabarCartillaInventario(cartillaInventarioBean);
			cartillaInventario.setMensajeRespuesta(getMensaje(messageSource, "msg.info.grabadoOk"));
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return cartillaInventario;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarProductoCartillaInventario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarProductoCartillaInventario(HttpServletRequest request, HttpServletResponse response) {
		List<ProductoCartillaInventarioBean> lista = null;
		try {			
			ProductoCartillaInventarioBean producto = new ProductoCartillaInventarioBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(producto, request.getParameterMap());			
			lista = logisticaService.listarProductoCartillaInventario(producto);
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
	@RequestMapping(value = "/grabarProductoCartillaInventario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarProductoCartillaInventario(HttpServletRequest request, HttpServletResponse response) {
		ProductoCartillaInventarioBean producto = null;
		try {			
			ProductoCartillaInventarioBean productoCartillaInventarioBean = new ProductoCartillaInventarioBean();

			// Convierte los vacios en nulos en los enteros
			IntegerConverter con_integer = new IntegerConverter(null);			
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
			beanUtilsBean.getConvertUtils().register(con_integer, Integer.class);
			// Convierte los vacios en nulos en los decimales
			BigDecimalConverter con_decimal = new BigDecimalConverter(null);
			beanUtilsBean.getConvertUtils().register(con_decimal, BigDecimal.class);
			// Copia los parametros del cliente al objeto
			beanUtilsBean.populate(productoCartillaInventarioBean, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	productoCartillaInventarioBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
        	productoCartillaInventarioBean.setUsuarioRegistro(usuarioBean.getUsuario());
			
			producto = logisticaService.grabarProductoCartillaInventario(productoCartillaInventarioBean);
			
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
	@RequestMapping(value = "/eliminarProductoCartillaInventario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object eliminarProductoCartillaInventario(HttpServletRequest request, HttpServletResponse response) {
		ProductoCartillaInventarioBean producto = null;
		try {			
			String[] arrIdDetalleCartillaInventario = request.getParameter("arrIdDetalleCartillaInventario").split(Constantes.UNDERLINE);
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
			for (String codigo : arrIdDetalleCartillaInventario) {				
				ProductoCartillaInventarioBean productoCartillaInventarioBean = new ProductoCartillaInventarioBean(getInteger(codigo));

	        	productoCartillaInventarioBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
				producto = logisticaService.eliminarProductoCartillaInventario(productoCartillaInventarioBean);				
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
	@RequestMapping(value = "/procesarProductosCartillaInventario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object procesarProductosCartillaInventario(HttpServletRequest request, HttpServletResponse response) {
		ProductoCartillaInventarioBean producto = null;
		try {
			ProductoCartillaInventarioBean productoCartillaInventarioBean = new ProductoCartillaInventarioBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(productoCartillaInventarioBean, request.getParameterMap());	
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);

        	productoCartillaInventarioBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
	        productoCartillaInventarioBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
			producto = logisticaService.procesarProductosCartillaInventario(productoCartillaInventarioBean);				

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
	@RequestMapping(value = "/listarStockAlmacenProductoLote", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarStockAlmacenProductoLote(HttpServletRequest request, HttpServletResponse response) {
		List<StockAlmacenProductoLoteBean> lista = null;
		try {			
			StockAlmacenProductoLoteBean stockAlmacenProductoLote = new StockAlmacenProductoLoteBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(stockAlmacenProductoLote, request.getParameterMap());
			stockAlmacenProductoLote.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
			lista = logisticaService.listarStockAlmacenProductoLote(stockAlmacenProductoLote);
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
	@RequestMapping(value = "/actualizarAjusteProductoCartillaInventario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object actualizarAjusteProductoCartillaInventario(HttpServletRequest request, HttpServletResponse response) {
		ProductoCartillaInventarioBean producto = null;
		try {
			ProductoCartillaInventarioBean productoCartillaInventarioBean = new ProductoCartillaInventarioBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(productoCartillaInventarioBean, request.getParameterMap());	
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);

        	productoCartillaInventarioBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
	        productoCartillaInventarioBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
			producto = logisticaService.actualizarAjusteProductoCartillaInventario(productoCartillaInventarioBean);				

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
	@RequestMapping(value = "/procesarAjusteProductoCartillaInventario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object procesarAjusteProductoCartillaInventario(HttpServletRequest request, HttpServletResponse response) {
		ProductoCartillaInventarioBean producto = null;
		try {
			ProductoCartillaInventarioBean productoCartillaInventarioBean = new ProductoCartillaInventarioBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(productoCartillaInventarioBean, request.getParameterMap());	
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);

        	productoCartillaInventarioBean.setTipoOrigen(Constantes.TIPO_ORIGEN_ALMACENES);
	        productoCartillaInventarioBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
			producto = logisticaService.procesarProductosCartillaInventario(productoCartillaInventarioBean);				

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
	@RequestMapping(value = "/listarEstadoCartillaInventario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarEstadoCartillaInventario(HttpServletRequest request, HttpServletResponse response) {
		List<EstadoCartillaInventarioBean> lista = null;
		try {			
			EstadoCartillaInventarioBean estado = new EstadoCartillaInventarioBean();			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(estado, request.getParameterMap());			
			lista = logisticaService.listarEstadoCartillaInventario(estado);
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
	@RequestMapping(value = "/obtenerEstadosCartillaInventario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object obtenerEstadosCartillaInventario(HttpServletRequest request, HttpServletResponse response) {
		List<EstadoCartillaInventarioBean> lista = null;
		try {			
			EstadoCartillaInventarioBean estado = new EstadoCartillaInventarioBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(estado, request.getParameterMap());
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	estado.setIdUsuario(usuarioBean.getIdUsuario());
			lista = logisticaService.obtenerEstadosCartillaInventario(estado);
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
	@RequestMapping(value = "/grabarEstadoCartillaInventario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object grabarEstadoCartillaInventario(HttpServletRequest request, HttpServletResponse response) {
		EstadoCartillaInventarioBean producto = null;
		try {
			EstadoCartillaInventarioBean estadoCartillaInventarioBean = new EstadoCartillaInventarioBean();
			
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(estadoCartillaInventarioBean, request.getParameterMap());	
			
			// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);

        	estadoCartillaInventarioBean.setUsuarioRegistro(usuarioBean.getUsuario());
				
			producto = logisticaService.grabarEstadoCartillaInventario(estadoCartillaInventarioBean);				

			producto.setMensajeRespuesta(getMensaje(messageSource, "msg.info.eliminadoOk"));				

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return producto;
	}
	
	/**
	 * @param codigoAnio 
	 * @param idAlmacen 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarExcel/{codigoAnio}/{idAlmacen}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("codigoAnio") String codigoAnio, 
								@PathVariable("idAlmacen") Integer idAlmacen,
								HttpServletResponse response) {
	    try {
	    	CartillaInventarioBean cartillaInventarioBean = new CartillaInventarioBean();
			cartillaInventarioBean.setCodigoAnio(verificaParametro(codigoAnio));
			cartillaInventarioBean.setIdAlmacen(idAlmacen);
			List<CartillaInventarioBean> lista = logisticaService.listarCartillaInventario(cartillaInventarioBean);
	    	
			String file_name = "CartillaInventario";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_XLS);
			
			ReporteCartillaInventario reporte = new ReporteCartillaInventario();
		    HSSFWorkbook wb = reporte.generaReporteExcelCartillaInventario(lista);
			
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
	 * @param indicador 
	 * @param request 
	 * @param response
	 * @return Objeto.
	 */
	@RequestMapping(value = "/exportarPdf/{codigo}/{indicador}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarPdf(@PathVariable("codigo") Integer codigo,
							  @PathVariable("indicador") String indicador,
							  HttpServletRequest request, 
							  HttpServletResponse response) {
	    try {
	    	ProductoCartillaInventarioBean productoCartillaInventarioBean = new ProductoCartillaInventarioBean();
	    	productoCartillaInventarioBean.setIdCartilla(codigo);
			List<ProductoCartillaInventarioBean> listaProducto = logisticaService.listarProductoCartillaInventario(productoCartillaInventarioBean);			
			if (isEmpty(listaProducto)) {
				return Constantes.COD_VALIDACION_GENERAL; // Sin registros asociados
			}			
			CartillaInventarioBean cartillaInventario = logisticaService.obtenerRegistroCartillaInventario(codigo);

			StringBuilder file_path = new StringBuilder();
	    	file_path.append(getPath(request));
	    	file_path.append(File.separator);
	    	file_path.append(Constantes.UPLOAD_PATH_FILE_TEMP);
	    	file_path.append(File.separator);
	    	file_path.append(Calendar.getInstance().getTime().getTime());
	    	file_path.append(Constantes.EXTENSION_FORMATO_PDF);
	    	
	    	String file_name = "ReporteCartillaInventario";
			file_name = file_name.concat(Constantes.EXTENSION_FORMATO_PDF);
			
			ReporteCartillaInventario reporte = new ReporteCartillaInventario();
			if (indicador.equals(Constantes.ONE_STRING)) { // Reporte Formato A
				reporte.generaPDFReporteCartillaInventarioA(file_path.toString(), cartillaInventario, listaProducto);
			} else if (indicador.equals(Constantes.TWO_STRING)) { // Reporte Formato B
				reporte.generaPDFReporteCartillaInventarioB(file_path.toString(), cartillaInventario, listaProducto);
			}
			
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
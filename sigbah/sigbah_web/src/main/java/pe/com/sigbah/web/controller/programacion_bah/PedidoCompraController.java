package pe.com.sigbah.web.controller.programacion_bah;

import java.util.Calendar;
import java.util.Date;
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

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ListaRespuestaRequerimientoBean;
import pe.com.sigbah.common.bean.PedidoCompraBean;
import pe.com.sigbah.common.bean.ProductoBean;
import pe.com.sigbah.common.bean.RacionBean;
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
@RequestMapping("/programacion-bath/pedido")
public class PedidoCompraController extends BaseController {
	
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
        	model.addAttribute("lista_ddi", generalService.listarDdi(new ItemBean()));
        	model.addAttribute("lista_estado", generalService.listarEstado( new ItemBean(null,Constantes.ONE_INT)));
       	
        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "pedido-compra";
    }

	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listarPedidosCompra", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarPedidosCompra(HttpServletRequest request, HttpServletResponse response) {
		List<PedidoCompraBean> lista = null;
		try {			
			PedidoCompraBean pedidoCompraBean = new PedidoCompraBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(pedidoCompraBean, request.getParameterMap());			
			
//			usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
			
			lista = programacionService.listarPedidosCompra(pedidoCompraBean); 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	@RequestMapping(value = "/mantenimientoPedido/{codigo}", method = RequestMethod.GET)
    public String mantenimientoPedido(@PathVariable("codigo") Integer codigo, Model model) {
        try {
        	PedidoCompraBean pedido = new PedidoCompraBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        
        	ListaRespuestaRequerimientoBean respuestaEdicion = new ListaRespuestaRequerimientoBean();
        	if (!isNullInteger(codigo)) {// editar
        		
//        		respuestaEdicion = programacionService.obtenerRequerimiento(usuarioBean.getCodigoAnio(),usuarioBean.getCodigoDdi(),codigo); 
//        		model.addAttribute("requerimiento", getParserObject(respuestaEdicion.getLstCabecera().get(0)));
//        		model.addAttribute("lista_requerimiento", getParserObject(respuestaEdicion.getLstDetalle()));
        	  		
        	} else {//nuevo

        		PedidoCompraBean parametros = new PedidoCompraBean();
//        		String anioActual = generalService.obtenerAnioActual();
//        		parametros.setCodAnio(anioActual);
//        		parametros.setIdDdi(usuarioBean.getIdDdi());   
//        		
//        		RacionBean respuestaCorrelativo = programacionService.obtenerCorrelativoRacion(parametros);
//        		pedido.setCodRacion(respuestaCorrelativo.getCodRacion());   		
//        		Date fecha_hora = Calendar.getInstance().getTime();
//        		pedido.setFechaRacion(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));
//        		
//    			model.addAttribute("pedido", getParserObject(pedido));
        	}
	
        	model.addAttribute("lista_estado", generalService.listarEstado(new ItemBean()));
        	model.addAttribute("lista_dee", generalService.listarDee(new ItemBean()));//whr consultar
        	model.addAttribute("lista_tipo_prod", generalService.listarRacion(new ItemBean()));
        	model.addAttribute("lista_categoria_prod", generalService.listarRacion(new ItemBean()));
        	model.addAttribute("lista_producto", generalService.listarRacion(new ItemBean()));
        	
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_racion";
    }
	
}

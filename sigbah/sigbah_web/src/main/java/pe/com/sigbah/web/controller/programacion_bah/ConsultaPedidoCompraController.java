package pe.com.sigbah.web.controller.programacion_bah;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.OrdenSalidaBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.LogisticaService;
import pe.com.sigbah.web.controller.common.BaseController;

/**
 * @className: ConsultaPedidoCompraController.java
 * @description: 
 * @date: 13 jul. 2017
 * @author: whr.
 */

@Controller
@RequestMapping("/programacion-bath/consulta-pedido")
public class ConsultaPedidoCompraController extends BaseController {
	
private static final long serialVersionUID = 1L;
	
	@Autowired 
	private LogisticaService logisticaService;
	
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
        	
        	model.addAttribute("lista_dee", generalService.listarDee(new ItemBean()));
        	model.addAttribute("lista_estado", generalService.listarEstado( new ItemBean(null,Constantes.ONE_INT)));
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
        return "consulta-pedido";
    }

	
}

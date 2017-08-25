package pe.com.sigbah.web.controller.programacion_bah;

import java.io.OutputStream;
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

import pe.com.sigbah.common.bean.AlimentariaEmergenciaBean;
import pe.com.sigbah.common.bean.CabeceraEmergenciaBean;
import pe.com.sigbah.common.bean.EmergenciaBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ListaRespuestaEmergenciaBean;
import pe.com.sigbah.common.bean.LocalidadEmergenciaBean;
import pe.com.sigbah.common.bean.NoAlimentariaEmergenciaBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.service.GeneralService;
import pe.com.sigbah.service.ProgramacionService;
import pe.com.sigbah.web.controller.common.BaseController;
import pe.com.sigbah.web.report.programacion_bah.ReporteEmergencia;

/**
 * @className: EmergenciaController.java
 * @description: 
 * @date: 13 jul. 2017
 * @author: whr.
 */

@Controller
@RequestMapping("/programacion-bath/emergencia")
public class EmergenciaController extends BaseController {
	
private static final long serialVersionUID = 1L;
	
	@Autowired 
	private ProgramacionService programacionService;
	
	@Autowired 
	private GeneralService generalService;
	
	@RequestMapping(value = "/inicio/{indicador}", method = RequestMethod.GET)
    public String inicio(@PathVariable("indicador") String indicador, Model model) {
        try {
        		
        	model.addAttribute("lista_anio", generalService.listarAnios());
        	
        	model.addAttribute("lista_mes", generalService.listarMeses(new ItemBean()));
        	
        	model.addAttribute("lista_region", generalService.listarRegion(new ItemBean()));
        	
        	model.addAttribute("lista_fenomeno", generalService.listarFenomeno(new ItemBean()));
   	
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	model.addAttribute("indicador", indicador);
        	model.addAttribute("base", getBaseRespuesta(Constantes.COD_EXITO_GENERAL));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "emergencias-sinpad";
    }

	
	/**
	 * @param request
	 * @param response
	 * @return objeto en formato json
	 */
	@RequestMapping(value = "/listarEmergencias", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listarEmergencias(HttpServletRequest request, HttpServletResponse response) {
		List<EmergenciaBean> lista = null;
		try {			
			EmergenciaBean emergenciaBean = new EmergenciaBean();	
			// Copia los parametros del cliente al objeto
			BeanUtils.populate(emergenciaBean, request.getParameterMap());			
			lista = programacionService.listarEmergencia(emergenciaBean); 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getBaseRespuesta(null);
		}
		return lista;
	}
	
	@RequestMapping(value = "/exportarExcel/{codAnio}/{codMes}/{codRegion}/{codFenomeno}", method = RequestMethod.GET)
	@ResponseBody
	public String exportarExcel(@PathVariable("codAnio") String codAnio, 
								@PathVariable("codMes") String codMes, 
								@PathVariable("codRegion") Integer codRegion, 
								@PathVariable("codFenomeno") Integer codFenomeno, 
								HttpServletResponse response) {
	    try {
	    	EmergenciaBean emergenciaBean = new EmergenciaBean();
			emergenciaBean.setCodAnio(verificaParametro(codAnio));
			emergenciaBean.setCodMes(verificaParametro(codMes));
			emergenciaBean.setIdRegion(codRegion);
			emergenciaBean.setIdFenomeno(codFenomeno);
			List<EmergenciaBean> lista = programacionService.listarEmergencia(emergenciaBean);
	    	
			String file_name = "Reporte_Emergencias";
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
	 * @param model
	 * @return - Retorna a la vista JSP.
	 */ 
	@RequestMapping(value = "/mantenimientoEmergencia/{codigo}/{codigoAnio}/{region}/{anio}/{mes}/{fenomeno}", method = RequestMethod.GET)
    public String mantenimientoEmergencia(@PathVariable("codigo") Integer codigo,
    									  @PathVariable("codigoAnio") String codigoAnio,
    									  @PathVariable("region") String region,
    									  @PathVariable("anio") String anio,
    									  @PathVariable("mes") String mes,
    									  @PathVariable("fenomeno") String fenomeno,
    									  Model model) {
        try {
        	ListaRespuestaEmergenciaBean detalle = new ListaRespuestaEmergenciaBean();
        	
        	// Retorno los datos de session
        	usuarioBean = (UsuarioBean) context().getAttribute("usuarioBean", RequestAttributes.SCOPE_SESSION);
        	
        	detalle = programacionService.obtenerRegistroEmergencia(codigo,  codigoAnio);
    		model.addAttribute("cabecera", getParserObject(detalle.getLstCabecera().get(0)));
    		model.addAttribute("lista_localidad", getParserObject(detalle.getLstLocalidad()));
    		model.addAttribute("lista_alimentaria", getParserObject(detalle.getLstAlimentaria()));
    		model.addAttribute("lista_no_alimentaria", getParserObject(detalle.getLstNoAlimentaria()));
    		
    		model.addAttribute("codiRegion", region);
    		model.addAttribute("codiAnio", anio);
    		model.addAttribute("codiMes", mes);
    		model.addAttribute("codiFenomeno", fenomeno);
    		
    		
            
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	model.addAttribute("base", getBaseRespuesta(null));
        }
        return "mantenimiento_emergencias-sinpad";
    }
	
}

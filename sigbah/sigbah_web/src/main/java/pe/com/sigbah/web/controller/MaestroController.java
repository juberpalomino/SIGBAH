package pe.com.sigbah.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pe.com.sigbah.common.bean.UbigeoBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.service.IMaestroService;

/**
 * @className: IngresosBoletosController.java
 * @description: 
 * @date: 23 de jun. de 2016
 * @author: Administrador.
 */
@Controller
public class MaestroController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Autowired 
	private IMaestroService iMaestroService;
	

	/**
	 * Metodo que retorna a la Bandeja Registro de Cuarta Categoria.
	 * @return - Retorna a la vista JSP.
	 */
	@RequestMapping("/lis_producto")
    public ModelAndView listarProducto() {
		ModelAndView model = new ModelAndView("lis_reg_producto");		
		try {
			
			List<UbigeoBean> lis_ubigeo = new ArrayList<UbigeoBean>();		
			lis_ubigeo = iMaestroService.listarUbigeo(new UbigeoBean());		
			model.addObject("lis_ubigeo", lis_ubigeo);
			
			
			
			RestTemplate restTemplate = new RestTemplate();

//			IntegrationTipoComprobanteResponseDTO objResponseDTO = null;

			String urlServicio = "http://localhost:8083/mig_web/integracion/comprobantes";

			ObjectMapper objectMapper = new ObjectMapper();
		    
		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		    HttpEntity<String> request = new HttpEntity<String>(headers);
		    ResponseEntity<String> response = restTemplate.exchange(urlServicio, HttpMethod.GET, request, String.class);
		    String responseBody = response.getBody();
		  
	        if (RestUtil.isError(response.getStatusCode())) {
	     //       MyErrorResource error = objectMapper.readValue(responseBody, MyErrorResource.class);
	     //       throw new RestClientException("[" + error.getCode() + "] " + error.getMessage());
	        	System.out.println("---> Error consumo Rest");
	        } else {
	        	System.out.println("---> Response ");
	        	
//	        	objResponseDTO = objectMapper.readValue(responseBody, IntegrationTipoComprobanteResponseDTO.class);
	        	
//	        	System.out.println(obtenerJsonFormateado(objResponseDTO));	
	        }

		} catch (Exception e) {
			log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
									  Constantes.NIVEL_APP_CONSTROLLER, 
									  this.getClass().getName(), e.getMessage()));
		}		
		return model;
    }
	
	/**
	 * @param obj
	 * @return json formateado
	 */
	public static String obtenerJsonFormateado(Object obj) {
		Gson json = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
		return json.toJson(obj);
	}
	
}

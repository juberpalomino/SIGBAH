package pe.com.sigbah.common.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import pe.com.sigbah.common.bean.ItemBean;

/**
 * @className: HtmlUtils.java
 * @description: Clase utilitaria.
 * @date: 17 de jul. de 2017
 * @author: ARCHY.
 */
public class HtmlUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String TablaEstadosXDonacion(List<ItemBean> listaEstados){
		Integer num=1;
        String outPut = 
                            "<table id=\"tbl_det_estados\" class=\"table table-bordered table-hover tbl-responsive\">" +
                                "<thead>" +
                                    "<tr>" +
                                        "<td>N°</td>" +
                                        "<td>Estado</td>" +
                                        "<td>Fecha</td>" +
                                        "<td>Usuario</td>" +
                                    "</tr>" +
                                "</thead>" +
                                "<tbody>";
        
        for(int i=0;i<listaEstados.size();i++){  

           outPut +=    "<tr class='<c:if test=\"${rowCounter.count % 2 == 0}\">on</c:if>'>" +
                            "<td>"+num+"</td>" +
                            "<td>"+listaEstados.get(i).getVcodigoParam2()+"</td>" +
                            "<td>"+listaEstados.get(i).getVcodigoParam3()+"</td>" +
                            "<td>"+listaEstados.get(i).getVcodigoParam4()+"</td>" +
                  		"</tr>";  
              num++;
        }         
                outPut +=       "</tbody>" +
                            "</table>";
         return outPut;
    }
	
	public static String TablaDocumentosDonacion(List<ItemBean> listaEstados){
		Integer num=1;
        String outPut = 
                            "<table id=\"tbl_det_estados\" class=\"table table-bordered table-hover tbl-responsive\">" +
                                "<thead>" +
                                    "<tr>" +
                                        "<td>N°</td>" +
                                        "<td>Estado</td>" +
                                        "<td>Fecha</td>" +
                                        "<td>Usuario</td>" +
                                    "</tr>" +
                                "</thead>" +
                                "<tbody>";
        
        for(int i=0;i<listaEstados.size();i++){  

           outPut +=    "<tr class='<c:if test=\"${rowCounter.count % 2 == 0}\">on</c:if>'>" +
                            "<td>"+num+"</td>" +
                            "<td>"+listaEstados.get(i).getVcodigoParam2()+"</td>" +
                            "<td>"+listaEstados.get(i).getVcodigoParam3()+"</td>" +
                            "<td>"+listaEstados.get(i).getVcodigoParam4()+"</td>" +
                  		"</tr>";  
              num++;
        }         
                outPut +=       "</tbody>" +
                            "</table>";
         return outPut;
    }
	
	public static String TablaProductosDonacion(List<ItemBean> listaEstados){
		Integer num=1;
        String outPut = 
                            "<table id=\"tbl_det_estados\" class=\"table table-bordered table-hover tbl-responsive\">" +
                                "<thead>" +
                                    "<tr>" +
                                        "<td>N°</td>" +
                                        "<td>Estado</td>" +
                                        "<td>Fecha</td>" +
                                        "<td>Usuario</td>" +
                                    "</tr>" +
                                "</thead>" +
                                "<tbody>";
        
        for(int i=0;i<listaEstados.size();i++){  

           outPut +=    "<tr class='<c:if test=\"${rowCounter.count % 2 == 0}\">on</c:if>'>" +
                            "<td>"+num+"</td>" +
                            "<td>"+listaEstados.get(i).getVcodigoParam2()+"</td>" +
                            "<td>"+listaEstados.get(i).getVcodigoParam3()+"</td>" +
                            "<td>"+listaEstados.get(i).getVcodigoParam4()+"</td>" +
                  		"</tr>";  
              num++;
        }         
                outPut +=       "</tbody>" +
                            "</table>";
         return outPut;
    }
	
}

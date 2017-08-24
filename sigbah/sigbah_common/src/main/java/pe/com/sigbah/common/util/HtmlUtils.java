package pe.com.sigbah.common.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.RolMenuBean;

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
	
	public static String mostrarRol(List<RolMenuBean> listaRolMenu){

		String outPut="";
		outPut +="<div id=\"myjstree1\">";
		outPut +="<ul>";
        for(int i=0;i<listaRolMenu.size();i++){  
        	
        	if(listaRolMenu.get(i).getIdPadre()==0){
        		System.out.println("IDPADRE: "+listaRolMenu.get(i).getIdPadre());
        		 outPut +="<li>"+listaRolMenu.get(i).getNombreMenu()+"</li>";
        		 outPut +="<ul>";
 	        	for(int j=0;j<listaRolMenu.size();j++){
 	        		if(listaRolMenu.get(i).getIdMenu()==listaRolMenu.get(j).getIdPadre() && listaRolMenu.get(j).getIdPadre()!=0){
 	        			outPut +="<li>"+listaRolMenu.get(j).getNombreMenu()+"</li>";
 	        			outPut +="<ul>";
 	        			for(int k=0;k<listaRolMenu.size();k++){
 	        				if(listaRolMenu.get(j).getIdMenu()==listaRolMenu.get(k).getIdPadre() && listaRolMenu.get(k).getIdPadre()!=0){
 	        					outPut +="<li>"+listaRolMenu.get(k).getNombreMenu()+"</li>";
 	        				}
 	        			}
 	        			outPut +="</ul>";
 	        		}
 	        	}
 	        	outPut +="</ul>";
        		 
        	}
        	
	        	
        	

        }         
        outPut +="</ul>";
        outPut +="</div>";
         return outPut;
    }
	
	public static String mostrarMenuRol(List<RolMenuBean> listaRolMenu){

		String outPut="";
		outPut +="<div id=\"myjstree1\">";
		outPut +="<ul>";
        for(int i=0;i<listaRolMenu.size();i++){  
        	
        	if(listaRolMenu.get(i).getIdPadre()==0){
        		 outPut +="<li>"+listaRolMenu.get(i).getNombreMenu();
        		 outPut +="<ul>";
 	        	for(int j=0;j<listaRolMenu.size();j++){
 	        		if(listaRolMenu.get(i).getIdMenu()==listaRolMenu.get(j).getIdPadre() && listaRolMenu.get(j).getIdPadre()!=0){
 	        			if(listaRolMenu.get(j).getExiste().equals("1") && listaRolMenu.get(j).getHijo().equals("0")){
 	        				outPut +="<li><a href=\"#\" class=\"jstree-clicked\" onclick=\"actualizarRol("+listaRolMenu.get(j).getIdPadre()+"," +listaRolMenu.get(j).getIdMenu()+")\">"+listaRolMenu.get(j).getNombreMenu()+"</a>";
 	        			}else{
 	        				outPut +="<li><a href=\"#\" onclick=\"actualizarRol("+listaRolMenu.get(j).getIdPadre()+"," +listaRolMenu.get(j).getIdMenu()+")\">"+listaRolMenu.get(j).getNombreMenu()+"</a>";
 	        			}
 	        			outPut +="<ul>";
 	        			for(int k=0;k<listaRolMenu.size();k++){
 	        				if(listaRolMenu.get(j).getIdMenu()==listaRolMenu.get(k).getIdPadre() && listaRolMenu.get(k).getIdPadre()!=0){
 	        					if(listaRolMenu.get(k).getExiste().equals("1")){
 	        						outPut +="<li><a href=\"#\" class=\"jstree-clicked\" onclick=\"actualizarRol("+listaRolMenu.get(k).getIdPadre()+"," +listaRolMenu.get(k).getIdMenu()+")\">"+listaRolMenu.get(k).getNombreMenu()+"</a></li>";
 	        					}else{
 	        						outPut +="<li><a href=\"#\" onclick=\"actualizarRol("+listaRolMenu.get(k).getIdPadre()+"," +listaRolMenu.get(k).getIdMenu()+")\">"+listaRolMenu.get(k).getNombreMenu()+"</a></li>";
 	        					}
 	        					
 	        				}
 	        			}
 	        			outPut +="</li>";
 	        			outPut +="</ul>";
 	        		}
 	        	}
 	        	outPut +="</li>";
 	        	outPut +="</ul>";
        		 
        	}
        }         
        outPut +="</ul>";
        outPut +="</div>";
         return outPut;
    }
	
}

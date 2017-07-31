package pe.com.sigbah.service;

import java.util.List;

import pe.com.sigbah.common.bean.DeeBean;
import pe.com.sigbah.common.bean.EmergenciaBean;
import pe.com.sigbah.common.bean.ListaRespuestaEmergenciaBean;
import pe.com.sigbah.common.bean.ListaRespuestaRequerimientoBean;
import pe.com.sigbah.common.bean.PedidoCompraBean;
import pe.com.sigbah.common.bean.ProductoRacionBean;
import pe.com.sigbah.common.bean.RacionBean;
import pe.com.sigbah.common.bean.RequerimientoBean;
import pe.com.sigbah.common.bean.UbigeoIneiBean;

/**
 * @className: ProgramacionService.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_PROGRAMACION.
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
public interface ProgramacionService {
	
	
	/**
	 * @param controlCalidadBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<EmergenciaBean> listarEmergencia(EmergenciaBean emergenciaBean) throws Exception;
	
	/**
	 * @param idEmergencia
	 * @param codAnio
	 * @return
	 * @throws Exception
	 */
	public abstract ListaRespuestaEmergenciaBean obtenerRegistroEmergencia(Integer idEmergencia, String codAnio) throws Exception;

	
	/**
	 * @param requerimientoBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<RequerimientoBean> listarRequerimiento(RequerimientoBean requerimientoBean) throws Exception;
	
	/**
	 * @param codAnio
	 * @return
	 * @throws Exception
	 */
	public abstract ListaRespuestaRequerimientoBean obtenerRequerimiento(String codAnio, String codDdi,Integer idRequerimiento) throws Exception;
	
	/**
	 * @param parametros
	 * @return
	 * @throws Exception
	 */
	public abstract RequerimientoBean obtenerCorrelativoRequerimiento(RequerimientoBean parametros ) throws Exception;
	
	
	/**
	 * @param requerimientoBean
	 * @return
	 * @throws Exception
	 */
	public abstract RequerimientoBean insertarRegistroRequerimiento(RequerimientoBean requerimientoBean) throws Exception;
	
	/**
	 * @param requerimientoBean
	 * @return
	 * @throws Exception
	 */
	public abstract RequerimientoBean actualizarRegistroRequerimiento(RequerimientoBean requerimientoBean) throws Exception;
	
	/**
	 * @param requerimientoBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<EmergenciaBean> listarEmergenciasActivas(RequerimientoBean requerimientoBean) throws Exception;
	
	
	/**
	 * @param requerimientoBean
	 * @return
	 * @throws Exception
	 */
	public abstract EmergenciaBean pasarDistritos(EmergenciaBean emergenciaBean) throws Exception;
	
	/**
	 * @param ubigeoBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<UbigeoIneiBean> listarUbigeoInei(UbigeoIneiBean ubigeoBean) throws Exception;
	
	
	/**
	 * @param ubigeoBean
	 * @return
	 * @throws Exception
	 */
	public abstract EmergenciaBean pasarDistritosUbigeo(EmergenciaBean emergenciaBean) throws Exception;
	
	/**
	 * @param racionBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<RacionBean> listarRaciones(RacionBean racionBean) throws Exception;
	
	
	/**
	 * @param racionBean
	 * @return
	 * @throws Exception
	 */
	public abstract RacionBean copiarRacion(RacionBean racionBean) throws Exception;
	
	/**
	 * @param parametros
	 * @return
	 * @throws Exception
	 */
	public abstract RacionBean obtenerCorrelativoRacion(RacionBean parametros ) throws Exception;
	
	/**
	 * @param racionBean
	 * @return
	 * @throws Exception
	 */
	public abstract RacionBean insertarRegistroRacion(RacionBean racionBean) throws Exception;
	
	/**
	 * @param racionBean
	 * @return
	 * @throws Exception
	 */
	public abstract RacionBean actualizarRegistroRacion(RacionBean racionBean) throws Exception;
	
	
	/**
	 * @param productoBean
	 * @return
	 * @throws Exception
	 */
	public abstract ProductoRacionBean insertarRegistroProducto(ProductoRacionBean productoBean) throws Exception;
	
	
	/**
	 * @param productoBean
	 * @return
	 * @throws Exception
	 */
	public abstract ProductoRacionBean actualizarRegistroProducto(ProductoRacionBean productoBean) throws Exception;
	
	
	
	/**
	 * @param pedidoBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<PedidoCompraBean> listarPedidosCompra(PedidoCompraBean pedidoBean) throws Exception;
	
	/**
	 * @param deeBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<DeeBean> listarDee(DeeBean deeBean) throws Exception;
}

package pe.com.sigbah.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.DeeBean;
import pe.com.sigbah.common.bean.EmergenciaBean;
import pe.com.sigbah.common.bean.ListaRespuestaEmergenciaBean;
import pe.com.sigbah.common.bean.ListaRespuestaRequerimientoBean;
import pe.com.sigbah.common.bean.PedidoCompraBean;
import pe.com.sigbah.common.bean.ProductoRacionBean;
import pe.com.sigbah.common.bean.RacionBean;
import pe.com.sigbah.common.bean.RequerimientoBean;
import pe.com.sigbah.common.bean.UbigeoIneiBean;
import pe.com.sigbah.dao.ProgramacionDao;
import pe.com.sigbah.service.ProgramacionService;

/**
 * @className: ProgramacionServiceImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_PROGRAMACION.
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Service
public class ProgramacionServiceImpl implements ProgramacionService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProgramacionDao programacionDao;

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#listarEmergencia(pe.com.sigbah.common.bean.EmergenciaBean)
	 */
	@Override
	public List<EmergenciaBean> listarEmergencia(EmergenciaBean emergenciaBean) throws Exception {
		return programacionDao.listarEmergencia(emergenciaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#obtenerRegistroEmergencia(java.lang.Integer)
	 */
	@Override
	public ListaRespuestaEmergenciaBean obtenerRegistroEmergencia(Integer idEmergencia, String codAnio) throws Exception {
		return programacionDao.obtenerRegistroEmergencia(idEmergencia,  codAnio);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#listarRequerimiento(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public List<RequerimientoBean> listarRequerimiento(RequerimientoBean requerimientoBean) throws Exception {
		return programacionDao.listarRequerimiento(requerimientoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#obtenerRequerimiento(java.lang.Integer, java.lang.String)
	 */
	@Override
	public ListaRespuestaRequerimientoBean obtenerRequerimiento( String codAnio, String codDdi,Integer idRequerimiento) throws Exception {
		return programacionDao.obtenerRequerimiento( codAnio,  codDdi, idRequerimiento );
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#obtenerCorrelativoRequerimiento(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public RequerimientoBean obtenerCorrelativoRequerimiento(RequerimientoBean parametros) throws Exception {
		return programacionDao.obtenerCorrelativoRequerimiento(parametros );
	}
	
	@Override
	public RequerimientoBean insertarRegistroRequerimiento(RequerimientoBean requerimiento) throws Exception {
		return programacionDao.insertarRegistroRequerimiento(requerimiento);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#actualizarRegistroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public RequerimientoBean actualizarRegistroRequerimiento(RequerimientoBean requerimiento) throws Exception {
		return programacionDao.actualizarRegistroRequerimiento(requerimiento);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#listarEmergenciasActivas(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public List<EmergenciaBean> listarEmergenciasActivas(RequerimientoBean requerimientoBean) throws Exception {
		return programacionDao.listarEmergenciasActivas(requerimientoBean);
	}
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#pasarDistritos(pe.com.sigbah.common.bean.EmergenciaBean)
	 */
	@Override
	public EmergenciaBean pasarDistritos(EmergenciaBean emergenciaBean) throws Exception {
		return programacionDao.pasarDistritos(emergenciaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#listarUbigeoInei(pe.com.sigbah.common.bean.UbigeoIneiBean)
	 */
	@Override
	public List<UbigeoIneiBean> listarUbigeoInei(UbigeoIneiBean ubigeoBean) throws Exception {
		return programacionDao.listarUbigeoInei(ubigeoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#pasarDistritosUbigeo(pe.com.sigbah.common.bean.UbigeoIneiBean)
	 */
	@Override
	public EmergenciaBean pasarDistritosUbigeo(EmergenciaBean emergenciaBean) throws Exception {
		return programacionDao.pasarDistritosUbigeo(emergenciaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#listarRaciones(pe.com.sigbah.common.bean.RacionBean)
	 */
	@Override
	public List<RacionBean> listarRaciones(RacionBean racionBean) throws Exception {
		return programacionDao.listarRaciones(racionBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#copiarRacion(pe.com.sigbah.common.bean.RacionBean)
	 */
	@Override
	public RacionBean copiarRacion(RacionBean racionBean) throws Exception {
		return programacionDao.copiarRacion(racionBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#obtenerCorrelativoRacion(pe.com.sigbah.common.bean.RacionBean)
	 */
	@Override
	public RacionBean obtenerCorrelativoRacion(RacionBean parametros) throws Exception {
		return programacionDao.obtenerCorrelativoRacion(parametros);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#insertarRegistroRacion(pe.com.sigbah.common.bean.RacionBean)
	 */
	@Override
	public RacionBean insertarRegistroRacion(RacionBean racionBean) throws Exception {
		return programacionDao.insertarRegistroRacion(racionBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#actualizarRegistroRacion(pe.com.sigbah.common.bean.RacionBean)
	 */
	@Override
	public RacionBean actualizarRegistroRacion(RacionBean racionBean) throws Exception {
		return programacionDao.actualizarRegistroRacion(racionBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#insertarRegistroProducto(pe.com.sigbah.common.bean.ProductoRacionBean)
	 */
	@Override
	public ProductoRacionBean insertarRegistroProducto(ProductoRacionBean productoBean) throws Exception {
		return programacionDao.insertarRegistroProducto(productoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#actualizarRegistroProducto(pe.com.sigbah.common.bean.ProductoRacionBean)
	 */
	@Override
	public ProductoRacionBean actualizarRegistroProducto(ProductoRacionBean productoBean) throws Exception {
		return programacionDao.actualizarRegistroProducto(productoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#listarPedidosCompra(pe.com.sigbah.common.bean.PedidoCompraBean)
	 */
	@Override
	public List<PedidoCompraBean> listarPedidosCompra(PedidoCompraBean pedidoBean) throws Exception {
		return programacionDao.listarPedidosCompra(pedidoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#listarDee(pe.com.sigbah.common.bean.DeeBean)
	 */
	@Override
	public List<DeeBean> listarDee(DeeBean deeBean) throws Exception {
		return programacionDao.listarDee(deeBean);
	}
}

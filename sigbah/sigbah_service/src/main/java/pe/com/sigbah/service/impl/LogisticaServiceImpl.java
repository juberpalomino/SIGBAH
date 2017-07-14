package pe.com.sigbah.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DetalleProductoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoIngresoBean;
import pe.com.sigbah.common.bean.DocumentoProyectoManifiestoBean;
import pe.com.sigbah.common.bean.DocumentoSalidaBean;
import pe.com.sigbah.common.bean.LoteProductoBean;
import pe.com.sigbah.common.bean.OrdenCompraBean;
import pe.com.sigbah.common.bean.OrdenIngresoBean;
import pe.com.sigbah.common.bean.OrdenSalidaBean;
import pe.com.sigbah.common.bean.ProductoControlCalidadBean;
import pe.com.sigbah.common.bean.ProductoIngresoBean;
import pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean;
import pe.com.sigbah.common.bean.ProductoSalidaBean;
import pe.com.sigbah.common.bean.ProyectoManifiestoBean;
import pe.com.sigbah.common.bean.ProyectoManifiestoVehiculoBean;
import pe.com.sigbah.dao.LogisticaDao;
import pe.com.sigbah.service.LogisticaService;

/**
 * @className: LogisticaServiceImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_LOGISTICA.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
@Service
public class LogisticaServiceImpl implements LogisticaService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LogisticaDao logisticaDao;
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public List<ControlCalidadBean> listarControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		return logisticaDao.listarControlCalidad(controlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#obtenerCorrelativoControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public ControlCalidadBean obtenerCorrelativoControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		return logisticaDao.obtenerCorrelativoControlCalidad(controlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarAlmacenActivo(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public List<ControlCalidadBean> listarAlmacenActivo(ControlCalidadBean controlCalidadBean) throws Exception {
		return logisticaDao.listarAlmacenActivo(controlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarOrdenCompra()
	 */
	@Override
	public List<OrdenCompraBean> listarOrdenCompra() throws Exception {
		return logisticaDao.listarOrdenCompra();
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#insertarRegistroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public ControlCalidadBean insertarRegistroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		return logisticaDao.insertarRegistroControlCalidad(controlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#actualizarRegistroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public ControlCalidadBean actualizarRegistroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		return logisticaDao.actualizarRegistroControlCalidad(controlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#obtenerRegistroControlCalidad(java.lang.Integer)
	 */
	@Override
	public ControlCalidadBean obtenerRegistroControlCalidad(Integer idControlCalidad) throws Exception {
		return logisticaDao.obtenerRegistroControlCalidad(idControlCalidad);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarProductoControlCalidad(pe.com.sigbah.common.bean.ProductoControlCalidadBean)
	 */
	@Override
	public List<ProductoControlCalidadBean> listarProductoControlCalidad(ProductoControlCalidadBean producto) throws Exception {
		return logisticaDao.listarProductoControlCalidad(producto);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#grabarProductoControlCalidad(pe.com.sigbah.common.bean.ProductoControlCalidadBean)
	 */
	@Override
	public ProductoControlCalidadBean grabarProductoControlCalidad(ProductoControlCalidadBean productoControlCalidadBean) throws Exception {
		return logisticaDao.grabarProductoControlCalidad(productoControlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#eliminarProductoControlCalidad(pe.com.sigbah.common.bean.ProductoControlCalidadBean)
	 */
	@Override
	public ProductoControlCalidadBean eliminarProductoControlCalidad(ProductoControlCalidadBean productoControlCalidadBean) throws Exception {
		return logisticaDao.eliminarProductoControlCalidad(productoControlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarDocumentoControlCalidad(pe.com.sigbah.common.bean.DocumentoControlCalidadBean)
	 */
	@Override
	public List<DocumentoControlCalidadBean> listarDocumentoControlCalidad(DocumentoControlCalidadBean documentoControlCalidadBean) throws Exception {
		return logisticaDao.listarDocumentoControlCalidad(documentoControlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#grabarDocumentoControlCalidad(pe.com.sigbah.common.bean.DocumentoControlCalidadBean)
	 */
	@Override
	public DocumentoControlCalidadBean grabarDocumentoControlCalidad(DocumentoControlCalidadBean documentoControlCalidadBean) throws Exception {
		return logisticaDao.grabarDocumentoControlCalidad(documentoControlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#eliminarDocumentoControlCalidad(pe.com.sigbah.common.bean.DocumentoControlCalidadBean)
	 */
	@Override
	public DocumentoControlCalidadBean eliminarDocumentoControlCalidad(DocumentoControlCalidadBean documentoControlCalidadBean) throws Exception {
		return logisticaDao.eliminarDocumentoControlCalidad(documentoControlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarDetalleProductoControlCalidad(java.lang.Integer)
	 */
	@Override
	public List<DetalleProductoControlCalidadBean> listarDetalleProductoControlCalidad(Integer idControlCalidad) throws Exception {
		return logisticaDao.listarDetalleProductoControlCalidad(idControlCalidad);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarOrdenIngreso(pe.com.sigbah.common.bean.OrdenIngresoBean)
	 */
	@Override
	public List<OrdenIngresoBean> listarOrdenIngreso(OrdenIngresoBean ordenIngresoBean) throws Exception {
		return logisticaDao.listarOrdenIngreso(ordenIngresoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#obtenerCorrelativoOrdenIngreso(pe.com.sigbah.common.bean.OrdenIngresoBean)
	 */
	@Override
	public OrdenIngresoBean obtenerCorrelativoOrdenIngreso(OrdenIngresoBean ordenIngresoBean) throws Exception {
		return logisticaDao.obtenerCorrelativoOrdenIngreso(ordenIngresoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarNroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public List<ControlCalidadBean> listarNroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		return logisticaDao.listarNroControlCalidad(controlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#grabarOrdenIngreso(pe.com.sigbah.common.bean.OrdenIngresoBean)
	 */
	@Override
	public OrdenIngresoBean grabarOrdenIngreso(OrdenIngresoBean ordenIngresoBean) throws Exception {
		return logisticaDao.grabarOrdenIngreso(ordenIngresoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#obtenerRegistroOrdenIngreso(java.lang.Integer)
	 */
	@Override
	public OrdenIngresoBean obtenerRegistroOrdenIngreso(Integer idIngreso) throws Exception {
		return logisticaDao.obtenerRegistroOrdenIngreso(idIngreso);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarProductoIngreso(pe.com.sigbah.common.bean.ProductoIngresoBean)
	 */
	@Override
	public List<ProductoIngresoBean> listarProductoIngreso(ProductoIngresoBean productoIngresoBean) throws Exception {
		return logisticaDao.listarProductoIngreso(productoIngresoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#grabarProductoIngreso(pe.com.sigbah.common.bean.ProductoIngresoBean)
	 */
	@Override
	public ProductoIngresoBean grabarProductoIngreso(ProductoIngresoBean productoIngresoBean) throws Exception {
		return logisticaDao.grabarProductoIngreso(productoIngresoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#eliminarProductoIngreso(pe.com.sigbah.common.bean.ProductoIngresoBean)
	 */
	@Override
	public ProductoIngresoBean eliminarProductoIngreso(ProductoIngresoBean productoIngresoBean) throws Exception {
		return logisticaDao.eliminarProductoIngreso(productoIngresoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarLoteProductos(pe.com.sigbah.common.bean.LoteProductoBean)
	 */
	@Override
	public List<LoteProductoBean> listarLoteProductos(LoteProductoBean loteProductoBean) throws Exception {
		return logisticaDao.listarLoteProductos(loteProductoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarDocumentoIngreso(pe.com.sigbah.common.bean.DocumentoIngresoBean)
	 */
	@Override
	public List<DocumentoIngresoBean> listarDocumentoIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception {
		return logisticaDao.listarDocumentoIngreso(documentoIngresoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#grabarDocumentoIngreso(pe.com.sigbah.common.bean.DocumentoIngresoBean)
	 */
	@Override
	public DocumentoIngresoBean grabarDocumentoIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception {
		return logisticaDao.grabarDocumentoIngreso(documentoIngresoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#eliminarDocumentoIngreso(pe.com.sigbah.common.bean.DocumentoIngresoBean)
	 */
	@Override
	public DocumentoIngresoBean eliminarDocumentoIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception {
		return logisticaDao.eliminarDocumentoIngreso(documentoIngresoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarOrdenSalida(pe.com.sigbah.common.bean.OrdenSalidaBean)
	 */
	@Override
	public List<OrdenSalidaBean> listarOrdenSalida(OrdenSalidaBean ordenSalidaBean) throws Exception {
		return logisticaDao.listarOrdenSalida(ordenSalidaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#obtenerCorrelativoOrdenSalida(pe.com.sigbah.common.bean.OrdenSalidaBean)
	 */
	@Override
	public OrdenSalidaBean obtenerCorrelativoOrdenSalida(OrdenSalidaBean ordenSalidaBean) throws Exception {
		return logisticaDao.obtenerCorrelativoOrdenSalida(ordenSalidaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#grabarOrdenSalida(pe.com.sigbah.common.bean.OrdenSalidaBean)
	 */
	@Override
	public OrdenSalidaBean grabarOrdenSalida(OrdenSalidaBean ordenSalidaBean) throws Exception {
		return logisticaDao.grabarOrdenSalida(ordenSalidaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#obtenerRegistroOrdenSalida(java.lang.Integer, java.lang.String)
	 */
	@Override
	public OrdenSalidaBean obtenerRegistroOrdenSalida(Integer idSalida, String anio) throws Exception {
		return logisticaDao.obtenerRegistroOrdenSalida(idSalida, anio);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarProductoSalida(pe.com.sigbah.common.bean.ProductoSalidaBean)
	 */
	@Override
	public List<ProductoSalidaBean> listarProductoSalida(ProductoSalidaBean productoSalidaBean) throws Exception {
		return logisticaDao.listarProductoSalida(productoSalidaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#grabarProductoSalida(pe.com.sigbah.common.bean.ProductoSalidaBean)
	 */
	@Override
	public ProductoSalidaBean grabarProductoSalida(ProductoSalidaBean productoSalidaBean) throws Exception {
		return logisticaDao.grabarProductoSalida(productoSalidaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#eliminarProductoSalida(pe.com.sigbah.common.bean.ProductoSalidaBean)
	 */
	@Override
	public ProductoSalidaBean eliminarProductoSalida(ProductoSalidaBean productoSalidaBean) throws Exception {
		return logisticaDao.eliminarProductoSalida(productoSalidaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarDocumentoSalida(pe.com.sigbah.common.bean.DocumentoSalidaBean)
	 */
	@Override
	public List<DocumentoSalidaBean> listarDocumentoSalida(DocumentoSalidaBean documentoSalidaBean) throws Exception {
		return logisticaDao.listarDocumentoSalida(documentoSalidaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#grabarDocumentoSalida(pe.com.sigbah.common.bean.DocumentoSalidaBean)
	 */
	@Override
	public DocumentoSalidaBean grabarDocumentoSalida(DocumentoSalidaBean documentoSalidaBean) throws Exception {
		return logisticaDao.grabarDocumentoSalida(documentoSalidaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#eliminarDocumentoSalida(pe.com.sigbah.common.bean.DocumentoSalidaBean)
	 */
	@Override
	public DocumentoSalidaBean eliminarDocumentoSalida(DocumentoSalidaBean documentoSalidaBean) throws Exception {
		return logisticaDao.eliminarDocumentoSalida(documentoSalidaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarManifiesto(pe.com.sigbah.common.bean.ProyectoManifiestoBean)
	 */
	@Override
	public List<ProyectoManifiestoBean> listarManifiesto(ProyectoManifiestoBean proyectoManifiestoBean) throws Exception {
		return logisticaDao.listarManifiesto(proyectoManifiestoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarProyectoManifiesto(pe.com.sigbah.common.bean.ProyectoManifiestoBean)
	 */
	@Override
	public List<ProyectoManifiestoBean> listarProyectoManifiesto(ProyectoManifiestoBean proyectoManifiestoBean)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#obtenerCorrelativoProyectoManifiesto(pe.com.sigbah.common.bean.ProyectoManifiestoBean)
	 */
	@Override
	public ProyectoManifiestoBean obtenerCorrelativoProyectoManifiesto(ProyectoManifiestoBean proyectoManifiestoBean)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#grabarProyectoManifiesto(pe.com.sigbah.common.bean.ProyectoManifiestoBean)
	 */
	@Override
	public ProyectoManifiestoBean grabarProyectoManifiesto(ProyectoManifiestoBean proyectoManifiestoBean)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#obtenerRegistroProyectoManifiesto(java.lang.Integer, java.lang.String)
	 */
	@Override
	public ProyectoManifiestoBean obtenerRegistroProyectoManifiesto(Integer idProyecto, String anio) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarProductoProyectoManifiesto(pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean)
	 */
	@Override
	public List<ProductoProyectoManifiestoBean> listarProductoProyectoManifiesto(
			ProductoProyectoManifiestoBean productoProyectoManifiestoBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#grabarProductoProyectoManifiesto(pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean)
	 */
	@Override
	public ProductoProyectoManifiestoBean grabarProductoProyectoManifiesto(
			ProductoProyectoManifiestoBean productoProyectoManifiestoBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#eliminarProductoProyectoManifiesto(pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean)
	 */
	@Override
	public ProductoProyectoManifiestoBean eliminarProductoProyectoManifiesto(
			ProductoProyectoManifiestoBean productoProyectoManifiestoBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarDocumentoProyectoManifiesto(pe.com.sigbah.common.bean.DocumentoProyectoManifiestoBean)
	 */
	@Override
	public List<DocumentoProyectoManifiestoBean> listarDocumentoProyectoManifiesto(
			DocumentoProyectoManifiestoBean documentoProyectoManifiestoBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#grabarDocumentoProyectoManifiesto(pe.com.sigbah.common.bean.DocumentoProyectoManifiestoBean)
	 */
	@Override
	public DocumentoProyectoManifiestoBean grabarDocumentoProyectoManifiesto(
			DocumentoProyectoManifiestoBean documentoProyectoManifiestoBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#eliminarDocumentoProyectoManifiesto(pe.com.sigbah.common.bean.DocumentoProyectoManifiestoBean)
	 */
	@Override
	public DocumentoProyectoManifiestoBean eliminarDocumentoProyectoManifiesto(
			DocumentoProyectoManifiestoBean documentoProyectoManifiestoBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#verificarProductosProgramacion(java.lang.Integer)
	 */
	@Override
	public int verificarProductosProgramacion(Integer idProgramacion) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarProyectoManifiestoVehiculo(pe.com.sigbah.common.bean.ProyectoManifiestoVehiculoBean)
	 */
	@Override
	public List<ProyectoManifiestoVehiculoBean> listarProyectoManifiestoVehiculo(ProyectoManifiestoVehiculoBean proyectoManifiestoVehiculoBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

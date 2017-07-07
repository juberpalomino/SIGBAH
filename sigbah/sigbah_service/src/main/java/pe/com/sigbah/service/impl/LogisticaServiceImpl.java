package pe.com.sigbah.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DetalleProductoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoIngresoBean;
import pe.com.sigbah.common.bean.LoteProductoBean;
import pe.com.sigbah.common.bean.OrdenCompraBean;
import pe.com.sigbah.common.bean.OrdenIngresoBean;
import pe.com.sigbah.common.bean.ProductoControlCalidadBean;
import pe.com.sigbah.common.bean.ProductoIngresoBean;
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

}

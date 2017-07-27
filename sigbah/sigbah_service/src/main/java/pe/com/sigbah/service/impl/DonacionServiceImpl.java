package pe.com.sigbah.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoDonacionBean;
import pe.com.sigbah.common.bean.DocumentoIngresoBean;
import pe.com.sigbah.common.bean.DonacionesBean;
import pe.com.sigbah.common.bean.DonacionesIngresoBean;
import pe.com.sigbah.common.bean.DonacionesSalidaBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ProductoDonacionBean;
import pe.com.sigbah.common.bean.ProductoDonacionIngresoBean;
import pe.com.sigbah.common.bean.RegionDonacionBean;
import pe.com.sigbah.dao.DonacionDao;
import pe.com.sigbah.service.DonacionService;

/**
 * @className: DonacionServiceImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_DONACION.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
@Service
public class DonacionServiceImpl implements DonacionService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DonacionDao donacionDao;
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public List<DonacionesBean> listarDonaciones(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.listarDonaciones(donacionesBean);
	}
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#actualizarRegistroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public DonacionesBean actualizarDonaciones(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.actualizarDonaciones(donacionesBean);
	}
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#actualizarRegistroControlCalidad(pe.com.sigbah.common.bean.DonacionesBean)
	 */
	@Override
	public DonacionesBean insertarDonaciones(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.insertarDonaciones(donacionesBean);
	}

	//////////////////////////////////////
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public List<DonacionesBean> listarSalidaDonaciones(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.listarSalidaDonaciones(donacionesBean);
	}
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#obtenerCorrelativoControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public DonacionesBean obtenerCodigoDonativo(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.obtenerCodigoDonativo(donacionesBean);
	}
	
	@Override
	public DonacionesBean insertarRegistroDonacion(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.insertarRegistroDonacion(donacionesBean);
	}

	@Override
	public DonacionesBean actualizarRegistroDonacion(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.actualizarRegistroDonacion(donacionesBean);
	}
	
	@Override
	public List<ItemBean> listarDonadores(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.listarDonadores(donacionesBean);
	}
	
	@Override
	public DonacionesBean obtenerDonacionXIdDonacion(Integer idDonacion) throws Exception {
		return donacionDao.obtenerDonacionXIdDonacion(idDonacion);
	}
	
	@Override
	public List<ItemBean> listarEstadoDonacionUsuario(ItemBean itemBean) throws Exception {
		return donacionDao.listarEstadoDonacionUsuario(itemBean);
	}
	
	@Override
	public List<ItemBean> mostrarEstadoDonacionUsuario(ItemBean itemBean) throws Exception {
		return donacionDao.mostrarEstadoDonacionUsuario(itemBean);
	}
	
	@Override
	public ProductoDonacionBean insertarProductoDonacion(ProductoDonacionBean productoDonacionBean) throws Exception {
		return donacionDao.insertarProductoDonacion(productoDonacionBean);
	}
	
	@Override
	public List<ProductoDonacionBean> listarProductoDonacion(ProductoDonacionBean productoDonacionBean) throws Exception {
		return donacionDao.listarProductoDonacion(productoDonacionBean);
	}
	
	@Override
	public ProductoDonacionBean eliminarProductoDonacion(ProductoDonacionBean productoDonacionBean) throws Exception {
		return donacionDao.eliminarProductoDonacion(productoDonacionBean);
	}
	
	@Override
	public DocumentoDonacionBean insertarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception {
		return donacionDao.insertarDocumentoDonacion(documentoDonacionBean);
	}
	
	@Override
	public DocumentoDonacionBean actualizarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception {
		return donacionDao.actualizarDocumentoDonacion(documentoDonacionBean);
	}
	
	@Override
	public List<DocumentoDonacionBean> listarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception {
		return donacionDao.listarDocumentoDonacion(documentoDonacionBean);
	}
	
	@Override
	public DocumentoDonacionBean eliminarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception {
		return donacionDao.eliminarDocumentoDonacion(documentoDonacionBean);
	}
	
	@Override
	public List<RegionDonacionBean> listarRegionesXDonacion(RegionDonacionBean regionDonacionBean) throws Exception {
		return donacionDao.listarRegionesXDonacion(regionDonacionBean);
	}

	@Override
	public RegionDonacionBean insertarRegionDonacion(RegionDonacionBean regionDonacionBean) throws Exception {
		return donacionDao.insertarRegionDonacion(regionDonacionBean);
	}
	
	@Override
	public RegionDonacionBean eliminarRegionDonacion(RegionDonacionBean regionDonacionBean) throws Exception {
		return donacionDao.eliminarRegionDonacion(regionDonacionBean);
	}
	
	@Override
	public List<ItemBean> listarReporteDonacion(Integer idDonacion) throws Exception {
		return donacionDao.listarReporteDonacion(idDonacion);
	}
	
	@Override
	public DonacionesBean actualizarEstadoDonacion(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.actualizarEstadoDonacion(donacionesBean);
	}
	///////INGRESO
	@Override
	public List<DonacionesIngresoBean> listarIngresoDonaciones(DonacionesIngresoBean donacionesIngresoBean) throws Exception {
		return donacionDao.listarIngresoDonaciones(donacionesIngresoBean);
	}
	
	@Override
	public DonacionesIngresoBean obtenerCorrelativoOrdenIngreso(DonacionesIngresoBean donacionesIngresoBean) throws Exception {
		return donacionDao.obtenerCorrelativoOrdenIngreso(donacionesIngresoBean);
	}
	
	@Override
	public List<DonacionesBean> listarCodigoDonacion(ItemBean itemBean) throws Exception {
		return donacionDao.listarCodigoDonacion(itemBean);
	}
	
	@Override
	public List<ControlCalidadBean> listarControCalidad(ItemBean itemBean) throws Exception {
		return donacionDao.listarControCalidad(itemBean);
	}
	
	@Override
	public DonacionesIngresoBean insertarRegistroDonacionIngreso(DonacionesIngresoBean donacionesIngresoBean) throws Exception {
		return donacionDao.insertarRegistroDonacionIngreso(donacionesIngresoBean);
	}
	
	@Override
	public DonacionesIngresoBean obtenerDonacionIngresoXIdIngreso(Integer donacionIngreso) throws Exception {
		return donacionDao.obtenerDonacionIngresoXIdIngreso(donacionIngreso);
	}
	
	@Override
	public List<ProductoDonacionIngresoBean> listarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionIngresoBean) throws Exception {
		return donacionDao.listarProductoDonacionIngreso(productoDonacionIngresoBean);
	}
	
	@Override
	public ProductoDonacionIngresoBean insertarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionIngresoBean) throws Exception {
		return donacionDao.insertarProductoDonacionIngreso(productoDonacionIngresoBean);
	}
	
	@Override
	public ProductoDonacionIngresoBean actualizarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionIngresoBean) throws Exception {
		return donacionDao.actualizarProductoDonacionIngreso(productoDonacionIngresoBean);
	}
	
	@Override
	public ProductoDonacionIngresoBean eliminarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionIngresoBean) throws Exception {
		return donacionDao.eliminarProductoDonacionIngreso(productoDonacionIngresoBean);
	}
	
	@Override
	public DocumentoIngresoBean insertarDocumentoDonacionIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception {
		return donacionDao.insertarDocumentoDonacionIngreso(documentoIngresoBean);
	}
	
	@Override
	public DocumentoIngresoBean actualizarDocumentoDonacionIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception {
		return donacionDao.actualizarDocumentoDonacionIngreso(documentoIngresoBean);
	}
	
	@Override
	public List<DocumentoIngresoBean> listarDocumentoDonacionIngreso(DocumentoIngresoBean documentoDonacionIngresoBean) throws Exception {
		return donacionDao.listarDocumentoDonacionIngreso(documentoDonacionIngresoBean);
	}
	
	@Override
	public DocumentoIngresoBean eliminarDocumentoIngresoDonacion(DocumentoIngresoBean documentoIngresoBean) throws Exception {
		return donacionDao.eliminarDocumentoIngresoDonacion(documentoIngresoBean);
	}
	
	@Override
	public List<ProductoDonacionBean> listarProductosDonacion(DonacionesIngresoBean itemBean) throws Exception {
		return donacionDao.listarProductosDonacion(itemBean);
	}
	
	@Override
	public DonacionesIngresoBean actualizarRegistroDonacionIngreso(DonacionesIngresoBean donacionesIngresoBean) throws Exception {
		return donacionDao.actualizarRegistroDonacionIngreso(donacionesIngresoBean);
	}
	
	@Override
	public List<ItemBean> listarSalida(ItemBean itemBean) throws Exception {
		return donacionDao.listarSalida(itemBean);
	}
	
	///////////SALIDA////////////
	
	
	@Override
	public List<DonacionesSalidaBean> listarSalidaDonaciones(DonacionesSalidaBean donacionesSalidaBean) throws Exception {
		return donacionDao.listarSalidaDonaciones(donacionesSalidaBean);
	}
	
	@Override
	public DonacionesSalidaBean obtenerCorrelativoOrdenSalida(DonacionesSalidaBean donacionesSalidaBean) throws Exception {
		return donacionDao.obtenerCorrelativoOrdenSalida(donacionesSalidaBean);
	}
	
}

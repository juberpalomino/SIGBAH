package pe.com.sigbah.dao;

import java.util.List;

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

/**
 * @className: DonacionDao.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_DONACION.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public interface DonacionDao {

	/**
	 * @param donacionesBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<DonacionesBean> listarDonaciones(DonacionesBean donacionesBean) throws Exception;
	
	
	/**
	 * @param donacionesBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract DonacionesBean insertarDonaciones(DonacionesBean donacionesBean) throws Exception;
	/**
	 * @param donacionesBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract DonacionesBean actualizarDonaciones(DonacionesBean donacionesBean) throws Exception;

	/////////////////////////////////////////
	
	/**
	 * @param donacionesBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<DonacionesBean> listarSalidaDonaciones(DonacionesBean donacionesBean) throws Exception;
	
	/**
	 * @param controlCalidadBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract DonacionesBean obtenerCodigoDonativo(DonacionesBean donacionesBean) throws Exception;
	
	
	public abstract DonacionesBean insertarRegistroDonacion(DonacionesBean donacionesBean) throws Exception;

	public abstract DonacionesBean actualizarRegistroDonacion(DonacionesBean donacionesBean) throws Exception;
	
	public abstract List<ItemBean> listarDonadores(DonacionesBean donacionesBean) throws Exception;
	
	public abstract DonacionesBean obtenerDonacionXIdDonacion(Integer idDonacion) throws Exception;
	
	public abstract List<ItemBean> listarEstadoDonacionUsuario(ItemBean itemBean) throws Exception;
	
	public abstract List<ItemBean> mostrarEstadoDonacionUsuario(ItemBean itemBean) throws Exception;
	
	public abstract ProductoDonacionBean insertarProductoDonacion(ProductoDonacionBean productoDonacionBean) throws Exception;
	
	public abstract List<ProductoDonacionBean> listarProductoDonacion(ProductoDonacionBean productoDonacionBean) throws Exception;
	
	public abstract ProductoDonacionBean eliminarProductoDonacion(ProductoDonacionBean productoDonacionBean) throws Exception;
	
	public abstract DocumentoDonacionBean insertarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception;
	
	public abstract DocumentoDonacionBean actualizarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception;
	
	public abstract List<DocumentoDonacionBean> listarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception;
	
	public abstract DocumentoDonacionBean eliminarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception;
	
	public abstract List<RegionDonacionBean> listarRegionesXDonacion(RegionDonacionBean regionDonacionBean) throws Exception;
	
	public abstract RegionDonacionBean insertarRegionDonacion(RegionDonacionBean regionDonacionBean) throws Exception;
	
	public abstract RegionDonacionBean eliminarRegionDonacion(RegionDonacionBean regionDonacionBean) throws Exception;
	
	public abstract List<ItemBean> listarReporteDonacion(Integer idDonacion) throws Exception;
	
	public abstract DonacionesBean actualizarEstadoDonacion(DonacionesBean donacionesBean) throws Exception;
	
	//////////////////////////////////////////////////////////////////////////////
	
	public abstract List<DonacionesIngresoBean> listarIngresoDonaciones(DonacionesIngresoBean donacionesIngresoBean) throws Exception;


	/**
	 * @param donacionesIngresoBean
	 * @return
	 * @throws Exception
	 */
	public abstract DonacionesIngresoBean obtenerCorrelativoOrdenIngreso(DonacionesIngresoBean donacionesIngresoBean) throws Exception;


	/**
	 * @param itemBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<DonacionesBean> listarCodigoDonacion(ItemBean itemBean) throws Exception;


	/**
	 * @param itemBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<ControlCalidadBean> listarControCalidad(ItemBean itemBean) throws Exception;


	/**
	 * @param donacionesIngresoBean
	 * @return
	 * @throws Exception
	 */
	public abstract DonacionesIngresoBean insertarRegistroDonacionIngreso(DonacionesIngresoBean donacionesIngresoBean) throws Exception;


	/**
	 * @param idDonacion
	 * @return
	 * @throws Exception
	 */
	public abstract DonacionesIngresoBean obtenerDonacionIngresoXIdIngreso(Integer idDonacion) throws Exception;


	/**
	 * @param itemBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<ProductoDonacionBean> listarProductosDonacion(DonacionesIngresoBean itemBean) throws Exception;


	/**
	 * @param productoDonacionIngresoBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<ProductoDonacionIngresoBean> listarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionIngresoBean) throws Exception;


	/**
	 * @param productoDonacionIngresoBean
	 * @return
	 * @throws Exception
	 */
	public abstract ProductoDonacionIngresoBean insertarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionIngresoBean) throws Exception;


	/**
	 * @param productoDonacionBean
	 * @return
	 * @throws Exception
	 */
	public abstract ProductoDonacionIngresoBean eliminarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionBean) throws Exception;


	/**
	 * @param documentoDonacionBean
	 * @return
	 * @throws Exception
	 */
	public abstract DocumentoIngresoBean insertarDocumentoDonacionIngreso(DocumentoIngresoBean documentoDonacionBean) throws Exception;


	/**
	 * @param documentoDonacionBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<DocumentoIngresoBean> listarDocumentoDonacionIngreso(DocumentoIngresoBean documentoDonacionBean) throws Exception;


	/**
	 * @param documentoDonacionBean
	 * @return
	 * @throws Exception
	 */
	public abstract DocumentoIngresoBean eliminarDocumentoIngresoDonacion(DocumentoIngresoBean documentoDonacionBean) throws Exception;


	/**
	 * @param donacionesIngresoBean
	 * @return
	 * @throws Exception
	 */
	public abstract DonacionesIngresoBean actualizarRegistroDonacionIngreso(DonacionesIngresoBean donacionesIngresoBean) throws Exception;


	/**
	 * @param documentoDonacionBean
	 * @return
	 * @throws Exception
	 */
	public abstract DocumentoIngresoBean actualizarDocumentoDonacionIngreso(DocumentoIngresoBean documentoDonacionBean) throws Exception;


	/**
	 * @param productoDonacionIngresoBean
	 * @return
	 * @throws Exception
	 */
	public abstract ProductoDonacionIngresoBean actualizarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionIngresoBean) throws Exception;


	/**
	 * @param itemBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarSalida(ItemBean itemBean) throws Exception;


	/**
	 * @param donacionesSalidaBean
	 * @return
	 * @throws Exception
	 */
	public abstract List<DonacionesSalidaBean> listarSalidaDonaciones(DonacionesSalidaBean donacionesSalidaBean) throws Exception;


	/**
	 * @param donacionesIngresoBean
	 * @return
	 * @throws Exception
	 */
	public abstract DonacionesSalidaBean obtenerCorrelativoOrdenSalida(DonacionesSalidaBean donacionesIngresoBean) throws Exception;
	
	
	
}

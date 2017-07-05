package pe.com.sigbah.dao;

import java.util.List;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DetalleProductoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoControlCalidadBean;
import pe.com.sigbah.common.bean.OrdenCompraBean;
import pe.com.sigbah.common.bean.OrdenIngresoBean;
import pe.com.sigbah.common.bean.ProductoControlCalidadBean;

/**
 * @className: LogisticaDao.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_LOGISTICA.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public interface LogisticaDao {
	
	/**
	 * @param controlCalidadBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ControlCalidadBean> listarControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception;
	
	/**
	 * @param controlCalidadBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ControlCalidadBean obtenerCorrelativoControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception;
	
	/**
	 * @param controlCalidadBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ControlCalidadBean> listarAlmacenActivo(ControlCalidadBean controlCalidadBean) throws Exception;

	/**
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<OrdenCompraBean> listarOrdenCompra() throws Exception;
	
	/**
	 * @param controlCalidadBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract ControlCalidadBean insertarRegistroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception;

	/**
	 * @param controlCalidadBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract ControlCalidadBean actualizarRegistroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception;
	
	/**
	 * @param idControlCalidad
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ControlCalidadBean obtenerRegistroControlCalidad(Integer idControlCalidad) throws Exception;
	
	/**
	 * @param producto
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract List<ProductoControlCalidadBean> listarProductoControlCalidad(ProductoControlCalidadBean producto) throws Exception;

	/**
	 * @param productoControlCalidadBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract ProductoControlCalidadBean grabarProductoControlCalidad(ProductoControlCalidadBean productoControlCalidadBean) throws Exception;
	
	/**
	 * @param productoControlCalidadBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProductoControlCalidadBean eliminarProductoControlCalidad(ProductoControlCalidadBean productoControlCalidadBean) throws Exception;
	
	/**
	 * @param documentoControlCalidadBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract List<DocumentoControlCalidadBean> listarDocumentoControlCalidad(DocumentoControlCalidadBean documentoControlCalidadBean) throws Exception;

	/**
	 * @param documentoControlCalidadBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract DocumentoControlCalidadBean grabarDocumentoControlCalidad(DocumentoControlCalidadBean documentoControlCalidadBean) throws Exception;
	
	/**
	 * @param documentoControlCalidadBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract DocumentoControlCalidadBean eliminarDocumentoControlCalidad(DocumentoControlCalidadBean documentoControlCalidadBean) throws Exception;
	
	/**
	 * @param idControlCalidad
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<DetalleProductoControlCalidadBean> listarDetalleProductoControlCalidad(Integer idControlCalidad) throws Exception;
	
	/**
	 * @param ordenIngresoBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<OrdenIngresoBean> listarOrdenIngreso(OrdenIngresoBean ordenIngresoBean) throws Exception;
	
	/**
	 * @param ordenIngresoBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract OrdenIngresoBean obtenerCorrelativoOrdenIngreso(OrdenIngresoBean ordenIngresoBean) throws Exception;
	
	/**
	 * @param controlCalidadBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ControlCalidadBean> listarNroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception;
	
	/**
	 * @param ordenIngresoBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract OrdenIngresoBean grabarOrdenIngreso(OrdenIngresoBean ordenIngresoBean) throws Exception;
	
}

package pe.com.sigbah.service;

import java.util.List;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DetalleProductoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoIngresoBean;
import pe.com.sigbah.common.bean.LoteProductoBean;
import pe.com.sigbah.common.bean.OrdenCompraBean;
import pe.com.sigbah.common.bean.OrdenIngresoBean;
import pe.com.sigbah.common.bean.ProductoControlCalidadBean;
import pe.com.sigbah.common.bean.ProductoIngresoBean;

/**
 * @className: LogisticaService.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_LOGISTICA.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public interface LogisticaService {
	
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
	
	/**
	 * @param idIngreso
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract OrdenIngresoBean obtenerRegistroOrdenIngreso(Integer idIngreso) throws Exception;
	
	/**
	 * @param productoIngresoBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract List<ProductoIngresoBean> listarProductoIngreso(ProductoIngresoBean productoIngresoBean) throws Exception;

	/**
	 * @param productoIngresoBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract ProductoIngresoBean grabarProductoIngreso(ProductoIngresoBean productoIngresoBean) throws Exception;
	
	/**
	 * @param productoIngresoBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProductoIngresoBean eliminarProductoIngreso(ProductoIngresoBean productoIngresoBean) throws Exception;
	
	/**
	 * @param loteProductoBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<LoteProductoBean> listarLoteProductos(LoteProductoBean loteProductoBean) throws Exception;

	/**
	 * @param documentoIngresoBean
	 * @return Lista de registros.
	 * @throws Exception 
	 */
	public abstract List<DocumentoIngresoBean> listarDocumentoIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception;

	/**
	 * @param documentoIngresoBean
	 * @return Lista de registros.
	 * @throws Exception 
	 */
	public abstract DocumentoIngresoBean grabarDocumentoIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception;

	/**
	 * @param documentoIngresoBean
	 * @return Lista de registros.
	 * @throws Exception 
	 */
	public abstract DocumentoIngresoBean eliminarDocumentoIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception;
	
}

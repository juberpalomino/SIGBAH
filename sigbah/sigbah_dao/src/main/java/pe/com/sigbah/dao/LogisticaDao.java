package pe.com.sigbah.dao;

import java.util.List;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DetalleProductoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoIngresoBean;
import pe.com.sigbah.common.bean.DocumentoProyectoManifiestoBean;
import pe.com.sigbah.common.bean.DocumentoSalidaBean;
import pe.com.sigbah.common.bean.GuiaRemisionBean;
import pe.com.sigbah.common.bean.LoteProductoBean;
import pe.com.sigbah.common.bean.ManifiestoVehiculoBean;
import pe.com.sigbah.common.bean.OrdenCompraBean;
import pe.com.sigbah.common.bean.OrdenIngresoBean;
import pe.com.sigbah.common.bean.OrdenSalidaBean;
import pe.com.sigbah.common.bean.ProductoControlCalidadBean;
import pe.com.sigbah.common.bean.ProductoIngresoBean;
import pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean;
import pe.com.sigbah.common.bean.ProductoSalidaBean;
import pe.com.sigbah.common.bean.ProyectoManifiestoBean;

/**
 * @className: LogisticaDao.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_LOGISTICA.
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
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
	 * @return Lista de registros.
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
	 * @return Lista de registros.
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
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract DocumentoIngresoBean grabarDocumentoIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception;

	/**
	 * @param documentoIngresoBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract DocumentoIngresoBean eliminarDocumentoIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception;
	
	/**
	 * @param ordenSalidaBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<OrdenSalidaBean> listarOrdenSalida(OrdenSalidaBean ordenSalidaBean) throws Exception;
	
	/**
	 * @param ordenSalidaBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract OrdenSalidaBean obtenerCorrelativoOrdenSalida(OrdenSalidaBean ordenSalidaBean) throws Exception;
	
	/**
	 * @param ordenSalidaBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract OrdenSalidaBean grabarOrdenSalida(OrdenSalidaBean ordenSalidaBean) throws Exception;
	
	/**
	 * @param idSalida
	 * @param anio 
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract OrdenSalidaBean obtenerRegistroOrdenSalida(Integer idSalida, String anio) throws Exception;
	
	/**
	 * @param productoSalidaBean
	 * @return Lista de registros.
	 * @throws Exception 
	 */
	public abstract List<ProductoSalidaBean> listarProductoSalida(ProductoSalidaBean productoSalidaBean) throws Exception;

	/**
	 * @param productoSalidaBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract ProductoSalidaBean grabarProductoSalida(ProductoSalidaBean productoSalidaBean) throws Exception;
	
	/**
	 * @param productoSalidaBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProductoSalidaBean eliminarProductoSalida(ProductoSalidaBean productoSalidaBean) throws Exception;

	/**
	 * @param documentoSalidaBean
	 * @return Lista de registros.
	 * @throws Exception 
	 */
	public abstract List<DocumentoSalidaBean> listarDocumentoSalida(DocumentoSalidaBean documentoSalidaBean) throws Exception;

	/**
	 * @param documentoSalidaBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract DocumentoSalidaBean grabarDocumentoSalida(DocumentoSalidaBean documentoSalidaBean) throws Exception;

	/**
	 * @param documentoSalidaBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract DocumentoSalidaBean eliminarDocumentoSalida(DocumentoSalidaBean documentoSalidaBean) throws Exception;
	
	/**
	 * @param proyectoManifiestoBean
	 * @return Lista de registros.
	 * @throws Exception 
	 */
	public abstract List<ProyectoManifiestoBean> listarManifiesto(ProyectoManifiestoBean proyectoManifiestoBean) throws Exception;
	
	/**
	 * @param proyectoManifiestoBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ProyectoManifiestoBean> listarProyectoManifiesto(ProyectoManifiestoBean proyectoManifiestoBean) throws Exception;
	
	/**
	 * @param proyectoManifiestoBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProyectoManifiestoBean obtenerCorrelativoProyectoManifiesto(ProyectoManifiestoBean proyectoManifiestoBean) throws Exception;
	
	/**
	 * @param proyectoManifiestoBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract ProyectoManifiestoBean grabarProyectoManifiesto(ProyectoManifiestoBean proyectoManifiestoBean) throws Exception;
	
	/**
	 * @param idProyecto
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProyectoManifiestoBean obtenerRegistroProyectoManifiesto(Integer idProyecto) throws Exception;
	
	/**
	 * @param productoProyectoManifiestoBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract List<ProductoProyectoManifiestoBean> listarProductoProyectoManifiesto(ProductoProyectoManifiestoBean productoProyectoManifiestoBean) throws Exception;

	/**
	 * @param productoProyectoManifiestoBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract ProductoProyectoManifiestoBean grabarProductoProyectoManifiesto(ProductoProyectoManifiestoBean productoProyectoManifiestoBean) throws Exception;
	
	/**
	 * @param productoProyectoManifiestoBean
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract ProductoProyectoManifiestoBean eliminarProductoProyectoManifiesto(ProductoProyectoManifiestoBean productoProyectoManifiestoBean) throws Exception;

	/**
	 * @param documentoProyectoManifiestoBean
	 * @return Lista de registros.
	 * @throws Exception 
	 */
	public abstract List<DocumentoProyectoManifiestoBean> listarDocumentoProyectoManifiesto(DocumentoProyectoManifiestoBean documentoProyectoManifiestoBean) throws Exception;

	/**
	 * @param documentoProyectoManifiestoBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract DocumentoProyectoManifiestoBean grabarDocumentoProyectoManifiesto(DocumentoProyectoManifiestoBean documentoProyectoManifiestoBean) throws Exception;

	/**
	 * @param documentoProyectoManifiestoBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract DocumentoProyectoManifiestoBean eliminarDocumentoProyectoManifiesto(DocumentoProyectoManifiestoBean documentoProyectoManifiestoBean) throws Exception;
	
	/**
	 * @param idProyecto
	 * @return Objeto.
	 * @throws Exception
	 */
	public abstract int verificarProductosProgramacion(Integer idProyecto) throws Exception;

	/**
	 * @param proyectoManifiestoBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract String procesarManifiestoProducto(ProyectoManifiestoBean proyectoManifiestoBean) throws Exception;
	
	/**
	 * @param manifiestoVehiculoBean
	 * @return Lista de registros.
	 * @throws Exception 
	 */
	public abstract List<ManifiestoVehiculoBean> listarManifiestoVehiculo(ManifiestoVehiculoBean manifiestoVehiculoBean) throws Exception;

	/**
	 * @param manifiestoVehiculoBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract ManifiestoVehiculoBean procesarManifiestoVehiculo(ManifiestoVehiculoBean manifiestoVehiculoBean) throws Exception;

	/**
	 * @param guiaRemisionBean
	 * @return Lista de registros.
	 * @throws Exception 
	 */
	public abstract List<GuiaRemisionBean> listarGuiaRemision(GuiaRemisionBean guiaRemisionBean) throws Exception;

	/**
	 * @param idGuiaRemision
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract GuiaRemisionBean obtenerRegistroGuiaRemision(Integer idGuiaRemision) throws Exception;

	/**
	 * @param guiaRemisionBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract GuiaRemisionBean insertarGuiaRemision(GuiaRemisionBean guiaRemisionBean) throws Exception;
	
	/**
	 * @param guiaRemisionBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract GuiaRemisionBean anularGuiaRemision(GuiaRemisionBean guiaRemisionBean) throws Exception;
	
	/**
	 * @param guiaRemisionBean
	 * @return Objeto.
	 * @throws Exception 
	 */
	public abstract GuiaRemisionBean actualizarGuiaRemision(GuiaRemisionBean guiaRemisionBean) throws Exception;
	
}

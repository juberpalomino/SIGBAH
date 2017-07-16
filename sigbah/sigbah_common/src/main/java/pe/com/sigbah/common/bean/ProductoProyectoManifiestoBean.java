package pe.com.sigbah.common.bean;

import java.math.BigDecimal;

/**
 * @className: ProductoSalidaBean.java
 * @description: 
 * @date: 25 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
public class ProductoProyectoManifiestoBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	private Integer idDetalleProyecto;
	private Integer idProyectoManifiesto;
	private Integer idCategoria;
	private Integer idProducto;
	private String nombreProducto;
	private String nombreUnidad;
	private String nombreEnvase;
	private BigDecimal cantidad;
	private BigDecimal pesoUnitarioBruto;
	private BigDecimal pesoTotal;
	private BigDecimal volumenUnitario;
	private BigDecimal volumenTotal;
	private BigDecimal costoBruto;
	private BigDecimal costoTotal;
	private String arrIdDetalleProyecto;
	private Integer idAlmacen;
	
	
	/**
	 * 
	 */
	public ProductoProyectoManifiestoBean() {
		super();
	}

	/**
	 * @param idDetalleProyecto
	 */
	public ProductoProyectoManifiestoBean(Integer idDetalleProyecto) {
		super();
		this.idDetalleProyecto = idDetalleProyecto;
	}

	/**
	 * @return the idDetalleProyecto
	 */
	public Integer getIdDetalleProyecto() {
		return idDetalleProyecto;
	}

	/**
	 * @param idDetalleProyecto the idDetalleProyecto to set
	 */
	public void setIdDetalleProyecto(Integer idDetalleProyecto) {
		this.idDetalleProyecto = idDetalleProyecto;
	}

	/**
	 * @return the idProyectoManifiesto
	 */
	public Integer getIdProyectoManifiesto() {
		return idProyectoManifiesto;
	}

	/**
	 * @param idProyectoManifiesto the idProyectoManifiesto to set
	 */
	public void setIdProyectoManifiesto(Integer idProyectoManifiesto) {
		this.idProyectoManifiesto = idProyectoManifiesto;
	}

	/**
	 * @return the idCategoria
	 */
	public Integer getIdCategoria() {
		return idCategoria;
	}

	/**
	 * @param idCategoria the idCategoria to set
	 */
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * @return the idProducto
	 */
	public Integer getIdProducto() {
		return idProducto;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * @return the nombreProducto
	 */
	public String getNombreProducto() {
		return nombreProducto;
	}

	/**
	 * @param nombreProducto the nombreProducto to set
	 */
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	/**
	 * @return the nombreUnidad
	 */
	public String getNombreUnidad() {
		return nombreUnidad;
	}

	/**
	 * @param nombreUnidad the nombreUnidad to set
	 */
	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

	/**
	 * @return the cantidad
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the nombreEnvase
	 */
	public String getNombreEnvase() {
		return nombreEnvase;
	}

	/**
	 * @param nombreEnvase the nombreEnvase to set
	 */
	public void setNombreEnvase(String nombreEnvase) {
		this.nombreEnvase = nombreEnvase;
	}

	/**
	 * @return the pesoUnitarioBruto
	 */
	public BigDecimal getPesoUnitarioBruto() {
		return pesoUnitarioBruto;
	}

	/**
	 * @param pesoUnitarioBruto the pesoUnitarioBruto to set
	 */
	public void setPesoUnitarioBruto(BigDecimal pesoUnitarioBruto) {
		this.pesoUnitarioBruto = pesoUnitarioBruto;
	}

	/**
	 * @return the pesoTotal
	 */
	public BigDecimal getPesoTotal() {
		return pesoTotal;
	}

	/**
	 * @param pesoTotal the pesoTotal to set
	 */
	public void setPesoTotal(BigDecimal pesoTotal) {
		this.pesoTotal = pesoTotal;
	}

	/**
	 * @return the volumenUnitario
	 */
	public BigDecimal getVolumenUnitario() {
		return volumenUnitario;
	}

	/**
	 * @param volumenUnitario the volumenUnitario to set
	 */
	public void setVolumenUnitario(BigDecimal volumenUnitario) {
		this.volumenUnitario = volumenUnitario;
	}

	/**
	 * @return the volumenTotal
	 */
	public BigDecimal getVolumenTotal() {
		return volumenTotal;
	}

	/**
	 * @param volumenTotal the volumenTotal to set
	 */
	public void setVolumenTotal(BigDecimal volumenTotal) {
		this.volumenTotal = volumenTotal;
	}

	/**
	 * @return the costoBruto
	 */
	public BigDecimal getCostoBruto() {
		return costoBruto;
	}

	/**
	 * @param costoBruto the costoBruto to set
	 */
	public void setCostoBruto(BigDecimal costoBruto) {
		this.costoBruto = costoBruto;
	}

	/**
	 * @return the costoTotal
	 */
	public BigDecimal getCostoTotal() {
		return costoTotal;
	}

	/**
	 * @param costoTotal the costoTotal to set
	 */
	public void setCostoTotal(BigDecimal costoTotal) {
		this.costoTotal = costoTotal;
	}

	/**
	 * @return the arrIdDetalleProyecto
	 */
	public String getArrIdDetalleProyecto() {
		return arrIdDetalleProyecto;
	}

	/**
	 * @param arrIdDetalleProyecto the arrIdDetalleProyecto to set
	 */
	public void setArrIdDetalleProyecto(String arrIdDetalleProyecto) {
		this.arrIdDetalleProyecto = arrIdDetalleProyecto;
	}

	/**
	 * @return the idAlmacen
	 */
	public Integer getIdAlmacen() {
		return idAlmacen;
	}

	/**
	 * @param idAlmacen the idAlmacen to set
	 */
	public void setIdAlmacen(Integer idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

}

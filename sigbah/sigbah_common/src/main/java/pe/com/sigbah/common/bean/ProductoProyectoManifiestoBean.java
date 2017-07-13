package pe.com.sigbah.common.bean;

import java.math.BigDecimal;

/**
 * @className: ProductoSalidaBean.java
 * @description: 
 * @date: 25 de jun. de 2017
 * @author: SUMERIO.
 */
public class ProductoProyectoManifiestoBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	private Integer idDetalleSalida;
	private Integer idSalida;
	private Integer idCategoria;
	private Integer idProducto;
	private String nombreProducto;
	private String nombreUnidad;
	private String nombreEnvase;
	private BigDecimal cantidad;
	private BigDecimal precioUnitario;
	private BigDecimal importeTotal;
	private BigDecimal pesoNetoTotal;
	private BigDecimal pesoBrutoTotal;
	private BigDecimal pesoUnitarioNeto;
	private BigDecimal pesoUnitarioBruto;
	private String nroLote;
	private String indControl;
	private String arrIdDetalleSalida;
	
	
	/**
	 * 
	 */
	public ProductoProyectoManifiestoBean() {
		super();
	}

	/**
	 * @param idDetalleSalida
	 */
	public ProductoProyectoManifiestoBean(Integer idDetalleSalida) {
		super();
		this.idDetalleSalida = idDetalleSalida;
	}

	/**
	 * @return the idDetalleSalida
	 */
	public Integer getIdDetalleSalida() {
		return idDetalleSalida;
	}

	/**
	 * @param idDetalleSalida the idDetalleSalida to set
	 */
	public void setIdDetalleSalida(Integer idDetalleSalida) {
		this.idDetalleSalida = idDetalleSalida;
	}

	/**
	 * @return the idSalida
	 */
	public Integer getIdSalida() {
		return idSalida;
	}

	/**
	 * @param idSalida the idSalida to set
	 */
	public void setIdSalida(Integer idSalida) {
		this.idSalida = idSalida;
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
	 * @return the precioUnitario
	 */
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	/**
	 * @param precioUnitario the precioUnitario to set
	 */
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	/**
	 * @return the importeTotal
	 */
	public BigDecimal getImporteTotal() {
		return importeTotal;
	}

	/**
	 * @param importeTotal the importeTotal to set
	 */
	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

	/**
	 * @return the pesoNetoTotal
	 */
	public BigDecimal getPesoNetoTotal() {
		return pesoNetoTotal;
	}

	/**
	 * @param pesoNetoTotal the pesoNetoTotal to set
	 */
	public void setPesoNetoTotal(BigDecimal pesoNetoTotal) {
		this.pesoNetoTotal = pesoNetoTotal;
	}

	/**
	 * @return the pesoBrutoTotal
	 */
	public BigDecimal getPesoBrutoTotal() {
		return pesoBrutoTotal;
	}

	/**
	 * @param pesoBrutoTotal the pesoBrutoTotal to set
	 */
	public void setPesoBrutoTotal(BigDecimal pesoBrutoTotal) {
		this.pesoBrutoTotal = pesoBrutoTotal;
	}
	
	/**
	 * @return the pesoUnitarioNeto
	 */
	public BigDecimal getPesoUnitarioNeto() {
		return pesoUnitarioNeto;
	}

	/**
	 * @param pesoUnitarioNeto the pesoUnitarioNeto to set
	 */
	public void setPesoUnitarioNeto(BigDecimal pesoUnitarioNeto) {
		this.pesoUnitarioNeto = pesoUnitarioNeto;
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
	 * @return the nroLote
	 */
	public String getNroLote() {
		return nroLote;
	}

	/**
	 * @param nroLote the nroLote to set
	 */
	public void setNroLote(String nroLote) {
		this.nroLote = nroLote;
	}
	
	/**
	 * @return the indControl
	 */
	public String getIndControl() {
		return indControl;
	}
	
	/**
	 * @param indControl the indControl to set
	 */
	public void setIndControl(String indControl) {
		this.indControl = indControl;
	}

	/**
	 * @return the arrIdDetalleSalida
	 */
	public String getArrIdDetalleSalida() {
		return arrIdDetalleSalida;
	}

	/**
	 * @param arrIdDetalleSalida the arrIdDetalleSalida to set
	 */
	public void setArrIdDetalleSalida(String arrIdDetalleSalida) {
		this.arrIdDetalleSalida = arrIdDetalleSalida;
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
	
}

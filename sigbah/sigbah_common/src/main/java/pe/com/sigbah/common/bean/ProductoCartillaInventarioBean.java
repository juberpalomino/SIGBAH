package pe.com.sigbah.common.bean;

import java.math.BigDecimal;

/**
 * @className: ProductoCartillaInventarioBean.java
 * @description: 
 * @date: 25 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
public class ProductoCartillaInventarioBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	private Integer idDetalleCartilla;
	private Integer idCartilla;
	private String tipoOrigen;
	private Integer idDdi;
	private Integer idAlmacen;
	private Integer idProducto;
	private String codigoProducto;
	private String nombreProducto;
	private String nombreUnidad;
	private String nombreEnvase;
	private String nroLote;
	private String nave;	
	private BigDecimal cantidadStock;
	private BigDecimal precioUnitario;
	private BigDecimal item;
	private String arrIdDetalleCartillaInventario;	
	private BigDecimal stockFisico;
	private BigDecimal stockSistema;
	private BigDecimal diferencia;
	private String tipo;
	private String documentoAjuste;
	private String fechaVencimiento;
	
	
	/**
	 * 
	 */
	public ProductoCartillaInventarioBean() {
		super();
	}
	
	/**
	 * @param idDetalleCartilla
	 */
	public ProductoCartillaInventarioBean(Integer idDetalleCartilla) {
		super();
		this.idDetalleCartilla = idDetalleCartilla;
	}

	/**
	 * @return the idDetalleCartilla
	 */
	public Integer getIdDetalleCartilla() {
		return idDetalleCartilla;
	}

	/**
	 * @param idDetalleCartilla the idDetalleCartilla to set
	 */
	public void setIdDetalleCartilla(Integer idDetalleCartilla) {
		this.idDetalleCartilla = idDetalleCartilla;
	}

	/**
	 * @return the idCartilla
	 */
	public Integer getIdCartilla() {
		return idCartilla;
	}

	/**
	 * @param idCartilla the idCartilla to set
	 */
	public void setIdCartilla(Integer idCartilla) {
		this.idCartilla = idCartilla;
	}

	/**
	 * @return the tipoOrigen
	 */
	public String getTipoOrigen() {
		return tipoOrigen;
	}

	/**
	 * @param tipoOrigen the tipoOrigen to set
	 */
	public void setTipoOrigen(String tipoOrigen) {
		this.tipoOrigen = tipoOrigen;
	}

	/**
	 * @return the idDdi
	 */
	public Integer getIdDdi() {
		return idDdi;
	}

	/**
	 * @param idDdi the idDdi to set
	 */
	public void setIdDdi(Integer idDdi) {
		this.idDdi = idDdi;
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
	 * @return the codigoProducto
	 */
	public String getCodigoProducto() {
		return codigoProducto;
	}

	/**
	 * @param codigoProducto the codigoProducto to set
	 */
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
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
	 * @return the nave
	 */
	public String getNave() {
		return nave;
	}

	/**
	 * @param nave the nave to set
	 */
	public void setNave(String nave) {
		this.nave = nave;
	}

	/**
	 * @return the cantidadStock
	 */
	public BigDecimal getCantidadStock() {
		return cantidadStock;
	}

	/**
	 * @param cantidadStock the cantidadStock to set
	 */
	public void setCantidadStock(BigDecimal cantidadStock) {
		this.cantidadStock = cantidadStock;
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
	 * @return the item
	 */
	public BigDecimal getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(BigDecimal item) {
		this.item = item;
	}

	/**
	 * @return the arrIdDetalleCartillaInventario
	 */
	public String getArrIdDetalleCartillaInventario() {
		return arrIdDetalleCartillaInventario;
	}

	/**
	 * @param arrIdDetalleCartillaInventario the arrIdDetalleCartillaInventario to set
	 */
	public void setArrIdDetalleCartillaInventario(String arrIdDetalleCartillaInventario) {
		this.arrIdDetalleCartillaInventario = arrIdDetalleCartillaInventario;
	}

	/**
	 * @return the stockFisico
	 */
	public BigDecimal getStockFisico() {
		return stockFisico;
	}

	/**
	 * @param stockFisico the stockFisico to set
	 */
	public void setStockFisico(BigDecimal stockFisico) {
		this.stockFisico = stockFisico;
	}

	/**
	 * @return the stockSistema
	 */
	public BigDecimal getStockSistema() {
		return stockSistema;
	}

	/**
	 * @param stockSistema the stockSistema to set
	 */
	public void setStockSistema(BigDecimal stockSistema) {
		this.stockSistema = stockSistema;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the documentoAjuste
	 */
	public String getDocumentoAjuste() {
		return documentoAjuste;
	}

	/**
	 * @param documentoAjuste the documentoAjuste to set
	 */
	public void setDocumentoAjuste(String documentoAjuste) {
		this.documentoAjuste = documentoAjuste;
	}

	/**
	 * @return the diferencia
	 */
	public BigDecimal getDiferencia() {
		return diferencia;
	}

	/**
	 * @param diferencia the diferencia to set
	 */
	public void setDiferencia(BigDecimal diferencia) {
		this.diferencia = diferencia;
	}

	/**
	 * @return the fechaVencimiento
	 */
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param fechaVencimiento the fechaVencimiento to set
	 */
	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

}

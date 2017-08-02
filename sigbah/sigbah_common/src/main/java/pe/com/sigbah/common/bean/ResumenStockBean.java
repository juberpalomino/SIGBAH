package pe.com.sigbah.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @className: ResumenStockBean.java
 * @description: 
 * @date: 9 de jul. de 2017
 * @author: Junior Huaman Flores.
 */
public class ResumenStockBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tipoOrigen;
	private Integer idDdi;
	private Integer idAlmacen;
	private Integer idProducto;
	private String nombreProducto;
	private BigDecimal cantidad;
	private BigDecimal totalTm;
	private BigDecimal saldoTm;
	
	
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
	 * @return the totalTm
	 */
	public BigDecimal getTotalTm() {
		return totalTm;
	}
	/**
	 * @param totalTm the totalTm to set
	 */
	public void setTotalTm(BigDecimal totalTm) {
		this.totalTm = totalTm;
	}
	/**
	 * @return the saldoTm
	 */
	public BigDecimal getSaldoTm() {
		return saldoTm;
	}
	/**
	 * @param saldoTm the saldoTm to set
	 */
	public void setSaldoTm(BigDecimal saldoTm) {
		this.saldoTm = saldoTm;
	}
	
}

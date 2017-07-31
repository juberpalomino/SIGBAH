package pe.com.sigbah.common.bean;

import java.math.BigDecimal;

/**
 * @className: ProductoBean.java
 * @description: Clase ProductoBean.
 * @date: 18/02/2015 22:26:30
 * @author: Junior Huaman Flores.
 */
public class ProductoRacionBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	
	private Integer idDetaRacion;
	private Integer idRacion;
	private Integer fkIdProducto;
	private BigDecimal pesoUnitarioPres;
	private BigDecimal cantRacionKg;
	private String codAnio;
	
	
	
	/**
	 * 
	 */
	public ProductoRacionBean() {
		super();
	}



	/**
	 * @return the idDetaRacion
	 */
	public Integer getIdDetaRacion() {
		return idDetaRacion;
	}



	/**
	 * @param idDetaRacion the idDetaRacion to set
	 */
	public void setIdDetaRacion(Integer idDetaRacion) {
		this.idDetaRacion = idDetaRacion;
	}



	/**
	 * @return the idRacion
	 */
	public Integer getIdRacion() {
		return idRacion;
	}



	/**
	 * @param idRacion the idRacion to set
	 */
	public void setIdRacion(Integer idRacion) {
		this.idRacion = idRacion;
	}



	/**
	 * @return the fkIdProducto
	 */
	public Integer getFkIdProducto() {
		return fkIdProducto;
	}



	/**
	 * @param fkIdProducto the fkIdProducto to set
	 */
	public void setFkIdProducto(Integer fkIdProducto) {
		this.fkIdProducto = fkIdProducto;
	}



	/**
	 * @return the pesoUnitarioPres
	 */
	public BigDecimal getPesoUnitarioPres() {
		return pesoUnitarioPres;
	}



	/**
	 * @param pesoUnitarioPres the pesoUnitarioPres to set
	 */
	public void setPesoUnitarioPres(BigDecimal pesoUnitarioPres) {
		this.pesoUnitarioPres = pesoUnitarioPres;
	}



	/**
	 * @return the cantRacionKg
	 */
	public BigDecimal getCantRacionKg() {
		return cantRacionKg;
	}



	/**
	 * @param cantRacionKg the cantRacionKg to set
	 */
	public void setCantRacionKg(BigDecimal cantRacionKg) {
		this.cantRacionKg = cantRacionKg;
	}



	/**
	 * @return the codAnio
	 */
	public String getCodAnio() {
		return codAnio;
	}



	/**
	 * @param codAnio the codAnio to set
	 */
	public void setCodAnio(String codAnio) {
		this.codAnio = codAnio;
	}
	
	
}
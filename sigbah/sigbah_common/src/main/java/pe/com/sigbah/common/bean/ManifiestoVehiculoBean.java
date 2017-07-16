package pe.com.sigbah.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @className: ManifiestoVehiculoBean.java
 * @description: Clase ProyectoManifiestoBean.
 * @date: 18/02/2015 22:26:30
 * @author: Junior Huaman Flores.
 */
public class ManifiestoVehiculoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idProyectoManifiesto;
	private String flagVehiculo;
	private Integer idTipoCamion;
	private String descripcionCamion;
	private BigDecimal tonelaje;
	private BigDecimal volumen;
	private Integer cantidadVehiculos;


	/**
	 * 
	 */
	public ManifiestoVehiculoBean() {
		super();
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
	 * @return the flagVehiculo
	 */
	public String getFlagVehiculo() {
		return flagVehiculo;
	}

	/**
	 * @param flagVehiculo the flagVehiculo to set
	 */
	public void setFlagVehiculo(String flagVehiculo) {
		this.flagVehiculo = flagVehiculo;
	}

	/**
	 * @return the idTipoCamion
	 */
	public Integer getIdTipoCamion() {
		return idTipoCamion;
	}

	/**
	 * @param idTipoCamion the idTipoCamion to set
	 */
	public void setIdTipoCamion(Integer idTipoCamion) {
		this.idTipoCamion = idTipoCamion;
	}

	/**
	 * @return the descripcionCamion
	 */
	public String getDescripcionCamion() {
		return descripcionCamion;
	}

	/**
	 * @param descripcionCamion the descripcionCamion to set
	 */
	public void setDescripcionCamion(String descripcionCamion) {
		this.descripcionCamion = descripcionCamion;
	}

	/**
	 * @return the tonelaje
	 */
	public BigDecimal getTonelaje() {
		return tonelaje;
	}

	/**
	 * @param tonelaje the tonelaje to set
	 */
	public void setTonelaje(BigDecimal tonelaje) {
		this.tonelaje = tonelaje;
	}

	/**
	 * @return the volumen
	 */
	public BigDecimal getVolumen() {
		return volumen;
	}

	/**
	 * @param volumen the volumen to set
	 */
	public void setVolumen(BigDecimal volumen) {
		this.volumen = volumen;
	}

	/**
	 * @return the cantidadVehiculos
	 */
	public Integer getCantidadVehiculos() {
		return cantidadVehiculos;
	}

	/**
	 * @param cantidadVehiculos the cantidadVehiculos to set
	 */
	public void setCantidadVehiculos(Integer cantidadVehiculos) {
		this.cantidadVehiculos = cantidadVehiculos;
	}

}
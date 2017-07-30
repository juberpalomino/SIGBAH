package pe.com.sigbah.common.bean;

import java.io.Serializable;

/**
 * @className: RacionOperativaBean.java
 * @description: 
 * @date: 29/07/2017
 * @author: Junior Huaman Flores.
 */
public class RacionOperativaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idRacionOperativa;
	private String codigoRacion;
	private String nombreRacion;
	private String codigoRacionOperativa;
	
	/**
	 * @return the idRacionOperativa
	 */
	public Integer getIdRacionOperativa() {
		return idRacionOperativa;
	}
	/**
	 * @param idRacionOperativa the idRacionOperativa to set
	 */
	public void setIdRacionOperativa(Integer idRacionOperativa) {
		this.idRacionOperativa = idRacionOperativa;
	}
	/**
	 * @return the codigoRacion
	 */
	public String getCodigoRacion() {
		return codigoRacion;
	}
	/**
	 * @param codigoRacion the codigoRacion to set
	 */
	public void setCodigoRacion(String codigoRacion) {
		this.codigoRacion = codigoRacion;
	}
	/**
	 * @return the nombreRacion
	 */
	public String getNombreRacion() {
		return nombreRacion;
	}
	/**
	 * @param nombreRacion the nombreRacion to set
	 */
	public void setNombreRacion(String nombreRacion) {
		this.nombreRacion = nombreRacion;
	}
	/**
	 * @return the codigoRacionOperativa
	 */
	public String getCodigoRacionOperativa() {
		return codigoRacionOperativa;
	}
	/**
	 * @param codigoRacionOperativa the codigoRacionOperativa to set
	 */
	public void setCodigoRacionOperativa(String codigoRacionOperativa) {
		this.codigoRacionOperativa = codigoRacionOperativa;
	}
	
}

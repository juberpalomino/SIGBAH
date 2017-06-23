package pe.com.sigbah.common.bean;

import java.util.Date;

/**
 * @className: ControlCalidadBean.java
 * @description: 
 * @date: 23 de jun. de 2017
 * @author: SUMERIO.
 */
public class ControlCalidadBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	private Integer idControlCalidad;
	private String codigoAnio;
	private String codigoMes;
	private String nombreAlmacen;
	private String nroRepControlCalidad;
	private Date fechaEmision;
	private String tipoControlCalidad;
	private String nombreEstado;
	private String codigoDdi;
	private String codigoAlmacen;
	
	
	/**
	 * @return the idControlCalidad
	 */
	public Integer getIdControlCalidad() {
		return idControlCalidad;
	}
	/**
	 * @param idControlCalidad the idControlCalidad to set
	 */
	public void setIdControlCalidad(Integer idControlCalidad) {
		this.idControlCalidad = idControlCalidad;
	}
	/**
	 * @return the codigoAnio
	 */
	public String getCodigoAnio() {
		return codigoAnio;
	}
	/**
	 * @param codigoAnio the codigoAnio to set
	 */
	public void setCodigoAnio(String codigoAnio) {
		this.codigoAnio = codigoAnio;
	}
	/**
	 * @return the codigoMes
	 */
	public String getCodigoMes() {
		return codigoMes;
	}
	/**
	 * @param codigoMes the codigoMes to set
	 */
	public void setCodigoMes(String codigoMes) {
		this.codigoMes = codigoMes;
	}
	/**
	 * @return the nombreAlmacen
	 */
	public String getNombreAlmacen() {
		return nombreAlmacen;
	}
	/**
	 * @param nombreAlmacen the nombreAlmacen to set
	 */
	public void setNombreAlmacen(String nombreAlmacen) {
		this.nombreAlmacen = nombreAlmacen;
	}
	/**
	 * @return the nroRepControlCalidad
	 */
	public String getNroRepControlCalidad() {
		return nroRepControlCalidad;
	}
	/**
	 * @param nroRepControlCalidad the nroRepControlCalidad to set
	 */
	public void setNroRepControlCalidad(String nroRepControlCalidad) {
		this.nroRepControlCalidad = nroRepControlCalidad;
	}
	/**
	 * @return the fechaEmision
	 */
	public Date getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaEmision the fechaEmision to set
	 */
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	/**
	 * @return the tipoControlCalidad
	 */
	public String getTipoControlCalidad() {
		return tipoControlCalidad;
	}
	/**
	 * @param tipoControlCalidad the tipoControlCalidad to set
	 */
	public void setTipoControlCalidad(String tipoControlCalidad) {
		this.tipoControlCalidad = tipoControlCalidad;
	}
	/**
	 * @return the nombreEstado
	 */
	public String getNombreEstado() {
		return nombreEstado;
	}
	/**
	 * @param nombreEstado the nombreEstado to set
	 */
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	/**
	 * @return the codigoDdi
	 */
	public String getCodigoDdi() {
		return codigoDdi;
	}
	/**
	 * @param codigoDdi the codigoDdi to set
	 */
	public void setCodigoDdi(String codigoDdi) {
		this.codigoDdi = codigoDdi;
	}
	/**
	 * @return the codigoAlmacen
	 */
	public String getCodigoAlmacen() {
		return codigoAlmacen;
	}
	/**
	 * @param codigoAlmacen the codigoAlmacen to set
	 */
	public void setCodigoAlmacen(String codigoAlmacen) {
		this.codigoAlmacen = codigoAlmacen;
	}

}

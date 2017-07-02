package pe.com.sigbah.common.bean;

/**
 * @className: DonacionesBean.java
 * @description: 
 * @date: 23 de jun. de 2017
 * @author: SUMERIO.
 */
public class DonacionesBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	private String codigoAnio;
	private String nombreDdi;
	private String codigoDonacion;
	private String fechaEmision;
	private String nombrePais;
	private String nombreDonante;
	private String nombreEstado;
	private String codigoDdi;
	private String codigoEstado;
	private Integer idEstado;
	private Integer idDonacion;
	private Integer idDdi;

	
	
	 
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
	 * @return the fechaEmision
	 */
	public String getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaEmision the fechaEmision to set
	 */
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
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
	 * @return the nombreDdi
	 */
	public String getNombreDdi() {
		return nombreDdi;
	}
	/**
	 * @param nombreDdi the nombreDdi to set
	 */
	public void setNombreDdi(String nombreDdi) {
		this.nombreDdi = nombreDdi;
	}
	/**
	 * @return the codigoDonacion
	 */
	public String getCodigoDonacion() {
		return codigoDonacion;
	}
	/**
	 * @param codigoDonacion the codigoDonacion to set
	 */
	public void setCodigoDonacion(String codigoDonacion) {
		this.codigoDonacion = codigoDonacion;
	}
	/**
	 * @return the nombrePais
	 */
	public String getNombrePais() {
		return nombrePais;
	}
	/**
	 * @param nombrePais the nombrePais to set
	 */
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
	/**
	 * @return the nombreDonante
	 */
	public String getNombreDonante() {
		return nombreDonante;
	}
	/**
	 * @param nombreDonante the nombreDonante to set
	 */
	public void setNombreDonante(String nombreDonante) {
		this.nombreDonante = nombreDonante;
	}
	/**
	 * @return the idDonacion
	 */
	public Integer getIdDonacion() {
		return idDonacion;
	}
	/**
	 * @param idDonacion the idDonacion to set
	 */
	public void setIdDonacion(Integer idDonacion) {
		this.idDonacion = idDonacion;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * @return the idEstado
	 */
	public Integer getIdEstado() {
		return idEstado;
	}
	/**
	 * @param idEstado the idEstado to set
	 */
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
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
	 * @return the codigoEstado
	 */
	public String getCodigoEstado() {
		return codigoEstado;
	}
	/**
	 * @param codigoEstado the codigoAlmacen to set
	 */
	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}
	

}

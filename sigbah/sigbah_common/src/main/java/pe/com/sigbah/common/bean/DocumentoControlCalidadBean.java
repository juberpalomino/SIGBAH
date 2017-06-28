package pe.com.sigbah.common.bean;

/**
 * @className: DocumentoControlCalidadBean.java
 * @description: 
 * @date: 25 de jun. de 2017
 * @author: SUMERIO.
 */
public class DocumentoControlCalidadBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	private Integer idDocumentoControlCalidad;
	private Integer idControlCalidad;
	private Integer idTipoDocumento;
	private String nombreDocumento;
	private String nroDocumento;
	private String fechaDocumento;
	private String observacion;
	private String rutaAlfresco;
	private Integer idArchivoAlfresco;

	
	/**
	 * 
	 */
	public DocumentoControlCalidadBean() {
		super();
	}

	/**
	 * @param idDocumentoControlCalidad
	 */
	public DocumentoControlCalidadBean(Integer idDocumentoControlCalidad) {
		super();
		this.idDocumentoControlCalidad = idDocumentoControlCalidad;
	}

	/**
	 * @return the idDocumentoControlCalidad
	 */
	public Integer getIdDocumentoControlCalidad() {
		return idDocumentoControlCalidad;
	}

	/**
	 * @param idDocumentoControlCalidad the idDocumentoControlCalidad to set
	 */
	public void setIdDocumentoControlCalidad(Integer idDocumentoControlCalidad) {
		this.idDocumentoControlCalidad = idDocumentoControlCalidad;
	}

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
	 * @return the idTipoDocumento
	 */
	public Integer getIdTipoDocumento() {
		return idTipoDocumento;
	}

	/**
	 * @param idTipoDocumento the idTipoDocumento to set
	 */
	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	/**
	 * @return the nombreDocumento
	 */
	public String getNombreDocumento() {
		return nombreDocumento;
	}

	/**
	 * @param nombreDocumento the nombreDocumento to set
	 */
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	/**
	 * @return the fechaDocumento
	 */
	public String getFechaDocumento() {
		return fechaDocumento;
	}

	/**
	 * @param fechaDocumento the fechaDocumento to set
	 */
	public void setFechaDocumento(String fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the rutaAlfresco
	 */
	public String getRutaAlfresco() {
		return rutaAlfresco;
	}

	/**
	 * @param rutaAlfresco the rutaAlfresco to set
	 */
	public void setRutaAlfresco(String rutaAlfresco) {
		this.rutaAlfresco = rutaAlfresco;
	}

	/**
	 * @return the nroDocumento
	 */
	public String getNroDocumento() {
		return nroDocumento;
	}

	/**
	 * @param nroDocumento the nroDocumento to set
	 */
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	/**
	 * @return the idArchivoAlfresco
	 */
	public Integer getIdArchivoAlfresco() {
		return idArchivoAlfresco;
	}

	/**
	 * @param idArchivoAlfresco the idArchivoAlfresco to set
	 */
	public void setIdArchivoAlfresco(Integer idArchivoAlfresco) {
		this.idArchivoAlfresco = idArchivoAlfresco;
	}

}

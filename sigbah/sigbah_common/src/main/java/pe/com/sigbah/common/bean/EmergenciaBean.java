package pe.com.sigbah.common.bean;


/**
 * @className: EmergenciaBean.java
 * @description: 
 * @date: 14 jul. 2017
 * @author: whr.
 */
public class EmergenciaBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	
	private Integer idEmergencia;
	private String nombreEmergencia;
	private Integer idFenomeno;
	private Integer idRegion;
	private String codFenomeno;
	private String descFenomeno;
	private String codAnio;
	private String nombreMes;
	private String codMes;
	private String codDepartamento;
	private String desDepartamento;
	private String codProvincia;
	private String desProvincia;	
	private String codDistrito;
	private String desDistrito;
	private Integer famAfectado;
	private Integer famDamnificado;
	private Integer persoAfectado;
	private Integer persoDamnificado;
	
	private String codRegion;
	private String fecha;
	private Integer poblacionINEI;
	private Integer totalFam;
	private Integer totalPerso;
	
	private String codSinpad;
	/**
	 * 
	 */
	public EmergenciaBean() {
		super();
	}
	
	/**
	 * @param idEmergencia
	 */
	public EmergenciaBean(Integer idEmergencia) {
		super();
		this.idEmergencia = idEmergencia;
	}

	/**
	 * @return the idEmergencia
	 */
	public Integer getIdEmergencia() {
		return idEmergencia;
	}

	/**
	 * @param idEmergencia the idEmergencia to set
	 */
	public void setIdEmergencia(Integer idEmergencia) {
		this.idEmergencia = idEmergencia;
	}

	/**
	 * @return the nombreEmergencia
	 */
	public String getNombreEmergencia() {
		return nombreEmergencia;
	}

	/**
	 * @param nombreEmergencia the nombreEmergencia to set
	 */
	public void setNombreEmergencia(String nombreEmergencia) {
		this.nombreEmergencia = nombreEmergencia;
	}

	/**
	 * @return the idFenomeno
	 */
	public Integer getIdFenomeno() {
		return idFenomeno;
	}

	/**
	 * @param idFenomeno the idFenomeno to set
	 */
	public void setIdFenomeno(Integer idFenomeno) {
		this.idFenomeno = idFenomeno;
	}

	/**
	 * @return the codFenomeno
	 */
	public String getCodFenomeno() {
		return codFenomeno;
	}

	/**
	 * @param codFenomeno the codFenomeno to set
	 */
	public void setCodFenomeno(String codFenomeno) {
		this.codFenomeno = codFenomeno;
	}

	
	/**
	 * @return the descFenomeno
	 */
	public String getDescFenomeno() {
		return descFenomeno;
	}

	/**
	 * @param descFenomeno the descFenomeno to set
	 */
	public void setDescFenomeno(String descFenomeno) {
		this.descFenomeno = descFenomeno;
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

	/**
	 * @return the nombreMes
	 */
	public String getNombreMes() {
		return nombreMes;
	}

	/**
	 * @param nombreMes the nombreMes to set
	 */
	public void setNombreMes(String nombreMes) {
		this.nombreMes = nombreMes;
	}

	/**
	 * @return the codMes
	 */
	public String getCodMes() {
		return codMes;
	}

	/**
	 * @param codMes the codMes to set
	 */
	public void setCodMes(String codMes) {
		this.codMes = codMes;
	}

	/**
	 * @return the codDepartamento
	 */
	public String getCodDepartamento() {
		return codDepartamento;
	}

	/**
	 * @param codDepartamento the codDepartamento to set
	 */
	public void setCodDepartamento(String codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	/**
	 * @return the desDepartamento
	 */
	public String getDesDepartamento() {
		return desDepartamento;
	}

	/**
	 * @param desDepartamento the desDepartamento to set
	 */
	public void setDesDepartamento(String desDepartamento) {
		this.desDepartamento = desDepartamento;
	}

	/**
	 * @return the codProvincia
	 */
	public String getCodProvincia() {
		return codProvincia;
	}

	/**
	 * @param codProvincia the codProvincia to set
	 */
	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}

	/**
	 * @return the desProvincia
	 */
	public String getDesProvincia() {
		return desProvincia;
	}

	/**
	 * @param desProvincia the desProvincia to set
	 */
	public void setDesProvincia(String desProvincia) {
		this.desProvincia = desProvincia;
	}

	/**
	 * @return the codDistrito
	 */
	public String getCodDistrito() {
		return codDistrito;
	}

	/**
	 * @param codDistrito the codDistrito to set
	 */
	public void setCodDistrito(String codDistrito) {
		this.codDistrito = codDistrito;
	}

	/**
	 * @return the desDistrito
	 */
	public String getDesDistrito() {
		return desDistrito;
	}

	/**
	 * @param desDistrito the desDistrito to set
	 */
	public void setDesDistrito(String desDistrito) {
		this.desDistrito = desDistrito;
	}

	/**
	 * @return the famAfectado
	 */
	public Integer getFamAfectado() {
		return famAfectado;
	}

	/**
	 * @param famAfectado the famAfectado to set
	 */
	public void setFamAfectado(Integer famAfectado) {
		this.famAfectado = famAfectado;
	}

	/**
	 * @return the famDamnificado
	 */
	public Integer getFamDamnificado() {
		return famDamnificado;
	}

	/**
	 * @param famDamnificado the famDamnificado to set
	 */
	public void setFamDamnificado(Integer famDamnificado) {
		this.famDamnificado = famDamnificado;
	}

	/**
	 * @return the persoAfectado
	 */
	public Integer getPersoAfectado() {
		return persoAfectado;
	}

	/**
	 * @param persoAfectado the persoAfectado to set
	 */
	public void setPersoAfectado(Integer persoAfectado) {
		this.persoAfectado = persoAfectado;
	}

	/**
	 * @return the persoDamnificado
	 */
	public Integer getPersoDamnificado() {
		return persoDamnificado;
	}

	/**
	 * @param persoDamnificado the persoDamnificado to set
	 */
	public void setPersoDamnificado(Integer persoDamnificado) {
		this.persoDamnificado = persoDamnificado;
	}

	/**
	 * @return the codRegion
	 */
	public String getCodRegion() {
		return codRegion;
	}

	/**
	 * @param codRegion the codRegion to set
	 */
	public void setCodRegion(String codRegion) {
		this.codRegion = codRegion;
	}

	/**
	 * @return the idRegion
	 */
	public Integer getIdRegion() {
		return idRegion;
	}

	/**
	 * @param idRegion the idRegion to set
	 */
	public void setIdRegion(Integer idRegion) {
		this.idRegion = idRegion;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	

	/**
	 * @return the poblacionINEI
	 */
	public Integer getPoblacionINEI() {
		return poblacionINEI;
	}

	/**
	 * @param poblacionINEI the poblacionINEI to set
	 */
	public void setPoblacionINEI(Integer poblacionINEI) {
		this.poblacionINEI = poblacionINEI;
	}

	/**
	 * @return the totalFam
	 */
	public Integer getTotalFam() {
		return totalFam;
	}

	/**
	 * @param totalFam the totalFam to set
	 */
	public void setTotalFam(Integer totalFam) {
		this.totalFam = totalFam;
	}

	/**
	 * @return the totalPerso
	 */
	public Integer getTotalPerso() {
		return totalPerso;
	}

	/**
	 * @param totalPerso the totalPerso to set
	 */
	public void setTotalPerso(Integer totalPerso) {
		this.totalPerso = totalPerso;
	}

	/**
	 * @return the codSinpad
	 */
	public String getCodSinpad() {
		return codSinpad;
	}

	/**
	 * @param codSinpad the codSinpad to set
	 */
	public void setCodSinpad(String codSinpad) {
		this.codSinpad = codSinpad;
	}

	
	
	
	
}

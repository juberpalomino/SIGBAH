package pe.com.sigbah.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @className: RolMenuBean.java
 * @description: 
 * @date: 22 de ago. de 2017
 * @author: ARCHY.
 */
public class RolMenuBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idRol;
	private String nombreRol;
	private String flagActivo;
	private String existe;
	private Integer idUsuario;
	private Integer idMenu;
	private Integer idPadre;
	private Integer nroOrden;
	private String nombreMenu;
	private String hijo;
	
	private String usuario;
	private String codigoRespuesta;
	private String mensajeRespuesta;

	/**
	 * @return the codigoRespuesta
	 */
	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}
	/**
	 * @param codigoRespuesta the codigoRespuesta to set
	 */
	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}
	/**
	 * @return the mensajeRespuesta
	 */
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	/**
	 * @param mensajeRespuesta the mensajeRespuesta to set
	 */
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}
	public Integer getIdRol() {
		return idRol;
	}
	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}
	public String getNombreRol() {
		return nombreRol;
	}
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	public String getFlagActivo() {
		return flagActivo;
	}
	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}
	public String getExiste() {
		return existe;
	}
	public void setExiste(String existe) {
		this.existe = existe;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Integer getIdMenu() {
		return idMenu;
	}
	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
	}
	public Integer getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}
	public Integer getNroOrden() {
		return nroOrden;
	}
	public void setNroOrden(Integer nroOrden) {
		this.nroOrden = nroOrden;
	}
	public String getNombreMenu() {
		return nombreMenu;
	}
	public void setNombreMenu(String nombreMenu) {
		this.nombreMenu = nombreMenu;
	}
	public String getHijo() {
		return hijo;
	}
	public void setHijo(String hijo) {
		this.hijo = hijo;
	}

	

}

package pe.com.sigbah.common.bean;

/**
 * @className: UsuarioBean.java
 * @description: 
 * @date: 17 de jun. de 2016
 * @author: SUMERIO.
 */
public class UsuarioBean extends MBaseBean {

	private static final long serialVersionUID = 1L;
	private Integer cod_login;
	private String cla_dinamica;
	private Integer cod_usuario;
	private String des_usuario;
	private Integer cod_estado;
	private Integer cod_area;
	private String vcod_area;
	private String des_area;
	private Integer cod_rol;
	private String des_rol;
	private Integer cod_sistema;
	private String des_sistema;
	private String nombres;
	private String apellidos;
	private int ind_per_mig;

	
	/**
	 * 
	 */
	public UsuarioBean() {
		super();
	}

	/**
	 * @return the cod_login
	 */
	public Integer getCod_login() {
		return cod_login;
	}
	/**
	 * @param cod_login the cod_login to set
	 */
	public void setCod_login(Integer cod_login) {
		this.cod_login = cod_login;
	}
	/**
	 * @return the cla_dinamica
	 */
	public String getCla_dinamica() {
		return cla_dinamica;
	}
	/**
	 * @param cla_dinamica the cla_dinamica to set
	 */
	public void setCla_dinamica(String cla_dinamica) {
		this.cla_dinamica = cla_dinamica;
	}
	/**
	 * @return the cod_usuario
	 */
	public Integer getCod_usuario() {
		return cod_usuario;
	}
	/**
	 * @param cod_usuario the cod_usuario to set
	 */
	public void setCod_usuario(Integer cod_usuario) {
		this.cod_usuario = cod_usuario;
	}
	/**
	 * @return the des_usuario
	 */
	public String getDes_usuario() {
		return des_usuario;
	}
	/**
	 * @param des_usuario the des_usuario to set
	 */
	public void setDes_usuario(String des_usuario) {
		this.des_usuario = des_usuario;
	}
	/**
	 * @return the cod_estado
	 */
	public Integer getCod_estado() {
		return cod_estado;
	}
	/**
	 * @param cod_estado the cod_estado to set
	 */
	public void setCod_estado(Integer cod_estado) {
		this.cod_estado = cod_estado;
	}
	/**
	 * @return the cod_area
	 */
	public Integer getCod_area() {
		return cod_area;
	}
	/**
	 * @param cod_area the cod_area to set
	 */
	public void setCod_area(Integer cod_area) {
		this.cod_area = cod_area;
	}
	/**
	 * @return the vcod_area
	 */
	public String getVcod_area() {
		return vcod_area;
	}
	/**
	 * @param vcod_area the vcod_area to set
	 */
	public void setVcod_area(String vcod_area) {
		this.vcod_area = vcod_area;
	}
	/**
	 * @return the des_area
	 */
	public String getDes_area() {
		return des_area;
	}
	/**
	 * @param des_area the des_area to set
	 */
	public void setDes_area(String des_area) {
		this.des_area = des_area;
	}
	/**
	 * @return the cod_rol
	 */
	public Integer getCod_rol() {
		return cod_rol;
	}
	/**
	 * @param cod_rol the cod_rol to set
	 */
	public void setCod_rol(Integer cod_rol) {
		this.cod_rol = cod_rol;
	}
	/**
	 * @return the des_rol
	 */
	public String getDes_rol() {
		return des_rol;
	}
	/**
	 * @param des_rol the des_rol to set
	 */
	public void setDes_rol(String des_rol) {
		this.des_rol = des_rol;
	}
	/**
	 * @return the cod_sistema
	 */
	public Integer getCod_sistema() {
		return cod_sistema;
	}
	/**
	 * @param cod_sistema the cod_sistema to set
	 */
	public void setCod_sistema(Integer cod_sistema) {
		this.cod_sistema = cod_sistema;
	}
	/**
	 * @return the des_sistema
	 */
	public String getDes_sistema() {
		return des_sistema;
	}
	/**
	 * @param des_sistema the des_sistema to set
	 */
	public void setDes_sistema(String des_sistema) {
		this.des_sistema = des_sistema;
	}
	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}
	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}
	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	/**
	 * @return the ind_per_mig
	 */
	public int getInd_per_mig() {
		return ind_per_mig;
	}
	/**
	 * @param ind_per_mig the ind_per_mig to set
	 */
	public void setInd_per_mig(int ind_per_mig) {
		this.ind_per_mig = ind_per_mig;
	}	

}

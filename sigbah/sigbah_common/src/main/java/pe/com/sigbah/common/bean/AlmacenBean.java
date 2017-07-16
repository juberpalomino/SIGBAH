package pe.com.sigbah.common.bean;

import java.io.Serializable;

/**
 * @className: AlmacenBean.java
 * @description: 
 * @date: 9 de jul. de 2017
 * @author: Junior Huaman Flores.
 */
public class AlmacenBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idUsuario;
	private Integer idAlmacen;
	private String codigoAlmacen;
	private String nombreAlmacen;
	
	
	/**
	 * @return the idUsuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}
	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
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

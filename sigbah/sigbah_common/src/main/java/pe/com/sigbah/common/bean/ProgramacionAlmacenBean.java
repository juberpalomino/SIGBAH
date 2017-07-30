package pe.com.sigbah.common.bean;

import java.io.Serializable;

/**
 * @className: ProgramacionAlmacenBean.java
 * @description: 
 * @date: 29/07/2017
 * @author: Junior Huaman Flores.
 */
public class ProgramacionAlmacenBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idProgramacionAlmacen;
	private Integer idProgramacion;
	private Integer idAlmacen;
	private String nombreAlmacen;
	
	
	/**
	 * @return the idProgramacionAlmacen
	 */
	public Integer getIdProgramacionAlmacen() {
		return idProgramacionAlmacen;
	}
	/**
	 * @param idProgramacionAlmacen the idProgramacionAlmacen to set
	 */
	public void setIdProgramacionAlmacen(Integer idProgramacionAlmacen) {
		this.idProgramacionAlmacen = idProgramacionAlmacen;
	}
	/**
	 * @return the idProgramacion
	 */
	public Integer getIdProgramacion() {
		return idProgramacion;
	}
	/**
	 * @param idProgramacion the idProgramacion to set
	 */
	public void setIdProgramacion(Integer idProgramacion) {
		this.idProgramacion = idProgramacion;
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

}

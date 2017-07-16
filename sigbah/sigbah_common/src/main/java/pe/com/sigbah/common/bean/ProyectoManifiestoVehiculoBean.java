package pe.com.sigbah.common.bean;

import java.util.List;

/**
 * @className: ProyectoManifiestoVehiculoBean.java
 * @description: Clase ProyectoManifiestoBean.
 * @date: 18/02/2015 22:26:30
 * @author: Junior Huaman Flores.
 */
public class ProyectoManifiestoVehiculoBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	private Integer idProyectoManifiesto;
	private List<ManifiestoVehiculoBean> vehiculos;
	
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
	 * @return the vehiculos
	 */
	public List<ManifiestoVehiculoBean> getVehiculos() {
		return vehiculos;
	}
	/**
	 * @param vehiculos the vehiculos to set
	 */
	public void setVehiculos(List<ManifiestoVehiculoBean> vehiculos) {
		this.vehiculos = vehiculos;
	}

}
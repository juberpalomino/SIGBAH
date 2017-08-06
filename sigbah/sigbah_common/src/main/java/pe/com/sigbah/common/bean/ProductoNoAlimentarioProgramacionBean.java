package pe.com.sigbah.common.bean;

import java.math.BigDecimal;

/**
 * @className: ProductoNoAlimentarioProgramacionBean.java
 * @description: 
 * @date: 25 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
public class ProductoNoAlimentarioProgramacionBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	private Integer idDetalleProductoNoAlimentario;
	private Integer idProducto;
	private Integer idProgramacion;
	private Integer idCategoria;
	private String nombreProducto;
	private String distribuirPor;
	private String tipoEntrega;
	private BigDecimal cantidad;
	
	
	/**
	 * 
	 */
	public ProductoNoAlimentarioProgramacionBean() {
		super();
	}
	/**
	 * @param idDetalleProductoNoAlimentario
	 */
	public ProductoNoAlimentarioProgramacionBean(Integer idDetalleProductoNoAlimentario) {
		super();
		this.idDetalleProductoNoAlimentario = idDetalleProductoNoAlimentario;
	}
	/**
	 * @return the idDetalleProductoNoAlimentario
	 */
	public Integer getIdDetalleProductoNoAlimentario() {
		return idDetalleProductoNoAlimentario;
	}
	/**
	 * @param idDetalleProductoNoAlimentario the idDetalleProductoNoAlimentario to set
	 */
	public void setIdDetalleProductoNoAlimentario(Integer idDetalleProductoNoAlimentario) {
		this.idDetalleProductoNoAlimentario = idDetalleProductoNoAlimentario;
	}
	/**
	 * @return the idProducto
	 */
	public Integer getIdProducto() {
		return idProducto;
	}
	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
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
	 * @return the idCategoria
	 */
	public Integer getIdCategoria() {
		return idCategoria;
	}
	/**
	 * @param idCategoria the idCategoria to set
	 */
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	/**
	 * @return the nombreProducto
	 */
	public String getNombreProducto() {
		return nombreProducto;
	}
	/**
	 * @param nombreProducto the nombreProducto to set
	 */
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	/**
	 * @return the distribuirPor
	 */
	public String getDistribuirPor() {
		return distribuirPor;
	}
	/**
	 * @param distribuirPor the distribuirPor to set
	 */
	public void setDistribuirPor(String distribuirPor) {
		this.distribuirPor = distribuirPor;
	}
	/**
	 * @return the tipoEntrega
	 */
	public String getTipoEntrega() {
		return tipoEntrega;
	}
	/**
	 * @param tipoEntrega the tipoEntrega to set
	 */
	public void setTipoEntrega(String tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}
	/**
	 * @return the cantidad
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

}

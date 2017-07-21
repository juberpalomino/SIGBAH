package pe.com.sigbah.common.bean;

/**
 * @className: ProductoBean.java
 * @description: Clase ProductoDonacionBean.
 * @date: 18/07/2017 
 * @author: ARCHY.
 */
public class ProductoDonacionBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	
	
	private Integer idProducto;
	private Integer idDonacion;
	private Integer idDetDonacion;
	private Integer idMoneda;
	private Double cantidad;
	private String fecVencimiento;
	private Double monOrigen;
	private Double monSoles;
	private Double monDolares;
	private String usuario;
	
	private String nombreProducto;
	private String unidadMedida;
	public Integer idCategoria;
	private String nombreCategoria;
	private String nombreMoneda;
	private Double precio; 

	/**
	 * 
	 */
	public ProductoDonacionBean() {
		super();
	}
	
	public ProductoDonacionBean(Integer idDetDonacion) {
		super();
		this.idDetDonacion = idDetDonacion;
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

	
	//////////////////
	
	public Integer getIdDonacion() {
		return idDonacion;
	}

	public void setIdDonacion(Integer idDonacion) {
		this.idDonacion = idDonacion;
	}
	
	public Integer getIdDetDonacion() {
		return idDetDonacion;
	}

	public void setIdDetDonacion(Integer idDetDonacion) {
		this.idDetDonacion = idDetDonacion;
	}
	
	public Integer getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(Integer idMoneda) {
		this.idMoneda = idMoneda;
	}
	
	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getFecVencimiento() {
		return fecVencimiento;
	}

	public void setFecVencimiento(String fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}
	
	public Double getMonOrigen() {
		return monOrigen;
	}

	public void setMonOrigen(Double monOrigen) {
		this.monOrigen = monOrigen;
	}
	
	public Double getMonSoles() {
		return monSoles;
	}

	public void setMonSoles(Double monSoles) {
		this.monSoles = monSoles;
	}
	
	public Double getMonDolares() {
		return monDolares;
	}

	public void setMonDolares(Double monDolares) {
		this.monDolares = monDolares;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	
	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	
	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	
	public String getNombreMoneda() {
		return nombreMoneda;
	}

	public void setNombreMoneda(String nombreMoneda) {
		this.nombreMoneda = nombreMoneda;
	}
	
	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}


}
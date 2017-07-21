package pe.com.sigbah.common.bean;

/**
 * @className: ProductoDonacionIngresoBean.java
 * @description: Clase ProductoDonacionIngresoBean.
 * @date: 21/07/2017 
 * @author: ARCHY.
 */
public class ProductoDonacionIngresoBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	
	
	private Integer idIngreso;
	private Integer idIngresoDet;
	private Integer idProducto;
	private String nombreProducto;
	private String unidadMedida;
	private Double cantidad;
	private Double precioUnitario;
	private Double importeTotal;
	private String fecVencimiento;
	private Integer idDonacion;

	/**
	 * 
	 */
	public ProductoDonacionIngresoBean() {
		super();
	}

	public Integer getIdIngreso() {
		return idIngreso;
	}

	public void setIdIngreso(Integer idIngreso) {
		this.idIngreso = idIngreso;
	}

	
	//////////////////
	
	public Integer getIdIngresoDet() {
		return idIngresoDet;
	}

	public void setIdIngresoDet(Integer idIngresoDet) {
		this.idIngresoDet = idIngresoDet;
	}
	
	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	
	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	
	public String getFecVencimiento() {
		return fecVencimiento;
	}

	public void setFecVencimiento(String fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}
	
	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	
	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}
	
	public Integer getIdDonacion() {
		return idDonacion;
	}

	public void setIdDonacion(Integer idDonacion) {
		this.idDonacion = idDonacion;
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


}
package pe.com.sigbah.common.bean;

/**
 * @className: OrdenIngresoBean.java
 * @description: 
 * @date: 3 de jul. de 2017
 * @author: SUMERIO.
 */
public class OrdenIngresoBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	private Integer idIngreso;
	private Integer item;
	private String codigoAnio;
	private String codigoDdi;
	private String nombreDdi;
	private Integer idAlmacen;
	private String nombreAlmacen;
	private String codigoIngreso;
	private String nroOrdenIngreso;
	private String fechaEmision;
	private Integer idMovimiento;
	private String codigoMovimiento;
	private String nombreMovimiento;
	private String nombreEstado;
	private String tipoOrigen;
	private String codigoMes;
	private Integer idDdi;
	private String codigoAlmacen;
	private String tipo;
	
	
	/**
	 * @return the idIngreso
	 */
	public Integer getIdIngreso() {
		return idIngreso;
	}
	/**
	 * @param idIngreso the idIngreso to set
	 */
	public void setIdIngreso(Integer idIngreso) {
		this.idIngreso = idIngreso;
	}
	/**
	 * @return the item
	 */
	public Integer getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(Integer item) {
		this.item = item;
	}
	/**
	 * @return the codigoAnio
	 */
	public String getCodigoAnio() {
		return codigoAnio;
	}
	/**
	 * @param codigoAnio the codigoAnio to set
	 */
	public void setCodigoAnio(String codigoAnio) {
		this.codigoAnio = codigoAnio;
	}
	/**
	 * @return the nombreDdi
	 */
	public String getNombreDdi() {
		return nombreDdi;
	}
	/**
	 * @param nombreDdi the nombreDdi to set
	 */
	public void setNombreDdi(String nombreDdi) {
		this.nombreDdi = nombreDdi;
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
	 * @return the nroOrdenIngreso
	 */
	public String getNroOrdenIngreso() {
		return nroOrdenIngreso;
	}
	/**
	 * @param nroOrdenIngreso the nroOrdenIngreso to set
	 */
	public void setNroOrdenIngreso(String nroOrdenIngreso) {
		this.nroOrdenIngreso = nroOrdenIngreso;
	}
	/**
	 * @return the fechaEmision
	 */
	public String getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaEmision the fechaEmision to set
	 */
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	/**
	 * @return the nombreMovimiento
	 */
	public String getNombreMovimiento() {
		return nombreMovimiento;
	}
	/**
	 * @param nombreMovimiento the nombreMovimiento to set
	 */
	public void setNombreMovimiento(String nombreMovimiento) {
		this.nombreMovimiento = nombreMovimiento;
	}
	/**
	 * @return the nombreEstado
	 */
	public String getNombreEstado() {
		return nombreEstado;
	}
	/**
	 * @param nombreEstado the nombreEstado to set
	 */
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	/**
	 * @return the codigoDdi
	 */
	public String getCodigoDdi() {
		return codigoDdi;
	}
	/**
	 * @param codigoDdi the codigoDdi to set
	 */
	public void setCodigoDdi(String codigoDdi) {
		this.codigoDdi = codigoDdi;
	}
	/**
	 * @return the codigoMovimiento
	 */
	public String getCodigoMovimiento() {
		return codigoMovimiento;
	}
	/**
	 * @param codigoMovimiento the codigoMovimiento to set
	 */
	public void setCodigoMovimiento(String codigoMovimiento) {
		this.codigoMovimiento = codigoMovimiento;
	}
	/**
	 * @return the codigoIngreso
	 */
	public String getCodigoIngreso() {
		return codigoIngreso;
	}
	/**
	 * @param codigoIngreso the codigoIngreso to set
	 */
	public void setCodigoIngreso(String codigoIngreso) {
		this.codigoIngreso = codigoIngreso;
	}
	/**
	 * @return the tipoOrigen
	 */
	public String getTipoOrigen() {
		return tipoOrigen;
	}
	/**
	 * @param tipoOrigen the tipoOrigen to set
	 */
	public void setTipoOrigen(String tipoOrigen) {
		this.tipoOrigen = tipoOrigen;
	}
	/**
	 * @return the codigoMes
	 */
	public String getCodigoMes() {
		return codigoMes;
	}
	/**
	 * @param codigoMes the codigoMes to set
	 */
	public void setCodigoMes(String codigoMes) {
		this.codigoMes = codigoMes;
	}
	/**
	 * @return the idDdi
	 */
	public Integer getIdDdi() {
		return idDdi;
	}
	/**
	 * @param idDdi the idDdi to set
	 */
	public void setIdDdi(Integer idDdi) {
		this.idDdi = idDdi;
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
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the idMovimiento
	 */
	public Integer getIdMovimiento() {
		return idMovimiento;
	}
	/**
	 * @param idMovimiento the idMovimiento to set
	 */
	public void setIdMovimiento(Integer idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

}

package pe.com.sigbah.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @className: DetalleGuiaRemisionBean.java
 * @description: 
 * @date: 18 de jul. de 2017
 * @author: SUMERIO.
 */
public class DetalleGuiaRemisionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal item;
	private Integer idSalida;
	private String nroGuiaEmision;
	private String sede;
	private String direccionPartida;
	private String puntoPartida;	
	private String fechaEmision;
	private String fechaInicioTraslado;
	private String empresaTransporte;
	private String rucEmpresaTransporte;
	private String nombreChofer;
	private String nroPlaca;
	private String puntoLlegada;
	private String razonSocialDestino;
	private String rucDestino;
	private String nombreProducto;	
	private BigDecimal cantidad;
	private String unidadMedida;
	private BigDecimal pesoTotal;
	private String observacionGuia;
	private String tipoMovimiento;	
	private Integer idMotivoTraslado;
	private String motivoTraslado;
	private String nroLicenciaConducir;
	
	
	/**
	 * @return the idSalida
	 */
	public Integer getIdSalida() {
		return idSalida;
	}
	/**
	 * @param idSalida the idSalida to set
	 */
	public void setIdSalida(Integer idSalida) {
		this.idSalida = idSalida;
	}
	/**
	 * @return the nroGuiaEmision
	 */
	public String getNroGuiaEmision() {
		return nroGuiaEmision;
	}
	/**
	 * @param nroGuiaEmision the nroGuiaEmision to set
	 */
	public void setNroGuiaEmision(String nroGuiaEmision) {
		this.nroGuiaEmision = nroGuiaEmision;
	}
	/**
	 * @return the sede
	 */
	public String getSede() {
		return sede;
	}
	/**
	 * @param sede the sede to set
	 */
	public void setSede(String sede) {
		this.sede = sede;
	}
	/**
	 * @return the direccionPartida
	 */
	public String getDireccionPartida() {
		return direccionPartida;
	}
	/**
	 * @param direccionPartida the direccionPartida to set
	 */
	public void setDireccionPartida(String direccionPartida) {
		this.direccionPartida = direccionPartida;
	}
	/**
	 * @return the puntoPartida
	 */
	public String getPuntoPartida() {
		return puntoPartida;
	}
	/**
	 * @param puntoPartida the puntoPartida to set
	 */
	public void setPuntoPartida(String puntoPartida) {
		this.puntoPartida = puntoPartida;
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
	 * @return the fechaInicioTraslado
	 */
	public String getFechaInicioTraslado() {
		return fechaInicioTraslado;
	}
	/**
	 * @param fechaInicioTraslado the fechaInicioTraslado to set
	 */
	public void setFechaInicioTraslado(String fechaInicioTraslado) {
		this.fechaInicioTraslado = fechaInicioTraslado;
	}
	/**
	 * @return the empresaTransporte
	 */
	public String getEmpresaTransporte() {
		return empresaTransporte;
	}
	/**
	 * @param empresaTransporte the empresaTransporte to set
	 */
	public void setEmpresaTransporte(String empresaTransporte) {
		this.empresaTransporte = empresaTransporte;
	}
	/**
	 * @return the rucEmpresaTransporte
	 */
	public String getRucEmpresaTransporte() {
		return rucEmpresaTransporte;
	}
	/**
	 * @param rucEmpresaTransporte the rucEmpresaTransporte to set
	 */
	public void setRucEmpresaTransporte(String rucEmpresaTransporte) {
		this.rucEmpresaTransporte = rucEmpresaTransporte;
	}
	/**
	 * @return the nombreChofer
	 */
	public String getNombreChofer() {
		return nombreChofer;
	}
	/**
	 * @param nombreChofer the nombreChofer to set
	 */
	public void setNombreChofer(String nombreChofer) {
		this.nombreChofer = nombreChofer;
	}
	/**
	 * @return the nroPlaca
	 */
	public String getNroPlaca() {
		return nroPlaca;
	}
	/**
	 * @param nroPlaca the nroPlaca to set
	 */
	public void setNroPlaca(String nroPlaca) {
		this.nroPlaca = nroPlaca;
	}
	/**
	 * @return the puntoLlegada
	 */
	public String getPuntoLlegada() {
		return puntoLlegada;
	}
	/**
	 * @param puntoLlegada the puntoLlegada to set
	 */
	public void setPuntoLlegada(String puntoLlegada) {
		this.puntoLlegada = puntoLlegada;
	}
	/**
	 * @return the razonSocialDestino
	 */
	public String getRazonSocialDestino() {
		return razonSocialDestino;
	}
	/**
	 * @param razonSocialDestino the razonSocialDestino to set
	 */
	public void setRazonSocialDestino(String razonSocialDestino) {
		this.razonSocialDestino = razonSocialDestino;
	}
	/**
	 * @return the rucDestino
	 */
	public String getRucDestino() {
		return rucDestino;
	}
	/**
	 * @param rucDestino the rucDestino to set
	 */
	public void setRucDestino(String rucDestino) {
		this.rucDestino = rucDestino;
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
	/**
	 * @return the unidadMedida
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}
	/**
	 * @param unidadMedida the unidadMedida to set
	 */
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	/**
	 * @return the observacionGuia
	 */
	public String getObservacionGuia() {
		return observacionGuia;
	}
	/**
	 * @param observacionGuia the observacionGuia to set
	 */
	public void setObservacionGuia(String observacionGuia) {
		this.observacionGuia = observacionGuia;
	}
	/**
	 * @return the tipoMovimiento
	 */
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}
	/**
	 * @param tipoMovimiento the tipoMovimiento to set
	 */
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	/**
	 * @return the idMotivoTraslado
	 */
	public Integer getIdMotivoTraslado() {
		return idMotivoTraslado;
	}
	/**
	 * @param idMotivoTraslado the idMotivoTraslado to set
	 */
	public void setIdMotivoTraslado(Integer idMotivoTraslado) {
		this.idMotivoTraslado = idMotivoTraslado;
	}
	/**
	 * @return the motivoTraslado
	 */
	public String getMotivoTraslado() {
		return motivoTraslado;
	}
	/**
	 * @param motivoTraslado the motivoTraslado to set
	 */
	public void setMotivoTraslado(String motivoTraslado) {
		this.motivoTraslado = motivoTraslado;
	}
	/**
	 * @return the nroLicenciaConducir
	 */
	public String getNroLicenciaConducir() {
		return nroLicenciaConducir;
	}
	/**
	 * @param nroLicenciaConducir the nroLicenciaConducir to set
	 */
	public void setNroLicenciaConducir(String nroLicenciaConducir) {
		this.nroLicenciaConducir = nroLicenciaConducir;
	}
	/**
	 * @return the pesoTotal
	 */
	public BigDecimal getPesoTotal() {
		return pesoTotal;
	}
	/**
	 * @param pesoTotal the pesoTotal to set
	 */
	public void setPesoTotal(BigDecimal pesoTotal) {
		this.pesoTotal = pesoTotal;
	}
	/**
	 * @return the item
	 */
	public BigDecimal getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(BigDecimal item) {
		this.item = item;
	}

}

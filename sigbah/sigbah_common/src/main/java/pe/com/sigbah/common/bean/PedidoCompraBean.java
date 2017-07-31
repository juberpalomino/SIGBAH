package pe.com.sigbah.common.bean;



/**
 * @className: PedidoCompraBean.java
 * @description: 
 * @date: 30 jul. 2017
 * @author: whr.
 */
public class PedidoCompraBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	
	private Integer idPedidoCom;
	private String codAnio;
	private String codPedido;
	private String fkIdeDdi;
	private String nombreDdi;
	private String fecPedido;
	private String dde;
	private String tipFenomeno;
	private String NomEstado;
	private String codEstado;
    
	/**
	 * 
	 */
	public PedidoCompraBean() {
		super();
	}

	/**
	 * @return the idPedidoCom
	 */
	public Integer getIdPedidoCom() {
		return idPedidoCom;
	}

	/**
	 * @param idPedidoCom the idPedidoCom to set
	 */
	public void setIdPedidoCom(Integer idPedidoCom) {
		this.idPedidoCom = idPedidoCom;
	}

	/**
	 * @return the codAnio
	 */
	public String getCodAnio() {
		return codAnio;
	}

	/**
	 * @param codAnio the codAnio to set
	 */
	public void setCodAnio(String codAnio) {
		this.codAnio = codAnio;
	}

	/**
	 * @return the codPedido
	 */
	public String getCodPedido() {
		return codPedido;
	}

	/**
	 * @param codPedido the codPedido to set
	 */
	public void setCodPedido(String codPedido) {
		this.codPedido = codPedido;
	}

	/**
	 * @return the fkIdeDdi
	 */
	public String getFkIdeDdi() {
		return fkIdeDdi;
	}

	/**
	 * @param fkIdeDdi the fkIdeDdi to set
	 */
	public void setFkIdeDdi(String fkIdeDdi) {
		this.fkIdeDdi = fkIdeDdi;
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
	 * @return the fecPedido
	 */
	public String getFecPedido() {
		return fecPedido;
	}

	/**
	 * @param fecPedido the fecPedido to set
	 */
	public void setFecPedido(String fecPedido) {
		this.fecPedido = fecPedido;
	}

	/**
	 * @return the dde
	 */
	public String getDde() {
		return dde;
	}

	/**
	 * @param dde the dde to set
	 */
	public void setDde(String dde) {
		this.dde = dde;
	}

	/**
	 * @return the tipFenomeno
	 */
	public String getTipFenomeno() {
		return tipFenomeno;
	}

	/**
	 * @param tipFenomeno the tipFenomeno to set
	 */
	public void setTipFenomeno(String tipFenomeno) {
		this.tipFenomeno = tipFenomeno;
	}

	/**
	 * @return the nomEstado
	 */
	public String getNomEstado() {
		return NomEstado;
	}

	/**
	 * @param nomEstado the nomEstado to set
	 */
	public void setNomEstado(String nomEstado) {
		NomEstado = nomEstado;
	}

	/**
	 * @return the codEstado
	 */
	public String getCodEstado() {
		return codEstado;
	}

	/**
	 * @param codEstado the codEstado to set
	 */
	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	
	
	
}

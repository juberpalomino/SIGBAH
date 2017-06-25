package pe.com.sigbah.common.bean;

/**
 * @className: OrdenCompraBean.java
 * @description: 
 * @date: 24/06/2017
 * @author: Sumerio.
 */
public class OrdenCompraBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	private String nroOrdenCompra;
	private String concepto;
	
	/**
	 * @return the nroOrdenCompra
	 */
	public String getNroOrdenCompra() {
		return nroOrdenCompra;
	}
	/**
	 * @param nroOrdenCompra the nroOrdenCompra to set
	 */
	public void setNroOrdenCompra(String nroOrdenCompra) {
		this.nroOrdenCompra = nroOrdenCompra;
	}
	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

}

package pe.com.sigbah.common.bean;

/**
 * @className: ItemBean.java
 * @description: POJO genérico para cargar combos, autocompletar, etc.
 * @date: 18/02/2015 22:26:30
 * @author: SUMERIO.
 */
public class ItemBean extends MBaseBean {

	private static final long serialVersionUID = 1920713534765475781L;
	private Integer icodigo;
	private short sicodigo;
	private String descripcion;
	private String descripcionCorta;
	
	/**
	 * 
	 */
	public ItemBean() {
		super();
	}
	
	/**
	 * @param icodigo - Código de la entidad, tipo Integer.
	 * @param sicodigo - Código de la entidad, tipo short.
	 * @param descripcion - Descripción de la entidad, tipo String.
	 * @param descripcionCorta - Descripción corta de la entidad, tipo String.
	 */
	public ItemBean(Integer icodigo, short sicodigo, String descripcion, String descripcionCorta) {
		super();
		this.icodigo = icodigo;
		this.sicodigo = sicodigo;
		this.descripcion = descripcion;
		this.descripcionCorta = descripcionCorta;
	}
	
	/**
	 * @param icodigo - Código de la entidad, tipo Integer.
	 * @param descripcion - Descripción de la entidad, tipo String.
	 */
	public ItemBean(Integer icodigo, String descripcion) {
		super();
		this.icodigo = icodigo;
		this.descripcion = descripcion;
	}

	/**
	 * @return the icodigo
	 */
	public Integer getIcodigo() {
		return icodigo;
	}

	/**
	 * @param icodigo the icodigo to set
	 */
	public void setIcodigo(Integer icodigo) {
		this.icodigo = icodigo;
	}

	/**
	 * @return the sicodigo
	 */
	public short getSicodigo() {
		return sicodigo;
	}

	/**
	 * @param sicodigo the sicodigo to set
	 */
	public void setSicodigo(short sicodigo) {
		this.sicodigo = sicodigo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * @return the descripcionCorta
	 */
	public String getDescripcionCorta() {
		String desCorta = descripcionCorta;
		return desCorta == null ? "" : desCorta;
	}
	
	/**
	 * @param descripcionCorta the descripcionCorta to set
	 */
	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItemBean [icodigo=" + icodigo + ", sicodigo=" + sicodigo
				+ ", descripcion=" + descripcion + ", descripcionCorta="
				+ descripcionCorta + "]";
	}

}
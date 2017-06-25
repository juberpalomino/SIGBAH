package pe.com.sigbah.common.bean;

/**
 * @className: ItemBean.java
 * @description: POJO genérico para cargar combos, autocompletar, etc.
 * @date: 18/02/2015 22:26:30
 * @author: SUMERIO.
 */
public class ItemBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	
	private Integer icodigo;
	private Integer icodigoParam2;
	private String vcodigo;
	private String vcodigoParam2;
	private String descripcion;
	private String descripcionCorta;
	
	/**
	 * 
	 */
	public ItemBean() {
		super();
	}

	
	/**
	 * @param icodigo
	 */
	public ItemBean(Integer icodigo) {
		super();
		this.icodigo = icodigo;
	}

	/**
	 * @param vcodigo
	 */
	public ItemBean(String vcodigo) {
		super();
		this.vcodigo = vcodigo;
	}

	/**
	 * @param icodigo - Código de la entidad, tipo Integer.
	 * @param descripcion - Descripción de la entidad, tipo String.
	 * @param descripcionCorta - Descripción corta de la entidad, tipo String.
	 */
	public ItemBean(Integer icodigo, String descripcion, String descripcionCorta) {
		super();
		this.icodigo = icodigo;
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
	 * @param vcodigo
	 * @param descripcion
	 */
	public ItemBean(String vcodigo, String descripcion) {
		super();
		this.vcodigo = vcodigo;
		this.descripcion = descripcion;
	}
	
	/**
	 * @param icodigo
	 * @param icodigoParam2
	 */
	public ItemBean(Integer icodigo, Integer icodigoParam2) {
		super();
		this.icodigo = icodigo;
		this.icodigoParam2 = icodigoParam2;
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
	 * @return the vcodigo
	 */
	public String getVcodigo() {
		return vcodigo;
	}

	/**
	 * @param vcodigo the vcodigo to set
	 */
	public void setVcodigo(String vcodigo) {
		this.vcodigo = vcodigo;
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
		return descripcionCorta;
	}
	
	/**
	 * @param descripcionCorta the descripcionCorta to set
	 */
	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	/**
	 * @return the icodigoParam2
	 */
	public Integer getIcodigoParam2() {
		return icodigoParam2;
	}

	/**
	 * @param icodigoParam2 the icodigoParam2 to set
	 */
	public void setIcodigoParam2(Integer icodigoParam2) {
		this.icodigoParam2 = icodigoParam2;
	}

	/**
	 * @return the vcodigoParam2
	 */
	public String getVcodigoParam2() {
		return vcodigoParam2;
	}

	/**
	 * @param vcodigoParam2 the vcodigoParam2 to set
	 */
	public void setVcodigoParam2(String vcodigoParam2) {
		this.vcodigoParam2 = vcodigoParam2;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItemBean [icodigo=" + icodigo + ", icodigoParam2=" + icodigoParam2 + ", vcodigo=" + vcodigo
				+ ", vcodigoParam2=" + vcodigoParam2 + ", descripcion=" + descripcion + ", descripcionCorta="
				+ descripcionCorta + "]";
	}

}
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
	private String vcodigo;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItemBean [icodigo=" + icodigo 
				+ ", descripcion=" + descripcion + ", descripcionCorta="
				+ descripcionCorta + "]";
	}

}
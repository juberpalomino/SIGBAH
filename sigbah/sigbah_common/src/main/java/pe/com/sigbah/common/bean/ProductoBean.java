package pe.com.sigbah.common.bean;

/**
 * @className: ProductoBean.java
 * @description: Clase ProductoBean.
 * @date: 18/02/2015 22:26:30
 * @author: SUMERIO.
 */
public class ProductoBean extends BaseOutputBean {

	private static final long serialVersionUID = 1L;
	
	private Integer idProducto;
	private String nombreProducto;
	private String codigoProducto;
	private Integer idUnidadMedida;
	private String nombreUnidadMedida;
	private Integer idEnvase;
	private String nombreEnvase;
	private Integer idCategoria;
	
	/**
	 * 
	 */
	public ProductoBean() {
		super();
	}
	
	/**
	 * @param idProducto
	 * @param idCategoria
	 */
	public ProductoBean(Integer idProducto, Integer idCategoria) {
		super();
		this.idProducto = idProducto;
		this.idCategoria = idCategoria;
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
	 * @return the codigoProducto
	 */
	public String getCodigoProducto() {
		return codigoProducto;
	}

	/**
	 * @param codigoProducto the codigoProducto to set
	 */
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	/**
	 * @return the idUnidadMedida
	 */
	public Integer getIdUnidadMedida() {
		return idUnidadMedida;
	}

	/**
	 * @param idUnidadMedida the idUnidadMedida to set
	 */
	public void setIdUnidadMedida(Integer idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}

	/**
	 * @return the nombreUnidadMedida
	 */
	public String getNombreUnidadMedida() {
		return nombreUnidadMedida;
	}

	/**
	 * @param nombreUnidadMedida the nombreUnidadMedida to set
	 */
	public void setNombreUnidadMedida(String nombreUnidadMedida) {
		this.nombreUnidadMedida = nombreUnidadMedida;
	}

	/**
	 * @return the idEnvase
	 */
	public Integer getIdEnvase() {
		return idEnvase;
	}

	/**
	 * @param idEnvase the idEnvase to set
	 */
	public void setIdEnvase(Integer idEnvase) {
		this.idEnvase = idEnvase;
	}

	/**
	 * @return the nombreEnvase
	 */
	public String getNombreEnvase() {
		return nombreEnvase;
	}

	/**
	 * @param nombreEnvase the nombreEnvase to set
	 */
	public void setNombreEnvase(String nombreEnvase) {
		this.nombreEnvase = nombreEnvase;
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

}
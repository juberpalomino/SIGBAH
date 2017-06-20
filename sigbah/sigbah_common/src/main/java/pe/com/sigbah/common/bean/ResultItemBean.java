package pe.com.sigbah.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @className: ResultItemBean.java
 * @description: 
 * @date: 20 de jun. de 2017
 * @author: SUMERIO.
 */
public class ResultItemBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ItemBean> itemBeans;

	/**
	 * @return the itemBeans
	 */
	public List<ItemBean> getItemBeans() {
		return itemBeans;
	}

	/**
	 * @param itemBeans the itemBeans to set
	 */
	public void setItemBeans(List<ItemBean> itemBeans) {
		this.itemBeans = itemBeans;
	}

}

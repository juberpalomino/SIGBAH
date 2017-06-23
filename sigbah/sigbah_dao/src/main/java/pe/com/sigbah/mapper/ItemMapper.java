package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.ItemBean;

/**
 * @className: ItemMapper.java
 * @description: 
 * @date: 22 de jun. de 2017
 * @author: SUMERIO.
 */
public class ItemMapper implements RowMapper<ItemBean> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public ItemBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemBean itemBean = new ItemBean();
		itemBean.setVcodigo(rs.getString("IDE_DDI"));
		itemBean.setDescripcion(rs.getString("NOM_DDI"));
		return itemBean;
	}

}

package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.util.Constantes;

/**
 * @className: EstadoDonacionMapper.java
 * @description: 
 * @date: 17 de jul. de 2017
 * @author: ARCHY.
 */
public class EstadoXUsuarioMapper implements RowMapper<ItemBean> {

	@Override
	public ItemBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemBean itemBean = new ItemBean();

		itemBean.setIcodigo(rs.getInt("FK_IDE_ESTADO"));
		itemBean.setDescripcion(rs.getString("NOM_ESTADO"));

		
		return itemBean;
	}

}

package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.util.Constantes;

/**
 * @className: ModAlmacenMapper.java
 * @description: 
 * @date: 22 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
public class ModAlmacenMapper implements RowMapper<ItemBean> {
	
	private boolean all_records = false; // Obtener todos los registros

	/**
	 * @param parametro
	 */
	public ModAlmacenMapper(Integer parametro) {
		if (parametro.equals(Constantes.ZERO_INT)) {
			all_records = true;
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public ItemBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemBean itemBean = new ItemBean();
		if (all_records) {
			itemBean.setIcodigo(rs.getInt("IDE_MOD_ALMACEN"));
			itemBean.setDescripcion(rs.getString("NOM_MODALIDAD"));
		} else {	
			itemBean.setIcodigo(rs.getInt("IDE_MOD_ALMACEN"));
			itemBean.setDescripcion(rs.getString("NOM_MODALIDAD"));
		}
		return itemBean;
	}

}

package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.util.Constantes;

/**
 * @className: EstadoMapper.java
 * @description: 
 * @date: 22 de jun. de 2017
 * @author: SUMERIO.
 */
public class CatologoProductosMapper implements RowMapper<ItemBean> {
	
	private boolean all_records = false; // Obtener todos los registros

	/**
	 * @param parametro
	 */
	public CatologoProductosMapper(String parametro) {
		if (parametro.equals(Constantes.PORCENTAJE)) {
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
			itemBean.setVcodigo(rs.getString("IDE_CAT_PRODUCTO"));
			itemBean.setDescripcion(rs.getString("NOM_PRODUCTO"));
			itemBean.setDescripcionCorta(rs.getString("NOMBRE_UNIDAD"));
		} else {	
			itemBean.setVcodigo(rs.getString("IDE_CAT_PRODUCTO"));
			itemBean.setDescripcion(rs.getString("NOM_PRODUCTO"));
			itemBean.setDescripcionCorta(rs.getString("NOMBRE_UNIDAD"));
		}
		return itemBean;
	}

}

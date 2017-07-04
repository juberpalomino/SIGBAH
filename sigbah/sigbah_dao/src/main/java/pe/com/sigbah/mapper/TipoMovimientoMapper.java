package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.util.Constantes;

/**
 * @className: EmpresaTransporteMapper.java
 * @description: 
 * @date: 22 de jun. de 2017
 * @author: SUMERIO.
 */
public class TipoMovimientoMapper implements RowMapper<ItemBean> {
	
	private boolean all_records = false; // Obtener todos los registros

	/**
	 * @param parametro
	 */
	public TipoMovimientoMapper(Integer parametro) {
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
			itemBean.setIcodigo(rs.getInt("IDE_TIP_MOVIMIENTO"));
			itemBean.setDescripcion(rs.getString("NOM_MOVIMIENTO"));
		} else {	
			itemBean.setIcodigo(rs.getInt("IDE_TIP_MOVIMIENTO"));
			itemBean.setDescripcion(rs.getString("NOM_MOVIMIENTO"));
		}
		return itemBean;
	}

}

package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.util.Constantes;

/**
 * @className: TipoDocumentoMapper.java
 * @description: 
 * @date: 22 de jun. de 2017
 * @author: SUMERIO.
 */
public class TipoDocumentoMapper implements RowMapper<ItemBean> {
	
	private boolean all_records = false; // Obtener todos los registros

	/**
	 * @param parametro
	 */
	public TipoDocumentoMapper(String parametro) {
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
			itemBean.setVcodigo(rs.getString("IDE_TIP_DOCUMENTO"));
			itemBean.setDescripcion(rs.getString("NOM_DOCUMENTO"));
		} else {	
			itemBean.setVcodigo(rs.getString("IDE_TIP_DOCUMENTO"));
			itemBean.setDescripcion(rs.getString("NOM_DOCUMENTO"));
		}
		return itemBean;
	}

}

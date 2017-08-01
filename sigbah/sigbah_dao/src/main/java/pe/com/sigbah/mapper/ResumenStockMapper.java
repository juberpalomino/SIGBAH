package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.ResumenStockBean;

/**
 * @className: ResumenStockMapper.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
public class ResumenStockMapper implements RowMapper<ResumenStockBean> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public ResumenStockBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResumenStockBean resumen = new ResumenStockBean();
		resumen.setTipoOrigen(rs.getString("TIPO_ORIGEN"));
		resumen.setIdDdi(rs.getInt("FK_IDE_DDI"));
		resumen.setIdAlmacen(rs.getInt("FK_IDE_ALMACEN"));
		resumen.setIdProducto(rs.getInt("FK_IDE_PRODUCTO"));
		resumen.setNombreProducto(rs.getString("NOMBRE_PRODUCTO"));
		resumen.setCantidad(rs.getBigDecimal("CANTIDAD"));
		return resumen;
	}

}

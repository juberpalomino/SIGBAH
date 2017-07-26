package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.CierreStockBean;

/**
 * @className: CierreStockMapper.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
public class CierreStockMapper implements RowMapper<CierreStockBean> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public CierreStockBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		CierreStockBean cierreStock = new CierreStockBean();
		cierreStock.setIdCartilla(rs.getInt("IDE_CARTILLA"));
		cierreStock.setIdAlmacen(rs.getInt("FK_IDE_ALMACEN"));
		cierreStock.setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
		cierreStock.setCodigoAnio(rs.getString("COD_ANIO"));
		cierreStock.setNroCartilla(rs.getString("NRO_CARTILLA"));		
		cierreStock.setFechaCartilla(rs.getString("FEC_CARTILLA"));
		cierreStock.setResponsable(rs.getString("REPONSABLE"));
		cierreStock.setItemInventariados(rs.getBigDecimal("ITEM_INVENTARIADOS"));		
		cierreStock.setIdEstado(rs.getInt("FK_IDE_ESTADO"));
		cierreStock.setNombreEstado(rs.getString("NOMBRE_ESTADO"));
		return cierreStock;
	}

}

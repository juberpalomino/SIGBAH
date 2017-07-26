package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.CierreStockBean;

/**
 * @className: RegistroCierreStockMapper.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
public class RegistroCierreStockMapper implements RowMapper<CierreStockBean> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public CierreStockBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		CierreStockBean cierreStock = new CierreStockBean();
		cierreStock.setIdCartilla(rs.getInt("IDE_CARTILLA"));
		cierreStock.setCodigoAnio(rs.getString("COD_ANIO"));
		cierreStock.setIdAlmacen(rs.getInt("FK_IDE_ALMACEN"));
		cierreStock.setCodigoAlmacen(rs.getString("COD_ALMACEN"));
		cierreStock.setIdDdi(rs.getInt("FK_IDE_DDI"));
		cierreStock.setCodigoDdi(rs.getString("COD_DDI"));
		cierreStock.setNombreDdi(rs.getString("NOMBRE_DDI"));
		cierreStock.setNroCartilla(rs.getString("NRO_CARTILLA"));		
		cierreStock.setCodigoCartilla(rs.getString("COD_CARTILLA"));
		cierreStock.setFechaCartilla(rs.getString("FEC_CARTILLA"));
		cierreStock.setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
		cierreStock.setIdResponsable(rs.getInt("FK_IDE_RESPONSABLE"));		
		cierreStock.setResponsable(rs.getString("RESPONSABLE"));
		cierreStock.setIdEstado(rs.getInt("FK_IDE_ESTADO"));
		cierreStock.setNombreEstado(rs.getString("NOMBRE_ESTADO"));
		cierreStock.setObservacion(rs.getString("OBSERVACION_CARTILLA"));		
		return cierreStock;
	}

}

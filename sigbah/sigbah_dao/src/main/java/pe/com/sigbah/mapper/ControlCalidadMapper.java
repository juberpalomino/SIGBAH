package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.ControlCalidadBean;

/**
 * @className: ControlCalidadMapper.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public class ControlCalidadMapper implements RowMapper<ControlCalidadBean> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public ControlCalidadBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ControlCalidadBean controlCalidad = new ControlCalidadBean();
		controlCalidad.setIdControlCalidad(rs.getInt("IDE_CONTROL_CALIDAD"));
		controlCalidad.setCodigoAnio(rs.getString("COD_ANIO"));
		controlCalidad.setCodigoMes(rs.getString("COD_MES"));
		controlCalidad.setNombreAlmacen(rs.getString("NOMBRE_ALMACEN").trim());
		controlCalidad.setNroRepControlCalidad(rs.getString("NRO_REP_CONTROL_CALIDAD"));
		controlCalidad.setFechaEmision(rs.getDate("FEC_EMISION"));
		controlCalidad.setTipoControlCalidad(rs.getString("TIPO_CONTROL_CALIDAD"));
		controlCalidad.setNombreEstado(rs.getString("NOMBRE_ESTADO"));
		return controlCalidad;
	}

}

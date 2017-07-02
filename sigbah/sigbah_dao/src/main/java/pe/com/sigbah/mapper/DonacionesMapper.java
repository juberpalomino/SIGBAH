package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.DonacionesBean;

/**
 * @className: ControlCalidadMapper.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public class DonacionesMapper implements RowMapper<DonacionesBean> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public DonacionesBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		DonacionesBean donaciones = new DonacionesBean();
		donaciones.setIdDonacion(rs.getInt("IDE_DONACION"));
		donaciones.setCodigoDonacion(rs.getString("COD_DONACION"));
		donaciones.setCodigoAnio(rs.getString("ANIO"));
	//	controlCalidad.setCodigoMes(rs.getString("COD_MES"));
	//	controlCalidad.setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));		
//		donaciones.setIdDdi(rs.getInt("DDI"));
		donaciones.setCodigoDdi(rs.getString("DDI"));
//		donaciones.setNombreDdi(rs.getString("NOM_DDI"));		
	//	controlCalidad.setNroControlCalidad(rs.getString("NRO_REP_CONTROL_CALIDAD"));
		donaciones.setFechaEmision(rs.getString("FECHA"));
    //	controlCalidad.setTipoControlCalidad(rs.getString("TIPO_CONTROL_CALIDAD"));
		donaciones.setNombreEstado(rs.getString("ESTADO"));
		return donaciones;
	}

}

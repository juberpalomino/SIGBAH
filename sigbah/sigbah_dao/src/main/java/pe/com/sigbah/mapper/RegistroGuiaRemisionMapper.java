package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.GuiaRemisionBean;

/**
 * @className: RegistroGuiaRemisionMapper.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
public class RegistroGuiaRemisionMapper implements RowMapper<GuiaRemisionBean> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public GuiaRemisionBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		GuiaRemisionBean guiaRemision = new GuiaRemisionBean();
		guiaRemision.setIdGuiaRemision(rs.getInt("IDE_GUIA_REMISION"));
		guiaRemision.setNroGuiaRemision(rs.getString("NRO_GUIA_REMISION"));
		guiaRemision.setCodigoAnio(rs.getString("COD_ANIO"));
		guiaRemision.setCodigoMes(rs.getString("COD_MES"));
		guiaRemision.setIdDdi(rs.getInt("IDE_DDI"));
		guiaRemision.setCodigoDdi(rs.getString("COD_DDI"));		
		guiaRemision.setNombreDdi(rs.getString("NOMBRE_DDI"));
		guiaRemision.setIdAlmacen(rs.getInt("FK_IDE_ALMACEN"));
		guiaRemision.setCodigoAlmacen(rs.getString("COD_ALMACEN"));		
		guiaRemision.setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));		
		guiaRemision.setTipoOrigen(rs.getString("TIPO_ORIGEN"));
		guiaRemision.setFechaEmision(rs.getString("FECHA_EMISION"));
		guiaRemision.setIdEstado(rs.getInt("FK_IDE_ESTADO"));
		guiaRemision.setNombreEstado(rs.getString("NOMBRE_ESTADO"));
		guiaRemision.setIdMovimiento(rs.getInt("FK_IDE_TIP_MOVIMIENTO"));
		guiaRemision.setNombreMovimiento(rs.getString("NOMBRE_MOVIMIENTO"));
//		guiaRemision.setIdAlmacenDestino(rs.getInt("FK_IDE_ALMACEN_DEST"));
//		guiaRemision.setNombreAlmacenDestino(rs.getString("NOMBRE_ALMACEN_DESTINO"));
//		guiaRemision.setObservacion(rs.getString("OBSERVACION"));
//		guiaRemision.setIdProgramacion(rs.getInt("FK_IDE_PROGRAMACION"));	
//		guiaRemision.setNroProgramacion(rs.getString("NRO_PROGRAMACION"));
//		guiaRemision.setFlagProgramacion(rs.getString("FLG_PROGRAMACION"));
		return guiaRemision;
	}

}

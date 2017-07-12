package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.ProyectoManifiestoBean;

/**
 * @className: ProyectoManifiestoMapper.java
 * @description: 
 * @date: 22 de jun. de 2017
 * @author: SUMERIO.
 */
public class ProyectoManifiestoMapper implements RowMapper<ProyectoManifiestoBean> {
	
	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public ProyectoManifiestoBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProyectoManifiestoBean proyecto = new ProyectoManifiestoBean();
		proyecto.setCodigoAnio(rs.getString("COD_ANIO"));
		proyecto.setCodigoMes(rs.getString("COD_MES"));
		proyecto.setIdAlmacen(rs.getInt("FK_IDE_ALMACEN"));
		proyecto.setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
		proyecto.setNroProyectoManifiesto(rs.getString("NRO_PROY_MANIFIESTO"));
		proyecto.setFechaEmision(rs.getString("FECHA_EMISION"));
		proyecto.setNroProgramacion(rs.getString("NRO_PROGRAMACION"));
		proyecto.setNombreMovimiento(rs.getString("NOM_MOVIMIENTO"));
		proyecto.setNombreEstado(rs.getString("NOMBRE_ESTADO"));
		return proyecto;
	}

}

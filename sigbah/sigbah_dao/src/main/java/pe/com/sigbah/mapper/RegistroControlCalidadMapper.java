package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;

/**
 * @className: ControlCalidadMapper.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public class RegistroControlCalidadMapper implements RowMapper<ControlCalidadBean> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public ControlCalidadBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ControlCalidadBean controlCalidad = new ControlCalidadBean();
		controlCalidad.setIdControlCalidad(rs.getInt("IDE_CONTROL_CALIDAD"));
		controlCalidad.setCodigoAnio(rs.getString("COD_ANIO"));
		controlCalidad.setCodigoMes(rs.getString("COD_MES"));
		controlCalidad.setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
		controlCalidad.setNroControlCalidad(rs.getString("NRO_REP_CONTROL_CALIDAD"));		
		controlCalidad.setFechaEmision(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, rs.getDate("FEC_EMISION")));		
		controlCalidad.setIdTipoControl(rs.getInt("COD_TIPO_CONTROL_CALIDAD"));
		controlCalidad.setIdEstado(rs.getInt("COD_ESTADO"));
		controlCalidad.setNroOrdenCompra(rs.getString("NRO_ORDEN_COMPRA"));
		controlCalidad.setIdAlmacenOrigen(rs.getInt("FK_IDE_ALMACEN_OD"));
		controlCalidad.setIdEncargado(rs.getInt("FK_IDE_ENCARGADO"));
		controlCalidad.setIdInspector(rs.getInt("FK_IDE_INSPECTOR"));
		controlCalidad.setIdProveedor(rs.getInt("FK_IDE_PROVEEDOR"));
		controlCalidad.setProvRep(rs.getString("PROV_REP"));
		controlCalidad.setIdChofer(rs.getInt("FK_IDE_CHOFER"));
		controlCalidad.setNroPlaca(rs.getString("NRO_PLACA"));
		controlCalidad.setFlagTipoBien(rs.getString("FLG_TIPO_BIEN"));
		controlCalidad.setConclusiones(rs.getString("CONCLUSIONES"));
		controlCalidad.setRecomendaciones(rs.getString("RECOMENDACIONES"));
		return controlCalidad;
	}

}

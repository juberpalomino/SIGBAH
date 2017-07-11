package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.OrdenSalidaBean;

/**
 * @className: RegistroOrdenSalidaMapper.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public class RegistroOrdenSalidaMapper implements RowMapper<OrdenSalidaBean> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public OrdenSalidaBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrdenSalidaBean ordenSalida = new OrdenSalidaBean();
		ordenSalida.setIdSalida(rs.getInt("IDE_INGRESO"));
		ordenSalida.setNroOrdenSalida(rs.getString("NRO_ORDEN_INGRESO"));
		ordenSalida.setCodigoAnio(rs.getString("COD_ANIO"));
		ordenSalida.setCodigoDdi(rs.getString("COD_DDI"));
		ordenSalida.setIdDdi(rs.getInt("IDE_DDI"));
		ordenSalida.setNombreDdi(rs.getString("NOMBRE_DDI"));
		ordenSalida.setCodigoAlmacen(rs.getString("COD_ALMACEN"));
		ordenSalida.setIdAlmacen(rs.getInt("FK_IDE_ALMACEN"));
		ordenSalida.setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
		ordenSalida.setCodigoMes(rs.getString("COD_MES"));
		ordenSalida.setFechaEmision(rs.getString("FECHA_EMISION"));
		ordenSalida.setIdEstado(rs.getInt("FK_IDE_ESTADO"));
		ordenSalida.setNombreEstado(rs.getString("NOMBRE_ESTADO"));
		ordenSalida.setIdMovimiento(rs.getInt("FK_IDE_TIP_MOVIMIENTO"));
		ordenSalida.setNombreMovimiento(rs.getString("NOMBRE_MOVIMIENTO"));
		ordenSalida.setNroOrdenCompra(rs.getString("NRO_ORDEN_COMPRA"));
		ordenSalida.setConcepto(rs.getString("CONCEPTO"));
		ordenSalida.setFlagTipoCompra(rs.getString("FLG_TIP_COMPRA"));
		ordenSalida.setFlagControlCalidad(rs.getString("FLG_CONTROL_CALIDAD"));
		ordenSalida.setIdControlCalidad(rs.getInt("FK_IDE_CONTROL_CALIDAD"));
		ordenSalida.setIdProveedor(rs.getInt("FK_IDE_PROVEEDOR"));
		ordenSalida.setProvRep(rs.getString("PROV_REP"));
		ordenSalida.setRepresentante(rs.getString("REPRESENTANTE"));
		ordenSalida.setIdAlmacenProcedencia(rs.getInt("FK_ID_ALM_PROCEDENCIA"));
		ordenSalida.setNombreAlmacenProcedencia(rs.getString("NOMBRE_ALMACEN_PROCEDENCIA"));
		ordenSalida.setIdMedioTransporte(rs.getInt("FK_IDE_MED_TRANSPORTE"));
		ordenSalida.setFechaLlegada(rs.getString("FECHA_LLEGADA"));
		ordenSalida.setIdEmpresaTransporte(rs.getInt("FK_IDE_EMP_TRANS"));
		ordenSalida.setEmpresaTransporte(rs.getString("EMPRESA_TRANSPORTE"));
		ordenSalida.setIdChofer(rs.getInt("FK_IDE_CHOFER"));
		ordenSalida.setNombreChofer(rs.getString("NOMBRE_CHOFER"));
		ordenSalida.setNroPlaca(rs.getString("NRO_PLACA"));
		ordenSalida.setIdResponsable(rs.getInt("FK_IDE_RESPONSABLE"));
		ordenSalida.setResponsable(rs.getString("RESPONSABLE"));
		ordenSalida.setObservacion(rs.getString("OBSERVACION"));
		ordenSalida.setIdDonacion(rs.getInt("FK_IDE_DONACION"));
		return ordenSalida;
	}

}

package pe.com.sigbah.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.OrdenCompraBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.LogisticaDao;
import pe.com.sigbah.mapper.AlmacenActivoMapper;
import pe.com.sigbah.mapper.ControlCalidadMapper;

/**
 * @className: LogisticaDaoImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_LOGISTICA.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class LogisticaDaoImpl extends JdbcDaoSupport implements LogisticaDao, Serializable {

	private static final long serialVersionUID = 1L;
	private transient final Log LOGGER = LogFactory.getLog(getClass());
	
	private SimpleJdbcCall objJdbcCall;
	
	/**
	 * @param dataSource
	 */
	@Autowired
	public LogisticaDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public List<ControlCalidadBean> listarControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		LOGGER.info("[listarControlCalidad] Inicio ");
		List<ControlCalidadBean> lista = new ArrayList<ControlCalidadBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_COD_ANIO", controlCalidadBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_DDI", controlCalidadBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_ALMACEN", Utils.getParam(controlCalidadBean.getCodigoAlmacen()), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SIG_BAH);
			objJdbcCall.withProcedureName("USP_LISTAR_CONTROL_CALIDAD");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_IDE_DDI", new SqlParameter("pi_IDE_DDI", Types.VARCHAR));
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new ControlCalidadMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				throw new Exception();
			} else {
				lista = (List<ControlCalidadBean>) out.get("po_Lr_Recordset");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarControlCalidad] Fin ");
		return lista;
	}

	@Override
	public ControlCalidadBean obtenerCorrelativo(ControlCalidadBean controlCalidadBean) throws Exception {
		LOGGER.info("[obtenerCorrelativo] Inicio ");
		ControlCalidadBean detalleUsuarioBean = new ControlCalidadBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_COD_ANIO", controlCalidadBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_DDI", controlCalidadBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_ALMACEN", controlCalidadBean.getIdAlmacen(), Types.NUMERIC);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SIG_BAH);
			objJdbcCall.withProcedureName("USP_SEL_GENERA_CORREL_CALIDAD");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_COD_DDI", new SqlParameter("pi_COD_DDI", Types.VARCHAR));
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("po_CORRELATIVO", new SqlOutParameter("po_CORRELATIVO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
		
			detalleUsuarioBean.setNroControlCalidad((String) out.get("po_CORRELATIVO"));
			detalleUsuarioBean.setCodigoRespuesta((String) out.get("po_CODIGO_RESPUESTA"));
			detalleUsuarioBean.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
			
			if (detalleUsuarioBean.getCodigoRespuesta().equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[obtenerCorrelativo] Ocurrio un error en la operacion del USP_SEL_GENERA_CORREL_CALIDAD");
    			throw new Exception();
    		}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCorrelativo] Fin ");
		return detalleUsuarioBean;
	}

	@Override
	public List<ControlCalidadBean> listarAlmacenActivo(ControlCalidadBean controlCalidadBean) throws Exception {
		LOGGER.info("[listarAlmacenActivo] Inicio ");
		List<ControlCalidadBean> lista = new ArrayList<ControlCalidadBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_ALMACEN", controlCalidadBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_TIPO", controlCalidadBean.getTipo(), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SIG_BAH);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_CAB_ACTIVA_ALM");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_TIPO", new SqlParameter("pi_TIPO", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new AlmacenActivoMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			lista = (List<ControlCalidadBean>) out.get("po_Lr_Recordset");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarAlmacenActivo] Fin ");
		return lista;
	}

	@Override
	public List<OrdenCompraBean> listarOrdenCompra() throws Exception {
		LOGGER.info("[listarOrdenCompra] Inicio ");
		List<OrdenCompraBean> lista = new ArrayList<OrdenCompraBean>();
		try {
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SIG_BAH);
			objJdbcCall.withProcedureName("USP_SEL_TAB_ORDEN_COMPRA");

			Map<String, Object> out = objJdbcCall.withoutProcedureColumnMetaDataAccess()
					.returningResultSet("po_Lr_Recordset", new RowMapper<OrdenCompraBean>() {
						public OrdenCompraBean mapRow(ResultSet rs, int rowNum) throws SQLException {
							OrdenCompraBean ordenCompra = new OrdenCompraBean();
							ordenCompra.setNroOrdenCompra(rs.getString("NRO_ORDCOM"));
							ordenCompra.setConcepto(rs.getString("CONCEPTO"));
							return ordenCompra;
						}
					}).execute(objJdbcCall);

			lista = new ArrayList((Collection<OrdenCompraBean>) out.get("po_Lr_Recordset"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarOrdenCompra] Fin ");
		return lista;
	}

}

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
import pe.com.sigbah.common.bean.DocumentoControlCalidadBean;
import pe.com.sigbah.common.bean.OrdenCompraBean;
import pe.com.sigbah.common.bean.ProductoControlCalidadBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.LogisticaDao;
import pe.com.sigbah.mapper.AlmacenActivoMapper;
import pe.com.sigbah.mapper.ControlCalidadMapper;
import pe.com.sigbah.mapper.DocumentoControlCalidadMapper;
import pe.com.sigbah.mapper.ProductoControlCalidadMapper;
import pe.com.sigbah.mapper.RegistroControlCalidadMapper;

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
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
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
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
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
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[obtenerCorrelativo] Ocurrio un error en la operacion del USP_SEL_GENERA_CORREL_CALIDAD");
    			throw new Exception();
    		}

			detalleUsuarioBean.setNroControlCalidad((String) out.get("po_CORRELATIVO"));
			detalleUsuarioBean.setCodigoRespuesta(codigoRespuesta);
			detalleUsuarioBean.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));

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
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
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
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
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

	@Override
	public ControlCalidadBean insertarRegistroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		LOGGER.info("[insertarRegistroControlCalidad] Inicio ");
		ControlCalidadBean registroControlCalidad = new ControlCalidadBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_COD_ANIO", controlCalidadBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_MES", controlCalidadBean.getCodigoMes(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", controlCalidadBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_FLG_TIPO_PRODUCTO", controlCalidadBean.getFlagTipoBien(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_ALMACEN", controlCalidadBean.getCodigoAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_DDI", controlCalidadBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_DDI", controlCalidadBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("pi_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(controlCalidadBean.getFechaEmision()), Types.DATE);
			input_objParametros.addValue("pi_FK_IDE_TIP_CONTROL", controlCalidadBean.getIdTipoControl(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_ENCARGADO", controlCalidadBean.getIdEncargado(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_INSPECTOR", controlCalidadBean.getIdInspector(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_PROVEEDOR", controlCalidadBean.getIdProveedor(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_EMP_TRANS", controlCalidadBean.getIdEmpresaTransporte(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_CHOFER", controlCalidadBean.getIdChofer(), Types.NUMERIC);			
			input_objParametros.addValue("pi_CONCLUSIONES", controlCalidadBean.getConclusiones(), Types.VARCHAR);
			input_objParametros.addValue("pi_RECOMENDACIONES", controlCalidadBean.getRecomendaciones(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN_OD", controlCalidadBean.getIdAlmacenOrigen(), Types.NUMERIC);
			input_objParametros.addValue("pi_NRO_PLACA", controlCalidadBean.getNroPlaca(), Types.VARCHAR);
			input_objParametros.addValue("pi_NRO_ORDEN_COMPRA", controlCalidadBean.getNroOrdenCompra(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ESTADO", controlCalidadBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("pi_FLG_TIPO_BIEN", controlCalidadBean.getFlagTipoBien(), Types.VARCHAR);
			input_objParametros.addValue("pi_USU_REGISTRO", controlCalidadBean.getUsuarioRegistro(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_REGISTRA_CONTROL_CALID");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_COD_MES", new SqlParameter("pi_COD_MES", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_FLG_TIPO_PRODUCTO", new SqlParameter("pi_FLG_TIPO_PRODUCTO", Types.VARCHAR));
			output_objParametros.put("pi_COD_ALMACEN", new SqlParameter("pi_COD_ALMACEN", Types.VARCHAR));
			output_objParametros.put("pi_COD_DDI", new SqlParameter("pi_COD_DDI", Types.VARCHAR));
			output_objParametros.put("pi_IDE_DDI", new SqlParameter("pi_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("pi_FEC_EMISION", new SqlParameter("pi_FEC_EMISION", Types.DATE));
			output_objParametros.put("pi_FK_IDE_TIP_CONTROL", new SqlParameter("pi_FK_IDE_TIP_CONTROL", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_ENCARGADO", new SqlParameter("pi_FK_IDE_ENCARGADO", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_INSPECTOR", new SqlParameter("pi_FK_IDE_INSPECTOR", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_PROVEEDOR", new SqlParameter("pi_FK_IDE_PROVEEDOR", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_EMP_TRANS", new SqlParameter("pi_FK_IDE_EMP_TRANS", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_CHOFER", new SqlParameter("pi_FK_IDE_CHOFER", Types.NUMERIC));			
			output_objParametros.put("pi_CONCLUSIONES", new SqlParameter("pi_CONCLUSIONES", Types.VARCHAR));
			output_objParametros.put("pi_RECOMENDACIONES", new SqlParameter("pi_RECOMENDACIONES", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_ALMACEN_OD", new SqlParameter("pi_FK_IDE_ALMACEN_OD", Types.NUMERIC));
			output_objParametros.put("pi_NRO_PLACA", new SqlParameter("pi_NRO_PLACA", Types.VARCHAR));
			output_objParametros.put("pi_NRO_ORDEN_COMPRA", new SqlParameter("pi_NRO_ORDEN_COMPRA", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_ESTADO", new SqlParameter("pi_FK_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("pi_FLG_TIPO_BIEN", new SqlParameter("pi_FLG_TIPO_BIEN", Types.VARCHAR));
			output_objParametros.put("pi_USU_REGISTRO", new SqlParameter("pi_USU_REGISTRO", Types.VARCHAR));			
			output_objParametros.put("po_PK_IDE_CONTROL_CALIDAD", new SqlOutParameter("po_PK_IDE_CONTROL_CALIDAD", Types.NUMERIC));
			output_objParametros.put("po_NRO_CONTROL_CALIDAD", new SqlOutParameter("po_NRO_CONTROL_CALIDAD", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[insertarRegistroControlCalidad] Ocurrio un error en la operacion del USP_INS_REGISTRA_CONTROL_CALID");
    			throw new Exception();
    		}
		
			registroControlCalidad.setIdControlCalidad((Integer) out.get("po_PK_IDE_CONTROL_CALIDAD"));
			registroControlCalidad.setNroControlCalidad((String) out.get("po_NRO_CONTROL_CALIDAD"));
			registroControlCalidad.setCodigoRespuesta(codigoRespuesta);
			registroControlCalidad.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarRegistroControlCalidad] Fin ");
		return registroControlCalidad;
	}

	@Override
	public ControlCalidadBean actualizarRegistroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		LOGGER.info("[actualizarRegistroControlCalidad] Inicio ");
		ControlCalidadBean registroControlCalidad = new ControlCalidadBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_CONTROL_CALIDAD", controlCalidadBean.getIdControlCalidad(), Types.NUMERIC);
			input_objParametros.addValue("pi_COD_ANIO", controlCalidadBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_MES", controlCalidadBean.getCodigoMes(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", controlCalidadBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_FLG_TIPO_PRODUCTO", controlCalidadBean.getFlagTipoBien(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_ALMACEN", controlCalidadBean.getCodigoAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_DDI", controlCalidadBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("pi_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(controlCalidadBean.getFechaEmision()), Types.DATE);
			input_objParametros.addValue("pi_FK_IDE_TIP_CONTROL", controlCalidadBean.getIdTipoControl(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_ENCARGADO", controlCalidadBean.getIdEncargado(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_INSPECTOR", controlCalidadBean.getIdInspector(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_PROVEEDOR", controlCalidadBean.getIdProveedor(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_EMP_TRANS", controlCalidadBean.getIdEmpresaTransporte(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_CHOFER", controlCalidadBean.getIdChofer(), Types.NUMERIC);			
			input_objParametros.addValue("pi_CONCLUSIONES", controlCalidadBean.getConclusiones(), Types.VARCHAR);
			input_objParametros.addValue("pi_RECOMENDACIONES", controlCalidadBean.getRecomendaciones(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN_OD", controlCalidadBean.getIdAlmacenOrigen(), Types.NUMERIC);
			input_objParametros.addValue("pi_NRO_PLACA", controlCalidadBean.getNroPlaca(), Types.VARCHAR);
			input_objParametros.addValue("pi_NRO_ORDEN_COMPRA", controlCalidadBean.getNroOrdenCompra(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ESTADO", controlCalidadBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("pi_FLG_TIPO_BIEN", controlCalidadBean.getFlagTipoBien(), Types.VARCHAR);
			input_objParametros.addValue("pi_USU_MODIFICA", controlCalidadBean.getUsuarioRegistro(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_UPD_REGISTRA_CONTROL_CALID");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_CONTROL_CALIDAD", new SqlParameter("pi_IDE_CONTROL_CALIDAD", Types.NUMERIC));
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_COD_MES", new SqlParameter("pi_COD_MES", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_FLG_TIPO_PRODUCTO", new SqlParameter("pi_FLG_TIPO_PRODUCTO", Types.VARCHAR));
			output_objParametros.put("pi_COD_ALMACEN", new SqlParameter("pi_COD_ALMACEN", Types.VARCHAR));
			output_objParametros.put("pi_COD_DDI", new SqlParameter("pi_COD_DDI", Types.VARCHAR));
			output_objParametros.put("pi_FEC_EMISION", new SqlParameter("pi_FEC_EMISION", Types.DATE));
			output_objParametros.put("pi_FK_IDE_TIP_CONTROL", new SqlParameter("pi_FK_IDE_TIP_CONTROL", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_ENCARGADO", new SqlParameter("pi_FK_IDE_ENCARGADO", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_INSPECTOR", new SqlParameter("pi_FK_IDE_INSPECTOR", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_PROVEEDOR", new SqlParameter("pi_FK_IDE_PROVEEDOR", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_EMP_TRANS", new SqlParameter("pi_FK_IDE_EMP_TRANS", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_CHOFER", new SqlParameter("pi_FK_IDE_CHOFER", Types.NUMERIC));			
			output_objParametros.put("pi_CONCLUSIONES", new SqlParameter("pi_CONCLUSIONES", Types.VARCHAR));
			output_objParametros.put("pi_RECOMENDACIONES", new SqlParameter("pi_RECOMENDACIONES", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_ALMACEN_OD", new SqlParameter("pi_FK_IDE_ALMACEN_OD", Types.NUMERIC));
			output_objParametros.put("pi_NRO_PLACA", new SqlParameter("pi_NRO_PLACA", Types.VARCHAR));
			output_objParametros.put("pi_NRO_ORDEN_COMPRA", new SqlParameter("pi_NRO_ORDEN_COMPRA", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_ESTADO", new SqlParameter("pi_FK_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("pi_FLG_TIPO_BIEN", new SqlParameter("pi_FLG_TIPO_BIEN", Types.VARCHAR));
			output_objParametros.put("pi_USU_MODIFICA", new SqlParameter("pi_USU_MODIFICA", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[actualizarRegistroControlCalidad] Ocurrio un error en la operacion del USP_UPD_REGISTRA_CONTROL_CALID");
    			throw new Exception();
    		}

			registroControlCalidad.setCodigoRespuesta(codigoRespuesta);
			registroControlCalidad.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarRegistroControlCalidad] Fin ");
		return registroControlCalidad;
	}

	@Override
	public ControlCalidadBean obtenerRegistroControlCalidad(Integer idControlCalidad) throws Exception {
		LOGGER.info("[obtenerRegistroControlCalidad] Inicio ");
		ControlCalidadBean controlCalidad = new ControlCalidadBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_CONTROL_CALIDAD", idControlCalidad, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_CONTROL_CALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_CONTROL_CALIDAD", new SqlParameter("pi_IDE_CONTROL_CALIDAD", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new RegistroControlCalidadMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[obtenerRegistroControlCalidad] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_CONTROL_CALIDA");
    			throw new Exception();
    		}
			
			List<ControlCalidadBean> lista = (List<ControlCalidadBean>) out.get("po_Lr_Recordset");
			if (!Utils.isEmpty(lista)) {
				controlCalidad = lista.get(0);
			}
			
			controlCalidad.setCodigoRespuesta(codigoRespuesta);
			controlCalidad.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRegistroControlCalidad] Fin ");
		return controlCalidad;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarProductoControlCalidad(pe.com.sigbah.common.bean.ProductoControlCalidadBean)
	 */
	@Override
	public List<ProductoControlCalidadBean> listarProductoControlCalidad(ProductoControlCalidadBean producto) throws Exception {
		LOGGER.info("[listarProductoControlCalidad] Inicio ");
		List<ProductoControlCalidadBean> lista = new ArrayList<ProductoControlCalidadBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_ID_CONTROL_CALIDAD", producto.getIdControlCalidad(), Types.NUMERIC);
			input_objParametros.addValue("pi_FLG_TIPO_PRODUCTO", producto.getFlagTipoProducto(), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_PROD_CONTROL_CALID");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_ID_CONTROL_CALIDAD", new SqlParameter("pi_ID_CONTROL_CALIDAD", Types.NUMERIC));
			output_objParametros.put("pi_FLG_TIPO_PRODUCTO", new SqlParameter("pi_FLG_TIPO_PRODUCTO", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new ProductoControlCalidadMapper()));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[listarProductoControlCalidad] Ocurrio un error en la operacion del USP_LISTAR_PROD_CONTROL_CALID");
    			throw new Exception();
    		}
			
			lista = (List<ProductoControlCalidadBean>) out.get("po_Lr_Recordset");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProductoControlCalidad] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#grabarProductoControlCalidad(pe.com.sigbah.common.bean.ProductoControlCalidadBean)
	 */
	@Override
	public ProductoControlCalidadBean grabarProductoControlCalidad(ProductoControlCalidadBean productoControlCalidadBean) throws Exception {
		LOGGER.info("[grabarProductoControlCalidad] Inicio ");
		ProductoControlCalidadBean registroProductoControlCalidad = new ProductoControlCalidadBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_DET_CONTROL_CAL", productoControlCalidadBean.getIdControlCalidad(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_CONTROL_CALIDAD", productoControlCalidadBean.getIdControlCalidad(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_CAT_PRODUCTO", productoControlCalidadBean.getIdProducto(), Types.NUMERIC);
			input_objParametros.addValue("pi_CANT_LOTE", productoControlCalidadBean.getCantidadLote(), Types.NUMERIC);
			input_objParametros.addValue("pi_CANT_MUESTRA", productoControlCalidadBean.getCantidadMuestra(), Types.NUMERIC);
			input_objParametros.addValue("pi_PRIMARIO", productoControlCalidadBean.getPrimario(), Types.VARCHAR);
			input_objParametros.addValue("pi_SECUNDARIO", productoControlCalidadBean.getSecundario(), Types.VARCHAR);			
			input_objParametros.addValue("pi_PAR_OLOR", productoControlCalidadBean.getParOlor(), Types.VARCHAR);
			input_objParametros.addValue("pi_PAR_COLOR", productoControlCalidadBean.getParColor(), Types.VARCHAR);
			input_objParametros.addValue("pi_PAR_TEXTURA", productoControlCalidadBean.getParTextura(), Types.VARCHAR);
			input_objParametros.addValue("pi_PAR_SABOR", productoControlCalidadBean.getParSabor(), Types.VARCHAR);
			input_objParametros.addValue("pi_FLG_CONFOR_PRODUCTO", productoControlCalidadBean.getFlagConforProducto(), Types.VARCHAR);
			input_objParametros.addValue("pi_FLAG_ESPEC_TECNICAS", productoControlCalidadBean.getFlagEspecTecnicas(), Types.VARCHAR);
			input_objParametros.addValue("pi_FEC_VENCIMIENTO", DateUtil.obtenerFechaHoraParseada(productoControlCalidadBean.getFechaVencimiento()), Types.DATE);
			input_objParametros.addValue("pi_USERNAME", productoControlCalidadBean.getUsuarioRegistro(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_PRODUCTO_CC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_DET_CONTROL_CAL", new SqlParameter("pi_IDE_DET_CONTROL_CAL", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_CONTROL_CALIDAD", new SqlParameter("pi_FK_IDE_CONTROL_CALIDAD", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_CAT_PRODUCTO", new SqlParameter("pi_FK_IDE_CAT_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("pi_CANT_LOTE", new SqlParameter("pi_CANT_LOTE", Types.NUMERIC));
			output_objParametros.put("pi_CANT_MUESTRA", new SqlParameter("pi_CANT_MUESTRA", Types.NUMERIC));
			output_objParametros.put("pi_PRIMARIO", new SqlParameter("pi_PRIMARIO", Types.VARCHAR));
			output_objParametros.put("pi_SECUNDARIO", new SqlParameter("pi_SECUNDARIO", Types.VARCHAR));			
			output_objParametros.put("pi_PAR_OLOR", new SqlParameter("pi_PAR_OLOR", Types.VARCHAR));
			output_objParametros.put("pi_PAR_COLOR", new SqlParameter("pi_PAR_COLOR", Types.VARCHAR));
			output_objParametros.put("pi_PAR_TEXTURA", new SqlParameter("pi_PAR_TEXTURA", Types.VARCHAR));
			output_objParametros.put("pi_PAR_SABOR", new SqlParameter("pi_PAR_SABOR", Types.VARCHAR));
			output_objParametros.put("pi_FLG_CONFOR_PRODUCTO", new SqlParameter("pi_FLG_CONFOR_PRODUCTO", Types.VARCHAR));
			output_objParametros.put("pi_FLAG_ESPEC_TECNICAS", new SqlParameter("pi_FLAG_ESPEC_TECNICAS", Types.VARCHAR));
			output_objParametros.put("pi_FEC_VENCIMIENTO", new SqlParameter("pi_FEC_VENCIMIENTO", Types.DATE));
			output_objParametros.put("pi_USERNAME", new SqlParameter("pi_USERNAME", Types.VARCHAR));
			output_objParametros.put("po_PK_IDE_DET_CONTROL_CAL", new SqlOutParameter("po_PK_IDE_DET_CONTROL_CAL", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[grabarProductoControlCalidad] Ocurrio un error en la operacion del USP_INS_UPD_PRODUCTO_CC");
    			throw new Exception();
    		}
			
			registroProductoControlCalidad.setCodigoRespuesta(codigoRespuesta);
			registroProductoControlCalidad.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarProductoControlCalidad] Fin ");
		return registroProductoControlCalidad;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#eliminarProductoControlCalidad(pe.com.sigbah.common.bean.ProductoControlCalidadBean)
	 */
	@Override
	public ProductoControlCalidadBean eliminarProductoControlCalidad(ProductoControlCalidadBean productoControlCalidadBean) throws Exception {
		LOGGER.info("[eliminarProductoControlCalidad] Inicio ");
		ProductoControlCalidadBean registroProductoControlCalidad = new ProductoControlCalidadBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_DET_CONTROL_CAL", productoControlCalidadBean.getIdDetalleControlCalidad(), Types.NUMERIC);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_PRODUCTO_CC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_DET_CONTROL_CAL", new SqlParameter("pi_IDE_DET_CONTROL_CAL", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[eliminarProductoControlCalidad] Ocurrio un error en la operacion del USP_DEL_PRODUCTO_CC");
    			throw new Exception();
    		}
			
			registroProductoControlCalidad.setCodigoRespuesta(codigoRespuesta);
			registroProductoControlCalidad.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarProductoControlCalidad] Fin ");
		return registroProductoControlCalidad;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarDocumentoControlCalidad(pe.com.sigbah.common.bean.DocumentoControlCalidadBean)
	 */
	@Override
	public List<DocumentoControlCalidadBean> listarDocumentoControlCalidad(DocumentoControlCalidadBean documentoControlCalidadBean) throws Exception {
		LOGGER.info("[listarDocumentoControlCalidad] Inicio ");
		List<DocumentoControlCalidadBean> lista = new ArrayList<DocumentoControlCalidadBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_ID_CONTROL_CALIDAD", documentoControlCalidadBean.getIdControlCalidad(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_DOCUMENTO_CC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_ID_CONTROL_CALIDAD", new SqlParameter("pi_ID_CONTROL_CALIDAD", Types.NUMERIC));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new DocumentoControlCalidadMapper()));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[listarDocumentoControlCalidad] Ocurrio un error en la operacion del USP_LISTAR_DOCUMENTO_CC");
    			throw new Exception();
    		}
			
			lista = (List<DocumentoControlCalidadBean>) out.get("po_Lr_Recordset");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarDocumentoControlCalidad] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#grabarDocumentoControlCalidad(pe.com.sigbah.common.bean.DocumentoControlCalidadBean)
	 */
	@Override
	public DocumentoControlCalidadBean grabarDocumentoControlCalidad(DocumentoControlCalidadBean documentoControlCalidadBean) throws Exception {
		LOGGER.info("[grabarDocumentoControlCalidad] Inicio ");
		DocumentoControlCalidadBean registroDocumentoControlCalidad = new DocumentoControlCalidadBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_FK_IDE_CONTROL_CALIDAD", documentoControlCalidadBean.getIdControlCalidad(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_TIP_DOCUMENTO", documentoControlCalidadBean.getIdTipoDocumento(), Types.NUMERIC);
			input_objParametros.addValue("pi_NRO_DOCUMENTO", documentoControlCalidadBean.getNroDocumento(), Types.NUMERIC);
			input_objParametros.addValue("pi_FEC_DOCUMENTO", DateUtil.obtenerFechaHoraParseada(documentoControlCalidadBean.getFechaDocumento()), Types.DATE);
			input_objParametros.addValue("pi_USU_REGISTRO", documentoControlCalidadBean.getUsuarioRegistro(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_DOCUMENTO_CC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_FK_IDE_CONTROL_CALIDAD", new SqlParameter("pi_FK_IDE_CONTROL_CALIDAD", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_TIP_DOCUMENTO", new SqlParameter("pi_FK_IDE_TIP_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("pi_NRO_DOCUMENTO", new SqlParameter("pi_NRO_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("pi_FEC_DOCUMENTO", new SqlParameter("pi_FEC_DOCUMENTO", Types.DATE));
			output_objParametros.put("pi_USU_REGISTRO", new SqlParameter("pi_USU_REGISTRO", Types.VARCHAR));
			output_objParametros.put("po_PK_IDE_DOCUMENTO_CAL", new SqlOutParameter("po_PK_IDE_DOCUMENTO_CAL", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[grabarDocumentoControlCalidad] Ocurrio un error en la operacion del USP_INS_DOCUMENTO_CC");
    			throw new Exception();
    		}
			
			registroDocumentoControlCalidad.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoControlCalidad.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarDocumentoControlCalidad] Fin ");
		return registroDocumentoControlCalidad;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#eliminarDocumentoControlCalidad(pe.com.sigbah.common.bean.DocumentoControlCalidadBean)
	 */
	@Override
	public DocumentoControlCalidadBean eliminarDocumentoControlCalidad(DocumentoControlCalidadBean documentoControlCalidadBean) throws Exception {
		LOGGER.info("[eliminarDocumentoControlCalidad] Inicio ");
		DocumentoControlCalidadBean registroDocumentoControlCalidad = new DocumentoControlCalidadBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_DET_CONTROL_CAL", documentoControlCalidadBean.getIdDocumentoControlCalidad(), Types.NUMERIC);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_DOCUMENTO_CC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_DET_CONTROL_CAL", new SqlParameter("pi_IDE_DET_CONTROL_CAL", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[eliminarDocumentoControlCalidad] Ocurrio un error en la operacion del USP_DEL_DOCUMENTO_CC");
    			throw new Exception();
    		}
			
			registroDocumentoControlCalidad.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoControlCalidad.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarDocumentoControlCalidad] Fin ");
		return registroDocumentoControlCalidad;
	}

}

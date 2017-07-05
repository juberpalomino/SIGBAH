package pe.com.sigbah.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
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
import pe.com.sigbah.common.bean.DetalleProductoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoControlCalidadBean;
import pe.com.sigbah.common.bean.OrdenCompraBean;
import pe.com.sigbah.common.bean.OrdenIngresoBean;
import pe.com.sigbah.common.bean.ProductoControlCalidadBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.LogisticaDao;
import pe.com.sigbah.mapper.AlmacenActivoMapper;
import pe.com.sigbah.mapper.ControlCalidadMapper;
import pe.com.sigbah.mapper.DetalleProductoControlCalidadMapper;
import pe.com.sigbah.mapper.DocumentoControlCalidadMapper;
import pe.com.sigbah.mapper.NroControlCalidadMapper;
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

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#obtenerCorrelativoControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public ControlCalidadBean obtenerCorrelativoControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		LOGGER.info("[obtenerCorrelativoControlCalidad] Inicio ");
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
				LOGGER.info("[obtenerCorrelativoControlCalidad] Ocurrio un error en la operacion del USP_SEL_GENERA_CORREL_CALIDAD");
    			throw new Exception();
    		}

			detalleUsuarioBean.setNroControlCalidad((String) out.get("po_CORRELATIVO"));
			detalleUsuarioBean.setCodigoRespuesta(codigoRespuesta);
			detalleUsuarioBean.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCorrelativoControlCalidad] Fin ");
		return detalleUsuarioBean;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarAlmacenActivo(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
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

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarOrdenCompra()
	 */
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

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#insertarRegistroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
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
		
			registroControlCalidad.setIdControlCalidad(((BigDecimal) out.get("po_PK_IDE_CONTROL_CALIDAD")).intValue());
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

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#actualizarRegistroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
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

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#obtenerRegistroControlCalidad(java.lang.Integer)
	 */
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
			input_objParametros.addValue("pi_IDE_DET_CONTROL_CAL", productoControlCalidadBean.getIdDetalleControlCalidad(), Types.NUMERIC);
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
			input_objParametros.addValue("pi_USU_MODIFICA", productoControlCalidadBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_PRODUCTO_CC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_DET_CONTROL_CAL", new SqlParameter("pi_IDE_DET_CONTROL_CAL", Types.NUMERIC));
			output_objParametros.put("pi_USU_MODIFICA", new SqlParameter("pi_USU_MODIFICA", Types.VARCHAR));
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
			input_objParametros.addValue("pi_IDE_TIP_DOCUMENTO", documentoControlCalidadBean.getIdDocumentoControlCalidad(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_CONTROL_CALIDAD", documentoControlCalidadBean.getIdControlCalidad(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_TIP_DOCUMENTO", documentoControlCalidadBean.getIdTipoDocumento(), Types.NUMERIC);
			input_objParametros.addValue("pi_NRO_DOCUMENTO", documentoControlCalidadBean.getNroDocumento(), Types.VARCHAR);
			input_objParametros.addValue("pi_FEC_DOCUMENTO", DateUtil.obtenerFechaHoraParseada(documentoControlCalidadBean.getFechaDocumento()), Types.DATE);
	        input_objParametros.addValue("pi_COD_ALFRESCO", documentoControlCalidadBean.getCodigoArchivoAlfresco(), Types.VARCHAR);
			input_objParametros.addValue("pi_NOM_ARCHIVO", documentoControlCalidadBean.getNombreArchivo(), Types.VARCHAR);			
			input_objParametros.addValue("pi_USU_REGISTRO", documentoControlCalidadBean.getUsuarioRegistro(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_DOCUMENTO_CC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_TIP_DOCUMENTO", new SqlParameter("pi_IDE_TIP_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_CONTROL_CALIDAD", new SqlParameter("pi_FK_IDE_CONTROL_CALIDAD", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_TIP_DOCUMENTO", new SqlParameter("pi_FK_IDE_TIP_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("pi_NRO_DOCUMENTO", new SqlParameter("pi_NRO_DOCUMENTO", Types.VARCHAR));
			output_objParametros.put("pi_FEC_DOCUMENTO", new SqlParameter("pi_FEC_DOCUMENTO", Types.DATE));
			output_objParametros.put("pi_COD_ALFRESCO", new SqlParameter("pi_COD_ALFRESCO", Types.VARCHAR));
			output_objParametros.put("pi_NOM_ARCHIVO", new SqlParameter("pi_NOM_ARCHIVO", Types.VARCHAR));
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
			input_objParametros.addValue("pi_USU_MODIFICA", documentoControlCalidadBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_DOCUMENTO_CC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_DET_CONTROL_CAL", new SqlParameter("pi_IDE_DET_CONTROL_CAL", Types.NUMERIC));
			output_objParametros.put("pi_USU_MODIFICA", new SqlParameter("pi_USU_MODIFICA", Types.VARCHAR));
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

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarDetalleProductoControlCalidad(java.lang.Integer)
	 */
	@Override
	public List<DetalleProductoControlCalidadBean> listarDetalleProductoControlCalidad(Integer idControlCalidad) throws Exception {
		LOGGER.info("[listarDetalleProductoControlCalidad] Inicio ");
		List<DetalleProductoControlCalidadBean> lista = new ArrayList<DetalleProductoControlCalidadBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_CONTROL_CALIDAD", idControlCalidad, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORTE_CC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_CONTROL_CALIDAD", new SqlParameter("pi_IDE_CONTROL_CALIDAD", Types.NUMERIC));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new DetalleProductoControlCalidadMapper()));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[listarDetalleProductoControlCalidad] Ocurrio un error en la operacion del USP_SEL_REPORTE_CC");
    			throw new Exception();
    		}
			
			lista = (List<DetalleProductoControlCalidadBean>) out.get("po_Lr_Recordset");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarDetalleProductoControlCalidad] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarOrdenIngreso(pe.com.sigbah.common.bean.OrdenIngresoBean)
	 */
	@Override
	public List<OrdenIngresoBean> listarOrdenIngreso(OrdenIngresoBean ordenIngresoBean) throws Exception {
		LOGGER.info("[listarOrdenIngreso] Inicio ");
		List<OrdenIngresoBean> lista = new ArrayList<OrdenIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_COD_ANIO", ordenIngresoBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_DDI", ordenIngresoBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_ALMACEN", Utils.getParam(ordenIngresoBean.getIdAlmacen()), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_TIP_MOVIMIENTO", Utils.getParam(ordenIngresoBean.getCodigoMovimiento()), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_ORDEN_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_IDE_DDI", new SqlParameter("pi_IDE_DDI", Types.VARCHAR));
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.VARCHAR));
			output_objParametros.put("pi_IDE_TIP_MOVIMIENTO", new SqlParameter("pi_IDE_TIP_MOVIMIENTO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new ControlCalidadMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				throw new Exception();
			} else {
				lista = (List<OrdenIngresoBean>) out.get("po_Lr_Recordset");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarOrdenIngreso] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#obtenerCorrelativoOrdenIngreso(pe.com.sigbah.common.bean.OrdenIngresoBean)
	 */
	@Override
	public OrdenIngresoBean obtenerCorrelativoOrdenIngreso(OrdenIngresoBean ordenIngresoBean) throws Exception {
		LOGGER.info("[obtenerCorrelativoOrdenIngreso] Inicio ");
		OrdenIngresoBean detalleUsuarioBean = new OrdenIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_COD_ANIO", ordenIngresoBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DDI", ordenIngresoBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_ALMACEN", ordenIngresoBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", ordenIngresoBean.getTipoOrigen(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_GENERA_NRO_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PO_NRO_INGRESO", new SqlOutParameter("PO_NRO_INGRESO", Types.VARCHAR));
			output_objParametros.put("PO_COD_INGRESO", new SqlOutParameter("PO_COD_INGRESO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[obtenerCorrelativoOrdenIngreso] Ocurrio un error en la operacion del USP_SEL_GENERA_NRO_INGRESO");
    			throw new Exception();
    		}

			detalleUsuarioBean.setNroOrdenIngreso((String) out.get("PO_NRO_INGRESO"));
			detalleUsuarioBean.setCodigoIngreso((String) out.get("PO_COD_INGRESO"));
			detalleUsuarioBean.setCodigoRespuesta(codigoRespuesta);
			detalleUsuarioBean.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCorrelativoOrdenIngreso] Fin ");
		return detalleUsuarioBean;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarNroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public List<ControlCalidadBean> listarNroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		LOGGER.info("[listarNroControlCalidad] Inicio ");
		List<ControlCalidadBean> lista = new ArrayList<ControlCalidadBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_COD_DDI", controlCalidadBean.getCodigoDdi(), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_CONTROL_CALIDAD");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_COD_DDI", new SqlParameter("pi_COD_DDI", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new NroControlCalidadMapper()));
			
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
		LOGGER.info("[listarNroControlCalidad] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#grabarOrdenIngreso(pe.com.sigbah.common.bean.OrdenIngresoBean)
	 */
	@Override
	public OrdenIngresoBean grabarOrdenIngreso(OrdenIngresoBean ordenIngresoBean) throws Exception {
		LOGGER.info("[grabarOrdenIngreso] Inicio ");
		OrdenIngresoBean registroOrdenIngreso = new OrdenIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_INGRESO", ordenIngresoBean.getIdIngreso(), Types.NUMERIC);
			input_objParametros.addValue("pi_COD_ANIO", ordenIngresoBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_MES", ordenIngresoBean.getCodigoMes(), Types.VARCHAR);
			input_objParametros.addValue("pi_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(ordenIngresoBean.getFechaEmision()), Types.DATE);			
			input_objParametros.addValue("pi_FK_IDE_MED_TRANSPORTE", ordenIngresoBean.getIdMedioTransporte(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_TIP_MOVIMIENTO", ordenIngresoBean.getIdMovimiento(), Types.NUMERIC);
			input_objParametros.addValue("pi_COD_ALMACEN", ordenIngresoBean.getCodigoAlmacen(), Types.VARCHAR);			
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", ordenIngresoBean.getIdAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_ID_ALM_PROCEDENCIA", ordenIngresoBean.getIdAlmacenProcedencia(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_PROVEEDOR", ordenIngresoBean.getIdProveedor(), Types.NUMERIC);
			input_objParametros.addValue("pi_COD_DDI", ordenIngresoBean.getCodigoDdi(), Types.VARCHAR);			
			input_objParametros.addValue("pi_NRO_ORDEN_INGRESO", ordenIngresoBean.getNroOrdenIngreso(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_CONTROL_CALIDAD", ordenIngresoBean.getIdControlCalidad(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_CHOFER", ordenIngresoBean.getIdChofer(), Types.NUMERIC);
			input_objParametros.addValue("pi_NRO_PLACA", ordenIngresoBean.getNroPlaca(), Types.VARCHAR);			
			input_objParametros.addValue("pi_FLG_TIP_COMPRA", ordenIngresoBean.getFlagTipoCompra(), Types.VARCHAR);			
			input_objParametros.addValue("pi_FEC_LLEGADA", DateUtil.obtenerFechaHoraParseada(ordenIngresoBean.getFechaLlegada()), Types.DATE);
			input_objParametros.addValue("pi_OBSERVACION", ordenIngresoBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ESTADO", ordenIngresoBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("pi_NRO_ORDEN_COMPRA", ordenIngresoBean.getNroOrdenCompra(), Types.VARCHAR);
			input_objParametros.addValue("pi_FLG_CONTROL_CALIDAD", ordenIngresoBean.getFlagControlCalidad(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_EMP_TRANS", ordenIngresoBean.getIdEmpresaTransporte(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_RESPONSABLE", ordenIngresoBean.getIdResponsable(), Types.NUMERIC);
			input_objParametros.addValue("pi_TIPO_INGRESO", ordenIngresoBean.getTipoIngreso(), Types.VARCHAR);
			input_objParametros.addValue("pi_USU_REGISTRO", ordenIngresoBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("pi_USU_MODIFICA", ordenIngresoBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_REGIST_INGRESO_ALM");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_INGRESO", new SqlParameter("pi_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_COD_MES", new SqlParameter("pi_COD_MES", Types.VARCHAR));
			output_objParametros.put("pi_FEC_EMISION", new SqlParameter("pi_FEC_EMISION", Types.DATE));			
			output_objParametros.put("pi_FK_IDE_MED_TRANSPORTE", new SqlParameter("pi_FK_IDE_MED_TRANSPORTE", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_TIP_MOVIMIENTO", new SqlParameter("pi_FK_IDE_TIP_MOVIMIENTO", Types.NUMERIC));
			output_objParametros.put("pi_COD_ALMACEN", new SqlParameter("pi_COD_ALMACEN", Types.VARCHAR));			
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.VARCHAR));
			output_objParametros.put("pi_FK_ID_ALM_PROCEDENCIA", new SqlParameter("pi_FK_ID_ALM_PROCEDENCIA", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_PROVEEDOR", new SqlParameter("pi_FK_IDE_PROVEEDOR", Types.NUMERIC));			
			output_objParametros.put("pi_COD_DDI", new SqlParameter("pi_COD_DDI", Types.VARCHAR));
			output_objParametros.put("pi_NRO_ORDEN_INGRESO", new SqlParameter("pi_NRO_ORDEN_INGRESO", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_CONTROL_CALIDAD", new SqlParameter("pi_FK_IDE_CONTROL_CALIDAD", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_CHOFER", new SqlParameter("pi_FK_IDE_CHOFER", Types.NUMERIC));
			output_objParametros.put("pi_NRO_PLACA", new SqlParameter("pi_NRO_PLACA", Types.VARCHAR));
			output_objParametros.put("pi_FLG_TIP_COMPRA", new SqlParameter("pi_FLG_TIP_COMPRA", Types.VARCHAR));			
			output_objParametros.put("pi_FEC_LLEGADA", new SqlParameter("pi_FEC_LLEGADA", Types.DATE));
			output_objParametros.put("pi_OBSERVACION", new SqlParameter("pi_OBSERVACION", Types.VARCHAR));			
			output_objParametros.put("pi_FK_IDE_ESTADO", new SqlParameter("pi_FK_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("pi_NRO_ORDEN_COMPRA", new SqlParameter("pi_NRO_ORDEN_COMPRA", Types.VARCHAR));
			output_objParametros.put("pi_FLG_CONTROL_CALIDAD", new SqlParameter("pi_FLG_CONTROL_CALIDAD", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_EMP_TRANS", new SqlParameter("pi_FK_IDE_EMP_TRANS", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_RESPONSABLE", new SqlParameter("pi_FK_IDE_RESPONSABLE", Types.NUMERIC));
			output_objParametros.put("pi_TIPO_INGRESO", new SqlParameter("pi_TIPO_INGRESO", Types.VARCHAR));
			output_objParametros.put("pi_USU_REGISTRO", new SqlParameter("pi_USU_REGISTRO", Types.VARCHAR));
			output_objParametros.put("pi_USU_MODIFICA", new SqlParameter("pi_USU_MODIFICA", Types.VARCHAR));
			output_objParametros.put("pi_SEQ_BAH_M_INGRESO", new SqlOutParameter("pi_SEQ_BAH_M_INGRESO", Types.NUMERIC));
			output_objParametros.put("po_NRO_ORDEN_INGRESO", new SqlOutParameter("po_NRO_ORDEN_INGRESO", Types.VARCHAR));
			output_objParametros.put("po_COD_ORDEN_INGRESO", new SqlOutParameter("po_COD_ORDEN_INGRESO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[grabarOrdenIngreso] Ocurrio un error en la operacion del USP_INS_UPD_REGIST_INGRESO_ALM");
    			throw new Exception();
    		}
		
			registroOrdenIngreso.setIdIngreso(((BigDecimal) out.get("pi_SEQ_BAH_M_INGRESO")).intValue());
			registroOrdenIngreso.setNroOrdenIngreso((String) out.get("po_NRO_ORDEN_INGRESO"));
			registroOrdenIngreso.setCodigoOrdenIngreso((String) out.get("po_COD_ORDEN_INGRESO"));
			registroOrdenIngreso.setCodigoRespuesta(codigoRespuesta);
			registroOrdenIngreso.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarOrdenIngreso] Fin ");
		return registroOrdenIngreso;
	}

}

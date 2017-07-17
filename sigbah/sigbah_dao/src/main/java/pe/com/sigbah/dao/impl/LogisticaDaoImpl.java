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

import org.apache.commons.lang.StringUtils;
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
import pe.com.sigbah.common.bean.DocumentoIngresoBean;
import pe.com.sigbah.common.bean.DocumentoProyectoManifiestoBean;
import pe.com.sigbah.common.bean.DocumentoSalidaBean;
import pe.com.sigbah.common.bean.GuiaRemisionBean;
import pe.com.sigbah.common.bean.LoteProductoBean;
import pe.com.sigbah.common.bean.ManifiestoVehiculoBean;
import pe.com.sigbah.common.bean.OrdenCompraBean;
import pe.com.sigbah.common.bean.OrdenIngresoBean;
import pe.com.sigbah.common.bean.OrdenSalidaBean;
import pe.com.sigbah.common.bean.ProductoControlCalidadBean;
import pe.com.sigbah.common.bean.ProductoIngresoBean;
import pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean;
import pe.com.sigbah.common.bean.ProductoSalidaBean;
import pe.com.sigbah.common.bean.ProyectoManifiestoBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.LogisticaDao;
import pe.com.sigbah.mapper.AlmacenActivoMapper;
import pe.com.sigbah.mapper.ControlCalidadMapper;
import pe.com.sigbah.mapper.DetalleProductoControlCalidadMapper;
import pe.com.sigbah.mapper.DocumentoControlCalidadMapper;
import pe.com.sigbah.mapper.DocumentoIngresoMapper;
import pe.com.sigbah.mapper.DocumentoProyectoManifiestoMapper;
import pe.com.sigbah.mapper.DocumentoSalidaMapper;
import pe.com.sigbah.mapper.GuiaRemisionMapper;
import pe.com.sigbah.mapper.LoteProductoMapper;
import pe.com.sigbah.mapper.ManifiestoMapper;
import pe.com.sigbah.mapper.ManifiestoVehiculoMapper;
import pe.com.sigbah.mapper.NroControlCalidadMapper;
import pe.com.sigbah.mapper.OrdenIngresoMapper;
import pe.com.sigbah.mapper.OrdenSalidaMapper;
import pe.com.sigbah.mapper.ProductoControlCalidadMapper;
import pe.com.sigbah.mapper.ProductoIngresoMapper;
import pe.com.sigbah.mapper.ProductoProyectoManifiestoMapper;
import pe.com.sigbah.mapper.ProductoSalidaMapper;
import pe.com.sigbah.mapper.ProyectoManifiestoMapper;
import pe.com.sigbah.mapper.RegistroControlCalidadMapper;
import pe.com.sigbah.mapper.RegistroGuiaRemisionMapper;
import pe.com.sigbah.mapper.RegistroOrdenIngresoMapper;
import pe.com.sigbah.mapper.RegistroOrdenSalidaMapper;
import pe.com.sigbah.mapper.RegistroProyectoManifiestoMapper;

/**
 * @className: LogisticaDaoImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_LOGISTICA.
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarControlCalidad] Ocurrio un error en la operacion del USP_LISTAR_CONTROL_CALIDAD : "+mensajeRespuesta);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerCorrelativoControlCalidad] Ocurrio un error en la operacion del USP_SEL_GENERA_CORREL_CALIDAD : "+mensajeRespuesta);
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
			input_objParametros.addValue("pi_NRO_PLACA", StringUtils.upperCase(controlCalidadBean.getNroPlaca()), Types.VARCHAR);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarRegistroControlCalidad] Ocurrio un error en la operacion del USP_INS_REGISTRA_CONTROL_CALID : "+mensajeRespuesta);
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
			input_objParametros.addValue("pi_NRO_PLACA", StringUtils.upperCase(controlCalidadBean.getNroPlaca()), Types.VARCHAR);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[actualizarRegistroControlCalidad] Ocurrio un error en la operacion del USP_UPD_REGISTRA_CONTROL_CALID : "+mensajeRespuesta);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRegistroControlCalidad] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_CONTROL_CALIDA : "+mensajeRespuesta);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductoControlCalidad] Ocurrio un error en la operacion del USP_LISTAR_PROD_CONTROL_CALID : "+mensajeRespuesta);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarProductoControlCalidad] Ocurrio un error en la operacion del USP_INS_UPD_PRODUCTO_CC : "+mensajeRespuesta);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarProductoControlCalidad] Ocurrio un error en la operacion del USP_DEL_PRODUCTO_CC : "+mensajeRespuesta);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarDocumentoControlCalidad] Ocurrio un error en la operacion del USP_LISTAR_DOCUMENTO_CC : "+mensajeRespuesta);
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
			input_objParametros.addValue("pi_NRO_DOCUMENTO", StringUtils.upperCase(documentoControlCalidadBean.getNroDocumento()), Types.VARCHAR);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarDocumentoControlCalidad] Ocurrio un error en la operacion del USP_INS_DOCUMENTO_CC : "+mensajeRespuesta);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarDocumentoControlCalidad] Ocurrio un error en la operacion del USP_DEL_DOCUMENTO_CC : "+mensajeRespuesta);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarDetalleProductoControlCalidad] Ocurrio un error en la operacion del USP_SEL_REPORTE_CC : "+mensajeRespuesta);
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
			input_objParametros.addValue("pi_IDE_ALMACEN", Utils.getParam(ordenIngresoBean.getCodigoAlmacen()), Types.VARCHAR);
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
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new OrdenIngresoMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarOrdenIngreso] Ocurrio un error en la operacion del USP_LISTAR_ORDEN_INGRESO : "+mensajeRespuesta);
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
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerCorrelativoOrdenIngreso] Ocurrio un error en la operacion del USP_SEL_GENERA_NRO_INGRESO : "+mensajeRespuesta);
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
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarNroControlCalidad] Ocurrio un error en la operacion del USP_SEL_TAB_CONTROL_CALIDAD : "+mensajeRespuesta);
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
			input_objParametros.addValue("pi_NRO_PLACA", StringUtils.upperCase(ordenIngresoBean.getNroPlaca()), Types.VARCHAR);			
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
			output_objParametros.put("po_SEQ_BAH_M_INGRESO", new SqlOutParameter("po_SEQ_BAH_M_INGRESO", Types.NUMERIC));
			output_objParametros.put("po_NRO_ORDEN_INGRESO", new SqlOutParameter("po_NRO_ORDEN_INGRESO", Types.VARCHAR));
			output_objParametros.put("po_COD_ORDEN_INGRESO", new SqlOutParameter("po_COD_ORDEN_INGRESO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarOrdenIngreso] Ocurrio un error en la operacion del USP_INS_UPD_REGIST_INGRESO_ALM : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroOrdenIngreso.setIdIngreso(((BigDecimal) out.get("po_SEQ_BAH_M_INGRESO")).intValue());
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

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#obtenerRegistroOrdenIngreso(java.lang.Integer)
	 */
	@Override
	public OrdenIngresoBean obtenerRegistroOrdenIngreso(Integer idIngreso) throws Exception {
		LOGGER.info("[obtenerRegistroOrdenIngreso] Inicio ");
		OrdenIngresoBean ordenIngreso = new OrdenIngresoBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_INGRESO", idIngreso, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_ORDEN_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_INGRESO", new SqlParameter("pi_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new RegistroOrdenIngresoMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRegistroOrdenIngreso] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_ORDEN_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<OrdenIngresoBean> lista = (List<OrdenIngresoBean>) out.get("po_Lr_Recordset");
			if (!Utils.isEmpty(lista)) {
				ordenIngreso = lista.get(0);
			}
			
			ordenIngreso.setCodigoRespuesta(codigoRespuesta);
			ordenIngreso.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRegistroOrdenIngreso] Fin ");
		return ordenIngreso;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarProductoIngreso(pe.com.sigbah.common.bean.ProductoIngresoBean)
	 */
	@Override
	public List<ProductoIngresoBean> listarProductoIngreso(ProductoIngresoBean productoIngresoBean) throws Exception {
		LOGGER.info("[listarProductoIngreso] Inicio ");
		List<ProductoIngresoBean> lista = new ArrayList<ProductoIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_INGRESO_DET", productoIngresoBean.getIdIngreso(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LIST_INGRESO_PRODUCTO_ALM");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_INGRESO_DET", new SqlParameter("pi_IDE_INGRESO_DET", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new ProductoIngresoMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductoIngreso] Ocurrio un error en la operacion del USP_LIST_INGRESO_PRODUCTO_ALM : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<ProductoIngresoBean>) out.get("po_Lr_Recordset");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}
		LOGGER.info("[listarProductoIngreso] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#grabarProductoIngreso(pe.com.sigbah.common.bean.ProductoIngresoBean)
	 */
	@Override
	public ProductoIngresoBean grabarProductoIngreso(ProductoIngresoBean productoIngresoBean) throws Exception {
		LOGGER.info("[grabarProductoIngreso] Inicio ");
		ProductoIngresoBean registroProductoIngreso = new ProductoIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_INGRESO_DET", productoIngresoBean.getIdDetalleIngreso(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_INGRESO", productoIngresoBean.getIdIngreso(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_PRODUCTO", productoIngresoBean.getIdProducto(), Types.NUMERIC);
			input_objParametros.addValue("pi_CANTIDAD", productoIngresoBean.getCantidad(), Types.NUMERIC);			
			input_objParametros.addValue("pi_PREC_UNITARIO", productoIngresoBean.getPrecioUnitario(), Types.NUMERIC);
			input_objParametros.addValue("pi_FEC_VENCIMIENTO", DateUtil.obtenerFechaHoraParseada(productoIngresoBean.getFechaVencimiento()), Types.DATE);
			input_objParametros.addValue("pi_FEC_INGRESO", DateUtil.obtenerFechaHoraParseada(productoIngresoBean.getFechaIngreso()), Types.DATE);			
			input_objParametros.addValue("pi_IDE_PROGRAMACION", productoIngresoBean.getIdProgramacion(), Types.NUMERIC);
			input_objParametros.addValue("pi_IDE_DONACION", productoIngresoBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("pi_NRO_LOTE", productoIngresoBean.getNroLote(), Types.NUMERIC);
			input_objParametros.addValue("pi_USUARIO", productoIngresoBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_INGRESO_PRODUC_ALM");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_INGRESO_DET", new SqlParameter("pi_IDE_INGRESO_DET", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_INGRESO", new SqlParameter("pi_FK_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_PRODUCTO", new SqlParameter("pi_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("pi_CANTIDAD", new SqlParameter("pi_CANTIDAD", Types.NUMERIC));
			output_objParametros.put("pi_PREC_UNITARIO", new SqlParameter("pi_PREC_UNITARIO", Types.NUMERIC));
			output_objParametros.put("pi_FEC_VENCIMIENTO", new SqlParameter("pi_FEC_VENCIMIENTO", Types.DATE));
			output_objParametros.put("pi_FEC_INGRESO", new SqlParameter("pi_FEC_INGRESO", Types.DATE));			
			output_objParametros.put("pi_IDE_PROGRAMACION", new SqlParameter("pi_IDE_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("pi_IDE_DONACION", new SqlParameter("pi_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("pi_NRO_LOTE", new SqlParameter("pi_NRO_LOTE", Types.NUMERIC));
			output_objParametros.put("pi_USUARIO", new SqlParameter("pi_USUARIO", Types.VARCHAR));
			output_objParametros.put("po_IDE_INGRESO_DET", new SqlOutParameter("po_IDE_INGRESO_DET", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarProductoIngreso] Ocurrio un error en la operacion del USP_INS_UPD_INGRESO_PRODUC_ALM : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroProductoIngreso.setCodigoRespuesta(codigoRespuesta);
			registroProductoIngreso.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarProductoIngreso] Fin ");
		return registroProductoIngreso;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#eliminarProductoIngreso(pe.com.sigbah.common.bean.ProductoIngresoBean)
	 */
	@Override
	public ProductoIngresoBean eliminarProductoIngreso(ProductoIngresoBean productoIngresoBean) throws Exception {
		LOGGER.info("[eliminarProductoIngreso] Inicio ");
		ProductoIngresoBean registroProductoIngreso = new ProductoIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_INGRESO_DET", productoIngresoBean.getIdDetalleIngreso(), Types.NUMERIC);
			input_objParametros.addValue("pi_USU_MODIFICA", productoIngresoBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_PRODUCTO_INGRESO_ALM");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_INGRESO_DET", new SqlParameter("pi_IDE_INGRESO_DET", Types.NUMERIC));
			output_objParametros.put("pi_USU_MODIFICA", new SqlParameter("pi_USU_MODIFICA", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarProductoIngreso] Ocurrio un error en la operacion del USP_DEL_PRODUCTO_INGRESO_ALM : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoIngreso.setCodigoRespuesta(codigoRespuesta);
			registroProductoIngreso.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarProductoIngreso] Fin ");
		return registroProductoIngreso;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarLoteProductos(pe.com.sigbah.common.bean.LoteProductoBean)
	 */
	@Override
	public List<LoteProductoBean> listarLoteProductos(LoteProductoBean loteProductoBean) throws Exception {
		LOGGER.info("[listarLoteProductos] Inicio ");
		List<LoteProductoBean> lista = new ArrayList<LoteProductoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_DDI", loteProductoBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("pi_IDE_ALMACEN", loteProductoBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_IDE_PRODUCTO", loteProductoBean.getIdProducto(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_LOTE_PRODUCTO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_DDI", new SqlParameter("pi_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_IDE_PRODUCTO", new SqlParameter("pi_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new LoteProductoMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarLoteProductos] Ocurrio un error en la operacion del USP_SEL_TAB_LOTE_PRODUCTO : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<LoteProductoBean>) out.get("po_Lr_Recordset");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}
		LOGGER.info("[listarLoteProductos] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarDocumentoIngreso(pe.com.sigbah.common.bean.DocumentoIngresoBean)
	 */
	@Override
	public List<DocumentoIngresoBean> listarDocumentoIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception {
		LOGGER.info("[listarDocumentoIngreso] Inicio ");
		List<DocumentoIngresoBean> lista = new ArrayList<DocumentoIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_ID_INGRESO", documentoIngresoBean.getIdIngreso(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_DOCUMENTO_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_ID_INGRESO", new SqlParameter("pi_ID_INGRESO", Types.NUMERIC));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new DocumentoIngresoMapper()));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarDocumentoIngreso] Ocurrio un error en la operacion del USP_LISTAR_DOCUMENTO_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<DocumentoIngresoBean>) out.get("po_Lr_Recordset");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarDocumentoIngreso] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#grabarDocumentoIngreso(pe.com.sigbah.common.bean.DocumentoIngresoBean)
	 */
	@Override
	public DocumentoIngresoBean grabarDocumentoIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception {
		LOGGER.info("[grabarDocumentoIngreso] Inicio ");
		DocumentoIngresoBean registroDocumentoIngreso = new DocumentoIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_DOCUMENTO_ING", documentoIngresoBean.getIdDocumentoIngreso(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_INGRESO", documentoIngresoBean.getIdIngreso(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_TIP_DOCUMENTO", documentoIngresoBean.getIdTipoDocumento(), Types.NUMERIC);
			input_objParametros.addValue("pi_NRO_DOCUMENTO", StringUtils.upperCase(documentoIngresoBean.getNroDocumento()), Types.VARCHAR);
			input_objParametros.addValue("pi_FEC_DOCUMENTO", DateUtil.obtenerFechaHoraParseada(documentoIngresoBean.getFechaDocumento()), Types.DATE);
	        input_objParametros.addValue("pi_COD_ALFRESCO", documentoIngresoBean.getCodigoArchivoAlfresco(), Types.VARCHAR);
			input_objParametros.addValue("pi_NOM_ARCHIVO", documentoIngresoBean.getNombreArchivo(), Types.VARCHAR);			
			input_objParametros.addValue("pi_USU_REGISTRO", documentoIngresoBean.getUsuarioRegistro(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_DOCUMENTO_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_DOCUMENTO_ING", new SqlParameter("pi_IDE_DOCUMENTO_ING", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_INGRESO", new SqlParameter("pi_FK_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_TIP_DOCUMENTO", new SqlParameter("pi_FK_IDE_TIP_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("pi_NRO_DOCUMENTO", new SqlParameter("pi_NRO_DOCUMENTO", Types.VARCHAR));
			output_objParametros.put("pi_FEC_DOCUMENTO", new SqlParameter("pi_FEC_DOCUMENTO", Types.DATE));
			output_objParametros.put("pi_COD_ALFRESCO", new SqlParameter("pi_COD_ALFRESCO", Types.VARCHAR));
			output_objParametros.put("pi_NOM_ARCHIVO", new SqlParameter("pi_NOM_ARCHIVO", Types.VARCHAR));
			output_objParametros.put("pi_USU_REGISTRO", new SqlParameter("pi_USU_REGISTRO", Types.VARCHAR));
			output_objParametros.put("po_PK_IDE_DOCUMENTO_ING", new SqlOutParameter("po_PK_IDE_DOCUMENTO_ING", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarDocumentoIngreso] Ocurrio un error en la operacion del USP_INS_UPD_DOCUMENTO_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroDocumentoIngreso.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoIngreso.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarDocumentoIngreso] Fin ");
		return registroDocumentoIngreso;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#eliminarDocumentoIngreso(pe.com.sigbah.common.bean.DocumentoIngresoBean)
	 */
	@Override
	public DocumentoIngresoBean eliminarDocumentoIngreso(DocumentoIngresoBean documentoIngresoBean) throws Exception {
		LOGGER.info("[eliminarDocumentoIngreso] Inicio ");
		DocumentoIngresoBean registroDocumentoIngreso = new DocumentoIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_ID_DOCUMENTO_ING", documentoIngresoBean.getIdDocumentoIngreso(), Types.NUMERIC);
			input_objParametros.addValue("pi_USU_MODIFICA", documentoIngresoBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_DOCUMENTO_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_ID_DOCUMENTO_ING", new SqlParameter("pi_ID_DOCUMENTO_ING", Types.NUMERIC));
			output_objParametros.put("pi_USU_MODIFICA", new SqlParameter("pi_USU_MODIFICA", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarDocumentoIngreso] Ocurrio un error en la operacion del USP_DEL_DOCUMENTO_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroDocumentoIngreso.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoIngreso.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarDocumentoIngreso] Fin ");
		return registroDocumentoIngreso;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarOrdenSalida(pe.com.sigbah.common.bean.OrdenSalidaBean)
	 */
	@Override
	public List<OrdenSalidaBean> listarOrdenSalida(OrdenSalidaBean ordenSalidaBean) throws Exception {
		LOGGER.info("[listarOrdenSalida] Inicio ");
		List<OrdenSalidaBean> lista = new ArrayList<OrdenSalidaBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", ordenSalidaBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", ordenSalidaBean.getCodigoMes(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_DDI", ordenSalidaBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DDI", ordenSalidaBean.getCodigoDdi(), Types.VARCHAR);			
			input_objParametros.addValue("PI_IDE_ALMACEN", ordenSalidaBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_MOVIMIENTO", ordenSalidaBean.getIdMovimiento(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", ordenSalidaBean.getTipoOrigen(), Types.VARCHAR);			
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_ORDENES_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));			
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_IDE_MOVIMIENTO", new SqlParameter("PI_IDE_MOVIMIENTO", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new OrdenSalidaMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarOrdenSalida] Ocurrio un error en la operacion del USP_SEL_LISTAR_ORDENES_SALIDA : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<OrdenSalidaBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarOrdenSalida] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#obtenerCorrelativoOrdenSalida(pe.com.sigbah.common.bean.OrdenSalidaBean)
	 */
	@Override
	public OrdenSalidaBean obtenerCorrelativoOrdenSalida(OrdenSalidaBean ordenSalidaBean) throws Exception {
		LOGGER.info("[obtenerCorrelativoOrdenSalida] Inicio ");
		OrdenSalidaBean detalleUsuarioBean = new OrdenSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_COD_ANIO", ordenSalidaBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DDI", ordenSalidaBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_ALMACEN", ordenSalidaBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ALMACEN", ordenSalidaBean.getCodigoAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("PI_TIPO_ORIGEN", ordenSalidaBean.getTipoOrigen(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_GENERA_NRO_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_COD_ALMACEN", new SqlParameter("PI_COD_ALMACEN", Types.VARCHAR));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PO_NRO_SALIDA", new SqlOutParameter("PO_NRO_SALIDA", Types.VARCHAR));
			output_objParametros.put("PO_COD_SALIDA", new SqlOutParameter("PO_COD_SALIDA", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerCorrelativoOrdenSalida] Ocurrio un error en la operacion del USP_SEL_GENERA_NRO_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}

			detalleUsuarioBean.setNroOrdenSalida((String) out.get("PO_NRO_SALIDA"));
			detalleUsuarioBean.setCodigoSalida((String) out.get("PO_COD_SALIDA"));
			detalleUsuarioBean.setCodigoRespuesta(codigoRespuesta);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCorrelativoOrdenSalida] Fin ");
		return detalleUsuarioBean;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#grabarOrdenSalida(pe.com.sigbah.common.bean.OrdenSalidaBean)
	 */
	@Override
	public OrdenSalidaBean grabarOrdenSalida(OrdenSalidaBean ordenSalidaBean) throws Exception {
		LOGGER.info("[grabarOrdenSalida] Inicio ");
		OrdenSalidaBean registroOrdenSalida = new OrdenSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_SALIDA", ordenSalidaBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", ordenSalidaBean.getTipoOrigen(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_ANIO", ordenSalidaBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", ordenSalidaBean.getCodigoMes(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_ALMACEN", ordenSalidaBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DDI", ordenSalidaBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_ALMACEN", ordenSalidaBean.getCodigoAlmacen(), Types.VARCHAR);			
			input_objParametros.addValue("PI_NRO_ORDEN_SALIDA", ordenSalidaBean.getNroOrdenSalida(), Types.VARCHAR);
			input_objParametros.addValue("PI_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(ordenSalidaBean.getFechaEmision()), Types.DATE);
			input_objParametros.addValue("PI_COD_UBIGEO", ordenSalidaBean.getCodigoUbigeo(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_PROGRAMACION", ordenSalidaBean.getIdProgramacion(), Types.NUMERIC);			
			input_objParametros.addValue("PI_FK_ID_RESPONSABLE", ordenSalidaBean.getIdResponsable(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_RESPONSABLE_EXT", ordenSalidaBean.getIdResponsableExt(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_ID_SOLICITANTE", ordenSalidaBean.getIdSolicitante(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_ID_RESPONSABLE_RECEPCION", ordenSalidaBean.getIdResponsableRecepcion(), Types.NUMERIC);			
			input_objParametros.addValue("PI_FK_IDE_PROYECTO_MANIF", ordenSalidaBean.getIdProyectoManifiesto(), Types.NUMERIC);			
			input_objParametros.addValue("PI_FK_IDE_TIP_MOVIMIENTO", ordenSalidaBean.getIdMovimiento(), Types.NUMERIC);			
			input_objParametros.addValue("PI_FK_IDE_ALM_DESTINO", ordenSalidaBean.getIdAlmacenDestino(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_ALM_DESTINO_EXT", ordenSalidaBean.getIdAlmacenDestinoExt(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_MED_TRANSPORTE", ordenSalidaBean.getIdMedioTransporte(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_EMP_TRANSPORTE", ordenSalidaBean.getIdEmpresaTransporte(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_CHOFER", ordenSalidaBean.getIdChofer(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_PLACA", StringUtils.upperCase(ordenSalidaBean.getNroPlaca()), Types.VARCHAR);
			input_objParametros.addValue("PI_FEC_ENTREGA", DateUtil.obtenerFechaHoraParseada(ordenSalidaBean.getFechaEntrega()), Types.DATE);
			input_objParametros.addValue("PI_FLG_TIP_DESTINO", ordenSalidaBean.getFlagTipoDestino(), Types.VARCHAR);
			input_objParametros.addValue("PI_OBSERVACION", ordenSalidaBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_ESTADO", ordenSalidaBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", ordenSalidaBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", ordenSalidaBean.getIndControl(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_ORDEN_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));			
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_ALMACEN", new SqlParameter("PI_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_COD_ALMACEN", new SqlParameter("PI_COD_ALMACEN", Types.VARCHAR));
			output_objParametros.put("PI_NRO_ORDEN_SALIDA", new SqlParameter("PI_NRO_ORDEN_SALIDA", Types.VARCHAR));
			output_objParametros.put("PI_FEC_EMISION", new SqlParameter("PI_FEC_EMISION", Types.DATE));
			output_objParametros.put("PI_COD_UBIGEO", new SqlParameter("PI_COD_UBIGEO", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_PROGRAMACION", new SqlParameter("PI_FK_IDE_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("PI_FK_ID_RESPONSABLE", new SqlParameter("PI_FK_ID_RESPONSABLE", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_RESPONSABLE_EXT", new SqlParameter("PI_FK_IDE_RESPONSABLE_EXT", Types.NUMERIC));
			output_objParametros.put("PI_FK_ID_SOLICITANTE", new SqlParameter("PI_FK_ID_SOLICITANTE", Types.NUMERIC));
			output_objParametros.put("PI_FK_ID_RESPONSABLE_RECEPCION", new SqlParameter("PI_FK_ID_RESPONSABLE_RECEPCION", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PROYECTO_MANIF", new SqlParameter("PI_FK_IDE_PROYECTO_MANIF", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_TIP_MOVIMIENTO", new SqlParameter("PI_FK_IDE_TIP_MOVIMIENTO", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_ALM_DESTINO", new SqlParameter("PI_FK_IDE_ALM_DESTINO", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_ALM_DESTINO_EXT", new SqlParameter("PI_FK_IDE_ALM_DESTINO_EXT", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_MED_TRANSPORTE", new SqlParameter("PI_FK_IDE_MED_TRANSPORTE", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_EMP_TRANSPORTE", new SqlParameter("PI_FK_IDE_EMP_TRANSPORTE", Types.NUMERIC));
			output_objParametros.put("PI_IDE_CHOFER", new SqlParameter("PI_IDE_CHOFER", Types.NUMERIC));
			output_objParametros.put("PI_NRO_PLACA", new SqlParameter("PI_NRO_PLACA", Types.VARCHAR));
			output_objParametros.put("PI_FEC_ENTREGA", new SqlParameter("PI_FEC_ENTREGA", Types.DATE));
			output_objParametros.put("PI_FLG_TIP_DESTINO", new SqlParameter("PI_FLG_TIP_DESTINO", Types.VARCHAR));
			output_objParametros.put("PI_OBSERVACION", new SqlParameter("PI_OBSERVACION", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_ESTADO", new SqlParameter("PI_FK_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_SEQ_BAH_M_SALIDA", new SqlOutParameter("PO_SEQ_BAH_M_SALIDA", Types.NUMERIC));			
			output_objParametros.put("PO_COD_SALIDA_CONCATENADO", new SqlOutParameter("PO_COD_SALIDA_CONCATENADO", Types.VARCHAR));
			output_objParametros.put("PO_NRO_ORDEN_SALIDA", new SqlOutParameter("PO_NRO_ORDEN_SALIDA", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarOrdenSalida] Ocurrio un error en la operacion del USP_INS_UPD_ORDEN_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroOrdenSalida.setIdSalida(((BigDecimal) out.get("PO_SEQ_BAH_M_SALIDA")).intValue());
			registroOrdenSalida.setNroOrdenSalida((String) out.get("PO_NRO_ORDEN_SALIDA"));
			registroOrdenSalida.setCodigoSalida((String) out.get("PO_COD_SALIDA_CONCATENADO"));
			registroOrdenSalida.setCodigoRespuesta(codigoRespuesta);
			registroOrdenSalida.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarOrdenSalida] Fin ");
		return registroOrdenSalida;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#obtenerRegistroOrdenSalida(java.lang.Integer, java.lang.String)
	 */
	@Override
	public OrdenSalidaBean obtenerRegistroOrdenSalida(Integer idSalida, String anio) throws Exception {
		LOGGER.info("[obtenerRegistroOrdenSalida] Inicio ");
		OrdenSalidaBean ordenSalida = new OrdenSalidaBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_COD_ANIO", anio, Types.VARCHAR);			
			input_objParametros.addValue("PI_IDE_SALIDA", idSalida, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_EDITAR_ORDEN_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new RegistroOrdenSalidaMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRegistroOrdenSalida] Ocurrio un error en la operacion del USP_SEL_EDITAR_ORDEN_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<OrdenSalidaBean> lista = (List<OrdenSalidaBean>) out.get("PO_LR_RECORDSET");
			if (!Utils.isEmpty(lista)) {
				ordenSalida = lista.get(0);
			}
			
			ordenSalida.setCodigoRespuesta(codigoRespuesta);
			ordenSalida.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRegistroOrdenSalida] Fin ");
		return ordenSalida;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarProductoSalida(pe.com.sigbah.common.bean.ProductoSalidaBean)
	 */
	@Override
	public List<ProductoSalidaBean> listarProductoSalida(ProductoSalidaBean productoSalidaBean) throws Exception {
		LOGGER.info("[listarProductoSalida] Inicio ");
		List<ProductoSalidaBean> lista = new ArrayList<ProductoSalidaBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_FK_IDE_SALIDA", productoSalidaBean.getIdSalida(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_PROD_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_FK_IDE_SALIDA", new SqlParameter("PI_FK_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new ProductoSalidaMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductoSalida] Ocurrio un error en la operacion del USP_SEL_LISTAR_PROD_SALIDA : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<ProductoSalidaBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}
		LOGGER.info("[listarProductoSalida] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#grabarProductoSalida(pe.com.sigbah.common.bean.ProductoSalidaBean)
	 */
	@Override
	public ProductoSalidaBean grabarProductoSalida(ProductoSalidaBean productoSalidaBean) throws Exception {
		LOGGER.info("[grabarProductoSalida] Inicio ");
		ProductoSalidaBean registroProductoSalida = new ProductoSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_ID_SALIDA_DET", productoSalidaBean.getIdDetalleSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_SALIDA", productoSalidaBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_PRODUCTO", productoSalidaBean.getIdProducto(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_LOTE", productoSalidaBean.getNroLote(), Types.VARCHAR);			
			input_objParametros.addValue("PI_CANTIDAD", productoSalidaBean.getCantidad(), Types.NUMERIC);
			input_objParametros.addValue("PI_PREC_UNITARIO", productoSalidaBean.getPrecioUnitario(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", productoSalidaBean.getUsuarioRegistro(), Types.VARCHAR);			
			input_objParametros.addValue("PI_CONTROL", productoSalidaBean.getIndControl(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_PRODUCTO_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_SALIDA_DET", new SqlParameter("PI_ID_SALIDA_DET", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_SALIDA", new SqlParameter("PI_FK_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PRODUCTO", new SqlParameter("PI_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("PI_NRO_LOTE", new SqlParameter("PI_NRO_LOTE", Types.VARCHAR));
			output_objParametros.put("PI_CANTIDAD", new SqlParameter("PI_CANTIDAD", Types.NUMERIC));
			output_objParametros.put("PI_PREC_UNITARIO", new SqlParameter("PI_PREC_UNITARIO", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));			
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarProductoSalida] Ocurrio un error en la operacion del USP_INS_UPD_PRODUCTO_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroProductoSalida.setCodigoRespuesta(codigoRespuesta);
			registroProductoSalida.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarProductoSalida] Fin ");
		return registroProductoSalida;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#eliminarProductoSalida(pe.com.sigbah.common.bean.ProductoSalidaBean)
	 */
	@Override
	public ProductoSalidaBean eliminarProductoSalida(ProductoSalidaBean productoSalidaBean) throws Exception {
		LOGGER.info("[eliminarProductoSalida] Inicio ");
		ProductoSalidaBean registroProductoSalida = new ProductoSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_ID_SALIDA_DET", productoSalidaBean.getIdDetalleSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", productoSalidaBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_SALIDA_PRODUCTO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_SALIDA_DET", new SqlParameter("PI_ID_SALIDA_DET", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarProductoSalida] Ocurrio un error en la operacion del USP_DEL_SALIDA_PRODUCTO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoSalida.setCodigoRespuesta(codigoRespuesta);
			registroProductoSalida.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarProductoSalida] Fin ");
		return registroProductoSalida;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarDocumentoSalida(pe.com.sigbah.common.bean.DocumentoSalidaBean)
	 */
	@Override
	public List<DocumentoSalidaBean> listarDocumentoSalida(DocumentoSalidaBean documentoSalidaBean) throws Exception {
		LOGGER.info("[listarDocumentoSalida] Inicio ");
		List<DocumentoSalidaBean> lista = new ArrayList<DocumentoSalidaBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_SALIDA", documentoSalidaBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", documentoSalidaBean.getTipoOrigen(), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_DOC_SALIDAS");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new DocumentoSalidaMapper()));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarDocumentoSalida] Ocurrio un error en la operacion del USP_SEL_LISTAR_DOC_SALIDAS : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<DocumentoSalidaBean>) out.get("PO_CURSOR");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarDocumentoSalida] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#grabarDocumentoSalida(pe.com.sigbah.common.bean.DocumentoSalidaBean)
	 */
	@Override
	public DocumentoSalidaBean grabarDocumentoSalida(DocumentoSalidaBean documentoSalidaBean) throws Exception {
		LOGGER.info("[grabarDocumentoSalida] Inicio ");
		DocumentoSalidaBean registroDocumentoSalida = new DocumentoSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_SALIDA", documentoSalidaBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DOCUMENTO", documentoSalidaBean.getIdDocumentoSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_DOCUMENTO", StringUtils.upperCase(documentoSalidaBean.getNroDocumento()), Types.VARCHAR);			
			input_objParametros.addValue("PI_IDE_TIP_DOCUMENTO", documentoSalidaBean.getIdTipoDocumento(), Types.NUMERIC);
			input_objParametros.addValue("PI_FEC_DOCUMENTO", DateUtil.obtenerFechaHoraParseada(documentoSalidaBean.getFechaDocumento()), Types.DATE);
	        input_objParametros.addValue("PI_COD_ALFRESCO", documentoSalidaBean.getCodigoArchivoAlfresco(), Types.VARCHAR);
			input_objParametros.addValue("PI_NOM_ARCHIVO", documentoSalidaBean.getNombreArchivo(), Types.VARCHAR);			
			input_objParametros.addValue("PI_USERNAME", documentoSalidaBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", documentoSalidaBean.getIndControl(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_FILE_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PI_IDE_DOCUMENTO", new SqlParameter("PI_IDE_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PI_NRO_DOCUMENTO", new SqlParameter("PI_NRO_DOCUMENTO", Types.VARCHAR));			
			output_objParametros.put("PI_IDE_TIP_DOCUMENTO", new SqlParameter("PI_IDE_TIP_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PI_FEC_DOCUMENTO", new SqlParameter("PI_FEC_DOCUMENTO", Types.DATE));
			output_objParametros.put("PI_COD_ALFRESCO", new SqlParameter("PI_COD_ALFRESCO", Types.VARCHAR));
			output_objParametros.put("PI_NOM_ARCHIVO", new SqlParameter("PI_NOM_ARCHIVO", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarDocumentoSalida] Ocurrio un error en la operacion del USP_INS_UPD_FILE_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroDocumentoSalida.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoSalida.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarDocumentoSalida] Fin ");
		return registroDocumentoSalida;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#eliminarDocumentoSalida(pe.com.sigbah.common.bean.DocumentoSalidaBean)
	 */
	@Override
	public DocumentoSalidaBean eliminarDocumentoSalida(DocumentoSalidaBean documentoSalidaBean) throws Exception {
		LOGGER.info("[eliminarDocumentoSalida] Inicio ");
		DocumentoSalidaBean registroDocumentoSalida = new DocumentoSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DOCUMENTO", documentoSalidaBean.getIdDocumentoSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", documentoSalidaBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_SALIDA_FILE");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DOCUMENTO", new SqlParameter("PI_IDE_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarDocumentoSalida] Ocurrio un error en la operacion del USP_DEL_SALIDA_FILE : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroDocumentoSalida.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoSalida.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarDocumentoSalida] Fin ");
		return registroDocumentoSalida;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarManifiesto(pe.com.sigbah.common.bean.ProyectoManifiestoBean)
	 */
	@Override
	public List<ProyectoManifiestoBean> listarManifiesto(ProyectoManifiestoBean proyectoManifiestoBean) throws Exception {
		LOGGER.info("[listarManifiesto] Inicio ");
		List<ProyectoManifiestoBean> lista = new ArrayList<ProyectoManifiestoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_COD_ANIO", proyectoManifiestoBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_DDI", proyectoManifiestoBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("pi_IDE_ALMACEN", proyectoManifiestoBean.getIdAlmacen(), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_PROY_MANIFIESTO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_IDE_DDI", new SqlParameter("pi_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new ManifiestoMapper()));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarManifiesto] Ocurrio un error en la operacion del USP_LISTAR_PROYECTO_MANIFIESTO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ProyectoManifiestoBean>) out.get("po_Lr_Recordset");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarManifiesto] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarProyectoManifiesto(pe.com.sigbah.common.bean.ProyectoManifiestoBean)
	 */
	@Override
	public List<ProyectoManifiestoBean> listarProyectoManifiesto(ProyectoManifiestoBean proyectoManifiestoBean) throws Exception {
		LOGGER.info("[listarProyectoManifiesto] Inicio ");
		List<ProyectoManifiestoBean> lista = new ArrayList<ProyectoManifiestoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_COD_ANIO", proyectoManifiestoBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_MES", proyectoManifiestoBean.getCodigoMes(), Types.VARCHAR);		
			input_objParametros.addValue("pi_IDE_ALMACEN", proyectoManifiestoBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_IDE_TIP_MOVIMIENTO", Utils.getParam(proyectoManifiestoBean.getCodigoMovimiento()), Types.VARCHAR);			
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_PROYECTO_MANIFIESTO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_COD_MES", new SqlParameter("pi_COD_MES", Types.VARCHAR));
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_IDE_TIP_MOVIMIENTO", new SqlParameter("pi_IDE_TIP_MOVIMIENTO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new ProyectoManifiestoMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProyectoManifiesto] Ocurrio un error en la operacion del USP_LISTAR_PROYECTO_MANIFIESTO : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<ProyectoManifiestoBean>) out.get("po_Lr_Recordset");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProyectoManifiesto] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#obtenerCorrelativoProyectoManifiesto(pe.com.sigbah.common.bean.ProyectoManifiestoBean)
	 */
	@Override
	public ProyectoManifiestoBean obtenerCorrelativoProyectoManifiesto(ProyectoManifiestoBean proyectoManifiestoBean) throws Exception {
		LOGGER.info("[obtenerCorrelativoProyectoManifiesto] Inicio ");
		ProyectoManifiestoBean detalleUsuarioBean = new ProyectoManifiestoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_COD_ANIO", proyectoManifiestoBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DDI", proyectoManifiestoBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_ALMACEN", proyectoManifiestoBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", proyectoManifiestoBean.getTipoOrigen(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_GEN_NRO_PROY_MANIFIEST");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PO_NRO_PROYECTO", new SqlOutParameter("PO_NRO_PROYECTO", Types.VARCHAR));
			output_objParametros.put("PO_COD_PROYECTO", new SqlOutParameter("PO_COD_PROYECTO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerCorrelativoProyectoManifiesto] Ocurrio un error en la operacion del USP_SEL_GEN_NRO_PROY_MANIFIEST : "+mensajeRespuesta);
    			throw new Exception();
    		}

			detalleUsuarioBean.setNroProyectoManifiesto((String) out.get("PO_NRO_PROYECTO"));
			detalleUsuarioBean.setCodigoProyectoManifiesto((String) out.get("PO_COD_PROYECTO"));
			detalleUsuarioBean.setCodigoRespuesta(codigoRespuesta);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCorrelativoProyectoManifiesto] Fin ");
		return detalleUsuarioBean;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#grabarProyectoManifiesto(pe.com.sigbah.common.bean.ProyectoManifiestoBean)
	 */
	@Override
	public ProyectoManifiestoBean grabarProyectoManifiesto(ProyectoManifiestoBean proyectoManifiestoBean) throws Exception {
		LOGGER.info("[grabarProyectoManifiesto] Inicio ");
		ProyectoManifiestoBean registroProyectoManifiesto = new ProyectoManifiestoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_PROYECTO_MANIF", proyectoManifiestoBean.getIdProyectoManifiesto(), Types.NUMERIC);
			input_objParametros.addValue("pi_TIPO_ORIGEN", proyectoManifiestoBean.getTipoOrigen(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_ANIO", proyectoManifiestoBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_DDI", proyectoManifiestoBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_MES", proyectoManifiestoBean.getCodigoMes(), Types.VARCHAR);			
			input_objParametros.addValue("pi_COD_ALMACEN", proyectoManifiestoBean.getCodigoAlmacen(), Types.VARCHAR);			
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", proyectoManifiestoBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN_DEST", proyectoManifiestoBean.getIdAlmacenDestino(), Types.NUMERIC);		
			input_objParametros.addValue("pi_NRO_PROY_MANIFIESTO", proyectoManifiestoBean.getNroProyectoManifiesto(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_TIP_MOVIMIENTO", proyectoManifiestoBean.getIdMovimiento(), Types.NUMERIC);			
			input_objParametros.addValue("pi_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(proyectoManifiestoBean.getFechaEmision()), Types.DATE);
			input_objParametros.addValue("pi_FLG_PROGRAMACION", proyectoManifiestoBean.getFlagProgramacion(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_PROGRAMACION", proyectoManifiestoBean.getIdProgramacion(), Types.NUMERIC);			
			input_objParametros.addValue("pi_FK_IDE_ESTADO", proyectoManifiestoBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("pi_OBSERVACION", proyectoManifiestoBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("pi_USUARIO", proyectoManifiestoBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_REG_PROYECTO_MANIF");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_PROYECTO_MANIF", new SqlParameter("pi_IDE_PROYECTO_MANIF", Types.NUMERIC));			
			output_objParametros.put("pi_TIPO_ORIGEN", new SqlParameter("pi_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_COD_DDI", new SqlParameter("pi_COD_DDI", Types.VARCHAR));
			output_objParametros.put("pi_COD_MES", new SqlParameter("pi_COD_MES", Types.VARCHAR));
			output_objParametros.put("pi_COD_ALMACEN", new SqlParameter("pi_COD_ALMACEN", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_ALMACEN_DEST", new SqlParameter("pi_FK_IDE_ALMACEN_DEST", Types.NUMERIC));			
			output_objParametros.put("pi_NRO_PROY_MANIFIESTO", new SqlParameter("pi_NRO_PROY_MANIFIESTO", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_TIP_MOVIMIENTO", new SqlParameter("pi_FK_IDE_TIP_MOVIMIENTO", Types.NUMERIC));			
			output_objParametros.put("pi_FEC_EMISION", new SqlParameter("pi_FEC_EMISION", Types.DATE));
			output_objParametros.put("pi_FLG_PROGRAMACION", new SqlParameter("pi_FLG_PROGRAMACION", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_PROGRAMACION", new SqlParameter("pi_FK_IDE_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_ESTADO", new SqlParameter("pi_FK_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("pi_OBSERVACION", new SqlParameter("pi_OBSERVACION", Types.VARCHAR));
			output_objParametros.put("pi_USUARIO", new SqlParameter("pi_USUARIO", Types.VARCHAR));
			output_objParametros.put("po_IDE_PROYECTO_MANIF", new SqlOutParameter("po_IDE_PROYECTO_MANIF", Types.NUMERIC));			
			output_objParametros.put("po_NRO_PROYECTO", new SqlOutParameter("po_NRO_PROYECTO", Types.VARCHAR));
			output_objParametros.put("po_COD_PROYECTO", new SqlOutParameter("po_COD_PROYECTO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarProyectoManifiesto] Ocurrio un error en la operacion del USP_INS_UPD_REG_PROYECTO_MANIF : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroProyectoManifiesto.setIdProyectoManifiesto(((BigDecimal) out.get("po_IDE_PROYECTO_MANIF")).intValue());
			registroProyectoManifiesto.setNroProyectoManifiesto((String) out.get("po_NRO_PROYECTO"));
			registroProyectoManifiesto.setCodigoProyectoManifiesto((String) out.get("po_COD_PROYECTO"));
			registroProyectoManifiesto.setCodigoRespuesta(codigoRespuesta);
			registroProyectoManifiesto.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarProyectoManifiesto] Fin ");
		return registroProyectoManifiesto;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#obtenerRegistroProyectoManifiesto(java.lang.Integer)
	 */
	@Override
	public ProyectoManifiestoBean obtenerRegistroProyectoManifiesto(Integer idProyecto) throws Exception {
		LOGGER.info("[obtenerRegistroProyectoManifiesto] Inicio ");
		ProyectoManifiestoBean proyectoManifiesto = new ProyectoManifiestoBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();	
			input_objParametros.addValue("pi_IDE_PROYECTO_MANIF", idProyecto, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_PROY_MANIFIEST");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_PROYECTO_MANIF", new SqlParameter("pi_IDE_PROYECTO_MANIF", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new RegistroProyectoManifiestoMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRegistroProyectoManifiesto] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_PROY_MANIFIEST : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<ProyectoManifiestoBean> lista = (List<ProyectoManifiestoBean>) out.get("po_Lr_Recordset");
			if (!Utils.isEmpty(lista)) {
				proyectoManifiesto = lista.get(0);
			}
			
			proyectoManifiesto.setCodigoRespuesta(codigoRespuesta);
			proyectoManifiesto.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRegistroProyectoManifiesto] Fin ");
		return proyectoManifiesto;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarProductoProyectoManifiesto(pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean)
	 */
	@Override
	public List<ProductoProyectoManifiestoBean> listarProductoProyectoManifiesto(ProductoProyectoManifiestoBean productoProyectoManifiestoBean) throws Exception {
		LOGGER.info("[listarProductoProyectoManifiesto] Inicio ");
		List<ProductoProyectoManifiestoBean> lista = new ArrayList<ProductoProyectoManifiestoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_PROYECTO_MANIF", productoProyectoManifiestoBean.getIdProyectoManifiesto(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LIST_PROYECTO_MANIF_PROD");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_PROYECTO_MANIF", new SqlParameter("pi_IDE_PROYECTO_MANIF", Types.NUMERIC));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new ProductoProyectoManifiestoMapper()));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductoProyectoManifiesto] Ocurrio un error en la operacion del USP_LIST_PROYECTO_MANIF_PROD : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<ProductoProyectoManifiestoBean>) out.get("po_Lr_Recordset");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}
		LOGGER.info("[listarProductoProyectoManifiesto] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#grabarProductoProyectoManifiesto(pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean)
	 */
	@Override
	public ProductoProyectoManifiestoBean grabarProductoProyectoManifiesto(ProductoProyectoManifiestoBean productoProyectoManifiestoBean) throws Exception {
		LOGGER.info("[grabarProductoProyectoManifiesto] Inicio ");
		ProductoProyectoManifiestoBean registroProductoProyectoManifiesto = new ProductoProyectoManifiestoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_ALMACEN", productoProyectoManifiestoBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_IDE_DET_PROYECTO_MANIF", productoProyectoManifiestoBean.getIdDetalleProyecto(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_PROYECTO_MANIF", productoProyectoManifiestoBean.getIdProyectoManifiesto(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_PRODUCTO", productoProyectoManifiestoBean.getIdProducto(), Types.NUMERIC);		
			input_objParametros.addValue("pi_CANTIDAD", productoProyectoManifiestoBean.getCantidad(), Types.NUMERIC);
			input_objParametros.addValue("pi_USUARIO", productoProyectoManifiestoBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_PROY_MANIF_PROD");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_IDE_DET_PROYECTO_MANIF", new SqlParameter("pi_IDE_DET_PROYECTO_MANIF", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_PROYECTO_MANIF", new SqlParameter("pi_FK_IDE_PROYECTO_MANIF", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_PRODUCTO", new SqlParameter("pi_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("pi_CANTIDAD", new SqlParameter("pi_CANTIDAD", Types.NUMERIC));
			output_objParametros.put("pi_USUARIO", new SqlParameter("pi_USUARIO", Types.VARCHAR));			
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarProductoProyectoManifiesto] Ocurrio un error en la operacion del USP_INS_UPD_PROY_MANIF_PROD : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroProductoProyectoManifiesto.setCodigoRespuesta(codigoRespuesta);
			registroProductoProyectoManifiesto.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarProductoProyectoManifiesto] Fin ");
		return registroProductoProyectoManifiesto;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#eliminarProductoProyectoManifiesto(pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean)
	 */
	@Override
	public ProductoProyectoManifiestoBean eliminarProductoProyectoManifiesto(ProductoProyectoManifiestoBean productoProyectoManifiestoBean) throws Exception {
		LOGGER.info("[eliminarProductoProyectoManifiesto] Inicio ");
		ProductoProyectoManifiestoBean registroProductoProyectoManifiesto = new ProductoProyectoManifiestoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_ID_SALIDA_DET", productoProyectoManifiestoBean.getIdDetalleProyecto(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", productoProyectoManifiestoBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_PROY_MANIF_PROD");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_SALIDA_DET", new SqlParameter("PI_ID_SALIDA_DET", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarProductoProyectoManifiesto] Ocurrio un error en la operacion del USP_DEL_PROY_MANIF_PROD : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoProyectoManifiesto.setCodigoRespuesta(codigoRespuesta);
			registroProductoProyectoManifiesto.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarProductoProyectoManifiesto] Fin ");
		return registroProductoProyectoManifiesto;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarDocumentoProyectoManifiesto(pe.com.sigbah.common.bean.DocumentoProyectoManifiestoBean)
	 */
	@Override
	public List<DocumentoProyectoManifiestoBean> listarDocumentoProyectoManifiesto(DocumentoProyectoManifiestoBean documentoProyectoManifiestoBean) throws Exception {
		LOGGER.info("[listarDocumentoProyectoManifiesto] Inicio ");
		List<DocumentoProyectoManifiestoBean> lista = new ArrayList<DocumentoProyectoManifiestoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_PROYECTO_MANIF", documentoProyectoManifiestoBean.getIdProyectoManifiesto(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_DOCUMENT_PROY_MANIF");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_PROYECTO_MANIF", new SqlParameter("pi_IDE_PROYECTO_MANIF", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new DocumentoProyectoManifiestoMapper()));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarDocumentoProyectoManifiesto] Ocurrio un error en la operacion del USP_LISTAR_DOCUMENT_PROY_MANIF : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<DocumentoProyectoManifiestoBean>) out.get("po_Lr_Recordset");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarDocumentoProyectoManifiesto] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#grabarDocumentoProyectoManifiesto(pe.com.sigbah.common.bean.DocumentoProyectoManifiestoBean)
	 */
	@Override
	public DocumentoProyectoManifiestoBean grabarDocumentoProyectoManifiesto(DocumentoProyectoManifiestoBean documentoProyectoManifiestoBean) throws Exception {
		LOGGER.info("[grabarDocumentoProyectoManifiesto] Inicio ");
		DocumentoProyectoManifiestoBean registroDocumentoProyectoManifiesto = new DocumentoProyectoManifiestoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_DOCUMENTO_PROY", documentoProyectoManifiestoBean.getIdDocumentoProyecto(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_PROYECTO_MANIF", documentoProyectoManifiestoBean.getIdProyectoManifiesto(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_TIP_DOCUMENTO", documentoProyectoManifiestoBean.getIdTipoDocumento(), Types.NUMERIC);			
			input_objParametros.addValue("pi_NRO_DOCUMENTO", StringUtils.upperCase(documentoProyectoManifiestoBean.getNroDocumento()), Types.VARCHAR);			
			input_objParametros.addValue("pi_FEC_DOCUMENTO", DateUtil.obtenerFechaHoraParseada(documentoProyectoManifiestoBean.getFechaDocumento()), Types.DATE);
	        input_objParametros.addValue("pi_COD_ALFRESCO", documentoProyectoManifiestoBean.getCodigoArchivoAlfresco(), Types.VARCHAR);
			input_objParametros.addValue("pi_NOM_ARCHIVO", documentoProyectoManifiestoBean.getNombreArchivo(), Types.VARCHAR);			
			input_objParametros.addValue("pi_USU_REGISTRO", documentoProyectoManifiestoBean.getUsuarioRegistro(), Types.VARCHAR);	

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_DOCUMENTO_PROY");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_DOCUMENTO_PROY", new SqlParameter("pi_IDE_DOCUMENTO_PROY", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_PROYECTO_MANIF", new SqlParameter("pi_FK_IDE_PROYECTO_MANIF", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_TIP_DOCUMENTO", new SqlParameter("pi_FK_IDE_TIP_DOCUMENTO", Types.NUMERIC));			
			output_objParametros.put("pi_NRO_DOCUMENTO", new SqlParameter("pi_NRO_DOCUMENTO", Types.VARCHAR));			
			output_objParametros.put("pi_FEC_DOCUMENTO", new SqlParameter("pi_FEC_DOCUMENTO", Types.DATE));
			output_objParametros.put("pi_COD_ALFRESCO", new SqlParameter("pi_COD_ALFRESCO", Types.VARCHAR));
			output_objParametros.put("pi_NOM_ARCHIVO", new SqlParameter("pi_NOM_ARCHIVO", Types.VARCHAR));
			output_objParametros.put("pi_USU_REGISTRO", new SqlParameter("pi_USU_REGISTRO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarDocumentoProyectoManifiesto] Ocurrio un error en la operacion del USP_INS_UPD_DOCUMENTO_PROY : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroDocumentoProyectoManifiesto.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoProyectoManifiesto.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarDocumentoProyectoManifiesto] Fin ");
		return registroDocumentoProyectoManifiesto;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#eliminarDocumentoProyectoManifiesto(pe.com.sigbah.common.bean.DocumentoProyectoManifiestoBean)
	 */
	@Override
	public DocumentoProyectoManifiestoBean eliminarDocumentoProyectoManifiesto(DocumentoProyectoManifiestoBean documentoProyectoManifiestoBean) throws Exception {
		LOGGER.info("[eliminarDocumentoProyectoManifiesto] Inicio ");
		DocumentoProyectoManifiestoBean registroDocumentoProyectoManifiesto = new DocumentoProyectoManifiestoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_DOCUMENTO_PROY", documentoProyectoManifiestoBean.getIdDocumentoProyecto(), Types.NUMERIC);
			input_objParametros.addValue("pi_USU_MODIFICA", documentoProyectoManifiestoBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_DOCUMENTO_PROY");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_DOCUMENTO_PROY", new SqlParameter("pi_IDE_DOCUMENTO_PROY", Types.NUMERIC));
			output_objParametros.put("pi_USU_MODIFICA", new SqlParameter("pi_USU_MODIFICA", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarDocumentoProyectoManifiesto] Ocurrio un error en la operacion del USP_DEL_DOCUMENTO_PROY : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroDocumentoProyectoManifiesto.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoProyectoManifiesto.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarDocumentoProyectoManifiesto] Fin ");
		return registroDocumentoProyectoManifiesto;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#verificarProductosProgramacion(java.lang.Integer)
	 */
	@Override
	public int verificarProductosProgramacion(Integer idProyecto) throws Exception {
		LOGGER.info("[verificarProductosProgramacion] Inicio ");
		Integer indicador = null;
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_PROYECTO_MANIF", idProyecto, Types.NUMERIC);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_PROC_COUNT_PROD_PROY_MANIF");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_PROYECTO_MANIF", new SqlParameter("pi_IDE_PROYECTO_MANIF", Types.NUMERIC));
			output_objParametros.put("po_count_productos", new SqlOutParameter("po_count_productos", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[verificarProductosProgramacion] Ocurrio un error en la operacion del USP_PROC_COUNT_PROD_PROY_MANIF : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			indicador = ((BigDecimal) out.get("po_count_productos")).intValue();
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[verificarProductosProgramacion] Fin ");
		return indicador;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#procesarManifiestoProducto(pe.com.sigbah.common.bean.ProyectoManifiestoBean)
	 */
	@Override
	public String procesarManifiestoProducto(ProyectoManifiestoBean proyectoManifiestoBean) throws Exception {
		LOGGER.info("[procesarManifiestoProducto] Inicio ");
		String indicador = null;
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_PROGRAMACION", proyectoManifiestoBean.getIdProgramacion(), Types.NUMERIC);
			input_objParametros.addValue("pi_IDE_PROYECTO_MANIF", proyectoManifiestoBean.getIdProyectoManifiesto(), Types.NUMERIC);
			input_objParametros.addValue("pi_USUARIO", proyectoManifiestoBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_PROC_PROYECTO_MANIF_PROD");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_PROGRAMACION", new SqlParameter("pi_IDE_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("pi_IDE_PROYECTO_MANIF", new SqlParameter("pi_IDE_PROYECTO_MANIF", Types.NUMERIC));
			output_objParametros.put("pi_USUARIO", new SqlParameter("pi_USUARIO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			indicador = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (indicador.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[procesarManifiestoProducto] Ocurrio un error en la operacion del USP_PROC_PROYECTO_MANIF_PROD : "+mensajeRespuesta);
    			throw new Exception();
    		}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[procesarManifiestoProducto] Fin ");
		return indicador;
	}
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarProyectoManifiestoVehiculo(pe.com.sigbah.common.bean.ManifiestoVehiculoBean)
	 */
	@Override
	public List<ManifiestoVehiculoBean> listarManifiestoVehiculo(ManifiestoVehiculoBean manifiestoVehiculoBean) throws Exception {
		LOGGER.info("[listarManifiestoVehiculo] Inicio ");
		List<ManifiestoVehiculoBean> lista = new ArrayList<ManifiestoVehiculoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_PROYECTO_MANIF", manifiestoVehiculoBean.getIdProyectoManifiesto(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LIST_PROY_MANIF_VEHICULO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_PROYECTO_MANIF", new SqlParameter("pi_IDE_PROYECTO_MANIF", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new ManifiestoVehiculoMapper()));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarManifiestoVehiculo] Ocurrio un error en la operacion del USP_LIST_PROY_MANIF_VEHICULO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ManifiestoVehiculoBean>) out.get("po_Lr_Recordset");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarManifiestoVehiculo] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#procesarManifiestoVehiculo(pe.com.sigbah.common.bean.ManifiestoVehiculoBean)
	 */
	@Override
	public ManifiestoVehiculoBean procesarManifiestoVehiculo(ManifiestoVehiculoBean manifiestoVehiculoBean) throws Exception {
		LOGGER.info("[procesarManifiestoVehiculo] Inicio ");
		ManifiestoVehiculoBean registroManifiestoVehiculo = new ManifiestoVehiculoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_PROYECTO_MANIF", manifiestoVehiculoBean.getIdProyectoManifiesto(), Types.NUMERIC);
			input_objParametros.addValue("pi_FLAG_VEHICULO", manifiestoVehiculoBean.getFlagVehiculo(), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_TIP_CAMION", manifiestoVehiculoBean.getIdTipoCamion(), Types.NUMERIC);
			input_objParametros.addValue("pi_VOLUMEN", manifiestoVehiculoBean.getVolumen(), Types.NUMERIC);
			input_objParametros.addValue("pi_USU_REGISTRO", manifiestoVehiculoBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_PROC_VEHICULO_PROYECTO_MAN");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_PROYECTO_MANIF", new SqlParameter("pi_IDE_PROYECTO_MANIF", Types.NUMERIC));
			output_objParametros.put("pi_FLAG_VEHICULO", new SqlParameter("pi_FLAG_VEHICULO", Types.VARCHAR));
			output_objParametros.put("pi_IDE_TIP_CAMION", new SqlParameter("pi_IDE_TIP_CAMION", Types.NUMERIC));
			output_objParametros.put("pi_VOLUMEN", new SqlParameter("pi_VOLUMEN", Types.NUMERIC));
			output_objParametros.put("pi_USU_REGISTRO", new SqlParameter("pi_USU_REGISTRO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[procesarManifiestoVehiculo] Ocurrio un error en la operacion del USP_PROC_VEHICULO_PROYECTO_MAN : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroManifiestoVehiculo.setCodigoRespuesta(codigoRespuesta);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[procesarManifiestoVehiculo] Fin ");
		return registroManifiestoVehiculo;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarGuiaRemision(pe.com.sigbah.common.bean.GuiaRemisionBean)
	 */
	@Override
	public List<GuiaRemisionBean> listarGuiaRemision(GuiaRemisionBean guiaRemisionBean) throws Exception {
		LOGGER.info("[listarGuiaRemision] Inicio ");
		List<GuiaRemisionBean> lista = new ArrayList<GuiaRemisionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_COD_ANIO", guiaRemisionBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_MES", guiaRemisionBean.getCodigoMes(), Types.VARCHAR);		
			input_objParametros.addValue("pi_IDE_ALMACEN", guiaRemisionBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_IDE_TIP_MOVIMIENTO", Utils.getParam(guiaRemisionBean.getCodigoMovimiento()), Types.VARCHAR);			
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_GUIA_REMISION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_COD_MES", new SqlParameter("pi_COD_MES", Types.VARCHAR));
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_IDE_TIP_MOVIMIENTO", new SqlParameter("pi_IDE_TIP_MOVIMIENTO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new GuiaRemisionMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarGuiaRemision] Ocurrio un error en la operacion del USP_LISTAR_GUIA_REMISION : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<GuiaRemisionBean>) out.get("po_Lr_Recordset");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarGuiaRemision] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#obtenerRegistroGuiaRemision(java.lang.Integer)
	 */
	@Override
	public GuiaRemisionBean obtenerRegistroGuiaRemision(Integer idGuiaRemision) throws Exception {
		LOGGER.info("[obtenerRegistroGuiaRemision] Inicio ");
		GuiaRemisionBean guiaRemision = new GuiaRemisionBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_GUIA_REMISION", idGuiaRemision, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_GUIA_REMISION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_GUIA_REMISION", new SqlParameter("pi_IDE_GUIA_REMISION", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new RegistroGuiaRemisionMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRegistroGuiaRemision] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_GUIA_REMISION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<GuiaRemisionBean> lista = (List<GuiaRemisionBean>) out.get("po_Lr_Recordset");
			if (!Utils.isEmpty(lista)) {
				guiaRemision = lista.get(0);
			}
			
			guiaRemision.setCodigoRespuesta(codigoRespuesta);
			guiaRemision.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRegistroGuiaRemision] Fin ");
		return guiaRemision;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#insertarGuiaRemision(pe.com.sigbah.common.bean.GuiaRemisionBean)
	 */
	@Override
	public GuiaRemisionBean insertarGuiaRemision(GuiaRemisionBean guiaRemisionBean) throws Exception {
		LOGGER.info("[insertarGuiaRemision] Inicio ");
		GuiaRemisionBean registroGuiaRemision = new GuiaRemisionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_TIPO_ORIGEN", guiaRemisionBean.getTipoOrigen(), Types.VARCHAR);
			input_objParametros.addValue("PI_cod_anio", guiaRemisionBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_cod_mes", guiaRemisionBean.getCodigoMes(), Types.VARCHAR);
			input_objParametros.addValue("PI_fk_ide_salida", guiaRemisionBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_fec_emision", DateUtil.obtenerFechaHoraParseada(guiaRemisionBean.getFechaEmision()), Types.DATE);
			input_objParametros.addValue("PI_cod_ddi", guiaRemisionBean.getCodigoDdi(), Types.VARCHAR);			
			input_objParametros.addValue("PI_cod_almacen", guiaRemisionBean.getCodigoAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", guiaRemisionBean.getUsuarioRegistro(), Types.VARCHAR);		
			input_objParametros.addValue("PI_fk_ide_estado", guiaRemisionBean.getIdEstado(), Types.NUMERIC);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_GUIA_REMISION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));			
			output_objParametros.put("PI_cod_anio", new SqlParameter("PI_cod_anio", Types.VARCHAR));
			output_objParametros.put("PI_cod_mes", new SqlParameter("PI_cod_mes", Types.VARCHAR));
			output_objParametros.put("PI_fk_ide_salida", new SqlParameter("PI_fk_ide_salida", Types.NUMERIC));
			output_objParametros.put("PI_fec_emision", new SqlParameter("PI_fec_emision", Types.DATE));
			output_objParametros.put("PI_cod_ddi", new SqlParameter("PI_cod_ddi", Types.VARCHAR));
			output_objParametros.put("PI_cod_almacen", new SqlParameter("PI_cod_almacen", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));			
			output_objParametros.put("PI_fk_ide_estado", new SqlParameter("PI_fk_ide_estado", Types.NUMERIC));
			output_objParametros.put("PO_ide_guia_remision", new SqlOutParameter("PO_ide_guia_remision", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarGuiaRemision] Ocurrio un error en la operacion del USP_INS_GUIA_REMISION : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroGuiaRemision.setIdGuiaRemision(((BigDecimal) out.get("PO_ide_guia_remision")).intValue());
			registroGuiaRemision.setCodigoRespuesta(codigoRespuesta);
			registroGuiaRemision.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarGuiaRemision] Fin ");
		return registroGuiaRemision;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#anularGuiaRemision(pe.com.sigbah.common.bean.GuiaRemisionBean)
	 */
	@Override
	public GuiaRemisionBean anularGuiaRemision(GuiaRemisionBean guiaRemisionBean) throws Exception {
		LOGGER.info("[anularGuiaRemision] Inicio ");
		GuiaRemisionBean registroGuiaRemision = new GuiaRemisionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_COD_ANIO", guiaRemisionBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_SALIDA", guiaRemisionBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", guiaRemisionBean.getTipoOrigen(), Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", guiaRemisionBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_ANULA_GUIA_REMISION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_SALIDA", new SqlParameter("PI_FK_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[anularGuiaRemision] Ocurrio un error en la operacion del USP_ANULA_GUIA_REMISION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroGuiaRemision.setCodigoRespuesta(codigoRespuesta);
			registroGuiaRemision.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[anularGuiaRemision] Fin ");
		return registroGuiaRemision;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#actualizarGuiaRemision(pe.com.sigbah.common.bean.GuiaRemisionBean)
	 */
	@Override
	public GuiaRemisionBean actualizarGuiaRemision(GuiaRemisionBean guiaRemisionBean) throws Exception {
		LOGGER.info("[actualizarGuiaRemision] Inicio ");
		GuiaRemisionBean registroGuiaRemision = new GuiaRemisionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_fk_ide_salida", guiaRemisionBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_ide_guia_remision", guiaRemisionBean.getIdGuiaRemision(), Types.NUMERIC);
			input_objParametros.addValue("PI_observacion_guia", guiaRemisionBean.getObservacionGuiaRemision(), Types.VARCHAR);
			input_objParametros.addValue("PI_observacion_acta", guiaRemisionBean.getObservacionActaEntregaRecepcion(), Types.VARCHAR);			
			input_objParametros.addValue("PI_observacion_manifiesto", guiaRemisionBean.getObservacionManifiestoCarga(), Types.VARCHAR);
			input_objParametros.addValue("PI_fk_ide_estado", guiaRemisionBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", guiaRemisionBean.getUsuarioRegistro(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_UPD_GUIA_REMISION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_fk_ide_salida", new SqlParameter("PI_fk_ide_salida", Types.NUMERIC));
			output_objParametros.put("PI_ide_guia_remision", new SqlParameter("PI_ide_guia_remision", Types.NUMERIC));
			output_objParametros.put("PI_observacion_guia", new SqlParameter("PI_observacion_guia", Types.VARCHAR));
			output_objParametros.put("PI_observacion_acta", new SqlParameter("PI_observacion_acta", Types.VARCHAR));
			output_objParametros.put("PI_observacion_manifiesto", new SqlParameter("PI_observacion_manifiesto", Types.VARCHAR));
			output_objParametros.put("PI_fk_ide_estado", new SqlParameter("PI_fk_ide_estado", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));			
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[actualizarGuiaRemision] Ocurrio un error en la operacion del USP_UPD_GUIA_REMISION : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroGuiaRemision.setCodigoRespuesta(codigoRespuesta);
			registroGuiaRemision.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarGuiaRemision] Fin ");
		return registroGuiaRemision;
	}

}

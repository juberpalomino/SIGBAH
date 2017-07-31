package pe.com.sigbah.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sigbah.common.bean.EstadoProgramacionBean;
import pe.com.sigbah.common.bean.ProgramacionAlmacenBean;
import pe.com.sigbah.common.bean.ProgramacionBean;
import pe.com.sigbah.common.bean.RacionOperativaBean;
import pe.com.sigbah.common.bean.RequerimientoBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.ProgramacionRequerimientoDao;
import pe.com.sigbah.mapper.ProgramacionAlmacenMapper;
import pe.com.sigbah.mapper.ProgramacionMapper;
import pe.com.sigbah.mapper.ProgramacionRacionOperativaMapper;
import pe.com.sigbah.mapper.ProgramacionRequerimientoMapper;
import pe.com.sigbah.mapper.RegistroProgramacionMapper;

/**
 * @className: ProgramacionRequerimientoDaoImpl.java
 * @description: 
 * @date: 29/07/2017
 * @author: Junior Huaman Flores.
 */
@SuppressWarnings("unchecked")
@Repository
public class ProgramacionRequerimientoDaoImpl extends JdbcDaoSupport implements ProgramacionRequerimientoDao, Serializable {

	private static final long serialVersionUID = 1L;
	private transient final Log LOGGER = LogFactory.getLog(getClass());
	
	private SimpleJdbcCall objJdbcCall;
	
	/**
	 * @param dataSource
	 */
	@Autowired
	public ProgramacionRequerimientoDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionRequerimientoDao#listarProgramacion(pe.com.sigbah.common.bean.ProgramacionBean)
	 */
	@Override
	public List<ProgramacionBean> listarProgramacion(ProgramacionBean programacionBean) throws Exception {
		LOGGER.info("[listarProgramacion] Inicio ");
		List<ProgramacionBean> lista = new ArrayList<ProgramacionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", programacionBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", Utils.getParam(programacionBean.getCodigoMes()), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_DDI", programacionBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("PI_FENOMENO", Utils.getParamInt(programacionBean.getIdFenomeno()), Types.NUMERIC);
			input_objParametros.addValue("PI_ESTADO", Utils.getParamInt(programacionBean.getIdEstado()), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_PROGRAMACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PI_FENOMENO", new SqlParameter("PI_FENOMENO", Types.NUMERIC));
			output_objParametros.put("PI_ESTADO", new SqlParameter("PI_ESTADO", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new ProgramacionMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProgramacion] Ocurrio un error en la operacion del USP_SEL_LISTAR_PROGRAMACION : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<ProgramacionBean>) out.get("PO_LR_RECORDSET");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProgramacion] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionRequerimientoDao#obtenerCorrelativoProgramacion(pe.com.sigbah.common.bean.ProgramacionBean)
	 */
	@Override
	public ProgramacionBean obtenerCorrelativoProgramacion(ProgramacionBean programacionBean) throws Exception {
		LOGGER.info("[obtenerCorrelativoProgramacion] Inicio ");
		ProgramacionBean registroProgramacion = new ProgramacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_ANIO", programacionBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_DDI", programacionBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DDI", programacionBean.getCodigoDdi(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_GENERA_CORR_PROG");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ANIO", new SqlParameter("PI_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_DDI", new SqlParameter("PI_DDI", Types.NUMERIC));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PO_COD_PROG", new SqlOutParameter("PO_COD_PROG", Types.VARCHAR));
			output_objParametros.put("PO_COD_PROG_CONCATENADO", new SqlOutParameter("PO_COD_PROG_CONCATENADO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerCorrelativoProgramacion] Ocurrio un error en la operacion del USP_SEL_GENERA_CORR_PROG : "+mensajeRespuesta);
    			throw new Exception();
    		}

			registroProgramacion.setCodigoProgramacion((String) out.get("PO_COD_PROG"));
			registroProgramacion.setNroProgramacion((String) out.get("PO_COD_PROG_CONCATENADO"));
			registroProgramacion.setCodigoRespuesta(codigoRespuesta);
			registroProgramacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCorrelativoProgramacion] Fin ");
		return registroProgramacion;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionRequerimientoDao#obtenerRegistroProgramacion(java.lang.Integer)
	 */
	@Override
	public ProgramacionBean obtenerRegistroProgramacion(Integer idProgramacion) throws Exception {
		LOGGER.info("[obtenerRegistroProgramacion] Inicio ");
		ProgramacionBean programacion = new ProgramacionBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_PROGRAMACION", idProgramacion, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_EDITAR_PROGRAMACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_PROGRAMACION", new SqlParameter("PI_IDE_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATOSGENERALES", new SqlOutParameter("PO_CURSOR_DATOSGENERALES", OracleTypes.CURSOR, new RegistroProgramacionMapper()));
			output_objParametros.put("PO_CURSOR_ALMACEN", new SqlOutParameter("PO_CURSOR_ALMACEN", OracleTypes.CURSOR, new ProgramacionAlmacenMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRegistroProgramacion] Ocurrio un error en la operacion del USP_SEL_EDITAR_PROGRAMACION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<ProgramacionBean> lista = (List<ProgramacionBean>) out.get("PO_CURSOR_DATOSGENERALES");
			if (!Utils.isEmpty(lista)) {
				programacion = lista.get(0);
			}
			
			programacion.setCodigoRespuesta(codigoRespuesta);
			programacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRegistroProgramacion] Fin ");
		return programacion;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionRequerimientoDao#listarRequerimiento(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public List<RequerimientoBean> listarRequerimiento(RequerimientoBean requerimientoBean) throws Exception {
		LOGGER.info("[listarRequerimiento] Inicio ");
		List<RequerimientoBean> lista = new ArrayList<RequerimientoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", requerimientoBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_DDI", requerimientoBean.getIdDdi(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REQUERIMIENTO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new ProgramacionRequerimientoMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarRequerimiento] Ocurrio un error en la operacion del USP_SEL_REQUERIMIENTO : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<RequerimientoBean>) out.get("PO_LR_RECORDSET");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarRequerimiento] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionRequerimientoDao#listarRacionOperativa(pe.com.sigbah.common.bean.RacionOperativaBean)
	 */
	@Override
	public List<RacionOperativaBean> listarRacionOperativa(RacionOperativaBean racionOperativaBean) throws Exception {
		LOGGER.info("[listarRacionOperativa] Inicio ");
		List<RacionOperativaBean> lista = new ArrayList<RacionOperativaBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_RAC_OPERATIVA", racionOperativaBean.getIdRacionOperativa(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_RACION_OPERATIVA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_RAC_OPERATIVA", new SqlParameter("PI_IDE_RAC_OPERATIVA", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new ProgramacionRacionOperativaMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarRacionOperativa] Ocurrio un error en la operacion del USP_SEL_RACION_OPERATIVA : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<RacionOperativaBean>) out.get("PO_LR_RECORDSET");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarRacionOperativa] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionRequerimientoDao#grabarEstadoProgramacion(pe.com.sigbah.common.bean.EstadoProgramacionBean)
	 */
	@Override
	public EstadoProgramacionBean grabarEstadoProgramacion(EstadoProgramacionBean estadoProgramacionBean) throws Exception {
		LOGGER.info("[grabarEstadoProgramacion] Inicio ");
		EstadoProgramacionBean registroEstadoProgramacion = new EstadoProgramacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_PROGRAMACION", estadoProgramacionBean.getIdProgramacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_ESTADO", estadoProgramacionBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("PI_OBSERVACION", estadoProgramacionBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", estadoProgramacionBean.getUsuarioRegistro(), Types.VARCHAR);				
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_ESTADO_PROGRAMACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_PROGRAMACION", new SqlParameter("PI_IDE_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("PI_IDE_ESTADO", new SqlParameter("PI_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("PI_OBSERVACION", new SqlParameter("PI_OBSERVACION", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));		
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarEstadoProgramacion] Ocurrio un error en la operacion del USP_INS_ESTADO_PROGRAMACION : "+mensajeRespuesta);
    			throw new Exception();
    		}

			registroEstadoProgramacion.setCodigoRespuesta(codigoRespuesta);
			registroEstadoProgramacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarEstadoProgramacion] Fin ");
		return registroEstadoProgramacion;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionRequerimientoDao#listarEstadoProgramacion(pe.com.sigbah.common.bean.EstadoProgramacionBean)
	 */
	@Override
	public List<EstadoProgramacionBean> listarEstadoProgramacion(EstadoProgramacionBean estadoProgramacionBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionRequerimientoDao#grabarProgramacion(pe.com.sigbah.common.bean.ProgramacionBean)
	 */
	@Override
	public ProgramacionBean grabarProgramacion(ProgramacionBean programacionBean) throws Exception {
		LOGGER.info("[grabarProgramacion] Inicio ");
		ProgramacionBean registroProgramacion = new ProgramacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_PROGRAMACION", programacionBean.getIdProgramacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ANIO", programacionBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_REQUERIMIENTO", programacionBean.getIdRequerimiento(), Types.NUMERIC);			
			input_objParametros.addValue("PI_FEC_PROGRAMACION", DateUtil.obtenerFechaHoraParseada(programacionBean.getFechaProgramacion()), Types.DATE);			
			input_objParametros.addValue("PI_IDE_DDI", programacionBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DDI", programacionBean.getCodigoDdi(), Types.VARCHAR);			
			input_objParametros.addValue("PI_IDE_REGION", programacionBean.getIdRegion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_RAC_OPERATIVA", programacionBean.getIdRacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_NOM_PROGRAMACION", programacionBean.getNombreProgramacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_DEE", programacionBean.getIdNroDee(), Types.NUMERIC);			
			input_objParametros.addValue("PI_TIP_ATENCION", programacionBean.getTipoAtencion(), Types.VARCHAR);
			input_objParametros.addValue("PI_OBSERVACION", programacionBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", programacionBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", programacionBean.getTipoControl(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_PROGRAMACIONDATGEN");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_PROGRAMACION", new SqlParameter("PI_IDE_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_IDE_REQUERIMIENTO", new SqlParameter("PI_IDE_REQUERIMIENTO", Types.NUMERIC));			
			output_objParametros.put("PI_FEC_PROGRAMACION", new SqlParameter("PI_FEC_PROGRAMACION", Types.DATE));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));			
			output_objParametros.put("PI_IDE_REGION", new SqlParameter("PI_IDE_REGION", Types.NUMERIC));
			output_objParametros.put("PI_IDE_RAC_OPERATIVA", new SqlParameter("PI_IDE_RAC_OPERATIVA", Types.NUMERIC));
			output_objParametros.put("PI_NOM_PROGRAMACION", new SqlParameter("PI_NOM_PROGRAMACION", Types.VARCHAR));			
			output_objParametros.put("PI_FK_IDE_DEE", new SqlParameter("PI_FK_IDE_DEE", Types.NUMERIC));
			output_objParametros.put("PI_TIP_ATENCION", new SqlParameter("PI_TIP_ATENCION", Types.NUMERIC));
			output_objParametros.put("PI_OBSERVACION", new SqlParameter("PI_OBSERVACION", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_ID_PROGRAMACION", new SqlOutParameter("PO_ID_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("PO_COD_PROGRAMACION", new SqlOutParameter("PO_COD_PROGRAMACION", Types.VARCHAR));
			output_objParametros.put("PO_COD_PROG_CONCATENADO", new SqlOutParameter("PO_COD_PROG_CONCATENADO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarProgramacion] Ocurrio un error en la operacion del USP_INS_UPD_PROGRAMACIONDATGEN : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			if (Utils.isNullInteger(programacionBean.getIdProgramacion())) {
				registroProgramacion.setIdProgramacion(((BigDecimal) out.get("PO_ID_PROGRAMACION")).intValue());
				registroProgramacion.setNroProgramacion((String) out.get("PO_COD_PROG_CONCATENADO"));
				registroProgramacion.setCodigoProgramacion((String) out.get("PO_COD_PROGRAMACION"));
				registroProgramacion.setIdEstado(Constantes.COD_GENERADO);
				registroProgramacion.setNombreEstado(Constantes.DES_GENERADO);
			}
			registroProgramacion.setCodigoRespuesta(codigoRespuesta);
			registroProgramacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarProgramacion] Fin ");
		return registroProgramacion;
	}
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionRequerimientoDao#listarProgramacionAlmacen(pe.com.sigbah.common.bean.ProgramacionAlmacenBean)
	 */
	@Override
	public List<ProgramacionAlmacenBean> listarProgramacionAlmacen(ProgramacionAlmacenBean programacionAlmacenBean) throws Exception {
		LOGGER.info("[listarProgramacionAlmacen] Inicio ");
		List<ProgramacionAlmacenBean> lista = new ArrayList<ProgramacionAlmacenBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_PROGRAMACION", programacionAlmacenBean.getIdProgramacion(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_ALMACEN_PROG");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_PROGRAMACION", new SqlParameter("PI_IDE_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new ProgramacionAlmacenMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProgramacionAlmacen] Ocurrio un error en la operacion del USP_SEL_LISTAR_ALMACEN_PROG : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<ProgramacionAlmacenBean>) out.get("PO_LR_RECORDSET");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProgramacionAlmacen] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionRequerimientoDao#grabarProgramacionAlmacen(pe.com.sigbah.common.bean.ProgramacionAlmacenBean)
	 */
	@Override
	public ProgramacionAlmacenBean grabarProgramacionAlmacen(ProgramacionAlmacenBean programacionAlmacenBean) throws Exception {
		LOGGER.info("[grabarProgramacionAlmacen] Inicio ");
		ProgramacionAlmacenBean registroProgramacionAlmacen = new ProgramacionAlmacenBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_FK_IDE_PROGRAMACION", programacionAlmacenBean.getIdProgramacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_ALMACEN", programacionAlmacenBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", programacionAlmacenBean.getUsuarioRegistro(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_PROGRAMACION_ALMACEN");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_FK_IDE_PROGRAMACION", new SqlParameter("PI_FK_IDE_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarProgramacionAlmacen] Ocurrio un error en la operacion del USP_INS_PROGRAMACION_ALMACEN : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProgramacionAlmacen.setCodigoRespuesta(codigoRespuesta);
			registroProgramacionAlmacen.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarProductoControlCalidad] Fin ");
		return registroProgramacionAlmacen;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionRequerimientoDao#eliminarProgramacionAlmacen(pe.com.sigbah.common.bean.ProgramacionAlmacenBean)
	 */
	@Override
	public ProgramacionAlmacenBean eliminarProgramacionAlmacen(ProgramacionAlmacenBean programacionAlmacenBean) throws Exception {
		LOGGER.info("[eliminarProgramacionAlmacen] Inicio ");
		ProgramacionAlmacenBean registroProductoControlCalidad = new ProgramacionAlmacenBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_PROG_ALMACEN", programacionAlmacenBean.getIdProgramacionAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", programacionAlmacenBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_PROGRAMACION_ALMACEN");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_PROG_ALMACEN", new SqlParameter("PI_IDE_PROG_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarProgramacionAlmacen] Ocurrio un error en la operacion del USP_DEL_PROGRAMACION_ALMACEN : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoControlCalidad.setCodigoRespuesta(codigoRespuesta);
			registroProductoControlCalidad.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarProgramacionAlmacen] Fin ");
		return registroProductoControlCalidad;
	}
	
}

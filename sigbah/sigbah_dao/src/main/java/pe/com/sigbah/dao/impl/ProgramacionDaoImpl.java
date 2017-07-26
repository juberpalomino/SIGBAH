package pe.com.sigbah.dao.impl;

import java.io.Serializable;
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
import pe.com.sigbah.common.bean.AlimentariaEmergenciaBean;
import pe.com.sigbah.common.bean.CabeceraEmergenciaBean;
import pe.com.sigbah.common.bean.EmergenciaBean;
import pe.com.sigbah.common.bean.ListaRespuestaEmergenciaBean;
import pe.com.sigbah.common.bean.LocalidadEmergenciaBean;
import pe.com.sigbah.common.bean.NoAlimentariaEmergenciaBean;
import pe.com.sigbah.common.bean.RequerimientoBean;
import pe.com.sigbah.common.bean.UbigeoIneiBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.ProgramacionDao;
import pe.com.sigbah.mapper.EmergenciaMapper;
import pe.com.sigbah.mapper.RegistroAlimentariaEmergenciaMapper;
import pe.com.sigbah.mapper.RegistroCabeceraEmergenciaMapper;
import pe.com.sigbah.mapper.RegistroControlCalidadMapper;
import pe.com.sigbah.mapper.RegistroLocalidadEmergenciaMapper;
import pe.com.sigbah.mapper.RegistroNoAlimentariaEmergenciaMapper;
import pe.com.sigbah.mapper.RequerimientoMapper;
import pe.com.sigbah.mapper.UbigeoIneiMapper;

/**
 * @className: ProgramacionDaoImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_PROGRAMACION.
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Repository
public class ProgramacionDaoImpl extends JdbcDaoSupport implements ProgramacionDao, Serializable {

	private static final long serialVersionUID = 1L;
	private transient final Log LOGGER = LogFactory.getLog(getClass());
	
	private SimpleJdbcCall objJdbcCall;
	
	/**
	 * @param dataSource
	 */
	@Autowired
	public ProgramacionDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	
	@Override
	public List<EmergenciaBean> listarEmergencia(EmergenciaBean emergenciaBean) throws Exception {
		LOGGER.info("[listarEmergencia] Inicio ");
		List<EmergenciaBean> lista = new ArrayList<EmergenciaBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", emergenciaBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", Utils.getParam( emergenciaBean.getCodMes()), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_REGION", emergenciaBean.getIdRegion(), Types.INTEGER);
			input_objParametros.addValue("PI_IDE_FENOMENO", emergenciaBean.getIdFenomeno(), Types.INTEGER);
						
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_EMERGENCIAS");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
			output_objParametros.put("PI_COD_REGION", new SqlParameter("PI_COD_REGION", Types.INTEGER));
			output_objParametros.put("PI_IDE_FENOMENO", new SqlParameter("PI_IDE_FENOMENO", Types.INTEGER));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new EmergenciaMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarEmergencia] Ocurrio un error en la operacion del USP_SEL_Listar_Emergencias : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<EmergenciaBean>) out.get("PO_LR_RECORDSET");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarEmergencia] Fin ");
		return lista;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#obtenerRegistroEmergencia(java.lang.Integer)
	 */
	@Override
	public ListaRespuestaEmergenciaBean obtenerRegistroEmergencia(Integer idEmergencia, String codAnio) throws Exception {
		LOGGER.info("[obtenerRegistroEmergencia] Inicio ");
		ListaRespuestaEmergenciaBean listaRetorno = new ListaRespuestaEmergenciaBean();
		
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();	
			input_objParametros.addValue("PI_COD_ANIO", codAnio, Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_EMERGENCIA", idEmergencia, Types.NUMERIC);
			
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_EMERGENCIAS");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_IDE_EMERGENCIA", new SqlParameter("PI_IDE_EMERGENCIA", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET_CABECERA", new SqlOutParameter("PO_LR_RECORDSET_CABECERA", OracleTypes.CURSOR, new RegistroCabeceraEmergenciaMapper()));
			output_objParametros.put("PO_LR_RECORDSET_LOCALIDAD", new SqlOutParameter("PO_LR_RECORDSET_LOCALIDAD", OracleTypes.CURSOR, new RegistroLocalidadEmergenciaMapper()));
			output_objParametros.put("PO_LR_RECORDSET_ALIMENTOS", new SqlOutParameter("PO_LR_RECORDSET_ALIMENTOS", OracleTypes.CURSOR, new RegistroAlimentariaEmergenciaMapper()));
			output_objParametros.put("PO_LR_RECORDSET_NO_ALIMENTOS", new SqlOutParameter("PO_LR_RECORDSET_NO_ALIMENTOS", OracleTypes.CURSOR, new RegistroNoAlimentariaEmergenciaMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRegistroEmergencia] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_EMERGENCIAS : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<CabeceraEmergenciaBean> listaCabecera = (List<CabeceraEmergenciaBean>) out.get("PO_LR_RECORDSET_CABECERA");
			List<LocalidadEmergenciaBean> listaLocalidad = (List<LocalidadEmergenciaBean>) out.get("PO_LR_RECORDSET_LOCALIDAD");
			List<AlimentariaEmergenciaBean> listaAlimentaria = (List<AlimentariaEmergenciaBean>) out.get("PO_LR_RECORDSET_ALIMENTOS");
			List<NoAlimentariaEmergenciaBean> listaNoAlimentaria = (List<NoAlimentariaEmergenciaBean>) out.get("PO_LR_RECORDSET_NO_ALIMENTOS");
			
			listaRetorno.setLstCabecera(listaCabecera);
			listaRetorno.setLstLocalidad(listaLocalidad);
			listaRetorno.setLstAlimentaria(listaAlimentaria);
			listaRetorno.setLstNoAlimentaria(listaNoAlimentaria);
			
			listaRetorno.setCodigoRespuesta(codigoRespuesta);
			listaRetorno.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRegistroEmergencia] Fin ");
		return listaRetorno;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#listarRequerimiento(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public List<RequerimientoBean> listarRequerimiento(RequerimientoBean requerimientoBean) throws Exception {
		LOGGER.info("[listarRequerimiento] Inicio ");
		List<RequerimientoBean> lista = new ArrayList<RequerimientoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", requerimientoBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", Utils.getParam(requerimientoBean.getCodMes()), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_FENOMENO", requerimientoBean.getIdFenomeno(), Types.VARCHAR);
			
		
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_REQUERIMIENTOS");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
			output_objParametros.put("PI_IDE_FENOMENO", new SqlParameter("PI_IDE_FENOMENO", Types.VARCHAR));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new RequerimientoMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarRequerimiento] Ocurrio un error en la operacion del USP_SEL_LISTAR_REQUERIMIENTOS : "+mensajeRespuesta);
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
	 * @see pe.com.sigbah.dao.ProgramacionDao#obtenerRequerimiento(java.lang.Integer, java.lang.String)
	 */
	@Override
	public RequerimientoBean obtenerRequerimiento( Integer idRequerimiento) throws Exception {
		LOGGER.info("[obtenerRequerimiento] Inicio ");
		RequerimientoBean controlCalidad = new RequerimientoBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_CONTROL_CALIDAD", idRequerimiento, Types.NUMERIC);
			
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
			
			List<RequerimientoBean> lista = (List<RequerimientoBean>) out.get("po_Lr_Recordset");
			if (!Utils.isEmpty(lista)) {
				controlCalidad = lista.get(0);
			}
			
			controlCalidad.setCodigoRespuesta(codigoRespuesta);
			controlCalidad.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRequerimiento] Fin ");
		return controlCalidad;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#obtenerCorrelativoRequerimiento(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public RequerimientoBean obtenerCorrelativoRequerimiento(RequerimientoBean requerimientoBean) throws Exception {
		LOGGER.info("[obtenerCorrelativoRequerimiento] Inicio ");
		RequerimientoBean detalleUsuarioBean = new RequerimientoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_COD_ANIO", requerimientoBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DDI", requerimientoBean.getCodDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_DDI", requerimientoBean.getIdDdi(), Types.NUMERIC);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_GENERA_REQUERIMIENTO");
			

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_DDI", new SqlParameter("PI_FK_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PO_COD_REQUERIMIENTO", new SqlOutParameter("PO_COD_REQUERIMIENTO", Types.VARCHAR));
			output_objParametros.put("PO_COD_REQ_CONCATENADO", new SqlOutParameter("PO_COD_REQ_CONCATENADO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerCorrelativoRequerimiento] Ocurrio un error en la operacion del USP_SEL_GENERA_REQUERIMIENTO : "+mensajeRespuesta);
    			throw new Exception();
    		}

			detalleUsuarioBean.setCodRequerimiento((String) out.get("PO_COD_REQUERIMIENTO"));
			detalleUsuarioBean.setNumRequerimiento((String) out.get("PO_COD_REQ_CONCATENADO"));
			detalleUsuarioBean.setCodigoRespuesta(codigoRespuesta);
			detalleUsuarioBean.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCorrelativoRequerimiento] Fin ");
		return detalleUsuarioBean;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#insertarRegistroRequerimiento(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public RequerimientoBean insertarRegistroRequerimiento(RequerimientoBean requerimientoBean) throws Exception {
		LOGGER.info("[insertarRequerimiento] Inicio ");
		RequerimientoBean registroRequerimiento = new RequerimientoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_REQUERIMIENTO", requerimientoBean.getIdRequerimiento(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ANIO", requerimientoBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", requerimientoBean.getCodMes(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_DDI", requerimientoBean.getFkIdeDdi(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_REGION", requerimientoBean.getFkIdeRegion(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_REQUERIMIENTO", requerimientoBean.getCodRequerimiento(), Types.VARCHAR);
			input_objParametros.addValue("PI_NOM_REQUERIMIENTO", requerimientoBean.getNomRequerimiento(), Types.VARCHAR);
			input_objParametros.addValue("PI_FEC_REQUERIMIENTO", DateUtil.obtenerFechaHoraParseada(requerimientoBean.getFechaRequerimiento()), Types.DATE);
			input_objParametros.addValue("PI_FLG_SINPAD", requerimientoBean.getFlgSinpad(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_FENOMENO", requerimientoBean.getFkIdeFenomeno(), Types.NUMERIC);
			input_objParametros.addValue("PI_OBSERVACION", requerimientoBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", requerimientoBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);
            
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_REQUERIMIENTO_CAB");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_REQUERIMIENTO", new SqlParameter("PI_IDE_REQUERIMIENTO", Types.NUMERIC));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_DDI", new SqlParameter("PI_FK_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_REGION", new SqlParameter("PI_FK_IDE_REGION", Types.NUMERIC));
			output_objParametros.put("PI_COD_REQUERIMIENTO", new SqlParameter("PI_COD_REQUERIMIENTO", Types.VARCHAR));
			output_objParametros.put("PI_NOM_REQUERIMIENTO", new SqlParameter("PI_NOM_REQUERIMIENTO", Types.VARCHAR));
			output_objParametros.put("PI_FEC_REQUERIMIENTO", new SqlParameter("PI_FEC_REQUERIMIENTO", Types.DATE));
			output_objParametros.put("PI_FLG_SINPAD", new SqlParameter("PI_FLG_SINPAD", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_FENOMENO", new SqlParameter("PI_FK_IDE_FENOMENO", Types.NUMERIC));
			output_objParametros.put("PI_OBSERVACION", new SqlParameter("PI_OBSERVACION", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarRequerimiento] Ocurrio un error en la operacion del USP_INS_REGISTRA_CONTROL_CALID : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			
			registroRequerimiento.setCodigoRespuesta(codigoRespuesta);
			registroRequerimiento.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarRequerimiento] Fin ");
		return registroRequerimiento;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#actualizarRegistroRequerimiento(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public RequerimientoBean actualizarRegistroRequerimiento(RequerimientoBean requerimientoBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#listarEmergenciasActivas(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public List<EmergenciaBean> listarEmergenciasActivas(RequerimientoBean requerimientoBean) throws Exception {
		LOGGER.info("[listarEmergenciasActivas] Inicio ");
		List<EmergenciaBean> lista = new ArrayList<EmergenciaBean>();
		try {				
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", requerimientoBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", Utils.getParam(requerimientoBean.getCodMes()), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DEPARTAMENTO", requerimientoBean.getCodDpto(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_PROVINCIA", Utils.getParam(requerimientoBean.getCodProvincia()), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_FENOMENO", Utils.getParam(requerimientoBean.getIdFenomeno()), Types.INTEGER);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_EMERG_ACTIVAS");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
			output_objParametros.put("PI_COD_DEPARTAMENTO", new SqlParameter("PI_COD_DEPARTAMENTO", Types.VARCHAR));
			output_objParametros.put("PI_COD_PROVINCIA", new SqlParameter("PI_COD_PROVINCIA", Types.VARCHAR));
			output_objParametros.put("PI_COD_FENOMENO", new SqlParameter("PI_COD_FENOMENO", Types.INTEGER));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new EmergenciaMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarEmergenciasActivas] Ocurrio un error en la operacion del USP_SEL_LISTAR_EMERG_ACTIVAS : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<EmergenciaBean>) out.get("PO_LR_RECORDSET");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarEmergenciasActivas] Fin ");
		return lista;
	}

	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#pasarDistritos(pe.com.sigbah.common.bean.EmergenciaBean)
	 */
	@Override
	public EmergenciaBean pasarDistritos(EmergenciaBean emergenciaBean) throws Exception {
		LOGGER.info("[pasarDistritos] Inicio ");
		EmergenciaBean emergenciaRequerimiento = new EmergenciaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_FK_IDE_REQUERIMIENTO", emergenciaBean.getFkIdRequerimiento(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_EMERGENCIA", emergenciaBean.getIdEmergencia(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DISTRITO", emergenciaBean.getCodDistrito(), Types.VARCHAR);
			input_objParametros.addValue("PI_NUM_FAM_AFECTADAS", emergenciaBean.getFamAfectado(), Types.NUMERIC);
			input_objParametros.addValue("PI_NUM_FAM_DAMNIFICADAS", emergenciaBean.getFamDamnificado(), Types.NUMERIC);
			input_objParametros.addValue("PI_NUM_PER_AFECTADAS", emergenciaBean.getPersoAfectado(), Types.NUMERIC);
			input_objParametros.addValue("PI_NUM_PER_DAMNIFICADAS", emergenciaBean.getPersoDamnificado(), Types.NUMERIC);
			input_objParametros.addValue("PI_NUM_FAM_AFECTADAS_REAL", emergenciaBean.getFamAfectadoReal(), Types.NUMERIC);
			input_objParametros.addValue("PI_NUM_FAM_DAMNIFICADAS_REAL", emergenciaBean.getFamDamnificadoReal(), Types.NUMERIC);
			input_objParametros.addValue("PI_NUM_PER_AFECTADAS_REAL", emergenciaBean.getPersoAfectadoReal(), Types.NUMERIC);
			input_objParametros.addValue("PI_NUM_PER_DAMNIFICADAS_REAL", emergenciaBean.getPersoDamnificadoReal(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", emergenciaBean.getUsuarioRegistro(), Types.VARCHAR);
			
			 
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_DISTRITOEMERGENCIA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_FK_IDE_REQUERIMIENTO", new SqlParameter("PI_FK_IDE_REQUERIMIENTO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_EMERGENCIA", new SqlParameter("PI_IDE_EMERGENCIA", Types.NUMERIC));
			output_objParametros.put("PI_COD_DISTRITO", new SqlParameter("PI_COD_DISTRITO", Types.VARCHAR));
			output_objParametros.put("PI_NUM_FAM_AFECTADAS", new SqlParameter("PI_NUM_FAM_AFECTADAS", Types.NUMERIC));
			output_objParametros.put("PI_NUM_FAM_DAMNIFICADAS", new SqlParameter("PI_NUM_FAM_DAMNIFICADAS", Types.NUMERIC));
			output_objParametros.put("PI_NUM_PER_AFECTADAS", new SqlParameter("PI_NUM_PER_AFECTADAS", Types.NUMERIC));
			output_objParametros.put("PI_NUM_PER_DAMNIFICADAS", new SqlParameter("PI_NUM_PER_DAMNIFICADAS", Types.NUMERIC));
			output_objParametros.put("PI_NUM_FAM_AFECTADAS_REAL", new SqlParameter("PI_NUM_FAM_AFECTADAS_REAL", Types.NUMERIC));
			output_objParametros.put("PI_NUM_FAM_DAMNIFICADAS_REAL", new SqlParameter("PI_NUM_FAM_DAMNIFICADAS_REAL", Types.NUMERIC));
			output_objParametros.put("PI_NUM_PER_AFECTADAS_REAL", new SqlParameter("PI_NUM_PER_AFECTADAS_REAL", Types.NUMERIC));
			output_objParametros.put("PI_NUM_PER_DAMNIFICADAS_REAL", new SqlParameter("PI_NUM_PER_DAMNIFICADAS_REAL", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[pasarDistritos] Ocurrio un error en la operacion del USP_INS_UPD_DISTRITOEMERGENCIA : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			
			emergenciaRequerimiento.setCodigoRespuesta(codigoRespuesta);
			emergenciaRequerimiento.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[pasarDistritos] Fin ");
		return emergenciaRequerimiento;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#listarUbigeoInei(pe.com.sigbah.common.bean.UbigeoIneiBean)
	 */
	@Override
	public List<UbigeoIneiBean> listarUbigeoInei(UbigeoIneiBean ubigeoBean) throws Exception {
		LOGGER.info("[listarUbigeoInei] Inicio ");
		List<UbigeoIneiBean> lista = new ArrayList<UbigeoIneiBean>();
		try {				
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", ubigeoBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DEPARTAMENTO", ubigeoBean.getCoddpto(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_PROVINCIA", Utils.getParam(ubigeoBean.getCodprov()), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_UBIGEO_INEI");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_DEPARTAMENTO", new SqlParameter("PI_COD_DEPARTAMENTO", Types.VARCHAR));
			output_objParametros.put("PI_COD_PROVINCIA", new SqlParameter("PI_COD_PROVINCIA", Types.VARCHAR));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new UbigeoIneiMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarUbigeoInei] Ocurrio un error en la operacion del USP_SEL_LISTAR_UBIGEO_INEI : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<UbigeoIneiBean>) out.get("PO_LR_RECORDSET");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarUbigeoInei] Fin ");
		return lista;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#pasarDistritosUbigeo(pe.com.sigbah.common.bean.UbigeoIneiBean)
	 */
	@Override
	public EmergenciaBean pasarDistritosUbigeo(EmergenciaBean emergenciaBean) throws Exception {
		LOGGER.info("[pasarDistritosUbigeo] Inicio ");
		EmergenciaBean emergenciaRequerimiento = new EmergenciaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_FK_IDE_REQUERIMIENTO", emergenciaBean.getFkIdRequerimiento(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DISTRITO", emergenciaBean.getCodDistrito(), Types.VARCHAR);
			input_objParametros.addValue("PI_NUM_POBLACION_INEI", emergenciaBean.getFamAfectado(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", emergenciaBean.getUsuarioRegistro(), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_DISTRITOEMERG_INEI");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_FK_IDE_REQUERIMIENTO", new SqlParameter("PI_FK_IDE_REQUERIMIENTO", Types.NUMERIC));
			output_objParametros.put("PI_COD_DISTRITO", new SqlParameter("PI_COD_DISTRITO", Types.VARCHAR));
			output_objParametros.put("PI_NUM_POBLACION_INEI", new SqlParameter("PI_NUM_POBLACION_INEI", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[pasarDistritosUbigeo] Ocurrio un error en la operacion del USP_INS_DISTRITOEMERG_INEI : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			
			emergenciaRequerimiento.setCodigoRespuesta(codigoRespuesta);
			emergenciaRequerimiento.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[pasarDistritosUbigeo] Fin ");
		return emergenciaRequerimiento;
	}


}

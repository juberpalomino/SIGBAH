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
import pe.com.sigbah.common.bean.AlimentariaEmergenciaBean;
import pe.com.sigbah.common.bean.CabeceraEmergenciaBean;
import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DeeBean;
import pe.com.sigbah.common.bean.EmergenciaBean;
import pe.com.sigbah.common.bean.ListaRespuestaEmergenciaBean;
import pe.com.sigbah.common.bean.ListaRespuestaRequerimientoBean;
import pe.com.sigbah.common.bean.LocalidadEmergenciaBean;
import pe.com.sigbah.common.bean.NoAlimentariaEmergenciaBean;
import pe.com.sigbah.common.bean.PedidoCompraBean;
import pe.com.sigbah.common.bean.ProductoControlCalidadBean;
import pe.com.sigbah.common.bean.ProductoRacionBean;
import pe.com.sigbah.common.bean.RacionBean;
import pe.com.sigbah.common.bean.RequerimientoBean;
import pe.com.sigbah.common.bean.UbigeoIneiBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.ProgramacionDao;
import pe.com.sigbah.mapper.EmergenciaMapper;
import pe.com.sigbah.mapper.ProductoRacionMapper;
import pe.com.sigbah.mapper.RacionMapper;
import pe.com.sigbah.mapper.RegistroAlimentariaEmergenciaMapper;
import pe.com.sigbah.mapper.RegistroCabeceraEmergenciaMapper;
import pe.com.sigbah.mapper.RegistroControlCalidadMapper;
import pe.com.sigbah.mapper.RegistroLocalidadEmergenciaMapper;
import pe.com.sigbah.mapper.RegistroNoAlimentariaEmergenciaMapper;
import pe.com.sigbah.mapper.RequerimientoDetalleMapper;
import pe.com.sigbah.mapper.RequerimientoEditMapper;
import pe.com.sigbah.mapper.RequerimientoMapper;
import pe.com.sigbah.mapper.UbigeoIneiMapper;


/**
 * @className: ProgramacionDaoImpl.java
 * @description: 
 * @date: 27 jul. 2017
 * @author: whr.
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
	public ListaRespuestaRequerimientoBean obtenerRequerimiento( String codAnio, String codDdi,Integer idRequerimiento) throws Exception {
		LOGGER.info("[obtenerRequerimiento] Inicio ");
		ListaRespuestaRequerimientoBean listaRetorno = new ListaRespuestaRequerimientoBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", codAnio, Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DDI", codDdi, Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_REQUERIMIENTO", idRequerimiento, Types.NUMERIC);
			
		    
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_EDITAR_REQUERIMIENTO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_IDE_REQUERIMIENTO", new SqlParameter("PI_IDE_REQUERIMIENTO", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET1", new SqlOutParameter("PO_LR_RECORDSET1", OracleTypes.CURSOR, new RequerimientoEditMapper()));
			output_objParametros.put("PO_LR_RECORDSET2", new SqlOutParameter("PO_LR_RECORDSET2", OracleTypes.CURSOR, new RequerimientoDetalleMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRequerimiento] Ocurrio un error en la operacion del USP_SEL_EDITAR_REQUERIMIENTO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<RequerimientoBean> listaCabecera = (List<RequerimientoBean>) out.get("PO_LR_RECORDSET1");
			List<EmergenciaBean> listaDetalle = (List<EmergenciaBean>) out.get("PO_LR_RECORDSET2");
			
			listaRetorno.setLstCabecera(listaCabecera);
			listaRetorno.setLstDetalle(listaDetalle);
			
			listaRetorno.setCodigoRespuesta(codigoRespuesta);
			listaRetorno.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));					
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRequerimiento] Fin ");
		return listaRetorno; 
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
			input_objParametros.addValue("PI_NUM_POBLACION_INEI", emergenciaBean.getPoblacionINEI(), Types.NUMERIC);
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


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#listarRaciones(pe.com.sigbah.common.bean.RacionBean)
	 */
	@Override
	public List<RacionBean> listarRaciones(RacionBean racionBean) throws Exception {
		LOGGER.info("[listarRaciones] Inicio ");
		List<RacionBean> lista = new ArrayList<RacionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", racionBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_DDI", racionBean.getIdDdi(), Types.INTEGER);
			input_objParametros.addValue("PI_TIPO_RACION", Utils.getParam(racionBean.getTipoRacion()), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_RACIONES");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.INTEGER));
			output_objParametros.put("PI_TIPO_RACION", new SqlParameter("PI_TIPO_RACION", Types.VARCHAR));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new RacionMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarRaciones] Ocurrio un error en la operacion del USP_SEL_LISTAR_RACIONES : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<RacionBean>) out.get("PO_LR_RECORDSET");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarRaciones] Fin ");
		return lista;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#copiarRacion(pe.com.sigbah.common.bean.RacionBean)
	 */
	@Override
	public RacionBean copiarRacion(RacionBean racionBean) throws Exception {
		LOGGER.info("[copiarRacion] Inicio ");
		RacionBean racion = new RacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_COD_ANIO", racionBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_ID_DDI", racionBean.getIdDdi(), Types.INTEGER);
			input_objParametros.addValue("PI_IDE_RAC_OPERATIVA", racionBean.getIdRacionOpe(), Types.INTEGER);
			input_objParametros.addValue("PI_USERNAME", Utils.getParam(racionBean.getUsuarioRegistro()), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_COPIAR_RACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_ID_DDI", new SqlParameter("PI_ID_DDI", Types.NUMERIC));
			output_objParametros.put("PI_IDE_RAC_OPERATIVA", new SqlParameter("PI_IDE_RAC_OPERATIVA", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[copiarRacion] Ocurrio un error en la operacion del USP_SEL_COPIAR_RACION : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			
			racion.setCodigoRespuesta(codigoRespuesta);
			racion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[copiarRacion] Fin ");
		return racion;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#obtenerCorrelativoRacion(pe.com.sigbah.common.bean.RacionBean)
	 */
	@Override
	public RacionBean obtenerCorrelativoRacion(RacionBean parametros) throws Exception {
		LOGGER.info("[obtenerCorrelativoRacion] Inicio ");
		RacionBean racionBean = new RacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_ANIO", parametros.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_DDI", parametros.getIdDdi(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_GENERA_CORR_RACION");
			

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ANIO", new SqlParameter("PI_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_DDI", new SqlParameter("PI_DDI", Types.NUMERIC));
			output_objParametros.put("PO_COD_RACION", new SqlOutParameter("PO_COD_RACION", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerCorrelativoRacion] Ocurrio un error en la operacion del USP_SEL_GENERA_CORR_RACION : "+mensajeRespuesta);
    			throw new Exception();
    		}

			racionBean.setCodRacion((String) out.get("PO_COD_RACION"));
			racionBean.setCodigoRespuesta(codigoRespuesta);
			racionBean.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCorrelativoRacion] Fin ");
		return racionBean;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#insertarRegistroRacion(pe.com.sigbah.common.bean.RacionBean)
	 */
	@Override
	public RacionBean insertarRegistroRacion(RacionBean racionBean) throws Exception {
		LOGGER.info("[insertarRegistroRacion] Inicio ");
		RacionBean registroRacion = new RacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_RAC_OPERATIVA", racionBean.getIdRacionOpe(), Types.NUMERIC);
			input_objParametros.addValue("PI_NOMBRE_RACION", racionBean.getNombreRacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_RACION", racionBean.getCodRacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_DDI", racionBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("PI_DIAS_ATENCION", racionBean.getDiasAtencion(), Types.NUMERIC);
			input_objParametros.addValue("PI_FEC_RACION", DateUtil.obtenerFechaHoraParseada(racionBean.getFechaRacion()), Types.DATE);
			input_objParametros.addValue("PI_TIP_RACION", racionBean.getTipoRacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_ANIO", racionBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", racionBean.getUsuarioRegistro(), Types.VARCHAR);
			
            objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_RACIONOPERATIVACAB");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_RAC_OPERATIVA", new SqlParameter("PI_IDE_RAC_OPERATIVA", Types.NUMERIC));
			output_objParametros.put("PI_NOMBRE_RACION", new SqlParameter("PI_NOMBRE_RACION", Types.VARCHAR));
			output_objParametros.put("PI_COD_RACION", new SqlParameter("PI_COD_RACION", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PI_DIAS_ATENCION", new SqlParameter("PI_DIAS_ATENCION", Types.NUMERIC));
			output_objParametros.put("PI_FEC_RACION", new SqlParameter("PI_FEC_RACION", Types.DATE));
			output_objParametros.put("PI_TIP_RACION", new SqlParameter("PI_TIP_RACION", Types.VARCHAR));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_IDE_RAC_OPERATIVA", new SqlOutParameter("PO_IDE_RAC_OPERATIVA", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
				
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarRegistroRacion] Ocurrio un error en la operacion del USP_INS_UPD_RACIONOPERATIVACAB : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroRacion.setIdRacionOpe(((BigDecimal)  out.get("PO_IDE_RAC_OPERATIVA")).intValue());
			registroRacion.setCodigoRespuesta(codigoRespuesta);
			registroRacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarRegistroRacion] Fin ");
		return registroRacion;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#actualizarRegistroRacion(pe.com.sigbah.common.bean.RacionBean)
	 */
	@Override
	public RacionBean actualizarRegistroRacion(RacionBean racionBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#insertarRegistroProducto(pe.com.sigbah.common.bean.ProductoRacionBean)
	 */
	@Override
	public ProductoRacionBean insertarRegistroProducto(ProductoRacionBean productoBean) throws Exception {
		LOGGER.info("[insertarRegistroProducto] Inicio ");
		ProductoRacionBean registroProducto = new ProductoRacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DET_RAC_OPERATIVA", productoBean.getIdDetaRacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_RAC_OPERATIVA", productoBean.getIdRacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_PRODUCTO", productoBean.getFkIdProducto(), Types.NUMERIC);
			input_objParametros.addValue("PI_PESO_UND_PRES", productoBean.getPesoUnitarioPres(), Types.NUMERIC);
			input_objParametros.addValue("PI_CANT_RACION_KGS", productoBean.getCantRacionKg(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ANIO", productoBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", productoBean.getUsuarioRegistro(), Types.VARCHAR);
			
            
            objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_RACIONOPERATIVADET");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DET_RAC_OPERATIVA", new SqlParameter("PI_IDE_DET_RAC_OPERATIVA", Types.NUMERIC));
			output_objParametros.put("PI_IDE_RAC_OPERATIVA", new SqlParameter("PI_IDE_RAC_OPERATIVA", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PRODUCTO", new SqlParameter("PI_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("PI_PESO_UND_PRES", new SqlParameter("PI_PESO_UND_PRES", Types.NUMERIC));
			output_objParametros.put("PI_CANT_RACION_KGS", new SqlParameter("PI_CANT_RACION_KGS", Types.NUMERIC));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarRegistroProducto] Ocurrio un error en la operacion del USP_INS_UPD_RACIONOPERATIVADET : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			
			registroProducto.setCodigoRespuesta(codigoRespuesta);
			registroProducto.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarRegistroProducto] Fin ");
		return registroProducto;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#actualizarRegistroProducto(pe.com.sigbah.common.bean.ProductoRacionBean)
	 */
	@Override
	public ProductoRacionBean actualizarRegistroProducto(ProductoRacionBean productoBean) throws Exception {
		LOGGER.info("[insertarRegistroProducto] Inicio ");
		ProductoRacionBean registroProducto = new ProductoRacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DET_RAC_OPERATIVA", productoBean.getIdDetaRacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_RAC_OPERATIVA", productoBean.getIdRacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_PRODUCTO", productoBean.getFkIdProducto(), Types.NUMERIC);
			input_objParametros.addValue("PI_PESO_UND_PRES", productoBean.getPesoUnitarioPres(), Types.NUMERIC);
			input_objParametros.addValue("PI_CANT_RACION_KGS", productoBean.getCantRacionKg(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ANIO", productoBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "U", Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", productoBean.getUsuarioRegistro(), Types.VARCHAR);
			
            
            objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_RACIONOPERATIVADET");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DET_RAC_OPERATIVA", new SqlParameter("PI_IDE_DET_RAC_OPERATIVA", Types.NUMERIC));
			output_objParametros.put("PI_IDE_RAC_OPERATIVA", new SqlParameter("PI_IDE_RAC_OPERATIVA", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PRODUCTO", new SqlParameter("PI_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("PI_PESO_UND_PRES", new SqlParameter("PI_PESO_UND_PRES", Types.NUMERIC));
			output_objParametros.put("PI_CANT_RACION_KGS", new SqlParameter("PI_CANT_RACION_KGS", Types.NUMERIC));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarRegistroProducto] Ocurrio un error en la operacion del USP_INS_UPD_RACIONOPERATIVADET : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			
			registroProducto.setCodigoRespuesta(codigoRespuesta);
			registroProducto.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarRegistroProducto] Fin ");
		return registroProducto;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#listarPedidosCompra(pe.com.sigbah.common.bean.PedidoCompraBean)
	 */
	@Override
	public List<PedidoCompraBean> listarPedidosCompra(PedidoCompraBean pedidoBean) throws Exception {
		LOGGER.info("[listarPedidosCompra] Inicio ");
		List<PedidoCompraBean> lista = new ArrayList<PedidoCompraBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", pedidoBean.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", pedidoBean.getFkIdeDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_ID_ESTADO", pedidoBean.getFkIdeDdi(), Types.INTEGER);
			
            
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_PEDIDO_COMPRA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
			output_objParametros.put("PI_ID_ESTADO", new SqlParameter("PI_ID_ESTADO", Types.INTEGER));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new RacionMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarPedidosCompra] Ocurrio un error en la operacion del USP_SEL_LISTAR_PEDIDO_COMPRA : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<PedidoCompraBean>) out.get("PO_LR_RECORDSET");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarPedidosCompra] Fin ");
		return lista;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#listarDee(pe.com.sigbah.common.bean.DeeBean)
	 */
	@Override
	public List<DeeBean> listarDee(DeeBean deeBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#listarProductos(pe.com.sigbah.common.bean.ProductoRacionBean)
	 */
	@Override
	public List<ProductoRacionBean> listarProductos(ProductoRacionBean racionBean) throws Exception {
		LOGGER.info("[listarProductos] Inicio ");
		List<ProductoRacionBean> lista = new ArrayList<ProductoRacionBean>();
		try { 
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_RAC_OPERATIVA", racionBean.getIdRacion(), Types.INTEGER);
			  
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_PRODUCTO_RACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_RAC_OPERATIVA", new SqlParameter("PI_IDE_RAC_OPERATIVA", Types.INTEGER));
			output_objParametros.put("PO_LR_RECORDSET2", new SqlOutParameter("PO_LR_RECORDSET2", OracleTypes.CURSOR, new ProductoRacionMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductos] Ocurrio un error en la operacion del USP_SEL_LISTAR_PRODUCTO_RACION : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<ProductoRacionBean>) out.get("PO_LR_RECORDSET2");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProductos] Fin ");
		return lista;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#eliminarProductoRacion(pe.com.sigbah.common.bean.ProductoRacionBean)
	 */
	@Override
	public ProductoRacionBean eliminarProductoRacion(ProductoRacionBean productoRacionBean) throws Exception {
		LOGGER.info("[eliminarProductoRacion] Inicio ");
		ProductoRacionBean registroProductoRacion = new ProductoRacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_ide_det_rac_operativa", productoRacionBean.getIdDetaRacion(), Types.NUMERIC);

            
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_PRODUCTO_RACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ide_det_rac_operativa", new SqlParameter("PI_ide_det_rac_operativa", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarProductoRacion] Ocurrio un error en la operacion del USP_DEL_PRODUCTO_RACION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoRacion.setCodigoRespuesta(codigoRespuesta);
			registroProductoRacion.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarProductoRacion] Fin ");
		return registroProductoRacion;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#obtenerRegistroRacion(java.lang.Integer)
	 */
	@Override
	public RacionBean obtenerRegistroRacion(Integer idRacion) throws Exception {
		LOGGER.info("[obtenerRegistroRacion] Inicio ");
		RacionBean racion = new RacionBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_RAC_OPERATIVA", idRacion, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_EDITAR_RACION");
			

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_RAC_OPERATIVA", new SqlParameter("PI_IDE_RAC_OPERATIVA", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET1", new SqlOutParameter("PO_LR_RECORDSET1", OracleTypes.CURSOR, new RegistroControlCalidadMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRegistroRacion] Ocurrio un error en la operacion del USP_SEL_EDITAR_RACION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<RacionBean> lista = (List<RacionBean>) out.get("PO_LR_RECORDSET1");
			if (!Utils.isEmpty(lista)) {
				racion = lista.get(0);
			}
			
			racion.setCodigoRespuesta(codigoRespuesta);
			racion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRegistroRacion] Fin ");
		return racion;
	}


	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.ProgramacionDao#obtenerCorrelativoPedidoCompra(pe.com.sigbah.common.bean.PedidoCompraBean)
	 */
	@Override
	public PedidoCompraBean obtenerCorrelativoPedidoCompra(PedidoCompraBean parametros) throws Exception {
		LOGGER.info("[obtenerCorrelativoPedidoCompra] Inicio ");
		PedidoCompraBean pedidoBean = new PedidoCompraBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_COD_ANIO", parametros.getCodAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DDI", parametros.getCodDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_DDI", parametros.getFkIdeDdi(), Types.NUMERIC);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_PROGRAMACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_GENERA_PEDIDO_COMPRA");
			

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_DDI", new SqlParameter("PI_FK_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PO_COD_PEDIDO_COMPRA", new SqlOutParameter("PO_COD_PEDIDO_COMPRA", Types.VARCHAR));
			output_objParametros.put("PO_COD_PEDIDO_CONCATENADO", new SqlOutParameter("PO_COD_PEDIDO_CONCATENADO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerCorrelativoPedidoCompra] Ocurrio un error en la operacion del USP_SEL_GENERA_PEDIDO_COMPRA : "+mensajeRespuesta);
    			throw new Exception();
    		}

			pedidoBean.setCodPedido((String) out.get("PO_COD_PEDIDO_COMPRA"));
			pedidoBean.setCodPedidoConcate((String) out.get("PO_COD_PEDIDO_CONCATENADO"));
			pedidoBean.setCodigoRespuesta(codigoRespuesta);
			pedidoBean.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCorrelativoPedidoCompra] Fin ");
		return pedidoBean;
		
	}


}

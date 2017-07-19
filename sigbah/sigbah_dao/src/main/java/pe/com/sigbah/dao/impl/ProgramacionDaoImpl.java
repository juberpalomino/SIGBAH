package pe.com.sigbah.dao.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
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
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.ProgramacionDao;
import pe.com.sigbah.mapper.EmergenciaMapper;
import pe.com.sigbah.mapper.RegistroAlimentariaEmergenciaMapper;
import pe.com.sigbah.mapper.RegistroCabeceraEmergenciaMapper;
import pe.com.sigbah.mapper.RegistroLocalidadEmergenciaMapper;
import pe.com.sigbah.mapper.RegistroNoAlimentariaEmergenciaMapper;

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
			input_objParametros.addValue("PI_COD_MES", emergenciaBean.getCodMes(), Types.VARCHAR);
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


}

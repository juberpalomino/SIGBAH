package pe.com.sigbah.dao.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DonacionesBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.DonacionDao;
import pe.com.sigbah.mapper.DonacionesMapper;

/**
 * @className: DonacionDaoImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_DONACION.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class DonacionDaoImpl extends JdbcDaoSupport implements DonacionDao, Serializable {

	private static final long serialVersionUID = 1L;
	private transient final Log LOGGER = LogFactory.getLog(getClass());
	
	private SimpleJdbcCall objJdbcCall;
	
	/**
	 * @param dataSource
	 */
	@Autowired
	public DonacionDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
 	public List<DonacionesBean> listarDonaciones(DonacionesBean donacionesBean) throws Exception {
		LOGGER.info("[listarDonaciones] Inicio ");
		List<DonacionesBean> lista = new ArrayList<DonacionesBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_ANIO", donacionesBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_DDI", donacionesBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_ESTADO", Utils.getParam(donacionesBean.getCodigoEstado()), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_PROPUESTASDONACIONE");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ANIO", new SqlParameter("PI_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.VARCHAR));
			output_objParametros.put("PI_ESTADO", new SqlParameter("PI_ESTADO", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new DonacionesMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				throw new Exception();
			} else {
				lista = (List<DonacionesBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarDonaciones] Fin ");
		return lista;
	}
	
	@Override
	public DonacionesBean insertarDonaciones(DonacionesBean donacionesBean) throws Exception {
		LOGGER.info("[insertarDonaciones] Inicio ");
		DonacionesBean registroDonacion= new DonacionesBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			
			input_objParametros.addValue("pi_IDE_DDI", donacionesBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("pi_IDE_DONACION", donacionesBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("pi_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(donacionesBean.getFechaEmision()), Types.DATE);
			input_objParametros.addValue("pi_FK_IDE_PAIS_DONANTE", donacionesBean.getIdPaisDonante(), Types.NUMERIC);
			input_objParametros.addValue("pi_TIP_DONANTE", donacionesBean.getTipoDonante(), Types.VARCHAR);
			input_objParametros.addValue("pi_TIP_ORIGEN_DONANTE", donacionesBean.getTipoOrigenDonante(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_OFICINA", donacionesBean.getIdOficina(), Types.NUMERIC);
			input_objParametros.addValue("pi_fk_ide_personal", donacionesBean.getIdPersonal(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_DONANTE", donacionesBean.getIdDonante(), Types.NUMERIC);
			input_objParametros.addValue("pi_FINALIDAD", donacionesBean.getFinalidad(), Types.VARCHAR);		
			input_objParametros.addValue("pi_OBSERVACION", donacionesBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ESTADO", donacionesBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("pi_BLOQUE_TEXTO1", donacionesBean.getTextoa(), Types.VARCHAR);
			input_objParametros.addValue("pi_BLOQUE_TEXTO2", donacionesBean.getTextob(), Types.VARCHAR);
			input_objParametros.addValue("pi_USERNAME", donacionesBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("pi_cod_ddi", donacionesBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_DONACION", donacionesBean.getCodigoDonacion(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_ANIO", donacionesBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_DEE", donacionesBean.getIdDee(), Types.NUMERIC);
			input_objParametros.addValue("pi_TIPO_DONACION", donacionesBean.getTipoDonacion(), Types.VARCHAR);
	
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_INSCRIPCIONDONACIO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			
			output_objParametros.put("pi_IDE_DDI", new SqlParameter("pi_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("pi_IDE_DONACION", new SqlParameter("pi_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("pi_FEC_EMISION", new SqlParameter("pi_FEC_EMISION", Types.DATE));
			output_objParametros.put("pi_FK_IDE_PAIS_DONANTE", new SqlParameter("pi_FK_IDE_PAIS_DONANTE", Types.NUMERIC));
			output_objParametros.put("pi_TIP_DONANTE", new SqlParameter("pi_TIP_DONANTE", Types.VARCHAR));
			output_objParametros.put("pi_TIP_ORIGEN_DONANTE", new SqlParameter("pi_TIP_ORIGEN_DONANTE", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_OFICINA", new SqlParameter("pi_FK_IDE_OFICINA", Types.NUMERIC));
			output_objParametros.put("pi_fk_ide_personal", new SqlParameter("pi_fk_ide_personal", Types.DATE));
			output_objParametros.put("pi_FK_IDE_DONANTE", new SqlParameter("pi_FK_IDE_DONANTE", Types.NUMERIC));
			output_objParametros.put("pi_FINALIDAD", new SqlParameter("pi_FINALIDAD", Types.NUMERIC));
			output_objParametros.put("pi_OBSERVACION", new SqlParameter("pi_OBSERVACION", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_ESTADO", new SqlParameter("pi_FK_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("pi_BLOQUE_TEXTO1", new SqlParameter("pi_BLOQUE_TEXTO1", Types.NUMERIC)); 
			output_objParametros.put("pi_BLOQUE_TEXTO2", new SqlParameter("pi_BLOQUE_TEXTO2", Types.VARCHAR));			
			output_objParametros.put("pi_USERNAME", new SqlOutParameter("pi_USERNAME", Types.NUMERIC));
			output_objParametros.put("pi_cod_ddi", new SqlOutParameter("pi_cod_ddi", Types.VARCHAR));
			output_objParametros.put("pi_COD_DONACION", new SqlOutParameter("pi_COD_DONACION", Types.VARCHAR));
			output_objParametros.put("pi_COD_ANIO", new SqlOutParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_IDE_DEE", new SqlOutParameter("pi_IDE_DEE", Types.VARCHAR));
			output_objParametros.put("pi_TIPO_DONACION", new SqlOutParameter("pi_TIPO_DONACION", Types.VARCHAR)); 
			output_objParametros.put("pi_control", new SqlOutParameter("pi_control", Types.VARCHAR));
			output_objParametros.put("po_IDE_DONACION", new SqlOutParameter("po_IDE_DONACION", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[insertarDonaciones] Ocurrio un error en la operacion del USP_INS_REGISTRA_CONTROL_CALID");
    			throw new Exception();
    		}
		
			registroDonacion.setIdDonacion((Integer) out.get("po_IDE_DONACION"));
			registroDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDonacion.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarDonaciones] Fin ");
		return registroDonacion;
	}
	
	@Override
	public DonacionesBean actualizarDonaciones(DonacionesBean controlCalidadBean) throws Exception {
		LOGGER.info("[actualizarDonaciones] Inicio ");
		DonacionesBean registroControlCalidad = new DonacionesBean();
//		try {			
//			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
//			input_objParametros.addValue("pi_IDE_CONTROL_CALIDAD", controlCalidadBean.getIdControlCalidad(), Types.NUMERIC);
//			input_objParametros.addValue("pi_COD_ANIO", controlCalidadBean.getCodigoAnio(), Types.VARCHAR);
//			input_objParametros.addValue("pi_COD_MES", controlCalidadBean.getCodigoMes(), Types.VARCHAR);
//			input_objParametros.addValue("pi_FK_IDE_ALMACEN", controlCalidadBean.getIdAlmacen(), Types.NUMERIC);
//			input_objParametros.addValue("pi_FLG_TIPO_PRODUCTO", controlCalidadBean.getFlagTipoBien(), Types.VARCHAR);
//			input_objParametros.addValue("pi_COD_ALMACEN", controlCalidadBean.getCodigoAlmacen(), Types.VARCHAR);
//			input_objParametros.addValue("pi_COD_DDI", controlCalidadBean.getCodigoDdi(), Types.VARCHAR);
//			input_objParametros.addValue("pi_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(controlCalidadBean.getFechaEmision()), Types.DATE);
//			input_objParametros.addValue("pi_FK_IDE_TIP_CONTROL", controlCalidadBean.getIdTipoControl(), Types.NUMERIC);
//			input_objParametros.addValue("pi_FK_IDE_ENCARGADO", controlCalidadBean.getIdEncargado(), Types.NUMERIC);
//			input_objParametros.addValue("pi_FK_IDE_INSPECTOR", controlCalidadBean.getIdInspector(), Types.NUMERIC);
//			input_objParametros.addValue("pi_FK_IDE_PROVEEDOR", controlCalidadBean.getIdProveedor(), Types.NUMERIC);
//			input_objParametros.addValue("pi_FK_IDE_EMP_TRANS", controlCalidadBean.getIdEmpresaTransporte(), Types.NUMERIC);
//			input_objParametros.addValue("pi_FK_IDE_CHOFER", controlCalidadBean.getIdChofer(), Types.NUMERIC);			
//			input_objParametros.addValue("pi_CONCLUSIONES", controlCalidadBean.getConclusiones(), Types.VARCHAR);
//			input_objParametros.addValue("pi_RECOMENDACIONES", controlCalidadBean.getRecomendaciones(), Types.VARCHAR);
//			input_objParametros.addValue("pi_FK_IDE_ALMACEN_OD", controlCalidadBean.getIdAlmacenOrigen(), Types.NUMERIC);
//			input_objParametros.addValue("pi_NRO_PLACA", controlCalidadBean.getNroPlaca(), Types.VARCHAR);
//			input_objParametros.addValue("pi_NRO_ORDEN_COMPRA", controlCalidadBean.getNroOrdenCompra(), Types.VARCHAR);
//			input_objParametros.addValue("pi_FK_IDE_ESTADO", controlCalidadBean.getIdEstado(), Types.NUMERIC);
//			input_objParametros.addValue("pi_FLG_TIPO_BIEN", controlCalidadBean.getFlagTipoBien(), Types.VARCHAR);
//			input_objParametros.addValue("pi_USU_MODIFICA", controlCalidadBean.getUsuarioRegistro(), Types.VARCHAR);			
//
//			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
//			objJdbcCall.withoutProcedureColumnMetaDataAccess();
//			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
//			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
//			objJdbcCall.withProcedureName("USP_UPD_REGISTRA_CONTROL_CALID");
//
//			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
//			output_objParametros.put("pi_IDE_CONTROL_CALIDAD", new SqlParameter("pi_IDE_CONTROL_CALIDAD", Types.NUMERIC));
//			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
//			output_objParametros.put("pi_COD_MES", new SqlParameter("pi_COD_MES", Types.VARCHAR));
//			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
//			output_objParametros.put("pi_FLG_TIPO_PRODUCTO", new SqlParameter("pi_FLG_TIPO_PRODUCTO", Types.VARCHAR));
//			output_objParametros.put("pi_COD_ALMACEN", new SqlParameter("pi_COD_ALMACEN", Types.VARCHAR));
//			output_objParametros.put("pi_COD_DDI", new SqlParameter("pi_COD_DDI", Types.VARCHAR));
//			output_objParametros.put("pi_FEC_EMISION", new SqlParameter("pi_FEC_EMISION", Types.DATE));
//			output_objParametros.put("pi_FK_IDE_TIP_CONTROL", new SqlParameter("pi_FK_IDE_TIP_CONTROL", Types.NUMERIC));
//			output_objParametros.put("pi_FK_IDE_ENCARGADO", new SqlParameter("pi_FK_IDE_ENCARGADO", Types.NUMERIC));
//			output_objParametros.put("pi_FK_IDE_INSPECTOR", new SqlParameter("pi_FK_IDE_INSPECTOR", Types.NUMERIC));
//			output_objParametros.put("pi_FK_IDE_PROVEEDOR", new SqlParameter("pi_FK_IDE_PROVEEDOR", Types.NUMERIC));
//			output_objParametros.put("pi_FK_IDE_EMP_TRANS", new SqlParameter("pi_FK_IDE_EMP_TRANS", Types.NUMERIC));
//			output_objParametros.put("pi_FK_IDE_CHOFER", new SqlParameter("pi_FK_IDE_CHOFER", Types.NUMERIC));			
//			output_objParametros.put("pi_CONCLUSIONES", new SqlParameter("pi_CONCLUSIONES", Types.VARCHAR));
//			output_objParametros.put("pi_RECOMENDACIONES", new SqlParameter("pi_RECOMENDACIONES", Types.VARCHAR));
//			output_objParametros.put("pi_FK_IDE_ALMACEN_OD", new SqlParameter("pi_FK_IDE_ALMACEN_OD", Types.NUMERIC));
//			output_objParametros.put("pi_NRO_PLACA", new SqlParameter("pi_NRO_PLACA", Types.VARCHAR));
//			output_objParametros.put("pi_NRO_ORDEN_COMPRA", new SqlParameter("pi_NRO_ORDEN_COMPRA", Types.VARCHAR));
//			output_objParametros.put("pi_FK_IDE_ESTADO", new SqlParameter("pi_FK_IDE_ESTADO", Types.NUMERIC));
//			output_objParametros.put("pi_FLG_TIPO_BIEN", new SqlParameter("pi_FLG_TIPO_BIEN", Types.VARCHAR));
//			output_objParametros.put("pi_USU_MODIFICA", new SqlParameter("pi_USU_MODIFICA", Types.VARCHAR));
//			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
//			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
//
//			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
//			
//			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
//			
//			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
//			
//			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
//				LOGGER.info("[actualizarRegistroControlCalidad] Ocurrio un error en la operacion del USP_UPD_REGISTRA_CONTROL_CALID");
//    			throw new Exception();
//    		}
//
//			registroControlCalidad.setCodigoRespuesta(codigoRespuesta);
//			registroControlCalidad.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
//	
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			throw new Exception();
//		}		
//		LOGGER.info("[actualizarRegistroControlCalidad] Fin ");
		return registroControlCalidad;
	}
	
	
}

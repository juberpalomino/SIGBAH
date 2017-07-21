package pe.com.sigbah.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;
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
import pe.com.sigbah.common.bean.DetalleProductoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoDonacionBean;
import pe.com.sigbah.common.bean.DocumentoIngresoBean;
import pe.com.sigbah.common.bean.DonacionesBean;
import pe.com.sigbah.common.bean.DonacionesIngresoBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ProductoBean;
import pe.com.sigbah.common.bean.ProductoDonacionBean;
import pe.com.sigbah.common.bean.ProductoDonacionIngresoBean;
import pe.com.sigbah.common.bean.ProductoIngresoBean;
import pe.com.sigbah.common.bean.RegionDonacionBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.DonacionDao;
import pe.com.sigbah.mapper.ControlCalidadDonIngresoMapper;
import pe.com.sigbah.mapper.DatosDonacionMapper;
import pe.com.sigbah.mapper.DetalleProductoControlCalidadMapper;
import pe.com.sigbah.mapper.DocumentoDonacionIngresoMapper;
import pe.com.sigbah.mapper.DocumentoDonacionMapper;
import pe.com.sigbah.mapper.DonacionesIngresosMapper;
import pe.com.sigbah.mapper.DonacionesMapper;
import pe.com.sigbah.mapper.PaisMapper;
import pe.com.sigbah.mapper.ProductoDonacionIngresoMapper;
import pe.com.sigbah.mapper.ProductoDonacionMapper;
import pe.com.sigbah.mapper.ProductoIngresoMapper;
import pe.com.sigbah.mapper.ProductosXDonacionMapper;
import pe.com.sigbah.mapper.RegionDonacionMapper;
import pe.com.sigbah.mapper.RegistroControlCalidadMapper;
import pe.com.sigbah.mapper.RegistroDonacionIngresoMapper;
import pe.com.sigbah.mapper.DonanteMapper;
import pe.com.sigbah.mapper.EstadoDonacionMapper;
import pe.com.sigbah.mapper.RegistroDonacionMapper;
import pe.com.sigbah.mapper.EstadoXUsuarioMapper;
import pe.com.sigbah.mapper.EstadosXDonacionMapper;

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
			input_objParametros.addValue("PI_ESTADO", donacionesBean.getCodigoEstado(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", Utils.getParam(donacionesBean.getCodigoMes()), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_PROPUESTASDONACIONE");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ANIO", new SqlParameter("PI_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.VARCHAR));
			output_objParametros.put("PI_ESTADO", new SqlParameter("PI_ESTADO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
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
	
	////////////////ARCHY///////////////////
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
 	public List<DonacionesBean> listarSalidaDonaciones(DonacionesBean donacionesBean) throws Exception {
		LOGGER.info("[listarSalidaDonaciones] Inicio ");
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
		LOGGER.info("[listarSalidaDonaciones] Fin ");
		return lista;
	}
	
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#obtenerCorrelativoControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public DonacionesBean obtenerCodigoDonativo(DonacionesBean donacionesBean) throws Exception {
		LOGGER.info("[obtenerCodigoDonativo] Inicio ");
		DonacionesBean detalleDonacion = new DonacionesBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_ANIO", donacionesBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_DDI", donacionesBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DDI", donacionesBean.getCodigoDdi(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_GENERA_CORR_DONACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ANIO", new SqlParameter("PI_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_DDI", new SqlParameter("PI_DDI", Types.NUMERIC));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PO_COD_DONACION", new SqlOutParameter("PO_COD_DONACION", Types.VARCHAR));
			output_objParametros.put("PO_COD_DONACION_CONCATENADO", new SqlOutParameter("PO_COD_DONACION_CONCATENADO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerCodigoDonativo] Ocurrio un error en la operacion del USP_SEL_GENERA_CORR_DONACION : "+mensajeRespuesta);
    			throw new Exception();
    		}

			detalleDonacion.setCodigoDonacion((String) out.get("PO_COD_DONACION"));
			detalleDonacion.setTextoCodigo((String) out.get("PO_COD_DONACION_CONCATENADO"));
			detalleDonacion.setCodigoRespuesta(codigoRespuesta);
			detalleDonacion.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCodigoDonativo] Fin ");
		return detalleDonacion;
	}
	
	
	public DonacionesBean insertarRegistroDonacion(DonacionesBean donacionesBean) throws Exception {
		LOGGER.info("[insertarRegistroDonacion] Inicio ");
		DonacionesBean registroDonacion = new DonacionesBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DDI", donacionesBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DONACION", donacionesBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(donacionesBean.getFechaEmision()), Types.DATE);
			input_objParametros.addValue("PI_FK_IDE_PAIS_DONANTE", donacionesBean.getIdPaisDonante(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIP_DONANTE", donacionesBean.getTipoDonante(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIP_ORIGEN_DONANTE", donacionesBean.getTipoOrigenDonante(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_OFICINA", donacionesBean.getIdOficina(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_PERSONAL", donacionesBean.getIdPersonal(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_DONANTE", donacionesBean.getIdDonante(), Types.NUMERIC);
			input_objParametros.addValue("PI_FINALIDAD", donacionesBean.getFinalidad(), Types.VARCHAR);
			input_objParametros.addValue("PI_OBSERVACION", donacionesBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_ESTADO", donacionesBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("PI_BLOQUE_TEXTO1", donacionesBean.getTextoa(), Types.VARCHAR);
			input_objParametros.addValue("PI_BLOQUE_TEXTO2", donacionesBean.getTextob(), Types.VARCHAR);			
			input_objParametros.addValue("PI_USERNAME", donacionesBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DDI", donacionesBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DONACION", donacionesBean.getCodigoDonacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_ANIO", donacionesBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_DEE", donacionesBean.getIdDee(), Types.VARCHAR);
			input_objParametros.addValue("PI_TIPO_DONACION", donacionesBean.getTipoDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_INSCRIPCIONDONACIO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.VARCHAR));
			output_objParametros.put("PI_FEC_EMISION", new SqlParameter("PI_FEC_EMISION", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PAIS_DONANTE", new SqlParameter("PI_FK_IDE_PAIS_DONANTE", Types.VARCHAR));
			output_objParametros.put("PI_TIP_DONANTE", new SqlParameter("PI_TIP_DONANTE", Types.VARCHAR));
			output_objParametros.put("PI_TIP_ORIGEN_DONANTE", new SqlParameter("PI_TIP_ORIGEN_DONANTE", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_OFICINA", new SqlParameter("PI_FK_IDE_OFICINA", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PERSONAL", new SqlParameter("PI_FK_IDE_PERSONAL", Types.DATE));
			output_objParametros.put("PI_FK_IDE_DONANTE", new SqlParameter("PI_FK_IDE_DONANTE", Types.NUMERIC));
			output_objParametros.put("PI_FINALIDAD", new SqlParameter("PI_FINALIDAD", Types.NUMERIC));
			output_objParametros.put("PI_OBSERVACION", new SqlParameter("PI_OBSERVACION", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_ESTADO", new SqlParameter("PI_FK_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("PI_BLOQUE_TEXTO1", new SqlParameter("PI_BLOQUE_TEXTO1", Types.NUMERIC));
			output_objParametros.put("PI_BLOQUE_TEXTO2", new SqlParameter("PI_BLOQUE_TEXTO2", Types.NUMERIC));			
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_COD_DONACION", new SqlParameter("PI_COD_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DEE", new SqlParameter("PI_IDE_DEE", Types.VARCHAR));
			output_objParametros.put("PI_TIPO_DONACION", new SqlParameter("PI_TIPO_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_IDE_DONACION", new SqlOutParameter("PO_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarRegistroDonacion] Ocurrio un error en la operacion del USP_INS_UPD_INSCRIPCIONDONACIO : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroDonacion.setIdDonacion(((BigDecimal) out.get("PO_IDE_DONACION")).intValue());
			registroDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarRegistroDonacion] Fin ");
		return registroDonacion;
	}


	@Override
	public DonacionesBean actualizarRegistroDonacion(DonacionesBean donacionesBean) throws Exception {
		LOGGER.info("[actualizarRegistroDonacion] Inicio ");
		DonacionesBean registroDonacion = new DonacionesBean();
		try {		
			System.out.println("ESTADO : "+donacionesBean.getIdEstado());
			System.out.println("ESTADO : "+donacionesBean.getIdDonacion());
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DDI", donacionesBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DONACION", donacionesBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(donacionesBean.getFechaEmision()), Types.DATE);
			input_objParametros.addValue("PI_FK_IDE_PAIS_DONANTE", donacionesBean.getIdPaisDonante(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIP_DONANTE", donacionesBean.getTipoDonante(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIP_ORIGEN_DONANTE", donacionesBean.getTipoOrigenDonante(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_OFICINA", donacionesBean.getIdOficina(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_PERSONAL", donacionesBean.getIdPersonal(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_DONANTE", donacionesBean.getIdDonante(), Types.NUMERIC);
			input_objParametros.addValue("PI_FINALIDAD", donacionesBean.getFinalidad(), Types.VARCHAR);
			input_objParametros.addValue("PI_OBSERVACION", donacionesBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_ESTADO", donacionesBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("PI_BLOQUE_TEXTO1", donacionesBean.getTextoa(), Types.VARCHAR);
			input_objParametros.addValue("PI_BLOQUE_TEXTO2", donacionesBean.getTextob(), Types.VARCHAR);			
			input_objParametros.addValue("PI_USERNAME", donacionesBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DDI", donacionesBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DONACION", donacionesBean.getCodigoDonacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_ANIO", donacionesBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_DEE", donacionesBean.getIdDee(), Types.VARCHAR);
			input_objParametros.addValue("PI_TIPO_DONACION", donacionesBean.getTipoDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_CONTROL", "U", Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_INSCRIPCIONDONACIO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.VARCHAR));
			output_objParametros.put("PI_FEC_EMISION", new SqlParameter("PI_FEC_EMISION", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PAIS_DONANTE", new SqlParameter("PI_FK_IDE_PAIS_DONANTE", Types.VARCHAR));
			output_objParametros.put("PI_TIP_DONANTE", new SqlParameter("PI_TIP_DONANTE", Types.VARCHAR));
			output_objParametros.put("PI_TIP_ORIGEN_DONANTE", new SqlParameter("PI_TIP_ORIGEN_DONANTE", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_OFICINA", new SqlParameter("PI_FK_IDE_OFICINA", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PERSONAL", new SqlParameter("PI_FK_IDE_PERSONAL", Types.DATE));
			output_objParametros.put("PI_FK_IDE_DONANTE", new SqlParameter("PI_FK_IDE_DONANTE", Types.NUMERIC));
			output_objParametros.put("PI_FINALIDAD", new SqlParameter("PI_FINALIDAD", Types.NUMERIC));
			output_objParametros.put("PI_OBSERVACION", new SqlParameter("PI_OBSERVACION", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_ESTADO", new SqlParameter("PI_FK_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("PI_BLOQUE_TEXTO1", new SqlParameter("PI_BLOQUE_TEXTO1", Types.NUMERIC));
			output_objParametros.put("PI_BLOQUE_TEXTO2", new SqlParameter("PI_BLOQUE_TEXTO2", Types.NUMERIC));			
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_COD_DONACION", new SqlParameter("PI_COD_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DEE", new SqlParameter("PI_IDE_DEE", Types.VARCHAR));
			output_objParametros.put("PI_TIPO_DONACION", new SqlParameter("PI_TIPO_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_IDE_DONACION", new SqlOutParameter("PO_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[actualizarRegistroDonacion] Ocurrio un error en la operacion del USP_INS_UPD_INSCRIPCIONDONACIO : "+mensajeRespuesta);
    			throw new Exception();
    		}
				
			registroDonacion.setIdDonacion(donacionesBean.getIdDonacion());
			registroDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarRegistroDonacion] Fin ");
		return registroDonacion;
	}
	
	@Override
	public List<ItemBean> listarDonadores(DonacionesBean donacionesBean) throws Exception  {
		LOGGER.info("[listarDonadores] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			System.out.println("DDI "+donacionesBean.getIdDdi());
			System.out.println("TIPODONANTE "+donacionesBean.getTipoDonante());
			Integer parametro1 = Utils.getParamInt(donacionesBean.getIdDdi());
			Integer parametro2 = Utils.getParamInt(Integer.parseInt(donacionesBean.getTipoDonante()));
			input_objParametros.addValue("PI_DDI", parametro1, Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_DONANTE", parametro2, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_DONANTES");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_DDI", new SqlParameter("PI_DDI", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_DONANTE", new SqlParameter("PI_TIPO_DONANTE", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new DonanteMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				throw new Exception();
			} else {
				lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarDonadores] Fin ");
		return lista;
	}
	
	@Override
	public DonacionesBean obtenerDonacionXIdDonacion(Integer idDonacion) throws Exception {
		LOGGER.info("[obtenerDonacionXIdDonacion] Inicio ");
		DonacionesBean donaciones = new DonacionesBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_DONACION", idDonacion, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_EDITAR_PROPUESTA_DONAC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new RegistroDonacionMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerDonacionXIdDonacion] Ocurrio un error en la operacion del USP_SEL_EDITAR_PROPUESTA_DONAC : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<DonacionesBean> lista = (List<DonacionesBean>) out.get("PO_CURSOR");
			if (!Utils.isEmpty(lista)) {
				donaciones = lista.get(0);
			}
			
			donaciones.setCodigoRespuesta(codigoRespuesta);
			donaciones.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerDonacionXIdDonacion] Fin ");
		return donaciones;
	}
	
	@Override
	public List<ItemBean> listarEstadoDonacionUsuario(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarEstadoDonacionUsuario] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			input_objParametros.addValue("PI_ID_USER", itemBean.getIcodigo(), Types.NUMERIC);
			input_objParametros.addValue("PI_NOMBRE_MODULO", itemBean.getDescripcion(), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_ESTADOS_POR_USUARIO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_USER", new SqlParameter("PI_ID_USER", Types.NUMERIC));
			output_objParametros.put("PI_NOMBRE_MODULO", new SqlParameter("PI_NOMBRE_MODULO", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_ESTADOS", new SqlOutParameter("PO_CURSOR_ESTADOS", OracleTypes.CURSOR, new EstadoXUsuarioMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_CURSOR_ESTADOS");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarEstadoDonacionUsuario] Fin ");
		return lista;
	}
	
	@Override
	public List<ItemBean> mostrarEstadoDonacionUsuario(ItemBean itemBean) throws Exception {
		LOGGER.info("[mostrarEstadoDonacionUsuario] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			input_objParametros.addValue("PI_IDE_DONACION", itemBean.getIcodigo(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_ESTADOS_DON");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new EstadosXDonacionMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_CURSOR");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[mostrarEstadoDonacionUsuario] Fin ");
		return lista;
	}
	
	@Override
	public ProductoDonacionBean insertarProductoDonacion(ProductoDonacionBean productoDonacionBean) throws Exception {
		LOGGER.info("[insertarProductoDonacion] Inicio ");
		ProductoDonacionBean registroProductoDonacion = new ProductoDonacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_FK_IDE_DONACION", productoDonacionBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DET_DONACION", productoDonacionBean.getIdDetDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_PRODUCTO", productoDonacionBean.getIdProducto(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_MONEDA", productoDonacionBean.getIdMoneda(), Types.NUMERIC);
			input_objParametros.addValue("PI_CANTIDAD", productoDonacionBean.getCantidad(), Types.NUMERIC);
			input_objParametros.addValue("PI_FEC_VENCIMIENTO",  DateUtil.obtenerFechaHoraParseada(productoDonacionBean.getFecVencimiento()), Types.DATE);
			input_objParametros.addValue("PI_IMP_MONEDA_ORIGEN", productoDonacionBean.getMonOrigen(), Types.NUMERIC);
			input_objParametros.addValue("PI_IMP_MONEDA_SOLES", productoDonacionBean.getMonSoles(), Types.NUMERIC);
			input_objParametros.addValue("PI_IMP_MONEDA_DOLAR", productoDonacionBean.getMonDolares(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", productoDonacionBean.getUsuario(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_PRODUCTODONACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_FK_IDE_DONACION", new SqlParameter("PI_FK_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_IDE_DET_DONACION", new SqlParameter("PI_IDE_DET_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PRODUCTO", new SqlParameter("PI_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_MONEDA", new SqlParameter("PI_IDE_MONEDA", Types.NUMERIC));
			output_objParametros.put("PI_CANTIDAD", new SqlParameter("PI_CANTIDAD", Types.NUMERIC));
			output_objParametros.put("PI_FEC_VENCIMIENTO", new SqlParameter("PI_FEC_VENCIMIENTO", Types.VARCHAR));
			output_objParametros.put("PI_IMP_MONEDA_ORIGEN", new SqlParameter("PI_IMP_MONEDA_ORIGEN", Types.NUMERIC));
			output_objParametros.put("PI_IMP_MONEDA_SOLES", new SqlParameter("PI_IMP_MONEDA_SOLES", Types.NUMERIC));
			output_objParametros.put("PI_IMP_MONEDA_DOLAR", new SqlParameter("PI_IMP_MONEDA_DOLAR", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_IDE_DET_DONACION", new SqlOutParameter("PO_IDE_DET_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarProductoDonacion] Ocurrio un error en la operacion del USP_INS_UPD_PRODUCTODONACION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			registroProductoDonacion.setIdDetDonacion(((BigDecimal) out.get("PO_IDE_DET_DONACION")).intValue());
			registroProductoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroProductoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarProductoDonacion] Fin ");
		return registroProductoDonacion;
	}
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.LogisticaDao#listarProductoDonacion(pe.com.sigbah.common.bean.ProductoDonacionBean)
	 */
	@Override
	public List<ProductoDonacionBean> listarProductoDonacion(ProductoDonacionBean productoDonacionBean) throws Exception {
		LOGGER.info("[listarProductoDonacion] Inicio ");
		List<ProductoDonacionBean> lista = new ArrayList<ProductoDonacionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_DONACION", productoDonacionBean.getIdDonacion(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_PROD_DONACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new ProductoDonacionMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductoDonacion] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_PROD_DONACION : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<ProductoDonacionBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}
		LOGGER.info("[listarProductoDonacion] Fin ");
		return lista;
	}
	
	@Override
	public ProductoDonacionBean eliminarProductoDonacion(ProductoDonacionBean productoDonacionBean) throws Exception {
		LOGGER.info("[eliminarProductoDonacion] Inicio ");
		ProductoDonacionBean registroProductoDonacion = new ProductoDonacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("IDE_DET_DONACION", productoDonacionBean.getIdDetDonacion(), Types.NUMERIC);
			input_objParametros.addValue("FK_IDE_DONACION", productoDonacionBean.getIdDonacion(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_PRODUCTODONACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("IDE_DET_DONACION", new SqlParameter("IDE_DET_DONACION", Types.NUMERIC));
			output_objParametros.put("FK_IDE_DONACION", new SqlParameter("FK_IDE_DONACION", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarProductoDonacion] Ocurrio un error en la operacion del USP_DEL_PRODUCTODONACION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroProductoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarProductoDonacion] Fin ");
		return registroProductoDonacion;
	}
	
	@Override
	public DocumentoDonacionBean insertarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception {
		LOGGER.info("[insertarDocumentoDonacion] Inicio ");
		DocumentoDonacionBean registroDocumentoDonacion = new DocumentoDonacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DONACION", documentoDonacionBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DOCUMENTO", documentoDonacionBean.getIdDocumentoDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_DOCUMENTO", documentoDonacionBean.getNroDocumento(), Types.VARCHAR);
			input_objParametros.addValue("PI_OBSERVACION", documentoDonacionBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_TIP_DOCUMENTO", documentoDonacionBean.getIdTipoDocumento(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ALFRESCO",  documentoDonacionBean.getCodAlfresco(), Types.VARCHAR);
			input_objParametros.addValue("PI_NOM_ARCHIVO", documentoDonacionBean.getNombreArchivo(), Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", documentoDonacionBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_FILE_DONACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_IDE_DOCUMENTO", new SqlParameter("PI_IDE_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PI_NRO_DOCUMENTO", new SqlParameter("PI_NRO_DOCUMENTO", Types.VARCHAR));
			output_objParametros.put("PI_OBSERVACION", new SqlParameter("PI_OBSERVACION", Types.VARCHAR));
			output_objParametros.put("PI_IDE_TIP_DOCUMENTO", new SqlParameter("PI_IDE_TIP_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PI_COD_ALFRESCO", new SqlParameter("PI_COD_ALFRESCO", Types.VARCHAR));
			output_objParametros.put("PI_NOM_ARCHIVO", new SqlParameter("PI_NOM_ARCHIVO", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_IDE_DOCUMENTO", new SqlOutParameter("PO_IDE_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarDocumentoDonacion] Ocurrio un error en la operacion del USP_INS_UPD_FILE_DONACION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			registroDocumentoDonacion.setIdDocumentoDonacion(((BigDecimal) out.get("PO_IDE_DOCUMENTO")).intValue());
			registroDocumentoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarDocumentoDonacion] Fin ");
		return registroDocumentoDonacion;
	}
	
	@Override
	public List<DocumentoDonacionBean> listarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception {
		LOGGER.info("[DocumentoDonacionBean] Inicio ");
		List<DocumentoDonacionBean> lista = new ArrayList<DocumentoDonacionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_DONACION", documentoDonacionBean.getIdDonacion(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_DOC_DONACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new DocumentoDonacionMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[DocumentoDonacionBean] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_DOC_DONACION : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<DocumentoDonacionBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}
		LOGGER.info("[DocumentoDonacionBean] Fin ");
		return lista;
	}
	
	@Override
	public DocumentoDonacionBean eliminarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception {
		LOGGER.info("[eliminarDocumentoDonacion] Inicio ");
		DocumentoDonacionBean registroDocumentoDonacion = new DocumentoDonacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DONACION", documentoDonacionBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DOCUMENTO", documentoDonacionBean.getIdDocumentoDonacion(), Types.NUMERIC);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_DOCUMENTO_DONACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_IDE_DOCUMENTO", new SqlParameter("PI_IDE_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarDocumentoDonacion] Ocurrio un error en la operacion del USP_DEL_DOCUMENTO_DONACION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroDocumentoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarDocumentoDonacion] Fin ");
		return registroDocumentoDonacion;
	}
	
	@Override
	public List<RegionDonacionBean> listarRegionesXDonacion(RegionDonacionBean regionDonacionBean) throws Exception  {
		LOGGER.info("[listarRegionesXDonacion] Inicio ");
		List<RegionDonacionBean> lista = new ArrayList<RegionDonacionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			Integer parametro1 = Utils.getParamInt(regionDonacionBean.getIdDonacion());
			System.out.println("CODIGO: "+parametro1);
			input_objParametros.addValue("PI_IDE_DONACION", parametro1, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_MOSTRAR_DONACION_REGION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new RegionDonacionMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				throw new Exception();
			} else {
				lista = (List<RegionDonacionBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarRegionesXDonacion] Fin ");
		return lista;
	}
	
	@Override
	public RegionDonacionBean insertarRegionDonacion(RegionDonacionBean regionDonacionBean) throws Exception {
		LOGGER.info("[insertarRegionDonacion] Inicio ");
		RegionDonacionBean registroDocumentoDonacion = new RegionDonacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DONACION", regionDonacionBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_REGION", regionDonacionBean.getIdRegion(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", regionDonacionBean.getUsuarioRegistro(), Types.VARCHAR);	

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_DONACION_REGION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_IDE_REGION", new SqlParameter("PI_IDE_REGION", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarRegionDonacion] Ocurrio un error en la operacion del USP_INS_DONACION_REGION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			registroDocumentoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarRegionDonacion] Fin ");
		return registroDocumentoDonacion;
	}
	
	@Override
	public RegionDonacionBean eliminarRegionDonacion(RegionDonacionBean regionDonacionBean) throws Exception {
		LOGGER.info("[eliminarRegionDonacion] Inicio ");
		RegionDonacionBean registroDocumentoDonacion = new RegionDonacionBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DONACION", regionDonacionBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_REGION", regionDonacionBean.getIdRegion(), Types.NUMERIC);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_DONACION_REGION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_IDE_REGION", new SqlParameter("PI_IDE_REGION", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarRegionDonacion] Ocurrio un error en la operacion del USP_DEL_DONACION_REGION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroDocumentoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarRegionDonacion] Fin ");
		return registroDocumentoDonacion;
	}
	

	@Override
	public List<ItemBean> listarReporteDonacion(Integer idControlCalidad) throws Exception {
		LOGGER.info("[listarReporteDonacion] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_ANIO", idControlCalidad, Types.VARCHAR);
			input_objParametros.addValue("PI_DDI", idControlCalidad, Types.NUMERIC);
			input_objParametros.addValue("PI_ID_DONACION", idControlCalidad, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_LOGISTICA);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_SOLICITUD_APROB");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ANIO", new SqlParameter("PI_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_DDI", new SqlParameter("PI_DDI", Types.NUMERIC));
			output_objParametros.put("PI_ID_DONACION", new SqlParameter("PI_ID_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATA_GENERAL", new SqlOutParameter("PO_CURSOR_DATA_GENERAL", OracleTypes.CURSOR, new DetalleProductoControlCalidadMapper()));
			output_objParametros.put("PO_CURSOR_PRODUCTOS", new SqlOutParameter("PO_CURSOR_PRODUCTOS", OracleTypes.CURSOR, new DetalleProductoControlCalidadMapper()));
			output_objParametros.put("PO_CURSOR_REGIONES", new SqlOutParameter("PO_CURSOR_REGIONES", OracleTypes.CURSOR, new DetalleProductoControlCalidadMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarReporteDonacion] Ocurrio un error en la operacion del USP_SEL_REPORT_SOLICITUD_APROB : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ItemBean>) out.get("PO_CURSOR_DATA_GENERAL");
			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarReporteDonacion] Fin ");
		return lista;
	}
	
	@Override
	public DonacionesBean actualizarEstadoDonacion(DonacionesBean donacionesBean) throws Exception {
		LOGGER.info("[actualizarEstadoDonacion] Inicio ");
		DonacionesBean registroDonacion = new DonacionesBean();
		try {		
			System.out.println("ESTADO : "+donacionesBean.getIdEstado());
			System.out.println("ESTADO : "+donacionesBean.getIdDonacion());
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_ide_donacion", donacionesBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("pi_ide_estado", donacionesBean.getIdEstado(), Types.NUMERIC);		
			input_objParametros.addValue("PI_USERNAME", donacionesBean.getUsuarioRegistro(), Types.VARCHAR);		
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_ESTADO_DONACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_ide_donacion", new SqlParameter("pi_ide_donacion", Types.NUMERIC));
			output_objParametros.put("pi_ide_estado", new SqlParameter("pi_ide_estado", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[actualizarEstadoDonacion] Ocurrio un error en la operacion del USP_INS_ESTADO_DONACION : "+mensajeRespuesta);
    			throw new Exception();
    		}
				
			registroDonacion.setIdDonacion(donacionesBean.getIdDonacion());
			registroDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarEstadoDonacion] Fin ");
		return registroDonacion;
	}
	
	///////////////////////////////////////////////////////////////
	///////////ORDEN INGRESO//////////////////////////////////////
	//////////////////////////////////////////////////////////////
	
	@Override
 	public List<DonacionesIngresoBean> listarIngresoDonaciones(DonacionesIngresoBean donacionesIngresoBean) throws Exception {
		LOGGER.info("[listarIngresoDonaciones] Inicio ");
		List<DonacionesIngresoBean> lista = new ArrayList<DonacionesIngresoBean>();
		try {

			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_ANIO", donacionesIngresoBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_MES", donacionesIngresoBean.getCodigoMes(), Types.VARCHAR);
			input_objParametros.addValue("PI_ALMACEN", donacionesIngresoBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_MOVIMIENTO", donacionesIngresoBean.getIdMovimiento(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_ORDENES_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ANIO", new SqlParameter("PI_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_MES", new SqlParameter("PI_MES", Types.VARCHAR));
			output_objParametros.put("PI_ALMACEN", new SqlParameter("PI_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_MOVIMIENTO", new SqlParameter("PI_TIPO_MOVIMIENTO", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new DonacionesIngresosMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				throw new Exception();
			} else {
				lista = (List<DonacionesIngresoBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarIngresoDonaciones] Fin ");
		return lista;
	}
	
	@Override
	public DonacionesIngresoBean obtenerCorrelativoOrdenIngreso(DonacionesIngresoBean donacionesIngresoBean) throws Exception {
		LOGGER.info("[obtenerCorrelativoOrdenIngreso] Inicio ");
		DonacionesIngresoBean detalleUsuarioBean = new DonacionesIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_COD_ANIO", donacionesIngresoBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DDI", donacionesIngresoBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_ALMACEN", donacionesIngresoBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ALMACEN", donacionesIngresoBean.getCodAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("PI_TIPO_ORIGEN", "D", Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_GENERA_NRO_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_COD_ALMACEN", new SqlParameter("PI_COD_ALMACEN", Types.VARCHAR));
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
			detalleUsuarioBean.setCodIngreso((String) out.get("PO_COD_INGRESO"));
			detalleUsuarioBean.setCodigoRespuesta(codigoRespuesta);
			detalleUsuarioBean.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCorrelativoOrdenIngreso] Fin ");
		return detalleUsuarioBean;
	}
	
	@Override
	public List<DonacionesBean> listarCodigoDonacion(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarCodigoDonacion] Inicio ");
		List<DonacionesBean> lista = new ArrayList<DonacionesBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_DDI", itemBean.getIcodigo(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ANIO", itemBean.getDescripcion(), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_CODIGO_DONACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.VARCHAR));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new DatosDonacionMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarCodigoDonacion] Ocurrio un error en la operacion del USP_SEL_LISTAR_CODIGO_DONACION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<DonacionesBean>) out.get("PO_CURSOR");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarCodigoDonacion] Fin ");
		return lista;
	}
	
	@Override
	public List<ControlCalidadBean> listarControCalidad(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarControCalidad] Inicio ");
		List<ControlCalidadBean> lista = new ArrayList<ControlCalidadBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", itemBean.getVcodigo(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_DDI", itemBean.getDescripcion(), Types.VARCHAR);
			input_objParametros.addValue("PI_TIPOCONTROL", Constantes.FIVE_INT, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_CONTROLCALIDAD");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_TIPOCONTROL", new SqlParameter("PI_TIPOCONTROL", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new ControlCalidadDonIngresoMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarControCalidad] Ocurrio un error en la operacion del USP_SEL_LISTAR_CONTROLCALIDAD : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ControlCalidadBean>) out.get("PO_LR_RECORDSET");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarControCalidad] Fin ");
		return lista;
	}
	
	@Override
	public DonacionesIngresoBean insertarRegistroDonacionIngreso(DonacionesIngresoBean donacionesIngresoBean) throws Exception {
		LOGGER.info("[insertarRegistroDonacionIngreso] Inicio ");
		DonacionesIngresoBean registroIngresoDonacion = new DonacionesIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_INGRESO", donacionesIngresoBean.getIdIngreso(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ANIO", donacionesIngresoBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(donacionesIngresoBean.getFechaEmision()), Types.DATE);
			input_objParametros.addValue("PI_FK_IDE_DONACION", donacionesIngresoBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_MED_TRANSPORTE", donacionesIngresoBean.getIdMedTransporte(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_TIP_MOVIMIENTO", donacionesIngresoBean.getIdTipoMovimiento(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ALMACEN", donacionesIngresoBean.getCodAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_ALMACEN", donacionesIngresoBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_ID_ALM_PROCEDENCIA", donacionesIngresoBean.getIdAlmacenProcedencia(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DDI", donacionesIngresoBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_NRO_ORDEN_INGRESO", donacionesIngresoBean.getNroOrdenIngreso(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_CONTROL_CALIDAD", donacionesIngresoBean.getIdControlCalidad(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_CHOFER", donacionesIngresoBean.getIdChofer(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_PLACA", donacionesIngresoBean.getNroPlaca(), Types.VARCHAR);		
			input_objParametros.addValue("PI_FEC_LLEGADA", DateUtil.obtenerFechaHoraParseada(donacionesIngresoBean.getFechaLlegada()), Types.DATE);			
			input_objParametros.addValue("PI_OBSERVACION", donacionesIngresoBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_ESTADO", donacionesIngresoBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("PI_FLG_CONTROL_CALIDAD", donacionesIngresoBean.getFlagControlCalidad(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_EMP_TRANS", donacionesIngresoBean.getIdEmpresaTrans(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_RESPONSABLE", donacionesIngresoBean.getIdResponsable(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", "D", Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", donacionesIngresoBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);			
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_ORDEN_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_INGRESO", new SqlParameter("PI_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_FEC_EMISION", new SqlParameter("PI_FEC_EMISION", Types.DATE));
			output_objParametros.put("PI_FK_IDE_DONACION", new SqlParameter("PI_FK_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_MED_TRANSPORTE", new SqlParameter("PI_FK_IDE_MED_TRANSPORTE", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_TIP_MOVIMIENTO", new SqlParameter("PI_FK_IDE_TIP_MOVIMIENTO", Types.NUMERIC));
			output_objParametros.put("PI_COD_ALMACEN", new SqlParameter("PI_COD_ALMACEN", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_ALMACEN", new SqlParameter("PI_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_FK_ID_ALM_PROCEDENCIA", new SqlParameter("PI_FK_ID_ALM_PROCEDENCIA", Types.NUMERIC));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_NRO_ORDEN_INGRESO", new SqlParameter("PI_NRO_ORDEN_INGRESO", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_CONTROL_CALIDAD", new SqlParameter("PI_FK_IDE_CONTROL_CALIDAD", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_CHOFER", new SqlParameter("PI_FK_IDE_CHOFER", Types.NUMERIC));
			output_objParametros.put("PI_NRO_PLACA", new SqlParameter("PI_NRO_PLACA", Types.VARCHAR));		
			output_objParametros.put("PI_FEC_LLEGADA", new SqlParameter("PI_FEC_LLEGADA", Types.DATE));			
			output_objParametros.put("PI_OBSERVACION", new SqlParameter("PI_OBSERVACION", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_ESTADO", new SqlParameter("PI_FK_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("PI_FLG_CONTROL_CALIDAD", new SqlParameter("PI_FLG_CONTROL_CALIDAD", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_EMP_TRANS", new SqlParameter("PI_FK_IDE_EMP_TRANS", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_RESPONSABLE", new SqlParameter("PI_FK_IDE_RESPONSABLE", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_NRO_ORDEN_INGRESO", new SqlOutParameter("PO_NRO_ORDEN_INGRESO", Types.VARCHAR));
			output_objParametros.put("PO_COD_INGRESO_CONCATENADO", new SqlOutParameter("PO_COD_INGRESO_CONCATENADO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarRegistroDonacionIngreso] Ocurrio un error en la operacion del USP_INS_UPD_ORDEN_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroIngresoDonacion.setNroOrdenIngreso(((String) out.get("PO_NRO_ORDEN_INGRESO")));
			registroIngresoDonacion.setCodIngreso(((String) out.get("PO_COD_INGRESO_CONCATENADO")));
			registroIngresoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroIngresoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarRegistroDonacionIngreso] Fin ");
		return registroIngresoDonacion;
	}
	
	@Override
	public DonacionesIngresoBean obtenerDonacionIngresoXIdIngreso(Integer idDonacion) throws Exception {
		LOGGER.info("[obtenerDonacionIngresoXIdDonacion] Inicio ");
		DonacionesIngresoBean donaciones = new DonacionesIngresoBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_INGRESO", idDonacion, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_EDITAR_ORDEN_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_INGRESO", new SqlParameter("PI_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new RegistroDonacionIngresoMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerDonacionIngresoXIdDonacion] Ocurrio un error en la operacion del USP_SEL_EDITAR_ORDEN_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<DonacionesIngresoBean> lista = (List<DonacionesIngresoBean>) out.get("PO_LR_RECORDSET");
			if (!Utils.isEmpty(lista)) {
				donaciones = lista.get(0);
			}
			
			donaciones.setCodigoRespuesta(codigoRespuesta);
			donaciones.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerDonacionIngresoXIdDonacion] Fin ");
		return donaciones;
	}
	
	@Override
	public List<ProductoDonacionBean> listarProductosDonacion(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarProductosDonacion] Inicio ");
		List<ProductoDonacionBean> lista = new ArrayList<ProductoDonacionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_DONACION", itemBean.getVcodigo(), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_PRODUCTOS_DON");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new ProductosXDonacionMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductosDonacion] Ocurrio un error en la operacion del USP_SEL_LISTAR_PRODUCTOS_DON : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ProductoDonacionBean>) out.get("PO_CURSOR");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProductosDonacion] Fin ");
		return lista;
	}
	
	@Override
	public List<ProductoDonacionIngresoBean> listarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionIngresoBean) throws Exception {
		LOGGER.info("[listarProductoDonacionIngreso] Inicio ");
		List<ProductoDonacionIngresoBean> lista = new ArrayList<ProductoDonacionIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_FK_IDE_INGRESO", productoDonacionIngresoBean.getIdIngreso(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_PROD_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new ProductoDonacionIngresoMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductoDonacionIngreso] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_PROD_INGRESO : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<ProductoDonacionIngresoBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}
		LOGGER.info("[listarProductoDonacionIngreso] Fin ");
		return lista;
	}
	
	@Override
	public ProductoDonacionIngresoBean insertarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionIngresoBean) throws Exception {
		LOGGER.info("[insertarProductoDonacionIngreso] Inicio ");
		ProductoDonacionIngresoBean registroProductoDonacion = new ProductoDonacionIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_FK_IDE_INGRESO", productoDonacionIngresoBean.getIdIngreso(), Types.NUMERIC);
			input_objParametros.addValue("PI_ID_INGRESO_DET", productoDonacionIngresoBean.getIdIngresoDet(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_PRODUCTO", productoDonacionIngresoBean.getIdProducto(), Types.NUMERIC);
			input_objParametros.addValue("PI_CANTIDAD", productoDonacionIngresoBean.getCantidad(), Types.NUMERIC);
			input_objParametros.addValue("PI_PREC_UNITARIO", productoDonacionIngresoBean.getPrecioUnitario(), Types.NUMERIC);
			input_objParametros.addValue("PI_FEC_VENCIMIENTO",  DateUtil.obtenerFechaHoraParseada(productoDonacionIngresoBean.getFecVencimiento()), Types.DATE);
			input_objParametros.addValue("PI_USERNAME", productoDonacionIngresoBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "I", Types.NUMERIC);	

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_PRODUCTO_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_FK_IDE_INGRESO", new SqlParameter("PI_FK_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PI_ID_INGRESO_DET", new SqlParameter("PI_ID_INGRESO_DET", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PRODUCTO", new SqlParameter("PI_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("PI_CANTIDAD", new SqlParameter("PI_CANTIDAD", Types.NUMERIC));
			output_objParametros.put("PI_PREC_UNITARIO", new SqlParameter("PI_PREC_UNITARIO", Types.NUMERIC));
			output_objParametros.put("PI_FEC_VENCIMIENTO", new SqlParameter("PI_FEC_VENCIMIENTO", Types.VARCHAR));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.NUMERIC));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarProductoDonacionIngreso] Ocurrio un error en la operacion del USP_INS_UPD_PRODUCTO_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			registroProductoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroProductoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarProductoDonacionIngreso] Fin ");
		return registroProductoDonacion;
	}
	
	@Override
	public ProductoDonacionIngresoBean eliminarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionBean) throws Exception {
		LOGGER.info("[eliminarProductoDonacionIngreso] Inicio ");
		ProductoDonacionIngresoBean registroProductoDonacion = new ProductoDonacionIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_INGRESO_DET", productoDonacionBean.getIdIngreso(), Types.NUMERIC);
			input_objParametros.addValue("pi_USERNAME", productoDonacionBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_INGRESO_PRODUCTO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_INGRESO_DET", new SqlParameter("pi_IDE_INGRESO_DET", Types.NUMERIC));
			output_objParametros.put("pi_USERNAME", new SqlParameter("pi_USERNAME", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarProductoDonacionIngreso] Ocurrio un error en la operacion del USP_DEL_INGRESO_PRODUCTO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroProductoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarProductoDonacionIngreso] Fin ");
		return registroProductoDonacion;
	}
	
	@Override
	public DocumentoIngresoBean insertarDocumentoDonacionIngreso(DocumentoIngresoBean documentoDonacionBean) throws Exception {
		LOGGER.info("[insertarDocumentoDonacionIngreso] Inicio ");
		DocumentoIngresoBean registroDocumentoDonacion = new DocumentoIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DOCUMENTO", documentoDonacionBean.getIdDocumentoIngreso(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_INGRESO", documentoDonacionBean.getIdIngreso(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_DOCUMENTO", documentoDonacionBean.getNroDocumento(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_TIP_DOCUMENTO", documentoDonacionBean.getIdTipoDocumento(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ALFRESCO", documentoDonacionBean.getCodigoArchivoAlfresco(), Types.VARCHAR);
			input_objParametros.addValue("PI_NOM_ARCHIVO",  documentoDonacionBean.getNombreArchivo(), Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", documentoDonacionBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);	

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_FILE_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DOCUMENTO", new SqlParameter("PI_IDE_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_INGRESO", new SqlParameter("PI_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PI_NRO_DOCUMENTO", new SqlParameter("PI_NRO_DOCUMENTO", Types.VARCHAR));
			output_objParametros.put("PI_IDE_TIP_DOCUMENTO", new SqlParameter("PI_IDE_TIP_DOCUMENTO", Types.NUMERIC));
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
				LOGGER.info("[insertarDocumentoDonacionIngreso] Ocurrio un error en la operacion del USP_INS_UPD_FILE_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}

			registroDocumentoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarDocumentoDonacionIngreso] Fin ");
		return registroDocumentoDonacion;
	}
	
	@Override
	public List<DocumentoIngresoBean> listarDocumentoDonacionIngreso(DocumentoIngresoBean documentoDonacionBean) throws Exception {
		LOGGER.info("[listarDocumentoDonacionIngreso] Inicio ");
		List<DocumentoIngresoBean> lista = new ArrayList<DocumentoIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_INGRESO", documentoDonacionBean.getIdIngreso(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", "D", Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_DOC_INGRESOS");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_INGRESO", new SqlParameter("PI_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new DocumentoDonacionIngresoMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarDocumentoDonacionIngreso] Ocurrio un error en la operacion del USP_SEL_LISTAR_DOC_INGRESOS : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<DocumentoIngresoBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}
		LOGGER.info("[listarDocumentoDonacionIngreso] Fin ");
		return lista;
	}
	
	@Override
	public DocumentoIngresoBean eliminarDocumentoIngresoDonacion(DocumentoIngresoBean documentoDonacionBean) throws Exception {
		LOGGER.info("[eliminarDocumentoIngresoDonacion] Inicio ");
		DocumentoIngresoBean registroDocumentoDonacion = new DocumentoIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DOCUMENTO", documentoDonacionBean.getIdDocumentoIngreso(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_INGRESO", documentoDonacionBean.getIdIngreso(), Types.NUMERIC);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_INGRESO_FILE");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DOCUMENTO", new SqlParameter("PI_IDE_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_INGRESO", new SqlParameter("PI_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarDocumentoIngresoDonacion] Ocurrio un error en la operacion del USP_DEL_INGRESO_FILE : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroDocumentoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarDocumentoIngresoDonacion] Fin ");
		return registroDocumentoDonacion;
	}
	
}

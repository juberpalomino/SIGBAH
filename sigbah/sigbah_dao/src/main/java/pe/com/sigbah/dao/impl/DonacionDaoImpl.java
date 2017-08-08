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

import pe.com.sigbah.common.bean.CartillaInventarioBean;
import pe.com.sigbah.common.bean.CierreStockBean;
import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DetalleGuiaRemisionBean;
import pe.com.sigbah.common.bean.DetalleProductoControlCalidadBean;
import pe.com.sigbah.common.bean.DocumentoDonacionBean;
import pe.com.sigbah.common.bean.DocumentoDonacionIngresoBean;
import pe.com.sigbah.common.bean.DocumentoIngresoBean;
import pe.com.sigbah.common.bean.DocumentoSalidaBean;
import pe.com.sigbah.common.bean.DonacionesBean;
import pe.com.sigbah.common.bean.DonacionesIngresoBean;
import pe.com.sigbah.common.bean.DonacionesSalidaBean;
import pe.com.sigbah.common.bean.EstadoCartillaInventarioBean;
import pe.com.sigbah.common.bean.GuiaRemisionBean;
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ProductoBean;
import pe.com.sigbah.common.bean.ProductoCartillaInventarioBean;
import pe.com.sigbah.common.bean.ProductoDonacionBean;
import pe.com.sigbah.common.bean.ProductoDonacionIngresoBean;
import pe.com.sigbah.common.bean.ProductoDonacionSalidaBean;
import pe.com.sigbah.common.bean.ProductoIngresoBean;
import pe.com.sigbah.common.bean.ProductoSalidaBean;
import pe.com.sigbah.common.bean.RegionDonacionBean;
import pe.com.sigbah.common.bean.StockAlmacenBean;
import pe.com.sigbah.common.bean.StockAlmacenProductoLoteBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.DonacionDao;
import pe.com.sigbah.mapper.CartillaInventarioMapper;
import pe.com.sigbah.mapper.CierreStockMapper;
import pe.com.sigbah.mapper.ControlCalidadDonIngresoMapper;
import pe.com.sigbah.mapper.DatosDonacionMapper;
import pe.com.sigbah.mapper.DetalleDocumentoDonacionIngresoMapper;
import pe.com.sigbah.mapper.DetalleDocumentoDonacionSalidaMapper;
import pe.com.sigbah.mapper.DetalleDonacionesIngresoInterNacionalMapper;
import pe.com.sigbah.mapper.DetalleDonacionesIngresoMapper;
import pe.com.sigbah.mapper.DetalleDonacionesIngresoNacionalMapper;
import pe.com.sigbah.mapper.DetalleDonacionesMapper;
import pe.com.sigbah.mapper.DetalleDonacionesSalidaMapper;
import pe.com.sigbah.mapper.DetalleEstadoCartillaInventarioMapper;
import pe.com.sigbah.mapper.DetalleGuiaRemisionMapper;
import pe.com.sigbah.mapper.DetalleProductoControlCalidadMapper;
import pe.com.sigbah.mapper.DetalleProductoDonacionIngresoInterNacionalMapper;
import pe.com.sigbah.mapper.DetalleProductoDonacionIngresoMapper;
import pe.com.sigbah.mapper.DetalleProductoDonacionIngresoNacionalMapper;
import pe.com.sigbah.mapper.DetalleProductoDonacionMapper;
import pe.com.sigbah.mapper.DetalleProductoDonacionSalidaMapper;
import pe.com.sigbah.mapper.DetalleRegionesDonacionMapper;
import pe.com.sigbah.mapper.DocumentoDonacionIngresoMapper;
import pe.com.sigbah.mapper.DocumentoDonacionMapper;
import pe.com.sigbah.mapper.DocumentoDonacionSalidaMapper;
import pe.com.sigbah.mapper.DonCierreStockMapper;
import pe.com.sigbah.mapper.DonRegistroCierreStockMapper;
import pe.com.sigbah.mapper.DonacionesIngresosMapper;
import pe.com.sigbah.mapper.DonacionesMapper;
import pe.com.sigbah.mapper.DonacionesSalidaMapper;
import pe.com.sigbah.mapper.PaisMapper;
import pe.com.sigbah.mapper.ProductoCartillaInventarioMapper;
import pe.com.sigbah.mapper.ProductoDonacionIngresoMapper;
import pe.com.sigbah.mapper.ProductoDonacionMapper;
import pe.com.sigbah.mapper.ProductoDonacionSalidaMapper;
import pe.com.sigbah.mapper.ProductoIngresoMapper;
import pe.com.sigbah.mapper.ProductosXDonacion2Mapper;
import pe.com.sigbah.mapper.ProductosXDonacionMapper;
import pe.com.sigbah.mapper.RegionDonacionMapper;
import pe.com.sigbah.mapper.RegistroCartillaInventarioMapper;
import pe.com.sigbah.mapper.RegistroCierreStockMapper;
import pe.com.sigbah.mapper.RegistroControlCalidadMapper;
import pe.com.sigbah.mapper.RegistroDonacionIngresoMapper;
import pe.com.sigbah.mapper.DonanteMapper;
import pe.com.sigbah.mapper.EstadoDonacionMapper;
import pe.com.sigbah.mapper.RegistroDonacionMapper;
import pe.com.sigbah.mapper.RegistroDonacionSalidaMapper;
import pe.com.sigbah.mapper.RegistroGuiaRemisionMapper;
import pe.com.sigbah.mapper.RegistroStockAlmacenMapper;
import pe.com.sigbah.mapper.SalidaIngresoMapper;
import pe.com.sigbah.mapper.StockAlmacenMapper;
import pe.com.sigbah.mapper.StockAlmacenProductoLoteMapper;
import pe.com.sigbah.mapper.EstadoXUsuarioMapper;
import pe.com.sigbah.mapper.EstadosXDonacionMapper;
import pe.com.sigbah.mapper.GuiaRemisionMapper;

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
			
			System.out.println("ID DONANTE : "+donacionesBean.getIdDonante());
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
			output_objParametros.put("PO_COD_DONACION", new SqlOutParameter("PO_COD_DONACION", Types.VARCHAR));
			output_objParametros.put("PO_COD_DONACION_CONCATENADO", new SqlOutParameter("PO_COD_DONACION_CONCATENADO", Types.VARCHAR));
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
			registroDonacion.setCodigoDonacion((String) out.get("PO_COD_DONACION"));
			registroDonacion.setTextoCodigo((String) out.get("PO_COD_DONACION_CONCATENADO"));
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
			output_objParametros.put("PO_COD_DONACION", new SqlOutParameter("PO_COD_DONACION", Types.VARCHAR));
			output_objParametros.put("PO_COD_DONACION_CONCATENADO", new SqlOutParameter("PO_COD_DONACION_CONCATENADO", Types.VARCHAR));
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
				
			registroDonacion.setIdDonacion(donacionesBean.getIdDdi());
			registroDonacion.setCodigoDonacion(donacionesBean.getCodigoDonacion());
			registroDonacion.setTextoCodigo(donacionesBean.getCodigoDonacion());
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
			objJdbcCall.withCatalogName(Constantes.PACKAGE_ADMINISTRACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_ESTADOS_POR_USUARIO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_USER", new SqlParameter("PI_ID_USER", Types.NUMERIC));
			output_objParametros.put("PI_NOMBRE_MODULO", new SqlParameter("PI_NOMBRE_MODULO", Types.VARCHAR));
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
			System.out.println("********");
			System.out.println(productoDonacionBean.getFecVencimiento());
			System.out.println(DateUtil.obtenerFechaHoraParseada(productoDonacionBean.getFecVencimiento()));
			
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
			output_objParametros.put("PI_FEC_VENCIMIENTO", new SqlParameter("PI_FEC_VENCIMIENTO", Types.NUMERIC));
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
	
	@Override
	public ProductoDonacionBean actualizarProductoDonacion(ProductoDonacionBean productoDonacionBean) throws Exception {
		LOGGER.info("[insertarProductoDonacion] Inicio ");
		ProductoDonacionBean registroProductoDonacion = new ProductoDonacionBean();
		try {
			System.out.println("********");
			System.out.println(productoDonacionBean.getFecVencimiento());
			System.out.println(DateUtil.obtenerFechaHoraParseada(productoDonacionBean.getFecVencimiento()));
			
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
			input_objParametros.addValue("PI_CONTROL", "U", Types.VARCHAR);			

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
			output_objParametros.put("PI_FEC_VENCIMIENTO", new SqlParameter("PI_FEC_VENCIMIENTO", Types.NUMERIC));
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
			registroProductoDonacion.setIdDetDonacion(productoDonacionBean.getIdDonacion());
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
			input_objParametros.addValue("PI_IDE_DET_DONACION", productoDonacionBean.getIdDetDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_DONACION", productoDonacionBean.getIdDonacion(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_PRODUCTODONACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DET_DONACION", new SqlParameter("PI_IDE_DET_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_DONACION", new SqlParameter("PI_FK_IDE_DONACION", Types.VARCHAR));
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
	public DocumentoDonacionBean actualizarDocumentoDonacion(DocumentoDonacionBean documentoDonacionBean) throws Exception {
		LOGGER.info("[actualizarDocumentoDonacion] Inicio ");
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
			input_objParametros.addValue("PI_CONTROL", "U", Types.VARCHAR);			

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
				LOGGER.info("[actualizarDocumentoDonacion] Ocurrio un error en la operacion del USP_INS_UPD_FILE_DONACION : "+mensajeRespuesta);
    			throw new Exception();
    		}
			registroDocumentoDonacion.setIdDocumentoDonacion(documentoDonacionBean.getIdDocumentoDonacion());
			registroDocumentoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarDocumentoDonacion] Fin ");
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
	public List<DonacionesBean> listarReporteDonacion(Integer idDonacion) throws Exception {
		LOGGER.info("[listarReporteDonacion] Inicio ");
		List<DonacionesBean> lista = new ArrayList<DonacionesBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_ID_DONACION", idDonacion, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_SOLICITUD_APROB");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_DONACION", new SqlParameter("PI_ID_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATA_GENERAL", new SqlOutParameter("PO_CURSOR_DATA_GENERAL", OracleTypes.CURSOR, new DetalleDonacionesMapper()));
			output_objParametros.put("PO_CURSOR_PRODUCTOS", new SqlOutParameter("PO_CURSOR_PRODUCTOS", OracleTypes.CURSOR, new DetalleProductoDonacionMapper()));
			output_objParametros.put("PO_CURSOR_REGIONES", new SqlOutParameter("PO_CURSOR_REGIONES", OracleTypes.CURSOR, new DetalleRegionesDonacionMapper()));
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
			
			lista = (List<DonacionesBean>) out.get("PO_CURSOR_DATA_GENERAL");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarReporteDonacion] Fin ");
		return lista;
	}
	
	@Override
	public List<ProductoDonacionBean> listarReporteDonacionProductos(Integer idDonacion) throws Exception {
		LOGGER.info("[listarReporteDonacionProductos] Inicio ");
		List<ProductoDonacionBean> lista = new ArrayList<ProductoDonacionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_ID_DONACION", idDonacion, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_SOLICITUD_APROB");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_DONACION", new SqlParameter("PI_ID_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATA_GENERAL", new SqlOutParameter("PO_CURSOR_DATA_GENERAL", OracleTypes.CURSOR, new DetalleDonacionesMapper()));
			output_objParametros.put("PO_CURSOR_PRODUCTOS", new SqlOutParameter("PO_CURSOR_PRODUCTOS", OracleTypes.CURSOR, new DetalleProductoDonacionMapper()));
			output_objParametros.put("PO_CURSOR_REGIONES", new SqlOutParameter("PO_CURSOR_REGIONES", OracleTypes.CURSOR, new DetalleRegionesDonacionMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarReporteDonacionProductos] Ocurrio un error en la operacion del USP_SEL_REPORT_SOLICITUD_APROB : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ProductoDonacionBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarReporteDonacionProductos] Fin ");
		return lista;
	}
	
	@Override
	public List<RegionDonacionBean> listarReporteDonacionRegiones(Integer idDonacion) throws Exception {
		LOGGER.info("[listarReporteDonacionRegiones] Inicio ");
		List<RegionDonacionBean> lista = new ArrayList<RegionDonacionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_ID_DONACION", idDonacion, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_SOLICITUD_APROB");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_DONACION", new SqlParameter("PI_ID_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATA_GENERAL", new SqlOutParameter("PO_CURSOR_DATA_GENERAL", OracleTypes.CURSOR, new DetalleDonacionesMapper()));
			output_objParametros.put("PO_CURSOR_PRODUCTOS", new SqlOutParameter("PO_CURSOR_PRODUCTOS", OracleTypes.CURSOR, new DetalleProductoDonacionMapper()));
			output_objParametros.put("PO_CURSOR_REGIONES", new SqlOutParameter("PO_CURSOR_REGIONES", OracleTypes.CURSOR, new DetalleRegionesDonacionMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarReporteDonacionRegiones] Ocurrio un error en la operacion del USP_SEL_REPORT_SOLICITUD_APROB : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<RegionDonacionBean>) out.get("PO_CURSOR_REGIONES");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarReporteDonacionRegiones] Fin ");
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
	public List<ItemBean> listarSalida(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarSalida] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_FK_IDE_ALMACEN", itemBean.getIcodigo(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_ORDEN_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_FK_IDE_ALMACEN", new SqlParameter("PI_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_ORDEN_SALIDA", new SqlOutParameter("PO_CURSOR_ORDEN_SALIDA", OracleTypes.CURSOR, new SalidaIngresoMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarSalida] Ocurrio un error en la operacion del USP_SEL_LISTAR_ORDEN_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ItemBean>) out.get("PO_CURSOR_ORDEN_SALIDA");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarSalida] Fin ");
		return lista;
	}
	
	@Override
	public DonacionesIngresoBean insertarRegistroDonacionIngreso(DonacionesIngresoBean donacionesIngresoBean) throws Exception {
		LOGGER.info("[insertarRegistroDonacionIngreso] Inicio ");
		DonacionesIngresoBean registroIngresoDonacion = new DonacionesIngresoBean();
		try {			
			Integer idDona=donacionesIngresoBean.getIdDonacion();
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
			input_objParametros.addValue("PI_FK_IDE_SALIDA", donacionesIngresoBean.getIdSalida(), Types.NUMERIC);
			
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
			output_objParametros.put("PI_FK_IDE_SALIDA", new SqlParameter("PI_FK_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PO_NRO_ORDEN_INGRESO", new SqlOutParameter("PO_NRO_ORDEN_INGRESO", Types.VARCHAR));
			output_objParametros.put("PO_ID_INGRESO", new SqlOutParameter("PO_ID_INGRESO", Types.NUMERIC));
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
			registroIngresoDonacion.setIdIngreso(((BigDecimal) out.get("PO_ID_INGRESO")).intValue());
			registroIngresoDonacion.setNroOrdenIngreso(((String) out.get("PO_NRO_ORDEN_INGRESO")));
			registroIngresoDonacion.setCodIngreso(((String) out.get("PO_COD_INGRESO_CONCATENADO")));
			registroIngresoDonacion.setIdDonacion(idDona);
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
	public DonacionesIngresoBean actualizarRegistroDonacionIngreso(DonacionesIngresoBean donacionesIngresoBean) throws Exception {
		LOGGER.info("[actualizarRegistroDonacionIngreso] Inicio ");
		DonacionesIngresoBean registroIngresoDonacion = new DonacionesIngresoBean();
		try {			
			Integer idDona=donacionesIngresoBean.getIdDonacion();
			Integer idIngr=donacionesIngresoBean.getIdIngreso();
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
			input_objParametros.addValue("PI_CONTROL", "U", Types.VARCHAR);	
			input_objParametros.addValue("PI_FK_IDE_SALIDA", donacionesIngresoBean.getIdSalida(), Types.NUMERIC);
			
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
			output_objParametros.put("PI_FK_IDE_SALIDA", new SqlParameter("PI_FK_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PO_NRO_ORDEN_INGRESO", new SqlOutParameter("PO_NRO_ORDEN_INGRESO", Types.VARCHAR));
			output_objParametros.put("PO_ID_INGRESO", new SqlOutParameter("PO_ID_INGRESO", Types.NUMERIC));
			output_objParametros.put("PO_COD_INGRESO_CONCATENADO", new SqlOutParameter("PO_COD_INGRESO_CONCATENADO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[actualizarRegistroDonacionIngreso] Ocurrio un error en la operacion del USP_INS_UPD_ORDEN_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroIngresoDonacion.setIdDonacion(idIngr);
			registroIngresoDonacion.setNroOrdenIngreso(((String) out.get("PO_NRO_ORDEN_INGRESO")));
			registroIngresoDonacion.setCodIngreso(((String) out.get("PO_COD_INGRESO_CONCATENADO")));
			registroIngresoDonacion.setIdDonacion(idDona);
			registroIngresoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroIngresoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarRegistroDonacionIngreso] Fin ");
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
			
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new RegistroDonacionIngresoMapper()));
			
			
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
	public List<ProductoDonacionBean> listarProductosDonacion(DonacionesIngresoBean itemBean) throws Exception {
		LOGGER.info("[listarProductosDonacion] Inicio ");
		List<ProductoDonacionBean> lista = new ArrayList<ProductoDonacionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_ALMACEN", itemBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DONACION", itemBean.getIdDonacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_MOVIMIENTO", itemBean.getIdTipoMovimiento(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_PRODUCTOS_DON1");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_MOVIMIENTO", new SqlParameter("PI_TIPO_MOVIMIENTO", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new ProductosXDonacionMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductosDonacion] Ocurrio un error en la operacion del USP_SEL_LISTAR_PRODUCTOS_DON1 : "+mensajeRespuesta);
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
			output_objParametros.put("PI_FK_IDE_INGRESO", new SqlParameter("PI_FK_IDE_INGRESO", Types.NUMERIC));
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
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);	
			input_objParametros.addValue("PI_IDE_DDI", productoDonacionIngresoBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DDI", productoDonacionIngresoBean.getCodDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_ALMACEN", productoDonacionIngresoBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ALMACEN", productoDonacionIngresoBean.getCodAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("PI_ID_DONACION", productoDonacionIngresoBean.getIdAlmacen(), Types.NUMERIC);

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
			output_objParametros.put("PI_FEC_VENCIMIENTO", new SqlParameter("PI_FEC_VENCIMIENTO", Types.DATE));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_COD_ALMACEN", new SqlParameter("PI_COD_ALMACEN", Types.VARCHAR));
			output_objParametros.put("PI_ID_DONACION", new SqlParameter("PI_ID_DONACION", Types.NUMERIC));
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
	public ProductoDonacionIngresoBean actualizarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionIngresoBean) throws Exception {
		LOGGER.info("[actualizarProductoDonacionIngreso] Inicio ");
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
			input_objParametros.addValue("PI_CONTROL", "U", Types.VARCHAR);	
			input_objParametros.addValue("PI_IDE_DDI", productoDonacionIngresoBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DDI", productoDonacionIngresoBean.getCodDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_ALMACEN", productoDonacionIngresoBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ALMACEN", productoDonacionIngresoBean.getCodAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("PI_ID_DONACION", productoDonacionIngresoBean.getIdAlmacen(), Types.NUMERIC);

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
			output_objParametros.put("PI_FEC_VENCIMIENTO", new SqlParameter("PI_FEC_VENCIMIENTO", Types.DATE));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_COD_ALMACEN", new SqlParameter("PI_COD_ALMACEN", Types.VARCHAR));
			output_objParametros.put("PI_ID_DONACION", new SqlParameter("PI_ID_DONACION", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[actualizarProductoDonacionIngreso] Ocurrio un error en la operacion del USP_INS_UPD_PRODUCTO_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			registroProductoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroProductoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarProductoDonacionIngreso] Fin ");
		return registroProductoDonacion;
	}
	
	@Override
	public ProductoDonacionIngresoBean eliminarProductoDonacionIngreso(ProductoDonacionIngresoBean productoDonacionBean) throws Exception {
		LOGGER.info("[eliminarProductoDonacionIngreso] Inicio ");
		ProductoDonacionIngresoBean registroProductoDonacion = new ProductoDonacionIngresoBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_INGRESO_DET", productoDonacionBean.getIdIngresoDet(), Types.NUMERIC);
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
			input_objParametros.addValue("PI_FEC_DOCUMENTO", DateUtil.obtenerFechaHoraParseada(documentoDonacionBean.getFechaDocumento()), Types.DATE);
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
			output_objParametros.put("PI_FEC_DOCUMENTO", new SqlParameter("PI_FEC_DOCUMENTO", Types.DATE));
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
	public DocumentoIngresoBean actualizarDocumentoDonacionIngreso(DocumentoIngresoBean documentoDonacionBean) throws Exception {
		LOGGER.info("[actualizarDocumentoDonacionIngreso] Inicio ");
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
			input_objParametros.addValue("PI_CONTROL", "U", Types.VARCHAR);	

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
				LOGGER.info("[actualizarDocumentoDonacionIngreso] Ocurrio un error en la operacion del USP_INS_UPD_FILE_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}

			registroDocumentoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarDocumentoDonacionIngreso] Fin ");
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
			input_objParametros.addValue("PI_USERNAME", documentoDonacionBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_INGRESO_FILE");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DOCUMENTO", new SqlParameter("PI_IDE_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_INGRESO", new SqlParameter("PI_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
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
	
	@Override
	public List<DonacionesIngresoBean> listarReporteDonacionIngreso(Integer idIngreso) throws Exception {
		LOGGER.info("[listarReporteDonacionIngreso] Inicio ");
		List<DonacionesIngresoBean> lista = new ArrayList<DonacionesIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_INGRESO", idIngreso, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_ORDEN_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_INGRESO", new SqlParameter("pi_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATA_GENERAL", new SqlOutParameter("PO_CURSOR_DATA_GENERAL", OracleTypes.CURSOR, new DetalleDonacionesIngresoMapper()));
			output_objParametros.put("PO_CURSOR_PRODUCTOS", new SqlOutParameter("PO_CURSOR_PRODUCTOS", OracleTypes.CURSOR, new DetalleProductoDonacionIngresoMapper()));
			output_objParametros.put("PO_CURSOR_DOCUMENTOS", new SqlOutParameter("PO_CURSOR_DOCUMENTOS", OracleTypes.CURSOR, new DetalleDocumentoDonacionIngresoMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarReporteDonacionIngreso] Ocurrio un error en la operacion del USP_SEL_REPORT_ORDEN_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<DonacionesIngresoBean>) out.get("PO_CURSOR_DATA_GENERAL");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarReporteDonacionIngreso] Fin ");
		return lista;
	}
	
	@Override
	public List<ProductoDonacionIngresoBean> listarProductosReporteDonacionIngreso(Integer idIngreso) throws Exception {
		LOGGER.info("[listarProductosReporteDonacionIngreso] Inicio ");
		List<ProductoDonacionIngresoBean> lista = new ArrayList<ProductoDonacionIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_INGRESO", idIngreso, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_ORDEN_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_INGRESO", new SqlParameter("pi_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATA_GENERAL", new SqlOutParameter("PO_CURSOR_DATA_GENERAL", OracleTypes.CURSOR, new DetalleDonacionesIngresoMapper()));
			output_objParametros.put("PO_CURSOR_PRODUCTOS", new SqlOutParameter("PO_CURSOR_PRODUCTOS", OracleTypes.CURSOR, new DetalleProductoDonacionIngresoMapper()));
			output_objParametros.put("PO_CURSOR_DOCUMENTOS", new SqlOutParameter("PO_CURSOR_DOCUMENTOS", OracleTypes.CURSOR, new DetalleDocumentoDonacionIngresoMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductosReporteDonacionIngreso] Ocurrio un error en la operacion del USP_SEL_REPORT_ORDEN_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ProductoDonacionIngresoBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProductosReporteDonacionIngreso] Fin ");
		return lista;
	}
	
	@Override
	public List<DocumentoDonacionIngresoBean> listarDocumentosReporteDonacionIngreso(Integer idIngreso) throws Exception {
		LOGGER.info("[listarDocumentosReporteDonacionIngreso] Inicio ");
		List<DocumentoDonacionIngresoBean> lista = new ArrayList<DocumentoDonacionIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_INGRESO", idIngreso, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_ORDEN_INGRESO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_INGRESO", new SqlParameter("pi_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATA_GENERAL", new SqlOutParameter("PO_CURSOR_DATA_GENERAL", OracleTypes.CURSOR, new DetalleDonacionesIngresoMapper()));
			output_objParametros.put("PO_CURSOR_PRODUCTOS", new SqlOutParameter("PO_CURSOR_PRODUCTOS", OracleTypes.CURSOR, new DetalleProductoDonacionIngresoMapper()));
			output_objParametros.put("PO_CURSOR_DOCUMENTOS", new SqlOutParameter("PO_CURSOR_DOCUMENTOS", OracleTypes.CURSOR, new DetalleDocumentoDonacionIngresoMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarDocumentosReporteDonacionIngreso] Ocurrio un error en la operacion del USP_SEL_REPORT_ORDEN_INGRESO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<DocumentoDonacionIngresoBean>) out.get("PO_CURSOR_DOCUMENTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarDocumentosReporteDonacionIngreso] Fin ");
		return lista;
	}
	
	@Override
	public List<DonacionesIngresoBean> listarReporteDonacionIngresoNacional(Integer idIngreso, Integer idDdi) throws Exception {
		LOGGER.info("[listarReporteDonacionIngresoNacional] Inicio ");
		List<DonacionesIngresoBean> lista = new ArrayList<DonacionesIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_INGRESO", idIngreso, Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DDI", idDdi, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_ACTAENTREGA_NAC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_INGRESO", new SqlParameter("PI_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATA_GENERAL", new SqlOutParameter("PO_CURSOR_DATA_GENERAL", OracleTypes.CURSOR, new DetalleDonacionesIngresoNacionalMapper()));
			output_objParametros.put("PO_CURSOR_PRODUCTOS", new SqlOutParameter("PO_CURSOR_PRODUCTOS", OracleTypes.CURSOR, new DetalleProductoDonacionIngresoNacionalMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarReporteDonacionIngresoNacional] Ocurrio un error en la operacion del USP_SEL_REPORT_ACTAENTREGA_NAC : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<DonacionesIngresoBean>) out.get("PO_CURSOR_DATA_GENERAL");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarReporteDonacionIngresoNacional] Fin ");
		return lista;
	}
	
	@Override
	public List<ProductoDonacionIngresoBean> listarProductoReporteDonacionIngresoNacional(Integer idIngreso, Integer idDdi) throws Exception {
		LOGGER.info("[listarProductoReporteDonacionIngresoNacional] Inicio ");
		List<ProductoDonacionIngresoBean> lista = new ArrayList<ProductoDonacionIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_INGRESO", idIngreso, Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DDI", idDdi, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_ACTAENTREGA_NAC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_INGRESO", new SqlParameter("PI_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATA_GENERAL", new SqlOutParameter("PO_CURSOR_DATA_GENERAL", OracleTypes.CURSOR, new DetalleDonacionesIngresoNacionalMapper()));
			output_objParametros.put("PO_CURSOR_PRODUCTOS", new SqlOutParameter("PO_CURSOR_PRODUCTOS", OracleTypes.CURSOR, new DetalleProductoDonacionIngresoNacionalMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductoReporteDonacionIngresoNacional] Ocurrio un error en la operacion del USP_SEL_REPORT_ACTAENTREGA_NAC : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ProductoDonacionIngresoBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProductoReporteDonacionIngresoNacional] Fin ");
		return lista;
	}
	
	@Override
	public List<DonacionesIngresoBean> listarReporteDonacionIngresoInternacional(Integer idIngreso, Integer idDdi) throws Exception {
		LOGGER.info("[listarReporteDonacionIngresoInternacional] Inicio ");
		List<DonacionesIngresoBean> lista = new ArrayList<DonacionesIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_INGRESO", idIngreso, Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DDI", idDdi, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_ACTAENTREGA_INT");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_INGRESO", new SqlParameter("PI_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATA_GENERAL", new SqlOutParameter("PO_CURSOR_DATA_GENERAL", OracleTypes.CURSOR, new DetalleDonacionesIngresoInterNacionalMapper()));
			output_objParametros.put("PO_CURSOR_PRODUCTOS", new SqlOutParameter("PO_CURSOR_PRODUCTOS", OracleTypes.CURSOR, new DetalleProductoDonacionIngresoInterNacionalMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarReporteDonacionIngresoInternacional] Ocurrio un error en la operacion del USP_SEL_REPORT_ACTAENTREGA_INT : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<DonacionesIngresoBean>) out.get("PO_CURSOR_DATA_GENERAL");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarReporteDonacionIngresoInternacional] Fin ");
		return lista;
	}
	
	@Override
	public List<ProductoDonacionIngresoBean> listarProductoReporteDonacionIngresoInternacional(Integer idIngreso, Integer idDdi) throws Exception {
		LOGGER.info("[listarProductoReporteDonacionIngresoInternacional] Inicio ");
		List<ProductoDonacionIngresoBean> lista = new ArrayList<ProductoDonacionIngresoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_INGRESO", idIngreso, Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DDI", idDdi, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_ACTAENTREGA_INT");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_INGRESO", new SqlParameter("PI_IDE_INGRESO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DATA_GENERAL", new SqlOutParameter("PO_CURSOR_DATA_GENERAL", OracleTypes.CURSOR, new DetalleDonacionesIngresoInterNacionalMapper()));
			output_objParametros.put("PO_CURSOR_PRODUCTOS", new SqlOutParameter("PO_CURSOR_PRODUCTOS", OracleTypes.CURSOR, new DetalleProductoDonacionIngresoInterNacionalMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductoReporteDonacionIngresoInternacional] Ocurrio un error en la operacion del USP_SEL_REPORT_ACTAENTREGA_INT : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ProductoDonacionIngresoBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProductoReporteDonacionIngresoInternacional] Fin ");
		return lista;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////
	///////ORDENES DE SALIDA////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	
	@Override
 	public List<DonacionesSalidaBean> listarSalidaDonaciones(DonacionesSalidaBean donacionesSalidaBean) throws Exception {
		LOGGER.info("[listarSalidaDonaciones] Inicio ");
		List<DonacionesSalidaBean> lista = new ArrayList<DonacionesSalidaBean>();
		try {

			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_COD_ANIO", donacionesSalidaBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", donacionesSalidaBean.getCodigoMes(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_ALMACEN", donacionesSalidaBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_MOVIMIENTO", donacionesSalidaBean.getIdMovimiento(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", "D", Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_ORDENES_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_IDE_MOVIMIENTO", new SqlParameter("PI_IDE_MOVIMIENTO", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new DonacionesSalidaMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				throw new Exception();
			} else {
				lista = (List<DonacionesSalidaBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarSalidaDonaciones] Fin ");
		return lista;
	}
	
	@Override
	public DonacionesSalidaBean obtenerCorrelativoOrdenSalida(DonacionesSalidaBean donacionesIngresoBean) throws Exception {
		LOGGER.info("[obtenerCorrelativoOrdenSalida] Inicio ");
		DonacionesSalidaBean detalleUsuarioBean = new DonacionesSalidaBean();
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
			detalleUsuarioBean.setCodSalida((String) out.get("PO_COD_SALIDA"));
			detalleUsuarioBean.setCodigoRespuesta(codigoRespuesta);
			detalleUsuarioBean.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerCorrelativoOrdenSalida] Fin ");
		return detalleUsuarioBean;
	}
	
	@Override
	public DonacionesSalidaBean insertarRegistroDonacionSalida(DonacionesSalidaBean donacionesSalidaBean) throws Exception {
		LOGGER.info("[insertarRegistroDonacionSalida] Inicio ");
		DonacionesSalidaBean registroIngresoDonacion = new DonacionesSalidaBean();
		try {			
			Integer idDona=donacionesSalidaBean.getIdDonacion();
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_SALIDA", donacionesSalidaBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", "D", Types.VARCHAR);
			input_objParametros.addValue("PI_COD_ANIO", donacionesSalidaBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_ALMACEN", donacionesSalidaBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DDI", donacionesSalidaBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_ALMACEN", donacionesSalidaBean.getCodAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("PI_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(donacionesSalidaBean.getFechaEmision()), Types.DATE);
			input_objParametros.addValue("PI_COD_UBIGEO", donacionesSalidaBean.getCodigoUbigeo(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_PROGRAMACION", donacionesSalidaBean.getIdProgramacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_ID_RESPONSABLE", donacionesSalidaBean.getIdResponsable(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_RESPONSABLE_EXT", donacionesSalidaBean.getIdResponsableExt(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_ID_SOLICITANTE", donacionesSalidaBean.getIdSolicitante(), Types.NUMERIC);		
			input_objParametros.addValue("PI_FK_ID_RESPONSABLE_RECEPCION", donacionesSalidaBean.getIdResponsableRecepcion(), Types.NUMERIC);		
			input_objParametros.addValue("PI_FK_IDE_PROYECTO_MANIF", donacionesSalidaBean.getIdProyectoManifiesto(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_TIP_MOVIMIENTO", donacionesSalidaBean.getIdTipoMovimiento(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_ALM_DESTINO", donacionesSalidaBean.getIdAlmacenDestino(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_ALM_DESTINO_EXT", donacionesSalidaBean.getIdAlmacenDestinoExt(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_MED_TRANSPORTE", donacionesSalidaBean.getIdMedTransporte(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_EMP_TRANSPORTE", donacionesSalidaBean.getIdEmpresaTrans(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_CHOFER", donacionesSalidaBean.getIdChofer(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_PLACA", donacionesSalidaBean.getNroPlaca(), Types.VARCHAR);
			input_objParametros.addValue("PI_FEC_ENTREGA",  DateUtil.obtenerFechaHoraParseada(donacionesSalidaBean.getFechaEntrega()), Types.DATE);
			input_objParametros.addValue("PI_FLG_TIP_DESTINO", donacionesSalidaBean.getFlagTipoDestino(), Types.VARCHAR);
			input_objParametros.addValue("PI_OBSERVACION", donacionesSalidaBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_ESTADO", donacionesSalidaBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", donacionesSalidaBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_ORDEN_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_ALMACEN", new SqlParameter("PI_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_COD_ALMACEN", new SqlParameter("PI_COD_ALMACEN", Types.VARCHAR));
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
			output_objParametros.put("PO_NRO_ORDEN_SALIDA", new SqlOutParameter("PO_NRO_ORDEN_SALIDA", Types.VARCHAR));
			output_objParametros.put("PO_COD_SALIDA_CONCATENADO", new SqlOutParameter("PO_COD_SALIDA_CONCATENADO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarRegistroDonacionSalida] Ocurrio un error en la operacion del USP_INS_UPD_ORDEN_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
			registroIngresoDonacion.setIdSalida(((BigDecimal) out.get("PO_SEQ_BAH_M_SALIDA")).intValue());
			registroIngresoDonacion.setNroOrdenSalida(((String) out.get("PO_NRO_ORDEN_SALIDA")));
			registroIngresoDonacion.setCodSalida(((String) out.get("PO_COD_SALIDA_CONCATENADO")));
			registroIngresoDonacion.setIdDonacion(idDona);
			registroIngresoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroIngresoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarRegistroDonacionSalida] Fin ");
		return registroIngresoDonacion;
	}
	
	@Override
	public DonacionesSalidaBean actualizarRegistroDonacionSalida(DonacionesSalidaBean donacionesSalidaBean) throws Exception {
		LOGGER.info("[insertarRegistroDonacionSalida] Inicio ");
		DonacionesSalidaBean registroIngresoDonacion = new DonacionesSalidaBean();
		try {			
			Integer idDona=donacionesSalidaBean.getIdDonacion();
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_SALIDA", donacionesSalidaBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", "D", Types.VARCHAR);
			input_objParametros.addValue("PI_COD_ANIO", donacionesSalidaBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_ALMACEN", donacionesSalidaBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_DDI", donacionesSalidaBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_ALMACEN", donacionesSalidaBean.getCodAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("PI_FEC_EMISION", DateUtil.obtenerFechaHoraParseada(donacionesSalidaBean.getFechaEmision()), Types.DATE);
			input_objParametros.addValue("PI_COD_UBIGEO", donacionesSalidaBean.getCodigoUbigeo(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_PROGRAMACION", donacionesSalidaBean.getIdProgramacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_ID_RESPONSABLE", donacionesSalidaBean.getIdResponsable(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_RESPONSABLE_EXT", donacionesSalidaBean.getIdResponsableExt(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_ID_SOLICITANTE", donacionesSalidaBean.getIdSolicitante(), Types.NUMERIC);		
			input_objParametros.addValue("PI_FK_ID_RESPONSABLE_RECEPCION", donacionesSalidaBean.getIdResponsableRecepcion(), Types.NUMERIC);		
			input_objParametros.addValue("PI_FK_IDE_PROYECTO_MANIF", donacionesSalidaBean.getIdProyectoManifiesto(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_TIP_MOVIMIENTO", donacionesSalidaBean.getIdTipoMovimiento(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_ALM_DESTINO", donacionesSalidaBean.getIdAlmacenDestino(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_ALM_DESTINO_EXT", donacionesSalidaBean.getIdAlmacenDestinoExt(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_MED_TRANSPORTE", donacionesSalidaBean.getIdMedTransporte(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_EMP_TRANSPORTE", donacionesSalidaBean.getIdEmpresaTrans(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_CHOFER", donacionesSalidaBean.getIdChofer(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_PLACA", donacionesSalidaBean.getNroPlaca(), Types.VARCHAR);
			input_objParametros.addValue("PI_FEC_ENTREGA",  DateUtil.obtenerFechaHoraParseada(donacionesSalidaBean.getFechaEntrega()), Types.DATE);
			input_objParametros.addValue("PI_FLG_TIP_DESTINO", donacionesSalidaBean.getFlagTipoDestino(), Types.VARCHAR);
			input_objParametros.addValue("PI_OBSERVACION", donacionesSalidaBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_FK_IDE_ESTADO", donacionesSalidaBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("PI_USERNAME", donacionesSalidaBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "U", Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_ORDEN_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_ALMACEN", new SqlParameter("PI_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_COD_DDI", new SqlParameter("PI_COD_DDI", Types.VARCHAR));
			output_objParametros.put("PI_COD_ALMACEN", new SqlParameter("PI_COD_ALMACEN", Types.VARCHAR));
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
			output_objParametros.put("PO_NRO_ORDEN_SALIDA", new SqlOutParameter("PO_NRO_ORDEN_SALIDA", Types.VARCHAR));
			output_objParametros.put("PO_COD_SALIDA_CONCATENADO", new SqlOutParameter("PO_COD_SALIDA_CONCATENADO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarRegistroDonacionSalida] Ocurrio un error en la operacion del USP_INS_UPD_ORDEN_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
			registroIngresoDonacion.setIdSalida(donacionesSalidaBean.getIdSalida());
			registroIngresoDonacion.setNroOrdenSalida(((String) out.get("PO_NRO_ORDEN_SALIDA")));
			registroIngresoDonacion.setCodSalida(((String) out.get("PO_COD_SALIDA_CONCATENADO")));
			registroIngresoDonacion.setIdDonacion(idDona);
			registroIngresoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroIngresoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarRegistroDonacionSalida] Fin ");
		return registroIngresoDonacion;
	}
	
	@Override
	public DonacionesSalidaBean obtenerDonacionSalidaXIdSalida(Integer idSalida) throws Exception {
		LOGGER.info("[obtenerDonacionSalidaXIdSalida] Inicio ");
		DonacionesSalidaBean donaciones = new DonacionesSalidaBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_SALIDA", idSalida, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_EDITAR_ORDEN_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new RegistroDonacionSalidaMapper()));
			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerDonacionSalidaXIdSalida] Ocurrio un error en la operacion del USP_SEL_EDITAR_ORDEN_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<DonacionesSalidaBean> lista = (List<DonacionesSalidaBean>) out.get("PO_LR_RECORDSET");
			if (!Utils.isEmpty(lista)) {
				donaciones = lista.get(0);
			}
			
			donaciones.setCodigoRespuesta(codigoRespuesta);
			donaciones.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerDonacionSalidaXIdSalida] Fin ");
		return donaciones;
	}
	
	
	@Override
	public List<ProductoDonacionSalidaBean> listarProductoDonacionSalida(ProductoDonacionSalidaBean productoDonacionSalidaBean) throws Exception {
		LOGGER.info("[listarProductoDonacionSalida] Inicio ");
		List<ProductoDonacionSalidaBean> lista = new ArrayList<ProductoDonacionSalidaBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_FK_IDE_SALIDA", productoDonacionSalidaBean.getIdSalida(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_PROD_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_FK_IDE_SALIDA", new SqlParameter("PI_FK_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new ProductoDonacionSalidaMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductoDonacionSalida] Ocurrio un error en la operacion del USP_SEL_LISTAR_PROD_SALIDA : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<ProductoDonacionSalidaBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}
		LOGGER.info("[listarProductoDonacionSalida] Fin ");
		return lista;
	}
	
	@Override
	public ProductoDonacionSalidaBean insertarProductoDonacionSalida(ProductoDonacionSalidaBean productoDonacionSalidaBean) throws Exception {
		LOGGER.info("[insertarProductoDonacionSalida] Inicio ");
		ProductoDonacionSalidaBean registroProductoDonacion = new ProductoDonacionSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_ID_SALIDA_DET", productoDonacionSalidaBean.getIdSalidaDet(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_SALIDA", productoDonacionSalidaBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_PRODUCTO", productoDonacionSalidaBean.getIdProducto(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_LOTE", "01", Types.VARCHAR);
			input_objParametros.addValue("PI_CANTIDAD", productoDonacionSalidaBean.getCantidad(), Types.NUMERIC);
			input_objParametros.addValue("PI_PREC_UNITARIO",  productoDonacionSalidaBean.getPrecioUnitario(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_PROGRAMACION", productoDonacionSalidaBean.getIdProgramacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DONACION", productoDonacionSalidaBean.getIdDonacion(), Types.NUMERIC);	
			input_objParametros.addValue("PI_USERNAME", productoDonacionSalidaBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_FLG_ACTIVO", "1", Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_PRODUCTO_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_SALIDA_DET", new SqlParameter("PI_ID_SALIDA_DET", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_SALIDA", new SqlParameter("PI_FK_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PRODUCTO", new SqlParameter("PI_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("PI_NRO_LOTE", new SqlParameter("PI_NRO_LOTE", Types.VARCHAR));
			output_objParametros.put("PI_CANTIDAD", new SqlParameter("PI_CANTIDAD", Types.NUMERIC));
			output_objParametros.put("PI_PREC_UNITARIO", new SqlParameter("PI_PREC_UNITARIO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_PROGRAMACION", new SqlParameter("PI_IDE_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_FLG_ACTIVO", new SqlParameter("PI_FLG_ACTIVO", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[insertarProductoDonacionSalida] Ocurrio un error en la operacion del USP_INS_UPD_PRODUCTO_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
			registroProductoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroProductoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarProductoDonacionSalida] Fin ");
		return registroProductoDonacion;
	}
	
	@Override
	public ProductoDonacionSalidaBean actualizarProductoDonacionSalida(ProductoDonacionSalidaBean productoDonacionSalidaBean) throws Exception {
		LOGGER.info("[actualizarProductoDonacionSalida] Inicio ");
		ProductoDonacionSalidaBean registroProductoDonacion = new ProductoDonacionSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_ID_SALIDA_DET", productoDonacionSalidaBean.getIdSalidaDet(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_SALIDA", productoDonacionSalidaBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_FK_IDE_PRODUCTO", productoDonacionSalidaBean.getIdProducto(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_LOTE", "01", Types.VARCHAR);
			input_objParametros.addValue("PI_CANTIDAD", productoDonacionSalidaBean.getCantidad(), Types.NUMERIC);
			input_objParametros.addValue("PI_PREC_UNITARIO",  productoDonacionSalidaBean.getPrecioUnitario(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_PROGRAMACION", productoDonacionSalidaBean.getIdProgramacion(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_DONACION", productoDonacionSalidaBean.getIdDonacion(), Types.NUMERIC);	
			input_objParametros.addValue("PI_USERNAME", productoDonacionSalidaBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_FLG_ACTIVO", "1", Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "U", Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_PRODUCTO_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_SALIDA_DET", new SqlParameter("PI_ID_SALIDA_DET", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_SALIDA", new SqlParameter("PI_FK_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PI_FK_IDE_PRODUCTO", new SqlParameter("PI_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("PI_NRO_LOTE", new SqlParameter("PI_NRO_LOTE", Types.VARCHAR));
			output_objParametros.put("PI_CANTIDAD", new SqlParameter("PI_CANTIDAD", Types.NUMERIC));
			output_objParametros.put("PI_PREC_UNITARIO", new SqlParameter("PI_PREC_UNITARIO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_PROGRAMACION", new SqlParameter("PI_IDE_PROGRAMACION", Types.NUMERIC));
			output_objParametros.put("PI_IDE_DONACION", new SqlParameter("PI_IDE_DONACION", Types.NUMERIC));
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_FLG_ACTIVO", new SqlParameter("PI_FLG_ACTIVO", Types.VARCHAR));
			output_objParametros.put("PI_CONTROL", new SqlParameter("PI_CONTROL", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[actualizarProductoDonacionSalida] Ocurrio un error en la operacion del USP_INS_UPD_PRODUCTO_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
			registroProductoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroProductoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarProductoDonacionSalida] Fin ");
		return registroProductoDonacion;
	}

	@Override
	public ProductoDonacionSalidaBean eliminarProductoDonacionSalida(ProductoDonacionSalidaBean productoDonacionSalidaBean) throws Exception {
		LOGGER.info("[eliminarProductoDonacionSalida] Inicio ");
		ProductoDonacionSalidaBean registroProductoDonacion = new ProductoDonacionSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_ID_SALIDA_DET", productoDonacionSalidaBean.getIdSalidaDet(), Types.NUMERIC);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_SALIDA_PRODUCTO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_SALIDA_DET", new SqlParameter("PI_ID_SALIDA_DET", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarProductoDonacionSalida] Ocurrio un error en la operacion del USP_DEL_SALIDA_PRODUCTO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroProductoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarProductoDonacionSalida] Fin ");
		return registroProductoDonacion;
	}
	
	@Override
	public List<ProductoDonacionSalidaBean> listarProductosDonacionSalida(ProductoDonacionSalidaBean itemBean) throws Exception {
		LOGGER.info("[listarProductosDonacionSalida] Inicio ");
		List<ProductoDonacionSalidaBean> lista = new ArrayList<ProductoDonacionSalidaBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_ALMACEN", itemBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_CATEGORIA_PRODUCTO", itemBean.getIdCategoria(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_PRODUCTOS_DON2");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PI_IDE_CATEGORIA_PRODUCTO", new SqlParameter("PI_IDE_CATEGORIA_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new ProductosXDonacion2Mapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductosDonacionSalida] Ocurrio un error en la operacion del USP_SEL_LISTAR_PRODUCTOS_DON2 : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ProductoDonacionSalidaBean>) out.get("PO_CURSOR");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProductosDonacionSalida] Fin ");
		return lista;
	}
	
	@Override
	public List<DocumentoSalidaBean> listarDocumentoDonacionSalida(DocumentoSalidaBean documentoDonacionBean) throws Exception {
		LOGGER.info("[listarDocumentoDonacionSalida] Inicio ");
		List<DocumentoSalidaBean> lista = new ArrayList<DocumentoSalidaBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_SALIDA", documentoDonacionBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO_ORIGEN", "D", Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_LISTAR_DOC_SALIDAS");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PI_TIPO_ORIGEN", new SqlParameter("PI_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new DocumentoDonacionSalidaMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarDocumentoDonacionSalida] Ocurrio un error en la operacion del USP_SEL_LISTAR_DOC_SALIDAS : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<DocumentoSalidaBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}
		LOGGER.info("[listarDocumentoDonacionSalida] Fin ");
		return lista;
	}
	
	@Override
	public DocumentoSalidaBean insertarDocumentoDonacionSalida(DocumentoSalidaBean documentoDonacionBean) throws Exception {
		LOGGER.info("[insertarDocumentoDonacionSalida] Inicio ");
		DocumentoSalidaBean registroDocumentoDonacion = new DocumentoSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DOCUMENTO", documentoDonacionBean.getIdDocumentoSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_SALIDA", documentoDonacionBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_DOCUMENTO", documentoDonacionBean.getNroDocumento(), Types.VARCHAR);
			input_objParametros.addValue("PI_FEC_DOCUMENTO", DateUtil.obtenerFechaHoraParseada(documentoDonacionBean.getFechaDocumento()), Types.DATE);
			input_objParametros.addValue("PI_IDE_TIP_DOCUMENTO", documentoDonacionBean.getIdTipoDocumento(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ALFRESCO", documentoDonacionBean.getCodigoArchivoAlfresco(), Types.VARCHAR);
			input_objParametros.addValue("PI_NOM_ARCHIVO",  documentoDonacionBean.getNombreArchivo(), Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", documentoDonacionBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "I", Types.VARCHAR);	

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_FILE_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DOCUMENTO", new SqlParameter("PI_IDE_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PI_NRO_DOCUMENTO", new SqlParameter("PI_NRO_DOCUMENTO", Types.VARCHAR));
			output_objParametros.put("PI_FEC_DOCUMENTO", new SqlParameter("PI_FEC_DOCUMENTO", Types.DATE));
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
				LOGGER.info("[insertarDocumentoDonacionSalida] Ocurrio un error en la operacion del USP_INS_UPD_FILE_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}

			registroDocumentoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[insertarDocumentoDonacionSalida] Fin ");
		return registroDocumentoDonacion;
	}
	
	@Override
	public DocumentoSalidaBean actualizarDocumentoDonacionSalida(DocumentoSalidaBean documentoDonacionBean) throws Exception {
		LOGGER.info("[actualizarDocumentoDonacionSalida] Inicio ");
		DocumentoSalidaBean registroDocumentoDonacion = new DocumentoSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DOCUMENTO", documentoDonacionBean.getIdDocumentoSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_SALIDA", documentoDonacionBean.getIdSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_NRO_DOCUMENTO", documentoDonacionBean.getNroDocumento(), Types.VARCHAR);
			input_objParametros.addValue("PI_FEC_DOCUMENTO", DateUtil.obtenerFechaHoraParseada(documentoDonacionBean.getFechaDocumento()), Types.DATE);
			input_objParametros.addValue("PI_IDE_TIP_DOCUMENTO", documentoDonacionBean.getIdTipoDocumento(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ALFRESCO", documentoDonacionBean.getCodigoArchivoAlfresco(), Types.VARCHAR);
			input_objParametros.addValue("PI_NOM_ARCHIVO",  documentoDonacionBean.getNombreArchivo(), Types.VARCHAR);
			input_objParametros.addValue("PI_USERNAME", documentoDonacionBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("PI_CONTROL", "U", Types.VARCHAR);	

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_FILE_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DOCUMENTO", new SqlParameter("PI_IDE_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PI_NRO_DOCUMENTO", new SqlParameter("PI_NRO_DOCUMENTO", Types.VARCHAR));
			output_objParametros.put("PI_FEC_DOCUMENTO", new SqlParameter("PI_FEC_DOCUMENTO", Types.DATE));
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
				LOGGER.info("[actualizarDocumentoDonacionSalida] Ocurrio un error en la operacion del USP_INS_UPD_FILE_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}

			registroDocumentoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarDocumentoDonacionSalida] Fin ");
		return registroDocumentoDonacion;
	}
	
	@Override
	public DocumentoSalidaBean eliminarDocumentoSalidaDonacion(DocumentoSalidaBean documentoDonacionBean) throws Exception {
		LOGGER.info("[eliminarDocumentoSalidaDonacion] Inicio ");
		DocumentoSalidaBean registroDocumentoDonacion = new DocumentoSalidaBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DOCUMENTO", documentoDonacionBean.getIdDocumentoSalida(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_SALIDA", documentoDonacionBean.getIdSalida(), Types.NUMERIC);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_SALIDA_FILE");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DOCUMENTO", new SqlParameter("PI_IDE_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarDocumentoSalidaDonacion] Ocurrio un error en la operacion del USP_DEL_SALIDA_FILE : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroDocumentoDonacion.setCodigoRespuesta(codigoRespuesta);
			registroDocumentoDonacion.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarDocumentoSalidaDonacion] Fin ");
		return registroDocumentoDonacion;
	}
	
	@Override
	public List<DonacionesSalidaBean> listarReporteDonacionSalida(Integer idSalida) throws Exception {
		LOGGER.info("[listarReporteDonacionSalida] Inicio ");
		List<DonacionesSalidaBean> lista = new ArrayList<DonacionesSalidaBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_SALIDA", idSalida, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_ORDEN_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DG", new SqlOutParameter("PO_CURSOR_DG", OracleTypes.CURSOR, new DetalleDonacionesSalidaMapper()));
			output_objParametros.put("PO_CURSOR_FILE", new SqlOutParameter("PO_CURSOR_FILE", OracleTypes.CURSOR, new DetalleDocumentoDonacionSalidaMapper()));
			output_objParametros.put("PO_CURSOR_PROD", new SqlOutParameter("PO_CURSOR_PROD", OracleTypes.CURSOR, new DetalleProductoDonacionSalidaMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarReporteDonacionSalida] Ocurrio un error en la operacion del USP_SEL_REPORT_ORDEN_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<DonacionesSalidaBean>) out.get("PO_CURSOR_DG");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarReporteDonacionSalida] Fin ");
		return lista;
	}
	
	@Override
	public List<ProductoDonacionSalidaBean> listarProductosReporteDonacionSalida(Integer idSalida) throws Exception {
		LOGGER.info("[listarProductosReporteDonacionSalida] Inicio ");
		List<ProductoDonacionSalidaBean> lista = new ArrayList<ProductoDonacionSalidaBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_SALIDA", idSalida, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_ORDEN_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DG", new SqlOutParameter("PO_CURSOR_DG", OracleTypes.CURSOR, new DetalleDonacionesSalidaMapper()));
			output_objParametros.put("PO_CURSOR_FILE", new SqlOutParameter("PO_CURSOR_FILE", OracleTypes.CURSOR, new DetalleDocumentoDonacionSalidaMapper()));
			output_objParametros.put("PO_CURSOR_PROD", new SqlOutParameter("PO_CURSOR_PROD", OracleTypes.CURSOR, new DetalleProductoDonacionSalidaMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductosReporteDonacionSalida] Ocurrio un error en la operacion del USP_SEL_REPORT_ORDEN_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ProductoDonacionSalidaBean>) out.get("PO_CURSOR_PROD");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProductosReporteDonacionSalida] Fin ");
		return lista;
	}
	
	@Override
	public List<DocumentoSalidaBean> listarDocumentosReporteDonacionSalida(Integer idSalida) throws Exception {
		LOGGER.info("[listarDocumentosReporteDonacionSalida] Inicio ");
		List<DocumentoSalidaBean> lista = new ArrayList<DocumentoSalidaBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_SALIDA", idSalida, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_REPORT_ORDEN_SALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_SALIDA", new SqlParameter("PI_IDE_SALIDA", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR_DG", new SqlOutParameter("PO_CURSOR_DG", OracleTypes.CURSOR, new DetalleDonacionesSalidaMapper()));
			output_objParametros.put("PO_CURSOR_FILE", new SqlOutParameter("PO_CURSOR_FILE", OracleTypes.CURSOR, new DetalleDocumentoDonacionSalidaMapper()));
			output_objParametros.put("PO_CURSOR_PROD", new SqlOutParameter("PO_CURSOR_PROD", OracleTypes.CURSOR, new DetalleProductoDonacionSalidaMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarDocumentosReporteDonacionSalida] Ocurrio un error en la operacion del USP_SEL_REPORT_ORDEN_SALIDA : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<DocumentoSalidaBean>) out.get("PO_CURSOR_FILE");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_PRODUCTOS");
//			lista = (List<ItemBean>) out.get("PO_CURSOR_REGIONES");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProductosReporteDonacionSalida] Fin ");
		return lista;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////GUIA DE REMISION////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<GuiaRemisionBean> listarGuiaRemision(GuiaRemisionBean guiaRemisionBean) throws Exception {
		LOGGER.info("[listarGuiaRemision] Inicio ");
		List<GuiaRemisionBean> lista = new ArrayList<GuiaRemisionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_COD_ANIO", guiaRemisionBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_MES", guiaRemisionBean.getCodigoMes(), Types.VARCHAR);		
			input_objParametros.addValue("pi_IDE_ALMACEN", guiaRemisionBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_IDE_TIP_MOVIMIENTO", guiaRemisionBean.getIdMovimiento(), Types.NUMERIC);			
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_GUIA_REMISION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_COD_MES", new SqlParameter("pi_COD_MES", Types.VARCHAR));
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_IDE_TIP_MOVIMIENTO", new SqlParameter("pi_IDE_TIP_MOVIMIENTO", Types.NUMERIC));
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
			input_objParametros.addValue("PI_fk_ide_motivo_traslado", guiaRemisionBean.getIdMotivoTraslado(), Types.NUMERIC);
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
			output_objParametros.put("PI_fk_ide_motivo_traslado", new SqlParameter("PI_fk_ide_motivo_traslado", Types.NUMERIC));
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
	
	@Override
	public List<DetalleGuiaRemisionBean> listarDetalleGuiaRemision(Integer idGuiaRemision, String tipoOrigen) throws Exception {
		LOGGER.info("[listarDetalleGuiaRemision] Inicio ");
		List<DetalleGuiaRemisionBean> lista = new ArrayList<DetalleGuiaRemisionBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_TIPO_ORIGEN", tipoOrigen, Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_GUIA_REMISION", idGuiaRemision, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_REPORT_GUIA_REMISION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_TIPO_ORIGEN", new SqlParameter("pi_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("pi_IDE_GUIA_REMISION", new SqlParameter("pi_IDE_GUIA_REMISION", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new DetalleGuiaRemisionMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarDetalleGuiaRemision] Ocurrio un error en la operacion del USP_REP_GUIA_DE_REMISION : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<DetalleGuiaRemisionBean>) out.get("po_Lr_Recordset");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarDetalleGuiaRemision] Fin ");
		return lista;
	}

	
	///////////////////////////////////////////////////////////////////////
	//////////////Stock Donacion//////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	@Override
	public List<StockAlmacenBean> listarStockAlmacen(StockAlmacenBean stockAlmacenBean) throws Exception {
		LOGGER.info("[listarStockAlmacen] Inicio ");
		List<StockAlmacenBean> lista = new ArrayList<StockAlmacenBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_TIPO_ORIGEN", stockAlmacenBean.getTipoOrigen(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", stockAlmacenBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_CATEGORIA_BAH", Utils.getParam(stockAlmacenBean.getCodigoCategoria()), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_STOK_ALMACEN");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_TIPO_ORIGEN", new SqlParameter("pi_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_CATEGORIA_BAH", new SqlParameter("pi_FK_IDE_CATEGORIA_BAH", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new StockAlmacenMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarStockAlmacen] Ocurrio un error en la operacion del USP_LISTAR_STOK_ALMACEN : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<StockAlmacenBean>) out.get("po_Lr_Recordset");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarStockAlmacen] Fin ");
		return lista;
	}
	
	@Override
	public StockAlmacenBean obtenerRegistroStockAlmacen(StockAlmacenBean stockAlmacenBean) throws Exception {
		LOGGER.info("[obtenerRegistroStockAlmacen] Inicio ");
		StockAlmacenBean stockAlmacen = new StockAlmacenBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_TIPO_ORIGEN", stockAlmacenBean.getTipoOrigen(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", stockAlmacenBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_DDI", stockAlmacenBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_PRODUCTO", stockAlmacenBean.getIdProducto(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_STOCK_ALMACEN");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_TIPO_ORIGEN", new SqlParameter("pi_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_DDI", new SqlParameter("pi_FK_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_PRODUCTO", new SqlParameter("pi_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new RegistroStockAlmacenMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRegistroStockAlmacen] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_STOCK_ALMACEN : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<StockAlmacenBean> lista = (List<StockAlmacenBean>) out.get("po_Lr_Recordset");
			if (!Utils.isEmpty(lista)) {
				stockAlmacen = lista.get(0);
			}
			
			stockAlmacen.setCodigoRespuesta(codigoRespuesta);
			stockAlmacen.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRegistroStockAlmacen] Fin ");
		return stockAlmacen;
	}
	
	@Override
	public StockAlmacenBean actualizarStockAlmacen(StockAlmacenBean stockAlmacenBean) throws Exception {
		LOGGER.info("[actualizarStockAlmacen] Inicio ");
		StockAlmacenBean registroStockAlmacen = new StockAlmacenBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_TIPO_ORIGEN", stockAlmacenBean.getTipoOrigen(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_DDI", stockAlmacenBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", stockAlmacenBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_PRODUCTO", stockAlmacenBean.getIdProducto(), Types.NUMERIC);			
			input_objParametros.addValue("pi_STOCK_SEGURIDAD", stockAlmacenBean.getStockSeguridad(), Types.NUMERIC);
			input_objParametros.addValue("pi_COD_ALMACEN", stockAlmacenBean.getCodigoAlmacen(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_TIP_ENVASE_SEC", stockAlmacenBean.getIdTipoEnvaseSecundario(), Types.NUMERIC);
			input_objParametros.addValue("pi_UNIDADES_ENVASE_SEC", stockAlmacenBean.getUnidadesEnvaseSecundario(), Types.NUMERIC);			
			input_objParametros.addValue("pi_DESCR_ENVASE_SEC", stockAlmacenBean.getDescripcionEnvaseSecundario(), Types.VARCHAR);	
			input_objParametros.addValue("pi_OBSERVACION", stockAlmacenBean.getObservacion(), Types.VARCHAR);	
			input_objParametros.addValue("pi_USU_MODIFICA", stockAlmacenBean.getUsuarioRegistro(), Types.VARCHAR);	
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_UPD_REGISTRA_STOCK_ALMACEN");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_TIPO_ORIGEN", new SqlParameter("pi_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_DDI", new SqlParameter("pi_FK_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_PRODUCTO", new SqlParameter("pi_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("pi_STOCK_SEGURIDAD", new SqlParameter("pi_STOCK_SEGURIDAD", Types.NUMERIC));
			output_objParametros.put("pi_COD_ALMACEN", new SqlParameter("pi_COD_ALMACEN", Types.VARCHAR));
			output_objParametros.put("pi_FK_TIP_ENVASE_SEC", new SqlParameter("pi_FK_TIP_ENVASE_SEC", Types.NUMERIC));
			output_objParametros.put("pi_UNIDADES_ENVASE_SEC", new SqlParameter("pi_UNIDADES_ENVASE_SEC", Types.NUMERIC));
			output_objParametros.put("pi_DESCR_ENVASE_SEC", new SqlParameter("pi_DESCR_ENVASE_SEC", Types.VARCHAR));
			output_objParametros.put("pi_OBSERVACION", new SqlParameter("pi_OBSERVACION", Types.VARCHAR));
			output_objParametros.put("pi_USU_MODIFICA", new SqlParameter("pi_USU_MODIFICA", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[actualizarStockAlmacen] Ocurrio un error en la operacion del USP_UPD_REGISTRA_STOCK_ALMACEN : "+mensajeRespuesta);
    			throw new Exception();
    		}
		
			registroStockAlmacen.setCodigoRespuesta(codigoRespuesta);
			registroStockAlmacen.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarStockAlmacen] Fin ");
		return registroStockAlmacen;
	}
	
	////////////////////////////////////////////////////////////////////////////////
	////////////////CARTILLA DE INVENTARIOS/////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<CartillaInventarioBean> listarCartillaInventario(CartillaInventarioBean cartillaInventarioBean) throws Exception {
		LOGGER.info("[listarCartillaInventario] Inicio ");
		List<CartillaInventarioBean> lista = new ArrayList<CartillaInventarioBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_COD_ANIO", Utils.getParam(cartillaInventarioBean.getCodigoAnio()), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", cartillaInventarioBean.getIdAlmacen(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_CARTILLA_INVENTARIO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new CartillaInventarioMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarCartillaInventario] Ocurrio un error en la operacion del USP_LISTAR_CARTILLA_INVENTARIO : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<CartillaInventarioBean>) out.get("po_Lr_Recordset");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarCartillaInventario] Fin ");
		return lista;
	}
	
	@Override
	public CartillaInventarioBean obtenerRegistroCartillaInventario(Integer idCartilla) throws Exception {
		LOGGER.info("[obtenerRegistroCartillaInventario] Inicio ");
		CartillaInventarioBean cartillaInventario = new CartillaInventarioBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_CARTILLA", idCartilla, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_CARTILLA_INV");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_CARTILLA", new SqlParameter("PI_IDE_CARTILLA", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_Lr_Recordset", new SqlOutParameter("PO_Lr_Recordset", OracleTypes.CURSOR, new RegistroCartillaInventarioMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRegistroCartillaInventario] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_CARTILLA_INV : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<CartillaInventarioBean> lista = (List<CartillaInventarioBean>) out.get("PO_Lr_Recordset");
			if (!Utils.isEmpty(lista)) {
				cartillaInventario = lista.get(0);
			}
			
			cartillaInventario.setCodigoRespuesta(codigoRespuesta);
			cartillaInventario.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRegistroCartillaInventario] Fin ");
		return cartillaInventario;
	}
	
	@Override
	public CartillaInventarioBean grabarCartillaInventario(CartillaInventarioBean cartillaInventarioBean) throws Exception {
		LOGGER.info("[grabarCartillaInventario] Inicio ");
		CartillaInventarioBean registroCartillaInventario = new CartillaInventarioBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_CARTILLA", cartillaInventarioBean.getIdCartilla(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", cartillaInventarioBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_COD_ANIO", cartillaInventarioBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_DDI", cartillaInventarioBean.getCodigoDdi(), Types.VARCHAR);
			input_objParametros.addValue("pi_COD_ALMACEN", cartillaInventarioBean.getCodigoAlmacen(), Types.VARCHAR);						
			input_objParametros.addValue("pi_FK_IDE_RESPONSABLE", cartillaInventarioBean.getIdResponsable(), Types.NUMERIC);
			input_objParametros.addValue("pi_FEC_CARTILLA", DateUtil.obtenerFechaHoraParseada(cartillaInventarioBean.getFechaCartilla()), Types.DATE);
			input_objParametros.addValue("pi_OBSERVACION_CARTILLA", cartillaInventarioBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("pi_USUARIO", cartillaInventarioBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_REGIS_CARTILLA_INV");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_CARTILLA", new SqlParameter("pi_IDE_CARTILLA", Types.NUMERIC));			
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_COD_DDI", new SqlParameter("pi_COD_DDI", Types.VARCHAR));
			output_objParametros.put("pi_COD_ALMACEN", new SqlParameter("pi_COD_ALMACEN", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_RESPONSABLE", new SqlParameter("pi_FK_IDE_RESPONSABLE", Types.NUMERIC));
			output_objParametros.put("pi_FEC_CARTILLA", new SqlParameter("pi_FEC_CARTILLA", Types.DATE));			
			output_objParametros.put("pi_OBSERVACION_CARTILLA", new SqlParameter("pi_OBSERVACION_CARTILLA", Types.VARCHAR));
			output_objParametros.put("pi_USUARIO", new SqlParameter("pi_USUARIO", Types.VARCHAR));
			output_objParametros.put("PO_IDE_CARTILLA", new SqlOutParameter("PO_IDE_CARTILLA", Types.NUMERIC));			
			output_objParametros.put("po_NRO_CARTILLA", new SqlOutParameter("po_NRO_CARTILLA", Types.VARCHAR));
			output_objParametros.put("po_COD_CARTILLA", new SqlOutParameter("po_COD_CARTILLA", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarCartillaInventario] Ocurrio un error en la operacion del USP_INS_UPD_REGIS_CARTILLA_INV : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			if (Utils.isNullInteger(cartillaInventarioBean.getIdCartilla())) { // Nuevo Registro
				registroCartillaInventario.setIdCartilla(((BigDecimal) out.get("PO_IDE_CARTILLA")).intValue());
				registroCartillaInventario.setNroCartilla((String) out.get("po_NRO_CARTILLA"));
				registroCartillaInventario.setCodigoCartilla((String) out.get("po_COD_CARTILLA"));
			}
			registroCartillaInventario.setCodigoRespuesta(codigoRespuesta);
			registroCartillaInventario.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarCartillaInventario] Fin ");
		return registroCartillaInventario;
	}
	
	@Override
	public List<ProductoCartillaInventarioBean> listarProductoCartillaInventario(ProductoCartillaInventarioBean productoCartillaInventarioBean) throws Exception {
		LOGGER.info("[listarProductoCartillaInventario] Inicio ");
		List<ProductoCartillaInventarioBean> lista = new ArrayList<ProductoCartillaInventarioBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_IDE_CARTILLA", productoCartillaInventarioBean.getIdCartilla(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LIST_CARTILLA_PRODUCTO_INV");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_CARTILLA", new SqlParameter("PI_IDE_CARTILLA", Types.NUMERIC));			
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_Lr_Recordset", new SqlOutParameter("PO_Lr_Recordset", OracleTypes.CURSOR, new ProductoCartillaInventarioMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[listarProductoCartillaInventario] Ocurrio un error en la operacion del USP_LIST_CARTILLA_PRODUCTO_INV : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<ProductoCartillaInventarioBean>) out.get("PO_Lr_Recordset");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProductoCartillaInventario] Fin ");
		return lista;
	}
	
	@Override
	public List<EstadoCartillaInventarioBean> listarEstadoCartillaInventario(EstadoCartillaInventarioBean estadoCartillaInventarioBean) throws Exception {
		LOGGER.info("[listarEstadoCartillaInventario] Inicio ");
		List<EstadoCartillaInventarioBean> lista = new ArrayList<EstadoCartillaInventarioBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_IDE_CARTILLA", estadoCartillaInventarioBean.getIdCartilla(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_ESTADO_CARTILLA_INV");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_CARTILLA", new SqlParameter("pi_IDE_CARTILLA", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_Lr_Recordset", new SqlOutParameter("PO_Lr_Recordset", OracleTypes.CURSOR, new DetalleEstadoCartillaInventarioMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarEstadoCartillaInventario] Ocurrio un error en la operacion del USP_LISTAR_ESTADO_CARTILLA_INV : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<EstadoCartillaInventarioBean>) out.get("PO_Lr_Recordset");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarEstadoCartillaInventario] Fin ");
		return lista;
	}

	@Override
	public EstadoCartillaInventarioBean grabarEstadoCartillaInventario(EstadoCartillaInventarioBean estadoCartillaInventarioBean) throws Exception {
		LOGGER.info("[grabarEstadoCartillaInventario] Inicio ");
		EstadoCartillaInventarioBean registroEstadoCartillaInventario = new EstadoCartillaInventarioBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_CARTILLA", estadoCartillaInventarioBean.getIdCartilla(), Types.NUMERIC);
			input_objParametros.addValue("pi_USERNAME", estadoCartillaInventarioBean.getUsuarioRegistro(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_ESTADO", estadoCartillaInventarioBean.getIdEstado(), Types.NUMERIC);
			input_objParametros.addValue("pi_FEC_ESTADO", DateUtil.obtenerFechaHoraParseada(estadoCartillaInventarioBean.getFechaEstado()), Types.DATE);
			input_objParametros.addValue("PI_OBSERVACION", estadoCartillaInventarioBean.getObservacion(), Types.VARCHAR);
			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_ESTADO_CARTILLA_INV");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_CARTILLA", new SqlParameter("pi_IDE_CARTILLA", Types.NUMERIC));
			output_objParametros.put("pi_USERNAME", new SqlParameter("pi_USERNAME", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_ESTADO", new SqlParameter("pi_FK_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("pi_FEC_ESTADO", new SqlParameter("pi_FEC_ESTADO", Types.DATE));			
			output_objParametros.put("PI_OBSERVACION", new SqlParameter("PI_OBSERVACION", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarEstadoCartillaInventario] Ocurrio un error en la operacion del USP_INS_ESTADO_CARTILLA_INV : "+mensajeRespuesta);
    			throw new Exception();
    		}

			registroEstadoCartillaInventario.setCodigoRespuesta(codigoRespuesta);
			registroEstadoCartillaInventario.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarEstadoCartillaInventario] Fin ");
		return registroEstadoCartillaInventario;
	}
	
	@Override
	public ProductoCartillaInventarioBean grabarProductoCartillaInventario(ProductoCartillaInventarioBean productoCartillaInventarioBean) throws Exception {
		LOGGER.info("[grabarProductoCartillaInventario] Inicio ");
		ProductoCartillaInventarioBean registroProductoCartillaInventario = new ProductoCartillaInventarioBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_DET_CARTILLA", productoCartillaInventarioBean.getIdDetalleCartilla(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_CARTILLA", productoCartillaInventarioBean.getIdCartilla(), Types.NUMERIC);
			input_objParametros.addValue("pi_TIPO_ORIGEN", productoCartillaInventarioBean.getTipoOrigen(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_DDI", productoCartillaInventarioBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", productoCartillaInventarioBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_PRODUCTO", productoCartillaInventarioBean.getIdProducto(), Types.NUMERIC);
			input_objParametros.addValue("pi_NRO_LOTE", productoCartillaInventarioBean.getNroLote(), Types.VARCHAR);			
			input_objParametros.addValue("pi_CANT_STOCK", productoCartillaInventarioBean.getCantidadStock(), Types.NUMERIC);
			input_objParametros.addValue("pi_USUARIO", productoCartillaInventarioBean.getUsuarioRegistro(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_CARTILLA_PROD_INV");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_DET_CARTILLA", new SqlParameter("pi_IDE_DET_CARTILLA", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_CARTILLA", new SqlParameter("pi_FK_IDE_CARTILLA", Types.NUMERIC));
			output_objParametros.put("pi_TIPO_ORIGEN", new SqlParameter("pi_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_DDI", new SqlParameter("pi_FK_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_PRODUCTO", new SqlParameter("pi_FK_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("pi_NRO_LOTE", new SqlParameter("pi_NRO_LOTE", Types.VARCHAR));			
			output_objParametros.put("pi_CANT_STOCK", new SqlParameter("pi_CANT_STOCK", Types.NUMERIC));
			output_objParametros.put("pi_USUARIO", new SqlParameter("pi_USUARIO", Types.VARCHAR));
			output_objParametros.put("po_IDE_DET_CARTILLA", new SqlOutParameter("po_IDE_DET_CARTILLA", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarProductoCartillaInventario] Ocurrio un error en la operacion del USP_INS_UPD_CARTILLA_PROD_INV : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoCartillaInventario.setCodigoRespuesta(codigoRespuesta);
			registroProductoCartillaInventario.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarProductoCartillaInventario] Fin ");
		return registroProductoCartillaInventario;
	}
	
	@Override
	public ProductoCartillaInventarioBean procesarProductosCartillaInventario(ProductoCartillaInventarioBean productoCartillaInventarioBean) throws Exception {
		LOGGER.info("[procesarProductosCartillaInventario] Inicio ");
		ProductoCartillaInventarioBean registroProductoCartillaInventario = new ProductoCartillaInventarioBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_FK_IDE_CARTILLA", productoCartillaInventarioBean.getIdCartilla(), Types.NUMERIC);
			input_objParametros.addValue("pi_TIPO_ORIGEN", productoCartillaInventarioBean.getTipoOrigen(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_DDI", productoCartillaInventarioBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", productoCartillaInventarioBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_USUARIO", productoCartillaInventarioBean.getUsuarioRegistro(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_CARTILLA_TODO_PROD_INV");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_FK_IDE_CARTILLA", new SqlParameter("pi_FK_IDE_CARTILLA", Types.NUMERIC));
			output_objParametros.put("pi_TIPO_ORIGEN", new SqlParameter("pi_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_DDI", new SqlParameter("pi_FK_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_USUARIO", new SqlParameter("pi_USUARIO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[procesarProductosCartillaInventario] Ocurrio un error en la operacion del USP_INS_CARTILLA_TODO_PROD_INV : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoCartillaInventario.setCodigoRespuesta(codigoRespuesta);
			registroProductoCartillaInventario.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[procesarProductosCartillaInventario] Fin ");
		return registroProductoCartillaInventario;
	}
	
	@Override
	public List<StockAlmacenProductoLoteBean> listarStockAlmacenProductoLote(StockAlmacenProductoLoteBean stockAlmacenProductoLoteBean) throws Exception {
		LOGGER.info("[listarStockAlmacenProductoLote] Inicio ");
		List<StockAlmacenProductoLoteBean> lista = new ArrayList<StockAlmacenProductoLoteBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_TIPO_ORIGEN", stockAlmacenProductoLoteBean.getTipoOrigen(), Types.VARCHAR);
			input_objParametros.addValue("pi_FK_IDE_DDI", stockAlmacenProductoLoteBean.getIdDdi(), Types.NUMERIC);
			input_objParametros.addValue("pi_FK_IDE_ALMACEN", stockAlmacenProductoLoteBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("pi_IDE_PRODUCTO", stockAlmacenProductoLoteBean.getIdProducto(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_ALMACEN_PROD_LOTE");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_TIPO_ORIGEN", new SqlParameter("pi_TIPO_ORIGEN", Types.VARCHAR));
			output_objParametros.put("pi_FK_IDE_DDI", new SqlParameter("pi_FK_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("pi_FK_IDE_ALMACEN", new SqlParameter("pi_FK_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("pi_IDE_PRODUCTO", new SqlParameter("pi_IDE_PRODUCTO", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new StockAlmacenProductoLoteMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarStockAlmacenProductoLote] Ocurrio un error en la operacion del USP_SEL_TAB_ALMACEN_PROD_LOTE : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			lista = (List<StockAlmacenProductoLoteBean>) out.get("po_Lr_Recordset");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarStockAlmacenProductoLote] Fin ");
		return lista;
	}
	
	@Override
	public ProductoCartillaInventarioBean eliminarProductoCartillaInventario(ProductoCartillaInventarioBean productoCartillaInventarioBean) throws Exception {
		LOGGER.info("[eliminarProductoCartillaInventario] Inicio ");
		ProductoCartillaInventarioBean registroProductoCartillaInventario = new ProductoCartillaInventarioBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_DET_CARTILLA", productoCartillaInventarioBean.getIdDetalleCartilla(), Types.NUMERIC);
			input_objParametros.addValue("PI_USUARIO", productoCartillaInventarioBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_DEL_CARTILLA_PRODUCTO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DET_CARTILLA", new SqlParameter("PI_IDE_DET_CARTILLA", Types.NUMERIC));
			output_objParametros.put("PI_USUARIO", new SqlParameter("PI_USUARIO", Types.VARCHAR));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[eliminarProductoCartillaInventario] Ocurrio un error en la operacion del USP_DEL_CARTILLA_PRODUCTO : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoCartillaInventario.setCodigoRespuesta(codigoRespuesta);
			registroProductoCartillaInventario.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[eliminarProductoCartillaInventario] Fin ");
		return registroProductoCartillaInventario;
	}
	
	@Override
	public ProductoCartillaInventarioBean actualizarAjusteProductoCartillaInventario(ProductoCartillaInventarioBean productoCartillaInventarioBean) throws Exception {
		LOGGER.info("[actualizarAjusteProductoCartillaInventario] Inicio ");
		ProductoCartillaInventarioBean registroProductoCartillaInventario = new ProductoCartillaInventarioBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("pi_IDE_DET_CARTILLA", productoCartillaInventarioBean.getIdDetalleCartilla(), Types.NUMERIC);
			input_objParametros.addValue("pi_STOCK_FISICO", productoCartillaInventarioBean.getStockFisico(), Types.NUMERIC);
			input_objParametros.addValue("pi_USUARIO", productoCartillaInventarioBean.getUsuarioRegistro(), Types.VARCHAR);			

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_UPD_CARTILLA_PROD_AJUSTE");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_IDE_DET_CARTILLA", new SqlParameter("pi_IDE_DET_CARTILLA", Types.NUMERIC));
			output_objParametros.put("pi_STOCK_FISICO", new SqlParameter("pi_STOCK_FISICO", Types.NUMERIC));
			output_objParametros.put("pi_USUARIO", new SqlParameter("pi_USUARIO", Types.VARCHAR));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[actualizarAjusteProductoCartillaInventario] Ocurrio un error en la operacion del USP_UPD_CARTILLA_PROD_AJUSTE : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			registroProductoCartillaInventario.setCodigoRespuesta(codigoRespuesta);
			registroProductoCartillaInventario.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[actualizarAjusteProductoCartillaInventario] Fin ");
		return registroProductoCartillaInventario;
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	/////////////////////CIERRE MENSUAL//////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<CierreStockBean> listarCierreStock(CierreStockBean cierreStockBean) throws Exception {
		LOGGER.info("[listarCierreStock] Inicio ");
		List<CierreStockBean> lista = new ArrayList<CierreStockBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("pi_COD_ANIO", cierreStockBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("pi_IDE_ALMACEN", Utils.getParam(cierreStockBean.getIdAlmacen()), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_LISTAR_CIERRE_ALMACEN");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("pi_COD_ANIO", new SqlParameter("pi_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("pi_IDE_ALMACEN", new SqlParameter("pi_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_Lr_Recordset", new SqlOutParameter("po_Lr_Recordset", OracleTypes.CURSOR, new DonCierreStockMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[listarCierreStock] Ocurrio un error en la operacion del USP_LISTAR_CIERRE_ALMACEN : "+mensajeRespuesta);
				throw new Exception();
			} else {
				lista = (List<CierreStockBean>) out.get("po_Lr_Recordset");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarCierreStock] Fin ");
		return lista;
	}

	
	@Override
	public CierreStockBean obtenerRegistroCierreStock(CierreStockBean cierreStockBean) throws Exception {
		LOGGER.info("[obtenerRegistroCierreStock] Inicio ");
		CierreStockBean cierreStock = new CierreStockBean();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_COD_ANIO", cierreStockBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", cierreStockBean.getCodigoMes(), Types.VARCHAR);
			input_objParametros.addValue("PI_IDE_ALMACEN", cierreStockBean.getIdAlmacen(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_MOSTRAR_CIERRE_ALMACEN");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_Lr_Recordset", new SqlOutParameter("PO_Lr_Recordset", OracleTypes.CURSOR, new DonRegistroCierreStockMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("PO_MENSAJE_RESPUESTA");
				LOGGER.info("[obtenerRegistroCierreStock] Ocurrio un error en la operacion del USP_SEL_MOSTRAR_CIERRE_ALMACEN : "+mensajeRespuesta);
    			throw new Exception();
    		}
			
			List<CierreStockBean> lista = (List<CierreStockBean>) out.get("PO_Lr_Recordset");
			if (!Utils.isEmpty(lista)) {
				cierreStock = lista.get(0);
			}
			
			cierreStock.setCodigoRespuesta(codigoRespuesta);
			cierreStock.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerRegistroCierreStock] Fin ");
		return cierreStock;
	}
	
	@Override
	public CierreStockBean grabarCierreStock(CierreStockBean cierreStockBean) throws Exception {
		LOGGER.info("[grabarCierreStock] Inicio ");
		CierreStockBean registroCierreStock = new CierreStockBean();
		try {			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_IDE_ALMACEN", cierreStockBean.getIdAlmacen(), Types.NUMERIC);
			input_objParametros.addValue("PI_COD_ANIO", cierreStockBean.getCodigoAnio(), Types.VARCHAR);
			input_objParametros.addValue("PI_COD_MES", cierreStockBean.getCodigoMes(), Types.VARCHAR);			
			input_objParametros.addValue("PI_FLG_CIERRE_DON", cierreStockBean.getFlagCierreAlmacen(), Types.VARCHAR);			
			input_objParametros.addValue("PI_FK_IDE_RESPONSABLE_DON", cierreStockBean.getIdResponsable(), Types.NUMERIC);
			input_objParametros.addValue("PI_OBSERVACION", cierreStockBean.getObservacion(), Types.VARCHAR);
			input_objParametros.addValue("PI_USUARIO", cierreStockBean.getUsuarioRegistro(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_DONACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_INS_UPD_CIERRE_ALMACEN");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_ALMACEN", new SqlParameter("PI_IDE_ALMACEN", Types.NUMERIC));			
			output_objParametros.put("PI_COD_ANIO", new SqlParameter("PI_COD_ANIO", Types.VARCHAR));
			output_objParametros.put("PI_COD_MES", new SqlParameter("PI_COD_MES", Types.VARCHAR));
			output_objParametros.put("PI_FLG_CIERRE_DON", new SqlParameter("PI_FLG_CIERRE_DON", Types.VARCHAR));
			output_objParametros.put("PI_FK_IDE_RESPONSABLE_DON", new SqlParameter("PI_FK_IDE_RESPONSABLE_DON", Types.NUMERIC));
			output_objParametros.put("PI_OBSERVACION", new SqlParameter("PI_OBSERVACION", Types.VARCHAR));
			output_objParametros.put("PI_USUARIO", new SqlParameter("PI_USUARIO", Types.VARCHAR));;
			output_objParametros.put("po_CODIGO_RESPUESTA", new SqlOutParameter("po_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("po_MENSAJE_RESPUESTA", new SqlOutParameter("po_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			String codigoRespuesta = (String) out.get("po_CODIGO_RESPUESTA");
			
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				String mensajeRespuesta = (String) out.get("po_MENSAJE_RESPUESTA");
				LOGGER.info("[grabarCierreStock] Ocurrio un error en la operacion del USP_INS_UPD_CIERRE_ALMACEN : "+mensajeRespuesta);
    			throw new Exception();
    		}

			registroCierreStock.setCodigoRespuesta(codigoRespuesta);
			registroCierreStock.setMensajeRespuesta((String) out.get("po_MENSAJE_RESPUESTA"));
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[grabarCierreStock] Fin ");
		return registroCierreStock;
	}

	
}

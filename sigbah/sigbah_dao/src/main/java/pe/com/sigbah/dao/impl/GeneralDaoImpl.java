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
import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.ProductoBean;
import pe.com.sigbah.common.bean.UbigeoBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.GeneralDao;
import pe.com.sigbah.mapper.AlmacenMapper;
import pe.com.sigbah.mapper.CategoriaMapper;
import pe.com.sigbah.mapper.CatologoProductosMapper;
import pe.com.sigbah.mapper.ChoferMapper;
import pe.com.sigbah.mapper.DdiMapper;
import pe.com.sigbah.mapper.EmpresaTransporteMapper;
import pe.com.sigbah.mapper.EstadoDonacionMapper;
import pe.com.sigbah.mapper.EstadoMapper;
import pe.com.sigbah.mapper.PersonalMapper;
import pe.com.sigbah.mapper.ProveedorMapper;
import pe.com.sigbah.mapper.TipoControlCalidadMapper;
import pe.com.sigbah.mapper.TipoDocumentoMapper;
import pe.com.sigbah.mapper.UnidadMedidaMapper;

/**
 * @className: GeneralDaoImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_GENERAL.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class GeneralDaoImpl extends JdbcDaoSupport implements GeneralDao, Serializable {

	private static final long serialVersionUID = 1L;
	private transient final Log LOGGER = LogFactory.getLog(getClass());
	
	private SimpleJdbcCall objJdbcCall;
	
	/**
	 * @param dataSource
	 */
	@Autowired
	public GeneralDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarDepartamentos(pe.com.sigbah.common.bean.UbigeoBean)
	 */
	@Override
	public List<UbigeoBean> listarDepartamentos(UbigeoBean ubigeoBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarProvincia(pe.com.sigbah.common.bean.UbigeoBean)
	 */
	@Override
	public List<UbigeoBean> listarProvincia(UbigeoBean ubigeoBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarDistrito(pe.com.sigbah.common.bean.UbigeoBean)
	 */
	@Override
	public List<UbigeoBean> listarDistrito(UbigeoBean ubigeoBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarFenomenos(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarFenomenos(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarAnios()
	 */
	@Override
	public List<ItemBean> listarAnios() throws Exception {
		LOGGER.info("[listarAnios] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_ANIOS");

			Map<String, Object> out = objJdbcCall.withoutProcedureColumnMetaDataAccess()
					.returningResultSet("PO_LR_RECORDSET", new RowMapper<ItemBean>() {
						public ItemBean mapRow(ResultSet rs, int rowNum) throws SQLException {
							ItemBean item = new ItemBean();
							item.setVcodigo(rs.getString("COD_ANIO"));
							item.setDescripcion(rs.getString("NOMBRE_ANIO"));
							return item;
						}
					}).execute(objJdbcCall);

			lista = new ArrayList((Collection<ItemBean>) out.get("PO_LR_RECORDSET"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarAnios] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarMeses(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarMeses(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarDdi(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarDdi(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarDdi] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(itemBean.getIcodigo());
			input_objParametros.addValue("PI_IDE_DDI", parametro, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_DDI");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new DdiMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarDdi] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarRegion(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarRegion(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarAlmacen(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarAlmacen(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarAlmacen] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(itemBean.getIcodigo());
			input_objParametros.addValue("PI_IDE_DDI", parametro, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_ALMACEN");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new AlmacenMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarAlmacen] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarUnidadMedida(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarUnidadMedida(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarUnidadMedida] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(itemBean.getIcodigo());
			input_objParametros.addValue("PI_IDE_UND_MEDIDA", parametro, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_UNIDAD_MEDIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_UND_MEDIDA", new SqlParameter("PI_IDE_UND_MEDIDA", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new UnidadMedidaMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarUnidadMedida] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarEnvase(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarEnvase(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarEstado(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarEstado(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarEstado] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(itemBean.getIcodigo());
			input_objParametros.addValue("PI_IDE_ESTADO", parametro, Types.NUMERIC);
			input_objParametros.addValue("PI_TIPO", itemBean.getIcodigoParam2(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_ESTADO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_ESTADO", new SqlParameter("PI_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("PI_TIPO", new SqlParameter("PI_TIPO", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new EstadoMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarEstado] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarAlmacenExterno(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarAlmacenExterno(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarTipoMovimiento(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarTipoMovimiento(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarTipoDocumento(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarTipoDocumento(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarTipoDocumento] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(itemBean.getIcodigo());
			input_objParametros.addValue("PI_IDE_TIP_DOCUMENTO", parametro, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_TIP_DOCUMENTO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_TIP_DOCUMENTO", new SqlParameter("PI_IDE_TIP_DOCUMENTO", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new TipoDocumentoMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarTipoDocumento] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarTipoCamion(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarTipoCamion(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarTipoControlCalidad(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarTipoControlCalidad(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarTipoControlCalidad] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(itemBean.getIcodigo());
			input_objParametros.addValue("PI_IDE_TIP_CONTROL", parametro, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_TIP_CONTROL_CALIDA");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_TIP_CONTROL", new SqlParameter("PI_IDE_TIP_CONTROL", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new TipoControlCalidadMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarTipoControlCalidad] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarProveedor(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarProveedor(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarProveedor] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(itemBean.getIcodigo());
			input_objParametros.addValue("PI_IDE_PROVEEDOR", parametro, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_PROVEEDOR");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_PROVEEDOR", new SqlParameter("PI_IDE_PROVEEDOR", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new ProveedorMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarProveedor] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarPersonal(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarPersonal(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarPersonal] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(itemBean.getIcodigo());
			input_objParametros.addValue("PI_IDE_DDI", parametro, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_PERSONAL");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new PersonalMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarPersonal] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarPersonalExterno(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarPersonalExterno(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarModAlmacen(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarModAlmacen(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarFuenteFinanciamiento(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarFuenteFinanciamiento(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarMarca(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarMarca(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarDonante(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarDonante(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarMoneda(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarMoneda(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarPais(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarPais(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarCategoria(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarCategoria(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarCategoria] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_TIPO", itemBean.getIcodigo(), Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_CATEGORIA_PRODUC");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_TIPO", new SqlParameter("PI_TIPO", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new CategoriaMapper()));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarCategoria] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarDee(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarDee(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarCatologoProductos(pe.com.sigbah.common.bean.ProductoBean)
	 */
	@Override
	public List<ProductoBean> listarCatologoProductos(ProductoBean productoBean) throws Exception {
		LOGGER.info("[listarCatologoProductos] Inicio ");
		List<ProductoBean> lista = new ArrayList<ProductoBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(productoBean.getIdProducto());
			input_objParametros.addValue("PI_IDE_CATEGORIA_BAH", productoBean.getIdCategoria(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_CAT_PRODUCTO", parametro, Types.NUMERIC);			
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_CATALOGO_PRODUCTOS");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_CATEGORIA_BAH", new SqlParameter("PI_IDE_CATEGORIA_BAH", Types.NUMERIC));
			output_objParametros.put("PI_IDE_CAT_PRODUCTO", new SqlParameter("PI_IDE_CAT_PRODUCTO", Types.NUMERIC));			
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new CatologoProductosMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ProductoBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarCatologoProductos] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarMedioTransporte(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarMedioTransporte(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarEmpresaTransporte(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarEmpresaTransporte(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarEmpresaTransporte] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(itemBean.getIcodigoParam3());
			input_objParametros.addValue("PI_IDE_DDI", itemBean.getIcodigo(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_MED_TRANSPORTE", itemBean.getIcodigoParam2(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_EMP_TRANS", parametro, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_EMP_TRANSPORTE");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_DDI", new SqlParameter("PI_IDE_DDI", Types.NUMERIC));
			output_objParametros.put("PI_IDE_MED_TRANSPORTE", new SqlParameter("PI_IDE_MED_TRANSPORTE", Types.NUMERIC));
			output_objParametros.put("PI_IDE_EMP_TRANS", new SqlParameter("PI_IDE_EMP_TRANS", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new EmpresaTransporteMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarEmpresaTransporte] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarEjecutora(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarEjecutora(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarChofer(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarChofer(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarChofer] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(itemBean.getIcodigoParam2());
			input_objParametros.addValue("PI_IDE_EMP_TRANS", itemBean.getIcodigo(), Types.NUMERIC);
			input_objParametros.addValue("PI_IDE_CHOFER", parametro, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_CHOFER");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_EMP_TRANS", new SqlParameter("PI_IDE_EMP_TRANS", Types.NUMERIC));
			output_objParametros.put("PI_IDE_CHOFER", new SqlParameter("PI_IDE_CHOFER", Types.NUMERIC));			
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new ChoferMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarChofer] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarParametro(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarParametro(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenerAnioActual() throws Exception {
		LOGGER.info("[obtenerAnioActual] Inicio ");
		String anio = null;
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_ANIO_ACTUAL");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PO_CODANIO", new SqlOutParameter("PO_CODANIO", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			anio = (String) out.get("PO_CODANIO");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerAnioActual] Fin ");
		return anio;
	}

	@Override
	public List<ItemBean> listarEstadoDonacion(ItemBean itemBean) throws Exception {
		LOGGER.info("[listarEstado] Inicio ");
		List<ItemBean> lista = new ArrayList<ItemBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();			
			Integer parametro = Utils.getParamInt(itemBean.getIcodigo());
			input_objParametros.addValue("PI_IDE_ESTADO", parametro, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_TAB_ESTADO_DONACION");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_IDE_ESTADO", new SqlParameter("PI_IDE_ESTADO", Types.NUMERIC));
			output_objParametros.put("PO_LR_RECORDSET", new SqlOutParameter("PO_LR_RECORDSET", OracleTypes.CURSOR, new EstadoDonacionMapper(parametro)));
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			lista = (List<ItemBean>) out.get("PO_LR_RECORDSET");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarEstado] Fin ");
		return lista;
	}

}

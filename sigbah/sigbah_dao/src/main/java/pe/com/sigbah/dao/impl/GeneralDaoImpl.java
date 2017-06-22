package pe.com.sigbah.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.UbigeoBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.dao.GeneralDao;

/**
 * @className: GeneralDaoImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_GENERAL.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ItemBean> listarAnios() throws Exception {
		LOGGER.info("[listarAnios] Inicio ");
		List<ItemBean> items = new ArrayList<ItemBean>();
		try {
			Collection<ItemBean> colItemBean = null;
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_GENERAL);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SIG_BAH);
			objJdbcCall.withProcedureName("USP_SEL_TAB_ANIOS");

			Map<String, Object> out = objJdbcCall.withoutProcedureColumnMetaDataAccess()
					.returningResultSet("po_Lr_Recordset", new RowMapper<ItemBean>() {
						public ItemBean mapRow(ResultSet rs, int rowNum) throws SQLException {
							ItemBean item = new ItemBean();
							item.setVcodigo(rs.getString("COD_ANIO"));
							item.setDescripcion(rs.getString("DESCRIPCION"));
							return item;
						}
					}).execute(objJdbcCall);

			colItemBean = (Collection<ItemBean>) out.get("po_Lr_Recordset");
			items = new ArrayList(colItemBean);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarAnios] Fin ");
		return items;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarUnidadMedida(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarUnidadMedida(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarProveedor(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarProveedor(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarPersonal(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarPersonal(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarCatologoProductos(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarCatologoProductos(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.general.GeneralDao#listarParametro(pe.com.sigbah.common.bean.ItemBean)
	 */
	@Override
	public List<ItemBean> listarParametro(ItemBean itemBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

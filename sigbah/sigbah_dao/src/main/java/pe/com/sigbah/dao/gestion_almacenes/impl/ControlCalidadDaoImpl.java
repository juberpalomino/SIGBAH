package pe.com.sigbah.dao.gestion_almacenes.impl;

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
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.dao.gestion_almacenes.ControlCalidadDao;

/**
 * @className: ControlCalidadDaoImpl.java
 * @description: 
 * @date: 19 de jun. de 2017
 * @author: SUMERIO.
 */
@Repository
public class ControlCalidadDaoImpl extends JdbcDaoSupport implements ControlCalidadDao, Serializable {
	
	private static final long serialVersionUID = 1L;
	private transient final Log LOGGER = LogFactory.getLog(getClass());
	
	private SimpleJdbcCall objJdbcCall;
	
	/**
	 * @param dataSource
	 */
	@Autowired
	public ControlCalidadDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.gestion_almacenes.ControlCalidadDao#listarAnios()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ItemBean> listarAnios() throws Exception {
		LOGGER.info("[listarAnios] Inicio ");
		List<ItemBean> perfiles = new ArrayList<ItemBean>();

		try {

			Collection<ItemBean> colPerfil = null;

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName("BAH_PKG_GENERAL");
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SIG_BAH);
			objJdbcCall.withProcedureName("USP_SEL_TAB_ANIOS");

			Map<String, Object> out = objJdbcCall.withoutProcedureColumnMetaDataAccess()
					.returningResultSet("OS_CURSOR", new RowMapper<ItemBean>() {
						public ItemBean mapRow(ResultSet rs, int rowNum) throws SQLException {
							ItemBean item = new ItemBean();
							item.setVcodigo(rs.getString("COD_ANIO"));
							item.setDescripcion(rs.getString("DESCRIPCION"));
							return item;
						}
					}).execute(objJdbcCall);

			colPerfil = (Collection<ItemBean>) out.get("OS_CURSOR");
			perfiles = new ArrayList(colPerfil);

			return perfiles;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		LOGGER.info("[listarAnios] Fin ");
		return perfiles;
	}

}

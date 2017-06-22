package pe.com.sigbah.dao.impl;

import java.io.Serializable;
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
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sigbah.common.bean.DetalleUsuarioBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.dao.AdministracionDao;
import pe.com.sigbah.mapper.UsuarioMapper;

/**
 * @className: AdministracionDaoImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_ADMINISTRACION.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
@Repository
public class AdministracionDaoImpl extends JdbcDaoSupport implements AdministracionDao, Serializable {
	
	private static final long serialVersionUID = 1L;
	private transient final Log LOGGER = LogFactory.getLog(getClass());
	
	private SimpleJdbcCall objJdbcCall;
	
	/**
	 * @param dataSource
	 */
	@Autowired
	public AdministracionDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.AdministracionDao#obtenerDatosUsuario(pe.com.sigbah.common.bean.UsuarioBean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DetalleUsuarioBean obtenerDatosUsuario(UsuarioBean usuarioBean) throws Exception {
		LOGGER.info("[obtenerDatosUsuario] Inicio ");
		DetalleUsuarioBean detalleUsuarioBean = new DetalleUsuarioBean();
		try {			
			Collection<UsuarioBean> colDetalleUsuarioBean = null;
			
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();
			input_objParametros.addValue("PI_USERNAME", usuarioBean.getUsuario(), Types.VARCHAR);
			input_objParametros.addValue("PI_PASSWORD", usuarioBean.getPassword(), Types.VARCHAR);

			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_ADMINISTRACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SIG_BAH);
			objJdbcCall.withProcedureName("USP_SEL_USUARIO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_USERNAME", new SqlParameter("PI_USERNAME", Types.VARCHAR));
			output_objParametros.put("PI_PASSWORD", new SqlParameter("PI_PASSWORD", Types.VARCHAR));
			output_objParametros.put("PO_USER_EXISTE", new SqlOutParameter("PO_USER_EXISTE", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new UsuarioMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));

			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);

			colDetalleUsuarioBean = (List<UsuarioBean>) out.get("PO_CURSOR");			
			detalleUsuarioBean.setIndicadorUsuario((String) out.get("PO_USER_EXISTE"));
			detalleUsuarioBean.setCodigoRespuesta((String) out.get("PO_CODIGO_RESPUESTA"));
			detalleUsuarioBean.setMensajeRespuesta((String) out.get("PO_MENSAJE_RESPUESTA"));			
			detalleUsuarioBean.setDatosUsuario(new ArrayList<UsuarioBean>(colDetalleUsuarioBean));			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerDatosUsuario] Fin ");
		return detalleUsuarioBean;
	}
	
}

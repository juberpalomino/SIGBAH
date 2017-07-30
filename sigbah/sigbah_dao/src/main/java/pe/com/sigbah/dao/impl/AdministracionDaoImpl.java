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
import pe.com.sigbah.common.bean.AlmacenBean;
import pe.com.sigbah.common.bean.DetalleUsuarioBean;
import pe.com.sigbah.common.bean.EstadoUsuarioBean;
import pe.com.sigbah.common.bean.ModuloBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.SpringUtil;
import pe.com.sigbah.common.util.Utils;
import pe.com.sigbah.dao.AdministracionDao;
import pe.com.sigbah.mapper.AlmacenUsuarioMapper;
import pe.com.sigbah.mapper.EstadoUsuarioMapper;
import pe.com.sigbah.mapper.UsuarioMapper;

/**
 * @className: AdministracionDaoImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_ADMINISTRACION.
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@SuppressWarnings("unchecked")
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
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
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
			
			if (!Utils.isEmpty(colDetalleUsuarioBean)) {
				detalleUsuarioBean.setDatosUsuario(new ArrayList<UsuarioBean>(colDetalleUsuarioBean));
			}
			
			if (detalleUsuarioBean.getCodigoRespuesta().equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[obtenerDatosUsuario] Ocurrio un error en la operacion del USP_SEL_USUARIO");
    			throw new Exception();
    		}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[obtenerDatosUsuario] Fin ");
		return detalleUsuarioBean;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.AdministracionDao#listarModuloUsuario(java.lang.Integer)
	 */
	@Override
	public List<ModuloBean> listarModuloUsuario(Integer idUsuario) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.AdministracionDao#listarAlmacenUsuario(java.lang.Integer)
	 */
	@Override
	public List<AlmacenBean> listarAlmacenUsuario(Integer idUsuario) throws Exception {
		LOGGER.info("[listarAlmacenUsuario] Inicio ");
		List<AlmacenBean> lista = new ArrayList<AlmacenBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_ID_USER", idUsuario, Types.NUMERIC);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_ADMINISTRACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_ALMACEN_POR_USUARIO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_USER", new SqlParameter("PI_ID_USER", Types.NUMERIC));
			output_objParametros.put("PO_CURSOR", new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR, new AlmacenUsuarioMapper()));
			output_objParametros.put("PO_CODIGO_RESPUESTA", new SqlOutParameter("PO_CODIGO_RESPUESTA", Types.VARCHAR));
			output_objParametros.put("PO_MENSAJE_RESPUESTA", new SqlOutParameter("PO_MENSAJE_RESPUESTA", Types.VARCHAR));			
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			String codigoRespuesta = (String) out.get("PO_CODIGO_RESPUESTA");
			if (codigoRespuesta.equals(Constantes.COD_ERROR_GENERAL)) {
				LOGGER.info("[listarAlmacenUsuario] Ocurrio un error en la operacion del USP_SEL_ALMACEN_POR_USUARIO");
				throw new Exception();
			} else {
				lista = (List<AlmacenBean>) out.get("PO_CURSOR");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarAlmacenUsuario] Fin ");
		return lista;
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.dao.AdministracionDao#listarEstadoUsuario(pe.com.sigbah.common.bean.EstadoUsuarioBean)
	 */
	@Override
	public List<EstadoUsuarioBean> listarEstadoUsuario(EstadoUsuarioBean estadoUsuarioBean) throws Exception {
		LOGGER.info("[listarEstadoUsuario] Inicio ");
		List<EstadoUsuarioBean> lista = new ArrayList<EstadoUsuarioBean>();
		try {
			MapSqlParameterSource input_objParametros = new MapSqlParameterSource();		
			input_objParametros.addValue("PI_ID_USER", estadoUsuarioBean.getIdUsuario(), Types.NUMERIC);
			input_objParametros.addValue("PI_NOMBRE_MODULO", estadoUsuarioBean.getNombreModulo(), Types.VARCHAR);
			
			objJdbcCall = new SimpleJdbcCall(getJdbcTemplate());
			objJdbcCall.withoutProcedureColumnMetaDataAccess();
			objJdbcCall.withCatalogName(Constantes.PACKAGE_ADMINISTRACION);
			objJdbcCall.withSchemaName(Constantes.ESQUEMA_SINPAD);
			objJdbcCall.withProcedureName("USP_SEL_ESTADOS_POR_USUARIO");

			LinkedHashMap<String, SqlParameter> output_objParametros = new LinkedHashMap<String, SqlParameter>();
			output_objParametros.put("PI_ID_USER", new SqlParameter("PI_ID_USER", Types.NUMERIC));
			output_objParametros.put("PI_NOMBRE_MODULO", new SqlParameter("PI_NOMBRE_MODULO", Types.VARCHAR));
			output_objParametros.put("PO_CURSOR_ESTADOS", new SqlOutParameter("PO_CURSOR_ESTADOS", OracleTypes.CURSOR, new EstadoUsuarioMapper()));		
			
			objJdbcCall.declareParameters((SqlParameter[]) SpringUtil.getHashMapObjectsArray(output_objParametros));
			
			Map<String, Object> out = objJdbcCall.execute(input_objParametros);
			
			lista = (List<EstadoUsuarioBean>) out.get("PO_CURSOR_ESTADOS");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception();
		}		
		LOGGER.info("[listarEstadoUsuario] Fin ");
		return lista;
	}
	
}

package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.UsuarioBean;

/**
 * @className: UsuarioMapper.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public class UsuarioMapper implements RowMapper<UsuarioBean> {

	@Override
	public UsuarioBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		UsuarioBean usuarioBean = new UsuarioBean();
		usuarioBean.setIdUsuario(rs.getInt("ID_USER"));
		usuarioBean.setUsuario(rs.getString("USERNAME"));
		usuarioBean.setCargo(rs.getString("CARGO"));
		usuarioBean.setNombreUsuario(rs.getString("NOMBRE_USUARIO").trim());
		usuarioBean.setIdDdi(rs.getInt("IDE_DDI"));
		usuarioBean.setCodigoDdi(rs.getString("COD_DDI"));
		usuarioBean.setNombreDdi(rs.getString("NOM_DDI"));
		usuarioBean.setIdAlmacen(rs.getInt("IDE_ALMACEN"));
		usuarioBean.setCodigoAlmacen(rs.getString("COD_ALMACEN"));
		usuarioBean.setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
		return usuarioBean;
	}

}

package pe.com.sigbah.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sigbah.common.bean.ProductoSalidaBean;

/**
 * @className: ProductoSalidaMapper.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public class ProductoSalidaMapper implements RowMapper<ProductoSalidaBean> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public ProductoSalidaBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductoSalidaBean producto = new ProductoSalidaBean();
		producto.setIdSalida(rs.getInt("IDE_INGRESO"));
		producto.setIdDetalleSalida(rs.getInt("IDE_INGRESO_DET"));
		producto.setIdProducto(rs.getInt("IDE_PRODUCTO"));
		producto.setNombreProducto(rs.getString("NOM_PRODUCTO"));
		producto.setNombreUnidad(rs.getString("NOM_UNIDAD"));
		producto.setNombreEnvase(rs.getString("NOM_ENVASE"));
		producto.setNroLote(rs.getInt("NRO_LOTE"));
		producto.setCantidad(rs.getBigDecimal("CANTIDAD"));
		producto.setPrecioUnitario(rs.getBigDecimal("PREC_UNITARIO"));
		producto.setImporteTotal(rs.getBigDecimal("IMP_TOTAL"));
		producto.setFechaVencimiento(rs.getString("FECHA_VENCIMIENTO"));
		producto.setIdCategoria(rs.getInt("FK_IDE_CATEGORIA_BAH"));
		return producto;
	}

}

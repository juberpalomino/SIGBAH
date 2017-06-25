package pe.com.sigbah.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.OrdenCompraBean;
import pe.com.sigbah.dao.LogisticaDao;
import pe.com.sigbah.service.LogisticaService;

/**
 * @className: LogisticaServiceImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_LOGISTICA.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
@Service
public class LogisticaServiceImpl implements LogisticaService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LogisticaDao logisticaDao;
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public List<ControlCalidadBean> listarControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		return logisticaDao.listarControlCalidad(controlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#obtenerCorrelativo(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public ControlCalidadBean obtenerCorrelativo(ControlCalidadBean controlCalidadBean) throws Exception {
		return logisticaDao.obtenerCorrelativo(controlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarAlmacenActivo(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public List<ControlCalidadBean> listarAlmacenActivo(ControlCalidadBean controlCalidadBean) throws Exception {
		return logisticaDao.listarAlmacenActivo(controlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarOrdenCompra()
	 */
	@Override
	public List<OrdenCompraBean> listarOrdenCompra() throws Exception {
		return logisticaDao.listarOrdenCompra();
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#insertarRegistroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public ControlCalidadBean insertarRegistroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		return logisticaDao.insertarRegistroControlCalidad(controlCalidadBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#actualizarRegistroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public ControlCalidadBean actualizarRegistroControlCalidad(ControlCalidadBean controlCalidadBean) throws Exception {
		return logisticaDao.actualizarRegistroControlCalidad(controlCalidadBean);
	}

}

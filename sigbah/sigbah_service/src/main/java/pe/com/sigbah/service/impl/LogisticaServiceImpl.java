package pe.com.sigbah.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.ControlCalidadBean;
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

}

package pe.com.sigbah.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.DonacionesBean;
import pe.com.sigbah.dao.DonacionDao;
import pe.com.sigbah.service.DonacionService;

/**
 * @className: DonacionServiceImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_DONACION.
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Service
public class DonacionServiceImpl implements DonacionService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DonacionDao donacionDao;
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#listarControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public List<DonacionesBean> listarDonaciones(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.listarDonaciones(donacionesBean);
	}
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#actualizarRegistroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public DonacionesBean actualizarDonaciones(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.actualizarDonaciones(donacionesBean);
	}
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#actualizarRegistroControlCalidad(pe.com.sigbah.common.bean.DonacionesBean)
	 */
	@Override
	public DonacionesBean insertarDonaciones(DonacionesBean donacionesBean) throws Exception {
		return donacionDao.insertarDonaciones(donacionesBean);
	}


}

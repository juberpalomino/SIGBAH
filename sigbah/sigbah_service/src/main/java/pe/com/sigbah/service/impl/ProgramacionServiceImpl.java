package pe.com.sigbah.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.ControlCalidadBean;
import pe.com.sigbah.common.bean.EmergenciaBean;
import pe.com.sigbah.common.bean.ListaRespuestaEmergenciaBean;
import pe.com.sigbah.common.bean.RequerimientoBean;
import pe.com.sigbah.dao.ProgramacionDao;
import pe.com.sigbah.service.ProgramacionService;

/**
 * @className: ProgramacionServiceImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_PROGRAMACION.
 * @date: 21 de jun. de 2017
 * @author: Junior Huaman Flores.
 */
@Service
public class ProgramacionServiceImpl implements ProgramacionService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProgramacionDao programacionDao;

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#listarEmergencia(pe.com.sigbah.common.bean.EmergenciaBean)
	 */
	@Override
	public List<EmergenciaBean> listarEmergencia(EmergenciaBean emergenciaBean) throws Exception {
		return programacionDao.listarEmergencia(emergenciaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#obtenerRegistroEmergencia(java.lang.Integer)
	 */
	@Override
	public ListaRespuestaEmergenciaBean obtenerRegistroEmergencia(Integer idEmergencia, String codAnio) throws Exception {
		return programacionDao.obtenerRegistroEmergencia(idEmergencia,  codAnio);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#listarRequerimiento(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public List<RequerimientoBean> listarRequerimiento(RequerimientoBean requerimientoBean) throws Exception {
		return programacionDao.listarRequerimiento(requerimientoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#obtenerRequerimiento(java.lang.Integer, java.lang.String)
	 */
	@Override
	public RequerimientoBean obtenerRequerimiento( Integer codigo) throws Exception {
		return programacionDao.obtenerRequerimiento(codigo );
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#obtenerCorrelativoRequerimiento(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public RequerimientoBean obtenerCorrelativoRequerimiento(RequerimientoBean parametros) throws Exception {
		return programacionDao.obtenerCorrelativoRequerimiento(parametros );
	}
	
	@Override
	public RequerimientoBean insertarRegistroRequerimiento(RequerimientoBean requerimiento) throws Exception {
		return programacionDao.insertarRegistroRequerimiento(requerimiento);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.LogisticaService#actualizarRegistroControlCalidad(pe.com.sigbah.common.bean.ControlCalidadBean)
	 */
	@Override
	public RequerimientoBean actualizarRegistroRequerimiento(RequerimientoBean requerimiento) throws Exception {
		return programacionDao.actualizarRegistroRequerimiento(requerimiento);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionService#listarEmergenciasActivas(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public List<EmergenciaBean> listarEmergenciasActivas(RequerimientoBean requerimientoBean) throws Exception {
		return programacionDao.listarEmergenciasActivas(requerimientoBean);
	}
}

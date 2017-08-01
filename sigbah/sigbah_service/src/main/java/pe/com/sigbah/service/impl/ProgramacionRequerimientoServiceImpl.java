package pe.com.sigbah.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.DetalleProgramacionAlimentoBean;
import pe.com.sigbah.common.bean.EstadoProgramacionBean;
import pe.com.sigbah.common.bean.ProductoAlimentoBean;
import pe.com.sigbah.common.bean.ProgramacionAlmacenBean;
import pe.com.sigbah.common.bean.ProgramacionBean;
import pe.com.sigbah.common.bean.RacionOperativaBean;
import pe.com.sigbah.common.bean.RequerimientoBean;
import pe.com.sigbah.dao.ProgramacionRequerimientoDao;
import pe.com.sigbah.service.ProgramacionRequerimientoService;

/**
 * @className: ProgramacionRequerimientoServiceImpl.java
 * @description: 
 * @date: 29/07/2017
 * @author: Junior Huaman Flores.
 */
@Service
public class ProgramacionRequerimientoServiceImpl implements ProgramacionRequerimientoService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProgramacionRequerimientoDao programacionRequerimientoDao;

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#listarProgramacion(pe.com.sigbah.common.bean.ProgramacionBean)
	 */
	@Override
	public List<ProgramacionBean> listarProgramacion(ProgramacionBean programacionBean) throws Exception {
		return programacionRequerimientoDao.listarProgramacion(programacionBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#obtenerCorrelativoProgramacion(pe.com.sigbah.common.bean.ProgramacionBean)
	 */
	@Override
	public ProgramacionBean obtenerCorrelativoProgramacion(ProgramacionBean programacionBean) throws Exception {
		return programacionRequerimientoDao.obtenerCorrelativoProgramacion(programacionBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#obtenerRegistroProgramacion(java.lang.Integer)
	 */
	@Override
	public ProgramacionBean obtenerRegistroProgramacion(Integer idProgramacion) throws Exception {
		return programacionRequerimientoDao.obtenerRegistroProgramacion(idProgramacion);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#listarRequerimiento(pe.com.sigbah.common.bean.RequerimientoBean)
	 */
	@Override
	public List<RequerimientoBean> listarRequerimiento(RequerimientoBean requerimientoBean) throws Exception {
		return programacionRequerimientoDao.listarRequerimiento(requerimientoBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#listarRacionOperativa(pe.com.sigbah.common.bean.RacionOperativaBean)
	 */
	@Override
	public List<RacionOperativaBean> listarRacionOperativa(RacionOperativaBean racionOperativaBean) throws Exception {
		return programacionRequerimientoDao.listarRacionOperativa(racionOperativaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#grabarEstadoProgramacion(pe.com.sigbah.common.bean.EstadoProgramacionBean)
	 */
	@Override
	public EstadoProgramacionBean grabarEstadoProgramacion(EstadoProgramacionBean estadoProgramacionBean) throws Exception {
		return programacionRequerimientoDao.grabarEstadoProgramacion(estadoProgramacionBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#listarEstadoProgramacion(pe.com.sigbah.common.bean.EstadoProgramacionBean)
	 */
	@Override
	public List<EstadoProgramacionBean> listarEstadoProgramacion(EstadoProgramacionBean estadoProgramacionBean) throws Exception {
		return programacionRequerimientoDao.listarEstadoProgramacion(estadoProgramacionBean);
	}
	
	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#grabarProgramacion(pe.com.sigbah.common.bean.ProgramacionBean)
	 */
	@Override
	public ProgramacionBean grabarProgramacion(ProgramacionBean programacionBean) throws Exception {
		return programacionRequerimientoDao.grabarProgramacion(programacionBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#listarProgramacionAlmacen(pe.com.sigbah.common.bean.ProgramacionAlmacenBean)
	 */
	@Override
	public List<ProgramacionAlmacenBean> listarProgramacionAlmacen(ProgramacionAlmacenBean programacionAlmacenBean) throws Exception {
		return programacionRequerimientoDao.listarProgramacionAlmacen(programacionAlmacenBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#grabarProgramacionAlmacen(pe.com.sigbah.common.bean.ProgramacionAlmacenBean)
	 */
	@Override
	public ProgramacionAlmacenBean grabarProgramacionAlmacen(ProgramacionAlmacenBean programacionAlmacenBean) throws Exception {
		return programacionRequerimientoDao.grabarProgramacionAlmacen(programacionAlmacenBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#eliminarProgramacionAlmacen(pe.com.sigbah.common.bean.ProgramacionAlmacenBean)
	 */
	@Override
	public ProgramacionAlmacenBean eliminarProgramacionAlmacen(ProgramacionAlmacenBean programacionAlmacenBean) throws Exception {
		return programacionRequerimientoDao.eliminarProgramacionAlmacen(programacionAlmacenBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#listarProgramacionRacionOperativa(java.lang.Integer)
	 */
	@Override
	public List<RacionOperativaBean> listarProgramacionRacionOperativa(Integer idRacionOperativa) throws Exception {
		return programacionRequerimientoDao.listarProgramacionRacionOperativa(idRacionOperativa);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#actualizarProgramacionRacionOperativa(pe.com.sigbah.common.bean.RacionOperativaBean)
	 */
	@Override
	public RacionOperativaBean actualizarProgramacionRacionOperativa(RacionOperativaBean racionOperativaBean) throws Exception {
		return programacionRequerimientoDao.actualizarProgramacionRacionOperativa(racionOperativaBean);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#obtenerDetalleProgramacionAlimento(java.lang.Integer, java.util.List)
	 */
	@Override
	public DetalleProgramacionAlimentoBean obtenerDetalleProgramacionAlimento(Integer idProgramacion, List<Integer> arrIdProducto) throws Exception {
		return programacionRequerimientoDao.obtenerDetalleProgramacionAlimento(idProgramacion, arrIdProducto);
	}

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.ProgramacionRequerimientoService#actualizarDetalleProgramacionAlimento(pe.com.sigbah.common.bean.ProductoAlimentoBean)
	 */
	@Override
	public ProductoAlimentoBean actualizarDetalleProgramacionAlimento(ProductoAlimentoBean productoAlimentoBean) throws Exception {
		return programacionRequerimientoDao.actualizarDetalleProgramacionAlimento(productoAlimentoBean);
	}

}

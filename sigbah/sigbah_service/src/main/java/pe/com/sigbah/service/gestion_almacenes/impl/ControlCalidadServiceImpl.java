package pe.com.sigbah.service.gestion_almacenes.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.dao.gestion_almacenes.ControlCalidadDao;
import pe.com.sigbah.service.gestion_almacenes.ControlCalidadService;
import pe.com.sigbah.service.impl.GenericServiceImpl;

/**
 * @className: ControlCalidadServiceImpl.java
 * @description: 
 * @date: 19 de jun. de 2017
 * @author: SUMERIO.
 */
@Service
public class ControlCalidadServiceImpl extends GenericServiceImpl implements ControlCalidadService {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ControlCalidadDao controlCalidadDao;

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.gestion_almacenes.ControlCalidadService#listarAnios()
	 */
	@Override
	public List<ItemBean> listarAnios() throws Exception {
		return controlCalidadDao.listarAnios();
	}

}

package pe.com.sigbah.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.DetalleUsuarioBean;
import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.dao.AdministracionDao;
import pe.com.sigbah.service.AdministracionService;

/**
 * @className: AdministracionServiceImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_ADMINISTRACION.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
@Service
public class AdministracionServiceImpl implements AdministracionService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AdministracionDao administracionDao;

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.AdministracionService#obtenerDatosUsuario(pe.com.sigbah.common.bean.UsuarioBean)
	 */
	@Override
	public DetalleUsuarioBean obtenerDatosUsuario(UsuarioBean usuarioBean) throws Exception {
		return administracionDao.obtenerDatosUsuario(usuarioBean);
	}

}

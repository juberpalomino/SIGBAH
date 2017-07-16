package pe.com.sigbah.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

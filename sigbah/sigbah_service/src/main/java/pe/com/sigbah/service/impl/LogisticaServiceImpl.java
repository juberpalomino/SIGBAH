package pe.com.sigbah.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

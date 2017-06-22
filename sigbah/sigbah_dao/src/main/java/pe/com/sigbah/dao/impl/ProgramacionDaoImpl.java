package pe.com.sigbah.dao.impl;

import java.io.Serializable;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import pe.com.sigbah.dao.ProgramacionDao;

/**
 * @className: ProgramacionDaoImpl.java
 * @description: Clase que contiene el consumo de los procedimientos del package BAH_PKG_PROGRAMACION.
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
@Repository
public class ProgramacionDaoImpl extends JdbcDaoSupport implements ProgramacionDao, Serializable {

	private static final long serialVersionUID = 1L;
	private transient final Log LOGGER = LogFactory.getLog(getClass());
	
	private SimpleJdbcCall objJdbcCall;
	
	/**
	 * @param dataSource
	 */
	@Autowired
	public ProgramacionDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

}

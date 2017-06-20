package pe.com.sigbah.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import pe.com.sigbah.common.bean.UbigeoBean;
import pe.com.sigbah.service.IMaestroService;

/**
 * @className: MaestroServiceImpl.java
 * @description: 
 * @date: 16 de jun. de 2016
 * @author: SUMERIO.
 */
@Service
public class MaestroServiceImpl extends GenericServiceImpl implements IMaestroService {

	private static final long serialVersionUID = 1L;
	
//	@Autowired
//	private MaestroMapper maestroMapper;
	

	/* (non-Javadoc)
	 * @see pe.com.sigbah.service.IMaestroService#listarUbigeo(pe.com.sigbah.common.bean.UbigeoBean)
	 */
	@Override
	public List<UbigeoBean> listarUbigeo(UbigeoBean ubigeo) throws Exception {
		return null;
//		return maestroMapper.listarUbigeo(ubigeo);
	}
		
}

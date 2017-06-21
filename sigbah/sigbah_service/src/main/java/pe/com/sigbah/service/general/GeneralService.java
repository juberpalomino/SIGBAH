package pe.com.sigbah.service.general;

import java.util.List;

import pe.com.sigbah.common.bean.ItemBean;
import pe.com.sigbah.common.bean.UbigeoBean;

/**
 * @className: GeneralService.java
 * @description: 
 * @date: 20 de jun. de 2017
 * @author: SUMERIO.
 */
public interface GeneralService {
	
	/**
	 * @param ubigeoBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<UbigeoBean> listarDepartamentos(UbigeoBean ubigeoBean) throws Exception;
	
	/**
	 * @param ubigeoBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<UbigeoBean> listarProvincia(UbigeoBean ubigeoBean) throws Exception;
	
	/**
	 * @param ubigeoBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<UbigeoBean> listarDistrito(UbigeoBean ubigeoBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarFenomenos(ItemBean itemBean) throws Exception;
	
	/**
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarAnios() throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarMeses(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarDdi(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarRegion(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarAlmacen(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarUnidadMedida(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarEnvase(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarEstado(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarAlmacenExterno(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarTipoMovimiento(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarTipoDocumento(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarTipoCamion(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarTipoControlCalidad(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarProveedor(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarPersonal(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarPersonalExterno(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarModAlmacen(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarFuenteFinanciamiento(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarMarca(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarDonante(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarMoneda(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarPais(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarCategoria(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarDee(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarCatologoProductos(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarMedioTransporte(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarEmpresaTransporte(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarEjecutora(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarChofer(ItemBean itemBean) throws Exception;
	
	/**
	 * @param itemBean
	 * @return Lista de registros.
	 * @throws Exception
	 */
	public abstract List<ItemBean> listarParametro(ItemBean itemBean) throws Exception;
	
	
/*	
	 PROCEDURE USP_SEL_TAB_DEPARTAMENTO(pi_COD_DEPARTAMENTO IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_PROVINCIA(pi_COD_DEPARTAMENTO IN varchar,pi_COD_PROVINCIA IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_DISTRITO(pi_COD_DEPARTAMENTO IN varchar,pi_COD_PROVINCIA IN varchar, pi_COD_DISTRITO IN varchar,  po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_FENOMENOS(pi_IDE_FENOMENO IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_ANIOS(po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_MESES(pi_IDE_MES IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_DDI(pi_IDE_DDI IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_REGION(pi_COD_REGION IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_ALMACEN(pi_IDE_DDI IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_UNIDAD_MEDIDA(pi_IDE_UND_MEDIDA IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_ENVASE(pi_IDE_ENVASE IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_ESTADO(pi_IDE_ESTADO IN varchar,pi_tipo in integer, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_ALMACEN_EXT(pi_IDE_REGION IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_TIP_MOVIMIENTO(pi_IDE_TIP_MOVIMIENTO IN varchar, pi_FLG_TIPO IN varchar , po_Lr_Recordset OUT SYS_REFCURSOR); 
	 PROCEDURE USP_SEL_TAB_TIP_DOCUMENTO(pi_IDE_TIP_DOCUMENTO IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);  
	 PROCEDURE USP_SEL_TAB_TIP_CAMION(pi_IDE_TIP_CAMION IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);  
	 PROCEDURE USP_SEL_TAB_TIP_CONTROL_CALIDA(pi_IDE_TIP_CONTROL IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR); 
	 PROCEDURE USP_SEL_TAB_PROVEEDOR(pi_IDE_PROVEEDOR IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR); 
	 PROCEDURE USP_SEL_TAB_PERSONAL(pi_IDE_DDI IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);  
	 PROCEDURE USP_SEL_TAB_PERSONAL_EXT(pi_IDE_REGION IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);  
	 PROCEDURE USP_SEL_TAB_MOD_ALMACEN(pi_IDE_MOD_ALMACEN IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_FUENTE_FINANC(pi_IDE_FTE_FINANC IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_MARCA(pi_IDE_MARCA IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_DONANTE(pi_IDE_DONANTE IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_MONEDA(pi_IDE_MONEDA IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_PAIS(pi_IDE_PAIS IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_CATEGORIA_PRODUC(pi_IDE_CATEGORIA IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_DEE(pi_IDE_DEE IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_CATALOGO_PRODUCTOS(pi_IDE_CATEGORIA_BAH IN integer, pi_IDE_CAT_PRODUCTO IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_MEDIO_TRANSPORTE(pi_IDE_MED_TRANSPORTE IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_EMP_TRANSPORTE(pi_IDE_DDI IN integer, pi_IDE_MED_TRANSPORTE IN integer,pi_IDE_EMP_TRANS IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_EJECUTORA(po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_CHOFER(pi_IDE_EMP_TRANS IN integer, pi_IDE_CHOFER IN varchar, po_Lr_Recordset OUT SYS_REFCURSOR);
	 PROCEDURE USP_SEL_TAB_PARAMETRO(pi_IDE_PARAMETRO IN integer, po_Lr_Recordset OUT SYS_REFCURSOR);
*/

}

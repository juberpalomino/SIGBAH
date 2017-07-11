package pe.com.sigbah.web.report.gestion_almacenes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import pe.com.sigbah.common.bean.OrdenSalidaBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;

/**
 * @className: ReporteOrdenSalida.java
 * @description: 
 * @date: 20 de jul. de 2016
 * @author: SUMERIO.
 */
public class ReporteOrdenSalida implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Log LOGGER = LogFactory.getLog(DateUtil.class.getName());
	
	private HSSFColor setColor(HSSFWorkbook workbook, byte r, byte g, byte b) {
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor hssfColor = null;
		try {
			hssfColor= palette.findColor(r, g, b); 
			if (hssfColor == null) {
			    palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g,b);
			    hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
			}
		 } catch (Exception e) {
			 LOGGER.error(e);
		 }
		 return hssfColor;
	}

	/**
	 * @param lista
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaReporteExcelOrdenSalida(List<OrdenSalidaBean> lista) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		try {				
	        HSSFSheet sheet = wb.createSheet("REGISTRO DE ORDEN DE INGRESO");
	        
	        sheet.setColumnWidth(1, 1500);
	        sheet.setColumnWidth(2, 2000);
	        sheet.setColumnWidth(3, 5000);
	        sheet.setColumnWidth(4, 5000);
	        sheet.setColumnWidth(5, 7000);
	        sheet.setColumnWidth(6, 5000);
			sheet.setColumnWidth(7, 6500);
			sheet.setColumnWidth(8, 4000);
	        
			HSSFRow row1 = sheet.createRow((short) 1);
	        
	        HSSFFont font_bold = wb.createFont();
	        font_bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        
	        HSSFFont font_norm = wb.createFont();
	        font_norm.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            
	        
	        HSSFCellStyle style_header = (HSSFCellStyle) wb.createCellStyle();
	        style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style_header.setFont(font_bold);
	        
	        HSSFColor color = setColor(wb, (byte) 242, (byte)242, (byte) 242);
	        style_header.setFillForegroundColor(color.getIndex());
	        style_header.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style_header.setBorderBottom((short) 1);
	        style_header.setBorderLeft((short) 1);	        
	        style_header.setBorderRight((short) 1);
	        style_header.setBorderTop((short) 1);
	        
	        row1.createCell(1).setCellValue("Item");
	        row1.getCell(1).setCellStyle(style_header);
	        
	        row1.createCell(2).setCellValue("Año");
	        row1.getCell(2).setCellStyle(style_header);
	        
	        row1.createCell(3).setCellValue("DDI");
	        row1.getCell(3).setCellStyle(style_header);
	        
	        row1.createCell(4).setCellValue("Almacén");
	        row1.getCell(4).setCellStyle(style_header);
	        
	        row1.createCell(5).setCellValue("N° Orden de Salida");
	        row1.getCell(5).setCellStyle(style_header);
	        
	        row1.createCell(6).setCellValue("Fecha");
	        row1.getCell(6).setCellStyle(style_header);
	        
	        row1.createCell(7).setCellValue("Tipo de Movimiento");
	        row1.getCell(7).setCellStyle(style_header);
	        
	        row1.createCell(8).setCellValue("Estado");
	        row1.getCell(8).setCellStyle(style_header);
	       
	        int row = 1;

	        HSSFCellStyle style_cell = (HSSFCellStyle) wb.createCellStyle();
	        style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        style_cell.setFont(font_norm);	        
	        style_cell.setBorderBottom((short) 1);
	        style_cell.setBorderLeft((short) 1);	        
	        style_cell.setBorderRight((short) 1);
	        style_cell.setBorderTop((short) 1);
	        
	        for (OrdenSalidaBean ingreso : lista) {
	        	
	        	HSSFRow rows  = sheet.createRow((short) row + 1);
	        	
	        	rows.createCell(1).setCellValue(row);
		        rows.getCell(1).setCellStyle(style_cell);

		        rows.createCell(2).setCellValue(ingreso.getCodigoAnio());
		        rows.getCell(2).setCellStyle(style_cell);
		        
		        rows.createCell(3).setCellValue(ingreso.getNombreDdi());
		        rows.getCell(3).setCellStyle(style_cell);
		        
		        rows.createCell(4).setCellValue(ingreso.getNombreAlmacen());
		        rows.getCell(4).setCellStyle(style_cell);
		        
		        rows.createCell(5).setCellValue(ingreso.getNroOrdenSalida());
		        rows.getCell(5).setCellStyle(style_cell);
		        
		        rows.createCell(6).setCellValue(ingreso.getFechaEmision());
		        rows.getCell(6).setCellStyle(style_cell);
		        
		        rows.createCell(7).setCellValue(ingreso.getNombreMovimiento());
		        rows.getCell(7).setCellStyle(style_cell);
		        
		        rows.createCell(8).setCellValue(ingreso.getNombreEstado());
		        rows.getCell(8).setCellStyle(style_cell);
	            
	            row++;	
	        }
			
    	} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
    	}
		return wb;
	}

	
	/**
	 * Retorna el valor parseado.
	 * @param campo - Valor del parámetro a evaluar, tipo Object.
	 * @return valor - Valor de la cadena.
	 */
	private static String getString(Object campo) {
		if (campo != null) {
			if (campo instanceof Integer) {
				return String.valueOf((Integer) campo);
			} else if (campo instanceof Long) {
				return String.valueOf((Long) campo);
			} else if (campo instanceof BigDecimal) {
				return String.valueOf((BigDecimal) campo);
			} else {
				return (String) campo;
			}
		}
		return Constantes.EMPTY; 	
	}
    
}
package pe.com.sigbah.web.report.programacion_bah;

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

import pe.com.sigbah.common.bean.ProductoAlimentoBean;
import pe.com.sigbah.common.bean.ProgramacionAlimentoBean;
import pe.com.sigbah.common.bean.ProgramacionBean;
import pe.com.sigbah.common.bean.ProgramacionNoAlimentarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;

/**
 * @className: ReporteProgramacion.java
 * @description: 
 * @date: 20 de jul. de 2017
 * @author: Junior Huaman Flores.
 */
public class ReporteProgramacion implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Log LOGGER = LogFactory.getLog(DateUtil.class.getName());
	
	/**
	 * @param lista
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaReporteExcelProgramacion(List<ProgramacionBean> lista) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		try {				
	        HSSFSheet sheet = wb.createSheet("REGISTRO DE PROGRAMACIONES");
	        
	        sheet.setColumnWidth(1, 1500);
	        sheet.setColumnWidth(2, 2000);
	        sheet.setColumnWidth(3, 4000);
	        sheet.setColumnWidth(4, 5000);
	        sheet.setColumnWidth(5, 6000);
	        sheet.setColumnWidth(6, 9000);
			sheet.setColumnWidth(7, 9000);
			sheet.setColumnWidth(8, 6000);
			sheet.setColumnWidth(10, 6000);
	        
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
	        
	        row1.createCell(1).setCellValue("Nº");
	        row1.getCell(1).setCellStyle(style_header);
	        
	        row1.createCell(2).setCellValue("Año");
	        row1.getCell(2).setCellStyle(style_header);
	        
	        row1.createCell(3).setCellValue("Mes");
	        row1.getCell(3).setCellStyle(style_header);
	        
	        row1.createCell(4).setCellValue("N° Programación");
	        row1.getCell(4).setCellStyle(style_header);
	        
	        row1.createCell(5).setCellValue("Fecha Programación");
	        row1.getCell(5).setCellStyle(style_header);
	        
	        row1.createCell(6).setCellValue("Fenómeno");
	        row1.getCell(6).setCellStyle(style_header);
	        
	        row1.createCell(7).setCellValue("Emergencia");
	        row1.getCell(7).setCellStyle(style_header);
	        
	        row1.createCell(8).setCellValue("N° DEE");
	        row1.getCell(8).setCellStyle(style_header);
	        
	        row1.createCell(9).setCellValue("Estado");
	        row1.getCell(9).setCellStyle(style_header);
	        
	        row1.createCell(10).setCellValue("Región Destino");
	        row1.getCell(10).setCellStyle(style_header);
	       
	        int row = 1;

	        HSSFCellStyle style_cell = (HSSFCellStyle) wb.createCellStyle();
	        style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        style_cell.setFont(font_norm);	        
	        style_cell.setBorderBottom((short) 1);
	        style_cell.setBorderLeft((short) 1);	        
	        style_cell.setBorderRight((short) 1);
	        style_cell.setBorderTop((short) 1);
	        
	        for (ProgramacionBean programacion : lista) {
	        	
	        	HSSFRow rows  = sheet.createRow((short) row + 1);
	        	
	        	rows.createCell(1).setCellValue(row);
		        rows.getCell(1).setCellStyle(style_cell);

		        rows.createCell(2).setCellValue(programacion.getCodigoAnio());
		        rows.getCell(2).setCellStyle(style_cell);
		        
		        rows.createCell(3).setCellValue(programacion.getNombreMes());
		        rows.getCell(3).setCellStyle(style_cell);
		        
		        rows.createCell(4).setCellValue(programacion.getNroProgramacion());
		        rows.getCell(4).setCellStyle(style_cell);
		        
		        rows.createCell(5).setCellValue(programacion.getFechaProgramacion());
		        rows.getCell(5).setCellStyle(style_cell);
		        
		        rows.createCell(6).setCellValue(programacion.getNombreFenomeno());
		        rows.getCell(6).setCellStyle(style_cell);
		        
		        rows.createCell(7).setCellValue(programacion.getNombreProgramacion());
		        rows.getCell(7).setCellStyle(style_cell);
		        
		        rows.createCell(8).setCellValue(programacion.getNroDee());
		        rows.getCell(8).setCellStyle(style_cell);
		        
		        rows.createCell(9).setCellValue(programacion.getNombreEstado());
		        rows.getCell(9).setCellStyle(style_cell);
		        
		        rows.createCell(10).setCellValue(programacion.getNombreRegion());
		        rows.getCell(10).setCellStyle(style_cell);
	            
	            row++;	
	        }
			
    	} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
    	}
		return wb;
	}
	
	/**
	 * @param workbook
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
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
	 * @param listaProgramacionAlimento 
	 * @param arrIdProducto
	 * @param arrNombreProducto
	 * @param arrUnidadProducto 
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaReporteExcelAlimento(List<ProgramacionAlimentoBean> listaProgramacionAlimento,
												   List<Integer> arrIdProducto, 
												   List<String> arrNombreProducto, 
												   List<BigDecimal> arrUnidadProducto) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		try {				
	        HSSFSheet sheet = wb.createSheet("REGISTRO DE ALIMENTOS");
	        
	        sheet.setColumnWidth(1, 1500);
	        sheet.setColumnWidth(2, 6000);
	        sheet.setColumnWidth(3, 6000);
	        sheet.setColumnWidth(4, 6000);
	        sheet.setColumnWidth(5, 6000);
	        sheet.setColumnWidth(6, 6000);
			sheet.setColumnWidth(7, 6000);
			sheet.setColumnWidth(8, 6000);
			sheet.setColumnWidth(9, 6000);
			sheet.setColumnWidth(10, 6000);
			sheet.setColumnWidth(11, 6000);
			sheet.setColumnWidth(12, 6000);
			sheet.setColumnWidth(13, 6000);
			sheet.setColumnWidth(14, 6000);
			sheet.setColumnWidth(15, 6000);
			sheet.setColumnWidth(16, 6000);
			sheet.setColumnWidth(17, 6000);
			sheet.setColumnWidth(18, 6000);
			sheet.setColumnWidth(19, 6000);
			sheet.setColumnWidth(20, 6000);
	        
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
	        
	        row1.createCell(1).setCellValue("Nº");
	        row1.getCell(1).setCellStyle(style_header);
	        
	        row1.createCell(2).setCellValue("Departamento");
	        row1.getCell(2).setCellStyle(style_header);
	        
	        row1.createCell(3).setCellValue("Provincia");
	        row1.getCell(3).setCellStyle(style_header);
	        
	        row1.createCell(4).setCellValue("Distrito");
	        row1.getCell(4).setCellStyle(style_header);
	        
	        row1.createCell(5).setCellValue("Pers. Afect.");
	        row1.getCell(5).setCellStyle(style_header);
	        
	        row1.createCell(6).setCellValue("Pers. Dam.");
	        row1.getCell(6).setCellStyle(style_header);
	        
	        row1.createCell(7).setCellValue("Total Pers.");
	        row1.getCell(7).setCellStyle(style_header);
	        
	        row1.createCell(8).setCellValue("Total Raciones");
	        row1.getCell(8).setCellStyle(style_header);
	        
	        int row1_9 = 9;
	        for (String producto : arrNombreProducto) {
	        	row1.createCell(row1_9).setCellValue(producto);
		        row1.getCell(row1_9).setCellStyle(style_header);
		        row1_9++;
	        }
	        
	        row1.createCell(row1_9).setCellValue("Total (TM)");
	        row1.getCell(row1_9).setCellStyle(style_header);
	       
	        int row = 1;

	        HSSFCellStyle style_cell = (HSSFCellStyle) wb.createCellStyle();
	        style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        style_cell.setFont(font_norm);	        
	        style_cell.setBorderBottom((short) 1);
	        style_cell.setBorderLeft((short) 1);	        
	        style_cell.setBorderRight((short) 1);
	        style_cell.setBorderTop((short) 1);
	        

	        for (ProgramacionAlimentoBean alimento : listaProgramacionAlimento) {
	        	
	        	HSSFRow rows  = sheet.createRow((short) row + 1);
	        	
	        	rows.createCell(1).setCellValue(row);
		        rows.getCell(1).setCellStyle(style_cell);

		        rows.createCell(2).setCellValue(alimento.getDepartamento());
		        rows.getCell(2).setCellStyle(style_cell);
		        
		        rows.createCell(3).setCellValue(alimento.getProvincia());
		        rows.getCell(3).setCellStyle(style_cell);
		        
		        rows.createCell(4).setCellValue(alimento.getDistrito());
		        rows.getCell(4).setCellStyle(style_cell);
		        
		        rows.createCell(5).setCellValue(getString(alimento.getPersAfect()));
		        rows.getCell(5).setCellStyle(style_cell);
		        
		        rows.createCell(6).setCellValue(getString(alimento.getPersDam()));
		        rows.getCell(6).setCellStyle(style_cell);
		        
		        rows.createCell(7).setCellValue(getString(alimento.getTotalPers()));
		        rows.getCell(7).setCellStyle(style_cell);
		        
		        rows.createCell(8).setCellValue(getString(alimento.getTotalRaciones()));
		        rows.getCell(8).setCellStyle(style_cell);
		        
		        int row2_9 = 9;
		        for (ProductoAlimentoBean producto : alimento.getListaProducto()) {
		        	rows.createCell(row2_9).setCellValue(getString(producto.getUnidad()));
			        rows.getCell(row2_9).setCellStyle(style_cell);
			        row2_9++;
		        }

		        rows.createCell(row2_9).setCellValue(getString(alimento.getTotalTm()));
		        rows.getCell(row2_9).setCellStyle(style_cell);
	            
	            row++;	
	        }
	        
	        HSSFRow row_total  = sheet.createRow((short) row + 1);
        	
	        row_total.createCell(4).setCellValue("Total:");
	        row_total.getCell(4).setCellStyle(style_cell);

	        int row3_5 = 5;
	        for (BigDecimal unidad : arrUnidadProducto) {
	        	row_total.createCell(row3_5).setCellValue(getString(unidad));
		        row_total.getCell(row3_5).setCellStyle(style_cell);
		        row3_5++;
	        }

            row++;
			
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

	/**
	 * @param listaProgramacionNoAlimentario
	 * @param arrIdProducto
	 * @param arrNombreProducto
	 * @param arrUnidadProducto
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaReporteExcelNoAlimentario(List<ProgramacionNoAlimentarioBean> listaProgramacionNoAlimentario, 
														List<Integer> arrIdProducto,
														List<String> arrNombreProducto, 
														List<BigDecimal> arrUnidadProducto) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		try {				
	        HSSFSheet sheet = wb.createSheet("REGISTRO DE NO ALIMENTARIOS");
	        
	        sheet.setColumnWidth(1, 1500);
	        sheet.setColumnWidth(2, 6000);
	        sheet.setColumnWidth(3, 6000);
	        sheet.setColumnWidth(4, 6000);
	        sheet.setColumnWidth(5, 6000);
	        sheet.setColumnWidth(6, 6000);
			sheet.setColumnWidth(7, 6000);
			sheet.setColumnWidth(8, 6000);
			sheet.setColumnWidth(9, 6000);
			sheet.setColumnWidth(10, 6000);
			sheet.setColumnWidth(11, 6000);
			sheet.setColumnWidth(12, 6000);
			sheet.setColumnWidth(13, 6000);
			sheet.setColumnWidth(14, 6000);
			sheet.setColumnWidth(15, 6000);
			sheet.setColumnWidth(16, 6000);
			sheet.setColumnWidth(17, 6000);
			sheet.setColumnWidth(18, 6000);
			sheet.setColumnWidth(19, 6000);
			sheet.setColumnWidth(20, 6000);
			sheet.setColumnWidth(21, 6000);
			sheet.setColumnWidth(22, 6000);
			sheet.setColumnWidth(23, 6000);
			sheet.setColumnWidth(24, 6000);
			sheet.setColumnWidth(25, 6000);
	        
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
	        
	        row1.createCell(1).setCellValue("Nº");
	        row1.getCell(1).setCellStyle(style_header);
	        
	        row1.createCell(2).setCellValue("Departamento");
	        row1.getCell(2).setCellStyle(style_header);
	        
	        row1.createCell(3).setCellValue("Provincia");
	        row1.getCell(3).setCellStyle(style_header);
	        
	        row1.createCell(4).setCellValue("Distrito");
	        row1.getCell(4).setCellStyle(style_header);
	        
	        row1.createCell(5).setCellValue("Fam. Afect.");
	        row1.getCell(5).setCellStyle(style_header);
	        
	        row1.createCell(6).setCellValue("Fam. Dam.");
	        row1.getCell(6).setCellStyle(style_header);
	        
	        row1.createCell(7).setCellValue("Total Fam.");
	        row1.getCell(7).setCellStyle(style_header);
	        
	        row1.createCell(8).setCellValue("Pers. Afect.");
	        row1.getCell(8).setCellStyle(style_header);
	        
	        row1.createCell(9).setCellValue("Pers. Dam.");
	        row1.getCell(9).setCellStyle(style_header);
	        
	        row1.createCell(10).setCellValue("Total Pers.");
	        row1.getCell(10).setCellStyle(style_header);
	        
	        int row1_11 = 11;
	        for (String producto : arrNombreProducto) {
	        	row1.createCell(row1_11).setCellValue(producto);
		        row1.getCell(row1_11).setCellStyle(style_header);
		        row1_11++;
	        }
	        
//	        row1.createCell(row1_11).setCellValue("Total (TM)");
//	        row1.getCell(row1_11).setCellStyle(style_header);
	       
	        int row = 1;

	        HSSFCellStyle style_cell = (HSSFCellStyle) wb.createCellStyle();
	        style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        style_cell.setFont(font_norm);	        
	        style_cell.setBorderBottom((short) 1);
	        style_cell.setBorderLeft((short) 1);	        
	        style_cell.setBorderRight((short) 1);
	        style_cell.setBorderTop((short) 1);
	        

	        for (ProgramacionNoAlimentarioBean alimento : listaProgramacionNoAlimentario) {
	        	
	        	HSSFRow rows  = sheet.createRow((short) row + 1);
	        	
	        	rows.createCell(1).setCellValue(row);
		        rows.getCell(1).setCellStyle(style_cell);

		        rows.createCell(2).setCellValue(alimento.getDepartamento());
		        rows.getCell(2).setCellStyle(style_cell);
		        
		        rows.createCell(3).setCellValue(alimento.getProvincia());
		        rows.getCell(3).setCellStyle(style_cell);
		        
		        rows.createCell(4).setCellValue(alimento.getDistrito());
		        rows.getCell(4).setCellStyle(style_cell);
		        
		        rows.createCell(5).setCellValue(getString(alimento.getFamAfect()));
		        rows.getCell(5).setCellStyle(style_cell);
		        
		        rows.createCell(6).setCellValue(getString(alimento.getFamDam()));
		        rows.getCell(6).setCellStyle(style_cell);
		        
		        rows.createCell(7).setCellValue(getString(alimento.getTotalFam()));
		        rows.getCell(7).setCellStyle(style_cell);
		        
		        rows.createCell(8).setCellValue(getString(alimento.getPersAfect()));
		        rows.getCell(8).setCellStyle(style_cell);
		        
		        rows.createCell(9).setCellValue(getString(alimento.getPersDam()));
		        rows.getCell(9).setCellStyle(style_cell);
		        
		        rows.createCell(10).setCellValue(getString(alimento.getTotalPers()));
		        rows.getCell(10).setCellStyle(style_cell);
		        
		        int row2_11 = 11;
		        for (ProductoAlimentoBean producto : alimento.getListaProducto()) {
		        	rows.createCell(row2_11).setCellValue(getString(producto.getUnidad()));
			        rows.getCell(row2_11).setCellStyle(style_cell);
			        row2_11++;
		        }

//		        rows.createCell(row2_11).setCellValue(getString(alimento.getTotalTm()));
//		        rows.getCell(row2_11).setCellStyle(style_cell);
	            
	            row++;	
	        }
	        
	        HSSFRow row_total  = sheet.createRow((short) row + 1);
        	
	        row_total.createCell(4).setCellValue("Total:");
	        row_total.getCell(4).setCellStyle(style_cell);

	        int row3_5 = 5;
	        for (BigDecimal unidad : arrUnidadProducto) {
	        	row_total.createCell(row3_5).setCellValue(getString(unidad));
		        row_total.getCell(row3_5).setCellStyle(style_cell);
		        row3_5++;
	        }

            row++;
			
    	} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
    	}
		return wb;
	}
    
}
//package pe.com.sigbah.common.util; 
//
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.text.DateFormat;
//import java.text.DecimalFormat;
//import java.text.DecimalFormatSymbols;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFPalette;
//import org.apache.poi.hssf.usermodel.HSSFRichTextString;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.RichTextString;
//import org.apache.poi.ss.util.CellRangeAddress;
//
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import pe.com.sigbah.model.bean.CartaFianzaBean;
//import pe.com.sigbah.model.bean.ComprasIngresosBean;
//import pe.com.sigbah.model.bean.ReporteDetalleComprasBean;
//import pe.com.sigbah.model.bean.ReporteDetalleIngresosBean;
//
///**
// * @className: Reporteador.java
// * @description: 
// * @date: 20 de jul. de 2016
// * @author: SUMERIO.
// */
//public class Reporteador implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//	private static final Log log = LogFactory.getLog(Reporteador.class);
//	
//	/**
//	 * @param response
//	 * @param lista
//	 */
//	public void generaExcelReporteCartaFianza(HttpServletResponse response, List<CartaFianzaBean> lista) {
//		try{
//	    	
//			HSSFWorkbook wb = new HSSFWorkbook();				
//	        HSSFSheet sheet = wb.createSheet("REGISTRO DE CARTAS FIANZA");
//	        
//	            sheet.setColumnWidth(0, 1000);
//		        sheet.setColumnWidth(1, 2000);
//		        sheet.setColumnWidth(2, 2000);
//		        sheet.setColumnWidth(3, 4000);
//		        sheet.setColumnWidth(4, 4000);
//		        sheet.setColumnWidth(5, 4000);
//		        sheet.setColumnWidth(6, 4000);
//				sheet.setColumnWidth(7, 4000);
//				sheet.setColumnWidth(8, 4000);
//				sheet.setColumnWidth(9, 4000);
//				sheet.setColumnWidth(10, 4000);
//				sheet.setColumnWidth(11, 4000);
//				sheet.setColumnWidth(12, 4000);
//				sheet.setColumnWidth(13, 8000);
//				sheet.setColumnWidth(14, 8000);
//				sheet.setColumnWidth(15, 4000);
//				sheet.setColumnWidth(16, 4000);
//				sheet.setColumnWidth(20, 4000);
//				
//				sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 7));
//			
//			HSSFRow row1 = sheet.createRow((short) 1);
////	        HSSFRow row3 = sheet.createRow((short) 3);
////	        HSSFRow row4 = sheet.createRow((short) 4);
//	        HSSFRow row5 = sheet.createRow((short) 5);
//	        HSSFRow row6 = sheet.createRow((short) 6);
//	        HSSFRow row8 = sheet.createRow((short) 8);
//	        
//	        HSSFFont font_bold = wb.createFont();
//	        font_bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//	        
//	        HSSFFont font_norm = wb.createFont();
//	        font_norm.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//	        
//	        DecimalFormatSymbols other_symbols = new DecimalFormatSymbols(Locale.US);
//            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, other_symbols);
//	        
//	        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//	        Date date = new Date();
//            
//	        HSSFRichTextString fec_ini = new HSSFRichTextString(dateFormat.format(date).toString());
//	        HSSFRichTextString fec_emi = new HSSFRichTextString("Fecha de Emisión: ");
//	        RichTextString fec_rango = new HSSFRichTextString(fec_emi +fec_ini.getString());
//	       
//	        row5.createCell(1).setCellValue(fec_rango);
//	        
//	        HSSFCellStyle style_tit = (HSSFCellStyle) wb.createCellStyle();
//	        style_tit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style_tit.setFont(font_bold);
//	        
//	        String titulo = null;
//             	titulo = "REGISTRO DE CARTAS FIANZA";
//  	
//            row1.createCell(5).setCellValue(titulo);	        
//	        row1.getCell(5).setCellStyle(style_tit);
//	        
//	        HSSFCellStyle style_header = (HSSFCellStyle) wb.createCellStyle();
//	        style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style_header.setFont(font_bold);	        
//	        HSSFColor color = setColor(wb, (byte) 242, (byte)242, (byte) 242);
//	        style_header.setFillForegroundColor(color.getIndex());
//	        style_header.setFillPattern(CellStyle.SOLID_FOREGROUND);
//	        style_header.setBorderBottom((short) 1);
//	        style_header.setBorderLeft((short) 1);	        
//	        style_header.setBorderRight((short) 1);
//	        style_header.setBorderTop((short) 1);
//	        
//	        
//	        HSSFCellStyle style_bottom = (HSSFCellStyle) wb.createCellStyle();
//	        
//				row6.createCell(2).setCellValue("");
//				row6.getCell(2).setCellStyle(style_bottom);
//				row6.createCell(3).setCellValue("");
//				row6.getCell(3).setCellStyle(style_bottom);
//				row6.createCell(5).setCellValue("");
//				row6.getCell(5).setCellStyle(style_bottom);
//				row6.createCell(6).setCellValue("");
//				row6.getCell(6).setCellStyle(style_bottom);
//				row6.createCell(7).setCellValue("");
//				row6.getCell(7).setCellStyle(style_bottom);
//				row6.createCell(8).setCellValue("");
//				row6.getCell(8).setCellStyle(style_bottom);
//				row6.createCell(9).setCellValue("");
//				row6.getCell(9).setCellStyle(style_bottom);
//				row6.createCell(11).setCellValue("");
//				row6.getCell(11).setCellStyle(style_bottom);
//				row6.createCell(12).setCellValue("");
//				row6.getCell(12).setCellStyle(style_bottom);
//				row6.createCell(13).setCellValue("");
//				row6.getCell(13).setCellStyle(style_bottom);
//				row6.createCell(14).setCellValue("");
//				row6.getCell(14).setCellStyle(style_bottom);
//				row6.createCell(15).setCellValue("");
//				row6.getCell(15).setCellStyle(style_bottom);				
//				row6.createCell(16).setCellValue("");
//				row6.getCell(16).setCellStyle(style_bottom);	
//				
//		        row8.createCell(1).setCellValue("Item");
//		        row8.getCell(1).setCellStyle(style_header);
//		        
//		        row8.createCell(2).setCellValue("Cod. Docu.");
//		        row8.getCell(2).setCellStyle(style_header);
//		        
//		        row8.createCell(3).setCellValue("Num. Docu.");
//		        row8.getCell(3).setCellStyle(style_header);
//		        
//		        row8.createCell(4).setCellValue("Sernanp");
//		        row8.getCell(4).setCellStyle(style_header);
//		        
//		        row8.createCell(5).setCellValue("Siglas Ofi.");
//		        row8.getCell(5).setCellStyle(style_header);
//		        
//		        row8.createCell(6).setCellValue("Fec. Docu.");
//		        row8.getCell(6).setCellStyle(style_header);
//		        
//		        row8.createCell(7).setCellValue("Fec. Recep.");
//		        row8.getCell(7).setCellStyle(style_header);
//		        
//		        row8.createCell(8).setCellValue("Empresa");
//		        row8.getCell(8).setCellStyle(style_header);
//		        
//		        row8.createCell(9).setCellValue("Monto");
//		        row8.getCell(9).setCellStyle(style_header);
//		        
//		        row8.createCell(10).setCellValue("Inicio");
//		        row8.getCell(10).setCellStyle(style_header);
//		        
//		        row8.createCell(11).setCellValue("Fin");
//		        row8.getCell(11).setCellStyle(style_header);
//		
//		        row8.createCell(12).setCellValue("Num. Carta");
//		        row8.getCell(12).setCellStyle(style_header);
//		
//		        row8.createCell(13).setCellValue("Entidad Financiera");
//		        row8.getCell(13).setCellStyle(style_header);
//		
//		        row8.createCell(14).setCellValue("Concepto");
//		        row8.getCell(14).setCellStyle(style_header);
//		
//		        row8.createCell(15).setCellValue("Doc. Devolución");
//		        row8.getCell(15).setCellStyle(style_header);
//		        
//		        row8.createCell(16).setCellValue("Legalidad");
//		        row8.getCell(16).setCellStyle(style_header);
//		        
//		        row8.createCell(17).setCellValue("Estado");
//		        row8.getCell(17).setCellStyle(style_header);
//		 
//		        int row = 1;
//	
//		        HSSFCellStyle style_cell = (HSSFCellStyle) wb.createCellStyle();
//		        style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//		        style_cell.setFont(font_norm);	        
//		        style_cell.setBorderBottom((short) 1);
//		        style_cell.setBorderLeft((short) 1);	        
//		        style_cell.setBorderRight((short) 1);
//		        style_cell.setBorderTop((short) 1);
//		        
//		        for (CartaFianzaBean rep_compra : lista) {
//		        	
//		        	HSSFRow rows  = sheet.createRow((short) row + 8);
//		        	
//		        	rows.createCell(1).setCellValue(row);
//			        rows.getCell(1).setCellStyle(style_cell);
//
//			        rows.createCell(2).setCellValue(rep_compra.getConcepto());
//			        rows.getCell(2).setCellStyle(style_cell);
//			        
//			        rows.createCell(3).setCellValue(rep_compra.getChrNumCarta());
//			        rows.getCell(3).setCellStyle(style_cell);
//			        
//			        rows.createCell(4).setCellValue("SERNANP");
//			        rows.getCell(4).setCellStyle(style_cell);
//			        
//			        rows.createCell(5).setCellValue(rep_compra.getChrSigOficina());
//			        rows.getCell(5).setCellStyle(style_cell);
//			        
//			        rows.createCell(6).setCellValue(rep_compra.getDteFecCarta());
//			        rows.getCell(6).setCellStyle(style_cell);
//			        
//			        rows.createCell(7).setCellValue(rep_compra.getDteFecRecepcion());
//			        rows.getCell(7).setCellStyle(style_cell);
//			        
//			        rows.createCell(8).setCellValue(rep_compra.getRazonsocial());
//			        rows.getCell(8).setCellStyle(style_cell);
//			        
//			        rows.createCell(9).setCellValue(dec_form.format(rep_compra.getIntImpCarta()));
//			        rows.getCell(9).setCellStyle(style_cell);		   
//		
//		            rows.createCell(10).setCellValue(rep_compra.getDteFecInicio());
//			        rows.getCell(10).setCellStyle(style_cell);
//		            
//			        rows.createCell(11).setCellValue(rep_compra.getDteFecFinal());
//			        rows.getCell(11).setCellStyle(style_cell);
//			        
//			        rows.createCell(12).setCellValue(rep_compra.getChrNumFianza());
//			        rows.getCell(12).setCellStyle(style_cell);
//		
//			        rows.createCell(13).setCellValue(rep_compra.getNom_entidad());
//			        rows.getCell(13).setCellStyle(style_cell);
//		
//			        rows.createCell(14).setCellValue(rep_compra.getChrConcepto());
//			        rows.getCell(14).setCellStyle(style_cell);
//		
//			        rows.createCell(15).setCellValue(rep_compra.getConcatenado());
//			        rows.getCell(15).setCellStyle(style_cell);
//			        
//			        rows.createCell(16).setCellValue(rep_compra.getLegalidad());
//			        rows.getCell(16).setCellStyle(style_cell);
//			        
//			        rows.createCell(17).setCellValue(rep_compra.getDesChrNomEstado());
//			        rows.getCell(17).setCellStyle(style_cell);
//		            
//		            row++;	
//		        }
//		        
//            // Captured backflow
//            OutputStream out = response.getOutputStream();
//            wb.write(out); // We write in that flow
//            out.flush(); // We emptied the flow
//            out.close(); // We close the flow
//			
//    	} catch(Exception e){
//    		log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//					  Constantes.NIVEL_APP_REPORTER, 
//					  this.getClass().getName(), e.getMessage()));
//    	}
//	}
//	
//	
//	
//	/**
//	 * @param ruta
//	 * @param lista
//	 * @return Document
//	 */
//	@SuppressWarnings("static-access")
//	public Document generaPDFCartaFianza(String ruta, List<CartaFianzaBean> lista) {
//		Document document = null;
//		try {                     
//			
//		//	document = new Document(PageSize.A4, 20, 20, 20, 20);
//			document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
//			PdfWriter.getInstance(document, new FileOutputStream(ruta));    
//			
////			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
////	        writer.setPageEvent(event);
//			
//			document.open();
//			
//			
//			float[] rotulo = {1.00f};   
//            Calendar cal = Calendar.getInstance().getInstance();
//			// Considerar que cada campo en array es una columna table de tu reporte
//			float[] pri = {2.00f};
//			
//			// Considerar que cada campo en array es una columna table de tu reporte
////			float[] ter = {0.25f, 0.05f, 1.70f};
//		  
//			// Considerar que cada campo en array es una columna table de tu reporte
//			float[] cell_ren = {0.20f, 0.40f, 0.40f, 0.40f, 0.40f, 0.40f, 0.40f, 0.40f, 0.40f,  0.40f, 0.40f,
//								0.40f, 0.40f, 0.40f, 0.40f, 0.40f};
//			
////			float[] cell_once = {0.20f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f,   
////								 0.30f, 0.30f, 0.30f, 0.30f, 0.30f};
//			
//			Paragraph p     = null;
////			Paragraph extra = null;
//			PdfPTable table = null;
//			PdfPCell cell   = null;
//
//			Font normal = FontFactory.getFont("Arial", 6, Font.NORMAL, BaseColor.BLACK);    
//			Font negrita = FontFactory.getFont("Arial", 6, Font.BOLD, BaseColor.BLACK);
//			   
//			
//			table = new PdfPTable(pri);   
//			p = new Paragraph("REGISTRO DE CARTAS FIANZA", negrita);
//			
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBorderColor(BaseColor.WHITE);
//			table.addCell(cell);
//			document.add(table);
//			
//            table = new PdfPTable(rotulo);     
//            StringBuilder fecha = new StringBuilder();
//            fecha.append("Fecha de Emision: ");
//            fecha.append(cal.get(Calendar.DATE));
//            fecha.append("/");
//            fecha.append(cal.get(Calendar.MONTH)+1);
//            fecha.append("/");
//            fecha.append(cal.get(Calendar.YEAR));
//            p = new Paragraph(fecha.toString(), normal);
//            cell = new PdfPCell(p);
//            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cell.setBorderColor(BaseColor.WHITE);
//            table.addCell(cell);
//            document.add(table);
//			
//			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//			
//			table = new PdfPTable(cell_ren); 
//			
//			p = new Paragraph("Item", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			table.addCell(cell);
//			
//			p = new Paragraph("DOCUMENTO DE REMISION", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(4);
//			table.addCell(cell);
//			
//			p = new Paragraph("F. DOCU", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(1);
//			table.addCell(cell);
//			
//			p = new Paragraph("F. RECEP", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(1);
//			table.addCell(cell);
//			
//			p = new Paragraph("EMPRESA", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(1);
//			table.addCell(cell);
//			
//			p = new Paragraph("MONTO", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(1);
//			table.addCell(cell);
//			
//			p = new Paragraph("INICIO", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(1);
//			table.addCell(cell);
//			
//			p = new Paragraph("FIN", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(1);
//			table.addCell(cell);
//			
//			p = new Paragraph("Nº C/F", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(1);
//			table.addCell(cell);
//			
//			p = new Paragraph("ENTIDAD FINANCIERA", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(1);
//			table.addCell(cell);
//
//			p = new Paragraph("CONCEPTO", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(1);
//			table.addCell(cell);
//			
//			p = new Paragraph("ESTADO", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(1);
//			table.addCell(cell);
//			
//			p = new Paragraph("LEGALIDAD", negrita);
//			cell = new PdfPCell(p);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(new BaseColor(242, 242, 242));
//			cell.setColspan(1);
//			table.addCell(cell);
//			
//			document.add(table);
//				
//			int row = 1;
//			
//			for (CartaFianzaBean cartafianza : lista) {
//			
//				table = new PdfPTable(cell_ren); 
//				
//				p = new Paragraph(getString(row), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getConcepto(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getChrNumCarta(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph("SERNANP", normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getChrSigOficina(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getDteFecCarta(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getDteFecRecepcion(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getRazonsocial(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(getString(cartafianza.getIntImpCarta()), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getDteFecInicio(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getDteFecFinal(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getChrNumFianza(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getNom_entidad(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getChrConcepto(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getConcatenado(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//				p = new Paragraph(cartafianza.getLegalidad(), normal);
//				cell = new PdfPCell(p);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.addCell(cell);
//				
//					
//				document.add(table);	             
//					
//				row++;
//			}
//				
//			document.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}   
//		return document;
//	}
//	
//	/**
//	 * @param ruta
//	 * @param compra
//	 * @param lista
//	 * @return Document
//	 */
//	public Document generaPDFReporteCompras(String ruta, ComprasIngresosBean compra, List<ReporteDetalleComprasBean> lista) {
//		Document document = null;
//        try {        
//        	if (compra.getCod_servicio().equals(Constantes.ONE_INT)) { // Resumen de Rendiciones por Encargo        	
//        		document = new Document(PageSize.A4, 20, 20, 20, 20);
//        	} else {
//        		document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
//        	}
//       
//            PdfWriter.getInstance(document, new FileOutputStream(ruta));         
//            document.open();
//
//            float[] pri = {2.00f};
//            
//            float[] ter = {0.25f, 0.05f, 1.70f};
//            
//            float[] cin = {0.13f, 0.02f, 0.45f, 0.30f, 0.30f};
//          
//            float[] cell_res = {0.20f, 0.40f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f};	
//            
//            float[] cell_ren = {0.20f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f,
//            					0.30f, 0.30f, 0.30f, 0.30f, 0.30f};
//            
//            float[] cell_ven = {0.30f, 0.30f, 0.10f, 0.30f, 0.40f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f,
//		 						0.30f};
//
//            Paragraph p     = null;
//            Paragraph extra = null;
//            PdfPTable table = null;
//            PdfPCell cell   = null;
// 
//            Font normal = FontFactory.getFont("Arial", 6, Font.NORMAL, BaseColor.BLACK);    
//            Font negrita = FontFactory.getFont("Arial", 6, Font.BOLD, BaseColor.BLACK);
//            Font neg_reg = FontFactory.getFont("Arial", 4, Font.BOLD, BaseColor.BLACK);
//            Font neg_reg_com = FontFactory.getFont("Arial", 3, Font.BOLD, BaseColor.BLACK);
//            Font nor_reg = FontFactory.getFont("Arial", 4, Font.NORMAL, BaseColor.BLACK);   
//			   
//            DecimalFormatSymbols other_symbols = new DecimalFormatSymbols(Locale.US);
//            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, other_symbols);
//            
//            table = new PdfPTable(pri);   
//            if (compra.getCod_servicio().equals(Constantes.ONE_INT)) {
//            	p = new Paragraph("Resumen de Rendiciones por Encargo", negrita);
//            	cell = new PdfPCell(p);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            } else if (compra.getCod_servicio().equals(Constantes.TWO_INT)) {            
//            	p = new Paragraph("Documentos de la Rendición de Cuenta", negrita);
//            	cell = new PdfPCell(p);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            } else if (compra.getCod_servicio().equals(Constantes.THREE_INT)) {
//            	String fec_sistema = "Fecha del sistema : ";
//            	String fec_actual = DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, new Date());
//            	p = new Paragraph(fec_sistema.concat(fec_actual), negrita);
//            	cell = new PdfPCell(p);
//            } else if (compra.getCod_servicio().equals(Constantes.FOUR_INT)) { // Registro de Compras
//            	String fec_sistema = "Fecha del sistema : ";
//            	String fec_actual = DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, new Date());
//            	p = new Paragraph(fec_sistema.concat(fec_actual), negrita);
//            	cell = new PdfPCell(p);
//            }
//            cell.setBorder(Rectangle.NO_BORDER);
//            table.addCell(cell);
//            document.add(table);
//            
//            
//            document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//            
//            ReporteDetalleComprasBean rep_com_tra = lista.get(0);
//            
//            if (compra.getCod_servicio().equals(Constantes.TWO_INT) && // Resumen de Rendiciones por Encargo
//            		!isNullOrEmpty(compra.getNro_doc_rc())) {
//            	
//            	table = new PdfPTable(cin);     
//	            p = new Paragraph("Unidad Operativa", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(":", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            String des_uni_operativa = compra.getDes_uni_operativa();
//	            if ((des_uni_operativa.trim()).length() == 0) {
//	            	des_uni_operativa = "Todos";
//	            }            
//	            p = new Paragraph(des_uni_operativa, normal);            
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            document.add(table);
//	            
//	            p = new Paragraph("");
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            	            
//	            Paragraph parag1 = new Paragraph("Fte Fto: ",negrita);
//	            Paragraph parag2 = new Paragraph(rep_com_tra.getDes_fue_financiamiento(), normal);
//	            Paragraph comb = new Paragraph(); 
//	            comb.add(parag1);
//	            comb.add(parag2);
//	            
//	            cell = new PdfPCell(comb);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//            } else if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Reporte de Compras
//            	
//            	table = new PdfPTable(pri);
//	            p = new Paragraph("REPORTE DE COMPRAS", negrita);         
//                cell = new PdfPCell(p);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	              
//            } else if (compra.getCod_servicio().equals(Constantes.FOUR_INT)) { // Registro de Compras
//            	
//            	table = new PdfPTable(pri);
//	            p = new Paragraph("REPORTE DE COMPRAS", negrita);         
//                cell = new PdfPCell(p);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	              
//            } else {
//            	
//            	table = new PdfPTable(ter);     
//	            p = new Paragraph("Unidad Operativa", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(":", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            String des_uni_operativa = compra.getDes_uni_operativa();
//	            if ((des_uni_operativa.trim()).length() == 0) {
//	            	des_uni_operativa = "Todos";
//	            }            
//	            p = new Paragraph(des_uni_operativa, normal);            
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//            }
//            document.add(table);
//            
//            
//            if (compra.getCod_servicio().equals(Constantes.TWO_INT) && // Resumen de Rendiciones por Encargo
//            		!isNullOrEmpty(compra.getNro_doc_rc())) {
//            	
//            	table = new PdfPTable(cin);     
//                p = new Paragraph("ANP", negrita);
//                cell = new PdfPCell(p);
//                cell.setBorder(Rectangle.NO_BORDER);
//                table.addCell(cell);
//
//                p = new Paragraph(":", negrita);
//                cell = new PdfPCell(p);
//                cell.setBorder(Rectangle.NO_BORDER);
//                table.addCell(cell);
//                
//                String des_anp = compra.getDes_reg_nac();
//                if ((des_anp.trim()).length() == 0) {
//                	des_anp = "Todos";
//                }            
//                p = new Paragraph(des_anp, normal);            
//                cell = new PdfPCell(p);
//                cell.setBorder(Rectangle.NO_BORDER);
//                table.addCell(cell);
//                document.add(table);
//                
//                p = new Paragraph("");
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            	            
//	            Paragraph parag1 = new Paragraph("Nro SIAF: ",negrita);
//	            Paragraph parag2 = new Paragraph(getString(rep_com_tra.getNum_siaf()), normal);
//	            Paragraph comb = new Paragraph(); 
//	            comb.add(parag1);
//	            comb.add(parag2);
//	            
//	            cell = new PdfPCell(comb);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell); 
//	            
//            } else if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Registro de Compras
//            	
//            	table = new PdfPTable(pri);            	
//            	StringBuilder periodo = new StringBuilder();
//		        periodo.append(getMes(compra.getCod_mes()));
//		        periodo.append(Constantes.ESPACIO);
//		        periodo.append(DateUtil.getAnioActual());
//	            p = new Paragraph(periodo.toString(), negrita);         
//                cell = new PdfPCell(p);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//            } else if (compra.getCod_servicio().equals(Constantes.FOUR_INT)) { // Registro de Compras
//	             
//            	table = new PdfPTable(pri);            	
//            	StringBuilder periodo = new StringBuilder();
//		        periodo.append(getMes(compra.getCod_mes()));
//		        periodo.append(Constantes.ESPACIO);
//		        periodo.append(DateUtil.getAnioActual());
//	            p = new Paragraph(periodo.toString(), negrita);         
//                cell = new PdfPCell(p);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//            	
//            } else {
//            	
//            	table = new PdfPTable(ter);     
//	            p = new Paragraph("ANP", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	
//	            p = new Paragraph(":", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            String des_anp = compra.getDes_reg_nac();
//	            if ((des_anp.trim()).length() == 0) {
//	            	des_anp = "Todos";
//	            }            
//	            p = new Paragraph(des_anp, normal);            
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//            }
//            document.add(table);
//	           
//	        
//            if (compra.getCod_servicio().equals(Constantes.TWO_INT) && // Resumen de Rendiciones por Encargo
//            		!isNullOrEmpty(compra.getNro_doc_rc())) {
//            	
//            	table = new PdfPTable(cin);     
//                p = new Paragraph("Del  ", negrita);            
//                extra = new Paragraph(compra.getFec_inicio(), normal); 
//    	        p.add(extra);	        
//    	        extra = new Paragraph("  al  ", negrita); 
//    	        p.add(extra);	        
//    	        extra = new Paragraph(compra.getFec_final(), normal); 
//    	        p.add(extra);            
//                cell = new PdfPCell(p);
//                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cell.setBorderColor(BaseColor.WHITE);
//                cell.setColspan(3);
//                table.addCell(cell);
//                
//                p = new Paragraph("");
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            Paragraph parag1 = new Paragraph("Nro  C/P: ",negrita);
//	            Paragraph parag2 = new Paragraph(getString(rep_com_tra.getNum_comprobante()), normal);
//	            Paragraph comb = new Paragraph(); 
//	            comb.add(parag1);
//	            comb.add(parag2);
//	            
//	            cell = new PdfPCell(comb);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//            } else if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Registro de Compras
//            	
//            	table = new PdfPTable(cin);            	
//            	p = new Paragraph("SERNANP", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(3);
//	            table.addCell(cell);	            
//	            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(2);
//	            table.addCell(cell);
//	            
//	            document.add(table);
//	            
//	            
//	            table = new PdfPTable(cin);             	
//            	p = new Paragraph("Servicio Nacional de Areas Naturales Protegidas por el Estado", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(3);
//	            table.addCell(cell);	            
//	            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(2);
//	            table.addCell(cell);
//	            
//            } else if (compra.getCod_servicio().equals(Constantes.FOUR_INT)) { // Registro de Compras
//            	
//            	table = new PdfPTable(cin);            	
//            	p = new Paragraph("SERNANP", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(3);
//	            table.addCell(cell);	            
//	            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(2);
//	            table.addCell(cell);
//	            
//	            document.add(table);
//	            
//	            
//	            table = new PdfPTable(cin);             	
//            	p = new Paragraph("Servicio Nacional de Areas Naturales Protegidas por el Estado", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(3);
//	            table.addCell(cell);	            
//	            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(2);
//	            table.addCell(cell);
//	            
//            } else {
//            	
//	            table = new PdfPTable(pri);     
//	            p = new Paragraph("Del  ", negrita);            
//	            extra = new Paragraph(compra.getFec_inicio(), normal); 
//		        p.add(extra);	        
//		        extra = new Paragraph("  al  ", negrita); 
//		        p.add(extra);	        
//		        extra = new Paragraph(compra.getFec_final(), normal); 
//		        p.add(extra);            
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	            cell.setBorderColor(BaseColor.WHITE);
//	            table.addCell(cell);
//	            
//            }
//            document.add(table);
//            
//            
//            if (compra.getCod_servicio().equals(Constantes.TWO_INT) && // Resumen de Rendiciones por Encargo 
//            		!isNullOrEmpty(compra.getNro_doc_rc())) {
//            	
//            	table = new PdfPTable(cin);
//                p = new Paragraph("");
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("");
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("");
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("");
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            Paragraph parag1 = new Paragraph("Fecha Transf.: ",negrita);
//	            Paragraph parag2 = new Paragraph(getString(rep_com_tra.getFec_com_transferencia()), normal);
//	            Paragraph comb = new Paragraph(); 
//	            comb.add(parag1);
//	            comb.add(parag2);
//	            
//	            cell = new PdfPCell(comb);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell); 
//                
//                document.add(table);
//                
//                
//                document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//                
//            } else {
//            	
//            	document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//            	
//            }
//
//            
//            if (compra.getCod_servicio().equals(Constantes.ONE_INT)) { // Consolidado de Partidas por Rendición de Cuenta
//					
//            	table = new PdfPTable(cell_res); 
//	             
//	            cell = new PdfPCell();
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(2);
//	            table.addCell(cell);	            
//	            
//	            p = new Paragraph("EJECUCION FINANCIERA", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(4);
//	            table.addCell(cell);
//	            
//	            cell = new PdfPCell();
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            table.addCell(cell);
//	            
//	            document.add(table);
//            	
//            	
//	            table = new PdfPTable(cell_res); 
//	            
//	            p = new Paragraph("Item", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Partida Especifica", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("POA", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Transferido", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Ejecutado", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Saldo", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Devoluciones", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            document.add(table);   
//	            
//	            
//	            int row = 1;
//	            BigDecimal num_poa = BigDecimal.ZERO;
//	            BigDecimal num_tra = BigDecimal.ZERO;
//	            BigDecimal num_eje = BigDecimal.ZERO;
//	            BigDecimal num_sal = BigDecimal.ZERO;
//	            BigDecimal num_dev = BigDecimal.ZERO;
//	            
//	            for (ReporteDetalleComprasBean rep_compra : lista) {
//	            
//	            	table = new PdfPTable(cell_res); 
//	            	
//	            	p = new Paragraph(getString(row), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getVcod_especifica(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_compra.getImp_partida()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_compra.getImp_partida()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_compra.getImp_det_total()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_compra.getImp_saldo()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_compra.getImp_devolucion()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                document.add(table);
//	                
//	                num_poa = num_poa.add(rep_compra.getImp_partida());
//	                num_tra = num_tra.add(rep_compra.getImp_partida());
//	                num_eje = num_eje.add(rep_compra.getImp_det_total());
//	                num_sal = num_sal.add(rep_compra.getImp_saldo());
//	                num_dev = num_dev.add(rep_compra.getImp_devolucion());
//		            
//		            row++;	                
//	            }
//	            
//	            table = new PdfPTable(cell_res); 
//	            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
// 
//	            p = new Paragraph("Totales:", negrita);
//            	cell = new PdfPCell(p);
//            	cell.setBorder(Rectangle.NO_BORDER);
//            	cell.setBorderColorTop(BaseColor.BLACK);
//                cell.setBorderColorRight(BaseColor.BLACK); 
//                table.addCell(cell);	           
//	            
//	            p = new Paragraph(dec_form.format(num_poa), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_tra), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_eje), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_sal), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_dev), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            document.add(table);	                
//				
//			} else if (compra.getCod_servicio().equals(Constantes.TWO_INT)) { // Documentos de la Rendición de Cuenta
//                        
//				table = new PdfPTable(cell_ren); 
//	             
//	            cell = new PdfPCell();
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(4);
//	            table.addCell(cell);	            
//	            
//	            p = new Paragraph("DOCUMENTO DE GASTOS", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(6);
//	            table.addCell(cell);	            
//	            
//	            p = new Paragraph("IMPORTE", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(6);
//	            table.addCell(cell);
//	            
//	            document.add(table);
//				
//				
//	            table = new PdfPTable(cell_ren); 
//	            
//	            p = new Paragraph("Item", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Nro R/C", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Part. Esp.", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Especifica", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);	            
//	            
//	            p = new Paragraph("Fecha C/P", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("SIAF", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Comp. de Pago", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Nro", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("RUC", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Razon Social", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	           
//	            p = new Paragraph("Monto", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Afecto", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("No Afecto", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("IGV", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("ISC", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Total", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            document.add(table);
//	            
//	            int row = 1;
//	            BigDecimal num_imp_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_igv = BigDecimal.ZERO;
//	            BigDecimal num_imp_isc = BigDecimal.ZERO;
//	            BigDecimal imp_det_total = BigDecimal.ZERO;
//	            
//	            for (ReporteDetalleComprasBean rep_compra : lista) {
//	            
//	            	table = new PdfPTable(cell_ren); 
//	            	
//	            	p = new Paragraph(getString(row), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getNro_doc_rc(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getVcod_especifica(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getDes_especifica(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getFec_comprobante(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(getString(rep_compra.getNro_siaf()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getTip_comprobante(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getNro_ser_comprobante(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getRuc_nro_doc_prov(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getRaz_soc_prov(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	            	
//	                p = new Paragraph(dec_form.format(rep_compra.getNum_imp_servicio()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_compra.getNum_imp_afecto()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_compra.getNum_imp_no_afecto()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_compra.getNum_imp_igv()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_compra.getNum_imp_isc()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_compra.getImp_det_total()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                document.add(table);
//	                
//	                num_imp_afecto = num_imp_afecto.add(rep_compra.getNum_imp_afecto());
//	                num_imp_no_afecto = num_imp_no_afecto.add(rep_compra.getNum_imp_no_afecto());
//	                num_imp_igv = num_imp_igv.add(rep_compra.getNum_imp_igv());
//	                num_imp_isc = num_imp_isc.add(rep_compra.getNum_imp_isc());
//	                imp_det_total = imp_det_total.add(rep_compra.getImp_det_total());
//		            
//		            row++;
//	            }
//	            
//	            table = new PdfPTable(cell_ren); 
//	            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell); 
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            
//	                      
//	            p = new Paragraph("Totales:", negrita);
//            	cell = new PdfPCell(p);
//            	cell.setBorder(Rectangle.NO_BORDER);
//            	cell.setBorderColorTop(BaseColor.BLACK);
//                cell.setBorderColorRight(BaseColor.BLACK); 
//                table.addCell(cell);
//	           
//	            
//	            p = new Paragraph(dec_form.format(num_imp_afecto), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_imp_no_afecto), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_imp_igv), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_imp_isc), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(imp_det_total), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            document.add(table);
//	        
//			} else if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Registro de Compras
//				
//				table = new PdfPTable(28); // 28 columnas
//	             
//				// Adicion celdas primer nivel
//	            p = new Paragraph("NÚMERO CORRELATIVO DEL REGISTRO O CÓDIGO UNICO DE LA OPERACIÓN", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);	            
//	            
//	            p = new Paragraph("FECHA DE EMISIÓN DEL COMPROBANTE DE PAGO O DOCUMENTO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("FECHA DE VENCIMIENTO O FECHA DE PAGO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("COMPROBANTE DE PAGO O DOCUMENTO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(3);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("N° DEL COMPROBANTE DE PAGO, DOCUMENTO, N° DE ORDEN DEL FORMULARIO FíSICO O VIRTUAL, N° DE DUA, DSI O LIQUIDACIÓN DE COBRANZA U OTROS DOCUMENTOS EMITIDOS POR SUNAT PARA ACREDITAR EL CRÉDITO FISCAL EN LA IMPORTACIÓN", neg_reg_com);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("INFORMACIÓN DEL PROVEEDOR", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(3);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("ADQUISICIONES GRABADAS DESTINADAS A OPERACIONES GRABADAS Y/O DE EXPORTACIÓN", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(2);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("ADQUISICIONES GRABADAS DESTINADAS A OPERACIONES GRABADAS Y/O DE EXPORTACIÓN Y/A OPERACIONES NO GRABADAS", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(2);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("ADQUISICIONES GRABADAS DESTINADAS A OPERACIONES NO GRABADAS", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(2);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("VALOR DE LAS ADQUISICIONES NO GRABADAS", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("ISC", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("OTROS TRIBUTOS Y CARGOS", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("IMPORTE TOTAL", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("N° DE COMPROBANTE DE PAGO EMITIDO POR SUJETO NO DOMICILIADO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("CONSTANCIA DE DEPÓSITO DE DETRACCIÓN", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(2);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("TIPO DE CAMBIO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("REFERENCIA DEL COMPROBANTE DE PAGO O DOCUMENTO ORIGINAL QUE SE MODIFICA", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(4);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            // Adicion celdas segundo nivel
//	            
//	            p = new Paragraph("TIPO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("SERIE O CÓDIGO DE LA DEPENDENCIA ADUANERA", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("AÑO DE EMISIÓN DE LA DUA O DSI", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//
//	            p = new Paragraph("DOCUMENTO DE IDENTIDAD", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(2);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("APELLIDOS Y NOMBRES, DENOMINACIÓN O RAZÓN SOCIAL", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("BASE IMPONIBLE", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("IGV", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("BASE IMPONIBLE", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("IGV", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("BASE IMPONIBLE", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("IGV", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//
//	            p = new Paragraph("NÚMERO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("FECHA DE EMISIÓN", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("FECHA", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("TIPO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("SERIE", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("N° DEL COMPROBANTE DE PAGO O DOCUMENTO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            // Adicion celdas tercer nivel
//	            
//	            p = new Paragraph("TIPO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("NÚMERO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//
//	            document.add(table);
//				
//				
//	            int row = 1;
//		        BigDecimal num_imp_afecto = BigDecimal.ZERO;
//		        BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//		        BigDecimal num_imp_igv = BigDecimal.ZERO;
//		        BigDecimal num_imp_isc = BigDecimal.ZERO;
//		        BigDecimal imp_det_total = BigDecimal.ZERO;
//		        BigDecimal imp_no_gra_igv = BigDecimal.ZERO;
//		        BigDecimal imp_gra_total = BigDecimal.ZERO;
//		        
//		        for (ReporteDetalleComprasBean rep_compra : lista) {
//		        	
//		        	table = new PdfPTable(28); // 28 columnas
//	            	
//	            	p = new Paragraph(getString(row), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getFec_comprobante(), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getFec_vto(), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getVcod_comprobante(), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_compra.getSer_comprobante(), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(Constantes.EMPTY, nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(StringUtils.stripStart(rep_compra.getNro_comprobante(), Constantes.ZERO_STRING), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                if (!isNullOrEmpty(rep_compra.getTip_documento())) {
//	    				p = new Paragraph(rep_compra.getTip_documento(), nor_reg);
//	    			} else {
//	    				p = new Paragraph(Constantes.EMPTY, nor_reg);
//	    			}
//					cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(getString(rep_compra.getRuc_nro_doc_prov()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(getString(rep_compra.getRaz_soc_prov()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					
//					BigDecimal imp_afecto = rep_compra.getNum_imp_afecto();
//			        if (rep_compra.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_afecto.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_afecto = imp_afecto.negate();
//			        }
//	                p = new Paragraph(dec_form.format(imp_afecto), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//					
//					BigDecimal imp_igv = rep_compra.getNum_imp_igv();
//			        if (rep_compra.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_igv.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_igv = imp_igv.negate();
//			        }
//	                p = new Paragraph(dec_form.format(imp_igv), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(BigDecimal.ZERO), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(BigDecimal.ZERO), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(BigDecimal.ZERO), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(BigDecimal.ZERO), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//					
//					BigDecimal imp_no_afecto = rep_compra.getNum_imp_no_afecto();
//			        if (rep_compra.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_no_afecto.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_no_afecto = imp_no_afecto.negate();
//			        }
//					p = new Paragraph(dec_form.format(imp_no_afecto), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//					
//					
//					BigDecimal imp_isc = rep_compra.getNum_imp_isc();
//			        if (rep_compra.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_isc.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_isc = imp_isc.negate();
//			        }
//					p = new Paragraph(dec_form.format(imp_isc), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(dec_form.format(BigDecimal.ZERO), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//					
//					
//					BigDecimal imp_total = rep_compra.getImp_det_total();
//			        if (rep_compra.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_total.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_total = imp_total.negate();
//			        }
//					p = new Paragraph(dec_form.format(imp_total), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(Constantes.EMPTY, nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(getString(rep_compra.getNum_constancia()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(getString(rep_compra.getFec_pag_deposito()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(getString(rep_compra.getNum_tip_cambio()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(getString(rep_compra.getFec_com_not_cre()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(getString(rep_compra.getTip_com_not_cre()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(getString(rep_compra.getSer_com_not_cre()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(StringUtils.stripStart(getString(rep_compra.getNro_com_not_cre()), Constantes.ZERO_STRING), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//	                document.add(table);		 		        
//			        
//			        num_imp_afecto = num_imp_afecto.add(imp_afecto);
//		            num_imp_no_afecto = num_imp_no_afecto.add(imp_no_afecto);
//		            num_imp_igv = num_imp_igv.add(imp_igv);
//		            num_imp_isc = num_imp_isc.add(imp_isc);
//		            imp_det_total = imp_det_total.add(imp_total);		            
//		            imp_no_gra_igv = imp_no_gra_igv.add(BigDecimal.valueOf(rep_compra.getImp_no_gra_igv()));
//		            imp_gra_total = imp_gra_total.add(BigDecimal.valueOf(rep_compra.getImp_gra_total()));
//		            
//		            row++;
//		        }
//		        
//				table = new PdfPTable(28); // 28 columnas 
//	            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell); 
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	                     
//	                     
//	            p = new Paragraph("TOTALES", neg_reg);
//            	cell = new PdfPCell(p);
//            	cell.setBorder(Rectangle.NO_BORDER);
//            	cell.setBorderColorTop(BaseColor.BLACK);
//                cell.setBorderColorRight(BaseColor.BLACK); 
//                table.addCell(cell);	           
//	            
//	            p = new Paragraph(dec_form.format(num_imp_afecto), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_imp_igv), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(BigDecimal.ZERO), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(BigDecimal.ZERO), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(BigDecimal.ZERO), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//				
//				p = new Paragraph(dec_form.format(BigDecimal.ZERO), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//				
//				p = new Paragraph(dec_form.format(num_imp_no_afecto), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//				
//				p = new Paragraph(dec_form.format(num_imp_isc), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//				
//				p = new Paragraph(dec_form.format(BigDecimal.ZERO), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//				
//				p = new Paragraph(dec_form.format(imp_det_total), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//				
//				
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//				cell.setBorderColorLeft(BaseColor.BLACK);
//	            table.addCell(cell);
//				
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//				
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//				
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//				
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//				
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//				
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//				
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);				
//	            
//	            document.add(table);				
//				
//			} else if (compra.getCod_servicio().equals(Constantes.FOUR_INT)) { // Registro de Compras
//            	
//            	table = new PdfPTable(cell_ven);
//            	
//            	p = new Paragraph("N° Documento", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Fecha", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Doc", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Num", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Nombre", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Afecto", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("No Afecto", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("IGV", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("ISC", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//            	
//	            p = new Paragraph("Total (S/.)", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Total (US$)", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//            	
//	            document.add(table);
//	            
//	            
//	            
//	            String unidad = "";
//		        String documento = "";
//		        if (!isEmpty(lista)) {
//		        	unidad = lista.get(0).getDes_uni_operativa();
//		        	documento = lista.get(0).getTip_comprobante();
//		        }
//		        
//		        String tip_operacion = compra.getTip_operacion();
//		        
//		        List<ReporteDetalleComprasBean> lis_uni_ope = null;
//		        List<ReporteDetalleComprasBean> lis_sede = null;	
//	            
//		        if (tip_operacion.equals(Constantes.TWO_STRING)) { // Sede Central
//		        	lis_uni_ope = new ArrayList<ReporteDetalleComprasBean>();
//		        	lis_sede = new ArrayList<ReporteDetalleComprasBean>();
//		        	for (ReporteDetalleComprasBean rep : lista) {
//		        		if (rep.getTip_operacion().equals(Constantes.TWO_STRING)) { // Sede Central
//		        			lis_sede.add(rep);
//		        		} else {
//		        			lis_uni_ope.add(rep);
//		        		}			        		
//		        	}
//		        	lista.clear();
//		        	lista.addAll(lis_sede);
//
//		        	table = new PdfPTable(cell_ven);
//			        p = new Paragraph("SERNANP - SEDE CENTRAL", negrita);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            cell.setColspan(11);
//		            table.addCell(cell);
//		            document.add(table);
//	    	      	    	        
//		        } else { // Unidad Operativa
//
//		        	table = new PdfPTable(cell_ven);
//			        p = new Paragraph(unidad, negrita);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            cell.setColspan(11);
//		            table.addCell(cell);
//		            document.add(table);
//		        	
//		        }
//	            
//
//		        boolean fir = true;
//		        boolean sub = false;
//		        int can = 0;
//		        int ult = lista.size() - 1;
//		        BigDecimal num_imp_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_igv = BigDecimal.ZERO;
//	            BigDecimal num_imp_isc = BigDecimal.ZERO;
//	            BigDecimal imp_det_total = BigDecimal.ZERO;
//	            BigDecimal imp_det_dol_total = BigDecimal.ZERO;
//	            
//	            BigDecimal num_imp_tot_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_no_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_igv = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_isc = BigDecimal.ZERO;
//	            BigDecimal imp_total = BigDecimal.ZERO;
//	            BigDecimal imp_dol_total = BigDecimal.ZERO;
//	            
//		        for (ReporteDetalleComprasBean rep_compra : lista) {
//		        	         		            	
//		        	if (!documento.equals(rep_compra.getTip_comprobante()) && !fir) {
//	            		
//	            		table = new PdfPTable(cell_ven);
//			    	    
//			    	    cell = new PdfPCell();
//			            cell.setBorder(Rectangle.TOP);
//			            cell.setColspan(4);
//			            table.addCell(cell);
//			    	    
//				        p = new Paragraph("Sub-Total:", negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//	            		sub = true;	            		
//	            	} else if (fir) {
//	            		
//	            		table = new PdfPTable(cell_ven);
//	            		p = new Paragraph("Documento: ".concat(documento.toUpperCase()), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            cell.setColspan(11);
//			            table.addCell(cell);
//			            document.add(table);
//	            		
//	            		fir = false;
//	            	}
//	            	
//	            	if (!sub) {	            	
//	            		table = new PdfPTable(cell_ven);
//	            	} else {
//	            		
//	            		p = new Paragraph(dec_form.format(num_imp_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//	            		
//			            p = new Paragraph(dec_form.format(num_imp_no_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_igv), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_isc), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(imp_det_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//	            					            
//			            p = new Paragraph(dec_form.format(imp_det_dol_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            document.add(table);
//	            			            		
//			    	    
//			    	    num_imp_afecto = BigDecimal.ZERO;
//			            num_imp_no_afecto = BigDecimal.ZERO;
//			            num_imp_igv = BigDecimal.ZERO;
//			            num_imp_isc = BigDecimal.ZERO;
//			            imp_det_total = BigDecimal.ZERO;
//			            imp_det_dol_total = BigDecimal.ZERO;
//			    	    
//	            		sub = false;
//	            		
//			        	if (compra.getTip_operacion().equals(Constantes.ONE_STRING)) { // Unidad Operativa
//			        		if (!unidad.equals(rep_compra.getDes_uni_operativa())) {
//		                				                		
//		                		unidad = rep_compra.getDes_uni_operativa();
//		                		
//		                				                		
//		                		table = new PdfPTable(cell_ven);
//					    	    
//					    	    cell = new PdfPCell();
//					    	    cell.setBorder(Rectangle.TOP);
//					            cell.setColspan(4);
//					            table.addCell(cell);
//					    	    
//						        p = new Paragraph("Total:", negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            p = new Paragraph(dec_form.format(num_imp_tot_afecto), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            p = new Paragraph(dec_form.format(num_imp_tot_no_afecto), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(num_imp_tot_igv), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(num_imp_tot_isc), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(imp_total), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(imp_dol_total), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            document.add(table);
//					            
//		                		
//					    	    num_imp_tot_afecto = BigDecimal.ZERO;
//					            num_imp_tot_no_afecto = BigDecimal.ZERO;
//					            num_imp_tot_igv = BigDecimal.ZERO;
//					            num_imp_tot_isc = BigDecimal.ZERO;
//					            imp_total = BigDecimal.ZERO;
//					            imp_dol_total = BigDecimal.ZERO;
//		                		
//					            
//					            document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//					            
//					            
//					            table = new PdfPTable(cell_ven);
//						        p = new Paragraph(unidad, negrita);
//					            cell = new PdfPCell(p);
//					            cell.setBorder(Rectangle.NO_BORDER);
//					            cell.setColspan(11);
//					            table.addCell(cell);
//					            document.add(table);					            
//		                		
//		                	} else {
//		                		
//		                		document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//		                		
//		                	}
//			        		
//			        	} else { // Sede Central	
//			        		
//			        		document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//			        		
//			        	}
//	            		
//			        	
//			        	
//			        	table = new PdfPTable(cell_ven);
//			        	documento = rep_compra.getTip_comprobante();
//	            		p = new Paragraph("Documento: ".concat(documento.toUpperCase()), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            cell.setColspan(11);
//			            table.addCell(cell);
//			            document.add(table);
//			        	
//			        	
//	            		
//			            table = new PdfPTable(cell_ven);
//	            	}
//	            		
//	            	
//	            	String ser_nro = rep_compra.getSer_comprobante().concat(" - ");
//		    	    ser_nro = ser_nro.concat(rep_compra.getNro_comprobante());
//	            	p = new Paragraph(ser_nro, normal);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//	            	
//		            p = new Paragraph(rep_compra.getFec_comprobante(), normal);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(rep_compra.getTip_documento(), normal);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(getString(rep_compra.getRuc_nro_doc_prov()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(getString(rep_compra.getRaz_soc_prov()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(dec_form.format(rep_compra.getNum_imp_afecto()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(dec_form.format(rep_compra.getNum_imp_no_afecto()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(dec_form.format(rep_compra.getNum_imp_igv()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//
//		            p = new Paragraph(dec_form.format(rep_compra.getNum_imp_isc()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		    	    if (rep_compra.getCod_moneda().equals(Constantes.ONE_INT)) { // Soles
//		    	    	p = new Paragraph(dec_form.format(rep_compra.getImp_det_total()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(BigDecimal.ZERO), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			    	    
//
//			    	    imp_det_total = imp_det_total.add(rep_compra.getImp_det_total());
//			    	    imp_total = imp_total.add(rep_compra.getImp_det_total());
//		    	    } else { // Dolares
//		    	    	p = new Paragraph(dec_form.format(BigDecimal.ZERO), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			    	    
//			            p = new Paragraph(dec_form.format(rep_compra.getImp_det_total()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			    	    
//			    	    
//			    	    imp_det_dol_total = imp_det_dol_total.add(rep_compra.getImp_det_total());
//			    	    imp_dol_total = imp_dol_total.add(rep_compra.getImp_det_total());
//		    	    }
//		    	    	
//		    	    document.add(table);
//		    	    
//		    	    
//		    	    num_imp_afecto = num_imp_afecto.add(rep_compra.getNum_imp_afecto());
//	                num_imp_no_afecto = num_imp_no_afecto.add(rep_compra.getNum_imp_no_afecto());
//	                num_imp_igv = num_imp_igv.add(rep_compra.getNum_imp_igv());
//	                num_imp_isc = num_imp_isc.add(rep_compra.getNum_imp_isc());	                
//	                
//
//                	num_imp_tot_afecto = num_imp_tot_afecto.add(rep_compra.getNum_imp_afecto());
//	                num_imp_tot_no_afecto = num_imp_tot_no_afecto.add(rep_compra.getNum_imp_no_afecto());
//	                num_imp_tot_igv = num_imp_tot_igv.add(rep_compra.getNum_imp_igv());
//	                num_imp_tot_isc = num_imp_tot_isc.add(rep_compra.getNum_imp_isc());
//	                
//	
//		    	    if (can == ult) { // Validacion ultimo registro
//		    	    	
//		    	    	table = new PdfPTable(cell_ven);
//			    	    
//			    	    cell = new PdfPCell();
//			    	    cell.setBorder(Rectangle.TOP);
//			            cell.setColspan(4);
//			            table.addCell(cell);
//			    	    
//				        p = new Paragraph("Sub-Total:", negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//	            		
//			            p = new Paragraph(dec_form.format(num_imp_no_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_igv), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_isc), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(imp_det_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//	            					            
//			            p = new Paragraph(dec_form.format(imp_det_dol_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            document.add(table);
//		    	    	
//			            
//			            table = new PdfPTable(cell_ven);
//			    	    
//			    	    cell = new PdfPCell();
//			    	    cell.setBorder(Rectangle.TOP);
//			            cell.setColspan(4);
//			            table.addCell(cell);
//			    	    
//				        p = new Paragraph("Total:", negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//                		
//			            p = new Paragraph(dec_form.format(num_imp_tot_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//                		
//			            p = new Paragraph(dec_form.format(num_imp_tot_no_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_tot_igv), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_tot_isc), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(imp_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(imp_dol_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//                		
//			            document.add(table);
//				    	   			    	 
//			    	    
//		    	    }		    	    
//		    	    
//		    	    can++;
//		        }
//		        
//		        
//		        if (tip_operacion.equals(Constantes.TWO_STRING)) { // Sede Central
//		        	
//		        	unidad = "";
//			        documento = "";
//			        String are_nat_pro = "";
//			        if (!isEmpty(lis_uni_ope)) {
//			        	unidad = lis_uni_ope.get(0).getDes_uni_operativa();
//			        	are_nat_pro = lis_uni_ope.get(0).getDes_reg_nac().toUpperCase();
//			        	documento = lis_uni_ope.get(0).getTip_comprobante();
//			        }
//
//			        document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//			        
//			        
//			        table = new PdfPTable(cell_ven);
//			        p = new Paragraph(are_nat_pro, negrita);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            cell.setColspan(11);
//		            table.addCell(cell);
//		            document.add(table);
//			        
//			        
//			        fir = true;
//			        sub = false;
//			        can = 0;
//			        ult = lis_uni_ope.size() - 1;
//			        num_imp_afecto = BigDecimal.ZERO;
//		            num_imp_no_afecto = BigDecimal.ZERO;
//		            num_imp_igv = BigDecimal.ZERO;
//		            num_imp_isc = BigDecimal.ZERO;
//		            imp_det_total = BigDecimal.ZERO;
//		            imp_det_dol_total = BigDecimal.ZERO;
//		            
//		            num_imp_tot_afecto = BigDecimal.ZERO;
//		            num_imp_tot_no_afecto = BigDecimal.ZERO;
//		            num_imp_tot_igv = BigDecimal.ZERO;
//		            num_imp_tot_isc = BigDecimal.ZERO;
//		            imp_total = BigDecimal.ZERO;
//		            imp_dol_total = BigDecimal.ZERO;
//		            
//		            BigDecimal imp_tot_gen_afecto = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_no_afecto = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_igv = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_isc = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_soles = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_dolares = BigDecimal.ZERO;
//		        	
//		        	for (ReporteDetalleComprasBean rep_compra : lis_uni_ope) {
//		        		
//		        		if (!documento.equals(rep_compra.getTip_comprobante()) && !fir) {
//		            		
//		            		table = new PdfPTable(cell_ven);
//				    	    
//				    	    cell = new PdfPCell();
//				            cell.setBorder(Rectangle.TOP);
//				            cell.setColspan(4);
//				            table.addCell(cell);
//				    	    
//					        p = new Paragraph("Sub-Total:", negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//		            		sub = true;	            		
//		            	} else if (fir) {
//		            		
//		            		table = new PdfPTable(cell_ven);
//		            		p = new Paragraph("Documento: ".concat(documento.toUpperCase()), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            cell.setColspan(11);
//				            table.addCell(cell);
//				            document.add(table);
//		            		
//		            		fir = false;
//		            	}
//		            	
//		            	if (!sub) {	            	
//		            		table = new PdfPTable(cell_ven);
//		            	} else {
//		            		
//		            		p = new Paragraph(dec_form.format(num_imp_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//		            		
//				            p = new Paragraph(dec_form.format(num_imp_no_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_igv), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_isc), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_det_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//		            					            
//				            p = new Paragraph(dec_form.format(imp_det_dol_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            document.add(table);
//		            			            		
//				    	    
//				    	    num_imp_afecto = BigDecimal.ZERO;
//				            num_imp_no_afecto = BigDecimal.ZERO;
//				            num_imp_igv = BigDecimal.ZERO;
//				            num_imp_isc = BigDecimal.ZERO;
//				            imp_det_total = BigDecimal.ZERO;
//				            imp_det_dol_total = BigDecimal.ZERO;
//				    	    
//		            		sub = false;
//		            		
//		            		if (!are_nat_pro.equals(rep_compra.getDes_reg_nac().toUpperCase())) {			                		
//			                				                		
//		                		table = new PdfPTable(cell_ven);
//					    	    
//					    	    cell = new PdfPCell();
//					    	    cell.setBorder(Rectangle.TOP);
//					            cell.setColspan(4);
//					            table.addCell(cell);
//					    	    
//						        p = new Paragraph("Total:", negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            p = new Paragraph(dec_form.format(num_imp_tot_afecto), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            p = new Paragraph(dec_form.format(num_imp_tot_no_afecto), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(num_imp_tot_igv), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(num_imp_tot_isc), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(imp_total), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(imp_dol_total), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            document.add(table);
//					            
//		                		
//					    	    num_imp_tot_afecto = BigDecimal.ZERO;
//					            num_imp_tot_no_afecto = BigDecimal.ZERO;
//					            num_imp_tot_igv = BigDecimal.ZERO;
//					            num_imp_tot_isc = BigDecimal.ZERO;
//					            imp_total = BigDecimal.ZERO;
//					            imp_dol_total = BigDecimal.ZERO;
//		                		
//					            
//					            document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//					            
//					            
//					            are_nat_pro = rep_compra.getDes_reg_nac().toUpperCase();
//					            
//					            table = new PdfPTable(cell_ven);
//						        p = new Paragraph(are_nat_pro, negrita);
//					            cell = new PdfPCell(p);
//					            cell.setBorder(Rectangle.NO_BORDER);
//					            cell.setColspan(11);
//					            table.addCell(cell);
//					            document.add(table);					            
//			                		
//				        		
//				        	} else { // Sede Central	
//				        		
//				        		document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//				        		
//				        	}
//		            		
//				        	
//				        	
//				        	table = new PdfPTable(cell_ven);
//				        	documento = rep_compra.getTip_comprobante();
//		            		p = new Paragraph("Documento: ".concat(documento.toUpperCase()), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            cell.setColspan(11);
//				            table.addCell(cell);
//				            document.add(table);
//				        	
//				        	
//		            		
//				            table = new PdfPTable(cell_ven);
//		            	}
//		            		
//		            	
//		            	String ser_nro = rep_compra.getSer_comprobante().concat(" - ");
//			    	    ser_nro = ser_nro.concat(rep_compra.getNro_comprobante());
//		            	p = new Paragraph(ser_nro, normal);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//		            	
//			            p = new Paragraph(rep_compra.getFec_comprobante(), normal);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(rep_compra.getTip_documento(), normal);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(getString(rep_compra.getRuc_nro_doc_prov()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(getString(rep_compra.getRaz_soc_prov()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(rep_compra.getNum_imp_afecto()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(rep_compra.getNum_imp_no_afecto()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(rep_compra.getNum_imp_igv()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//
//			            p = new Paragraph(dec_form.format(rep_compra.getNum_imp_isc()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			    	    if (rep_compra.getCod_moneda().equals(Constantes.ONE_INT)) { // Soles
//			    	    	p = new Paragraph(dec_form.format(rep_compra.getImp_det_total()), normal);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(BigDecimal.ZERO), normal);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            table.addCell(cell);
//				    	    
//
//				    	    imp_det_total = imp_det_total.add(rep_compra.getImp_det_total());
//				    	    imp_total = imp_total.add(rep_compra.getImp_det_total());
//				    	    imp_tot_gen_soles = imp_tot_gen_soles.add(rep_compra.getImp_det_total());
//			    	    } else { // Dolares
//			    	    	p = new Paragraph(dec_form.format(BigDecimal.ZERO), normal);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            table.addCell(cell);
//				    	    
//				            p = new Paragraph(dec_form.format(rep_compra.getImp_det_total()), normal);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            table.addCell(cell);
//				    	    
//				    	    
//				    	    imp_det_dol_total = imp_det_dol_total.add(rep_compra.getImp_det_total());
//				    	    imp_dol_total = imp_dol_total.add(rep_compra.getImp_det_total());
//				    	    imp_tot_gen_dolares = imp_tot_gen_dolares.add(rep_compra.getImp_det_total());
//			    	    }
//			    	    	
//			    	    document.add(table);
//			    	    
//			    	    
//			    	    num_imp_afecto = num_imp_afecto.add(rep_compra.getNum_imp_afecto());
//		                num_imp_no_afecto = num_imp_no_afecto.add(rep_compra.getNum_imp_no_afecto());
//		                num_imp_igv = num_imp_igv.add(rep_compra.getNum_imp_igv());
//		                num_imp_isc = num_imp_isc.add(rep_compra.getNum_imp_isc());	                
//		                
//
//	                	num_imp_tot_afecto = num_imp_tot_afecto.add(rep_compra.getNum_imp_afecto());
//		                num_imp_tot_no_afecto = num_imp_tot_no_afecto.add(rep_compra.getNum_imp_no_afecto());
//		                num_imp_tot_igv = num_imp_tot_igv.add(rep_compra.getNum_imp_igv());
//		                num_imp_tot_isc = num_imp_tot_isc.add(rep_compra.getNum_imp_isc());
//		                
//		                
//		                imp_tot_gen_afecto = imp_tot_gen_afecto.add(rep_compra.getNum_imp_afecto());
//		                imp_tot_gen_no_afecto = imp_tot_gen_no_afecto.add(rep_compra.getNum_imp_no_afecto());
//		                imp_tot_gen_igv = imp_tot_gen_igv.add(rep_compra.getNum_imp_igv());
//		                imp_tot_gen_isc = imp_tot_gen_isc.add(rep_compra.getNum_imp_isc());
//		                
//		
//			    	    if (can == ult) { // Validacion ultimo registro
//			    	    	
//			    	    	table = new PdfPTable(cell_ven);
//				    	    
//				    	    cell = new PdfPCell();
//				    	    cell.setBorder(Rectangle.TOP);
//				            cell.setColspan(4);
//				            table.addCell(cell);
//				    	    
//					        p = new Paragraph("Sub-Total:", negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//		            		
//				            p = new Paragraph(dec_form.format(num_imp_no_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_igv), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_isc), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_det_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//		            					            
//				            p = new Paragraph(dec_form.format(imp_det_dol_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            document.add(table);
//			    	    	
//				            
//				            table = new PdfPTable(cell_ven);
//				    	    
//				    	    cell = new PdfPCell();
//				    	    cell.setBorder(Rectangle.TOP);
//				            cell.setColspan(4);
//				            table.addCell(cell);
//				    	    
//					        p = new Paragraph("Total:", negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            p = new Paragraph(dec_form.format(num_imp_tot_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            p = new Paragraph(dec_form.format(num_imp_tot_no_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_tot_igv), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_tot_isc), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_dol_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            document.add(table);
//					    	   			    	 
//				            
//				            table = new PdfPTable(cell_ven);
//				    	    
//				    	    cell = new PdfPCell();
//				    	    cell.setBorder(Rectangle.TOP);
//				            cell.setColspan(4);
//				            table.addCell(cell);
//				    	    
//					        p = new Paragraph("Total General: ", negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            p = new Paragraph(dec_form.format(imp_tot_gen_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            p = new Paragraph(dec_form.format(imp_tot_gen_no_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_tot_gen_igv), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_tot_gen_isc), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_tot_gen_soles), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_tot_gen_dolares), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            document.add(table);
//				    	    
//			    	    }		    	    
//			    	    
//			    	    can++;
//		        		
//		        	}
//		        
//				}
//		        
//            	
//            }
//                    
//            document.close();
//            
//        } catch (Exception e) {
//        	log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//					  Constantes.NIVEL_APP_REPORTER, 
//					  this.getClass().getName(), e.getMessage()));
//        }   
//        return document;
//		
//	}
//
//	/**
//	 * @param response
//	 * @param compra
//	 * @param lista
//	 */
//	public void generaExcelReporteCompras(HttpServletResponse response, ComprasIngresosBean compra, List<ReporteDetalleComprasBean> lista) {
//		try{
//	    	HSSFWorkbook wb = new HSSFWorkbook();				
//	        HSSFSheet sheet = wb.createSheet("Comprobante de Pago");
//	        
//	        sheet.setColumnWidth(0, 1000);	        
//	        
//	        if (compra.getCod_servicio().equals(Constantes.ONE_INT)) { // Consolidado de Partidas por Rendición de Cuenta
//	        	sheet.setColumnWidth(1, 2000);
//	        	sheet.setColumnWidth(2, 6000);
//	        	sheet.setColumnWidth(3, 5000);
//				sheet.setColumnWidth(4, 5000);
//				sheet.setColumnWidth(5, 5000);
//				sheet.setColumnWidth(6, 5000);
//				sheet.setColumnWidth(7, 5000);
//				
//				sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 5));
//				sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 2));
//				sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 2));
//				sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 2));
//				sheet.addMergedRegion(new CellRangeAddress(7, 7, 3, 6));
//				
//	        } else if (compra.getCod_servicio().equals(Constantes.TWO_INT)) {  // Documentos de la Rendición de Cuenta
//	        	sheet.setColumnWidth(1, 2000);
//		        sheet.setColumnWidth(2, 4000);
//		        sheet.setColumnWidth(3, 4000);
//		        sheet.setColumnWidth(4, 10000);
//		        sheet.setColumnWidth(5, 5000);
//		        sheet.setColumnWidth(6, 6000);
//		        sheet.setColumnWidth(7, 4500);
//				sheet.setColumnWidth(8, 5000);
//				sheet.setColumnWidth(9, 5000);
//				sheet.setColumnWidth(10, 8000);
//				sheet.setColumnWidth(11, 4000);
//				sheet.setColumnWidth(12, 4000);
//				sheet.setColumnWidth(13, 4000);
//				sheet.setColumnWidth(14, 4000);
//				sheet.setColumnWidth(15, 4000);
//				sheet.setColumnWidth(16, 5000);
//				sheet.setColumnWidth(21, 5000);
//				
//				sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 7));
//				sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 2));
//				sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 2));				
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 1, 4));
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 5, 10));
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 11, 16));
//				
//	        } else if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Reporte de Compras
//	        	sheet.setColumnWidth(1, 5000);
//		        sheet.setColumnWidth(2, 5000);
//		        sheet.setColumnWidth(3, 5000);
//		        sheet.setColumnWidth(4, 4000);
//		        sheet.setColumnWidth(5, 5000);
//		        sheet.setColumnWidth(6, 5000);
//		        sheet.setColumnWidth(7, 6000);
//				sheet.setColumnWidth(8, 5000);
//				sheet.setColumnWidth(9, 5000);
//				sheet.setColumnWidth(10, 8000);
//				sheet.setColumnWidth(11, 5000);
//				sheet.setColumnWidth(12, 5000);
//				sheet.setColumnWidth(13, 5000);
//				sheet.setColumnWidth(14, 5000);
//				sheet.setColumnWidth(15, 5000);
//				sheet.setColumnWidth(16, 5000);
//				sheet.setColumnWidth(17, 5000);
//				sheet.setColumnWidth(18, 4000);
//				sheet.setColumnWidth(19, 5000);
//				sheet.setColumnWidth(20, 5000);
//				sheet.setColumnWidth(21, 5000);
//				sheet.setColumnWidth(22, 5000);
//				sheet.setColumnWidth(23, 5000);
//				sheet.setColumnWidth(24, 5000);
//				sheet.setColumnWidth(25, 5000);
//				sheet.setColumnWidth(26, 5000);
//				sheet.setColumnWidth(27, 5000);	
//				sheet.setColumnWidth(28, 6000);
//
//
//				sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 7));
//
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 1, 1));
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 2, 2));
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 3, 3));
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 4, 6));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 4, 4));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 5, 5));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 6, 6));
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 7, 7));
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 8, 10));
//				sheet.addMergedRegion(new CellRangeAddress(9, 9, 8, 9));
//				sheet.addMergedRegion(new CellRangeAddress(10, 10, 8, 8));
//				sheet.addMergedRegion(new CellRangeAddress(10, 10, 9, 9));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 10, 10));
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 11, 12));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 11, 11));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 12, 12));
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 13, 14));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 13, 13));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 14, 14));
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 15, 16));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 15, 15));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 16, 16));
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 17, 17));
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 18, 18));
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 19, 19));
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 20, 20));
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 21, 21));
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 22, 23));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 22, 22));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 23, 23));
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 24, 24));
//								
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 25, 28));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 25, 25));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 26, 26));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 27, 27));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 28, 28));
//				
//	        } else if (compra.getCod_servicio().equals(Constantes.FOUR_INT)) { // Registro de Compras
//	        	
//	        	sheet.setColumnWidth(1, 5000);
//	        	sheet.setColumnWidth(2, 4000);
//		        sheet.setColumnWidth(3, 3000);
//		        sheet.setColumnWidth(4, 4000);
//		        sheet.setColumnWidth(5, 7000);
//		        sheet.setColumnWidth(6, 4000);
//				sheet.setColumnWidth(7, 4000);
//				sheet.setColumnWidth(8, 4000);
//				sheet.setColumnWidth(9, 4000);
//				sheet.setColumnWidth(10, 4000);
//				sheet.setColumnWidth(11, 4000);
//				
//				sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 7));
//				sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 7));
//				sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 4));
//	        	
//	        }
//
//	        HSSFRow row0 = sheet.createRow((short) 0);
//			HSSFRow row1 = sheet.createRow((short) 1);
//	        HSSFRow row3 = sheet.createRow((short) 3);
//	        HSSFRow row4 = sheet.createRow((short) 4);
//	        HSSFRow row5 = sheet.createRow((short) 5);
//	        HSSFRow row6 = sheet.createRow((short) 6);
//	        HSSFRow row7 = sheet.createRow((short) 7);
//	        HSSFRow row8 = sheet.createRow((short) 8);
//	        HSSFRow row9 = sheet.createRow((short) 9);
//	        HSSFRow row10 = sheet.createRow((short) 10);
//	        
//	        if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Reporte de Compras
//	        	row8.setHeight((short) 0x400);
//	        	row9.setHeight((short) 0x170);
//	        	row10.setHeight((short) 0x170);
//	        }
//	        
//	        HSSFFont font_bold = wb.createFont();
//	        font_bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//	        
//	        HSSFFont font_norm = wb.createFont();
//	        font_norm.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//	        
//	        DecimalFormatSymbols other_symbols = new DecimalFormatSymbols(Locale.US);
//            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, other_symbols);
//	        
//	        
//	        HSSFCellStyle style_tit = (HSSFCellStyle) wb.createCellStyle();
//	        style_tit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style_tit.setFont(font_bold);
//	        
//	        
//	        HSSFCellStyle style_hed = (HSSFCellStyle) wb.createCellStyle();
//	        style_hed.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//	        style_hed.setFont(font_bold);
//	        
//	        HSSFCellStyle style_cell = (HSSFCellStyle) wb.createCellStyle();
//            style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//            style_cell.setFont(font_norm);	        
//	        style_cell.setBorderBottom((short) 1);
//	        style_cell.setBorderLeft((short) 1);	        
//	        style_cell.setBorderRight((short) 1);
//	        style_cell.setBorderTop((short) 1);
//	        
//	        HSSFCellStyle style_total = (HSSFCellStyle) wb.createCellStyle();
//	        style_total.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//	        style_total.setFont(font_bold);	        
//	        style_total.setBorderBottom((short) 1);
//	        style_total.setBorderLeft((short) 1);	        
//	        style_total.setBorderRight((short) 1);
//	        style_total.setBorderTop((short) 1);
//	        
//	        HSSFCellStyle style_mont = (HSSFCellStyle) wb.createCellStyle();
//	        style_mont.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//	        style_mont.setFont(font_norm);	        
//	        style_mont.setBorderBottom((short) 1);
//	        style_mont.setBorderLeft((short) 1);	        
//	        style_mont.setBorderRight((short) 1);
//	        style_mont.setBorderTop((short) 1);
//	        
//	        HSSFCellStyle style_sub_mont = (HSSFCellStyle) wb.createCellStyle();
//	        style_sub_mont.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//	        style_sub_mont.setFont(font_bold);	        
//	        style_sub_mont.setBorderBottom((short) 1);
//	        style_sub_mont.setBorderLeft((short) 1);	        
//	        style_sub_mont.setBorderRight((short) 1);
//	        style_sub_mont.setBorderTop((short) 1);
//	        	        
//	        
//	        String titulo = null;
//	        if (compra.getCod_servicio().equals(Constantes.ONE_INT)) { // Consolidado de Partidas por Rendición de Cuenta
//	        	titulo = "Resumen de Rendiciones por Encargo";
//	        	row1.createCell(3).setCellValue(titulo);	        
//		        row1.getCell(3).setCellStyle(style_tit);
//	        } else if (compra.getCod_servicio().equals(Constantes.TWO_INT)) {  // Documentos de la Rendición de Cuenta	        
//	        	titulo = "Documentos de la Rendición de Cuenta";
//		        row1.createCell(5).setCellValue(titulo);	        
//		        row1.getCell(5).setCellStyle(style_tit);
//	        } else if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Reporte de Compras
//	        	String fec_sistema = "Fecha del sistema : ";
//            	String fec_actual = DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, new Date());
//	        	row0.createCell(1).setCellValue(fec_sistema.concat(fec_actual));	        
//	        	row0.getCell(1).setCellStyle(style_hed);
//	        	
//	        	titulo = "Reporte de Compras";
//		        row1.createCell(5).setCellValue(titulo);	        
//		        row1.getCell(5).setCellStyle(style_tit);
//	        } else if (compra.getCod_servicio().equals(Constantes.FOUR_INT)) { // Registro de Compras
//	        	String fec_sistema = "Fecha del sistema : ";
//            	String fec_actual = DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, new Date());
//	        	row0.createCell(1).setCellValue(fec_sistema.concat(fec_actual));	        
//	        	row0.getCell(1).setCellStyle(style_hed);	        	
//	        	
//	        	titulo = "Reporte de Compras";
//		        row1.createCell(5).setCellValue(titulo);	        
//		        row1.getCell(5).setCellStyle(style_tit);
//	        }
//	        
//	        
//	        if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Reporte de Compras
//	        	HSSFRichTextString for_rep = new HSSFRichTextString("FORMATO 8.1 : ");
//		        HSSFRichTextString for_des = new HSSFRichTextString("REGISTRO DE COMPRAS");
//		        RichTextString fec_rep_compra = new HSSFRichTextString(for_rep.getString() + for_des.getString());
//		        fec_rep_compra.applyFont(0, 13, font_norm);
//		        fec_rep_compra.applyFont(14, 33, font_bold);
//		        row3.createCell(1).setCellValue(fec_rep_compra);
//	        } else if (compra.getCod_servicio().equals(Constantes.FOUR_INT)) { // Registro de Compras
//	        	HSSFRichTextString for_rep = new HSSFRichTextString("              ");
//		        HSSFRichTextString for_des = new HSSFRichTextString("REGISTRO DE COMPRAS");
//		        RichTextString fec_rep_compra = new HSSFRichTextString(for_rep.getString() + for_des.getString());
//		        fec_rep_compra.applyFont(0, 13, font_norm);
//		        fec_rep_compra.applyFont(14, 33, font_bold);
//		        row3.createCell(1).setCellValue(fec_rep_compra);
//	        } else {
//	        	HSSFRichTextString uni_operativa = new HSSFRichTextString("Unidad Operativa : ");
//		        uni_operativa.applyFont(font_bold);
//		        row3.createCell(1).setCellValue(uni_operativa);
//		        String des_uni_operativa = compra.getDes_uni_operativa();
//	            if ((des_uni_operativa.trim()).length() == 0) {
//	            	des_uni_operativa = "Todos";
//	            }
//		        row3.createCell(3).setCellValue(des_uni_operativa);
//	        }
//	        
//	        
//	        ReporteDetalleComprasBean rep_com_tra = lista.get(0);
//	        
//	        if (compra.getCod_servicio().equals(Constantes.TWO_INT) && !isNullOrEmpty(compra.getNro_doc_rc())) {
//	        	HSSFRichTextString tex_transf = new HSSFRichTextString("Fte Fto: ");
//		        HSSFRichTextString det_transf = new HSSFRichTextString(rep_com_tra.getDes_fue_financiamiento());
//		        RichTextString det_rango = new HSSFRichTextString(tex_transf.getString() + det_transf.getString());
//		        det_rango.applyFont(0, 8, font_bold);
//		        row3.createCell(9).setCellValue(det_rango);	        	
//	        }
//	        
//	        if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Reporte de Compras
//	        	
//		        row4.createCell(1).setCellValue("PERIODO : ");
//		        
//		        StringBuilder periodo = new StringBuilder();
//		        periodo.append(getMes(compra.getCod_mes()));
//		        periodo.append(Constantes.ESPACIO);
//		        periodo.append(DateUtil.getAnioActual());
//		        
//		        HSSFRichTextString per = new HSSFRichTextString(periodo.toString());
//		        per.applyFont(font_bold);
//
//		        row4.createCell(2).setCellValue(per);
//	        	
//	        } else if (compra.getCod_servicio().equals(Constantes.FOUR_INT)) { // Registro de Compras
//	        	
//	        	row4.createCell(1).setCellValue("PERIODO : ");
//		        
//		        StringBuilder periodo = new StringBuilder();
//		        periodo.append(getMes(compra.getCod_mes()));
//		        periodo.append(Constantes.ESPACIO);
//		        periodo.append(DateUtil.getAnioActual());
//		        
//		        HSSFRichTextString per = new HSSFRichTextString(periodo.toString());
//		        per.applyFont(font_bold);
//
//		        row4.createCell(2).setCellValue(per);
//		        
//	        } else {
//	        	
//		        HSSFRichTextString anp = new HSSFRichTextString("ANP : ");
//		        anp.applyFont(font_bold);
//		        row4.createCell(1).setCellValue(anp);
//		        String des_anp = compra.getDes_reg_nac();
//	            if ((des_anp.trim()).length() == 0) {
//	            	des_anp = "Todos";
//	            }
//		        row4.createCell(3).setCellValue(des_anp);
//		        
//	        }
//	        
//	        
//	        if (compra.getCod_servicio().equals(Constantes.TWO_INT) && !isNullOrEmpty(compra.getNro_doc_rc())) {
//	        	HSSFRichTextString tex_transf = new HSSFRichTextString("Nro SIAF: ");
//		        HSSFRichTextString det_transf = new HSSFRichTextString(getString(rep_com_tra.getNum_siaf()));
//		        RichTextString det_rango = new HSSFRichTextString(tex_transf.getString() + det_transf.getString());
//		        det_rango.applyFont(0, 8, font_bold);
//		        row4.createCell(9).setCellValue(det_rango);	        	
//	        }
//	        	      
//	        if (!isNullOrEmpty(compra.getFec_inicio()) && !isNullOrEmpty(compra.getFec_final())) {
//	        	
//	        	HSSFRichTextString fec_del = new HSSFRichTextString("Del  ");
//		        HSSFRichTextString fec_ini = new HSSFRichTextString(compra.getFec_inicio());
//		        HSSFRichTextString fec_al = new HSSFRichTextString("  al  ");
//		        HSSFRichTextString fec_fin = new HSSFRichTextString(compra.getFec_final());
//		        RichTextString fec_rango = new HSSFRichTextString(fec_del.getString() + fec_ini.getString() +
//						  										  fec_al.getString() + fec_fin.getString());
//		        fec_rango.applyFont(0, 3, font_bold);
//		        fec_rango.applyFont(5, 15, font_norm);
//		        fec_rango.applyFont(17, 19, font_bold);
//		        fec_rango.applyFont(21, 31, font_norm);
//		        row5.createCell(1).setCellValue(fec_rango);
//		        
//	        } else if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Reporte de Compras
//	        	
//		        row5.createCell(1).setCellValue("RUC : ");
//		        HSSFRichTextString per = new HSSFRichTextString("20478053178");
//		        per.applyFont(font_bold);
//		        row5.createCell(2).setCellValue(per);
//		        		        		        
//		        HSSFRichTextString raz_soc = new HSSFRichTextString("APELLIDOS Y NOMBRE, DENOMINACION O RAZÓN SOCIAL : ");
//		        HSSFRichTextString det_raz_soc = new HSSFRichTextString("Servicio Nacional de Áreas Naturales Protegidas por el Estado");
//		        RichTextString raz_social = new HSSFRichTextString(raz_soc.getString() + det_raz_soc.getString());
//		        raz_social.applyFont(50, 111, font_bold);
//		        row6.createCell(1).setCellValue(raz_social);
//		        
//	        } else if (compra.getCod_servicio().equals(Constantes.FOUR_INT)) { // Registro de Compras
//	        	
//	        	row5.createCell(1).setCellValue("RUC : ");
//		        HSSFRichTextString per = new HSSFRichTextString("20478053178");
//		        per.applyFont(font_bold);
//		        row5.createCell(2).setCellValue(per);
//		        		        		        
//		        HSSFRichTextString raz_soc = new HSSFRichTextString("APELLIDOS Y NOMBRE, DENOMINACION O RAZÓN SOCIAL : ");
//		        HSSFRichTextString det_raz_soc = new HSSFRichTextString("Servicio Nacional de Áreas Naturales Protegidas por el Estado");
//		        RichTextString raz_social = new HSSFRichTextString(raz_soc.getString() + det_raz_soc.getString());
//		        raz_social.applyFont(50, 111, font_bold);
//		        row6.createCell(1).setCellValue(raz_social);	        	
//		        
//	        } else if (!isNullInteger(compra.getCod_mes())) {
//	        	
//	        	HSSFRichTextString periodo = new HSSFRichTextString("Periodo : ");
//	        	periodo.applyFont(font_bold);
//	        	row5.createCell(1).setCellValue(periodo);
//	 	        row5.createCell(3).setCellValue(getMes(compra.getCod_mes()));	
//	 	        
//	        }	        
//	        
//	        if (compra.getCod_servicio().equals(Constantes.TWO_INT) && !isNullOrEmpty(compra.getNro_doc_rc())) {
//	        	HSSFRichTextString tex_transf = new HSSFRichTextString("Nro C/P: ");
//		        HSSFRichTextString det_transf = new HSSFRichTextString(getString(rep_com_tra.getNum_comprobante()));
//		        RichTextString det_rango = new HSSFRichTextString(tex_transf.getString() + det_transf.getString());
//		        det_rango.applyFont(0, 8, font_bold);
//		        row5.createCell(9).setCellValue(det_rango);	   
//
//		        tex_transf = new HSSFRichTextString("Fecha Transf.: ");
//		        det_transf = new HSSFRichTextString(getString(rep_com_tra.getFec_com_transferencia()));
//		        det_rango = new HSSFRichTextString(tex_transf.getString() + det_transf.getString());
//		        det_rango.applyFont(0, 14, font_bold);
//		        row6.createCell(9).setCellValue(det_rango);	 
//	        }
//	        
//
//	        
//	        HSSFCellStyle style_header = (HSSFCellStyle) wb.createCellStyle();
//	        style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style_header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style_header.setFont(font_bold);	        
//	        HSSFColor color = setColor(wb, (byte) 242, (byte)242, (byte) 242);
//	        style_header.setFillForegroundColor(color.getIndex());
//	        style_header.setFillPattern(CellStyle.SOLID_FOREGROUND);
//	        style_header.setBorderBottom((short) 1);
//	        style_header.setBorderLeft((short) 1);	        
//	        style_header.setBorderRight((short) 1);
//	        style_header.setBorderTop((short) 1);
//	        
//	        if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Reporte de Compras
//	        	style_header.setWrapText(true);
//	        }
//	        
//	        HSSFCellStyle style_top = (HSSFCellStyle) wb.createCellStyle();
//	        style_top.setBorderTop((short) 1);
//	        
//	        HSSFCellStyle style_bottom = (HSSFCellStyle) wb.createCellStyle();
//	        style_bottom.setBorderBottom((short) 1);	  
//	        
//	        HSSFCellStyle style_left = (HSSFCellStyle) wb.createCellStyle();
//	        style_left.setBorderLeft((short) 1);
//	        
//	        HSSFCellStyle style_right = (HSSFCellStyle) wb.createCellStyle();
//	        style_right.setBorderRight((short) 1);
//	        
//	        
//	        if (compra.getCod_servicio().equals(Constantes.ONE_INT)) { // Consolidado de Partidas por Rendición de Cuenta
//					
//	        	row9.createCell(2).setCellValue("");
//				row9.getCell(2).setCellStyle(style_bottom);
//	        	row9.createCell(4).setCellValue("");
//				row9.getCell(4).setCellStyle(style_bottom);
//				row9.createCell(5).setCellValue("");
//				row9.getCell(5).setCellStyle(style_bottom);
//				row9.createCell(6).setCellValue("");
//				row9.getCell(6).setCellStyle(style_bottom);
//							
//				
//				row9.createCell(1).setCellValue("");
//		        row9.getCell(1).setCellStyle(style_header);
//				
//		        row9.createCell(3).setCellValue("EJECUCION FINANCIERA");
//		        row9.getCell(3).setCellStyle(style_header);
//		        		        
//		        row9.createCell(7).setCellValue("");
//		        row9.getCell(7).setCellStyle(style_header);
//				
//		        row9.createCell(1).setCellValue("Item");
//		        row9.getCell(1).setCellStyle(style_header);
//		        
//		        row9.createCell(2).setCellValue("Partida Especifica");
//		        row9.getCell(2).setCellStyle(style_header);
//		        
//		        row9.createCell(3).setCellValue("POA");
//		        row9.getCell(3).setCellStyle(style_header);
//		        
//		        row9.createCell(4).setCellValue("Transferido");
//		        row9.getCell(4).setCellStyle(style_header);
//		        
//		        row9.createCell(5).setCellValue("Ejecutado");
//		        row9.getCell(5).setCellStyle(style_header);
//		        
//		        row9.createCell(6).setCellValue("Saldo");
//		        row9.getCell(6).setCellStyle(style_header);
//		        
//		        row9.createCell(7).setCellValue("Devoluciones");
//		        row9.getCell(7).setCellStyle(style_header);
//		        
//		        
//		        int row = 1;
//	            BigDecimal num_poa = BigDecimal.ZERO;
//	            BigDecimal num_tra = BigDecimal.ZERO;
//	            BigDecimal num_eje = BigDecimal.ZERO;
//	            BigDecimal num_sal = BigDecimal.ZERO;
//	            BigDecimal num_dev = BigDecimal.ZERO;
//		        
//
//		        for (ReporteDetalleComprasBean rep_compra : lista) {
//		        	
//		        	HSSFRow rows  = sheet.createRow((short) row + 9);
//		        	
//		        	rows.createCell(1).setCellValue(row);
//			        rows.getCell(1).setCellStyle(style_cell);
//			        
//			        rows.createCell(2).setCellValue(getString(rep_compra.getVcod_especifica()));
//			        rows.getCell(2).setCellStyle(style_cell);
//			        
//			        rows.createCell(3).setCellValue(dec_form.format(rep_compra.getImp_partida()));
//			        rows.getCell(3).setCellStyle(style_mont);
//			        
//			        rows.createCell(4).setCellValue(dec_form.format(rep_compra.getImp_partida()));
//			        rows.getCell(4).setCellStyle(style_mont);
//			        
//			        rows.createCell(5).setCellValue(dec_form.format(rep_compra.getImp_det_total()));
//			        rows.getCell(5).setCellStyle(style_mont);
//			        
//			        rows.createCell(6).setCellValue(dec_form.format(rep_compra.getImp_saldo()));
//			        rows.getCell(6).setCellStyle(style_mont);
//			        
//			        rows.createCell(7).setCellValue(dec_form.format(rep_compra.getImp_devolucion()));
//			        rows.getCell(7).setCellStyle(style_mont);
//			        
//			        num_poa = num_poa.add(rep_compra.getImp_partida());
//	                num_tra = num_tra.add(rep_compra.getImp_partida());
//	                num_eje = num_eje.add(rep_compra.getImp_det_total());
//	                num_sal = num_sal.add(rep_compra.getImp_saldo());
//	                num_dev = num_dev.add(rep_compra.getImp_devolucion());
//		            
//		            row++;
//		        }
//	        	
//		        HSSFCellStyle style_cell_total = (HSSFCellStyle) wb.createCellStyle();
//		        style_cell_total.setFont(font_bold);	        
//		        style_cell_total.setBorderBottom((short) 1);
//		        style_cell_total.setBorderLeft((short) 1);	        
//		        style_cell_total.setBorderRight((short) 1);
//		        style_cell_total.setBorderTop((short) 1);
//		        
//		        HSSFCellStyle style_tot_mont = (HSSFCellStyle) wb.createCellStyle();
//		        style_tot_mont.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//		        style_tot_mont.setFont(font_bold);	        
//		        style_tot_mont.setBorderBottom((short) 1);
//		        style_tot_mont.setBorderLeft((short) 1);	        
//		        style_tot_mont.setBorderRight((short) 1);
//		        style_tot_mont.setBorderTop((short) 1);
//		        
//		        HSSFRow row_total  = sheet.createRow((short) row + 9);
//		        
//		        HSSFRichTextString total = new HSSFRichTextString("Totales:");
//		        total.applyFont(font_bold);
//		        row_total.createCell(2).setCellValue(total);
//		        
//		        row_total.createCell(3).setCellValue(dec_form.format(num_poa));
//		        row_total.getCell(3).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(4).setCellValue(dec_form.format(num_tra));
//		        row_total.getCell(4).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(5).setCellValue(dec_form.format(num_eje));
//		        row_total.getCell(5).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(6).setCellValue(dec_form.format(num_sal));
//		        row_total.getCell(6).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(7).setCellValue(dec_form.format(num_dev));
//		        row_total.getCell(7).setCellStyle(style_tot_mont);
//				
//	        } else if (compra.getCod_servicio().equals(Constantes.TWO_INT)) {  // Documentos de la Rendición de Cuenta
//
//				row7.createCell(2).setCellValue("");
//				row7.getCell(2).setCellStyle(style_bottom);
//				row7.createCell(3).setCellValue("");
//				row7.getCell(3).setCellStyle(style_bottom);
//				row7.createCell(4).setCellValue("");
//				row7.getCell(4).setCellStyle(style_bottom);
//				row7.createCell(5).setCellValue("");
//				row7.getCell(5).setCellStyle(style_bottom);
//				row7.createCell(6).setCellValue("");
//				row7.getCell(6).setCellStyle(style_bottom);
//				row7.createCell(7).setCellValue("");
//				row7.getCell(7).setCellStyle(style_bottom);
//				row7.createCell(8).setCellValue("");
//				row7.getCell(8).setCellStyle(style_bottom);
//				row7.createCell(9).setCellValue("");
//				row7.getCell(9).setCellStyle(style_bottom);
//				row7.createCell(10).setCellValue("");
//				row7.getCell(10).setCellStyle(style_bottom);
//				row7.createCell(11).setCellValue("");
//				row7.getCell(11).setCellStyle(style_bottom);
//				row7.createCell(12).setCellValue("");
//				row7.getCell(12).setCellStyle(style_bottom);
//				row7.createCell(13).setCellValue("");
//				row7.getCell(13).setCellStyle(style_bottom);
//				row7.createCell(14).setCellValue("");
//				row7.getCell(14).setCellStyle(style_bottom);
//				row7.createCell(15).setCellValue("");
//				row7.getCell(15).setCellStyle(style_bottom);
//				row7.createCell(16).setCellValue("");
//				row7.getCell(16).setCellStyle(style_bottom);
//				
//				
//				row8.createCell(1).setCellValue("");
//		        row8.getCell(1).setCellStyle(style_header);
//				
//		        row8.createCell(5).setCellValue("DOCUMENTO DE GASTOS");
//		        row8.getCell(5).setCellStyle(style_header);
//		        
//		        row8.createCell(11).setCellValue("IMPORTE");
//		        row8.getCell(11).setCellStyle(style_header);
//		        
//		        row8.createCell(17).setCellValue("");
//		        row8.getCell(17).setCellStyle(style_left);
//				
//		        row9.createCell(1).setCellValue("Item");
//		        row9.getCell(1).setCellStyle(style_header);
//		        
//		        row9.createCell(2).setCellValue("Nro R/C");
//		        row9.getCell(2).setCellStyle(style_header);
//		        
//		        row9.createCell(3).setCellValue("Partida Esp.");
//		        row9.getCell(3).setCellStyle(style_header);
//		        
//		        row9.createCell(4).setCellValue("Especifica");
//		        row9.getCell(4).setCellStyle(style_header);
//		        
//		        row9.createCell(5).setCellValue("Fecha C/P");
//		        row9.getCell(5).setCellStyle(style_header);
//		        
//		        row9.createCell(6).setCellValue("SIAF");
//		        row9.getCell(6).setCellStyle(style_header);
//		        
//		        row9.createCell(7).setCellValue("Comp. de Pago");
//		        row9.getCell(7).setCellStyle(style_header);
//		        
//		        row9.createCell(8).setCellValue("Nro");
//		        row9.getCell(8).setCellStyle(style_header);
//		        
//		        row9.createCell(9).setCellValue("RUC");
//		        row9.getCell(9).setCellStyle(style_header);
//		        
//		        row9.createCell(10).setCellValue("Razon Social");
//		        row9.getCell(10).setCellStyle(style_header);
//		        
//		        row9.createCell(11).setCellValue("Monto");
//		        row9.getCell(11).setCellStyle(style_header);
//		        
//		        row9.createCell(12).setCellValue("Afecto");
//		        row9.getCell(12).setCellStyle(style_header);
//		
//		        row9.createCell(13).setCellValue("No Afecto");
//		        row9.getCell(13).setCellStyle(style_header);
//		
//		        row9.createCell(14).setCellValue("IGV");
//		        row9.getCell(14).setCellStyle(style_header);
//		
//		        row9.createCell(15).setCellValue("ISC");
//		        row9.getCell(15).setCellStyle(style_header);
//		
//		        row9.createCell(16).setCellValue("Total");
//		        row9.getCell(16).setCellStyle(style_header);
//		        
//		 
//		        int row = 1;
//		        BigDecimal num_imp_afecto = BigDecimal.ZERO;
//		        BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//		        BigDecimal num_imp_igv = BigDecimal.ZERO;
//		        BigDecimal num_imp_isc = BigDecimal.ZERO;
//		        BigDecimal imp_det_total = BigDecimal.ZERO;
//		        
//		        
//		        for (ReporteDetalleComprasBean rep_compra : lista) {
//		        	
//		        	HSSFRow rows  = sheet.createRow((short) row + 9);
//		        	
//		        	rows.createCell(1).setCellValue(row);
//			        rows.getCell(1).setCellStyle(style_cell);
//			        
//			        rows.createCell(2).setCellValue(getString(rep_compra.getNro_doc_rc()));
//			        rows.getCell(2).setCellStyle(style_cell);
//			        
//			        rows.createCell(3).setCellValue(rep_compra.getVcod_especifica());
//			        rows.getCell(3).setCellStyle(style_cell);
//			        
//			        rows.createCell(4).setCellValue(rep_compra.getDes_especifica());
//			        rows.getCell(4).setCellStyle(style_cell);
//			        
//			        rows.createCell(5).setCellValue(rep_compra.getFec_comprobante());
//			        rows.getCell(5).setCellStyle(style_cell);
//			        
//			        rows.createCell(6).setCellValue(getString(rep_compra.getNro_siaf()));
//			        rows.getCell(6).setCellStyle(style_cell);
//			        
//			        rows.createCell(7).setCellValue(rep_compra.getTip_comprobante());
//			        rows.getCell(7).setCellStyle(style_cell);
//			        
//			        rows.createCell(8).setCellValue(rep_compra.getNro_ser_comprobante());
//			        rows.getCell(8).setCellStyle(style_cell);
//			        
//			        rows.createCell(9).setCellValue(rep_compra.getRuc_nro_doc_prov());
//			        rows.getCell(9).setCellStyle(style_cell);
//			        
//			        rows.createCell(10).setCellValue(rep_compra.getRaz_soc_prov());
//			        rows.getCell(10).setCellStyle(style_cell);		   
//		
//		            rows.createCell(11).setCellValue(dec_form.format(rep_compra.getNum_imp_servicio()));
//			        rows.getCell(11).setCellStyle(style_mont);
//		            
//			        rows.createCell(12).setCellValue(dec_form.format(rep_compra.getNum_imp_afecto()));
//			        rows.getCell(12).setCellStyle(style_mont);
//		
//			        rows.createCell(13).setCellValue(dec_form.format(rep_compra.getNum_imp_no_afecto()));
//			        rows.getCell(13).setCellStyle(style_mont);
//		
//			        rows.createCell(14).setCellValue(dec_form.format(rep_compra.getNum_imp_igv()));
//			        rows.getCell(14).setCellStyle(style_mont);
//		
//			        rows.createCell(15).setCellValue(dec_form.format(rep_compra.getNum_imp_isc()));
//			        rows.getCell(15).setCellStyle(style_mont);
//		
//			        rows.createCell(16).setCellValue(dec_form.format(rep_compra.getImp_det_total()));
//			        rows.getCell(16).setCellStyle(style_mont);
//		            
//		            num_imp_afecto = num_imp_afecto.add(rep_compra.getNum_imp_afecto());
//		            num_imp_no_afecto = num_imp_no_afecto.add(rep_compra.getNum_imp_no_afecto());
//		            num_imp_igv = num_imp_igv.add(rep_compra.getNum_imp_igv());
//		            num_imp_isc = num_imp_isc.add(rep_compra.getNum_imp_isc());
//		            imp_det_total = imp_det_total.add(rep_compra.getImp_det_total());
//		            
//		            row++;	
//		        }
//		        
//		        
//		        HSSFCellStyle style_cell_total = (HSSFCellStyle) wb.createCellStyle();
//		        style_cell_total.setFont(font_bold);	        
//		        style_cell_total.setBorderBottom((short) 1);
//		        style_cell_total.setBorderLeft((short) 1);	        
//		        style_cell_total.setBorderRight((short) 1);
//		        style_cell_total.setBorderTop((short) 1);
//		        
//		        HSSFCellStyle style_tot_mont = (HSSFCellStyle) wb.createCellStyle();
//		        style_tot_mont.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//		        style_tot_mont.setFont(font_bold);	        
//		        style_tot_mont.setBorderBottom((short) 1);
//		        style_tot_mont.setBorderLeft((short) 1);	        
//		        style_tot_mont.setBorderRight((short) 1);
//		        style_tot_mont.setBorderTop((short) 1);
//		        
//		        HSSFRow row_total  = sheet.createRow((short) row + 9);
//		        
//		        HSSFRichTextString total = new HSSFRichTextString("Totales:");
//		        total.applyFont(font_bold);
//		        row_total.createCell(11).setCellValue(total);
//		        
//		        row_total.createCell(12).setCellValue(dec_form.format(num_imp_afecto));
//		        row_total.getCell(12).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(13).setCellValue(dec_form.format(num_imp_no_afecto));
//		        row_total.getCell(13).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(14).setCellValue(dec_form.format(num_imp_igv));
//		        row_total.getCell(14).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(15).setCellValue(dec_form.format(num_imp_isc));
//		        row_total.getCell(15).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(16).setCellValue(dec_form.format(imp_det_total));
//		        row_total.getCell(16).setCellStyle(style_tot_mont);
//		        
//			} else if (compra.getCod_servicio().equals(Constantes.THREE_INT)) { // Reporte de Compras
//				
//				row7.createCell(5).setCellValue("");
//				row7.getCell(5).setCellStyle(style_bottom);
//				
//				row7.createCell(6).setCellValue("");
//				row7.getCell(6).setCellStyle(style_bottom);
//				
//				row7.createCell(9).setCellValue("");
//				row7.getCell(9).setCellStyle(style_bottom);
//				
//				row7.createCell(10).setCellValue("");
//				row7.getCell(10).setCellStyle(style_bottom);
//				
//				row7.createCell(12).setCellValue("");
//				row7.getCell(12).setCellStyle(style_bottom);
//				
//				row7.createCell(14).setCellValue("");
//				row7.getCell(14).setCellStyle(style_bottom);
//				
//				row7.createCell(16).setCellValue("");
//				row7.getCell(16).setCellStyle(style_bottom);
//				
//				row7.createCell(23).setCellValue("");
//				row7.getCell(23).setCellStyle(style_bottom);
//						
//				row7.createCell(26).setCellValue("");
//				row7.getCell(26).setCellStyle(style_bottom);
//				
//				row7.createCell(27).setCellValue("");
//				row7.getCell(27).setCellStyle(style_bottom);
//				
//				row7.createCell(28).setCellValue("");
//				row7.getCell(28).setCellStyle(style_bottom);				
//				
//				row9.createCell(0).setCellValue("");
//				row9.getCell(0).setCellStyle(style_right);
//				
//				row10.createCell(0).setCellValue("");
//				row10.getCell(0).setCellStyle(style_right);
//				
//				row9.createCell(1).setCellValue("");
//				row9.getCell(1).setCellStyle(style_right);
//				
//				row10.createCell(1).setCellValue("");
//				row10.getCell(1).setCellStyle(style_right);
//				
//				row9.createCell(2).setCellValue("");
//				row9.getCell(2).setCellStyle(style_right);
//				
//				row10.createCell(2).setCellValue("");
//				row10.getCell(2).setCellStyle(style_right);
//				
//				row10.createCell(3).setCellValue("");
//				row10.getCell(3).setCellStyle(style_right);
//				
//				row10.createCell(4).setCellValue("");
//				row10.getCell(4).setCellStyle(style_right);
//				
//				row10.createCell(5).setCellValue("");
//				row10.getCell(5).setCellStyle(style_right);
//				
//				row10.createCell(6).setCellValue("");
//				row10.getCell(6).setCellStyle(style_right);
//				
//				row9.createCell(9).setCellValue("");
//				row9.getCell(9).setCellStyle(style_top);
//				
//				row10.createCell(10).setCellValue("");
//				row10.getCell(10).setCellStyle(style_right);
//				
//				row10.createCell(11).setCellValue("");
//				row10.getCell(11).setCellStyle(style_right);
//				
//				row10.createCell(12).setCellValue("");
//				row10.getCell(12).setCellStyle(style_right);
//				
//				row10.createCell(13).setCellValue("");
//				row10.getCell(13).setCellStyle(style_right);
//				
//				row10.createCell(14).setCellValue("");
//				row10.getCell(14).setCellStyle(style_right);
//				
//				row10.createCell(15).setCellValue("");
//				row10.getCell(15).setCellStyle(style_right);
//				
//				row10.createCell(16).setCellValue("");
//				row10.getCell(16).setCellStyle(style_right);
//				
//				row9.createCell(17).setCellValue("");
//				row9.getCell(17).setCellStyle(style_right);
//				
//				row10.createCell(17).setCellValue("");
//				row10.getCell(17).setCellStyle(style_right);
//				
//				row9.createCell(18).setCellValue("");
//				row9.getCell(18).setCellStyle(style_right);
//				
//				row10.createCell(18).setCellValue("");
//				row10.getCell(18).setCellStyle(style_right);
//				
//				row9.createCell(19).setCellValue("");
//				row9.getCell(19).setCellStyle(style_right);
//				
//				row10.createCell(19).setCellValue("");
//				row10.getCell(19).setCellStyle(style_right);
//				
//				row9.createCell(20).setCellValue("");
//				row9.getCell(20).setCellStyle(style_right);
//				
//				row10.createCell(20).setCellValue("");
//				row10.getCell(20).setCellStyle(style_right);
//				
//				row10.createCell(21).setCellValue("");
//				row10.getCell(21).setCellStyle(style_right);
//				
//				row10.createCell(22).setCellValue("");
//				row10.getCell(22).setCellStyle(style_right);
//				
//				row10.createCell(23).setCellValue("");
//				row10.getCell(23).setCellStyle(style_right);
//				
//				row10.createCell(24).setCellValue("");
//				row10.getCell(24).setCellStyle(style_right);
//				
//				row10.createCell(25).setCellValue("");
//				row10.getCell(25).setCellStyle(style_right);
//				
//				row10.createCell(26).setCellValue("");
//				row10.getCell(26).setCellStyle(style_right);
//				
//				row10.createCell(27).setCellValue("");
//				row10.getCell(27).setCellStyle(style_right);
//
//				row10.createCell(28).setCellValue("");
//				row10.getCell(28).setCellStyle(style_right);
//							
//				row8.createCell(29).setCellValue("");
//				row8.getCell(29).setCellStyle(style_left);
//								
//		
//				
//				row8.createCell(1).setCellValue("NÚMERO CORRELATIVO DEL REGISTRO O CÓDIGO UNICO DE LA OPERACIÓN");
//		        row8.getCell(1).setCellStyle(style_header);
//				
//		        row8.createCell(2).setCellValue("FECHA DE EMISIÓN DEL COMPROBANTE DE PAGO O DOCUMENTO");
//		        row8.getCell(2).setCellStyle(style_header);
//		        
//		        row8.createCell(3).setCellValue("FECHA DE VENCIMIENTO O FECHA DE PAGO");
//		        row8.getCell(3).setCellStyle(style_header);
//		        
//		        row8.createCell(4).setCellValue("COMPROBANTE DE PAGO O DOCUMENTO");
//		        row8.getCell(4).setCellStyle(style_header);
//				
//		        row9.createCell(4).setCellValue("TIPO");
//		        row9.getCell(4).setCellStyle(style_header);
//		        
//		        row9.createCell(5).setCellValue("SERIE O CÓDIGO DE LA DEPENDENCIA ADUANERA");
//		        row9.getCell(5).setCellStyle(style_header);
//		        
//		        row9.createCell(6).setCellValue("AÑO DE EMISIÓN DE LA DUA O DSI");
//		        row9.getCell(6).setCellStyle(style_header);
//				
//		        row8.createCell(7).setCellValue("N° DEL COMPROBANTE DE PAGO, DOCUMENTO, N° DE ORDEN DEL FORMULARIO FíSICO O VIRTUAL, N° DE DUA, DSI O LIQUIDACIÓN DE COBRANZA U OTROS DOCUMENTOS EMITIDOS POR SUNAT PARA ACREDITAR EL CRÉDITO FISCAL EN LA IMPORTACIÓN");
//		        row8.getCell(7).setCellStyle(style_header);			
//		        
//		        row8.createCell(8).setCellValue("INFORMACIÓN DEL PROVEEDOR");
//		        row8.getCell(8).setCellStyle(style_header);
//		        
//		        row9.createCell(8).setCellValue("DOCUMENTO DE IDENTIDAD");
//		        row9.getCell(8).setCellStyle(style_header);
//
//		        row10.createCell(8).setCellValue("TIPO");
//		        row10.getCell(8).setCellStyle(style_header);
//		        
//		        row10.createCell(9).setCellValue("NÚMERO");
//		        row10.getCell(9).setCellStyle(style_header);
//		        
//		        row9.createCell(10).setCellValue("APELLIDOS Y NOMBRES, DENOMINACIÓN O RAZÓN SOCIAL");
//		        row9.getCell(10).setCellStyle(style_header);
//		        
//		        row8.createCell(11).setCellValue("ADQUISICIONES GRABADAS DESTINADAS A OPERACIONES GRABADAS Y/O DE EXPORTACIÓN");
//		        row8.getCell(11).setCellStyle(style_header);
//		        
//		        row9.createCell(11).setCellValue("BASE IMPONIBLE");
//		        row9.getCell(11).setCellStyle(style_header);
//		        
//		        row9.createCell(12).setCellValue("IGV");
//		        row9.getCell(12).setCellStyle(style_header);
//
//		        row8.createCell(13).setCellValue("ADQUISICIONES GRABADAS DESTINADAS A OPERACIONES GRABADAS Y/O DE EXPORTACIÓN Y/A OPERACIONES NO GRABADAS");
//		        row8.getCell(13).setCellStyle(style_header);
//		        
//		        row9.createCell(13).setCellValue("BASE IMPONIBLE");
//		        row9.getCell(13).setCellStyle(style_header);
//		        
//		        row9.createCell(14).setCellValue("IGV");
//		        row9.getCell(14).setCellStyle(style_header);
//
//		        row8.createCell(15).setCellValue("ADQUISICIONES GRABADAS DESTINADAS A OPERACIONES NO GRABADAS");
//		        row8.getCell(15).setCellStyle(style_header);
//		        
//		        row9.createCell(15).setCellValue("BASE IMPONIBLE");
//		        row9.getCell(15).setCellStyle(style_header);
//		        
//		        row9.createCell(16).setCellValue("IGV");
//		        row9.getCell(16).setCellStyle(style_header);
//		        
//		        row8.createCell(17).setCellValue("VALOR DE LAS ADQUISICIONES NO GRABADAS");
//		        row8.getCell(17).setCellStyle(style_header);
//		        		        
//		        row8.createCell(18).setCellValue("ISC");
//		        row8.getCell(18).setCellStyle(style_header);
//		        
//		        row8.createCell(19).setCellValue("OTROS TRIBUTOS Y CARGOS");
//		        row8.getCell(19).setCellStyle(style_header);
//		        
//		        row8.createCell(20).setCellValue("IMPORTE TOTAL");
//		        row8.getCell(20).setCellStyle(style_header);
//		        
//		        row8.createCell(21).setCellValue("N° DE COMPROBANTE DE PAGO EMITIDO POR SUJETO NO DOMICILIADO");
//		        row8.getCell(21).setCellStyle(style_header);
//		        
//		        row8.createCell(22).setCellValue("CONSTANCIA DE DEPÓSITO DE DETRACCIÓN");
//		        row8.getCell(22).setCellStyle(style_header);
//		        
//		        row9.createCell(22).setCellValue("NÚMERO");
//		        row9.getCell(22).setCellStyle(style_header);
//		        
//		        row9.createCell(23).setCellValue("FECHA DE EMISIÓN");
//		        row9.getCell(23).setCellStyle(style_header);
//		        
//		        row8.createCell(24).setCellValue("TIPO DE CAMBIO");
//		        row8.getCell(24).setCellStyle(style_header);
//		              
//		        row8.createCell(25).setCellValue("REFERENCIA DEL COMPROBANTE DE PAGO O DOCUMENTO ORIGINAL QUE SE MODIFICA");
//		        row8.getCell(25).setCellStyle(style_header);
//		        
//		        row9.createCell(25).setCellValue("FECHA");
//		        row9.getCell(25).setCellStyle(style_header);
//
//		        row9.createCell(26).setCellValue("TIPO");
//		        row9.getCell(26).setCellStyle(style_header);
//		        
//		        row9.createCell(27).setCellValue("SERIE");
//		        row9.getCell(27).setCellStyle(style_header);
//		        
//		        row9.createCell(28).setCellValue("N° DEL COMPROBANTE DE PAGO O DOCUMENTO");
//		        row9.getCell(28).setCellStyle(style_header);		        
//				
//		        
//		        int row = 1;
//		        BigDecimal num_imp_afecto = BigDecimal.ZERO;
//		        BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//		        BigDecimal num_imp_igv = BigDecimal.ZERO;
//		        BigDecimal num_imp_isc = BigDecimal.ZERO;
//		        BigDecimal imp_det_total = BigDecimal.ZERO;
//		        BigDecimal imp_no_gra_igv = BigDecimal.ZERO;
//		        BigDecimal imp_gra_total = BigDecimal.ZERO;
//		        
//		        
//		        for (ReporteDetalleComprasBean rep_compra : lista) {
//		        	
//		        	HSSFRow rows  = sheet.createRow((short) row + 10);
//		        	
//		        	rows.createCell(1).setCellValue(row);
//			        rows.getCell(1).setCellStyle(style_cell);
//			        
//			        rows.createCell(2).setCellValue(rep_compra.getFec_comprobante());
//			        rows.getCell(2).setCellStyle(style_cell);
//			        
//			        rows.createCell(3).setCellValue(rep_compra.getFec_vto());
//			        rows.getCell(3).setCellStyle(style_cell);
//		        
//			        rows.createCell(4).setCellValue(rep_compra.getVcod_comprobante());
//			        rows.getCell(4).setCellStyle(style_cell);
//			        
//			        rows.createCell(5).setCellValue(rep_compra.getSer_comprobante());
//			        rows.getCell(5).setCellStyle(style_cell);
//			        
//			        rows.createCell(6).setCellValue("");
//			        rows.getCell(6).setCellStyle(style_cell);
//			        
//			        rows.createCell(7).setCellValue(StringUtils.stripStart(rep_compra.getNro_comprobante(), Constantes.ZERO_STRING));
//			        rows.getCell(7).setCellStyle(style_cell);
//			        
//			        if (!isNullOrEmpty(rep_compra.getTip_documento())) {
//	    				rows.createCell(8).setCellValue(rep_compra.getTip_documento());
//	    			} else {
//	    				rows.createCell(8).setCellValue(Constantes.EMPTY);
//	    			}
//			        rows.getCell(8).setCellStyle(style_cell);
//			        
//			        rows.createCell(9).setCellValue(getString(rep_compra.getRuc_nro_doc_prov()));
//			        rows.getCell(9).setCellStyle(style_cell);
//			        
//			        rows.createCell(10).setCellValue(getString(rep_compra.getRaz_soc_prov()));
//			        rows.getCell(10).setCellStyle(style_cell);
//			        
//			        
//			        BigDecimal imp_afecto = rep_compra.getNum_imp_afecto();
//			        if (rep_compra.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_afecto.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_afecto = imp_afecto.negate();
//			        }			        
//			        rows.createCell(11).setCellValue(dec_form.format(imp_afecto));
//			        rows.getCell(11).setCellStyle(style_mont);
//			        
//			        
//			        BigDecimal imp_igv = rep_compra.getNum_imp_igv();
//			        if (rep_compra.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_igv.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_igv = imp_igv.negate();
//			        }
//			        rows.createCell(12).setCellValue(dec_form.format(imp_igv));
//			        rows.getCell(12).setCellStyle(style_mont);
//			        
//			        rows.createCell(13).setCellValue(dec_form.format(BigDecimal.ZERO));
//			        rows.getCell(13).setCellStyle(style_mont);
//			        
//			        rows.createCell(14).setCellValue(dec_form.format(BigDecimal.ZERO));
//			        rows.getCell(14).setCellStyle(style_mont);
//			        
//			        rows.createCell(15).setCellValue(dec_form.format(BigDecimal.ZERO));
//			        rows.getCell(15).setCellStyle(style_mont);
//			        
//			        rows.createCell(16).setCellValue(dec_form.format(BigDecimal.ZERO));
//			        rows.getCell(16).setCellStyle(style_mont);
//			        
//			        
//			        BigDecimal imp_no_afecto = rep_compra.getNum_imp_no_afecto();
//			        if (rep_compra.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_no_afecto.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_no_afecto = imp_no_afecto.negate();
//			        }
//			        rows.createCell(17).setCellValue(dec_form.format(imp_no_afecto));
//			        rows.getCell(17).setCellStyle(style_mont);
//			        
//			        
//			        BigDecimal imp_isc = rep_compra.getNum_imp_isc();
//			        if (rep_compra.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_isc.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_isc = imp_isc.negate();
//			        }
//			        rows.createCell(18).setCellValue(dec_form.format(imp_isc));
//			        rows.getCell(18).setCellStyle(style_mont);
//			        
//			        rows.createCell(19).setCellValue(dec_form.format(BigDecimal.ZERO));
//			        rows.getCell(19).setCellStyle(style_mont);
//			        
//			        
//			        BigDecimal imp_total = rep_compra.getImp_det_total();
//			        if (rep_compra.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_total.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_total = imp_total.negate();
//			        }
//			        rows.createCell(20).setCellValue(dec_form.format(imp_total));
//			        rows.getCell(20).setCellStyle(style_mont);
//			        
//			        rows.createCell(21).setCellValue("");
//			        rows.getCell(21).setCellStyle(style_cell);
//			        
//			        rows.createCell(22).setCellValue(getString(rep_compra.getNum_constancia()));
//			        rows.getCell(22).setCellStyle(style_cell);
//			        
//			        rows.createCell(23).setCellValue(getString(rep_compra.getFec_pag_deposito()));
//			        rows.getCell(23).setCellStyle(style_cell);
//			        
//			        rows.createCell(24).setCellValue(getString(rep_compra.getNum_tip_cambio()));
//			        rows.getCell(24).setCellStyle(style_cell);
//			 
//			        rows.createCell(25).setCellValue(getString(rep_compra.getFec_com_not_cre()));
//			        rows.getCell(25).setCellStyle(style_cell);
//			        
//			        rows.createCell(26).setCellValue(getString(rep_compra.getTip_com_not_cre()));
//			        rows.getCell(26).setCellStyle(style_cell);
//			        
//			        rows.createCell(27).setCellValue(getString(rep_compra.getSer_com_not_cre()));
//			        rows.getCell(27).setCellStyle(style_cell);
//			        
//			        rows.createCell(28).setCellValue(StringUtils.stripStart(getString(rep_compra.getNro_com_not_cre()), Constantes.ZERO_STRING));
//			        rows.getCell(28).setCellStyle(style_cell);			        
//			        
//			        num_imp_afecto = num_imp_afecto.add(imp_afecto);
//		            num_imp_no_afecto = num_imp_no_afecto.add(imp_no_afecto);
//		            num_imp_igv = num_imp_igv.add(imp_igv);
//		            num_imp_isc = num_imp_isc.add(imp_isc);
//		            imp_det_total = imp_det_total.add(imp_total);		            
//		            imp_no_gra_igv = imp_no_gra_igv.add(BigDecimal.valueOf(rep_compra.getImp_no_gra_igv()));
//		            imp_gra_total = imp_gra_total.add(BigDecimal.valueOf(rep_compra.getImp_gra_total()));
//		            
//		            row++;
//		        }
//		        
//		        HSSFCellStyle style_cell_total = (HSSFCellStyle) wb.createCellStyle();
//		        style_cell_total.setFont(font_bold);	        
//		        style_cell_total.setBorderBottom((short) 1);
//		        style_cell_total.setBorderLeft((short) 1);	        
//		        style_cell_total.setBorderRight((short) 1);
//		        style_cell_total.setBorderTop((short) 1);
//		        
//		        HSSFCellStyle style_tot_mont = (HSSFCellStyle) wb.createCellStyle();
//		        style_tot_mont.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//		        style_tot_mont.setFont(font_bold);	        
//		        style_tot_mont.setBorderBottom((short) 1);
//		        style_tot_mont.setBorderLeft((short) 1);	        
//		        style_tot_mont.setBorderRight((short) 1);
//		        style_tot_mont.setBorderTop((short) 1);
//		        
//		        HSSFRow row_total  = sheet.createRow((short) row + 10);
//		        
//		        HSSFRichTextString total = new HSSFRichTextString("TOTALES:");
//		        total.applyFont(font_bold);
//		        row_total.createCell(10).setCellValue(total);
//		        
//		        row_total.createCell(11).setCellValue(dec_form.format(num_imp_afecto));
//		        row_total.getCell(11).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(12).setCellValue(dec_form.format(num_imp_igv));
//		        row_total.getCell(12).setCellStyle(style_tot_mont);
//	
//		        row_total.createCell(13).setCellValue(dec_form.format(BigDecimal.ZERO));
//		        row_total.getCell(13).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(14).setCellValue(dec_form.format(BigDecimal.ZERO));
//		        row_total.getCell(14).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(15).setCellValue(dec_form.format(BigDecimal.ZERO));
//		        row_total.getCell(15).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(16).setCellValue(dec_form.format(BigDecimal.ZERO));
//		        row_total.getCell(16).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(17).setCellValue(dec_form.format(num_imp_no_afecto));
//		        row_total.getCell(17).setCellStyle(style_tot_mont);		        
//		        
//		        row_total.createCell(18).setCellValue(dec_form.format(num_imp_isc));
//		        row_total.getCell(18).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(19).setCellValue(dec_form.format(BigDecimal.ZERO));
//		        row_total.getCell(19).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(20).setCellValue(dec_form.format(imp_det_total));
//		        row_total.getCell(20).setCellStyle(style_tot_mont);		        
//		        
//			} else if (compra.getCod_servicio().equals(Constantes.FOUR_INT)) { // Registro de Compras
//	        	
//	        	row8.createCell(1).setCellValue("N° Documento");
//		        row8.getCell(1).setCellStyle(style_header);
//		        
//		        row8.createCell(2).setCellValue("Fecha");
//		        row8.getCell(2).setCellStyle(style_header);
//		        
//		        row8.createCell(3).setCellValue("Doc");
//		        row8.getCell(3).setCellStyle(style_header);
//		        
//		        row8.createCell(4).setCellValue("Num");
//		        row8.getCell(4).setCellStyle(style_header);
//		        
//		        row8.createCell(5).setCellValue("Nombre");
//		        row8.getCell(5).setCellStyle(style_header);
//	        	
//		        row8.createCell(6).setCellValue("Afecto");
//		        row8.getCell(6).setCellStyle(style_header);
//	
//		        row8.createCell(7).setCellValue("No Afecto");
//		        row8.getCell(7).setCellStyle(style_header);
//	
//		        row8.createCell(8).setCellValue("IGV");
//		        row8.getCell(8).setCellStyle(style_header);
//	
//		        row8.createCell(9).setCellValue("ISC");
//		        row8.getCell(9).setCellStyle(style_header);
//	
//		        row8.createCell(10).setCellValue("Total (S/.)");
//		        row8.getCell(10).setCellStyle(style_header);
//	        	
//		        row8.createCell(11).setCellValue("Total (US$)");
//		        row8.getCell(11).setCellStyle(style_header);
//		        
//		        
//		        String unidad = "";
//		        String documento = "";
//		        if (!isEmpty(lista)) {
//		        	unidad = lista.get(0).getDes_uni_operativa();
//		        	documento = lista.get(0).getTip_comprobante();
//		        }
//		        
//		        String tip_operacion = compra.getTip_operacion();
//		        Integer cod_not_credito = Integer.parseInt(ReadParameterProperties.getPropiedad("codigo.comprobante.nota.credito"));
//		        
//		        List<ReporteDetalleComprasBean> lis_uni_ope = null;
//		        List<ReporteDetalleComprasBean> lis_sede = null;		        
//
//		        if (tip_operacion.equals(Constantes.TWO_STRING)) { // Sede Central
//		        	lis_uni_ope = new ArrayList<ReporteDetalleComprasBean>();
//		        	lis_sede = new ArrayList<ReporteDetalleComprasBean>();
//		        	for (ReporteDetalleComprasBean rep : lista) {
//		        		if (rep.getTip_operacion().equals(Constantes.TWO_STRING)) { // Sede Central
//		        			lis_sede.add(rep);
//		        		} else {
//		        			lis_uni_ope.add(rep);
//		        		}			        		
//		        	}
//		        	lista.clear();
//		        	lista.addAll(lis_sede);
//
//		        	row9.createCell(1).setCellValue("SERNANP - SEDE CENTRAL");
//		        	row9.getCell(1).setCellStyle(style_hed);
//	    	      	    	        
//		        } else { // Unidad Operativa
//		        
//		        	row9.createCell(1).setCellValue(unidad);
//		        	row9.getCell(1).setCellStyle(style_hed);
//		        	
//		        }
//		        
//		        String anp = "ANP: ";
//		        int row = 10;
//		        boolean pri = true;
//		        boolean sub = false;
//		        int can = 0;
//		        int ult = 0;
//		        if (!isEmpty(lista)) {
//		        	ult = lista.size() - 1;
//		        }
//		        
//		        HSSFRow rows = null;
//		        BigDecimal num_imp_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_igv = BigDecimal.ZERO;
//	            BigDecimal num_imp_isc = BigDecimal.ZERO;
//	            BigDecimal imp_det_total = BigDecimal.ZERO;
//	            BigDecimal imp_det_dol_total = BigDecimal.ZERO;
//	            
//	            BigDecimal num_imp_tot_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_no_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_igv = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_isc = BigDecimal.ZERO;
//	            BigDecimal imp_total = BigDecimal.ZERO;
//	            BigDecimal imp_dol_total = BigDecimal.ZERO;
//	            
//	            
//	            if (tip_operacion.equals(Constantes.TWO_STRING)) { // Sede Central
//	            	
//	            	BigDecimal sed_num_imp_tot_afecto = BigDecimal.ZERO;
//		            BigDecimal sed_num_imp_tot_no_afecto = BigDecimal.ZERO;
//		            BigDecimal sed_num_imp_tot_igv = BigDecimal.ZERO;
//		            BigDecimal sed_num_imp_tot_isc = BigDecimal.ZERO;
//		            BigDecimal sed_imp_total = BigDecimal.ZERO;
//		            BigDecimal sed_imp_dol_total = BigDecimal.ZERO;
//	            	
//	            	// Primera Bloque Sede Central
//	            	for (ReporteDetalleComprasBean rep_compra : lista) {
// 		            	
//		            	if (!documento.equals(rep_compra.getTip_comprobante()) && !pri) {
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Sub-Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//				    	    
//		            		sub = true;	            		
//		            	} else if (pri) {
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(1).setCellValue("Documento: ".concat(documento.toUpperCase()));
//				    	    rows.getCell(1).setCellStyle(style_hed);	            		
//		            		row++;
//		            		pri = false;
//		            	}
//		            	
//		            	if (!sub) {
//		            		
//		            		rows = sheet.createRow((short) row);
//		            		
//		            	} else {
//		            		
//		            		rows.createCell(6).setCellValue(dec_form.format(num_imp_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_det_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_det_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);
//				    	    	            		
//				    	    
//				    	    num_imp_afecto = BigDecimal.ZERO;
//				            num_imp_no_afecto = BigDecimal.ZERO;
//				            num_imp_igv = BigDecimal.ZERO;
//				            num_imp_isc = BigDecimal.ZERO;
//				            imp_det_total = BigDecimal.ZERO;
//				            imp_det_dol_total = BigDecimal.ZERO;
//				    	    
//		            		sub = false;
//		            		
//				        	row = row + 2;
//				        		
//		            		rows = sheet.createRow((short) row);
//		            		documento = rep_compra.getTip_comprobante();
//		            		rows.createCell(1).setCellValue("Documento: ".concat(documento.toUpperCase()));
//				    	    rows.getCell(1).setCellStyle(style_hed);
//
//				    	    row = row + 1;
//		            		
//		            		rows = sheet.createRow((short) row);
//		            	}
//		            		
//
//		            	String ser_nro = rep_compra.getSer_comprobante().concat(" - ");
//			    	    ser_nro = ser_nro.concat(rep_compra.getNro_comprobante());
//			    	    rows.createCell(1).setCellValue(ser_nro);
//			    	    rows.getCell(1).setCellStyle(style_cell);
//			        
//			    	    rows.createCell(2).setCellValue(rep_compra.getFec_comprobante());
//			    	    rows.getCell(2).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(3).setCellValue(rep_compra.getTip_documento());
//			    	    rows.getCell(3).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(4).setCellValue(getString(rep_compra.getRuc_nro_doc_prov()));
//			    	    rows.getCell(4).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(5).setCellValue(getString(rep_compra.getRaz_soc_prov()));
//			    	    rows.getCell(5).setCellStyle(style_cell);
//			    	    
//			    	    
//			    	    BigDecimal imp_afecto = rep_compra.getNum_imp_afecto();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_afecto.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_afecto = imp_afecto.negate();
//		            	}
//			    	     
//			    	    rows.createCell(6).setCellValue(dec_form.format(imp_afecto));
//			    	    rows.getCell(6).setCellStyle(style_mont);
//			    	    
//			    	    BigDecimal imp_no_afecto = rep_compra.getNum_imp_no_afecto();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_no_afecto.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_no_afecto = imp_no_afecto.negate();
//		            	}
//			    	     
//			    	    rows.createCell(7).setCellValue(dec_form.format(imp_no_afecto));
//			    	    rows.getCell(7).setCellStyle(style_mont);
//			    	    
//			    	    BigDecimal imp_igv = rep_compra.getNum_imp_igv();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_igv.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_igv = imp_igv.negate();
//		            	}
//			    	     
//			    	    rows.createCell(8).setCellValue(dec_form.format(imp_igv));
//			    	    rows.getCell(8).setCellStyle(style_mont);
//			    	    
//			    	    BigDecimal imp_isc = rep_compra.getNum_imp_isc();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_isc.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_isc = imp_isc.negate();
//		            	}
//			    	     
//			    	    rows.createCell(9).setCellValue(dec_form.format(imp_isc));
//			    	    rows.getCell(9).setCellStyle(style_mont);
//			    	    
//			    	    if (rep_compra.getCod_moneda().equals(Constantes.ONE_INT)) { // Soles
//			    	    	
//			    	    	BigDecimal det_total = rep_compra.getImp_det_total();
//			            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//			            			det_total.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//			            		det_total = det_total.negate();
//			            	}
//			    	    	
//			    	    	rows.createCell(10).setCellValue(dec_form.format(det_total));
//				    	    rows.getCell(10).setCellStyle(style_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(BigDecimal.ZERO));
//				    	    rows.getCell(11).setCellStyle(style_mont);
//				    	    
//				    	    imp_det_total = imp_det_total.add(det_total);
//				    	    imp_total = imp_total.add(det_total);
//				    	    
//			    	    } else { // Dolares
//			    	    	
//			    	    	rows.createCell(10).setCellValue(dec_form.format(BigDecimal.ZERO));
//				    	    rows.getCell(10).setCellStyle(style_mont);
//				    	    
//				    	    BigDecimal det_total = rep_compra.getImp_det_total();
//			            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//			            			det_total.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//			            		det_total = det_total.negate();
//			            	}
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(det_total));
//				    	    rows.getCell(11).setCellStyle(style_mont);
//				    	    
//				    	    imp_det_dol_total = imp_det_dol_total.add(det_total);
//				    	    imp_dol_total = imp_dol_total.add(det_total);
//			    	    }
//			    	    		    	  
//			    	    
//			    	    num_imp_afecto = num_imp_afecto.add(imp_afecto);
//		                num_imp_no_afecto = num_imp_no_afecto.add(imp_no_afecto);
//		                num_imp_igv = num_imp_igv.add(imp_igv);
//		                num_imp_isc = num_imp_isc.add(imp_isc);	                
//		                
//
//	                	num_imp_tot_afecto = num_imp_tot_afecto.add(imp_afecto);
//		                num_imp_tot_no_afecto = num_imp_tot_no_afecto.add(imp_no_afecto);
//		                num_imp_tot_igv = num_imp_tot_igv.add(imp_igv);
//		                num_imp_tot_isc = num_imp_tot_isc.add(imp_isc);
//		                
//		                		    	    
//			    	    row++;
//			    	    
//			    	    if (can == ult) { // Validacion ultimo registro
//			    	    	rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Sub-Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//			    	    	
//				    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_det_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_det_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);
//				    	   
//				    	    
//				    	    row = row + 1;
//				    	 
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//			    	    	
//				    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_tot_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_tot_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_tot_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_tot_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);			    	   			    	 
//				    	    
//			    	    }		    	    
//			    	    
//			    	    can++;
//			        }
//	            	
//	            	
//	            	
//	            	// Segundo bloque de Unidad Operativa
//	            	
//	            	unidad = "";
//			        documento = "";
//			        String are_nat_pro = "";
//			        if (!isEmpty(lis_uni_ope)) {
//			        	unidad = lis_uni_ope.get(0).getDes_uni_operativa();
//			        	are_nat_pro = lis_uni_ope.get(0).getDes_reg_nac().toUpperCase();
//			        	documento = lis_uni_ope.get(0).getTip_comprobante();
//			        }
//
//			        row = row + 3;
//
//			        rows = sheet.createRow((short) row);
//			        rows.createCell(1).setCellValue(unidad);
//			        rows.getCell(1).setCellStyle(style_hed);
//			        
//			        row = row + 1;
//			        
//			        rows = sheet.createRow((short) row);
//			        rows.createCell(1).setCellValue(anp.concat(are_nat_pro));
//			        rows.getCell(1).setCellStyle(style_hed);
//			        
//			        
//			        row = row + 1;
//			        
//			        
//			        pri = true;
//			        sub = false;
//			        can = 0;
//			        ult = lis_uni_ope.size() - 1;
//			        rows = null;
//			        num_imp_afecto = BigDecimal.ZERO;
//		            num_imp_no_afecto = BigDecimal.ZERO;
//		            num_imp_igv = BigDecimal.ZERO;
//		            num_imp_isc = BigDecimal.ZERO;
//		            imp_det_total = BigDecimal.ZERO;
//		            imp_det_dol_total = BigDecimal.ZERO;
//		            
//		            // Sede Monto Total
//		            sed_num_imp_tot_afecto = num_imp_tot_afecto;
//		            sed_num_imp_tot_no_afecto = num_imp_tot_no_afecto;
//		            sed_num_imp_tot_igv = num_imp_tot_igv;
//		            sed_num_imp_tot_isc = num_imp_tot_isc;
//		            sed_imp_total = imp_total;
//		            sed_imp_dol_total = imp_dol_total;		            
//		            
//		            num_imp_tot_afecto = BigDecimal.ZERO;
//		            num_imp_tot_no_afecto = BigDecimal.ZERO;
//		            num_imp_tot_igv = BigDecimal.ZERO;
//		            num_imp_tot_isc = BigDecimal.ZERO;
//		            imp_total = BigDecimal.ZERO;
//		            imp_dol_total = BigDecimal.ZERO;
//		            
//		            BigDecimal imp_tot_gen_afecto = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_no_afecto = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_igv = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_isc = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_soles = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_dolares = BigDecimal.ZERO;
//		        	
//		        	for (ReporteDetalleComprasBean rep_compra : lis_uni_ope) {
//		        		
//		            	if ((!documento.equals(rep_compra.getTip_comprobante()) && !pri) || 
//		            			!are_nat_pro.equals(rep_compra.getDes_reg_nac().toUpperCase())) {
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Sub-Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//				    	    
//		            		sub = true;	            		
//		            	} else if (pri) {
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(1).setCellValue("Documento: ".concat(documento.toUpperCase()));
//				    	    rows.getCell(1).setCellStyle(style_hed);	            		
//		            		row++;
//		            		pri = false;
//		            	}
//		            	
//		            	if (!sub) {	            	
//		            		rows = sheet.createRow((short) row);
//		            	} else {
//		            		
//		            		rows.createCell(6).setCellValue(dec_form.format(num_imp_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_det_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_det_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);
//				    	    	            		
//				    	    
//				    	    num_imp_afecto = BigDecimal.ZERO;
//				            num_imp_no_afecto = BigDecimal.ZERO;
//				            num_imp_igv = BigDecimal.ZERO;
//				            num_imp_isc = BigDecimal.ZERO;
//				            imp_det_total = BigDecimal.ZERO;
//				            imp_det_dol_total = BigDecimal.ZERO;
//				    	    
//		            		sub = false;
//		            		
//		            		
//			        		if (!are_nat_pro.equals(rep_compra.getDes_reg_nac().toUpperCase())) {
//			        			
//			        			
//			        			row = row + 1;
//			        			
//			        			rows = sheet.createRow((short) row);
//			            		rows.createCell(5).setCellValue("Total: ");
//					    	    rows.getCell(5).setCellStyle(style_total); 
//				    	    	
//					    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_tot_afecto));
//					    	    rows.getCell(6).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_tot_no_afecto));
//					    	    rows.getCell(7).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_tot_igv));
//					    	    rows.getCell(8).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_tot_isc));
//					    	    rows.getCell(9).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(10).setCellValue(dec_form.format(imp_total));
//					    	    rows.getCell(10).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(11).setCellValue(dec_form.format(imp_dol_total));
//					    	    rows.getCell(11).setCellStyle(style_sub_mont);
//					    	   
//		                		
//					    	    num_imp_tot_afecto = BigDecimal.ZERO;
//					            num_imp_tot_no_afecto = BigDecimal.ZERO;
//					            num_imp_tot_igv = BigDecimal.ZERO;
//					            num_imp_tot_isc = BigDecimal.ZERO;
//					            imp_total = BigDecimal.ZERO;
//					            imp_dol_total = BigDecimal.ZERO;
//					            
//					            
//					            row = row + 2;
//					            					            					            
//					            
//					            if (!unidad.equals(rep_compra.getDes_uni_operativa())) {
//			                		
//			                		unidad = rep_compra.getDes_uni_operativa();
//			                		
//			                		rows = sheet.createRow((short) row);
//			    			        rows.createCell(1).setCellValue(unidad);
//			    			        rows.getCell(1).setCellStyle(style_hed);
//			    			        
//			    			        row = row + 1;
//			                		
//					            }
//					            
//			        			
//			        			are_nat_pro = rep_compra.getDes_reg_nac().toUpperCase();
//			        			
//			        			rows = sheet.createRow((short) row);
//			            		rows.createCell(1).setCellValue(anp.concat(are_nat_pro));
//					    	    rows.getCell(1).setCellStyle(style_hed);
//
//					    	    row = row + 1;
//					    	    
//			        		} else {
//			        			
//			        			row = row + 2;
//			        			
//			        		}
//
//		            		
//		            		rows = sheet.createRow((short) row);
//		            		documento = rep_compra.getTip_comprobante();
//		            		rows.createCell(1).setCellValue("Documento: ".concat(documento.toUpperCase()));
//				    	    rows.getCell(1).setCellStyle(style_hed);
//
//				    	    row = row + 1;
//		            		
//		            		rows = sheet.createRow((short) row);
//		            	}
//		            		
//		            	
//		            	String ser_nro = rep_compra.getSer_comprobante().concat(" - ");
//			    	    ser_nro = ser_nro.concat(rep_compra.getNro_comprobante());
//			    	    rows.createCell(1).setCellValue(ser_nro);
//			    	    rows.getCell(1).setCellStyle(style_cell);
//			        
//			    	    rows.createCell(2).setCellValue(rep_compra.getFec_comprobante());
//			    	    rows.getCell(2).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(3).setCellValue(rep_compra.getTip_documento());
//			    	    rows.getCell(3).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(4).setCellValue(getString(rep_compra.getRuc_nro_doc_prov()));
//			    	    rows.getCell(4).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(5).setCellValue(getString(rep_compra.getRaz_soc_prov()));
//			    	    rows.getCell(5).setCellStyle(style_cell);
//			    	    
//			    	    BigDecimal imp_afecto = rep_compra.getNum_imp_afecto();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_afecto.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_afecto = imp_afecto.negate();
//		            	}
//			    	     
//			    	    rows.createCell(6).setCellValue(dec_form.format(imp_afecto));
//			    	    rows.getCell(6).setCellStyle(style_mont);
//			    	    
//			    	    BigDecimal imp_no_afecto = rep_compra.getNum_imp_no_afecto();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_no_afecto.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_no_afecto = imp_no_afecto.negate();
//		            	}
//			    	     
//			    	    rows.createCell(7).setCellValue(dec_form.format(imp_no_afecto));
//			    	    rows.getCell(7).setCellStyle(style_mont);
//			    	    
//			    	    BigDecimal imp_igv = rep_compra.getNum_imp_igv();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_igv.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_igv = imp_igv.negate();
//		            	}
//			    	     
//			    	    rows.createCell(8).setCellValue(dec_form.format(imp_igv));
//			    	    rows.getCell(8).setCellStyle(style_mont);
//			    	    
//			    	    BigDecimal imp_isc = rep_compra.getNum_imp_isc();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_isc.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_isc = imp_isc.negate();
//		            	}
//			    	     
//			    	    rows.createCell(9).setCellValue(dec_form.format(imp_isc));
//			    	    rows.getCell(9).setCellStyle(style_mont);
//			    	    
//			    	    if (rep_compra.getCod_moneda().equals(Constantes.ONE_INT)) { // Soles
//			    	    	
//			    	    	BigDecimal det_total = rep_compra.getImp_det_total();
//			            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//			            			det_total.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//			            		det_total = det_total.negate();
//			            	}
//			    	    	
//			    	    	rows.createCell(10).setCellValue(dec_form.format(det_total));
//				    	    rows.getCell(10).setCellStyle(style_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(BigDecimal.ZERO));
//				    	    rows.getCell(11).setCellStyle(style_mont);
//				    	    
//				    	    imp_det_total = imp_det_total.add(det_total);
//				    	    imp_total = imp_total.add(det_total);
//				    	    imp_tot_gen_soles = imp_tot_gen_soles.add(det_total);
//				    	    
//			    	    } else { // Dolares
//			    	    	
//			    	    	rows.createCell(10).setCellValue(dec_form.format(BigDecimal.ZERO));
//				    	    rows.getCell(10).setCellStyle(style_mont);
//				    	    
//				    	    BigDecimal det_total = rep_compra.getImp_det_total();
//			            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//			            			det_total.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//			            		det_total = det_total.negate();
//			            	}
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(det_total));
//				    	    rows.getCell(11).setCellStyle(style_mont);
//				    	    
//				    	    imp_det_dol_total = imp_det_dol_total.add(det_total);
//				    	    imp_dol_total = imp_dol_total.add(det_total);
//				    	    imp_tot_gen_dolares = imp_tot_gen_dolares.add(det_total);
//				    	    
//			    	    }
//			    	    		    	  
//			    	    
//			    	    num_imp_afecto = num_imp_afecto.add(imp_afecto);
//		                num_imp_no_afecto = num_imp_no_afecto.add(imp_no_afecto);
//		                num_imp_igv = num_imp_igv.add(imp_igv);
//		                num_imp_isc = num_imp_isc.add(imp_isc);	                
//		                
//
//	                	num_imp_tot_afecto = num_imp_tot_afecto.add(imp_afecto);
//		                num_imp_tot_no_afecto = num_imp_tot_no_afecto.add(imp_no_afecto);
//		                num_imp_tot_igv = num_imp_tot_igv.add(imp_igv);
//		                num_imp_tot_isc = num_imp_tot_isc.add(imp_isc);
//		                		                
//		                
//		                imp_tot_gen_afecto = imp_tot_gen_afecto.add(imp_afecto);
//		                imp_tot_gen_no_afecto = imp_tot_gen_no_afecto.add(imp_no_afecto);
//		                imp_tot_gen_igv = imp_tot_gen_igv.add(imp_igv);
//		                imp_tot_gen_isc = imp_tot_gen_isc.add(imp_isc);
//		                
//	    	    
//			    	    row++;
//			    	    
//			    	    if (can == ult) { // Validacion ultimo registro
//			    	    	rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Sub-Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//			    	    	
//				    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_det_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_det_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);
//				    	   
//				    	    
//				    	    row = row + 1;
//				    	 
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//			    	    	
//				    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_tot_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_tot_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_tot_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_tot_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);	   
//				    	    
//				    	    
//				    	    row = row + 1;
//					    	 
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Total General: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//				    	    
//				    	    
//				    	    rows.createCell(6).setCellValue(dec_form.format(imp_tot_gen_afecto.add(sed_num_imp_tot_afecto)));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(imp_tot_gen_no_afecto.add(sed_num_imp_tot_no_afecto)));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(imp_tot_gen_igv.add(sed_num_imp_tot_igv)));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(imp_tot_gen_isc.add(sed_num_imp_tot_isc)));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_tot_gen_soles.add(sed_imp_total)));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_tot_gen_dolares.add(sed_imp_dol_total)));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);	
//				    	    
//			    	    }		    	    
//			    	    
//			    	    can++;
//			        }
//	            	
//	            	
//	            } else { // Unidad Operativa
//	            	
//	            	BigDecimal imp_tot_gen_afecto = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_no_afecto = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_igv = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_isc = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_soles = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_dolares = BigDecimal.ZERO;
//	            	
//	            	
//	            	String are_nat_pro = "";
//			        if (!isEmpty(lista)) {
//			        	are_nat_pro = lista.get(0).getDes_reg_nac().toUpperCase();
//			        }
//	            	
//	            	rows = sheet.createRow((short) row);
//			        rows.createCell(1).setCellValue(anp.concat(are_nat_pro));
//			        rows.getCell(1).setCellStyle(style_hed);
//			        
//			        
//			        row = row + 1;
//	            	
//
//		            for (ReporteDetalleComprasBean rep_compra : lista) {
//			        	         		            	
//		            	if (!documento.equals(rep_compra.getTip_comprobante()) && !pri) {
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Sub-Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//				    	    
//		            		sub = true;	            		
//		            	} else if (pri) {
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(1).setCellValue("Documento: ".concat(documento.toUpperCase()));
//				    	    rows.getCell(1).setCellStyle(style_hed);	            		
//		            		row++;
//		            		pri = false;
//		            	}
//		            	
//		            	if (!sub) {	            	
//		            		rows = sheet.createRow((short) row);
//		            	} else {
//		            		
//		            		rows.createCell(6).setCellValue(dec_form.format(num_imp_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_det_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_det_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);
//				    	    	            		
//				    	    
//				    	    num_imp_afecto = BigDecimal.ZERO;
//				            num_imp_no_afecto = BigDecimal.ZERO;
//				            num_imp_igv = BigDecimal.ZERO;
//				            num_imp_isc = BigDecimal.ZERO;
//				            imp_det_total = BigDecimal.ZERO;
//				            imp_det_dol_total = BigDecimal.ZERO;
//				    	    
//		            		sub = false;
//		            		
//			        		
//			        		if (!are_nat_pro.equals(rep_compra.getDes_reg_nac().toUpperCase())) {
//			        			
//			        			
//			        			row = row + 1;
//			        			
//			        			rows = sheet.createRow((short) row);
//			            		rows.createCell(5).setCellValue("Total: ");
//					    	    rows.getCell(5).setCellStyle(style_total); 
//				    	    	
//					    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_tot_afecto));
//					    	    rows.getCell(6).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_tot_no_afecto));
//					    	    rows.getCell(7).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_tot_igv));
//					    	    rows.getCell(8).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_tot_isc));
//					    	    rows.getCell(9).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(10).setCellValue(dec_form.format(imp_total));
//					    	    rows.getCell(10).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(11).setCellValue(dec_form.format(imp_dol_total));
//					    	    rows.getCell(11).setCellStyle(style_sub_mont);
//					    	   
//		                		
//					    	    num_imp_tot_afecto = BigDecimal.ZERO;
//					            num_imp_tot_no_afecto = BigDecimal.ZERO;
//					            num_imp_tot_igv = BigDecimal.ZERO;
//					            num_imp_tot_isc = BigDecimal.ZERO;
//					            imp_total = BigDecimal.ZERO;
//					            imp_dol_total = BigDecimal.ZERO;
//					            
//					            
//					            row = row + 2;
//					            					            					            
//					            
//					            if (!unidad.equals(rep_compra.getDes_uni_operativa())) {
//			                		
//			                		unidad = rep_compra.getDes_uni_operativa();
//			                		
//			                		rows = sheet.createRow((short) row);
//			    			        rows.createCell(1).setCellValue(unidad);
//			    			        rows.getCell(1).setCellStyle(style_hed);
//			    			        
//			    			        row = row + 1;
//			                		
//					            }
//					            
//			        			
//			        			are_nat_pro = rep_compra.getDes_reg_nac().toUpperCase();
//			        			
//			        			rows = sheet.createRow((short) row);
//			            		rows.createCell(1).setCellValue(anp.concat(are_nat_pro));
//					    	    rows.getCell(1).setCellStyle(style_hed);
//
//					    	    row = row + 1;
//					    	    
//			        		} else {
//			        			
//			        			row = row + 2;
//			        			
//			        		}
//			        		
//			        		
//		            		rows = sheet.createRow((short) row);
//		            		documento = rep_compra.getTip_comprobante();
//		            		rows.createCell(1).setCellValue("Documento: ".concat(documento.toUpperCase()));
//				    	    rows.getCell(1).setCellStyle(style_hed);
//	
//				    	    row = row + 1;
//		            		
//		            		rows = sheet.createRow((short) row);
//		            	}
//		            		
//		            	
//		            	String ser_nro = rep_compra.getSer_comprobante().concat(" - ");
//			    	    ser_nro = ser_nro.concat(rep_compra.getNro_comprobante());
//			    	    rows.createCell(1).setCellValue(ser_nro);
//			    	    rows.getCell(1).setCellStyle(style_cell);
//			        
//			    	    rows.createCell(2).setCellValue(rep_compra.getFec_comprobante());
//			    	    rows.getCell(2).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(3).setCellValue(rep_compra.getTip_documento());
//			    	    rows.getCell(3).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(4).setCellValue(getString(rep_compra.getRuc_nro_doc_prov()));
//			    	    rows.getCell(4).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(5).setCellValue(getString(rep_compra.getRaz_soc_prov()));
//			    	    rows.getCell(5).setCellStyle(style_cell);
//			    	    
//			    	    BigDecimal imp_afecto = rep_compra.getNum_imp_afecto();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_afecto.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_afecto = imp_afecto.negate();
//		            	}
//			    	     
//			    	    rows.createCell(6).setCellValue(dec_form.format(imp_afecto));
//			    	    rows.getCell(6).setCellStyle(style_mont);
//			    	    
//			    	    BigDecimal imp_no_afecto = rep_compra.getNum_imp_no_afecto();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_no_afecto.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_no_afecto = imp_no_afecto.negate();
//		            	}
//			    	     
//			    	    rows.createCell(7).setCellValue(dec_form.format(imp_no_afecto));
//			    	    rows.getCell(7).setCellStyle(style_mont);
//			    	    
//			    	    BigDecimal imp_igv = rep_compra.getNum_imp_igv();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_igv.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_igv = imp_igv.negate();
//		            	}
//			    	     
//			    	    rows.createCell(8).setCellValue(dec_form.format(imp_igv));
//			    	    rows.getCell(8).setCellStyle(style_mont);
//			    	    
//			    	    BigDecimal imp_isc = rep_compra.getNum_imp_isc();
//		            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//		            			imp_isc.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//		            		imp_isc = imp_isc.negate();
//		            	}
//			    	     
//			    	    rows.createCell(9).setCellValue(dec_form.format(imp_isc));
//			    	    rows.getCell(9).setCellStyle(style_mont);
//			    	    
//			    	    if (rep_compra.getCod_moneda().equals(Constantes.ONE_INT)) { // Soles
//			    	    	
//			    	    	BigDecimal det_total = rep_compra.getImp_det_total();
//			            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//			            			det_total.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//			            		det_total = det_total.negate();
//			            	}
//			    	    	
//			    	    	rows.createCell(10).setCellValue(dec_form.format(det_total));
//				    	    rows.getCell(10).setCellStyle(style_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(BigDecimal.ZERO));
//				    	    rows.getCell(11).setCellStyle(style_mont);
//				    	    
//				    	    imp_det_total = imp_det_total.add(det_total);
//				    	    imp_total = imp_total.add(det_total);
//				    	    
//			    	    } else { // Dolares
//			    	    	
//			    	    	rows.createCell(10).setCellValue(dec_form.format(BigDecimal.ZERO));
//				    	    rows.getCell(10).setCellStyle(style_mont);
//				    	    
//				    	    BigDecimal det_total = rep_compra.getImp_det_total();
//			            	if (cod_not_credito.equals(rep_compra.getCod_comprobante()) && // Nota de Credito
//			            			det_total.compareTo(BigDecimal.ZERO) == 1) { // Mayor a cero
//			            		det_total = det_total.negate();
//			            	}
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(det_total));
//				    	    rows.getCell(11).setCellStyle(style_mont);
//				    	    
//				    	    imp_det_dol_total = imp_det_dol_total.add(det_total);
//				    	    imp_dol_total = imp_dol_total.add(det_total);
//				    	    
//			    	    }
//			    	    		    	  
//			    	    
//			    	    num_imp_afecto = num_imp_afecto.add(imp_afecto);
//		                num_imp_no_afecto = num_imp_no_afecto.add(imp_no_afecto);
//		                num_imp_igv = num_imp_igv.add(imp_igv);
//		                num_imp_isc = num_imp_isc.add(imp_isc);	                
//		                
//	
//	                	num_imp_tot_afecto = num_imp_tot_afecto.add(imp_afecto);
//		                num_imp_tot_no_afecto = num_imp_tot_no_afecto.add(imp_no_afecto);
//		                num_imp_tot_igv = num_imp_tot_igv.add(imp_igv);
//		                num_imp_tot_isc = num_imp_tot_isc.add(imp_isc);
//		                
//		                
//		                imp_tot_gen_afecto = imp_tot_gen_afecto.add(imp_afecto);
//		                imp_tot_gen_no_afecto = imp_tot_gen_no_afecto.add(imp_no_afecto);
//		                imp_tot_gen_igv = imp_tot_gen_igv.add(imp_igv);
//		                imp_tot_gen_isc = imp_tot_gen_isc.add(imp_isc);
//		                
//		                		    	    
//			    	    row++;
//			    	    
//			    	    if (can == ult) { // Validacion ultimo registro
//			    	    	rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Sub-Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//			    	    	
//				    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_det_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_det_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);
//				    	   
//				    	    
//				    	    row = row + 1;
//				    	 
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//			    	    	
//				    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_tot_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_tot_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_tot_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_tot_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);
//				    	    
//				    	    
//				    	    row = row + 1;
//					    	 
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Total General: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//				    	    
//				    	    
//				    	    rows.createCell(6).setCellValue(dec_form.format(imp_tot_gen_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(imp_tot_gen_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(imp_tot_gen_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(imp_tot_gen_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_tot_gen_soles));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_tot_gen_dolares));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);	
//				    	    
//			    	    }		    	    
//			    	    
//			    	    can++;
//			        }
//		        
//		            
//	            }
//	                    		        
//	        	
//	        }
//	                    
//            
//            // Captured backflow
//            OutputStream out = response.getOutputStream();
//            wb.write(out); // We write in that flow
//            out.flush(); // We emptied the flow
//            out.close(); // We close the flow
//            
//    	} catch(Exception e){
//    		log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//					  Constantes.NIVEL_APP_REPORTER, 
//					  this.getClass().getName(), e.getMessage()));
//    	}
//	}
//	
//	/**
//	 * @param ruta
//	 * @param ingreso 
//	 * @param lista
//	 * @return Document
//	 */
//	public Document generaPDFReporteIngresos(String ruta, ComprasIngresosBean ingreso, List<ReporteDetalleIngresosBean> lista) {
//		Document document = null;
//        try {                        
//        	document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
//       
//            PdfWriter.getInstance(document, new FileOutputStream(ruta)); 
//            document.open();
//
//            float[] pri = {2.00f};
//            
//            float[] ter = {0.25f, 0.05f, 1.70f};
//            
//            float[] cin = {0.13f, 0.02f, 0.45f, 0.30f, 0.30f};
//          
//            float[] cell_serv = {0.20f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f,
//					  			 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f,
//					  			 0.30f};
//            
//            float[] cell_bol = {0.20f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f,
//		  			 			0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 
//		  			 			0.30f, 0.30f, 0.30f, 0.30f};	
//            
//            float[] cell_todos = {0.20f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f,
//            					  0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f,
//            					  0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f};
//            
//            float[] cell_ven = {0.30f, 0.30f, 0.10f, 0.30f, 0.40f, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f,
//					 			0.30f};
//            
//            
//            Paragraph p     = null;
//            Paragraph extra = null;
//            PdfPTable table = null;
//            PdfPCell cell   = null;
// 
//            Font normal = FontFactory.getFont("Arial", 6, Font.NORMAL, BaseColor.BLACK);    
//            Font negrita = FontFactory.getFont("Arial", 6, Font.BOLD, BaseColor.BLACK);
//            Font neg_reg = FontFactory.getFont("Arial", 4, Font.BOLD, BaseColor.BLACK);
//            Font nor_reg = FontFactory.getFont("Arial", 4, Font.NORMAL, BaseColor.BLACK);  
//            
//            DecimalFormatSymbols other_symbols = new DecimalFormatSymbols(Locale.US);
//            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, other_symbols);
//               
//            
//            
//            if (!ingreso.getTip_transaccion().equals(Constantes.SIX_STRING) && // Registro de Ventas
//            		!ingreso.getTip_transaccion().equals(Constantes.SEVEN_STRING)) { // Formato 14.1 - Registro de Ventas e Ingresos
//
//	            table = new PdfPTable(pri);     
//	            p = new Paragraph("Registro de Comprobantes de Pago", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorderColor(BaseColor.WHITE);
//	            table.addCell(cell);
//	            document.add(table);
//	            
//	            
//	            document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//	            
//	            
//	            table = new PdfPTable(ter);     
//	            p = new Paragraph("Unidad Operativa", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(":", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            String des_uni_operativa = ingreso.getDes_uni_operativa();
//	            if ((des_uni_operativa.trim()).length() == 0) {
//	            	des_uni_operativa = "Todos";
//	            }            
//	            p = new Paragraph(des_uni_operativa, normal);            
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            document.add(table);
//	            
//	            
//	            table = new PdfPTable(ter);     
//	            p = new Paragraph("ANP", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	
//	            p = new Paragraph(":", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            String des_anp = ingreso.getDes_reg_nac();
//	            if ((des_anp.trim()).length() == 0) {
//	            	des_anp = "Todos";
//	            }            
//	            p = new Paragraph(des_anp, normal);            
//	            cell = new PdfPCell(p);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            document.add(table);
//	            
//	            
//	            table = new PdfPTable(pri);     
//	            p = new Paragraph("Del  ", negrita);            
//	            extra = new Paragraph(ingreso.getFec_inicio(), normal); 
//		        p.add(extra);	        
//		        extra = new Paragraph("  al  ", negrita); 
//		        p.add(extra);	        
//		        extra = new Paragraph(ingreso.getFec_final(), normal); 
//		        p.add(extra);            
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	            cell.setBorderColor(BaseColor.WHITE);
//	            table.addCell(cell);
//	            document.add(table);
//            
//            } else {
//            	
//            	table = new PdfPTable(pri);
//            	String fec_sistema = "Fecha del sistema : ";
//            	String fec_actual = DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, new Date());
//            	p = new Paragraph(fec_sistema.concat(fec_actual), negrita);
//            	cell = new PdfPCell(p);
//            	cell.setBorder(Rectangle.NO_BORDER);
//                table.addCell(cell); 
//                
//                document.add(table);
//            	
//                
//                document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//            	
//            	
//                table = new PdfPTable(pri);
//	            p = new Paragraph("REPORTE DE VENTAS", negrita);         
//                cell = new PdfPCell(p);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            document.add(table);
//	            
//	            
//	            table = new PdfPTable(pri);            	
//            	StringBuilder periodo = new StringBuilder();
//		        periodo.append(getMes(ingreso.getCod_mes()));
//		        periodo.append(Constantes.ESPACIO);
//		        periodo.append(DateUtil.getAnioActual());
//	            p = new Paragraph(periodo.toString(), negrita);         
//                cell = new PdfPCell(p);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(cell);
//	            
//	            document.add(table);
//	            
//	            
//	            table = new PdfPTable(cin);            	
//            	p = new Paragraph("SERNANP", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(3);
//	            table.addCell(cell);	            
//	            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(2);
//	            table.addCell(cell);
//	            
//	            document.add(table);
//	            
//	            
//	            table = new PdfPTable(cin);             	
//            	p = new Paragraph("Servicio Nacional de Areas Naturales Protegidas por el Estado", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(3);
//	            table.addCell(cell);	            
//	            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setColspan(2);
//	            table.addCell(cell);
//	            
//	            document.add(table);
//            	
//            }
//            
//            document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//            
//            
//            if (!ingreso.getTip_transaccion().equals(Constantes.SIX_STRING) && // Registro de Ventas
//            		!ingreso.getTip_transaccion().equals(Constantes.SEVEN_STRING)) { // Formato 14.1 - Registro de Ventas e Ingresos
//            
//	            if (!isNullOrEmpty(ingreso.getTip_transaccion())) {
//	            	if (ingreso.getTip_transaccion().equals(Constantes.THREE_STRING)) { // Boletos
//	            		table = new PdfPTable(cell_bol);
//	            	} else {
//	            		table = new PdfPTable(cell_serv);
//	            	}            	
//	            } else {
//	            	table = new PdfPTable(cell_todos);
//	            }                
//	            p = new Paragraph("Item", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Tip. C/P", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Serie y Nro C/P", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Fecha C/P", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Tipo Moneda", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Tipo Cambio", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);            
//	            
//	            p = new Paragraph("Unid. Oper.", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("ANP", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("RUC", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Razon Social", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            if (!isNullOrEmpty(ingreso.getTip_transaccion())) {
//	            	if (ingreso.getTip_transaccion().equals(Constantes.ONE_STRING)) {
//	            		p = new Paragraph("Servicio", negrita);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                    cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                    table.addCell(cell);
//	                                     
//	                    p = new Paragraph("Dias", negrita);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                    cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                    table.addCell(cell);
//					} else if (ingreso.getTip_transaccion().equals(Constantes.TWO_STRING)) {
//						p = new Paragraph("Producto", negrita);
//		                cell = new PdfPCell(p);
//		                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//		                table.addCell(cell);
//		                
//		                p = new Paragraph("Cantidad", negrita);
//		                cell = new PdfPCell(p);
//		                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//		                table.addCell(cell);
//					} else if (ingreso.getTip_transaccion().equals(Constantes.THREE_STRING)) {
//						p = new Paragraph("Serie Tarifa", negrita);
//		                cell = new PdfPCell(p);
//		                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//		                table.addCell(cell);
//						
//						p = new Paragraph("Tarifa", negrita);
//		                cell = new PdfPCell(p);
//		                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//		                table.addCell(cell);
//		                
//		                p = new Paragraph("Nro. Inicio", negrita);
//		                cell = new PdfPCell(p);
//		                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//		                table.addCell(cell);
//		                
//		                p = new Paragraph("Nro. Fin", negrita);
//		                cell = new PdfPCell(p);
//		                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//		                table.addCell(cell);
//		                
//		                p = new Paragraph("Cantidad", negrita);
//		                cell = new PdfPCell(p);
//		                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//		                table.addCell(cell);
//					} else if (ingreso.getTip_transaccion().equals(Constantes.FOUR_STRING)) {
//						p = new Paragraph("Tarifa", negrita);
//		                cell = new PdfPCell(p);
//		                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//		                table.addCell(cell);
//		                
//		                p = new Paragraph("Cantidad", negrita);
//		                cell = new PdfPCell(p);
//		                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//		                table.addCell(cell);
//					} else if (ingreso.getTip_transaccion().equals(Constantes.FIVE_STRING)) {
//						p = new Paragraph("Tarifa", negrita);
//		                cell = new PdfPCell(p);
//		                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//		                table.addCell(cell);
//		                
//		                p = new Paragraph("Cantidad", negrita);
//		                cell = new PdfPCell(p);
//		                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//		                table.addCell(cell);
//					}
//	            } else {
//	            	p = new Paragraph("Servicio", negrita);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                table.addCell(cell);
//	                
//	                p = new Paragraph("Producto", negrita);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                table.addCell(cell);
//	                
//	                p = new Paragraph("Serie Tarifa", negrita);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                table.addCell(cell);
//	                
//	                p = new Paragraph("Tarifa Boleto", negrita);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                table.addCell(cell);
//	                
//	                p = new Paragraph("Nro. Inicio", negrita);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                table.addCell(cell);
//	                
//	                p = new Paragraph("Nro. Fin", negrita);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                table.addCell(cell);
//	                
//	                p = new Paragraph("Tarifa Tupa", negrita);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                table.addCell(cell);
//	                
//	                p = new Paragraph("Tarifa Otros", negrita);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                table.addCell(cell);
//	                
//	                p = new Paragraph("Cantidad", negrita);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                table.addCell(cell);
//	                
//	                p = new Paragraph("Dias", negrita);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	                table.addCell(cell);  
//	            }
//	            p = new Paragraph("Prec. Vta.", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Afecto", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("No Afecto", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("IGV", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("ISC", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Total", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Cta Cte", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Nro Depósito", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Fec. Depósito", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            table.addCell(cell);
//	            
//	            document.add(table);
//	            
//	            int row = 1;
//	            BigDecimal num_imp_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_igv = BigDecimal.ZERO;
//	            BigDecimal num_imp_isc = BigDecimal.ZERO;
//	            BigDecimal imp_det_total = BigDecimal.ZERO;
//	            
//	            for (ReporteDetalleIngresosBean rep_ingreso : lista) {
//	            
//	            	if (!isNullOrEmpty(ingreso.getTip_transaccion())) {
//	            		if (ingreso.getTip_transaccion().equals(Constantes.THREE_STRING)) { // Boletos
//	                		table = new PdfPTable(cell_bol);
//	                	} else {
//	                		table = new PdfPTable(cell_serv);
//	                	}
//	                } else {
//	                	table = new PdfPTable(cell_todos); 
//	                }
//	            	p = new Paragraph(getString(row), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_ingreso.getTip_com_siglas(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                String ser_nro = rep_ingreso.getSer_comprobante().concat(" - ");
//	    	        ser_nro = ser_nro.concat(rep_ingreso.getNro_comprobante());
//	                p = new Paragraph(ser_nro, normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_ingreso.getFec_comprobante(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_ingreso.getTip_moneda(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(getString(rep_ingreso.getNum_tip_cambio()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_ingreso.getDes_uni_operativa(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_ingreso.getDes_reg_nac(), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(getString(rep_ingreso.getRuc_nro_doc_prov()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(getString(rep_ingreso.getRaz_soc_prov()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	            	
//	                if (!isNullOrEmpty(ingreso.getTip_transaccion())) {
//	                	if (ingreso.getTip_transaccion().equals(Constantes.ONE_STRING)) {
//	                		p = new Paragraph(rep_ingreso.getDes_servicio(), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	                        
//	                        p = new Paragraph(getString(rep_ingreso.getCan_dias()), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	    				} else if (ingreso.getTip_transaccion().equals(Constantes.TWO_STRING)) {
//	    					p = new Paragraph(rep_ingreso.getNom_producto(), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	                        
//	                        p = new Paragraph(getString(rep_ingreso.getCan_producto()), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	    				} else if (ingreso.getTip_transaccion().equals(Constantes.THREE_STRING)) {
//	    					p = new Paragraph(getString(rep_ingreso.getNum_ser_boleto()), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	    					
//	    					p = new Paragraph(rep_ingreso.getDes_boleto(), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	                        
//	                        p = new Paragraph(getString(rep_ingreso.getIni_boleto()), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	                        
//	                        p = new Paragraph(getString(rep_ingreso.getFin_boleto()), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	                        
//	                        p = new Paragraph(getString(rep_ingreso.getCan_producto()), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	    				} else if (ingreso.getTip_transaccion().equals(Constantes.FOUR_STRING)) {
//	    					p = new Paragraph(rep_ingreso.getNom_tupa(), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	                        
//	                        p = new Paragraph(getString(rep_ingreso.getCan_producto()), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	    				} else if (ingreso.getTip_transaccion().equals(Constantes.FIVE_STRING)) {
//	    					p = new Paragraph(rep_ingreso.getDes_otros(), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	                        
//	                        p = new Paragraph(getString(rep_ingreso.getCan_producto()), normal);
//	                        cell = new PdfPCell(p);
//	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                        table.addCell(cell);
//	    				}
//	                } else {
//	                	p = new Paragraph(rep_ingreso.getDes_servicio(), normal);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                    table.addCell(cell);
//	                    
//	                    p = new Paragraph(rep_ingreso.getNom_producto(), normal);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                    table.addCell(cell);
//	                    
//	                    p = new Paragraph(getString(rep_ingreso.getNum_ser_boleto()), normal);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                    table.addCell(cell);
//	                    
//	                    p = new Paragraph(rep_ingreso.getDes_boleto(), normal);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                    table.addCell(cell);
//	                    
//	                    p = new Paragraph(getString(rep_ingreso.getIni_boleto()), normal);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                    table.addCell(cell);
//	                    
//	                    p = new Paragraph(getString(rep_ingreso.getFin_boleto()), normal);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                    table.addCell(cell);
//	                    
//	                    p = new Paragraph(rep_ingreso.getNom_tupa(), normal);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                    table.addCell(cell);
//	                    
//	                    p = new Paragraph(rep_ingreso.getDes_otros(), normal);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                    table.addCell(cell);
//	                    
//	                    p = new Paragraph(getString(rep_ingreso.getCan_producto()), normal);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                    table.addCell(cell);
//	                    
//	                    p = new Paragraph(getString(rep_ingreso.getCan_dias()), normal);
//	                    cell = new PdfPCell(p);
//	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                    table.addCell(cell);
//	                }
//	                p = new Paragraph(dec_form.format(rep_ingreso.getNum_pre_unitario()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_afecto()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_no_afecto()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_igv()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_isc()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(dec_form.format(rep_ingreso.getImp_det_total()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(getString(rep_ingreso.getNum_cta_cte()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(getString(rep_ingreso.getNum_voucher()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(getString(rep_ingreso.getFec_voucher()), normal);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                document.add(table);
//	                
//	                num_imp_afecto = num_imp_afecto.add(rep_ingreso.getNum_imp_afecto());
//	                num_imp_no_afecto = num_imp_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//	                num_imp_igv = num_imp_igv.add(rep_ingreso.getNum_imp_igv());
//	                num_imp_isc = num_imp_isc.add(rep_ingreso.getNum_imp_isc());
//	                imp_det_total = imp_det_total.add(rep_ingreso.getImp_det_total());
//		            
//		            row++;
//	            }
//	            
//	            if (!isNullOrEmpty(ingreso.getTip_transaccion())) {
//	            	if (ingreso.getTip_transaccion().equals(Constantes.THREE_STRING)) { // Boletos
//	            		table = new PdfPTable(cell_bol);
//	            	} else {
//	            		table = new PdfPTable(cell_serv);
//	            	}
//	            } else {
//	            	table = new PdfPTable(cell_todos); 
//	            }
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell); 
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//	                      
//	            p = new Paragraph("Totales:", negrita);
//	            if (isNullOrEmpty(ingreso.getTip_transaccion())) {
//	            	cell = new PdfPCell();
//	                cell.setBorder(Rectangle.NO_BORDER);
//	                cell.setBorderColorTop(BaseColor.BLACK);
//	                table.addCell(cell);
//	            	cell = new PdfPCell();
//	                cell.setBorder(Rectangle.NO_BORDER);
//	                cell.setBorderColorTop(BaseColor.BLACK);
//	                table.addCell(cell);
//	            	cell = new PdfPCell();
//	                cell.setBorder(Rectangle.NO_BORDER);
//	                cell.setBorderColorTop(BaseColor.BLACK);
//	                table.addCell(cell);
//	                cell = new PdfPCell();
//	                cell.setBorder(Rectangle.NO_BORDER);
//	                cell.setBorderColorTop(BaseColor.BLACK);
//	                table.addCell(cell);            
//	                cell = new PdfPCell();
//	                cell.setBorder(Rectangle.NO_BORDER);
//	                cell.setBorderColorTop(BaseColor.BLACK);
//	                table.addCell(cell);            
//	                cell = new PdfPCell();
//	                cell.setBorder(Rectangle.NO_BORDER);
//	                cell.setBorderColorTop(BaseColor.BLACK);
//	                table.addCell(cell);
//	                cell = new PdfPCell();
//	                cell.setBorder(Rectangle.NO_BORDER);
//	                cell.setBorderColorTop(BaseColor.BLACK);
//	                table.addCell(cell);            
//	                cell = new PdfPCell();
//	                cell.setBorder(Rectangle.NO_BORDER);
//	                cell.setBorderColorTop(BaseColor.BLACK);
//	                table.addCell(cell);
//	                cell = new PdfPCell(p);
//	            	cell.setBorder(Rectangle.NO_BORDER);
//	            	cell.setBorderColorTop(BaseColor.BLACK);
//	                cell.setBorderColorRight(BaseColor.BLACK); 
//	                table.addCell(cell);
//	            } else {
//	            	if (ingreso.getTip_transaccion().equals(Constantes.THREE_STRING)) { // Boletos
//	            		cell = new PdfPCell();
//	                    cell.setBorder(Rectangle.NO_BORDER);
//	                    cell.setBorderColorTop(BaseColor.BLACK);
//	                    table.addCell(cell);
//	                    cell = new PdfPCell();
//	                    cell.setBorder(Rectangle.NO_BORDER);
//	                    cell.setBorderColorTop(BaseColor.BLACK);
//	                    table.addCell(cell);
//	                    cell = new PdfPCell();
//	                    cell.setBorder(Rectangle.NO_BORDER);
//	                    cell.setBorderColorTop(BaseColor.BLACK);
//	                    table.addCell(cell);
//	            		cell = new PdfPCell(p);
//	                	cell.setBorder(Rectangle.NO_BORDER);
//	                	cell.setBorderColorTop(BaseColor.BLACK);
//	                    cell.setBorderColorRight(BaseColor.BLACK); 
//	                    table.addCell(cell);
//	            	} else {
//	            		cell = new PdfPCell(p);
//	                	cell.setBorder(Rectangle.NO_BORDER);
//	                	cell.setBorderColorTop(BaseColor.BLACK);
//	                    cell.setBorderColorRight(BaseColor.BLACK); 
//	                    table.addCell(cell);
//	            	}
//	            }
//	            
//	            p = new Paragraph(dec_form.format(num_imp_afecto), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_imp_no_afecto), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_imp_igv), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_imp_isc), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(imp_det_total), negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            cell = new PdfPCell();
//	        	cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell); 
//	            
//	        	cell = new PdfPCell();
//	        	cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell); 
//	            
//	            cell = new PdfPCell();
//	        	cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell); 
//	            
//	            document.add(table);
//	            
//            } else if (ingreso.getTip_transaccion().equals(Constantes.SIX_STRING)) { // Registro de Ventas
//            	
//            	table = new PdfPTable(cell_ven);
//            	
//            	p = new Paragraph("N° Documento", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Fecha", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Doc", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Num", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Nombre", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Afecto", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("No Afecto", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("IGV", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("ISC", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//            	
//	            p = new Paragraph("Total (S/.)", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("Total (US$)", negrita);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//	            table.addCell(cell);
//            	
//	            document.add(table);
//	            
//	            
//	            
//	            String unidad = "";
//		        String documento = "";
//		        if (!isEmpty(lista)) {
//		        	unidad = lista.get(0).getDes_uni_operativa();
//		        	documento = lista.get(0).getTip_comprobante();
//		        }
//		        
//		        String tip_operacion = ingreso.getTip_operacion();
//		        
//		        List<ReporteDetalleIngresosBean> lis_uni_ope = null;
//		        List<ReporteDetalleIngresosBean> lis_sede = null;	
//	            
//		        if (tip_operacion.equals(Constantes.TWO_STRING)) { // Sede Central
//		        	lis_uni_ope = new ArrayList<ReporteDetalleIngresosBean>();
//		        	lis_sede = new ArrayList<ReporteDetalleIngresosBean>();
//		        	for (ReporteDetalleIngresosBean rep : lista) {
//		        		if (rep.getTip_operacion().equals(Constantes.TWO_STRING)) { // Sede Central
//		        			lis_sede.add(rep);
//		        		} else {
//		        			lis_uni_ope.add(rep);
//		        		}			        		
//		        	}
//		        	lista.clear();
//		        	lista.addAll(lis_sede);
//
//		        	table = new PdfPTable(cell_ven);
//			        p = new Paragraph("SERNANP - SEDE CENTRAL", negrita);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            cell.setColspan(11);
//		            table.addCell(cell);
//		            document.add(table);
//	    	      	    	        
//		        } else { // Unidad Operativa
//
//		        	table = new PdfPTable(cell_ven);
//			        p = new Paragraph(unidad, negrita);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            cell.setColspan(11);
//		            table.addCell(cell);
//		            document.add(table);
//		        	
//		        }
//	            
//
//		        boolean fir = true;
//		        boolean sub = false;
//		        int can = 0;
//		        int ult = lista.size() - 1;
//		        BigDecimal num_imp_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_igv = BigDecimal.ZERO;
//	            BigDecimal num_imp_isc = BigDecimal.ZERO;
//	            BigDecimal imp_det_total = BigDecimal.ZERO;
//	            BigDecimal imp_det_dol_total = BigDecimal.ZERO;
//	            
//	            BigDecimal num_imp_tot_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_no_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_igv = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_isc = BigDecimal.ZERO;
//	            BigDecimal imp_total = BigDecimal.ZERO;
//	            BigDecimal imp_dol_total = BigDecimal.ZERO;
//	            
//		        for (ReporteDetalleIngresosBean rep_ingreso : lista) {
//		        	         		            	
//		        	if (!documento.equals(rep_ingreso.getTip_comprobante()) && !fir) {
//	            		
//	            		table = new PdfPTable(cell_ven);
//			    	    
//			    	    cell = new PdfPCell();
//			            cell.setBorder(Rectangle.TOP);
//			            cell.setColspan(4);
//			            table.addCell(cell);
//			    	    
//				        p = new Paragraph("Sub-Total:", negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//	            		sub = true;	            		
//	            	} else if (fir) {
//	            		
//	            		table = new PdfPTable(cell_ven);
//	            		p = new Paragraph("Documento: ".concat(documento.toUpperCase()), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            cell.setColspan(11);
//			            table.addCell(cell);
//			            document.add(table);
//	            		
//	            		fir = false;
//	            	}
//	            	
//	            	if (!sub) {	            	
//	            		table = new PdfPTable(cell_ven);
//	            	} else {
//	            		
//	            		p = new Paragraph(dec_form.format(num_imp_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//	            		
//			            p = new Paragraph(dec_form.format(num_imp_no_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_igv), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_isc), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(imp_det_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//	            					            
//			            p = new Paragraph(dec_form.format(imp_det_dol_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            document.add(table);
//	            			            		
//			    	    
//			    	    num_imp_afecto = BigDecimal.ZERO;
//			            num_imp_no_afecto = BigDecimal.ZERO;
//			            num_imp_igv = BigDecimal.ZERO;
//			            num_imp_isc = BigDecimal.ZERO;
//			            imp_det_total = BigDecimal.ZERO;
//			            imp_det_dol_total = BigDecimal.ZERO;
//			    	    
//	            		sub = false;
//	            		
//			        	if (ingreso.getTip_operacion().equals(Constantes.ONE_STRING)) { // Unidad Operativa
//			        		if (!unidad.equals(rep_ingreso.getDes_uni_operativa())) {
//		                				                		
//		                		unidad = rep_ingreso.getDes_uni_operativa();
//		                		
//		                				                		
//		                		table = new PdfPTable(cell_ven);
//					    	    
//					    	    cell = new PdfPCell();
//					    	    cell.setBorder(Rectangle.TOP);
//					            cell.setColspan(4);
//					            table.addCell(cell);
//					    	    
//						        p = new Paragraph("Total:", negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            p = new Paragraph(dec_form.format(num_imp_tot_afecto), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            p = new Paragraph(dec_form.format(num_imp_tot_no_afecto), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(num_imp_tot_igv), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(num_imp_tot_isc), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(imp_total), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(imp_dol_total), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            document.add(table);
//					            
//		                		
//					    	    num_imp_tot_afecto = BigDecimal.ZERO;
//					            num_imp_tot_no_afecto = BigDecimal.ZERO;
//					            num_imp_tot_igv = BigDecimal.ZERO;
//					            num_imp_tot_isc = BigDecimal.ZERO;
//					            imp_total = BigDecimal.ZERO;
//					            imp_dol_total = BigDecimal.ZERO;
//		                		
//					            
//					            document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//					            
//					            
//					            table = new PdfPTable(cell_ven);
//						        p = new Paragraph(unidad, negrita);
//					            cell = new PdfPCell(p);
//					            cell.setBorder(Rectangle.NO_BORDER);
//					            cell.setColspan(11);
//					            table.addCell(cell);
//					            document.add(table);					            
//		                		
//		                	} else {
//		                		
//		                		document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//		                		
//		                	}
//			        		
//			        	} else { // Sede Central	
//			        		
//			        		document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//			        		
//			        	}
//	            		
//			        	
//			        	
//			        	table = new PdfPTable(cell_ven);
//			        	documento = rep_ingreso.getTip_comprobante();
//	            		p = new Paragraph("Documento: ".concat(documento.toUpperCase()), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            cell.setColspan(11);
//			            table.addCell(cell);
//			            document.add(table);
//			        	
//			        	
//	            		
//			            table = new PdfPTable(cell_ven);
//	            	}
//	            		
//	            	
//	            	String ser_nro = rep_ingreso.getSer_comprobante().concat(" - ");
//		    	    ser_nro = ser_nro.concat(rep_ingreso.getNro_comprobante());
//	            	p = new Paragraph(ser_nro, normal);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//	            	
//		            p = new Paragraph(rep_ingreso.getFec_comprobante(), normal);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(rep_ingreso.getTip_documento(), normal);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(getString(rep_ingreso.getRuc_nro_doc_prov()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(getString(rep_ingreso.getRaz_soc_prov()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_afecto()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_no_afecto()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		            p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_igv()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//
//		            p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_isc()), normal);
//		            cell = new PdfPCell(p);
//		            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            table.addCell(cell);
//		            
//		    	    if (rep_ingreso.getCod_moneda().equals(Constantes.ONE_INT)) { // Soles
//		    	    	
//		    	    	p = new Paragraph(dec_form.format(rep_ingreso.getImp_det_total()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(BigDecimal.ZERO), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			    	    
//
//			    	    imp_det_total = imp_det_total.add(rep_ingreso.getImp_det_total());
//			    	    imp_total = imp_total.add(rep_ingreso.getImp_det_total());
//			    	    
//		    	    } else { // Dolares
//		    	    	
//		    	    	if (rep_ingreso.getNum_tip_cambio() != null && rep_ingreso.getNum_tip_cambio().compareTo(BigDecimal.ZERO) > 0) {
//		    	    		
//		    	    		p = new Paragraph(dec_form.format(rep_ingreso.getImp_det_total()), normal);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(rep_ingreso.getImp_det_total_dolares()), normal);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            table.addCell(cell);
//				            
//				            imp_det_total = imp_det_total.add(rep_ingreso.getImp_det_total());
//				    	    imp_total = imp_total.add(rep_ingreso.getImp_det_total());
//				            
//				            imp_det_dol_total = imp_det_dol_total.add(rep_ingreso.getImp_det_total_dolares());
//				    	    imp_dol_total = imp_dol_total.add(rep_ingreso.getImp_det_total_dolares());
//		    	    		
//		    	    	} else {
//		    	    		
//		    	    		p = new Paragraph(dec_form.format(BigDecimal.ZERO), normal);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            table.addCell(cell);
//				    	    
//				            p = new Paragraph(dec_form.format(rep_ingreso.getImp_det_total()), normal);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            table.addCell(cell);				    	    
//				    	    
//				    	    imp_det_dol_total = imp_det_dol_total.add(rep_ingreso.getImp_det_total());
//				    	    imp_dol_total = imp_dol_total.add(rep_ingreso.getImp_det_total());
//		    	    		
//		    	    	}
//
//		    	    }
//		    	    	
//		    	    document.add(table);
//		    	    
//		    	    
//		    	    num_imp_afecto = num_imp_afecto.add(rep_ingreso.getNum_imp_afecto());
//	                num_imp_no_afecto = num_imp_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//	                num_imp_igv = num_imp_igv.add(rep_ingreso.getNum_imp_igv());
//	                num_imp_isc = num_imp_isc.add(rep_ingreso.getNum_imp_isc());	                
//	                
//
//                	num_imp_tot_afecto = num_imp_tot_afecto.add(rep_ingreso.getNum_imp_afecto());
//	                num_imp_tot_no_afecto = num_imp_tot_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//	                num_imp_tot_igv = num_imp_tot_igv.add(rep_ingreso.getNum_imp_igv());
//	                num_imp_tot_isc = num_imp_tot_isc.add(rep_ingreso.getNum_imp_isc());
//	                
//	
//		    	    if (can == ult) { // Validacion ultimo registro
//		    	    	
//		    	    	table = new PdfPTable(cell_ven);
//			    	    
//			    	    cell = new PdfPCell();
//			    	    cell.setBorder(Rectangle.TOP);
//			            cell.setColspan(4);
//			            table.addCell(cell);
//			    	    
//				        p = new Paragraph("Sub-Total:", negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//	            		
//			            p = new Paragraph(dec_form.format(num_imp_no_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_igv), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_isc), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(imp_det_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//	            					            
//			            p = new Paragraph(dec_form.format(imp_det_dol_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            document.add(table);
//		    	    	
//			            
//			            table = new PdfPTable(cell_ven);
//			    	    
//			    	    cell = new PdfPCell();
//			    	    cell.setBorder(Rectangle.TOP);
//			            cell.setColspan(4);
//			            table.addCell(cell);
//			    	    
//				        p = new Paragraph("Total:", negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//                		
//			            p = new Paragraph(dec_form.format(num_imp_tot_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//                		
//			            p = new Paragraph(dec_form.format(num_imp_tot_no_afecto), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_tot_igv), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(num_imp_tot_isc), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(imp_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(imp_dol_total), negrita);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.TOP);
//			            table.addCell(cell);
//                		
//			            document.add(table);
//				    	   			    	 
//			    	    
//		    	    }		    	    
//		    	    
//		    	    can++;
//		        }
//		        
//		        
//		        if (tip_operacion.equals(Constantes.TWO_STRING)) { // Sede Central
//		        	
//		        	unidad = "";
//			        documento = "";
//			        String are_nat_pro = "";
//			        if (!isEmpty(lis_uni_ope)) {
//			        	unidad = lis_uni_ope.get(0).getDes_uni_operativa();
//			        	are_nat_pro = lis_uni_ope.get(0).getDes_reg_nac().toUpperCase();
//			        	documento = lis_uni_ope.get(0).getTip_comprobante();
//			        }
//
//			        document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//			        
//			        
//			        table = new PdfPTable(cell_ven);
//			        p = new Paragraph(are_nat_pro, negrita);
//		            cell = new PdfPCell(p);
//		            cell.setBorder(Rectangle.NO_BORDER);
//		            cell.setColspan(11);
//		            table.addCell(cell);
//		            document.add(table);
//			        
//			        
//			        fir = true;
//			        sub = false;
//			        can = 0;
//			        ult = lis_uni_ope.size() - 1;
//			        num_imp_afecto = BigDecimal.ZERO;
//		            num_imp_no_afecto = BigDecimal.ZERO;
//		            num_imp_igv = BigDecimal.ZERO;
//		            num_imp_isc = BigDecimal.ZERO;
//		            imp_det_total = BigDecimal.ZERO;
//		            imp_det_dol_total = BigDecimal.ZERO;
//		            
//		            num_imp_tot_afecto = BigDecimal.ZERO;
//		            num_imp_tot_no_afecto = BigDecimal.ZERO;
//		            num_imp_tot_igv = BigDecimal.ZERO;
//		            num_imp_tot_isc = BigDecimal.ZERO;
//		            imp_total = BigDecimal.ZERO;
//		            imp_dol_total = BigDecimal.ZERO;
//		            
//		            BigDecimal imp_tot_gen_afecto = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_no_afecto = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_igv = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_isc = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_soles = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_dolares = BigDecimal.ZERO;
//		        	
//		        	for (ReporteDetalleIngresosBean rep_ingreso : lis_uni_ope) {
//		        		
//		        		if (!documento.equals(rep_ingreso.getTip_comprobante()) && !fir) {
//		            		
//		            		table = new PdfPTable(cell_ven);
//				    	    
//				    	    cell = new PdfPCell();
//				            cell.setBorder(Rectangle.TOP);
//				            cell.setColspan(4);
//				            table.addCell(cell);
//				    	    
//					        p = new Paragraph("Sub-Total:", negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//		            		sub = true;	            		
//		            	} else if (fir) {
//		            		
//		            		table = new PdfPTable(cell_ven);
//		            		p = new Paragraph("Documento: ".concat(documento.toUpperCase()), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            cell.setColspan(11);
//				            table.addCell(cell);
//				            document.add(table);
//		            		
//		            		fir = false;
//		            	}
//		            	
//		            	if (!sub) {	            	
//		            		table = new PdfPTable(cell_ven);
//		            	} else {
//		            		
//		            		p = new Paragraph(dec_form.format(num_imp_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//		            		
//				            p = new Paragraph(dec_form.format(num_imp_no_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_igv), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_isc), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_det_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//		            					            
//				            p = new Paragraph(dec_form.format(imp_det_dol_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            document.add(table);
//		            			            		
//				    	    
//				    	    num_imp_afecto = BigDecimal.ZERO;
//				            num_imp_no_afecto = BigDecimal.ZERO;
//				            num_imp_igv = BigDecimal.ZERO;
//				            num_imp_isc = BigDecimal.ZERO;
//				            imp_det_total = BigDecimal.ZERO;
//				            imp_det_dol_total = BigDecimal.ZERO;
//				    	    
//		            		sub = false;
//		            		
//		            		if (!are_nat_pro.equals(rep_ingreso.getDes_reg_nac().toUpperCase())) {			                		
//			                				                		
//		                		table = new PdfPTable(cell_ven);
//					    	    
//					    	    cell = new PdfPCell();
//					    	    cell.setBorder(Rectangle.TOP);
//					            cell.setColspan(4);
//					            table.addCell(cell);
//					    	    
//						        p = new Paragraph("Total:", negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            p = new Paragraph(dec_form.format(num_imp_tot_afecto), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            p = new Paragraph(dec_form.format(num_imp_tot_no_afecto), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(num_imp_tot_igv), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(num_imp_tot_isc), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(imp_total), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(imp_dol_total), negrita);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.TOP);
//					            table.addCell(cell);
//		                		
//					            document.add(table);
//					            
//		                		
//					    	    num_imp_tot_afecto = BigDecimal.ZERO;
//					            num_imp_tot_no_afecto = BigDecimal.ZERO;
//					            num_imp_tot_igv = BigDecimal.ZERO;
//					            num_imp_tot_isc = BigDecimal.ZERO;
//					            imp_total = BigDecimal.ZERO;
//					            imp_dol_total = BigDecimal.ZERO;
//		                		
//					            
//					            document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//					            
//					            
//					            are_nat_pro = rep_ingreso.getDes_reg_nac().toUpperCase();
//					            
//					            table = new PdfPTable(cell_ven);
//						        p = new Paragraph(are_nat_pro, negrita);
//					            cell = new PdfPCell(p);
//					            cell.setBorder(Rectangle.NO_BORDER);
//					            cell.setColspan(11);
//					            table.addCell(cell);
//					            document.add(table);					            
//			                		
//				        		
//				        	} else { // Sede Central	
//				        		
//				        		document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea
//				        		
//				        	}
//		            		
//				        	
//				        	
//				        	table = new PdfPTable(cell_ven);
//				        	documento = rep_ingreso.getTip_comprobante();
//		            		p = new Paragraph("Documento: ".concat(documento.toUpperCase()), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            cell.setColspan(11);
//				            table.addCell(cell);
//				            document.add(table);
//				        	
//				        	
//		            		
//				            table = new PdfPTable(cell_ven);
//		            	}
//		            		
//		            	
//		            	String ser_nro = rep_ingreso.getSer_comprobante().concat(" - ");
//			    	    ser_nro = ser_nro.concat(rep_ingreso.getNro_comprobante());
//		            	p = new Paragraph(ser_nro, normal);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//		            	
//			            p = new Paragraph(rep_ingreso.getFec_comprobante(), normal);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(rep_ingreso.getTip_documento(), normal);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(getString(rep_ingreso.getRuc_nro_doc_prov()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(getString(rep_ingreso.getRaz_soc_prov()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_afecto()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_no_afecto()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			            p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_igv()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//
//			            p = new Paragraph(dec_form.format(rep_ingreso.getNum_imp_isc()), normal);
//			            cell = new PdfPCell(p);
//			            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			            cell.setBorder(Rectangle.NO_BORDER);
//			            table.addCell(cell);
//			            
//			    	    if (rep_ingreso.getCod_moneda().equals(Constantes.ONE_INT)) { // Soles
//			    	    	
//			    	    	p = new Paragraph(dec_form.format(rep_ingreso.getImp_det_total()), normal);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(BigDecimal.ZERO), normal);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.NO_BORDER);
//				            table.addCell(cell);
//				    	    
//
//				    	    imp_det_total = imp_det_total.add(rep_ingreso.getImp_det_total());
//				    	    imp_total = imp_total.add(rep_ingreso.getImp_det_total());
//				    	    imp_tot_gen_soles = imp_tot_gen_soles.add(rep_ingreso.getImp_det_total());
//				    	    
//			    	    } else { // Dolares
//			    	    	
//			    	    	if (rep_ingreso.getNum_tip_cambio() != null && rep_ingreso.getNum_tip_cambio().compareTo(BigDecimal.ZERO) > 0) {
//			    	    		
//			    	    		p = new Paragraph(dec_form.format(rep_ingreso.getImp_det_total()), normal);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.NO_BORDER);
//					            table.addCell(cell);
//					            
//					            p = new Paragraph(dec_form.format(rep_ingreso.getImp_det_total_dolares()), normal);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.NO_BORDER);
//					            table.addCell(cell);
//					            
//					            imp_det_total = imp_det_total.add(rep_ingreso.getImp_det_total());
//					    	    imp_total = imp_total.add(rep_ingreso.getImp_det_total());
//					    	    imp_tot_gen_soles = imp_tot_gen_soles.add(rep_ingreso.getImp_det_total());
//					            
//					    	    imp_det_dol_total = imp_det_dol_total.add(rep_ingreso.getImp_det_total_dolares());
//					    	    imp_dol_total = imp_dol_total.add(rep_ingreso.getImp_det_total_dolares());
//					    	    imp_tot_gen_dolares = imp_tot_gen_dolares.add(rep_ingreso.getImp_det_total_dolares());
//			    	    		
//			    	    	} else {
//			    	    		
//			    	    		p = new Paragraph(dec_form.format(BigDecimal.ZERO), normal);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.NO_BORDER);
//					            table.addCell(cell);
//					    	    
//					            p = new Paragraph(dec_form.format(rep_ingreso.getImp_det_total()), normal);
//					            cell = new PdfPCell(p);
//					            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					            cell.setBorder(Rectangle.NO_BORDER);
//					            table.addCell(cell);				    	    
//					    	    
//					            imp_det_dol_total = imp_det_dol_total.add(rep_ingreso.getImp_det_total());
//					    	    imp_dol_total = imp_dol_total.add(rep_ingreso.getImp_det_total());
//					    	    imp_tot_gen_dolares = imp_tot_gen_dolares.add(rep_ingreso.getImp_det_total());
//			    	    		
//			    	    	}
//
//			    	    }
//			    	    	
//			    	    document.add(table);
//			    	    
//			    	    
//			    	    num_imp_afecto = num_imp_afecto.add(rep_ingreso.getNum_imp_afecto());
//		                num_imp_no_afecto = num_imp_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//		                num_imp_igv = num_imp_igv.add(rep_ingreso.getNum_imp_igv());
//		                num_imp_isc = num_imp_isc.add(rep_ingreso.getNum_imp_isc());	                
//		                
//
//	                	num_imp_tot_afecto = num_imp_tot_afecto.add(rep_ingreso.getNum_imp_afecto());
//		                num_imp_tot_no_afecto = num_imp_tot_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//		                num_imp_tot_igv = num_imp_tot_igv.add(rep_ingreso.getNum_imp_igv());
//		                num_imp_tot_isc = num_imp_tot_isc.add(rep_ingreso.getNum_imp_isc());
//		                
//		                
//		                imp_tot_gen_afecto = imp_tot_gen_afecto.add(rep_ingreso.getNum_imp_afecto());
//		                imp_tot_gen_no_afecto = imp_tot_gen_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//		                imp_tot_gen_igv = imp_tot_gen_igv.add(rep_ingreso.getNum_imp_igv());
//		                imp_tot_gen_isc = imp_tot_gen_isc.add(rep_ingreso.getNum_imp_isc());
//		                
//		
//			    	    if (can == ult) { // Validacion ultimo registro
//			    	    	
//			    	    	table = new PdfPTable(cell_ven);
//				    	    
//				    	    cell = new PdfPCell();
//				    	    cell.setBorder(Rectangle.TOP);
//				            cell.setColspan(4);
//				            table.addCell(cell);
//				    	    
//					        p = new Paragraph("Sub-Total:", negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//		            		
//				            p = new Paragraph(dec_form.format(num_imp_no_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_igv), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_isc), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_det_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//		            					            
//				            p = new Paragraph(dec_form.format(imp_det_dol_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            document.add(table);
//			    	    	
//				            
//				            table = new PdfPTable(cell_ven);
//				    	    
//				    	    cell = new PdfPCell();
//				    	    cell.setBorder(Rectangle.TOP);
//				            cell.setColspan(4);
//				            table.addCell(cell);
//				    	    
//					        p = new Paragraph("Total:", negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            p = new Paragraph(dec_form.format(num_imp_tot_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            p = new Paragraph(dec_form.format(num_imp_tot_no_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_tot_igv), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(num_imp_tot_isc), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_dol_total), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            document.add(table);
//					    	   			    	 
//				            
//				            table = new PdfPTable(cell_ven);
//				    	    
//				    	    cell = new PdfPCell();
//				    	    cell.setBorder(Rectangle.TOP);
//				            cell.setColspan(4);
//				            table.addCell(cell);
//				    	    
//					        p = new Paragraph("Total General: ", negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            p = new Paragraph(dec_form.format(imp_tot_gen_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            p = new Paragraph(dec_form.format(imp_tot_gen_no_afecto), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_tot_gen_igv), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_tot_gen_isc), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_tot_gen_soles), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//				            
//				            p = new Paragraph(dec_form.format(imp_tot_gen_dolares), negrita);
//				            cell = new PdfPCell(p);
//				            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				            cell.setBorder(Rectangle.TOP);
//				            table.addCell(cell);
//	                		
//				            document.add(table);
//				    	    
//			    	    }		    	    
//			    	    
//			    	    can++;
//		        		
//		        	}
//		        
//				}
//		        
//            	
//            } else if (ingreso.getTip_transaccion().equals(Constantes.SEVEN_STRING)) { // Formato 14.1 - Registro de Ventas e Ingresos
//            	
//            	table = new PdfPTable(22); // 22 columnas
//	             
//				// Adicion celdas primer nivel
//            	
//	            p = new Paragraph("NÚMERO CORRELATIVO DEL REGISTRO O CÓDIGO UNICO DE LA OPERACIÓN", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);	            
//	            
//	            p = new Paragraph("FECHA DE EMISIÓN DEL COMPROBANTE DE PAGO O DOCUMENTO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("FECHA DE VENCIMIENTO Y/O PAGO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("COMPROBANTE DE PAGO O DOCUMENTO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(3);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("INFORMACIÓN DEL CLIENTE", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(3);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//            	
//	            p = new Paragraph("VALOR FACTURADO DE LA EXPORTACIÓN", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("BASE IMPONIBLE DE LA OPERACIÓN GRAVADA", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//            	
//	            p = new Paragraph("IMPORTE TOTAL DE LA OPERACIÓN EXONERADA O INAFECTA", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(2);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            	            
//	            p = new Paragraph("ISC", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("IGV Y/O IPM", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("OTROS TRIBUTOS Y CARGOS QUE NO FORMAN PARTE DE LA BASE IMPONIBLE", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("IMPORTE TOTAL DEL COMPROBANTE DE PAGO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("TIPO DE CAMBIO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(3);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("REFERENCIA DEL COMPROBANTE DE PAGO O DOCUMENTO ORIGINAL QUE SE MODIFICA", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(4);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            // Adicion celdas segundo nivel
//	            
//	            p = new Paragraph("TIPO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("N° SERIE O N° DE SERIE DE LA MAQUINA REGISTRADORA", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("NÚMERO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("DOCUMENTO DE IDENTIDAD", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(2);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("APELLIDOS Y NOMBRES, DENOMINACIÓN O RAZÓN SOCIAL", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            	            
//	            p = new Paragraph("EXONERADA", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("INAFECTA", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            	            
//	            p = new Paragraph("FECHA", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("TIPO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("SERIE", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("N° DEL COMPROBANTE DE PAGO O DOCUMENTO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(2);
//	            table.addCell(cell);
//	            
//	            // Adicion celdas tercer nivel
//	            
//	            p = new Paragraph("TIPO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph("NÚMERO", neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBackgroundColor(new BaseColor(242, 242, 242));
//	            cell.setColspan(1);
//	            cell.setRowspan(1);
//	            table.addCell(cell);
//
//	            document.add(table);
//	            
//				
//	            int row = 1;
//		        BigDecimal num_imp_afecto = BigDecimal.ZERO;
//		        BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//		        BigDecimal num_imp_igv = BigDecimal.ZERO;
//		        BigDecimal num_imp_isc = BigDecimal.ZERO;
//		        BigDecimal imp_det_total = BigDecimal.ZERO;	
//	            
//	            for (ReporteDetalleIngresosBean rep_ingreso : lista) {
//		        	
//		        	table = new PdfPTable(22); // 22 columnas
//	            	
//	            	p = new Paragraph("M".concat(String.valueOf(row)), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_ingreso.getFec_comprobante(), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_ingreso.getFec_vto(), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_ingreso.getVcod_comprobante(), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(rep_ingreso.getSer_comprobante(), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	               
//	                p = new Paragraph(StringUtils.stripStart(rep_ingreso.getNro_comprobante(), Constantes.ZERO_STRING), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                if (rep_ingreso.getCod_tip_doc_proveedor().equals(Constantes.ZERO_STRING) ||
//			        		isNullOrEmpty(rep_ingreso.getCod_tip_doc_proveedor())) {
//						p = new Paragraph(Constantes.EMPTY, nor_reg);					
//	    			} else {
//	    				p = new Paragraph(StringUtils.leftPad(rep_ingreso.getCod_tip_doc_proveedor(), 2, '0'), nor_reg);
//	    			}
//					cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(getString(rep_ingreso.getRuc_nro_doc_prov()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//	                
//	                p = new Paragraph(getString(rep_ingreso.getRaz_soc_prov()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(Constantes.EMPTY, nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					
//					BigDecimal imp_afecto = rep_ingreso.getNum_imp_afecto();
//			        if (rep_ingreso.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_afecto.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_afecto = imp_afecto.negate();
//			        }
//	                p = new Paragraph(dec_form.format(imp_afecto), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	                
//					p = new Paragraph(dec_form.format(BigDecimal.ZERO), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//					
//					BigDecimal imp_no_afecto = rep_ingreso.getNum_imp_no_afecto();
//			        if (rep_ingreso.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_no_afecto.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_no_afecto = imp_no_afecto.negate();
//			        }
//					p = new Paragraph(dec_form.format(imp_no_afecto), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//					
//					
//					BigDecimal imp_isc = rep_ingreso.getNum_imp_isc();
//			        if (rep_ingreso.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_isc.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_isc = imp_isc.negate();
//			        }
//					p = new Paragraph(dec_form.format(imp_isc), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//					
//					
//					BigDecimal imp_igv = rep_ingreso.getNum_imp_igv();
//			        if (rep_ingreso.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_igv.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_igv = imp_igv.negate();
//			        }
//	                p = new Paragraph(dec_form.format(imp_igv), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//					
//					
//					p = new Paragraph(Constantes.EMPTY, nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					
//					BigDecimal imp_total = rep_ingreso.getImp_det_total();
//			        if (rep_ingreso.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_total.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_total = imp_total.negate();
//			        }
//					p = new Paragraph(dec_form.format(imp_total), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//					
//
//					p = new Paragraph(getString(rep_ingreso.getNum_tip_cambio()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(getString(rep_ingreso.getFec_com_not_cre()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(getString(rep_ingreso.getTip_com_not_cre()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(getString(rep_ingreso.getSer_com_not_cre()), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//					p = new Paragraph(StringUtils.stripStart(getString(rep_ingreso.getNro_com_not_cre()), Constantes.ZERO_STRING), nor_reg);
//	                cell = new PdfPCell(p);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//					
//	                document.add(table);		 		        
//			        
//			        num_imp_afecto = num_imp_afecto.add(imp_afecto);
//		            num_imp_no_afecto = num_imp_no_afecto.add(imp_no_afecto);
//		            num_imp_igv = num_imp_igv.add(imp_igv);
//		            num_imp_isc = num_imp_isc.add(imp_isc);
//		            imp_det_total = imp_det_total.add(imp_total);
//		            
//		            row++;
//		        }
//		        
//				table = new PdfPTable(22); // 22 columnas 
//	            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell); 
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);            
//	            cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);     
//	                     
//	            p = new Paragraph("TOTALES", neg_reg);
//            	cell = new PdfPCell(p);
//            	cell.setBorder(Rectangle.NO_BORDER);
//            	cell.setBorderColorTop(BaseColor.BLACK);
//                cell.setBorderColorRight(BaseColor.BLACK); 
//                table.addCell(cell);
//                
//                p = new Paragraph(Constantes.EMPTY, neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(dec_form.format(num_imp_afecto), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//				
//				p = new Paragraph(dec_form.format(BigDecimal.ZERO), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//				p = new Paragraph(dec_form.format(num_imp_no_afecto), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//				
//				p = new Paragraph(dec_form.format(num_imp_isc), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//				
//	            p = new Paragraph(dec_form.format(num_imp_igv), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//	            p = new Paragraph(Constantes.EMPTY, neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//	            
//				p = new Paragraph(dec_form.format(imp_det_total), neg_reg);
//	            cell = new PdfPCell(p);
//	            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            table.addCell(cell);
//				
//
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//				cell.setBorderColorLeft(BaseColor.BLACK);
//	            table.addCell(cell);
//				
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//				
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//				
//				cell = new PdfPCell();
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            cell.setBorderColorTop(BaseColor.BLACK);
//	            table.addCell(cell);
//	            
//	            document.add(table);
//	            	            
//            }            
//        
//            document.close();
//            
//        } catch (Exception e) {
//        	log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//					  Constantes.NIVEL_APP_REPORTER, 
//					  this.getClass().getName(), e.getMessage()));
//        }   
//        return document;
//	}
//	
//	/**
//	 * @param response 
//	 * @param ingreso
//	 * @param lista
//	 */
//	public void generaExcelReporteIngresos(HttpServletResponse response, ComprasIngresosBean ingreso, List<ReporteDetalleIngresosBean> lista) {
//    	try{
//	    	HSSFWorkbook wb = new HSSFWorkbook();				
//	        HSSFSheet sheet = wb.createSheet("Comprobante de Pago");
//	        sheet.setColumnWidth(0, 1000);
//	        
//	        if (!ingreso.getTip_transaccion().equals(Constantes.SIX_STRING) &&
//	        		!ingreso.getTip_transaccion().equals(Constantes.SEVEN_STRING)) {
//	        	
//	        	sheet.setColumnWidth(1, 2000);
//	        	sheet.setColumnWidth(2, 4000);
//		        sheet.setColumnWidth(3, 5000);
//		        sheet.setColumnWidth(4, 5000);
//		        sheet.setColumnWidth(5, 6000);
//		        sheet.setColumnWidth(6, 6000);
//				sheet.setColumnWidth(7, 6000);
//				sheet.setColumnWidth(8, 8000);
//				sheet.setColumnWidth(9, 6000);
//				sheet.setColumnWidth(10, 6000);
//				sheet.setColumnWidth(11, 6000);
//				sheet.setColumnWidth(12, 4000);
//				sheet.setColumnWidth(13, 4000);
//				sheet.setColumnWidth(14, 4000);
//				sheet.setColumnWidth(15, 5000);
//				sheet.setColumnWidth(16, 5000);
//				sheet.setColumnWidth(17, 5000);
//				sheet.setColumnWidth(18, 5000);
//				sheet.setColumnWidth(19, 5000);
//				sheet.setColumnWidth(20, 5000);
//				sheet.setColumnWidth(21, 5000);
//				sheet.setColumnWidth(22, 5000);
//				sheet.setColumnWidth(22, 5000);
//				sheet.setColumnWidth(23, 5000);
//				sheet.setColumnWidth(24, 5000);
//				sheet.setColumnWidth(25, 5000);
//				sheet.setColumnWidth(26, 5000);
//				sheet.setColumnWidth(27, 5000);
//				sheet.setColumnWidth(28, 5000);
//				sheet.setColumnWidth(29, 5000);			
//				sheet.setColumnWidth(30, 5000);
//				sheet.setColumnWidth(31, 5000);
//				sheet.setColumnWidth(32, 5000);
//				sheet.setColumnWidth(33, 5000);
//				
//				sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 7));
//				sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 2));
//				sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 2));
//				
//	        } else if (ingreso.getTip_transaccion().equals(Constantes.SIX_STRING)) {
//	        	
//	        	sheet.setColumnWidth(1, 5000);
//	        	sheet.setColumnWidth(2, 4000);
//		        sheet.setColumnWidth(3, 3000);
//		        sheet.setColumnWidth(4, 4000);
//		        sheet.setColumnWidth(5, 7000);
//		        sheet.setColumnWidth(6, 4000);
//				sheet.setColumnWidth(7, 4000);
//				sheet.setColumnWidth(8, 4000);
//				sheet.setColumnWidth(9, 4000);
//				sheet.setColumnWidth(10, 4000);
//				sheet.setColumnWidth(11, 4000);
//				
//				sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 7));
//				sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 7));
//				sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 4));
//				sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 4));
//				
//	        } else if (ingreso.getTip_transaccion().equals(Constantes.SEVEN_STRING)) {
//
//	        	sheet.setColumnWidth(1, 5000);
//		        sheet.setColumnWidth(2, 5000);
//		        sheet.setColumnWidth(3, 5000);
//		        sheet.setColumnWidth(4, 4000);
//		        sheet.setColumnWidth(5, 5000);
//		        sheet.setColumnWidth(6, 5000);
//		        sheet.setColumnWidth(7, 6000);
//				sheet.setColumnWidth(8, 5000);
//				sheet.setColumnWidth(9, 5000);
//				sheet.setColumnWidth(10, 8000);
//				sheet.setColumnWidth(11, 5000);
//				sheet.setColumnWidth(12, 5000);
//				sheet.setColumnWidth(13, 5000);
//				sheet.setColumnWidth(14, 5000);
//				sheet.setColumnWidth(15, 5000);
//				sheet.setColumnWidth(16, 5000);
//				sheet.setColumnWidth(17, 5000);
//				sheet.setColumnWidth(18, 4000);
//				sheet.setColumnWidth(19, 5000);
//				sheet.setColumnWidth(20, 5000);
//				sheet.setColumnWidth(21, 5000);
//				sheet.setColumnWidth(22, 5000);
//
//
//				sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 7));
//				sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 7));
//				sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 4));
//				sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 4));
//
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 1, 1));
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 2, 2));
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 3, 3));
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 4, 6));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 4, 4));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 5, 5));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 6, 6));
//				
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 7, 9));
//				sheet.addMergedRegion(new CellRangeAddress(9, 9, 7, 8));
//				sheet.addMergedRegion(new CellRangeAddress(10, 10, 7, 7));
//				sheet.addMergedRegion(new CellRangeAddress(10, 10, 8, 8));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 9, 9));
//								
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 10, 10));
//
//
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 11, 11));
//				
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 12, 13));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 12, 12));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 13, 13));
//				
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 14, 14));
//
//
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 15, 15));
//				
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 16, 16));
//
//
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 17, 17));
//				
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 10, 18, 18));
//				
//				
//				sheet.addMergedRegion(new CellRangeAddress(8, 8, 19, 22));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 19, 19));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 20, 20));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 21, 21));
//				sheet.addMergedRegion(new CellRangeAddress(9, 10, 22, 22));				
//				
//	        }			
//			
//	        
//	        HSSFRow row0 = sheet.createRow((short) 0);
//			HSSFRow row1 = sheet.createRow((short) 1);
//			HSSFRow row2 = sheet.createRow((short) 2);
//	        HSSFRow row3 = sheet.createRow((short) 3);
//	        HSSFRow row4 = sheet.createRow((short) 4);
//	        HSSFRow row5 = sheet.createRow((short) 5);
//	        // HSSFRow row6 = sheet.createRow((short) 6);
//	        HSSFRow row7 = sheet.createRow((short) 7);
//	        HSSFRow row8 = sheet.createRow((short) 8);
//	        HSSFRow row9 = sheet.createRow((short) 9);
//	        HSSFRow row10 = sheet.createRow((short) 10);
//	        
//	        if (ingreso.getTip_transaccion().equals(Constantes.SEVEN_STRING)) { // Reporte de Ventas
//	        	row8.setHeight((short) 0x320);
//	        	row9.setHeight((short) 0x200);
//	        	row10.setHeight((short) 0x200);
//	        }
//	        
//	        
//	        HSSFFont font_bold = wb.createFont();
//	        font_bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//	        
//	        HSSFFont font_norm = wb.createFont();
//	        font_norm.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//	        
//	        DecimalFormatSymbols other_symbols = new DecimalFormatSymbols(Locale.US);
//            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, other_symbols);
//            
//	        
//	        HSSFCellStyle style_tit = (HSSFCellStyle) wb.createCellStyle();
//	        style_tit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style_tit.setFont(font_bold);
//	        
//	        
//	        HSSFCellStyle style_hed = (HSSFCellStyle) wb.createCellStyle();
//	        style_hed.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//	        style_hed.setFont(font_bold);
//	        
//	        
//	        
//	        if (!ingreso.getTip_transaccion().equals(Constantes.SIX_STRING) &&
//	        		!ingreso.getTip_transaccion().equals(Constantes.SEVEN_STRING)) { 
//	        	
//	        	row1.createCell(5).setCellValue("Registro de Comprobantes de Pago");	        
//	        	row1.getCell(5).setCellStyle(style_tit);
//	        	
//	        	
//	        	HSSFRichTextString uni_operativa = new HSSFRichTextString("Unidad Operativa : ");
//		        uni_operativa.applyFont(font_bold);
//		        row3.createCell(1).setCellValue(uni_operativa);
//		        String des_uni_operativa = ingreso.getDes_uni_operativa();
//	            if ((des_uni_operativa.trim()).length() == 0) {
//	            	des_uni_operativa = "Todos";
//	            }
//		        row3.createCell(3).setCellValue(des_uni_operativa);
//		        
//		        
//		        HSSFRichTextString anp = new HSSFRichTextString("ANP : ");
//		        anp.applyFont(font_bold);
//		        row4.createCell(1).setCellValue(anp);
//		        String des_anp = ingreso.getDes_reg_nac();
//	            if ((des_anp.trim()).length() == 0) {
//	            	des_anp = "Todos";
//	            }
//		        row4.createCell(3).setCellValue(des_anp);
//		        
//		        
//		        if (!isNullOrEmpty(ingreso.getFec_inicio()) && !isNullOrEmpty(ingreso.getFec_final())) {
//		        	
//		        	HSSFRichTextString fec_del = new HSSFRichTextString("Del  ");
//			        HSSFRichTextString fec_ini = new HSSFRichTextString(ingreso.getFec_inicio());
//			        HSSFRichTextString fec_al = new HSSFRichTextString("  al  ");
//			        HSSFRichTextString fec_fin = new HSSFRichTextString(ingreso.getFec_final());
//			        RichTextString fec_rango = new HSSFRichTextString(fec_del.getString() + fec_ini.getString() +
//							  										  fec_al.getString() + fec_fin.getString());
//			        fec_rango.applyFont(0, 3, font_bold);
//			        fec_rango.applyFont(5, 15, font_norm);
//			        fec_rango.applyFont(17, 19, font_bold);
//			        fec_rango.applyFont(21, 31, font_norm);
//			        row5.createCell(1).setCellValue(fec_rango);
//			        
//		        } else if (!isNullInteger(ingreso.getCod_mes())) {
//		        	
//		        	HSSFRichTextString periodo = new HSSFRichTextString("Periodo : ");
//		        	periodo.applyFont(font_bold);
//		        	row5.createCell(1).setCellValue(periodo);
//		 	        row5.createCell(3).setCellValue(getMes(ingreso.getCod_mes()));	
//		 	        
//		        }
//		        
//	        	
//	        } else {
//	        		        	
//	        	
//	        	String fec_sistema = "Fecha del sistema : ";
//            	String fec_actual = DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, new Date());
//	        	row0.createCell(1).setCellValue(fec_sistema.concat(fec_actual));	        
//	        	row0.getCell(1).setCellStyle(style_hed);
//	        	
//	        	
//	        	row1.createCell(5).setCellValue("REPORTE DE VENTAS");	        
//	        	row1.getCell(5).setCellStyle(style_tit);
//	        	
//	        	StringBuilder periodo = new StringBuilder();
//		        periodo.append(getMes(ingreso.getCod_mes()));
//		        periodo.append(Constantes.ESPACIO);
//		        periodo.append(DateUtil.getAnioActual());
//	        	row2.createCell(5).setCellValue(periodo.toString());	        
//	        	row2.getCell(5).setCellStyle(style_tit);
//	        	
//	        	
//	        	row3.createCell(1).setCellValue("SERNANP");	        
//	        	row3.getCell(1).setCellStyle(style_tit);
//	        	
//	        	
//	        	row4.createCell(1).setCellValue("Servicio Nacional de Areas Naturales Protegidas por el Estado");	        
//	        	row4.getCell(1).setCellStyle(style_tit);	        	
//	        	
//	        }
//
//	        HSSFCellStyle style_header = (HSSFCellStyle) wb.createCellStyle();
//	        style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style_header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style_header.setFont(font_bold);	        
//	        HSSFColor color = setColor(wb, (byte) 242, (byte)242, (byte) 242);
//	        style_header.setFillForegroundColor(color.getIndex());
//	        style_header.setFillPattern(CellStyle.SOLID_FOREGROUND);
//	        style_header.setBorderBottom((short) 1);
//	        style_header.setBorderLeft((short) 1);	        
//	        style_header.setBorderRight((short) 1);
//	        style_header.setBorderTop((short) 1);
//	        
//	        if (ingreso.getTip_transaccion().equals(Constantes.SEVEN_STRING)) { // Reporte de Compras
//	        	style_header.setWrapText(true);
//	        }	  
//	        
//	        HSSFCellStyle style_top = (HSSFCellStyle) wb.createCellStyle();
//	        style_top.setBorderTop((short) 1);
//	        
//	        HSSFCellStyle style_bottom = (HSSFCellStyle) wb.createCellStyle();
//	        style_bottom.setBorderBottom((short) 1);	  
//	        
//	        HSSFCellStyle style_left = (HSSFCellStyle) wb.createCellStyle();
//	        style_left.setBorderLeft((short) 1);
//	        
//	        HSSFCellStyle style_right = (HSSFCellStyle) wb.createCellStyle();
//	        style_right.setBorderRight((short) 1);
//	        
//	        
//            HSSFCellStyle style_cell = (HSSFCellStyle) wb.createCellStyle();
//            style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//            style_cell.setFont(font_norm);	        
//	        style_cell.setBorderBottom((short) 1);
//	        style_cell.setBorderLeft((short) 1);	        
//	        style_cell.setBorderRight((short) 1);
//	        style_cell.setBorderTop((short) 1);
//	        
//	        HSSFCellStyle style_total = (HSSFCellStyle) wb.createCellStyle();
//	        style_total.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//	        style_total.setFont(font_bold);	        
//	        style_total.setBorderBottom((short) 1);
//	        style_total.setBorderLeft((short) 1);	        
//	        style_total.setBorderRight((short) 1);
//	        style_total.setBorderTop((short) 1);
//	        
//	        HSSFCellStyle style_mont = (HSSFCellStyle) wb.createCellStyle();
//	        style_mont.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//	        style_mont.setFont(font_norm);	        
//	        style_mont.setBorderBottom((short) 1);
//	        style_mont.setBorderLeft((short) 1);	        
//	        style_mont.setBorderRight((short) 1);
//	        style_mont.setBorderTop((short) 1);
//	        
//	        HSSFCellStyle style_sub_mont = (HSSFCellStyle) wb.createCellStyle();
//	        style_sub_mont.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//	        style_sub_mont.setFont(font_bold);	        
//	        style_sub_mont.setBorderBottom((short) 1);
//	        style_sub_mont.setBorderLeft((short) 1);	        
//	        style_sub_mont.setBorderRight((short) 1);
//	        style_sub_mont.setBorderTop((short) 1);
//	        
//	        
//	        if (!ingreso.getTip_transaccion().equals(Constantes.SIX_STRING) &&
//	        		!ingreso.getTip_transaccion().equals(Constantes.SEVEN_STRING)) {        
//
//		        row7.createCell(1).setCellValue("Item");
//		        row7.getCell(1).setCellStyle(style_header);
//		        
//		        row7.createCell(2).setCellValue("Tip. C/P");
//		        row7.getCell(2).setCellStyle(style_header);
//		        
//		        row7.createCell(3).setCellValue("Serie y Nro C/P");
//		        row7.getCell(3).setCellStyle(style_header);
//		        
//		        row7.createCell(4).setCellValue("Periodo");
//		        row7.getCell(4).setCellStyle(style_header);
//		        
//		        row7.createCell(5).setCellValue("Fecha C/P");
//		        row7.getCell(5).setCellStyle(style_header);
//		        
//		        row7.createCell(6).setCellValue("Tipo Moneda");
//		        row7.getCell(6).setCellStyle(style_header);
//		        
//		        row7.createCell(7).setCellValue("Tipo Cambio");
//		        row7.getCell(7).setCellStyle(style_header);
//		        
//		        row7.createCell(8).setCellValue("Unid. Oper.");
//		        row7.getCell(8).setCellStyle(style_header);
//		        
//		        row7.createCell(9).setCellValue("ANP");
//		        row7.getCell(9).setCellStyle(style_header);
//		        
//		        row7.createCell(10).setCellValue("RUC");
//		        row7.getCell(10).setCellStyle(style_header);
//		        
//		        row7.createCell(11).setCellValue("Razon Social");
//		        row7.getCell(11).setCellStyle(style_header);
//		        
//		        int posicion = 11;
//	            if (!isNullOrEmpty(ingreso.getTip_transaccion())) {
//	            	if (ingreso.getTip_transaccion().equals(Constantes.ONE_STRING)) { // Servicio
//	            		row7.createCell(12).setCellValue("Servicio");
//	        	        row7.getCell(12).setCellStyle(style_header);
//	        	        
//	        	        row7.createCell(13).setCellValue("Dias");
//	        	        row7.getCell(13).setCellStyle(style_header);
//	        	        
//	        	        posicion = posicion + 2;
//					} else if (ingreso.getTip_transaccion().equals(Constantes.TWO_STRING)) { // Producto
//						row7.createCell(12).setCellValue("Producto");
//	        	        row7.getCell(12).setCellStyle(style_header);
//	        	        
//	        	        row7.createCell(13).setCellValue("Cantidad");
//	        	        row7.getCell(13).setCellStyle(style_header);
//	        	        
//	        	        posicion = posicion + 2;
//					} else if (ingreso.getTip_transaccion().equals(Constantes.THREE_STRING)) { // Boleto
//						row7.createCell(12).setCellValue("Serie Tarifa");
//	        	        row7.getCell(12).setCellStyle(style_header);
//						
//						row7.createCell(13).setCellValue("Tarifa");
//	        	        row7.getCell(13).setCellStyle(style_header);
//	        	        
//	        	        row7.createCell(14).setCellValue("Nro. Inicio");
//	        	        row7.getCell(14).setCellStyle(style_header);
//	        	        
//	        	        row7.createCell(15).setCellValue("Nro. Fin");
//	        	        row7.getCell(15).setCellStyle(style_header);
//	        	                	        
//	        	        row7.createCell(16).setCellValue("Cantidad");
//	        	        row7.getCell(16).setCellStyle(style_header);
//	        	        
//	        	        posicion = posicion + 5;
//					} else if (ingreso.getTip_transaccion().equals(Constantes.FOUR_STRING)) { //  Tupa
//						row7.createCell(12).setCellValue("TUPA");
//	        	        row7.getCell(12).setCellStyle(style_header);
//	        	        
//	        	        row7.createCell(13).setCellValue("Tarifa");
//	        	        row7.getCell(13).setCellStyle(style_header);
//	        	        
//	        	        row7.createCell(14).setCellValue("Cantidad");
//	        	        row7.getCell(14).setCellStyle(style_header);
//	        	        
//	        	        posicion = posicion + 3;
//					} else if (ingreso.getTip_transaccion().equals(Constantes.FIVE_STRING)) { // Otro
//						row7.createCell(12).setCellValue("Tarifa");
//	        	        row7.getCell(12).setCellStyle(style_header);
//	        	        
//	        	        row7.createCell(13).setCellValue("Cantidad");
//	        	        row7.getCell(13).setCellStyle(style_header);
//	        	        
//	        	        posicion = posicion + 2;
//					}
//	            } else {
//	            	row7.createCell(12).setCellValue("Servicio");
//	    	        row7.getCell(12).setCellStyle(style_header);
//	    	        
//	    	        row7.createCell(13).setCellValue("Producto");
//	    	        row7.getCell(13).setCellStyle(style_header);
//	    	        
//	    	        row7.createCell(14).setCellValue("TUPA");
//	    	        row7.getCell(14).setCellStyle(style_header);
//	    	        
//	    	        row7.createCell(15).setCellValue("Serie Tarifa");
//	    	        row7.getCell(15).setCellStyle(style_header);
//	    	        
//	    	        row7.createCell(16).setCellValue("Tarifa Boleto");
//	    	        row7.getCell(16).setCellStyle(style_header);
//	    	        
//	    	        row7.createCell(17).setCellValue("Nro. Inicio");
//	    	        row7.getCell(17).setCellStyle(style_header);
//	    	        
//	    	        row7.createCell(18).setCellValue("Nro. Fin");
//	    	        row7.getCell(18).setCellStyle(style_header);
//	    	        
//	    	        row7.createCell(19).setCellValue("Tarifa Tupa");
//	    	        row7.getCell(19).setCellStyle(style_header);
//	    	        
//	    	        row7.createCell(20).setCellValue("Tarifa Otros");
//	    	        row7.getCell(20).setCellStyle(style_header);
//	    	        
//	    	        row7.createCell(21).setCellValue("Cantidad");
//	    	        row7.getCell(21).setCellStyle(style_header);
//	    	        
//	    	        row7.createCell(22).setCellValue("Dias");
//	    	        row7.getCell(22).setCellStyle(style_header);
//	    	        
//	    	        posicion = posicion + 11;
//	            }
//	            
//	            row7.createCell(posicion + 1).setCellValue("Prec. Vta.");
//		        row7.getCell(posicion + 1).setCellStyle(style_header);
//	            
//		        row7.createCell(posicion + 2).setCellValue("Afecto");
//		        row7.getCell(posicion + 2).setCellStyle(style_header);
//	
//		        row7.createCell(posicion + 3).setCellValue("No Afecto");
//		        row7.getCell(posicion + 3).setCellStyle(style_header);
//	
//		        row7.createCell(posicion + 4).setCellValue("IGV");
//		        row7.getCell(posicion + 4).setCellStyle(style_header);
//	
//		        row7.createCell(posicion + 5).setCellValue("ISC");
//		        row7.getCell(posicion + 5).setCellStyle(style_header);
//	
//		        row7.createCell(posicion + 6).setCellValue("Total");
//		        row7.getCell(posicion + 6).setCellStyle(style_header);
//		        
//		        row7.createCell(posicion + 7).setCellValue("Cta. Cte");
//		        row7.getCell(posicion + 7).setCellStyle(style_header);
//		        
//		        row7.createCell(posicion + 8).setCellValue("Nro Depósito");
//		        row7.getCell(posicion + 8).setCellStyle(style_header);
//		        
//		        row7.createCell(posicion + 9).setCellValue("Fec. Depósito");
//		        row7.getCell(posicion + 9).setCellStyle(style_header);
//	
//		        row7.createCell(posicion + 10).setCellValue("Cta. Cble");
//		        row7.getCell(posicion + 10).setCellStyle(style_header); 
//	
//		        row7.createCell(posicion + 11).setCellValue("Part. Presupuestal");
//		        row7.getCell(posicion + 11).setCellStyle(style_header);
//		        
//		        
//	            int row = 1;
//	            BigDecimal num_imp_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_igv = BigDecimal.ZERO;
//	            BigDecimal num_imp_isc = BigDecimal.ZERO;
//	            BigDecimal imp_det_total = BigDecimal.ZERO;
//	    
//	            
//	            for (ReporteDetalleIngresosBean rep_ingreso : lista) {
//	            	
//	            	HSSFRow rows  = sheet.createRow((short) row + 7);
//	            	
//	            	rows.createCell(1).setCellValue(row);
//	    	        rows.getCell(1).setCellStyle(style_cell);
//	    	        
//	    	        rows.createCell(2).setCellValue(rep_ingreso.getTip_com_siglas());
//	    	        rows.getCell(2).setCellStyle(style_cell);
//	    	        
//	    	        String ser_nro = rep_ingreso.getSer_comprobante().concat(" - ");
//	    	        ser_nro = ser_nro.concat(rep_ingreso.getNro_comprobante());
//	    	        rows.createCell(3).setCellValue(ser_nro);
//	    	        rows.getCell(3).setCellStyle(style_cell);
//	    	            	        
//	    	        rows.createCell(4).setCellValue(rep_ingreso.getPeriodo());
//	    	        rows.getCell(4).setCellStyle(style_cell);    	        
//	    	        
//	    	        rows.createCell(5).setCellValue(rep_ingreso.getFec_comprobante());
//	    	        rows.getCell(5).setCellStyle(style_cell);
//	    	        
//	    	        rows.createCell(6).setCellValue(rep_ingreso.getTip_moneda());
//	    	        rows.getCell(6).setCellStyle(style_cell);
//	    	        
//	    	        rows.createCell(7).setCellValue(getString(rep_ingreso.getNum_tip_cambio()));
//	    	        rows.getCell(7).setCellStyle(style_cell);
//	    	        
//	    	        rows.createCell(8).setCellValue(rep_ingreso.getDes_uni_operativa());
//	    	        rows.getCell(8).setCellStyle(style_cell);
//	    	        
//	    	        rows.createCell(9).setCellValue(rep_ingreso.getDes_reg_nac());
//	    	        rows.getCell(9).setCellStyle(style_cell);
//	    	        
//	    	        rows.createCell(10).setCellValue(getString(rep_ingreso.getRuc_nro_doc_prov()));
//	    	        rows.getCell(10).setCellStyle(style_cell);
//	    	        
//	    	        rows.createCell(11).setCellValue(getString(rep_ingreso.getRaz_soc_prov()));
//	    	        rows.getCell(11).setCellStyle(style_cell);
//	            	
//	    	        int pos_cell = 11;
//	                if (!isNullOrEmpty(ingreso.getTip_transaccion())) {
//	                	if (ingreso.getTip_transaccion().equals(Constantes.ONE_STRING)) {
//	                		rows.createCell(12).setCellValue(rep_ingreso.getDes_servicio());
//	                		rows.getCell(12).setCellStyle(style_cell);
//	            	        
//	            	        rows.createCell(13).setCellValue(rep_ingreso.getCan_dias());
//	            	        rows.getCell(13).setCellStyle(style_cell);
//	            	        
//	            	        pos_cell = pos_cell + 2;
//	    				} else if (ingreso.getTip_transaccion().equals(Constantes.TWO_STRING)) {
//	    					rows.createCell(12).setCellValue(rep_ingreso.getNom_producto());
//	                        rows.getCell(12).setCellStyle(style_cell);
//	            	        
//	                        rows.createCell(13).setCellValue(rep_ingreso.getCan_producto());
//	                        rows.getCell(13).setCellStyle(style_cell);
//	            	        
//	            	        pos_cell = pos_cell + 2;
//	    				} else if (ingreso.getTip_transaccion().equals(Constantes.THREE_STRING)) { // Boletos
//	    					rows.createCell(12).setCellValue(rep_ingreso.getNum_ser_boleto());
//	                        rows.getCell(12).setCellStyle(style_cell);
//	    					
//	    					rows.createCell(13).setCellValue(rep_ingreso.getDes_boleto());
//	                        rows.getCell(13).setCellStyle(style_cell);
//	                        
//	                        rows.createCell(14).setCellValue(getString(rep_ingreso.getIni_boleto()));
//	                        rows.getCell(14).setCellStyle(style_cell);
//	                        
//	                        rows.createCell(15).setCellValue(getString(rep_ingreso.getFin_boleto()));
//	                        rows.getCell(15).setCellStyle(style_cell);
//	            	        
//	                        rows.createCell(16).setCellValue(rep_ingreso.getCan_producto());
//	                        rows.getCell(16).setCellStyle(style_cell);
//	            	        
//	            	        pos_cell = pos_cell + 5;
//	    				} else if (ingreso.getTip_transaccion().equals(Constantes.FOUR_STRING)) {
//	    					rows.createCell(12).setCellValue(rep_ingreso.getVcod_tupa());
//	                        rows.getCell(12).setCellStyle(style_cell);
//	    					
//	    					rows.createCell(13).setCellValue(rep_ingreso.getNom_tupa());
//	                        rows.getCell(13).setCellStyle(style_cell);
//	            	        
//	                        rows.createCell(14).setCellValue(rep_ingreso.getCan_producto());
//	                        rows.getCell(14).setCellStyle(style_cell);
//	            	        
//	            	        pos_cell = pos_cell + 3;
//	    				} else if (ingreso.getTip_transaccion().equals(Constantes.FIVE_STRING)) {
//	    					rows.createCell(12).setCellValue(rep_ingreso.getDes_otros());
//	                        rows.getCell(12).setCellStyle(style_cell);
//	            	        
//	                        rows.createCell(13).setCellValue(rep_ingreso.getCan_producto());
//	                        rows.getCell(13).setCellStyle(style_cell);
//	            	        
//	            	        pos_cell = pos_cell + 2;
//	    				}
//	                } else {
//	                	rows.createCell(12).setCellValue(getString(rep_ingreso.getDes_servicio()));
//	                    rows.getCell(12).setCellStyle(style_cell);
//	        	        
//	                    rows.createCell(13).setCellValue(getString(rep_ingreso.getNom_producto()));
//	                    rows.getCell(13).setCellStyle(style_cell);
//	                    
//	                    rows.createCell(14).setCellValue(rep_ingreso.getVcod_tupa());
//	                    rows.getCell(14).setCellStyle(style_cell);
//	                    
//	                    rows.createCell(15).setCellValue(getString(rep_ingreso.getNum_ser_boleto()));
//	                    rows.getCell(15).setCellStyle(style_cell);
//	                    
//	                    rows.createCell(16).setCellValue(getString(rep_ingreso.getDes_boleto()));
//	                    rows.getCell(16).setCellStyle(style_cell);
//	                    
//	                    rows.createCell(17).setCellValue(getString(rep_ingreso.getIni_boleto()));
//	                    rows.getCell(17).setCellStyle(style_cell);
//	                    
//	                    rows.createCell(18).setCellValue(getString(rep_ingreso.getFin_boleto()));
//	                    rows.getCell(18).setCellStyle(style_cell);
//	        	        
//	                    rows.createCell(19).setCellValue(getString(rep_ingreso.getNom_tupa()));
//	                    rows.getCell(19).setCellStyle(style_cell);
//	                    
//	                    rows.createCell(20).setCellValue(getString(rep_ingreso.getDes_otros()));
//	                    rows.getCell(20).setCellStyle(style_cell);
//	        	        
//	                    rows.createCell(21).setCellValue(getString(rep_ingreso.getCan_producto()));
//	                    rows.getCell(21).setCellStyle(style_cell);
//	                    
//	                    rows.createCell(22).setCellValue(getString(rep_ingreso.getCan_dias()));
//	                    rows.getCell(22).setCellStyle(style_cell);
//	                    
//	                    pos_cell = pos_cell + 11;
//	                }
//	
//	                rows.createCell(pos_cell + 1).setCellValue(dec_form.format(rep_ingreso.getNum_pre_unitario()));
//	    	        rows.getCell(pos_cell + 1).setCellStyle(style_mont);
//	                
//	    	        rows.createCell(pos_cell + 2).setCellValue(dec_form.format(rep_ingreso.getNum_imp_afecto()));
//	    	        rows.getCell(pos_cell + 2).setCellStyle(style_mont);
//	
//	    	        rows.createCell(pos_cell + 3).setCellValue(dec_form.format(rep_ingreso.getNum_imp_no_afecto()));
//	    	        rows.getCell(pos_cell + 3).setCellStyle(style_mont);
//	
//	    	        rows.createCell(pos_cell + 4).setCellValue(dec_form.format(rep_ingreso.getNum_imp_igv()));
//	    	        rows.getCell(pos_cell + 4).setCellStyle(style_mont);
//	
//	    	        rows.createCell(pos_cell + 5).setCellValue(dec_form.format(rep_ingreso.getNum_imp_isc()));
//	    	        rows.getCell(pos_cell + 5).setCellStyle(style_mont);
//	
//	    	        rows.createCell(pos_cell + 6).setCellValue(dec_form.format(rep_ingreso.getImp_det_total()));
//	    	        rows.getCell(pos_cell + 6).setCellStyle(style_mont);
//	    	        
//	    	        rows.createCell(pos_cell + 7).setCellValue(getString(rep_ingreso.getNum_cta_cte()));
//	    	        rows.getCell(pos_cell + 7).setCellStyle(style_cell);
//	    	        
//	    	        rows.createCell(pos_cell + 8).setCellValue(getString(rep_ingreso.getNum_voucher()));
//	    	        rows.getCell(pos_cell + 8).setCellStyle(style_cell);
//	    	        
//	    	        rows.createCell(pos_cell + 9).setCellValue(getString(rep_ingreso.getFec_voucher()));
//	    	        rows.getCell(pos_cell + 9).setCellStyle(style_cell);
//	    	        
//	    	        rows.createCell(pos_cell + 10).setCellValue(getString(rep_ingreso.getCod_cuenta()));
//	    	        rows.getCell(pos_cell + 10).setCellStyle(style_cell);
//	    	        
//	    	        rows.createCell(pos_cell + 11).setCellValue(getString(rep_ingreso.getCod_especifica()));
//	    	        rows.getCell(pos_cell + 11).setCellStyle(style_cell);
//	    	        
//	    	                        
//	                num_imp_afecto = num_imp_afecto.add(rep_ingreso.getNum_imp_afecto());
//	                num_imp_no_afecto = num_imp_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//	                num_imp_igv = num_imp_igv.add(rep_ingreso.getNum_imp_igv());
//	                num_imp_isc = num_imp_isc.add(rep_ingreso.getNum_imp_isc());
//	                imp_det_total = imp_det_total.add(rep_ingreso.getImp_det_total());
//		            
//		            row++;	
//	            }
//		        
//		        
//		        HSSFCellStyle style_tot_mont = (HSSFCellStyle) wb.createCellStyle();
//		        style_tot_mont.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//		        style_tot_mont.setFont(font_bold);	        
//		        style_tot_mont.setBorderBottom((short) 1);
//		        style_tot_mont.setBorderLeft((short) 1);	        
//		        style_tot_mont.setBorderRight((short) 1);
//		        style_tot_mont.setBorderTop((short) 1);
//		        
//		        
//	            HSSFRow row_total  = sheet.createRow((short) row + 7);
//	            int pos_cell_tot = 0;
//	            if (!isNullOrEmpty(ingreso.getTip_transaccion())) {
//	            	if (ingreso.getTip_transaccion().equals(Constantes.THREE_STRING)) { // Boletos
//	            		pos_cell_tot = 17;
//	            	} else if (ingreso.getTip_transaccion().equals(Constantes.FOUR_STRING)) { // Tupa
//	            		pos_cell_tot = 15;
//	            	} else {
//	            		pos_cell_tot = 14;
//	            	}
//	            } else {
//	            	pos_cell_tot = 23;
//	            }
//	            
//	            HSSFRichTextString total = new HSSFRichTextString("Totales:");
//	            total.applyFont(font_bold);
//	            row_total.createCell(pos_cell_tot).setCellValue(total);
//	            
//	            row_total.createCell(pos_cell_tot + 1).setCellValue(dec_form.format(num_imp_afecto));
//	            row_total.getCell(pos_cell_tot + 1).setCellStyle(style_tot_mont);
//	            
//	            row_total.createCell(pos_cell_tot + 2).setCellValue(dec_form.format(num_imp_no_afecto));
//	            row_total.getCell(pos_cell_tot + 2).setCellStyle(style_tot_mont);
//	            
//	            row_total.createCell(pos_cell_tot + 3).setCellValue(dec_form.format(num_imp_igv));
//	            row_total.getCell(pos_cell_tot + 3).setCellStyle(style_tot_mont);
//	            
//	            row_total.createCell(pos_cell_tot + 4).setCellValue(dec_form.format(num_imp_isc));
//	            row_total.getCell(pos_cell_tot + 4).setCellStyle(style_tot_mont);
//	            
//	            row_total.createCell(pos_cell_tot + 5).setCellValue(dec_form.format(imp_det_total));
//	            row_total.getCell(pos_cell_tot + 5).setCellStyle(style_tot_mont);
//	            
//	        } else if (ingreso.getTip_transaccion().equals(Constantes.SIX_STRING)) { // Registro de Ventas
//	        	
//	        	row7.createCell(1).setCellValue("N° Documento");
//		        row7.getCell(1).setCellStyle(style_header);
//		        
//		        row7.createCell(2).setCellValue("Fecha");
//		        row7.getCell(2).setCellStyle(style_header);
//		        
//		        row7.createCell(3).setCellValue("Doc");
//		        row7.getCell(3).setCellStyle(style_header);
//		        
//		        row7.createCell(4).setCellValue("Num");
//		        row7.getCell(4).setCellStyle(style_header);
//		        
//		        row7.createCell(5).setCellValue("Nombre");
//		        row7.getCell(5).setCellStyle(style_header);
//	        	
//		        row7.createCell(6).setCellValue("Afecto");
//		        row7.getCell(6).setCellStyle(style_header);
//	
//		        row7.createCell(7).setCellValue("No Afecto");
//		        row7.getCell(7).setCellStyle(style_header);
//	
//		        row7.createCell(8).setCellValue("IGV");
//		        row7.getCell(8).setCellStyle(style_header);
//	
//		        row7.createCell(9).setCellValue("ISC");
//		        row7.getCell(9).setCellStyle(style_header);
//	
//		        row7.createCell(10).setCellValue("Total (S/.)");
//		        row7.getCell(10).setCellStyle(style_header);
//	        	
//		        row7.createCell(11).setCellValue("Total (US$)");
//		        row7.getCell(11).setCellStyle(style_header);
//		        
//		        
//		        String unidad = "";
//		        String documento = "";
//		        if (!isEmpty(lista)) {
//		        	unidad = lista.get(0).getDes_uni_operativa();
//		        	documento = lista.get(0).getTip_comprobante();
//		        }
//		        
//		        String tip_operacion = ingreso.getTip_operacion();
//		        
//		        List<ReporteDetalleIngresosBean> lis_uni_ope = null;
//		        List<ReporteDetalleIngresosBean> lis_sede = null;		        
//
//		        if (tip_operacion.equals(Constantes.TWO_STRING)) { // Sede Central
//		        	lis_uni_ope = new ArrayList<ReporteDetalleIngresosBean>();
//		        	lis_sede = new ArrayList<ReporteDetalleIngresosBean>();
//		        	for (ReporteDetalleIngresosBean rep : lista) {
//		        		if (rep.getTip_operacion().equals(Constantes.TWO_STRING)) { // Sede Central
//		        			lis_sede.add(rep);
//		        		} else {
//		        			lis_uni_ope.add(rep);
//		        		}			        		
//		        	}
//		        	lista.clear();
//		        	lista.addAll(lis_sede);
//
//		        	row8.createCell(1).setCellValue("SERNANP - SEDE CENTRAL");
//		        	row8.getCell(1).setCellStyle(style_hed);
//	    	      	    	        
//		        } else { // Unidad Operativa
//		        
//		        	row8.createCell(1).setCellValue(unidad);
//		        	row8.getCell(1).setCellStyle(style_hed);
//		        	
//		        }
//		        
//		        int row = 9;
//		        boolean pri = true;
//		        boolean sub = false;
//		        int can = 0;
//		        int ult = lista.size() - 1;
//		        HSSFRow rows = null;
//		        BigDecimal num_imp_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_igv = BigDecimal.ZERO;
//	            BigDecimal num_imp_isc = BigDecimal.ZERO;
//	            BigDecimal imp_det_total = BigDecimal.ZERO;
//	            BigDecimal imp_det_dol_total = BigDecimal.ZERO;
//	            
//	            BigDecimal num_imp_tot_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_no_afecto = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_igv = BigDecimal.ZERO;
//	            BigDecimal num_imp_tot_isc = BigDecimal.ZERO;
//	            BigDecimal imp_total = BigDecimal.ZERO;
//	            BigDecimal imp_dol_total = BigDecimal.ZERO;
//	            
//		        for (ReporteDetalleIngresosBean rep_ingreso : lista) {
//		        	         		            	
//	            	if (!documento.equals(rep_ingreso.getTip_comprobante()) && !pri) {
//	            		rows = sheet.createRow((short) row);
//	            		rows.createCell(5).setCellValue("Sub-Total: ");
//			    	    rows.getCell(5).setCellStyle(style_total); 
//			    	    
//	            		sub = true;	            		
//	            	} else if (pri) {
//	            		rows = sheet.createRow((short) row);
//	            		rows.createCell(1).setCellValue("Documento: ".concat(documento.toUpperCase()));
//			    	    rows.getCell(1).setCellStyle(style_hed);	            		
//	            		row++;
//	            		pri = false;
//	            	}
//	            	
//	            	if (!sub) {	            	
//	            		rows = sheet.createRow((short) row);
//	            	} else {
//	            		
//	            		rows.createCell(6).setCellValue(dec_form.format(num_imp_afecto));
//			    	    rows.getCell(6).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_no_afecto));
//			    	    rows.getCell(7).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_igv));
//			    	    rows.getCell(8).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_isc));
//			    	    rows.getCell(9).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(10).setCellValue(dec_form.format(imp_det_total));
//			    	    rows.getCell(10).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(11).setCellValue(dec_form.format(imp_det_dol_total));
//			    	    rows.getCell(11).setCellStyle(style_sub_mont);
//			    	    	            		
//			    	    
//			    	    num_imp_afecto = BigDecimal.ZERO;
//			            num_imp_no_afecto = BigDecimal.ZERO;
//			            num_imp_igv = BigDecimal.ZERO;
//			            num_imp_isc = BigDecimal.ZERO;
//			            imp_det_total = BigDecimal.ZERO;
//			            imp_det_dol_total = BigDecimal.ZERO;
//			    	    
//	            		sub = false;
//	            		
//			        	if (tip_operacion.equals(Constantes.ONE_STRING)) { // Unidad Operativa
//			        		if (!unidad.equals(rep_ingreso.getDes_uni_operativa())) {
//		                				                		
//		                		unidad = rep_ingreso.getDes_uni_operativa();
//		                		
//		                		row = row + 1;
//		                		
//		                		rows = sheet.createRow((short) row);
//			            		rows.createCell(5).setCellValue("Total: ");
//					    	    rows.getCell(5).setCellStyle(style_total); 
//				    	    	
//					    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_tot_afecto));
//					    	    rows.getCell(6).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_tot_no_afecto));
//					    	    rows.getCell(7).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_tot_igv));
//					    	    rows.getCell(8).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_tot_isc));
//					    	    rows.getCell(9).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(10).setCellValue(dec_form.format(imp_total));
//					    	    rows.getCell(10).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(11).setCellValue(dec_form.format(imp_dol_total));
//					    	    rows.getCell(11).setCellStyle(style_sub_mont);
//					    	    
//		                		
//					    	    num_imp_tot_afecto = BigDecimal.ZERO;
//					            num_imp_tot_no_afecto = BigDecimal.ZERO;
//					            num_imp_tot_igv = BigDecimal.ZERO;
//					            num_imp_tot_isc = BigDecimal.ZERO;
//					            imp_total = BigDecimal.ZERO;
//					            imp_dol_total = BigDecimal.ZERO;
//		                		
//					            
//					            row = row + 2;
//					            
//					            rows = sheet.createRow((short) row);
//					            rows.createCell(1).setCellValue(unidad);
//					            rows.getCell(1).setCellStyle(style_hed);
//					            
//					            row = row + 1;
//		                		
//		                	} else {
//		                		
//		                		row = row + 2;
//		                		
//		                	}
//			        		
//			        	} else { // Sede Central	
//			        		
//			        		row = row + 2;
//			        		
//			        	}
//	            		
//	            		
//	            		rows = sheet.createRow((short) row);
//	            		documento = rep_ingreso.getTip_comprobante();
//	            		rows.createCell(1).setCellValue("Documento: ".concat(documento.toUpperCase()));
//			    	    rows.getCell(1).setCellStyle(style_hed);
//
//			    	    row = row + 1;
//	            		
//	            		rows = sheet.createRow((short) row);
//	            	}
//	            		
//	            	
//	            	String ser_nro = rep_ingreso.getSer_comprobante().concat(" - ");
//		    	    ser_nro = ser_nro.concat(rep_ingreso.getNro_comprobante());
//		    	    rows.createCell(1).setCellValue(ser_nro);
//		    	    rows.getCell(1).setCellStyle(style_cell);
//		        
//		    	    rows.createCell(2).setCellValue(rep_ingreso.getFec_comprobante());
//		    	    rows.getCell(2).setCellStyle(style_cell);
//		    	     
//		    	    rows.createCell(3).setCellValue(rep_ingreso.getTip_documento());
//		    	    rows.getCell(3).setCellStyle(style_cell);
//		    	     
//		    	    rows.createCell(4).setCellValue(getString(rep_ingreso.getRuc_nro_doc_prov()));
//		    	    rows.getCell(4).setCellStyle(style_cell);
//		    	     
//		    	    rows.createCell(5).setCellValue(getString(rep_ingreso.getRaz_soc_prov()));
//		    	    rows.getCell(5).setCellStyle(style_cell);
//		    	     
//		    	    rows.createCell(6).setCellValue(dec_form.format(rep_ingreso.getNum_imp_afecto()));
//		    	    rows.getCell(6).setCellStyle(style_mont);
//		    	     
//		    	    rows.createCell(7).setCellValue(dec_form.format(rep_ingreso.getNum_imp_no_afecto()));
//		    	    rows.getCell(7).setCellStyle(style_mont);
//		    	     
//		    	    rows.createCell(8).setCellValue(dec_form.format(rep_ingreso.getNum_imp_igv()));
//		    	    rows.getCell(8).setCellStyle(style_mont);
//		    	     
//		    	    rows.createCell(9).setCellValue(dec_form.format(rep_ingreso.getNum_imp_isc()));
//		    	    rows.getCell(9).setCellStyle(style_mont);
//		    	    
//		    	    if (rep_ingreso.getCod_moneda().equals(Constantes.ONE_INT)) { // Soles
//		    	    	
//		    	    	rows.createCell(10).setCellValue(dec_form.format(rep_ingreso.getImp_det_total()));
//			    	    rows.getCell(10).setCellStyle(style_mont);
//			    	    
//			    	    rows.createCell(11).setCellValue(dec_form.format(BigDecimal.ZERO));
//			    	    rows.getCell(11).setCellStyle(style_mont);
//			    	    
//			    	    imp_det_total = imp_det_total.add(rep_ingreso.getImp_det_total());
//			    	    imp_total = imp_total.add(rep_ingreso.getImp_det_total());
//			    	    
//		    	    } else { // Dolares
//		    	    	
//		    	    	if (rep_ingreso.getNum_tip_cambio() != null && rep_ingreso.getNum_tip_cambio().compareTo(BigDecimal.ZERO) > 0) {
//		    	    		
//				    	    rows.createCell(10).setCellValue(dec_form.format(rep_ingreso.getImp_det_total()));
//				    	    rows.getCell(10).setCellStyle(style_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(rep_ingreso.getImp_det_total_dolares()));
//				    	    rows.getCell(11).setCellStyle(style_mont);
//				    	    
//				    	    imp_det_total = imp_det_total.add(rep_ingreso.getImp_det_total());
//				    	    imp_total = imp_total.add(rep_ingreso.getImp_det_total());
//				    	    
//				    	    imp_det_dol_total = imp_det_dol_total.add(rep_ingreso.getImp_det_total_dolares());
//				    	    imp_dol_total = imp_dol_total.add(rep_ingreso.getImp_det_total_dolares());
//		    	    		
//		    	    	} else {
//		    	    		
//		    	    		rows.createCell(10).setCellValue(dec_form.format(BigDecimal.ZERO));
//				    	    rows.getCell(10).setCellStyle(style_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(rep_ingreso.getImp_det_total()));
//				    	    rows.getCell(11).setCellStyle(style_mont);
//				    	    
//				    	    imp_det_dol_total = imp_det_dol_total.add(rep_ingreso.getImp_det_total());
//				    	    imp_dol_total = imp_dol_total.add(rep_ingreso.getImp_det_total());
//		    	    		
//		    	    	}
//
//		    	    }
//		    	    		    	  
//		    	    
//		    	    num_imp_afecto = num_imp_afecto.add(rep_ingreso.getNum_imp_afecto());
//	                num_imp_no_afecto = num_imp_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//	                num_imp_igv = num_imp_igv.add(rep_ingreso.getNum_imp_igv());
//	                num_imp_isc = num_imp_isc.add(rep_ingreso.getNum_imp_isc());	                
//	                
//
//                	num_imp_tot_afecto = num_imp_tot_afecto.add(rep_ingreso.getNum_imp_afecto());
//	                num_imp_tot_no_afecto = num_imp_tot_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//	                num_imp_tot_igv = num_imp_tot_igv.add(rep_ingreso.getNum_imp_igv());
//	                num_imp_tot_isc = num_imp_tot_isc.add(rep_ingreso.getNum_imp_isc());
//	                
//	                		    	    
//		    	    row++;
//		    	    
//		    	    if (can == ult) { // Validacion ultimo registro
//		    	    	rows = sheet.createRow((short) row);
//	            		rows.createCell(5).setCellValue("Sub-Total: ");
//			    	    rows.getCell(5).setCellStyle(style_total); 
//		    	    	
//			    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_afecto));
//			    	    rows.getCell(6).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_no_afecto));
//			    	    rows.getCell(7).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_igv));
//			    	    rows.getCell(8).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_isc));
//			    	    rows.getCell(9).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(10).setCellValue(dec_form.format(imp_det_total));
//			    	    rows.getCell(10).setCellStyle(style_sub_mont);
//			    
//			    	    rows.createCell(11).setCellValue(dec_form.format(imp_det_dol_total));
//			    	    rows.getCell(11).setCellStyle(style_sub_mont);
//			    	   
//			    	    
//			    	    row = row + 1;
//			    	 
//	            		rows = sheet.createRow((short) row);
//	            		rows.createCell(5).setCellValue("Total: ");
//			    	    rows.getCell(5).setCellStyle(style_total); 
//		    	    	
//			    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_tot_afecto));
//			    	    rows.getCell(6).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_tot_no_afecto));
//			    	    rows.getCell(7).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_tot_igv));
//			    	    rows.getCell(8).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_tot_isc));
//			    	    rows.getCell(9).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(10).setCellValue(dec_form.format(imp_total));
//			    	    rows.getCell(10).setCellStyle(style_sub_mont);
//			    	    
//			    	    rows.createCell(11).setCellValue(dec_form.format(imp_dol_total));
//			    	    rows.getCell(11).setCellStyle(style_sub_mont);			    	   			    	 
//			    	    
//		    	    }		    	    
//		    	    
//		    	    can++;
//		        }
//		        
//		        
//		        if (tip_operacion.equals(Constantes.TWO_STRING)) { // Sede Central
//		        	
//		        	unidad = "";
//			        documento = "";
//			        String are_nat_pro = "";
//			        if (!isEmpty(lis_uni_ope)) {
//			        	unidad = lis_uni_ope.get(0).getDes_uni_operativa();
//			        	are_nat_pro = lis_uni_ope.get(0).getDes_reg_nac().toUpperCase();
//			        	documento = lis_uni_ope.get(0).getTip_comprobante();
//			        }
//
//			        row = row + 3;
//
//			        
//			        rows = sheet.createRow((short) row);
//			        rows.createCell(1).setCellValue(are_nat_pro);
//			        rows.getCell(1).setCellStyle(style_hed);
//			        
//			        
//			        row = row + 1;
//			        
//			        
//			        pri = true;
//			        sub = false;
//			        can = 0;
//			        ult = lis_uni_ope.size() - 1;
//			        rows = null;
//			        num_imp_afecto = BigDecimal.ZERO;
//		            num_imp_no_afecto = BigDecimal.ZERO;
//		            num_imp_igv = BigDecimal.ZERO;
//		            num_imp_isc = BigDecimal.ZERO;
//		            imp_det_total = BigDecimal.ZERO;
//		            imp_det_dol_total = BigDecimal.ZERO;
//		            
//		            num_imp_tot_afecto = BigDecimal.ZERO;
//		            num_imp_tot_no_afecto = BigDecimal.ZERO;
//		            num_imp_tot_igv = BigDecimal.ZERO;
//		            num_imp_tot_isc = BigDecimal.ZERO;
//		            imp_total = BigDecimal.ZERO;
//		            imp_dol_total = BigDecimal.ZERO;
//		            
//		            BigDecimal imp_tot_gen_afecto = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_no_afecto = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_igv = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_isc = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_soles = BigDecimal.ZERO;
//		            BigDecimal imp_tot_gen_dolares = BigDecimal.ZERO;
//		        	
//		        	for (ReporteDetalleIngresosBean rep_ingreso : lis_uni_ope) {
//		        		
//		            	if ((!documento.equals(rep_ingreso.getTip_comprobante()) && !pri) || 
//		            			!are_nat_pro.equals(rep_ingreso.getDes_reg_nac().toUpperCase())) {
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Sub-Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//				    	    
//		            		sub = true;	            		
//		            	} else if (pri) {
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(1).setCellValue("Documento: ".concat(documento.toUpperCase()));
//				    	    rows.getCell(1).setCellStyle(style_hed);	            		
//		            		row++;
//		            		pri = false;
//		            	}
//		            	
//		            	if (!sub) {	            	
//		            		rows = sheet.createRow((short) row);
//		            	} else {
//		            		
//		            		rows.createCell(6).setCellValue(dec_form.format(num_imp_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_det_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_det_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);
//				    	    	            		
//				    	    
//				    	    num_imp_afecto = BigDecimal.ZERO;
//				            num_imp_no_afecto = BigDecimal.ZERO;
//				            num_imp_igv = BigDecimal.ZERO;
//				            num_imp_isc = BigDecimal.ZERO;
//				            imp_det_total = BigDecimal.ZERO;
//				            imp_det_dol_total = BigDecimal.ZERO;
//				    	    
//		            		sub = false;
//		            		
//		            		
//			        		if (!are_nat_pro.equals(rep_ingreso.getDes_reg_nac().toUpperCase())) {
//			        			
//			        			
//			        			row = row + 1;
//			        			
//			        			rows = sheet.createRow((short) row);
//			            		rows.createCell(5).setCellValue("Total: ");
//					    	    rows.getCell(5).setCellStyle(style_total); 
//				    	    	
//					    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_tot_afecto));
//					    	    rows.getCell(6).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_tot_no_afecto));
//					    	    rows.getCell(7).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_tot_igv));
//					    	    rows.getCell(8).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_tot_isc));
//					    	    rows.getCell(9).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(10).setCellValue(dec_form.format(imp_total));
//					    	    rows.getCell(10).setCellStyle(style_sub_mont);
//					    	    
//					    	    rows.createCell(11).setCellValue(dec_form.format(imp_dol_total));
//					    	    rows.getCell(11).setCellStyle(style_sub_mont);
//					    	   
//		                		
//					    	    num_imp_tot_afecto = BigDecimal.ZERO;
//					            num_imp_tot_no_afecto = BigDecimal.ZERO;
//					            num_imp_tot_igv = BigDecimal.ZERO;
//					            num_imp_tot_isc = BigDecimal.ZERO;
//					            imp_total = BigDecimal.ZERO;
//					            imp_dol_total = BigDecimal.ZERO;
//					            
//					            
//					            row = row + 2;
//					            
//			        			
//			        			are_nat_pro = rep_ingreso.getDes_reg_nac().toUpperCase();
//			        			
//			        			rows = sheet.createRow((short) row);
//			            		rows.createCell(1).setCellValue(are_nat_pro);
//					    	    rows.getCell(1).setCellStyle(style_hed);
//
//					    	    row = row + 1;
//					    	    
//			        		} else {
//			        			
//			        			row = row + 2;
//			        			
//			        		}
//
//		            		
//		            		rows = sheet.createRow((short) row);
//		            		documento = rep_ingreso.getTip_comprobante();
//		            		rows.createCell(1).setCellValue("Documento: ".concat(documento.toUpperCase()));
//				    	    rows.getCell(1).setCellStyle(style_hed);
//
//				    	    row = row + 1;
//		            		
//		            		rows = sheet.createRow((short) row);
//		            	}
//		            		
//		            	
//		            	String ser_nro = rep_ingreso.getSer_comprobante().concat(" - ");
//			    	    ser_nro = ser_nro.concat(rep_ingreso.getNro_comprobante());
//			    	    rows.createCell(1).setCellValue(ser_nro);
//			    	    rows.getCell(1).setCellStyle(style_cell);
//			        
//			    	    rows.createCell(2).setCellValue(rep_ingreso.getFec_comprobante());
//			    	    rows.getCell(2).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(3).setCellValue(rep_ingreso.getTip_documento());
//			    	    rows.getCell(3).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(4).setCellValue(getString(rep_ingreso.getRuc_nro_doc_prov()));
//			    	    rows.getCell(4).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(5).setCellValue(getString(rep_ingreso.getRaz_soc_prov()));
//			    	    rows.getCell(5).setCellStyle(style_cell);
//			    	     
//			    	    rows.createCell(6).setCellValue(dec_form.format(rep_ingreso.getNum_imp_afecto()));
//			    	    rows.getCell(6).setCellStyle(style_mont);
//			    	     
//			    	    rows.createCell(7).setCellValue(dec_form.format(rep_ingreso.getNum_imp_no_afecto()));
//			    	    rows.getCell(7).setCellStyle(style_mont);
//			    	     
//			    	    rows.createCell(8).setCellValue(dec_form.format(rep_ingreso.getNum_imp_igv()));
//			    	    rows.getCell(8).setCellStyle(style_mont);
//			    	     
//			    	    rows.createCell(9).setCellValue(dec_form.format(rep_ingreso.getNum_imp_isc()));
//			    	    rows.getCell(9).setCellStyle(style_mont);
//			    	    
//			    	    if (rep_ingreso.getCod_moneda().equals(Constantes.ONE_INT)) { // Soles
//			    	    	
//			    	    	rows.createCell(10).setCellValue(dec_form.format(rep_ingreso.getImp_det_total()));
//				    	    rows.getCell(10).setCellStyle(style_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(BigDecimal.ZERO));
//				    	    rows.getCell(11).setCellStyle(style_mont);
//				    	    
//				    	    imp_det_total = imp_det_total.add(rep_ingreso.getImp_det_total());
//				    	    imp_total = imp_total.add(rep_ingreso.getImp_det_total());
//				    	    imp_tot_gen_soles = imp_tot_gen_soles.add(rep_ingreso.getImp_det_total());
//				    	    
//			    	    } else { // Dolares
//			    	    	
//			    	    	if (rep_ingreso.getNum_tip_cambio() != null && rep_ingreso.getNum_tip_cambio().compareTo(BigDecimal.ZERO) > 0) {
//			    	    		
//					    	    rows.createCell(10).setCellValue(dec_form.format(rep_ingreso.getImp_det_total()));
//					    	    rows.getCell(10).setCellStyle(style_mont);
//					    	    
//					    	    rows.createCell(11).setCellValue(dec_form.format(rep_ingreso.getImp_det_total_dolares()));
//					    	    rows.getCell(11).setCellStyle(style_mont);
//					    	    
//					    	    imp_det_total = imp_det_total.add(rep_ingreso.getImp_det_total());
//					    	    imp_total = imp_total.add(rep_ingreso.getImp_det_total());
//					    	    imp_tot_gen_soles = imp_tot_gen_soles.add(rep_ingreso.getImp_det_total());
//					    	    
//					    	    imp_det_dol_total = imp_det_dol_total.add(rep_ingreso.getImp_det_total_dolares());
//					    	    imp_dol_total = imp_dol_total.add(rep_ingreso.getImp_det_total_dolares());
//					    	    imp_tot_gen_dolares = imp_tot_gen_dolares.add(rep_ingreso.getImp_det_total_dolares());
//			    	    		
//			    	    	} else {
//			    	    		
//			    	    		rows.createCell(10).setCellValue(dec_form.format(BigDecimal.ZERO));
//					    	    rows.getCell(10).setCellStyle(style_mont);
//					    	    
//					    	    rows.createCell(11).setCellValue(dec_form.format(rep_ingreso.getImp_det_total()));
//					    	    rows.getCell(11).setCellStyle(style_mont);
//					    	    
//					    	    imp_det_dol_total = imp_det_dol_total.add(rep_ingreso.getImp_det_total());
//					    	    imp_dol_total = imp_dol_total.add(rep_ingreso.getImp_det_total());
//					    	    imp_tot_gen_dolares = imp_tot_gen_dolares.add(rep_ingreso.getImp_det_total());
//			    	    		
//			    	    	}
//			    	    	
//			    	    }
//			    	    		    	  
//			    	    
//			    	    num_imp_afecto = num_imp_afecto.add(rep_ingreso.getNum_imp_afecto());
//		                num_imp_no_afecto = num_imp_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//		                num_imp_igv = num_imp_igv.add(rep_ingreso.getNum_imp_igv());
//		                num_imp_isc = num_imp_isc.add(rep_ingreso.getNum_imp_isc());	                
//		                
//
//	                	num_imp_tot_afecto = num_imp_tot_afecto.add(rep_ingreso.getNum_imp_afecto());
//		                num_imp_tot_no_afecto = num_imp_tot_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//		                num_imp_tot_igv = num_imp_tot_igv.add(rep_ingreso.getNum_imp_igv());
//		                num_imp_tot_isc = num_imp_tot_isc.add(rep_ingreso.getNum_imp_isc());
//		                		                
//		                
//		                imp_tot_gen_afecto = imp_tot_gen_afecto.add(rep_ingreso.getNum_imp_afecto());
//		                imp_tot_gen_no_afecto = imp_tot_gen_no_afecto.add(rep_ingreso.getNum_imp_no_afecto());
//		                imp_tot_gen_igv = imp_tot_gen_igv.add(rep_ingreso.getNum_imp_igv());
//		                imp_tot_gen_isc = imp_tot_gen_isc.add(rep_ingreso.getNum_imp_isc());
//		                
//	    	    
//			    	    row++;
//			    	    
//			    	    if (can == ult) { // Validacion ultimo registro
//			    	    	rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Sub-Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//			    	    	
//				    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_det_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_det_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);
//				    	   
//				    	    
//				    	    row = row + 1;
//				    	 
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Total: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//			    	    	
//				    	    rows.createCell(6).setCellValue(dec_form.format(num_imp_tot_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(num_imp_tot_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(num_imp_tot_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(num_imp_tot_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_total));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_dol_total));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);	   
//				    	    
//				    	    
//				    	    row = row + 1;
//					    	 
//		            		rows = sheet.createRow((short) row);
//		            		rows.createCell(5).setCellValue("Total General: ");
//				    	    rows.getCell(5).setCellStyle(style_total); 
//			    	    	
//				    	    rows.createCell(6).setCellValue(dec_form.format(imp_tot_gen_afecto));
//				    	    rows.getCell(6).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(7).setCellValue(dec_form.format(imp_tot_gen_no_afecto));
//				    	    rows.getCell(7).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(8).setCellValue(dec_form.format(imp_tot_gen_igv));
//				    	    rows.getCell(8).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(9).setCellValue(dec_form.format(imp_tot_gen_isc));
//				    	    rows.getCell(9).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(10).setCellValue(dec_form.format(imp_tot_gen_soles));
//				    	    rows.getCell(10).setCellStyle(style_sub_mont);
//				    	    
//				    	    rows.createCell(11).setCellValue(dec_form.format(imp_tot_gen_dolares));
//				    	    rows.getCell(11).setCellStyle(style_sub_mont);	
//				    	    
//			    	    }		    	    
//			    	    
//			    	    can++;
//			        }
//		        	
//		        }	        		        
//	        	
//	        } else if (ingreso.getTip_transaccion().equals(Constantes.SEVEN_STRING)) {
//	        	
//	        	row9.createCell(0).setCellValue("");
//				row9.getCell(0).setCellStyle(style_right);
//		
//				row10.createCell(0).setCellValue("");
//				row10.getCell(0).setCellStyle(style_right);
//	        	
//				row9.createCell(1).setCellValue("");
//				row9.getCell(1).setCellStyle(style_right);
//		
//				row10.createCell(1).setCellValue("");
//				row10.getCell(1).setCellStyle(style_right);
//				
//				row9.createCell(2).setCellValue("");
//				row9.getCell(2).setCellStyle(style_right);
//		
//				row10.createCell(2).setCellValue("");
//				row10.getCell(2).setCellStyle(style_right);
//				
//				row10.createCell(3).setCellValue("");
//				row10.getCell(3).setCellStyle(style_right);
//				
//				row10.createCell(4).setCellValue("");
//				row10.getCell(4).setCellStyle(style_right);
//				
//				row7.createCell(5).setCellValue("");
//				row7.getCell(5).setCellStyle(style_bottom);
//				
//				row10.createCell(5).setCellValue("");
//				row10.getCell(5).setCellStyle(style_right);
//				
//				row7.createCell(6).setCellValue("");
//				row7.getCell(6).setCellStyle(style_bottom);
//				
//				row7.createCell(8).setCellValue("");
//				row7.getCell(8).setCellStyle(style_bottom);
//				
//				row8.createCell(8).setCellValue("");
//				row8.getCell(8).setCellStyle(style_bottom);
//	        	
//				row7.createCell(9).setCellValue("");
//				row7.getCell(9).setCellStyle(style_bottom);
//				
//				row10.createCell(9).setCellValue("");
//				row10.getCell(9).setCellStyle(style_right);
//
//				row10.createCell(9).setCellValue("");
//				row10.getCell(9).setCellStyle(style_right);
//				
//				row9.createCell(10).setCellValue("");
//				row9.getCell(10).setCellStyle(style_right);
//				
//				row10.createCell(10).setCellValue("");
//				row10.getCell(10).setCellStyle(style_right);
//				
//				row10.createCell(11).setCellValue("");
//				row10.getCell(11).setCellStyle(style_right);
//				
//				row10.createCell(12).setCellValue("");
//				row10.getCell(12).setCellStyle(style_right);
//				
//				row7.createCell(13).setCellValue("");
//				row7.getCell(13).setCellStyle(style_bottom);
//	        	
//				row10.createCell(13).setCellValue("");
//				row10.getCell(13).setCellStyle(style_right);
//	        	
//				row9.createCell(14).setCellValue("");
//				row9.getCell(14).setCellStyle(style_right);
//	        	
//				row10.createCell(14).setCellValue("");
//				row10.getCell(14).setCellStyle(style_right);
//	        	
//				row9.createCell(15).setCellValue("");
//				row9.getCell(15).setCellStyle(style_right);
//	        	
//				row10.createCell(15).setCellValue("");
//				row10.getCell(15).setCellStyle(style_right);
//				
//				row9.createCell(16).setCellValue("");
//				row9.getCell(16).setCellStyle(style_right);
//	        	
//				row10.createCell(16).setCellValue("");
//				row10.getCell(16).setCellStyle(style_right);
//				
//				row9.createCell(17).setCellValue("");
//				row9.getCell(17).setCellStyle(style_right);
//	        	
//				row10.createCell(17).setCellValue("");
//				row10.getCell(17).setCellStyle(style_right);
//				
//				row10.createCell(18).setCellValue("");
//				row10.getCell(18).setCellStyle(style_right);
//				
//				row10.createCell(19).setCellValue("");
//				row10.getCell(19).setCellStyle(style_right);
//	        	
//				row7.createCell(20).setCellValue("");
//				row7.getCell(20).setCellStyle(style_bottom);
//	        	
//				row10.createCell(20).setCellValue("");
//				row10.getCell(20).setCellStyle(style_right);
//								
//				row7.createCell(21).setCellValue("");
//				row7.getCell(21).setCellStyle(style_bottom);
//	        	
//				row10.createCell(21).setCellValue("");
//				row10.getCell(21).setCellStyle(style_right);
//				
//				row7.createCell(22).setCellValue("");
//				row7.getCell(22).setCellStyle(style_bottom);
//	        	
//				row10.createCell(22).setCellValue("");
//				row10.getCell(22).setCellStyle(style_right);
//				
//				row8.createCell(23).setCellValue("");
//				row8.getCell(23).setCellStyle(style_left);
//	        	
//	        	
//				
//				row8.createCell(1).setCellValue("NÚMERO CORRELATIVO DEL REGISTRO O CÓDIGO UNICO DE LA OPERACIÓN");
//		        row8.getCell(1).setCellStyle(style_header);
//				
//		        row8.createCell(2).setCellValue("FECHA DE EMISIÓN DEL COMPROBANTE DE PAGO O DOCUMENTO");
//		        row8.getCell(2).setCellStyle(style_header);
//		        
//		        row8.createCell(3).setCellValue("FECHA DE VENCIMIENTO Y/O PAGO");
//		        row8.getCell(3).setCellStyle(style_header);
//		        
//		        row8.createCell(4).setCellValue("COMPROBANTE DE PAGO O DOCUMENTO");
//		        row8.getCell(4).setCellStyle(style_header);
//				
//		        row9.createCell(4).setCellValue("TIPO");
//		        row9.getCell(4).setCellStyle(style_header);
//		        
//		        row9.createCell(5).setCellValue("N° SERIE O N° DE SERIE DE LA MAQUINA REGISTRADORA");
//		        row9.getCell(5).setCellStyle(style_header);
//		        
//		        row9.createCell(6).setCellValue("NÚMERO");
//		        row9.getCell(6).setCellStyle(style_header);
//
//		        row8.createCell(7).setCellValue("INFORMACIÓN DEL CLIENTE");
//		        row8.getCell(7).setCellStyle(style_header);
//		        
//		        row9.createCell(7).setCellValue("DOCUMENTO DE IDENTIDAD");
//		        row9.getCell(7).setCellStyle(style_header);
//
//		        row10.createCell(7).setCellValue("TIPO");
//		        row10.getCell(7).setCellStyle(style_header);
//		        
//		        row10.createCell(8).setCellValue("NÚMERO");
//		        row10.getCell(8).setCellStyle(style_header);
//		        
//		        row9.createCell(9).setCellValue("APELLIDOS Y NOMBRES, DENOMINACIÓN O RAZÓN SOCIAL");
//		        row9.getCell(9).setCellStyle(style_header);
//		        
//		        row8.createCell(10).setCellValue("VALOR FACTURADO DE LA EXPORTACIÓN");
//		        row8.getCell(10).setCellStyle(style_header);
//		        
//		        row8.createCell(11).setCellValue("BASE IMPONIBLE DE LA OPERACIÓN GRAVADA");
//		        row8.getCell(11).setCellStyle(style_header);
//		        
//		        row8.createCell(12).setCellValue("IMPORTE TOTAL DE LA OPERACIÓN EXONERADA O INAFECTA");
//		        row8.getCell(12).setCellStyle(style_header);
//		        		        
//		        row9.createCell(12).setCellValue("EXONERADA");
//		        row9.getCell(12).setCellStyle(style_header);
//		        
//		        row9.createCell(13).setCellValue("INAFECTA");
//		        row9.getCell(13).setCellStyle(style_header);
//		        		        
//		        row8.createCell(14).setCellValue("ISC");
//		        row8.getCell(14).setCellStyle(style_header);
//		        
//		        row8.createCell(15).setCellValue("IGV Y/O IPM");
//		        row8.getCell(15).setCellStyle(style_header);
//		        
//		        row8.createCell(16).setCellValue("OTROS TRIBUTOS Y CARGOS QUE NO FORMAN PARTE DE LA BASE IMPONIBLE");
//		        row8.getCell(16).setCellStyle(style_header);
//		        
//		        row8.createCell(17).setCellValue("IMPORTE TOTAL DEL COMPROBANTE DE PAGO");
//		        row8.getCell(17).setCellStyle(style_header);
//		        
//		        row8.createCell(18).setCellValue("TIPO DE CAMBIO");
//		        row8.getCell(18).setCellStyle(style_header);
//		              
//		        row8.createCell(19).setCellValue("REFERENCIA DEL COMPROBANTE DE PAGO O DOCUMENTO ORIGINAL QUE SE MODIFICA");
//		        row8.getCell(19).setCellStyle(style_header);
//		        
//		        row9.createCell(19).setCellValue("FECHA");
//		        row9.getCell(19).setCellStyle(style_header);
//
//		        row9.createCell(20).setCellValue("TIPO");
//		        row9.getCell(20).setCellStyle(style_header);
//		        
//		        row9.createCell(21).setCellValue("SERIE");
//		        row9.getCell(21).setCellStyle(style_header);
//		        
//		        row9.createCell(22).setCellValue("N° DEL COMPROBANTE DE PAGO O DOCUMENTO");
//		        row9.getCell(22).setCellStyle(style_header);		        
//				
//		        
//		        int row = 1;
//		        BigDecimal num_imp_afecto = BigDecimal.ZERO;
//		        BigDecimal num_imp_no_afecto = BigDecimal.ZERO;
//		        BigDecimal num_imp_igv = BigDecimal.ZERO;
//		        BigDecimal num_imp_isc = BigDecimal.ZERO;
//		        BigDecimal imp_det_total = BigDecimal.ZERO;		        
//		        
//		        for (ReporteDetalleIngresosBean rep_ingreso : lista) {
//		        	
//		        	HSSFRow rows  = sheet.createRow((short) row + 10);
//		        	
//		        	rows.createCell(1).setCellValue("M".concat(String.valueOf(row)));
//			        rows.getCell(1).setCellStyle(style_cell);
//			        
//			        rows.createCell(2).setCellValue(rep_ingreso.getFec_comprobante());
//			        rows.getCell(2).setCellStyle(style_cell);
//			        
//			        rows.createCell(3).setCellValue(rep_ingreso.getFec_vto());
//			        rows.getCell(3).setCellStyle(style_cell);
//		        
//			        rows.createCell(4).setCellValue(rep_ingreso.getVcod_comprobante());
//			        rows.getCell(4).setCellStyle(style_cell);
//			        
//			        rows.createCell(5).setCellValue(rep_ingreso.getSer_comprobante());
//			        rows.getCell(5).setCellStyle(style_cell);
//
//			        rows.createCell(6).setCellValue(StringUtils.stripStart(rep_ingreso.getNro_comprobante(), Constantes.ZERO_STRING));
//			        rows.getCell(6).setCellStyle(style_cell);
//			        
//			        if (rep_ingreso.getCod_tip_doc_proveedor().equals(Constantes.ZERO_STRING) ||
//			        		isNullOrEmpty(rep_ingreso.getCod_tip_doc_proveedor())) {
//			        	rows.createCell(7).setCellValue(Constantes.EMPTY);
//			        } else {			        
//			        	rows.createCell(7).setCellValue(StringUtils.leftPad(rep_ingreso.getCod_tip_doc_proveedor(), 2, '0'));
//			        }
//			        rows.getCell(7).setCellStyle(style_cell);
//
//			        rows.createCell(8).setCellValue(getString(rep_ingreso.getRuc_nro_doc_prov()));
//			        rows.getCell(8).setCellStyle(style_cell);
//			        
//			        rows.createCell(9).setCellValue(getString(rep_ingreso.getRaz_soc_prov()));
//			        rows.getCell(9).setCellStyle(style_cell);
//			        
//			        rows.createCell(10).setCellValue(Constantes.EMPTY);
//			        rows.getCell(10).setCellStyle(style_cell);
//			        
//			        
//			        BigDecimal imp_afecto = rep_ingreso.getNum_imp_afecto();
//			        if (rep_ingreso.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_afecto.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_afecto = imp_afecto.negate();
//			        }
//			        rows.createCell(11).setCellValue(dec_form.format(imp_afecto));
//			        rows.getCell(11).setCellStyle(style_mont);
//			        
//			        rows.createCell(12).setCellValue(dec_form.format(BigDecimal.ZERO));
//			        rows.getCell(12).setCellStyle(style_mont);
//			        
//			        
//			        BigDecimal imp_no_afecto = rep_ingreso.getNum_imp_no_afecto();
//			        if (rep_ingreso.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_no_afecto.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_no_afecto = imp_no_afecto.negate();
//			        }
//			        rows.createCell(13).setCellValue(dec_form.format(imp_no_afecto));
//			        rows.getCell(13).setCellStyle(style_mont);
//			        
//			        
//			        BigDecimal imp_isc = rep_ingreso.getNum_imp_isc();
//			        if (rep_ingreso.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_isc.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_isc = imp_isc.negate();
//			        }
//			        rows.createCell(14).setCellValue(dec_form.format(imp_isc));
//			        rows.getCell(14).setCellStyle(style_mont);
//			        
//			        
//			        BigDecimal imp_igv = rep_ingreso.getNum_imp_igv();
//			        if (rep_ingreso.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_igv.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_igv = imp_igv.negate();
//			        }
//			        rows.createCell(15).setCellValue(dec_form.format(imp_igv));
//			        rows.getCell(15).setCellStyle(style_mont);
//			        
//			        rows.createCell(16).setCellValue(Constantes.EMPTY);
//			        rows.getCell(16).setCellStyle(style_cell);
//			        
//			        
//			        BigDecimal imp_total = rep_ingreso.getImp_det_total();
//			        if (rep_ingreso.getCod_comprobante().equals(Constantes.EIGHT_INT) && // Nota de Credito
//			        		imp_total.compareTo(BigDecimal.ZERO) == 1) {
//			        	imp_total = imp_total.negate();
//			        }
//			        rows.createCell(17).setCellValue(dec_form.format(imp_total));
//			        rows.getCell(17).setCellStyle(style_mont);
//			        
//			        rows.createCell(18).setCellValue(getString(rep_ingreso.getNum_tip_cambio()));
//			        rows.getCell(18).setCellStyle(style_cell);
//			 
//			        rows.createCell(19).setCellValue(getString(rep_ingreso.getFec_com_not_cre()));
//			        rows.getCell(19).setCellStyle(style_cell);
//			        
//			        rows.createCell(20).setCellValue(getString(rep_ingreso.getTip_com_not_cre()));
//			        rows.getCell(20).setCellStyle(style_cell);
//			        
//			        rows.createCell(21).setCellValue(getString(rep_ingreso.getSer_com_not_cre()));
//			        rows.getCell(21).setCellStyle(style_cell);
//			        
//			        rows.createCell(22).setCellValue(StringUtils.stripStart(getString(rep_ingreso.getNro_com_not_cre()), Constantes.ZERO_STRING));
//			        rows.getCell(22).setCellStyle(style_cell);			        
//			        
//			        num_imp_afecto = num_imp_afecto.add(imp_afecto);
//		            num_imp_no_afecto = num_imp_no_afecto.add(imp_no_afecto);
//		            num_imp_igv = num_imp_igv.add(imp_igv);
//		            num_imp_isc = num_imp_isc.add(imp_isc);
//		            imp_det_total = imp_det_total.add(imp_total);
//		            
//		            row++;
//		        }
//		        
//		        HSSFCellStyle style_cell_total = (HSSFCellStyle) wb.createCellStyle();
//		        style_cell_total.setFont(font_bold);	        
//		        style_cell_total.setBorderBottom((short) 1);
//		        style_cell_total.setBorderLeft((short) 1);	        
//		        style_cell_total.setBorderRight((short) 1);
//		        style_cell_total.setBorderTop((short) 1);
//		        
//		        HSSFCellStyle style_tot_mont = (HSSFCellStyle) wb.createCellStyle();
//		        style_tot_mont.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//		        style_tot_mont.setFont(font_bold);	        
//		        style_tot_mont.setBorderBottom((short) 1);
//		        style_tot_mont.setBorderLeft((short) 1);	        
//		        style_tot_mont.setBorderRight((short) 1);
//		        style_tot_mont.setBorderTop((short) 1);
//		        
//		        HSSFRow row_total  = sheet.createRow((short) row + 10);
//		        
//		        HSSFRichTextString total = new HSSFRichTextString("TOTALES:");
//		        total.applyFont(font_bold);
//		        row_total.createCell(9).setCellValue(total);
//		        
//		        row_total.createCell(10).setCellValue(Constantes.EMPTY);
//		        row_total.getCell(10).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(11).setCellValue(dec_form.format(num_imp_afecto));
//		        row_total.getCell(11).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(12).setCellValue(dec_form.format(BigDecimal.ZERO));
//		        row_total.getCell(12).setCellStyle(style_tot_mont);
//	
//		        row_total.createCell(13).setCellValue(dec_form.format(num_imp_no_afecto));
//		        row_total.getCell(13).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(14).setCellValue(dec_form.format(num_imp_isc));
//		        row_total.getCell(14).setCellStyle(style_tot_mont);
//
//		        row_total.createCell(15).setCellValue(dec_form.format(num_imp_igv));
//		        row_total.getCell(15).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(16).setCellValue(Constantes.EMPTY);
//		        row_total.getCell(16).setCellStyle(style_tot_mont);
//		        
//		        row_total.createCell(17).setCellValue(dec_form.format(imp_det_total));
//		        row_total.getCell(17).setCellStyle(style_tot_mont);	
//	        	
//	        }
//	            
//            
//            // Captured backflow
//            OutputStream out = response.getOutputStream();
//            wb.write(out); // We write in that flow
//            out.flush(); // We emptied the flow
//            out.close(); // We close the flow
//            
//    	} catch(Exception e) {
//    		e.printStackTrace();
//    		log.error(getGenerarError(Thread.currentThread().getStackTrace()[1].getMethodName(),
//					  Constantes.NIVEL_APP_REPORTER, 
//					  this.getClass().getName(), e.getMessage()));
//    	}
//    }
//    
//    private String getMes(int mes) {
//    	String desmes = null;
//    	switch (mes) {
//			case 1:
//				desmes = "Enero";
//				break;
//			case 2:
//				desmes = "Febrero";
//				break;
//			case 3:
//				desmes = "Marzo";
//				break;
//			case 4:
//				desmes = "Abril";
//				break;
//			case 5:
//				desmes = "Mayo";
//				break;
//			case 6:
//				desmes = "Junio";
//				break;
//			case 7:
//				desmes = "Julio";
//				break;
//			case 8:
//				desmes = "Agosto";
//				break;
//			case 9:
//				desmes = "Setiembre";
//				break;
//			case 10:
//				desmes = "Octubre";
//				break;
//			case 11:
//				desmes = "Noviembre";
//				break;
//			case 12:
//				desmes = "Diciembre";
//				break;
//		}    	
//    	return desmes;
//    }
//    
//    /**
//	 * Verifica si la cadena esta vacía
//	 * @param campo - valor del parámetro a evaluar, tipo String
//	 * @return true si es vacío o nulo y false lo contrario
//	 */
//	private static boolean isNullOrEmpty(String campo) { 
//	    return campo == null || campo.trim().length() == 0;
//	}
//	
//	/**
//	 * Verifica si la lista esta vacía
//	 * @param coll - Lista parámetro a evaluar, tipo Collection.
//	 * @return true si es vacío o nulo y false lo contrario.
//	 */
//	@SuppressWarnings("rawtypes")
//	private static boolean isEmpty(Collection coll) {
//	    return (coll == null || coll.isEmpty());
//	}
//	
//	/**
//	 * Retorna el valor parseado.
//	 * @param campo - Valor del parámetro a evaluar, tipo Object.
//	 * @return valor - Valor de la cadena.
//	 */
//	private static String getString(Object campo) {
//		if (campo != null) {
//			if (campo instanceof Integer) {
//				return String.valueOf((Integer) campo);
//			} else if (campo instanceof Long) {
//				return String.valueOf((Long) campo);
//			} else if (campo instanceof BigDecimal) {
//				return String.valueOf((BigDecimal) campo);
//			} else {
//				return (String) campo;
//			}
//		}
//		return Constantes.EMPTY; 	
//	}
//	
//	/**
//	 * Verifica si el entero es nulo o con valor 0 por defecto.
//	 * @param campo - valor del parámetro a evaluar, tipo Integer.
//	 * @return true si es vacío o nulo y false lo contrario
//	 */
//	private static boolean isNullInteger(Integer campo) {
//		if (!isNull(campo) && campo > 0) {
//			return false;
//		} else {
//			return true;
//		}
//	}
//	
//	/**
//	 * Verifica si la cadena esta vacía
//	 * @param campo - valor del parámetro a evaluar, tipo Object
//	 * @return true si es vacío o nulo y false lo contrario
//	 */
//	private static boolean isNull(Object campo) {
//		if (campo == null) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	/**
//	 * Devuelve el error formateado con el nombre del método.
//	 * @param nombre - Nombre del método donde se genero el error, tipo String
//	 * @param nivel - Nivel donde se genero el error, tipo String
//	 * @param nombreClase - Nombre de la clase, tipo String
//	 * @param mensaje - Mensaje del error, tipo String
//	 * @return Mensaje formateado.
//	 */
//	private String getGenerarError(String nombre, String nivel, String nombreClase, String mensaje) {
//		StringBuffer error = new StringBuffer();
//		error.append(Constantes.DIVISOR_ERROR_4);
//		error.append(Constantes.DIVISOR_ERROR_1);
//		error.append(nombreClase);
//		error.append(" - ");
//		error.append(nombre);
//		error.append(Constantes.DIVISOR_ERROR_2);
//		error.append(mensaje);	
//		return error.toString();
//	}
//	
//	private HSSFColor setColor(HSSFWorkbook workbook, byte r, byte g, byte b){
//		HSSFPalette palette = workbook.getCustomPalette();
//		HSSFColor hssfColor = null;
//		try {
//			hssfColor= palette.findColor(r, g, b); 
//			if (hssfColor == null) {
//			    palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g,b);
//			    hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
//			}
//		 } catch (Exception e) {
//			 log.error(e.getMessage());
//		 }
//		 return hssfColor;
//	}
//	
//    
//}
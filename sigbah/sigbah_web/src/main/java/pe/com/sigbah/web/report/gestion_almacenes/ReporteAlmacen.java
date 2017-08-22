package pe.com.sigbah.web.report.gestion_almacenes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import pe.com.sigbah.common.bean.BincardAlmacenBean;
import pe.com.sigbah.common.bean.DetalleGuiaRemisionBean;
import pe.com.sigbah.common.bean.GuiaRemisionBean;
import pe.com.sigbah.common.bean.KardexAlmacenBean;
import pe.com.sigbah.common.bean.OrdenIngresoBean;
import pe.com.sigbah.common.bean.OrdenSalidaBean;
import pe.com.sigbah.common.bean.ProductoIngresoBean;
import pe.com.sigbah.common.bean.ProductoProyectoManifiestoBean;
import pe.com.sigbah.common.bean.ProductoSalidaBean;
import pe.com.sigbah.common.bean.ProyectoManifiestoBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.common.util.DateUtil;

/**
 * @className: ReporteAlmacen.java
 * @description: 
 * @date: 20 de jul. de 2017
 * @author: Junior Huaman Flores.
 */
public class ReporteAlmacen implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Log LOGGER = LogFactory.getLog(ReporteAlmacen.class.getName());
	
	/**
	 * @param workbook
	 * @param r
	 * @param g
	 * @param b
	 * @return Objeto.
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
	 * @param mes
	 * @return descripcion del mes
	 */
	private static String getMes(String mes) {
    	String descripcionMes = null;
    	int codigoMes = Integer.valueOf(mes);
    	switch (codigoMes) {
			case 1:
				descripcionMes = "Enero";
				break;
			case 2:
				descripcionMes = "Febrero";
				break;
			case 3:
				descripcionMes = "Marzo";
				break;
			case 4:
				descripcionMes = "Abril";
				break;
			case 5:
				descripcionMes = "Mayo";
				break;
			case 6:
				descripcionMes = "Junio";
				break;
			case 7:
				descripcionMes = "Julio";
				break;
			case 8:
				descripcionMes = "Agosto";
				break;
			case 9:
				descripcionMes = "Setiembre";
				break;
			case 10:
				descripcionMes = "Octubre";
				break;
			case 11:
				descripcionMes = "Noviembre";
				break;
			case 12:
				descripcionMes = "Diciembre";
				break;
			default: 
				descripcionMes = Constantes.EMPTY;
            	break;
		}    	
    	return descripcionMes;
    }
	
	/**
	 * Retorna el valor parseado.
	 * @param campo - Valor del parámetro a evaluar, tipo BigDecimal.
	 * @return valor - Valor de la campo sino cero.
	 */
	private static BigDecimal getBigDecimal(BigDecimal campo) {
		if (campo != null) {
			return campo;
		}
		return BigDecimal.ZERO; 	
	}

	/**
	 * @param ruta
	 * @param proyectoManifiestoBean 
	 * @param listaProyectoManifiesto
	 * @throws Exception 
	 */
	public void generaPDFReporteProyectoManifiesto(String ruta, 
												   ProyectoManifiestoBean proyectoManifiestoBean, 
												   List<ProyectoManifiestoBean> listaProyectoManifiesto) throws Exception {
		Document document = null;
		try {
			document = new Document(PageSize.A4.rotate(), 0, 0, 20, 20);
			PdfWriter.getInstance(document, new FileOutputStream(ruta));    
			
			document.open();
			 
			// Considerar que cada campo en array es una columna table de tu reporte
			float[] ftit = {20, 15, 65};			
			
			float[] f1 = {100};
			
			float[] f3 = {30, 30, 40};

			float[] f9 = {5, 10, 12, 13, 15, 15, 10, 10, 10};
			
			BaseColor header = new BaseColor(242, 242, 242);
			
			DecimalFormatSymbols dec_for_symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, dec_for_symbols);

			Paragraph p     = null;
			Paragraph pdet 	= null;
			PdfPTable table = null;
			PdfPCell cell   = null;

			Font titulo = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
			Font hide = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.WHITE);
			Font encabezado = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
			Font normal = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);			
			Font negrita = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
			   
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(ftit);
			
			String path = ruta.substring(0, ruta.indexOf(Constantes.REPORT_PATH_RESOURCES));			
			Image img = Image.getInstance(path.concat(Constantes.IMAGE_INDECI_REPORT_PATH));
			cell = new PdfPCell(img, true);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			StringBuilder det_encabezado = new StringBuilder();
			det_encabezado.append(Constantes.TITULO_ENCABEZADO_REPORTE);
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.FECHA_ENCABEZADO_REPORTE);
			Date fecha_hora = Calendar.getInstance().getTime();
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.HORA_ENCABEZADO_REPORTE);
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_HORA, fecha_hora));
			p = new Paragraph(det_encabezado.toString(), encabezado);
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE_PDF, hide);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(1);
			table.setWidths(f1);
			
			p = new Paragraph("REPORTE DE PROYECTO DE MANIFIESTO", titulo);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin			
			
			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("DDI: ", negrita);
			pdet = new Paragraph(listaProyectoManifiesto.get(0).getNombreDdi(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("AÑO: ", negrita);
			pdet = new Paragraph(proyectoManifiestoBean.getCodigoAnio(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("ALMACEN: ", negrita);
			pdet = new Paragraph(listaProyectoManifiesto.get(0).getNombreAlmacen(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("MES INICIO: ", negrita);
			pdet = new Paragraph(getMes(proyectoManifiestoBean.getCodigoMesInicio()), normal);
			p.add(pdet);			
			pdet = new Paragraph("   MES FIN: ", negrita);
			p.add(pdet);			
			pdet = new Paragraph(getMes(proyectoManifiestoBean.getCodigoMesFin()), normal);
			p.add(pdet);			
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin

			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	

			
			// Bloque Inicio
			table = new PdfPTable(9);
			table.setWidths(f9);
			
			p = new Paragraph("ITEM", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("FECHA EMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO PROY. DE MANIFIESTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO PROGRAMACION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("TIPO MOVIMIENTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("DESTINO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("IMPORTE TOTAL", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PESO TOTAL KGR", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("ESTADO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			int row = 1;
			
			for (ProyectoManifiestoBean proyecto : listaProyectoManifiesto) {
			
				table = new PdfPTable(9);
				table.setWidths(f9);
				
				p = new Paragraph(String.valueOf(row), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			
				p = new Paragraph(proyecto.getFechaEmision(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(proyecto.getNroProyectoManifiesto(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(proyecto.getNroProgramacion(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(proyecto.getNombreMovimiento(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(proyecto.getNombreAlmacenDestino(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(proyecto.getVolumenTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(proyecto.getPesoTotalKgr())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(proyecto.getNombreEstado(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				document.add(table);	             
				
				row++;
			}
			// Bloque Fin
			
		} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * @param ruta
	 * @param productoProyectoManifiestoBean 
	 * @param listaDetalleProyectoManifiesto
	 * @throws Exception 
	 */
	public void generaPDFReporteDetalleProyectoManifiesto(String ruta, 
														  ProductoProyectoManifiestoBean productoProyectoManifiestoBean, 
														  List<ProductoProyectoManifiestoBean> listaDetalleProyectoManifiesto) throws Exception {
		Document document = null;
		try {
			document = new Document(PageSize.A4.rotate(), 0, 0, 20, 20);
			PdfWriter.getInstance(document, new FileOutputStream(ruta));    
			
			document.open();
			 
			// Considerar que cada campo en array es una columna table de tu reporte
			float[] ftit = {20, 15, 65};			
			
			float[] f1 = {100};
			
			float[] f3 = {30, 30, 40};

			float[] f11 = {5, 8, 10, 11, 10, 8, 16, 8, 8, 8, 8};
			
			BaseColor header = new BaseColor(242, 242, 242);

			Paragraph p     = null;
			Paragraph pdet 	= null;
			PdfPTable table = null;
			PdfPCell cell   = null;

			Font titulo = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
			Font hide = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.WHITE);
			Font encabezado = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
			Font normal = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);			
			Font negrita = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
			
			DecimalFormatSymbols dec_for_symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, dec_for_symbols);
			   
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(ftit);
			
			String path = ruta.substring(0, ruta.indexOf(Constantes.REPORT_PATH_RESOURCES));			
			Image img = Image.getInstance(path.concat(Constantes.IMAGE_INDECI_REPORT_PATH));
			cell = new PdfPCell(img, true);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			StringBuilder det_encabezado = new StringBuilder();
			det_encabezado.append(Constantes.TITULO_ENCABEZADO_REPORTE);
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.FECHA_ENCABEZADO_REPORTE);
			Date fecha_hora = Calendar.getInstance().getTime();
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.HORA_ENCABEZADO_REPORTE);
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_HORA, fecha_hora));
			p = new Paragraph(det_encabezado.toString(), encabezado);
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE_PDF, hide);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(1);
			table.setWidths(f1);
			
			StringBuilder det_titulo = new StringBuilder();
			det_titulo.append("REPORTE DE PROYECTO DE MANIFIESTO");
			det_titulo.append(Constantes.SALTO_LINEA_PARRAFO);
			det_titulo.append("DETALLE POR PRODUCTO");
			p = new Paragraph(det_titulo.toString(), titulo);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin			
			
			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("DDI: ", negrita);
			pdet = new Paragraph(listaDetalleProyectoManifiesto.get(0).getNombreDdi(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("AÑO: ", negrita);
			pdet = new Paragraph(productoProyectoManifiestoBean.getCodigoAnio(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("ALMACEN: ", negrita);
			pdet = new Paragraph(listaDetalleProyectoManifiesto.get(0).getNombreAlmacen(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("MES INICIO: ", negrita);
			pdet = new Paragraph(getMes(productoProyectoManifiestoBean.getCodigoMesInicio()), normal);
			p.add(pdet);			
			pdet = new Paragraph("   MES FIN: ", negrita);
			p.add(pdet);			
			pdet = new Paragraph(getMes(productoProyectoManifiestoBean.getCodigoMesFin()), normal);
			p.add(pdet);			
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin

			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	

			
			// Bloque Inicio
			table = new PdfPTable(11);
			table.setWidths(f11);
			
			p = new Paragraph("ITEM", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("FECHA EMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO PROY. MANIFIESTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO PROGRAMACION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("TIPO MOVIMIENTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("ESTADO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PRODUCTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("UNIDAD", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("CANTIDAD", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("VOLUMEN M3", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PESO TOTAL KGR", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			int row = 0;
			int count = 1;
			boolean ind_primero = false;
			BigDecimal cantidadSubTotal = BigDecimal.ZERO;
			BigDecimal volumenSubTotal = BigDecimal.ZERO;
			BigDecimal pesoSubTotal = BigDecimal.ZERO;
			String nroProyectoManifiesto = Constantes.EMPTY;
			for (ProductoProyectoManifiestoBean proyecto : listaDetalleProyectoManifiesto) {
			
				table = new PdfPTable(11);
				table.setWidths(f11);

				if (!nroProyectoManifiesto.equals(proyecto.getNroProyectoManifiesto())) {
					
					if (count > 1) {
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph("TOTAL PROYECTO", negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(volumenSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(pesoSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cantidadSubTotal = BigDecimal.ZERO;
						volumenSubTotal = BigDecimal.ZERO;
						pesoSubTotal = BigDecimal.ZERO;
							
						document.add(table);
						
						table = new PdfPTable(11);
						table.setWidths(f11);
						
					}
						
					row++;
				
					nroProyectoManifiesto = proyecto.getNroProyectoManifiesto();
				
					p = new Paragraph(String.valueOf(row), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
				
					p = new Paragraph(proyecto.getFechaEmision(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(nroProyectoManifiesto, normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(proyecto.getNroProgramacion(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(proyecto.getNombreMovimiento(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(proyecto.getNombreEstado(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					ind_primero = true;
				
				} else {
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);					
					
				}
				
				p = new Paragraph(proyecto.getNombreProducto(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(proyecto.getNombreUnidad(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(proyecto.getCantidad())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(proyecto.getVolumenTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(proyecto.getPesoTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				cantidadSubTotal = cantidadSubTotal.add(getBigDecimal(proyecto.getCantidad()));
				volumenSubTotal = volumenSubTotal.add(getBigDecimal(proyecto.getVolumenTotal()));
				pesoSubTotal = pesoSubTotal.add(getBigDecimal(proyecto.getPesoTotal()));
				count++;
				
				ind_primero = false;

				document.add(table);
				
				// Subtotal del ultimo registro
				if (count == listaDetalleProyectoManifiesto.size() + 1) {
					
					table = new PdfPTable(11);
					table.setWidths(f11);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph("TOTAL PROYECTO", negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(volumenSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(pesoSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					document.add(table);
					
				}
				
				
			}
			// Bloque Fin
			
		} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * @param ruta
	 * @param ordenSalidaBean 
	 * @param listaOrdenSalida
	 * @throws Exception 
	 */
	public void generaPDFReporteOrdenSalida(String ruta, 
											OrdenSalidaBean ordenSalidaBean, 
											List<OrdenSalidaBean> listaOrdenSalida) throws Exception {
		Document document = null;
		try {
			document = new Document(PageSize.A4.rotate(), 0, 0, 20, 20);
			PdfWriter.getInstance(document, new FileOutputStream(ruta));    
			
			document.open();
			 
			// Considerar que cada campo en array es una columna table de tu reporte
			float[] ftit = {20, 15, 65};			
			
			float[] f1 = {100};
			
			float[] f3 = {30, 30, 40};

			float[] f9 = {5, 10, 12, 13, 15, 15, 10, 10, 10};
			
			BaseColor header = new BaseColor(242, 242, 242);
			
			DecimalFormatSymbols dec_for_symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, dec_for_symbols);

			Paragraph p     = null;
			Paragraph pdet 	= null;
			PdfPTable table = null;
			PdfPCell cell   = null;

			Font titulo = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
			Font hide = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.WHITE);
			Font encabezado = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
			Font normal = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);			
			Font negrita = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
			   
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(ftit);
			
			String path = ruta.substring(0, ruta.indexOf(Constantes.REPORT_PATH_RESOURCES));			
			Image img = Image.getInstance(path.concat(Constantes.IMAGE_INDECI_REPORT_PATH));
			cell = new PdfPCell(img, true);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			StringBuilder det_encabezado = new StringBuilder();
			det_encabezado.append(Constantes.TITULO_ENCABEZADO_REPORTE);
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.FECHA_ENCABEZADO_REPORTE);
			Date fecha_hora = Calendar.getInstance().getTime();
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.HORA_ENCABEZADO_REPORTE);
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_HORA, fecha_hora));
			p = new Paragraph(det_encabezado.toString(), encabezado);
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE_PDF, hide);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(1);
			table.setWidths(f1);
			
			p = new Paragraph("REPORTE DE ORDENES DE SALIDA", titulo);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin			
			
			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("DDI: ", negrita);
			pdet = new Paragraph(listaOrdenSalida.get(0).getNombreDdi(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("AÑO: ", negrita);
			pdet = new Paragraph(ordenSalidaBean.getCodigoAnio(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("ALMACEN: ", negrita);
			pdet = new Paragraph(listaOrdenSalida.get(0).getNombreAlmacen(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("MES INICIO: ", negrita);
			pdet = new Paragraph(getMes(ordenSalidaBean.getCodigoMesInicio()), normal);
			p.add(pdet);			
			pdet = new Paragraph("   MES FIN: ", negrita);
			p.add(pdet);			
			pdet = new Paragraph(getMes(ordenSalidaBean.getCodigoMesFin()), normal);
			p.add(pdet);			
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin

			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	

			
			// Bloque Inicio
			table = new PdfPTable(9);
			table.setWidths(f9);
			
			p = new Paragraph("ITEM", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("FECHA EMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO ORDEN DE SALIDA", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO GUIA REMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("TIPO MOVIMIENTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("DESTINO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("IMPORTE TOTAL", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PESO TOTAL KGR", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("ESTADO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			int row = 1;
			
			for (OrdenSalidaBean ordenSalida : listaOrdenSalida) {
			
				table = new PdfPTable(9);
				table.setWidths(f9);
				
				p = new Paragraph(String.valueOf(row), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			
				p = new Paragraph(ordenSalida.getFechaEmision(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(ordenSalida.getNroOrdenSalida(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(ordenSalida.getNroGuiaRemision(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(ordenSalida.getNombreMovimiento(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(ordenSalida.getNombreAlmacenDestino(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(ordenSalida.getImporteTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(ordenSalida.getPesoTotalKgr())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(ordenSalida.getNombreEstado(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				document.add(table);	             
				
				row++;
			}
			// Bloque Fin
			
		} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * @param ruta
	 * @param productoSalidaBean 
	 * @param listaDetalleOrdenSalida
	 * @throws Exception 
	 */
	public void generaPDFReporteDetalleOrdenSalida(String ruta, 
												   ProductoSalidaBean productoSalidaBean, 
												   List<ProductoSalidaBean> listaDetalleOrdenSalida) throws Exception {
		Document document = null;
		try {
			document = new Document(PageSize.A4.rotate(), 0, 0, 20, 20);
			PdfWriter.getInstance(document, new FileOutputStream(ruta));    
			
			document.open();
			 
			// Considerar que cada campo en array es una columna table de tu reporte
			float[] ftit = {20, 15, 65};			
			
			float[] f1 = {100};
			
			float[] f3 = {30, 30, 40};

			float[] f11 = {5, 8, 10, 11, 10, 9, 15, 8, 8, 8, 8};
			
			BaseColor header = new BaseColor(242, 242, 242);

			Paragraph p     = null;
			Paragraph pdet 	= null;
			PdfPTable table = null;
			PdfPCell cell   = null;

			Font titulo = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
			Font hide = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.WHITE);
			Font encabezado = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
			Font normal = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);			
			Font negrita = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
			
			DecimalFormatSymbols dec_for_symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, dec_for_symbols);
			   
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(ftit);
			
			String path = ruta.substring(0, ruta.indexOf(Constantes.REPORT_PATH_RESOURCES));			
			Image img = Image.getInstance(path.concat(Constantes.IMAGE_INDECI_REPORT_PATH));
			cell = new PdfPCell(img, true);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			StringBuilder det_encabezado = new StringBuilder();
			det_encabezado.append(Constantes.TITULO_ENCABEZADO_REPORTE);
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.FECHA_ENCABEZADO_REPORTE);
			Date fecha_hora = Calendar.getInstance().getTime();
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.HORA_ENCABEZADO_REPORTE);
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_HORA, fecha_hora));
			p = new Paragraph(det_encabezado.toString(), encabezado);
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE_PDF, hide);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(1);
			table.setWidths(f1);
			
			StringBuilder det_titulo = new StringBuilder();
			det_titulo.append("REPORTE DE ORDENES DE SALIDA");
			det_titulo.append(Constantes.SALTO_LINEA_PARRAFO);
			det_titulo.append("DETALLE POR PRODUCTO");
			p = new Paragraph(det_titulo.toString(), titulo);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin			
			
			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("DDI: ", negrita);
			pdet = new Paragraph(listaDetalleOrdenSalida.get(0).getNombreDdi(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("AÑO: ", negrita);
			pdet = new Paragraph(productoSalidaBean.getCodigoAnio(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("ALMACEN: ", negrita);
			pdet = new Paragraph(listaDetalleOrdenSalida.get(0).getNombreAlmacen(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("MES INICIO: ", negrita);
			pdet = new Paragraph(getMes(productoSalidaBean.getCodigoMesInicio()), normal);
			p.add(pdet);			
			pdet = new Paragraph("   MES FIN: ", negrita);
			p.add(pdet);			
			pdet = new Paragraph(getMes(productoSalidaBean.getCodigoMesFin()), normal);
			p.add(pdet);			
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin

			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	

			
			// Bloque Inicio
			table = new PdfPTable(11);
			table.setWidths(f11);
			
			p = new Paragraph("ITEM", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("FECHA EMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO ORDEN SALIDA", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO GUIA REMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("TIPO MOVIMIENTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("ESTADO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PRODUCTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("UNIDAD", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("CANTIDAD", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PRECIO UNIT. PROMEDIO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("IMPORTE TOTAL", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			int row = 0;
			int count = 1;
			boolean ind_primero = false;
			BigDecimal cantidadSubTotal = BigDecimal.ZERO;
			BigDecimal precioUnitarioSubTotal = BigDecimal.ZERO;
			BigDecimal importeSubTotal = BigDecimal.ZERO;
			String nroOrdenSalida = Constantes.EMPTY;
			for (ProductoSalidaBean producto : listaDetalleOrdenSalida) {
			
				table = new PdfPTable(11);
				table.setWidths(f11);

				if (!nroOrdenSalida.equals(producto.getNroOrdenSalida())) {
					
					if (count > 1) {
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph("TOTAL ORDEN", negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(precioUnitarioSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(importeSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cantidadSubTotal = BigDecimal.ZERO;
						precioUnitarioSubTotal = BigDecimal.ZERO;
						importeSubTotal = BigDecimal.ZERO;
							
						document.add(table);
						
						table = new PdfPTable(11);
						table.setWidths(f11);
						
					}
						
					row++;
				
					nroOrdenSalida = producto.getNroOrdenSalida();
				
					p = new Paragraph(String.valueOf(row), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
				
					p = new Paragraph(producto.getFechaEmision(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(nroOrdenSalida, normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(producto.getNroGuiaRemision(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(producto.getNombreMovimiento(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(producto.getNombreEstado(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
				
				} else {
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);					
					
				}
				
				p = new Paragraph(producto.getNombreProducto(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(producto.getNombreUnidad(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getCantidad())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getPrecioUnitario())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getImporteTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				cantidadSubTotal = cantidadSubTotal.add(getBigDecimal(producto.getCantidad()));
				precioUnitarioSubTotal = precioUnitarioSubTotal.add(getBigDecimal(producto.getPrecioUnitario()));
				importeSubTotal = importeSubTotal.add(getBigDecimal(producto.getImporteTotal()));
				count++;
				
				ind_primero = false;

				document.add(table);
				
				// Subtotal del ultimo registro
				if (count == listaDetalleOrdenSalida.size() + 1) {
					
					table = new PdfPTable(11);
					table.setWidths(f11);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph("TOTAL ORDEN", negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(precioUnitarioSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(importeSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					document.add(table);
					
				}
				
				
			}
			// Bloque Fin
			
		} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * @param ruta
	 * @param ordenIngresoBean 
	 * @param listaOrdenIngreso
	 * @throws Exception 
	 */
	public void generaPDFReporteOrdenIngreso(String ruta, 
											 OrdenIngresoBean ordenIngresoBean, 
											 List<OrdenIngresoBean> listaOrdenIngreso) throws Exception {
		Document document = null;
		try {
			document = new Document(PageSize.A4.rotate(), 0, 0, 20, 20);
			PdfWriter.getInstance(document, new FileOutputStream(ruta));    
			
			document.open();
			 
			// Considerar que cada campo en array es una columna table de tu reporte
			float[] ftit = {20, 15, 65};			
			
			float[] f1 = {100};
			
			float[] f3 = {30, 30, 40};

			float[] f9 = {5, 10, 12, 13, 15, 15, 10, 10, 10};
			
			BaseColor header = new BaseColor(242, 242, 242);
			
			DecimalFormatSymbols dec_for_symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, dec_for_symbols);

			Paragraph p     = null;
			Paragraph pdet 	= null;
			PdfPTable table = null;
			PdfPCell cell   = null;

			Font titulo = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
			Font hide = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.WHITE);
			Font encabezado = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
			Font normal = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);			
			Font negrita = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
			   
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(ftit);
			
			String path = ruta.substring(0, ruta.indexOf(Constantes.REPORT_PATH_RESOURCES));			
			Image img = Image.getInstance(path.concat(Constantes.IMAGE_INDECI_REPORT_PATH));
			cell = new PdfPCell(img, true);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			StringBuilder det_encabezado = new StringBuilder();
			det_encabezado.append(Constantes.TITULO_ENCABEZADO_REPORTE);
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.FECHA_ENCABEZADO_REPORTE);
			Date fecha_hora = Calendar.getInstance().getTime();
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.HORA_ENCABEZADO_REPORTE);
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_HORA, fecha_hora));
			p = new Paragraph(det_encabezado.toString(), encabezado);
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE_PDF, hide);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(1);
			table.setWidths(f1);
			
			p = new Paragraph("REPORTE DE ORDENES DE INGRESO", titulo);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin			
			
			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("DDI: ", negrita);
			pdet = new Paragraph(listaOrdenIngreso.get(0).getNombreDdi(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("AÑO: ", negrita);
			pdet = new Paragraph(ordenIngresoBean.getCodigoAnio(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("ALMACEN: ", negrita);
			pdet = new Paragraph(listaOrdenIngreso.get(0).getNombreAlmacen(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("MES INICIO: ", negrita);
			pdet = new Paragraph(getMes(ordenIngresoBean.getCodigoMesInicio()), normal);
			p.add(pdet);			
			pdet = new Paragraph("   MES FIN: ", negrita);
			p.add(pdet);			
			pdet = new Paragraph(getMes(ordenIngresoBean.getCodigoMesFin()), normal);
			p.add(pdet);			
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin

			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	

			
			// Bloque Inicio
			table = new PdfPTable(9);
			table.setWidths(f9);
			
			p = new Paragraph("ITEM", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("FECHA EMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO ORDEN DE INGRESO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO ORDEN DE COMPRA", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("TIPO MOVIMIENTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("ORIGEN", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("IMPORTE TOTAL", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PESO TOTAL KGR", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("ESTADO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			int row = 1;
			
			for (OrdenIngresoBean ordenIngreso : listaOrdenIngreso) {
			
				table = new PdfPTable(9);
				table.setWidths(f9);
				
				p = new Paragraph(String.valueOf(row), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			
				p = new Paragraph(ordenIngreso.getFechaEmision(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(ordenIngreso.getNroOrdenIngreso(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(ordenIngreso.getNroOrdenCompra(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(ordenIngreso.getNombreMovimiento(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(ordenIngreso.getNombreAlmacenProcedencia(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(ordenIngreso.getImporteTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(ordenIngreso.getPesoTotalKgr())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(ordenIngreso.getNombreEstado(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				document.add(table);	             
				
				row++;
			}
			// Bloque Fin
			
		} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * @param ruta
	 * @param productoIngresoBean 
	 * @param listaDetalleOrdenIngreso
	 * @throws Exception 
	 */
	public void generaPDFReporteDetalleOrdenIngreso(String ruta, 
													ProductoIngresoBean productoIngresoBean, 
													List<ProductoIngresoBean> listaDetalleOrdenIngreso) throws Exception {
		Document document = null;
		try {
			document = new Document(PageSize.A4.rotate(), 0, 0, 20, 20);
			PdfWriter.getInstance(document, new FileOutputStream(ruta));    
			
			document.open();
			 
			// Considerar que cada campo en array es una columna table de tu reporte
			float[] ftit = {20, 15, 65};			
			
			float[] f1 = {100};
			
			float[] f3 = {30, 30, 40};

			float[] f11 = {5, 8, 10, 11, 10, 9, 15, 8, 8, 8, 8};
			
			BaseColor header = new BaseColor(242, 242, 242);

			Paragraph p     = null;
			Paragraph pdet 	= null;
			PdfPTable table = null;
			PdfPCell cell   = null;

			Font titulo = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
			Font hide = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.WHITE);
			Font encabezado = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
			Font normal = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);			
			Font negrita = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
			
			DecimalFormatSymbols dec_for_symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, dec_for_symbols);
			   
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(ftit);
			
			String path = ruta.substring(0, ruta.indexOf(Constantes.REPORT_PATH_RESOURCES));			
			Image img = Image.getInstance(path.concat(Constantes.IMAGE_INDECI_REPORT_PATH));
			cell = new PdfPCell(img, true);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			StringBuilder det_encabezado = new StringBuilder();
			det_encabezado.append(Constantes.TITULO_ENCABEZADO_REPORTE);
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.FECHA_ENCABEZADO_REPORTE);
			Date fecha_hora = Calendar.getInstance().getTime();
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.HORA_ENCABEZADO_REPORTE);
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_HORA, fecha_hora));
			p = new Paragraph(det_encabezado.toString(), encabezado);
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE_PDF, hide);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(1);
			table.setWidths(f1);
			
			StringBuilder det_titulo = new StringBuilder();
			det_titulo.append("REPORTE DE ORDENES DE INGRESO");
			det_titulo.append(Constantes.SALTO_LINEA_PARRAFO);
			det_titulo.append("DETALLE POR PRODUCTO");
			p = new Paragraph(det_titulo.toString(), titulo);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin			
			
			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("DDI: ", negrita);
			pdet = new Paragraph(listaDetalleOrdenIngreso.get(0).getNombreDdi(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("AÑO: ", negrita);
			pdet = new Paragraph(productoIngresoBean.getCodigoAnio(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("ALMACEN: ", negrita);
			pdet = new Paragraph(listaDetalleOrdenIngreso.get(0).getNombreAlmacen(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("MES INICIO: ", negrita);
			pdet = new Paragraph(getMes(productoIngresoBean.getCodigoMesInicio()), normal);
			p.add(pdet);			
			pdet = new Paragraph("   MES FIN: ", negrita);
			p.add(pdet);			
			pdet = new Paragraph(getMes(productoIngresoBean.getCodigoMesFin()), normal);
			p.add(pdet);			
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin

			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	

			
			// Bloque Inicio
			table = new PdfPTable(11);
			table.setWidths(f11);
			
			p = new Paragraph("ITEM", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("FECHA EMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO ORDEN INGRESO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO ORDEN COMPRA", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("TIPO MOVIMIENTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("ESTADO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PRODUCTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("UNIDAD", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("CANTIDAD", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PRECIO UNITARIO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("IMPORTE TOTAL", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			int row = 0;
			int count = 1;
			boolean ind_primero = false;
			BigDecimal cantidadSubTotal = BigDecimal.ZERO;
			BigDecimal precioUnitarioSubTotal = BigDecimal.ZERO;
			BigDecimal importeSubTotal = BigDecimal.ZERO;
			String nroOrdenIngreso = Constantes.EMPTY;
			for (ProductoIngresoBean producto : listaDetalleOrdenIngreso) {
			
				table = new PdfPTable(11);
				table.setWidths(f11);

				if (!nroOrdenIngreso.equals(producto.getNroOrdenIngreso())) {
					
					if (count > 1) {
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph("TOTAL ORDEN", negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(precioUnitarioSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(importeSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cantidadSubTotal = BigDecimal.ZERO;
						precioUnitarioSubTotal = BigDecimal.ZERO;
						importeSubTotal = BigDecimal.ZERO;
							
						document.add(table);
						
						table = new PdfPTable(11);
						table.setWidths(f11);
						
					}
						
					row++;
				
					nroOrdenIngreso = producto.getNroOrdenIngreso();
				
					p = new Paragraph(String.valueOf(row), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
				
					p = new Paragraph(producto.getFechaEmision(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(nroOrdenIngreso, normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(producto.getNroOrdenCompra(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(producto.getNombreMovimiento(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(producto.getNombreEstado(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
				
				} else {
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);					
					
				}
				
				p = new Paragraph(producto.getNombreProducto(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(producto.getNombreUnidad(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getCantidad())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getPrecioUnitario())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getImporteTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				cantidadSubTotal = cantidadSubTotal.add(getBigDecimal(producto.getCantidad()));
				precioUnitarioSubTotal = precioUnitarioSubTotal.add(getBigDecimal(producto.getPrecioUnitario()));
				importeSubTotal = importeSubTotal.add(getBigDecimal(producto.getImporteTotal()));
				count++;
				
				ind_primero = false;

				document.add(table);
				
				// Subtotal del ultimo registro
				if (count == listaDetalleOrdenIngreso.size() + 1) {
					
					table = new PdfPTable(11);
					table.setWidths(f11);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph("TOTAL ORDEN", negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(precioUnitarioSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(importeSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					document.add(table);
					
				}
				
				
			}
			// Bloque Fin
			
		} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * @param ruta
	 * @param guiaRemisionBean 
	 * @param listaGuiaRemision
	 * @throws Exception 
	 */
	public void generaPDFReporteGuiaRemision(String ruta, 
											 GuiaRemisionBean guiaRemisionBean, 
											 List<GuiaRemisionBean> listaGuiaRemision) throws Exception {
		Document document = null;
		try {
			document = new Document(PageSize.A4.rotate(), 0, 0, 20, 20);
			PdfWriter.getInstance(document, new FileOutputStream(ruta));    
			
			document.open();
			 
			// Considerar que cada campo en array es una columna table de tu reporte
			float[] ftit = {20, 15, 65};			
			
			float[] f1 = {100};
			
			float[] f3 = {30, 30, 40};

			float[] f9 = {5, 10, 12, 13, 15, 15, 10, 10, 10};
			
			BaseColor header = new BaseColor(242, 242, 242);
			
			DecimalFormatSymbols dec_for_symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, dec_for_symbols);

			Paragraph p     = null;
			Paragraph pdet 	= null;
			PdfPTable table = null;
			PdfPCell cell   = null;

			Font titulo = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
			Font hide = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.WHITE);
			Font encabezado = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
			Font normal = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);			
			Font negrita = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
			   
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(ftit);
			
			String path = ruta.substring(0, ruta.indexOf(Constantes.REPORT_PATH_RESOURCES));			
			Image img = Image.getInstance(path.concat(Constantes.IMAGE_INDECI_REPORT_PATH));
			cell = new PdfPCell(img, true);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			StringBuilder det_encabezado = new StringBuilder();
			det_encabezado.append(Constantes.TITULO_ENCABEZADO_REPORTE);
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.FECHA_ENCABEZADO_REPORTE);
			Date fecha_hora = Calendar.getInstance().getTime();
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.HORA_ENCABEZADO_REPORTE);
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_HORA, fecha_hora));
			p = new Paragraph(det_encabezado.toString(), encabezado);
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE_PDF, hide);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(1);
			table.setWidths(f1);
			
			p = new Paragraph("REPORTE DE GUIA DE REMISION", titulo);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin			
			
			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("DDI: ", negrita);
			pdet = new Paragraph(listaGuiaRemision.get(0).getNombreDdi(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("AÑO: ", negrita);
			pdet = new Paragraph(guiaRemisionBean.getCodigoAnio(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("ALMACEN: ", negrita);
			pdet = new Paragraph(listaGuiaRemision.get(0).getNombreAlmacen(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("MES INICIO: ", negrita);
			pdet = new Paragraph(getMes(guiaRemisionBean.getCodigoMesInicio()), normal);
			p.add(pdet);			
			pdet = new Paragraph("   MES FIN: ", negrita);
			p.add(pdet);			
			pdet = new Paragraph(getMes(guiaRemisionBean.getCodigoMesFin()), normal);
			p.add(pdet);			
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin

			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	

			
			// Bloque Inicio
			table = new PdfPTable(9);
			table.setWidths(f9);
			
			p = new Paragraph("ITEM", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("FECHA EMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO ORDEN SALIDA", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO GUIA REMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO MANIFIESTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("TIPO MOVIMIENTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("DESTINO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PESO TOTAL KGR", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("ESTADO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			int row = 1;
			
			for (GuiaRemisionBean guiaRemision : listaGuiaRemision) {
			
				table = new PdfPTable(9);
				table.setWidths(f9);
				
				p = new Paragraph(String.valueOf(row), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			
				p = new Paragraph(guiaRemision.getFechaEmision(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(guiaRemision.getNroOrdenSalida(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(guiaRemision.getNroGuiaRemision(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(guiaRemision.getNroManifiestoCarga(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(guiaRemision.getNombreMovimiento(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(guiaRemision.getNombreAlmacenDestino(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(guiaRemision.getPesoTotalKgr())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(guiaRemision.getNombreEstado(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				document.add(table);	             
				
				row++;
			}
			// Bloque Fin
			
		} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * @param ruta
	 * @param detalleGuiaRemisionBean 
	 * @param listaDetalleGuiaRemision
	 * @throws Exception 
	 */
	public void generaPDFReporteDetalleGuiaRemision(String ruta, 
													DetalleGuiaRemisionBean detalleGuiaRemisionBean, 
													List<DetalleGuiaRemisionBean> listaDetalleGuiaRemision) throws Exception {
		Document document = null;
		try {
			document = new Document(PageSize.A4.rotate(), 0, 0, 20, 20);
			PdfWriter.getInstance(document, new FileOutputStream(ruta));    
			
			document.open();
			 
			// Considerar que cada campo en array es una columna table de tu reporte
			float[] ftit = {20, 15, 65};			
			
			float[] f1 = {100};
			
			float[] f3 = {30, 30, 40};

			float[] f11 = {5, 8, 10, 11, 10, 8, 16, 8, 8, 8, 8};
			
			BaseColor header = new BaseColor(242, 242, 242);

			Paragraph p     = null;
			Paragraph pdet 	= null;
			PdfPTable table = null;
			PdfPCell cell   = null;

			Font titulo = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
			Font hide = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.WHITE);
			Font encabezado = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
			Font normal = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);			
			Font negrita = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
			
			DecimalFormatSymbols dec_for_symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat dec_form = new DecimalFormat(Constantes.EXPRESION_MONEDA, dec_for_symbols);
			   
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(ftit);
			
			String path = ruta.substring(0, ruta.indexOf(Constantes.REPORT_PATH_RESOURCES));			
			Image img = Image.getInstance(path.concat(Constantes.IMAGE_INDECI_REPORT_PATH));
			cell = new PdfPCell(img, true);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			StringBuilder det_encabezado = new StringBuilder();
			det_encabezado.append(Constantes.TITULO_ENCABEZADO_REPORTE);
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.FECHA_ENCABEZADO_REPORTE);
			Date fecha_hora = Calendar.getInstance().getTime();
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));
			det_encabezado.append(Constantes.SALTO_LINEA_PARRAFO);
			det_encabezado.append(Constantes.HORA_ENCABEZADO_REPORTE);
			det_encabezado.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_HORA, fecha_hora));
			p = new Paragraph(det_encabezado.toString(), encabezado);
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE_PDF, hide);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(1);
			table.setWidths(f1);
			
			StringBuilder det_titulo = new StringBuilder();
			det_titulo.append("REPORTE DE GUIA DE REMISION");
			det_titulo.append(Constantes.SALTO_LINEA_PARRAFO);
			det_titulo.append("DETALLE POR PRODUCTO");
			p = new Paragraph(det_titulo.toString(), titulo);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin			
			
			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("DDI: ", negrita);
			pdet = new Paragraph(listaDetalleGuiaRemision.get(0).getNombreDdi(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("AÑO: ", negrita);
			pdet = new Paragraph(detalleGuiaRemisionBean.getCodigoAnio(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			table = new PdfPTable(3);
			table.setWidths(f3);
			
			p = new Paragraph("ALMACEN: ", negrita);
			pdet = new Paragraph(listaDetalleGuiaRemision.get(0).getNombreAlmacen(), normal);
			p.add(pdet);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			p = new Paragraph("MES INICIO: ", negrita);
			pdet = new Paragraph(getMes(detalleGuiaRemisionBean.getCodigoMesInicio()), normal);
			p.add(pdet);			
			pdet = new Paragraph("   MES FIN: ", negrita);
			p.add(pdet);			
			pdet = new Paragraph(getMes(detalleGuiaRemisionBean.getCodigoMesFin()), normal);
			p.add(pdet);			
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(BaseColor.WHITE);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin

			
			document.add(new Paragraph(Constantes.ESPACIO)); // Salto de linea	

			
			// Bloque Inicio
			table = new PdfPTable(11);
			table.setWidths(f11);
			
			p = new Paragraph("ITEM", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("FECHA EMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO ORDEN SALIDA", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO GUIA REMISION", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("NRO MANIFIESTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("TIPO MOVIMIENTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("ESTADO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PRODUCTO", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("UNIDAD", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("CANTIDAD", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			p = new Paragraph("PESO TOTAL KGR", negrita);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(header);
			table.addCell(cell);
			
			document.add(table);
			// Bloque Fin
			
			
			// Bloque Inicio
			int row = 0;
			int count = 1;
			boolean ind_primero = false;
			BigDecimal cantidadSubTotal = BigDecimal.ZERO;
			BigDecimal pesoSubTotal = BigDecimal.ZERO;
			String nroGuiaRemision = Constantes.EMPTY;
			for (DetalleGuiaRemisionBean guia : listaDetalleGuiaRemision) {
			
				table = new PdfPTable(11);
				table.setWidths(f11);

				if (!nroGuiaRemision.equals(guia.getNroGuiaRemision())) {
					
					if (count > 1) {
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph("TOTAL ORDEN", negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cell = new PdfPCell();
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(pesoSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
						table.addCell(cell);
						
						cantidadSubTotal = BigDecimal.ZERO;
						pesoSubTotal = BigDecimal.ZERO;
							
						document.add(table);
						
						table = new PdfPTable(11);
						table.setWidths(f11);
						
					}
						
					row++;
				
					nroGuiaRemision = guia.getNroGuiaRemision();
				
					p = new Paragraph(String.valueOf(row), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
				
					p = new Paragraph(guia.getFechaEmision(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(guia.getNroOrdenSalida(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(nroGuiaRemision, normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(guia.getNroManifiestoCarga(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(guia.getNombreMovimiento(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					p = new Paragraph(guia.getNombreEstado(), normal);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					table.addCell(cell);
					
					ind_primero = true;
				
				} else {
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
				}
				
				p = new Paragraph(guia.getNombreProducto(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(guia.getUnidadMedida(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(guia.getCantidad())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(guia.getPesoTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (ind_primero) {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				} else {
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}
				table.addCell(cell);
				
				cantidadSubTotal = cantidadSubTotal.add(getBigDecimal(guia.getCantidad()));
				pesoSubTotal = pesoSubTotal.add(getBigDecimal(guia.getPesoTotal()));
				count++;
				
				ind_primero = false;

				document.add(table);
				
				// Subtotal del ultimo registro
				if (count == listaDetalleGuiaRemision.size() + 1) {
					
					table = new PdfPTable(11);
					table.setWidths(f11);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph("TOTAL ORDEN", negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(pesoSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
					document.add(table);
					
				}
				
				
			}
			// Bloque Fin
			
		} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * @param ruta
	 * @param listaKardexAlmacen
	 * @throws Exception 
	 */
	public void generaPDFReporteKardexAlmacen(String ruta, 
											  List<KardexAlmacenBean> listaKardexAlmacen) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param ruta
	 * @param listaBincardAlmacen
	 * @throws Exception 
	 */
	public void generaPDFReporteBincardAlmacen(String ruta, 
											   List<BincardAlmacenBean> listaBincardAlmacen) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param ruta
	 * @param proyectoManifiestoBean
	 * @param listaProyectoManifiesto
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaExcelReporteProyectoManifiesto(String ruta,
															 ProyectoManifiestoBean proyectoManifiestoBean, 
															 List<ProyectoManifiestoBean> listaProyectoManifiesto) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		try {				
	        HSSFSheet sheet = wb.createSheet("PROYECTO DE MANIFIESTO");
	        
	        sheet.setColumnWidth(0, 500);
	        sheet.setColumnWidth(1, 1500);
	        sheet.setColumnWidth(2, 2000);
	        sheet.setColumnWidth(3, 5000);
	        sheet.setColumnWidth(4, 5000);
	        sheet.setColumnWidth(5, 7000);
	        sheet.setColumnWidth(6, 5000);
			sheet.setColumnWidth(7, 7000);
			sheet.setColumnWidth(8, 6000);
			sheet.setColumnWidth(9, 6000);
			
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 9));
	        
			
	        HSSFFont font_bold = wb.createFont();
	        font_bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        
	        HSSFFont font_norm = wb.createFont();
	        font_norm.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

	        HSSFCellStyle style_cabecera = (HSSFCellStyle) wb.createCellStyle();
	        style_cabecera.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
	        style_cabecera.setFont(font_norm);
	        
	        HSSFCellStyle style_tit_cabecera = (HSSFCellStyle) wb.createCellStyle();
	        style_tit_cabecera.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style_tit_cabecera.setFont(font_bold);     
	        
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
	        
	        HSSFCellStyle style_cell = (HSSFCellStyle) wb.createCellStyle();
	        style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        style_cell.setFont(font_norm);	        
	        style_cell.setBorderBottom((short) 1);
	        style_cell.setBorderLeft((short) 1);	        
	        style_cell.setBorderRight((short) 1);
	        style_cell.setBorderTop((short) 1);
	        
	        
	        // Bloque Inicio
	        String path = ruta.substring(0, ruta.indexOf(Constantes.REPORT_PATH_RESOURCES));
	        InputStream is = new FileInputStream(path.concat(Constantes.IMAGE_INDECI_REPORT_PATH));
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			is.close();
			CreationHelper helper = wb.getCreationHelper();
			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor anchor = helper.createClientAnchor();
			anchor.setCol1(1);
			anchor.setRow1(1);
			Picture pict = drawing.createPicture(anchor, pictureIdx);
			pict.resize();
	        
			HSSFRow row1 = sheet.createRow((short) 1);
			row1.createCell(9).setCellValue(Constantes.TITULO_ENCABEZADO_REPORTE);
	        row1.getCell(9).setCellStyle(style_cabecera);
	        
	        HSSFRow row2 = sheet.createRow((short) 2);
	        StringBuilder det_fecha = new StringBuilder();
	        Date fecha_hora = Calendar.getInstance().getTime();
	        det_fecha.append(Constantes.FECHA_ENCABEZADO_REPORTE);
	        det_fecha.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_FECHA, fecha_hora));	        
			row2.createCell(9).setCellValue(det_fecha.toString());
	        row2.getCell(9).setCellStyle(style_cabecera);
	        
	        StringBuilder det_hora = new StringBuilder();
	        det_hora.append(Constantes.HORA_ENCABEZADO_REPORTE);
	        det_hora.append(DateUtil.obtenerFechaFormateada(Constantes.FORMATO_HORA, fecha_hora));
	        det_hora.append(Constantes.ESPACIO_ENCABEZADO_REPORTE_EXCEL);
	        HSSFRow row3 = sheet.createRow((short) 3);
			row3.createCell(9).setCellValue(det_hora.toString());
	        row3.getCell(9).setCellStyle(style_cabecera);
			// Bloque Fin
			
			
	        // Bloque Inicio
	        HSSFRow row5 = sheet.createRow((short) 5);
			row5.createCell(1).setCellValue("REPORTE DE PROYECTO DE MANIFIESTO");
	        row5.getCell(1).setCellStyle(style_tit_cabecera);	
	        // Bloque Fin
			
	        
	        // Bloque Inicio
	        HSSFRow row7 = sheet.createRow((short) 7);
	        
	        HSSFRichTextString for_rep_ddi = new HSSFRichTextString("DDI: ");
	        HSSFRichTextString for_des_ddi = new HSSFRichTextString(listaProyectoManifiesto.get(0).getNombreDdi());
	        RichTextString det_ddi = new HSSFRichTextString(for_rep_ddi.getString() + for_des_ddi.getString());
	        int tam_ddi = listaProyectoManifiesto.get(0).getNombreDdi().length();
	        det_ddi.applyFont(0, 5, font_bold);
	        det_ddi.applyFont(5, tam_ddi + 5, font_norm);	        
			row7.createCell(1).setCellValue(det_ddi);	
			
			HSSFRichTextString for_rep_anio = new HSSFRichTextString("AÑO: ");
	        HSSFRichTextString for_des_anio = new HSSFRichTextString(proyectoManifiestoBean.getCodigoAnio());
	        RichTextString det_anio = new HSSFRichTextString(for_rep_anio.getString() + for_des_anio.getString());
	        int tam_anio = proyectoManifiestoBean.getCodigoAnio().length();
	        det_anio.applyFont(0, 5, font_bold);
	        det_anio.applyFont(5, tam_anio + 5, font_norm);	        
			row7.createCell(4).setCellValue(det_anio);	
	        // Bloque Fin
	        
	        
	        
	        
	        
			// Bloque Inicio	        
	        HSSFRow row10 = sheet.createRow((short) 10);
	        
	        row10.createCell(1).setCellValue("ITEM");
	        row10.getCell(1).setCellStyle(style_header);
	        
	        row10.createCell(2).setCellValue("FECHA EMISION");
	        row10.getCell(2).setCellStyle(style_header);
	        
	        row10.createCell(3).setCellValue("NRO PROY. DE MANIFIESTO");
	        row10.getCell(3).setCellStyle(style_header);
	        
	        row10.createCell(4).setCellValue("Almacén");
	        row10.getCell(4).setCellStyle(style_header);
	        
	        row10.createCell(5).setCellValue("N° Orden de Salida");
	        row10.getCell(5).setCellStyle(style_header);
	        
	        row10.createCell(6).setCellValue("Fecha");
	        row10.getCell(6).setCellStyle(style_header);
	        
	        row10.createCell(7).setCellValue("Tipo de Movimiento");
	        row10.getCell(7).setCellStyle(style_header);
	        
	        row10.createCell(8).setCellValue("N° Guia de Remision");
	        row10.getCell(8).setCellStyle(style_header);
	        
	        row10.createCell(9).setCellValue("Estado");
	        row10.getCell(9).setCellStyle(style_header);
	        // Bloque Fin
	       
	        
	        // Bloque Inicio
	        int row = 11;
	        int count = 1;
	        for (ProyectoManifiestoBean proyecto : listaProyectoManifiesto) {
	        	
	        	HSSFRow rows = sheet.createRow((short) row + 1);
	        	
	        	rows.createCell(1).setCellValue(count);
		        rows.getCell(1).setCellStyle(style_cell);

		        rows.createCell(2).setCellValue(proyecto.getFechaEmision());
		        rows.getCell(2).setCellStyle(style_cell);
		        
//		        rows.createCell(3).setCellValue(proyecto.getNombreMes());
//		        rows.getCell(3).setCellStyle(style_cell);
//		        
//		        rows.createCell(4).setCellValue(proyecto.getNombreAlmacen());
//		        rows.getCell(4).setCellStyle(style_cell);
//		        
//		        rows.createCell(5).setCellValue(ingreso.getNroOrdenSalida());
//		        rows.getCell(5).setCellStyle(style_cell);
//		        
//		        rows.createCell(6).setCellValue(ingreso.getFechaEmision());
//		        rows.getCell(6).setCellStyle(style_cell);
//		        
//		        rows.createCell(7).setCellValue(ingreso.getNombreMovimiento());
//		        rows.getCell(7).setCellStyle(style_cell);
//		        
//		        rows.createCell(8).setCellValue(ingreso.getNroGuiaRemision());
//		        rows.getCell(8).setCellStyle(style_cell);
//		        
//		        rows.createCell(9).setCellValue(ingreso.getNombreEstado());
//		        rows.getCell(9).setCellStyle(style_cell);
	            
	            row++;
	            count++;
	        }
	        // Bloque Fin
			
    	} catch(Exception e) {
    		LOGGER.error(e);
    		throw new Exception();
    	}
		return wb;
	}

	/**
	 * @param ruta
	 * @param productoProyectoManifiestoBean
	 * @param listaDetalleProyectoManifiesto
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaExcelReporteDetalleProyectoManifiesto(String ruta,
																	ProductoProyectoManifiestoBean productoProyectoManifiestoBean,
																	List<ProductoProyectoManifiestoBean> listaDetalleProyectoManifiesto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param ruta
	 * @param ordenSalidaBean
	 * @param listaOrdenSalida
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaExcelReporteOrdenSalida(String ruta, 
													  OrdenSalidaBean ordenSalidaBean,
													  List<OrdenSalidaBean> listaOrdenSalida) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param ruta
	 * @param productoSalidaBean
	 * @param listaDetalleOrdenSalida
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaExcelReporteDetalleOrdenSalida(String ruta, 
															 ProductoSalidaBean productoSalidaBean,
															 List<ProductoSalidaBean> listaDetalleOrdenSalida) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param ruta
	 * @param ordenIngresoBean
	 * @param listaOrdenIngreso
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaExcelReporteOrdenIngreso(String ruta, 
													   OrdenIngresoBean ordenIngresoBean,
													   List<OrdenIngresoBean> listaOrdenIngreso) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param ruta
	 * @param productoIngresoBean
	 * @param listaDetalleOrdenIngreso
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaExcelReporteDetalleOrdenIngreso(String ruta, 
															  ProductoIngresoBean productoIngresoBean,
															  List<ProductoIngresoBean> listaDetalleOrdenIngreso) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param ruta
	 * @param guiaRemisionBean
	 * @param listaGuiaRemision
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaExcelReporteGuiaRemision(String ruta, 
													   GuiaRemisionBean guiaRemisionBean,
													   List<GuiaRemisionBean> listaGuiaRemision) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param ruta
	 * @param detalleGuiaRemisionBean
	 * @param listaDetalleGuiaRemision
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaExcelReporteDetalleGuiaRemision(String ruta,
															  DetalleGuiaRemisionBean detalleGuiaRemisionBean, 
															  List<DetalleGuiaRemisionBean> listaDetalleGuiaRemision) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param ruta
	 * @param listaKardexAlmacen
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaExcelReporteKardexAlmacen(String ruta, 
														List<KardexAlmacenBean> listaKardexAlmacen) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param ruta
	 * @param listaBincardAlmacen
	 * @return Objeto.
	 * @throws Exception 
	 */
	public HSSFWorkbook generaExcelReporteBincardAlmacen(String ruta, 
														 List<BincardAlmacenBean> listaBincardAlmacen) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
    
}
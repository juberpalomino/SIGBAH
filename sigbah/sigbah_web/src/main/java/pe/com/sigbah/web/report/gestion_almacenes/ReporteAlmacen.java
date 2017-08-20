package pe.com.sigbah.web.report.gestion_almacenes;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

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
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE, hide);
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

			float[] f11 = {5, 10, 10, 11, 10, 9, 15, 7, 8, 7, 8};
			
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
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE, hide);
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
						table.addCell(cell);
						
						cell = new PdfPCell();
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(volumenSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(pesoSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
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
				table.addCell(cell);
				
				p = new Paragraph(proyecto.getNombreUnidad(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(proyecto.getCantidad())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(proyecto.getVolumenTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(proyecto.getPesoTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				cantidadSubTotal = cantidadSubTotal.add(getBigDecimal(proyecto.getCantidad()));
				volumenSubTotal = volumenSubTotal.add(getBigDecimal(proyecto.getVolumenTotal()));
				pesoSubTotal = pesoSubTotal.add(getBigDecimal(proyecto.getPesoTotal()));
				count++;

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
					table.addCell(cell);
					
					cell = new PdfPCell();
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(volumenSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(pesoSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
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
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE, hide);
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
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE, hide);
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
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(precioUnitarioSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(importeSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
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
				table.addCell(cell);
				
				p = new Paragraph(producto.getNombreUnidad(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getCantidad())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getPrecioUnitario())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getImporteTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				cantidadSubTotal = cantidadSubTotal.add(getBigDecimal(producto.getCantidad()));
				precioUnitarioSubTotal = precioUnitarioSubTotal.add(getBigDecimal(producto.getPrecioUnitario()));
				importeSubTotal = importeSubTotal.add(getBigDecimal(producto.getImporteTotal()));
				count++;

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
					table.addCell(cell);
					
					cell = new PdfPCell();
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(precioUnitarioSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(importeSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
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
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE, hide);
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
			pdet = new Paragraph(Constantes.ESPACIO_ENCABEZADO_REPORTE, hide);
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
						table.addCell(cell);
						
						cell = new PdfPCell();
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(precioUnitarioSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);
						
						p = new Paragraph(dec_form.format(importeSubTotal), negrita);
						cell = new PdfPCell(p);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
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
				table.addCell(cell);
				
				p = new Paragraph(producto.getNombreUnidad(), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getCantidad())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getPrecioUnitario())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				p = new Paragraph(dec_form.format(getBigDecimal(producto.getImporteTotal())), normal);
				cell = new PdfPCell(p);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				cantidadSubTotal = cantidadSubTotal.add(getBigDecimal(producto.getCantidad()));
				precioUnitarioSubTotal = precioUnitarioSubTotal.add(getBigDecimal(producto.getPrecioUnitario()));
				importeSubTotal = importeSubTotal.add(getBigDecimal(producto.getImporteTotal()));
				count++;

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
					table.addCell(cell);
					
					cell = new PdfPCell();
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(cantidadSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(precioUnitarioSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					
					p = new Paragraph(dec_form.format(importeSubTotal), negrita);
					cell = new PdfPCell(p);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param ruta
	 * @param listaKardexAlmacen
	 * @throws Exception 
	 */
	public void generaPDFReporteKardexAlmacen(String ruta, List<KardexAlmacenBean> listaKardexAlmacen) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param ruta
	 * @param listaBincardAlmacen
	 * @throws Exception 
	 */
	public void generaPDFReporteBincardAlmacen(String ruta, List<BincardAlmacenBean> listaBincardAlmacen) throws Exception {
		// TODO Auto-generated method stub
		
	}
    
}
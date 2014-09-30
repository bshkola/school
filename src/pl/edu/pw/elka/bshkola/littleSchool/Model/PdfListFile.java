/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Богдан
 *
 */
public class PdfListFile {

	private final String filePath = "C:" + File.separator + "Users" + File.separator + "Богдан" + File.separator + "Documents" + File.separator + "123.pdf";
	private final Document document;
	private final BaseFont specialFont = BaseFont.createFont("c:/windows/fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);


	public PdfListFile(final String[] listOfGroups, final Group[] group) throws FileNotFoundException, DocumentException, IOException {
		new Font(specialFont, 13, Font.BOLD, new BaseColor(0, 0, 0));
		new Font(specialFont, 18, Font.BOLD, new BaseColor(0, 0, 0));
		
		document = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(this.filePath));
		
		document.open();

		addData(listOfGroups, group, writer);
 		document.close();
		
	}

	private void addData(String[] listOfGroups, Group[] group, PdfWriter writer) {
		
		document.addTitle("Список дітей Маленької школи");
		document.addAuthor("Ірина Школа");
		document.addCreator("Богдан Школа");
		document.addCreationDate();
		/*
		PdfContentByte canvas = writer.getDirectContent();
		String text = "Text-Текст";
		canvas.beginText();
		canvas.setFontAndSize(specialFont, 16);
		canvas.moveText(36, 806);
		canvas.moveTextWithLeading(0, -24);
		canvas.showText(text);
		canvas.newlineText();
		canvas.saveState();
		canvas.endText();*/
	}

}

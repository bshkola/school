package pl.edu.pw.elka.bshkola.littleSchool.Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfBirthFile {

	private final Document document;
	private String filePath;
	private final String[] namesOfHeaders = { "Група", "Прізвище та ім'я",
			"Дата народж." };
	private final int numberOfHeaders = namesOfHeaders.length;
	private static BaseFont specialFont;
	private static Font monthFont;
	private static Font headerFont;
	private static Font dataFont;

	// private final String filePath = "E:\\/pdf.pdf";

	private final String[] listOfMonthes = { "Січень", "Лютий", "Березень",
			"Квітень", "Травень", "Червень", "Липень", "Серпень", "Вересень",
			"Жовтень", "Листопад", "Грудень" };

	public PdfBirthFile(final int monthIndex, final List<DataModel> list,
			final String filePath) throws DocumentException, IOException {

		specialFont = BaseFont.createFont("c:/windows/fonts/times.ttf",
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		headerFont = new Font(specialFont, 12, Font.BOLD,
				new BaseColor(0, 0, 0));
		dataFont = new Font(specialFont, 12, Font.NORMAL,
				new BaseColor(0, 0, 0));
		monthFont = new Font(specialFont, 17, Font.BOLD, new BaseColor(0, 0, 0));
		document = new Document(PageSize.A4, 100, 100, 30, 30);

		this.filePath = filePath;
		this.filePath = this.filePath.replaceAll("!!!",
				listOfMonthes[monthIndex - 1]);

		PdfWriter.getInstance(document, new FileOutputStream(new File(
				this.filePath)));

		document.open();
		addData(monthIndex, list);
		document.close();

	}

	private void addData(final int monthIndex, final List<DataModel> list)
			throws DocumentException {

		document.addTitle("Дні народження в місяці: "
				+ listOfMonthes[monthIndex - 1]);
		document.addAuthor("Ірина Школа");
		document.addCreator("Богдан Школа");
		document.addCreationDate();

		Paragraph paragraph = new Paragraph(
				listOfMonthes[monthIndex - 1] + ":", monthFont);
		document.add(paragraph);
		paragraph = new Paragraph(" ");
		paragraph.setAlignment(Element.ALIGN_LEFT);

		PdfPTable table = new PdfPTable(numberOfHeaders);
		table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 8, 11, 5 });
		PdfPCell[] headers = new PdfPCell[numberOfHeaders];
		for (int j = 0; j < numberOfHeaders; j++) {
			headers[j] = new PdfPCell(new Paragraph(namesOfHeaders[j],
					headerFont));
			headers[j].setHorizontalAlignment(Element.ALIGN_LEFT);
			headers[j].setVerticalAlignment(Element.ALIGN_CENTER);
			// headers[j].setBorderColor(BaseColor.WHITE);
			table.addCell(headers[j]);
		}

		PdfPCell cell;
		for (DataModel dataModel : list) {
			cell = new PdfPCell(new Paragraph(dataModel.getGroup(), dataFont));
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(dataModel.getSurname() + " "
					+ dataModel.getName(), dataFont));
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(dataModel.getDateOfBirth(),
					dataFont));
			table.addCell(cell);
		}

		document.add(paragraph);
		document.add(table);
		// document.newPage();

	}

	public String getFilePath() {
		return this.filePath;
	}
}

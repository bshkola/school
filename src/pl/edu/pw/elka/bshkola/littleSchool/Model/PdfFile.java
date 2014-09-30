/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

/**
 * @author bogdan
 * 
 */
public class PdfFile {

	private final Document document;
	private String filePath;

	private final Font groupFont;
	private final Font dataFont;

	private final String[] namesOfHeaders = { "№", "Прізвище та ім'я",
			"Дата народж.", "Телефон", "Адреса", "Мати", "Батько" };
	private final int numberOfHeaders = namesOfHeaders.length;
	private final BaseFont specialFont = BaseFont.createFont(
			"c:/windows/fonts/times.ttf", BaseFont.IDENTITY_H,
			BaseFont.EMBEDDED);

	public PdfFile(final String filePath, final String[] listOfGroups,
			final Group[] group, final String actualDatabaseName)
			throws DocumentException, IOException {

		dataFont = new Font(specialFont, 12, Font.NORMAL,
				new BaseColor(0, 0, 0));
		groupFont = new Font(specialFont, 17, Font.BOLD, new BaseColor(0, 0, 0));
		document = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);

		this.filePath = filePath;
		this.filePath = this.filePath.replaceAll("!!-!!", actualDatabaseName);

		PdfWriter.getInstance(document, new FileOutputStream(new File(
				this.filePath)));

		document.open();
		addData(listOfGroups, group);
		document.close();

	}

	private void addData(final String[] listOfGroups, final Group[] group)
			throws DocumentException {

		document.addTitle("Список дітей Маленької школи");
		document.addAuthor("Ірина Школа");
		document.addCreator("Богдан Школа");
		document.addCreationDate();

		for (int i = 0; i < group.length; i++) {
			Paragraph paragraph = new Paragraph("Група: "
					+ group[i].getNameOfGroup() + "\n\n", groupFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);

			PdfPTable table = new PdfPTable(numberOfHeaders);
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1.3f, 11, 4, 8, 9, 11, 11 });
			PdfPCell[] headers = new PdfPCell[numberOfHeaders];

			for (int j = 0; j < numberOfHeaders; j++) {
				headers[j] = new PdfPCell(new Paragraph(namesOfHeaders[j],
						dataFont));
				headers[j].setHorizontalAlignment(Element.ALIGN_CENTER);
				headers[j].setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(headers[j]);
			}

			java.util.List<DataModel> dataModels = group[i].getDataFromGroup()
					.getListOfData();
			for (DataModel dataModel : dataModels) {
				table.addCell(new PdfPCell(new Paragraph(dataModel.getNumber(),
						dataFont)));
				table.addCell(new PdfPCell(new Paragraph(dataModel.getSurname()
						+ " " + dataModel.getName(), dataFont)));
				table.addCell(new PdfPCell(new Paragraph(dataModel
						.getDateOfBirth(), dataFont)));

				String telephones = dataModel.getTelephones()[0];
				for (int j = 1; j < 3; j++) {
					if (dataModel.getTelephones()[j].equals("")) {
						break;
					}
					telephones = telephones.concat("; "
							+ dataModel.getTelephones()[j]);
				}

				table.addCell(new PdfPCell(new Paragraph(telephones, dataFont)));
				table.addCell(new PdfPCell(new Paragraph(
						dataModel.getAddress(), dataFont)));
				table.addCell(new PdfPCell(new Paragraph(dataModel.getMother(),
						dataFont)));
				table.addCell(new PdfPCell(new Paragraph(dataModel.getFather(),
						dataFont)));
			}

			document.add(paragraph);
			document.add(table);
			document.newPage();
		}
	}

	public String getFilePath() {
		return this.filePath;
	}

}

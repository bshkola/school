/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import pl.edu.pw.elka.bshkola.littleSchool.Common.DataInGroupModel;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;

import com.itextpdf.text.DocumentException;

/**
 * @author bogdan
 * 
 */
class Database implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5617884329340957484L;

	private final Group[] group;
	private final String[] listOfGroups = { "Всі групи", "Ромашки", "Сніжинки",
			"Зірочки", "Барвінки", "Дзвіночки", "Волошки", "Зайчики",
			"Метелики", "Сонечка", "Білочки", "Бджілки", "Без групи" };

	public Database() {

		group = new Group[listOfGroups.length - 1];
		for (int i = 1; i < listOfGroups.length; i++) {
			group[i - 1] = new Group(listOfGroups[i]);
		}
	}

	public void addData(final DataModel dataModel) {
		searchGroup(dataModel.getGroup()).addData(dataModel);
	}

	public String[] getListOfGroups() {
		return listOfGroups;
	}

	public DataInGroupModel[] getDataFromGroup() {
		// if (nameOfGroup.equals(listOfGroups[0])) {
		DataInGroupModel[] dataInGroup = new DataInGroupModel[listOfGroups.length - 1];
		for (int i = 0; i < listOfGroups.length - 1; i++) {
			dataInGroup[i] = group[i].getDataFromGroup();
		}
		return dataInGroup;
		// } else {
		// DataInGroupModel[] dataInGroup = new DataInGroupModel[1];
		// dataInGroup[0] = searchGroup(nameOfGroup).getDataFromGroup();
		// return dataInGroup;
		// }
	}

	private Group searchGroup(final String nameOfGroup) {
		for (int i = 0; i < group.length; i++) {
			if (nameOfGroup.equals(group[i].getNameOfGroup())) {
				return group[i];
			}
		}
		return null;
	}

	public void deleteData(final String surname_name, final String nameOfGroup) {
		searchGroup(nameOfGroup).deleteData(surname_name);
	}

	public DataModel getData(final String surname_name, final String nameOfGroup) {
		return searchGroup(nameOfGroup).getData(surname_name);
	}

	public List<DataModel> searchData(final String searchingText) {

		List<DataModel> listOfSearchingData = new LinkedList<DataModel>();
		for (int i = 1; i < listOfGroups.length; i++) {
			group[i - 1].searchDataInGroup(searchingText, listOfSearchingData);
		}

		return listOfSearchingData;
	}

	public List<DataModel> searchDataByMonth(final int monthIndex) {
		List<DataModel> listOfSearchingData = new LinkedList<DataModel>();
		for (int i = 1; i < listOfGroups.length; i++) {
			group[i - 1].searchDataInGroupByMonth(monthIndex,
					listOfSearchingData);
		}
		Collections.sort(listOfSearchingData, new Comparator<DataModel>() {

			@Override
			public int compare(final DataModel o1, final DataModel o2) {
				return o1.getDateOfBirth().substring(0, 2)
						.compareTo(o2.getDateOfBirth().substring(0, 2));
			}
		});

		return listOfSearchingData;
	}

	public String createPdfFullBaseFile(final String filePath,
			final String actualDatabaseName) throws DocumentException,
			IOException {
		return new PdfFile(filePath, listOfGroups, group, actualDatabaseName)
				.getFilePath();
	}

	public void createPdfListFile() throws FileNotFoundException,
			DocumentException, IOException {
		new PdfListFile(listOfGroups, group);
	}

	public String createBirthFile(final int monthIndex, final String filePath)
			throws IOException, DocumentException {
		return new PdfBirthFile(monthIndex, searchDataByMonth(monthIndex),
				filePath).getFilePath();
	}
}

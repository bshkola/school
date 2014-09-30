/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pl.edu.pw.elka.bshkola.littleSchool.Common.DataInGroupModel;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;

/**
 * @author bogdan
 * 
 */
class Group implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6917909349997743979L;

	private final String nameOfGroup;
	private final List<DataLine> listOfDataLines;

	public Group(final String nameOfGroup) {
		this.nameOfGroup = nameOfGroup;
		listOfDataLines = new LinkedList<DataLine>();
	}

	public String getNameOfGroup() {
		return nameOfGroup;
	}

	public void addData(final DataModel dataModel) {
		listOfDataLines.add(new DataLine(dataModel));
		Collections.sort(listOfDataLines);
		printList(listOfDataLines);
	}

	private void printList(final List<DataLine> listOfDataLines2) {

		// System.out.println("surname\tName");
		// for (DataLine dataLine: listOfDataLines2) {
		// System.out.println(dataLine.getSurname() + "\t" +
		// dataLine.getName());
		// }

	}

	public DataInGroupModel getDataFromGroup() {

		List<DataModel> listOfDataModel = new LinkedList<DataModel>();
		listOfDataModel = transform();
		DataInGroupModel dataInGroup = new DataInGroupModel(nameOfGroup,
				listOfDataModel);

		return dataInGroup;
	}

	private List<DataModel> transform() {

		LinkedList<DataModel> dataModels = new LinkedList<DataModel>();
		Iterator<DataLine> iter = listOfDataLines.iterator();
		int i = 1;
		while (iter.hasNext()) {
			DataLine dataLine = iter.next();
			dataModels.add(dataLine.writeDataToModel(i));
			i++;
		}
		return dataModels;

		// DataLine[] dataLines = new DataLine[listOfDataLines.size()];
		// listOfDataLines.toArray(dataLines);
		//
		// String[][] data = new String[dataLines.length][8];
		// for (int i = 0; i < dataLines.length; i++)
		// {
		// dataLines[i].writeDataToArray(data, i);
		// }
		// return data;
	}

	public void deleteData(final String surname_name) {
		Iterator<DataLine> iter = listOfDataLines.iterator();
		while (iter.hasNext()) {
			DataLine dataLine = iter.next();
			if (dataLine.checkData(surname_name) == true) {
				listOfDataLines.remove(dataLine);
				return;
			}
		}
	}

	public DataModel getData(final String surname_name) {
		Iterator<DataLine> iter = listOfDataLines.iterator();
		while (iter.hasNext()) {
			DataLine dataLine = iter.next();
			if (dataLine.checkData(surname_name) == true) {
				return dataLine.writeDataToModel(nameOfGroup);
			}
		}
		return null;
	}

	public void searchDataInGroup(final String searchingText,
			final List<DataModel> listOfSearchingData) {

		for (DataLine dataLine : listOfDataLines) {
			if (dataLine.getSurname().toLowerCase()
					.startsWith(searchingText.toLowerCase())
					|| dataLine.getName().toLowerCase()
							.startsWith(searchingText.toLowerCase())
					|| dataLine.getTelephone()[0].toLowerCase().startsWith(
							searchingText.toLowerCase())) { // TODO CHANGE TO
															// MANY TELEPHONES

				listOfSearchingData.add(dataLine.writeDataToModel(nameOfGroup));
			}
		}
	}

	public void searchDataInGroupByMonth(final int monthIndex,
			final List<DataModel> listOfSearchingData) {
		for (DataLine dataLine : listOfDataLines) {
			if (monthIndex < 10) {
				// System.out.println(dataLine.getDateOfBirth() + "|");
				if (dataLine.getDateOfBirth().substring(3, 5)
						.equals("0" + new Integer(monthIndex).toString())) {
					listOfSearchingData.add(dataLine
							.writeDataToModel(nameOfGroup));
				}
			} else {
				if (dataLine.getDateOfBirth().substring(3, 5)
						.equals(new Integer(monthIndex).toString())) {
					listOfSearchingData.add(dataLine
							.writeDataToModel(nameOfGroup));
				}
			}
		}
	}

}
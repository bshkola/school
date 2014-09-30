/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Model;

import java.io.Serializable;

import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;

/**
 * @author bogdan
 * 
 */
class DataLine implements Serializable, Comparable<DataLine> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7681935930519163378L;
	private String surname;
	private String name;
	private String dateOfBirth;
	private String[] telephones;
	private String address;
	private String mother;
	private String father;
	private static UkrainianStringMap ukr = new UkrainianStringMap();

	public DataLine(final DataModel dataModel) {

		surname = dataModel.getSurname();
		name = dataModel.getName();
		dateOfBirth = dataModel.getDateOfBirth();
		telephones = dataModel.getTelephones();
		address = dataModel.getAddress();
		mother = dataModel.getMother();
		father = dataModel.getFather();

	}

	public DataModel writeDataToModel(final int i) {

		return new DataModel(i, surname, name, dateOfBirth, telephones,
				address, mother, father);

	}

	public DataModel writeDataToModel(final String nameOfGroup) {

		return new DataModel(nameOfGroup, surname, name, dateOfBirth,
				telephones, address, mother, father);

	}

	@Override
	public int compareTo(final DataLine anotherDataLine) {
		/*
		 * int condition = this.surname.compareToIgnoreCase(anotherDataLine
		 * .getSurname()); if (condition != 0) { return condition; } else {
		 * return this.name.compareToIgnoreCase(anotherDataLine.getName()); }
		 */

		return compareIgnoreCase(this.surname + " " + this.name,
				anotherDataLine.surname + " " + anotherDataLine.name);

	}

	private int compareIgnoreCase(final String string1, final String string2) {

		final int loop = Math.min(string1.length(), string2.length());
		for (int i = 0; i < loop; i++) {
			int letter1 = ukr.getUkrainianLetterValue(string1.substring(i,
					i + 1));
			int letter2 = ukr.getUkrainianLetterValue(string2.substring(i,
					i + 1));
			if (letter1 != letter2) {
				return letter1 - letter2;
			}
		}
		if (string1.length() < string2.length()) {
			return 1;
		}
		if (string1.length() > string2.length()) {
			return -1;
		}
		return 0;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}

	public String[] getTelephone() {
		return telephones;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public boolean checkData(final String surname_name) {
		if ((this.surname + " " + this.name).equalsIgnoreCase(surname_name)) {
			return true;
		} else {
			return false;
		}
	}

}

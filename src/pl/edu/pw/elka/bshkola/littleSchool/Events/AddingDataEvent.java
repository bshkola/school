/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Events;

/**
 * @author bogdan
 * 
 */
public class AddingDataEvent extends Event {

	private String surname;
	private String name;
	private String dateOfBirth;
	private String[] telephones;
	private String address;
	private String mother;
	private String father;
	private String group;

	public AddingDataEvent(final String surname, final String name,
			final String dateOfBirth, final String[] telephones,
			final String address, final String mother, final String father,
			final String group) {

		this.surname = surname;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.telephones = telephones;
		this.address = address;
		this.mother = mother;
		this.father = father;
		this.group = group;

	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String[] getTelephone() {
		return telephones;
	}

	public String getAddress() {
		return address;
	}

	public String getMother() {
		return mother;
	}

	public String getFather() {
		return father;
	}

	public String getGroup() {
		return group;
	}

}

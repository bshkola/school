/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Events;

/**
 * @author bogdan
 * 
 */
public class EditingDataEvent extends Event {

	private final String oldSurname;
	private final String oldName;
	private final String oldGroup;
	private final String surname;
	private final String name;
	private final String dateOfBirth;
	private final String[] telephones;
	private final String address;
	private final String mother;
	private final String father;
	private final String group;

	public EditingDataEvent(final String oldSurname, final String oldName,
			final String oldGroup, final String surname, final String name,
			final String dateOfBirth, final String[] telephones,
			final String address, final String mother, final String father,
			final String group) {

		this.oldSurname = oldSurname;
		this.oldName = oldName;
		this.oldGroup = oldGroup;
		this.surname = surname;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.telephones = telephones;
		this.address = address;
		this.mother = mother;
		this.father = father;
		this.group = group;

	}

	public String getOldSurname() {
		return oldSurname;
	}

	public String getOldName() {
		return oldName;
	}

	public String getOldGroup() {
		return oldGroup;
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

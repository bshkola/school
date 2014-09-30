/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Events;

/**
 * @author bogdan
 * 
 */
public class EditingDataClickedEvent extends Event {

	private final String surname_name;
	private final String nameOfGroup;

	public EditingDataClickedEvent(final String surname_name,
			final String nameOfGroup) {
		this.surname_name = surname_name;
		this.nameOfGroup = nameOfGroup;
	}

	public String getSurnameName() {
		return surname_name;
	}

	public String getNameOfGroup() {
		return nameOfGroup;
	}

}

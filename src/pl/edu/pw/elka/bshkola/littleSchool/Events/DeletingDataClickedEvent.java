/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Events;

/**
 * @author Богдан
 * 
 */
public class DeletingDataClickedEvent extends Event {

	private final String surname_name;
	private final String group;

	public DeletingDataClickedEvent(final String surname_name,
			final String group) {

		this.surname_name = surname_name;
		this.group = group;

	}

	public String getSurnameName() {
		return surname_name;
	}

	public String getGroup() {
		return group;
	}

}

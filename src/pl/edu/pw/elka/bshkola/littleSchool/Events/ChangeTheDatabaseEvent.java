/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Events;

/**
 * @author bogdan
 * 
 */
public class ChangeTheDatabaseEvent extends Event {

	private final String selectedDatabase;

	public ChangeTheDatabaseEvent(final String selectedItem) {
		selectedDatabase = selectedItem;
	}

	public String getSelectedDatabase() {
		return selectedDatabase;
	}

}

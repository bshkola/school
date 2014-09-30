/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Events;

/**
 * @author bogdan
 *
 */
public class AddingDataClickedEvent extends Event {

	private String selectedGroup;
	
	public AddingDataClickedEvent(final String selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
	
	public String getSelectedGroup() {
		return selectedGroup;
	}

}

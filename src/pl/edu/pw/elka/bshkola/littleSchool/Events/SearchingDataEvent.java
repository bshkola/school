/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Events;

/**
 * @author bogdan
 *
 */
public class SearchingDataEvent extends Event {

	private final String searchingText;
	
	public SearchingDataEvent(final String searchingText) {
		this.searchingText = searchingText;
	}

	public String getSearchingText() {
		return searchingText;
	}
	
}

/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Events;

/**
 * @author bogdan
 * 
 */
public class SearchingDataByMonthEvent extends Event {

	private final int monthIndex;

	public SearchingDataByMonthEvent(final int monthIndex) {
		this.monthIndex = monthIndex;
	}

	public int getMonthIndex() {
		return monthIndex;
	}

}

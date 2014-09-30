/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Events;

/**
 * @author Богдан
 *
 */
public class MyErrorEvent extends Event {

	private final String text;
	
	public MyErrorEvent(final String string) {
		text = string;
	}

	public String getText() {
		return text;
	}
	
}

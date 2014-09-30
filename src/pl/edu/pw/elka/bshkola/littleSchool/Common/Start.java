/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import pl.edu.pw.elka.bshkola.littleSchool.Controller.Controller;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;
import pl.edu.pw.elka.bshkola.littleSchool.Model.Model;
import pl.edu.pw.elka.bshkola.littleSchool.View.View;

/**
 * @author bogdan
 * 
 * Klasa poczatku programu. Miesci metode main(String[])
 */
public class Start {

	/**
	 * @param args - argumenty wywolania programu (nieuzywane)
	 * @throws LackOfDatabaseException 
	 */
	public static void main(String[] args) {
		
		BlockingQueue<Event> blockingQueue = new LinkedBlockingQueue<Event>();
		Model model = new Model();
		View view = new View(blockingQueue);
		Controller controller = new Controller(blockingQueue, view, model);
		controller.begin();
		
	}

}

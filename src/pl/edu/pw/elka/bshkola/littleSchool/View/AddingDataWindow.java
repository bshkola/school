/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.awt.event.ActionEvent;
import java.util.concurrent.BlockingQueue;

import pl.edu.pw.elka.bshkola.littleSchool.Events.AddingDataEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;

/**
 * @author bogdan
 * 
 */
class AddingDataWindow extends DataWindowPattern {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1881306520841884633L;

	public AddingDataWindow(final BlockingQueue<Event> blockingQueue,
			final String[] listOfGroups, final String selectedGroup,
			final MainWindow mainWindow) {

		super(blockingQueue, listOfGroups, mainWindow);
		setTitle("Додавання");
		setGroup(selectedGroup);

		okButton.addActionListener(new OKButtonListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {

				if (finalChecking() == false) {
					blockingQueue.add(new AddingDataEvent(surname, name,
							dateOfBirth, telephones, address, mother, father,
							group));
					dispose();
				}
			}
		});

	}

}

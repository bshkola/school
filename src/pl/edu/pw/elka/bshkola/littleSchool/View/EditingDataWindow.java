/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.awt.event.ActionEvent;
import java.util.concurrent.BlockingQueue;

import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;
import pl.edu.pw.elka.bshkola.littleSchool.Events.EditingDataEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;

/**
 * @author bogdan
 * 
 */
class EditingDataWindow extends DataWindowPattern {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1881306520841884633L;

	private final String oldSurname;
	private final String oldName;
	private final String oldGroup;

	public EditingDataWindow(final BlockingQueue<Event> blockingQueue,
			final String[] listOfGroups, final DataModel dataForEdit,
			final MainWindow mainWindow) {

		super(blockingQueue, listOfGroups, mainWindow);

		oldSurname = dataForEdit.getSurname();
		oldName = dataForEdit.getName();
		oldGroup = dataForEdit.getGroup();

		setTitle("Редагування");
		setGroup(oldGroup);
		setTelephoneFields(dataForEdit.getTelephones());
		setText(dataForEdit);

		okButton.addActionListener(new OKButtonListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (finalChecking() == false) {
					blockingQueue.add(new EditingDataEvent(oldSurname, oldName,
							oldGroup, surname, name, dateOfBirth, telephones,
							address, mother, father, group));
					dispose();
				}
			}
		});

	}

}

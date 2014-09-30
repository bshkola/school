/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import pl.edu.pw.elka.bshkola.littleSchool.Common.ApplicationProperties;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataInGroupModel;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;
import pl.edu.pw.elka.bshkola.littleSchool.Events.DeletingDataEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;

/**
 * @author bogdan
 * 
 *         Klasa widoku.
 */
public class View {

	private final BlockingQueue<Event> blockingQueue;
	private ApplicationProperties applicationProperties;
	private MainWindow mainWindow;
	private SearchingWindow searchingWindow;
	private SettingsWindow settingsWindow;
	private BirthWindow birthWindow;

	public View(final BlockingQueue<Event> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	public void createMainWindow(final String[] listOfGroups,
			final DataInGroupModel[] data, final String[] listOfDatabases) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainWindow = new MainWindow(blockingQueue, listOfGroups, data,
						listOfDatabases, applicationProperties);
				mainWindow.setVisible(true);
			}
		});
	}

	public void showAddingDataWindow(final String[] listOfGroups,
			final String selectedGroup) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AddingDataWindow(blockingQueue, listOfGroups,
						selectedGroup, mainWindow)
						.setLocationRelativeTo(mainWindow);
				mainWindow.setEnabled(false);
			}
		});
	}

	// public String getActiveGroup() {
	// return mainWindow.getActiveGroup();
	// }

	public void renewData(final DataInGroupModel[] data) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainWindow.renewData(data);
			}
		});
	}

	public void showEditingDataWindow(final String[] listOfGroups,
			final DataModel dataForEdit) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new EditingDataWindow(blockingQueue, listOfGroups, dataForEdit,
						mainWindow).setLocationRelativeTo(mainWindow);
				mainWindow.setEnabled(false);
			}
		});
	}

	public void createSearchWindow(final List<DataModel> dataModelForSearching,
			final String searchingText) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (searchingWindow == null) {
					searchingWindow = new SearchingWindow(blockingQueue,
							dataModelForSearching, searchingText, mainWindow);
				} else {
					searchingWindow.renew(dataModelForSearching, searchingText);
				}
				mainWindow.setEnabled(false);
			}
		});
	}

	public void enableMainWindow() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				mainWindow.setEnabled(true);
				mainWindow.toFront();
			}
		});
	}

	public void showSimpleListWindow(final DataInGroupModel[] data) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new SimpleListWindow(blockingQueue, data, mainWindow)
						.setLocationRelativeTo(mainWindow);
				mainWindow.setEnabled(false);
			}
		});
	}

	public void createErrorLackOfDatabaseDialog() {
		JOptionPane.showMessageDialog(mainWindow,
				"Помилка! \nНе знайдено бази даних.\nБуде створена нова база.",
				"Помилка бази!", JOptionPane.INFORMATION_MESSAGE);
	}

	public void createErrorNotCompatibleDatabaseDialog() {
		JOptionPane.showMessageDialog(mainWindow,
				"Помилка! \nБаза даних не сумісна.\nБуде створена нова база.",
				"Помилка бази!", JOptionPane.INFORMATION_MESSAGE);
	}

	public void createCommonErrorDialog() {
		JOptionPane.showMessageDialog(mainWindow, "Помилка програми!",
				"Помилка!", JOptionPane.ERROR_MESSAGE);
	}

	public void createMyErrorDialog(final String text) {
		JOptionPane.showMessageDialog(mainWindow, text, text,
				JOptionPane.ERROR_MESSAGE);
	}

	public void showDeletingDialog(final String surname_name, final String group) {

		String[] options = { "Так", "Ні" };
		int choice = JOptionPane.showOptionDialog(mainWindow,
				"Чи впевненні, що хочете видалити:\n" + surname_name
						+ " з групи " + group + "?", "Підтвердження видалення",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[1]);
		if (choice == 0) {
			blockingQueue.add(new DeletingDataEvent(surname_name, group));
		}
	}

	public void setStatus(final String status) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				mainWindow.setStatus(status);
			}
		});

	}

	public void createBirthWindow(final List<DataModel> dataModelForSearching,
			final int monthIndex) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (birthWindow == null) {
					birthWindow = new BirthWindow(blockingQueue,
							dataModelForSearching, monthIndex, mainWindow);
				} else {
					birthWindow.renew(dataModelForSearching, monthIndex);
				}
				mainWindow.setEnabled(false);
			}
		});
	}

	public void showSettingsWindow(final String[] listOfDatabases) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (settingsWindow == null) {
					settingsWindow = new SettingsWindow(blockingQueue,
							applicationProperties, mainWindow, listOfDatabases);
				} else {
					settingsWindow.renew();
				}
				mainWindow.setEnabled(false);
			}
		});
	}

	public void setProperties(final ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	public void createFolderTreeWindow(final Properties properties) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				settingsWindow.setEnabled(false);
				new FolderTreeWindow(blockingQueue, properties, settingsWindow);
			}
		});

	}

	public void enableSettingsWindow() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				settingsWindow.setEnabled(true);
				settingsWindow.toFront();
				settingsWindow.refreshModyfiedProperties();
			}
		});
	}

	public void setTitle(final String selectedDatabase) {
		mainWindow.setTitle(selectedDatabase);
	}

	public void showAboutWindow() {
		new AboutWindow(mainWindow);
	}
}

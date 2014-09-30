/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import pl.edu.pw.elka.bshkola.littleSchool.Common.CreatePdfListFileEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataInGroupModel;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;
import pl.edu.pw.elka.bshkola.littleSchool.Common.LackOfDatabaseException;
import pl.edu.pw.elka.bshkola.littleSchool.Events.AddingDataClickedEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.AddingDataEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.ChangeTheDatabaseEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.CloseFolderTreeWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.CommonErrorEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.CreateFolderTreeWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.CreatePdfFileEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.CreatingMainWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.DeletingDataClickedEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.DeletingDataEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.EditingDataClickedEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.EditingDataEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.EnableMainWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.ErrorLackOfDatabaseEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.ErrorNotCompatibleDatabaseEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;
import pl.edu.pw.elka.bshkola.littleSchool.Events.MyErrorEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.OpenSettingsWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.PrintBirthEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.SavePropertiesEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.SearchingDataByMonthClickedEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.SearchingDataByMonthEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.SearchingDataEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.ShowAboutWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.ShowSimpleListEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Model.Model;
import pl.edu.pw.elka.bshkola.littleSchool.View.View;

/**
 * @author bogdan
 * 
 */
public class Controller {

	private final BlockingQueue<Event> blockingQueue;
	private final View view;
	private final Model model;
	private final Map<Class<? extends Event>, Strategy> strategyMap;

	public Controller(final BlockingQueue<Event> blockingQueue,
			final View view, final Model model) {

		this.blockingQueue = blockingQueue;
		this.view = view;
		this.model = model;
		strategyMap = new HashMap<Class<? extends Event>, Strategy>();
		initMap();

	}

	private void initMap() {

		strategyMap.put(CreatingMainWindowEvent.class,
				new CreatingMainWindowStrategy());
		strategyMap.put(AddingDataClickedEvent.class,
				new AddingDataClickedStrategy());
		strategyMap.put(AddingDataEvent.class, new AddingDataStrategy());
		strategyMap.put(ChangeTheDatabaseEvent.class,
				new ChangeTheDatabaseStrategy());
		strategyMap.put(DeletingDataEvent.class, new DeleteDataStrategy());
		strategyMap.put(EditingDataClickedEvent.class,
				new EditDataClickedStrategy());
		strategyMap.put(EditingDataEvent.class, new EditDataStrategy());
		strategyMap.put(SearchingDataEvent.class, new SearchDataStrategy());
		strategyMap.put(EnableMainWindowEvent.class,
				new EnableMainWindowStrategy());
		strategyMap.put(SearchingDataByMonthClickedEvent.class,
				new SearchingDataByMonthClickedStrategy());
		strategyMap.put(SearchingDataByMonthEvent.class,
				new SearchingDataByMonthStrategy());
		strategyMap.put(ShowSimpleListEvent.class,
				new ShowingSimpleListStrategy());
		strategyMap.put(CreatePdfFileEvent.class, new CreatePdfFileStrategy());
		strategyMap.put(CreatePdfListFileEvent.class,
				new CreatePdfListFileStrategy());
		strategyMap.put(ErrorLackOfDatabaseEvent.class,
				new ErrorLackOfDatabaseStrategy());
		strategyMap.put(ErrorNotCompatibleDatabaseEvent.class,
				new ErrorNotCompatibleDatabaseStrategy());
		strategyMap.put(CommonErrorEvent.class, new CommonErrorStrategy());
		strategyMap.put(MyErrorEvent.class, new MyErrorStrategy());
		strategyMap.put(DeletingDataClickedEvent.class,
				new DeletingDataClickedStrategy());
		strategyMap.put(PrintBirthEvent.class, new PrintBirthStrategy());
		strategyMap.put(OpenSettingsWindowEvent.class,
				new OpenSettingsWindowStrategy());
		strategyMap
				.put(SavePropertiesEvent.class, new SavePropertiesStrategy());
		strategyMap.put(CreateFolderTreeWindowEvent.class,
				new CreateFolderTreeStrategy());
		strategyMap.put(CloseFolderTreeWindowEvent.class,
				new EnableSettingsWindowStrategy());
		strategyMap.put(ShowAboutWindowEvent.class,
				new ShowAboutWindowStrategy());

	}

	public void begin() {

		try {
			view.setProperties(model.loadProperties());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			model.loadDatabase(null);
		} catch (LackOfDatabaseException e) {
			blockingQueue.add(new ErrorLackOfDatabaseEvent());
			try {
				model.createDatabase();
			} catch (Exception e1) {
				blockingQueue.add(new CommonErrorEvent());
			}
		} catch (FileNotFoundException e) {
			blockingQueue.add(new ErrorLackOfDatabaseEvent());
			try {
				model.createDatabase();
			} catch (Exception e1) {
				blockingQueue.add(new CommonErrorEvent());
			}
		} catch (ClassCastException e) {
			blockingQueue.add(new ErrorNotCompatibleDatabaseEvent());
			try {
				model.createDatabase();
			} catch (Exception e1) {
				blockingQueue.add(new CommonErrorEvent());
			}
		} catch (IOException e) {
			blockingQueue.add(new CommonErrorEvent());
			try {
				model.createDatabase();
			} catch (Exception e1) {
				blockingQueue.add(new CommonErrorEvent());
			}
		} catch (ClassNotFoundException e) {
			blockingQueue.add(new ErrorNotCompatibleDatabaseEvent());
			try {
				model.createDatabase();
			} catch (Exception e1) {
				blockingQueue.add(new CommonErrorEvent());
			}
		}

		blockingQueue.add(new CreatingMainWindowEvent());
		// for (int i = 0; i < 100; i++) {
		// blockingQueue.add(new AddingDataEvent(new Integer(i).toString(),
		// new Integer(500 - i).toString(), "", "", "", "", "",
		// "Ромашки"));
		// }

		while (true) {
			try {
				Event event = blockingQueue.take();
				strategyMap.get(event.getClass()).perform(event);
			} catch (InterruptedException e) {
				blockingQueue.add(new CommonErrorEvent());
			}
		}

	}

	private abstract class Strategy {
		public abstract void perform(Event event);
	}

	private class CreatingMainWindowStrategy extends Strategy {

		@Override
		public void perform(final Event event) {

			String[] listOfDatabases = model.findDatabases();
			String[] listOfGroups = model.getListOfGroups();
			DataInGroupModel[] data = model.getDataFromGroup();
			view.createMainWindow(listOfGroups, data, listOfDatabases);
			view.setStatus("база завантажена");
		}
	}

	private class AddingDataClickedStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			AddingDataClickedEvent addingDataClickedEvent = (AddingDataClickedEvent) event;

			String[] listOfGroups = model.getListOfGroups();
			view.showAddingDataWindow(listOfGroups,
					addingDataClickedEvent.getSelectedGroup());
		}
	}

	private class AddingDataStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			AddingDataEvent addingDataEvent = (AddingDataEvent) event;
			DataModel datamodel = new DataModel(addingDataEvent);
			model.addData(datamodel);
			DataInGroupModel[] data = model.getDataFromGroup();
			view.renewData(data);
			try {
				model.saveDatabase();
				view.setStatus(addingDataEvent.getSurname() + " "
						+ addingDataEvent.getName() + " доданий/а");
			} catch (Exception e) {
				blockingQueue.add(new CommonErrorEvent());
			}

		}
	}

	private class ChangeTheDatabaseStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			ChangeTheDatabaseEvent changeTheDatabaseEvent = (ChangeTheDatabaseEvent) event;
			try {
				model.loadDatabase(changeTheDatabaseEvent.getSelectedDatabase());
				DataInGroupModel[] data = model.getDataFromGroup();
				view.renewData(data);
				view.setStatus("база "
						+ changeTheDatabaseEvent.getSelectedDatabase()
						+ " завантажена");
				view.setTitle(changeTheDatabaseEvent.getSelectedDatabase());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LackOfDatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private class DeleteDataStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			DeletingDataEvent deletingDataEvent = (DeletingDataEvent) event;
			model.deleteData(deletingDataEvent.getSurnameName(),
					deletingDataEvent.getNameOfGroup());
			DataInGroupModel[] data = model.getDataFromGroup();
			view.renewData(data);

			try {
				model.saveDatabase();
				view.setStatus(deletingDataEvent.getSurnameName()
						+ " видалений/а");
			} catch (Exception e) {
				blockingQueue.add(new CommonErrorEvent());
			}

		}
	}

	private class EditDataClickedStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			EditingDataClickedEvent editingDataClickedEvent = (EditingDataClickedEvent) event;
			DataModel dataForEdit = model.getData(
					editingDataClickedEvent.getSurnameName(),
					editingDataClickedEvent.getNameOfGroup());
			String[] listOfGroups = model.getListOfGroups();
			view.showEditingDataWindow(listOfGroups, dataForEdit);
		}
	}

	private class EditDataStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			EditingDataEvent editingDataEvent = (EditingDataEvent) event;
			model.deleteData(editingDataEvent.getOldSurname() + " "
					+ editingDataEvent.getOldName(),
					editingDataEvent.getOldGroup());
			model.addData(new DataModel(editingDataEvent));
			DataInGroupModel[] data = model.getDataFromGroup();
			view.renewData(data);

			try {
				model.saveDatabase();
				view.setStatus(editingDataEvent.getSurname() + " "
						+ editingDataEvent.getName() + " відредагований/а");
			} catch (Exception e) {
				blockingQueue.add(new CommonErrorEvent());
			}

		}
	}

	private class SearchDataStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			SearchingDataEvent searchingDataEvent = (SearchingDataEvent) event;
			List<DataModel> dataModelForSearching = model
					.searchData(searchingDataEvent.getSearchingText());
			view.createSearchWindow(dataModelForSearching,
					searchingDataEvent.getSearchingText());
		}
	}

	private class EnableMainWindowStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			view.enableMainWindow();
		}
	}

	private class SearchingDataByMonthClickedStrategy extends Strategy {

		@Override
		public void perform(final Event event) {

			Calendar calendar = Calendar.getInstance();

			List<DataModel> dataModels = model.searchDataByMonth(calendar
					.get(Calendar.MONTH) + 1);
			// System.out.println(calendar.get(Calendar.MONTH));
			view.createBirthWindow(dataModels, calendar.get(Calendar.MONTH) + 1);

		}
	}

	private class SearchingDataByMonthStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			SearchingDataByMonthEvent monthEvent = (SearchingDataByMonthEvent) event;
			List<DataModel> dataModels = model.searchDataByMonth(monthEvent
					.getMonthIndex());
			view.createBirthWindow(dataModels, monthEvent.getMonthIndex());

		}
	}

	private class ShowingSimpleListStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			DataInGroupModel[] data = model.getDataFromGroup();
			view.showSimpleListWindow(data);
		}
	}

	private class CreatePdfFileStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			try {
				String nameOfFile = model.createPdfFile();
				view.setStatus("файл-список даних про дітей (" + nameOfFile
						+ ") створений");
			} catch (Exception e) {
				blockingQueue.add(new CommonErrorEvent());
			}

		}
	}

	private class CreatePdfListFileStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			try {
				model.createPdfListFile();
			} catch (Exception e) {
				blockingQueue.add(new CommonErrorEvent());
			}
		}

	}

	private class PrintBirthStrategy extends Strategy {

		@Override
		public void perform(final Event event) {

			PrintBirthEvent printBirthEvent = (PrintBirthEvent) event;

			try {

				String nameOfFile = model.createBirthFile(printBirthEvent
						.getMonthIndex());
				view.setStatus("файл з даними про дні народження " + nameOfFile
						+ " збережено");
			} catch (Exception e) {
				blockingQueue.add(new CommonErrorEvent());
			}

		}
	}

	private class ErrorLackOfDatabaseStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			view.createErrorLackOfDatabaseDialog();
			// view.setStatus("не знайдено бази");
		}

	}

	private class ErrorNotCompatibleDatabaseStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			view.createErrorNotCompatibleDatabaseDialog();
			view.setStatus("база не сумісна");
		}

	}

	private class CommonErrorStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			view.createCommonErrorDialog();
			view.setStatus("помилка програми");
		}

	}

	private class MyErrorStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			String text = ((MyErrorEvent) event).getText();
			view.createMyErrorDialog(text);
			view.setStatus(text);
		}

	}

	private class DeletingDataClickedStrategy extends Strategy {

		@Override
		public void perform(final Event event) {

			DeletingDataClickedEvent deletedClickedEvent = (DeletingDataClickedEvent) event;
			view.showDeletingDialog(deletedClickedEvent.getSurnameName(),
					deletedClickedEvent.getGroup());

		}

	}

	private class OpenSettingsWindowStrategy extends Strategy {

		@Override
		public void perform(final Event event) {

			view.showSettingsWindow(model.findDatabases());

		}

	}

	private class SavePropertiesStrategy extends Strategy {

		@Override
		public void perform(final Event event) {

			try {
				model.saveProperties();
				view.setStatus("Налаштування збережені");
			} catch (IOException e) {
				view.createCommonErrorDialog();
			}

		}

	}

	private class CreateFolderTreeStrategy extends Strategy {

		@Override
		public void perform(final Event event) {

			CreateFolderTreeWindowEvent createFolderTreeEvent = (CreateFolderTreeWindowEvent) event;
			view.createFolderTreeWindow(createFolderTreeEvent
					.getModyfiedProperties());

		}

	}

	private class EnableSettingsWindowStrategy extends Strategy {

		@Override
		public void perform(final Event event) {

			view.enableSettingsWindow();

		}

	}

	private class ShowAboutWindowStrategy extends Strategy {

		@Override
		public void perform(final Event event) {
			view.showAboutWindow();
		}

	}

}

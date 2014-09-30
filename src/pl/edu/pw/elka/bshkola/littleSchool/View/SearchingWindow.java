package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;
import pl.edu.pw.elka.bshkola.littleSchool.Events.SearchingDataEvent;

public class SearchingWindow extends OneTableWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7476643926295072172L;

	protected JTextField searchField;

	public SearchingWindow(final BlockingQueue<Event> blockingQueue,
			final List<DataModel> dataModelForSearching,
			final String searchingText, final MainWindow mainWindow) {

		super(blockingQueue, dataModelForSearching, mainWindow);
		initView(dataModelForSearching, searchingText);
		initListeners();

	}

	private void initView(final List<DataModel> dataModelForSearching,
			final String searchingText) {

		searchField = new JTextField(searchingText, 30);
		northLeftPanel.add(searchField);
		setTitle("Пошук: " + searchingText);

	}

	private void initListeners() {

		searchField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(final DocumentEvent arg0) {
				blockingQueue.add(new SearchingDataEvent(searchField.getText()));
			}

			@Override
			public void insertUpdate(final DocumentEvent arg0) {
				blockingQueue.add(new SearchingDataEvent(searchField.getText()));
			}

			@Override
			public void changedUpdate(final DocumentEvent arg0) {
				blockingQueue.add(new SearchingDataEvent(searchField.getText()));
			}
		});

	}

	public void renew(final List<DataModel> dataModelForSearching,
			final String searchingText) {

		renew(dataModelForSearching);
		setTitle("Пошук: " + searchingText);

	}

}

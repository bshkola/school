package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;

import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;
import pl.edu.pw.elka.bshkola.littleSchool.Events.PrintBirthEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.SearchingDataByMonthEvent;

public class BirthWindow extends OneTableWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8045462584471473412L;

	private final BlockingQueue<Event> blockingQueue;
	private JButton printButton;
	private JComboBox<String> monthes;
	private RenewListener renewListener;
	private final String[] listOfMonthes = { "Січень", "Лютий", "Березень",
			"Квітень", "Травень", "Червень", "Липень", "Серпень", "Вересень",
			"Жовтень", "Листопад", "Грудень" };

	public BirthWindow(final BlockingQueue<Event> blockingQueue,
			final List<DataModel> dataModelForSearching, final int monthIndex,
			final MainWindow mainWindow) {
		super(blockingQueue, dataModelForSearching, mainWindow);

		this.blockingQueue = blockingQueue;
		initView(monthIndex);
		addListeners();

	}

	private void initView(final int monthIndex) {

		setTitle("Пошук: " + listOfMonthes[monthIndex - 1]);
		printButton = new JButton("Роздрукувати");

		monthes = new JComboBox<String>(listOfMonthes);
		monthes.setSelectedIndex(monthIndex - 1);
		monthes.setMaximumRowCount(listOfMonthes.length);

		northLeftPanel.add(monthes);
		northLeftPanel.add(printButton);

	}

	private void addListeners() {

		printButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				blockingQueue.add(new PrintBirthEvent(monthes
						.getSelectedIndex() + 1));
			}
		});

		renewListener = new RenewListener();
		monthes.addActionListener(renewListener);

	}

	public void renew(final List<DataModel> dataModelForSearching,
			final int monthIndex) {

		renew(dataModelForSearching);

		monthes.removeActionListener(renewListener);
		monthes.setSelectedIndex(monthIndex - 1);
		setTitle("Місяць: " + listOfMonthes[monthIndex - 1]);
		monthes.addActionListener(renewListener);

	}

	class RenewListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			blockingQueue.add(new SearchingDataByMonthEvent(monthes
					.getSelectedIndex() + 1));
		}
	}

}

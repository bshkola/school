package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;
import pl.edu.pw.elka.bshkola.littleSchool.Events.EnableMainWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;

public class OneTableWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1585570260610460766L;

	protected final BlockingQueue<Event> blockingQueue;
	private JPanel northPanel;
	protected JPanel northLeftPanel;
	private JButton closeButton;
	protected TableModel tableModel;
	private JTable table;

	public OneTableWindow(final BlockingQueue<Event> blockingQueue,
			final List<DataModel> dataModelForSearching,
			final MainWindow mainWindow) {

		super(mainWindow);
		this.blockingQueue = blockingQueue;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(700, 500));
		setMinimumSize(new Dimension(700, 500));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		pack();

		initView(dataModelForSearching);
		initListeners();
	}

	private void initView(final List<DataModel> dataModelForSearching) {

		northPanel = new JPanel(new BorderLayout());
		northLeftPanel = new JPanel();
		closeButton = new JButton("Закрий");
		tableModel = new TableModel();
		tableModel.changeNameOfColumn(0, "Група");
		tableModel.setModelData(dataModelForSearching);
		table = new JTable(tableModel);
		JScrollPane pane = new JScrollPane(table,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		add(northPanel, BorderLayout.NORTH);

		northPanel.add(northLeftPanel, BorderLayout.WEST);
		northPanel.add(closeButton, BorderLayout.EAST);
		add(pane, BorderLayout.CENTER);

		table.getColumnModel().getColumn(0).setMaxWidth(60);
		table.getColumnModel().getColumn(2).setMaxWidth(55);

	}

	private void initListeners() {

		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				dispose();
			}
		});

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(final WindowEvent arg0) {
				blockingQueue.add(new EnableMainWindowEvent());
			}

		});

	}

	protected void renew(final List<DataModel> dataModelForSearching) {
		tableModel.setModelData(dataModelForSearching);
		tableModel.fireTableDataChanged();

		setVisible(true);
	}
}

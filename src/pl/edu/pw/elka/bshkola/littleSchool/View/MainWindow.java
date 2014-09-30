/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.concurrent.BlockingQueue;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pl.edu.pw.elka.bshkola.littleSchool.Common.ApplicationProperties;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataInGroupModel;
import pl.edu.pw.elka.bshkola.littleSchool.Common.Listeners.MyListSelectionListener;
import pl.edu.pw.elka.bshkola.littleSchool.Events.AddingDataClickedEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.ChangeTheDatabaseEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.CreatePdfFileEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.DeletingDataClickedEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.EditingDataClickedEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;
import pl.edu.pw.elka.bshkola.littleSchool.Events.OpenSettingsWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.SearchingDataByMonthClickedEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.SearchingDataEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.ShowAboutWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.ShowSimpleListEvent;

/**
 * @author bogdan
 * 
 */
class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9094776636771174753L;

	private final BlockingQueue<Event> blockingQueue;

	private JMenuBar menuBar;
	private JMenu mainMenu;
	private JMenu toolsMenu;
	private JMenu PDFtoolsMenu;
	private JMenu settingsMenu;
	private JMenu informationMenu;

	private JMenuItem exitMenuItem;
	private JMenuItem newMenuItem;
	private JMenuItem editMenuItem;
	private JMenuItem removeMenuItem;
	private JMenuItem searchMenuItem;
	private JMenuItem birthMenuItem;
	private JMenuItem simpleListMenuItem;
	private JMenuItem fullPDFMenuItem;
	private JMenuItem settingsMenuItem;
	private JMenuItem informationMenuItem;

	private JPanel statusBar;
	private JLabel statusLabel;
	private JButton newButton;
	private JButton editButton;
	private JButton removeButton;
	private JPanel rightPanel;
	private JTextField searchPanel;
	private JButton searchButton;
	private JComboBox<String> databases;
	// private JComboBox<String> monthes;
	private JButton birthButton;
	private JButton simpleListButton;
	// private JButton createPdfListButton;
	private JButton createPdf;
	private JButton settingsButton;
	private JPanel[] groupPanels;
	private JLabel[] groupLabels;
	private JTable[] tables;
	private TableModel[] tableModel;
	private JTabbedPane tabbedPane;
	// private final String[] listOfMonthes = { "Січень", "Лютий", "Березень",
	// "Квітень", "Травень", "Червень", "Липень", "Серпень", "Вересень",
	// "Жовтень", "Листопад", "Грудень" };

	private final int WIDTH = 800;
	private final int HEIGHT = 600;

	public MainWindow(final BlockingQueue<Event> blockingQueue,
			final String[] listOfGroups, final DataInGroupModel[] data,
			final String[] listOfDatabases,
			final ApplicationProperties applicationProperties) {

		this.blockingQueue = blockingQueue;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initView(
				listOfGroups,
				data,
				listOfDatabases,
				(String) applicationProperties.getProperties().get(
						"default_database_name"));
		initListeners();

	}

	private void initView(final String[] listOfGroups,
			final DataInGroupModel[] data, final String[] listOfDatabases,
			final String databaseName) {

		setTitle(databaseName);
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout());
		pack();

		menuBar = new JMenuBar();

		mainMenu = new JMenu("Файл");
		toolsMenu = new JMenu("Можливості");
		PDFtoolsMenu = new JMenu("Роздрукувати");
		settingsMenu = new JMenu("Налаштування");
		informationMenu = new JMenu("Про програму");

		newMenuItem = new JMenuItem("Додати інформацію");
		editMenuItem = new JMenuItem("Редагувати інформацію");
		removeMenuItem = new JMenuItem("Видалити інформацію");
		exitMenuItem = new JMenuItem("Вийти");

		searchMenuItem = new JMenuItem("Пошук");
		birthMenuItem = new JMenuItem("Дні народження");
		simpleListMenuItem = new JMenuItem("Простий список");

		fullPDFMenuItem = new JMenuItem("Роздрукувати списки дітей");

		settingsMenuItem = new JMenuItem("Налаштування");

		informationMenuItem = new JMenuItem("Про програму");

		getContentPane().add(menuBar, BorderLayout.NORTH);
		menuBar.add(mainMenu);
		menuBar.add(toolsMenu);
		menuBar.add(PDFtoolsMenu);
		menuBar.add(settingsMenu);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(informationMenu);

		mainMenu.setMnemonic('F');
		toolsMenu.setMnemonic('T');
		PDFtoolsMenu.setMnemonic('P');
		settingsMenu.setMnemonic('S');
		informationMenu.setMnemonic('I');

		mainMenu.add(newMenuItem);
		mainMenu.add(editMenuItem);
		mainMenu.add(removeMenuItem);
		mainMenu.addSeparator();
		mainMenu.add(exitMenuItem);
		toolsMenu.add(searchMenuItem);
		toolsMenu.add(birthMenuItem);
		toolsMenu.add(simpleListMenuItem);
		PDFtoolsMenu.add(fullPDFMenuItem);
		settingsMenu.add(settingsMenuItem);
		informationMenu.add(informationMenuItem);

		statusBar = new JPanel();
		statusLabel = new JLabel("Статус:");

		getContentPane().add(statusBar, BorderLayout.SOUTH);
		statusBar.setLayout(new BorderLayout());
		statusBar.add(statusLabel, BorderLayout.WEST);
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));

		rightPanel = new JPanel();
		newButton = new JButton("Додати інформацію");
		editButton = new JButton("Редагувати інформацію");
		removeButton = new JButton("Видалити інформацію");
		searchPanel = new JTextField();
		searchButton = new JButton("Пошук");
		createPdf = new JButton("Створити PDF");
		databases = new JComboBox<String>(listOfDatabases);
		databases.setSelectedItem(databaseName);
		// monthes = new JComboBox<String>(listOfMonthes);
		birthButton = new JButton("Дні народження");
		simpleListButton = new JButton("Показати простий список");
		// createPdfListButton = new JButton("Створити PDF зі списком");
		settingsButton = new JButton("Налаштування");

		databases.setMaximumRowCount(listOfGroups.length);
		// monthes.setMaximumRowCount(listOfMonthes.length);

		// Box box = Box.createVerticalBox();
		// getContentPane().add(new JScrollPane(box), BorderLayout.CENTER);

		groupPanels = new JPanel[data.length];
		for (int i = 0; i < groupPanels.length; i++) {
			groupPanels[i] = new JPanel();
		}

		tableModel = new TableModel[data.length];
		for (int i = 0; i < tableModel.length; i++) {
			tableModel[i] = new TableModel();
			tableModel[i].setModelData(data[i].getListOfData());
		}

		int[] sizes = { 25, 100, 100, 60, 100, 100, 100, 100 };
		tables = new JTable[tableModel.length];
		for (int i = 0; i < tables.length; i++) {
			tables[i] = new MyJTable(tableModel[i]);
			// tables[i].res

			tables[i]
					.setPreferredScrollableViewportSize(new Dimension(300, 300));
			tables[i].setFillsViewportHeight(true);
			tables[i].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tables[i].getColumnModel().getColumn(0).setMaxWidth(20);
			tables[i].getColumnModel().getColumn(2).setMaxWidth(55);
			for (int j = 0; j < 7; j++) {
				tables[i].getColumnModel().getColumn(j)
						.setPreferredWidth(sizes[j]);
			}
		}

		groupLabels = new JLabel[tables.length];
		for (int i = 0; i < groupLabels.length; i++) {
			groupLabels[i] = new JLabel(data[i].getNameOfGroup());
		}

		tabbedPane = new JTabbedPane();

		for (int i = 0; i < tables.length; i++) {
			tabbedPane.addTab(listOfGroups[i + 1], null, new JScrollPane(
					tables[i]), listOfGroups[i + 1]);
		}

		add(tabbedPane, BorderLayout.CENTER);

		getContentPane().add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new GridLayout(20, 1));
		rightPanel.add(newButton);
		rightPanel.add(editButton);
		rightPanel.add(removeButton);
		rightPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		rightPanel.add(new JLabel("Пошук:"));
		rightPanel.add(searchPanel);
		rightPanel.add(searchButton);
		rightPanel.add(new JPanel());
		rightPanel.add(new JPanel());
		rightPanel.add(createPdf);
		// rightPanel.add(createPdfListButton);
		rightPanel.add(new JPanel());
		rightPanel.add(new JPanel());
		rightPanel.add(new JLabel("Навчальні роки:"));
		rightPanel.add(databases);
		rightPanel.add(new JPanel());
		// rightPanel.add(monthes);
		rightPanel.add(birthButton);
		rightPanel.add(new JPanel());
		rightPanel.add(simpleListButton);
		rightPanel.add(new JPanel());
		rightPanel.add(settingsButton);

	}

	private void initListeners() {

		ActionListener newActionListener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				blockingQueue.add(new AddingDataClickedEvent(tabbedPane
						.getTitleAt(tabbedPane.getSelectedIndex())));
			}
		};

		newButton.addActionListener(newActionListener);
		newMenuItem.addActionListener(newActionListener);
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.CTRL_MASK));

		ActionListener editActionListener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				for (int i = 0; i < tables.length; i++) {
					if (tables[i].getSelectedRow() != -1) {
						int selectedRow = tables[i].getSelectedRow();
						blockingQueue.add(new EditingDataClickedEvent(
								(String) tableModel[i].getValueAt(selectedRow,
										1), tabbedPane.getTitleAt(tabbedPane
										.getSelectedIndex())));
						return;
					}
				}
			}
		};

		editButton.addActionListener(editActionListener);
		editMenuItem.addActionListener(editActionListener);
		editMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				KeyEvent.CTRL_MASK));

		ActionListener removeActionListener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {

				for (int i = 0; i < tables.length; i++) {
					if (tables[i].getSelectedRow() != -1) {
						int selectedRow = tables[i].getSelectedRow();
						blockingQueue.add(new DeletingDataClickedEvent(
								(String) tableModel[i].getValueAt(selectedRow,
										1), tabbedPane.getTitleAt(tabbedPane
										.getSelectedIndex())));
						return;
					}
				}
			}
		};

		removeButton.addActionListener(removeActionListener);
		removeMenuItem.addActionListener(removeActionListener);
		removeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				KeyEvent.CTRL_MASK));

		exitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});

		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				KeyEvent.CTRL_MASK));

		ActionListener searchActionListener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				blockingQueue
						.add(new SearchingDataEvent(searchPanel.getText()));
			}
		};

		searchButton.addActionListener(searchActionListener);
		searchMenuItem.addActionListener(searchActionListener);
		searchMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.CTRL_MASK));

		databases.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				blockingQueue.add(new ChangeTheDatabaseEvent((String) databases
						.getSelectedItem()));
			}
		});

		ActionListener birthActionListener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				blockingQueue.add(new SearchingDataByMonthClickedEvent());
			}
		};

		birthButton.addActionListener(birthActionListener);
		birthMenuItem.addActionListener(birthActionListener);
		birthMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
				KeyEvent.CTRL_MASK));

		ActionListener simpleListActionListener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				blockingQueue.add(new ShowSimpleListEvent());
			}
		};

		simpleListButton.addActionListener(simpleListActionListener);
		simpleListMenuItem.addActionListener(simpleListActionListener);
		simpleListMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				KeyEvent.CTRL_MASK));

		// ActionListener createPDFActionListener = new ActionListener() {
		//
		// @Override
		// public void actionPerformed(final ActionEvent arg0) {
		// blockingQueue.add(new CreatePdfListFileEvent());
		// }
		// };
		//
		// createPdfListButton.addActionListener(createPDFActionListener);

		ActionListener fullPDFActionListener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				blockingQueue.add(new CreatePdfFileEvent());
			}
		};

		createPdf.addActionListener(fullPDFActionListener);
		fullPDFMenuItem.addActionListener(fullPDFActionListener);
		fullPDFMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				KeyEvent.CTRL_MASK));

		ActionListener settingsActionListener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				blockingQueue.add(new OpenSettingsWindowEvent());
			}
		};

		settingsButton.addActionListener(settingsActionListener);
		settingsMenuItem.addActionListener(settingsActionListener);
		settingsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				KeyEvent.CTRL_MASK));

		ActionListener informationActionListener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				blockingQueue.add(new ShowAboutWindowEvent());
			}
		};

		informationMenuItem.addActionListener(informationActionListener);
		informationMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_I, KeyEvent.CTRL_MASK));

		for (int i = 0; i < tables.length; i++) {
			ListSelectionModel listSelectionModel = tables[i]
					.getSelectionModel();
			listSelectionModel
					.addListSelectionListener(new MyListSelectionListener(i,
							tables));
			tables[i].setSelectionModel(listSelectionModel);
		}

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				for (int i = 0; i < tables.length; i++) {
					tables[i].clearSelection();
				}
			}
		});

	}

	// public String getActiveGroup() {
	// return tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
	// }

	public void renewData(final DataInGroupModel[] data) {

		for (int i = 0; i < data.length; i++) {
			tableModel[i].setModelData(data[i].getListOfData());
			tableModel[i].fireTableDataChanged();
			groupLabels[i].setText(data[i].getNameOfGroup());
		}

		if (data.length == tables.length) {
			for (int i = 1; i < tables.length; i++) {
				tables[i].setVisible(true);
				groupLabels[i].setVisible(true);
			}
		} else {
			for (int i = 1; i < tables.length; i++) {
				tables[i].setVisible(false);
				groupLabels[i].setVisible(false);
			}
		}

	}

	public void setStatus(final String status) {
		statusLabel.setText("Статус:     " + status);
	} // TODO change deleteItem source to deleteButton's

	@Override
	public void setTitle(final String title) {
		super.setTitle("Маленька школа [" + title + "]");
	}
}

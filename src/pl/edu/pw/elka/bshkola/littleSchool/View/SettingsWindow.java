package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pl.edu.pw.elka.bshkola.littleSchool.Common.ApplicationProperties;
import pl.edu.pw.elka.bshkola.littleSchool.Events.CreateFolderTreeWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.EnableMainWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;
import pl.edu.pw.elka.bshkola.littleSchool.Events.SavePropertiesEvent;

public class SettingsWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2455435473457839706L;

	private final BlockingQueue<Event> blockingQueue;
	private ApplicationProperties actualProperties;
	private Properties modyfiedProperties;

	private JPanel mainPanel;
	private JPanel leftPanel;
	private JPanel bottomPanel;
	private SubSettingsPanel[] subSettingsPanels;
	private JButton okButton;
	private JButton cancelButton;

	private JList<String> list;
	private String[] listOfDatabases;

	private String[] listStrings = { "Загальні", "База", "PDF", "Мова" };
	// private String[] listOfLanguages = { "Українська", "Російська",
	// "Англійська" };

	private final static int WIDTH = 600;
	private final static int HEIGHT = 500;

	public SettingsWindow(final BlockingQueue<Event> blockingQueue,
			final ApplicationProperties applicationProperties,
			final MainWindow mainWindow, final String[] listOfDatabases) {

		super(mainWindow);
		this.blockingQueue = blockingQueue;
		this.actualProperties = applicationProperties;
		this.listOfDatabases = listOfDatabases;
		modyfiedProperties = actualProperties.getProperties();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initView();
		initListeners();
		setVisible(true);

	}

	private void initView() {

		setTitle("Налаштування");
		getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(new Dimension(WIDTH, HEIGHT));
		setResizable(false);
		pack();

		// setLayout(new BorderLayout());

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		leftPanel = new JPanel();
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		okButton = new JButton("Зберегти");
		cancelButton = new JButton("Закрити");

		list = new JList<String>(listStrings);
		list.setSelectedIndex(0);
		list.setPreferredSize(new Dimension(150,
				getContentPane().getSize().height - 60));
		list.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		subSettingsPanels = new SubSettingsPanel[listStrings.length];

		for (int i = 0; i < listStrings.length; i++) {
			subSettingsPanels[i] = new SubSettingsPanel(listStrings[i]);
		}

		subSettingsPanels[0].initSubSettingsPanel0();
		subSettingsPanels[1].initSubSettingsPanel1();
		subSettingsPanels[2].initSubSettingsPanel2();
		subSettingsPanels[3].initSubSettingsPanel3();

		add(mainPanel);

		leftPanel.add(list);
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(subSettingsPanels[0], BorderLayout.CENTER);
		bottomPanel.add(new JSeparator(JSeparator.HORIZONTAL),
				BorderLayout.NORTH);

		JPanel temporaryPanel = new JPanel();
		temporaryPanel.add(okButton);
		temporaryPanel.add(cancelButton);
		bottomPanel.add(temporaryPanel, BorderLayout.EAST);

		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

	}

	private void initListeners() {

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(final WindowEvent arg0) {
				blockingQueue.add(new EnableMainWindowEvent());
			}

		});

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent e) {
				mainPanel.removeAll();
				mainPanel.add(subSettingsPanels[list.getSelectedIndex()],
						BorderLayout.CENTER);
				mainPanel.add(leftPanel, BorderLayout.WEST);
				mainPanel.add(bottomPanel, BorderLayout.SOUTH);
				mainPanel.revalidate();
				mainPanel.repaint();
				// System.out.println(listStrings[list.getSelectedIndex()]);
			}
		});

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {

				actualProperties.setProperties(modyfiedProperties);
				dispose();
				blockingQueue.add(new SavePropertiesEvent());

			}
		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				dispose();
			}
		});

	}

	public void renew() {

		modyfiedProperties = actualProperties.getProperties();
		list.setSelectedIndex(0);
		subSettingsPanels[1].list.setSelectedValue(
				modyfiedProperties.getProperty("default_database_name"), true);
		setVisible(true);

	}

	class SubSettingsPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3846151210576965787L;

		private JTextField pathTextField;
		private JList<String> list;

		private SubSettingsPanel(final String titleName) {

			TitledBorder title = BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.black), titleName);

			title.setTitleJustification(TitledBorder.CENTER);
			setBorder(title);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		}

		public Component add(final JPanel component, final String titleOfPanel) {
			component.setBorder(BorderFactory.createTitledBorder(titleOfPanel));
			return super.add(component);
		}

		void initSubSettingsPanel0() {
			add(new JPanel(), "Недоступно");
		}

		void initSubSettingsPanel1() {

			JPanel panel = new JPanel();
			list = new JList<String>(listOfDatabases);
			list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			list.setSelectedValue(
					modyfiedProperties.getProperty("default_database_name"),
					true);
			list.setLayoutOrientation(JList.VERTICAL_WRAP);
			list.setVisibleRowCount(-1);
			// list.set

			JScrollPane listScroller = new JScrollPane(list);
			listScroller.setPreferredSize(new Dimension(200, 60));

			panel.add(listScroller);

			add(panel, "Навчальні роки за умовчуванням:");

			list.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(final ListSelectionEvent arg0) {
					modyfiedProperties.setProperty("default_database_name",
							list.getSelectedValue());
				}
			});
		}

		void initSubSettingsPanel2() {

			JPanel panel = new JPanel();
			pathTextField = new JTextField(
					modyfiedProperties.getProperty("PDF_folder_path"), 30);
			pathTextField.setEditable(false);
			JButton button1 = new JButton("...");
			panel.add(pathTextField);
			panel.add(button1);

			add(panel, "Папка для зберігання файлів PDF: ");

			panel = new JPanel();
			final JTextField textField2 = new JTextField(
					modyfiedProperties.getProperty("PDF_full_list_file_name"),
					25);
			textField2.setEditable(false);
			JButton button2 = new JButton("Змінити");
			panel.add(textField2);
			panel.add(button2);
			JLabel note1 = new JLabel(
					"!!-!! замінюється на навчальний рік. Наприклад, 12-13.");
			note1.setForeground(Color.GRAY);
			panel.add(note1);
			add(panel, "Назва повного списку дітей (файлу): ");

			panel = new JPanel();
			final JTextField textField3 = new JTextField(
					modyfiedProperties.getProperty("PDF_birth_list_file_name"),
					25);
			textField3.setEditable(false);
			JButton button3 = new JButton("Змінити");
			panel.add(textField3);
			panel.add(button3);
			JLabel note2 = new JLabel(
					"!!! замінюється на назву місяця. Наприклад, березень.");
			note2.setForeground(Color.GRAY);
			panel.add(note2);

			add(panel, "Назва списку днів народжень дітей (файлу): ");

			button1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					blockingQueue.add(new CreateFolderTreeWindowEvent(
							modyfiedProperties));
				}
			});

			button2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent arg0) {
					String nameOfFile = JOptionPane
							.showInputDialog(
									"Введіть нову назву файла.\n\n !!-!! замінюється на навчальний рік. Наприклад, 12-13.",
									textField2.getText());
					if (nameOfFile != null) {
						if (!nameOfFile.toLowerCase().endsWith(".pdf")) {
							nameOfFile = nameOfFile + ".pdf";
						}
						modyfiedProperties.setProperty(
								"PDF_full_list_file_name", nameOfFile);
						textField2.setText(nameOfFile);
					}
				}
			});

			button3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent arg0) {
					String nameOfFile = JOptionPane
							.showInputDialog(
									"Введіть нову назву файла.\n\n !!! замінюється на назву місяця. Наприклад, березень.",
									textField3.getText());
					if (nameOfFile != null) {

						if (!nameOfFile.toLowerCase().endsWith(".pdf")) {
							nameOfFile = nameOfFile + ".pdf";
						}
						modyfiedProperties.setProperty(
								"PDF_birth_list_file_name", nameOfFile);
						textField3.setText(nameOfFile);
					}
				}
			});

		}

		void initSubSettingsPanel3() {

			JPanel panel = new JPanel();
			// JComboBox<String> comboBox1 = new
			// JComboBox<String>(listOfLanguages);
			// JButton button1 = new JButton("Змінити");
			// panel.add(comboBox1);
			// panel.add(button1);

			panel.add(new JLabel("В даній версії зміна мови недоступна."));
			panel.add(new JLabel("Мова за умовчуванням - українська."));
			add(panel, "Мова: ");

		}

	}

	public void refreshModyfiedProperties() {
		subSettingsPanels[2].pathTextField.setText(modyfiedProperties
				.getProperty("PDF_folder_path"));
	}
}

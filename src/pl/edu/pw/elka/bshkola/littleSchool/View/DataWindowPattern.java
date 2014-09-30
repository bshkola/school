package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.BlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;
import pl.edu.pw.elka.bshkola.littleSchool.Events.EnableMainWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;

public class DataWindowPattern extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4823661741289512596L;

	private final BlockingQueue<Event> blockingQueue;

	private JTextField nameField;
	private JTextField surnameField;
	private JFormattedTextField dateOfBirthField;
	private JTextField[] telephoneFields;
	private JButton expandTelephone;
	private JTextField addressField;
	private JTextField motherField;
	private JTextField fatherField;
	private JComboBox<String> groupsComboBox;
	protected JButton okButton;
	private JButton cancelButton;

	private final int NR_TELEPHONES = 3;
	private int nrTelephones = 1;

	private ImageIcon addIcon;

	private final int WIDTH = 300;
	private final int HEIGHT = 400;

	public DataWindowPattern(final BlockingQueue<Event> blockingQueue,
			final String[] listOfGroups, final MainWindow mainWindow) {

		super(mainWindow);
		this.blockingQueue = blockingQueue;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initView(listOfGroups);
		initListeners();
		setVisible(true);

	}

	private void initView(final String[] listOfGroups) {

		getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		pack();

		// URL imgURL = getClass().getResource("images/plus-icon.gif");
		// if (imgURL != null) {
		addIcon = new ImageIcon("images/plus-icon.png");
		// } else {
		// System.err.println("Couldn't find file: " + "plus-icon.gif");
		// }

		Box mainBox = Box.createVerticalBox();
		add(mainBox, BorderLayout.CENTER);
		JPanel namePanel = new JPanel();
		JPanel surnamePanel = new JPanel();

		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("##'.##'.##");
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		dateOfBirthField = new JFormattedTextField(formatter);
		dateOfBirthField.setColumns(5);

		JPanel dateOfBirthPanel = new JPanel();
		final JPanel telephonePanel = new JPanel();
		JPanel addressPanel = new JPanel();
		JPanel parentsPanel = new JPanel();
		JPanel motherPanel = new JPanel();
		JPanel fatherPanel = new JPanel();
		groupsComboBox = new JComboBox<String>(listOfGroups);
		groupsComboBox.removeItemAt(0);
		groupsComboBox.setMaximumRowCount(listOfGroups.length);
		JPanel decisionPanel = new JPanel();

		mainBox.add(surnamePanel);
		mainBox.add(namePanel);
		mainBox.add(dateOfBirthPanel);
		mainBox.add(telephonePanel);
		mainBox.add(addressPanel);
		mainBox.add(parentsPanel);
		mainBox.add(motherPanel);
		mainBox.add(fatherPanel);
		mainBox.add(groupsComboBox);
		mainBox.add(decisionPanel);

		surnamePanel.add(new JLabel("Прізвище:"));
		namePanel.add(new JLabel("Ім'я:"));
		dateOfBirthPanel.add(new JLabel("Дата народження:"));
		telephonePanel.add(new JLabel("Телефон:"));
		addressPanel.add(new JLabel("Адреса:"));
		parentsPanel.add(new JLabel("Батьки:"));
		motherPanel.add(new JLabel("       Мати:"));
		fatherPanel.add(new JLabel("       Батько:"));

		nameField = new JTextField(15);
		surnameField = new JTextField(15);
		telephoneFields = new JTextField[NR_TELEPHONES];
		for (int i = 0; i < NR_TELEPHONES; i++) {
			telephoneFields[i] = new JTextField(10);
		}
		expandTelephone = new JButton(addIcon);
		expandTelephone.setMaximumSize(new Dimension(10, 10));
		addressField = new JTextField(15);
		motherField = new JTextField(15);
		fatherField = new JTextField(15);

		namePanel.add(nameField);
		surnamePanel.add(surnameField);
		dateOfBirthPanel.add(dateOfBirthField);

		final Box telephoneFieldsBox = Box.createVerticalBox();
		telephonePanel.add(telephoneFieldsBox);
		telephoneFieldsBox.add(telephoneFields[0]);
		telephonePanel.add(expandTelephone);
		expandTelephone.setPreferredSize(new Dimension(25, 25));

		expandTelephone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				telephoneFieldsBox.removeAll();
				for (int i = 0; i <= nrTelephones; i++) {
					telephoneFieldsBox.add(telephoneFields[i]);
				}
				if (nrTelephones == NR_TELEPHONES - 1) {
					telephonePanel.removeAll();
					telephonePanel.add(new JLabel("Телефон:"));
					telephonePanel.add(telephoneFieldsBox);
				} else {
					nrTelephones++;
				}
				telephoneFieldsBox.revalidate();
				telephoneFieldsBox.repaint();
			}
		});

		// telephoneFieldsPanel.add(telephoneFields[1]);
		// telephoneFieldsPanel.add(telephoneFields[2]);

		addressPanel.add(addressField);
		motherPanel.add(motherField);
		fatherPanel.add(fatherField);

		okButton = new JButton("OK");
		cancelButton = new JButton("Відмінити");

		decisionPanel.add(okButton);
		decisionPanel.add(cancelButton);

	}

	protected void setText(final DataModel dataForEdit) {
		nameField.setText(dataForEdit.getName());
		surnameField.setText(dataForEdit.getSurname());
		dateOfBirthField.setText(dataForEdit.getDateOfBirth());
		for (int i = 0; i < NR_TELEPHONES; i++) {
			telephoneFields[i].setText(dataForEdit.getTelephones()[i]);
		}
		addressField.setText(dataForEdit.getAddress());
		motherField.setText(dataForEdit.getMother());
		fatherField.setText(dataForEdit.getFather());
	}

	protected void setGroup(final String groupName) {
		groupsComboBox.setSelectedItem(groupName);
	}

	protected void setTelephoneFields(final String[] telephones) {

		for (int i = 1; i < NR_TELEPHONES; i++) {
			if (telephones[i].equals("")) {
				break;
			}
			expandTelephone.doClick();
		}
	}

	private void initListeners() {

		cancelButton.addActionListener(new ActionListener() {
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

	abstract class OKButtonListener implements ActionListener {

		String name;
		String surname;
		String dateOfBirth;
		String telephones[];
		String address;
		String mother;
		String father;
		String group;

		protected boolean finalChecking() {

			name = nameField.getText();
			surname = surnameField.getText();
			dateOfBirth = dateOfBirthField.getText();
			telephones = new String[3];
			for (int i = 0; i < NR_TELEPHONES; i++) {
				telephones[i] = telephoneFields[i].getText();
			}
			address = addressField.getText();
			mother = motherField.getText();
			father = fatherField.getText();
			group = (String) groupsComboBox.getSelectedItem();

			boolean lackOfData = false;

			if (name.length() == 0) {
				nameField.setBorder(BorderFactory.createLineBorder(Color.red));
				lackOfData = true;
			} else {
				nameField.setBorder(BorderFactory.createLineBorder(Color.gray));
			}

			if (surname.length() == 0) {
				surnameField.setBorder(BorderFactory
						.createLineBorder(Color.red));
				lackOfData = true;
			} else {
				surnameField.setBorder(BorderFactory
						.createLineBorder(Color.gray));
			}

			if (dateOfBirth.equals("  .  .  ")) {
				dateOfBirthField.setBorder(BorderFactory
						.createLineBorder(Color.gray));
			} else {
				Integer day = Integer.parseInt(dateOfBirth.substring(0, 2));
				Integer month = Integer.parseInt(dateOfBirth.substring(3, 5));
				Integer year = Integer.parseInt(dateOfBirth.substring(6, 8));
				if (month < 1 || month > 12 || year < 0) {
					dateOfBirthField.setBorder(BorderFactory
							.createLineBorder(Color.red));
					lackOfData = true;
				} else if (day < 1) {
					dateOfBirthField.setBorder(BorderFactory
							.createLineBorder(Color.red));
					lackOfData = true;
				} else if ((month == 1 || month == 3 || month == 5
						|| month == 7 || month == 8 || month == 10 || month == 12)
						&& day > 31) {
					dateOfBirthField.setBorder(BorderFactory
							.createLineBorder(Color.red));
					lackOfData = true;
				} else if ((month == 4 || month == 6 || month == 9 || month == 11)
						&& day > 30) {
					dateOfBirthField.setBorder(BorderFactory
							.createLineBorder(Color.red));
					lackOfData = true;
				} else if (month == 2 && (year % 4 == 0) && day > 29) {
					dateOfBirthField.setBorder(BorderFactory
							.createLineBorder(Color.red));
					lackOfData = true;
				} else if (month == 2 && (year % 4 != 0) && day > 28) {
					dateOfBirthField.setBorder(BorderFactory
							.createLineBorder(Color.red));
					lackOfData = true;
				} else {
					dateOfBirthField.setBorder(BorderFactory
							.createLineBorder(Color.gray));
				}
			}
			return lackOfData;

		}
	}
}

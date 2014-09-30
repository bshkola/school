/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;

/**
 * @author bogdan
 * 
 */
class TableModel extends AbstractTableModel {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -5981556692320082509L;
	private List<DataModel> listOfData = null;
	private String[] namesOfColumns = { "№", "Прізвище та ім'я",
			"Дата народження", "Телефони", "Адреса", "Мати", "Батько" };

	private final static int NUMBER_GROUP_IDX = 0;
	private final static int SURNAME_NAME_IDX = 1;
	// private final static int NAME_IDX = 2;
	private final static int BIRTH_IDX = 2;
	private final static int TELEPHON_IDX = 3;
	private final static int ADDRESS_IDX = 4;
	private final static int MOTHER_IDX = 5;
	private final static int FATHER_IDX = 6;
	private boolean flag = false;

	public TableModel() {

	}

	@Override
	public int getColumnCount() {
		return namesOfColumns.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		if (listOfData == null)
			return 0;
		return listOfData.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		if (listOfData == null)
			return null;
		DataModel p = listOfData.get(rowIndex);
		switch (columnIndex) {
		case NUMBER_GROUP_IDX:
			if (flag == false) {
				return p.getNumber();
			} else {
				return p.getGroup();
			}
		case SURNAME_NAME_IDX:
			return p.getSurname() + " " + p.getName();
		case BIRTH_IDX:
			return p.getDateOfBirth();
		case TELEPHON_IDX: {
			// String telephones = p.getTelephones()[0];
			// for (int i = 1; i < 3; i++) {
			// if (p.getTelephones()[i].equals("")) {
			// break;
			// }
			// telephones = telephones.concat("; " + p.getTelephones()[i]);
			// }
			// return telephones;
			String telephones = "<html>" + p.getTelephones()[0];
			for (int i = 1; i < 3; i++) {
				if (p.getTelephones()[i].equals("")) {
					break;
				}
				telephones = telephones + ";<br> " + p.getTelephones()[i];
			}
			return telephones;
		}
		case ADDRESS_IDX:
			return p.getAddress();
		case MOTHER_IDX:
			return p.getMother();
		case FATHER_IDX:
			return p.getFather();
		default:
			return p;
		}
	}

	@Override
	public String getColumnName(final int column) {
		return namesOfColumns[column].toString();
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return false;
	}

	public void setModelData(final List<DataModel> listOfData) {
		this.listOfData = listOfData;
	}

	public void changeNameOfColumn(final int numberOfColumn,
			final String nameOfColumn) {
		if (numberOfColumn >= 0 && numberOfColumn < namesOfColumns.length) {
			namesOfColumns[numberOfColumn] = nameOfColumn;
			flag = true;
		}
	}

	public int getNumberOfTelephones(final int rowIndex) {
		int nrTelephones = 1;
		for (int i = 1; i < 3; i++) {
			if (listOfData.get(rowIndex).getTelephones()[i].equals("")) {
				break;
			}
			nrTelephones++;
		}
		return nrTelephones;
		// return listOfData.get(rowIndex).getTelephones().length;
	}
}

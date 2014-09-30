package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.awt.event.MouseEvent;

import javax.swing.JTable;

public class MyJTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3831969539236820610L;

	public MyJTable(final TableModel tableModel) {
		super(tableModel);
		resetHeight(tableModel);
	}

	public void resetHeight(final TableModel tableModel) {
		int rowCount = getRowCount();

		for (int i = 0; i < rowCount; i++) {
			setRowHeight(i,
					getRowHeight() * tableModel.getNumberOfTelephones(i));
		}
	}

	@Override
	public String getToolTipText(final MouseEvent e) {
		String tip = null;
		java.awt.Point p = e.getPoint();
		int rowIndex = rowAtPoint(p);
		int colIndex = columnAtPoint(p);
		int realColumnIndex = convertColumnIndexToModel(colIndex);

		if (rowIndex != -1) {
			if (realColumnIndex == 1) { // Sport column
				tip = "<html>Ім\'я та прізвище: "
						+ (String) getValueAt(rowIndex, 1) + "<br>"
						+ "Дата народження: "
						+ (String) getValueAt(rowIndex, 2) + "<br>"
						+ "Телефони: " + (String) getValueAt(rowIndex, 3)
						+ "<br>" + "Адреса: "
						+ (String) getValueAt(rowIndex, 4) + "<br>" + "Мати: "
						+ (String) getValueAt(rowIndex, 5) + "<br>"
						+ "Батько: " + (String) getValueAt(rowIndex, 6)
						+ "<br>" + " </html>";

			} else {
				tip = super.getToolTipText(e);
			}
		}
		return tip;
	}

}

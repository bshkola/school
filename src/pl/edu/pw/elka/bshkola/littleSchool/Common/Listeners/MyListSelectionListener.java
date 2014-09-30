/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Common.Listeners;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author bogdan
 * 
 */
public class MyListSelectionListener implements ListSelectionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event
	 * .ListSelectionEvent)
	 */

	private final int numberOfTable;
	private final JTable[] tables;

	public MyListSelectionListener(final int i, final JTable[] tables) {
		this.numberOfTable = i;
		this.tables = tables;
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {

		boolean isAdjusting = e.getValueIsAdjusting();
		if (isAdjusting == true) {
			for (int i = 0; i < tables.length; i++) {
				if (i != numberOfTable) {
					tables[i].clearSelection();
				}
			}
		}
	}
}

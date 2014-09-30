/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.BlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import pl.edu.pw.elka.bshkola.littleSchool.Common.DataInGroupModel;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;
import pl.edu.pw.elka.bshkola.littleSchool.Events.EnableMainWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;

/**
 * @author bogdan
 *
 */
public class SimpleListWindow extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6966527681589226786L;
	
	private final BlockingQueue<Event> blockingQueue;
	private final String NEWLINE = "\n";
	
	public SimpleListWindow(final BlockingQueue<Event> blockingQueue, final DataInGroupModel[] data, final MainWindow mainWindow) {

		super(mainWindow);
		this.blockingQueue = blockingQueue;
		setTitle("Список дітей");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(600, 700));
		setMinimumSize(new Dimension(600, 700));
		setVisible(true);
		pack();
		
		initView(data);
		initListeners();
	}

	private void initView(final DataInGroupModel[] data) {
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		TitledBorder border = BorderFactory.createTitledBorder("Список дітей");
		border.setTitleJustification(TitledBorder.CENTER);
		textArea.setBorder(border);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane);
		for (int i = 0; i < data.length; i++)
		{
			textArea.append(data[i].getNameOfGroup().toUpperCase() + NEWLINE);
			for (DataModel dataModel : data[i].getListOfData())
			{
				textArea.append(dataModel.getSurname() + " " + dataModel.getName() + NEWLINE);
			}
			textArea.append(NEWLINE);
		}
	}

	private void initListeners() {

		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(final WindowEvent e) {
				blockingQueue.add(new EnableMainWindowEvent());
			}
		});
	}
	
}

package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.awt.Dimension;

import javax.swing.JDialog;

public class AboutWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1387628334960364569L;

	public AboutWindow(final MainWindow mainWindow) {

		super(mainWindow);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initView();
		setVisible(true);

	}

	/**
	 * 
	 */
	private void initView() {

		setTitle("Про програму");
		setMinimumSize(new Dimension(200, 200));
		setResizable(false);
		// add(new JTextArea(
		// "Ця програма не є власністю групи динамічно-варіативного розвитку "
		// + "\"Маленька школа \" "));
		pack();
	}
}

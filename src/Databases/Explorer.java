package Databases;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Explorer extends JFrame {
	File file;
	Scanner scan;
	String path;
	String[] listOfFiles;
	JTextField txtPath;
	JLabel lblLocation;
	JLabel child[];
	JPanel childPanel;
	JPanel masterPanel;

	public Explorer() {
		setVisible(true);
		lblLocation = new JLabel("Location: ");
		/*
		 * the declaration of panels
		 */
		masterPanel = new JPanel();
		childPanel = new JPanel();
		JPanel panel = new JPanel();

		/* declaration of other components */

		txtPath = new JTextField("", 20);
		/* addition of components to panel for layout */
		panel.add(lblLocation);
		panel.add(txtPath);
		/* adding to master panel, for sophisticated layout */
		masterPanel.add(panel, BorderLayout.NORTH);
		masterPanel.add(childPanel, BorderLayout.SOUTH);

		getContentPane().add(masterPanel);
		/*
		 * this place from where address is fetched like
		 * /home/revolution/Desktop etc on ubuntu
		 */

		txtPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ev) {
				childPanel.removeAll();
				path = new String(txtPath.getText());// the absolute path
				file = new File(path);
				File childFiles[];
				String name = path.substring(path.lastIndexOf('/') + 1,
						path.length());// the name of the directory being
										// displayed
				setTitle(name);

				if (!file.isDirectory()) {
					JOptionPane.showMessageDialog(null,
							"Error file is not a directory");
				} else {
					listOfFiles = file.list();
					child = new JLabel[listOfFiles.length];// labels equal to
															// the number fo
															// files and with
															// name of the
															// coresponding
															// file/folder

					childFiles = new File[listOfFiles.length];// references to
																// files
					childPanel.setLayout(new GridLayout(listOfFiles.length / 2,
							listOfFiles.length / 2));// setting grid layout

					for (int i = 0; i < listOfFiles.length; i++) {
						childFiles[i] = new File(listOfFiles[i]);
						child[i] = new JLabel(listOfFiles[i]);
						child[i].setToolTipText(childFiles[i].isFile() ? "File"
								: "Folder");
						childPanel.add(child[i]);
					}
					if (path.equals("src")) {
						childPanel.add(new JButton("safsgdfg"));
					} else {
						childPanel.add(new JButton("1111111"));
						childPanel.setVisible(true);
					}
				}
				childPanel.revalidate();
			}

		});
	}

	public static void main(final String[] args) {
		new Explorer();
	}
}
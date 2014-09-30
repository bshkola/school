package pl.edu.pw.elka.bshkola.littleSchool.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import pl.edu.pw.elka.bshkola.littleSchool.Events.CloseFolderTreeWindowEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.Event;

public class FolderTreeWindow extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6464727262942648004L;

	public static final ImageIcon ICON_COMPUTER = new ImageIcon("computer.gif");
	public static final ImageIcon ICON_DISK = new ImageIcon("disk.gif");
	public static final ImageIcon ICON_FOLDER = new ImageIcon("folder.gif");
	public static final ImageIcon ICON_EXPANDEDFOLDER = new ImageIcon(
			"expandedfolder.gif");

	protected JTree m_tree;
	protected DefaultTreeModel m_model;
	protected JTextField m_display;
	protected JPanel buttonsPanel;
	protected JButton okButton;
	protected JButton cancelButton;

	public FolderTreeWindow(final BlockingQueue<Event> blockingQueue,
			final Properties properties, final SettingsWindow settingsWindow) {

		super(settingsWindow);
		setSize(300, 500);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		DefaultMutableTreeNode top = new DefaultMutableTreeNode(new IconData(
				ICON_COMPUTER, null, "Computer"));

		DefaultMutableTreeNode node;
		File[] roots = File.listRoots();
		for (int k = 0; k < roots.length; k++) {
			node = new DefaultMutableTreeNode(new IconData(ICON_DISK, null,
					new FileNode(roots[k])));
			top.add(node);
			node.add(new DefaultMutableTreeNode(new Boolean(true)));
		}

		m_model = new DefaultTreeModel(top);
		m_tree = new JTree(m_model);

		m_tree.putClientProperty("JTree.lineStyle", "Angled");

		TreeCellRenderer renderer = new IconCellRenderer();
		m_tree.setCellRenderer(renderer);

		m_tree.addTreeExpansionListener(new DirExpansionListener());

		m_tree.addTreeSelectionListener(new DirSelectionListener());

		m_tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		m_tree.setShowsRootHandles(true);
		m_tree.setEditable(false);

		JScrollPane s = new JScrollPane();
		s.getViewport().add(m_tree);
		getContentPane().add(s, BorderLayout.CENTER);

		m_display = new JTextField("Select some folder:");
		m_display.setEditable(false);
		getContentPane().add(m_display, BorderLayout.NORTH);

		okButton = new JButton("OK");
		okButton.setEnabled(false);

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				properties.setProperty("PDF_folder_path", m_display.getText());
				dispose();
			}
		});

		cancelButton = new JButton("Cancel");

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				dispose();
			}
		});

		buttonsPanel = new JPanel();
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

		WindowListener wndCloser = new WindowAdapter() {
			@Override
			public void windowClosed(final WindowEvent e) {
				blockingQueue.add(new CloseFolderTreeWindowEvent());
			}
		};
		addWindowListener(wndCloser);

		setVisible(true);
	}

	DefaultMutableTreeNode getTreeNode(final TreePath path) {
		return (DefaultMutableTreeNode) (path.getLastPathComponent());
	}

	FileNode getFileNode(final DefaultMutableTreeNode node) {
		if (node == null)
			return null;
		Object obj = node.getUserObject();
		if (obj instanceof IconData)
			obj = ((IconData) obj).getObject();
		if (obj instanceof FileNode)
			return (FileNode) obj;
		else
			return null;
	}

	// Make sure expansion is threaded and updating the tree model
	// only occurs within the event dispatching thread.
	class DirExpansionListener implements TreeExpansionListener {
		@Override
		public void treeExpanded(final TreeExpansionEvent event) {
			final DefaultMutableTreeNode node = getTreeNode(event.getPath());
			final FileNode fnode = getFileNode(node);

			Thread runner = new Thread() {
				@Override
				public void run() {
					if (fnode != null && fnode.expand(node)) {
						Runnable runnable = new Runnable() {
							@Override
							public void run() {
								m_model.reload(node);
							}
						};
						SwingUtilities.invokeLater(runnable);
					}
				}
			};
			runner.start();
		}

		@Override
		public void treeCollapsed(final TreeExpansionEvent event) {
		}
	}

	class DirSelectionListener implements TreeSelectionListener {
		@Override
		public void valueChanged(final TreeSelectionEvent event) {
			DefaultMutableTreeNode node = getTreeNode(event.getPath());
			FileNode fnode = getFileNode(node);
			if (fnode != null) {
				m_display.setText(fnode.getFile().getAbsolutePath());
				okButton.setEnabled(true);
			} else {
				m_display.setText("Select some folder:");
				okButton.setEnabled(false);
			}
		}
	}

}

class IconCellRenderer extends JLabel implements TreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7768419078421763642L;

	protected Color m_textSelectionColor;
	protected Color m_textNonSelectionColor;
	protected Color m_bkSelectionColor;
	protected Color m_bkNonSelectionColor;
	protected Color m_borderSelectionColor;

	protected boolean m_selected;

	public IconCellRenderer() {
		super();
		m_textSelectionColor = UIManager.getColor("Tree.selectionForeground");
		m_textNonSelectionColor = UIManager.getColor("Tree.textForeground");
		m_bkSelectionColor = UIManager.getColor("Tree.selectionBackground");
		m_bkNonSelectionColor = UIManager.getColor("Tree.textBackground");
		m_borderSelectionColor = UIManager
				.getColor("Tree.selectionBorderColor");
		setOpaque(false);
	}

	@Override
	public Component getTreeCellRendererComponent(final JTree tree,
			final Object value, final boolean sel, final boolean expanded,
			final boolean leaf, final int row, final boolean hasFocus)

	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object obj = node.getUserObject();
		setText(obj.toString());

		if (obj instanceof Boolean)
			setText("Retrieving data...");

		if (obj instanceof IconData) {
			IconData idata = (IconData) obj;
			if (expanded)
				setIcon(idata.getExpandedIcon());
			else
				setIcon(idata.getIcon());
		} else
			setIcon(null);

		setFont(tree.getFont());
		setForeground(sel ? m_textSelectionColor : m_textNonSelectionColor);
		setBackground(sel ? m_bkSelectionColor : m_bkNonSelectionColor);
		m_selected = sel;
		return this;
	}

	@Override
	public void paintComponent(final Graphics g) {
		Color bColor = getBackground();
		Icon icon = getIcon();

		g.setColor(bColor);
		int offset = 0;
		if (icon != null && getText() != null)
			offset = (icon.getIconWidth() + getIconTextGap());
		g.fillRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);

		if (m_selected) {
			g.setColor(m_borderSelectionColor);
			g.drawRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
		}
		super.paintComponent(g);
	}
}

class IconData {
	protected Icon m_icon;
	protected Icon m_expandedIcon;
	protected Object m_data;

	public IconData(final Icon icon, final Object data) {
		m_icon = icon;
		m_expandedIcon = null;
		m_data = data;
	}

	public IconData(final Icon icon, final Icon expandedIcon, final Object data) {
		m_icon = icon;
		m_expandedIcon = expandedIcon;
		m_data = data;
	}

	public Icon getIcon() {
		return m_icon;
	}

	public Icon getExpandedIcon() {
		return m_expandedIcon != null ? m_expandedIcon : m_icon;
	}

	public Object getObject() {
		return m_data;
	}

	@Override
	public String toString() {
		return m_data.toString();
	}
}

class FileNode {
	protected File m_file;

	public FileNode(final File file) {
		m_file = file;
	}

	public File getFile() {
		return m_file;
	}

	@Override
	public String toString() {
		return m_file.getName().length() > 0 ? m_file.getName() : m_file
				.getPath();
	}

	public boolean expand(final DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode flag = (DefaultMutableTreeNode) parent
				.getFirstChild();
		if (flag == null) // No flag
			return false;
		Object obj = flag.getUserObject();
		if (!(obj instanceof Boolean))
			return false; // Already expanded

		parent.removeAllChildren(); // Remove Flag

		File[] files = listFiles();
		if (files == null)
			return true;

		Vector<FileNode> v = new Vector<FileNode>();

		for (int k = 0; k < files.length; k++) {
			File f = files[k];
			if (!(f.isDirectory()))
				continue;

			FileNode newNode = new FileNode(f);

			boolean isAdded = false;
			for (int i = 0; i < v.size(); i++) {
				FileNode nd = v.elementAt(i);
				if (newNode.compareTo(nd) < 0) {
					v.insertElementAt(newNode, i);
					isAdded = true;
					break;
				}
			}
			if (!isAdded)
				v.addElement(newNode);
		}

		for (int i = 0; i < v.size(); i++) {
			FileNode nd = v.elementAt(i);
			IconData idata = new IconData(FolderTreeWindow.ICON_FOLDER,
					FolderTreeWindow.ICON_EXPANDEDFOLDER, nd);
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(idata);
			parent.add(node);

			if (nd.hasSubDirs())
				node.add(new DefaultMutableTreeNode(new Boolean(true)));
		}

		return true;
	}

	public boolean hasSubDirs() {
		File[] files = listFiles();
		if (files == null)
			return false;
		for (int k = 0; k < files.length; k++) {
			if (files[k].isDirectory())
				return true;
		}
		return false;
	}

	public int compareTo(final FileNode toCompare) {
		return m_file.getName().compareToIgnoreCase(toCompare.m_file.getName());
	}

	protected File[] listFiles() {
		if (!m_file.isDirectory())
			return null;
		try {
			return m_file.listFiles();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error reading directory "
					+ m_file.getAbsolutePath(), "Warning",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}
}

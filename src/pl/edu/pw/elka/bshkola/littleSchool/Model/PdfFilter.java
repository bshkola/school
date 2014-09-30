/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Model;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author bogdan
 *
 */
public class PdfFilter extends FileFilter{

	@Override
	public boolean accept(final File file) {
		if (file.isDirectory()) {
			return true;
		}
		else if (file.getName().toLowerCase().endsWith(".pdf"))
			return true;
		else
			return false;
	}

	@Override
	public String getDescription() {
		return "No description";
	}

}

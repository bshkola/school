/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import pl.edu.pw.elka.bshkola.littleSchool.Common.ApplicationProperties;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataInGroupModel;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;
import pl.edu.pw.elka.bshkola.littleSchool.Common.LackOfDatabaseException;

import com.itextpdf.text.DocumentException;

/**
 * @author bogdan
 * 
 *         Klasa modelu
 */
public class Model {

	private Program program;

	public Model() {
		program = new Program();
	}

	public void loadDatabase(final String nameOfDatabase)
			throws LackOfDatabaseException, FileNotFoundException, IOException,
			ClassNotFoundException {
		program.loadDatabase(nameOfDatabase);
	}

	public void createDatabase() throws FileNotFoundException, IOException {
		program.createDatabase();
	}

	public void saveDatabase() throws FileNotFoundException, IOException {
		program.saveDatabase();
	}

	public void addData(final DataModel dataModel) {
		program.addData(dataModel);
	}

	public String[] getListOfGroups() {
		return program.getListOfGroups();
	}

	public DataInGroupModel[] getDataFromGroup() {
		return program.getDataFromGroup();
	}

	public void deleteData(final String surname_name, final String nameOfGroup) {
		program.deleteData(surname_name, nameOfGroup);
	}

	public DataModel getData(final String surname_name, final String nameOfGroup) {
		return program.getData(surname_name, nameOfGroup);
	}

	public List<DataModel> searchData(final String searchingText) {
		return program.searchData(searchingText);
	}

	public List<DataModel> searchDataByMonth(final int monthIndex) {
		return program.searchDataByMonth(monthIndex);
	}

	public String createPdfFile() throws DocumentException, IOException {
		return program.createPdfFile();
	}

	public void createPdfListFile() throws FileNotFoundException,
			DocumentException, IOException {
		program.createPdfListFile();
	}

	public String createBirthFile(final int monthIndex) throws IOException,
			DocumentException {
		return program.createBirthFile(monthIndex);
	}

	public ApplicationProperties loadProperties() throws IOException {
		return program.loadProperties();
	}

	public void saveProperties() throws IOException {
		program.saveProperties();
	}

	public String[] findDatabases() {
		return program.findDatabases();
	}

}
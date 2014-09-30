/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import pl.edu.pw.elka.bshkola.littleSchool.Common.ApplicationProperties;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataInGroupModel;
import pl.edu.pw.elka.bshkola.littleSchool.Common.DataModel;
import pl.edu.pw.elka.bshkola.littleSchool.Common.LackOfDatabaseException;

import com.itextpdf.text.DocumentException;

/**
 * @author bogdan
 * 
 */
class Program {

	private Database database;
	private ApplicationProperties applicationProperties;
	private String actualDatabaseName;

	private final String DATABASE_PATH = "." + File.separator + "src"
			+ File.separator + "Databases";

	public Program() {
		applicationProperties = new ApplicationProperties();

		// for (int i = 13; i < 25; i++) {
		// database = new Database();
		// ObjectOutputStream oos;
		// try {
		// oos = new ObjectOutputStream(new FileOutputStream(DATABASE_PATH
		// + File.separator + new Integer(i).toString() + "-"
		// + new Integer(i + 1).toString() + ".data"));
		// oos.writeObject(database);
		// System.out.println("SAVE: " + actualDatabaseName + ".data");
		// if (oos != null) {
		// oos.close();
		// }
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}

	void loadDatabase(final String nameOfDatabase)
			throws LackOfDatabaseException, FileNotFoundException, IOException,
			ClassNotFoundException {

		File actualDatabaseFile;
		if (nameOfDatabase == null) {
			actualDatabaseName = applicationProperties.getProperties()
					.getProperty("default_database_name");
			actualDatabaseFile = new File(DATABASE_PATH + File.separator
					+ actualDatabaseName + ".data");

		} else {
			actualDatabaseFile = new File(DATABASE_PATH + File.separator
					+ nameOfDatabase + ".data");
			actualDatabaseName = nameOfDatabase;
		}
		if (!actualDatabaseFile.exists() || actualDatabaseFile.isDirectory()) {
			throw new LackOfDatabaseException();
		}

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				actualDatabaseFile));
		database = (Database) ois.readObject();
		if (ois != null) {
			ois.close();
		}

	}

	public void createDatabase() throws FileNotFoundException, IOException {

		database = new Database();
		actualDatabaseName = applicationProperties.getProperties().getProperty(
				"default_database_name");
		saveDatabase();

	}

	public void saveDatabase() throws FileNotFoundException, IOException {

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				DATABASE_PATH + File.separator + actualDatabaseName + ".data"));
		oos.writeObject(database);
		if (oos != null) {
			oos.close();
		}

	}

	public void addData(final DataModel dataModel) {
		database.addData(dataModel);
	}

	public String[] getListOfGroups() {
		return database.getListOfGroups();
	}

	public DataInGroupModel[] getDataFromGroup() {
		return database.getDataFromGroup();
	}

	public void deleteData(final String surname_name, final String nameOfGroup) {
		database.deleteData(surname_name, nameOfGroup);
	}

	public DataModel getData(final String surname_name, final String nameOfGroup) {
		return database.getData(surname_name, nameOfGroup);
	}

	public List<DataModel> searchData(final String searchingText) {
		return database.searchData(searchingText);
	}

	public List<DataModel> searchDataByMonth(final int monthIndex) {
		return database.searchDataByMonth(monthIndex);
	}

	public String createPdfFile() throws DocumentException, IOException {
		String filePath = database.createPdfFullBaseFile(
				applicationProperties.getProperties().getProperty(
						"PDF_folder_path")
						+ applicationProperties.getProperties().getProperty(
								"PDF_full_list_file_name"), actualDatabaseName);
		return filePath;
	}

	public void createPdfListFile() throws FileNotFoundException,
			DocumentException, IOException {
		database.createPdfListFile();
	}

	public String createBirthFile(final int monthIndex) throws IOException,
			DocumentException {
		String filePath = database.createBirthFile(
				monthIndex,
				applicationProperties.getProperties().getProperty(
						"PDF_folder_path")
						+ applicationProperties.getProperties().getProperty(
								"PDF_birth_list_file_name"));
		return filePath;
	}

	public ApplicationProperties loadProperties() throws IOException {
		applicationProperties.loadProperties();
		return applicationProperties;
	}

	public void saveProperties() throws IOException {
		applicationProperties.saveProperties();
	}

	public String[] findDatabases() {
		File folder = new File(DATABASE_PATH);
		File[] files = folder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(final File directory, final String filename) {
				return filename.endsWith(".data");
			}
		});

		String[] namesOfFiles = new String[files.length];
		for (int fileInList = 0; fileInList < files.length; fileInList++) {
			namesOfFiles[fileInList] = files[fileInList].toString().substring(
					DATABASE_PATH.length() + 1,
					files[fileInList].toString().length() - 5);
			// System.out.println(namesOfFiles[fileInList]);
		}

		return namesOfFiles;

	}
}

package pl.edu.pw.elka.bshkola.littleSchool.Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {

	private Properties applicationProps;

	private final String PROPERTIES_PATH = "." + File.separator + "properties"
			+ File.separator + "appProperties";

	public ApplicationProperties() {

	}

	public void loadProperties() throws IOException {

		applicationProps = new Properties();

		try {
			FileInputStream in = new FileInputStream(PROPERTIES_PATH);
			applicationProps.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			createProperties();
		}
		// System.out.println("appProperties: ");
		// applicationProps.list(System.out);

	}

	private void createProperties() throws IOException {

		applicationProps.setProperty("language", "ukr");
		applicationProps.setProperty("PDF_folder_path", "src/");
		applicationProps
				.setProperty("PDF_full_list_file_name", "full_list.pdf");
		applicationProps.setProperty("PDF_birth_list_file_name",
				"birth_list.pdf");
		applicationProps.setProperty("default_database_name", "12-13");
		saveProperties();

	}

	public void saveProperties() throws IOException {
		FileOutputStream out = new FileOutputStream(PROPERTIES_PATH);
		applicationProps.store(out, "---No Comment---");
		out.close();
		System.out.println("Saved");
	}

	public Properties getProperties() {
		return applicationProps;
	}

	public void setProperties(final Properties modyfiedProperties) {
		applicationProps = modyfiedProperties;
	}
}

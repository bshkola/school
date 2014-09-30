package pl.edu.pw.elka.bshkola.littleSchool.Events;

import java.util.Properties;

public class CreateFolderTreeWindowEvent extends Event {

	private final Properties modyfiedProperties;

	public CreateFolderTreeWindowEvent(final Properties modyfiedProperties) {
		this.modyfiedProperties = modyfiedProperties;
	}

	public Properties getModyfiedProperties() {
		return modyfiedProperties;
	}

}

/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Common;

import java.util.List;

/**
 * @author bogdan
 *
 */
public class DataInGroupModel {
	
	private String nameOfGroup;
	private List<DataModel> listOfData;
	
	public DataInGroupModel(final String nameOfGroup, final List<DataModel> data) {

		this.nameOfGroup = nameOfGroup;
		listOfData = data;
		
	}

	public String getNameOfGroup() {
		return nameOfGroup;
	}

	public List<DataModel> getListOfData() {
		return listOfData;
	}
	
}

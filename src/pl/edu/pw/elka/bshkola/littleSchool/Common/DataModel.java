/**
 * 
 */
package pl.edu.pw.elka.bshkola.littleSchool.Common;

import pl.edu.pw.elka.bshkola.littleSchool.Events.AddingDataEvent;
import pl.edu.pw.elka.bshkola.littleSchool.Events.EditingDataEvent;

/**
 * @author bogdan
 * 
 */
public class DataModel {

	private String number;
	private String surname;
	private String name;
	private String dateOfBirth;
	private String[] telephones;
	private String address;
	private String mother;
	private String father;
	private String group;

	public DataModel(final AddingDataEvent addingDataEvent) {

		surname = addingDataEvent.getSurname();
		name = addingDataEvent.getName();
		dateOfBirth = addingDataEvent.getDateOfBirth();
		telephones = addingDataEvent.getTelephone();
		address = addingDataEvent.getAddress();
		mother = addingDataEvent.getMother();
		father = addingDataEvent.getFather();
		group = addingDataEvent.getGroup();

	}

	public DataModel(final EditingDataEvent editingDataEvent) {

		surname = editingDataEvent.getSurname();
		name = editingDataEvent.getName();
		dateOfBirth = editingDataEvent.getDateOfBirth();
		telephones = editingDataEvent.getTelephone();
		address = editingDataEvent.getAddress();
		mother = editingDataEvent.getMother();
		father = editingDataEvent.getFather();
		group = editingDataEvent.getGroup();

	}

	public DataModel(final int i, final String surname, final String name,
			final String dateOfBirth, final String[] telephones,
			final String address, final String mother, final String father) {

		this.number = new Integer(i).toString();
		this.surname = surname;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.telephones = telephones;
		this.address = address;
		this.mother = mother;
		this.father = father;

	}

	public DataModel(final String nameOfGroup, final String surname,
			final String name, final String dateOfBirth,
			final String[] telephones, final String address,
			final String mother, final String father) {

		this.surname = surname;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.telephones = telephones;
		this.address = address;
		this.mother = mother;
		this.father = father;
		this.group = nameOfGroup;

	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(final String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String[] getTelephones() {
		return telephones;
	}

	public void setTelephone(final String[] telephones) {
		this.telephones = telephones;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getMother() {
		return mother;
	}

	public void setMother(final String mother) {
		this.mother = mother;
	}

	public String getFather() {
		return father;
	}

	public void setFather(final String father) {
		this.father = father;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(final String group) {
		this.group = group;
	}

}

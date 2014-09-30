package pl.edu.pw.elka.bshkola.littleSchool.Events;

public class PrintBirthEvent extends Event {

	private final int monthIndex;

	public PrintBirthEvent(final int monthIndex) {
		this.monthIndex = monthIndex;
	}

	public int getMonthIndex() {
		return monthIndex;
	}

}

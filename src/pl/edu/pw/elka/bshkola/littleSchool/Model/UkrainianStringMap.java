package pl.edu.pw.elka.bshkola.littleSchool.Model;

import java.util.HashMap;

public class UkrainianStringMap {

	private HashMap<String, Integer> ukrainianHashMap;

	public UkrainianStringMap() {

		ukrainianHashMap = new HashMap<String, Integer>();
		ukrainianHashMap.put(" ", 0);
		ukrainianHashMap.put("�", 1);
		ukrainianHashMap.put("�", 2);
		ukrainianHashMap.put("�", 3);
		ukrainianHashMap.put("�", 4);
		ukrainianHashMap.put("�", 5);
		ukrainianHashMap.put("�", 6);
		ukrainianHashMap.put("�", 7);
		ukrainianHashMap.put("�", 8);
		ukrainianHashMap.put("�", 9);
		ukrainianHashMap.put("�", 10);
		ukrainianHashMap.put("�", 11);
		ukrainianHashMap.put("�", 12);
		ukrainianHashMap.put("�", 13);
		ukrainianHashMap.put("�", 14);
		ukrainianHashMap.put("�", 15);
		ukrainianHashMap.put("�", 16);
		ukrainianHashMap.put("�", 17);
		ukrainianHashMap.put("�", 18);
		ukrainianHashMap.put("�", 19);
		ukrainianHashMap.put("�", 20);
		ukrainianHashMap.put("�", 21);
		ukrainianHashMap.put("�", 22);
		ukrainianHashMap.put("�", 23);
		ukrainianHashMap.put("�", 24);
		ukrainianHashMap.put("�", 25);
		ukrainianHashMap.put("�", 26);
		ukrainianHashMap.put("�", 27);
		ukrainianHashMap.put("�", 28);
		ukrainianHashMap.put("�", 29);
		ukrainianHashMap.put("�", 30);
		ukrainianHashMap.put("�", 31);
		ukrainianHashMap.put("�", 32);
		ukrainianHashMap.put("�", 33);
		ukrainianHashMap.put("'", 34);

	}

	public int getUkrainianLetterValue(final String string) {
		// System.out.println(string);
		if (ukrainianHashMap.containsKey(string.toLowerCase())) {
			return ukrainianHashMap.get(string.toLowerCase());
		} else {
			return 0;
		}
	}
}

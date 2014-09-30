package pl.edu.pw.elka.bshkola.littleSchool.Model;

import java.util.HashMap;

public class UkrainianStringMap {

	private HashMap<String, Integer> ukrainianHashMap;

	public UkrainianStringMap() {

		ukrainianHashMap = new HashMap<String, Integer>();
		ukrainianHashMap.put(" ", 0);
		ukrainianHashMap.put("à", 1);
		ukrainianHashMap.put("á", 2);
		ukrainianHashMap.put("â", 3);
		ukrainianHashMap.put("ã", 4);
		ukrainianHashMap.put("´", 5);
		ukrainianHashMap.put("ä", 6);
		ukrainianHashMap.put("å", 7);
		ukrainianHashMap.put("º", 8);
		ukrainianHashMap.put("æ", 9);
		ukrainianHashMap.put("ç", 10);
		ukrainianHashMap.put("è", 11);
		ukrainianHashMap.put("³", 12);
		ukrainianHashMap.put("¿", 13);
		ukrainianHashMap.put("é", 14);
		ukrainianHashMap.put("ê", 15);
		ukrainianHashMap.put("ë", 16);
		ukrainianHashMap.put("ì", 17);
		ukrainianHashMap.put("í", 18);
		ukrainianHashMap.put("î", 19);
		ukrainianHashMap.put("ï", 20);
		ukrainianHashMap.put("ð", 21);
		ukrainianHashMap.put("ñ", 22);
		ukrainianHashMap.put("ò", 23);
		ukrainianHashMap.put("ó", 24);
		ukrainianHashMap.put("ô", 25);
		ukrainianHashMap.put("õ", 26);
		ukrainianHashMap.put("ö", 27);
		ukrainianHashMap.put("÷", 28);
		ukrainianHashMap.put("ø", 29);
		ukrainianHashMap.put("ù", 30);
		ukrainianHashMap.put("ü", 31);
		ukrainianHashMap.put("þ", 32);
		ukrainianHashMap.put("ÿ", 33);
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

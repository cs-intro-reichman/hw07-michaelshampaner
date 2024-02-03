
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		if (str.length() == 1) {
			return "";
		}
		return str.substring(1);
	}

	public static int levenshtein(String word1, String word2) {
		String lowerCaseWord1 = word1.toLowerCase();
		String lowerCaseWord2 = word2.toLowerCase();
		if (word1.length() == 0) {
			return word2.length();
		}
		if (word2.length() == 0) {
			return word1.length();
		}
		if (lowerCaseWord1.charAt(0) == lowerCaseWord2.charAt(0)) {
			return levenshtein(tail(lowerCaseWord1), tail(lowerCaseWord2));
		}
		else{
			int distance1 = levenshtein(tail(lowerCaseWord1), tail(lowerCaseWord2));
			int distance2 = levenshtein(lowerCaseWord1, tail(lowerCaseWord2));
			int distance3 = levenshtein(tail(lowerCaseWord1), tail(lowerCaseWord2));
			int tempMin = Math.min(distance1,distance2);
			int actualMin = Math.min(distance3, tempMin);
			return 1 + actualMin;
		}
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);

		for (int i = 0; i < dictionary.length; i++) {
			String word = in.readLine();
			dictionary[i] = word;
		}

		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		String minLevDistanceString = dictionary[0];
		int minLevDistanceNum = levenshtein(dictionary[0], word);
		for (int i = 1; i < dictionary.length; i++) {
			if (levenshtein(dictionary[i], word) < minLevDistanceNum) {
				minLevDistanceString = dictionary[i];
				minLevDistanceNum = levenshtein(dictionary[i], word);
			}
		}
		if (minLevDistanceNum <= threshold) {
			return minLevDistanceString;
		}
		else{
			return word;
		}
	}

}

package StringAlgorithms;
import java.util.HashMap;

public class PatternMatching {

	public static void main(String[] args) {
		String text = "aabbcaba";
		String pattern = "aabb";
		int count = RabinKarp(text, pattern);
		System.out.println("The index where pattern occurs in text is: " + count + ".");
	}
	
	public static int bruteForce(String text, String pattern) {
		int index = 0;
		int pat = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == pattern.charAt(pat)) {
				pat++;
				if (pat == pattern.length()) {
					index++;
					pat = 0;
				}
			} else
				pat = 0;
		}
		return index;
	}

	private static HashMap<Character, Integer> lastTable(String pattern) {
		HashMap<Character, Integer> lastT = new HashMap<>();
		for (int i = 0; i < pattern.length(); i++) {
			lastT.put(pattern.charAt(i), i);
		}
		return lastT;
	}
	
	public static int boyerMoore(String text, String pattern) {
		HashMap<Character, Integer> last = lastTable(pattern);
		int t = 0;
		int stop = text.length() - pattern.length();
		while (t <= stop) {
			int p = pattern.length() - 1;
			while (p >= 0 && text.charAt(p + t) == pattern.charAt(p)) {
				p--;
			}
			if (p == -1) //It's a match.
				return t; 
			else { //Not a match
				int shift = last.getOrDefault(text.charAt(p+t), -1);
				if (shift < p) {
					t += p - shift;
				} else 
					t++;
			}
		}
		return -1;
	}
	
	private static int[] failureTable(String pattern) {
		int[] failureT = new int[pattern.length()];
		failureT[0] = 0;
		int f = 1, p = 0;
		while (f < pattern.length()) {
			if (pattern.charAt(p) == pattern.charAt(f)) {
				failureT[f] = p + 1;
				p++; f++;
			} else if (pattern.charAt(p) != pattern.charAt(f) && p > 0) {
				p -= 1; 
			} else {
				failureT[f] = 0;
				f++;
			}
		}
		return failureT;
	}
	
	public static int KMP(String text, String pattern) {
		int[] table = failureTable(pattern);
		System.out.print("Failure Table: ");
		for (int i : table) {
			System.out.print(i + " ");
		}
		System.out.println();
		int t = 0, p = 0;
		int stop = text.length() - pattern.length();
		while (t <= stop) {
			while (p < pattern.length() && pattern.charAt(p) == text.charAt(t + p)) {
					p++;
			}
			if (p == pattern.length())
				return t;
			else if (p == 0)
				t++;
			else {
				int shift = table[p - 1];
				t += p - shift;
				p = shift;
			}
		}
		return -1;
	}
	
	private static HashMap<Character, Integer> hashes() {
		String alphabets = "abcdefghijklmnopqrstuvwxyz";
		HashMap<Character, Integer> hashCodes = new HashMap<>();
		for (int i = 0; i < alphabets.length(); i++) {
			hashCodes.put(alphabets.charAt(i), i);
		}
		return hashCodes;
	}
	
	private static int hashCode(String pattern) {
		HashMap<Character, Integer> hashCodes = hashes();
		int code = 0;
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			code += hashCodes.get(c);
		}
		return code;
	}
	
	public static int RabinKarp(String text, String pattern) {
		int patternCode = hashCode(pattern);
		System.out.println("The pattern's hashCode: " + patternCode + ".");
		int t = 0, stop = text.length() - pattern.length();
		while (t <= stop) {
			int i = t, j = 0;
			String check = "";
			while (j < pattern.length()) {
				check += text.charAt(i);
				i++; j++;
			}
			int code = hashCode(check);
			if (code == patternCode) {
				int p = 0;
				while (text.charAt(p + t) == pattern.charAt(p)) {
					p++;
					if (p == pattern.length())
						return t;
				}
			}
			t++;
		}
		return -1;
	}
}






















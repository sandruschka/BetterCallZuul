package Utils;

public class RegexUtils {
	
	final private String alphabeticRegex = "^[a-zA-Z]*$";
	final private String numbericRegex = "[0-9]+";
	
	public boolean alphabeticString(String str) {
		return str.matches(alphabeticRegex);
	}
	
	public boolean numericString(String str) {
		return str.matches(numbericRegex);
	}
	
}

package sg.nus.iss.shoppingCart.validation;

public class PasswordValidate {


	public static int min_length = 8;
	public static boolean need_upper = true;
	public static boolean need_lower = true;
	public static boolean need_digit = true;
	public static boolean need_special = false;
	
	// method used for checking if a password is valid
	public static boolean isValidPassword(String password) {
		
		if (password.length() < min_length) {
			return false;
		}
		
		int have_upper = 0;
		int have_lower = 0;
		int have_digit = 0;
		int have_special = 0;
		
		int i = 0;
		char char_i;
		
		for (i=0;i<password.length();i++) {
			char_i = password.charAt(i);
				
			if (Character.isLetter(char_i) & Character.isUpperCase(char_i)) {
				// is upper case
				have_upper += 1;
			} else if (Character.isLetter(char_i) & Character.isLowerCase(char_i)) {
				// is lower case
				have_lower += 1;
			} else if (Character.isDigit(char_i)) {
				// is a digit
				have_digit += 1;
			} else {
				// others
				have_special += 1;
			}
		}
		
		if (need_upper & have_upper == 0) {
			return false;
		} if (need_lower & have_lower == 0) {
			return false;
		} if (need_digit & have_digit == 0) {
			return false;
		} if (need_special & have_special == 0) {
			return false;
		}
		
		return true;
	}
	
}

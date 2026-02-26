package utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    // Check ID format (e.g., starts with a letter followed by numbers)
    public static boolean isValidId(String id) {
        return id != null && id.matches("^[A-Z][0-9]+$");
    }

    // Check email format
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    // Check grade range 0-10
    public static boolean isValidGrade(double grade) {
        return grade >= 0 && grade <= 10;
    }

    // Check password strength (8+ chars, upper, lower, digit, special)
    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8)
            return false;
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                hasUpper = true;
            else if (Character.isLowerCase(c))
                hasLower = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else if ("!@#$%^&*()-_=+[]{}|;:,.<>?".indexOf(c) != -1)
                hasSpecial = true;
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}

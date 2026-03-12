package utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    // Check ID format
    public static boolean isValidId(String id) {
        return id != null && !id.trim().isEmpty() && id.matches("^[A-Z0-9]+$");
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

    // Check not empty
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}

package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.ArrayList;

/**
 * Utility class cho bao mat mat khau
 */
public class PasswordUtils {

    private static final int SALT_LENGTH = 16;
    private static final int MIN_PASSWORD_LENGTH = 8;

    /**
     * Tao salt ngau nhien
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hash mat khau voi SHA-256 + salt
     */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPassword = salt + password;
            byte[] hashedBytes = md.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    /**
     * Xac minh mat khau
     */
    public static boolean verifyPassword(String inputPassword, String salt, String hashedPassword) {
        String inputHash = hashPassword(inputPassword, salt);
        return inputHash.equals(hashedPassword);
    }

    /**
     * Kiem tra do manh mat khau
     * 
     * @return List cac loi, neu rong thi mat khau hop le
     */
    public static List<String> validatePasswordStrength(String password, String username) {
        List<String> errors = new ArrayList<>();

        // Kiem tra do dai
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            errors.add("Mat khau phai co it nhat " + MIN_PASSWORD_LENGTH + " ky tu");
        }

        if (password == null) {
            return errors;
        }

        // Kiem tra chu hoa
        if (!password.matches(".*[A-Z].*")) {
            errors.add("Mat khau phai co it nhat 1 chu hoa (A-Z)");
        }

        // Kiem tra chu thuong
        if (!password.matches(".*[a-z].*")) {
            errors.add("Mat khau phai co it nhat 1 chu thuong (a-z)");
        }

        // Kiem tra so
        if (!password.matches(".*[0-9].*")) {
            errors.add("Mat khau phai co it nhat 1 chu so (0-9)");
        }

        // Kiem tra ky tu dac biet
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            errors.add("Mat khau phai co it nhat 1 ky tu dac biet (!@#$%^&*...)");
        }

        // Kiem tra khong trung username
        if (username != null && password.equalsIgnoreCase(username)) {
            errors.add("Mat khau khong duoc trung voi ten dang nhap");
        }

        // Kiem tra khong chua username
        if (username != null && password.toLowerCase().contains(username.toLowerCase())) {
            errors.add("Mat khau khong duoc chua ten dang nhap");
        }

        return errors;
    }

    /**
     * Kiem tra email hop le
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Regex don gian cho email
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    /**
     * Kiem tra username hop le (chi cho phep chu, so, gach duoi)
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.length() < 3) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9_]+$");
    }
}

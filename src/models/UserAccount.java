package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Lop UserAccount dai dien cho tai khoan nguoi dung trong he thong
 */
public class UserAccount {
    private String userID; // Ma tai khoan
    private String username; // Ten dang nhap
    private String email; // Email
    private String salt; // Salt cho password
    private String hashedPassword; // Mat khau da hash
    private List<String> passwordHistory; // Lich su 3 mat khau gan nhat
    private int loginAttempts; // So lan dang nhap sai
    private boolean isLocked; // Trang thai khoa tai khoan

    public static final int MAX_LOGIN_ATTEMPTS = 5;
    public static final int PASSWORD_HISTORY_SIZE = 3;

    // Constructor mac dinh
    public UserAccount() {
        this.passwordHistory = new ArrayList<>();
        this.loginAttempts = 0;
        this.isLocked = false;
    }

    // Constructor day du tham so
    public UserAccount(String userID, String username, String email, String salt, String hashedPassword) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.passwordHistory = new ArrayList<>();
        this.loginAttempts = 0;
        this.isLocked = false;
    }

    // Getters va Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public List<String> getPasswordHistory() {
        return passwordHistory;
    }

    public void setPasswordHistory(List<String> passwordHistory) {
        this.passwordHistory = passwordHistory;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    /**
     * Tang so lan dang nhap sai va khoa neu vuot qua gioi han
     */
    public void incrementLoginAttempts() {
        this.loginAttempts++;
        if (this.loginAttempts >= MAX_LOGIN_ATTEMPTS) {
            this.isLocked = true;
        }
    }

    /**
     * Reset so lan dang nhap sai khi dang nhap thanh cong
     */
    public void resetLoginAttempts() {
        this.loginAttempts = 0;
    }

    /**
     * Them mat khau vao lich su (giu toi da 3 mat khau)
     */
    public void addToPasswordHistory(String hashedPassword) {
        if (this.passwordHistory == null) {
            this.passwordHistory = new ArrayList<>();
        }
        // Them mat khau moi vao lich su
        this.passwordHistory.add(0, hashedPassword);
        // Giu toi da 3 mat khau
        while (this.passwordHistory.size() > PASSWORD_HISTORY_SIZE) {
            this.passwordHistory.remove(this.passwordHistory.size() - 1);
        }
    }

    /**
     * Kiem tra mat khau co trong lich su khong
     */
    public boolean isPasswordInHistory(String hashedPassword) {
        if (this.passwordHistory == null) {
            return false;
        }
        return this.passwordHistory.contains(hashedPassword);
    }

    /**
     * Chuyen lich su mat khau thanh chuoi de luu file
     */
    public String getPasswordHistoryAsString() {
        if (passwordHistory == null || passwordHistory.isEmpty()) {
            return "";
        }
        return String.join(";", passwordHistory);
    }

    /**
     * Doc lich su mat khau tu chuoi
     */
    public void setPasswordHistoryFromString(String historyStr) {
        this.passwordHistory = new ArrayList<>();
        if (historyStr != null && !historyStr.isEmpty()) {
            String[] parts = historyStr.split(";");
            for (String part : parts) {
                if (!part.isEmpty()) {
                    this.passwordHistory.add(part);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", isLocked=" + isLocked +
                ", loginAttempts=" + loginAttempts +
                '}';
    }
}

package models;

/**
 * Lớp UserAccount đại diện cho tài khoản người dùng trong hệ thống
 */
public class UserAccount {
    private String userID; // Mã tài khoản
    private String username; // Tên đăng nhập
    private String password; // Mật khẩu

    // Constructor mặc định
    public UserAccount() {
    }

    // Constructor đầy đủ tham số
    public UserAccount(String userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    // Getters và Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Phương thức kiểm tra mật khẩu
    public boolean validatePassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}

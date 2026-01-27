package managers;

import models.UserAccount;
import java.io.*;
import java.util.*;

/**
 * Quản lý tài khoản người dùng
 */
public class UserAccountManager {
    private ArrayList<UserAccount> users = new ArrayList<>();
    private final String FILE_NAME = "data/users.txt";

    // Thêm tài khoản
    public void addUser(UserAccount u) {
        users.add(u);
        System.out.println("-> Tạo tài khoản thành công!");
    }

    // Sửa tài khoản theo ID
    public void updateUser(String id, String newUsername, String newPassword) {
        UserAccount u = findUserById(id);
        if (u != null) {
            u.setUsername(newUsername);
            u.setPassword(newPassword);
            System.out.println("-> Cập nhật tài khoản thành công!");
        } else {
            System.out.println("-> Không tìm thấy tài khoản.");
        }
    }

    // Đổi mật khẩu
    public void changePassword(String id, String oldPassword, String newPassword) {
        UserAccount u = findUserById(id);
        if (u != null) {
            if (u.validatePassword(oldPassword)) {
                u.setPassword(newPassword);
                System.out.println("-> Đổi mật khẩu thành công!");
            } else {
                System.out.println("-> Mật khẩu cũ không đúng!");
            }
        } else {
            System.out.println("-> Không tìm thấy tài khoản.");
        }
    }

    // Xóa tài khoản
    public void deleteUser(String id) {
        UserAccount u = findUserById(id);
        if (u != null) {
            users.remove(u);
            System.out.println("-> Đã xóa tài khoản có ID: " + id);
        } else {
            System.out.println("-> Không tìm thấy tài khoản để xóa.");
        }
    }

    // Đăng nhập
    public UserAccount login(String username, String password) {
        for (UserAccount u : users) {
            if (u.getUsername().equals(username) && u.validatePassword(password)) {
                System.out.println("-> Đăng nhập thành công! Xin chào " + username);
                return u;
            }
        }
        System.out.println("-> Sai tên đăng nhập hoặc mật khẩu!");
        return null;
    }

    // Hiển thị danh sách
    public void displayAll() {
        if (users.isEmpty()) {
            System.out.println("-> Danh sách tài khoản trống!");
            return;
        }
        System.out.println("| Mã TK      | Tên đăng nhập        |");
        System.out.println("--------------------------------------");
        for (UserAccount u : users) {
            System.out.printf("| %-10s | %-20s |\n", u.getUserID(), u.getUsername());
        }
    }

    // Tìm tài khoản theo ID
    public UserAccount findUserById(String id) {
        for (UserAccount u : users) {
            if (u.getUserID().equalsIgnoreCase(id)) {
                return u;
            }
        }
        return null;
    }

    // Tìm tài khoản theo username
    public UserAccount findUserByUsername(String username) {
        for (UserAccount u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    // Lưu file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (UserAccount u : users) {
                writer.write(u.getUserID() + "," + u.getUsername() + "," + u.getPassword());
                writer.newLine();
            }
            System.out.println("-> Đã lưu dữ liệu tài khoản vào " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("-> Lỗi khi lưu file tài khoản: " + e.getMessage());
        }
    }

    // Đọc file
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            users.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    UserAccount u = new UserAccount(parts[0], parts[1], parts[2]);
                    users.add(u);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file tài khoản: " + e.getMessage());
        }
    }
}

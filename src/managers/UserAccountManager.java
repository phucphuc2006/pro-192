package managers;

import models.UserAccount;
import models.UserAccount.UserRole;
import models.UserAccount.UserStatus;
import utils.PasswordUtils;
import java.io.*;
import java.util.*;

/**
 * Quan ly tai khoan nguoi dung voi bao mat
 */
public class UserAccountManager {
    private ArrayList<UserAccount> users = new ArrayList<>();
    private final String FILE_NAME = "data/users.txt";

    /**
     * Dang ky tai khoan moi (mac dinh la STUDENT)
     * 
     * @return UserAccount neu thanh cong, null neu that bai
     */
    public UserAccount register(String username, String email, String password) {
        return register(username, email, password, UserRole.STUDENT);
    }

    /**
     * Dang ky tai khoan moi voi role
     * 
     * @return UserAccount neu thanh cong, null neu that bai
     */
    public UserAccount register(String username, String email, String password, UserRole role) {
        // Kiem tra username hop le
        if (!PasswordUtils.isValidUsername(username)) {
            System.out.println("-> Loi: Username chi duoc chua chu, so va gach duoi, toi thieu 3 ky tu");
            return null;
        }

        // Kiem tra username trung
        if (findUserByUsername(username) != null) {
            System.out.println("-> Loi: Username '" + username + "' da ton tai!");
            return null;
        }

        // Kiem tra email hop le
        if (!PasswordUtils.isValidEmail(email)) {
            System.out.println("-> Loi: Email khong hop le!");
            return null;
        }

        // Kiem tra email trung
        if (findUserByEmail(email) != null) {
            System.out.println("-> Loi: Email '" + email + "' da duoc su dung!");
            return null;
        }

        // Kiem tra do manh mat khau
        List<String> passwordErrors = PasswordUtils.validatePasswordStrength(password, username);
        if (!passwordErrors.isEmpty()) {
            System.out.println("-> Loi: Mat khau qua yeu!");
            for (String error : passwordErrors) {
                System.out.println("   - " + error);
            }
            return null;
        }

        // Tao tai khoan moi
        String userID = "U" + String.format("%03d", users.size() + 1);
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);

        UserAccount newUser = new UserAccount(userID, username, email, salt, hashedPassword, role);
        newUser.addToPasswordHistory(hashedPassword);
        users.add(newUser);

        System.out.println("-> Dang ky thanh cong! Ma tai khoan: " + userID + ", Role: " + role.getDisplayName());
        return newUser;
    }

    /**
     * Lay thong bao loi dang ky (cho GUI)
     */
    public String getRegisterError(String username, String email, String password) {
        if (!PasswordUtils.isValidUsername(username)) {
            return "Username chi duoc chua chu, so va gach duoi, toi thieu 3 ky tu";
        }
        if (findUserByUsername(username) != null) {
            return "Username '" + username + "' da ton tai!";
        }
        if (!PasswordUtils.isValidEmail(email)) {
            return "Email khong hop le!";
        }
        if (findUserByEmail(email) != null) {
            return "Email '" + email + "' da duoc su dung!";
        }
        List<String> passwordErrors = PasswordUtils.validatePasswordStrength(password, username);
        if (!passwordErrors.isEmpty()) {
            StringBuilder sb = new StringBuilder("Mat khau qua yeu:\n");
            for (String error : passwordErrors) {
                sb.append("- ").append(error).append("\n");
            }
            return sb.toString();
        }
        return null; // Khong loi
    }

    /**
     * Dang nhap
     * 
     * @return UserAccount neu thanh cong, null neu that bai
     */
    public UserAccount login(String username, String password) {
        UserAccount user = findUserByUsername(username);

        if (user == null) {
            System.out.println("-> Loi: Sai ten dang nhap hoac mat khau!");
            return null;
        }

        // Check status
        if (user.getStatus() == UserStatus.PENDING) {
            System.out.println("-> Loi: Tai khoan dang cho duyet boi quan tri vien!");
            return null;
        } else if (user.getStatus() == UserStatus.BANNED) {
            System.out.println("-> Loi: Tai khoan da bi cam!");
            return null;
        }

        // Kiem tra tai khoan bi khoa (do dang nhap sai)
        if (user.isLocked()) {
            System.out.println("-> Loi: Tai khoan da bi khoa do dang nhap sai qua " +
                    UserAccount.MAX_LOGIN_ATTEMPTS + " lan!");
            System.out.println("   Vui long lien he quan tri vien de mo khoa.");
            return null;
        }

        // Xac minh mat khau
        if (PasswordUtils.verifyPassword(password, user.getSalt(), user.getHashedPassword())) {
            user.resetLoginAttempts();
            System.out.println("-> Dang nhap thanh cong! Xin chao " + username);
            return user;
        } else {
            user.incrementLoginAttempts();
            int remainingAttempts = UserAccount.MAX_LOGIN_ATTEMPTS - user.getLoginAttempts();

            if (user.isLocked()) {
                System.out.println("-> Loi: Tai khoan da bi KHOA do dang nhap sai qua nhieu lan!");
            } else {
                System.out.println("-> Loi: Sai mat khau! Con lai " + remainingAttempts + " lan thu.");
            }
            return null;
        }
    }

    /**
     * Doi mat khau
     * 
     * @return true neu thanh cong
     */
    public boolean changePassword(String userID, String oldPassword, String newPassword) {
        UserAccount user = findUserById(userID);

        if (user == null) {
            System.out.println("-> Loi: Khong tim thay tai khoan!");
            return false;
        }

        // Xac minh mat khau cu
        if (!PasswordUtils.verifyPassword(oldPassword, user.getSalt(), user.getHashedPassword())) {
            System.out.println("-> Loi: Mat khau cu khong dung!");
            return false;
        }

        // Kiem tra mat khau moi khong trung mat khau cu
        String newHashedWithOldSalt = PasswordUtils.hashPassword(newPassword, user.getSalt());
        if (newHashedWithOldSalt.equals(user.getHashedPassword())) {
            System.out.println("-> Loi: Mat khau moi khong duoc trung voi mat khau cu!");
            return false;
        }

        // Kiem tra do manh mat khau moi
        List<String> passwordErrors = PasswordUtils.validatePasswordStrength(newPassword, user.getUsername());
        if (!passwordErrors.isEmpty()) {
            System.out.println("-> Loi: Mat khau moi qua yeu!");
            for (String error : passwordErrors) {
                System.out.println("   - " + error);
            }
            return false;
        }

        // Kiem tra mat khau co trong lich su khong
        // Tao hash moi voi salt moi de kiem tra
        String newSalt = PasswordUtils.generateSalt();
        String newHashed = PasswordUtils.hashPassword(newPassword, newSalt);

        // Kiem tra voi tung mat khau trong lich su (su dung salt cu de so sanh)
        for (String historyHash : user.getPasswordHistory()) {
            // So sanh bang cach hash mat khau moi voi salt cu
            if (PasswordUtils.hashPassword(newPassword, user.getSalt()).equals(historyHash)) {
                System.out.println("-> Loi: Mat khau moi khong duoc trung voi " +
                        UserAccount.PASSWORD_HISTORY_SIZE + " mat khau gan nhat!");
                return false;
            }
        }

        // Cap nhat mat khau
        user.addToPasswordHistory(user.getHashedPassword());
        user.setSalt(newSalt);
        user.setHashedPassword(newHashed);

        System.out.println("-> Doi mat khau thanh cong!");
        return true;
    }

    /**
     * Mo khoa tai khoan (cho admin)
     */
    public void unlockAccount(String userID) {
        UserAccount user = findUserById(userID);
        if (user != null) {
            user.setLocked(false);
            user.resetLoginAttempts();
            System.out.println("-> Da mo khoa tai khoan: " + user.getUsername());
        } else {
            System.out.println("-> Khong tim thay tai khoan!");
        }
    }

    /**
     * Duyet tai khoan (cho admin)
     */
    public void approveUser(String userID) {
        UserAccount user = findUserById(userID);
        if (user != null) {
            user.setStatus(UserStatus.ACTIVE);
            System.out.println("-> Da duyet tai khoan: " + user.getUsername());
        } else {
            System.out.println("-> Khong tim thay tai khoan!");
        }
    }

    /**
     * Xoa tai khoan
     */
    public void deleteUser(String id) {
        UserAccount u = findUserById(id);
        if (u != null) {
            users.remove(u);
            System.out.println("-> Da xoa tai khoan co ID: " + id);
        } else {
            System.out.println("-> Khong tim thay tai khoan de xoa.");
        }
    }

    /**
     * Hien thi danh sach tai khoan
     */
    public void displayAll() {
        if (users.isEmpty()) {
            System.out.println("-> Danh sach tai khoan trong!");
            return;
        }
        System.out.println("| Ma TK      | Username             | Email                     | Locked |");
        System.out.println("--------------------------------------------------------------------------");
        for (UserAccount u : users) {
            System.out.printf("| %-10s | %-20s | %-25s | %-6s |\n",
                    u.getUserID(), u.getUsername(), u.getEmail(), u.isLocked() ? "Yes" : "No");
        }
    }

    /**
     * Tim tai khoan theo ID
     */
    public UserAccount findUserById(String id) {
        for (UserAccount u : users) {
            if (u.getUserID().equalsIgnoreCase(id)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Tim tai khoan theo username
     */
    public UserAccount findUserByUsername(String username) {
        for (UserAccount u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Tim tai khoan theo email
     */
    public UserAccount findUserByEmail(String email) {
        for (UserAccount u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Luu file - Mat khau duoc luu dang hash
     */
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (UserAccount u : users) {
                // Format:
                // userID,username,email,salt,hashedPassword,role,passwordHistory,loginAttempts,isLocked,status
                writer.write(u.getUserID() + "," +
                        u.getUsername() + "," +
                        u.getEmail() + "," +
                        u.getSalt() + "," +
                        u.getHashedPassword() + "," +
                        u.getRole().name() + "," +
                        u.getPasswordHistoryAsString() + "," +
                        u.getLoginAttempts() + "," +
                        u.isLocked() + "," +
                        u.getStatus().name());
                writer.newLine();
            }
            System.out.println("-> Da luu du lieu tai khoan vao " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("-> Loi khi luu file tai khoan: " + e.getMessage());
        }
    }

    /**
     * Doc file
     */
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            users.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 10) {
                    // Newest format with status
                    UserAccount u = new UserAccount();
                    u.setUserID(parts[0]);
                    u.setUsername(parts[1]);
                    u.setEmail(parts[2]);
                    u.setSalt(parts[3]);
                    u.setHashedPassword(parts[4]);
                    try {
                        u.setRole(UserRole.valueOf(parts[5]));
                    } catch (IllegalArgumentException e) {
                        u.setRole(UserRole.STUDENT);
                    }
                    u.setPasswordHistoryFromString(parts[6]);
                    u.setLoginAttempts(Integer.parseInt(parts[7]));
                    u.setLocked(Boolean.parseBoolean(parts[8]));
                    try {
                        u.setStatus(UserStatus.valueOf(parts[9]));
                    } catch (IllegalArgumentException e) {
                        u.setStatus(UserStatus.ACTIVE);
                    }
                    users.add(u);
                } else if (parts.length >= 9) {
                    // Previous format with role
                    UserAccount u = new UserAccount();
                    u.setUserID(parts[0]);
                    u.setUsername(parts[1]);
                    u.setEmail(parts[2]);
                    u.setSalt(parts[3]);
                    u.setHashedPassword(parts[4]);
                    try {
                        u.setRole(UserRole.valueOf(parts[5]));
                    } catch (IllegalArgumentException e) {
                        u.setRole(UserRole.STUDENT);
                    }
                    u.setPasswordHistoryFromString(parts[6]);
                    u.setLoginAttempts(Integer.parseInt(parts[7]));
                    u.setLocked(Boolean.parseBoolean(parts[8]));
                    // Default status based on role
                    if (u.getRole() == UserRole.TEACHER) {
                        u.setStatus(UserStatus.PENDING); // Old teachers might be pending? Or assume active if existing?
                        // Let's assume existing teachers are ACTIVE to avoid locking out current users.
                        // Actually, requirement says "newly created". Existing ones should probably be
                        // ACTIVE.
                        u.setStatus(UserStatus.ACTIVE);
                    } else {
                        u.setStatus(UserStatus.ACTIVE);
                    }
                    users.add(u);
                } else if (parts.length >= 8) {
                    // Old format without role (backward compatible)
                    UserAccount u = new UserAccount();
                    u.setUserID(parts[0]);
                    u.setUsername(parts[1]);
                    u.setEmail(parts[2]);
                    u.setSalt(parts[3]);
                    u.setHashedPassword(parts[4]);
                    u.setRole(UserRole.STUDENT); // Default to student
                    u.setPasswordHistoryFromString(parts[5]);
                    u.setLoginAttempts(Integer.parseInt(parts[6]));
                    u.setLocked(Boolean.parseBoolean(parts[7]));
                    users.add(u);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Loi khi doc file tai khoan: " + e.getMessage());
        }
    }

    /**
     * Kiem tra da co user nao chua
     */
    public boolean hasUsers() {
        return !users.isEmpty();
    }

    /**
     * Lay danh sach tat ca user
     */
    public ArrayList<UserAccount> getAll() {
        return users;
    }

    /**
     * Tao tai khoan Admin mac dinh neu chua co admin nao
     * Username: admin
     * Password: Admin@123
     */
    public void createDefaultAdmin() {
        // Kiem tra da co admin chua
        for (UserAccount u : users) {
            if (u.getRole() == UserRole.ADMIN) {
                return; // Da co admin, khong can tao
            }
        }

        // Tao tai khoan admin mac dinh
        String userID = "ADMIN001";
        String username = "admin";
        String email = "admin@school.edu";
        String password = "Admin@123";

        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);

        UserAccount admin = new UserAccount(userID, username, email, salt, hashedPassword, UserRole.ADMIN);
        admin.addToPasswordHistory(hashedPassword);
        users.add(admin);

        saveToFile();
        System.out.println("--> Da tao tai khoan Admin mac dinh:");
        System.out.println("    Username: admin");
        System.out.println("    Password: Admin@123");
    }

    /**
     * Thay doi role cua user (chi admin moi co quyen)
     */
    public boolean changeUserRole(String userID, UserRole newRole) {
        UserAccount user = findUserById(userID);
        if (user != null) {
            user.setRole(newRole);
            return true;
        }
        return false;
    }
}

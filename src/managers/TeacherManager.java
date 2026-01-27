package managers;

import models.Teacher;
import java.io.*;
import java.util.*;

/**
 * Quản lý giảng viên
 */
public class TeacherManager {
    private ArrayList<Teacher> teachers = new ArrayList<>();
    private final String FILE_NAME = "data/teachers.txt";

    // Thêm giảng viên
    public void addTeacher(Teacher t) {
        teachers.add(t);
        System.out.println("-> Thêm giảng viên thành công!");
    }

    // Sửa giảng viên theo ID
    public void updateTeacher(String id, String newName, String newDepartment, String newEmail, String newPhone) {
        Teacher t = findTeacherById(id);
        if (t != null) {
            t.setFullName(newName);
            t.setDepartment(newDepartment);
            t.setEmail(newEmail);
            t.setPhone(newPhone);
            System.out.println("-> Cập nhật giảng viên thành công!");
        } else {
            System.out.println("-> Không tìm thấy giảng viên.");
        }
    }

    // Xóa giảng viên
    public void deleteTeacher(String id) {
        Teacher t = findTeacherById(id);
        if (t != null) {
            teachers.remove(t);
            System.out.println("-> Đã xóa giảng viên có ID: " + id);
        } else {
            System.out.println("-> Không tìm thấy giảng viên để xóa.");
        }
    }

    // Tìm kiếm theo tên
    public void searchByName(String keyword) {
        System.out.println("--- KẾT QUẢ TÌM KIẾM GIẢNG VIÊN ---");
        boolean found = false;
        for (Teacher t : teachers) {
            if (t.getFullName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found)
            System.out.println("-> Không tìm thấy giảng viên nào: " + keyword);
    }

    // Hiển thị danh sách
    public void displayAll() {
        if (teachers.isEmpty()) {
            System.out.println("-> Danh sách giảng viên trống!");
            return;
        }
        System.out.println(
                "| Mã GV      | Họ tên               | Khoa              | Email                  | SĐT          |");
        System.out.println(
                "------------------------------------------------------------------------------------------------");
        for (Teacher t : teachers) {
            System.out.printf("| %-10s | %-20s | %-17s | %-22s | %-12s |\n",
                    t.getTeacherID(), t.getFullName(), t.getDepartment(), t.getEmail(), t.getPhone());
        }
    }

    // Tìm giảng viên theo ID
    public Teacher findTeacherById(String id) {
        for (Teacher t : teachers) {
            if (t.getTeacherID().equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    // Lưu file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Teacher t : teachers) {
                writer.write(t.getTeacherID() + "," + t.getFullName() + "," +
                        t.getDepartment() + "," + t.getEmail() + "," + t.getPhone());
                writer.newLine();
            }
            System.out.println("-> Đã lưu dữ liệu giảng viên vào " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("-> Lỗi khi lưu file giảng viên: " + e.getMessage());
        }
    }

    // Đọc file
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            teachers.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Teacher t = new Teacher(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    teachers.add(t);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file giảng viên: " + e.getMessage());
        }
    }
}

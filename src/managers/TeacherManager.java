package managers;

import models.Teacher;
import java.io.*;

/**
 * Quản lý giảng viên
 */
public class TeacherManager extends BaseManager<Teacher> {

    public TeacherManager() {
        super("data/teachers.txt");
    }

    // Wrapper cho add
    public void addTeacher(Teacher t) {
        super.add(t);
    }

    // Wrapper cho delete
    public void deleteTeacher(String id) {
        super.delete(id);
    }

    // Wrapper cho find
    public Teacher findTeacherById(String id) {
        return super.findById(id);
    }

    // Sửa giảng viên theo ID
    public void updateTeacher(String id, String newName, String newDept, String newEmail, String newPhone) {
        Teacher t = findById(id);
        if (t != null) {
            t.setName(newName);
            t.setDepartment(newDept);
            t.setEmail(newEmail);
            t.setPhone(newPhone);
            System.out.println("-> Update giao vien thanh cong!");
        } else {
            System.out.println("-> Khong tim thay giao vien.");
        }
    }

    // Hiển thị danh sách
    public void displayAll() {
        if (list.isEmpty()) {
            System.out.println("-> Danh sach trong.");
            return;
        }
        System.out.println(
                "| Mã GV      | Họ tên               | Khoa            | Email                | SĐT          |");
        System.out.println(
                "------------------------------------------------------------------------------------------------");
        for (Teacher t : list) {
            System.out.printf("| %-10s | %-20s | %-15s | %-20s | %-12s |\n",
                    t.getId(), t.getName(), t.getDepartment(), t.getEmail(), t.getPhone());
        }
    }

    // Lưu file
    @Override
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Teacher t : list) {
                writer.write(t.getId() + "," + t.getName() + "," +
                        t.getDepartment() + "," + t.getEmail() + "," + t.getPhone());
                writer.newLine();
            }
            System.out.println("-> Da luu file.");
        } catch (IOException e) {
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }

    // Đọc file
    @Override
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            list.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Teacher t = new Teacher(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    list.add(t);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file giảng viên: " + e.getMessage());
        }
    }
}

package managers;

import models.Department;
import java.io.*;
import java.util.*;

/**
 * Quản lý khoa
 */
public class DepartmentManager {
    private ArrayList<Department> departments = new ArrayList<>();
    private final String FILE_NAME = "data/departments.txt";

    // Thêm khoa
    public void addDepartment(Department d) {
        departments.add(d);
        System.out.println("-> Thêm khoa thành công!");
    }

    // Sửa khoa theo ID
    public void updateDepartment(String id, String newName, int newFacultyCount) {
        Department d = findDepartmentById(id);
        if (d != null) {
            d.setDepartmentName(newName);
            d.setFacultyCount(newFacultyCount);
            System.out.println("-> Cập nhật khoa thành công!");
        } else {
            System.out.println("-> Không tìm thấy khoa.");
        }
    }

    // Xóa khoa
    public void deleteDepartment(String id) {
        Department d = findDepartmentById(id);
        if (d != null) {
            departments.remove(d);
            System.out.println("-> Đã xóa khoa có ID: " + id);
        } else {
            System.out.println("-> Không tìm thấy khoa để xóa.");
        }
    }

    // Tìm kiếm theo tên
    public void searchByName(String keyword) {
        System.out.println("--- KẾT QUẢ TÌM KIẾM KHOA ---");
        boolean found = false;
        for (Department d : departments) {
            if (d.getDepartmentName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(d);
                found = true;
            }
        }
        if (!found)
            System.out.println("-> Không tìm thấy khoa nào: " + keyword);
    }

    // Hiển thị danh sách
    public void displayAll() {
        if (departments.isEmpty()) {
            System.out.println("-> Danh sách khoa trống!");
            return;
        }
        System.out.println("| Mã khoa    | Tên khoa                     | Số GV  |");
        System.out.println("------------------------------------------------------");
        for (Department d : departments) {
            System.out.printf("| %-10s | %-28s | %-6d |\n",
                    d.getDepartmentID(), d.getDepartmentName(), d.getFacultyCount());
        }
    }

    // Tìm khoa theo ID
    public Department findDepartmentById(String id) {
        for (Department d : departments) {
            if (d.getDepartmentID().equalsIgnoreCase(id)) {
                return d;
            }
        }
        return null;
    }

    // Lưu file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Department d : departments) {
                writer.write(d.getDepartmentID() + "," + d.getDepartmentName() + "," + d.getFacultyCount());
                writer.newLine();
            }
            System.out.println("-> Đã lưu dữ liệu khoa vào " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("-> Lỗi khi lưu file khoa: " + e.getMessage());
        }
    }

    // Đọc file
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            departments.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Department d = new Department(parts[0], parts[1], Integer.parseInt(parts[2]));
                    departments.add(d);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file khoa: " + e.getMessage());
        }
    }
}

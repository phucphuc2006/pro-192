package managers;

import models.Department;
import java.io.*;

/**
 * Quản lý khoa
 */
public class DepartmentManager extends BaseManager<Department> {

    public DepartmentManager() {
        super("data/departments.txt");
    }

    // Wrapper cho add
    public void addDepartment(Department d) {
        super.add(d);
    }

    // Wrapper cho delete
    public void deleteDepartment(String id) {
        super.delete(id);
    }

    // Wrapper cho find
    public Department findDepartmentById(String id) {
        return super.findById(id);
    }

    // Sửa khoa theo ID
    public void update(String id, String newName, int newCount) {
        Department d = findById(id);
        if (d != null) {
            d.setName(newName);
            d.setFacultyCount(newCount);
            System.out.println("-> Update khoa thanh cong!");
        } else {
            System.out.println("-> Khong tim thay khoa.");
        }
    }

    public void displayAll() {
        if (list.isEmpty()) {
            System.out.println("-> Danh sach khoa trong.");
            return;
        }
        System.out.println("| Mã Khoa    | Tên Khoa             | SL GV |");
        System.out.println("---------------------------------------------");
        for (Department d : list) {
            System.out.printf("| %-10s | %-20s | %-5d |\n",
                    d.getId(), d.getName(), d.getFacultyCount());
        }
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Department d : list) {
                writer.write(d.getId() + "," + d.getName() + "," + d.getFacultyCount());
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
                if (parts.length == 3) {
                    Department d = new Department(parts[0], parts[1], Integer.parseInt(parts[2]));
                    list.add(d);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file khoa: " + e.getMessage());
        }
    }
}

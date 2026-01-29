package managers;

import models.Semester;
import java.io.*;

/**
 * Quản lý học kỳ
 */
public class SemesterManager extends BaseManager<Semester> {

    public SemesterManager() {
        super("data/semesters.txt");
    }

    // Wrapper cho add
    public void addSemester(Semester s) {
        super.add(s);
    }

    // Wrapper cho delete
    public void deleteSemester(String id) {
        super.delete(id);
    }

    // Wrapper cho find
    public Semester findSemesterById(String id) {
        return super.findById(id);
    }

    // Sửa học kỳ theo ID
    public void update(String id, String newName, String newStart, String newEnd) {
        Semester s = findById(id);
        if (s != null) {
            s.setName(newName);
            s.setStartDate(newStart);
            s.setEndDate(newEnd);
            System.out.println("-> Update hoc ky thanh cong!");
        } else {
            System.out.println("-> Khong tim thay hoc ky.");
        }
    }

    public void displayAll() {
        if (list.isEmpty()) {
            System.out.println("-> Danh sach hoc ky trong.");
            return;
        }
        System.out.println("| Mã HK      | Tên Học Kỳ           | Ngày BĐ    | Ngày KT    |");
        System.out.println("-------------------------------------------------------------");
        for (Semester s : list) {
            System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n",
                    s.getId(), s.getName(), s.getStartDate(), s.getEndDate());
        }
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Semester s : list) {
                writer.write(s.getId() + "," + s.getName() + "," +
                        s.getStartDate() + "," + s.getEndDate());
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
                if (parts.length == 4) {
                    Semester s = new Semester(parts[0], parts[1], parts[2], parts[3]);
                    list.add(s);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file học kỳ: " + e.getMessage());
        }
    }
}

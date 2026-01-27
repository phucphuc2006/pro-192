package managers;

import models.Semester;
import java.io.*;
import java.util.*;

/**
 * Quản lý học kỳ
 */
public class SemesterManager {
    private ArrayList<Semester> semesters = new ArrayList<>();
    private final String FILE_NAME = "data/semesters.txt";

    // Thêm học kỳ
    public void addSemester(Semester s) {
        semesters.add(s);
        System.out.println("-> Thêm học kỳ thành công!");
    }

    // Sửa học kỳ theo ID
    public void updateSemester(String id, String newName, String newStartDate, String newEndDate) {
        Semester s = findSemesterById(id);
        if (s != null) {
            s.setSemesterName(newName);
            s.setStartDate(newStartDate);
            s.setEndDate(newEndDate);
            System.out.println("-> Cập nhật học kỳ thành công!");
        } else {
            System.out.println("-> Không tìm thấy học kỳ.");
        }
    }

    // Xóa học kỳ
    public void deleteSemester(String id) {
        Semester s = findSemesterById(id);
        if (s != null) {
            semesters.remove(s);
            System.out.println("-> Đã xóa học kỳ có ID: " + id);
        } else {
            System.out.println("-> Không tìm thấy học kỳ để xóa.");
        }
    }

    // Hiển thị danh sách
    public void displayAll() {
        if (semesters.isEmpty()) {
            System.out.println("-> Danh sách học kỳ trống!");
            return;
        }
        System.out.println("| Mã HK      | Tên học kỳ           | Ngày bắt đầu | Ngày kết thúc |");
        System.out.println("--------------------------------------------------------------------");
        for (Semester s : semesters) {
            System.out.printf("| %-10s | %-20s | %-12s | %-13s |\n",
                    s.getSemesterID(), s.getSemesterName(), s.getStartDate(), s.getEndDate());
        }
    }

    // Tìm học kỳ theo ID
    public Semester findSemesterById(String id) {
        for (Semester s : semesters) {
            if (s.getSemesterID().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // Lưu file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Semester s : semesters) {
                writer.write(s.getSemesterID() + "," + s.getSemesterName() + "," +
                        s.getStartDate() + "," + s.getEndDate());
                writer.newLine();
            }
            System.out.println("-> Đã lưu dữ liệu học kỳ vào " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("-> Lỗi khi lưu file học kỳ: " + e.getMessage());
        }
    }

    // Đọc file
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            semesters.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Semester s = new Semester(parts[0], parts[1], parts[2], parts[3]);
                    semesters.add(s);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file học kỳ: " + e.getMessage());
        }
    }
}

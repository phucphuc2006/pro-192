package managers;

import models.Grade;
import java.io.*;
import java.util.*;

/**
 * Quản lý điểm số
 */
public class GradeManager {
    private ArrayList<Grade> grades = new ArrayList<>();
    private final String FILE_NAME = "data/grades.txt";

    // Thêm điểm
    public void addGrade(Grade g) {
        grades.add(g);
        System.out.println("-> Thêm điểm thành công!");
    }

    // Sửa điểm theo ID
    public void updateGrade(String id, double newMidterm, double newFinal) {
        Grade g = findGradeById(id);
        if (g != null) {
            g.setMidterm(newMidterm);
            g.setFinalExam(newFinal);
            g.setTotal(g.calculateTotal());
            System.out.println("-> Cập nhật điểm thành công!");
        } else {
            System.out.println("-> Không tìm thấy điểm.");
        }
    }

    // Xóa điểm
    public void deleteGrade(String id) {
        Grade g = findGradeById(id);
        if (g != null) {
            grades.remove(g);
            System.out.println("-> Đã xóa điểm có ID: " + id);
        } else {
            System.out.println("-> Không tìm thấy điểm để xóa.");
        }
    }

    // Tìm kiếm theo mã sinh viên
    public void searchByStudentId(String studentId) {
        System.out.println("--- BẢNG ĐIỂM CỦA SINH VIÊN " + studentId + " ---");
        boolean found = false;
        for (Grade g : grades) {
            if (g.getStudentID().equalsIgnoreCase(studentId)) {
                System.out.printf("Môn: %s | Giữa kỳ: %.2f | Cuối kỳ: %.2f | Tổng: %.2f\n",
                        g.getCourseID(), g.getMidterm(), g.getFinalExam(), g.getTotal());
                found = true;
            }
        }
        if (!found)
            System.out.println("-> Chưa có điểm nào.");
    }

    // Hiển thị danh sách
    public void displayAll() {
        if (grades.isEmpty()) {
            System.out.println("-> Danh sách điểm trống!");
            return;
        }
        System.out.println("| Mã điểm    | Mã SV      | Mã MH      | Giữa kỳ | Cuối kỳ | Tổng   |");
        System.out.println("---------------------------------------------------------------------");
        for (Grade g : grades) {
            System.out.printf("| %-10s | %-10s | %-10s | %-7.2f | %-7.2f | %-6.2f |\n",
                    g.getGradeID(), g.getStudentID(), g.getCourseID(),
                    g.getMidterm(), g.getFinalExam(), g.getTotal());
        }
    }

    // Tìm điểm theo ID
    public Grade findGradeById(String id) {
        for (Grade g : grades) {
            if (g.getGradeID().equalsIgnoreCase(id)) {
                return g;
            }
        }
        return null;
    }

    // Lưu file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Grade g : grades) {
                writer.write(g.getGradeID() + "," + g.getStudentID() + "," +
                        g.getCourseID() + "," + g.getMidterm() + "," +
                        g.getFinalExam() + "," + g.getTotal());
                writer.newLine();
            }
            System.out.println("-> Đã lưu dữ liệu điểm vào " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("-> Lỗi khi lưu file điểm: " + e.getMessage());
        }
    }

    // Đọc file
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            grades.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    Grade g = new Grade(parts[0], parts[1], parts[2],
                            Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4]),
                            Double.parseDouble(parts[5]));
                    grades.add(g);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file điểm: " + e.getMessage());
        }
    }
}

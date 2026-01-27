package managers;

import models.Enrollment;
import java.io.*;
import java.util.*;

/**
 * Quản lý đăng ký môn học
 */
public class EnrollmentManager {
    private ArrayList<Enrollment> enrollments = new ArrayList<>();
    private final String FILE_NAME = "data/enrollments.txt";

    // Thêm đăng ký
    public void addEnrollment(Enrollment e) {
        enrollments.add(e);
        System.out.println("-> Đăng ký môn học thành công!");
    }

    // Sửa đăng ký theo ID
    public void updateEnrollment(String id, String newStudentID, String newCourseID, String newSemester) {
        Enrollment e = findEnrollmentById(id);
        if (e != null) {
            e.setStudentID(newStudentID);
            e.setCourseID(newCourseID);
            e.setSemester(newSemester);
            System.out.println("-> Cập nhật đăng ký thành công!");
        } else {
            System.out.println("-> Không tìm thấy đăng ký.");
        }
    }

    // Xóa đăng ký
    public void deleteEnrollment(String id) {
        Enrollment e = findEnrollmentById(id);
        if (e != null) {
            enrollments.remove(e);
            System.out.println("-> Đã xóa đăng ký có ID: " + id);
        } else {
            System.out.println("-> Không tìm thấy đăng ký để xóa.");
        }
    }

    // Tìm kiếm theo mã sinh viên
    public void searchByStudentId(String studentId) {
        System.out.println("--- DANH SÁCH MÔN ĐÃ ĐĂNG KÝ CỦA SINH VIÊN " + studentId + " ---");
        boolean found = false;
        for (Enrollment e : enrollments) {
            if (e.getStudentID().equalsIgnoreCase(studentId)) {
                System.out.println(e);
                found = true;
            }
        }
        if (!found)
            System.out.println("-> Sinh viên chưa đăng ký môn nào.");
    }

    // Hiển thị danh sách
    public void displayAll() {
        if (enrollments.isEmpty()) {
            System.out.println("-> Danh sách đăng ký trống!");
            return;
        }
        System.out.println("| Mã ĐK      | Mã SV      | Mã MH      | Học kỳ     |");
        System.out.println("-----------------------------------------------------");
        for (Enrollment e : enrollments) {
            System.out.printf("| %-10s | %-10s | %-10s | %-10s |\n",
                    e.getEnrollmentID(), e.getStudentID(), e.getCourseID(), e.getSemester());
        }
    }

    // Tìm đăng ký theo ID
    public Enrollment findEnrollmentById(String id) {
        for (Enrollment e : enrollments) {
            if (e.getEnrollmentID().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    // Lưu file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Enrollment e : enrollments) {
                writer.write(e.getEnrollmentID() + "," + e.getStudentID() + "," +
                        e.getCourseID() + "," + e.getSemester());
                writer.newLine();
            }
            System.out.println("-> Đã lưu dữ liệu đăng ký vào " + FILE_NAME);
        } catch (IOException ex) {
            System.out.println("-> Lỗi khi lưu file đăng ký: " + ex.getMessage());
        }
    }

    // Đọc file
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            enrollments.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Enrollment e = new Enrollment(parts[0], parts[1], parts[2], parts[3]);
                    enrollments.add(e);
                }
            }
        } catch (IOException ex) {
            System.out.println("-> Lỗi khi đọc file đăng ký: " + ex.getMessage());
        }
    }
}

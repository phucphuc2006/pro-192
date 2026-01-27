package managers;

import models.Course;
import java.io.*;
import java.util.*;

/**
 * Quản lý môn học
 */
public class CourseManager {
    private ArrayList<Course> courses = new ArrayList<>();
    private final String FILE_NAME = "data/courses.txt";

    // Thêm môn học
    public void addCourse(Course c) {
        courses.add(c);
        System.out.println("-> Thêm môn học thành công!");
    }

    // Sửa môn học theo ID
    public void updateCourse(String id, String newName, int newCredits, String newSemester, String newTeacherID) {
        Course c = findCourseById(id);
        if (c != null) {
            c.setCourseName(newName);
            c.setCredits(newCredits);
            c.setSemester(newSemester);
            c.setTeacherID(newTeacherID);
            System.out.println("-> Cập nhật môn học thành công!");
        } else {
            System.out.println("-> Không tìm thấy môn học.");
        }
    }

    // Xóa môn học
    public void deleteCourse(String id) {
        Course c = findCourseById(id);
        if (c != null) {
            courses.remove(c);
            System.out.println("-> Đã xóa môn học có ID: " + id);
        } else {
            System.out.println("-> Không tìm thấy môn học để xóa.");
        }
    }

    // Tìm kiếm theo tên
    public void searchByName(String keyword) {
        System.out.println("--- KẾT QUẢ TÌM KIẾM MÔN HỌC ---");
        boolean found = false;
        for (Course c : courses) {
            if (c.getCourseName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found)
            System.out.println("-> Không tìm thấy môn học nào: " + keyword);
    }

    // Hiển thị danh sách
    public void displayAll() {
        if (courses.isEmpty()) {
            System.out.println("-> Danh sách môn học trống!");
            return;
        }
        System.out.println("| Mã MH      | Tên môn học          | Tín chỉ | Học kỳ  | Mã GV     |");
        System.out.println("--------------------------------------------------------------------");
        for (Course c : courses) {
            System.out.printf("| %-10s | %-20s | %-7d | %-7s | %-9s |\n",
                    c.getCourseID(), c.getCourseName(), c.getCredits(), c.getSemester(), c.getTeacherID());
        }
    }

    // Tìm môn học theo ID
    public Course findCourseById(String id) {
        for (Course c : courses) {
            if (c.getCourseID().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    // Lưu file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Course c : courses) {
                writer.write(c.getCourseID() + "," + c.getCourseName() + "," +
                        c.getCredits() + "," + c.getSemester() + "," + c.getTeacherID());
                writer.newLine();
            }
            System.out.println("-> Đã lưu dữ liệu môn học vào " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("-> Lỗi khi lưu file môn học: " + e.getMessage());
        }
    }

    // Đọc file
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            courses.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Course c = new Course(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], parts[4]);
                    courses.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file môn học: " + e.getMessage());
        }
    }
}

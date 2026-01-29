package managers;

import models.Course;
import java.io.*;

/**
 * Quản lý môn học
 */
public class CourseManager extends BaseManager<Course> {

    public CourseManager() {
        super("data/courses.txt");
    }

    // Wrapper cho add
    public void addCourse(Course c) {
        super.add(c);
    }

    // Wrapper cho delete
    public void deleteCourse(String id) {
        super.delete(id);
    }

    // Wrapper cho find
    public Course findCourseById(String id) {
        return super.findById(id);
    }

    // Sửa môn học theo ID
    public void update(String id, String newName, int newCredits, String newSemester, String newTeacherID) {
        Course c = findById(id);
        if (c != null) {
            c.setName(newName);
            c.setCredits(newCredits);
            c.setSemester(newSemester);
            c.setTeacherID(newTeacherID);
            System.out.println("-> Update mon hoc thanh cong!");
        } else {
            System.out.println("-> Khong tim thay mon hoc.");
        }
    }

    public void displayAll() {
        if (list.isEmpty()) {
            System.out.println("-> Danh sach mon hoc trong.");
            return;
        }
        System.out.println(
                "| Mã MH      | Tên Môn Học          | Tín Chỉ | Học Kỳ   | Mã GV      |");
        System.out.println(
                "-----------------------------------------------------------------------");
        for (Course c : list) {
            System.out.printf("| %-10s | %-20s | %-7d | %-8s | %-10s |\n",
                    c.getId(), c.getName(), c.getCredits(), c.getSemester(), c.getTeacherID());
        }
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Course c : list) {
                writer.write(c.getId() + "," + c.getName() + "," +
                        c.getCredits() + "," + c.getSemester() + "," + c.getTeacherID());
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
                    Course c = new Course(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], parts[4]);
                    list.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file môn học: " + e.getMessage());
        }
    }
}

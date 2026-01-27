package managers;

import models.ClassRoom;
import java.io.*;
import java.util.*;

/**
 * Quản lý lớp học
 */
public class ClassRoomManager {
    private ArrayList<ClassRoom> classRooms = new ArrayList<>();
    private final String FILE_NAME = "data/classrooms.txt";

    // Thêm lớp học
    public void addClassRoom(ClassRoom c) {
        classRooms.add(c);
        System.out.println("-> Thêm lớp học thành công!");
    }

    // Sửa lớp học theo ID
    public void updateClassRoom(String id, String newName, String newTeacherID, String newCourseID) {
        ClassRoom c = findClassRoomById(id);
        if (c != null) {
            c.setClassName(newName);
            c.setTeacherID(newTeacherID);
            c.setCourseID(newCourseID);
            System.out.println("-> Cập nhật lớp học thành công!");
        } else {
            System.out.println("-> Không tìm thấy lớp học.");
        }
    }

    // Xóa lớp học
    public void deleteClassRoom(String id) {
        ClassRoom c = findClassRoomById(id);
        if (c != null) {
            classRooms.remove(c);
            System.out.println("-> Đã xóa lớp học có ID: " + id);
        } else {
            System.out.println("-> Không tìm thấy lớp học để xóa.");
        }
    }

    // Tìm kiếm theo tên
    public void searchByName(String keyword) {
        System.out.println("--- KẾT QUẢ TÌM KIẾM LỚP HỌC ---");
        boolean found = false;
        for (ClassRoom c : classRooms) {
            if (c.getClassName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found)
            System.out.println("-> Không tìm thấy lớp học nào: " + keyword);
    }

    // Hiển thị danh sách
    public void displayAll() {
        if (classRooms.isEmpty()) {
            System.out.println("-> Danh sách lớp học trống!");
            return;
        }
        System.out.println("| Mã lớp     | Tên lớp              | Mã GV      | Mã MH      |");
        System.out.println("--------------------------------------------------------------");
        for (ClassRoom c : classRooms) {
            System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n",
                    c.getClassID(), c.getClassName(), c.getTeacherID(), c.getCourseID());
        }
    }

    // Tìm lớp học theo ID
    public ClassRoom findClassRoomById(String id) {
        for (ClassRoom c : classRooms) {
            if (c.getClassID().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    // Lưu file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (ClassRoom c : classRooms) {
                writer.write(c.getClassID() + "," + c.getClassName() + "," +
                        c.getTeacherID() + "," + c.getCourseID());
                writer.newLine();
            }
            System.out.println("-> Đã lưu dữ liệu lớp học vào " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("-> Lỗi khi lưu file lớp học: " + e.getMessage());
        }
    }

    // Đọc file
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            classRooms.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    ClassRoom c = new ClassRoom(parts[0], parts[1], parts[2], parts[3]);
                    classRooms.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file lớp học: " + e.getMessage());
        }
    }
}

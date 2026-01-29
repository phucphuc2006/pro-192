package managers;

import models.ClassRoom;
import java.io.*;

/**
 * Quản lý lớp học
 */
public class ClassRoomManager extends BaseManager<ClassRoom> {

    public ClassRoomManager() {
        super("data/classrooms.txt");
    }

    // Wrapper cho add
    public void addClassRoom(ClassRoom c) {
        super.add(c);
    }

    // Wrapper cho delete
    public void deleteClassRoom(String id) {
        super.delete(id);
    }

    // Wrapper cho find
    public ClassRoom findClassRoomById(String id) {
        return super.findById(id);
    }

    // Sửa lớp học theo ID
    public void update(String id, String newName, String newTeacherID, String newCourseID) {
        ClassRoom c = findById(id);
        if (c != null) {
            c.setName(newName);
            c.setTeacherID(newTeacherID);
            c.setCourseID(newCourseID);
            System.out.println("-> Update lop hoc phan thanh cong!");
        } else {
            System.out.println("-> Khong tim thay lop hoc phan.");
        }
    }

    public void displayAll() {
        if (list.isEmpty()) {
            System.out.println("-> Danh sach lop hoc phan trong.");
            return;
        }
        System.out.println("| Mã Lớp     | Tên Lớp              | Mã GV      | Mã MH      |");
        System.out.println("-------------------------------------------------------------");
        for (ClassRoom c : list) {
            System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n",
                    c.getId(), c.getName(), c.getTeacherID(), c.getCourseID());
        }
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (ClassRoom c : list) {
                writer.write(c.getId() + "," + c.getName() + "," +
                        c.getTeacherID() + "," + c.getCourseID());
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
                    ClassRoom c = new ClassRoom(parts[0], parts[1], parts[2], parts[3]);
                    list.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file lớp học: " + e.getMessage());
        }
    }
}

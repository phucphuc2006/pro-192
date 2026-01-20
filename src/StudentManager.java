import java.io.*;
import java.util.*;

public class StudentManager {
    private ArrayList<Student> students = new ArrayList<>();
    private final String FILE_NAME = "student.txt";

    // 1. Thêm sinh viên
    public void addStudent(Student s) {
        students.add(s);
        System.out.println("-> Thêm sinh viên thành công!");
    }

    // 2. Sửa sinh viên theo ID
    public void updateStudent(String id, String newName, int newAge, double newGpa) {
        Student s = findStudentById(id);
        if (s != null) {
            s.setName(newName);
            s.setAge(newAge);
            s.setGpa(newGpa);
            System.out.println("-> Cập nhật thành công!");
        } else {
            System.out.println("-> Không tìm thấy ID sinh viên.");
        }
    }

    // 3. Xóa sinh viên
    public void deleteStudent(String id) {
        Student s = findStudentById(id);
        if (s != null) {
            students.remove(s);
            System.out.println("-> Đã xóa sinh viên có ID: " + id);
        } else {
            System.out.println("-> Không tìm thấy sinh viên để xóa.");
        }
    }

    // 4. Tìm kiếm theo tên (gần đúng)
    public void searchByName(String keyword) {
        System.out.println("--- KẾT QUẢ TÌM KIẾM ---");
        boolean found = false;
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found) System.out.println("-> Không tìm thấy sinh viên nào tên: " + keyword);
    }

    // 5. Sắp xếp theo điểm GPA tăng dần
    public void sortByGpa() {
        // Sử dụng Comparator
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                // Nếu muốn giảm dần thì đổi chỗ s2 và s1
                return Double.compare(s1.getGpa(), s2.getGpa());
            }
        });
        System.out.println("-> Đã sắp xếp danh sách theo điểm!");
    }

    // 6. Hiển thị danh sách
    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("-> Danh sách trống!");
            return;
        }
        System.out.println("| ID         | Tên                  | Tuổi  | GPA   |");
        System.out.println("-----------------------------------------------------");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // Helper: Tìm sinh viên theo ID
    public Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // 7. Lưu file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.write(s.toFileString());
                writer.newLine();
            }
            System.out.println("-> Đã lưu dữ liệu vào " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("-> Lỗi khi lưu file: " + e.getMessage());
        }
    }

    // 8. Đọc file
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return; // Nếu file chưa tồn tại thì bỏ qua

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            students.clear(); // Xóa danh sách cũ trước khi load
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Student s = new Student(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]));
                    students.add(s);
                }
            }
            System.out.println("-> Đã tải dữ liệu từ file.");
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file: " + e.getMessage());
        }
    }
}

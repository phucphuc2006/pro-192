import java.io.*;
import java.util.*;

public class StudentManager {
    private ArrayList<Student> students;
    private final String FILE_NAME = "student.txt";

    public StudentManager() {
        students = new ArrayList<>();
        loadDataFromFile(); // Tự động tải dữ liệu cũ khi khởi động
    }

    // 1. Thêm sinh viên
    public void addStudent(Student s) {
        students.add(s);
        System.out.println("-> Thêm thành công!");
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
            System.out.println("-> Không tìm thấy ID: " + id);
        }
    }

    // 3. Xóa sinh viên
    public void deleteStudent(String id) {
        Student s = findStudentById(id);
        if (s != null) {
            students.remove(s);
            System.out.println("-> Đã xóa sinh viên có ID: " + id);
        } else {
            System.out.println("-> Không tìm thấy ID để xóa.");
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
        if (!found) System.out.println("-> Không tìm thấy sinh viên nào.");
    }

    // 5. Sắp xếp theo điểm GPA giảm dần
    public void sortByGpa() {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                // Nếu s2 > s1 trả về dương (để s2 lên trước)
                return Double.compare(s2.getGpa(), s1.getGpa());
            }
        });
        System.out.println("-> Đã sắp xếp danh sách theo điểm giảm dần.");
    }

    // 6. Hiển thị danh sách
    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("-> Danh sách trống!");
            return;
        }
        System.out.println("-------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-5s | %-5s |\n", "ID", "TÊN", "TUỔI", "ĐIỂM");
        System.out.println("-------------------------------------------------------");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("-------------------------------------------------------");
    }

    // Hàm phụ trợ: Tìm sinh viên theo ID
    public Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // --- PHẦN GHI/ĐỌC FILE ---
    
    public void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.write(s.toFileString());
                writer.newLine();
            }
            System.out.println("-> Đã lưu dữ liệu vào " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    private void loadDataFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    students.add(new Student(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3])));
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
}
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();

        // Bao Scanner trong try-with-resources để tránh cảnh báo rò rỉ
        try (Scanner scanner = new Scanner(System.in)) {
            // Tự động tải dữ liệu cũ khi mở chương trình
            manager.loadFromFile();

            while (true) {
            System.out.println("\n========== QUẢN LÝ SINH VIÊN ==========");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Sửa thông tin sinh viên");
            System.out.println("3. Xóa sinh viên");
            System.out.println("4. Tìm kiếm theo tên");
            System.out.println("5. Sắp xếp theo điểm GPA");
            System.out.println("6. Hiển thị danh sách");
            System.out.println("7. Lưu và Thoát");
            System.out.print("Chọn chức năng: ");
            


            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }

                switch (choice) {
                    case 1:
                        System.out.print("Nhập ID: ");
                        String id = scanner.nextLine();
                        // Kiểm tra ID trùng
                        if (manager.findStudentById(id) != null) {
                            System.out.println("ID này đã tồn tại!");
                            break;
                        }
                        System.out.print("Nhập Tên: ");
                        String name = scanner.nextLine();
                        System.out.print("Nhập Tuổi: ");
                        int age = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nhập GPA: ");
                        double gpa = Double.parseDouble(scanner.nextLine());
                        manager.addStudent(new Student(id, name, age, gpa));
                        break;

                    case 2:
                        System.out.print("Nhập ID sinh viên cần sửa: ");
                        String editId = scanner.nextLine();
                        System.out.print("Nhập Tên mới: ");
                        String newName = scanner.nextLine();
                        System.out.print("Nhập Tuổi mới: ");
                        int newAge = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nhập GPA mới: ");
                        double newGpa = Double.parseDouble(scanner.nextLine());
                        manager.updateStudent(editId, newName, newAge, newGpa);
                        break;

                    case 3:
                        System.out.print("Nhập ID sinh viên cần xóa: ");
                        String delId = scanner.nextLine();
                        manager.deleteStudent(delId);
                        break;

                    case 4:
                        System.out.print("Nhập tên cần tìm: ");
                        String keyword = scanner.nextLine();
                        manager.searchByName(keyword);
                        break;

                    case 5:
                        manager.sortByGpa();
                        manager.displayAll();
                        break;

                    case 6:
                        manager.displayAll();
                        break;

                    case 7:
                        manager.saveToFile();
                        System.out.println("Đang thoát chương trình...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            }
        }
    }
}

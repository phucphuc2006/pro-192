import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();

        // Bao Scanner trong try-with-resources để tránh cảnh báo rò rỉ
        try (Scanner scanner = new Scanner(System.in)) {
            // Tự động tải dữ liệu cũ khi mở chương trình
            manager.loadFromFile();

            while (true) {
                System.out.println("\n========= QUAN LY SINH VIEN =========");
                System.out.println("1. Them sinh vien");
                System.out.println("2. Sua thong tin sinh vien");
                System.out.println("3. Xoa sinh vien");
                System.out.println("4. Tim kiem theo ten");
                System.out.println("5. Sap xep theo diem GPA");
                System.out.println("6. Hien thi danh sach");
                System.out.println("7. Luu va Thoat");
                System.out.print("Chon chuc nang: ");
            


            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so: ");
                continue;
            }

                switch (choice) {
                    case 1:
                        System.out.print("Nhapap ID: ");
                        String id = scanner.nextLine();
                        // Kiểm tra ID trùng
                        if (manager.findStudentById(id) != null) {
                            System.out.println("ID này đa ton tai!");
                            break;
                        }
                        System.out.print("Nhap Ten: ");
                    String name = scanner.nextLine();
                    
                    int age = 0;
                    while (true) {
                        System.out.print("Nhap Tuoi: ");
                        try {
                            age = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("-> Tuoi phai la so!");
                        }
                    }

                    double gpa = 0;
                    while (true) {
                        System.out.print("Nhap GPA: ");
                        try {
                            gpa = Double.parseDouble(scanner.nextLine());
                            if (gpa < 0 || gpa > 10) {
                                System.out.println("-> GPA phai tu 0 den 10");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("-> GPA phai la so!");
                        }
                    }
                    
                    manager.addStudent(new Student(id, name, age, gpa));
                    break;

                case 2:
                    System.out.print("Nhap ID sinh viên can sua: ");
                    String editId = scanner.nextLine();
                    // Kiểm tra tồn tại trước khi nhập dữ liệu mới để đỡ mất công
                    if (manager.findStudentById(editId) == null) {
                        System.out.println("-> Khong tim thay sinh vien!");
                        break;
                    }
                    System.out.print("Nhap Ten moi: ");
                    String newName = scanner.nextLine();
                    System.out.print("Nhap Tuoi moi: ");
                    int newAge = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nhap GPA moi: ");
                    double newGpa = Double.parseDouble(scanner.nextLine());
                    manager.updateStudent(editId, newName, newAge, newGpa);
                    break;

                case 3:
                    System.out.print("Nhap ID sinh vien can xoa: ");
                    String delId = scanner.nextLine();
                    manager.deleteStudent(delId);
                    break;

                case 4:
                    System.out.print("Nhap ten can tim: ");
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
                    System.out.println("Dang thoat chuong trinh...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
}}
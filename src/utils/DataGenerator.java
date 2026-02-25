package utils;

import java.util.Scanner;

public class DataGenerator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU NHẬP DỮ LIỆU =====");
            System.out.println("1. Nhập thông tin Student");
            System.out.println("2. Nhập thông tin Teacher");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 0) {
                System.out.println("Kết thúc chương trình.");
                break;
            }

            // Nhập các thông tin chung của Person
            System.out.print("Nhập ID: ");
            String id = sc.nextLine();
            System.out.print("Nhập Họ tên: ");
            String fullName = sc.nextLine();
            System.out.print("Nhập Email: ");
            String email = sc.nextLine();
            System.out.print("Nhập Số điện thoại: ");
            String phone = sc.nextLine();

            if (choice == 1) {
                // Nhập thêm thông tin riêng của Student
                System.out.print("Nhập Ngày sinh (yyyy-mm-dd): ");
                String dob = sc.nextLine();
                System.out.print("Nhập Giới tính: ");
                String gender = sc.nextLine();
                System.out.print("Nhập Mã lớp: ");
                String classID = sc.nextLine();

                System.out.println("\n--- Dữ liệu Student vừa nhập ---");
                System.out.println("ID: " + id);
                System.out.println("Họ tên: " + fullName);
                System.out.println("Email: " + email);
                System.out.println("SĐT: " + phone);
                System.out.println("Ngày sinh: " + dob);
                System.out.println("Giới tính: " + gender);
                System.out.println("Lớp: " + classID);

            } else if (choice == 2) {
                // Nhập thêm thông tin riêng của Teacher
                System.out.print("Nhập Khoa/Phòng ban: ");
                String department = sc.nextLine();

                System.out.println("\n--- Dữ liệu Teacher vừa nhập ---");
                System.out.println("ID: " + id);
                System.out.println("Họ tên: " + fullName);
                System.out.println("Email: " + email);
                System.out.println("SĐT: " + phone);
                System.out.println("Khoa: " + department);
            } else {
                System.out.println("Lựa chọn không hợp lệ!");
            }
        }
        sc.close();
    }
}

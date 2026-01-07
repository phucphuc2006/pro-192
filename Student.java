// File: Student.java
public class Student {
    // 1. Thuộc tính (Encapsulation - Đóng gói: dùng private để bảo vệ dữ liệu)
    private String id;
    private String name;
    private int age;
    private double gpa;

    // 2. Constructor (Hàm khởi tạo: chạy ngay khi tạo đối tượng mới)
    public Student(String id, String name, int age, double gpa) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }

    // 3. Getters và Setters (Để bên ngoài có thể xem hoặc sửa dữ liệu an toàn)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    // 4. toString (Để in thông tin sinh viên ra màn hình cho đẹp)
    @Override
    public String toString() {
        return "ID: " + id + " | Tên: " + name + " | Tuổi: " + age + " | Điểm TB: " + gpa;
    }
}
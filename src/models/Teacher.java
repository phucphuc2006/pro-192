package models;

/**
 * Lớp Teacher đại diện cho một giảng viên trong hệ thống
 */
public class Teacher extends Person {
    private String department; // Khoa

    // Constructor mặc định
    public Teacher() {
    }

    // Constructor đầy đủ tham số
    public Teacher(String teacherID, String fullName, String department,
            String email, String phone) {
        super(teacherID, fullName, email, phone);
        this.department = department;
    }

    // Getters và Setters riêng
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                super.toString() +
                ", department='" + department + '\'' +
                '}';
    }
}

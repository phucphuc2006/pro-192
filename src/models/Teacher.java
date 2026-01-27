package models;

/**
 * Lớp Teacher đại diện cho một giảng viên trong hệ thống
 */
public class Teacher {
    private String teacherID; // Mã giảng viên
    private String fullName; // Họ và tên
    private String department; // Khoa
    private String email; // Địa chỉ email
    private String phone; // Số điện thoại

    // Constructor mặc định
    public Teacher() {
    }

    // Constructor đầy đủ tham số
    public Teacher(String teacherID, String fullName, String department,
            String email, String phone) {
        this.teacherID = teacherID;
        this.fullName = fullName;
        this.department = department;
        this.email = email;
        this.phone = phone;
    }

    // Getters và Setters
    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherID='" + teacherID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

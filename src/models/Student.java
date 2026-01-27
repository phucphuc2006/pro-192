package models;

/**
 * Lớp Student đại diện cho một sinh viên trong hệ thống
 */
public class Student {
    private String studentID; // Mã sinh viên
    private String fullName; // Họ và tên
    private String dob; // Ngày sinh
    private String gender; // Giới tính
    private String email; // Địa chỉ email
    private String phone; // Số điện thoại
    private String classID; // Mã lớp học

    // Constructor mặc định
    public Student() {
    }

    // Constructor đầy đủ tham số
    public Student(String studentID, String fullName, String dob, String gender,
            String email, String phone, String classID) {
        this.studentID = studentID;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.classID = classID;
    }

    // Getters và Setters
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID='" + studentID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", classID='" + classID + '\'' +
                '}';
    }
}

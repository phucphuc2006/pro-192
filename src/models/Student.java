package models;

/**
 * Lop Student dai dien cho mot sinh vien trong he thong quan ly.
 * Chua cac thong tin co ban ve sinh vien nhu ma, ten, ngay sinh, lop.
 * 
 * @author StudentManagement Team
 * @version 1.0
 * @since 2024
 */
public class Student extends Person {
    private String dob; // Ngày sinh
    private String gender; // Giới tính
    private String classID; // Mã lớp học

    // Constructor mặc định
    public Student() {
    }

    // Constructor đầy đủ tham số
    public Student(String studentID, String fullName, String dob, String gender,
            String email, String phone, String classID) {
        super(studentID, fullName, email, phone);
        this.dob = dob;
        this.gender = gender;
        this.classID = classID;
    }

    // Getters và Setters riêng của Student
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

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    @Override
    public String toString() {
        return "Student{" +
                super.toString() +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", classID='" + classID + '\'' +
                '}';
    }
}

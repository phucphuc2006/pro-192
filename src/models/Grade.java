package models;

/**
 * Lop Grade dai dien cho diem so cua sinh vien.
 * Chua diem giua ky, cuoi ky va tu dong tinh tong diem theo cong thuc 40%-60%.
 * 
 * @author StudentManagement Team
 * @version 1.0
 * @since 2024
 */
public class Grade {
    private String gradeID; // Mã điểm
    private String studentID; // Mã sinh viên
    private String courseID; // Mã môn học
    private double midterm; // Điểm giữa kỳ
    private double finalExam; // Điểm cuối kỳ
    private double total; // Tổng điểm

    // Constructor mặc định
    public Grade() {
    }

    // Constructor đầy đủ tham số
    public Grade(String gradeID, String studentID, String courseID,
            double midterm, double finalExam, double total) {
        this.gradeID = gradeID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.total = total;
    }

    // Constructor tự động tính tổng điểm
    public Grade(String gradeID, String studentID, String courseID,
            double midterm, double finalExam) {
        this.gradeID = gradeID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.total = calculateTotal();
    }

    // Phương thức tính tổng điểm (có thể tùy chỉnh công thức)
    public double calculateTotal() {
        // Công thức mặc định: 40% giữa kỳ + 60% cuối kỳ
        return midterm * 0.4 + finalExam * 0.6;
    }

    // Getters và Setters
    public String getGradeID() {
        return gradeID;
    }

    public void setGradeID(String gradeID) {
        this.gradeID = gradeID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public double getMidterm() {
        return midterm;
    }

    public void setMidterm(double midterm) {
        this.midterm = midterm;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeID='" + gradeID + '\'' +
                ", studentID='" + studentID + '\'' +
                ", courseID='" + courseID + '\'' +
                ", midterm=" + midterm +
                ", finalExam=" + finalExam +
                ", total=" + total +
                '}';
    }
}

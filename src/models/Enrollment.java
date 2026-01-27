package models;

/**
 * Lớp Enrollment đại diện cho việc đăng ký môn học của sinh viên
 */
public class Enrollment {
    private String enrollmentID; // Mã đăng ký
    private String studentID; // Mã sinh viên
    private String courseID; // Mã môn học
    private String semester; // Học kỳ đăng ký

    // Constructor mặc định
    public Enrollment() {
    }

    // Constructor đầy đủ tham số
    public Enrollment(String enrollmentID, String studentID, String courseID, String semester) {
        this.enrollmentID = enrollmentID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.semester = semester;
    }

    // Getters và Setters
    public String getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(String enrollmentID) {
        this.enrollmentID = enrollmentID;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentID='" + enrollmentID + '\'' +
                ", studentID='" + studentID + '\'' +
                ", courseID='" + courseID + '\'' +
                ", semester='" + semester + '\'' +
                '}';
    }
}

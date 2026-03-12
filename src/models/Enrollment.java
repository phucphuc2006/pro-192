package models;

public class Enrollment {
    private String enrollmentID;
    private String studentID;
    private String courseID;
    private double grade;

    public Enrollment(String enrollmentID, String studentID, String courseID, double grade) {
        this.enrollmentID = enrollmentID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.grade = grade;
    }

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

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentID='" + enrollmentID + '\'' +
                ", studentID='" + studentID + '\'' +
                ", courseID='" + courseID + '\'' +
                ", grade=" + grade +
                '}';
    }
}

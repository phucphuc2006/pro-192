package models;

public class Enrollment {
    private String id;
    private String studentID;
    private String courseID;
    private double grade;

    public Enrollment(String id, String studentID, String courseID, double grade) {
        this.id = id;
        this.studentID = studentID;
        this.courseID = courseID;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", studentID='" + studentID + '\'' +
                ", courseID='" + courseID + '\'' +
                ", grade=" + grade +
                '}';
    }
}

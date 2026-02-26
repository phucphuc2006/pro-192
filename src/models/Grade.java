package models;

public class Grade {
    private String gradeID;
    private String studentID;
    private String courseID;
    private double midterm;
    private double finalExam;
    private double total;

    public Grade(String gradeID, String studentID, String courseID, double midterm, double finalExam) {
        this.gradeID = gradeID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.total = calculateTotal();
    }

    public double calculateTotal() {
        // Formula: 0.4 * Midterm + 0.6 * FinalExam
        return (midterm * 0.4) + (finalExam * 0.6);
    }

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
        this.total = calculateTotal();
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
        this.total = calculateTotal();
    }

    public double getTotal() {
        return total;
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

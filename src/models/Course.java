package models;

/**
 * Lớp Course đại diện cho một môn học trong hệ thống
 */
public class Course implements Identifiable {
    private String courseID; // Mã môn học
    private String courseName; // Tên môn học
    private int credits; // Số tín chỉ
    private String semester; // Học kỳ giảng dạy
    private String teacherID; // Mã giảng viên

    // Constructor mặc định
    public Course() {
    }

    // Constructor đầy đủ tham số
    public Course(String courseID, String courseName, int credits,
            String semester, String teacherID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credits = credits;
        this.semester = semester;
        this.teacherID = teacherID;
    }

    @Override
    public String getId() {
        return courseID;
    }

    @Override
    public void setId(String id) {
        this.courseID = id;
    }

    @Override
    public String getName() {
        return courseName;
    }

    @Override
    public void setName(String name) {
        this.courseName = name;
    }

    // Getters và Setters
    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credits=" + credits +
                ", semester='" + semester + '\'' +
                ", teacherID='" + teacherID + '\'' +
                '}';
    }
}

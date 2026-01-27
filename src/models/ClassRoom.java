package models;

/**
 * Lớp ClassRoom đại diện cho một lớp học trong hệ thống
 */
public class ClassRoom {
    private String classID; // Mã lớp học
    private String className; // Tên lớp học
    private String teacherID; // Mã giảng viên giảng dạy lớp
    private String courseID; // Mã môn học

    // Constructor mặc định
    public ClassRoom() {
    }

    // Constructor đầy đủ tham số
    public ClassRoom(String classID, String className, String teacherID, String courseID) {
        this.classID = classID;
        this.className = className;
        this.teacherID = teacherID;
        this.courseID = courseID;
    }

    // Getters và Setters
    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "classID='" + classID + '\'' +
                ", className='" + className + '\'' +
                ", teacherID='" + teacherID + '\'' +
                ", courseID='" + courseID + '\'' +
                '}';
    }
}

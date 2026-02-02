package models;

public class ClassRoom {
    private String classID;
    private String className;
    private String teacherID;
    private String courseID;

    public ClassRoom(String classID, String className, String teacherID, String courseID) {
        this.classID = classID;
        this.className = className;
        this.teacherID = teacherID;
        this.courseID = courseID;
    }

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

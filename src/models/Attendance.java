package models;

public class Attendance {
    private String attendanceID;
    private String studentID;
    private String classID;
    private String date;
    private String status; // Expecting "Present", "Absent", "Excused" or similar codes

    public Attendance(String attendanceID, String studentID, String classID, String date, String status) {
        this.attendanceID = attendanceID;
        this.studentID = studentID;
        this.classID = classID;
        this.date = date;
        this.status = status;
    }

    public boolean isPresent() {
        return "Present".equalsIgnoreCase(status) || "Co mat".equalsIgnoreCase(status);
    }

    public boolean isAbsent() {
        return "Absent".equalsIgnoreCase(status) || "Vang".equalsIgnoreCase(status);
    }

    public boolean isExcused() {
        return "Excused".equalsIgnoreCase(status) || "Co phep".equalsIgnoreCase(status);
    }

    public String getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(String attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "attendanceID='" + attendanceID + '\'' +
                ", studentID='" + studentID + '\'' +
                ", classID='" + classID + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

package models;

/**
 * Lớp Attendance đại diện cho việc điểm danh sinh viên
 */
public class Attendance {
    private String attendanceID; // Mã điểm danh
    private String studentID; // Mã sinh viên
    private String classID; // Mã lớp học
    private String date; // Ngày điểm danh
    private String status; // Trạng thái (Có mặt, Vắng, Có phép)

    // Các hằng số trạng thái
    public static final String STATUS_PRESENT = "Có mặt";
    public static final String STATUS_ABSENT = "Vắng";
    public static final String STATUS_EXCUSED = "Có phép";

    // Constructor mặc định
    public Attendance() {
    }

    // Constructor đầy đủ tham số
    public Attendance(String attendanceID, String studentID, String classID,
            String date, String status) {
        this.attendanceID = attendanceID;
        this.studentID = studentID;
        this.classID = classID;
        this.date = date;
        this.status = status;
    }

    // Getters và Setters
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

    // Kiểm tra sinh viên có mặt không
    public boolean isPresent() {
        return STATUS_PRESENT.equals(status);
    }

    // Kiểm tra sinh viên vắng không
    public boolean isAbsent() {
        return STATUS_ABSENT.equals(status);
    }

    // Kiểm tra sinh viên có phép không
    public boolean isExcused() {
        return STATUS_EXCUSED.equals(status);
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

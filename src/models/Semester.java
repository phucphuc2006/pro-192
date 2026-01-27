package models;

/**
 * Lớp Semester đại diện cho một học kỳ
 */
public class Semester {
    private String semesterID; // Mã học kỳ
    private String semesterName; // Tên học kỳ (Ví dụ: HK1, HK2)
    private String startDate; // Ngày bắt đầu học kỳ
    private String endDate; // Ngày kết thúc học kỳ

    // Constructor mặc định
    public Semester() {
    }

    // Constructor đầy đủ tham số
    public Semester(String semesterID, String semesterName, String startDate, String endDate) {
        this.semesterID = semesterID;
        this.semesterName = semesterName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters và Setters
    public String getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(String semesterID) {
        this.semesterID = semesterID;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Semester{" +
                "semesterID='" + semesterID + '\'' +
                ", semesterName='" + semesterName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}

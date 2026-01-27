package models;

/**
 * Lớp Department đại diện cho một khoa trong trường
 */
public class Department {
    private String departmentID; // Mã khoa
    private String departmentName; // Tên khoa
    private int facultyCount; // Số lượng giảng viên trong khoa

    // Constructor mặc định
    public Department() {
    }

    // Constructor đầy đủ tham số
    public Department(String departmentID, String departmentName, int facultyCount) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.facultyCount = facultyCount;
    }

    // Getters và Setters
    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getFacultyCount() {
        return facultyCount;
    }

    public void setFacultyCount(int facultyCount) {
        this.facultyCount = facultyCount;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentID='" + departmentID + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", facultyCount=" + facultyCount +
                '}';
    }
}

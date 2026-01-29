package models;

/**
 * Lớp Department đại diện cho một khoa trong trường
 */
public class Department implements Identifiable {
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

    @Override
    public String getId() {
        return departmentID;
    }

    @Override
    public void setId(String id) {
        this.departmentID = id;
    }

    @Override
    public String getName() {
        return departmentName;
    }

    @Override
    public void setName(String name) {
        this.departmentName = name;
    }

    // Getters và Setters
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

package models;

public class Department {
    private String departmentID;
    private String departmentName;
    private int facultyCount;

    public Department(String departmentID, String departmentName, int facultyCount) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.facultyCount = facultyCount;
    }

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

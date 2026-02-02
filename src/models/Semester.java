package models;

public class Semester {
    private String semesterID;
    private String semesterName;
    private String startDate;
    private String endDate;

    public Semester(String semesterID, String semesterName, String startDate, String endDate) {
        this.semesterID = semesterID;
        this.semesterName = semesterName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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

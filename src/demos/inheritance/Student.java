package demos.inheritance;

public class Student extends Person {
    private String dob;
    private String gender;
    private String classID;

    public Student() {
    }

    public Student(String id, String fullName, String email, String phone, String dob, String gender, String classID) {
        super(id, fullName, email, phone);
        this.dob = dob;
        this.gender = gender;
        this.classID = classID;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", classID='" + classID + '\'' +
                '}';
    }
}

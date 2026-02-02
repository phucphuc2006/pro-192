package models;

public class Student {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String dob;
    private String gender;
    private String classID;

    public Student(String id, String fullName, String email, String phone, String dob, String gender, String classID) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.classID = classID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", classID='" + classID + '\'' +
                '}';
    }
}

package demos.inheritance;

public class Teacher extends Person {
    private String department;

    public Teacher() {
    }

    public Teacher(String id, String fullName, String email, String phone, String department) {
        super(id, fullName, email, phone);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}

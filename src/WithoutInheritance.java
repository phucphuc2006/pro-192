
// ============ LỚP STUDENT (độc lập) ============
class StudentNoInherit {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String dob;
    private String gender;
    private String classID;

    public StudentNoInherit() {
    }

    public StudentNoInherit(String id, String fullName, String email, String phone, String dob, String gender,
            String classID) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.classID = classID;
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
        return "Student{id='" + id + "', fullName='" + fullName + "', email='" + email +
                "', phone='" + phone + "', dob='" + dob + "', gender='" + gender + "', classID='" + classID + "'}";
    }
}

// ============ LỚP TEACHER (độc lập) ============
class TeacherNoInherit {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String department;

    public TeacherNoInherit() {
    }

    public TeacherNoInherit(String id, String fullName, String email, String phone, String department) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Teacher{id='" + id + "', fullName='" + fullName + "', email='" + email +
                "', phone='" + phone + "', department='" + department + "'}";
    }
}

// ============ CHƯƠNG TRÌNH CHÍNH ============
public class WithoutInheritance {
    public static void main(String[] args) {
        System.out.println("=== DEMO: KHÔNG KẾ THỪA (WITHOUT INHERITANCE) ===\n");

        // Tạo Student
        StudentNoInherit student = new StudentNoInherit("S002", "Tran Van C", "student2@example.com",
                "0911223344", "2004-05-05", "Female", "SE1802");
        System.out.println("Student Info:");
        System.out.println(student.toString());

        // Tạo Teacher
        TeacherNoInherit teacher = new TeacherNoInherit("T002", "Pham Thi D", "teacher2@example.com",
                "0998877665", "Information Systems");
        System.out.println("\nTeacher Info:");
        System.out.println(teacher.toString());

        // Không có Polymorphism vì không có lớp cha chung

    }
}

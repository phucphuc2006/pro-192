
// ============ LỚP CHA: PERSON ============
class Person {
    private String id;
    private String fullName;
    private String email;
    private String phone;

    public Person() {
    }

    public Person(String id, String fullName, String email, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
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

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "Person{id='" + id + "', fullName='" + fullName + "', email='" + email + "', phone='" + phone + "'}";
    }
}

// ============ LỚP CON: STUDENT (kế thừa Person) ============
class Student extends Person {
    private String dob;
    private String gender;
    private String classID;

    public Student() {
    }

    public Student(String id, String fullName, String email, String phone, String dob, String gender, String classID) {
        super(id, fullName, email, phone); // Gọi constructor của lớp cha
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
        return "Student{id='" + getId() + "', fullName='" + getFullName() + "', email='" + getEmail() +
                "', phone='" + getPhone() + "', dob='" + dob + "', gender='" + gender + "', classID='" + classID + "'}";
    }
}

// ============ LỚP CON: TEACHER (kế thừa Person) ============
class Teacher extends Person {
    private String department;

    public Teacher() {
    }

    public Teacher(String id, String fullName, String email, String phone, String department) {
        super(id, fullName, email, phone); // Gọi constructor của lớp cha
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
        return "Teacher{id='" + getId() + "', fullName='" + getFullName() + "', email='" + getEmail() +
                "', phone='" + getPhone() + "', department='" + department + "'}";
    }
}

// ============ CHƯƠNG TRÌNH CHÍNH ============
public class WithInheritance {
    public static void main(String[] args) {
        System.out.println("=== DEMO: CÓ KẾ THỪA (WITH INHERITANCE) ===\n");

        // Tạo Student
        Student student = new Student("S001", "Nguyen Van A", "student@example.com", "0901234567",
                "2005-01-01", "Male", "SE1801");
        System.out.println("Student Info:");
        System.out.println(student.toString());

        // Tạo Teacher
        Teacher teacher = new Teacher("T001", "Le Van B", "teacher@example.com", "0909876543",
                "Software Engineering");
        System.out.println("\nTeacher Info:");
        System.out.println(teacher.toString());

        // Demo tính đa hình (Polymorphism)
        System.out.println("\n--- Polymorphism Demo ---");
        Person person1 = student; // Student có thể gán vào biến Person
        Person person2 = teacher; // Teacher có thể gán vào biến Person
        System.out.println("Person 1 (Student) Name: " + person1.getFullName());
        System.out.println("Person 2 (Teacher) Name: " + person2.getFullName());
    }
}

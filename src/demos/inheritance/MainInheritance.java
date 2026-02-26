package demos.inheritance;

public class MainInheritance {
    public static void main(String[] args) {
        System.out.println("=== DEMO: WITH INHERITANCE ===");

        // Create a Student
        // Student kế thừa từ Person, nên có constructor gọi super()
        Student student = new Student("S001", "Nguyen Van Student", "student@example.com", "0901234567", "2005-01-01",
                "Male", "SE1801");
        System.out.println("Student Info:");
        System.out.println(student.toString());

        // Create a Teacher
        // Teacher cũng kế thừa từ Person
        Teacher teacher = new Teacher("T001", "Le Van Teacher", "teacher@example.com", "0909876543",
                "Software Engineering");
        System.out.println("\nTeacher Info:");
        System.out.println(teacher.toString());

        // Lợi ích của kế thừa: Đa hình (Polymorphism)
        // Có thể coi cả Student và Teacher là Person
        System.out.println("\n--- Polymorphism Demo ---");
        Person person1 = student;
        Person person2 = teacher;

        System.out.println("Person 1 (Student) Name: " + person1.getFullName());
        System.out.println("Person 2 (Teacher) Name: " + person2.getFullName());
    }
}

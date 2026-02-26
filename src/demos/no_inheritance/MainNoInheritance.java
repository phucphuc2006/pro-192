package demos.no_inheritance;

public class MainNoInheritance {
    public static void main(String[] args) {
        System.out.println("=== DEMO: NO INHERITANCE ===");

        // Create a Student
        // Student ở đây là một lớp độc lập, tự định nghĩa lại id, name, email, phone
        Student student = new Student("S002", "Tran Van Student", "student2@example.com", "0911223344", "2004-05-05",
                "Female", "SE1802");
        System.out.println("Student Info:");
        System.out.println(student.toString());

        // Create a Teacher
        // Teacher ở đây cũng là một lớp độc lập
        Teacher teacher = new Teacher("T002", "Pham Thi Teacher", "teacher2@example.com", "0998877665",
                "Information Systems");
        System.out.println("\nTeacher Info:");
        System.out.println(teacher.toString());

        // Không có kế thừa, Student và Teacher là 2 kiểu dữ liệu hoàn toàn khác biệt
        // Không thể gán chúng cho một biến chung kiểu "Person"
        System.out.println("\nNote: Without inheritance, Student and Teacher are completely separate types.");
    }
}

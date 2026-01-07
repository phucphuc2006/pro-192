import java.io.Serializable;

public class Student implements Serializable {
    private String id;
    private String name;
    private int age;
    private double gpa;

    // Constructor
    public Student(String id, String name, int age, double gpa) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    // Hiển thị thông tin dạng chuỗi đẹp mắt
    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-5d | %-5.2f |", id, name, age, gpa);
    }
    
    // Định dạng để lưu vào file (CSV format)
    public String toFileString() {
        return id + "," + name + "," + age + "," + gpa;
    }
}
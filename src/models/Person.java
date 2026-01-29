package models;

/**
 * Lop Person dai dien cho thong tin chung cua con nguoi (Sinh vien, Giang
 * vien).
 * 
 * @author StudentManagement Team
 */
public abstract class Person implements Identifiable {
    private String id; // ID chung (Student ID hoac Teacher ID)
    private String fullName; // Ho ten
    private String email; // Email
    private String phone; // So dien thoai

    // Constructor khong tham so
    public Person() {
    }

    // Constructor day du tham so
    public Person(String id, String fullName, String email, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    // Getters va Setters
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return fullName;
    }

    @Override
    public void setName(String name) {
        this.fullName = name;
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'';
    }
}

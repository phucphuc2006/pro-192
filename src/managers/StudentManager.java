package managers;

import models.Student;
import java.io.*;
import java.util.*;

/**
 * Quan ly sinh vien - Su dung models.Student moi
 */
public class StudentManager {
    private ArrayList<Student> students = new ArrayList<>();
    private final String FILE_NAME = "data/students.txt";

    // Them sinh vien
    public void addStudent(Student s) {
        students.add(s);
        System.out.println("-> Them sinh vien thanh cong!");
    }

    // Sua sinh vien theo ID
    public void updateStudent(String id, String newName, String newDob, String newGender,
            String newEmail, String newPhone, String newClassID) {
        Student s = findStudentById(id);
        if (s != null) {
            s.setFullName(newName);
            s.setDob(newDob);
            s.setGender(newGender);
            s.setEmail(newEmail);
            s.setPhone(newPhone);
            s.setClassID(newClassID);
            System.out.println("-> Cap nhat sinh vien thanh cong!");
        } else {
            System.out.println("-> Khong tim thay sinh vien.");
        }
    }

    // Xoa sinh vien
    public void deleteStudent(String id) {
        Student s = findStudentById(id);
        if (s != null) {
            students.remove(s);
            System.out.println("-> Da xoa sinh vien co ID: " + id);
        } else {
            System.out.println("-> Khong tim thay sinh vien de xoa.");
        }
    }

    // Tim kiem theo ten
    public void searchByName(String keyword) {
        System.out.println("--- KET QUA TIM KIEM SINH VIEN ---");
        boolean found = false;
        for (Student s : students) {
            if (s.getFullName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found)
            System.out.println("-> Khong tim thay sinh vien nao: " + keyword);
    }

    // Hien thi danh sach
    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("-> Danh sach sinh vien trong!");
            return;
        }
        System.out.println(
                "| Ma SV      | Ho ten               | Ngay sinh  | Gioi tinh | Email                | SDT          | Ma lop     |");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------");
        for (Student s : students) {
            System.out.printf("| %-10s | %-20s | %-10s | %-9s | %-20s | %-12s | %-10s |\n",
                    s.getStudentID(), s.getFullName(), s.getDob(), s.getGender(),
                    s.getEmail(), s.getPhone(), s.getClassID());
        }
    }

    // Tim sinh vien theo ID
    public Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getStudentID().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Lay danh sach tat ca sinh vien.
     * 
     * @return ArrayList sinh vien
     */
    public ArrayList<Student> getAll() {
        return new ArrayList<>(students);
    }

    /**
     * Lay so luong sinh vien.
     * 
     * @return So luong
     */
    public int getCount() {
        return students.size();
    }

    /**
     * Sap xep theo ten (A-Z).
     */
    public void sortByName() {
        students.sort((s1, s2) -> s1.getFullName().compareToIgnoreCase(s2.getFullName()));
        System.out.println("-> Da sap xep theo ten!");
    }

    /**
     * Sap xep theo ma sinh vien.
     */
    public void sortById() {
        students.sort((s1, s2) -> s1.getStudentID().compareToIgnoreCase(s2.getStudentID()));
        System.out.println("-> Da sap xep theo ma SV!");
    }

    /**
     * Sap xep theo lop.
     */
    public void sortByClass() {
        students.sort((s1, s2) -> s1.getClassID().compareToIgnoreCase(s2.getClassID()));
        System.out.println("-> Da sap xep theo lop!");
    }

    // Luu file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.write(s.getStudentID() + "," + s.getFullName() + "," +
                        s.getDob() + "," + s.getGender() + "," +
                        s.getEmail() + "," + s.getPhone() + "," + s.getClassID());
                writer.newLine();
            }
            System.out.println("-> Da luu du lieu sinh vien vao " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("-> Loi khi luu file sinh vien: " + e.getMessage());
        }
    }

    // Doc file
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            students.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    Student s = new Student(parts[0], parts[1], parts[2], parts[3],
                            parts[4], parts[5], parts[6]);
                    students.add(s);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Loi khi doc file sinh vien: " + e.getMessage());
        }
    }
}

package managers;

import models.Student;
import java.io.*;

/**
 * Quan ly sinh vien - Su dung models.Student moi
 */
public class StudentManager extends BaseManager<Student> {

    public StudentManager() {
        super("data/students.txt");
    }

    // Wrapper cho add (de giu tuong thich neu can, hoac su dung add truc tiep)
    public void addStudent(Student s) {
        super.add(s);
    }

    // Wrapper cho delete
    // Luu y: BaseManager.delete in ra thong bao chung, neu muon giu thong bao cu
    // thi can override
    public void deleteStudent(String id) {
        super.delete(id);
    }

    // Wrapper cho find
    public Student findStudentById(String id) {
        return super.findById(id);
    }

    // Sua sinh vien theo ID
    public void updateStudent(String id, String newName, String newDob, String newGender,
            String newEmail, String newPhone, String newClassID) {
        Student s = findById(id);
        if (s != null) {
            s.setName(newName);
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

    // Hien thi danh sach (Grid view rieng biet)
    public void displayAll() {
        if (list.isEmpty()) {
            System.out.println("-> Danh sach sinh vien trong!");
            return;
        }
        System.out.println(
                "| Ma SV      | Ho ten               | Ngay sinh  | Gioi tinh | Email                | SDT          | Ma lop     |");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------");
        for (Student s : list) {
            System.out.printf("| %-10s | %-20s | %-10s | %-9s | %-20s | %-12s | %-10s |\n",
                    s.getId(), s.getName(), s.getDob(), s.getGender(),
                    s.getEmail(), s.getPhone(), s.getClassID());
        }
    }

    /**
     * Sap xep theo ten (A-Z).
     */
    public void sortByName() {
        list.sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
        System.out.println("-> Da sap xep theo Ten.");
    }

    /**
     * Sap xep theo ma sinh vien.
     */
    public void sortById() {
        list.sort((s1, s2) -> s1.getId().compareToIgnoreCase(s2.getId()));
        System.out.println("-> Da sap xep theo ID.");
    }

    /**
     * Sap xep theo lop.
     */
    public void sortByClass() {
        list.sort((s1, s2) -> s1.getClassID().compareToIgnoreCase(s2.getClassID()));
        System.out.println("-> Da sap xep theo lop!");
    }

    // Luu file
    @Override
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : list) {
                writer.write(s.getId() + "," + s.getName() + "," +
                        s.getDob() + "," + s.getGender() + "," +
                        s.getClassID() + "," + s.getEmail() + "," + s.getPhone());
                writer.newLine();
            }
            System.out.println("-> Da luu file.");
        } catch (IOException e) {
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }

    // Doc file
    @Override
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            list.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    Student s = new Student(parts[0], parts[1], parts[2], parts[3],
                            parts[4], parts[5], parts[6]);
                    list.add(s);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Loi khi doc file sinh vien: " + e.getMessage());
        }
    }
}

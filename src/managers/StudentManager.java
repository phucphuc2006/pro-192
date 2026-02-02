package managers;

import models.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students;
    private final String FILE_PATH = "data/students.txt";

    public StudentManager() {
        this.students = new ArrayList<>();
        loadFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveToFile();
    }

    public void updateStudent(String id, Student newStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.set(i, newStudent);
                saveToFile();
                return;
            }
        }
    }

    public void deleteStudent(String id) {
        students.removeIf(s -> s.getId().equals(id));
        saveToFile();
    }

    public Student getStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public List<Student> searchStudents(String keyword) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getFullName().toLowerCase().contains(keyword.toLowerCase()) ||
                    s.getId().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    students.add(new Student(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            parts[3].trim(),
                            parts[4].trim(),
                            parts[5].trim(),
                            parts[6].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student s : students) {
                bw.write(String.format("%s,%s,%s,%s,%s,%s,%s",
                        s.getId(),
                        s.getFullName(),
                        s.getEmail(),
                        s.getPhone(),
                        s.getDob(),
                        s.getGender(),
                        s.getClassID()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }
}

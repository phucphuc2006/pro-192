package managers;

import models.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentManager implements IManager<Student> {
    private List<Student> students;
    private final String FILE_PATH = "data/students.txt";

    public StudentManager() {
        this.students = new ArrayList<>();
        loadFromFile();
    }

    @Override
    public void add(Student item) {
        students.add(item);
        saveToFile();
    }

    @Override
    public void update(String id, Student item) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.set(i, item);
                saveToFile();
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        students.removeIf(s -> s.getId().equals(id));
        saveToFile();
    }

    @Override
    public Student getById(String id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Student> getAll() {
        return students;
    }

    @Override
    public void sort(Comparator<Student> comparator) {
        students.sort(comparator);
    }

    @Override
    public List<Student> search(Predicate<Student> condition) {
        return students.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    @Override
    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            students.clear();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    students.add(new Student(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(),
                            parts[4].trim(), parts[5].trim(), parts[6].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student s : students) {
                bw.write(String.format("%s,%s,%s,%s,%s,%s,%s",
                        s.getId(), s.getFullName(), s.getEmail(), s.getPhone(),
                        s.getDob(), s.getGender(), s.getClassID()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }
}

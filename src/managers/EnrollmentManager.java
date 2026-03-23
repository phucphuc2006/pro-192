package managers;

import models.Enrollment;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentManager implements IManager<Enrollment> {
    private List<Enrollment> enrollments;
    private final String FILE_PATH = "data/enrollments.txt";

    public EnrollmentManager() {
        this.enrollments = new ArrayList<>();
        loadFromFile();
    }

    @Override
    public void add(Enrollment item) {
        enrollments.add(item);
        saveToFile();
    }

    @Override
    public void update(String id, Enrollment item) {
        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getId().equals(id)) {
                enrollments.set(i, item);
                saveToFile();
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        enrollments.removeIf(e -> e.getId().equals(id));
        saveToFile();
    }

    @Override
    public Enrollment getById(String id) {
        return enrollments.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Enrollment> getAll() {
        return enrollments;
    }

    @Override
    public void sort(Comparator<Enrollment> comparator) {
        enrollments.sort(comparator);
    }

    @Override
    public List<Enrollment> search(String keyword) {
        return enrollments.stream()
                .filter(e -> e.getStudentID().toLowerCase().contains(keyword.toLowerCase()) ||
                        e.getCourseID().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            enrollments.clear();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    enrollments.add(new Enrollment(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(), Double.parseDouble(parts[3].trim())));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading enrollments: " + e.getMessage());
        }
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Enrollment e : enrollments) {
                bw.write(String.format("%s,%s,%s,%.2f",
                        e.getId(), e.getStudentID(), e.getCourseID(), e.getGrade()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving enrollments: " + e.getMessage());
        }
    }
}

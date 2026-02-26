package managers;

import models.Enrollment;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentManager {
    private List<Enrollment> enrollments;
    private final String FILE_PATH = "data/enrollments.txt";

    public EnrollmentManager() {
        this.enrollments = new ArrayList<>();
        loadFromFile();
    }

    public void add(Enrollment e) {
        enrollments.add(e);
        saveToFile();
    }

    public void delete(String id) {
        enrollments.removeIf(e -> e.getEnrollmentID().equals(id));
        saveToFile();
    }

    public List<Enrollment> getAll() {
        return enrollments;
    }

    public List<Enrollment> getByStudentId(String studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudentID().equals(studentId))
                result.add(e);
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
                if (line.isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    enrollments.add(new Enrollment(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Enrollment e : enrollments) {
                bw.write(String.format("%s,%s,%s,%s", e.getEnrollmentID(), e.getStudentID(), e.getCourseID(),
                        e.getSemester()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package managers;

import models.Grade;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GradeManager {
    private List<Grade> grades;
    private final String FILE_PATH = "data/grades.txt";

    public GradeManager() {
        this.grades = new ArrayList<>();
        loadFromFile();
    }

    public void add(Grade g) {
        grades.add(g);
        saveToFile();
    }

    public void update(String id, Grade newItem) {
        for (int i = 0; i < grades.size(); i++) {
            if (grades.get(i).getGradeID().equals(id)) {
                grades.set(i, newItem);
                saveToFile();
                return;
            }
        }
    }

    public void delete(String id) {
        grades.removeIf(g -> g.getGradeID().equals(id));
        saveToFile();
    }

    public List<Grade> getAll() {
        return grades;
    }

    public List<Grade> getByStudentId(String studentId) {
        List<Grade> result = new ArrayList<>();
        for (Grade g : grades) {
            if (g.getStudentID().equals(studentId))
                result.add(g);
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
                if (parts.length >= 5) { // Assuming total might not be stored or is 6th
                    grades.add(new Grade(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4])));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Grade g : grades) {
                bw.write(String.format("%s,%s,%s,%.2f,%.2f,%.2f",
                        g.getGradeID(), g.getStudentID(), g.getCourseID(), g.getMidterm(), g.getFinalExam(),
                        g.getTotal()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

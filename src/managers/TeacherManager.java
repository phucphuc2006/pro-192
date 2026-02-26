package managers;

import models.Teacher;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherManager {
    private List<Teacher> teachers;
    private final String FILE_PATH = "data/teachers.txt";

    public TeacherManager() {
        this.teachers = new ArrayList<>();
        loadFromFile();
    }

    public void add(Teacher t) {
        teachers.add(t);
        saveToFile();
    }

    public void update(String id, Teacher newTeacher) {
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getId().equals(id)) {
                teachers.set(i, newTeacher);
                saveToFile();
                return;
            }
        }
    }

    public void delete(String id) {
        teachers.removeIf(t -> t.getId().equals(id));
        saveToFile();
    }

    public Teacher getById(String id) {
        return teachers.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Teacher> getAll() {
        return teachers;
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
                if (parts.length >= 5) {
                    teachers.add(new Teacher(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Teacher t : teachers) {
                bw.write(String.format("%s,%s,%s,%s,%s", t.getId(), t.getFullName(), t.getEmail(), t.getPhone(),
                        t.getDepartment()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

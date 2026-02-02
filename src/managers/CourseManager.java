package managers;

import models.Course;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private List<Course> courses;
    private final String FILE_PATH = "data/courses.txt";

    public CourseManager() {
        this.courses = new ArrayList<>();
        loadFromFile();
    }

    public void add(Course c) {
        courses.add(c);
        saveToFile();
    }

    public void update(String id, Course newCourse) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseID().equals(id)) {
                courses.set(i, newCourse);
                saveToFile();
                return;
            }
        }
    }

    public void delete(String id) {
        courses.removeIf(c -> c.getCourseID().equals(id));
        saveToFile();
    }

    public Course getById(String id) {
        return courses.stream().filter(c -> c.getCourseID().equals(id)).findFirst().orElse(null);
    }

    public List<Course> getAll() {
        return courses;
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
                    courses.add(new Course(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], parts[4]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Course c : courses) {
                bw.write(String.format("%s,%s,%d,%s,%s", c.getCourseID(), c.getCourseName(), c.getCredits(),
                        c.getSemester(), c.getTeacherID()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

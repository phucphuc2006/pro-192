package managers;

import models.Course;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CourseManager implements IManager<Course> {
    private List<Course> courses;
    private final String FILE_PATH = "data/courses.txt";

    public CourseManager() {
        this.courses = new ArrayList<>();
        loadFromFile();
    }

    @Override
    public void add(Course item) {
        courses.add(item);
        saveToFile();
    }

    @Override
    public void update(String id, Course item) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId().equals(id)) {
                courses.set(i, item);
                saveToFile();
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        courses.removeIf(c -> c.getId().equals(id));
        saveToFile();
    }

    @Override
    public Course getById(String id) {
        return courses.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Course> getAll() {
        return courses;
    }

    @Override
    public void sort(Comparator<Course> comparator) {
        courses.sort(comparator);
    }

    @Override
    public List<Course> search(Predicate<Course> condition) {
        return courses.stream()
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
            courses.clear();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    courses.add(new Course(parts[0].trim(), parts[1].trim(), Integer.parseInt(parts[2].trim())));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Course c : courses) {
                bw.write(String.format("%s,%s,%d", c.getId(), c.getCourseName(), c.getCredits()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }
}

package managers;

import models.ClassRoom;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClassRoomManager {
    private List<ClassRoom> classes;
    private final String FILE_PATH = "data/classes.txt";

    public ClassRoomManager() {
        this.classes = new ArrayList<>();
        loadFromFile();
    }

    public void add(ClassRoom c) {
        classes.add(c);
        saveToFile();
    }

    public void update(String id, ClassRoom newItem) {
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(i).getClassID().equals(id)) {
                classes.set(i, newItem);
                saveToFile();
                return;
            }
        }
    }

    public void delete(String id) {
        classes.removeIf(c -> c.getClassID().equals(id));
        saveToFile();
    }

    public ClassRoom getById(String id) {
        return classes.stream().filter(c -> c.getClassID().equals(id)).findFirst().orElse(null);
    }

    public List<ClassRoom> getAll() {
        return classes;
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
                    classes.add(new ClassRoom(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ClassRoom c : classes) {
                bw.write(String.format("%s,%s,%s,%s", c.getClassID(), c.getClassName(), c.getTeacherID(),
                        c.getCourseID()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package managers;

import models.Department;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentManager {
    private List<Department> departments;
    private final String FILE_PATH = "data/departments.txt";

    public DepartmentManager() {
        this.departments = new ArrayList<>();
        loadFromFile();
    }

    public void add(Department d) {
        departments.add(d);
        saveToFile();
    }

    // CRUD methods simplified
    public List<Department> getAll() {
        return departments;
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
                if (parts.length >= 3) {
                    departments.add(new Department(parts[0], parts[1], Integer.parseInt(parts[2])));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Department d : departments) {
                bw.write(d.getDepartmentID() + "," + d.getDepartmentName() + "," + d.getFacultyCount());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

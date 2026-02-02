package managers;

import models.Semester;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SemesterManager {
    private List<Semester> semesters;
    private final String FILE_PATH = "data/semesters.txt";

    public SemesterManager() {
        this.semesters = new ArrayList<>();
        loadFromFile();
    }

    public void add(Semester s) {
        semesters.add(s);
        saveToFile();
    }

    public List<Semester> getAll() {
        return semesters;
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
                    semesters.add(new Semester(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Semester s : semesters) {
                bw.write(String.format("%s,%s,%s,%s", s.getSemesterID(), s.getSemesterName(), s.getStartDate(),
                        s.getEndDate()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

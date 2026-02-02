package managers;

import models.Attendance;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceManager {
    private List<Attendance> attendanceList;
    private final String FILE_PATH = "data/attendance.txt";

    public AttendanceManager() {
        this.attendanceList = new ArrayList<>();
        loadFromFile();
    }

    public void add(Attendance a) {
        attendanceList.add(a);
        saveToFile();
    }

    public List<Attendance> getAll() {
        return attendanceList;
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
                    attendanceList.add(new Attendance(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Attendance a : attendanceList) {
                bw.write(String.format("%s,%s,%s,%s,%s", a.getAttendanceID(), a.getStudentID(), a.getClassID(),
                        a.getDate(), a.getStatus()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

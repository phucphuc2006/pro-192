package managers;

import models.Attendance;
import java.io.*;
import java.util.*;

/**
 * Quản lý điểm danh
 */
public class AttendanceManager {
    private ArrayList<Attendance> attendances = new ArrayList<>();
    private final String FILE_NAME = "data/attendances.txt";

    // Thêm điểm danh
    public void addAttendance(Attendance a) {
        attendances.add(a);
        System.out.println("-> Điểm danh thành công!");
    }

    // Sửa điểm danh theo ID
    public void updateAttendance(String id, String newStatus) {
        Attendance a = findAttendanceById(id);
        if (a != null) {
            a.setStatus(newStatus);
            System.out.println("-> Cập nhật điểm danh thành công!");
        } else {
            System.out.println("-> Không tìm thấy điểm danh.");
        }
    }

    // Xóa điểm danh
    public void deleteAttendance(String id) {
        Attendance a = findAttendanceById(id);
        if (a != null) {
            attendances.remove(a);
            System.out.println("-> Đã xóa điểm danh có ID: " + id);
        } else {
            System.out.println("-> Không tìm thấy điểm danh để xóa.");
        }
    }

    // Tìm kiếm theo mã sinh viên
    public void searchByStudentId(String studentId) {
        System.out.println("--- LỊCH SỬ ĐIỂM DANH CỦA SINH VIÊN " + studentId + " ---");
        boolean found = false;
        int present = 0, absent = 0, excused = 0;
        for (Attendance a : attendances) {
            if (a.getStudentID().equalsIgnoreCase(studentId)) {
                System.out.printf("Lớp: %s | Ngày: %s | Trạng thái: %s\n",
                        a.getClassID(), a.getDate(), a.getStatus());
                found = true;
                if (a.isPresent())
                    present++;
                else if (a.isAbsent())
                    absent++;
                else if (a.isExcused())
                    excused++;
            }
        }
        if (found) {
            System.out.println("---");
            System.out.printf("Tổng kết: Có mặt: %d | Vắng: %d | Có phép: %d\n", present, absent, excused);
        } else {
            System.out.println("-> Chưa có dữ liệu điểm danh.");
        }
    }

    // Hiển thị danh sách
    public void displayAll() {
        if (attendances.isEmpty()) {
            System.out.println("-> Danh sách điểm danh trống!");
            return;
        }
        System.out.println("| Mã ĐD      | Mã SV      | Mã Lớp     | Ngày       | Trạng thái |");
        System.out.println("-------------------------------------------------------------------");
        for (Attendance a : attendances) {
            System.out.printf("| %-10s | %-10s | %-10s | %-10s | %-10s |\n",
                    a.getAttendanceID(), a.getStudentID(), a.getClassID(), a.getDate(), a.getStatus());
        }
    }

    // Tìm điểm danh theo ID
    public Attendance findAttendanceById(String id) {
        for (Attendance a : attendances) {
            if (a.getAttendanceID().equalsIgnoreCase(id)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Lay danh sach tat ca diem danh.
     * 
     * @return ArrayList diem danh
     */
    public ArrayList<Attendance> getAll() {
        return new ArrayList<>(attendances);
    }

    /**
     * Lay so luong diem danh.
     * 
     * @return So luong
     */
    public int getCount() {
        return attendances.size();
    }

    // Luu file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Attendance a : attendances) {
                writer.write(a.getAttendanceID() + "," + a.getStudentID() + "," +
                        a.getClassID() + "," + a.getDate() + "," + a.getStatus());
                writer.newLine();
            }
            System.out.println("-> Đã lưu dữ liệu điểm danh vào " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("-> Lỗi khi lưu file điểm danh: " + e.getMessage());
        }
    }

    // Đọc file
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            attendances.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Attendance a = new Attendance(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    attendances.add(a);
                }
            }
        } catch (IOException e) {
            System.out.println("-> Lỗi khi đọc file điểm danh: " + e.getMessage());
        }
    }
}

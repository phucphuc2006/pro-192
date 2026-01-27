package utils;

import models.*;
import java.util.*;

/**
 * Lop tien ich thong ke du lieu.
 * Cung cap cac phuong thuc tinh toan thong ke cho sinh vien, diem so, diem
 * danh.
 * 
 * @author StudentManagement Team
 * @version 1.0
 */
public class StatisticsUtils {

    /**
     * Tinh diem trung binh cua mot danh sach diem.
     * 
     * @param grades Danh sach diem
     * @return Diem trung binh, tra ve 0 neu danh sach rong
     */
    public static double calculateAverageGrade(List<Grade> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }
        double total = 0;
        for (Grade g : grades) {
            total += g.getTotal();
        }
        return Math.round((total / grades.size()) * 100.0) / 100.0;
    }

    /**
     * Tinh ty le chuyen can (% co mat).
     * 
     * @param attendances Danh sach diem danh
     * @return Ty le % co mat
     */
    public static double calculateAttendanceRate(List<Attendance> attendances) {
        if (attendances == null || attendances.isEmpty()) {
            return 0.0;
        }
        int presentCount = 0;
        for (Attendance a : attendances) {
            if (Attendance.STATUS_PRESENT.equals(a.getStatus()) ||
                    Attendance.STATUS_EXCUSED.equals(a.getStatus())) {
                presentCount++;
            }
        }
        return Math.round((presentCount * 100.0 / attendances.size()) * 100.0) / 100.0;
    }

    /**
     * Dem so sinh vien theo lop.
     * 
     * @param students Danh sach sinh vien
     * @return Map voi key la ma lop, value la so sinh vien
     */
    public static Map<String, Integer> countStudentsByClass(List<Student> students) {
        Map<String, Integer> result = new HashMap<>();
        if (students == null)
            return result;

        for (Student s : students) {
            String classId = s.getClassID();
            result.put(classId, result.getOrDefault(classId, 0) + 1);
        }
        return result;
    }

    /**
     * Phan loai hoc luc dua tren diem.
     * 
     * @param score Diem trung binh
     * @return Xep loai hoc luc
     */
    public static String classifyGrade(double score) {
        if (score >= 9.0)
            return "Xuat sac";
        if (score >= 8.0)
            return "Gioi";
        if (score >= 7.0)
            return "Kha";
        if (score >= 5.5)
            return "Trung binh kha";
        if (score >= 5.0)
            return "Trung binh";
        if (score >= 4.0)
            return "Yeu";
        return "Kem";
    }

    /**
     * Dem so luong theo tung loai hoc luc.
     * 
     * @param grades Danh sach diem
     * @return Map voi key la loai hoc luc, value la so luong
     */
    public static Map<String, Integer> countByGradeClassification(List<Grade> grades) {
        Map<String, Integer> result = new HashMap<>();
        result.put("Xuat sac", 0);
        result.put("Gioi", 0);
        result.put("Kha", 0);
        result.put("Trung binh kha", 0);
        result.put("Trung binh", 0);
        result.put("Yeu", 0);
        result.put("Kem", 0);

        if (grades == null)
            return result;

        for (Grade g : grades) {
            String classification = classifyGrade(g.getTotal());
            result.put(classification, result.get(classification) + 1);
        }
        return result;
    }

    /**
     * Tim diem cao nhat.
     * 
     * @param grades Danh sach diem
     * @return Diem cao nhat, tra ve 0 neu danh sach rong
     */
    public static double findMaxGrade(List<Grade> grades) {
        if (grades == null || grades.isEmpty())
            return 0;
        double max = 0;
        for (Grade g : grades) {
            if (g.getTotal() > max) {
                max = g.getTotal();
            }
        }
        return max;
    }

    /**
     * Tim diem thap nhat.
     * 
     * @param grades Danh sach diem
     * @return Diem thap nhat, tra ve 0 neu danh sach rong
     */
    public static double findMinGrade(List<Grade> grades) {
        if (grades == null || grades.isEmpty())
            return 0;
        double min = 10;
        for (Grade g : grades) {
            if (g.getTotal() < min) {
                min = g.getTotal();
            }
        }
        return min;
    }

    /**
     * Hien thi bang thong ke diem.
     * 
     * @param grades Danh sach diem
     */
    public static void displayGradeStatistics(List<Grade> grades) {
        System.out.println("\n========== THONG KE DIEM ==========");
        System.out.println("Tong so bang diem: " + (grades != null ? grades.size() : 0));
        System.out.println("Diem trung binh: " + calculateAverageGrade(grades));
        System.out.println("Diem cao nhat: " + findMaxGrade(grades));
        System.out.println("Diem thap nhat: " + findMinGrade(grades));

        System.out.println("\n--- Phan loai hoc luc ---");
        Map<String, Integer> classification = countByGradeClassification(grades);
        System.out.println("Xuat sac: " + classification.get("Xuat sac"));
        System.out.println("Gioi: " + classification.get("Gioi"));
        System.out.println("Kha: " + classification.get("Kha"));
        System.out.println("Trung binh kha: " + classification.get("Trung binh kha"));
        System.out.println("Trung binh: " + classification.get("Trung binh"));
        System.out.println("Yeu: " + classification.get("Yeu"));
        System.out.println("Kem: " + classification.get("Kem"));
        System.out.println("====================================");
    }

    /**
     * Hien thi thong ke sinh vien theo lop.
     * 
     * @param students Danh sach sinh vien
     */
    public static void displayStudentsByClass(List<Student> students) {
        System.out.println("\n========== THONG KE SINH VIEN THEO LOP ==========");
        Map<String, Integer> counts = countStudentsByClass(students);

        if (counts.isEmpty()) {
            System.out.println("Khong co du lieu!");
            return;
        }

        System.out.println("| Ma lop     | So sinh vien |");
        System.out.println("------------------------------");
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            System.out.printf("| %-10s | %-12d |\n", entry.getKey(), entry.getValue());
        }
        System.out.println("=============================================");
    }

    /**
     * Hien thi thong ke diem danh.
     * 
     * @param attendances Danh sach diem danh
     */
    public static void displayAttendanceStatistics(List<Attendance> attendances) {
        System.out.println("\n========== THONG KE DIEM DANH ==========");
        if (attendances == null || attendances.isEmpty()) {
            System.out.println("Khong co du lieu!");
            return;
        }

        int present = 0, absent = 0, excused = 0;
        for (Attendance a : attendances) {
            switch (a.getStatus()) {
                case Attendance.STATUS_PRESENT:
                    present++;
                    break;
                case Attendance.STATUS_ABSENT:
                    absent++;
                    break;
                case Attendance.STATUS_EXCUSED:
                    excused++;
                    break;
            }
        }

        System.out.println("Tong so buoi: " + attendances.size());
        System.out.println("Co mat: " + present + " (" + (present * 100 / attendances.size()) + "%)");
        System.out.println("Vang: " + absent + " (" + (absent * 100 / attendances.size()) + "%)");
        System.out.println("Co phep: " + excused + " (" + (excused * 100 / attendances.size()) + "%)");
        System.out.println("Ty le chuyen can: " + calculateAttendanceRate(attendances) + "%");
        System.out.println("=========================================");
    }
}

package utils;

import models.*;
import java.io.*;
import java.util.*;

/**
 * Lop tien ich xuat bao cao.
 * Cung cap cac phuong thuc xuat du lieu ra file CSV.
 * 
 * @author StudentManagement Team
 * @version 1.0
 */
public class ReportUtils {

    private static final String REPORT_DIR = "reports";

    /**
     * Tao thu muc reports neu chua ton tai.
     */
    private static void ensureReportDir() {
        File dir = new File(REPORT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Xuat danh sach sinh vien ra file CSV.
     * 
     * @param students Danh sach sinh vien
     * @param filename Ten file (khong can duoi .csv)
     * @return true neu thanh cong, false neu that bai
     */
    public static boolean exportStudentsToCSV(List<Student> students, String filename) {
        ensureReportDir();
        String filepath = REPORT_DIR + "/" + filename + ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            // Header
            writer.println("Ma SV,Ho ten,Ngay sinh,Gioi tinh,Email,SDT,Ma lop");

            // Data
            for (Student s : students) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s\n",
                        s.getStudentID(), s.getFullName(), s.getDob(), s.getGender(),
                        s.getEmail(), s.getPhone(), s.getClassID());
            }

            System.out.println("-> Xuat thanh cong: " + filepath);
            return true;
        } catch (IOException e) {
            System.out.println("-> Loi khi xuat file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Xuat danh sach giang vien ra file CSV.
     * 
     * @param teachers Danh sach giang vien
     * @param filename Ten file
     * @return true neu thanh cong
     */
    public static boolean exportTeachersToCSV(List<Teacher> teachers, String filename) {
        ensureReportDir();
        String filepath = REPORT_DIR + "/" + filename + ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            writer.println("Ma GV,Ho ten,Khoa,Email,SDT");

            for (Teacher t : teachers) {
                writer.printf("%s,%s,%s,%s,%s\n",
                        t.getTeacherID(), t.getFullName(), t.getDepartment(),
                        t.getEmail(), t.getPhone());
            }

            System.out.println("-> Xuat thanh cong: " + filepath);
            return true;
        } catch (IOException e) {
            System.out.println("-> Loi khi xuat file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Xuat bang diem ra file CSV.
     * 
     * @param grades   Danh sach diem
     * @param filename Ten file
     * @return true neu thanh cong
     */
    public static boolean exportGradesToCSV(List<Grade> grades, String filename) {
        ensureReportDir();
        String filepath = REPORT_DIR + "/" + filename + ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            writer.println("Ma diem,Ma SV,Ma MH,Giua ky,Cuoi ky,Tong diem,Xep loai");

            for (Grade g : grades) {
                String classification = StatisticsUtils.classifyGrade(g.getTotal());
                writer.printf("%s,%s,%s,%.1f,%.1f,%.2f,%s\n",
                        g.getGradeID(), g.getStudentID(), g.getCourseID(),
                        g.getMidterm(), g.getFinalExam(), g.getTotal(), classification);
            }

            System.out.println("-> Xuat thanh cong: " + filepath);
            return true;
        } catch (IOException e) {
            System.out.println("-> Loi khi xuat file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Xuat bao cao diem danh ra file CSV.
     * 
     * @param attendances Danh sach diem danh
     * @param filename    Ten file
     * @return true neu thanh cong
     */
    public static boolean exportAttendanceToCSV(List<Attendance> attendances, String filename) {
        ensureReportDir();
        String filepath = REPORT_DIR + "/" + filename + ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            writer.println("Ma DD,Ma SV,Ma lop,Ngay,Trang thai");

            for (Attendance a : attendances) {
                writer.printf("%s,%s,%s,%s,%s\n",
                        a.getAttendanceID(), a.getStudentID(), a.getClassID(),
                        a.getDate(), a.getStatus());
            }

            System.out.println("-> Xuat thanh cong: " + filepath);
            return true;
        } catch (IOException e) {
            System.out.println("-> Loi khi xuat file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Xuat danh sach mon hoc ra file CSV.
     * 
     * @param courses  Danh sach mon hoc
     * @param filename Ten file
     * @return true neu thanh cong
     */
    public static boolean exportCoursesToCSV(List<Course> courses, String filename) {
        ensureReportDir();
        String filepath = REPORT_DIR + "/" + filename + ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            writer.println("Ma MH,Ten mon,So tin chi,Hoc ky,Ma GV");

            for (Course c : courses) {
                writer.printf("%s,%s,%d,%s,%s\n",
                        c.getCourseID(), c.getCourseName(), c.getCredits(),
                        c.getSemester(), c.getTeacherID());
            }

            System.out.println("-> Xuat thanh cong: " + filepath);
            return true;
        } catch (IOException e) {
            System.out.println("-> Loi khi xuat file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Xuat bao cao thong ke tong hop.
     * 
     * @param students    Danh sach sinh vien
     * @param grades      Danh sach diem
     * @param attendances Danh sach diem danh
     * @param filename    Ten file
     * @return true neu thanh cong
     */
    public static boolean exportSummaryReport(List<Student> students,
            List<Grade> grades,
            List<Attendance> attendances,
            String filename) {
        ensureReportDir();
        String filepath = REPORT_DIR + "/" + filename + ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            writer.println("BAO CAO TONG HOP HE THONG QUAN LY SINH VIEN");
            writer.println("-------------------------------------------");
            writer.println("");

            writer.println("1. THONG KE SINH VIEN");
            writer.println("Tong so sinh vien," + (students != null ? students.size() : 0));

            if (students != null) {
                Map<String, Integer> byClass = StatisticsUtils.countStudentsByClass(students);
                writer.println("So lop," + byClass.size());
            }

            writer.println("");
            writer.println("2. THONG KE DIEM");
            writer.println("Tong so bang diem," + (grades != null ? grades.size() : 0));
            writer.println("Diem trung binh," + StatisticsUtils.calculateAverageGrade(grades));
            writer.println("Diem cao nhat," + StatisticsUtils.findMaxGrade(grades));
            writer.println("Diem thap nhat," + StatisticsUtils.findMinGrade(grades));

            if (grades != null) {
                Map<String, Integer> classification = StatisticsUtils.countByGradeClassification(grades);
                writer.println("");
                writer.println("Phan loai,So luong");
                for (Map.Entry<String, Integer> entry : classification.entrySet()) {
                    writer.println(entry.getKey() + "," + entry.getValue());
                }
            }

            writer.println("");
            writer.println("3. THONG KE DIEM DANH");
            writer.println("Tong so buoi," + (attendances != null ? attendances.size() : 0));
            writer.println("Ty le chuyen can," + StatisticsUtils.calculateAttendanceRate(attendances) + "%");

            System.out.println("-> Xuat thanh cong: " + filepath);
            return true;
        } catch (IOException e) {
            System.out.println("-> Loi khi xuat file: " + e.getMessage());
            return false;
        }
    }
}

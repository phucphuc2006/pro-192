import java.util.Scanner;
import java.io.PrintStream;
import models.*;
import managers.*;

public class Main {
    // Khai bao cac manager
    private static StudentManager studentManager = new StudentManager();
    private static CourseManager courseManager = new CourseManager();
    private static TeacherManager teacherManager = new TeacherManager();
    private static ClassRoomManager classRoomManager = new ClassRoomManager();
    private static EnrollmentManager enrollmentManager = new EnrollmentManager();
    private static GradeManager gradeManager = new GradeManager();
    private static AttendanceManager attendanceManager = new AttendanceManager();
    private static DepartmentManager departmentManager = new DepartmentManager();
    private static SemesterManager semesterManager = new SemesterManager();
    private static UserAccountManager userManager = new UserAccountManager();

    private static Scanner scanner = new Scanner(System.in);
    private static UserAccount currentUser = null; // User dang dang nhap

    public static void main(String[] args) {
        // Thiet lap UTF-8 cho console Windows
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "chcp 65001").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Bo qua neu khong thiet lap duoc
        }

        // Tai du lieu tu file
        loadAllData();

        // Hien thi menu dang nhap truoc
        while (currentUser == null) {
            if (!showAuthMenu()) {
                // User chon thoat
                System.out.println("Tam biet!");
                scanner.close();
                System.exit(0);
            }
        }

        // Sau khi dang nhap thanh cong, hien thi menu chinh
        while (true) {
            showMainMenu();
            int choice = getIntInput("Chon chuc nang: ");

            switch (choice) {
                case 1:
                    studentMenu();
                    break;
                case 2:
                    teacherMenu();
                    break;
                case 3:
                    courseMenu();
                    break;
                case 4:
                    classRoomMenu();
                    break;
                case 5:
                    enrollmentMenu();
                    break;
                case 6:
                    gradeMenu();
                    break;
                case 7:
                    attendanceMenu();
                    break;
                case 8:
                    departmentMenu();
                    break;
                case 9:
                    semesterMenu();
                    break;
                case 10:
                    userAccountMenu();
                    break;
                case 11:
                    changePasswordMenu();
                    break;
                case 12:
                    // Dang xuat
                    System.out.println("-> Da dang xuat!");
                    currentUser = null;
                    while (currentUser == null) {
                        if (!showAuthMenu()) {
                            saveAllData();
                            System.out.println("Tam biet!");
                            scanner.close();
                            System.exit(0);
                        }
                    }
                    break;
                case 0:
                    saveAllData();
                    System.out.println("Dang thoat chuong trinh...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ==================== MENU DANG NHAP/DANG KY ====================
    private static boolean showAuthMenu() {
        System.out.println("\n+=============================================+");
        System.out.println("|     HE THONG QUAN LY SINH VIEN              |");
        System.out.println("+=============================================+");
        System.out.println("|  1. Dang nhap                               |");
        System.out.println("|  2. Dang ky tai khoan moi                   |");
        System.out.println("|  0. Thoat                                   |");
        System.out.println("+=============================================+");

        int choice = getIntInput("Chon: ");

        switch (choice) {
            case 1:
                return loginMenu();
            case 2:
                registerMenu();
                return true; // Tiep tuc vong lap
            case 0:
                return false; // Thoat
            default:
                System.out.println("Lua chon khong hop le!");
                return true;
        }
    }

    private static boolean loginMenu() {
        System.out.println("\n--- DANG NHAP ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Mat khau: ");
        String password = scanner.nextLine();

        currentUser = userManager.login(username, password);
        return true;
    }

    private static void registerMenu() {
        System.out.println("\n--- DANG KY TAI KHOAN MOI ---");
        System.out.println("Yeu cau mat khau:");
        System.out.println("  - Toi thieu 8 ky tu");
        System.out.println("  - Co it nhat 1 chu hoa (A-Z)");
        System.out.println("  - Co it nhat 1 chu thuong (a-z)");
        System.out.println("  - Co it nhat 1 chu so (0-9)");
        System.out.println("  - Co it nhat 1 ky tu dac biet (!@#$%...)");
        System.out.println("  - Khong duoc trung voi username");
        System.out.println();

        System.out.print("Username (chi chu, so, gach duoi): ");
        String username = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Mat khau: ");
        String password = scanner.nextLine();
        System.out.print("Nhap lai mat khau: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("-> Loi: Mat khau xac nhan khong khop!");
            return;
        }

        UserAccount newUser = userManager.register(username, email, password);
        if (newUser != null) {
            System.out.println("-> Hay dang nhap de su dung he thong.");
        }
    }

    private static void changePasswordMenu() {
        System.out.println("\n--- DOI MAT KHAU ---");
        System.out.print("Nhap mat khau cu: ");
        String oldPassword = scanner.nextLine();
        System.out.print("Nhap mat khau moi: ");
        String newPassword = scanner.nextLine();
        System.out.print("Nhap lai mat khau moi: ");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("-> Loi: Mat khau xac nhan khong khop!");
            return;
        }

        userManager.changePassword(currentUser.getUserID(), oldPassword, newPassword);
    }

    // ==================== MENU CHINH ====================
    private static void showMainMenu() {
        System.out.println("\n+=============================================+");
        System.out.println("|     HE THONG QUAN LY SINH VIEN              |");
        System.out.println("|     Xin chao: " + String.format("%-29s", currentUser.getUsername()) + "|");
        System.out.println("+=============================================+");
        System.out.println("|  1. Quan ly sinh vien                       |");
        System.out.println("|  2. Quan ly giang vien                      |");
        System.out.println("|  3. Quan ly mon hoc                         |");
        System.out.println("|  4. Quan ly lop hoc                         |");
        System.out.println("|  5. Quan ly dang ky mon                     |");
        System.out.println("|  6. Quan ly diem so                         |");
        System.out.println("|  7. Quan ly diem danh                       |");
        System.out.println("|  8. Quan ly khoa                            |");
        System.out.println("|  9. Quan ly hoc ky                          |");
        System.out.println("|  10. Quan ly tai khoan                      |");
        System.out.println("|  11. Doi mat khau                           |");
        System.out.println("|  12. Dang xuat                              |");
        System.out.println("|  0. Luu va thoat                            |");
        System.out.println("+=============================================+");
    }

    // ==================== QUAN LY SINH VIEN ====================
    private static void studentMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY SINH VIEN ---");
            System.out.println("1. Them sinh vien");
            System.out.println("2. Sua thong tin sinh vien");
            System.out.println("3. Xoa sinh vien");
            System.out.println("4. Tim kiem theo ten");
            System.out.println("5. Hien thi danh sach");
            System.out.println("0. Quay lai");

            int choice = getIntInput("Chon: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhap ma sinh vien: ");
                    String id = scanner.nextLine();
                    if (studentManager.findStudentById(id) != null) {
                        System.out.println("Ma SV da ton tai!");
                        break;
                    }
                    System.out.print("Nhap ho ten: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhap ngay sinh (dd/mm/yyyy): ");
                    String dob = scanner.nextLine();
                    System.out.print("Nhap gioi tinh (Nam/Nu): ");
                    String gender = scanner.nextLine();
                    System.out.print("Nhap email: ");
                    String email = scanner.nextLine();
                    System.out.print("Nhap so dien thoai: ");
                    String phone = scanner.nextLine();
                    System.out.print("Nhap ma lop: ");
                    String classID = scanner.nextLine();
                    studentManager.addStudent(new Student(id, name, dob, gender, email, phone, classID));
                    break;
                case 2:
                    System.out.print("Nhap ma SV can sua: ");
                    String editId = scanner.nextLine();
                    if (studentManager.findStudentById(editId) == null) {
                        System.out.println("Khong tim thay sinh vien!");
                        break;
                    }
                    System.out.print("Nhap ho ten moi: ");
                    String newName = scanner.nextLine();
                    System.out.print("Nhap ngay sinh moi: ");
                    String newDob = scanner.nextLine();
                    System.out.print("Nhap gioi tinh moi: ");
                    String newGender = scanner.nextLine();
                    System.out.print("Nhap email moi: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Nhap SDT moi: ");
                    String newPhone = scanner.nextLine();
                    System.out.print("Nhap ma lop moi: ");
                    String newClassID = scanner.nextLine();
                    studentManager.updateStudent(editId, newName, newDob, newGender, newEmail, newPhone, newClassID);
                    break;
                case 3:
                    System.out.print("Nhap ma SV can xoa: ");
                    studentManager.deleteStudent(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ten can tim: ");
                    studentManager.searchByName(scanner.nextLine());
                    break;
                case 5:
                    studentManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ==================== QUAN LY GIANG VIEN ====================
    private static void teacherMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY GIANG VIEN ---");
            System.out.println("1. Them giang vien");
            System.out.println("2. Sua thong tin");
            System.out.println("3. Xoa giang vien");
            System.out.println("4. Tim kiem theo ten");
            System.out.println("5. Hien thi danh sach");
            System.out.println("0. Quay lai");

            int choice = getIntInput("Chon: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhap ma GV: ");
                    String tid = scanner.nextLine();
                    if (teacherManager.findTeacherById(tid) != null) {
                        System.out.println("Ma GV da ton tai!");
                        break;
                    }
                    System.out.print("Nhap ho ten: ");
                    String tname = scanner.nextLine();
                    System.out.print("Nhap khoa: ");
                    String dept = scanner.nextLine();
                    System.out.print("Nhap email: ");
                    String temail = scanner.nextLine();
                    System.out.print("Nhap SDT: ");
                    String tphone = scanner.nextLine();
                    teacherManager.addTeacher(new Teacher(tid, tname, dept, temail, tphone));
                    break;
                case 2:
                    System.out.print("Nhap ma GV can sua: ");
                    String editTid = scanner.nextLine();
                    if (teacherManager.findTeacherById(editTid) == null) {
                        System.out.println("Khong tim thay giang vien!");
                        break;
                    }
                    System.out.print("Nhap ho ten moi: ");
                    String newTname = scanner.nextLine();
                    System.out.print("Nhap khoa moi: ");
                    String newDept = scanner.nextLine();
                    System.out.print("Nhap email moi: ");
                    String newTemail = scanner.nextLine();
                    System.out.print("Nhap SDT moi: ");
                    String newTphone = scanner.nextLine();
                    teacherManager.updateTeacher(editTid, newTname, newDept, newTemail, newTphone);
                    break;
                case 3:
                    System.out.print("Nhap ma GV can xoa: ");
                    teacherManager.deleteTeacher(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ten can tim: ");
                    teacherManager.searchByName(scanner.nextLine());
                    break;
                case 5:
                    teacherManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ==================== QUAN LY MON HOC ====================
    private static void courseMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY MON HOC ---");
            System.out.println("1. Them mon hoc");
            System.out.println("2. Sua thong tin");
            System.out.println("3. Xoa mon hoc");
            System.out.println("4. Tim kiem theo ten");
            System.out.println("5. Hien thi danh sach");
            System.out.println("0. Quay lai");

            int choice = getIntInput("Chon: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhap ma MH: ");
                    String cid = scanner.nextLine();
                    if (courseManager.findCourseById(cid) != null) {
                        System.out.println("Ma MH da ton tai!");
                        break;
                    }
                    System.out.print("Nhap ten mon: ");
                    String cname = scanner.nextLine();
                    int credits = getIntInput("Nhap so tin chi: ");
                    System.out.print("Nhap hoc ky: ");
                    String csem = scanner.nextLine();
                    System.out.print("Nhap ma GV phu trach: ");
                    String cteacher = scanner.nextLine();
                    courseManager.addCourse(new Course(cid, cname, credits, csem, cteacher));
                    break;
                case 2:
                    System.out.print("Nhap ma MH can sua: ");
                    String editCid = scanner.nextLine();
                    if (courseManager.findCourseById(editCid) == null) {
                        System.out.println("Khong tim thay mon hoc!");
                        break;
                    }
                    System.out.print("Nhap ten moi: ");
                    String newCname = scanner.nextLine();
                    int newCredits = getIntInput("Nhap so tin chi moi: ");
                    System.out.print("Nhap hoc ky moi: ");
                    String newCsem = scanner.nextLine();
                    System.out.print("Nhap ma GV moi: ");
                    String newCteacher = scanner.nextLine();
                    courseManager.updateCourse(editCid, newCname, newCredits, newCsem, newCteacher);
                    break;
                case 3:
                    System.out.print("Nhap ma MH can xoa: ");
                    courseManager.deleteCourse(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ten can tim: ");
                    courseManager.searchByName(scanner.nextLine());
                    break;
                case 5:
                    courseManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ==================== QUAN LY LOP HOC ====================
    private static void classRoomMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY LOP HOC ---");
            System.out.println("1. Them lop hoc");
            System.out.println("2. Sua thong tin");
            System.out.println("3. Xoa lop hoc");
            System.out.println("4. Tim kiem theo ten");
            System.out.println("5. Hien thi danh sach");
            System.out.println("0. Quay lai");

            int choice = getIntInput("Chon: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhap ma lop: ");
                    String clid = scanner.nextLine();
                    if (classRoomManager.findClassRoomById(clid) != null) {
                        System.out.println("Ma lop da ton tai!");
                        break;
                    }
                    System.out.print("Nhap ten lop: ");
                    String clname = scanner.nextLine();
                    System.out.print("Nhap ma GV: ");
                    String clteacher = scanner.nextLine();
                    System.out.print("Nhap ma MH: ");
                    String clcourse = scanner.nextLine();
                    classRoomManager.addClassRoom(new ClassRoom(clid, clname, clteacher, clcourse));
                    break;
                case 2:
                    System.out.print("Nhap ma lop can sua: ");
                    String editClid = scanner.nextLine();
                    if (classRoomManager.findClassRoomById(editClid) == null) {
                        System.out.println("Khong tim thay lop hoc!");
                        break;
                    }
                    System.out.print("Nhap ten moi: ");
                    String newClname = scanner.nextLine();
                    System.out.print("Nhap ma GV moi: ");
                    String newClteacher = scanner.nextLine();
                    System.out.print("Nhap ma MH moi: ");
                    String newClcourse = scanner.nextLine();
                    classRoomManager.updateClassRoom(editClid, newClname, newClteacher, newClcourse);
                    break;
                case 3:
                    System.out.print("Nhap ma lop can xoa: ");
                    classRoomManager.deleteClassRoom(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ten can tim: ");
                    classRoomManager.searchByName(scanner.nextLine());
                    break;
                case 5:
                    classRoomManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ==================== QUAN LY DANG KY MON ====================
    private static void enrollmentMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY DANG KY MON HOC ---");
            System.out.println("1. Dang ky mon hoc");
            System.out.println("2. Sua dang ky");
            System.out.println("3. Huy dang ky");
            System.out.println("4. Xem mon da dang ky cua SV");
            System.out.println("5. Hien thi tat ca");
            System.out.println("0. Quay lai");

            int choice = getIntInput("Chon: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhap ma dang ky: ");
                    String eid = scanner.nextLine();
                    if (enrollmentManager.findEnrollmentById(eid) != null) {
                        System.out.println("Ma dang ky da ton tai!");
                        break;
                    }
                    System.out.print("Nhap ma SV: ");
                    String esid = scanner.nextLine();
                    System.out.print("Nhap ma MH: ");
                    String ecid = scanner.nextLine();
                    System.out.print("Nhap hoc ky: ");
                    String esem = scanner.nextLine();
                    enrollmentManager.addEnrollment(new Enrollment(eid, esid, ecid, esem));
                    break;
                case 2:
                    System.out.print("Nhap ma DK can sua: ");
                    String editEid = scanner.nextLine();
                    if (enrollmentManager.findEnrollmentById(editEid) == null) {
                        System.out.println("Khong tim thay dang ky!");
                        break;
                    }
                    System.out.print("Nhap ma SV moi: ");
                    String newEsid = scanner.nextLine();
                    System.out.print("Nhap ma MH moi: ");
                    String newEcid = scanner.nextLine();
                    System.out.print("Nhap hoc ky moi: ");
                    String newEsem = scanner.nextLine();
                    enrollmentManager.updateEnrollment(editEid, newEsid, newEcid, newEsem);
                    break;
                case 3:
                    System.out.print("Nhap ma DK can huy: ");
                    enrollmentManager.deleteEnrollment(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ma SV: ");
                    enrollmentManager.searchByStudentId(scanner.nextLine());
                    break;
                case 5:
                    enrollmentManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ==================== QUAN LY DIEM SO ====================
    private static void gradeMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY DIEM SO ---");
            System.out.println("1. Them diem");
            System.out.println("2. Sua diem");
            System.out.println("3. Xoa diem");
            System.out.println("4. Xem diem cua SV");
            System.out.println("5. Hien thi tat ca");
            System.out.println("0. Quay lai");

            int choice = getIntInput("Chon: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhap ma diem: ");
                    String gid = scanner.nextLine();
                    if (gradeManager.findGradeById(gid) != null) {
                        System.out.println("Ma diem da ton tai!");
                        break;
                    }
                    System.out.print("Nhap ma SV: ");
                    String gsid = scanner.nextLine();
                    System.out.print("Nhap ma MH: ");
                    String gcid = scanner.nextLine();
                    double midterm = getDoubleInput("Nhap diem giua ky: ");
                    double finalExam = getDoubleInput("Nhap diem cuoi ky: ");
                    gradeManager.addGrade(new Grade(gid, gsid, gcid, midterm, finalExam));
                    break;
                case 2:
                    System.out.print("Nhap ma diem can sua: ");
                    String editGid = scanner.nextLine();
                    if (gradeManager.findGradeById(editGid) == null) {
                        System.out.println("Khong tim thay diem!");
                        break;
                    }
                    double newMidterm = getDoubleInput("Nhap diem giua ky moi: ");
                    double newFinal = getDoubleInput("Nhap diem cuoi ky moi: ");
                    gradeManager.updateGrade(editGid, newMidterm, newFinal);
                    break;
                case 3:
                    System.out.print("Nhap ma diem can xoa: ");
                    gradeManager.deleteGrade(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ma SV: ");
                    gradeManager.searchByStudentId(scanner.nextLine());
                    break;
                case 5:
                    gradeManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ==================== QUAN LY DIEM DANH ====================
    private static void attendanceMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY DIEM DANH ---");
            System.out.println("1. Them diem danh");
            System.out.println("2. Sua trang thai");
            System.out.println("3. Xoa diem danh");
            System.out.println("4. Xem lich su diem danh cua SV");
            System.out.println("5. Hien thi tat ca");
            System.out.println("0. Quay lai");

            int choice = getIntInput("Chon: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhap ma diem danh: ");
                    String aid = scanner.nextLine();
                    if (attendanceManager.findAttendanceById(aid) != null) {
                        System.out.println("Ma diem danh da ton tai!");
                        break;
                    }
                    System.out.print("Nhap ma SV: ");
                    String asid = scanner.nextLine();
                    System.out.print("Nhap ma lop: ");
                    String aclid = scanner.nextLine();
                    System.out.print("Nhap ngay (dd/mm/yyyy): ");
                    String adate = scanner.nextLine();
                    System.out.println("Trang thai: 1-Co mat, 2-Vang, 3-Co phep");
                    int statusChoice = getIntInput("Chon trang thai: ");
                    String status = statusChoice == 1 ? Attendance.STATUS_PRESENT
                            : statusChoice == 2 ? Attendance.STATUS_ABSENT : Attendance.STATUS_EXCUSED;
                    attendanceManager.addAttendance(new Attendance(aid, asid, aclid, adate, status));
                    break;
                case 2:
                    System.out.print("Nhap ma diem danh can sua: ");
                    String editAid = scanner.nextLine();
                    if (attendanceManager.findAttendanceById(editAid) == null) {
                        System.out.println("Khong tim thay diem danh!");
                        break;
                    }
                    System.out.println("Trang thai moi: 1-Co mat, 2-Vang, 3-Co phep");
                    int newStatusChoice = getIntInput("Chon: ");
                    String newStatus = newStatusChoice == 1 ? Attendance.STATUS_PRESENT
                            : newStatusChoice == 2 ? Attendance.STATUS_ABSENT : Attendance.STATUS_EXCUSED;
                    attendanceManager.updateAttendance(editAid, newStatus);
                    break;
                case 3:
                    System.out.print("Nhap ma diem danh can xoa: ");
                    attendanceManager.deleteAttendance(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ma SV: ");
                    attendanceManager.searchByStudentId(scanner.nextLine());
                    break;
                case 5:
                    attendanceManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ==================== QUAN LY KHOA ====================
    private static void departmentMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY KHOA ---");
            System.out.println("1. Them khoa");
            System.out.println("2. Sua thong tin");
            System.out.println("3. Xoa khoa");
            System.out.println("4. Tim kiem theo ten");
            System.out.println("5. Hien thi danh sach");
            System.out.println("0. Quay lai");

            int choice = getIntInput("Chon: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhap ma khoa: ");
                    String did = scanner.nextLine();
                    if (departmentManager.findDepartmentById(did) != null) {
                        System.out.println("Ma khoa da ton tai!");
                        break;
                    }
                    System.out.print("Nhap ten khoa: ");
                    String dname = scanner.nextLine();
                    int fcount = getIntInput("Nhap so giang vien: ");
                    departmentManager.addDepartment(new Department(did, dname, fcount));
                    break;
                case 2:
                    System.out.print("Nhap ma khoa can sua: ");
                    String editDid = scanner.nextLine();
                    if (departmentManager.findDepartmentById(editDid) == null) {
                        System.out.println("Khong tim thay khoa!");
                        break;
                    }
                    System.out.print("Nhap ten moi: ");
                    String newDname = scanner.nextLine();
                    int newFcount = getIntInput("Nhap so GV moi: ");
                    departmentManager.updateDepartment(editDid, newDname, newFcount);
                    break;
                case 3:
                    System.out.print("Nhap ma khoa can xoa: ");
                    departmentManager.deleteDepartment(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ten can tim: ");
                    departmentManager.searchByName(scanner.nextLine());
                    break;
                case 5:
                    departmentManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ==================== QUAN LY HOC KY ====================
    private static void semesterMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY HOC KY ---");
            System.out.println("1. Them hoc ky");
            System.out.println("2. Sua thong tin");
            System.out.println("3. Xoa hoc ky");
            System.out.println("4. Hien thi danh sach");
            System.out.println("0. Quay lai");

            int choice = getIntInput("Chon: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhap ma hoc ky: ");
                    String sid = scanner.nextLine();
                    if (semesterManager.findSemesterById(sid) != null) {
                        System.out.println("Ma hoc ky da ton tai!");
                        break;
                    }
                    System.out.print("Nhap ten hoc ky (VD: HK1-2024): ");
                    String sname = scanner.nextLine();
                    System.out.print("Nhap ngay bat dau: ");
                    String sstart = scanner.nextLine();
                    System.out.print("Nhap ngay ket thuc: ");
                    String send = scanner.nextLine();
                    semesterManager.addSemester(new Semester(sid, sname, sstart, send));
                    break;
                case 2:
                    System.out.print("Nhap ma HK can sua: ");
                    String editSid = scanner.nextLine();
                    if (semesterManager.findSemesterById(editSid) == null) {
                        System.out.println("Khong tim thay hoc ky!");
                        break;
                    }
                    System.out.print("Nhap ten moi: ");
                    String newSname = scanner.nextLine();
                    System.out.print("Nhap ngay bat dau moi: ");
                    String newSstart = scanner.nextLine();
                    System.out.print("Nhap ngay ket thuc moi: ");
                    String newSend = scanner.nextLine();
                    semesterManager.updateSemester(editSid, newSname, newSstart, newSend);
                    break;
                case 3:
                    System.out.print("Nhap ma HK can xoa: ");
                    semesterManager.deleteSemester(scanner.nextLine());
                    break;
                case 4:
                    semesterManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ==================== QUAN LY TAI KHOAN ====================
    private static void userAccountMenu() {
        while (true) {
            System.out.println("\n--- QUAN LY TAI KHOAN ---");
            System.out.println("1. Hien thi danh sach tai khoan");
            System.out.println("2. Mo khoa tai khoan");
            System.out.println("3. Xoa tai khoan");
            System.out.println("0. Quay lai");

            int choice = getIntInput("Chon: ");
            switch (choice) {
                case 1:
                    userManager.displayAll();
                    break;
                case 2:
                    System.out.print("Nhap ma TK can mo khoa: ");
                    userManager.unlockAccount(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Nhap ma TK can xoa: ");
                    String delId = scanner.nextLine();
                    if (delId.equals(currentUser.getUserID())) {
                        System.out.println("Khong the xoa tai khoan dang dang nhap!");
                        break;
                    }
                    userManager.deleteUser(delId);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ==================== HELPER METHODS ====================
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so nguyen!");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so!");
            }
        }
    }

    private static void loadAllData() {
        System.out.println("Dang tai du lieu...");
        studentManager.loadFromFile();
        courseManager.loadFromFile();
        teacherManager.loadFromFile();
        classRoomManager.loadFromFile();
        enrollmentManager.loadFromFile();
        gradeManager.loadFromFile();
        attendanceManager.loadFromFile();
        departmentManager.loadFromFile();
        semesterManager.loadFromFile();
        userManager.loadFromFile();
        System.out.println("-> Tai du lieu hoan tat!");
    }

    private static void saveAllData() {
        System.out.println("Dang luu du lieu...");
        studentManager.saveToFile();
        courseManager.saveToFile();
        teacherManager.saveToFile();
        classRoomManager.saveToFile();
        enrollmentManager.saveToFile();
        gradeManager.saveToFile();
        attendanceManager.saveToFile();
        departmentManager.saveToFile();
        semesterManager.saveToFile();
        userManager.saveToFile();
        System.out.println("-> Luu du lieu hoan tat!");
    }
}
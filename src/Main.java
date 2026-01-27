import java.util.Scanner;
import java.io.PrintStream;
import models.*;
import managers.*;

public class Main {
    // Khai báo các manager
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

    public static void main(String[] args) {
        // Thiết lập UTF-8 cho console Windows
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            // Chạy lệnh chcp 65001 để hỗ trợ Unicode trên Windows
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "chcp 65001").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Bỏ qua nếu không thiết lập được
        }

        // Tải dữ liệu từ file
        loadAllData();

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

    // ==================== MENU CHÍNH ====================
    private static void showMainMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║     HỆ THỐNG QUẢN LÝ SINH VIÊN             ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  1. Quản lý sinh viên                      ║");
        System.out.println("║  2. Quản lý giảng viên                     ║");
        System.out.println("║  3. Quản lý môn học                        ║");
        System.out.println("║  4. Quản lý lớp học                        ║");
        System.out.println("║  5. Quản lý đăng ký môn                    ║");
        System.out.println("║  6. Quản lý điểm số                        ║");
        System.out.println("║  7. Quản lý điểm danh                      ║");
        System.out.println("║  8. Quản lý khoa                           ║");
        System.out.println("║  9. Quản lý học kỳ                         ║");
        System.out.println("║  10. Quản lý tài khoản                     ║");
        System.out.println("║  0. Lưu và thoát                           ║");
        System.out.println("╚════════════════════════════════════════════╝");
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

    // ==================== QUẢN LÝ GIẢNG VIÊN ====================
    private static void teacherMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ GIẢNG VIÊN ---");
            System.out.println("1. Thêm giảng viên");
            System.out.println("2. Sửa thông tin");
            System.out.println("3. Xóa giảng viên");
            System.out.println("4. Tìm kiếm theo tên");
            System.out.println("5. Hiển thị danh sách");
            System.out.println("0. Quay lại");

            int choice = getIntInput("Chọn: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhập mã GV: ");
                    String tid = scanner.nextLine();
                    if (teacherManager.findTeacherById(tid) != null) {
                        System.out.println("Mã GV đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập họ tên: ");
                    String tname = scanner.nextLine();
                    System.out.print("Nhập khoa: ");
                    String dept = scanner.nextLine();
                    System.out.print("Nhập email: ");
                    String temail = scanner.nextLine();
                    System.out.print("Nhập SĐT: ");
                    String tphone = scanner.nextLine();
                    teacherManager.addTeacher(new Teacher(tid, tname, dept, temail, tphone));
                    break;
                case 2:
                    System.out.print("Nhập mã GV cần sửa: ");
                    String editTid = scanner.nextLine();
                    if (teacherManager.findTeacherById(editTid) == null) {
                        System.out.println("Không tìm thấy giảng viên!");
                        break;
                    }
                    System.out.print("Nhập họ tên mới: ");
                    String newTname = scanner.nextLine();
                    System.out.print("Nhập khoa mới: ");
                    String newDept = scanner.nextLine();
                    System.out.print("Nhập email mới: ");
                    String newTemail = scanner.nextLine();
                    System.out.print("Nhập SĐT mới: ");
                    String newTphone = scanner.nextLine();
                    teacherManager.updateTeacher(editTid, newTname, newDept, newTemail, newTphone);
                    break;
                case 3:
                    System.out.print("Nhập mã GV cần xóa: ");
                    teacherManager.deleteTeacher(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhập tên cần tìm: ");
                    teacherManager.searchByName(scanner.nextLine());
                    break;
                case 5:
                    teacherManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ==================== QUẢN LÝ MÔN HỌC ====================
    private static void courseMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ MÔN HỌC ---");
            System.out.println("1. Thêm môn học");
            System.out.println("2. Sửa thông tin");
            System.out.println("3. Xóa môn học");
            System.out.println("4. Tìm kiếm theo tên");
            System.out.println("5. Hiển thị danh sách");
            System.out.println("0. Quay lại");

            int choice = getIntInput("Chọn: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhập mã MH: ");
                    String cid = scanner.nextLine();
                    if (courseManager.findCourseById(cid) != null) {
                        System.out.println("Mã MH đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập tên môn: ");
                    String cname = scanner.nextLine();
                    int credits = getIntInput("Nhập số tín chỉ: ");
                    System.out.print("Nhập học kỳ: ");
                    String csem = scanner.nextLine();
                    System.out.print("Nhập mã GV phụ trách: ");
                    String cteacher = scanner.nextLine();
                    courseManager.addCourse(new Course(cid, cname, credits, csem, cteacher));
                    break;
                case 2:
                    System.out.print("Nhập mã MH cần sửa: ");
                    String editCid = scanner.nextLine();
                    if (courseManager.findCourseById(editCid) == null) {
                        System.out.println("Không tìm thấy môn học!");
                        break;
                    }
                    System.out.print("Nhập tên mới: ");
                    String newCname = scanner.nextLine();
                    int newCredits = getIntInput("Nhập số tín chỉ mới: ");
                    System.out.print("Nhập học kỳ mới: ");
                    String newCsem = scanner.nextLine();
                    System.out.print("Nhập mã GV mới: ");
                    String newCteacher = scanner.nextLine();
                    courseManager.updateCourse(editCid, newCname, newCredits, newCsem, newCteacher);
                    break;
                case 3:
                    System.out.print("Nhập mã MH cần xóa: ");
                    courseManager.deleteCourse(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhập tên cần tìm: ");
                    courseManager.searchByName(scanner.nextLine());
                    break;
                case 5:
                    courseManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ==================== QUẢN LÝ LỚP HỌC ====================
    private static void classRoomMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ LỚP HỌC ---");
            System.out.println("1. Thêm lớp học");
            System.out.println("2. Sửa thông tin");
            System.out.println("3. Xóa lớp học");
            System.out.println("4. Tìm kiếm theo tên");
            System.out.println("5. Hiển thị danh sách");
            System.out.println("0. Quay lại");

            int choice = getIntInput("Chọn: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhập mã lớp: ");
                    String clid = scanner.nextLine();
                    if (classRoomManager.findClassRoomById(clid) != null) {
                        System.out.println("Mã lớp đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập tên lớp: ");
                    String clname = scanner.nextLine();
                    System.out.print("Nhập mã GV: ");
                    String clteacher = scanner.nextLine();
                    System.out.print("Nhập mã MH: ");
                    String clcourse = scanner.nextLine();
                    classRoomManager.addClassRoom(new ClassRoom(clid, clname, clteacher, clcourse));
                    break;
                case 2:
                    System.out.print("Nhập mã lớp cần sửa: ");
                    String editClid = scanner.nextLine();
                    if (classRoomManager.findClassRoomById(editClid) == null) {
                        System.out.println("Không tìm thấy lớp học!");
                        break;
                    }
                    System.out.print("Nhập tên mới: ");
                    String newClname = scanner.nextLine();
                    System.out.print("Nhập mã GV mới: ");
                    String newClteacher = scanner.nextLine();
                    System.out.print("Nhập mã MH mới: ");
                    String newClcourse = scanner.nextLine();
                    classRoomManager.updateClassRoom(editClid, newClname, newClteacher, newClcourse);
                    break;
                case 3:
                    System.out.print("Nhập mã lớp cần xóa: ");
                    classRoomManager.deleteClassRoom(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhập tên cần tìm: ");
                    classRoomManager.searchByName(scanner.nextLine());
                    break;
                case 5:
                    classRoomManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ==================== QUẢN LÝ ĐĂNG KÝ MÔN ====================
    private static void enrollmentMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ ĐĂNG KÝ MÔN HỌC ---");
            System.out.println("1. Đăng ký môn học");
            System.out.println("2. Sửa đăng ký");
            System.out.println("3. Hủy đăng ký");
            System.out.println("4. Xem môn đã đăng ký của SV");
            System.out.println("5. Hiển thị tất cả");
            System.out.println("0. Quay lại");

            int choice = getIntInput("Chọn: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhập mã đăng ký: ");
                    String eid = scanner.nextLine();
                    if (enrollmentManager.findEnrollmentById(eid) != null) {
                        System.out.println("Mã đăng ký đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập mã SV: ");
                    String esid = scanner.nextLine();
                    System.out.print("Nhập mã MH: ");
                    String ecid = scanner.nextLine();
                    System.out.print("Nhập học kỳ: ");
                    String esem = scanner.nextLine();
                    enrollmentManager.addEnrollment(new Enrollment(eid, esid, ecid, esem));
                    break;
                case 2:
                    System.out.print("Nhập mã ĐK cần sửa: ");
                    String editEid = scanner.nextLine();
                    if (enrollmentManager.findEnrollmentById(editEid) == null) {
                        System.out.println("Không tìm thấy đăng ký!");
                        break;
                    }
                    System.out.print("Nhập mã SV mới: ");
                    String newEsid = scanner.nextLine();
                    System.out.print("Nhập mã MH mới: ");
                    String newEcid = scanner.nextLine();
                    System.out.print("Nhập học kỳ mới: ");
                    String newEsem = scanner.nextLine();
                    enrollmentManager.updateEnrollment(editEid, newEsid, newEcid, newEsem);
                    break;
                case 3:
                    System.out.print("Nhập mã ĐK cần hủy: ");
                    enrollmentManager.deleteEnrollment(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhập mã SV: ");
                    enrollmentManager.searchByStudentId(scanner.nextLine());
                    break;
                case 5:
                    enrollmentManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ==================== QUẢN LÝ ĐIỂM SỐ ====================
    private static void gradeMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ ĐIỂM SỐ ---");
            System.out.println("1. Thêm điểm");
            System.out.println("2. Sửa điểm");
            System.out.println("3. Xóa điểm");
            System.out.println("4. Xem điểm của SV");
            System.out.println("5. Hiển thị tất cả");
            System.out.println("0. Quay lại");

            int choice = getIntInput("Chọn: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhập mã điểm: ");
                    String gid = scanner.nextLine();
                    if (gradeManager.findGradeById(gid) != null) {
                        System.out.println("Mã điểm đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập mã SV: ");
                    String gsid = scanner.nextLine();
                    System.out.print("Nhập mã MH: ");
                    String gcid = scanner.nextLine();
                    double midterm = getDoubleInput("Nhập điểm giữa kỳ: ");
                    double finalExam = getDoubleInput("Nhập điểm cuối kỳ: ");
                    gradeManager.addGrade(new Grade(gid, gsid, gcid, midterm, finalExam));
                    break;
                case 2:
                    System.out.print("Nhập mã điểm cần sửa: ");
                    String editGid = scanner.nextLine();
                    if (gradeManager.findGradeById(editGid) == null) {
                        System.out.println("Không tìm thấy điểm!");
                        break;
                    }
                    double newMidterm = getDoubleInput("Nhập điểm giữa kỳ mới: ");
                    double newFinal = getDoubleInput("Nhập điểm cuối kỳ mới: ");
                    gradeManager.updateGrade(editGid, newMidterm, newFinal);
                    break;
                case 3:
                    System.out.print("Nhập mã điểm cần xóa: ");
                    gradeManager.deleteGrade(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhập mã SV: ");
                    gradeManager.searchByStudentId(scanner.nextLine());
                    break;
                case 5:
                    gradeManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ==================== QUẢN LÝ ĐIỂM DANH ====================
    private static void attendanceMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ ĐIỂM DANH ---");
            System.out.println("1. Thêm điểm danh");
            System.out.println("2. Sửa trạng thái");
            System.out.println("3. Xóa điểm danh");
            System.out.println("4. Xem lịch sử điểm danh của SV");
            System.out.println("5. Hiển thị tất cả");
            System.out.println("0. Quay lại");

            int choice = getIntInput("Chọn: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhập mã điểm danh: ");
                    String aid = scanner.nextLine();
                    if (attendanceManager.findAttendanceById(aid) != null) {
                        System.out.println("Mã điểm danh đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập mã SV: ");
                    String asid = scanner.nextLine();
                    System.out.print("Nhập mã lớp: ");
                    String aclid = scanner.nextLine();
                    System.out.print("Nhập ngày (dd/mm/yyyy): ");
                    String adate = scanner.nextLine();
                    System.out.println("Trạng thái: 1-Có mặt, 2-Vắng, 3-Có phép");
                    int statusChoice = getIntInput("Chọn trạng thái: ");
                    String status = statusChoice == 1 ? Attendance.STATUS_PRESENT
                            : statusChoice == 2 ? Attendance.STATUS_ABSENT : Attendance.STATUS_EXCUSED;
                    attendanceManager.addAttendance(new Attendance(aid, asid, aclid, adate, status));
                    break;
                case 2:
                    System.out.print("Nhập mã điểm danh cần sửa: ");
                    String editAid = scanner.nextLine();
                    if (attendanceManager.findAttendanceById(editAid) == null) {
                        System.out.println("Không tìm thấy điểm danh!");
                        break;
                    }
                    System.out.println("Trạng thái mới: 1-Có mặt, 2-Vắng, 3-Có phép");
                    int newStatusChoice = getIntInput("Chọn: ");
                    String newStatus = newStatusChoice == 1 ? Attendance.STATUS_PRESENT
                            : newStatusChoice == 2 ? Attendance.STATUS_ABSENT : Attendance.STATUS_EXCUSED;
                    attendanceManager.updateAttendance(editAid, newStatus);
                    break;
                case 3:
                    System.out.print("Nhập mã điểm danh cần xóa: ");
                    attendanceManager.deleteAttendance(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhập mã SV: ");
                    attendanceManager.searchByStudentId(scanner.nextLine());
                    break;
                case 5:
                    attendanceManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ==================== QUẢN LÝ KHOA ====================
    private static void departmentMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ KHOA ---");
            System.out.println("1. Thêm khoa");
            System.out.println("2. Sửa thông tin");
            System.out.println("3. Xóa khoa");
            System.out.println("4. Tìm kiếm theo tên");
            System.out.println("5. Hiển thị danh sách");
            System.out.println("0. Quay lại");

            int choice = getIntInput("Chọn: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhập mã khoa: ");
                    String did = scanner.nextLine();
                    if (departmentManager.findDepartmentById(did) != null) {
                        System.out.println("Mã khoa đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập tên khoa: ");
                    String dname = scanner.nextLine();
                    int fcount = getIntInput("Nhập số giảng viên: ");
                    departmentManager.addDepartment(new Department(did, dname, fcount));
                    break;
                case 2:
                    System.out.print("Nhập mã khoa cần sửa: ");
                    String editDid = scanner.nextLine();
                    if (departmentManager.findDepartmentById(editDid) == null) {
                        System.out.println("Không tìm thấy khoa!");
                        break;
                    }
                    System.out.print("Nhập tên mới: ");
                    String newDname = scanner.nextLine();
                    int newFcount = getIntInput("Nhập số GV mới: ");
                    departmentManager.updateDepartment(editDid, newDname, newFcount);
                    break;
                case 3:
                    System.out.print("Nhập mã khoa cần xóa: ");
                    departmentManager.deleteDepartment(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhập tên cần tìm: ");
                    departmentManager.searchByName(scanner.nextLine());
                    break;
                case 5:
                    departmentManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ==================== QUẢN LÝ HỌC KỲ ====================
    private static void semesterMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ HỌC KỲ ---");
            System.out.println("1. Thêm học kỳ");
            System.out.println("2. Sửa thông tin");
            System.out.println("3. Xóa học kỳ");
            System.out.println("4. Hiển thị danh sách");
            System.out.println("0. Quay lại");

            int choice = getIntInput("Chọn: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhập mã học kỳ: ");
                    String sid = scanner.nextLine();
                    if (semesterManager.findSemesterById(sid) != null) {
                        System.out.println("Mã học kỳ đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập tên học kỳ (VD: HK1-2024): ");
                    String sname = scanner.nextLine();
                    System.out.print("Nhập ngày bắt đầu: ");
                    String sstart = scanner.nextLine();
                    System.out.print("Nhập ngày kết thúc: ");
                    String send = scanner.nextLine();
                    semesterManager.addSemester(new Semester(sid, sname, sstart, send));
                    break;
                case 2:
                    System.out.print("Nhập mã HK cần sửa: ");
                    String editSid = scanner.nextLine();
                    if (semesterManager.findSemesterById(editSid) == null) {
                        System.out.println("Không tìm thấy học kỳ!");
                        break;
                    }
                    System.out.print("Nhập tên mới: ");
                    String newSname = scanner.nextLine();
                    System.out.print("Nhập ngày bắt đầu mới: ");
                    String newSstart = scanner.nextLine();
                    System.out.print("Nhập ngày kết thúc mới: ");
                    String newSend = scanner.nextLine();
                    semesterManager.updateSemester(editSid, newSname, newSstart, newSend);
                    break;
                case 3:
                    System.out.print("Nhập mã HK cần xóa: ");
                    semesterManager.deleteSemester(scanner.nextLine());
                    break;
                case 4:
                    semesterManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ==================== QUẢN LÝ TÀI KHOẢN ====================
    private static void userAccountMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ TÀI KHOẢN ---");
            System.out.println("1. Tạo tài khoản");
            System.out.println("2. Đổi mật khẩu");
            System.out.println("3. Xóa tài khoản");
            System.out.println("4. Hiển thị danh sách");
            System.out.println("0. Quay lại");

            int choice = getIntInput("Chọn: ");
            switch (choice) {
                case 1:
                    System.out.print("Nhập mã TK: ");
                    String uid = scanner.nextLine();
                    if (userManager.findUserById(uid) != null) {
                        System.out.println("Mã TK đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập username: ");
                    String uname = scanner.nextLine();
                    if (userManager.findUserByUsername(uname) != null) {
                        System.out.println("Username đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập password: ");
                    String upass = scanner.nextLine();
                    userManager.addUser(new UserAccount(uid, uname, upass));
                    break;
                case 2:
                    System.out.print("Nhập mã TK: ");
                    String changeUid = scanner.nextLine();
                    System.out.print("Nhập mật khẩu cũ: ");
                    String oldPass = scanner.nextLine();
                    System.out.print("Nhập mật khẩu mới: ");
                    String newPass = scanner.nextLine();
                    userManager.changePassword(changeUid, oldPass, newPass);
                    break;
                case 3:
                    System.out.print("Nhập mã TK cần xóa: ");
                    userManager.deleteUser(scanner.nextLine());
                    break;
                case 4:
                    userManager.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
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
                System.out.println("Vui lòng nhập số nguyên!");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
            }
        }
    }

    private static void loadAllData() {
        System.out.println("Đang tải dữ liệu...");
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
        System.out.println("-> Tải dữ liệu hoàn tất!");
    }

    private static void saveAllData() {
        System.out.println("Đang lưu dữ liệu...");
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
        System.out.println("-> Lưu dữ liệu hoàn tất!");
    }
}
# PROPOSAL: HỆ THỐNG QUẢN LÝ SINH VIÊN
## (Student Management System)

---

## 1. Giới Thiệu (Introduction)

Hệ thống Quản lý Sinh viên là ứng dụng console được phát triển bằng Java, hỗ trợ quản lý toàn diện các hoạt động đào tạo bao gồm: quản lý sinh viên, giảng viên, môn học, điểm số, điểm danh và bảo mật người dùng.

Dự án được xây dựng áp dụng các nguyên lý Lập trình Hướng Đối tượng (OOP) và kiến trúc phân tầng rõ ràng.

---

## 2. Kiến Trúc Hệ Thống (System Architecture)

Hệ thống bao gồm **25 Classes** được tổ chức thành 4 packages:

### A. Package `models` (10 Classes - Thực thể dữ liệu)

| Class | Mô tả | Thuộc tính chính |
|-------|-------|------------------|
| Student | Sinh viên | studentID, fullName, dob, gender, email, phone, classID |
| Teacher | Giảng viên | teacherID, fullName, department, email, phone |
| Course | Môn học | courseID, courseName, credits, semester, teacherID |
| ClassRoom | Lớp học | classID, className, teacherID, courseID |
| Enrollment | Đăng ký môn | enrollmentID, studentID, courseID, semester |
| Grade | Điểm số | gradeID, studentID, courseID, midterm, finalExam, total |
| Attendance | Điểm danh | attendanceID, studentID, classID, date, status |
| Department | Khoa | departmentID, departmentName, numberOfTeachers |
| Semester | Học kỳ | semesterID, semesterName, startDate, endDate |
| UserAccount | Tài khoản | userID, username, email, salt, hashedPassword, passwordHistory, loginAttempts, isLocked |

### B. Package `managers` (10 Classes - Xử lý nghiệp vụ)

**Chức năng chung của tất cả Managers:**
- Thêm mới (add)
- Cập nhật (update)
- Xóa (delete)
- Tìm kiếm theo ID (findById)
- Tìm kiếm theo tên/từ khóa (search)
- Hiển thị danh sách (displayAll)
- Lưu file (saveToFile)
- Đọc file (loadFromFile)

| Class | Quản lý | Chức năng bổ sung |
|-------|---------|-------------------|
| StudentManager | Sinh viên | getAll(), sortByName(), sortById(), sortByClass(), getCount() |
| TeacherManager | Giảng viên | Chức năng cơ bản |
| CourseManager | Môn học | Chức năng cơ bản |
| ClassRoomManager | Lớp học | Chức năng cơ bản |
| EnrollmentManager | Đăng ký môn | Chức năng cơ bản |
| GradeManager | Điểm số | getAll(), sortByTotalDesc(), sortByTotalAsc(), sortByStudentId(), getCount() |
| AttendanceManager | Điểm danh | getAll(), getCount() |
| DepartmentManager | Khoa | Chức năng cơ bản |
| SemesterManager | Học kỳ | Chức năng cơ bản |
| UserAccountManager | Tài khoản | register(), login(), changePassword(), unlockAccount() |


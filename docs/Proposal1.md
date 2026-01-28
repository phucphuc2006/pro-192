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
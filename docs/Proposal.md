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


### C. Package `utils` (4 Classes - Tiện ích)

| Class | Mô tả | Phương thức static |
|-------|-------|-------------------|
| PasswordUtils | Bảo mật mật khẩu | generateSalt(), hashPassword(), verifyPassword(), isStrongPassword() |
| ValidationUtils | Kiểm tra dữ liệu | isValidEmail(), isValidPhone(), isValidDate(), isValidScore() |
| StatisticsUtils | Thống kê | calculateAverageGrade(), calculateAttendanceRate(), classifyGrade() |
| ReportUtils | Xuất báo cáo | exportStudentsToCSV(), exportGradesToCSV(), exportSummaryReport() |

### D. Main Class (1 Class - Điểm khởi đầu)

| Class | Mô tả |
|-------|-------|
| Main | Chứa hàm main(), quản lý menu và điều phối chương trình |

---

## 3. Các Tính Năng Chính (Key Features)

### 3.1. Quản lý Đối tượng (CRUD)
- Sinh viên, Giảng viên, Môn học, Lớp học
- Đăng ký môn, Điểm số, Điểm danh
- Khoa, Học kỳ, Tài khoản người dùng

### 3.2. Bảo mật Hệ thống
- Mật khẩu hash SHA-256 với salt
- Yêu cầu mật khẩu mạnh (8+ ký tự, chữ hoa/thường/số/đặc biệt)
- Khóa tài khoản sau 5 lần đăng nhập sai
- Lưu lịch sử 3 mật khẩu gần nhất

### 3.3. Xử lý Dữ liệu Nâng cao
- **Sắp xếp:** Theo tên, mã, điểm, lớp
- **Tìm kiếm:** Theo từ khóa, ID
- **Thống kê:** Điểm trung bình, tỷ lệ chuyên cần, phân loại học lực
- **Xuất báo cáo:** File CSV

### 3.4. Lưu trữ Dữ liệu
- Lưu toàn bộ dữ liệu xuống file .txt khi thoát
- Tự động load dữ liệu khi khởi động
- Thư mục: `data/`

---

## 4. Áp Dụng Kỹ Thuật OOP (Technical Requirements)

| Nguyên lý | Áp dụng trong project |
|-----------|----------------------|
| **Encapsulation** | Mọi thuộc tính là `private`, truy xuất qua Getter/Setter |
| **Modularity** | Phân chia rõ ràng: models, managers, utils |
| **Single Responsibility** | Mỗi class chỉ làm một nhiệm vụ cụ thể |
| **Static Methods** | PasswordUtils, ValidationUtils, StatisticsUtils, ReportUtils |
| **File I/O** | BufferedReader/BufferedWriter cho persistence |
| **Security** | SHA-256 hashing, salt, login attempts limit |

---

## 5. Cấu Trúc Thư Mục (Directory Structure)

```
StudentManagement_NhomXX/
├── src/
│   ├── Main.java
│   ├── models/          (10 files)
│   ├── managers/        (10 files)
│   └── utils/           (4 files)
├── data/                (9 files .txt)
├── reports/             (Thư mục xuất CSV)
└── docs/                (Tài liệu)
```

---

## 6. Công Nghệ Sử Dụng (Technologies)

| Công nghệ | Phiên bản |
|-----------|-----------|
| Ngôn ngữ | Java JDK 8+ |
| Lưu trữ | File-based (text files) |
| Bảo mật | SHA-256 + Salt |
| Encoding | UTF-8 |

---

## 7. Đội Phát Triển (Development Team)

| Thành viên | MSSV | Email |
|------------|------|-------|
| Trương Gia Huy | QE190139 | - |
| Trần Văn Phúc | QE200141 | - |

---

## 8. Kết Luận (Conclusion)

Hệ thống Quản lý Sinh viên đã được xây dựng hoàn chỉnh với:
- ✅ 25 classes phân chia rõ ràng
- ✅ Đầy đủ chức năng CRUD cho 10 loại đối tượng
- ✅ Bảo mật mạnh mẽ với SHA-256
- ✅ Thống kê và xuất báo cáo CSV
- ✅ 900 dòng dữ liệu mẫu
- ✅ Tài liệu chi tiết

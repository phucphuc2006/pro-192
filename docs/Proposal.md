# PROPOSAL: HỆ THỐNG QUẢN LÝ SINH VIÊN
## (Student Management System)

---

## 1. Giới Thiệu (Introduction)

Hệ thống Quản lý Sinh viên là ứng dụng Console Application (CLI) được phát triển bằng Java, hỗ trợ quản lý toàn diện các hoạt động đào tạo bao gồm: quản lý sinh viên, giảng viên, môn học, điểm số, điểm danh và báo cáo thống kê.

Dự án được xây dựng áp dụng các nguyên lý Lập trình Hướng Đối tượng (OOP) và kiến trúc phân tầng rõ ràng.

---

## 2. Kiến Trúc Hệ Thống (System Architecture)

Hệ thống được tổ chức thành 3 packages chính theo mô hình MVC (Model-View-Controller) cải tiến, với View được tích hợp trong Main console:

### A. Console Interface
- **Main.java**: Chứa hàm main và menu điều hướng người dùng (CLI).

### B. Package `models` (9 Classes - Thực thể dữ liệu)

| Class | Mô tả | Thuộc tính chính |
|-------|-------|------------------|
| Student | Sinh viên | id, fullName, email, phone, dob, gender, classID |
| Teacher | Giảng viên | id, fullName, email, phone, department |
| Course | Môn học | courseID, courseName, credits, semester, teacherID |
| ClassRoom | Lớp học | classID, className, teacherID, courseID |
| Enrollment | Đăng ký môn | enrollmentID, studentID, courseID, semester |
| Grade | Điểm số | gradeID, studentID, courseID, midterm, finalExam, total |
| Attendance | Điểm danh | attendanceID, studentID, classID, date, status |
| Department | Khoa | departmentID, departmentName, facultyCount |
| Semester | Học kỳ | semesterID, semesterName, startDate, endDate |

### C. Package `managers` (9 Classes - Xử lý nghiệp vụ)

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
| StudentManager | Sinh viên | searchStudents(), getAllStudents() |
| TeacherManager | Giảng viên | getAll(), getById() |
| CourseManager | Môn học | getAll(), getById() |
| ClassRoomManager | Lớp học | getAll(), getById() |
| EnrollmentManager | Đăng ký môn | getAll(), getByStudentId() |
| GradeManager | Điểm số | getAll(), getByStudentId() |
| AttendanceManager | Điểm danh | getAll() |
| DepartmentManager | Khoa | getAll() |
| SemesterManager | Học kỳ | getAll() |


### D. Package `utils`

| Class | Mô tả | Phương thức static |
|-------|-------|-------------------|
| InputHelper | Hỗ trợ nhập liệu | readString(), readInt(), readDouble() |
| DataGenerator | Tạo dữ liệu mẫu | generateAll() |

## 3. Các Tính Năng Chính (Key Features)

### 3.1. Quản lý Đối tượng (CRUD)
- Sinh viên, Giảng viên, Môn học, Lớp học
- Đăng ký môn, Điểm số, Điểm danh
- Khoa, Học kỳ

### 3.2. Không yêu cầu xác thực
- Hệ thống truy cập trực tiếp vào Dashboard quản lý (CLI).

### 3.3. Xử lý Dữ liệu
- **Tìm kiếm:** Theo từ khóa, ID.
- **Tính toán:** Tự động tính điểm tổng kết dựa trên hệ số 0.4 (giữa kỳ) và 0.6 (cuối kỳ).

### 3.4. Lưu trữ Dữ liệu
- Lưu toàn bộ dữ liệu xuống file .txt.
- Tự động load dữ liệu khi khởi động từ thư mục: `data/`.

---

## 4. Áp Dụng Kỹ Thuật OOP (Technical Requirements)

| Nguyên lý | Áp dụng trong project |
|-----------|----------------------|
| **Encapsulation** | Mọi thuộc tính là `private`, truy xuất qua Getter/Setter |
| **Modularity** | Phân chia rõ ràng: models, managers, utils, ui |
| **Single Responsibility** | Mỗi class chỉ làm một nhiệm vụ cụ thể |
| **File I/O** | BufferedReader/BufferedWriter cho persistence |

---

## 5. Cấu Trúc Thư Mục (Directory Structure)

```
StudentManagement_NhomXX/
├── src/
│   ├── Main.java
│   ├── ui/              (Console Interface)
│   ├── models/          (9 files)
│   ├── managers/        (9 files)
│   └── utils/           (2 files)
├── data/                (9 files .txt)
└── docs/                (Tài liệu)
```

---

## 6. Công Nghệ Sử Dụng (Technologies)

| Công nghệ | Phiên bản |
|-----------|-----------|
| Ngôn ngữ | Java JDK 8+ |
| Giao diện | Console (CLI) |
| Lưu trữ | File-based (text files) |
| Encoding | UTF-8 |

---

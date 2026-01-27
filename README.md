# HỆ THỐNG QUẢN LÝ SINH VIÊN
**Advanced Student Management System**

## Giới Thiệu

Hệ thống Quản lý Sinh viên là ứng dụng console được phát triển bằng Java, áp dụng các nguyên lý Lập trình Hướng Đối tượng (OOP). Hệ thống hỗ trợ quản lý toàn diện thông tin sinh viên, giảng viên, môn học, điểm số và nhiều tính năng khác.

## Tính Năng Chính

### Quản Lý Dữ Liệu
- **Sinh viên**: Thêm, sửa, xóa, tìm kiếm, sắp xếp
- **Giảng viên**: Quản lý thông tin giảng viên theo khoa
- **Môn học**: Quản lý môn học theo học kỳ
- **Lớp học**: Quản lý lớp và phân công giảng viên
- **Đăng ký môn**: Quản lý đăng ký môn học của sinh viên
- **Điểm số**: Nhập điểm giữa kỳ, cuối kỳ, tự động tính tổng
- **Điểm danh**: Theo dõi chuyên cần sinh viên
- **Khoa/Học kỳ**: Quản lý cấu trúc tổ chức

### Bảo Mật
- Mật khẩu hash SHA-256 với salt
- Yêu cầu mật khẩu mạnh (8+ ký tự, chữ hoa/thường/số/đặc biệt)
- Khóa tài khoản sau 5 lần đăng nhập sai
- Lưu lịch sử 3 mật khẩu gần nhất

### Thống Kê & Báo Cáo
- Thống kê sinh viên theo lớp, khoa
- Thống kê điểm trung bình
- Tỷ lệ chuyên cần
- Xuất báo cáo CSV

## Cấu Trúc Project

```
StudentManagement_NhomXX/
├── src/
│   ├── Main.java              # Điểm bắt đầu chương trình
│   ├── models/                # Các lớp đối tượng
│   │   ├── Student.java
│   │   ├── Teacher.java
│   │   ├── Course.java
│   │   ├── ClassRoom.java
│   │   ├── Enrollment.java
│   │   ├── Grade.java
│   │   ├── Attendance.java
│   │   ├── Department.java
│   │   ├── Semester.java
│   │   └── UserAccount.java
│   ├── managers/              # Các lớp quản lý
│   │   ├── StudentManager.java
│   │   ├── TeacherManager.java
│   │   ├── CourseManager.java
│   │   ├── ClassRoomManager.java
│   │   ├── EnrollmentManager.java
│   │   ├── GradeManager.java
│   │   ├── AttendanceManager.java
│   │   ├── DepartmentManager.java
│   │   ├── SemesterManager.java
│   │   └── UserAccountManager.java
│   └── utils/                 # Tiện ích
│       ├── PasswordUtils.java
│       └── ValidationUtils.java
├── data/                      # Dữ liệu (tự động tạo)
│   ├── students.txt
│   ├── teachers.txt
│   ├── courses.txt
│   └── ...
└── docs/                      # Tài liệu
```

## Hướng Dẫn Cài Đặt & Chạy

### Yêu Cầu
- Java JDK 8 trở lên
- Terminal/Command Prompt

### Biên Dịch
```bash
cd src
javac -encoding UTF-8 utils/*.java models/*.java managers/*.java Main.java
```

### Chạy Chương Trình
```bash
java Main
```

### Sử Dụng Lần Đầu
1. Chọn **"2. Đăng ký tài khoản mới"**
2. Nhập username, email và mật khẩu (theo yêu cầu bảo mật)
3. Đăng nhập với tài khoản vừa tạo
4. Sử dụng các chức năng trong menu

## Đội Phát Triển

| Thành viên | MSSV | Vai trò |
|------------|------|---------|
| Trương Gia Huy | QE190139 | Developer |
| Trần Văn Phúc | QE200141 | Developer |

## Công Nghệ Sử Dụng

- **Ngôn ngữ**: Java (JDK 8+)
- **Lưu trữ**: File-based (text files)
- **Bảo mật**: SHA-256 + Salt
- **Kiến trúc**: MVC Pattern

## License

Dự án này được phát triển cho mục đích học tập.

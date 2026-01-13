# PROPOSAL: HỆ THỐNG QUẢN LÝ ĐÀO TẠO (ACADEMIC MANAGEMENT SYSTEM)

## 1. Giới thiệu (Introduction)
Hệ thống được xây dựng để quản lý toàn diện quy trình đào tạo, bao gồm quản lý thông tin con người (Sinh viên, Giảng viên), quản lý khóa học và quản lý điểm số/đăng ký học phần. Dự án áp dụng triệt để các nguyên lý Lập trình hướng đối tượng (OOP).

## 2. Kiến trúc Hệ thống & Danh sách Class (System Architecture)

Dự kiến hệ thống sẽ bao gồm khoảng **15-18 Classes/Interfaces**, chia làm các gói (packages) như sau:

### A. Package `model` (Các thực thể dữ liệu)
Đây là nơi thể hiện rõ nhất tính chất Kế thừa và Đóng gói.

1.  **`Person` (Abstract Class):** Lớp cha trừu tượng.
    * *Thuộc tính:* `id`, `fullName`, `email`, `phoneNumber`, `gender`.
    * *Phương thức:* `toString()`, `showProfile()` (abstract).
2.  **`Student` (extends Person):**
    * *Thuộc tính riêng:* `studentID`, `major` (Chuyên ngành), `gpa`, `enrollmentYear`.
    * *Phương thức:* Override `showProfile()`, `calculateScholarship()`.
3.  **`Lecturer` (extends Person):** (Thêm lớp này để thể hiện Đa hình với Student)
    * *Thuộc tính riêng:* `staffID`, `department`, `salary`.
    * *Phương thức:* Override `showProfile()`.
4.  **`Course`:** Lớp quản lý môn học.
    * *Thuộc tính:* `courseCode`, `courseName`, `credits` (số tín chỉ).
5.  **`Enrollment`:** Lớp liên kết giữa Student và Course (Bảng điểm chi tiết).
    * *Thuộc tính:* `studentID`, `courseCode`, `semester`, `score`.
    * *Phương thức:* `getGradeLetter()` (chuyển điểm số sang A, B, C...).

### B. Package `enums` (Định nghĩa hằng số)
Dùng để chuẩn hóa dữ liệu, tránh nhập sai string.

6.  **`Major` (Enum):** SE, IA, GD, IB...
7.  **`Semester` (Enum):** SPRING_2025, SUMMER_2025...
8.  **`Rank` (Enum):** EXCELLENT, GOOD, AVERAGE, WEAK (Xếp loại dựa trên GPA).

### C. Package `service` (Xử lý nghiệp vụ & Logic)
Áp dụng Interface để tách biệt định nghĩa và cài đặt (Loose Coupling).

9.  **`IStudentService` (Interface):** Định nghĩa các hàm CRUD cho sinh viên.
10. **`StudentManager` (implements IStudentService):** Code chi tiết thêm/sửa/xóa/tìm kiếm sinh viên.
11. **`CourseManager`:** Quản lý danh sách môn học.
12. **`EnrollmentManager`:** Quản lý việc đăng ký môn và nhập điểm cho sinh viên.

### D. Package `utils` (Tiện ích dùng chung - Bắt buộc Static)
Đáp ứng yêu cầu về Utility Classes và xử lý dữ liệu.

13. **`ValidationUtils`:** Chứa các hàm `static` để kiểm tra đầu vào (Check ID format, Check email, Check range điểm 0-10).
14. **`DataSeeder`:** Chứa hàm `static` để tự động sinh 100+ dòng dữ liệu giả (Seed Data) khi chương trình khởi chạy.
15. **`FileUtils`:** Chứa hàm `static` để đọc/ghi file text hoặc object (Serialization).

### E. Package `view` (Giao diện Console)
16. **`Menu`:** Class quản lý việc hiển thị menu và nhận lựa chọn từ người dùng.

### F. Package `main`
17. **`Main`:** Chứa hàm `public static void main(String[] args)` để chạy chương trình.

---

## 3. Các tính năng chính (Key Features)

1.  **Quản lý Sinh viên & Giảng viên:**
    * Thêm mới (Validate dữ liệu chặt chẽ).
    * Cập nhật thông tin.
    * Xóa (Kiểm tra ràng buộc: Sinh viên đã có điểm thì không được xóa).
2.  **Quản lý Khóa học & Điểm số:**
    * Đăng ký môn học cho sinh viên.
    * Nhập điểm/Sửa điểm.
3.  **Xử lý dữ liệu (Advanced):**
    * **Sorting:** Sắp xếp sinh viên theo Tên hoặc theo GPA.
    * **Searching:** Tìm kiếm sinh viên theo khoảng điểm (ví dụ: tìm các bạn từ 8.0 - 9.0).
    * **Reporting:** In danh sách sinh viên đủ điều kiện học bổng.
4.  **Lưu trữ:**
    * Lưu toàn bộ dữ liệu xuống file `students.dat`, `courses.dat` khi thoát.
    * Load lại dữ liệu khi mở chương trình.

## 4. Áp dụng kỹ thuật (Technical Requirements)
* **Encapsulation:** Mọi thuộc tính là `private`, truy xuất qua Getter/Setter.
* **Inheritance:** `Student` và `Lecturer` kế thừa `Person`.
* **Polymorphism:** Dùng `List<Person>` để quản lý chung cả sinh viên và giảng viên; Override `toString()`.
* **Abstraction:** Class `Person` là abstract; Interface `IStudentService`.
* **Static:** Class `ValidationUtils` chỉ chứa static methods.

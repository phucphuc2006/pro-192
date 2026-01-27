# PROPOSAL: HỆ THỐNG QUẢN LÝ ĐÀO TẠO (ACADEMIC MANAGEMENT SYSTEM)

## 1. Giới thiệu (Introduction)
Hệ thống được xây dựng để quản lý toàn diện quy trình đào tạo, bao gồm quản lý thông tin con người (Sinh viên, Giảng viên), quản lý khóa học và quản lý điểm số/đăng ký học phần. Dự án áp dụng triệt để các nguyên lý Lập trình hướng đối tượng (OOP).

## 2. Kiến trúc Hệ thống & Danh sách Class (System Architecture)

Dự kiến hệ thống sẽ bao gồm khoảng **15-18 Classes/Interfaces**, chia làm các gói (packages) như sau:

### A. Package `model` (Các thực thể dữ liệu)

Student (Sinh viên)
studentID: Mã sinh viên

fullName: Họ và tên

dob: Ngày sinh

gender: Giới tính

email: Địa chỉ email

phone: Số điện thoại

classID: Mã lớp học

Course (Môn học)
courseID: Mã môn học

courseName: Tên môn học

credits: Số tín chỉ

semester: Học kỳ giảng dạy

teacherID: Mã giảng viên

Teacher (Giảng viên)
teacherID: Mã giảng viên

fullName: Họ và tên

department: Khoa

email: Địa chỉ email

phone: Số điện thoại

ClassRoom (Lớp học)
classID: Mã lớp học

className: Tên lớp học

teacherID: Mã giảng viên giảng dạy lớp

courseID: Mã môn học

Enrollment (Đăng ký môn học)
enrollmentID: Mã đăng ký

studentID: Mã sinh viên

courseID: Mã môn học

semester: Học kỳ đăng ký

Grade (Điểm số)
gradeID: Mã điểm

studentID: Mã sinh viên

courseID: Mã môn học

midterm: Điểm giữa kỳ

final: Điểm cuối kỳ

total: Tổng điểm

Attendance (Điểm danh)
attendanceID: Mã điểm danh

studentID: Mã sinh viên

classID: Mã lớp học

date: Ngày điểm danh

status: Trạng thái (Có mặt, Vắng, Có phép)

Department (Khoa)
departmentID: Mã khoa

departmentName: Tên khoa

facultyCount: Số lượng giảng viên trong khoa

Semester (Học kỳ)
semesterID: Mã học kỳ

semesterName: Tên học kỳ (Ví dụ: HK1, HK2)

startDate: Ngày bắt đầu học kỳ

endDate: Ngày kết thúc học kỳ

UserAccount (Tài khoản người dùng)
userID: Mã tài khoản

username: Tên đăng nhập

password: Mật khẩu
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

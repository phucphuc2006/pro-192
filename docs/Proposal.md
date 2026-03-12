# PROPOSAL: HỆ THỐNG QUẢN LÝ SINH VIÊN & KHÓA HỌC
## (Student & Course Management System)

---

## 1. Giới Thiệu (Introduction)

Hệ thống Quản lý Sinh viên & Khóa học là ứng dụng Console Application (CLI) được phát triển bằng Java, hỗ trợ quản lý 3 nghiệp vụ chính yếu: 
1. Quản lý Sinh viên (Student)
2. Quản lý Khóa học (Course)
3. Quản lý Đăng ký môn & Điểm số (Enrollment)

Dự án tuân thủ nghiêm ngặt các nguyên lý Lập trình Hướng Đối tượng (OOP) và sử dụng kiến trúc MVC rút gọn. Thiết kế được làm gọn để đạt 100% các tiêu chí từ Rubric PRO192.

---

## 2. Kiến Trúc Hệ Thống (System Architecture)

Hệ thống được tổ chức thành 4 packages chính:

### A. Console Interface
- **Main.java**: Chứa hàm main và menu điều hướng người dùng (CLI).

### B. Package `models` (Thực thể dữ liệu)

| Class | Mô tả | Thuộc tính chính |
|-------|-------|------------------|
| Person | Người (Abstract Class) | id, fullName, email, phone |
| Student | Sinh viên (Kế thừa Person)| dob, gender, classID |
| Course | Khóa học | courseID, courseName, credits |
| Enrollment | Biên bản đăng ký môn | enrollmentID, studentID, courseID, grade |

### C. Package `managers` (Xử lý nghiệp vụ & Data Scaling)

- **`IManager<T>`**: Interface Generics định nghĩa các tác vụ chuẩn (CRUD, Sort, Search, File I/O).
- `StudentManager` (Implements `IManager<Student>`)
- `CourseManager` (Implements `IManager<Course>`)
- `EnrollmentManager` (Implements `IManager<Enrollment>`)

=> Sử dụng cấu trúc dữ liệu `ArrayList` từ Java Collections Framework để xử lý linh hoạt hàng ngàn bản ghi (đáp ứng tiêu chí Data Scaling).

### D. Package `utils` (Tiện ích)

| Class | Mô tả | Phương thức |
|-------|-------|-------------|
| InputHelper | Hỗ trợ nhập liệu có xử lý Try/Catch | readString(), readInt(), readDouble() |
| DataGenerator | Bootstrap 100+ dòng dữ liệu khi khởi chạy | generateAll() |
| ValidationUtils | Static Methods kiểm tra tính hợp lệ dữ liệu | isValidId(), isValidEmail(), isValidGrade(), isNotEmpty() |

---

## 3. Các Tính Năng Chính (Key Features)

### 3.1. Quản lý Đối tượng (CRUD)
- Khởi tạo, Cập nhật, Xóa, Hiển thị danh sách cho Sinh viên, Khóa học và Đăng ký môn.

### 3.2. Tiện ích Xử lý
- **Tìm kiếm (Search):** Tìm theo ID, Tên sinh viên, khóa học.
- **Sắp xếp (Sort):** Sắp xếp danh sách Sinh viên tự động theo tên.
- **Xác thực (Validation):** Không cho phép thông tin rỗng, bắt định dạng số và email sai.

### 3.3. File I/O (Persistence)
- Lưu toàn bộ dữ liệu xuống 3 file text (`students.txt`, `courses.txt`, `enrollments.txt`) trong thư mục `data/`.
- Tự động nạp (Load) dữ liệu lên ArrayList khi khởi động.

---

## 4. Mức Độ Đáp Ứng Rubric Môn Học

| Tiêu chuẩn | Thể hiện qua Code |
|-----------|-------------------------|
| **Encapsulation** | Đóng gói an toàn các thuộc tính (`private`) ở tất cả Entities. |
| **Inheritance** | Thiết kế kế thừa `Student extends Person`. |
| **Polymorphism** | Kế thừa ghi đè (`@Override toString()`). Triển khai đa hình qua Interface (`IManager`). |
| **Abstraction** | Kịch bản trích xuất thông tin người qua Abstract Class `Person` và interface `IManager`. |
| **Data Scaling** | Auto-Gen 100 Test Samples mỗi lúc chương trình kích hoạt. |
| **Static Utility**| `ValidationUtils` chứa static boolean functions kiểm tra chuẩn. |

---

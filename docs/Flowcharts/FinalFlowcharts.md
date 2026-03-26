# FINAL FLOWCHARTS - PRO192 Project

> Hướng dẫn: Copy từng đoạn code Mermaid vào https://mermaid.live rồi xuất ra file .png
> Lưu vào thư mục docs/Flowcharts/

---

## 1. Main Menu Flowchart (Đã có MainFlowchart.png)

```mermaid
flowchart TD
    A(["Bắt đầu chương trình"]) --> B["Khởi tạo StudentManager, CourseManager, EnrollmentManager"]
    B --> C["Constructor gọi loadFromFile() - Nạp dữ liệu từ ổ cứng"]
    C --> D{"File dữ liệu trống?"}
    D -- "Trống" --> E["Gọi DataGenerator.generateAll() - Sinh 100+ bản ghi"]
    D -- "Có dữ liệu" --> F
    E --> F["Hiển thị MAIN DASHBOARD"]
    F --> G["Người dùng nhập lựa chọn"]
    G --> H{"Kiểm tra giá trị"}
    H -- "1" --> I["Mở Menu STUDENT MANAGEMENT"]
    H -- "2" --> J["Mở Menu COURSE MANAGEMENT"]
    H -- "3" --> K["Mở Menu ENROLLMENT MANAGEMENT"]
    H -- "0" --> L(["Thoát chương trình"])
    H -- "Khác" --> M["Báo lỗi: Invalid choice"]
    M --> F
    I --> F
    J --> F
    K --> F
```

---

## 2. Validation Flowchart (Luồng kiểm tra dữ liệu)

```mermaid
flowchart TD
    A(["Bắt đầu nhập dữ liệu"]) --> B["Nhập Student ID"]
    B --> C{"Regex: ID khớp mẫu Sxxx?"}
    C -- "Không" --> D["Báo lỗi: Invalid ID format"]
    D --> B
    C -- "Có" --> E{"ID đã tồn tại trong danh sách?"}
    E -- "Có" --> F["Báo lỗi: ID already exists"]
    F --> B
    E -- "Không" --> G["Nhập Full Name"]
    G --> H{"Tên rỗng?"}
    H -- "Rỗng" --> I["Báo lỗi: Name cannot be empty"]
    I --> G
    H -- "Không" --> J["Nhập Email"]
    J --> K{"Regex: Email khớp mẫu xxx@xxx.xxx?"}
    K -- "Không" --> L["Báo lỗi: Invalid email format"]
    L --> J
    K -- "Có" --> M["Nhập Phone, DOB, Gender, ClassID"]
    M --> N["Tạo new Student(...)"]
    N --> O["studentManager.add(student)"]
    O --> P["saveToFile() - Lưu xuống ổ cứng"]
    P --> Q(["Hoàn tất - Quay về Menu"])
```

---

## 3. Add Student Flowchart (Thêm sinh viên)

```mermaid
flowchart TD
    A(["Người dùng chọn 1. Add Student"]) --> B["Nhập thông tin: ID, Name, Email, Phone, DOB, Gender, ClassID"]
    B --> C{"Validation: ID hợp lệ? Email hợp lệ? ID chưa tồn tại?"}
    C -- "Không hợp lệ" --> D["Báo lỗi cụ thể"]
    D --> B
    C -- "Hợp lệ" --> E["Tạo đối tượng: new Student(...)"]
    E --> F["Main gọi: studentManager.add(student)"]
    F --> G["StudentManager: students.add(item)"]
    G --> H["StudentManager: saveToFile()"]
    H --> I["Mở file students.txt - Ghi toàn bộ danh sách"]
    I --> J["In: Student added successfully"]
    J --> K(["Quay về Menu Student"])
```

---

## 4. Update Student Flowchart (Cập nhật sinh viên)

```mermaid
flowchart TD
    A(["Người dùng chọn 2. Update Student"]) --> B["Nhập Student ID cần sửa"]
    B --> C["Main gọi: studentManager.getById(id)"]
    C --> D{"Tìm thấy sinh viên?"}
    D -- "Không" --> E["Báo lỗi: Student not found"]
    E --> F(["Quay về Menu Student"])
    D -- "Có" --> G["Hiển thị thông tin hiện tại"]
    G --> H["Nhập thông tin mới: Name, Email, Phone, DOB, Gender, ClassID"]
    H --> I{"Validation thông tin mới hợp lệ?"}
    I -- "Không" --> J["Báo lỗi"]
    J --> H
    I -- "Có" --> K["Tạo đối tượng mới: new Student(...)"]
    K --> L["Main gọi: studentManager.update(id, newStudent)"]
    L --> M["StudentManager: Thay thế student cũ bằng mới"]
    M --> N["StudentManager: saveToFile()"]
    N --> O["In: Student updated successfully"]
    O --> F
```

---

## 5. Delete Student Flowchart (Xóa sinh viên)

```mermaid
flowchart TD
    A(["Người dùng chọn 3. Delete Student"]) --> B["Nhập Student ID cần xóa"]
    B --> C["Main gọi: studentManager.getById(id)"]
    C --> D{"Tìm thấy sinh viên?"}
    D -- "Không" --> E["Báo lỗi: Student not found"]
    E --> F(["Quay về Menu Student"])
    D -- "Có" --> G["Hiển thị thông tin sinh viên sắp xóa"]
    G --> H["Main gọi: studentManager.delete(id)"]
    H --> I["StudentManager: Xóa student khỏi List"]
    I --> J["StudentManager: saveToFile()"]
    J --> K["In: Student deleted successfully"]
    K --> F
```

---

## 6. Search Flowchart (Tìm kiếm - Dùng Predicate)

```mermaid
flowchart TD
    A(["Người dùng chọn 5. Search"]) --> B["Nhập từ khóa keyword"]
    B --> C["Main.java tạo Predicate - Bộ quy tắc lọc"]
    C --> D["Truyền Predicate vào studentManager.search(condition)"]
    D --> E["StudentManager gọi students.stream()"]
    E --> F["stream.filter(condition) - Bắt đầu lọc"]
    F --> G{"Lấy sinh viên thứ i"}
    G --> H{"Predicate: Tên hoặc ID chứa keyword?"}
    H -- "TRUE" --> I["Sinh viên lọt phễu"]
    H -- "FALSE" --> J["Loại bỏ"]
    I --> K{"Còn sinh viên tiếp?"}
    J --> K
    K -- "Có" --> G
    K -- "Hết" --> L["collect Collectors.toList - Gom vào List mới"]
    L --> M["Return List kết quả về Main"]
    M --> N["forEach println - In ra màn hình"]
    N --> O(["Quay về Menu Student"])
```

---

## 7. Sort Flowchart (Sắp xếp - Dùng Comparator)

```mermaid
flowchart TD
    A(["Người dùng chọn 6. Sort by Name"]) --> B["Main.java tạo Comparator"]
    B --> C["Comparator.comparing Student getFullName"]
    C --> D["Truyền Comparator vào studentManager.sort(comparator)"]
    D --> E["StudentManager gọi students.sort(comparator)"]
    E --> F["Thuật toán TimSort kích hoạt"]
    F --> G["TimSort bế 2 sinh viên A và B"]
    G --> H["Hỏi Comparator: Ai lớn hơn?"]
    H --> I["Comparator gọi getFullName trên A và B"]
    I --> J["String.compareTo so sánh 2 tên"]
    J --> K{"Kết quả?"}
    K -- "Số ÂM" --> L["Giữ nguyên vị trí"]
    K -- "Số 0" --> L
    K -- "Số DƯƠNG" --> M["Đổi chỗ A và B"]
    L --> N{"Còn cặp chưa xét?"}
    M --> N
    N -- "Có" --> G
    N -- "Hết" --> O["Danh sách đã sắp xếp A-Z"]
    O --> P["Main gọi getAll forEach - In ra màn hình"]
    P --> Q(["Quay về Menu Student"])
```

---

## 8. File I/O Flowchart (loadFromFile + saveToFile)

```mermaid
flowchart TD
    subgraph LOAD["loadFromFile - Đọc file lên RAM"]
        L1(["Bắt đầu loadFromFile"]) --> L2{"File students.txt tồn tại?"}
        L2 -- "Không" --> L3["return - Danh sách rỗng"]
        L2 -- "Có" --> L4["Mở BufferedReader - try-with-resources"]
        L4 --> L5["students.clear - Xóa danh sách cũ"]
        L5 --> L6{"readLine - Đọc 1 dòng"}
        L6 -- "null - Hết file" --> L7["Đóng file tự động"]
        L6 -- "Có dữ liệu" --> L8{"Dòng rỗng?"}
        L8 -- "Rỗng" --> L6
        L8 -- "Có nội dung" --> L9["split dấu phẩy - Chặt thành mảng parts"]
        L9 --> L10{"parts.length >= 7?"}
        L10 -- "Không" --> L6
        L10 -- "Có" --> L11["new Student parts 0 đến parts 6"]
        L11 --> L12["students.add student"]
        L12 --> L6
        L7 --> L13(["Hoàn tất - Dữ liệu đã trên RAM"])
    end

    subgraph SAVE["saveToFile - Ghi từ RAM xuống file"]
        S1(["Bắt đầu saveToFile"]) --> S2["Mở BufferedWriter - try-with-resources"]
        S2 --> S3{"Lấy sinh viên tiếp theo"}
        S3 -- "Hết" --> S4["Đóng file tự động"]
        S3 -- "Có" --> S5["String.format: Nối 7 trường bằng dấu phẩy"]
        S5 --> S6["bw.write dòng text"]
        S6 --> S7["bw.newLine - Xuống dòng"]
        S7 --> S3
        S4 --> S8(["Hoàn tất - Dữ liệu đã trên ổ cứng"])
    end
```

---

## Tóm tắt: Danh sách file PNG cần xuất

| STT | Tên file PNG | Mermaid section |
|---|---|---|
| 1 | MainFlowchart.png | ĐÃ CÓ SẴN |
| 2 | ValidationFlowchart.png | Section 2 |
| 3 | AddStudentFlowchart.png | Section 3 |
| 4 | UpdateStudentFlowchart.png | Section 4 |
| 5 | DeleteStudentFlowchart.png | Section 5 |
| 6 | SearchFlowchart.png | Section 6 |
| 7 | SortFlowchart.png | Section 7 |
| 8 | FileIOFlowchart.png | Section 8 |

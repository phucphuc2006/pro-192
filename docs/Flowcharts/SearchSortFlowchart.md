# Flowchart: Search & Sort Chi Tiết

## 1. Luồng Tìm Kiếm (Search Flow)

```mermaid
flowchart TD
    A["Người dùng chọn 5. Search Student"] --> B["Nhập từ khóa (keyword)"]
    B --> C["Main.java tạo Predicate (Bộ quy tắc lọc)"]
    C --> D["Truyền Predicate vào studentManager.search()"]
    D --> E["StudentManager nhận Predicate"]
    E --> F["Gọi students.stream() - Mở băng chuyền"]
    F --> G["filter(condition) - Lọc từng sinh viên"]
    
    G --> H{"Sinh viên thứ i: Tên hoặc ID có chứa keyword?"}
    H -- "TRUE" --> I["Sinh viên lọt phễu"]
    H -- "FALSE" --> J["Loại bỏ"]
    
    I --> K{"Còn sinh viên tiếp theo?"}
    J --> K
    K -- "Có" --> H
    K -- "Hết" --> L["collect(Collectors.toList()) - Gom kết quả vào List mới"]
    
    L --> M["Return List kết quả về Main.java"]
    M --> N["forEach(System.out::println) - In ra màn hình"]
    N --> O["Quay về Menu Student Management"]
```

### Giải thích:
- **Predicate** được tạo ở `Main.java`, truyền vào `StudentManager` (giống Comparator của Sort)
- **Stream API** mở băng chuyền dữ liệu, lọc từng sinh viên qua phễu `filter`
- Kết quả được gom vào **List mới** (không thay đổi danh sách gốc)
- **toLowerCase()** được gọi ở cả 2 phía (Tên sinh viên + Từ khóa) để tìm kiếm không phân biệt Hoa/Thường

---

## 2. Luồng Sắp Xếp (Sort Flow)

```mermaid
flowchart TD
    A["Người dùng chọn 6. Sort Students by Name"] --> B["Main.java tạo Comparator"]
    B --> C["Comparator.comparing(Student::getFullName)"]
    C --> D["Truyền Comparator vào studentManager.sort()"]
    D --> E["StudentManager nhận Comparator"]
    E --> F["Gọi students.sort(comparator)"]
    F --> G["Thuật toán TimSort kích hoạt"]
    
    G --> H["TimSort bế 2 sinh viên (A, B) ra"]
    H --> I["Hỏi Comparator: Ai lớn hơn?"]
    I --> J["Comparator gọi getFullName() trên A và B"]
    J --> K["Lấy được 2 chuỗi String (Tên)"]
    K --> L["String.compareTo() so sánh theo bảng ASCII"]
    
    L --> M{"Kết quả?"}
    M -- "Số ÂM: A nhỏ hơn" --> N["Giữ nguyên vị trí"]
    M -- "Số DƯƠNG: A lớn hơn" --> O["Đổi chỗ A và B"]
    M -- "Số 0: Bằng nhau" --> N
    
    N --> P{"Còn cặp sinh viên nào chưa xét?"}
    O --> P
    P -- "Có" --> H
    P -- "Hết" --> Q["Danh sách đã sắp xếp A-Z xong"]
    
    Q --> R["Main.java gọi getAll().forEach() in ra"]
    R --> S["Quay về Menu Student Management"]
```

### Giải thích:
- **Comparator** được tạo ở `Main.java`, truyền vào `StudentManager`
- `StudentManager` hoàn toàn **mù tịt** về tiêu chí sắp xếp (Open/Closed Principle)
- **TimSort** (thuật toán có sẵn của Java) thực hiện việc đổi chỗ
- **String.compareTo()** (phương thức có sẵn của String) thực hiện việc so sánh tên
- Muốn đổi tiêu chí (xếp theo ID, Lớp...) → Chỉ sửa ở `Main.java`, không đụng `StudentManager`

---

## 3. So sánh Search vs Sort

```mermaid
flowchart LR
    subgraph SEARCH["SEARCH (Tìm kiếm)"]
        S1["Main tạo Predicate"] --> S2["Manager nhận Predicate"]
        S2 --> S3["Stream.filter(condition)"]
        S3 --> S4["Trả về List mới"]
    end
    
    subgraph SORT["SORT (Sắp xếp)"]
        T1["Main tạo Comparator"] --> T2["Manager nhận Comparator"]
        T2 --> T3["List.sort(comparator)"]
        T3 --> T4["Thay đổi List gốc"]
    end
```

### Điểm giống nhau:
- Cả 2 đều **truyền hành vi (Behavior)** từ Main vào Manager
- Manager đều **mù tịt** về logic bên trong
- Muốn thay đổi tiêu chí → Chỉ sửa ở Main, không đụng Manager

### Điểm khác nhau:
| | Search | Sort |
|---|---|---|
| Dùng gì | `Predicate<T>` (Bộ lọc) | `Comparator<T>` (Bộ so sánh) |
| Trả về | `true/false` (Lọt hay không) | Số Âm/0/Dương (Ai lớn hơn) |
| Kết quả | List **MỚI** (không đụng gốc) | Thay đổi List **GỐC** |

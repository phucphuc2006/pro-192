
## 8. Flowchart Quản Lý Sinh Viên

```mermaid
flowchart TD
    A([MENU QUẢN LÝ SINH VIÊN]) --> B[/"Hiển thị menu:<br/>1.Thêm 2.Sửa 3.Xóa<br/>4.Tìm 5.Hiển thị 0.Quay lại"/]
    B --> C{Chọn option?}
    C -->|1| D[/Nhập ID, Tên, DOB, Giới tính, Email, SĐT, Mã lớp/]
    C -->|2| E[/Nhập ID cần sửa + thông tin mới/]
    C -->|3| F[/Nhập ID cần xóa/]
    C -->|4| G[/Nhập tên cần tìm/]
    C -->|5| H[Hiển thị danh sách]
    C -->|0| I([QUAY LẠI MENU CHÍNH])
    D --> J{Validate dữ liệu?}
    E --> J
    F --> J
    G --> J
    H --> B
    J -->|Không| K[/Báo lỗi cụ thể/]
    K --> B
    J -->|Có| L[Thực hiện thao tác]
    L --> M[/THÀNH CÔNG/]
    M --> B
```

---

## 9. Flowchart Quản Lý Giảng Viên

```mermaid
flowchart TD
    A([MENU QUẢN LÝ GIẢNG VIÊN]) --> B[/"1.Thêm GV 2.Sửa GV<br/>3.Xóa GV 4.Tìm kiếm<br/>5.Hiển thị DS 0.Quay lại"/]
    B --> C{Option?}
    C -->|1-5| D[/Nhập dữ liệu GV:<br/>ID, Tên, Khoa, Email, SĐT/]
    C -->|0| E([QUAY LẠI])
    D --> F[Xử lý theo option]
    F --> G[/Thông báo kết quả/]
    G --> B
```

---

## 10. Flowchart Quản Lý Môn Học

```mermaid
flowchart TD
    A([MENU QUẢN LÝ MÔN HỌC]) --> B[/"1.Thêm môn 2.Sửa môn<br/>3.Xóa môn 4.Tìm kiếm<br/>5.Hiển thị 0.Quay lại"/]
    B --> C[/Nhập: Mã môn, Tên môn,<br/>Số tín chỉ, Học kỳ, Mã GV/]
    C --> D{Tín chỉ 1-10?}
    D -->|Không| E[/Báo lỗi tín chỉ không hợp lệ/]
    E --> C
    D -->|Có| F[Thực hiện CRUD]
    F --> G([HOÀN THÀNH])
```

---

## 11. Flowchart Quản Lý Lớp Học

```mermaid
flowchart TD
    A([MENU QUẢN LÝ LỚP HỌC]) --> B[/Nhập: Mã lớp, Tên lớp,<br/>Mã GV chủ nhiệm, Mã môn chính/]
    B --> C[Thực hiện CRUD]
    C --> D[/Hiển thị kết quả/]
    D --> E([HOÀN THÀNH])
```

---

## 12. Flowchart Quản Lý Đăng Ký Môn

```mermaid
flowchart TD
    A([MENU ĐĂNG KÝ MÔN HỌC]) --> B[/Nhập: Mã đăng ký, Mã SV,<br/>Mã môn học, Học kỳ/]
    B --> C{SV tồn tại?}
    C -->|Không| D[/Báo lỗi SV không tồn tại/]
    D --> B
    C -->|Có| E{Môn tồn tại?}
    E -->|Không| F[/Báo lỗi môn không tồn tại/]
    F --> B
    E -->|Có| G[Tạo đăng ký]
    G --> H([THÀNH CÔNG])
```

---

## 13. Flowchart Quản Lý Điểm Danh

```mermaid
flowchart TD
    A([MENU ĐIỂM DANH]) --> B[/Nhập: Mã điểm danh, Mã SV,<br/>Mã lớp, Ngày, Trạng thái/]
    B --> C{Trạng thái hợp lệ?<br/>Co mat/Vang/Co phep}
    C -->|Không| D[/Báo lỗi trạng thái/]
    D --> B
    C -->|Có| E[Lưu điểm danh]
    E --> F([THÀNH CÔNG])
```

---

## 14. Flowchart Quản Lý Khoa

```mermaid
flowchart TD
    A([MENU QUẢN LÝ KHOA]) --> B[/Nhập: Mã khoa, Tên khoa, Số GV/]
    B --> C{Số GV >= 0?}
    C -->|Không| D[/Báo lỗi số GV không hợp lệ/]
    D --> B
    C -->|Có| E[Thực hiện CRUD]
    E --> F([HOÀN THÀNH])
```

---

## 15. Flowchart Quản Lý Học Kỳ

```mermaid
flowchart TD
    A([MENU QUẢN LÝ HỌC KỲ]) --> B[/Nhập: Mã học kỳ, Tên học kỳ,<br/>Ngày bắt đầu, Ngày kết thúc/]
    B --> C{Ngày hợp lệ?<br/>bắt đầu < kết thúc}
    C -->|Không| D[/Báo lỗi ngày/]
    D --> B
    C -->|Có| E[Thực hiện CRUD]
    E --> F([HOÀN THÀNH])
```

---

## 16. Flowchart Thống Kê

```mermaid
flowchart TD
    A([MENU THỐNG KÊ]) --> B[/"1.TK SV theo lớp<br/>2.TK điểm số<br/>3.TK điểm danh<br/>0.Quay lại"/]
    B --> C{Option?}
    C -->|1| D[Đếm SV theo từng lớp]
    C -->|2| E[Tính TB điểm, tìm max/min]
    C -->|3| F[Tính tỷ lệ chuyên cần]
    C -->|0| G([QUAY LẠI])
    D --> H[/Hiển thị bảng đếm/]
    E --> I[Phân loại học lực]
    I --> J[/Hiển thị kết quả/]
    F --> K[/Hiển thị % có mặt/]
    H --> L[/IN THỐNG KÊ RA MÀN HÌNH/]
    J --> L
    K --> L
    L --> B
```

---

# Flowcharts - Sơ Đồ Luồng Hệ Thống

> **Chú thích các hình dạng chuẩn:**
> - `([...])` = Hình bầu dục → Bắt đầu/Kết thúc
> - `[...]` = Hình chữ nhật → Xử lý/Thao tác
> - `[/.../ ]` = Hình bình hành → Nhập/Xuất dữ liệu
> - `{...}` = Hình thoi → Điều kiện rẽ nhánh


## 4. Flowchart CRUD

### 4.1 Thêm mới (Add)

```mermaid
flowchart TD
    A([BẮT ĐẦU]) --> B[/Nhập thông tin đối tượng mới/]
    B --> C{Dữ liệu hợp lệ?}
    C -->|Không| D[/Báo lỗi/]
    D --> B
    C -->|Có| E[Thêm vào ArrayList]
    E --> F([THÊM THÀNH CÔNG])
```

### 4.2 Sửa (Update)

```mermaid
flowchart TD
    A([BẮT ĐẦU]) --> B[/Nhập ID cần sửa/]
    B --> C{Tìm thấy ID?}
    C -->|Không| D[/Báo không tìm thấy/]
    D --> E([QUAY LẠI])
    C -->|Có| F[/Nhập thông tin mới/]
    F --> G[Cập nhật đối tượng]
    G --> H([SỬA THÀNH CÔNG])
```

### 4.3 Xóa (Delete)

```mermaid
flowchart TD
    A([BẮT ĐẦU]) --> B[/Nhập ID cần xóa/]
    B --> C{Tìm thấy ID?}
    C -->|Không| D[/Báo không tìm thấy/]
    D --> E([QUAY LẠI])
    C -->|Có| F[Xóa khỏi ArrayList]
    F --> G([XÓA THÀNH CÔNG])
```

---

## 5. Flowchart Đổi Mật Khẩu

```mermaid
flowchart TD
    A([BẮT ĐẦU]) --> B[/Nhập mật khẩu cũ/]
    B --> C{Mật khẩu cũ đúng?}
    C -->|Không| D[/Báo lỗi sai MK/]
    D --> B
    C -->|Có| E[/Nhập mật khẩu mới/]
    E --> F{MK mới đủ mạnh?}
    F -->|Không| G[/Báo lỗi MK yếu/]
    G --> E
    F -->|Có| H{Trùng MK cũ hoặc history?}
    H -->|Có| I[/Báo lỗi trùng/]
    I --> E
    H -->|Không| J[Hash MK mới, Lưu history]
    J --> K([ĐỔI MK THÀNH CÔNG])
```

---

## 6. Flowchart Xuất Báo Cáo CSV

```mermaid
flowchart TD
    A([BẮT ĐẦU]) --> B[/Chọn loại báo cáo 1-4/]
    B --> C[/Nhập tên file/]
    C --> D[Tạo thư mục reports/ nếu chưa có]
    D --> E[Ghi header CSV]
    E --> F[Duyệt ArrayList ghi từng dòng]
    F --> G[Đóng file]
    G --> H([XUẤT THÀNH CÔNG<br/>reports/xxx.csv])
```

---

## 7. Flowchart Tính Điểm Tổng

```mermaid
flowchart TD
    A([BẮT ĐẦU]) --> B[/Nhập điểm giữa kỳ GK/]
    B --> C{0 <= GK <= 10?}
    C -->|Không| D[/Báo lỗi điểm không hợp lệ/]
    D --> B
    C -->|Có| E[/Nhập điểm cuối kỳ CK/]
    E --> F{0 <= CK <= 10?}
    F -->|Không| G[/Báo lỗi điểm không hợp lệ/]
    G --> E
    F -->|Có| H["Tính tổng: GK*0.4 + CK*0.6"]
    H --> I([LƯU ĐIỂM THÀNH CÔNG])
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




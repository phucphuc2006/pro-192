# Flowcharts - Sơ Đồ Luồng Hệ Thống

> **Chú thích các hình dạng chuẩn:**
> - `([...])` = Hình bầu dục → Bắt đầu/Kết thúc
> - `[...]` = Hình chữ nhật → Xử lý/Thao tác
> - `[/.../ ]` = Hình bình hành → Nhập/Xuất dữ liệu
> - `{...}` = Hình thoi → Điều kiện rẽ nhánh

---

## 1. Flowchart Tổng Quan Hệ Thống

```mermaid
flowchart TD
    A([KHỞI ĐỘNG CHƯƠNG TRÌNH]) --> B[Load dữ liệu từ file]
    B --> C[/Hiển thị Menu Đăng nhập/Đăng ký/]
    C --> D{Đăng nhập thành công?}
    D -->|Không| E[Thử lại hoặc đăng ký]
    E --> C
    D -->|Có| F[/Hiển thị MENU CHÍNH/]
    F --> G[/Chọn chức năng 1-14/]
    G --> H[Xử lý và hiển thị kết quả]
    H --> I{Thoát? - option 0}
    I -->|Không| F
    I -->|Có| J[Lưu dữ liệu xuống file]
    J --> K([KẾT THÚC])
```

---


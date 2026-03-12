# Flow Chart Luồng Xử Lý Chính (Main Menu)
Sơ đồ thuật toán dưới đây được vẽ bởi **MermaidJS** để mô phỏng luồng làm việc của Menu chính và quá trình chạy ứng dụng. Hình ảnh có thể hiển thị bằng cách gắn code này vào [Mermaid Live Editor](https://mermaid.live/) hoặc File markdown hỗ trợ.

```mermaid
flowchart TD
    Start([Bắt đầu chương trình Java]) --> BootSystem[Khởi tạo StudentManager, CourseManager, EnrollmentManager]
    BootSystem --> LoadData[Nạp dữ liệu từ thư mục /data]
    
    LoadData --> CheckData{File dữ liệu có trống?}
    CheckData -- Trống --> GenData[Gọi utils.DataGenerator.generateAll() sinh 100+ bản ghi]
    GenData --> MainDash
    CheckData -- Đã có dữ liệu --> MainDash
    
    MainDash[\"Hiển thị MAIN DASHBOARD"\] --> WaitInput[Người dùng nhập Lựa chọn (Choice)]
    WaitInput --> ValidateInput{Kiểm tra kiểu số\n(Try-Catch)}
    
    ValidateInput -- Nhập chữ/Lỗi --> ShowError[Báo lỗi Input]
    ShowError --> MainDash
    
    ValidateInput -- Hợp lệ --> SwitchCase{Kiểm tra giá trị}
    
    SwitchCase -- "Choice == 1" --> MenuStudent[Mở Menu STUDENT MANAGEMENT]
    SwitchCase -- "Choice == 2" --> MenuCourse[Mở Menu COURSE MANAGEMENT]
    SwitchCase -- "Choice == 3" --> MenuEnroll[Mở Menu ENROLLMENT & GRADES]
    SwitchCase -- "Choice == 0" --> Exit([Thoát Chương trình])
    SwitchCase -- Mặc định --> ShowError
    
    %% Mở rộng nhanh Student Management
    MenuStudent --> StdOpt[Nhập 0-6 cho chức năng]
    StdOpt -- 1 --> Add[Thêm Mới]
    StdOpt -- 2 --> Update[Sửa]
    StdOpt -- 3 --> Delete[Xóa]
    StdOpt -- "..." --> Etc[Các chức năng khác]
    StdOpt -- 0 --> MenuBack[Thoát về Main Dashboard]
    
    Add --> InputValidation[Validate ID, Email, Trống...]
    InputValidation -- Lỗi --> LoopInput[Yêu cầu nhập lại]
    LoopInput --> InputValidation
    InputValidation -- Hợp lệ --> SaveData[Add vào Collection & Save File]
    SaveData --> MainDash
    
    Update --> MainDash
    Delete --> MainDash
    Etc --> MainDash
    MenuBack --> MainDash
    
    MenuCourse --> MenuBack
    MenuEnroll --> MenuBack
```

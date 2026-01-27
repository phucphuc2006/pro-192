# Class Diagram - Sơ Đồ Lớp

## Tổng Quan Hệ Thống

Hệ thống gồm **25 classes** được tổ chức theo mô hình **MVC** (Model-View-Controller):

```
┌─────────────────────────────────────────────────────────────────────┐
│                           MAIN.JAVA                                 │
│                    (Controller - Điều khiển)                        │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
        ┌───────────────────────┼───────────────────────┐
        │                       │                       │
        ▼                       ▼                       ▼
┌───────────────┐       ┌───────────────┐       ┌───────────────┐
│    MODELS     │       │   MANAGERS    │       │     UTILS     │
│  (10 classes) │◀──────│  (10 classes) │───────│  (4 classes)  │
│               │       │               │       │               │
└───────────────┘       └───────────────┘       └───────────────┘
```

---

## 1. Package MODELS (10 Classes)

```
┌─────────────────────────────────────────────────────────────────────┐
│                         ENTITY CLASSES                              │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│    Student      │     │    Teacher      │     │    Course       │
├─────────────────┤     ├─────────────────┤     ├─────────────────┤
│ -studentID      │     │ -teacherID      │     │ -courseID       │
│ -fullName       │     │ -fullName       │     │ -courseName     │
│ -dob            │     │ -department     │     │ -credits        │
│ -gender         │     │ -email          │     │ -semester       │
│ -email          │     │ -phone          │     │ -teacherID      │
│ -phone          │     ├─────────────────┤     ├─────────────────┤
│ -classID        │     │ +getters()      │     │ +getters()      │
├─────────────────┤     │ +setters()      │     │ +setters()      │
│ +getters()      │     │ +toString()     │     │ +toString()     │
│ +setters()      │     └─────────────────┘     └─────────────────┘
│ +toString()     │
└─────────────────┘

┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│   ClassRoom     │     │   Enrollment    │     │     Grade       │
├─────────────────┤     ├─────────────────┤     ├─────────────────┤
│ -classID        │     │ -enrollmentID   │     │ -gradeID        │
│ -className      │     │ -studentID      │     │ -studentID      │
│ -teacherID      │     │ -courseID       │     │ -courseID       │
│ -courseID       │     │ -semester       │     │ -midterm        │
├─────────────────┤     ├─────────────────┤     │ -finalExam      │
│ +getters()      │     │ +getters()      │     │ -total          │
│ +setters()      │     │ +setters()      │     ├─────────────────┤
│ +toString()     │     │ +toString()     │     │ +calculateTotal()│
└─────────────────┘     └─────────────────┘     │ +getters()      │
                                                 │ +setters()      │
                                                 └─────────────────┘

┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│   Attendance    │     │   Department    │     │    Semester     │
├─────────────────┤     ├─────────────────┤     ├─────────────────┤
│ -attendanceID   │     │ -departmentID   │     │ -semesterID     │
│ -studentID      │     │ -departmentName │     │ -semesterName   │
│ -classID        │     │ -numberOfTeachers│    │ -startDate      │
│ -date           │     ├─────────────────┤     │ -endDate        │
│ -status         │     │ +getters()      │     ├─────────────────┤
├─────────────────┤     │ +setters()      │     │ +getters()      │
│ +isPresent()    │     │ +toString()     │     │ +setters()      │
│ +isAbsent()     │     └─────────────────┘     │ +toString()     │
│ +isExcused()    │                             └─────────────────┘
│ +getters()      │
│ +setters()      │
└─────────────────┘

┌─────────────────────────────────────────┐
│              UserAccount                │
├─────────────────────────────────────────┤
│ -userID                                 │
│ -username                               │
│ -email                                  │
│ -salt                                   │
│ -hashedPassword                         │
│ -passwordHistory : List<String>         │
│ -loginAttempts : int                    │
│ -isLocked : boolean                     │
├─────────────────────────────────────────┤
│ +validatePassword(input) : boolean      │
│ +addToPasswordHistory(hash) : void      │
│ +isPasswordInHistory(hash) : boolean    │
│ +incrementLoginAttempts() : void        │
│ +resetLoginAttempts() : void            │
│ +getters() / setters()                  │
└─────────────────────────────────────────┘
```

---

## 2. Package MANAGERS (10 Classes)

```
┌─────────────────────────────────────────────────────────────────────┐
│                        MANAGER CLASSES                              │
│           (Mỗi class quản lý một loại Entity)                       │
└─────────────────────────────────────────────────────────────────────┘

┌───────────────────────────────────────────────────────────────────┐
│                     <<COMMON METHODS>>                            │
│  Tất cả Managers đều có các phương thức chung:                    │
│  +add(), +update(), +delete(), +findById(), +search()             │
│  +displayAll(), +saveToFile(), +loadFromFile()                    │
└───────────────────────────────────────────────────────────────────┘

┌─────────────────────┐     ┌─────────────────────┐
│   StudentManager    │     │   TeacherManager    │
├─────────────────────┤     ├─────────────────────┤
│ -students:ArrayList │     │ -teachers:ArrayList │
│ -FILE_NAME:String   │     │ -FILE_NAME:String   │
├─────────────────────┤     ├─────────────────────┤
│ +getAll()           │     │ +common methods     │
│ +getCount()         │     └─────────────────────┘
│ +sortByName()       │
│ +sortById()         │     ┌─────────────────────┐
│ +sortByClass()      │     │   CourseManager     │
│ +common methods     │     ├─────────────────────┤
└─────────────────────┘     │ -courses:ArrayList  │
                            │ -FILE_NAME:String   │
┌─────────────────────┐     ├─────────────────────┤
│  ClassRoomManager   │     │ +common methods     │
├─────────────────────┤     └─────────────────────┘
│ -classRooms:ArrayList│
│ -FILE_NAME:String   │     ┌─────────────────────┐
├─────────────────────┤     │  EnrollmentManager  │
│ +common methods     │     ├─────────────────────┤
└─────────────────────┘     │ -enrollments:ArrayList│
                            │ -FILE_NAME:String    │
┌─────────────────────┐     ├─────────────────────┤
│    GradeManager     │     │ +common methods     │
├─────────────────────┤     └─────────────────────┘
│ -grades:ArrayList   │
│ -FILE_NAME:String   │     ┌─────────────────────┐
├─────────────────────┤     │  AttendanceManager  │
│ +getAll()           │     ├─────────────────────┤
│ +getCount()         │     │ -attendances:ArrayList│
│ +sortByTotalDesc()  │     │ -FILE_NAME:String    │
│ +sortByTotalAsc()   │     ├─────────────────────┤
│ +sortByStudentId()  │     │ +getAll()            │
│ +common methods     │     │ +getCount()          │
└─────────────────────┘     │ +common methods      │
                            └─────────────────────┘
┌─────────────────────┐     ┌─────────────────────┐
│  DepartmentManager  │     │   SemesterManager   │
├─────────────────────┤     ├─────────────────────┤
│ -departments:ArrayList│   │ -semesters:ArrayList│
│ -FILE_NAME:String   │     │ -FILE_NAME:String   │
├─────────────────────┤     ├─────────────────────┤
│ +common methods     │     │ +common methods     │
└─────────────────────┘     └─────────────────────┘

┌───────────────────────────────────────────────────────────────────┐
│                     UserAccountManager                            │
├───────────────────────────────────────────────────────────────────┤
│ -users : ArrayList<UserAccount>                                   │
│ -FILE_NAME : String                                               │
├───────────────────────────────────────────────────────────────────┤
│ +register(username, email, password) : UserAccount                │
│ +login(username, password) : UserAccount                          │
│ +changePassword(userID, oldPass, newPass) : boolean               │
│ +unlockAccount(userID) : void                                     │
│ +findByUsername(username) : UserAccount                           │
│ +findByEmail(email) : UserAccount                                 │
│ +common methods                                                   │
└───────────────────────────────────────────────────────────────────┘
```

---

## 3. Package UTILS (4 Classes)

```
┌─────────────────────────────────────────────────────────────────────┐
│                       UTILITY CLASSES                               │
│              (Chỉ chứa static methods)                              │
└─────────────────────────────────────────────────────────────────────┘

┌───────────────────────────────────────────────────────────────────┐
│                       PasswordUtils                               │
├───────────────────────────────────────────────────────────────────┤
│ +generateSalt() : String                      [STATIC]            │
│ +hashPassword(password, salt) : String        [STATIC]            │
│ +verifyPassword(input, salt, hash) : boolean  [STATIC]            │
│ +isStrongPassword(password) : boolean         [STATIC]            │
│ +isValidEmail(email) : boolean                [STATIC]            │
│ +isValidUsername(username) : boolean          [STATIC]            │
└───────────────────────────────────────────────────────────────────┘

┌───────────────────────────────────────────────────────────────────┐
│                      ValidationUtils                              │
├───────────────────────────────────────────────────────────────────┤
│ -EMAIL_PATTERN : Pattern                                          │
│ -PHONE_PATTERN : Pattern                                          │
│ -STUDENT_ID_PATTERN : Pattern                                     │
├───────────────────────────────────────────────────────────────────┤
│ +isValidEmail(email) : boolean                [STATIC]            │
│ +isValidPhone(phone) : boolean                [STATIC]            │
│ +isValidDate(date) : boolean                  [STATIC]            │
│ +isValidScore(score) : boolean                [STATIC]            │
│ +isValidCredits(credits) : boolean            [STATIC]            │
│ +isValidGender(gender) : boolean              [STATIC]            │
│ +normalizeGender(gender) : String             [STATIC]            │
└───────────────────────────────────────────────────────────────────┘

┌───────────────────────────────────────────────────────────────────┐
│                      StatisticsUtils                              │
├───────────────────────────────────────────────────────────────────┤
│ +calculateAverageGrade(grades) : double       [STATIC]            │
│ +calculateAttendanceRate(attendances) : double [STATIC]           │
│ +countStudentsByClass(students) : Map         [STATIC]            │
│ +classifyGrade(total) : String                [STATIC]            │
│ +findMaxGrade(grades) : Grade                 [STATIC]            │
│ +findMinGrade(grades) : Grade                 [STATIC]            │
│ +displayGradeStatistics(grades) : void        [STATIC]            │
│ +displayStudentsByClass(students) : void      [STATIC]            │
│ +displayAttendanceStatistics(attendances) : void [STATIC]         │
└───────────────────────────────────────────────────────────────────┘

┌───────────────────────────────────────────────────────────────────┐
│                        ReportUtils                                │
├───────────────────────────────────────────────────────────────────┤
│ -REPORT_DIR : String = "reports/"                                 │
├───────────────────────────────────────────────────────────────────┤
│ +exportStudentsToCSV(students, filename) : void    [STATIC]       │
│ +exportTeachersToCSV(teachers, filename) : void    [STATIC]       │
│ +exportGradesToCSV(grades, filename) : void        [STATIC]       │
│ +exportAttendanceToCSV(attendances, filename) : void [STATIC]     │
│ +exportCoursesToCSV(courses, filename) : void      [STATIC]       │
│ +exportSummaryReport(students, grades, attendances, filename) : void │
└───────────────────────────────────────────────────────────────────┘
```

---

## 4. Main Class

```
┌───────────────────────────────────────────────────────────────────┐
│                            Main                                   │
├───────────────────────────────────────────────────────────────────┤
│ -studentManager : StudentManager                                  │
│ -teacherManager : TeacherManager                                  │
│ -courseManager : CourseManager                                    │
│ -classRoomManager : ClassRoomManager                              │
│ -enrollmentManager : EnrollmentManager                            │
│ -gradeManager : GradeManager                                      │
│ -attendanceManager : AttendanceManager                            │
│ -departmentManager : DepartmentManager                            │
│ -semesterManager : SemesterManager                                │
│ -userManager : UserAccountManager                                 │
│ -scanner : Scanner                                                │
│ -currentUser : UserAccount                                        │
├───────────────────────────────────────────────────────────────────┤
│ +main(args) : void                                 [PUBLIC STATIC]│
│ -showAuthMenu() : boolean                          [PRIVATE]      │
│ -showMainMenu() : void                             [PRIVATE]      │
│ -studentMenu() : void                              [PRIVATE]      │
│ -teacherMenu() : void                              [PRIVATE]      │
│ -courseMenu() : void                               [PRIVATE]      │
│ -classRoomMenu() : void                            [PRIVATE]      │
│ -enrollmentMenu() : void                           [PRIVATE]      │
│ -gradeMenu() : void                                [PRIVATE]      │
│ -attendanceMenu() : void                           [PRIVATE]      │
│ -departmentMenu() : void                           [PRIVATE]      │
│ -semesterMenu() : void                             [PRIVATE]      │
│ -userAccountMenu() : void                          [PRIVATE]      │
│ -statisticsMenu() : void                           [PRIVATE]      │
│ -reportMenu() : void                               [PRIVATE]      │
│ -changePasswordMenu() : void                       [PRIVATE]      │
│ -loadAllData() : void                              [PRIVATE]      │
│ -saveAllData() : void                              [PRIVATE]      │
│ -getIntInput(prompt) : int                         [PRIVATE]      │
│ -getDoubleInput(prompt) : double                   [PRIVATE]      │
└───────────────────────────────────────────────────────────────────┘
```

---

## 5. Quan Hệ Giữa Các Class

```
                    ┌─────────────┐
                    │ Department  │
                    └──────┬──────┘
                           │ 1
                           │
                           ▼ *
                    ┌─────────────┐         ┌─────────────┐
                    │   Teacher   │─────────│   Course    │
                    └──────┬──────┘    1  * └──────┬──────┘
                           │                       │
                           │ 1                     │ 1
                           ▼                       ▼
                    ┌─────────────┐         ┌─────────────┐
                    │  ClassRoom  │◀────────│  Semester   │
                    └──────┬──────┘         └─────────────┘
                           │
                           │ 1
                           ▼ *
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  Enrollment │◀────│   Student   │────▶│    Grade    │
└─────────────┘  *  └──────┬──────┘  *  └─────────────┘
                           │
                           │ 1
                           ▼ *
                    ┌─────────────┐
                    │  Attendance │
                    └─────────────┘


Chú thích:
─────────── Quan hệ liên kết
──────────▶ Quan hệ sử dụng (uses)
1 - * : Quan hệ một - nhiều
```

---

## 6. Bảng Tổng Kết

| Package | Số Class | Danh sách |
|---------|----------|-----------|
| models | 10 | Student, Teacher, Course, ClassRoom, Enrollment, Grade, Attendance, Department, Semester, UserAccount |
| managers | 10 | StudentManager, TeacherManager, CourseManager, ClassRoomManager, EnrollmentManager, GradeManager, AttendanceManager, DepartmentManager, SemesterManager, UserAccountManager |
| utils | 4 | PasswordUtils, ValidationUtils, StatisticsUtils, ReportUtils |
| (root) | 1 | Main |
| **TỔNG** | **25** | |

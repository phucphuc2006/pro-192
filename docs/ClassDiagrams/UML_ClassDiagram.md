# UML Class Diagram - Hệ Thống Quản Lý Sinh Viên

Biểu đồ dưới đây sử dụng **MermaidJS** để thể hiện cấu trúc Hướng đối tượng của hệ thống sau khi đã tái cấu trúc (Refactor). 
Bạn có thể copy đoạn code dưới đây dán vào [Mermaid Live Editor](https://mermaid.live/) hoặc chèn trực tiếp vào các file Markdown hỗ trợ Mermaid (như GitHub README).

```mermaid
classDiagram
    %% Lớp nền tảng Person
    class Person {
        <<abstract>>
        -String id
        -String fullName
        -String email
        -String phone
        +Person(id, fullName, email, phone)
        +getId() String
        +setId(id)
        +getFullName() String
        +setFullName(fullName)
        +getEmail() String
        +setEmail(email)
        +getPhone() String
        +setPhone(phone)
        +toString()* String
    }

    %% Thực thể
    class Student {
        -String dob
        -String gender
        -String classID
        +Student(id, fullName, email, phone, dob, gender, classID)
        +getDob() String
        +setDob(dob)
        +getGender() String
        +setGender(gender)
        +getClassID() String
        +setClassID(classID)
        +toString() String
    }

    class Course {
        -String courseID
        -String courseName
        -int credits
        +Course(courseID, courseName, credits)
        +getCourseID() String
        +setCourseID(courseID)
        +getCourseName() String
        +setCourseName(courseName)
        +getCredits() int
        +setCredits(credits)
        +toString() String
    }

    class Enrollment {
        -String enrollmentID
        -String studentID
        -String courseID
        -double grade
        +Enrollment(enrollmentID, studentID, courseID, grade)
        +getEnrollmentID() String
        +setEnrollmentID(enrollmentID)
        +getStudentID() String
        +setStudentID(studentID)
        +getCourseID() String
        +setCourseID(courseID)
        +getGrade() double
        +setGrade(grade)
        +toString() String
    }

    %% Interface M6
    class IManager~T~ {
        <<interface>>
        +add(T item)
        +update(String id, T item)
        +delete(String id)
        +getById(String id) T
        +getAll() List~T~
        +sort(Comparator~T~ comparator)
        +search(String keyword) List~T~
        +loadFromFile()
        +saveToFile()
    }

    %% Quản lý thực thể
    class StudentManager {
        -List~Student~ students
        -final String FILE_PATH
        +StudentManager()
    }

    class CourseManager {
        -List~Course~ courses
        -final String FILE_PATH
        +CourseManager()
    }

    class EnrollmentManager {
        -List~Enrollment~ enrollments
        -final String FILE_PATH
        +EnrollmentManager()
    }

    %% Controller / View CLI
    class Main {
        -StudentManager studentManager
        -CourseManager courseManager
        -EnrollmentManager enrollmentManager
        +Main()
        +main(String[] args)$
        +run()
        -showMainMenu()
        -manageStudents()
        -manageCourses()
        -manageEnrollments()
        -readNonEmptyString(prompt) String
        -readEmail() String
        -readValidGrade(prompt) double
    }

    %% Utilities tĩnh
    class ValidationUtils {
        <<utility>>
        +isValidId(String id)$ boolean
        +isValidEmail(String email)$ boolean
        +isValidGrade(double grade)$ boolean
        +isNotEmpty(String str)$ boolean
    }

    class DataGenerator {
        <<utility>>
        +generateAll()$
    }

    class InputHelper {
        <<utility>>
        -Scanner scanner$
        +readString(String prompt)$ String
        +readInt(String prompt)$ int
        +readInt(String prompt, int min, int max)$ int
        +readDouble(String prompt)$ double
        +readDouble(String prompt, double min, double max)$ double
    }

    %% Các quan hệ UML
    Person <|-- Student : Khai báo tính Kế Thừa (Inheritance)
    
    IManager~Student~ <|.. StudentManager : Implement (Realization)
    IManager~Course~ <|.. CourseManager : Implement (Realization)
    IManager~Enrollment~ <|.. EnrollmentManager : Implement (Realization)

    StudentManager "1" --> "*" Student : Quản lý (Association)
    CourseManager "1" --> "*" Course : Quản lý (Association)
    EnrollmentManager "1" --> "*" Enrollment : Quản lý (Association)

    Main *-- StudentManager : Chứa tập hợp (Composition)
    Main *-- CourseManager : Chứa tập hợp (Composition)
    Main *-- EnrollmentManager : Chứa tập hợp (Composition)

    Main ..> ValidationUtils : Phụ thuộc (Dependency)
    Main ..> InputHelper : Phụ thuộc (Dependency)
    Main ..> DataGenerator : Phụ thuộc (Dependency)
```

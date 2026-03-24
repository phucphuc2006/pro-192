# PROPOSAL: STUDENT & COURSE MANAGEMENT SYSTEM
## (Student & Course Management System)

---

## 1. Introduction

The Student & Course Management System is a Console Application (CLI) developed in Java, supporting three main business operations:
1. Student Management
2. Course Management
3. Enrollment & Grade Management

The project strictly complies with Object-Oriented Programming (OOP) principles and implements a simplified MVC architecture. The design is streamlined to achieve 100% of the criteria from the PRO192 Rubric.

---

## 2. System Architecture

The system is organized into 4 main packages:

### A. Console Interface
- **Main.java**: Contains the main method and user navigation menu (CLI).

### B. Package `models` (Data Entities)

| Class | Description | Main Attributes |
|-------|-------|------------------|
| Person | Person (Abstract Class) | id, fullName, email, phone |
| Student | Student (Inherits from Person)| dob, gender, classID |
| Course | Course | courseID, courseName, credits |
| Enrollment | Enrollment Record | enrollmentID, studentID, courseID, grade |

### C. Package `managers` (Business Logic & Data Scaling)

- **`IManager<T>`**: Generics Interface defining standard operations (CRUD, Sort, Search, File I/O).
- `StudentManager` (Implements `IManager<Student>`)
- `CourseManager` (Implements `IManager<Course>`)
- `EnrollmentManager` (Implements `IManager<Enrollment>`)

=> Utilizes the `ArrayList` data structure from the Java Collections Framework to flexibly handle thousands of records (meeting Data Scaling criteria).

### D. Package `utils` (Utilities)

| Class | Description | Methods |
|-------|-------|-------------|
| InputHelper | Input helper with Try/Catch logic | readString(), readInt(), readDouble() |
| DataGenerator | Bootstraps 100+ lines of data upon startup | generateAll() |
| ValidationUtils | Static Methods for data validation | isValidId(), isValidEmail(), isValidGrade(), isNotEmpty() |

---

## 3. Key Features

### 3.1. Object Management (CRUD)
- Create, Read/Update, Delete, and Display lists for Students, Courses, and Enrollments.

### 3.2. Processing Utilities
- **Search:** Find by ID, Student Name, or Course.
- **Sort:** Automatically sort the Student list by name.
- **Validation:** Disallows empty information, catches invalid number/email formats.

### 3.3. File I/O (Persistence)
- Save all data into 3 text files (`students.txt`, `courses.txt`, `enrollments.txt`) inside the `data/` directory.
- Automatically load data into an ArrayList upon startup.

---

## 4. Course Rubric Fulfillment

| Standard | Demonstrated in Code |
|-----------|-------------------------|
| **Encapsulation** | Securely encapsulates attributes (`private`) across all Entities. |
| **Inheritance** | Implements inheritance: `Student extends Person`. |
| **Polymorphism** | Method overriding (`@Override toString()`). Polymorphic implementation via Interface (`IManager`). |
| **Abstraction** | Extracts person information through the Abstract Class `Person` and `IManager` interface. |
| **Data Scaling** | Auto-generates 100 Test Samples whenever the program is launched. |
| **Static Utility**| `ValidationUtils` contains static boolean functions for standard data validation. |

---

package ui;

import managers.*;
import models.*;
import utils.InputHelper;

public class ConsoleMenu {
    private StudentManager studentManager;
    private TeacherManager teacherManager;
    private CourseManager courseManager;
    private ClassRoomManager classRoomManager;
    private EnrollmentManager enrollmentManager;
    private GradeManager gradeManager;
    private AttendanceManager attendanceManager;
    private DepartmentManager departmentManager;
    private SemesterManager semesterManager;

    public ConsoleMenu() {
        this.studentManager = new StudentManager();
        this.teacherManager = new TeacherManager();
        this.courseManager = new CourseManager();
        this.classRoomManager = new ClassRoomManager();
        this.enrollmentManager = new EnrollmentManager();
        this.gradeManager = new GradeManager();
        this.attendanceManager = new AttendanceManager();
        this.departmentManager = new DepartmentManager();
        this.semesterManager = new SemesterManager();
    }

    public void run() {
        System.out.println("Welcome to Student Management System");
        while (true) {
            showMainMenu();
        }
    }

    private void showMainMenu() {
        System.out.println("\n--- MAIN DASHBOARD ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Teachers");
        System.out.println("3. Manage Courses");
        System.out.println("4. Manage Classes");
        System.out.println("5. Manage Enrollments");
        System.out.println("6. Manage Grades");
        System.out.println("7. Manage Attendance");
        System.out.println("8. Manage Departments");
        System.out.println("9. Manage Semesters");
        System.out.println("0. Exit");

        int choice = InputHelper.readInt("Choose option", 0, 9);
        switch (choice) {
            case 0:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            case 1:
                manageStudents();
                break;
            case 2:
                manageTeachers();
                break;
            case 3:
                manageCourses();
                break;
            case 4:
                manageClasses();
                break;
            case 5:
                manageEnrollments();
                break;
            case 6:
                manageGrades();
                break;
            case 7:
                manageAttendance();
                break;
            case 8:
                manageDepartments();
                break;
            case 9:
                manageSemesters();
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private void manageStudents() {
        System.out.println("\n--- STUDENT MANAGEMENT ---");
        System.out.println("1. Add Student");
        System.out.println("2. Update Student");
        System.out.println("3. Delete Student");
        System.out.println("4. List All Students");
        System.out.println("0. Back");
        int choice = InputHelper.readInt("Choose", 0, 4);
        switch (choice) {
            case 1:
                studentManager.addStudent(new Student(
                        InputHelper.readString("ID"),
                        InputHelper.readString("Full Name"),
                        InputHelper.readString("Email"),
                        InputHelper.readString("Phone"),
                        InputHelper.readString("DOB(dd/MM/yyyy)"),
                        InputHelper.readString("Gender"),
                        InputHelper.readString("Class ID")));
                System.out.println("Added successfully.");
                break;
            case 2:
                String id = InputHelper.readString("Enter Student ID to update");
                if (studentManager.getStudentById(id) != null) {
                    studentManager.updateStudent(id, new Student(
                            id,
                            InputHelper.readString("New Name"),
                            InputHelper.readString("New Email"),
                            InputHelper.readString("New Phone"),
                            InputHelper.readString("New DOB"),
                            InputHelper.readString("New Gender"),
                            InputHelper.readString("New Class ID")));
                    System.out.println("Updated.");
                } else
                    System.out.println("Not found.");
                break;
            case 3:
                studentManager.deleteStudent(InputHelper.readString("Enter ID to delete"));
                System.out.println("Deleted.");
                break;
            case 4:
                for (Student s : studentManager.getAllStudents())
                    System.out.println(s);
                break;
        }
    }

    private void manageTeachers() {
        System.out.println("--- TEACHERS ---");
        System.out.println("1. List All");
        System.out.println("2. Add Teacher");
        System.out.println("0. Back");
        int c = InputHelper.readInt("Option", 0, 2);
        if (c == 1)
            for (Teacher t : teacherManager.getAll())
                System.out.println(t);
        else if (c == 2) {
            teacherManager.add(new Teacher(
                    InputHelper.readString("ID"),
                    InputHelper.readString("Name"),
                    InputHelper.readString("Email"),
                    InputHelper.readString("Phone"),
                    InputHelper.readString("Dept")));
        }
    }

    private void manageCourses() {
        System.out.println("--- COURSES ---");
        System.out.println("1. List All");
        System.out.println("2. Add Course");
        System.out.println("0. Back");
        int c = InputHelper.readInt("Option", 0, 2);
        if (c == 1)
            for (Course x : courseManager.getAll())
                System.out.println(x);
        else if (c == 2) {
            courseManager.add(new Course(
                    InputHelper.readString("ID"),
                    InputHelper.readString("Name"),
                    InputHelper.readInt("Credits"),
                    InputHelper.readString("Semester"),
                    InputHelper.readString("TeacherID")));
        }
    }

    private void manageClasses() {
        System.out.println("--- CLASSES ---");
        System.out.println("1. List All");
        System.out.println("2. Add Class");
        // ...
        int c = InputHelper.readInt("Option", 0, 2);
        if (c == 1)
            for (ClassRoom x : classRoomManager.getAll())
                System.out.println(x);
        else if (c == 2)
            classRoomManager.add(new ClassRoom(InputHelper.readString("ID"), InputHelper.readString("Name"),
                    InputHelper.readString("TeacherID"), InputHelper.readString("CourseID")));
    }

    private void manageEnrollments() {
        System.out.println("--- ENROLLMENT MANAGEMENT ---");
        System.out.println("1. List All");
        System.out.println("2. Add Enrollment");
        System.out.println("0. Back");
        int c = InputHelper.readInt("Option", 0, 2);
        if (c == 1) {
            for (Enrollment e : enrollmentManager.getAll())
                System.out.println(e);
        } else if (c == 2) {
            enrollmentManager.add(new Enrollment(
                    InputHelper.readString("Enrollment ID"),
                    InputHelper.readString("Student ID"),
                    InputHelper.readString("Course ID"),
                    InputHelper.readString("Semester")));
            System.out.println("Enrollment added.");
        }
    }

    private void manageGrades() {
        System.out.println("--- GRADE MANAGEMENT ---");
        System.out.println("1. List All");
        System.out.println("2. Add Grade");
        System.out.println("0. Back");
        int c = InputHelper.readInt("Option", 0, 2);
        if (c == 1) {
            for (Grade g : gradeManager.getAll())
                System.out.println(g);
        } else if (c == 2) {
            gradeManager.add(new Grade(
                    InputHelper.readString("Grade ID"),
                    InputHelper.readString("Student ID"),
                    InputHelper.readString("Course ID"),
                    InputHelper.readDouble("Midterm Grade"),
                    InputHelper.readDouble("Final Exam Grade")));
            System.out.println("Grade added and total calculated.");
        }
    }

    private void manageAttendance() {
        System.out.println("--- ATTENDANCE MANAGEMENT ---");
        System.out.println("1. List All");
        System.out.println("2. Add Attendance");
        System.out.println("0. Back");
        int c = InputHelper.readInt("Option", 0, 2);
        if (c == 1) {
            for (Attendance a : attendanceManager.getAll())
                System.out.println(a);
        } else if (c == 2) {
            attendanceManager.add(new Attendance(
                    InputHelper.readString("Attendance ID"),
                    InputHelper.readString("Student ID"),
                    InputHelper.readString("Class ID"),
                    InputHelper.readString("Date (dd/mm/yyyy)"),
                    InputHelper.readString("Status (Present/Absent/Excused)")));
            System.out.println("Attendance recorded.");
        }
    }

    private void manageDepartments() {
        System.out.println("--- DEPARTMENT MANAGEMENT ---");
        System.out.println("1. List All");
        System.out.println("2. Add Department");
        System.out.println("0. Back");
        int c = InputHelper.readInt("Option", 0, 2);
        if (c == 1) {
            for (Department d : departmentManager.getAll())
                System.out.println(d);
        } else if (c == 2) {
            departmentManager.add(new Department(
                    InputHelper.readString("Department ID"),
                    InputHelper.readString("Department Name"),
                    InputHelper.readInt("Faculty Count")));
            System.out.println("Department added.");
        }
    }

    private void manageSemesters() {
        System.out.println("--- SEMESTER MANAGEMENT ---");
        System.out.println("1. List All");
        System.out.println("2. Add Semester");
        System.out.println("0. Back");
        int c = InputHelper.readInt("Option", 0, 2);
        if (c == 1) {
            for (Semester s : semesterManager.getAll())
                System.out.println(s);
        } else if (c == 2) {
            semesterManager.add(new Semester(
                    InputHelper.readString("Semester ID"),
                    InputHelper.readString("Semester Name"),
                    InputHelper.readString("Start Date (dd/mm/yyyy)"),
                    InputHelper.readString("End Date (dd/mm/yyyy)")));
            System.out.println("Semester added.");
        }
    }
}

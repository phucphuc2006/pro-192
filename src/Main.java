import managers.*;
import models.*;
import utils.DataGenerator;
import utils.InputHelper;
import utils.ValidationUtils;

import java.util.Comparator;

public class Main {
    private StudentManager studentManager;
    private CourseManager courseManager;
    private EnrollmentManager enrollmentManager;

    public Main() {
        this.studentManager = new StudentManager();
        this.courseManager = new CourseManager();
        this.enrollmentManager = new EnrollmentManager();
    }

    public static void main(String[] args) {
        System.out.println("Loading databases and generating mock data if empty...");
        DataGenerator.generateAll();

        Main app = new Main();
        app.run();
    }

    public void run() {
        System.out.println("==========================================");
        System.out.println("WELCOME TO STUDENT/COURSE MANAGEMENT SYSTEM");
        System.out.println("==========================================");
        while (true) {
            showMainMenu();
        }
    }

    private void showMainMenu() {
        System.out.println("\n--- MAIN DASHBOARD ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments & Grades");
        System.out.println("0. Exit");

        int choice = InputHelper.readInt("Choose option", 0, 3);
        switch (choice) {
            case 0:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            case 1:
                manageStudents();
                break;
            case 2:
                manageCourses();
                break;
            case 3:
                manageEnrollments();
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
        System.out.println("5. Search Student");
        System.out.println("6. Sort Students by Name");
        System.out.println("0. Back");
        int choice = InputHelper.readInt("Choose option", 0, 6);
        switch (choice) {
            case 1:
                String id, email;
                do {
                    id = InputHelper.readString("ID (Format: S001)");
                    if (!ValidationUtils.isValidId(id))
                        System.out.println("Invalid ID form");
                } while (!ValidationUtils.isValidId(id));

                if (studentManager.getById(id) != null) {
                    System.out.println("Student ID already exists!");
                    break;
                }

                String name = readNonEmptyString("Full Name");
                do {
                    email = InputHelper.readString("Email");
                    if (!ValidationUtils.isValidEmail(email))
                        System.out.println("Invalid email format");
                } while (!ValidationUtils.isValidEmail(email));

                studentManager.add(new Student(
                        id, name, email,
                        readNonEmptyString("Phone"),
                        readNonEmptyString("DOB (dd/mm/yyyy)"),
                        readNonEmptyString("Gender"),
                        readNonEmptyString("Class ID")));
                System.out.println("Student added.");
                break;
            case 2:
                String updateId = InputHelper.readString("Enter Student ID to update");
                if (studentManager.getById(updateId) != null) {
                    studentManager.update(updateId, new Student(
                            updateId,
                            readNonEmptyString("New Full Name"),
                            readEmail(),
                            readNonEmptyString("New Phone"),
                            readNonEmptyString("New DOB"),
                            readNonEmptyString("New Gender"),
                            readNonEmptyString("New Class ID")));
                    System.out.println("Student updated.");
                } else {
                    System.out.println("Student not found.");
                }
                break;
            case 3:
                String delId = InputHelper.readString("Enter Student ID to delete");
                if (studentManager.getById(delId) != null) {
                    studentManager.delete(delId);
                    System.out.println("Student deleted.");
                } else {
                    System.out.println("Student not found.");
                }
                break;
            case 4:
                studentManager.getAll().forEach(System.out::println);
                break;
            case 5:
                String keyword = readNonEmptyString("Enter keyword (ID or Name)");
                studentManager.search(s -> s.getFullName().toLowerCase().contains(keyword.toLowerCase()) ||
                        s.getId().toLowerCase().contains(keyword.toLowerCase()))
                        .forEach(System.out::println);
                break;
            case 6:
                studentManager.sort(Comparator.comparing(Student::getFullName));
                System.out.println("Sorted.");
                studentManager.getAll().forEach(System.out::println);
                break;

        }
    }

    private void manageCourses() {
        System.out.println("\n--- COURSE MANAGEMENT ---");
        System.out.println("1. Add Course");
        System.out.println("2. Update Course");
        System.out.println("3. Delete Course");
        System.out.println("4. List All Courses");
        System.out.println("5. Search Course");
        System.out.println("0. Back");
        int choice = InputHelper.readInt("Choose option", 0, 5);
        switch (choice) {
            case 1:
                String id = readNonEmptyString("Course ID");
                if (courseManager.getById(id) != null) {
                    System.out.println("Course ID already exists!");
                    break;
                }
                courseManager.add(new Course(
                        id,
                        readNonEmptyString("Course Name"),
                        InputHelper.readInt("Credits")));
                System.out.println("Course added.");
                break;
            case 2:
                String updateId = InputHelper.readString("Enter Course ID to update");
                if (courseManager.getById(updateId) != null) {
                    courseManager.update(updateId, new Course(
                            updateId,
                            readNonEmptyString("New Course Name"),
                            InputHelper.readInt("New Credits")));
                    System.out.println("Course updated.");
                } else {
                    System.out.println("Course not found.");
                }
                break;
            case 3:
                String delId = InputHelper.readString("Enter Course ID to delete");
                if (courseManager.getById(delId) != null) {
                    courseManager.delete(delId);
                    System.out.println("Course deleted.");
                } else {
                    System.out.println("Course not found.");
                }
                break;
            case 4:
                courseManager.getAll().forEach(System.out::println);
                break;
            case 5:
                String keyword = readNonEmptyString("Enter keyword (ID or Name)");
                courseManager.search(c -> c.getCourseName().toLowerCase().contains(keyword.toLowerCase()) ||
                        c.getId().toLowerCase().contains(keyword.toLowerCase()))
                        .forEach(System.out::println);
                break;
        }
    }

    private void manageEnrollments() {
        System.out.println("\n--- ENROLLMENT & GRADE MANAGEMENT ---");
        System.out.println("1. Enroll Student to Course");
        System.out.println("2. Update Grade");
        System.out.println("3. Cancel Enrollment");
        System.out.println("4. List All Enrollments");
        System.out.println("5. Search Enrollment by Student/Course ID");
        System.out.println("0. Back");
        int choice = InputHelper.readInt("Choose option", 0, 5);
        switch (choice) {
            case 1:
                String id = readNonEmptyString("Enrollment ID");
                if (enrollmentManager.getById(id) != null) {
                    System.out.println("Enrollment ID already exists!");
                    break;
                }
                String studentId = readNonEmptyString("Student ID");
                if (studentManager.getById(studentId) == null) {
                    System.out.println("Student not found!");
                    break;
                }
                String courseId = readNonEmptyString("Course ID");
                if (courseManager.getById(courseId) == null) {
                    System.out.println("Course not found!");
                    break;
                }
                double grade = readValidGrade("Grade (0-10)");
                enrollmentManager.add(new Enrollment(id, studentId, courseId, grade));
                System.out.println("Enrollment added.");
                break;
            case 2:
                String updateId = InputHelper.readString("Enter Enrollment ID to update grade");
                Enrollment e = enrollmentManager.getById(updateId);
                if (e != null) {
                    e.setGrade(readValidGrade("New Grade"));
                    enrollmentManager.update(updateId, e);
                    System.out.println("Grade updated.");
                } else {
                    System.out.println("Enrollment not found.");
                }
                break;
            case 3:
                String delId = InputHelper.readString("Enter Enrollment ID to cancel");
                if (enrollmentManager.getById(delId) != null) {
                    enrollmentManager.delete(delId);
                    System.out.println("Enrollment canceled.");
                } else {
                    System.out.println("Enrollment not found.");
                }
                break;
            case 4:
                enrollmentManager.getAll().forEach(System.out::println);
                break;
            case 5:
                String keyword = readNonEmptyString("Enter Student/Course ID to search");
                enrollmentManager.search(en -> en.getStudentID().toLowerCase().contains(keyword.toLowerCase()) ||
                        en.getCourseID().toLowerCase().contains(keyword.toLowerCase()))
                        .forEach(System.out::println);
                break;
        }
    }

    private String readNonEmptyString(String prompt) {
        String input;
        do {
            input = InputHelper.readString(prompt);
            if (!ValidationUtils.isNotEmpty(input))
                System.out.println("Input cannot be empty.");
        } while (!ValidationUtils.isNotEmpty(input));
        return input;
    }

    private String readEmail() {
        String email;
        do {
            email = InputHelper.readString("Email");
            if (!ValidationUtils.isValidEmail(email))
                System.out.println("Invalid email format.");
        } while (!ValidationUtils.isValidEmail(email));
        return email;
    }

    private double readValidGrade(String prompt) {
        double grade;
        do {
            grade = InputHelper.readDouble(prompt);
            if (!ValidationUtils.isValidGrade(grade))
                System.out.println("Grade must be between 0 and 10.");
        } while (!ValidationUtils.isValidGrade(grade));
        return grade;
    }
}

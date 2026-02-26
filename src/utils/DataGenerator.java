package utils;

import models.*;
import managers.*;
import java.util.Random;

public class DataGenerator {
    public static void main(String[] args) {
        generateAll();
    }

    public static void generateAll() {
        Random rand = new Random();

        // 1. Departments
        DepartmentManager deptMgr = new DepartmentManager();
        for (int i = 1; i <= 100; i++) {
            deptMgr.add(new Department("D" + i, "Department " + i, rand.nextInt(20) + 5));
        }

        // 2. Semesters
        SemesterManager semMgr = new SemesterManager();
        for (int i = 1; i <= 100; i++) {
            semMgr.add(new Semester("SEM" + i, "Semester " + i, "01/01/202" + (i % 9), "31/05/202" + (i % 9)));
        }

        // 3. Teachers
        TeacherManager teacherMgr = new TeacherManager();
        for (int i = 1; i <= 100; i++) {
            teacherMgr.add(new Teacher("T" + i, "Teacher Name " + i, "teacher" + i + "@school.edu",
                    "0912345" + String.format("%03d", i), "Department " + (rand.nextInt(10) + 1)));
        }

        // 4. Students
        StudentManager studentMgr = new StudentManager();
        for (int i = 1; i <= 100; i++) {
            studentMgr.addStudent(new Student("S" + i, "Student Name " + i, "student" + i + "@gmail.com",
                    "0987654" + String.format("%03d", i), "15/05/200" + (rand.nextInt(5)),
                    (i % 2 == 0 ? "Male" : "Female"), "CLASS" + (rand.nextInt(10) + 1)));
        }

        // 5. Courses
        CourseManager courseMgr = new CourseManager();
        for (int i = 1; i <= 100; i++) {
            courseMgr.add(new Course("CRS" + i, "Course Title " + i, rand.nextInt(4) + 1, "SEM" + (rand.nextInt(5) + 1),
                    "T" + (rand.nextInt(50) + 1)));
        }

        // 6. ClassRooms
        ClassRoomManager classMgr = new ClassRoomManager();
        for (int i = 1; i <= 100; i++) {
            classMgr.add(new ClassRoom("CLASS" + i, "Room " + i + "A", "T" + (rand.nextInt(20) + 1),
                    "CRS" + (rand.nextInt(20) + 1)));
        }

        // 7. Enrollments
        EnrollmentManager enrollMgr = new EnrollmentManager();
        for (int i = 1; i <= 100; i++) {
            enrollMgr.add(new Enrollment("E" + i, "S" + (rand.nextInt(100) + 1), "CRS" + (rand.nextInt(50) + 1),
                    "SEM" + (rand.nextInt(5) + 1)));
        }

        // 8. Grades
        GradeManager gradeMgr = new GradeManager();
        for (int i = 1; i <= 100; i++) {
            gradeMgr.add(new Grade("G" + i, "S" + i, "CRS" + (rand.nextInt(20) + 1), rand.nextDouble() * 10,
                    rand.nextDouble() * 10));
        }

        // 9. Attendance
        AttendanceManager attMgr = new AttendanceManager();
        for (int i = 1; i <= 100; i++) {
            attMgr.add(new Attendance("A" + i, "S" + (rand.nextInt(100) + 1), "CLASS" + (rand.nextInt(10) + 1),
                    "02/02/2026", (rand.nextInt(10) > 2 ? "Present" : "Absent")));
        }

        System.out.println("Successfully generated 100 records for each entity!");
    }
}

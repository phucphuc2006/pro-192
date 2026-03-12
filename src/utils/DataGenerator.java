package utils;

import models.*;
import managers.*;
import java.util.Random;

public class DataGenerator {

    public static void generateAll() {
        Random rand = new Random();

        // 1. Students
        StudentManager studentMgr = new StudentManager();
        if (studentMgr.getAll().isEmpty()) {
            for (int i = 1; i <= 100; i++) {
                studentMgr.add(
                        new Student("S" + String.format("%03d", i), "Student Name " + i, "student" + i + "@fpt.edu.vn",
                                "0987654" + String.format("%03d", i), "15/05/200" + (rand.nextInt(5)),
                                (i % 2 == 0 ? "Male" : "Female"), "SE" + (rand.nextInt(10) + 1)));
            }
            System.out.println("Generated 100 Students.");
        }

        // 2. Courses
        CourseManager courseMgr = new CourseManager();
        if (courseMgr.getAll().isEmpty()) {
            for (int i = 1; i <= 100; i++) {
                courseMgr.add(
                        new Course("PRO" + String.format("%03d", i), "Programming Course " + i, rand.nextInt(4) + 1));
            }
            System.out.println("Generated 100 Courses.");
        }

        // 3. Enrollments
        EnrollmentManager enrollMgr = new EnrollmentManager();
        if (enrollMgr.getAll().isEmpty()) {
            for (int i = 1; i <= 100; i++) {
                enrollMgr.add(new Enrollment("E" + String.format("%03d", i),
                        "S" + String.format("%03d", rand.nextInt(100) + 1),
                        "PRO" + String.format("%03d", rand.nextInt(100) + 1),
                        Math.round(rand.nextDouble() * 10.0 * 100.0) / 100.0));
            }
            System.out.println("Generated 100 Enrollments.");
        }

        System.out.println("Data Generation Complete!");
    }
}

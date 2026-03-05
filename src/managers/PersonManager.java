package managers;

import models.Person;
import models.Student;
import models.Teacher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonManager {
    private List<Person> persons;
    private final String FILE_PATH = "data/persons.txt";

    public PersonManager() {
        this.persons = new ArrayList<>();
        loadFromFile();
    }

    public void addPerson(Person person) {
        persons.add(person);
        saveToFile();
    }

    public void updatePerson(String id, Person newPerson) {
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getId().equals(id)) {
                persons.set(i, newPerson);
                saveToFile();
                return;
            }
        }
    }

    public void deletePerson(String id) {
        persons.removeIf(p -> p.getId().equals(id));
        saveToFile();
    }

    public Person getPersonById(String id) {
        for (Person p : persons) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public List<Person> getAllPersons() {
        return persons;
    }

    private void loadFromFile() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                String type = parts[0].trim();

                if (type.equals("Student") && parts.length >= 8) {
                    persons.add(new Student(
                            parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim(),
                            parts[5].trim(), parts[6].trim(), parts[7].trim()));
                } else if (type.equals("Teacher") && parts.length >= 6) {
                    persons.add(new Teacher(
                            parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim(),
                            parts[5].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading persons: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Person p : persons) {
                if (p instanceof Student) {
                    Student s = (Student) p;
                    bw.write(String.format("Student,%s,%s,%s,%s,%s,%s,%s",
                            s.getId(), s.getFullName(), s.getEmail(), s.getPhone(),
                            s.getDob(), s.getGender(), s.getClassID()));
                } else if (p instanceof Teacher) {
                    Teacher t = (Teacher) p;
                    bw.write(String.format("Teacher,%s,%s,%s,%s,%s",
                            t.getId(), t.getFullName(), t.getEmail(), t.getPhone(),
                            t.getDepartment()));
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving persons: " + e.getMessage());
        }
    }
}

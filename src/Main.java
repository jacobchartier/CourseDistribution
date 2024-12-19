import entities.Department;
import util.file.*;

import java.io.File;
import java.util.*;

public class Main {
    private static final String PROFESSORS_FILE = "\\professors.txt";
    private static final String COURSES_FILE = "\\ListeDeCours.csv";

    public static void main(String[] args) {
        // C:\Users\Jacob Chartier\Desktop\Data

        File directory = new File(getWorkingPath());

        try {
            Department department = new Department(ProfessorHandler.getProfessors(directory + PROFESSORS_FILE));
            department.setCourseMap(CourseHandler.getCourses(directory + COURSES_FILE));
            department.processChoices(directory.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static String getWorkingPath() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter the directory path: ");
        return scanner.nextLine();
    }
}
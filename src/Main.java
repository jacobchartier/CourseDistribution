import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String PROFESSORS_FILE = "\\professors.txt";
    private static final String COURSES_FILE = "\\ListeDeCours.csv";

    public static void main(String[] args) {
        // C:\Users\Jacob Chartier\Desktop\Data

        File directory = /*new File(getWorkingPath());*/ new File("C:\\Users\\Jacob Chartier\\Desktop\\Data");
        File[] listOfFiles = directory.listFiles();

        try {
            ArrayList<Professor> professors = Professor.getProfessorsFromFile(directory + PROFESSORS_FILE);
            HashMap<String, List<Course>> courses = Course.getCoursesFromFile(directory + COURSES_FILE);

            for (HashMap.Entry<String, List<Course>> entry : courses.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
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
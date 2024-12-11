import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String PROFESSORS_FILE = "\\professors.txt";
    private static final String COURSES_FILE = "\\ListeDeCours.csv";

    public static void main(String[] args) {
        // C:\Users\Jacob Chartier\Desktop\Data

        File directory = new File(getWorkingPath()); // new File("C:\\Users\\Jacob Chartier\\Desktop\\Data");
        File[] listOfFiles = directory.listFiles();

        try {
            ArrayList<Professor> professors = Professor.getProfessorsFromFile(directory + PROFESSORS_FILE);



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

    private static void processCourses(String path) {
        System.out.println("\n--> Processing " + path + "\n");

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
package util.file;

import entities.*;
import util.ConversionHelper;
import util.Debug;

import java.io.*;
import java.util.*;

public class ProfessorHandler {

    private static final String PROFESSOR_OUTPUT_FILE = "professors_finalAssignments.txt";

    public static ArrayList<Professor> getProfessors(String path) throws IOException {
        Debug.printLoggingHeader(path);
        ArrayList<Professor> professors = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(":");

                String id = tokens[0];
                String name = tokens[1];
                float seniority = 0.00f;
                Set<String> disciplines = getDisciplines(tokens);

                if (!ConversionHelper.toFloat(tokens[2])) {
                    Debug.log(Debug.type.REJECTED, name + " (" + id + ") didn't provide a valid seniority. (Value provided: " + tokens[2] + ")");
                    continue;
                }

                seniority = Float.parseFloat(tokens[2]);

                if (disciplines.isEmpty()) {
                    Debug.log(Debug.type.REJECTED, name + " (" + id + ") didn't provide any disciplines.");
                    continue;
                }

                professors.add(new Professor(id, name, seniority, disciplines));
            }

            br.close();
            Debug.log(Debug.type.SUCCESS, professors.size() + " professors successfully retrieved from file.");

            Collections.sort(professors);
            Collections.reverse(professors);

            return professors;
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static Set<String> getDisciplines(String[] data) {
        Set<String> disciplines = new HashSet<>();
        String[] output = data[data.length - 1].split("[|,]");

        for (String s : output) {
            if (s.startsWith("0") && output.length == 1) {
                break;
            }

            if (!(s.trim().matches("[A-Za-z]{1,2}(-|)[0-9]{1,2}"))) {
                Debug.log(Debug.type.IGNORED, data[1] + " (" + data[0] + ") didn't provide a valid discipline. (Value provided: " + s.trim() + ")");
                continue;
            }

            s = s.replaceAll("([A-Za-z]{2})(0*)([0-9]{1,2})", "$1-$3");
            disciplines.add(s.trim());
        }

        return disciplines;
    }

    public static void generateProfessorsOutputFile(String path, ArrayList<Professor> professors) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + "\\" + PROFESSOR_OUTPUT_FILE))) {
            for (Professor professor : professors) {

                int affectedHours = 0;
                for (Course course : professor.getAffectedCourses()) {
                    affectedHours += (course.getHours() / 15);
                }

                bw.write(professor.getId() + " | " + professor.getRequestedHours() + ", " + affectedHours + " | " + professor.getAffectedCourses().size());
                bw.newLine();
            }

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}

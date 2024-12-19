package entities;

import util.*;
import util.file.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Department {
    private final int DEFAULT_PREFERRED_HOURS = 24;
    private final int MAX_HOURS = 30;

    private HashMap<String, List<Course>> courseMap;
    private ArrayList<Professor> professorList;

    public Department(ArrayList<Professor> professors) {
        this.professorList = professors;
       // this.courseMap = new HashMap<>();
    }

    public void setCourseMap(HashMap<String, List<Course>> courseMap) {
        this.courseMap = courseMap;
    }

    public void processChoices(String path) {
        File directory = new File(path);

        for (Professor professor : professorList) {
            int preferredHours = 0;
            int accumulatedHours = 0;
            List<Course> affectedCoursesList = new ArrayList<>();

            String line = null;
            int currentLine = 0;

            Debug.printLoggingHeader(path + "\\" + professor.getId() + "_request.txt");

            try (BufferedReader br = new BufferedReader(new FileReader(directory + "\\" + professor.getId() + "_request.txt"))) {
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(":");

                    String id;
                    String discipline;
                    String language;
                    byte groups;

                    currentLine++;

                    // Validation for hours
                    if (currentLine == 1) {
                        Matcher matcher = Pattern.compile("\\d+").matcher(tokens[0]);

                        if (matcher.find()) {
                            preferredHours = Integer.parseInt(matcher.group());

                            if (preferredHours > MAX_HOURS) {
                                preferredHours = MAX_HOURS;
                                Debug.log(Debug.type.MODIFIED, professor.getName() + " (" + professor.getId() + ") exceeded the maximum number of hours, it has been set to " + MAX_HOURS + ". (Value provided: " + tokens[0] + ")");
                            }
                        }
                        else {
                            preferredHours = DEFAULT_PREFERRED_HOURS;
                            Debug.log(Debug.type.MODIFIED, professor.getName() + " (" + professor.getId() + ") didn't provide a valid number for preferred hours, it has been set to " + DEFAULT_PREFERRED_HOURS + ". (Value provided: " + tokens[0] + ")");
                        }

                        professor.setRequestedHours(preferredHours);
                        continue;
                    }

                    id = tokens[0];
                    discipline = tokens[2];
                    language = tokens[tokens.length - 2];
                    groups = 1;

                    // Validation for the course id
                    if (id.matches("!([0-9]{3})([A-Za-z0-9]{3})([A-Za-z]{2})")) {
                        Debug.log(Debug.type.REJECTED, "The course id is not in a correct format. (Value provided: " + id + ")");
                        continue;
                    }

                    if (!courseMap.containsKey(id) && !tokens[0].startsWith("NaN")) {
                        Debug.log(Debug.type.REJECTED, "The course with id " + id + " is not offered during this semester or doesn't have anymore groups.");
                        continue;
                    }

                    // Convert the language to a proper format
                    if (language.contains("French")) {
                        language = "FR";
                    }

                    if (language.contains("English")) {
                        language = "AN";
                    }

                    // Validation for groups
                    if (!ConversionHelper.toByte(tokens[tokens.length - 1])) {
                        if (!tokens[tokens.length - 1].startsWith("NaN"))
                            Debug.log(Debug.type.REJECTED, id + " doesn't provide a valid number for groups. (Value provided: " + tokens[tokens.length - 1] + ")");

                        continue;
                    }

                    groups = Byte.parseByte(tokens[tokens.length - 1]);

                    // Check if the professor has the discipline
                    discipline = discipline.replaceAll("([A-Za-z]{2})(0*)([0-9]{1,2})", "$1-$3");

                    if (!professor.getDisciplines().contains(discipline) && !tokens[0].startsWith("NaN")) {
                        Debug.log(Debug.type.REJECTED, professor.getName() + " (" + professor.getId() + ") doesn't have the discipline " + discipline + ".");
                        continue;
                    }

                    // Match the professor with a course
                    for (Course course : courseMap.get(id)) {
                        if (course.getLanguage().equals(language) && course.getGroups() > 0) {
                            int hoursPerWeek = course.getHours() / 15;

                            // Determine the number of group I can assign to the professor
                            if (accumulatedHours + hoursPerWeek < professor.getRequestedHours()) {
                                course.setGroups((byte) (course.getGroups() - 1));

                                accumulatedHours += hoursPerWeek;
                                // Create a course copy
                                affectedCoursesList.add(course);
                                Debug.log(Debug.type.SUCCESS, professor.getName() + " (" + professor.getId() + ") has been matched with " + course.getTitle() + " (" + course.getLanguage() + ")");
                            }
                        }
                    }
                }
                professor.setAffectedCourses(affectedCoursesList);

            } catch (IOException exception) {
                throw new UncheckedIOException(exception);
            }
        }

        Debug.log(Debug.type.OTHER, "");
        Debug.log(Debug.type.OTHER, "All professors has been processed. Generating output files...");

        ProfessorHandler.generateProfessorsOutputFile(path, professorList);
        Debug.log(Debug.type.INFORMATION, "File \"" + path + "\\professors_finalAssignments.txt\" has been generated.");

        CourseHandler.generateUnassignedCoursesFile(path, courseMap);
        Debug.log(Debug.type.INFORMATION, "File \"" + path + "\\courses_unassigned.csv\" has been generated.");
    }

    @Override
    public String toString() {
        return professorList.toString() + "\n\n" + courseMap.toString();
    }
}
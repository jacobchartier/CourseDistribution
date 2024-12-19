package util.file;

import entities.*;
import util.*;

import java.io.*;
import java.util.*;

public class CourseHandler {
    public static final String COURSE_OUTPUT_FILE = "courses_unassigned.csv";

    public static HashMap<String, List<Course>> getCourses(String path) {
        HashMap<String, List<Course>> courseMap = new HashMap<>();
        Debug.printLoggingHeader(path);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                List<Course> courseList = new ArrayList<>();
                boolean alreadyExists = false;

                String id = tokens[1];
                String title = tokens[2];
                byte hours = -1;
                byte groups = -1;
                String language = tokens[tokens.length - 2];
                String discipline = tokens[0];

                // Validation for id
                if (!(id.matches("([0-9]{3})([A-Za-z0-9]{3})([A-Za-z]{2})"))) {
                    Debug.log(Debug.type.REJECTED, "The course id is not in a correct format. (Value provided: " + id + ")");
                    continue;
                }

                // Formatting for title
                if (title.startsWith("\"")) {
                    title = line.split("\"")[1];
                }

                // Validation for hours
                if (!ConversionHelper.toByte(tokens[tokens.length - 3])) {
                    Debug.log(Debug.type.REJECTED, id + " doesn't provide a valid number for hours. (Value provided: " + tokens[tokens.length - 3] + ")");
                    continue;
                }

                hours = Byte.parseByte(tokens[tokens.length - 3]);

                // Validation for groups
                if (!ConversionHelper.toByte(tokens[tokens.length - 1])) {
                    Debug.log(Debug.type.REJECTED, id + " doesn't provide a valid number for groups. (Value provided: " + tokens[tokens.length - 1] + ")");
                    continue;
                }

                groups = Byte.parseByte(tokens[tokens.length - 1]);

                if (groups < 1) {
                    Debug.log(Debug.type.REJECTED, title + " (" + id + ") doesn't have any groups.");
                    continue;
                }

                // Formatting for discipline
                discipline = discipline.replaceAll("([A-Za-z]{2})(0*)([0-9]{1,2})", "$1-$3");

                // Check if the course already exist in the hashmap
                if (!courseMap.containsKey(id)) {
                    courseList.add(new Course(id, title, hours, groups, language, discipline));
                    courseMap.put(id, courseList);

                    continue;
                }

                courseList = courseMap.get(id);

                for (Course course : courseList) {
                    if (course.getLanguage().equals(language)) {
                        // Update the number of groups
                        course.setGroups((byte) (course.getGroups() + Byte.parseByte(tokens[tokens.length - 1])));
                        alreadyExists = true;
                        break;
                    }
                }

                // If no course with the same language exists, create and add a new entities.Course
                if (!alreadyExists) {
                    courseList.add(new Course(id, title, hours, groups, language, discipline));
                }
            }

            br.close();
            Debug.log(Debug.type.SUCCESS, courseMap.size() + " courseMap successfully retrieved from file.");

            return courseMap;
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static void generateUnassignedCoursesFile(String path, HashMap<String, List<Course>> courseMap) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + "\\" + COURSE_OUTPUT_FILE))) {
            for (List<Course> courseList : courseMap.values()) {
                for (Course course : courseList) {
                    if (course.getGroups() > 0) {
                        bw.write(course.getId() + ", " + course.getTitle() + ", " + course.getDiscipline() + ", " + course.getLanguage() + ", " + course.getHours() + ", " + course.getGroups());
                        bw.newLine();
                    }
                }
            }

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}


import java.io.File;
import java.util.*;

public class Course {
    private String id;

    private byte hours;
    private byte groups;
    private String title;
    private String discipline;
    private String language;

    public Course(String id, byte hours, byte groups, String title, String discipline, String language) {
        this.id = id;
        this.hours = hours;
        this.groups = groups;
        this.title = title;
        this.discipline = discipline;
        this.language = language;
    }

    public Course(Course course, Byte groups, String title, String language) {
        this(course.id, course.hours, groups, title, course.discipline, language);
    }

    public String getId() {
        return id;
    }

    public byte getHours() {
        return hours;
    }

    public byte getGroups() {
        return groups;
    }
    public void setGroups(byte groups) {
        this.groups = groups;
    }

    public String getTitle() {
        return title;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getLanguage() {
        return language;
    }

    public static HashMap<String, List<Course>> getCoursesFromFile(String path) {
        Debug.printLoggingHeader(path);
        HashMap<String, List<Course>> courses = new HashMap<>();

        try {
            Scanner scanner = new Scanner(new File(path));

            while (scanner.hasNextLine()) {
                List<Course> courseList = new ArrayList<>();
                String[] data = scanner.nextLine().split(",");
                boolean isFound = false;


                if (!(data[1].matches("([0-9]{3})([A-Za-z0-9]{3})([A-Za-z]{2})"))) {
                    Debug.log(Debug.type.REJECTED, "The course id is not in a correct format: " + data[1]);
                    continue;
                }

                if (!courses.containsKey(data[1])) {
                    courseList.add(new Course(data[1], Byte.parseByte(data[data.length - 3]), Byte.parseByte(data[data.length - 1]), data[2], data[0], data[data.length - 2]));
                    courses.put(data[1], courseList);
                }

                for (Course course : courses.get(data[1])) {

                    if (course.getLanguage().equals(data[data.length - 2])) {
                        course.setGroups((byte) (course.getGroups() + Byte.parseByte(data[data.length - 3])));
                        courses.put(data[1], courseList);
                        isFound = true;
                        break;
                    }
                }

                if (!isFound) {
                    courseList.add(new Course(data[1], Byte.parseByte(data[data.length - 3]), Byte.parseByte(data[data.length - 1]), data[2], data[0], data[data.length - 2]));
                    courses.put(data[1], courseList);
                }

            }

            scanner.close();
            Debug.log(Debug.type.INFORMATION, courses.size() + " courses successfully retrieved from file.");

            return courses;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String toString() {
        return "Course [id=" + id + ", hours=" + hours + ", groups=" + groups + ", title=" + title + ", discipline=" +
                discipline + ", language=" + language + "]";
    }

}

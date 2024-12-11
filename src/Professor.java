import java.io.File;
import java.util.*;

public class Professor implements Comparable<Professor> {
    private String id;
    private String name;
    private float seniority;
    private Set<String> disciplines;
    private List<Course> affectedCourses;

    public Professor(String id, String name, float seniority, Set<String> disciplines) {
        this.id = id;
        this.name = name;
        this.seniority = seniority;
        this.disciplines = disciplines;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getSeniority() {
        return seniority;
    }

    public Set<String> getDisciplines() {
        return disciplines;
    }

    public List<Course> getAffectedCourses() {
        return affectedCourses;
    }
    public void setAffectedCourses(List<Course> affectedCourses) {
        this.affectedCourses.addAll(affectedCourses);
    }

    public static ArrayList<Professor> getProfessorsFromFile(String path) {
        Debug.printLoggingHeader(path);
        ArrayList<Professor> professors = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(path));

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(":");
                Set<String> disciplines = getDisciplines(data);

                // Check if the seniority is valid
                if (!isFloatValid(data[2])) {
                    // Error message
                    Debug.log(Debug.type.REJECTED, data[1] + " (id=" + data[0] + ") provided the value " + data[2] + " as seniority, a numerical value is needed.");
                    // Skip to the next professor
                    continue;
                }

                // Check for the disciplines
                if (disciplines.isEmpty()) {
                    // Skip to the next professor
                    continue;
                }

                professors.add(new Professor(data[0], data[1], Float.parseFloat(data[2]), disciplines));
            }

            scanner.close();
            Debug.log(Debug.type.INFORMATION, professors.size() + " professors successfully retrieved from file.");

            return professors;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    protected static boolean isFloatValid(String str) {
        if (str == null || str.isEmpty())
            return false;

        try {
            Float.parseFloat(str);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    protected static Set<String> getDisciplines(String[] data) {
        Set<String> disciplines = new HashSet<>();
        String[] output = data[data.length - 1].split("[|,]");

        for (String s : output) {
            if (s.startsWith("0") && output.length == 1) {
                Debug.log(Debug.type.REJECTED, data[1] + " (id=" + data[0] + ") did not provide any discipline.");
                break;
            }

            if (!(s.trim().matches("[A-Za-z]{1,2}(-|)[0-9]{1,2}"))) {
                Debug.log(Debug.type.IGNORED, data[1] + " (id=" + data[0] + ") provided an invalid discipline: " + s.trim());
                continue;
            }

            s = s.replaceAll("([A-Za-z]{2})([0-9]{1,2})", "$1-$2");
            disciplines.add(s.trim());
        }

        return disciplines;
    }

    @Override
    public String toString() {
        return "Professor [id=" + id + ", name=" + name + ", seniority=" + seniority + ", disciplines=" + disciplines +
                ", affectedCourses=" + affectedCourses + "]";
    }

    @Override
    public int compareTo(Professor other) {
        if (other == null)
            throw new NullPointerException("Cannot compare null object");

        if (seniority > other.seniority)
            return 1;
        else if(seniority < other.seniority)
            return -1;
        else
            return 0;
    }
}

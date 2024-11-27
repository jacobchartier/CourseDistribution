import java.util.Date;
import java.util.List;
import java.util.Set;

public class Professor implements Comparable {
    private int id;
    private String name;
    private Date seniority;
    private Set<String> disciplines;
    private List<Course> affectedCourses = null;

    @Override
    public String toString() {
        return "Professor [id=" + id + ", name=" + name + ", seniority=" + seniority + ", disciplines=" + disciplines + ", affectedCourses=" + affectedCourses + "]";
    }

    @Override
    public int compareTo(Object o) {
        return id - ((Professor) o).id;
    }
}

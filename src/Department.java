import java.util.*;

public class Department {
    private HashMap<String, List<Course>> courseMap;
    private ArrayList<Professor> professors;

    public Department(ArrayList<Professor> professors) {
        this.professors = professors;
        courseMap = new HashMap<>();
    }
}
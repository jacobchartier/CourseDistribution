import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Department {
    private HashMap<String, List<Course>> courseMap;
    private ArrayList<Professor> professors;

    public Department(ArrayList<Professor> professors) {
        this.professors = professors;
        courseMap = new HashMap<>();
    }
}
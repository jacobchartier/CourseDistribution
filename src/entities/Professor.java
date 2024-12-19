package entities;

import java.util.*;

public class Professor implements Comparable<Professor> {
    private String id;
    private String name;
    private float seniority;
    private Set<String> disciplines;

    private int requestedHours;
    private List<Course> affectedCourses = new ArrayList<>();

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

    public int getRequestedHours() {
        return requestedHours;
    }
    public void setRequestedHours(int requestedHours) {
        this.requestedHours = requestedHours;
    }

    public List<Course> getAffectedCourses() {
        return affectedCourses;
    }
    public void setAffectedCourses(List<Course> affectedCourses) {
        this.affectedCourses.addAll(affectedCourses);
    }

    @Override
    public String toString() {
        return "\nentities.Professor [id=" + id + ", name=" + name + ", seniority=" + seniority + ", disciplines=" + disciplines +
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

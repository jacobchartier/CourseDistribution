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
    }

    public Course(Course course) {
        this(course.id, course.hours, course.groups, course.title, course.discipline, course.language);
    }
}

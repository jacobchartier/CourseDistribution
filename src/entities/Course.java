package entities;

import util.*;

import java.io.*;
import java.util.*;

public class Course {
    private String id;

    private byte hours;
    private byte groups;
    private String title;
    private String discipline;
    private String language;

    public Course(String id, String title, byte hours, byte groups, String language, String discipline) {
        this.id = id;
        this.hours = hours;
        this.groups = groups;
        this.title = title;
        this.discipline = discipline;
        this.language = language;
    }

    public Course(Course course) {
        this.id = new String(course.id);
        this.hours = course.hours;
        this.groups = course.groups;
        this.title = course.title;
        this.discipline = course.discipline;
        this.language = course.language;
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

    @Override
    public String toString() {
        return "Course [id=" + id + ", hours=" + hours + ", groups=" + groups + ", title=" + title + ", discipline=" +
                discipline + ", language=" + language + "]";
    }

}

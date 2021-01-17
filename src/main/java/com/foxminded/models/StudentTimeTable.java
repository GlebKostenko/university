package com.foxminded.models;

import java.util.Date;
import java.util.List;

public class StudentTimeTable {
    private Date date;
    private List<Subject> subjects;

    public StudentTimeTable(Date date, List<Subject> subjects) {
        this.date = date;
        this.subjects = subjects;
    }

    public Date getDate() {
        return date;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}

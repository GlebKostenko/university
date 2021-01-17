package com.foxminded.models;

import java.util.Date;
import java.util.List;

public class TeacherTimeTable {
    private Date date;
    private List<Subject> subjects;

    public TeacherTimeTable(Date date, List<Subject> subjects) {
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

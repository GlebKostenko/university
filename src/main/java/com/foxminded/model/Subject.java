package com.foxminded.model;

public class Subject {
    private long subjectId;
    private String subjectName;
    public Subject(long subjectId,String subjectName){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }
    public String getSubjectName() {
        return subjectName;
    }

    public long getSubjectId() {
        return subjectId;
    }
}

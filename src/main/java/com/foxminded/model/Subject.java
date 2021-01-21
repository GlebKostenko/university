package com.foxminded.model;

public class Subject {
    private Long subjectId;
    private String subjectName;
    public Subject(Long subjectId,String subjectName){
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

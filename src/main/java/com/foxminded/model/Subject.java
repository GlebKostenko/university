package com.foxminded.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;
    @Column(name = "subject_name")
    private String subjectName;
    public Subject(Long subjectId,String subjectName){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public Subject(){}

    public Subject(Long subjectId){
        this.subjectId = subjectId;
    }

    public Subject(String subjectName){
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(subjectId, subject.subjectId) && Objects.equals(subjectName, subject.subjectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId, subjectName);
    }
}

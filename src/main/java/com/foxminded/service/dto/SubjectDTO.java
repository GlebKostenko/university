package com.foxminded.service.dto;

import java.util.Objects;

public class SubjectDTO {
    private Long subjectId;
    private String subjectName;
    public SubjectDTO(Long subjectId, String subjectName){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public SubjectDTO(){}

    public SubjectDTO(Long subjectId){
        this.subjectId = subjectId;
    }

    public SubjectDTO(String subjectName){
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public long getSubjectId() {
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
        SubjectDTO subject = (SubjectDTO) o;
        return Objects.equals(subjectId, subject.subjectId) && Objects.equals(subjectName, subject.subjectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId, subjectName);
    }
}

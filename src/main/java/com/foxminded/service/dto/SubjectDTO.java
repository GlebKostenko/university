package com.foxminded.service.dto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subjects")
public class SubjectDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;
    @Column(name = "subject_name")
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
        SubjectDTO subject = (SubjectDTO) o;
        return Objects.equals(subjectId, subject.subjectId) && Objects.equals(subjectName, subject.subjectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId, subjectName);
    }
}

package com.foxminded.service.dto;

import java.util.Objects;

public class TeacherDTO {
    private Long teacherId;
    private String firstName;
    private String lastName;

    public TeacherDTO(Long teacherId, String firstName, String lastName) {
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public TeacherDTO(){}

    public TeacherDTO(Long teacherId){
        this.teacherId = teacherId;
    }

    public TeacherDTO(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherDTO teacher = (TeacherDTO) o;
        return Objects.equals(teacherId, teacher.teacherId) && Objects.equals(firstName, teacher.firstName) && Objects.equals(lastName, teacher.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, firstName, lastName);
    }
}

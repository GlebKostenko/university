package com.foxminded.model;

import java.util.List;
import java.util.Objects;

public class Teacher {
    private Long teacherId;
    private String firstName;
    private String lastName;

    public Teacher(Long teacherId, String firstName, String lastName) {
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Teacher(Long teacherId){
        this.teacherId = teacherId;
    }

    public Teacher(String firstName,String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getTeacherId() {
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
        Teacher teacher = (Teacher) o;
        return Objects.equals(teacherId, teacher.teacherId) && Objects.equals(firstName, teacher.firstName) && Objects.equals(lastName, teacher.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, firstName, lastName);
    }
}

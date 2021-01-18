package com.foxminded.model;

import java.util.List;

public class Teacher {
    private long teacherId;
    private String firstName;
    private String lastName;

    public Teacher(long teacherId, String firstName, String lastName) {
        this.teacherId = teacherId;
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
}

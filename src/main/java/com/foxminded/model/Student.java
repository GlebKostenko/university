package com.foxminded.model;

public class Student {
    private long studentId;
    private String firstName;
    private String lastName;
    private Group group;

    public Student(long studentId, String firstName, String lastName, Group group) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public long getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Group getGroup(){
        return group;
    }
}

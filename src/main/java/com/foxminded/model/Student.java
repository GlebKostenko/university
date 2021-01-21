package com.foxminded.model;

public class Student {
    private Long studentId;
    private String firstName;
    private String lastName;
    private Group group;

    public Student(Long studentId, String firstName, String lastName, Group group) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public Student(String firstName, String lastName, Group group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public Student(Long studentId) {
        this.studentId = studentId;
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

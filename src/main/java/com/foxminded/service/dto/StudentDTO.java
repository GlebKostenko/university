package com.foxminded.service.dto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students")
public class StudentDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne(fetch = FetchType.LAZY)
    private GroupDTO group;

    public StudentDTO(Long studentId, String firstName, String lastName, GroupDTO group) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public StudentDTO(){}

    public StudentDTO(String firstName, String lastName, GroupDTO group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public StudentDTO(Long studentId) {
        this.studentId = studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public GroupDTO getGroup(){
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO student = (StudentDTO) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName, group);
    }
}

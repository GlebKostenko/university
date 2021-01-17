package com.foxminded.service;

import com.foxminded.models.Student;
import com.foxminded.models.StudentTimeTable;
import com.foxminded.models.Teacher;
import com.foxminded.models.TeacherTimeTable;

import java.util.List;

public class UniversityService {
    private List<Student> students;
    private List<Teacher> teachers;

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public StudentTimeTable showStudentTimeTableForADay(int studentId){
        return students.stream().filter(x->x.getStudentId()== studentId).findAny().get().getTimeTableForADay();
    }

    public List<StudentTimeTable> showStudentTimeTableForAMonth(int studentId){
        return students.stream().filter(x->x.getStudentId()== studentId).findAny().get().getTimeTableForAMonth();
    }

    public TeacherTimeTable showTeacherTimeTableForADay(int teacherId){
        return teachers.stream().filter(x->x.getTeacherId()== teacherId).findAny().get().getTimeTableForADay();
    }

    public List<TeacherTimeTable> showTeacherTimeTableForAMonth(int teacherId){
        return teachers.stream().filter(x->x.getTeacherId()== teacherId).findAny().get().getTimeTableForAMonth();
    }
}

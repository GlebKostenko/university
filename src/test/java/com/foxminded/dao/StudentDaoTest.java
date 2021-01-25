package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.Group;
import com.foxminded.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class StudentDaoTest {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private GroupDao groupDao;
    @Test
    void save() throws SQLException {
        Group group = groupDao.save(new Group("fakt-06"));
        Student student = studentDao.save(new Student("Ivan","Ivanov",new Group(group.getGroupId())));
        assertEquals(student,studentDao.findById(new Student(student.getStudentId())));
    }

    @Test
    void findById() throws SQLException{
        Group group = groupDao.save(new Group("fivt-07"));
        Student student = studentDao.save(new Student("Victor","Victorov",new Group(group.getGroupId())));
        assertEquals(student,studentDao.findById(new Student(student.getStudentId())));
    }

    @Test
    void findAll() throws SQLException{
        Group group = groupDao.save(new Group("fivt-03"));
        Student student = studentDao.save(new Student("Victor","Victorov",new Group(group.getGroupId())));
        assertTrue(!studentDao.findAll().isEmpty());
    }

    @Test
    void update() throws SQLException{
        Group group = groupDao.save(new Group("fivt-01"));
        Group groupNew = groupDao.save(new Group("fopf-04"));
        Student student = studentDao.save(new Student("Victor","Victorov",new Group(group.getGroupId())));
        Student studentNew = new Student(student.getStudentId(),"Ivan","Ivanov",new Group(groupNew.getGroupId()));
        studentDao.update(studentNew);
        Student updatedStudent = new Student(student.getStudentId(),studentNew.getFirstName(),studentNew.getLastName(),groupNew);
        assertEquals(updatedStudent,studentDao.findById(student));
    }

    @Test
    void delete() throws SQLException{
        Group group = groupDao.save(new Group("fivt-02"));
        Student student = studentDao.save(new Student("Victor","Victorov",new Group(group.getGroupId())));
        studentDao.delete(new Student(student.getStudentId()));
        assertTrue(studentDao.findAll().isEmpty());
    }
}
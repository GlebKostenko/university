package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.Group;
import com.foxminded.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class StudentDaoTest {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    StudentDaoTest(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Test
    void save() throws SQLException {
        Group group = groupDao.save(new Group("fakt-06"));
        Student student = studentDao.save(new Student("Ivan","Ivanov",new Group(group.getGroupId())));
        int numberOfStudent = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM students WHERE student_id = ?"
                ,new Object[]{student.getStudentId()}
                ,Integer.class);
        assertTrue(numberOfStudent > 0);
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
        Student studentNew = new Student("Ivan","Ivanov",new Group(groupNew.getGroupId()));
        studentDao.update(student.getStudentId(),studentNew);
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
package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class TeacherDaoTest {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    TeacherDaoTest(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Test
    void save() throws SQLException {
        Teacher teacher = teacherDao.save(new Teacher("Ivan","Ivanov"));
        int numberOfTeacher = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM teachers WHERE teacher_id = ?"
                ,new Object[]{teacher.getTeacherId()},Integer.class);
        assertTrue(numberOfTeacher > 0);
    }

    @Test
    void findById() throws SQLException {
        Teacher teacher = teacherDao.save(new Teacher("Petr","Petrov"));
        assertTrue(teacherDao.findById(new Teacher(teacher.getTeacherId())).equals(teacher));
    }

    @Test
    void findAll() throws SQLException {
        teacherDao.save(new Teacher("Victor","Victorov"));
        assertTrue(!teacherDao.findAll().isEmpty());
    }

    @Test
    void update() throws SQLException {
        Teacher teacher = teacherDao.save(new Teacher("Alexander","Alexandrov"));
        Teacher teacherNew = new Teacher("Lev","Landau");
        teacherDao.update(teacher.getTeacherId(),teacherNew);
        Teacher updatedTeacher = new Teacher(teacher.getTeacherId(),"Lev","Landau");
        assertEquals(updatedTeacher,teacherDao.findById(new Teacher(teacher.getTeacherId())));
    }

    @Test
    void delete() throws SQLException {
        Teacher teacher = teacherDao.save(new Teacher("Petr","Kapitsa"));
        teacherDao.delete(new Teacher(teacher.getTeacherId()));
        assertFalse(teacherDao.findAll().contains(teacher));
    }
}
package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class TeacherDaoTest {
    @Autowired
    TeacherDao teacherDao;
    @Test
    void save() throws SQLException {
        Teacher teacher = teacherDao.save(new Teacher("Ivan","Ivanov"));
        assertEquals(teacher,teacherDao.findById(new Teacher(teacher.getTeacherId())));
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
        Teacher teacherNew = new Teacher(teacher.getTeacherId(),"Lev","Landau");
        teacherDao.update(teacherNew);
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
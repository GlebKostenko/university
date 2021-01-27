package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class TeacherDaoTest {
    @Autowired
    TeacherDao teacherDao;
    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord() {
        Teacher teacher = teacherDao.save(new Teacher("Ivan","Ivanov"));
        assertEquals(teacher,teacherDao.findById(new Teacher(teacher.getTeacherId())));
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        Teacher teacher = teacherDao.save(new Teacher("Petr","Petrov"));
        assertTrue(teacherDao.findById(new Teacher(teacher.getTeacherId())).equals(teacher));
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        Throwable exception = assertThrows(EmptyResultDataAccessException.class, () -> teacherDao.findById(new Teacher(83L)));
        assertEquals("Incorrect result size: expected 1, actual 0", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList() {
        teacherDao.save(new Teacher("Victor","Victorov"));
        assertTrue(!teacherDao.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        Teacher teacher = teacherDao.save(new Teacher("Alexander","Alexandrov"));
        Teacher teacherNew = new Teacher(teacher.getTeacherId(),"Lev","Landau");
        teacherDao.update(teacherNew);
        Teacher updatedTeacher = new Teacher(teacher.getTeacherId(),"Lev","Landau");
        assertEquals(updatedTeacher,teacherDao.findById(new Teacher(teacher.getTeacherId())));
    }

    @Test
    void update_WhenRecordDoesNotExist_thenNothingGoesWrong() {
        Teacher teacher = new Teacher(93L,"Georgiy","Semenov");
        teacherDao.update(teacher);
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        Teacher teacher = teacherDao.save(new Teacher("Petr","Kapitsa"));
        teacherDao.delete(new Teacher(teacher.getTeacherId()));
        assertFalse(teacherDao.findAll().contains(teacher));
    }

    @Test
    void delete_WhenRecordDoesNotExist_thenNothingGoesWrong() {
        teacherDao.delete(new Teacher(83L));
    }
}
package com.foxminded.dao;

import com.foxminded.configuration.SpringHibernateConfigTest;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Subject;
import com.foxminded.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.OptimisticLockException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringHibernateConfigTest.class})
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
    void findById_WhenRecordDoesNotExist_thenShouldBeNothing() {
        teacherDao.findById(new Teacher(83L));
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
    void update_WhenRecordDoesNotExist_thenShouldBeException() {
        Teacher teacher = new Teacher(93L,"Georgiy","Semenov");
        Throwable exception = assertThrows(OptimisticLockException.class, () -> teacherDao.update(teacher));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        Teacher teacher = teacherDao.save(new Teacher("Petr","Kapitsa"));
        teacherDao.delete(new Teacher(teacher.getTeacherId()));
        assertFalse(teacherDao.findAll().contains(teacher));
    }

    @Test
    void delete_WhenRecordDoesNotExist_thenShouldBeException() {
        Throwable exception = assertThrows(OptimisticLockException.class, () -> teacherDao.delete(new Teacher(83L)));
    }
}
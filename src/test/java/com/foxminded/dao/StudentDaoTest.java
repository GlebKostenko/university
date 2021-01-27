package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.Group;
import com.foxminded.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class StudentDaoTest {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private GroupDao groupDao;
    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord() {
        Group group = groupDao.save(new Group("fakt-06"));
        Student student = studentDao.save(new Student("Ivan","Ivanov",new Group(group.getGroupId())));
        assertEquals(student.getGroup().getGroupId()
                ,studentDao.findById(new Student(student.getStudentId())).getGroup().getGroupId());
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        Group group = groupDao.save(new Group("fivt-07"));
        Student student = studentDao.save(new Student("Victor","Victorov",new Group(group.getGroupId())));
        assertEquals(student.getFirstName()
                ,studentDao.findById(new Student(student.getStudentId())).getFirstName());
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        Throwable exception = assertThrows(EmptyResultDataAccessException.class, () -> studentDao.findById(new Student(98L)));
        assertEquals("Incorrect result size: expected 1, actual 0", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList() {
        Group group = groupDao.save(new Group("fivt-03"));
        Student student = studentDao.save(new Student("Victor","Victorov",new Group(group.getGroupId())));
        assertTrue(!studentDao.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        Group group = groupDao.save(new Group("fivt-01"));
        Group groupNew = groupDao.save(new Group("fopf-04"));
        Student student = studentDao.save(new Student("Victor","Victorov",new Group(group.getGroupId())));
        Student studentNew = new Student(student.getStudentId(),"Ivan","Ivanov",new Group(groupNew.getGroupId()));
        studentDao.update(studentNew);
        Student updatedStudent = new Student(student.getStudentId(),studentNew.getFirstName(),studentNew.getLastName(),groupNew);
        assertEquals(updatedStudent,studentDao.findById(student));
    }

    @Test
    void update_WhenRecordDoesNotExist_thenNothingGoesWrong() {
        Student student = new Student(98L,"Ivan","Ivanov",new Group(48L));
        studentDao.update(student);
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        Group group = groupDao.save(new Group("fivt-02"));
        Student student = studentDao.save(new Student("Victor","Victorov",new Group(group.getGroupId())));
        studentDao.delete(new Student(student.getStudentId()));
        assertFalse(studentDao.findAll().contains(new Student(student.getStudentId(),"Victor","Victorov",group)));
    }

    @Test
    void delete_WhenRecordDoesNotExist_thenNothingGoesWrong() {
        studentDao.delete(new Student(98L));
    }
}
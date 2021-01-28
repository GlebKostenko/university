package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class SubjectDaoTest {
    @Autowired
    SubjectDao subjectDao;
    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord() {
        Subject subject = subjectDao.save(new Subject("Math"));
        assertEquals(subject,subjectDao.findById(new Subject(subject.getSubjectId())));
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        Subject subject = subjectDao.save(new Subject("Biology"));
        assertTrue(subjectDao.findById(new Subject(subject.getSubjectId())).equals(subject));
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        Throwable exception = assertThrows(EmptyResultSetExceptionDao.class, () -> subjectDao.findById(new Subject(55L)));
        assertEquals("Subjects table doesn't contain this record", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList() {
        Subject subject = subjectDao.save(new Subject("Physics"));
        assertTrue(!subjectDao.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        Subject subject = subjectDao.save(new Subject("Design"));
        Subject subjectNew = new Subject(subject.getSubjectId(),"Programming");
        subjectDao.update(subjectNew);
        Subject updatedSubject = new Subject(subject.getSubjectId(),"Programming");
        assertEquals(updatedSubject,subjectDao.findById(new Subject(subject.getSubjectId())));
    }

    @Test
    void update_WhenRecordDoesNotExist_thenNothingGoesWrong() {
        Subject subject = new Subject(55L,"Physics");
        subjectDao.update(subject);
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        Subject subject = subjectDao.save(new Subject("Calculus"));
        subjectDao.delete(new Subject(subject.getSubjectId()));
        assertFalse(subjectDao.findAll().contains(subject));
    }

    @Test
    void delete_WhenRecordDoesNotExist_thenNothingGoesWrong() {
        subjectDao.delete(new Subject(55L));
    }
}
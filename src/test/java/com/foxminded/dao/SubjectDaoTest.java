package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class SubjectDaoTest {
    @Autowired
    SubjectDao subjectDao;
    @Test
    void save() throws SQLException {
        Subject subject = subjectDao.save(new Subject("Math"));
        assertEquals(subject,subjectDao.findById(new Subject(subject.getSubjectId())));
    }

    @Test
    void findById() throws SQLException{
        Subject subject = subjectDao.save(new Subject("Biology"));
        assertTrue(subjectDao.findById(new Subject(subject.getSubjectId())).equals(subject));
    }

    @Test
    void findAll() throws SQLException{
        Subject subject = subjectDao.save(new Subject("Physics"));
        assertTrue(!subjectDao.findAll().isEmpty());
    }

    @Test
    void update() throws SQLException{
        Subject subject = subjectDao.save(new Subject("Design"));
        Subject subjectNew = new Subject(subject.getSubjectId(),"Programming");
        subjectDao.update(subjectNew);
        Subject updatedSubject = new Subject(subject.getSubjectId(),"Programming");
        assertEquals(updatedSubject,subjectDao.findById(new Subject(subject.getSubjectId())));
    }

    @Test
    void delete() throws SQLException{
        Subject subject = subjectDao.save(new Subject("Calculus"));
        subjectDao.delete(new Subject(subject.getSubjectId()));
        assertFalse(subjectDao.findAll().contains(subject));
    }
}
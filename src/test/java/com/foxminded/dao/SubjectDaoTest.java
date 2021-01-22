package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class SubjectDaoTest {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    SubjectDao subjectDao;
    @Autowired
    SubjectDaoTest(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }
    @Test
    void save() throws SQLException {
        Subject subject = subjectDao.save(new Subject("Math"));
        int numberOfSubject = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM subjects WHERE subject_id = ?"
                ,new Object[]{subject.getSubjectId()}
                ,Integer.class);
        assertTrue(numberOfSubject > 0);
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
        Subject subjectNew = new Subject("Programming");
        subjectDao.update(subject.getSubjectId(),subjectNew);
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
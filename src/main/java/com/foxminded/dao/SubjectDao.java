package com.foxminded.dao;

import com.foxminded.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
@Repository
public class SubjectDao implements Dao<Subject>{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SubjectDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Subject save(Subject subject) throws SQLException {
        jdbcTemplate.update("INSERT INTO subjects(subject_name) VALUES ?",subject.getSubjectName());
        return jdbcTemplate.queryForObject("SELECT subject_id,subject_name FROM subjects WHERE subject_name = ?"
                ,new Object[]{subject.getSubjectName()}
                ,(rs,rowNum)->
                new Subject(rs.getLong(1),rs.getString(2))
        );
    }

    @Override
    public Subject findById(Subject subject) throws SQLException {
        return jdbcTemplate.queryForObject("SELECT subject_id,subject_name FROM subjects WHERE subject_id = ?"
                ,new Object[]{subject.getSubjectId()}
                ,(rs,rowNum)->
                new Subject(rs.getLong(1),rs.getString(2))
        );
    }

    @Override
    public List<?> findAll() throws SQLException {
        return jdbcTemplate.query("SELECT subject_id,subject_name FROM subjects"
                ,(rs,rowNum)->
                new Subject(rs.getLong(1),rs.getString(2))
        );
    }

    @Override
    public void update(Long subjectId, Subject subject) {
        jdbcTemplate.update("UPDATE subjects SET subject_name = ? WHERE subject_id = ?",subject.getSubjectName(),subjectId);
    }

    @Override
    public void delete(Subject subject) {
        jdbcTemplate.update("DELETE FROM subjects WHERE subject_id = ?",subject.getSubjectId());
    }
}

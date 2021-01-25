package com.foxminded.dao;

import com.foxminded.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO subjects(subject_name) VALUES ?",new String[]{"subject_id"});
            ps.setString(1, subject.getSubjectName());
            return ps;
        },keyHolder);
        return new Subject(keyHolder.getKey().longValue(),subject.getSubjectName());
    }

    @Override
    public Subject findById(Subject subject) throws SQLException {
        return jdbcTemplate.queryForObject("SELECT subject_id,subject_name FROM subjects WHERE subject_id = ?"
                ,new BeanPropertyRowMapper<Subject>(Subject.class),subject.getSubjectId()
        );
    }

    @Override
    public List<?> findAll() throws SQLException {
        return jdbcTemplate.query("SELECT subject_id,subject_name FROM subjects"
                ,new BeanPropertyRowMapper<Subject>(Subject.class)
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

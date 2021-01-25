package com.foxminded.dao;

import com.foxminded.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SubjectDao implements Dao<Subject>{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SubjectDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Subject save(Subject subject) throws SQLException {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("subject_name",subject.getSubjectName());
        Long id = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("subjects")
                .usingGeneratedKeyColumns("subject_id")
                .executeAndReturnKey(parameters).longValue();
        subject.setSubjectId(id);
        return subject;
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
    public void update(Subject subject) {
        jdbcTemplate.update("UPDATE subjects SET subject_name = ? WHERE subject_id = ?"
                ,subject.getSubjectName()
                ,subject.getSubjectId());
    }

    @Override
    public void delete(Subject subject) {
        jdbcTemplate.update("DELETE FROM subjects WHERE subject_id = ?",subject.getSubjectId());
    }
}

package com.foxminded.dao;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SubjectDao implements Dao<Subject>{
    private static final Logger logger = LoggerFactory.getLogger(SubjectDao.class.getSimpleName());
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SubjectDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Subject save(Subject subject) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("subject_name",subject.getSubjectName());
        logger.debug("Trying to insert new record in subjects table");
        Long id = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("subjects")
                .usingGeneratedKeyColumns("subject_id")
                .executeAndReturnKey(parameters).longValue();
        subject.setSubjectId(id);
        logger.debug("Return Subject with id: {}", id);
        return subject;
    }

    @Override
    public Subject findById(Subject subject) {
        logger.debug("Trying to find subject with id: {}",subject.getSubjectId());
        try {
            return jdbcTemplate.queryForObject("SELECT subject_id,subject_name FROM subjects WHERE subject_id = ?"
                    , new BeanPropertyRowMapper<Subject>(Subject.class), subject.getSubjectId()
            );
        }catch (EmptyResultDataAccessException e){
            logger.warn("Subject with the same id wasn't found");
            throw new EmptyResultSetExceptionDao("Subjects table doesn't contain this record",e);
        }
    }

    @Override
    public List<Subject> findAll() {
        logger.debug("Trying to return existing subjects");
        try {
            return jdbcTemplate.query("SELECT subject_id,subject_name FROM subjects"
                    , new BeanPropertyRowMapper<Subject>(Subject.class)
            );
        }catch (EmptyResultDataAccessException e){
            logger.warn("List of subjects is empty");
            throw new EmptyResultSetExceptionDao("Subjects table is empty",e);
        }
    }

    @Override
    public void update(Subject subject) {
        logger.debug("Updating record with id: {}",subject.getSubjectId());
        jdbcTemplate.update("UPDATE subjects SET subject_name = ? WHERE subject_id = ?"
                ,subject.getSubjectName()
                ,subject.getSubjectId());
    }

    @Override
    public void delete(Subject subject) {
        logger.debug("Deleting record with id: {}",subject.getSubjectId());
        jdbcTemplate.update("DELETE FROM subjects WHERE subject_id = ?",subject.getSubjectId());
    }
}

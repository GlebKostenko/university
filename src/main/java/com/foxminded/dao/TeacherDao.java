package com.foxminded.dao;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Teacher;
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
public class TeacherDao implements Dao<Teacher>{
    private static final Logger logger = LoggerFactory.getLogger(TeacherDao.class.getSimpleName());
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Teacher save(Teacher teacher) {
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("first_name",teacher.getFirstName());
        parameters.put("last_name",teacher.getLastName());
        logger.debug("Trying to insert new record in teachers table");
        Long id = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("teachers")
                .usingGeneratedKeyColumns("teacher_id")
                .executeAndReturnKey(parameters).longValue();
        teacher.setTeacherId(id);
        logger.debug("Return Teacher with id: {}", id);
        return teacher;
    }

    @Override
    public Teacher findById(Teacher teacher) {
        logger.debug("Trying to find teacher with id: {}",teacher.getTeacherId());
        try {
            return jdbcTemplate.queryForObject("SELECT teacher_id,first_name,last_name FROM teachers WHERE teacher_id = ?"
                    , new BeanPropertyRowMapper<Teacher>(Teacher.class), teacher.getTeacherId()
            );
        }catch (EmptyResultDataAccessException e){
            logger.error("Teacher with the same id wasn't found");
            throw new EmptyResultSetExceptionDao("Teachers table doesn't contain this record",e);
        }
    }

    @Override
    public List<Teacher> findAll() {
        logger.debug("Trying to return existing teachers");
        try {
            return jdbcTemplate.query("SELECT teacher_id,first_name,last_name FROM teachers"
                    , new BeanPropertyRowMapper<Teacher>(Teacher.class)
            );
        }catch (EmptyResultDataAccessException e){
            logger.error("List of teachers is empty");
            throw new EmptyResultSetExceptionDao("Teachers table is empty",e);
        }
    }

    @Override
    public void update(Teacher teacher) {
        logger.debug("Updating record with id: {}",teacher.getTeacherId());
        jdbcTemplate.update("UPDATE teachers SET first_name = ?,last_name = ? WHERE teacher_id = ?"
                ,teacher.getFirstName()
                ,teacher.getLastName()
                ,teacher.getTeacherId());
    }

    @Override
    public void delete(Teacher teacher) {
        logger.debug("Deleting record with id: {}",teacher.getTeacherId());
        jdbcTemplate.update("DELETE FROM teachers WHERE teacher_id = ?",teacher.getTeacherId());
    }
}

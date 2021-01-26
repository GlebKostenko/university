package com.foxminded.dao;

import com.foxminded.model.Teacher;
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
public class TeacherDao implements Dao<Teacher>{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Teacher save(Teacher teacher) throws SQLException {
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("first_name",teacher.getFirstName());
        parameters.put("last_name",teacher.getLastName());
        Long id = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("teachers")
                .usingGeneratedKeyColumns("teacher_id")
                .executeAndReturnKey(parameters).longValue();
        teacher.setTeacherId(id);
        return teacher;
    }

    @Override
    public Teacher findById(Teacher teacher) throws SQLException {
        return jdbcTemplate.queryForObject("SELECT teacher_id,first_name,last_name FROM teachers WHERE teacher_id = ?"
                ,new BeanPropertyRowMapper<Teacher>(Teacher.class),teacher.getTeacherId()
        );
    }

    @Override
    public List<Teacher> findAll() throws SQLException {
        return jdbcTemplate.query("SELECT teacher_id,first_name,last_name FROM teachers"
                ,new BeanPropertyRowMapper<Teacher>(Teacher.class)
        );
    }

    @Override
    public void update(Teacher teacher) {
        jdbcTemplate.update("UPDATE teachers SET first_name = ?,last_name = ? WHERE teacher_id = ?"
                ,teacher.getFirstName()
                ,teacher.getLastName()
                ,teacher.getTeacherId());
    }

    @Override
    public void delete(Teacher teacher) {
        jdbcTemplate.update("DELETE FROM teachers WHERE teacher_id = ?",teacher.getTeacherId());
    }
}

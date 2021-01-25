package com.foxminded.dao;

import com.foxminded.model.Teacher;
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
public class TeacherDao implements Dao<Teacher>{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Teacher save(Teacher teacher) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO teachers(first_name,last_name) VALUES (?,?)",new String[]{"teacher_id"});
            ps.setString(1, teacher.getFirstName());
            ps.setString(2, teacher.getLastName());
            return ps;
        },keyHolder);
        return new Teacher(keyHolder.getKey().longValue(),teacher.getFirstName(),teacher.getLastName());
    }

    @Override
    public Teacher findById(Teacher teacher) throws SQLException {
        return jdbcTemplate.queryForObject("SELECT teacher_id,first_name,last_name FROM teachers WHERE teacher_id = ?"
                ,new BeanPropertyRowMapper<Teacher>(Teacher.class),teacher.getTeacherId()
        );
    }

    @Override
    public List<?> findAll() throws SQLException {
        return jdbcTemplate.query("SELECT teacher_id,first_name,last_name FROM teachers"
                ,new BeanPropertyRowMapper<Teacher>(Teacher.class)
        );
    }

    @Override
    public void update(Long teacherId, Teacher teacher) {
        jdbcTemplate.update("UPDATE teachers SET first_name = ?,last_name = ? WHERE teacher_id = ?",teacher.getFirstName(),teacher.getLastName(),teacherId);
    }

    @Override
    public void delete(Teacher teacher) {
        jdbcTemplate.update("DELETE FROM teachers WHERE teacher_id = ?",teacher.getTeacherId());
    }
}

package com.foxminded.dao;

import com.foxminded.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        jdbcTemplate.update("INSERT INTO teachers(first_name,last_name) VALUES (?,?)"
                ,teacher.getFirstName(),teacher.getLastName());
        String sql = "SELECT teacher_id,first_name,last_name FROM teachers" +
                " WHERE first_name = ? AND last_name = ?";
        return jdbcTemplate.queryForObject(sql
                ,new Object[]{teacher.getFirstName(),teacher.getLastName()}
                ,(rs,rowNum)->
                new Teacher(rs.getLong(1),rs.getString(2),rs.getString(3))
        );
    }

    @Override
    public Teacher findById(Teacher teacher) throws SQLException {
        return jdbcTemplate.queryForObject("SELECT teacher_id,first_name,last_name FROM teachers WHERE teacher_id = ?"
                ,new Object[]{teacher.getTeacherId()}
                ,(rs,rowNum)->
                new Teacher(rs.getLong(1),rs.getString(2),rs.getString(3))
        );
    }

    @Override
    public List<?> findAll() throws SQLException {
        return jdbcTemplate.query("SELECT teacher_id,first_name,last_name FROM teachers"
                ,(rs,rowNum)->
                new Teacher(rs.getLong(1),rs.getString(2),rs.getString(3))
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

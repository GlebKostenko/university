package com.foxminded.dao;

import com.foxminded.model.Group;
import com.foxminded.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentDao implements Dao<Student>{

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public StudentDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Student save(Student student) throws SQLException {
        jdbcTemplate.update("INSERT INTO students(first_name,last_name,group_id) VALUES (?,?,?)",
                student.getFirstName(),student.getLastName(),student.getGroup().getGroupId());
        String sql = "SELECT st.student_id,st.first_name,st.last_name,gr.group_id,gr.group_name" +
                " FROM students st " +
                "LEFT JOIN groups gr ON gr.group_id = st.group_id " +
                "WHERE st.first_name = ? AND st.last_name = ? AND gr.group_id =?";
        return jdbcTemplate.queryForObject(sql
                ,new Object[]{student.getFirstName(),student.getLastName(),student.getGroup().getGroupId()}
                ,(rs,rowNum)->
                new Student(rs.getLong(1)
                        ,rs.getString(2)
                        ,rs.getString(3)
                        ,new Group(rs.getLong(4),rs.getString(5))
                )
        );
    }

    @Override
    public Student findById(Student student) throws SQLException {
        String sql = "SELECT st.student_id,st.first_name,st.last_name,gr.group_id,gr.group_name" +
                " FROM students st " +
                "LEFT JOIN groups gr ON gr.group_id = st.group_id " +
                "WHERE st.student_id = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{student.getStudentId()}
        ,(rs,rowNum)->
                new Student(rs.getLong(1)
                        ,rs.getString(2)
                        ,rs.getString(3)
                        ,new Group(rs.getLong(4),rs.getString(5))
                )
        );
    }

    @Override
    public List<?> findAll() throws SQLException {
        String sql = "SELECT st.student_id,st.first_name,st.last_name,gr.group_id,gr.group_name" +
                " FROM students st " +
                "LEFT JOIN groups gr ON gr.group_id = st.group_id ";
        return jdbcTemplate.query(sql,(rs,rowNum)->
                new Student(rs.getLong(1)
                        ,rs.getString(2)
                        ,rs.getString(3)
                        ,new Group(rs.getLong(4),rs.getString(5))
                )
        );
    }

    @Override
    public void update(Long studentId, Student student) {
        jdbcTemplate.update("UPDATE students SET first_name = ?,last_name = ?,group_id = ? WHERE student_id = ?",
                student.getFirstName(),
                student.getLastName(),
                student.getGroup().getGroupId(),
                studentId);
    }

    @Override
    public void delete(Student student) {
        jdbcTemplate.update("DELETE FROM students WHERE student_id = ?",student.getStudentId());
    }
}

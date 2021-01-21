package com.foxminded.dao;

import com.foxminded.model.Group;
import com.foxminded.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
@Repository
public class StudentDao implements Dao<Student>{

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    @Override
    public Student save(Student student) throws SQLException {
        jdbcTemplate.update("INSERT INTO students(first_name,last_name,group_id) VALUES (?,?,?)",
                student.getFirstName(),student.getLastName(),student.getGroup().getGroupId());
        PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement("SELECT st.student_id,st.first_name,st.last_name,gr.group_id,gr.group_name" +
                        " FROM students st " +
                        "LEFT JOIN groups gr ON gr.group_id = st.group_id " +
                        "WHERE st.first_name = ? AND st.last_name = ? AND gr.group_name =?");
        preparedStatement.setString(1,student.getFirstName());
        preparedStatement.setString(2,student.getLastName());
        preparedStatement.setLong(3,student.getGroup().getGroupId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Student(resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                new Group(
                        resultSet.getLong(4),
                        resultSet.getString(5)
                )
        );
    }

    @Override
    public Student findById(Student student) throws SQLException {
        PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement("SELECT st.student_id,st.first_name,st.last_name,gr.group_id,gr.group_name" +
                        " FROM students st " +
                        "LEFT JOIN groups gr ON gr.group_id = st.group_id " +
                        "WHERE st.student_id = ?");
        preparedStatement.setLong(1,student.getStudentId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Student(resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                new Group(
                        resultSet.getLong(4),
                        resultSet.getString(5)
                )
        );
    }

    @Override
    public List<?> findAll() throws SQLException {
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT st.student_id,st.first_name,st.last_name,gr.group_id,gr.group_name" +
                " FROM students st " +
                "LEFT JOIN groups gr ON gr.group_id = st.group_id ");
        List<Student> students = new ArrayList<>();
        while (resultSet.next()){
            students.add(new Student(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    new Group(
                            resultSet.getLong(4),
                            resultSet.getString(5)
                    )
            ));
        }
        return students;
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

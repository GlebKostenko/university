package com.foxminded.dao;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Group;
import com.foxminded.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDao implements Dao<Student>{
    private static final Logger logger = LoggerFactory.getLogger(StudentDao.class.getSimpleName());
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public StudentDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Student save(Student student) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("first_name",student.getFirstName());
        parameters.put("last_name",student.getLastName());
        parameters.put("group_id",student.getGroup().getGroupId());
        logger.debug("Trying to insert new record in students table");
        Long id = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("students")
                .usingGeneratedKeyColumns("student_id")
                .executeAndReturnKey(parameters).longValue();
        student.setStudentId(id);
        logger.debug("Return Student with id: {}", id);
        return student;
    }

    @Override
    public Student findById(Student student) {
        logger.debug("Trying to find student with id: {}",student.getStudentId());
        try {
            String sql = "SELECT st.student_id,st.first_name,st.last_name,gr.group_id,gr.group_name" +
                    " FROM students st " +
                    "LEFT JOIN groups gr ON gr.group_id = st.group_id " +
                    "WHERE st.student_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{student.getStudentId()}
                    , (rs, rowNum) ->
                            new Student(rs.getLong(1)
                                    , rs.getString(2)
                                    , rs.getString(3)
                                    , new Group(rs.getLong(4), rs.getString(5))
                            )
            );
        }catch (EmptyResultDataAccessException e){
            logger.error("Student with the same id wasn't found");
            throw new EmptyResultSetExceptionDao("Students table doesn't contain this record",e);
        }
    }

    @Override
    public List<Student> findAll() {
        logger.debug("Trying to return existing students");
        try {
            String sql = "SELECT st.student_id,st.first_name,st.last_name,gr.group_id,gr.group_name" +
                    " FROM students st " +
                    "LEFT JOIN groups gr ON gr.group_id = st.group_id ";
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Student(rs.getLong(1)
                            , rs.getString(2)
                            , rs.getString(3)
                            , new Group(rs.getLong(4), rs.getString(5))
                    )
            );
        }catch (EmptyResultDataAccessException e){
            logger.error("List of students is empty");
            throw new EmptyResultSetExceptionDao("Students table is empty",e);
        }
    }

    @Override
    public void update(Student student) {
        logger.debug("Updating record with id: {}",student.getStudentId());
        jdbcTemplate.update("UPDATE students SET first_name = ?,last_name = ?,group_id = ? WHERE student_id = ?",
                student.getFirstName(),
                student.getLastName(),
                student.getGroup().getGroupId(),
                student.getStudentId());
    }

    @Override
    public void delete(Student student) {
        logger.debug("Deleting record with id: {}",student.getStudentId());
        jdbcTemplate.update("DELETE FROM students WHERE student_id = ?",student.getStudentId());
    }
}

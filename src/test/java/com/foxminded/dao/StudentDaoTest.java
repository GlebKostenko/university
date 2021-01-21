package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfig;
import com.foxminded.model.Group;
import com.foxminded.model.Student;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfig.class,StudentDao.class})
class StudentDaoTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private StudentDao studentDao;
    @BeforeEach
    void createTables(){
        String aSQLScriptFilePath = "/home/gleb/IdeaProjects/university/src/test/resources/creating_scripts.sql";

        try {
            ScriptRunner sr = new ScriptRunner(dataSource.getConnection());
            Reader reader = new BufferedReader(
                    new FileReader(aSQLScriptFilePath));
            sr.runScript(reader);
        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath
                    + " The error is " + e.getMessage());
        }
    }
    @Test
    void save() throws SQLException {
        Student student = studentDao.save(new Student("Ivan","Ivanov",new Group(1L)));
        PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement("SELECT COUNT(1) FROM students WHERE student_id = ?");
        preparedStatement.setLong(1,student.getStudentId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertTrue(resultSet.getInt(1) > 0);
    }

    @Test
    void findById() throws SQLException{
        Student student = studentDao.save(new Student("Ivan","Ivanov",new Group(1L)));
        Student studentForFind = new Student(1L);
        assertEquals(student,studentDao.findById(studentForFind));
    }

    @Test
    void findAll() throws SQLException{
        List<Student> students = new ArrayList<>();
        students.add(studentDao.save(new Student("Ivan","Ivanov",new Group(1L))));
        assertEquals(students,studentDao.findAll());
    }

    @Test
    void update() throws SQLException{
        studentDao.save(new Student("Ivan","Ivanov",new Group(1L)));
        Student student = new Student("Victor","Victorov",new Group(2L));
        studentDao.update(1L,student);
        assertEquals("Victor",studentDao.findById(new Student(1L)).getFirstName());
    }

    @Test
    void delete() throws SQLException{
        studentDao.save(new Student("Ivan","Ivanov",new Group(1L)));
        studentDao.delete(new Student(1L));
        assertTrue(studentDao.findAll().isEmpty());
    }
}
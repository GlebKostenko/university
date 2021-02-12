package com.foxminded.dao;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Group;
import com.foxminded.model.Student;
import com.foxminded.model.Teacher;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    private SessionFactory factory;
    private static final Logger logger = LoggerFactory.getLogger(StudentDao.class.getSimpleName());

    @Autowired
    public StudentDao(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public Student save(Student student) {
        logger.debug("Trying to insert new record in students table");
        try (final Session session = factory.openSession()) {

            session.beginTransaction();

            student.setStudentId((Long) session.save(student));

            session.getTransaction().commit();

            return student;
        }
    }

    @Override
    public Student findById(Student student) {
        logger.debug("Trying to find student with id: {}",student.getStudentId());
        try (final Session session = factory.openSession()) {
            Student result = session.get(Student.class, student.getStudentId());
            if(result != null){
                Hibernate.initialize(result.getGroup());
            }
            return result;
        }
    }

    @Override
    public List<Student> findAll() {
        logger.debug("Trying to return existing students");
        try (final Session session = factory.openSession()) {
            List<Student> students = session.createQuery("SELECT a FROM Student a", Student.class).getResultList();
            students.stream().forEach((x) -> Hibernate.initialize(x.getGroup()));
            return students;
        }
    }

    @Override
    public void update(Student student) {
        logger.debug("Updating record with id: {}",student.getStudentId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.update(student);

            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Student student) {
        logger.debug("Deleting record with id: {}",student.getStudentId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.delete(student);

            session.getTransaction().commit();
        }
    }
}

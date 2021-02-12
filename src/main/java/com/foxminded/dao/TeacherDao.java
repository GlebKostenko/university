package com.foxminded.dao;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.LectureHall;
import com.foxminded.model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    private SessionFactory factory;
    private static final Logger logger = LoggerFactory.getLogger(TeacherDao.class.getSimpleName());

    @Autowired
    public TeacherDao(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Teacher save(Teacher teacher) {
        logger.debug("Trying to insert new record in teachers table");
        try (final Session session = factory.openSession()) {

            session.beginTransaction();

            teacher.setTeacherId((Long) session.save(teacher));

            session.getTransaction().commit();

            return teacher;
        }
    }

    @Override
    public Teacher findById(Teacher teacher) {
        logger.debug("Trying to find teacher with id: {}",teacher.getTeacherId());
        try (final Session session = factory.openSession()) {
            return session.get(Teacher.class, teacher.getTeacherId());
        }
    }

    @Override
    public List<Teacher> findAll() {
        logger.debug("Trying to return existing teachers");
        try (final Session session = factory.openSession()) {
            return session.createQuery("SELECT a FROM Teacher a", Teacher.class).getResultList();
        }
    }

    @Override
    public void update(Teacher teacher) {
        logger.debug("Updating record with id: {}",teacher.getTeacherId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.update(teacher);

            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Teacher teacher) {
        logger.debug("Deleting record with id: {}",teacher.getTeacherId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.delete(teacher);

            session.getTransaction().commit();
        }
    }
}

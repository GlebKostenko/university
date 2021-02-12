package com.foxminded.dao;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.LectureHall;
import com.foxminded.model.Subject;
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
public class SubjectDao implements Dao<Subject>{
    private SessionFactory factory;
    private static final Logger logger = LoggerFactory.getLogger(SubjectDao.class.getSimpleName());
    @Autowired
    public SubjectDao(SessionFactory factory){
        this.factory = factory;
    }
    @Override
    public Subject save(Subject subject) {
        logger.debug("Trying to insert new record in subjects table");
        try (final Session session = factory.openSession()) {

            session.beginTransaction();

            subject.setSubjectId((Long) session.save(subject));

            session.getTransaction().commit();

            return subject;
        }
    }

    @Override
    public Subject findById(Subject subject) {
        logger.debug("Trying to find subject with id: {}",subject.getSubjectId());
        try (final Session session = factory.openSession()) {
            return session.get(Subject.class, subject.getSubjectId());
        }
    }

    @Override
    public List<Subject> findAll() {
        logger.debug("Trying to return existing subjects");
        try (final Session session = factory.openSession()) {
            return session.createQuery("SELECT a FROM Subject a", Subject.class).getResultList();
        }
    }

    @Override
    public void update(Subject subject) {
        logger.debug("Updating record with id: {}",subject.getSubjectId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.update(subject);

            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Subject subject) {
        logger.debug("Deleting record with id: {}",subject.getSubjectId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.delete(subject);

            session.getTransaction().commit();
        }
    }
}

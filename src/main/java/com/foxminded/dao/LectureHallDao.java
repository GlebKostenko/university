package com.foxminded.dao;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Group;
import com.foxminded.model.LectureHall;
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
public class LectureHallDao implements Dao<LectureHall>{
    private SessionFactory factory;
    private static final Logger logger = LoggerFactory.getLogger(LectureHallDao.class.getSimpleName());
    @Autowired
    public LectureHallDao(SessionFactory factory){
          this.factory = factory;
    }

    @Override
    public LectureHall save(LectureHall lectureHall) {
        logger.debug("Trying to insert new record in Lecture_halls table");
        try (final Session session = factory.openSession()) {

            session.beginTransaction();

            lectureHall.setHallId((Long) session.save(lectureHall));

            session.getTransaction().commit();

            return lectureHall;
        }
    }

    @Override
    public LectureHall findById(LectureHall lectureHall) {
        logger.debug("Trying to find hall with id: {}",lectureHall.getHallId());
        try (final Session session = factory.openSession()) {
            return session.get(LectureHall.class, lectureHall.getHallId());
        }
    }

    @Override
    public List<LectureHall> findAll() {
        logger.debug("Trying to return existing halls");
        try (final Session session = factory.openSession()) {
            return session.createQuery("SELECT a FROM LectureHall a", LectureHall.class).getResultList();
        }
    }

    @Override
    public void update(LectureHall lectureHall) {
        logger.debug("Updating record with id: {}",lectureHall.getHallId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.update(lectureHall);

            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(LectureHall lectureHall) {
        logger.debug("Deleting record with id: {}",lectureHall.getHallId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.delete(lectureHall);

            session.getTransaction().commit();
        }
    }
}

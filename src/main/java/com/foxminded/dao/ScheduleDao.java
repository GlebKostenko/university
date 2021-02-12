package com.foxminded.dao;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.*;
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
public class ScheduleDao implements Dao<Schedule>{
    private SessionFactory factory;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleDao.class.getSimpleName());

    @Autowired
    public ScheduleDao(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public Schedule save(Schedule schedule)  {
        logger.debug("Trying to insert new record in schedules table");
        try (final Session session = factory.openSession()) {

            session.beginTransaction();

            schedule.setScheduleId((Long) session.save(schedule));

            session.getTransaction().commit();

            return schedule;
        }
    }

    @Override
    public Schedule findById(Schedule schedule)  {
        logger.debug("Trying to find schedule with id: {}",schedule.getScheduleId());
        try (final Session session = factory.openSession()) {
             Schedule result = session.get(Schedule.class, schedule.getScheduleId());
             if(result != null){
                 Hibernate.initialize(result.getGroup());
                 Hibernate.initialize(result.getTeacher());
                 Hibernate.initialize(result.getLectureHall());
                 Hibernate.initialize(result.getSubject());
             }
             return result;
        }
    }

    @Override
    public List<Schedule> findAll() {
        logger.debug("Trying to return existing schedules");
        try (final Session session = factory.openSession()) {
            List<Schedule> schedules = session.createQuery("SELECT a FROM Schedule a", Schedule.class).getResultList();
            schedules.stream().forEach((x) -> {
                Hibernate.initialize(x.getGroup());
                Hibernate.initialize(x.getTeacher());
                Hibernate.initialize(x.getLectureHall());
                Hibernate.initialize(x.getSubject());
            });
            return schedules;
        }
    }

    @Override
    public void update(Schedule schedule) {
        logger.debug("Updating record with id: {}",schedule.getScheduleId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.update(schedule);

            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Schedule schedule) {
        logger.debug("Deleting record with id: {}",schedule.getScheduleId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.delete(schedule);

            session.getTransaction().commit();
        }
    }
}

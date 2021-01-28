package com.foxminded.dao;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.LectureHall;
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
    private static final Logger logger = LoggerFactory.getLogger(LectureHallDao.class.getSimpleName());
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public LectureHallDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public LectureHall save(LectureHall lectureHall) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("hall_name",lectureHall.getHallName());
        logger.debug("Trying to insert new record in Lecture_halls table");
        Long id = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("lecture_halls")
                .usingGeneratedKeyColumns("hall_id")
                .executeAndReturnKey(parameters).longValue();
        lectureHall.setHallId(id);
        logger.debug("Return LectureHall with id: {}", id);
        return  lectureHall;
    }

    @Override
    public LectureHall findById(LectureHall lectureHall) {
        logger.debug("Trying to find hall with id: {}",lectureHall.getHallId());
        try {
            return jdbcTemplate.queryForObject("SELECT hall_id ,hall_name FROM lecture_halls WHERE hall_id =?"
                    , new BeanPropertyRowMapper<LectureHall>(LectureHall.class), lectureHall.getHallId()
            );
        }catch (EmptyResultDataAccessException e){
            logger.warn("Hall with the same id wasn't found");
            throw new EmptyResultSetExceptionDao("Lecture_halls table doesn't contain this record",e);
        }
    }

    @Override
    public List<LectureHall> findAll() {
        logger.debug("Trying to return existing halls");
        try {
            return jdbcTemplate.query("SELECT hall_id,hall_name FROM lecture_halls"
                    , new BeanPropertyRowMapper<LectureHall>(LectureHall.class)
            );
        }catch (EmptyResultDataAccessException e){
            logger.warn("List of halls is empty");
            throw new EmptyResultSetExceptionDao("Lecture halls table is empty",e);
        }
    }

    @Override
    public void update(LectureHall lectureHall) {
        logger.debug("Updating record with id: {}",lectureHall.getHallId());
        jdbcTemplate.update("UPDATE lecture_halls SET hall_name = ? WHERE hall_id = ?"
                ,lectureHall.getHallName()
                ,lectureHall.getHallId());
    }

    @Override
    public void delete(LectureHall lectureHall) {
        logger.debug("Deleting record with id: {}",lectureHall.getHallId());
        jdbcTemplate.update("DELETE FROM lecture_halls WHERE hall_id = ?",lectureHall.getHallId());
    }
}

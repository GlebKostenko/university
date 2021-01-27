package com.foxminded.dao;

import com.foxminded.model.LectureHall;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public LectureHallDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public LectureHall save(LectureHall lectureHall) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("hall_name",lectureHall.getHallName());
        Long id = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("lecture_halls")
                .usingGeneratedKeyColumns("hall_id")
                .executeAndReturnKey(parameters).longValue();
        lectureHall.setHallId(id);
        return  lectureHall;
    }

    @Override
    public LectureHall findById(LectureHall lectureHall) {
        return jdbcTemplate.queryForObject("SELECT hall_id ,hall_name FROM lecture_halls WHERE hall_id =?"
                ,new BeanPropertyRowMapper<LectureHall>(LectureHall.class),lectureHall.getHallId()
        );
    }

    @Override
    public List<LectureHall> findAll() {
        return jdbcTemplate.query("SELECT hall_id,hall_name FROM lecture_halls"
                ,new BeanPropertyRowMapper<LectureHall>(LectureHall.class)
        );
    }

    @Override
    public void update(LectureHall lectureHall) {
        jdbcTemplate.update("UPDATE lecture_halls SET hall_name = ? WHERE hall_id = ?"
                ,lectureHall.getHallName()
                ,lectureHall.getHallId());
    }

    @Override
    public void delete(LectureHall lectureHall) {
        jdbcTemplate.update("DELETE FROM lecture_halls WHERE hall_id = ?",lectureHall.getHallId());
    }
}

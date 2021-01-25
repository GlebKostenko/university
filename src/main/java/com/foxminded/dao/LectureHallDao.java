package com.foxminded.dao;

import com.foxminded.model.LectureHall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public LectureHall save(LectureHall lectureHall) throws SQLException {

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("hall_name",lectureHall.getHallName());
        Long id = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("lecture_halls")
                .usingGeneratedKeyColumns("hall_id")
                .executeAndReturnKey(parameters).longValue();
        return  new LectureHall(id,lectureHall.getHallName());
    }

    @Override
    public LectureHall findById(LectureHall lectureHall) throws SQLException {
        return jdbcTemplate.queryForObject("SELECT hall_id ,hall_name FROM lecture_halls WHERE hall_id =?"
                ,new BeanPropertyRowMapper<LectureHall>(LectureHall.class),lectureHall.getHallId()
        );
    }

    @Override
    public List<?> findAll() throws SQLException {
        return jdbcTemplate.query("SELECT hall_id,hall_name FROM lecture_halls"
                ,new BeanPropertyRowMapper<LectureHall>(LectureHall.class)
        );
    }

    @Override
    public void update(Long hallId, LectureHall lectureHall) {
        jdbcTemplate.update("UPDATE lecture_halls SET hall_name = ? WHERE hall_id = ?",lectureHall.getHallName(),hallId);
    }

    @Override
    public void delete(LectureHall lectureHall) {
        jdbcTemplate.update("DELETE FROM lecture_halls WHERE hall_id = ?",lectureHall.getHallId());
    }
}

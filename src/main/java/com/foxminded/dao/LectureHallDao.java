package com.foxminded.dao;

import com.foxminded.model.LectureHall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LectureHallDao implements Dao<LectureHall>{
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public LectureHallDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public LectureHall save(LectureHall lectureHall) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO lecture_halls(hall_name) VALUES ?",new String[]{"hall_id"});
            ps.setString(1, lectureHall.getHallName());
            return ps;
        },keyHolder);
        return new LectureHall(keyHolder.getKey().longValue(),lectureHall.getHallName());
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

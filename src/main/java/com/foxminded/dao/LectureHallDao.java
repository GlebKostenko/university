package com.foxminded.dao;

import com.foxminded.model.LectureHall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        jdbcTemplate.update("INSERT INTO lecture_halls(hall_name) VALUES ?",lectureHall.getHallName());
        return jdbcTemplate.queryForObject("SELECT hall_id,hall_name FROM lecture_halls WHERE hall_name = ?"
                ,new Object[] {lectureHall.getHallName()}
                ,(rs,rowNum)->
                new LectureHall(rs.getLong(1),rs.getString(2))
        );
    }

    @Override
    public LectureHall findById(LectureHall lectureHall) throws SQLException {
        return jdbcTemplate.queryForObject("SELECT hall_id,hall_name FROM lecture_halls WHERE hall_id =?"
                ,new Object[]{lectureHall.getHallId()}
                ,(rs,rowNum)->
                new LectureHall(rs.getLong(1),rs.getString(2))
        );
    }

    @Override
    public List<?> findAll() throws SQLException {
        return jdbcTemplate.query("SELECT hall_id,hall_name FROM lecture_halls",(rs,rowNum) ->
                new LectureHall(rs.getLong(1),rs.getString(2)));
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

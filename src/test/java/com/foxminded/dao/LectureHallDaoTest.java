package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.LectureHall;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class LectureHallDaoTest {
    @Autowired
    LectureHallDao lectureHallDao;
    @Test
    void save() throws SQLException {
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("Bolishaya fizicheskaya"));
        assertEquals(lectureHall,lectureHallDao.findById(new LectureHall(lectureHall.getHallId())));
    }

    @Test
    void findById() throws SQLException{
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("NK 202"));
        assertTrue(lectureHallDao.findById(new LectureHall(lectureHall.getHallId())).equals(lectureHall));
    }

    @Test
    void findAll() throws SQLException{
        lectureHallDao.save(new LectureHall("Bolishaya chimisheskaya"));
        assertTrue(!lectureHallDao.findAll().isEmpty());
    }

    @Test
    void update() throws SQLException{
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("123GK"));
        LectureHall lectureHallNew = new LectureHall(lectureHall.getHallId(),"113GK");
        lectureHallDao.update(lectureHallNew);
        LectureHall updatedLectureHall = new LectureHall(lectureHall.getHallId(),"113GK");
        assertEquals(updatedLectureHall,lectureHallDao.findById(new LectureHall(lectureHall.getHallId())));
    }

    @Test
    void delete() throws SQLException{
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("110 KPM"));
        lectureHallDao.delete(new LectureHall(lectureHall.getHallId()));
        assertFalse(lectureHallDao.findAll().contains(lectureHall));
    }
}
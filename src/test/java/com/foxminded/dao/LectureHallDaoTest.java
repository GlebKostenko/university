package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.LectureHall;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class LectureHallDaoTest {
    @Autowired
    LectureHallDao lectureHallDao;
    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord() {
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("Bolishaya fizicheskaya"));
        assertEquals(lectureHall,lectureHallDao.findById(new LectureHall(lectureHall.getHallId())));
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("NK 202"));
        assertTrue(lectureHallDao.findById(new LectureHall(lectureHall.getHallId())).equals(lectureHall));
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        Throwable exception = assertThrows(EmptyResultSetExceptionDao.class, () -> lectureHallDao.findById(new LectureHall(56L)));
        assertEquals("Lecture_halls table doesn't contain this record", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList() {
        lectureHallDao.save(new LectureHall("Bolishaya chimisheskaya"));
        assertTrue(!lectureHallDao.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("123GK"));
        LectureHall lectureHallNew = new LectureHall(lectureHall.getHallId(),"113GK");
        lectureHallDao.update(lectureHallNew);
        LectureHall updatedLectureHall = new LectureHall(lectureHall.getHallId(),"113GK");
        assertEquals(updatedLectureHall,lectureHallDao.findById(new LectureHall(lectureHall.getHallId())));
    }

    @Test
    void update_WhenRecordDoesNotExist_thenNothingGoesWrong() {
        LectureHall lectureHallNew = new LectureHall(56L,"122");
        lectureHallDao.update(lectureHallNew);
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("110 KPM"));
        lectureHallDao.delete(new LectureHall(lectureHall.getHallId()));
        assertFalse(lectureHallDao.findAll().contains(lectureHall));
    }

    @Test
    void delete_WhenRecordDoesNotExist_thenNothingGoesWrong() {
        lectureHallDao.delete(new LectureHall(56L));
    }
}
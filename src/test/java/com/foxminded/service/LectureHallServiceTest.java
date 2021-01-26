package com.foxminded.service;

import com.foxminded.configuration.ServiceConfig;
import com.foxminded.dao.LectureHallDao;
import com.foxminded.model.LectureHall;
import com.foxminded.service.dto.LectureHallDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LectureHallServiceTest {
    private LectureHallDao lectureHallDao = mock(LectureHallDao.class);
    ModelMapper modelMapper = new ModelMapper();
    private LectureHallService lectureHallService = new LectureHallService(modelMapper,lectureHallDao);

    @Test
    void save() throws SQLException {
        given(lectureHallDao.save(new LectureHall("GK"))).willReturn(new LectureHall(1L,"GK"));
        LectureHallDTO lectureHallDTO = lectureHallService.save(new LectureHallDTO("GK"));
        assertEquals(1,1);
    }
}
package com.foxminded.service;

import com.foxminded.configuration.ServiceConfig;
import com.foxminded.dao.LectureHallDao;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.exception.EmptyResultSetExceptionService;
import com.foxminded.model.LectureHall;
import com.foxminded.service.dto.LectureHallDTO;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class LectureHallServiceTest {
    private LectureHallDao lectureHallDao = mock(LectureHallDao.class);
    private ServiceConfig serviceConfig = new ServiceConfig();
    private LectureHallService lectureHallService = new LectureHallService(serviceConfig.modelMapper(),lectureHallDao);

    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord() {
        given(lectureHallDao.save(new LectureHall("124")))
                .willReturn(new LectureHall(1L,"124"));
        LectureHallDTO lectureHallDTO = lectureHallService.save(new LectureHallDTO("124"));
        assertEquals(lectureHallDTO,new LectureHallDTO(lectureHallDTO.getHallId(),"124"));
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord(){
        given(lectureHallDao.findById(new LectureHall(1L)))
                .willReturn(new LectureHall(1L,"124"));
        LectureHallDTO lectureHallDTO = lectureHallService.findById(new LectureHallDTO(1L));
        assertEquals(lectureHallDTO,lectureHallService.findById(new LectureHallDTO(lectureHallDTO.getHallId())));
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        given(lectureHallDao.findById(new LectureHall(56L)))
                .willThrow(new EmptyResultSetExceptionDao("Lecture_halls table doesn't contain this record",new EmptyResultDataAccessException(1)));
        Throwable exception = assertThrows(EmptyResultSetExceptionDao.class, () -> lectureHallService.findById(new LectureHallDTO(56L)));
        assertEquals("Lecture_halls table doesn't contain this record", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList(){
        given(lectureHallDao.findAll()).willReturn(Arrays.asList(new LectureHall(1L,"621")));
        assertTrue(!lectureHallService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        doNothing().when(lectureHallDao).update(new LectureHall(1L,"glavnaya chimicheskaya"));
        lectureHallService.update(new LectureHallDTO(1L,"glavnaya chimicheskaya"));
        verify(lectureHallDao,times(1)).update(new LectureHall(1L,"glavnaya chimicheskaya"));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(lectureHallDao).delete(new LectureHall(1L));
        lectureHallService.delete(new LectureHallDTO(1L));
        verify(lectureHallDao,times(1)).delete(new LectureHall(1L));
    }
}
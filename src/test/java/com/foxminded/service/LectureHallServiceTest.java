package com.foxminded.service;

import com.foxminded.configuration.ServiceConfig;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.LectureHall;
import com.foxminded.repository.LectureHallRepository;
import com.foxminded.service.dto.LectureHallDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

class LectureHallServiceTest {
    @Mock
    private LectureHallRepository lectureHallRepository;
    @InjectMocks
    private LectureHallService lectureHallService;

    LectureHallServiceTest(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord() {
        given(lectureHallRepository.save(new LectureHallDTO("124")))
                .willReturn(new LectureHallDTO(1L,"124"));
        LectureHallDTO lectureHallDTO = lectureHallService.save(new LectureHallDTO("124"));
        assertEquals(lectureHallDTO,new LectureHallDTO(lectureHallDTO.getHallId(),"124"));
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord(){
        given(lectureHallRepository.findById(1L))
                .willReturn(Optional.of(new LectureHallDTO(1L,"124")));
        LectureHallDTO lectureHallDTO = lectureHallService.findById(new LectureHallDTO(1L));
        assertEquals(lectureHallDTO,lectureHallService.findById(new LectureHallDTO(lectureHallDTO.getHallId())));
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        given(lectureHallRepository.findById(56L))
                .willThrow(new EmptyResultSetExceptionDao("Lecture_halls table doesn't contain this record",new EmptyResultDataAccessException(1)));
        Throwable exception = assertThrows(EmptyResultSetExceptionDao.class, () -> lectureHallService.findById(new LectureHallDTO(56L)));
        assertEquals("Lecture_halls table doesn't contain this record", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList(){
        given(lectureHallRepository.findAll()).willReturn(Arrays.asList(new LectureHallDTO(1L,"621")));
        assertTrue(!lectureHallService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        lectureHallService.update(new LectureHallDTO(1L,"glavnaya chimicheskaya"));
        verify(lectureHallRepository,times(1)).save(new LectureHallDTO(1L,"glavnaya chimicheskaya"));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(lectureHallRepository).delete(new LectureHallDTO(1L));
        lectureHallService.delete(new LectureHallDTO(1L));
        verify(lectureHallRepository,times(1)).delete(new LectureHallDTO(1L));
    }
}
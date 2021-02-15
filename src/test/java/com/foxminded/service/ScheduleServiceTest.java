package com.foxminded.service;


import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.*;
import com.foxminded.repository.ScheduleRepository;
import com.foxminded.service.dto.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {
    ModelMapper mapper = new ModelMapper();
    ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
    ScheduleService scheduleService = new ScheduleService(mapper,scheduleRepository);


    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        given(scheduleRepository.save(new Schedule(
                    new Group(3L)
                    ,localDateTime
                    ,3600
                    ,new Teacher(7L)
                    ,new LectureHall(223L)
                    ,new Subject(9L))
                )
        ).willReturn(new Schedule(
                1L,
                new Group(3L)
                ,localDateTime
                ,3600
                ,new Teacher(7L)
                ,new LectureHall(223L)
                ,new Subject(9L))
        );
        ScheduleDTO scheduleDTO = scheduleService.save(
                new ScheduleDTO(
                        new GroupDTO(3L)
                        ,localDateTime
                        ,3600
                        ,new TeacherDTO(7L)
                        ,new LectureHallDTO(223L)
                        ,new SubjectDTO(9L))
        );
        assertEquals(1L,(long)scheduleDTO.getScheduleId());
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord(){
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        given(scheduleRepository.findById(1L)).willReturn(Optional.of(new Schedule(
                1L,
                new Group(3L,"frkt-001")
                ,localDateTime
                ,3600
                ,new Teacher(7L,"Semen","Yokhov")
                ,new LectureHall(223L,"Harmony")
                ,new Subject(9L,"Physics"))
        ));
        ScheduleDTO scheduleDTO = scheduleService.findById(new ScheduleDTO(1L));
        assertEquals(scheduleDTO.getGroup().getGroupName(),"frkt-001");
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        given(scheduleRepository.findById(77L))
                .willThrow(new EmptyResultSetExceptionDao("Schedules table doesn't contain this record",new EmptyResultDataAccessException(1)));
        Throwable exception = assertThrows(EmptyResultSetExceptionDao.class, () -> scheduleService.findById(new ScheduleDTO(77L)));
        assertEquals("Schedules table doesn't contain this record", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList(){
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        given(scheduleRepository.findAll()).willReturn(Arrays.asList(new Schedule(
                        1L,
                        new Group(3L,"frkt-001")
                        ,localDateTime
                        ,3600
                        ,new Teacher(7L,"Semen","Yokhov")
                        ,new LectureHall(223L,"Harmony")
                        ,new Subject(9L,"Physics"))
                )
        );
        assertTrue(!scheduleService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        ScheduleDTO scheduleForUpdate = new ScheduleDTO(
                1L,
                new GroupDTO(3L,"frkt-001")
                ,localDateTime
                ,3600
                ,new TeacherDTO(7L,"Semen","Yokhov")
                ,new LectureHallDTO(223L,"Harmony")
                ,new SubjectDTO(9L,"Physics")
        );
        scheduleService.update(scheduleForUpdate);
        verify(scheduleRepository,times(1)).save(new Schedule(
                1L,
                new Group(3L,"frkt-001")
                ,localDateTime
                ,3600
                ,new Teacher(7L,"Semen","Yokhov")
                ,new LectureHall(223L,"Harmony")
                ,new Subject(9L,"Physics")
        ));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(scheduleRepository).delete(new Schedule(1L));
        scheduleService.delete(new ScheduleDTO(1L));
        verify(scheduleRepository,times(1)).delete(new Schedule(1L));
    }
}
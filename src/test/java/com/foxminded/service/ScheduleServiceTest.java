package com.foxminded.service;

import com.foxminded.dao.ScheduleDao;
import com.foxminded.model.*;
import com.foxminded.service.dto.*;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {
    ScheduleDao scheduleDao = mock(ScheduleDao.class);
    ModelMapper modelMapper = new ModelMapper();
    ScheduleService scheduleService = new ScheduleService(modelMapper,scheduleDao);

    @Test
    void save() throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        given(scheduleDao.save(new Schedule(
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
        assertEquals(1L,scheduleDTO.getScheduleId());
    }

    @Test
    void findById() throws SQLException{
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        given(scheduleDao.findById(new Schedule(1L))).willReturn(new Schedule(
                1L,
                new Group(3L,"frkt-001")
                ,localDateTime
                ,3600
                ,new Teacher(7L,"Semen","Yokhov")
                ,new LectureHall(223L,"Harmony")
                ,new Subject(9L,"Physics"))
        );
        ScheduleDTO scheduleDTO = scheduleService.findById(new ScheduleDTO(1L));
        assertEquals(scheduleDTO.getGroup().getGroupName(),"frkt-001");
    }

    @Test
    void findAll() throws SQLException{
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        given(scheduleDao.findAll()).willReturn(Arrays.asList(new Schedule(
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
    void update() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        Schedule scheduleForUpdate = new Schedule(
                1L,
                new Group(3L,"frkt-001")
                ,localDateTime
                ,3600
                ,new Teacher(7L,"Semen","Yokhov")
                ,new LectureHall(223L,"Harmony")
                ,new Subject(9L,"Physics")
        );
        doNothing().when(scheduleDao).update(scheduleForUpdate);
        scheduleService.update(new ScheduleDTO(
                1L,
                new GroupDTO(3L,"frkt-001")
                ,localDateTime
                ,3600
                ,new TeacherDTO(7L,"Semen","Yokhov")
                ,new LectureHallDTO(223L,"Harmony")
                ,new SubjectDTO(9L,"Physics")
                )
        );
        verify(scheduleDao,times(1)).update(scheduleForUpdate);
    }

    @Test
    void delete() {
        doNothing().when(scheduleDao).delete(new Schedule(1L));
        scheduleService.delete(new ScheduleDTO(1L));
        verify(scheduleDao,times(1)).delete(new Schedule(1L));
    }
}
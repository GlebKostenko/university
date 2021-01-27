package com.foxminded.service;

import com.foxminded.dao.TeacherDao;
import com.foxminded.model.Teacher;
import com.foxminded.service.dto.TeacherDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TeacherServiceTest {
    TeacherDao teacherDao = mock(TeacherDao.class);
    ModelMapper modelMapper = new ModelMapper();
    TeacherService teacherService = new TeacherService(modelMapper,teacherDao);

    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord() {
        given(teacherDao.save(new Teacher("Lev","Landau")))
                .willReturn(new Teacher(1L,"Lev","Landau"));
        TeacherDTO teacherDTO = teacherService.save(new TeacherDTO("Lev","Landau"));
        assertEquals(1L,teacherDTO.getTeacherId());
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord(){
        given(teacherDao.findById(new Teacher(1L)))
                .willReturn(new Teacher(1L,"Lev","Landau"));
        TeacherDTO teacherDTO = teacherService.findById(new TeacherDTO(1L));
        assertEquals("Lev",teacherDTO.getFirstName());
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        given(teacherDao.findById(new Teacher(83L))).willThrow(new EmptyResultDataAccessException(1));
        Throwable exception = assertThrows(EmptyResultDataAccessException.class, () -> teacherService.findById(new TeacherDTO(83L)));
        assertEquals("Incorrect result size: expected 1, actual 0", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList(){
        given(teacherDao.findAll())
                .willReturn(Arrays.asList(new Teacher(1L,"Lev","Landau")));
        assertTrue(!teacherService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        doNothing().when(teacherDao).update(new Teacher(1L,"Petr","Petrov"));
        teacherService.update(new TeacherDTO(1L,"Petr","Petrov"));
        verify(teacherDao,times(1)).update(new Teacher(1L,"Petr","Petrov"));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(teacherDao).delete(new Teacher(1L));
        teacherService.delete(new TeacherDTO(1L));
        verify(teacherDao,times(1)).delete(new Teacher(1L));
    }
}
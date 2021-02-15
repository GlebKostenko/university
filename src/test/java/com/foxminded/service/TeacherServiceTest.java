package com.foxminded.service;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Teacher;
import com.foxminded.repository.TeacherRepository;
import com.foxminded.service.dto.TeacherDTO;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TeacherServiceTest {
    ModelMapper mapper = new ModelMapper();
    TeacherRepository teacherRepository = mock(TeacherRepository.class);
    TeacherService teacherService = new TeacherService(mapper,teacherRepository);

    TeacherServiceTest(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord() {
        given(teacherRepository.save(new Teacher("Lev","Landau")))
                .willReturn(new Teacher(1L,"Lev","Landau"));
        TeacherDTO teacherDTO = teacherService.save(new TeacherDTO("Lev","Landau"));
        assertEquals(1L,(long)teacherDTO.getTeacherId());
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord(){
        given(teacherRepository.findById(1L))
                .willReturn(Optional.of(new Teacher(1L,"Lev","Landau")));
        TeacherDTO teacherDTO = teacherService.findById(new TeacherDTO(1L));
        assertEquals("Lev",teacherDTO.getFirstName());
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        given(teacherRepository.findById(83L))
                .willThrow(new EmptyResultSetExceptionDao("Teachers table doesn't contain this record",new EmptyResultDataAccessException(1)));
        Throwable exception = assertThrows(EmptyResultSetExceptionDao.class, () -> teacherService.findById(new TeacherDTO(83L)));
        assertEquals("Teachers table doesn't contain this record", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList(){
        given(teacherRepository.findAll())
                .willReturn(Arrays.asList(new Teacher(1L,"Lev","Landau")));
        assertTrue(!teacherService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        teacherService.update(new TeacherDTO(1L,"Petr","Petrov"));
        verify(teacherRepository,times(1)).save(new Teacher(1L,"Petr","Petrov"));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(teacherRepository).delete(new Teacher(1L));
        teacherService.delete(new TeacherDTO(1L));
        verify(teacherRepository,times(1)).delete(new Teacher(1L));
    }
}
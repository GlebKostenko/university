package com.foxminded.service;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.repository.StudentRepository;
import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.StudentDTO;
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
import static org.mockito.Mockito.*;

class StudentServiceTest {
    @Mock
    StudentRepository studentRepository;
    @InjectMocks
    StudentService studentService;

    StudentServiceTest(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord()  {
        given(studentRepository.save(new StudentDTO("Ivan","Ivanov",new GroupDTO(977L))))
                .willReturn(new StudentDTO(1L,"Ivan","Ivanov",new GroupDTO(977L)));
        StudentDTO studentDTO = studentService
                .save(new StudentDTO("Ivan","Ivanov",new GroupDTO(977L)));
        assertEquals(1L,(long)studentDTO.getStudentId());
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        given(studentRepository.findById(1L))
                .willReturn(Optional.of(new StudentDTO(1L,"Ivan","Ivanov",new GroupDTO(977L,"977"))));
        StudentDTO studentDTO = studentService.findById(new StudentDTO(1L));
        assertEquals(studentDTO.getGroup().getGroupName(),"977");
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        given(studentRepository.findById(98L))
                .willThrow(new EmptyResultSetExceptionDao("Students table doesn't contain this record",new EmptyResultDataAccessException(1)));
        Throwable exception = assertThrows(EmptyResultSetExceptionDao.class, () -> studentService.findById(new StudentDTO(98L)));
        assertEquals("Students table doesn't contain this record", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList() {
        given(studentRepository.findAll())
                .willReturn(Arrays.asList(
                        new StudentDTO(1L,"Ivan","Ivanov",new GroupDTO(977L,"977"))
                        )
                );
        assertTrue(!studentService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        studentService.update(new StudentDTO(1L,"Ivan","Ivanov",new GroupDTO(454L)));
        verify(studentRepository,times(1)).save(new StudentDTO(1L,"Ivan","Ivanov",new GroupDTO(454L)));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(studentRepository).delete(new StudentDTO(1L));
        studentService.delete(new StudentDTO(1L));
        verify(studentRepository,times(1)).delete(new StudentDTO(1L));
    }
}
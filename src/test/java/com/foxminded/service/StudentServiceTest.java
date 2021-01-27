package com.foxminded.service;

import com.foxminded.dao.StudentDao;
import com.foxminded.model.Group;
import com.foxminded.model.Student;
import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.StudentDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    StudentDao studentDao = mock(StudentDao.class);
    ModelMapper modelMapper = new ModelMapper();
    StudentService studentService = new StudentService(modelMapper,studentDao);

    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord()  {
        given(studentDao.save(new Student("Ivan","Ivanov",new Group(977L))))
                .willReturn(new Student(1L,"Ivan","Ivanov",new Group(977L)));
        StudentDTO studentDTO = studentService
                .save(new StudentDTO("Ivan","Ivanov",new GroupDTO(977L)));
        assertEquals(1L,studentDTO.getStudentId());
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        given(studentDao.findById(new Student(1L)))
                .willReturn(new Student(1L,"Ivan","Ivanov",new Group(977L,"977")));
        StudentDTO studentDTO = studentService.findById(new StudentDTO(1L));
        assertEquals(studentDTO.getGroup().getGroupName(),"977");
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        given(studentDao.findById(new Student(98L))).willThrow(new EmptyResultDataAccessException(1));
        Throwable exception = assertThrows(EmptyResultDataAccessException.class, () -> studentService.findById(new StudentDTO(98L)));
        assertEquals("Incorrect result size: expected 1, actual 0", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList() {
        given(studentDao.findAll())
                .willReturn(Arrays.asList(
                        new Student(1L,"Ivan","Ivanov",new Group(977L,"977"))
                        )
                );
        assertTrue(!studentService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        doNothing().when(studentDao).update(new Student(1L,"Ivan","Ivanov",new Group(454L)));
        studentService.update(new StudentDTO(1L,"Ivan","Ivanov",new GroupDTO(454L)));
        verify(studentDao,times(1)).update(new Student(1L,"Ivan","Ivanov",new Group(454L)));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(studentDao).delete(new Student(1L));
        studentService.delete(new StudentDTO(1L));
        verify(studentDao,times(1)).delete(new Student(1L));
    }
}
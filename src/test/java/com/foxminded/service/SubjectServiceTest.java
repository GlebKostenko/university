package com.foxminded.service;

import com.foxminded.dao.SubjectDao;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Subject;
import com.foxminded.service.dto.SubjectDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SubjectServiceTest {
    SubjectDao subjectDao = mock(SubjectDao.class);
    ModelMapper modelMapper = new ModelMapper();
    SubjectService subjectService = new SubjectService(modelMapper,subjectDao);

    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord()  {
        given(subjectDao.save(new Subject("chemistry")))
                .willReturn(new Subject(1L,"chemistry"));
        SubjectDTO subjectDTO = subjectService.save(new SubjectDTO("chemistry"));
        assertEquals(subjectDTO,new SubjectDTO(subjectDTO.getSubjectId(),"chemistry"));
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        given(subjectDao.findById(new Subject(1L)))
                .willReturn(new Subject(1L,"chemistry"));
        SubjectDTO subjectDTO = subjectService.findById(new SubjectDTO(1L));
        assertEquals(subjectDTO,new SubjectDTO(subjectDTO.getSubjectId(),"chemistry"));
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        given(subjectDao.findById(new Subject(55L)))
                .willThrow(new EmptyResultSetExceptionDao("Subjects table doesn't contain this record",new EmptyResultDataAccessException(1)));
        Throwable exception = assertThrows(EmptyResultSetExceptionDao.class, () -> subjectService.findById(new SubjectDTO(55L)));
        assertEquals("Subjects table doesn't contain this record", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList() {
        given(subjectDao.findAll()).willReturn(Arrays.asList(new Subject(1L,"chemistry")));
        assertTrue(!subjectService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        doNothing().when(subjectDao).update(new Subject(1L,"chemistry"));
        subjectService.update(new SubjectDTO(1L,"chemistry"));
        verify(subjectDao,times(1)).update(new Subject(1l,"chemistry"));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(subjectDao).delete(new Subject(1L));
        subjectService.delete(new SubjectDTO(1L));
        verify(subjectDao,times(1)).delete(new Subject(1L));
    }
}
package com.foxminded.service;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Subject;
import com.foxminded.repository.SubjectRepository;
import com.foxminded.service.dto.SubjectDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SubjectServiceTest {
    ModelMapper mapper = new ModelMapper();
    SubjectRepository subjectRepository = mock(SubjectRepository.class);
    SubjectService subjectService = new SubjectService(mapper,subjectRepository);

    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord()  {
        given(subjectRepository.save(new Subject("chemistry")))
                .willReturn(new Subject(1L,"chemistry"));
        SubjectDTO subjectDTO = subjectService.save(new SubjectDTO("chemistry"));
        assertEquals(subjectDTO,new SubjectDTO(subjectDTO.getSubjectId(),"chemistry"));
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        given(subjectRepository.findById(1L))
                .willReturn(Optional.of(new Subject(1L,"chemistry")));
        SubjectDTO subjectDTO = subjectService.findById(new SubjectDTO(1L));
        assertEquals(subjectDTO,new SubjectDTO(subjectDTO.getSubjectId(),"chemistry"));
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException() {
        given(subjectRepository.findById(55L))
                .willThrow(new EmptyResultSetExceptionDao("Subjects table doesn't contain this record",new EmptyResultDataAccessException(1)));
        Throwable exception = assertThrows(EmptyResultSetExceptionDao.class, () -> subjectService.findById(new SubjectDTO(55L)));
        assertEquals("Subjects table doesn't contain this record", exception.getMessage());
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList() {
        given(subjectRepository.findAll()).willReturn(Arrays.asList(new Subject(1L,"chemistry")));
        assertTrue(!subjectService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        subjectService.update(new SubjectDTO(1L,"chemistry"));
        verify(subjectRepository,times(1)).save(new Subject(1l,"chemistry"));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(subjectRepository).delete(new Subject(1L));
        subjectService.delete(new SubjectDTO(1L));
        verify(subjectRepository,times(1)).delete(new Subject(1L));
    }
}
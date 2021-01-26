package com.foxminded.service;

import com.foxminded.dao.SubjectDao;
import com.foxminded.model.Subject;
import com.foxminded.service.dto.SubjectDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SubjectServiceTest {
    SubjectDao subjectDao = mock(SubjectDao.class);
    ModelMapper modelMapper = new ModelMapper();
    SubjectService subjectService = new SubjectService(modelMapper,subjectDao);

    @Test
    void save() throws SQLException {
        given(subjectDao.save(new Subject("chemistry")))
                .willReturn(new Subject(1L,"chemistry"));
        SubjectDTO subjectDTO = subjectService.save(new SubjectDTO("chemistry"));
        assertEquals(subjectDTO,new SubjectDTO(subjectDTO.getSubjectId(),"chemistry"));
    }

    @Test
    void findById() throws SQLException{
        given(subjectDao.findById(new Subject(1L)))
                .willReturn(new Subject(1L,"chemistry"));
        SubjectDTO subjectDTO = subjectService.findById(new SubjectDTO(1L));
        assertEquals(subjectDTO,new SubjectDTO(subjectDTO.getSubjectId(),"chemistry"));
    }

    @Test
    void findAll() throws SQLException{
        given(subjectDao.findAll()).willReturn(Arrays.asList(new Subject(1L,"chemistry")));
        assertTrue(!subjectService.findAll().isEmpty());
    }

    @Test
    void update() {
        doNothing().when(subjectDao).update(new Subject(1L,"chemistry"));
        subjectService.update(new SubjectDTO(1L,"chemistry"));
        verify(subjectDao,times(1)).update(new Subject(1l,"chemistry"));
    }

    @Test
    void delete() {
        doNothing().when(subjectDao).delete(new Subject(1L));
        subjectService.delete(new SubjectDTO(1L));
        verify(subjectDao,times(1)).delete(new Subject(1L));
    }
}
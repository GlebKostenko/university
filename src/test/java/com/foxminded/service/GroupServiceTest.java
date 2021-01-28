package com.foxminded.service;

import com.foxminded.configuration.ServiceConfig;
import com.foxminded.dao.GroupDao;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.exception.EmptyResultSetExceptionService;
import com.foxminded.model.Group;
import com.foxminded.service.dto.GroupDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class GroupServiceTest {
    private GroupDao groupDao = mock(GroupDao.class);
    ModelMapper modelMapper = new ModelMapper();
    private GroupService groupService = new GroupService(modelMapper,groupDao);
    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord()  {
        given(groupDao.save(new Group("falt-05"))).willReturn(new Group(1L,"falt-05"));
        GroupDTO groupDTO = groupService.save(new GroupDTO("falt-05"));
        assertEquals(groupDTO,new GroupDTO(groupDTO.getGroupId(),"falt-05"));
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        given(groupDao.findById(new Group(1L))).willReturn(new Group(1L,"falt-05"));
        GroupDTO groupDTO = groupService.findById(new GroupDTO(1L));
        assertEquals(groupDTO,groupService.findById(new GroupDTO(groupDTO.getGroupId())));
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException(){
        given(groupDao.findById(new Group(44L)))
                .willThrow(new EmptyResultSetExceptionDao("Groups table doesn't contain this record",new EmptyResultDataAccessException(1)));
        Throwable exception = assertThrows(EmptyResultSetExceptionDao.class, () -> groupService.findById(new GroupDTO(44L)));
        assertEquals("Groups table doesn't contain this record", exception.getMessage());
    }
    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList() {
        given(groupDao.findAll()).willReturn(Arrays.asList(new Group(1L,"falt-05")));
        assertTrue(!groupService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated()  {
        doNothing().when(groupDao).update(new Group(1L,"falt-05"));
        groupService.update(new GroupDTO(1L,"falt-05"));
        verify(groupDao,times(1)).update(new Group(1L,"falt-05"));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(groupDao).delete(new Group(1L));
        groupService.delete(new GroupDTO(1L));
        verify(groupDao,times(1)).delete(new Group(1L));
    }

}
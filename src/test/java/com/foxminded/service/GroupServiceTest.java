package com.foxminded.service;

import com.foxminded.configuration.ServiceConfig;
import com.foxminded.dao.GroupDao;
import com.foxminded.model.Group;
import com.foxminded.service.dto.GroupDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class GroupServiceTest {
    private GroupDao groupDao = mock(GroupDao.class);
    ModelMapper modelMapper = new ModelMapper();
    private GroupService groupService = new GroupService(modelMapper,groupDao);
    @Test
    void save() throws SQLException {
        given(groupDao.save(new Group("falt-05"))).willReturn(new Group(1L,"falt-05"));
        GroupDTO groupDTO = groupService.save(new GroupDTO("falt-05"));
        assertEquals(groupDTO,new GroupDTO(groupDTO.getGroupId(),"falt-05"));
    }

    @Test
    void findById() throws SQLException{
        given(groupDao.findById(new Group(1L))).willReturn(new Group(1L,"falt-05"));
        GroupDTO groupDTO = groupService.findById(new GroupDTO(1L));
        assertEquals(groupDTO,groupService.findById(new GroupDTO(groupDTO.getGroupId())));
    }

    @Test
    void findAll() throws SQLException{
        given(groupDao.findAll()).willReturn(Arrays.asList(new Group(1L,"falt-05")));
        assertTrue(!groupService.findAll().isEmpty());
    }

    @Test
    void update() throws SQLException {
        doNothing().when(groupDao).update(new Group(1L,"falt-05"));
        groupService.update(new GroupDTO(1L,"falt-05"));
        verify(groupDao,times(1)).update(new Group(1L,"falt-05"));
    }

    @Test
    void delete() throws SQLException{
        doNothing().when(groupDao).delete(new Group(1L));
        groupService.delete(new GroupDTO(1L));
        verify(groupDao,times(1)).delete(new Group(1L));
    }

}
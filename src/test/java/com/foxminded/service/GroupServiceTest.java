package com.foxminded.service;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Group;
import com.foxminded.repository.GroupRepository;
import com.foxminded.service.dto.GroupDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class GroupServiceTest {
    ModelMapper mapper = new ModelMapper();
    GroupRepository groupRepository = mock(GroupRepository.class);
    GroupService groupService = new GroupService(mapper,groupRepository);

    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord()  {
        when(groupRepository.save(new Group("falt-05"))).thenReturn(new Group(1L,"falt-05"));
        GroupDTO groupDTO = groupService.save(new GroupDTO("falt-05"));
        assertEquals(groupDTO,new GroupDTO(1L,"falt-05"));
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        Group group = new Group(1L,"fivt");
        when(groupRepository.findById(group.getGroupId())).thenReturn(Optional.of(group));
        assertEquals(groupService.findById(new GroupDTO(1L)).getGroupName(),"fivt");
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeException(){
        when(groupRepository.findById(1L))
                .thenThrow(new EmptyResultSetExceptionDao("Groups table doesn't contain this record",new EmptyResultDataAccessException(1)));
        Throwable exception = assertThrows(EmptyResultSetExceptionDao.class, () -> groupService.findById(new GroupDTO(1L)));
        assertEquals("Groups table doesn't contain this record", exception.getMessage());
    }
    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList() {
        given(groupRepository.findAll()).willReturn(Arrays.asList(new Group(1L,"falt-05")));
        assertTrue(!groupService.findAll().isEmpty());assertTrue(!groupService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated()  {
        groupService.update(new GroupDTO(1L,"falt-05"));
        verify(groupRepository,times(1)).save(new Group(1L,"falt-05"));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(groupRepository).delete(new Group(1L));
        groupService.delete(new GroupDTO(1L));
        verify(groupRepository,times(1)).delete(new Group(1L));
    }

    @Test
    public void findByNameAndUpdate(){
        Map<String,String> dataForUpdate = new HashMap<>();
        dataForUpdate.put("group_name","Fivt");
        doNothing().when(groupRepository).findByNameAndUpdate("FKN",dataForUpdate);
        groupService.findByNameAndUpdate("FKN",dataForUpdate);
        verify(groupRepository,times(1)).findByNameAndUpdate("FKN",dataForUpdate);
    }

}
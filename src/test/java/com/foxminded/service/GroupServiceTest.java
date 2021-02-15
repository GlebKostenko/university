package com.foxminded.service;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.repository.GroupRepository;
import com.foxminded.service.dto.GroupDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class GroupServiceTest {
    @Mock
    GroupRepository groupRepository;
    @InjectMocks
    GroupService groupService;

    GroupServiceTest(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord()  {
        GroupDTO group =  new GroupDTO("falt-05");
        groupService.save(group);
        verify(groupRepository,times(1)).save(group);

    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        GroupDTO groupDTO = new GroupDTO(1L,"fivt");
        when(groupRepository.findById(groupDTO.getGroupId())).thenReturn(Optional.of(groupDTO));
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
        given(groupRepository.findAll()).willReturn(Arrays.asList(new GroupDTO(1L,"falt-05")));
        assertTrue(!groupService.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated()  {
        groupService.update(new GroupDTO(1L,"falt-05"));
        verify(groupRepository,times(1)).save(new GroupDTO(1L,"falt-05"));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        doNothing().when(groupRepository).delete(new GroupDTO(1L));
        groupService.delete(new GroupDTO(1L));
        verify(groupRepository,times(1)).delete(new GroupDTO(1L));
    }

}
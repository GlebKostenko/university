package com.foxminded.controller;

import com.foxminded.service.GroupService;
import com.foxminded.service.dto.GroupDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GroupsControllerTest {
    @Mock
    private GroupService groupService;
    @InjectMocks
    private GroupsController groupsController;

    private MockMvc mockMvc;
    GroupsControllerTest(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupsController).build();
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        List<GroupDTO> groups = new ArrayList<>();
        groups.add(new GroupDTO());
        groups.add(new GroupDTO());
        when(groupService.findAll()).thenReturn((List) groups);
        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups/find-all"))
                .andExpect(model().attribute("groups",hasSize(2)));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        GroupDTO groupDTO = new GroupDTO(1L);
        when(groupService.findById(groupDTO)).thenReturn(new GroupDTO());
        mockMvc.perform(get("/groups/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups/find-by-id"))
                .andExpect(model().attribute("group",instanceOf(GroupDTO.class)));
    }

    @Test
    void newGroup_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        verifyZeroInteractions(groupService);
        mockMvc.perform(get("/groups/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups/new"))
                .andExpect(model().attribute("group",instanceOf(GroupDTO.class)));
    }

    @Test
    void edit_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        GroupDTO groupDTO = new GroupDTO(1L);
        when(groupService.findById(groupDTO)).thenReturn(new GroupDTO());
        mockMvc.perform(get("/groups/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups/edit"))
                .andExpect(model().attribute("group",instanceOf(GroupDTO.class)));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        GroupDTO groupDTO = new GroupDTO(1L);
        doNothing().when(groupService).delete(groupDTO);
        groupsController.delete(1L);
        verify(groupService,times(1)).delete(groupDTO);
    }
}
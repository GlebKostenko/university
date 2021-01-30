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
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
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

//    @Test
//    void findAll() throws Exception{
//        List<GroupDTO> groups = new ArrayList<>();
//        groups.add(new GroupDTO());
//        groups.add(new GroupDTO());
//        when(groupService.findAll()).thenReturn((List) groups);
//        mockMvc.perform(get("/groups/findAll"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("groups/findAll"))
//                .andExpect(model().attribute("groups",hasSize(2)));
//    }

    @Test
    void findById() {
    }

    @Test
    void newGroup() {
    }

    @Test
    void save() {
    }

    @Test
    void edit() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
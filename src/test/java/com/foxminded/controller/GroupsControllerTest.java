package com.foxminded.controller;

import com.foxminded.service.GroupService;
import com.foxminded.service.dto.GroupDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(GroupsController.class)
class GroupsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupService groupService;
    @Autowired
    GroupsController groupsController;


    @Test
    void saveEmptyGroup_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(post("/groups").flashAttr("group",new GroupDTO("")))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void updateGroupWithEmptyName_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(patch("/groups/1").flashAttr("group",new GroupDTO("")))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(groupService).update(new GroupDTO(1L,"fivt"));
        mockMvc.perform(patch("/groups/1").flashAttr("group",new GroupDTO("fivt")));
        verify(groupService,times(1)).update(new GroupDTO(1L,"fivt"));
    }
    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        when(groupService.save(new GroupDTO("fivt"))).thenReturn(new GroupDTO(1L,"fivt"));
        mockMvc.perform(post("/groups").flashAttr("group",new GroupDTO("fivt")));
        verify(groupService,times(1)).save(new GroupDTO("fivt"));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(groupService).delete(new GroupDTO(1L));
        mockMvc.perform(delete("/groups/1"));
        verify(groupService,times(1)).delete(new GroupDTO(1L));
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

}
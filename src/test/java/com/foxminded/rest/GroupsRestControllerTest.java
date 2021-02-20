package com.foxminded.rest;

import com.foxminded.service.GroupService;
import com.foxminded.service.dto.GroupDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupsRestController.class)
class GroupsRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupService groupService;

    @Test
    void findAll_WhenAllIsOk_thenShouldBeNotEmptyResultList() throws Exception{
        when(groupService.findAll()).thenReturn(Arrays.asList(new GroupDTO(1L,"fivt")));
        mockMvc.perform(get("/groups-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].groupId").value(1L))
                .andExpect(jsonPath("$[0].groupName").value("fivt"));
    }

    @Test
    void findById() throws Exception{
        when(groupService.findById(new GroupDTO(1L))).thenReturn(new GroupDTO(1L,"fivt"));
        mockMvc.perform(get("/groups-rest/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").value(1L))
                .andExpect(jsonPath("$.groupName").value("fivt"));
    }

    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        when(groupService.save(new GroupDTO("fivt"))).thenReturn(new GroupDTO(1L,"fivt"));
        mockMvc.perform(post("/groups-rest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"groupName\":\"fivt\"}"))
                .andExpect(jsonPath("$.groupId").value(1L))
                .andExpect(jsonPath("$.groupName").value("fivt"));
    }
    @Test
    void saveEmptyGroup_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(post("/groups-rest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"groupName\":\"\"}"))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(groupService).update(new GroupDTO(1L,"faki"));
        mockMvc.perform(patch("/groups-rest/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"groupName\":\"faki\"}"))
                .andExpect(status().isOk());
        verify(groupService,times(1)).update(new GroupDTO(1L,"faki"));
    }
    @Test
    void updateGroupWithEmptyName_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(patch("/groups-rest/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"groupName\":\"\"}"))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(groupService).delete(new GroupDTO(1L));
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/groups-rest/1"))
                .andExpect(status().isOk());
        verify(groupService,times(1)).delete(new GroupDTO(1L));
    }
}
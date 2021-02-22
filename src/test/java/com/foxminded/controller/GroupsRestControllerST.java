package com.foxminded.controller;

import com.foxminded.model.Group;
import com.foxminded.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class GroupsRestControllerST {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    GroupRepository groupRepository;

    @Test
    void findAll_WhenAllIsOk_thenShouldBeNotEmptyResultList() throws Exception{
        groupRepository.save(new Group("fivt"));
        mockMvc.perform(get("/groups-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(greaterThan(0))));
    }

    @Test
    void findById() throws Exception{
        Group group = groupRepository.save(new Group("fupm"));
        String URL = "/groups-rest/" + group.getGroupId();
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").value(group.getGroupId()))
                .andExpect(jsonPath("$.groupName").value("fupm"));
    }

    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        mockMvc.perform(post("/groups-rest")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"groupName\":\"fizfak\"}"))
                .andExpect(jsonPath("$.groupName").value("fizfak"));
        List<Group> groups = (List<Group>) groupRepository.findAll();
        assertTrue(!groups.stream().filter(x -> x.getGroupName().equals("fizfak")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Group group = groupRepository.save(new Group("mehmat"));
        String URL = "/groups-rest/" + group.getGroupId();
        mockMvc.perform(patch(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"groupName\":\"faki\"}"))
                .andExpect(status().isOk());
        List<Group> groups = (List<Group>) groupRepository.findAll();
        assertTrue(groups.stream().filter(x -> x.getGroupName().equals("mehmat")).findAny().isEmpty());
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Group group = groupRepository.save(new Group("FKN"));
        String URL = "/groups-rest/" + group.getGroupId();
        mockMvc.perform(delete(URL))
                .andExpect(status().isOk());
        assertTrue(groupRepository.findById(group.getGroupId()).isEmpty());
    }
}

package com.foxminded.rest;

import com.foxminded.Application;
import com.foxminded.service.LectureHallService;
import com.foxminded.service.dto.LectureHallDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@WebMvcTest(LectureHallsRestController.class)
class LectureHallsRestControllerTest {
    @MockBean
    private LectureHallService lectureHallService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveEmptyHall_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(post("/halls-rest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"hallName\":\"\"}"))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void updateHallWithEmptyName_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(patch("/halls-rest/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"hallName\":\"\"}"))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(lectureHallService).update(new LectureHallDTO(1L,"GK"));
        mockMvc.perform(patch("/halls-rest/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"hallName\":\"GK\"}"));
        verify(lectureHallService,times(1)).update(new LectureHallDTO(1L,"GK"));
    }

    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        when(lectureHallService.save(new LectureHallDTO("bolishaya"))).thenReturn(new LectureHallDTO(1L,"bolishaya"));
        mockMvc.perform(post("/halls-rest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"hallName\":\"bolishaya\"}"))
                .andExpect(jsonPath("$.hallId").value(1L))
                .andExpect(jsonPath("$.hallName").value("bolishaya"));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(lectureHallService).delete(new LectureHallDTO(1L));
        mockMvc.perform(delete("/halls-rest/1"))
                .andExpect(status().isOk());
        verify(lectureHallService,times(1)).delete(new LectureHallDTO(1L));
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        when(lectureHallService.findAll()).thenReturn(Arrays.asList(new LectureHallDTO(1L,"GK")));
        mockMvc.perform(get("/halls-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].hallId").value(1L))
                .andExpect(jsonPath("$[0].hallName").value("GK"));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        when(lectureHallService.findById(new LectureHallDTO(1L))).thenReturn(new LectureHallDTO(1L,"GK"));
        mockMvc.perform(get("/halls-rest/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hallId").value(1L))
                .andExpect(jsonPath("$.hallName").value("GK"));
    }
}
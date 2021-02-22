package com.foxminded.controller;


import com.foxminded.model.LectureHall;
import com.foxminded.repository.LectureHallRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class HallsRestControllerST {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    LectureHallRepository hallRepository;

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        LectureHall lectureHall = hallRepository.save(new LectureHall("GK"));
        String URL = "/halls-rest/" + lectureHall.getHallId();
        mockMvc.perform(patch(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"hallName\":\"Lectoriy\"}"));
        List<LectureHall> lectureHalls = (List<LectureHall>) hallRepository.findAll();
        assertTrue(lectureHalls.stream().filter(x -> x.getHallName().equals("GK")).findAny().isEmpty());
    }

    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        mockMvc.perform(post("/halls-rest")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"hallName\":\"bolishaya\"}"))
                .andExpect(jsonPath("$.hallName").value("bolishaya"));
        List<LectureHall> halls = (List<LectureHall>) hallRepository.findAll();
        assertTrue(halls.stream().filter(x -> x.getHallName().equals("bolishaya")).findAny().isPresent());
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        LectureHall lectureHall = hallRepository.save(new LectureHall("Arktika"));
        String URL = "/halls-rest/" + lectureHall.getHallId();
        mockMvc.perform(delete(URL))
                .andExpect(status().isOk());
        assertTrue(hallRepository.findById(lectureHall.getHallId()).isEmpty());
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        hallRepository.save(new LectureHall("Kvant"));
        mockMvc.perform(get("/halls-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(greaterThan(0))));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        LectureHall lectureHall = hallRepository.save(new LectureHall("NK"));
        String URL = "/halls-rest/" + lectureHall.getHallId();
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hallId").value(lectureHall.getHallId()))
                .andExpect(jsonPath("$.hallName").value("NK"));
    }

}

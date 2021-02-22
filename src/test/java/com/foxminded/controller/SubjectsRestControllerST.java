package com.foxminded.controller;

import com.foxminded.model.Subject;
import com.foxminded.repository.SubjectRepository;
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
class SubjectsRestControllerST {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    SubjectRepository subjectRepository;

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Subject subject = subjectRepository.save(new Subject("Programming"));
        String URL = "/subjects-rest/" + subject.getSubjectId();
        mockMvc.perform(patch(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"subjectName\":\"Biology\"}"))
                .andExpect(status().isOk());
        assertTrue(subjectRepository.findById(subject.getSubjectId()).get().getSubjectName().equals("Biology"));
    }
    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        mockMvc.perform(post("/subjects-rest")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"subjectName\":\"Math\"}"))
                .andExpect(jsonPath("$.subjectName").value("Math"));
        List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
        assertTrue(subjects.stream().filter(x -> x.getSubjectName().equals("Math")).findAny().isPresent());
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Subject subject = subjectRepository.save(new Subject("Economic"));
        String URL = "/subjects-rest/" + subject.getSubjectId();
        mockMvc.perform(delete(URL))
                .andExpect(status().isOk());
        assertTrue(subjectRepository.findById(subject.getSubjectId()).isEmpty());
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        Subject subject = subjectRepository.save(new Subject("English"));
        mockMvc.perform(get("/subjects-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(greaterThan(0))));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        Subject subject = subjectRepository.save(new Subject("Physics"));
        String URL = "/subjects-rest/" + subject.getSubjectId();
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjectId").value(subject.getSubjectId()))
                .andExpect(jsonPath("$.subjectName").value("Physics"));
    }


}

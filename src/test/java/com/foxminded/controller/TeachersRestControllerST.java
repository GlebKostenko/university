package com.foxminded.controller;

import com.foxminded.model.Teacher;
import com.foxminded.repository.TeacherRepository;
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
class TeachersRestControllerST {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TeacherRepository teacherRepository;

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Teacher teacher = teacherRepository.save(new Teacher("Petr","Kapronov"));
        String URL = "/teachers-rest/" + teacher.getTeacherId();
        mockMvc.perform(patch(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Nikolay\",\"lastName\":\"Semenov\"}"))
                .andExpect(status().isOk());
        assertTrue(teacherRepository.findById(teacher.getTeacherId()).get().getFirstName().equals("Nikolay"));
    }
    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        mockMvc.perform(post("/teachers-rest")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Anton\",\"lastName\":\"Korolev\"}"))
                .andExpect(jsonPath("$.firstName").value("Anton"))
                .andExpect(jsonPath("$.lastName").value("Korolev"));
        List<Teacher> teachers = (List<Teacher>) teacherRepository.findAll();
        assertTrue(teachers.stream()
                .filter(x -> x.getFirstName().equals("Anton") && x.getLastName().equals("Korolev"))
                .findAny().isPresent());
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Teacher teacher = teacherRepository.save(new Teacher("Dmitry","Fridman"));
        String URL = "/teachers-rest/" + teacher.getTeacherId();
        mockMvc.perform(delete(URL))
                .andExpect(status().isOk());
        assertTrue(teacherRepository.findById(teacher.getTeacherId()).isEmpty());
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        Teacher teacher = teacherRepository.save(new Teacher("Sergey","Kasianov"));
        mockMvc.perform(get("/teachers-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(greaterThan(0))));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        Teacher teacher = teacherRepository.save(new Teacher("Michael","Potapov"));
        String URL = "/teachers-rest/" + teacher.getTeacherId();
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teacherId").value(teacher.getTeacherId()))
                .andExpect(jsonPath("$.firstName").value("Michael"))
                .andExpect(jsonPath("$.lastName").value("Potapov"));
    }

}

package com.foxminded.controller;

import com.foxminded.service.TeacherService;
import com.foxminded.service.dto.SubjectDTO;
import com.foxminded.service.dto.TeacherDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TeachersController.class)
class TeachersControllerTest {
    @MockBean
    private TeacherService teacherService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void save_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        mockMvc.perform(get("/teachers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/new"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        given(teacherService.findById(new TeacherDTO(1L))).willReturn(new TeacherDTO(1L,"Lev","Landau"));
        doNothing().when(teacherService).update(new TeacherDTO(1L,"Nikolay","Semenov"));
        mockMvc.perform(get("/teachers/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/edit"));
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        List<TeacherDTO> teachers = new ArrayList<>();
        teachers.add(new TeacherDTO());
        teachers.add(new TeacherDTO());
        when(teacherService.findAll()).thenReturn((List) teachers);
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/find-all"))
                .andExpect(model().attribute("teachers",hasSize(2)));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        TeacherDTO teacherDTO = new TeacherDTO(1L);
        when(teacherService.findById(teacherDTO)).thenReturn(new TeacherDTO());
        mockMvc.perform(get("/teachers/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/find-by-id"))
                .andExpect(model().attribute("teacher",instanceOf(TeacherDTO.class)));
    }

    @Test
    void newTeacher_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        verifyZeroInteractions(teacherService);
        mockMvc.perform(get("/teachers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/new"))
                .andExpect(model().attribute("teacher",instanceOf(TeacherDTO.class)));
    }

    @Test
    void edit_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        TeacherDTO teacherDTO = new TeacherDTO(1L);
        when(teacherService.findById(teacherDTO)).thenReturn(new TeacherDTO());
        mockMvc.perform(get("/teachers/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/edit"))
                .andExpect(model().attribute("teacher",instanceOf(TeacherDTO.class)));
    }

}
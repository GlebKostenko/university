package com.foxminded.controller;

import com.foxminded.service.TeacherService;
import com.foxminded.service.dto.TeacherDTO;
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

class TeachersControllerTest {
    @Mock
    private TeacherService teacherService;
    @InjectMocks
    private TeachersController teachersController;

    private MockMvc mockMvc;
    TeachersControllerTest(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(teachersController).build();
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

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        TeacherDTO teacherDTO = new TeacherDTO(1L);
        doNothing().when(teacherService).delete(teacherDTO);
        teachersController.delete(1L);
        verify(teacherService,times(1)).delete(teacherDTO);
    }
}
package com.foxminded.controller;

import com.foxminded.service.SubjectService;
import com.foxminded.service.dto.SubjectDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SubjectsController.class)
class SubjectsControllerTest {
    @MockBean
    private SubjectService subjectService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void save_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        mockMvc.perform(get("/subjects/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("subjects/new"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        given(subjectService.findById(new SubjectDTO(1L))).willReturn(new SubjectDTO(1L,"Math"));
        doNothing().when(subjectService).update(new SubjectDTO(1L,"Math"));
        mockMvc.perform(get("/subjects/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("subjects/edit"));
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        List<SubjectDTO> subjects = new ArrayList<>();
        subjects.add(new SubjectDTO());
        subjects.add(new SubjectDTO());
        when(subjectService.findAll()).thenReturn((List) subjects);
        mockMvc.perform(get("/subjects"))
                .andExpect(status().isOk())
                .andExpect(view().name("subjects/find-all"))
                .andExpect(model().attribute("subjects",hasSize(2)));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        SubjectDTO subjectDTO = new SubjectDTO(1L);
        when(subjectService.findById(subjectDTO)).thenReturn(new SubjectDTO());
        mockMvc.perform(get("/subjects/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("subjects/find-by-id"))
                .andExpect(model().attribute("subject",instanceOf(SubjectDTO.class)));
    }

    @Test
    void newSubject_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        verifyZeroInteractions(subjectService);
        mockMvc.perform(get("/subjects/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("subjects/new"))
                .andExpect(model().attribute("subject",instanceOf(SubjectDTO.class)));
    }

    @Test
    void edit_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        SubjectDTO subjectDTO = new SubjectDTO(1L);
        when(subjectService.findById(subjectDTO)).thenReturn(new SubjectDTO());
        mockMvc.perform(get("/subjects/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("subjects/edit"))
                .andExpect(model().attribute("subject",instanceOf(SubjectDTO.class)));
    }

}
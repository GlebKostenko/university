package com.foxminded.controller;

import com.foxminded.service.SubjectService;
import com.foxminded.service.dto.SubjectDTO;
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

class SubjectsControllerTest {
    @Mock
    private SubjectService subjectService;
    @InjectMocks
    private SubjectsController subjectsController;

    private MockMvc mockMvc;
    SubjectsControllerTest(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subjectsController).build();
    }

    @Test
    void save_WhenAllIsOk_thenShouldBeOneCallWithoutError(){
        SubjectDTO subjectDTO = new SubjectDTO("Math");
        when(subjectService.save(subjectDTO)).thenReturn(new SubjectDTO(1L,"Math"));
        subjectsController.save(subjectDTO);
        verify(subjectService,times(1)).save(subjectDTO);
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError(){
        doNothing().when(subjectService).update(new SubjectDTO(1L,"Math"));
        subjectsController.update(new SubjectDTO("Math"),1L);
        verify(subjectService,times(1)).update(new SubjectDTO(1L,"Math"));
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

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        SubjectDTO subjectDTO = new SubjectDTO(1L);
        doNothing().when(subjectService).delete(subjectDTO);
        subjectsController.delete(1L);
        verify(subjectService,times(1)).delete(subjectDTO);
    }
}
package com.foxminded.controller;

import com.foxminded.service.SubjectService;
import com.foxminded.service.dto.GroupDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SubjectsController.class)
class SubjectsControllerTest {
    @MockBean
    private SubjectService subjectService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveEmptySubject_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(post("/subjects").flashAttr("subject",new SubjectDTO("")))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void updateSubjectWithEmptyName_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(patch("/subjects/1").flashAttr("subject",new SubjectDTO("")))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(subjectService).update(new SubjectDTO(1L,"Biology"));
        mockMvc.perform(patch("/subjects/1").flashAttr("subject",new SubjectDTO("Biology")));
        verify(subjectService,times(1)).update(new SubjectDTO(1L,"Biology"));
    }
    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        when(subjectService.save(new SubjectDTO("Biology"))).thenReturn(new SubjectDTO(1L,"Biology"));
        mockMvc.perform(post("/subjects").flashAttr("subject",new SubjectDTO("Biology")));
        verify(subjectService,times(1)).save(new SubjectDTO("Biology"));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(subjectService).delete(new SubjectDTO(1L));
        mockMvc.perform(delete("/subjects/1"));
        verify(subjectService,times(1)).delete(new SubjectDTO(1L));
    }

    @Test
    void edit_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
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
package com.foxminded.rest;

import com.foxminded.service.SubjectService;
import com.foxminded.service.dto.SubjectDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SubjectsRestController.class)
class SubjectsRestControllerTest {
    @MockBean
    private SubjectService subjectService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveEmptySubject_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(post("/subjects-rest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"subjectName\":\"\"}"))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void updateSubjectWithEmptyName_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(patch("/subjects-rest/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"subjectName\":\"\"}"))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(subjectService).update(new SubjectDTO(1L,"Biology"));
        mockMvc.perform(patch("/subjects-rest/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"subjectName\":\"Biology\"}"))
                .andExpect(status().isOk());
        verify(subjectService,times(1)).update(new SubjectDTO(1L,"Biology"));
    }
    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        when(subjectService.save(new SubjectDTO("Biology"))).thenReturn(new SubjectDTO(1L,"Biology"));
        mockMvc.perform(post("/subjects-rest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"subjectName\":\"Biology\"}"))
                .andExpect(jsonPath("$.subjectId").value(1L))
                .andExpect(jsonPath("$.subjectName").value("Biology"));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(subjectService).delete(new SubjectDTO(1L));
        mockMvc.perform(delete("/subjects-rest/1"))
                .andExpect(status().isOk());
        verify(subjectService,times(1)).delete(new SubjectDTO(1L));
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        List<SubjectDTO> subjects = new ArrayList<>();
        subjects.add(new SubjectDTO(1L,"Math"));
        when(subjectService.findAll()).thenReturn((List) subjects);
        mockMvc.perform(get("/subjects-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(1)))
                .andExpect(jsonPath("$[0].subjectId").value(1L))
                .andExpect(jsonPath("$[0].subjectName").value("Math"));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        SubjectDTO subjectDTO = new SubjectDTO(1L);
        when(subjectService.findById(subjectDTO)).thenReturn(new SubjectDTO(1L,"Math"));
        mockMvc.perform(get("/subjects-rest/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjectId").value(1L))
                .andExpect(jsonPath("$.subjectName").value("Math"));
    }

}
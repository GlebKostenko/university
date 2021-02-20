package com.foxminded.rest;

import com.foxminded.service.TeacherService;
import com.foxminded.service.dto.TeacherDTO;
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
@WebMvcTest(TeachersRestController.class)
class TeachersRestControllerTest {
    @MockBean
    private TeacherService teacherService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveEmptyTeacher_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(post("/teachers-rest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\":\"\",\"lastName\":\"\"}"))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void updateTeacherWithEmptyFirstAndLastName_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(patch("/teachers-rest/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\":\"\",\"lastName\":\"\"}"))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(teacherService).update(new TeacherDTO(1L,"Petr","Kapronov"));
        mockMvc.perform(patch("/teachers-rest/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\":\"Petr\",\"lastName\":\"Kapronov\"}"))
                .andExpect(status().isOk());
        verify(teacherService,times(1)).update(new TeacherDTO(1L,"Petr","Kapronov"));
    }
    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        when(teacherService.save(new TeacherDTO("Petr","Kapronov"))).thenReturn(new TeacherDTO(1L,"Petr","Kapronov"));
        mockMvc.perform(post("/teachers-rest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\":\"Petr\",\"lastName\":\"Kapronov\"}"))
                .andExpect(jsonPath("$.teacherId").value(1L))
                .andExpect(jsonPath("$.firstName").value("Petr"))
                .andExpect(jsonPath("$.lastName").value("Kapronov"));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(teacherService).delete(new TeacherDTO(1L));
        mockMvc.perform(delete("/teachers-rest/1"))
                .andExpect(status().isOk());
        verify(teacherService,times(1)).delete(new TeacherDTO(1L));
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        List<TeacherDTO> teachers = new ArrayList<>();
        teachers.add(new TeacherDTO(1L,"Petr","Kapronov"));
        when(teacherService.findAll()).thenReturn((List) teachers);
        mockMvc.perform(get("/teachers-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(1)))
                .andExpect(jsonPath("$[0].teacherId").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("Petr"))
                .andExpect(jsonPath("$[0].lastName").value("Kapronov"));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        TeacherDTO teacherDTO = new TeacherDTO(1L);
        when(teacherService.findById(teacherDTO)).thenReturn(new TeacherDTO(1L,"Petr","Kapronov"));
        mockMvc.perform(get("/teachers-rest/1"))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teacherId").value(1L))
                .andExpect(jsonPath("$.firstName").value("Petr"))
                .andExpect(jsonPath("$.lastName").value("Kapronov"));
    }
}
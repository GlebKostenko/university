package com.foxminded.controller;

import com.foxminded.service.GroupService;
import com.foxminded.service.StudentService;
import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.LectureHallDTO;
import com.foxminded.service.dto.StudentDTO;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentsController.class)
class StudentsControllerTest {
    @MockBean
    private StudentService studentService;
    @MockBean
    private GroupService groupService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void save_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/new"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        given(studentService.findById(new StudentDTO(1L))).willReturn(new StudentDTO(1L,
                "Alexey",
                "Romanov",
                new GroupDTO(1L,"fivt")));
        mockMvc.perform(get("/students/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/edit"));
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        List<StudentDTO> students = new ArrayList<>();
        students.add(new StudentDTO(1L,
                "Alexey",
                "Romanov",
                new GroupDTO(1L,"fivt")));
        when(studentService.findAll()).thenReturn((List) students);
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/find-all"))
                .andExpect(model().attribute("students",hasSize(1)));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        StudentDTO studentDTO = new StudentDTO(1L);
        when(studentService.findById(studentDTO)).thenReturn(new StudentDTO(1L,
                "Alexey",
                "Romanov",
                new GroupDTO(1L,"fivt")));
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/find-by-id"))
                .andExpect(model().attribute("student",instanceOf(StudentDTO.class)));
    }

    @Test
    void newSchedule_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        verifyZeroInteractions(studentService);
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/new"))
                .andExpect(model().attribute("student",instanceOf(StudentDTO.class)));
    }

    @Test
    void edit_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        StudentDTO studentDTO = new StudentDTO(1L);
        when(studentService.findById(studentDTO)).thenReturn(new StudentDTO(1L,
                "Alexey",
                "Romanov",
                new GroupDTO(1L,"fivt")));
        mockMvc.perform(get("/students/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/edit"))
                .andExpect(model().attribute("student",instanceOf(StudentDTO.class)));
    }


}
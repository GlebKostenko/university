package com.foxminded.rest;

import com.foxminded.service.GroupService;
import com.foxminded.service.StudentService;
import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.StudentDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentsRestController.class)
class StudentsRestControllerTest {
    @MockBean
    private StudentService studentService;
    @MockBean
    private GroupService groupService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveEmptyStudent() throws Exception{
        mockMvc.perform(post("/students-rest"))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void updateStudentWithEmptyFirstAndLastName() throws Exception{
        mockMvc.perform(patch("/students-rest/1")
                .param("group",""))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        when(groupService.findAll()).thenReturn(Arrays.asList(new GroupDTO(1L,"fivt")));
        doNothing().when(studentService).update(new StudentDTO(1L,"Alexey","Romanov",new GroupDTO(1L)));
        mockMvc.perform(patch("/students-rest/1")
                .param("first-name","Alexey")
                .param("last-name","Romanov")
                .param("group","fivt"))
                .andExpect(status().isOk());
        verify(studentService,times(1)).update(new StudentDTO(1L,"Alexey","Romanov",new GroupDTO(1L)));
    }
    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        when(groupService.findAll()).thenReturn(Arrays.asList(new GroupDTO(1L,"fivt")));
        StudentDTO studentDTO = new StudentDTO("Alexey","Romanov",new GroupDTO(1L));
        when(studentService.save(studentDTO)).thenReturn(studentDTO);
        mockMvc.perform(post("/students-rest")
                .param("first-name","Alexey")
                .param("last-name","Romanov")
                .param("group","fivt"))
                .andExpect(jsonPath("$.firstName").value("Alexey"))
                .andExpect(jsonPath("$.lastName").value("Romanov"))
                .andExpect(jsonPath("$.group.groupId").value(1L));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(studentService).delete(new StudentDTO(1L));
        mockMvc.perform(delete("/students-rest/1"))
                .andExpect(status().isOk());
        verify(studentService,times(1)).delete(new StudentDTO(1L));
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        List<StudentDTO> students = new ArrayList<>();
        students.add(new StudentDTO(1L,
                "Alexey",
                "Romanov",
                new GroupDTO(1L,"fivt")));
        when(studentService.findAll()).thenReturn((List) students);
        mockMvc.perform(get("/students-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(1)))
                .andExpect(jsonPath("$[0].firstName").value("Alexey"))
                .andExpect(jsonPath("$[0].lastName").value("Romanov"))
                .andExpect(jsonPath("$[0].group.groupName").value("fivt"));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        when(studentService.findById(new StudentDTO(1L))).thenReturn(new StudentDTO(1L,
                "Alexey",
                "Romanov",
                new GroupDTO(1L,"fivt")));
        mockMvc.perform(get("/students-rest/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alexey"))
                .andExpect(jsonPath("$.lastName").value("Romanov"))
                .andExpect(jsonPath("$.group.groupName").value("fivt"));
    }
}
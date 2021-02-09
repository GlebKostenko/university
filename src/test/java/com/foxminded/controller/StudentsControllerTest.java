package com.foxminded.controller;

import com.foxminded.service.GroupService;
import com.foxminded.service.StudentService;
import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.StudentDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StudentsControllerTest {
    @Mock
    private StudentService studentService;
    @Mock
    private GroupService groupService;
    @InjectMocks
    private StudentsController studentsController;

    private MockMvc mockMvc;
    StudentsControllerTest(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentsController).build();
    }

    @Test
    void save_WhenAllIsOk_thenShouldBeOneCallWithoutError(){
        when(groupService.findAll())
                .thenReturn(Arrays.asList(new GroupDTO(1L,"mehmat")));
        StudentDTO studentDTO = new StudentDTO("Ivan","Ivanov",new GroupDTO(1L));
        when(studentService.save(studentDTO)).thenReturn(new StudentDTO(1L,"Ivan","Ivanov",new GroupDTO(1L)));
        studentsController.save("Ivan","Ivanov","mehmat");
        verify(studentService,times(1)).save(studentDTO);
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError(){
        when(groupService.findAll())
                .thenReturn(Arrays.asList(new GroupDTO(1L,"mehmat")));
        StudentDTO studentDTO = new StudentDTO(1L,"Ivan","Ivanov",new GroupDTO(1L));
        doNothing().when(studentService).update(studentDTO);
        studentsController.update("Ivan","Ivanov","mehmat",1L);
        verify(studentService,times(1)).update(studentDTO);
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        List<StudentDTO> students = new ArrayList<>();
        students.add(new StudentDTO());
        students.add(new StudentDTO());
        when(studentService.findAll()).thenReturn((List) students);
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/find-all"))
                .andExpect(model().attribute("students",hasSize(2)));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        StudentDTO studentDTO = new StudentDTO(1L);
        when(studentService.findById(studentDTO)).thenReturn(new StudentDTO());
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
        when(studentService.findById(studentDTO)).thenReturn(new StudentDTO());
        mockMvc.perform(get("/students/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/edit"))
                .andExpect(model().attribute("student",instanceOf(StudentDTO.class)));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        StudentDTO studentDTO= new StudentDTO(1L);
        doNothing().when(studentService).delete(studentDTO);
        studentsController.delete(1L);
        verify(studentService,times(1)).delete(studentDTO);
    }
}
package com.foxminded.controller;

import com.foxminded.service.*;
import com.foxminded.service.dto.*;
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

import java.time.LocalDateTime;
import java.time.Month;
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
@WebMvcTest(SchedulesController.class)
class SchedulesControllerTest {
    @MockBean
    private ScheduleService scheduleService;
    @MockBean
    private GroupService groupService;
    @MockBean
    private LectureHallService lectureHallService;
    @MockBean
    private TeacherService teacherService;
    @MockBean
    private SubjectService subjectService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void save_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        mockMvc.perform(get("/schedules/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedules/new"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        LocalDateTime ldt = LocalDateTime.of(2021,Month.APRIL,8,12,30);
        given(scheduleService.findById(new ScheduleDTO(1L))).willReturn(new ScheduleDTO(1L,
                new GroupDTO(1L,"fivt"),
                ldt,
                5400,
                new TeacherDTO(1L,"Ivan","Ivanov"),
                new LectureHallDTO(1L,"GK"),
                new SubjectDTO(1L,"Math")));
        doNothing().when(scheduleService).update(new ScheduleDTO(1L));
        mockMvc.perform(get("/schedules/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedules/edit"));
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        LocalDateTime ldt = LocalDateTime.of(2021,Month.APRIL,8,12,30);
        List<ScheduleDTO> schedules = new ArrayList<>();
        schedules.add(new ScheduleDTO(1L,
                new GroupDTO(1L,"fivt"),
                ldt,
                5400,
                new TeacherDTO(1L,"Ivan","Ivanov"),
                new LectureHallDTO(1L,"GK"),
                new SubjectDTO(1L,"Math")));
        when(scheduleService.findAll()).thenReturn((List) schedules);
        mockMvc.perform(get("/schedules"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedules/find-all"))
                .andExpect(model().attribute("schedules",hasSize(1)));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        ScheduleDTO scheduleDTO = new ScheduleDTO(1L);
        LocalDateTime ldt = LocalDateTime.of(2021,Month.APRIL,8,12,30);
        when(scheduleService.findById(scheduleDTO)).thenReturn(new ScheduleDTO(1L,
                new GroupDTO(1L,"fivt"),
                ldt,
                5400,
                new TeacherDTO(1L,"Ivan","Ivanov"),
                new LectureHallDTO(1L,"GK"),
                new SubjectDTO(1L,"Math")));
        mockMvc.perform(get("/schedules/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedules/find-by-id"))
                .andExpect(model().attribute("schedule",instanceOf(ScheduleDTO.class)));
    }

    @Test
    void newSchedule_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        verifyZeroInteractions(scheduleService);
        mockMvc.perform(get("/schedules/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedules/new"))
                .andExpect(model().attribute("schedule",instanceOf(ScheduleDTO.class)));
    }

    @Test
    void edit_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        LocalDateTime ldt = LocalDateTime.of(2021,Month.APRIL,8,12,30);
        ScheduleDTO scheduleDTO = new ScheduleDTO(1L);
        when(scheduleService.findById(scheduleDTO)).thenReturn(new ScheduleDTO(1L,
                new GroupDTO(1L,"fivt"),
                ldt,
                5400,
                new TeacherDTO(1L,"Ivan","Ivanov"),
                new LectureHallDTO(1L,"GK"),
                new SubjectDTO(1L,"Math")));
        mockMvc.perform(get("/schedules/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedules/edit"))
                .andExpect(model().attribute("schedule",instanceOf(ScheduleDTO.class)));
    }
}
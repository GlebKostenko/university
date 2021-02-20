package com.foxminded.rest;

import com.foxminded.service.*;
import com.foxminded.service.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SchedulesRestController.class)
class SchedulesRestControllerTest {
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
    void saveEmptySchedule_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(post("/schedules-rest").param("group","")
                .param("date-time","")
                .param("duration","")
                .param("teacher","")
                .param("hall","")
                .param("subject",""))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void updateScheduleWithEmptyParameters_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(patch("/schedules-rest/1")
                .param("group","fivt")
                .param("date-time","2021-04-08T12:30")
                .param("duration","")
                .param("teacher","Ivan Ivanov")
                .param("hall","glavnaya")
                .param("subject","Math"))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        when(groupService.findAll()).thenReturn(Arrays.asList(new GroupDTO(1L,"fivt")));
        when(lectureHallService.findAll()).thenReturn(Arrays.asList(new LectureHallDTO(1L,"glavnaya")));
        when(teacherService.findAll()).thenReturn(Arrays.asList(new TeacherDTO(1L,"Ivan","Ivanov")));
        when(subjectService.findAll()).thenReturn(Arrays.asList(new SubjectDTO(1L,"Math")));
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        ScheduleDTO scheduleDTO = new ScheduleDTO(1L,
                new GroupDTO(1L),
                ldt,
                5400,
                new TeacherDTO(1L),
                new LectureHallDTO(1L),
                new SubjectDTO(1L));
        doNothing().when(scheduleService).update(scheduleDTO);
        mockMvc.perform(patch("/schedules-rest/1")
                .param("group","fivt")
                .param("date-time","2021-04-08T12:30")
                .param("duration","5400")
                .param("teacher","Ivan Ivanov")
                .param("hall","glavnaya")
                .param("subject","Math"));
        verify(scheduleService,times(1)).update(scheduleDTO);
    }
    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        when(groupService.findAll()).thenReturn(Arrays.asList(new GroupDTO(1L,"fivt")));
        when(lectureHallService.findAll()).thenReturn(Arrays.asList(new LectureHallDTO(1L,"glavnaya")));
        when(teacherService.findAll()).thenReturn(Arrays.asList(new TeacherDTO(1L,"Ivan","Ivanov")));
        when(subjectService.findAll()).thenReturn(Arrays.asList(new SubjectDTO(1L,"Math")));
        LocalDateTime ldt = LocalDateTime.of(2021,Month.APRIL,8,12,30);
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new GroupDTO(1L),
                ldt,
                5400,
                new TeacherDTO(1L),
                new LectureHallDTO(1L),
                new SubjectDTO(1L));
        ScheduleDTO scheduleDTOAfterSaving = new ScheduleDTO(1L,
                new GroupDTO(1L),
                ldt,
                5400,
                new TeacherDTO(1L),
                new LectureHallDTO(1L),
                new SubjectDTO(1L));
        when(scheduleService.save(scheduleDTO)).thenReturn(scheduleDTOAfterSaving);
        mockMvc.perform(post("/schedules-rest").param("group","fivt")
                .param("date-time","2021-04-08T12:30")
                .param("duration","5400")
                .param("teacher","Ivan Ivanov")
                .param("hall","glavnaya")
                .param("subject","Math"))
                .andExpect(jsonPath("$.scheduleId").value(1L))
                .andExpect(jsonPath("$.duration").value(5400))
                .andExpect(jsonPath("$.group.groupId").value(1L));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(scheduleService).delete(new ScheduleDTO(1L));
        mockMvc.perform(delete("/schedules-rest/1"))
                .andExpect(status().isOk());
        verify(scheduleService,times(1)).delete(new ScheduleDTO(1L));
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
        mockMvc.perform(get("/schedules-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].scheduleId").value(1L))
                .andExpect(jsonPath("$[0].duration").value(5400))
                .andExpect(jsonPath("$[0].group.groupName").value("fivt"));
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
        mockMvc.perform(get("/schedules-rest/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scheduleId").value(1L))
                .andExpect(jsonPath("$.duration").value(5400))
                .andExpect(jsonPath("$.group.groupName").value("fivt"));
    }
}
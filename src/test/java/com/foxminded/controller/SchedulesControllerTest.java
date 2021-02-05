package com.foxminded.controller;

import com.foxminded.service.*;
import com.foxminded.service.dto.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SchedulesControllerTest {
    @Mock
    private ScheduleService scheduleService;
    @Mock
    private GroupService groupService;
    @Mock
    private LectureHallService lectureHallService;
    @Mock
    private TeacherService teacherService;
    @Mock
    private SubjectService subjectService;
    @InjectMocks
    private SchedulesController schedulesController;

    private MockMvc mockMvc;
    SchedulesControllerTest(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(schedulesController).build();
    }

    @Test
    void save_WhenAllIsOk_thenShouldBeOneCallWithoutError(){
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        ScheduleDTO scheduleDTO = new ScheduleDTO(new GroupDTO(1L),
                localDateTime,
                5400,
                new TeacherDTO(1L),
                new LectureHallDTO(1L),
                new SubjectDTO(1L));
        when(scheduleService.save(scheduleDTO)).thenReturn(new ScheduleDTO(1L));
        schedulesController.save(1L,"2021-04-08T12:30",5400,1L,1L,1L);
        verify(scheduleService,times(1)).save(scheduleDTO);
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError(){
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        ScheduleDTO scheduleDTO = new ScheduleDTO(1L,
                new GroupDTO(1L),
                localDateTime,
                5400,
                new TeacherDTO(1L),
                new LectureHallDTO(1L),
                new SubjectDTO(1L));
        doNothing().when(scheduleService).update(scheduleDTO);
        schedulesController.update(1L,"2021-04-08T12:30",5400,1L,1L,1L,1L);
        verify(scheduleService,times(1)).update(scheduleDTO);
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        List<ScheduleDTO> schedules = new ArrayList<>();
        schedules.add(new ScheduleDTO());
        schedules.add(new ScheduleDTO());
        when(scheduleService.findAll()).thenReturn((List) schedules);
        mockMvc.perform(get("/schedules"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedules/find-all"))
                .andExpect(model().attribute("schedules",hasSize(2)));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        ScheduleDTO scheduleDTO = new ScheduleDTO(1L);
        when(scheduleService.findById(scheduleDTO)).thenReturn(new ScheduleDTO());
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
        ScheduleDTO scheduleDTO = new ScheduleDTO(1L);
        when(scheduleService.findById(scheduleDTO)).thenReturn(new ScheduleDTO());
        mockMvc.perform(get("/schedules/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedules/edit"))
                .andExpect(model().attribute("schedule",instanceOf(ScheduleDTO.class)));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        ScheduleDTO scheduleDTO = new ScheduleDTO(1L);
        doNothing().when(scheduleService).delete(scheduleDTO);
        schedulesController.delete(1L);
        verify(scheduleService,times(1)).delete(scheduleDTO);
    }
}
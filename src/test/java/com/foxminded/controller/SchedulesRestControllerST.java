package com.foxminded.controller;

import com.foxminded.model.*;
import com.foxminded.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SchedulesRestControllerST {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    LectureHallRepository lectureHallRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SubjectRepository subjectRepository;

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Group group = groupRepository.save(new Group("matmeh"));
        Group groupForUpdate = groupRepository.save(new Group("SHAD"));
        LectureHall lectureHall = lectureHallRepository.save(new LectureHall("Chifra"));
        Teacher teacher = teacherRepository.save(new Teacher("Alexander","Miranchuk"));
        Subject subject = subjectRepository.save(new Subject("Calculus"));
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        Schedule schedule = scheduleRepository.save(new Schedule(
                new Group(group.getGroupId()),
                ldt,
                5400,
                new Teacher(teacher.getTeacherId()),
                new LectureHall(lectureHall.getHallId()),
                new Subject(subject.getSubjectId())));
        String URL = "/schedules-rest/" + schedule.getScheduleId();
        mockMvc.perform(patch(URL)
                .param("group","SHAD")
                .param("date-time","2021-04-08T12:30")
                .param("duration","5400")
                .param("teacher","Alexander Miranchuk")
                .param("hall","Chifra")
                .param("subject","Calculus"));
        List<Schedule> schedules = (List<Schedule>) scheduleRepository.findAll();
        assertTrue(schedules.stream().filter(x -> x.getGroup().equals(group)).findAny().isEmpty());
    }
    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Group group = groupRepository.save(new Group("B05-645"));
        LectureHall lectureHall = lectureHallRepository.save(new LectureHall("202 KPM"));
        Teacher teacher = teacherRepository.save(new Teacher("Anton","Chibirov"));
        Subject subject = subjectRepository.save(new Subject("Math statistics"));
        LocalDateTime ldt = LocalDateTime.of(2021,Month.APRIL,8,12,30);
        mockMvc.perform(post("/schedules-rest").param("group","B05-645")
                .param("date-time","2021-04-08T12:30")
                .param("duration","5400")
                .param("teacher","Anton Chibirov")
                .param("hall","202 KPM")
                .param("subject","Math statistics"));
        List<Schedule> schedules = (List<Schedule>)scheduleRepository.findAll();
        boolean scheduleIsPresent = schedules.stream().filter(x -> x.getGroup().equals(group) &&
                x.getLectureHall().equals(lectureHall) &&
                x.getTeacher().equals(teacher) &&
                x.getSubject().equals(subject) &&
                x.getDateTime().equals(ldt)).findAny().isPresent();
        assertTrue(scheduleIsPresent);
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Group group = groupRepository.save(new Group("matmeh"));
        LectureHall lectureHall = lectureHallRepository.save(new LectureHall("Glavniy"));
        Teacher teacher = teacherRepository.save(new Teacher("Alexander","Miranchuk"));
        Subject subject = subjectRepository.save(new Subject("Calculus"));
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        Schedule schedule = scheduleRepository.save(new Schedule(
                new Group(group.getGroupId()),
                ldt,
                5400,
                new Teacher(teacher.getTeacherId()),
                new LectureHall(lectureHall.getHallId()),
                new Subject(subject.getSubjectId())));
        String URL = "/schedules-rest/" + schedule.getScheduleId();
        mockMvc.perform(delete(URL))
                .andExpect(status().isOk());
        assertTrue(scheduleRepository.findById(schedule.getScheduleId()).isEmpty());
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        Group group = groupRepository.save(new Group("ЗИ-11 - СКБ141"));
        LectureHall lectureHall = lectureHallRepository.save(new LectureHall("HSE Miasnitskaya"));
        Teacher teacher = teacherRepository.save(new Teacher("Evgeny","Shatohin"));
        Subject subject = subjectRepository.save(new Subject("linear algebra"));
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        Schedule schedule = scheduleRepository.save(new Schedule(
                new Group(group.getGroupId()),
                ldt,
                5400,
                new Teacher(teacher.getTeacherId()),
                new LectureHall(lectureHall.getHallId()),
                new Subject(subject.getSubjectId())));
        mockMvc.perform(get("/schedules-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(greaterThan(0))));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        Group group = groupRepository.save(new Group("MathGroup"));
        LectureHall lectureHall = lectureHallRepository.save(new LectureHall("HSE Glavniy"));
        Teacher teacher = teacherRepository.save(new Teacher("Evgeny","Shatohin"));
        Subject subject = subjectRepository.save(new Subject("linear algebra"));
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        Schedule schedule = scheduleRepository.save(new Schedule(
                new Group(group.getGroupId()),
                ldt,
                5400,
                new Teacher(teacher.getTeacherId()),
                new LectureHall(lectureHall.getHallId()),
                new Subject(subject.getSubjectId())));
        String URL = "/schedules-rest/" + schedule.getScheduleId();
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scheduleId").value(schedule.getScheduleId()))
                .andExpect(jsonPath("$.duration").value(5400))
                .andExpect(jsonPath("$.group.groupName").value("MathGroup"));
    }

}

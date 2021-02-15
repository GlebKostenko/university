package com.foxminded.repository;

import com.foxminded.model.*;
import com.foxminded.service.SubjectService;
import com.foxminded.service.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ScheduleRepositoryIntegrationTest {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private LectureHallRepository hallRepository;

    @Test
    public void whenFindById_thenReturnSchedule() {
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        Group group = groupRepository.save(new Group("fizfak"));
        Teacher teacher = teacherRepository.save( new Teacher("Georgiy","Ivanov"));
        Subject subject = subjectRepository.save(new Subject("Mechanics"));
        LectureHall lectureHall = hallRepository.save(new LectureHall("506"));
        Schedule schedule = new Schedule( new Group(group.getGroupId()),
                ldt,
                5400,
                new Teacher(teacher.getTeacherId()),
                new LectureHall(lectureHall.getHallId()),
                new Subject(subject.getSubjectId()));
        scheduleRepository.save(schedule);
        List<Schedule> schedules = (List<Schedule>) scheduleRepository.findAll();
        assertTrue(schedules.contains(schedule));
    }

    @Test
    public void whenUpdate_thenShouldBeScheduleWithUpdatedData() {
        Group group = groupRepository.save(new Group("matfak"));
        Teacher teacher = teacherRepository.save(new Teacher("Ivan","Arseniev"));
        Subject subject = subjectRepository.save(new Subject("UI"));
        LectureHall lectureHall = hallRepository.save(new LectureHall("bolishaya chimeshiskaya"));
        Group groupNew = groupRepository.save(new Group("matmeh"));
        Teacher teacherNew = teacherRepository.save(new Teacher("Anton","Semenov"));
        Subject subjectNew = subjectRepository.save(new Subject("UX"));
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,9,12,30);

        Schedule schedule = new Schedule(new Group(group.getGroupId()),
                ldt,
                5400,
                new Teacher(teacher.getTeacherId()),
                new LectureHall(lectureHall.getHallId()),
                new Subject(subject.getSubjectId()));
        scheduleRepository.save(schedule);
        List<Schedule> schedules = (List<Schedule>) scheduleRepository.findAll();
        schedules.stream().filter(x->x.getScheduleId().equals(schedule.getScheduleId())).forEach(x -> scheduleRepository.save(new Schedule(x.getScheduleId(),
                new Group(groupNew.getGroupId()),
                ldt,
                5400,
                new Teacher(teacherNew.getTeacherId()),
                new LectureHall(lectureHall.getHallId()),
                new Subject(subjectNew.getSubjectId()))));
        schedules = (List<Schedule>) scheduleRepository.findAll();
        assertTrue(schedules.stream()
                .filter(x->x.getScheduleId().equals(schedule.getScheduleId()))
                .findAny().get().getGroup().equals(groupNew)
        );
    }

    @Test
    public void whenDeleteById_thenShouldBeNoGroup() {
        Group group = groupRepository.save(new Group("b03-001"));
        Teacher teacher = teacherRepository.save(new Teacher("Nikolay","Archipov"));
        Subject subject = subjectRepository.save(new Subject("LinAl"));
        LectureHall lectureHall = hallRepository.save(new LectureHall("galvanaya"));
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,10,12,30);

        Schedule schedule = new Schedule(new Group(group.getGroupId()),
                ldt,
                5400,
                new Teacher(teacher.getTeacherId()),
                new LectureHall(lectureHall.getHallId()),
                new Subject(subject.getSubjectId()));
        scheduleRepository.save(schedule);
        scheduleRepository.delete(new Schedule(schedule.getScheduleId()));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> scheduleRepository.findById(schedule.getScheduleId()).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        Group group = groupRepository.save(new Group("b03-002"));
        Teacher teacher = teacherRepository.save(new Teacher("Alexey","Sharipov"));
        Subject subject = subjectRepository.save(new Subject("AnGem"));
        LectureHall lectureHall = hallRepository.save(new LectureHall("LK"));
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,11,12,30);

        Schedule schedule = new Schedule(new Group(group.getGroupId()),
                ldt,
                5400,
                new Teacher(teacher.getTeacherId()),
                new LectureHall(lectureHall.getHallId()),
                new Subject(subject.getSubjectId()));
        scheduleRepository.save(schedule);
        List<Schedule> schedules = (List<Schedule>) scheduleRepository.findAll();
        assertTrue(!schedules.isEmpty());
    }

}

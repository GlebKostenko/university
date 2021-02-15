package com.foxminded.repository;

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
    private TestEntityManager entityManager;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Test
    public void whenFindById_thenReturnSchedule() {
        GroupDTO groupDTO = new GroupDTO("fivt");
        TeacherDTO teacherDTO = new TeacherDTO("Ivan","Ivanov");
        SubjectDTO subjectDTO = new SubjectDTO("Biology");
        LectureHallDTO lectureHallDTO = new LectureHallDTO("GK");
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        entityManager.persist(groupDTO);
        entityManager.flush();
        entityManager.persist(teacherDTO);
        entityManager.flush();
        entityManager.persist(subjectDTO);
        entityManager.flush();
        entityManager.persist(lectureHallDTO);
        entityManager.flush();
        ScheduleDTO scheduleDTO = new ScheduleDTO( new GroupDTO(groupDTO.getGroupId()),
                ldt,
                5400,
                new TeacherDTO(teacherDTO.getTeacherId()),
                new LectureHallDTO(lectureHallDTO.getHallId()),
                new SubjectDTO(subjectDTO.getSubjectId()));
        entityManager.persist(scheduleDTO);
        entityManager.flush();
        List<ScheduleDTO> schedules = (List<ScheduleDTO>) scheduleRepository.findAll();
        assertTrue(schedules.contains(scheduleDTO));
    }

    @Test
    public void whenUpdate_thenShouldBeScheduleWithUpdatedData() {
        GroupDTO groupDTO = new GroupDTO("fivt");
        TeacherDTO teacherDTO = new TeacherDTO("Ivan","Ivanov");
        SubjectDTO subjectDTO = new SubjectDTO("Biology");
        LectureHallDTO lectureHallDTO = new LectureHallDTO("GK");
        GroupDTO groupDTO1 = new GroupDTO("fupm");
        TeacherDTO teacherDTO1 = new TeacherDTO("Lev","Semenov");
        SubjectDTO subjectDTO1 = new SubjectDTO("Math");
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,9,12,30);
        entityManager.persist(groupDTO);
        entityManager.flush();
        entityManager.persist(teacherDTO);
        entityManager.flush();
        entityManager.persist(subjectDTO);
        entityManager.flush();
        entityManager.persist(lectureHallDTO);
        entityManager.flush();
        entityManager.persist(groupDTO1);
        entityManager.flush();
        entityManager.persist(teacherDTO1);
        entityManager.flush();
        entityManager.persist(subjectDTO1);
        entityManager.flush();
        ScheduleDTO scheduleDTO = new ScheduleDTO(new GroupDTO(groupDTO.getGroupId()),
                ldt,
                5400,
                new TeacherDTO(teacherDTO.getTeacherId()),
                new LectureHallDTO(lectureHallDTO.getHallId()),
                new SubjectDTO(subjectDTO.getSubjectId()));
        entityManager.persist(scheduleDTO);
        entityManager.flush();
        List<ScheduleDTO> schedules = (List<ScheduleDTO>) scheduleRepository.findAll();
        schedules.stream().filter(x->x.getScheduleId().equals(scheduleDTO.getScheduleId())).forEach(x -> scheduleRepository.save(new ScheduleDTO(x.getScheduleId(),
                new GroupDTO(groupDTO1.getGroupId()),
                ldt,
                5400,
                new TeacherDTO(teacherDTO1.getTeacherId()),
                new LectureHallDTO(lectureHallDTO.getHallId()),
                new SubjectDTO(subjectDTO1.getSubjectId()))));
        schedules = (List<ScheduleDTO>) scheduleRepository.findAll();
        assertTrue(schedules.stream().filter(x->x.getGroup().getGroupId().equals(groupDTO.getGroupId())).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenDeleteById_thenShouldBeNoSchedule() {
        GroupDTO groupDTO = new GroupDTO("fopf");
        TeacherDTO teacherDTO = new TeacherDTO("Valeriy","Koldunov");
        SubjectDTO subjectDTO = new SubjectDTO("Physics");
        LectureHallDTO lectureHallDTO = new LectureHallDTO("bolishaya fizicheskaya");
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,10,12,30);
        entityManager.persist(groupDTO);
        entityManager.flush();
        entityManager.persist(teacherDTO);
        entityManager.flush();
        entityManager.persist(subjectDTO);
        entityManager.flush();
        entityManager.persist(lectureHallDTO);
        entityManager.flush();
        ScheduleDTO scheduleDTO = new ScheduleDTO(new GroupDTO(groupDTO.getGroupId()),
                ldt,
                5400,
                new TeacherDTO(teacherDTO.getTeacherId()),
                new LectureHallDTO(lectureHallDTO.getHallId()),
                new SubjectDTO(subjectDTO.getSubjectId()));
        entityManager.persist(scheduleDTO);
        entityManager.flush();
        scheduleRepository.delete(new ScheduleDTO(scheduleDTO.getScheduleId()));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> scheduleRepository.findById(scheduleDTO.getScheduleId()).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        GroupDTO groupDTO = new GroupDTO("faki");
        TeacherDTO teacherDTO = new TeacherDTO("Petr","Kapronov");
        SubjectDTO subjectDTO = new SubjectDTO("Programming");
        LectureHallDTO lectureHallDTO = new LectureHallDTO("fizicheskaya");
        LocalDateTime ldt = LocalDateTime.of(2021, Month.APRIL,11,12,30);
        entityManager.persist(groupDTO);
        entityManager.flush();
        entityManager.persist(teacherDTO);
        entityManager.flush();
        entityManager.persist(subjectDTO);
        entityManager.flush();
        entityManager.persist(lectureHallDTO);
        entityManager.flush();
        ScheduleDTO scheduleDTO = new ScheduleDTO(new GroupDTO(groupDTO.getGroupId()),
                ldt,
                5400,
                new TeacherDTO(teacherDTO.getTeacherId()),
                new LectureHallDTO(lectureHallDTO.getHallId()),
                new SubjectDTO(subjectDTO.getSubjectId()));
        entityManager.persist(scheduleDTO);
        entityManager.flush();
        List<ScheduleDTO> schedules = (List<ScheduleDTO>) scheduleRepository.findAll();
        assertTrue(!schedules.isEmpty());
    }

}

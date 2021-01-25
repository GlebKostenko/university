package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class ScheduleDaoTest {
    @Autowired
    ScheduleDao scheduleDao;
    @Autowired
    GroupDao groupDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    LectureHallDao lectureHallDao;
    @Autowired
    SubjectDao subjectDao;
    @Autowired
    TeacherDao teacherDao;
    @Test
    void save() throws SQLException {
        Group group = groupDao.save(new Group("fupm-06"));
        Teacher teacher = teacherDao.save(new Teacher("Nikolay","Semenov"));
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("Glavnaya fizicheskaya"));
        Subject subject = subjectDao.save(new Subject("Economics"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,12,30);
        Schedule schedule = scheduleDao.save(new Schedule(new Group(group.getGroupId())
                ,localDateTime,5400
                ,new Teacher(teacher.getTeacherId())
                ,new LectureHall(lectureHall.getHallId())
                ,new Subject(subject.getSubjectId()))
        );
        assertEquals(schedule,scheduleDao.findById(new Schedule(schedule.getScheduleId())));
    }

    @Test
    void findById() throws SQLException{
        Group group = groupDao.save(new Group("faki-03"));
        Teacher teacher = teacherDao.save(new Teacher("Dmitriy","Tereshin"));
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("110 LK"));
        Subject subject = subjectDao.save(new Subject("Analit"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,13,30);
        Schedule schedule = scheduleDao.save(new Schedule(new Group(group.getGroupId())
                ,localDateTime,5400
                ,new Teacher(teacher.getTeacherId())
                ,new LectureHall(lectureHall.getHallId())
                ,new Subject(subject.getSubjectId()))
        );
        assertEquals(schedule,scheduleDao.findById(new Schedule(schedule.getScheduleId())));

    }

    @Test
    void findAll() throws SQLException{
        Group group = groupDao.save(new Group("mehMat-302"));
        Teacher teacher = teacherDao.save(new Teacher("Vyacheslav","Artomonov"));
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("232"));
        Subject subject = subjectDao.save(new Subject("Algebra"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,14,30);
        Schedule schedule = scheduleDao.save(new Schedule(new Group(group.getGroupId())
                ,localDateTime,5400
                ,new Teacher(teacher.getTeacherId())
                ,new LectureHall(lectureHall.getHallId())
                ,new Subject(subject.getSubjectId()))
        );
        assertTrue(!scheduleDao.findAll().isEmpty());
    }

    @Test
    void update() throws SQLException{
        Group group = groupDao.save(new Group("fizfak-205"));
        Group groupNew = groupDao.save(new Group("matfak-807"));
        Teacher teacher = teacherDao.save(new Teacher("Vladimir","Karavaev"));
        Teacher teacherNew = teacherDao.save(new Teacher("Michiail","Alfimov"));
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("103"));
        LectureHall lectureHallNew = lectureHallDao.save(new LectureHall("435"));
        Subject subject = subjectDao.save(new Subject("Mechanics"));
        Subject subjectNew = subjectDao.save(new Subject("Math statistics"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,8,15,30);
        LocalDateTime localDateTimeNew = LocalDateTime.of(2021,Month.APRIL,9,12,30);
        Schedule schedule = scheduleDao.save(new Schedule(new Group(group.getGroupId())
                ,localDateTime,5400
                ,new Teacher(teacher.getTeacherId())
                ,new LectureHall(lectureHall.getHallId())
                ,new Subject(subject.getSubjectId()))
        );
        Schedule scheduleNew = new Schedule(schedule.getScheduleId(),new Group(groupNew.getGroupId())
                ,localDateTimeNew
                ,3600
                ,new Teacher(teacherNew.getTeacherId())
                ,new LectureHall(lectureHallNew.getHallId())
                ,new Subject(subjectNew.getSubjectId())
        );
        Schedule updatedSchedule = new Schedule(schedule.getScheduleId()
                ,groupNew
                ,localDateTimeNew
                ,3600
                ,teacherNew
                ,lectureHallNew
                ,subjectNew
        );
        scheduleDao.update(scheduleNew);
        Schedule schedule1 = scheduleDao.findById(new Schedule(schedule.getScheduleId()));
        assertEquals(updatedSchedule,scheduleDao.findById(new Schedule(schedule.getScheduleId())));
    }

    @Test
    void delete() throws SQLException{
        Group group = groupDao.save(new Group("fkn-302"));
        Teacher teacher = teacherDao.save(new Teacher("Roman","Avdeev"));
        LectureHall lectureHall = lectureHallDao.save(new LectureHall("503"));
        Subject subject = subjectDao.save(new Subject("Computer Science"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.APRIL,9,14,30);
        Schedule schedule = scheduleDao.save(new Schedule(new Group(group.getGroupId())
                ,localDateTime,5400
                ,new Teacher(teacher.getTeacherId())
                ,new LectureHall(lectureHall.getHallId())
                ,new Subject(subject.getSubjectId()))
        );
        scheduleDao.delete(new Schedule(schedule.getScheduleId()));
        assertFalse(scheduleDao.findAll().contains(schedule));
    }
}
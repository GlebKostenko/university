package com.foxminded.service.dto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


public class ScheduleDTO {
    private Long scheduleId;
    private GroupDTO group;
    private LocalDateTime dateTime;
    private int duration;
    private TeacherDTO teacher;
    private LectureHallDTO lectureHall;
    private SubjectDTO subject;

    public ScheduleDTO(Long scheduleId, GroupDTO group, LocalDateTime dateTime, int duration, TeacherDTO teacher, LectureHallDTO lectureHall, SubjectDTO subject) {
        this.scheduleId = scheduleId;
        this.group = group;
        this.dateTime = dateTime;
        this.duration = duration;
        this.teacher = teacher;
        this.lectureHall = lectureHall;
        this.subject = subject;
    }

    public ScheduleDTO(GroupDTO group, LocalDateTime dateTime, int duration, TeacherDTO teacher, LectureHallDTO lectureHall, SubjectDTO subject) {
        this.group = group;
        this.dateTime = dateTime;
        this.duration = duration;
        this.teacher = teacher;
        this.lectureHall = lectureHall;
        this.subject = subject;
    }

    public ScheduleDTO(Long scheduleId){
        this.scheduleId = scheduleId;
    }

    public ScheduleDTO(){}

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public void setLectureHall(LectureHallDTO lectureHall) {
        this.lectureHall = lectureHall;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public LectureHallDTO getLectureHall() {
        return lectureHall;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDTO schedule = (ScheduleDTO) o;
        return duration == schedule.duration && Objects.equals(scheduleId, schedule.scheduleId) && Objects.equals(group, schedule.group) && Objects.equals(dateTime, schedule.dateTime) && Objects.equals(teacher, schedule.teacher) && Objects.equals(lectureHall, schedule.lectureHall) && Objects.equals(subject, schedule.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, group, dateTime, duration, teacher, lectureHall, subject);
    }
}

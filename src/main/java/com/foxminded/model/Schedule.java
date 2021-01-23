package com.foxminded.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Schedule {
    private Long scheduleId;
    private Group group;
    private LocalDateTime dateTime;
    private int duration;
    private Teacher teacher;
    private LectureHall lectureHall;
    private Subject subject;

    public Schedule(Long scheduleId,Group group, LocalDateTime dateTime, int duration, Teacher teacher, LectureHall lectureHall, Subject subject) {
        this.scheduleId = scheduleId;
        this.group = group;
        this.dateTime = dateTime;
        this.duration = duration;
        this.teacher = teacher;
        this.lectureHall = lectureHall;
        this.subject = subject;
    }

    public Schedule(Group group, LocalDateTime dateTime, int duration, Teacher teacher, LectureHall lectureHall, Subject subject) {
        this.group = group;
        this.dateTime = dateTime;
        this.duration = duration;
        this.teacher = teacher;
        this.lectureHall = lectureHall;
        this.subject = subject;
    }

    public Schedule(Long scheduleId){
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public Group getGroup() {
        return group;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public LectureHall getLectureHall() {
        return lectureHall;
    }

    public Subject getSubject() {
        return subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return duration == schedule.duration && Objects.equals(scheduleId, schedule.scheduleId) && Objects.equals(group, schedule.group) && Objects.equals(dateTime, schedule.dateTime) && Objects.equals(teacher, schedule.teacher) && Objects.equals(lectureHall, schedule.lectureHall) && Objects.equals(subject, schedule.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, group, dateTime, duration, teacher, lectureHall, subject);
    }
}

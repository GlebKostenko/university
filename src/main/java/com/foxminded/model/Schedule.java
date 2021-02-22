package com.foxminded.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;
    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "duration")
    private int duration;
    @ManyToOne(fetch = FetchType.EAGER)
    private Teacher teacher;
    @ManyToOne(fetch = FetchType.EAGER)
    private LectureHall lectureHall;
    @ManyToOne(fetch = FetchType.EAGER)
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

    public Schedule(){}

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setLectureHall(LectureHall lectureHall) {
        this.lectureHall = lectureHall;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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

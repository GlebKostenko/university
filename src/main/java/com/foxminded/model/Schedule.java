package com.foxminded.model;

import java.time.LocalDateTime;

public class Schedule {
    private Group group;
    private LocalDateTime dateTime;
    private int duration;
    private Teacher teacher;
    private LectureHall lectureHall;
    private Subject subject;

    public Schedule(Group group, LocalDateTime dateTime, int duration, Teacher teacher, LectureHall lectureHall, Subject subject) {
        this.group = group;
        this.dateTime = dateTime;
        this.duration = duration;
        this.teacher = teacher;
        this.lectureHall = lectureHall;
        this.subject = subject;
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
}

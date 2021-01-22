package com.foxminded.dao;

import com.foxminded.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
@Repository
public class ScheduleDao implements Dao<Schedule>{
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public ScheduleDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Schedule save(Schedule schedule) throws SQLException {
        jdbcTemplate.update("INSERT INTO schedules(group_id,date_time,duration,teacher_id,hall_id,subject_id) " +
                "VALUES (?,?,?,?,?,?)"
                ,schedule.getGroup().getGroupId()
                ,schedule.getDateTime()
                ,schedule.getDuration()
                ,schedule.getTeacher().getTeacherId()
                ,schedule.getLectureHall().getHallId()
                ,schedule.getSubject().getSubjectId()
        );
        String sql = "SElECT sched.schedule_id" +
                ",gr.group_id,gr.group_name" +
                ",sched.date_time,sched.duration" +
                ",teach.teacher_id,teach.first_name,teach.last_name" +
                ",hall.hall_id,hall.hall_name" +
                ",sub.subject_id,sub.subject_name " +
                "FROM schedules sched " +
                "LEFT JOIN groups gr ON gr.group_id = sched.group_id " +
                "LEFT JOIN teachers teach ON teach.teacher_id = sched.teacher_id " +
                "LEFT JOIN lecture_halls hall ON hall.hall_id = sched.hall_id " +
                "LEFT JOIN subjects sub ON sub.subject_id = sched.subject_id " +
                "WHERE gr.group_id = ? " +
                "AND sched.date_time = ? " +
                "AND sched.duration = ? " +
                "AND teach.teacher_id = ? " +
                "AND hall.hall_id = ? " +
                "AND sub.subject_id = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{schedule.getGroup().getGroupId(),schedule.getDateTime(),schedule.getDuration(),schedule.getTeacher().getTeacherId(),schedule.getLectureHall().getHallId(),schedule.getSubject().getSubjectId()}
        ,(rs,rowNum)->
                new Schedule(rs.getLong(1)
                        ,new Group(rs.getLong(2),rs.getString(3))
                        ,rs.getTimestamp(4).toLocalDateTime()
                        ,rs.getInt(5)
                        ,new Teacher(rs.getLong(6),rs.getString(7),rs.getString(8))
                        ,new LectureHall(rs.getLong(9),rs.getString(10))
                        ,new Subject(rs.getLong(11),rs.getString(12)))
        );
    }

    @Override
    public Schedule findById(Schedule schedule) throws SQLException {
        String sql = "SElECT sched.schedule_id" +
                ",gr.group_id,gr.group_name" +
                ",sched.date_time,sched.duration" +
                ",teach.teacher_id,teach.first_name,teach.last_name" +
                ",hall.hall_id,hall.hall_name" +
                ",sub.subject_id,sub.subject_name " +
                "FROM schedules sched " +
                "LEFT JOIN groups gr ON gr.group_id = sched.group_id " +
                "LEFT JOIN teachers teach ON teach.teacher_id = sched.teacher_id " +
                "LEFT JOIN lecture_halls hall ON hall.hall_id = sched.hall_id " +
                "LEFT JOIN subjects sub ON sub.subject_id = sched.subject_id " +
                "WHERE sched.schedule_id = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{schedule.getScheduleId()}
                ,(rs,rowNum)->
                        new Schedule(rs.getLong(1)
                                ,new Group(rs.getLong(2),rs.getString(3))
                                ,rs.getTimestamp(4).toLocalDateTime()
                                ,rs.getInt(5)
                                ,new Teacher(rs.getLong(6),rs.getString(7),rs.getString(8))
                                ,new LectureHall(rs.getLong(9),rs.getString(10))
                                ,new Subject(rs.getLong(11),rs.getString(12)))
        );
    }

    @Override
    public List<?> findAll() throws SQLException {
        String sql = "SElECT sched.schedule_id" +
                ",gr.group_id,gr.group_name" +
                ",sched.date_time,sched.duration" +
                ",teach.teacher_id,teach.first_name,teach.last_name" +
                ",hall.hall_id,hall.hall_name" +
                ",sub.subject_id,sub.subject_name " +
                "FROM schedules sched " +
                "LEFT JOIN groups gr ON gr.group_id = sched.group_id " +
                "LEFT JOIN teachers teach ON teach.teacher_id = sched.teacher_id " +
                "LEFT JOIN lecture_halls hall ON hall.hall_id = sched.hall_id " +
                "LEFT JOIN subjects sub ON sub.subject_id = sched.subject_id ";
        return jdbcTemplate.query(sql
                ,(rs,rowNum)->
                        new Schedule(rs.getLong(1)
                                ,new Group(rs.getLong(2),rs.getString(3))
                                ,rs.getTimestamp(4).toLocalDateTime()
                                ,rs.getInt(5)
                                ,new Teacher(rs.getLong(6),rs.getString(7),rs.getString(8))
                                ,new LectureHall(rs.getLong(9),rs.getString(10))
                                ,new Subject(rs.getLong(11),rs.getString(12)))
        );
    }

    @Override
    public void update(Long scheduleId, Schedule schedule) {
        jdbcTemplate.update("UPDATE schedules SET group_id = ?" +
                ",date_time = ?" +
                ",duration = ?" +
                ",teacher_id = ?" +
                ",hall_id = ?" +
                ",subject_id = ? " +
                "WHERE schedule_id = ?"
                ,schedule.getGroup().getGroupId()
                ,schedule.getDateTime()
                ,schedule.getDuration()
                ,schedule.getTeacher().getTeacherId()
                ,schedule.getLectureHall().getHallId()
                ,schedule.getSubject().getSubjectId()
                ,scheduleId
        );
    }

    @Override
    public void delete(Schedule schedule) {
        jdbcTemplate.update("DELETE FROM schedules WHERE schedule_id = ?",schedule.getScheduleId());
    }
}

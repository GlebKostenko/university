package com.foxminded.dao;

import com.foxminded.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleDao implements Dao<Schedule>{
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public ScheduleDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Schedule save(Schedule schedule)  {
        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("group_id",schedule.getGroup().getGroupId());
        parameters.put("date_time",schedule.getDateTime());
        parameters.put("duration",schedule.getDuration());
        parameters.put("teacher_id",schedule.getTeacher().getTeacherId());
        parameters.put("hall_id",schedule.getLectureHall().getHallId());
        parameters.put("subject_id",schedule.getSubject().getSubjectId());
        Long id = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("schedules")
                .usingGeneratedKeyColumns("schedule_id")
                .executeAndReturnKey(parameters).longValue();
        schedule.setScheduleId(id);
        return schedule;
    }

    @Override
    public Schedule findById(Schedule schedule)  {
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
    public List<Schedule> findAll() {
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
    public void update(Schedule schedule) {
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
                ,schedule.getScheduleId()
        );
    }

    @Override
    public void delete(Schedule schedule) {
        jdbcTemplate.update("DELETE FROM schedules WHERE schedule_id = ?",schedule.getScheduleId());
    }
}

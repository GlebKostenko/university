package com.foxminded.rest;

import com.foxminded.service.*;
import com.foxminded.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/schedules-rest")
public class SchedulesRestController {
    private final ScheduleService scheduleService;
    private final GroupService groupService;
    private final LectureHallService lectureHallService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    @Autowired
    public SchedulesRestController(ScheduleService scheduleService,
                               GroupService groupService,
                               LectureHallService lectureHallService,
                               SubjectService subjectService,
                               TeacherService teacherService) {
        this.scheduleService = scheduleService;
        this.groupService = groupService;
        this.lectureHallService = lectureHallService;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    @GetMapping()
    public List<ScheduleDTO> findAll(){
        return scheduleService.findAll();
    }

    @GetMapping("/{id}")
    public ScheduleDTO findById(@PathVariable("id") Long id){
        return scheduleService.findById(new ScheduleDTO(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleDTO save(@NotBlank @RequestParam("group") String group
            ,@NotBlank@RequestParam("date-time") String ldt
            ,@NotBlank @RequestParam("duration") Integer  duration
            ,@NotBlank @RequestParam("teacher") String teacher
            ,@NotBlank @RequestParam("hall") String hall
            ,@NotBlank @RequestParam("subject") String subject){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(ldt,formatter);
        GroupDTO groupDTO = groupService.findAll().stream()
                .filter((x)->x.getGroupName().equals(group)).findAny().get();
        TeacherDTO teacherDTO = teacherService.findAll().stream()
                .filter((x)-> (x.getFirstName() + " " + x.getLastName()).equals(teacher)).findAny().get();
        LectureHallDTO lectureHallDTO = lectureHallService.findAll().stream()
                .filter((x) -> x.getHallName().equals(hall)).findAny().get();
        SubjectDTO subjectDTO = subjectService.findAll().stream()
                .filter((x) -> x.getSubjectName().equals(subject)).findAny().get();
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new GroupDTO(groupDTO.getGroupId()),
                localDateTime,
                duration,
                new TeacherDTO(teacherDTO.getTeacherId()),
                new LectureHallDTO(lectureHallDTO.getHallId()),
                new SubjectDTO(subjectDTO.getSubjectId())
        );
        return scheduleService.save(scheduleDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@NotBlank @RequestParam("group") String group,
                         @NotBlank @RequestParam("date-time") String ldt,
                         @NotBlank @RequestParam("duration") int duration,
                         @NotBlank @RequestParam("teacher") String teacher,
                         @NotBlank @RequestParam("hall") String hall,
                         @NotBlank @RequestParam("subject") String subject,
                         @PathVariable("id") Long id){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(ldt,formatter);
        GroupDTO groupDTO = groupService.findAll().stream()
                .filter((x)->x.getGroupName().equals(group)).findAny().get();
        TeacherDTO teacherDTO = teacherService.findAll().stream()
                .filter((x)-> (x.getFirstName() + " " + x.getLastName()).equals(teacher)).findAny().get();
        LectureHallDTO lectureHallDTO = lectureHallService.findAll().stream()
                .filter((x) -> x.getHallName().equals(hall)).findAny().get();
        SubjectDTO subjectDTO = subjectService.findAll().stream()
                .filter((x) -> x.getSubjectName().equals(subject)).findAny().get();
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                id,
                new GroupDTO(groupDTO.getGroupId()),
                localDateTime,
                duration,
                new TeacherDTO(teacherDTO.getTeacherId()),
                new LectureHallDTO(lectureHallDTO.getHallId()),
                new SubjectDTO(subjectDTO.getSubjectId())
        );
        scheduleService.update(scheduleDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        scheduleService.delete(new ScheduleDTO(id));
    }
}

package com.foxminded.controller;

import com.foxminded.service.ScheduleService;
import com.foxminded.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/schedules")
public class SchedulesController {
    private final ScheduleService scheduleService;

    @Autowired
    public SchedulesController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping()
    public String findAll(Model model){
        model.addAttribute("schedules",scheduleService.findAll());
        return "schedules/find-all";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        model.addAttribute("schedule", scheduleService.findById(new ScheduleDTO(id)));
        return "schedules/find-by-id";
    }

    @GetMapping("/new")
    public String newSchedule(@ModelAttribute("schedule") ScheduleDTO scheduleDTO){
        return "schedules/new";
    }

    @PostMapping()
    public String save(@RequestParam("group-id") Long groupId
                      ,@RequestParam("date-time") String ldt
                      ,@RequestParam("duration") Integer  duration
                      ,@RequestParam("teacher-id") Long teacherId
                      ,@RequestParam("hall-id") Long hallId
                      ,@RequestParam("subject-id") Long subjectId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(ldt,formatter);
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new GroupDTO(groupId),
                localDateTime,
                duration,
                new TeacherDTO(teacherId),
                new LectureHallDTO(hallId),
                new SubjectDTO(subjectId)
        );
        scheduleService.save(scheduleDTO);
        return "redirect:/schedules";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("schedule",scheduleService.findById(new ScheduleDTO(id)));
        return "schedules/edit";
    }

    @PatchMapping("/{id}")
    public String update(@RequestParam("group-id") Long groupId,
                         @RequestParam("date-time") String ldt,
                         @RequestParam("duration") int duration,
                         @RequestParam("teacher-id") Long teacherId,
                         @RequestParam("hall-id") Long hallId,
                         @RequestParam("subject-id") Long subjectId,
                         @PathVariable("id") Long id){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(ldt,formatter);
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                id,
                new GroupDTO(groupId),
                localDateTime,
                duration,
                new TeacherDTO(teacherId),
                new LectureHallDTO(hallId),
                new SubjectDTO(subjectId)
        );
        scheduleService.update(scheduleDTO);
        return "redirect:/schedules";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        scheduleService.delete(new ScheduleDTO(id));
        return "redirect:/schedules";
    }
}

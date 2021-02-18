package com.foxminded.controller;

import com.foxminded.service.TeacherService;
import com.foxminded.service.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/teachers")
public class TeachersController {
    private final TeacherService teacherService;

    @Autowired
    public TeachersController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping()
    public String findAll(Model model){
        model.addAttribute("teachers",teacherService.findAll());
        return "teachers/find-all";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        model.addAttribute("teacher", teacherService.findById(new TeacherDTO(id)));
        return "teachers/find-by-id";
    }

    @GetMapping("/new")
    public String newTeacher(@ModelAttribute("teacher") TeacherDTO teacherDTO){
        return "teachers/new";
    }

    @PostMapping()
    public String save(@Valid @ModelAttribute("teacher") TeacherDTO teacherDTO){
        teacherService.save(teacherDTO);
        return "redirect:/teachers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("teacher",teacherService.findById(new TeacherDTO(id)));
        return "teachers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@Valid @ModelAttribute("teacher") TeacherDTO teacherDTO, @PathVariable("id") Long id){
        teacherDTO.setTeacherId(id);
        teacherService.update(teacherDTO);
        return "redirect:/teachers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        teacherService.delete(new TeacherDTO(id));
        return "redirect:/teachers";
    }
}

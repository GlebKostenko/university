package com.foxminded.controller;

import com.foxminded.service.GroupService;
import com.foxminded.service.StudentService;
import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/students")
public class StudentsController {
    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public StudentsController(StudentService studentService,GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping()
    public String findAll(Model model){
        model.addAttribute("students",studentService.findAll());
        return "students/find-all";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id,Model model){
        model.addAttribute("student",studentService.findById(new StudentDTO(id)));
        return "students/find-by-id";
    }

    @GetMapping("/new")
    public String newStudent(@ModelAttribute("student") StudentDTO studentDTO,Model model){
        model.addAttribute("groups",groupService.findAll());
        return "students/new";
    }

    @PostMapping()
    public String save( @NotBlank @RequestParam("first-name") String firstName
                      , @NotBlank @RequestParam("last-name") String lastName
                      , @NotBlank @RequestParam("group") String group){
        GroupDTO groupDTO = groupService.findAll().stream().filter((x)->x.getGroupName().equals(group)).findAny().get();
        studentService.save(new StudentDTO( firstName,lastName,new GroupDTO(groupDTO.getGroupId())));
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("groups",groupService.findAll());
        model.addAttribute("student",studentService.findById(new StudentDTO(id)));
        return "students/edit";
    }

    @PatchMapping("/{id}")
    public String update(@NotBlank @RequestParam("first-name") String firstName
                        ,@NotBlank @RequestParam("last-name") String lastName
                        ,@NotBlank @RequestParam("group") String group
                        ,@PathVariable("id") Long id){
        GroupDTO groupDTO = groupService.findAll().stream().filter((x)->x.getGroupName().equals(group)).findAny().get();
        studentService.update(new StudentDTO(id,firstName,lastName,new GroupDTO(groupDTO.getGroupId())));
        return "redirect:/students";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        studentService.delete(new StudentDTO(id));
        return "redirect:/students";
    }
}

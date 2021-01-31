package com.foxminded.controller;

import com.foxminded.service.StudentService;
import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentsController {
    private final StudentService studentService;

    @Autowired
    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
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
    public String newStudent(@ModelAttribute("student") StudentDTO studentDTO){
        return "students/new";
    }

    @PostMapping()
    public String save(@RequestParam("first-name") String firstName
                      ,@RequestParam("last-name") String lastName
                      ,@RequestParam("group-id") Long groupId){
        studentService.save(new StudentDTO(firstName,lastName,new GroupDTO(groupId)));
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("student",studentService.findById(new StudentDTO(id)));
        return "students/edit";
    }

    @PatchMapping("/{id}")
    public String update(@RequestParam("first-name") String firstName
                        ,@RequestParam("last-name") String lastName
                        ,@RequestParam("group-id") Long groupId
                        ,@PathVariable("id") Long id){
        studentService.update(new StudentDTO(id,firstName,lastName,new GroupDTO(groupId)));
        return "redirect:/students";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        studentService.delete(new StudentDTO(id));
        return "redirect:/students";
    }
}

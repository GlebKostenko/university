package com.foxminded.controller;

import com.foxminded.service.SubjectService;
import com.foxminded.service.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/subjects")
public class SubjectsController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectsController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping()
    public String findAll(Model model){
        model.addAttribute("subjects",subjectService.findAll());
        return "subjects/find-all";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        model.addAttribute("subject", subjectService.findById(new SubjectDTO(id)));
        return "subjects/find-by-id";
    }

    @GetMapping("/new")
    public String newSubject(@ModelAttribute("subject") SubjectDTO subjectDTO){
        return "subjects/new";
    }

    @PostMapping()
    public String save(@Valid @ModelAttribute("subject") SubjectDTO subjectDTO){
        subjectService.save(subjectDTO);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("subject",subjectService.findById(new SubjectDTO(id)));
        return "subjects/edit";
    }

    @PatchMapping("/{id}")
    public String update(@Valid @ModelAttribute("subject") SubjectDTO subjectDTO,@PathVariable("id") Long id){
        subjectDTO.setSubjectId(id);
        subjectService.update(subjectDTO);
        return "redirect:/subjects";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        subjectService.delete(new SubjectDTO(id));
        return "redirect:/subjects";
    }
}

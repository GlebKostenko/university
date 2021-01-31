package com.foxminded.controller;

import com.foxminded.service.LectureHallService;
import com.foxminded.service.dto.LectureHallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/halls")
public class LectureHallsController {
    private final LectureHallService lectureHallService;

    @Autowired
    public LectureHallsController(LectureHallService lectureHallService) {
        this.lectureHallService = lectureHallService;
    }

    @GetMapping()
    public String findAll(Model model){
        model.addAttribute("halls",lectureHallService.findAll());
        return "halls/find-all";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        model.addAttribute("hall", lectureHallService.findById(new LectureHallDTO(id)));
        return "halls/find-by-id";
    }

    @GetMapping("/new")
    public String newHall(@ModelAttribute("hall") LectureHallDTO lectureHallDTO){
        return "halls/new";
    }

    @PostMapping()
    public String save(@ModelAttribute("hall") LectureHallDTO lectureHallDTO){
        lectureHallService.save(lectureHallDTO);
        return "redirect:/halls";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("hall",lectureHallService.findById(new LectureHallDTO(id)));
        return "halls/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("hall") LectureHallDTO lectureHallDTO,@PathVariable("id") Long id){
        lectureHallDTO.setHallId(id);
        lectureHallService.update(lectureHallDTO);
        return "redirect:/halls";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        lectureHallService.delete(new LectureHallDTO(id));
        return "redirect:/halls";
    }
}

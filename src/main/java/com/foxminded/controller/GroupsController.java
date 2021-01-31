package com.foxminded.controller;

import com.foxminded.service.GroupService;
import com.foxminded.service.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/groups")
public class GroupsController {
    private final GroupService groupService;

    @Autowired
    public GroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public String findAll(Model model){
        model.addAttribute("groups",groupService.findAll());
        return "groups/find-all";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id,Model model){
        model.addAttribute("group", groupService.findById(new GroupDTO(id)));
        return "groups/find-by-id";
    }

    @GetMapping("/new")
    public String newGroup(@ModelAttribute("group") GroupDTO groupDTO){
        return "groups/new";
    }

    @PostMapping()
    public String save(@ModelAttribute("group") GroupDTO groupDTO){
        groupService.save(groupDTO);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("group",groupService.findById(new GroupDTO(id)));
        return "groups/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("group") GroupDTO groupDTO,@PathVariable("id") Long id){
        groupDTO.setGroupId(id);
        groupService.update(groupDTO);
        return "redirect:/groups";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        groupService.delete(new GroupDTO(id));
        return "redirect:/groups";
    }
}

package com.foxminded.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.service.GroupService;
import com.foxminded.service.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups-rest")
public class GroupsRestController {
    private final GroupService groupService;

    @Autowired
    public GroupsRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public List<GroupDTO> findAll(){
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    public GroupDTO findById(@PathVariable("id") Long id){
        return groupService.findById(new GroupDTO(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDTO save(@Valid @RequestBody GroupDTO groupDTO){
        return groupService.save(groupDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody GroupDTO groupDTO,@PathVariable("id") Long id){
        groupDTO.setGroupId(id);
        groupService.update(groupDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        groupService.delete(new GroupDTO(id));
    }
}


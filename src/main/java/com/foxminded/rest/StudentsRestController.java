package com.foxminded.rest;

import com.foxminded.service.GroupService;
import com.foxminded.service.StudentService;
import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/students-rest")
public class StudentsRestController {
    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public StudentsRestController(StudentService studentService,GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping()
    public List<StudentDTO> findAll(){
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public StudentDTO findById(@PathVariable("id") Long id){
        return studentService.findById(new StudentDTO(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO save( @NotBlank @RequestParam("first-name") String firstName
            , @NotBlank @RequestParam("last-name") String lastName
            , @NotBlank @RequestParam("group") String group){
        GroupDTO groupDTO = groupService.findAll().stream().filter((x)->x.getGroupName().equals(group)).findAny().get();
        return studentService.save(new StudentDTO( firstName,lastName,new GroupDTO(groupDTO.getGroupId())));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@NotBlank @RequestParam("first-name") String firstName
            ,@NotBlank @RequestParam("last-name") String lastName
            ,@NotBlank @RequestParam("group") String group
            ,@PathVariable("id") Long id){
        GroupDTO groupDTO = groupService.findAll().stream().filter((x)->x.getGroupName().equals(group)).findAny().get();
        studentService.update(new StudentDTO(id,firstName,lastName,new GroupDTO(groupDTO.getGroupId())));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        studentService.delete(new StudentDTO(id));
    }
}

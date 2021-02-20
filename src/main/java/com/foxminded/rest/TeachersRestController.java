package com.foxminded.rest;

import com.foxminded.service.TeacherService;
import com.foxminded.service.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teachers-rest")
public class TeachersRestController {
    private final TeacherService teacherService;

    @Autowired
    public TeachersRestController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping()
    public List<TeacherDTO> findAll(){
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public TeacherDTO findById(@PathVariable("id") Long id){
        return teacherService.findById(new TeacherDTO(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDTO save(@Valid @RequestBody TeacherDTO teacherDTO){
        return teacherService.save(teacherDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody TeacherDTO teacherDTO, @PathVariable("id") Long id){
        teacherDTO.setTeacherId(id);
        teacherService.update(teacherDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        teacherService.delete(new TeacherDTO(id));
    }
}

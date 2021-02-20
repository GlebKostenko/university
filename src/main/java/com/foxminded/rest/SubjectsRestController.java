package com.foxminded.rest;

import com.foxminded.service.SubjectService;
import com.foxminded.service.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subjects-rest")
public class SubjectsRestController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectsRestController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping()
    public List<SubjectDTO> findAll(){
        return subjectService.findAll();
    }

    @GetMapping("/{id}")
    public SubjectDTO findById(@PathVariable("id") Long id){
        return subjectService.findById(new SubjectDTO(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDTO save(@Valid @RequestBody SubjectDTO subjectDTO){
        return subjectService.save(subjectDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody SubjectDTO subjectDTO,@PathVariable("id") Long id){
        subjectDTO.setSubjectId(id);
        subjectService.update(subjectDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        subjectService.delete(new SubjectDTO(id));
    }
}

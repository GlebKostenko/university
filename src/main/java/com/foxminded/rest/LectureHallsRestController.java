package com.foxminded.rest;

import com.foxminded.service.LectureHallService;
import com.foxminded.service.dto.LectureHallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/halls-rest")
public class LectureHallsRestController {
    private final LectureHallService lectureHallService;

    @Autowired
    public LectureHallsRestController(LectureHallService lectureHallService) {
        this.lectureHallService = lectureHallService;
    }

    @GetMapping()
    public List<LectureHallDTO> findAll(){
        return lectureHallService.findAll();
    }

    @GetMapping("/{id}")
    public LectureHallDTO findById(@PathVariable("id") Long id){
        return lectureHallService.findById(new LectureHallDTO(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LectureHallDTO save(@Valid @RequestBody LectureHallDTO lectureHallDTO){
        return lectureHallService.save(lectureHallDTO);
    }

    @PatchMapping("/{id}")
    public void update(@Valid @RequestBody LectureHallDTO lectureHallDTO,@PathVariable("id") Long id){
        lectureHallDTO.setHallId(id);
        lectureHallService.update(lectureHallDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        lectureHallService.delete(new LectureHallDTO(id));
    }
}

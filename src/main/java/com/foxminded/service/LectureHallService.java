package com.foxminded.service;

import com.foxminded.dao.LectureHallDao;
import com.foxminded.model.LectureHall;
import com.foxminded.service.dto.LectureHallDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureHallService implements ServiceLayer<LectureHallDTO>{
    private LectureHallDao lectureHallDao;
    private ModelMapper modelMapper;

    @Autowired
    public LectureHallService( ModelMapper modelMapper,LectureHallDao lectureHallDao){
        this.modelMapper = modelMapper;
        this.lectureHallDao = lectureHallDao;
    }

    @Override
    public LectureHallDTO save(LectureHallDTO lectureHallDTO) {
        return modelMapper.map(lectureHallDao
                .save(modelMapper.map(lectureHallDTO,LectureHall.class)),LectureHallDTO.class);
    }

    @Override
    public LectureHallDTO findById(LectureHallDTO lectureHallDTO) {
        return modelMapper.map(lectureHallDao
                .findById(modelMapper.map(lectureHallDTO,LectureHall.class)),LectureHallDTO.class);
    }

    @Override
    public List<?> findAll() {
        return lectureHallDao.findAll().stream()
                .map(elem -> modelMapper.map(elem,LectureHallDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(LectureHallDTO lectureHallDTO) {
        lectureHallDao.update(modelMapper.map(lectureHallDTO,LectureHall.class));
    }

    @Override
    public void delete(LectureHallDTO lectureHallDTO) {
        lectureHallDao.delete(modelMapper.map(lectureHallDTO,LectureHall.class));
    }
}

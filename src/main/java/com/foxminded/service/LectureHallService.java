package com.foxminded.service;

import com.foxminded.dao.LectureHallDao;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.exception.EmptyResultSetExceptionService;
import com.foxminded.model.LectureHall;
import com.foxminded.service.dto.LectureHallDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureHallService implements ServiceLayer<LectureHallDTO>{
    private static final Logger logger = LoggerFactory.getLogger(LectureHallService.class.getSimpleName());
    private LectureHallDao lectureHallDao;
    private ModelMapper modelMapper;

    @Autowired
    public LectureHallService( ModelMapper modelMapper,LectureHallDao lectureHallDao){
        this.modelMapper = modelMapper;
        this.lectureHallDao = lectureHallDao;
    }

    @Override
    public LectureHallDTO save(LectureHallDTO lectureHallDTO) {
        logger.debug("Calling the save method from dao");
        return modelMapper.map(lectureHallDao
                .save(modelMapper.map(lectureHallDTO,LectureHall.class)),LectureHallDTO.class);
    }

    @Override
    public LectureHallDTO findById(LectureHallDTO lectureHallDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return modelMapper.map(lectureHallDao
                    .findById(modelMapper.map(lectureHallDTO, LectureHall.class)), LectureHallDTO.class);
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find this record",e);
        }
    }

    @Override
    public List<?> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            return lectureHallDao.findAll().stream()
                    .map(elem -> modelMapper.map(elem, LectureHallDTO.class))
                    .collect(Collectors.toList());
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find any records",e);
        }
    }

    @Override
    public void update(LectureHallDTO lectureHallDTO) {
        logger.debug("Calling the update method from dao");
        lectureHallDao.update(modelMapper.map(lectureHallDTO,LectureHall.class));
    }

    @Override
    public void delete(LectureHallDTO lectureHallDTO) {
        logger.debug("Calling the delete method from dao");
        lectureHallDao.delete(modelMapper.map(lectureHallDTO,LectureHall.class));
    }
}

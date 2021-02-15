package com.foxminded.service;

import com.foxminded.exception.DomainException;
import com.foxminded.model.LectureHall;
import com.foxminded.repository.LectureHallRepository;
import com.foxminded.service.dto.LectureHallDTO;
import org.modelmapper.MappingException;
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
    @Autowired
    private LectureHallRepository lectureHallRepository;

    @Override
    public LectureHallDTO save(LectureHallDTO lectureHallDTO) {
        try {
            logger.debug("Calling the save method from dao and trying to save lecture hall with hall name: {}", lectureHallDTO.getHallName());
            return lectureHallRepository.save(lectureHallDTO);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map LectureHallDTO to LectureHall or LectureHall to LectureHallDTO",e);
        }
    }

    @Override
    public LectureHallDTO findById(LectureHallDTO lectureHallDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return lectureHallRepository.findById(lectureHallDTO.getHallId()).get();
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map LectureHallDTO to LectureHall or LectureHall to LectureHallDTO",e);
        }
    }

    @Override
    public List<LectureHallDTO> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            return (List<LectureHallDTO>) lectureHallRepository.findAll();
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map LectureHall to LectureHallDTO",e);
        }
    }

    @Override
    public void update(LectureHallDTO lectureHallDTO) {
        try {
            logger.debug("Calling the update method from dao");
            lectureHallRepository.save(lectureHallDTO);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map LectureHallDTO to LectureHall",e);
        }
    }

    @Override
    public void delete(LectureHallDTO lectureHallDTO) {
        try {
            logger.debug("Calling the delete method from dao");
            lectureHallRepository.delete(lectureHallDTO);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map LectureHallDTO to LectureHall",e);
        }
    }
}

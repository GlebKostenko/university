package com.foxminded.service;

import com.foxminded.exception.DomainException;
import com.foxminded.model.Teacher;
import com.foxminded.repository.TeacherRepository;
import com.foxminded.service.dto.TeacherDTO;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService implements ServiceLayer<TeacherDTO>{
    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class.getSimpleName());
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public TeacherDTO save(TeacherDTO teacherDTO) {
        try {
            logger.debug("Calling the save method from dao and trying to save teacher with next parameters:\n" +
                    "first_name: {}\n" +
                    "last_name: {}", teacherDTO.getFirstName(), teacherDTO.getFirstName());
            return teacherRepository.save(teacherDTO);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map TeacherDTO to Teacher or Teacher to TeacherDTO",e);
        }
    }

    @Override
    public TeacherDTO findById(TeacherDTO teacherDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return teacherRepository.findById(teacherDTO.getTeacherId()).get();
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map TeacherDTO to Teacher or Teacher to TeacherDTO",e);
        }
    }

    @Override
    public List<TeacherDTO> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            return (List<TeacherDTO>) teacherRepository.findAll();
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map Teacher to TeacherDTO",e);
        }
    }

    @Override
    public void update(TeacherDTO teacherDTO) {
        try {
            logger.debug("Calling the update method from dao");
            teacherRepository.save(teacherDTO);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map TeacherDTO to Teacher",e);
        }
    }

    @Override
    public void delete(TeacherDTO teacherDTO) {
        try {
            logger.debug("Calling the delete method from dao");
            teacherRepository.delete(teacherDTO);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map TeacherDTO to Teacher",e);
        }
    }
}

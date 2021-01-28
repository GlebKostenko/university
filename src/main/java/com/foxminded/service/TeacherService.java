package com.foxminded.service;

import com.foxminded.dao.TeacherDao;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.exception.EmptyResultSetExceptionService;
import com.foxminded.model.Teacher;
import com.foxminded.service.dto.TeacherDTO;
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
    private ModelMapper modelMapper;
    private TeacherDao teacherDao;

    @Autowired
    public TeacherService( ModelMapper modelMapper, TeacherDao teacherDao){
        this.modelMapper = modelMapper;
        this.teacherDao = teacherDao;
    }

    @Override
    public TeacherDTO save(TeacherDTO teacherDTO) {
        logger.debug("Calling the save method from dao");
        return modelMapper.map(teacherDao
                .save(modelMapper.map(teacherDTO, Teacher.class)),TeacherDTO.class);
    }

    @Override
    public TeacherDTO findById(TeacherDTO teacherDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return modelMapper.map(teacherDao
                    .findById(modelMapper.map(teacherDTO, Teacher.class)), TeacherDTO.class);
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find this record",e);
        }
    }

    @Override
    public List<?> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            return teacherDao.findAll().stream()
                    .map(elem -> modelMapper.map(elem, TeacherDTO.class))
                    .collect(Collectors.toList());
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find any records",e);
        }
    }

    @Override
    public void update(TeacherDTO teacherDTO) {
        logger.debug("Calling the update method from dao");
        teacherDao.update(modelMapper.map(teacherDTO,Teacher.class));
    }

    @Override
    public void delete(TeacherDTO teacherDTO) {
        logger.debug("Calling the delete method from dao");
        teacherDao.delete(modelMapper.map(teacherDTO,Teacher.class));
    }
}

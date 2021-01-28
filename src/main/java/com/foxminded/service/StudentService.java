package com.foxminded.service;

import com.foxminded.dao.StudentDao;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.exception.EmptyResultSetExceptionService;
import com.foxminded.model.Student;
import com.foxminded.service.dto.StudentDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements ServiceLayer<StudentDTO>{
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class.getSimpleName());
    private ModelMapper modelMapper;
    private StudentDao studentDao;

    @Autowired
    public StudentService( ModelMapper modelMapper, StudentDao studentDao){
        this.modelMapper = modelMapper;
        this.studentDao = studentDao;
    }

    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        logger.debug("Calling the save method from dao");
        return modelMapper.map(studentDao
                .save(modelMapper.map(studentDTO,Student.class)), StudentDTO.class);
    }

    @Override
    public StudentDTO findById(StudentDTO studentDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return modelMapper.map(studentDao
                    .findById(modelMapper.map(studentDTO, Student.class)), StudentDTO.class);
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find this record",e);
        }
    }

    @Override
    public List<?> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            return studentDao.findAll().stream()
                    .map(elem -> modelMapper.map(elem, StudentDTO.class))
                    .collect(Collectors.toList());
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find any records",e);
        }
    }

    @Override
    public void update(StudentDTO studentDTO) {
        logger.debug("Calling the update method from dao");
        studentDao.update(modelMapper.map(studentDTO,Student.class));
    }

    @Override
    public void delete(StudentDTO studentDTO) {
        logger.debug("Calling the delete method from dao");
        studentDao.delete(modelMapper.map(studentDTO,Student.class));
    }
}

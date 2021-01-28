package com.foxminded.service;

import com.foxminded.dao.SubjectDao;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.exception.EmptyResultSetExceptionService;
import com.foxminded.model.Subject;
import com.foxminded.service.dto.SubjectDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService implements ServiceLayer<SubjectDTO>{
    private static final Logger logger = LoggerFactory.getLogger(SubjectService.class.getSimpleName());
    private ModelMapper modelMapper;
    private SubjectDao subjectDao;

    @Autowired
    public SubjectService( ModelMapper modelMapper, SubjectDao subjectDao){
        this.modelMapper = modelMapper;
        this.subjectDao = subjectDao;
    }

    @Override
    public SubjectDTO save(SubjectDTO subjectDTO) {
        logger.debug("Calling the save method from dao");
        return modelMapper.map(subjectDao
        .save(modelMapper.map(subjectDTO, Subject.class)),SubjectDTO.class);
    }

    @Override
    public SubjectDTO findById(SubjectDTO subjectDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return modelMapper.map(subjectDao
                    .findById(modelMapper.map(subjectDTO,Subject.class)),SubjectDTO.class);
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find this record",e);
        }
    }

    @Override
    public List<?> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            return subjectDao.findAll().stream()
                    .map(elem -> modelMapper.map(elem,SubjectDTO.class))
                    .collect(Collectors.toList());
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find any records",e);
        }
    }

    @Override
    public void update(SubjectDTO subjectDTO) {
        logger.debug("Calling the update method from dao");
        subjectDao.update(modelMapper.map(subjectDTO,Subject.class));
    }

    @Override
    public void delete(SubjectDTO subjectDTO) {
        logger.debug("Calling the delete method from dao");
        subjectDao.delete(modelMapper.map(subjectDTO,Subject.class));
    }
}

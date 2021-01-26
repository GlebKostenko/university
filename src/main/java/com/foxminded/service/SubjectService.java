package com.foxminded.service;

import com.foxminded.dao.SubjectDao;
import com.foxminded.model.Subject;
import com.foxminded.service.dto.SubjectDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService implements ServiceLayer<SubjectDTO>{
    private ModelMapper modelMapper;
    private SubjectDao subjectDao;

    public SubjectService(@Autowired ModelMapper modelMapper,@Autowired SubjectDao subjectDao){
        this.modelMapper = modelMapper;
        this.subjectDao = subjectDao;
    }

    @Override
    public SubjectDTO save(SubjectDTO subjectDTO) throws SQLException {
        return modelMapper.map(subjectDao
        .save(modelMapper.map(subjectDTO, Subject.class))
        ,SubjectDTO.class);
    }

    @Override
    public SubjectDTO findById(SubjectDTO subjectDTO) throws SQLException {
        return modelMapper.map(subjectDao
                .findById(modelMapper.map(subjectDTO,Subject.class))
                ,SubjectDTO.class);
    }

    @Override
    public List<?> findAll() throws SQLException {
        return subjectDao.findAll().stream()
                .map(elem -> modelMapper.map(elem,SubjectDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(SubjectDTO subjectDTO) {
        subjectDao.update(modelMapper.map(subjectDTO,Subject.class));
    }

    @Override
    public void delete(SubjectDTO subjectDTO) {
        subjectDao.delete(modelMapper.map(subjectDTO,Subject.class));
    }
}

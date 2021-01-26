package com.foxminded.service;

import com.foxminded.dao.StudentDao;
import com.foxminded.model.Student;
import com.foxminded.service.dto.StudentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements ServiceLayer<StudentDTO>{
    private ModelMapper modelMapper;
    private StudentDao studentDao;

    public StudentService(@Autowired ModelMapper modelMapper,@Autowired StudentDao studentDao){
        this.modelMapper = modelMapper;
        this.studentDao = studentDao;
    }

    @Override
    public StudentDTO save(StudentDTO studentDTO) throws SQLException {
        return modelMapper.map(studentDao
                .save(modelMapper.map(studentDTO,Student.class))
                , StudentDTO.class);
    }

    @Override
    public StudentDTO findById(StudentDTO studentDTO) throws SQLException {
        return modelMapper.map(studentDao
                .findById(modelMapper.map(studentDTO,Student.class))
                ,StudentDTO.class);
    }

    @Override
    public List<?> findAll() throws SQLException {
        return studentDao.findAll().stream()
                .map(elem -> modelMapper.map(elem,StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(StudentDTO studentDTO) {
        studentDao.update(modelMapper.map(studentDTO,Student.class));
    }

    @Override
    public void delete(StudentDTO studentDTO) {
        studentDao.delete(modelMapper.map(studentDTO,Student.class));
    }
}

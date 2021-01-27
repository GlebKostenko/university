package com.foxminded.service;

import com.foxminded.dao.StudentDao;
import com.foxminded.model.Student;
import com.foxminded.service.dto.StudentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements ServiceLayer<StudentDTO>{
    private ModelMapper modelMapper;
    private StudentDao studentDao;

    @Autowired
    public StudentService( ModelMapper modelMapper, StudentDao studentDao){
        this.modelMapper = modelMapper;
        this.studentDao = studentDao;
    }

    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        return modelMapper.map(studentDao
                .save(modelMapper.map(studentDTO,Student.class)), StudentDTO.class);
    }

    @Override
    public StudentDTO findById(StudentDTO studentDTO) {
        return modelMapper.map(studentDao
                .findById(modelMapper.map(studentDTO,Student.class)),StudentDTO.class);
    }

    @Override
    public List<?> findAll() {
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

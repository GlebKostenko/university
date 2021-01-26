package com.foxminded.service;

import com.foxminded.dao.TeacherDao;
import com.foxminded.model.Teacher;
import com.foxminded.service.dto.TeacherDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService implements ServiceLayer<TeacherDTO>{
    private ModelMapper modelMapper;
    private TeacherDao teacherDao;

    public TeacherService(@Autowired ModelMapper modelMapper,@Autowired TeacherDao teacherDao){
        this.modelMapper = modelMapper;
        this.teacherDao = teacherDao;
    }

    @Override
    public TeacherDTO save(TeacherDTO teacherDTO) throws SQLException {
        return modelMapper.map(teacherDao
                .save(modelMapper.map(teacherDTO, Teacher.class))
                ,TeacherDTO.class);
    }

    @Override
    public TeacherDTO findById(TeacherDTO teacherDTO) throws SQLException {
        return modelMapper.map(teacherDao
                .findById(modelMapper.map(teacherDTO,Teacher.class))
                ,TeacherDTO.class);
    }

    @Override
    public List<?> findAll() throws SQLException {
        return teacherDao.findAll().stream()
                .map(elem -> modelMapper.map(elem,TeacherDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(TeacherDTO teacherDTO) {
        teacherDao.update(modelMapper.map(teacherDTO,Teacher.class));
    }

    @Override
    public void delete(TeacherDTO teacherDTO) {
        teacherDao.delete(modelMapper.map(teacherDTO,Teacher.class));
    }
}

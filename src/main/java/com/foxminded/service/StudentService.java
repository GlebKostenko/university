package com.foxminded.service;

import com.foxminded.exception.DomainException;
import com.foxminded.model.Student;
import com.foxminded.repository.StudentRepository;
import com.foxminded.service.dto.StudentDTO;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements ServiceLayer<StudentDTO>{
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class.getSimpleName());
    ModelMapper mapper;
    private StudentRepository studentRepository;
    @Autowired
    public StudentService(ModelMapper mapper, StudentRepository studentRepository) {
        this.mapper = mapper;
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        try {
            logger.debug("Calling the save method from dao and trying to save student with next parameters:\n" +
                    "first_name: {}\n" +
                    "last_name: {}", studentDTO.getFirstName(), studentDTO.getLastName());
            return mapper.map(studentRepository.save(mapper.map(studentDTO, Student.class)),StudentDTO.class);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map StudentDTO to Student or Student to StudentDTO",e);
        }
    }

    @Override
    public StudentDTO findById(StudentDTO studentDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return mapper.map(studentRepository.findById(studentDTO.getStudentId()).get(),StudentDTO.class);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map StudentDTO to Student or Student to StudentDTO",e);
        }
    }

    @Override
    public List<StudentDTO> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            List<Student> students = (List<Student>) studentRepository.findAll();
            return students.stream().map(x -> mapper.map(x,StudentDTO.class)).collect(Collectors.toList());
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map Student to StudentDTO",e);
        }
    }

    @Override
    public void update(StudentDTO studentDTO) {
        try {
            logger.debug("Calling the update method from dao");
            studentRepository.save(mapper.map(studentDTO,Student.class));
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map StudentDTO to Student",e);
        }
    }

    @Override
    public void delete(StudentDTO studentDTO) {
        try {
            logger.debug("Calling the delete method from dao");
            studentRepository.delete(mapper.map(studentDTO,Student.class));
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map StudentDTO to Student",e);
        }
    }
}

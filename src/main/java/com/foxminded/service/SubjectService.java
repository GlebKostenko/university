package com.foxminded.service;

import com.foxminded.exception.DomainException;
import com.foxminded.model.Subject;
import com.foxminded.repository.SubjectRepository;
import com.foxminded.service.dto.SubjectDTO;
import org.modelmapper.MappingException;
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
    ModelMapper mapper;
    private SubjectRepository subjectRepository;
    @Autowired
    public SubjectService(ModelMapper mapper, SubjectRepository subjectRepository) {
        this.mapper = mapper;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public SubjectDTO save(SubjectDTO subjectDTO) {
        try {
            logger.debug("Calling the save method from dao and trying to subject with name: {}", subjectDTO.getSubjectName());
            return mapper.map(subjectRepository.save(mapper.map(subjectDTO, Subject.class)),SubjectDTO.class);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map SubjectDTO to Subject or Subject to SubjectDTO",e);
        }
    }

    @Override
    public SubjectDTO findById(SubjectDTO subjectDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return mapper.map(subjectRepository.findById(subjectDTO.getSubjectId()).get(),SubjectDTO.class);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map SubjectDTO to Subject or Subject to SubjectDTO",e);
        }
    }

    @Override
    public List<SubjectDTO> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
            return subjects.stream().map(x -> mapper.map(x,SubjectDTO.class)).collect(Collectors.toList());
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map Subject to SubjectDTO",e);
        }
    }

    @Override
    public void update(SubjectDTO subjectDTO) {
        try {
            logger.debug("Calling the update method from dao");
            subjectRepository.save(mapper.map(subjectDTO,Subject.class));
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map SubjectDTO to Subject",e);
        }
    }

    @Override
    public void delete(SubjectDTO subjectDTO) {
        try {
            logger.debug("Calling the delete method from dao");
            subjectRepository.delete(mapper.map(subjectDTO,Subject.class));
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map SubjectDTO to Subject",e);
        }
    }
}

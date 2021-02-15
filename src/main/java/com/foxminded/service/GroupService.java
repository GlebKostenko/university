package com.foxminded.service;

import com.foxminded.exception.DomainException;
import com.foxminded.model.Group;
import com.foxminded.repository.GroupRepository;
import com.foxminded.service.dto.GroupDTO;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService implements ServiceLayer<GroupDTO>{
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class.getSimpleName());
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public GroupDTO save(GroupDTO groupDTO) {
        try {
            logger.debug("Calling the save method from dao and trying to save group with name: {}", groupDTO.getGroupName());
            return groupRepository.save(groupDTO);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map GroupDTO to Group or Group to GroupDTO",e);
        }
    }

    @Override
    public GroupDTO findById(GroupDTO groupDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return groupRepository.findById(groupDTO.getGroupId()).get();
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map GroupDTO to Group or Group to GroupDTO",e);
        }
    }

    @Override
    public List<GroupDTO> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            return (List<GroupDTO>) groupRepository.findAll();
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map Group to GroupDTO",e);
        }
    }

    @Override
    public void update(GroupDTO groupDTO) {
        try {
            logger.debug("Calling the update method from dao");
            groupRepository.save(groupDTO);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map GroupDTO to Group",e);
        }

    }

    @Override
    public void delete(GroupDTO groupDTO) {
        try {
            logger.debug("Calling the delete method from dao");
            groupRepository.delete(groupDTO);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map GroupDTO to Group",e);
        }
    }
}

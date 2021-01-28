package com.foxminded.service;

import com.foxminded.dao.GroupDao;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.exception.EmptyResultSetExceptionService;
import com.foxminded.model.Group;
import com.foxminded.service.dto.GroupDTO;
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
    private ModelMapper modelMapper;
    private GroupDao groupDao;

    @Autowired
    public GroupService( ModelMapper modelMapper,GroupDao groupDao){
        this.modelMapper = modelMapper;
        this.groupDao = groupDao;
    }

    @Override
    public GroupDTO save(GroupDTO groupDTO) {
        logger.debug("Calling the save method from dao");
        return modelMapper.map(groupDao
                 .save(modelMapper.map(groupDTO, Group.class)),GroupDTO.class);
    }

    @Override
    public GroupDTO findById(GroupDTO groupDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return modelMapper.map(groupDao
                    .findById(modelMapper.map(groupDTO, Group.class)), GroupDTO.class);
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find this record",e);
        }
    }

    @Override
    public List<?> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            return groupDao.findAll().stream()
                    .map(elem -> modelMapper.map(elem, GroupDTO.class))
                    .collect(Collectors.toList());
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find any records",e);
        }
    }

    @Override
    public void update(GroupDTO groupDTO) {
        logger.debug("Calling the update method from dao");
        groupDao.update(modelMapper.map(groupDTO,Group.class));
    }

    @Override
    public void delete(GroupDTO groupDTO) {
        logger.debug("Calling the delete method from dao");
        groupDao.delete(modelMapper.map(groupDTO,Group.class));
    }
}

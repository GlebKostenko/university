package com.foxminded.service;

import com.foxminded.dao.GroupDao;
import com.foxminded.model.Group;
import com.foxminded.service.dto.GroupDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GroupService implements ServiceLayer<GroupDTO>{
    private ModelMapper modelMapper;
    private GroupDao groupDao;

    public GroupService(@Autowired ModelMapper modelMapper,@Autowired GroupDao groupDao){
        this.modelMapper = modelMapper;
        this.groupDao = groupDao;
    }

    @Override
    public GroupDTO save(GroupDTO groupDTO) throws SQLException {
         return modelMapper.map(groupDao
                 .save(modelMapper.map(groupDTO, Group.class))
                 ,GroupDTO.class);
    }

    @Override
    public GroupDTO findById(GroupDTO groupDTO) throws SQLException {
        return modelMapper.map(groupDao
                .findById(modelMapper.map(groupDTO,Group.class))
                ,GroupDTO.class);
    }

    @Override
    public List<?> findAll() throws SQLException {
        return groupDao.findAll().stream()
                .map(elem -> modelMapper.map(elem,GroupDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(GroupDTO groupDTO) {
        groupDao.update(modelMapper.map(groupDTO,Group.class));
    }

    @Override
    public void delete(GroupDTO groupDTO) {
        groupDao.delete(modelMapper.map(groupDTO,Group.class));
    }
}

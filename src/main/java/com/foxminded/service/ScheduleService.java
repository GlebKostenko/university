package com.foxminded.service;

import com.foxminded.dao.ScheduleDao;
import com.foxminded.model.Schedule;
import com.foxminded.service.dto.ScheduleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService implements ServiceLayer<ScheduleDTO>{
    private ModelMapper modelMapper;
    private ScheduleDao scheduleDao;

    public ScheduleService(@Autowired ModelMapper modelMapper,@Autowired ScheduleDao scheduleDao){
        this.modelMapper = modelMapper;
        this.scheduleDao = scheduleDao;
    }

    @Override
    public ScheduleDTO save(ScheduleDTO scheduleDTO) throws SQLException {
        return modelMapper.map(scheduleDao
                .save(modelMapper.map(scheduleDTO, Schedule.class))
                ,ScheduleDTO.class);
    }

    @Override
    public ScheduleDTO findById(ScheduleDTO scheduleDTO) throws SQLException {
        return modelMapper.map(scheduleDao
                .findById(modelMapper.map(scheduleDTO,Schedule.class))
                ,ScheduleDTO.class);
    }

    @Override
    public List<?> findAll() throws SQLException {
        return scheduleDao.findAll().stream()
                .map(elem -> modelMapper.map(elem,ScheduleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(ScheduleDTO scheduleDTO) {
        scheduleDao.update(modelMapper.map(scheduleDTO,Schedule.class));
    }

    @Override
    public void delete(ScheduleDTO scheduleDTO) {
        scheduleDao.delete(modelMapper.map(scheduleDTO,Schedule.class));
    }
}

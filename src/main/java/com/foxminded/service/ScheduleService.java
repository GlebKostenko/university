package com.foxminded.service;

import com.foxminded.dao.ScheduleDao;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.exception.EmptyResultSetExceptionService;
import com.foxminded.model.Schedule;
import com.foxminded.service.dto.ScheduleDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService implements ServiceLayer<ScheduleDTO>{
    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class.getSimpleName());
    private ModelMapper modelMapper;
    private ScheduleDao scheduleDao;

    @Autowired
    public ScheduleService( ModelMapper modelMapper,ScheduleDao scheduleDao){
        this.modelMapper = modelMapper;
        this.scheduleDao = scheduleDao;
    }

    @Override
    public ScheduleDTO save(ScheduleDTO scheduleDTO) {
        logger.debug("Calling the save method from dao");
        return modelMapper.map(scheduleDao
                .save(modelMapper.map(scheduleDTO, Schedule.class)),ScheduleDTO.class);
    }

    @Override
    public ScheduleDTO findById(ScheduleDTO scheduleDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return modelMapper.map(scheduleDao
                    .findById(modelMapper.map(scheduleDTO, Schedule.class)), ScheduleDTO.class);
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find this record",e);
        }
    }

    @Override
    public List<?> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            return scheduleDao.findAll().stream()
                    .map(elem -> modelMapper.map(elem, ScheduleDTO.class))
                    .collect(Collectors.toList());
        }catch (EmptyResultSetExceptionDao e){
            logger.warn("Dao throws exception");
            throw new EmptyResultSetExceptionService("Dao layer can't find any records",e);
        }
    }

    @Override
    public void update(ScheduleDTO scheduleDTO) {
        logger.debug("Calling the update method from dao");
        scheduleDao.update(modelMapper.map(scheduleDTO,Schedule.class));
    }

    @Override
    public void delete(ScheduleDTO scheduleDTO) {
        logger.debug("Calling the delete method from dao");
        scheduleDao.delete(modelMapper.map(scheduleDTO,Schedule.class));
    }
}

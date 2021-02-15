package com.foxminded.service;

import com.foxminded.exception.DomainException;
import com.foxminded.model.Schedule;
import com.foxminded.repository.ScheduleRepository;
import com.foxminded.service.dto.ScheduleDTO;
import org.modelmapper.MappingException;
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
    ModelMapper mapper;
    private ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ModelMapper mapper, ScheduleRepository scheduleRepository) {
        this.mapper = mapper;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleDTO save(ScheduleDTO scheduleDTO) {
        try {
            logger.debug("Calling the save method from dao and trying to save schedule with next parameters\n" +
                            "group_id: {}\n" +
                            "date_time: {}\n" +
                            "duration: {}\n" +
                            "teacher_id: {}\n" +
                            "hall_id: {}\n" +
                            "subject_id: {}"
                    , scheduleDTO.getGroup().getGroupId()
                    , scheduleDTO.getDateTime()
                    , scheduleDTO.getDuration()
                    , scheduleDTO.getTeacher().getTeacherId()
                    , scheduleDTO.getLectureHall().getHallId()
                    , scheduleDTO.getSubject().getSubjectId()
            );
            return mapper.map(scheduleRepository.save(mapper.map(scheduleDTO, Schedule.class)),ScheduleDTO.class);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map ScheduleDTO to Schedule or Schedule to ScheduleDTO",e);
        }
    }

    @Override
    public ScheduleDTO findById(ScheduleDTO scheduleDTO) {
        logger.debug("Calling the findById method from dao");
        try {
            return mapper.map(scheduleRepository.findById(scheduleDTO.getScheduleId()).get(),ScheduleDTO.class);
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map ScheduleDTO to Schedule or Schedule to ScheduleDTO",e);
        }
    }

    @Override
    public List<ScheduleDTO> findAll() {
        logger.debug("Calling the findAll method from dao");
        try {
            List<Schedule> schedules = (List<Schedule>) scheduleRepository.findAll();
            return schedules.stream().map(x -> mapper.map(x,ScheduleDTO.class)).collect(Collectors.toList());
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map Schedule to ScheduleDTO",e);
        }
    }

    @Override
    public void update(ScheduleDTO scheduleDTO) {
        try {
            logger.debug("Calling the update method from dao");
            scheduleRepository.save(mapper.map(scheduleDTO,Schedule.class));
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map ScheduleDTO to Schedule",e);
        }
    }

    @Override
    public void delete(ScheduleDTO scheduleDTO) {
        try {
            logger.debug("Calling the delete method from dao");
            scheduleRepository.delete(mapper.map(scheduleDTO,Schedule.class));
        }catch (MappingException e){
            logger.error("Mapping error");
            throw new DomainException("Can't map ScheduleDTO to Schedule",e);
        }
    }
}

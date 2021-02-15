package com.foxminded.repository;

import com.foxminded.model.Schedule;
import com.foxminded.service.dto.ScheduleDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<ScheduleDTO,Long> {
}

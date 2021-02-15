package com.foxminded.repository;

import com.foxminded.model.Teacher;
import com.foxminded.service.dto.TeacherDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends CrudRepository<TeacherDTO,Long> {
}

package com.foxminded.repository;

import com.foxminded.model.Student;
import com.foxminded.service.dto.StudentDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<StudentDTO,Long> {
}

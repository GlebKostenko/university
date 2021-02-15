package com.foxminded.repository;

import com.foxminded.model.Subject;
import com.foxminded.service.dto.SubjectDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<SubjectDTO,Long> {
}

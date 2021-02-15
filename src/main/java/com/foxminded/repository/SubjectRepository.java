package com.foxminded.repository;

import com.foxminded.model.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<Subject,Long> {
}

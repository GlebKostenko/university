package com.foxminded.repository;

import com.foxminded.model.LectureHall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureHallRepository extends CrudRepository<LectureHall,Long> {
}

package com.foxminded.repository;

import com.foxminded.model.Group;
import com.foxminded.service.dto.GroupDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<GroupDTO,Long> {
}

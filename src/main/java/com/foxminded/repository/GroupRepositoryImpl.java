package com.foxminded.repository;

import com.foxminded.model.Group;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Map;

@Repository
public class GroupRepositoryImpl implements GroupRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void findByNameAndUpdate(String groupName, Map<String, String> dataFroUpdate) {
        TypedQuery<Group> tq = entityManager.createQuery("from Group WHERE groupName = '" + groupName + "'", Group.class);
        Group group = tq.getSingleResult();
        group.setGroupName(dataFroUpdate.get("group_name"));
        entityManager.merge(group);
    }
}

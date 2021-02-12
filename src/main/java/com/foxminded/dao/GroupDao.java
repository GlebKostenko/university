package com.foxminded.dao;

import com.foxminded.model.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupDao implements Dao<Group> {
    private SessionFactory factory;
    private static final Logger logger = LoggerFactory.getLogger(GroupDao.class.getSimpleName());

    @Autowired
    public GroupDao(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public Group save(Group group) {
        logger.debug("Trying to insert new record in Groups table");
        try (final Session session = factory.openSession()) {

            session.beginTransaction();

            group.setGroupId((Long) session.save(group));

            session.getTransaction().commit();

            return group;
        }
    }
    @Override
    public Group findById(Group group) {
        logger.debug("Trying to find group with id: {}",group.getGroupId());
        try (final Session session = factory.openSession()) {
           return session.get(Group.class, group.getGroupId());
        }
    }

    @Override
    public List<Group> findAll()  {
        logger.debug("Trying to return existing groups");
        try (final Session session = factory.openSession()) {
            return session.createQuery("SELECT a FROM Group a", Group.class).getResultList();

        }
    }

    @Override
    public void update(Group group) {
        logger.debug("Updating record with id: {}", group.getGroupId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.update(group);

            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Group group) {
        logger.debug("Deleting record with id: {}",group.getGroupId());
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.delete(group);

            session.getTransaction().commit();
        }
    }
}

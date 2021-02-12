package com.foxminded.dao;

import com.foxminded.configuration.SpringHibernateConfigTest;
import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Group;
import com.foxminded.model.Schedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.persistence.OptimisticLockException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringHibernateConfigTest.class})
class GroupDaoTest {

    @Autowired
    private GroupDao groupDao;
    @Test
    void save_WhenAllIsRight_thenShouldBeNewRecord() {
        Group group = groupDao.save(new Group("fakt-03"));
        assertEquals(group,groupDao.findById(new Group(group.getGroupId())));
    }

    @Test
    void findById_WhenRecordExist_thenShouldFindThisRecord() {
        Group group = groupDao.save(new Group("fakt-02"));
        assertTrue(groupDao.findById(new Group(group.getGroupId())).equals(group));
    }

    @Test
    void findById_WhenRecordDoesNotExist_thenShouldBeNothing(){
        groupDao.findById(new Group(44L));
    }

    @Test
    void findAll_WhenRecordsExist_thenShouldBeNotEmptyResultList()  {
        groupDao.save(new Group("fakt-01"));
        assertTrue(!groupDao.findAll().isEmpty());
    }

    @Test
    void update_WhenRecordExist_thenRecordShouldBeUpdated() {
        Group group =groupDao.save(new Group("frkt-01"));
        Group groupNew = new Group(group.getGroupId(),"fivt-05");
        groupDao.update(groupNew);
        Group updatedGroup = new Group(group.getGroupId(),"fivt-05");
        assertEquals(updatedGroup,groupDao.findById(new Group(group.getGroupId())));
    }

    @Test
    void update_WhenRecordDoesNotExist_thenShouldBeException(){
        Group groupNew = new Group(44L,"fefm-002");
        Throwable exception = assertThrows(OptimisticLockException.class, () -> groupDao.update(groupNew));
    }

    @Test
    void delete_WhenRecordExist_thenRecordShouldBeDeleted() {
        Group group = groupDao.save(new Group("fopf-07"));
        groupDao.delete(new Group(group.getGroupId()));
        assertFalse(groupDao.findAll().contains(group));
    }

    @Test
    void delete_WhenRecordDoesNotExist_thenShouldBeException() {
        Throwable exception = assertThrows(OptimisticLockException.class, () -> groupDao.delete(new Group(44L)));
    }
}
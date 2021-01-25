package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class GroupDaoTest {
    @Autowired
    private GroupDao groupDao;
    @Test
    void save() throws SQLException {
        Group group = groupDao.save(new Group("fakt-03"));
        assertEquals(group,groupDao.findById(new Group(group.getGroupId())));
    }

    @Test
    void findById() throws SQLException{
        Group group = groupDao.save(new Group("fakt-02"));
        assertTrue(groupDao.findById(new Group(group.getGroupId())).equals(group));
    }

    @Test
    void findAll() throws SQLException{
        groupDao.save(new Group("fakt-01"));
        assertTrue(!groupDao.findAll().isEmpty());
    }

    @Test
    void update() throws SQLException{
        Group group =groupDao.save(new Group("frkt-01"));
        Group groupNew = new Group(group.getGroupId(),"fivt-05");
        groupDao.update(groupNew);
        Group updatedGroup = new Group(group.getGroupId(),"fivt-05");
        assertEquals(updatedGroup,groupDao.findById(new Group(group.getGroupId())));
    }

    @Test
    void delete() throws SQLException{
        Group group = groupDao.save(new Group("fopf-07"));
        groupDao.delete(new Group(group.getGroupId()));
        assertFalse(groupDao.findAll().contains(group));
    }
}
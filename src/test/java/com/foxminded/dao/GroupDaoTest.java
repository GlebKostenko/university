package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfigTest;
import com.foxminded.model.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfigTest.class})
class GroupDaoTest {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    GroupDaoTest(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Test
    void save() throws SQLException {
        Group group = groupDao.save(new Group("fakt-03"));
        int numberOfGroup = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM groups WHERE group_id = ?",new Object[]{group.getGroupId()},Integer.class);
        assertTrue(numberOfGroup > 0);
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
        Group groupNew = new Group("fivt-05");
        groupDao.update(group.getGroupId(),groupNew);
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
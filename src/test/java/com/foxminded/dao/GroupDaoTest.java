package com.foxminded.dao;

import com.foxminded.configuration.SpringJdbcConfig;
import com.foxminded.model.Group;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfig.class,GroupDao.class})
class GroupDaoTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private GroupDao groupDao;
    @BeforeEach
    void createTables(){
        String aSQLScriptFilePath = "/home/gleb/IdeaProjects/university/src/test/resources/creating_scripts.sql";

        try {
            ScriptRunner sr = new ScriptRunner(dataSource.getConnection());
            Reader reader = new BufferedReader(
                    new FileReader(aSQLScriptFilePath));
            sr.runScript(reader);
        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath
                    + " The error is " + e.getMessage());
        }
    }

    @Test
    void save() throws SQLException {
        Group group = groupDao.save(new Group("fakt-03"));
        PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement("SELECT COUNT(1) FROM groups WHERE group_id = ?");
        preparedStatement.setLong(1,group.getGroupId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertTrue(resultSet.getInt(1) > 0);
    }

    @Test
    void findById() throws SQLException{
        Group group = groupDao.save(new Group("fakt-03"));
        Group groupForFind = new Group(1L);
        assertEquals(group,groupDao.findById(groupForFind));
    }

    @Test
    void findAll() throws SQLException{
        List<Group> groups = new ArrayList<>();
        groups.add(groupDao.save(new Group("fakt-03")));
        assertEquals(groups,groupDao.findAll());
    }

    @Test
    void update() throws SQLException{
        groupDao.save(new Group("fakt-03"));
        Group group = new Group("fivt-05");
        groupDao.update(1L,group);
        Group updatedGroup = new Group(1L,"fivt-05");
        assertEquals(updatedGroup,groupDao.findById(new Group(1L)));
    }

    @Test
    void delete() throws SQLException{
        groupDao.save(new Group("fakt-03"));
        Group groupForDeleting = new Group(1L);
        groupDao.delete(groupForDeleting);
        assertTrue(groupDao.findAll().isEmpty());
    }
}
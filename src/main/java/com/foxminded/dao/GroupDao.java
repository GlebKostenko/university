package com.foxminded.dao;

import com.foxminded.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupDao implements Dao<Group> {
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    @Override
    public Group save(Group group) throws SQLException{
        jdbcTemplate.update("INSERT INTO groups(group_name) VALUES ?", group.getGroupName());
        PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement("SELECT group_id,group_name FROM groups WHERE group_name =?");
        preparedStatement.setString(1,group.getGroupName());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Group(resultSet.getLong(1),resultSet.getString(2));
    }
    @Override
    public Group findById(Group group) throws SQLException{
        PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement("SELECT group_id,group_name FROM groups WHERE group_id =?");
        preparedStatement.setLong(1,group.getGroupId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Group(resultSet.getLong(1),resultSet.getString(2));
    }

    @Override
    public List<Group> findAll() throws SQLException {
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT group_id,group_name FROM groups");
        List<Group> groups = new ArrayList<>();
        while(resultSet.next()){
            groups.add(new Group(resultSet.getLong(1),resultSet.getString(2)));
        }
        return groups;
    }

    @Override
    public void update(Long groupId,Group group) {
        jdbcTemplate.update("UPDATE groups SET group_name = ? WHERE group_id = ?",group.getGroupName(),groupId);
    }

    @Override
    public void delete(Group group) {
        jdbcTemplate.update("DELETE FROM groups WHERE group_id = ?",group.getGroupId());
    }
}

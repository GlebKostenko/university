package com.foxminded.dao;

import com.foxminded.model.Group;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroupDao implements GroupCRUD{
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);
    private DataSource dataSource = context.getBean("postgresDataSource",DataSource.class);
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    @Override
    public void save(String groupName){
        jdbcTemplate.update("INSERT INTO groups(group_name) VALUES ?",groupName);
    }

    @Override
    public Group findById(int groupId) throws SQLException{
        PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement("SELECT group_id,group_name FROM groups WHERE group_id =?");
        preparedStatement.setInt(1,groupId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Group(resultSet.getInt(1),resultSet.getString(2));
    }

    @Override
    public List<Group> findAll() throws SQLException {
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT group_id,group_name FROM groups");
        List<Group> groups = new ArrayList<>();
        while(resultSet.next()){
            groups.add(new Group(resultSet.getInt(1),resultSet.getString(2)));
        }
        return groups;
    }

    @Override
    public void update(int groupId, String groupName) {
        jdbcTemplate.update("UPDATE groups SET group_name = ? WHERE group_id = ?",groupName,groupId);
    }

    @Override
    public void delete(int groupId) {
        jdbcTemplate.update("DELETE FROM groups WHERE group_id = ?",groupId);
    }
}

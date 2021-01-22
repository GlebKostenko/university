package com.foxminded.dao;

import com.foxminded.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GroupDao implements Dao<Group> {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public GroupDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Group save(Group group) throws SQLException{
        jdbcTemplate.update("INSERT INTO groups(group_name) VALUES ?", group.getGroupName());
        return jdbcTemplate.queryForObject("SELECT group_id,group_name FROM groups WHERE group_name =?"
                ,new Object[]{group.getGroupName()},(rs,rowNum)->
                        new Group(rs.getLong(1),rs.getString(2))
        );
    }
    @Override
    public Group findById(Group group) throws SQLException{
        return jdbcTemplate.queryForObject("SELECT group_id,group_name FROM groups WHERE group_id =?"
                ,new Object[]{group.getGroupId()},(rs,rowNum)->
                        new Group(rs.getLong(1),rs.getString(2))
        );
    }

    @Override
    public List<Group> findAll() throws SQLException {
        return jdbcTemplate.query("SELECT group_id,group_name FROM groups",(rs,rowNum)->
                new Group(rs.getLong(1),rs.getString(2))
        );
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

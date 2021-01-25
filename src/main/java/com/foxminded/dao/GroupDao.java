package com.foxminded.dao;

import com.foxminded.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO groups(group_name) VALUES ?",new String[]{"group_id"});
            ps.setString(1,group.getGroupName());
            return ps;
        },keyHolder);
        return new Group( keyHolder.getKey().longValue(),group.getGroupName());
    }
    @Override
    public Group findById(Group group) throws SQLException{
        return jdbcTemplate.queryForObject("SELECT group_id,group_name FROM groups WHERE group_id =?"
                ,new BeanPropertyRowMapper<Group>(Group.class),group.getGroupId()
        );
    }

    @Override
    public List<Group> findAll() throws SQLException {
        return jdbcTemplate.query("SELECT group_id,group_name FROM groups"
                ,new BeanPropertyRowMapper<Group>(Group.class)
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

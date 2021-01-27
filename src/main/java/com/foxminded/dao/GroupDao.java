package com.foxminded.dao;

import com.foxminded.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GroupDao implements Dao<Group> {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public GroupDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Group save(Group group) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("group_name",group.getGroupName());
        Long id = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("groups")
                .usingGeneratedKeyColumns("group_id")
                .executeAndReturnKey(parameters).longValue();
        group.setGroupId(id);
        return group;
    }
    @Override
    public Group findById(Group group) {
        return jdbcTemplate.queryForObject("SELECT group_id,group_name FROM groups WHERE group_id =?"
                ,new BeanPropertyRowMapper<Group>(Group.class),group.getGroupId()
        );
    }

    @Override
    public List<Group> findAll()  {
        return jdbcTemplate.query("SELECT group_id,group_name FROM groups"
                ,new BeanPropertyRowMapper<Group>(Group.class)
        );
    }

    @Override
    public void update(Group group) {
        jdbcTemplate.update("UPDATE groups SET group_name = ? WHERE group_id = ?"
                ,group.getGroupName()
                ,group.getGroupId());
    }

    @Override
    public void delete(Group group) {
        jdbcTemplate.update("DELETE FROM groups WHERE group_id = ?",group.getGroupId());
    }
}

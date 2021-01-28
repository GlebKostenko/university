package com.foxminded.dao;

import com.foxminded.exception.EmptyResultSetExceptionDao;
import com.foxminded.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private static final Logger logger = LoggerFactory.getLogger(GroupDao.class.getSimpleName());

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public GroupDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Group save(Group group) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("group_name",group.getGroupName());
        logger.debug("Trying to insert new record in Groups table");
        Long id = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("groups")
                .usingGeneratedKeyColumns("group_id")
                .executeAndReturnKey(parameters).longValue();
        group.setGroupId(id);
        logger.debug("Return Group with id: {}", id);
        return group;
    }
    @Override
    public Group findById(Group group) {
        logger.debug("Trying to find group with id: {}",group.getGroupId());
        try {
            return jdbcTemplate.queryForObject("SELECT group_id,group_name FROM groups WHERE group_id =?"
                    , new BeanPropertyRowMapper<Group>(Group.class), group.getGroupId()
            );
        }catch (EmptyResultDataAccessException e){
            logger.warn("Group with the same id wasn't found");
            throw new EmptyResultSetExceptionDao("Groups table doesn't contain this record",e);
        }
    }

    @Override
    public List<Group> findAll()  {
        logger.debug("Trying to return existing groups");
        try {
            return jdbcTemplate.query("SELECT group_id,group_name FROM groups"
                    , new BeanPropertyRowMapper<Group>(Group.class)
            );
        }catch (EmptyResultDataAccessException e){
            logger.warn("List of groups is empty");
            throw new EmptyResultSetExceptionDao("Groups table is empty",e);
        }
    }

    @Override
    public void update(Group group) {
        logger.debug("Updating record with id: {}", group.getGroupId());
        jdbcTemplate.update("UPDATE groups SET group_name = ? WHERE group_id = ?"
                ,group.getGroupName()
                ,group.getGroupId());
    }

    @Override
    public void delete(Group group) {
        logger.debug("Deleting record with id: {}",group.getGroupId());
        jdbcTemplate.update("DELETE FROM groups WHERE group_id = ?",group.getGroupId());
    }
}

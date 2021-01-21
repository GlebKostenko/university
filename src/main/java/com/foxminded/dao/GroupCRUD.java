package com.foxminded.dao;

import com.foxminded.model.Group;

import java.sql.SQLException;
import java.util.List;

public interface GroupCRUD {
    void save(String groupName);
    Group findById(int groupId) throws SQLException;
    List<Group> findAll() throws SQLException;
    void update(int groupId,String groupName);
    void delete(int groupId);
}

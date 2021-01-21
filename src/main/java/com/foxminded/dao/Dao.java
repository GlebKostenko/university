package com.foxminded.dao;

import com.foxminded.model.Group;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    T save(T t) throws SQLException;
    T findById(T t) throws SQLException;
    List<?> findAll() throws SQLException;
    void update(Long id,T t);
    void delete(T t);
}

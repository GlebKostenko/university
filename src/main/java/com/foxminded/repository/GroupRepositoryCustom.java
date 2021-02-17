package com.foxminded.repository;

import java.util.Map;

public interface GroupRepositoryCustom {
    void findByNameAndUpdate(String groupName, Map<String,String> dataFroUpdate);
}

package com.foxminded.model;


public class Group {
    private long groupId;
    private String groupName;

    public Group(long groupId,String groupName){
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public long getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }
}

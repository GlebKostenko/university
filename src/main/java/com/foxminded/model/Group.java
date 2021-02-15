
package com.foxminded.model;


import javax.persistence.*;
import java.util.Objects;

public class Group {

    private Long groupId;
    private String groupName;

    public Group(Long groupId,String groupName){
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Group(){}

    public Group(String groupName){
        this.groupName = groupName;
    }

    public Group(Long groupId){
        this.groupId = groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(groupId, group.groupId) && Objects.equals(groupName, group.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName);
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}

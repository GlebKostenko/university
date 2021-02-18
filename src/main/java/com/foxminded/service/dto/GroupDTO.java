
package com.foxminded.service.dto;


import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class GroupDTO {
    private Long groupId;
    @NotBlank(message = "group name is mandatory")
    private String groupName;

    public GroupDTO(Long groupId, String groupName){
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public GroupDTO(){}

    public GroupDTO(String groupName){
        this.groupName = groupName;
    }

    public GroupDTO(Long groupId){
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
        GroupDTO group = (GroupDTO) o;
        return Objects.equals(groupId, group.groupId) && Objects.equals(groupName, group.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName);
    }
}

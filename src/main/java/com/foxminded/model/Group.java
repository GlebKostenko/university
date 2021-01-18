package com.foxminded.model;

import java.util.List;

public class Group {
    private long groupId;
    private List<Student> students;
    public Group(long groupId, List<Student> students){
        this.groupId = groupId;
        this.students = students;
    }
    public long getGroupId(){
        return groupId;
    }

    public List<Student> getStudents(){
        return students;
    }
}

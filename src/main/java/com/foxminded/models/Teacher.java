package com.foxminded.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Teacher {
    private int teacherId;
    private List<TeacherTimeTable> teacherTimeTableList;

    public Teacher(int teacherId, List<TeacherTimeTable> teacherTimeTableList) {
        this.teacherId = teacherId;
        this.teacherTimeTableList = teacherTimeTableList;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public List<TeacherTimeTable> getTeacherTimeTableList() {
        return teacherTimeTableList;
    }

    public TeacherTimeTable getTimeTableForADay(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return teacherTimeTableList.stream().
                filter(x -> simpleDateFormat.format(x.getDate()).equals(simpleDateFormat.format(new Date().getTime()))).
                findAny().get();
    }

    public List<TeacherTimeTable> getTimeTableForAMonth()  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return teacherTimeTableList.stream().
                filter(x->{
                    try {
                        return x.getDate().compareTo(simpleDateFormat.parse(simpleDateFormat.format(new Date()))) >= 0;
                    }catch (ParseException e){
                        e.printStackTrace();
                        return 1<0;
                    }
                }).limit(30).collect(Collectors.toList());
    }
}

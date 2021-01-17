package com.foxminded.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Student {
    private int studentId;
    private List<StudentTimeTable> studentTimeTableList;

    public Student(int studentId, List<StudentTimeTable> studentTimeTableList) {
        this.studentId = studentId;
        this.studentTimeTableList = studentTimeTableList;
    }

    public int getStudentId() {
        return studentId;
    }

    public List<StudentTimeTable> getStudentTimeTableList() {
        return studentTimeTableList;
    }
    public StudentTimeTable getTimeTableForADay(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return studentTimeTableList.stream().
                filter(x -> simpleDateFormat.format(x.getDate()).equals(simpleDateFormat.format(new Date().getTime()))).
        findAny().get();
    }

    public List<StudentTimeTable> getTimeTableForAMonth()  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return studentTimeTableList.stream().
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

package com.foxminded.model;

public class LectureHall {
    private Long hallId;
    private String hallName;
    public LectureHall(Long hallId, String hallName){
        this.hallId = hallId;
        this.hallName = hallName;
    }
    public long getHallId(){
        return hallId;
    }

    public String getHallName() {
        return hallName;
    }
}

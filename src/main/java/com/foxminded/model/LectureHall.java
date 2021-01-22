package com.foxminded.model;

import java.util.Objects;

public class LectureHall {
    private Long hallId;
    private String hallName;
    public LectureHall(Long hallId, String hallName){
        this.hallId = hallId;
        this.hallName = hallName;
    }
    public LectureHall(Long hallId){
        this.hallId = hallId;
    }

    public LectureHall(String hallName){
        this.hallName = hallName;
    }

    public long getHallId(){
        return hallId;
    }

    public String getHallName() {
        return hallName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureHall that = (LectureHall) o;
        return Objects.equals(hallId, that.hallId) && Objects.equals(hallName, that.hallName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hallId, hallName);
    }
}

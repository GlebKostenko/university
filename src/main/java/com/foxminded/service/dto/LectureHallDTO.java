package com.foxminded.service.dto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "lecture_halls")
public class LectureHallDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id")
    private Long hallId;
    @Column(name = "hall_name")
    private String hallName;
    public LectureHallDTO(){}
    public LectureHallDTO(Long hallId, String hallName){
        this.hallId = hallId;
        this.hallName = hallName;
    }
    public LectureHallDTO(Long hallId){
        this.hallId = hallId;
    }

    public LectureHallDTO(String hallName){
        this.hallName = hallName;
    }

    public Long getHallId(){
        return hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureHallDTO that = (LectureHallDTO) o;
        return Objects.equals(hallId, that.hallId) && Objects.equals(hallName, that.hallName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hallId, hallName);
    }
}

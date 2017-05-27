package com.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by mzzhang on 2017/5/27.
 */
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;//教室名称，如'丹青楼203'

    @OneToMany(mappedBy = "room")
    private Set<ExamRoom> examRooms;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ExamRoom> getExamRooms() {
        return examRooms;
    }

    public void setExamRooms(Set<ExamRoom> examRooms) {
        this.examRooms = examRooms;
    }


}

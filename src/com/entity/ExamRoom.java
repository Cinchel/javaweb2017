package com.entity;

import javax.persistence.*;
import java.util.Set;

/**
考场类
 */

@Entity
public class ExamRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany
    private Set<Teacher> teachers; //监考老师
    @ManyToOne
    private Exam exam;//本考场属于哪门考试
    @ManyToOne
    private Room room;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}

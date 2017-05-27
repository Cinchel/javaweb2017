package com.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * Created by mzzhang on 2017/5/27.
 */
@Entity
public class Teacher extends User{

    @ManyToMany
    private Set<ExamRoom> examRooms;

    public Set<ExamRoom> getExamRooms() {
        return examRooms;
    }

    public void setExamRooms(Set<ExamRoom> examRooms) {
        this.examRooms = examRooms;
    }
}

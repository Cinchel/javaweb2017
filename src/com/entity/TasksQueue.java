package com.entity;

import javax.persistence.*;

@Entity
public class TasksQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Task task;
    @ManyToOne
    private Teacher teacher;
    private String status;
}

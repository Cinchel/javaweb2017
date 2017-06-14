package com.entity;

import javax.persistence.*;

import java.util.Date;

/**
 * Created by libby on 2017/6/11.
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false, length = 30)
    private String taskName;
    private String description;
    private Date  deadline;
    @ManyToOne
    private Admin createAdmin;//创建任务的管理员

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTaskName() {return taskName;}

    public void setTaskName(String taskName) {this.taskName = taskName;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Date getDeadline() {return deadline;}

    public void setDeadline(Date deadline) {this.deadline = deadline;}

    public Admin getCreateAdmin2() {return createAdmin;}

    public void setCreateAdmin2(Admin createAdmin2) {this.createAdmin = createAdmin2;}
}

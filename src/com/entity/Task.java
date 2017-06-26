package com.entity;

import javax.persistence.*;

import java.io.File;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = false, nullable = false, length = 70)
    private String taskName;
    private String description;
    private Date deadline;
    private String replyMessage;
    private String filePath;
    @ManyToOne
    private Admin createAdmin;//创建任务的管理员

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTaskName() {return taskName;}

    public void setTaskName(String taskName) {this.taskName = taskName;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Admin getCreateAdmin() {
        return createAdmin;
    }

    public void setCreateAdmin(Admin createAdmin) {
        this.createAdmin = createAdmin;
    }

    public String getReplyMessage() {return replyMessage;}

    public void setReplyMessage(String replyMessage) {this.replyMessage = replyMessage;}

    public String getFilePath(){
        return filePath;
    }

    public void setFilePath(String path){
        this.filePath = path;
    }

    public Admin getCreateAdmin2() {return createAdmin;}

    public void setCreateAdmin2(Admin createAdmin2) {this.createAdmin = createAdmin2;}

}

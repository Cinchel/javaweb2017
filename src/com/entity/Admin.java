package com.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created by mzzhang on 2017/5/27.
 */
@Entity
public class Admin extends User{
    @OneToMany(mappedBy = "createAdmin")
    private Set<Exam> exams;//该管理员创建的考试
    @OneToMany(mappedBy = "createAdmin")
    private Set<Task> tasks;//该管理员创建的任务

    public Admin() {
    }

    public Set<Exam> getExams() {
        return exams;
    }

    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }
}

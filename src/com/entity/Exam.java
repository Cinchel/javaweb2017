package com.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;


@Entity
public class Exam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;//考试名称
    private String room;//考试地点
    private Date date;//考试日期
    private Time startTime;//开始时间
    private Time endTime;//结束时间
    private int number;
    @ManyToOne
	private Admin createAdmin;//创建考试的管理员
    @OneToMany(mappedBy = "exam")
    private Set<ExamTeacher> examTeacher;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Set<ExamTeacher> getExamTeacher() {
        return examTeacher;
    }

    public void setExamTeacher(Set<ExamTeacher> examTeacher) {
        this.examTeacher = examTeacher;
    }

    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
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
    public Admin getCreateAdmin() {
        return createAdmin;
    }
    public void setCreateAdmin(Admin createAdmin) {
        this.createAdmin = createAdmin;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Time getStartTime() {
        return startTime;
    }
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    public Time getEndTime() {
        return endTime;
    }
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

}

package com.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Exam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;//考试名称
    private Date startTime;
    private Date endTime;
    @ManyToOne
	private Admin createAdmin;//创建考试的管理员

    @OneToMany(mappedBy = "exam")
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

    public Admin getCreateAdmin() {
        return createAdmin;
    }

    public void setCreateAdmin(Admin createAdmin) {
        this.createAdmin = createAdmin;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Set<ExamRoom> getExamRooms() {
        return examRooms;
    }

    public void setExamRooms(Set<ExamRoom> examRooms) {
        this.examRooms = examRooms;
    }

}

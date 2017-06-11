package com.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false, length = 30)
	private String userName;
    @Column(nullable = false, length = 40)
	private String password;
    @Column(length = 20)
	private String phone;//电话号码
	private String email;//电子邮件
    @Column(length = 20)
	private String title;//职称
    @Column(length = 1000)
	private String introduction;//简介
	@OneToMany(mappedBy = "teacher")
	private Set<ExamTeacher> teacherExam;

	public Set<ExamTeacher> getTeacherExam() {
		return teacherExam;
	}

	public void setTeacherExam(Set<ExamTeacher> teacherExam) {
		this.teacherExam = teacherExam;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/*public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}*/

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
}

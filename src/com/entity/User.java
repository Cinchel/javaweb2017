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
	/*
	role表示用户角色，分别有'root'，'admin'，'teacher'三种角色
	root(超级管理员): 设置学期基点时间，即学期第一周周日的日期(保存在 properties)
	admin(管理员/专业主任): 添加用户;修改指定用户信息
	teacher(教师用户):
	 */
	/*private String role;*/

    @Column(length = 20)
	private String phone;//电话号码
	private String email;//电子邮件
    @Column(length = 20)
	private String title;//职称
    @Column(length = 1000)
	private String introduction;//简介
	@ManyToMany
	private Set<Exam> exam;

	public Set<Exam> getExam() {
		return exam;
	}

	public void setExam(Set<Exam> exam) {
		this.exam = exam;
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

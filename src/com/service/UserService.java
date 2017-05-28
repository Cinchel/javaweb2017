package com.service;

import com.dao.UserDao;
import com.entity.Admin;
import com.entity.Teacher;
import com.entity.User;
import com.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public User getUser(String userName, String password) {
		return userDao.find(userName, password);
	}
	public User getUserWithoutPassword(String userName) {
		return userDao.findWithoutPassword(userName);
	}
	public String usersList(int offset,int limit) {
		return Json.writeTableList(userDao.usersCount(), userDao.usersList(offset,limit));
	}
	public User insertUser(String userName, String title, String introduction, String phone, String role) {
		User user;
		if(role.equals("teacher")) user = new Teacher();
		else if(role.equals("admin")) user = new Admin();
		else return null;
		user.setUserName(userName);
		user.setTitle(title);
		user.setIntroduction(introduction);
		user.setPhone(phone);
		return userDao.insertUser(user);
	}
}

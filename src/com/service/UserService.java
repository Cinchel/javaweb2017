package com.service;

import com.dao.UserDao;
import com.entity.Admin;
import com.entity.Teacher;
import com.entity.User;
import com.util.Json;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
		List<User> list = userDao.usersList(offset,limit);
		List<JSONObject> list2 = new ArrayList<JSONObject>();
		for(User user : list) {
			JSONObject obj = new JSONObject();
			System.out.println(user.getUserName());
			obj.put("id",user.getId());
			obj.put("userName",user.getUserName());
			String role = user.getClass().toString();
			if(role.equals("class com.entity.Teacher")) obj.put("role","teacher");
			else if(role.equals("class com.entity.Admin")) obj.put("role","admin");
			else if(role.equals("class com.entity.Root")) obj.put("role","root");
			obj.put("introduction",user.getIntroduction());
			obj.put("phone",user.getPhone());
			obj.put("title",user.getTitle());
			list2.add(obj);
		}
		return Json.writeTableList(userDao.usersCount(), list2);
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

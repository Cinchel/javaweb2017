package com.service;

import com.dao.UserDao;
import com.entity.Admin;
import com.entity.Teacher;
import com.entity.User;
import com.exception.PostException;
import com.util.Json;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.http.HTTPException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao userDao;
	public User getUser(String userName, String password) throws PostException {
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
			obj.put("id",user.getId());
			obj.put("userName",user.getUserName());
			String role = user.getClass().toString();
			if(role.equals("class com.entity.Teacher")) obj.put("role","教师&nbsp;&nbsp;<button class='btn btn-success' onclick=userAdminEdit_toggleRole("+user.getId()+")>升为管理员</button>");
			else if(role.equals("class com.entity.Admin")) obj.put("role","管理员&nbsp;&nbsp;<button class='btn btn-info' onclick=userAdminEdit_toggleRole("+user.getId()+")>降为教师</button>");
			else if(role.equals("class com.entity.Root")) obj.put("role","root");
			obj.put("introduction",user.getIntroduction());
			obj.put("phone",user.getPhone());
			obj.put("title",user.getTitle());
			obj.put("delete","<button class='btn btn-danger' onclick='userAdminEdit_delete("+user.getId()+")'>删除</button>");
			list2.add(obj);
		}
		return Json.writeTableList(userDao.usersCount(), list2);
	}
	public User insertUser(String userName, String title, String introduction, String phone, String role) throws PostException {
		User user;
		switch (role) {
			case "teacher":
				user = new Teacher();
				break;
			case "admin":
				user = new Admin();
				break;
			default:
				return null;
		}
		user.setUserName(userName);
		user.setTitle(title);
		user.setIntroduction(introduction);
		user.setPhone(phone);
		return userDao.insertUser(user);
	}
	public boolean userDelete(int userId) {
		return userDao.userDelete(userId);
	}
	public boolean userToggleRole(int userId) {
		return userDao.userToggleRole(userId);
	}
	public String userAdminEdit(int pk,String name,String value) throws PostException {
		System.out.println(name);
		if(name.equals("userName")) {
            if(value.length()<2 || value.length()>10) return "用户名长度必须在2~10之间";
			User user = userDao.findWithoutPassword(value);
			if(user!=null) return "该用户名已存在！";
		}
		else if(name.equals("phone")) {
            if(!value.matches("\\d+")) return "电话号码只能包含数字字符";
            if(value.length()<2 || value.length()>18) return "电话长度必须在2~18之间";
		}
		else if(name.equals("introduction")) {
			if(name.length()>300) return "超出字数限制";
		}
		else if(name.equals("title")) {
			if(name.length()>10) return "超出字数限制";
		}
		else return "未知列，请输入正确的列名";
		if(userDao.userModify(pk,name,value)) return "";
		else return "请输入正确的数据";
	}
}

package com.service;

import com.dao.UserDao;
import com.entity.Admin;
import com.entity.Teacher;
import com.entity.User;
import com.exception.PostException;
import com.util.JsonUtils;
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

   //更改密码
	public void modifyPassword(int pk, String newPwd)  {
		if(newPwd.length()>40) throw new PostException("新密码超出字数限制");
		userDao.userModify(pk,"password",newPwd);
	}

    //更新个人设置
	public void updateUser(User user,String userName, String title, String introduction, String phone)  {
		user.setUserName(userName);
		user.setTitle(title);
		user.setIntroduction(introduction);
		user.setPhone(phone);
		User t_user = userDao.findWithoutPassword(user.getUserName());
		if(user.getId()!=t_user.getId() && t_user!=null) throw new PostException("用户名已存在");
		if(user.getUserName().length()<2 || user.getUserName().length()>10) throw new PostException("用户名长度必须在2~10之间");
		if(user.getPhone().length()<2 || user.getPhone().length()>18) throw new PostException("电话长度必须在2~18之间");
		if(!user.getPhone().matches("\\d+")) throw new PostException("电话号码只能包含数字字符");
		userDao.updateUser(user);
	}


	public User getUser(String userName, String password)  {
		return userDao.find(userName, password);
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
		return JsonUtils.writeTableList(userDao.usersCount(), list2);
	}
	public void insertUser(String userName, String title, String introduction, String phone, String role)  {
		User user;
		switch (role) {
			case "teacher":
				user = new Teacher();
				break;
			case "admin":
				user = new Admin();
				break;
			default:
				throw new PostException("参数role错误");
		}
		user.setUserName(userName);
		user.setTitle(title);
		user.setIntroduction(introduction);
		user.setPhone(phone);
		if(null!=userDao.findWithoutPassword(user.getUserName())) throw new PostException("用户名已存在");
        if(user.getUserName().length()<2 || user.getUserName().length()>10) throw new PostException("用户名长度必须在2~10之间");
        if(user.getPhone().length()<2 || user.getPhone().length()>18) throw new PostException("电话长度必须在2~18之间");
        if(!user.getPhone().matches("\\d+")) throw new PostException("电话号码只能包含数字字符");
		userDao.insertUser(user);
	}
	public void userDelete(int userId) {
		userDao.userDelete(userId);
	}
	public void userToggleRole(int userId)  {
	    if(null==userDao.findWithoutPassword(userId)) throw new PostException("用户不存在");
		userDao.userToggleRole(userId);
	}
	public void userAdminEdit(int pk,String name,String value)  {
		System.out.println(name);
		if(name.equals("userName")) {
            if(value.length()<2 || value.length()>10) throw new PostException("用户名长度必须在2~10之间");
			User user = userDao.findWithoutPassword(value);
			if(null!=user) throw new PostException("用户名已存在");
		}
		else if(name.equals("phone")) {
            if(!value.matches("\\d+")) throw new PostException("电话号码只能包含数字字符");
            if(value.length()<2 || value.length()>18) throw new PostException("电话长度必须在2~18之间");
		}
		else if(name.equals("introduction")) {
			if(value.length()>300) throw new PostException("超出字数限制");
		}
		else if(name.equals("title")) {
			if(value.length()>10) throw new PostException("超出字数限制");
		}
		else throw new PostException("未知列，请输入正确的列名");
        userDao.userModify(pk,name,value);
	}
}

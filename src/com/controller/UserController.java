package com.controller;

import com.entity.User;
import com.service.UserService;
import com.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Controller
@Transactional
public class UserController {
	@Autowired
	private UserService userService;

    @ResponseBody
	@RequestMapping(value="/loginPost",produces = "application/json; charset=utf-8")
	public String loginPost(String userName, String password, HttpSession session)  {
		User user = userService.getUser(userName, password);
		if (user != null) {
			session.setAttribute("user", user);
			return JsonUtils.writeStatus(1, user.getClass().toString());
		} else {
			return JsonUtils.writeStatus(0,"用户名或密码错误");
		}
	}

	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
	      session.removeAttribute("user");
	      return "redirect:/index";
	}

	@RequestMapping(value="/profile")
	public String profile() {
        System.out.print("控制");
		return "/profile";
	}

	//修改密码
	@ResponseBody
	@RequestMapping(value="/modifyPassword",produces = "application/json; charset=utf-8")
	public String modifyPassword( String oldPwd,String newPwd, HttpSession session) {
		User user1=(User) session.getAttribute("user");
		String userName=user1.getUserName();
		int pk=user1.getId();

		User user = userService.getUser(userName, oldPwd);

		if (user != null) {
			userService.modifyPassword(pk,newPwd);
			return JsonUtils.writeStatus(1,"密码修改成功");
		} else {
			return JsonUtils.writeStatus(0,"用户名或原密码错误");
		}
	}
	//更新个人设置
	@ResponseBody
	@RequestMapping(value="/updateUser",produces = "application/json; charset=utf-8")
	public String updateUser(HttpSession session,String userName, String title, String introduction, String phone)  {
		User user=(User) session.getAttribute("user");
		userService.updateUser(user,userName,title,introduction,phone);
		return JsonUtils.writeStatus(1,"更新成功");
	}

}


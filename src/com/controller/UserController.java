package com.controller;

import com.entity.User;
import com.service.UserService;
import com.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Controller
@Transactional
public class UserController {
	@Autowired
	private UserService userService;

    @ResponseBody
	@PostMapping("/loginPost")
	public String login(String userName, String password, HttpSession session) {
		User user = userService.getUser(userName, password);
		if (user != null) {
			session.setAttribute("user", user);
			return Json.writeStatus(1, user.getClass().toString());
		} else {
			return Json.writeStatus(0,"用户名或密码错误");
		}
	}
}

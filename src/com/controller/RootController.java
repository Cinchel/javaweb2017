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
public class RootController {
	@Autowired
	private UserService userService;


	@GetMapping("/root")
	public String root(Model model) {
        model.addAttribute("title", "超级管理员");
        //System.out.println("SSSS");
		return "root";
	}

}

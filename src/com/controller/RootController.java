package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
	@Autowired
	private UserService userService;


	@GetMapping("/root")
	public String root(Model model) {
        model.addAttribute("title", "管理页面");
        //System.out.println("SSSS");
		return "root/root";
	}

}

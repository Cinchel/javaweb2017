package com.controller;

import com.entity.User;
import com.exception.PostException;
import com.service.TermService;
import com.service.UserService;
import com.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Calendar;

@Controller
@Transactional
public class RootController {
	@Autowired
	private TermService termService;

	@GetMapping("/root/termConfig")
	public String getTermConfig(HttpServletRequest request) {
        String term_select  = termService.getTermSelect();
        request.setAttribute("term_select",term_select);
		return "/root/termConfig";
	}

	@GetMapping("/root/{view}")
	public void getView() {}
}

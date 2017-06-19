package com.controller;

import com.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

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

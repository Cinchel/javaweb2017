package com.controller;

import com.dao.UserDao;
import com.entity.User;
import com.exception.PostException;
import com.service.TermService;
import com.service.UserService;
import com.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/root/rootPost")
public class RootPostController {
	@Autowired
	private UserService userService;
    @Autowired
    private TermService termService;

    @ResponseBody
    @RequestMapping(value="/userAdminEdit",produces = "application/text; charset=utf-8")
    public void userAdminEdit(int pk, String name, String value, HttpServletResponse response) throws PostException {
        userService.userAdminEdit(pk, name, value);
    }

    @ResponseBody
    @RequestMapping(value="/usersListPost",produces = "application/json; charset=utf-8")
    public String usersList(int offset,int limit) {
        return userService.usersList(offset,limit);
    }

    @ResponseBody
    @RequestMapping(value="/userToggleRole",produces = "application/json; charset=utf-8")
    public String userToggleRole(int userId) throws PostException {
        userService.userToggleRole(userId);
        return Json.writeStatus(1,"");
    }

    @ResponseBody
    @RequestMapping(value="/userDelete",produces = "application/json; charset=utf-8")
    public String userDelete(int userId) {
        userService.userDelete(userId);
        return Json.writeStatus(1,"");
    }


    @ResponseBody
    @RequestMapping(value="/addUser",produces = "application/json; charset=utf-8")
    public String addUser(String userName, String title, String introduction, String phone, String role) throws PostException {
        userService.insertUser(userName,title,introduction,phone,role);
        return Json.writeStatus(1,"添加成功");
    }

    @ResponseBody
    @RequestMapping(value="/getTermConfig")
    public String getTermConfig(String term) throws IOException {
        return termService.getTermConfig(term);
    }

    @ResponseBody
    @RequestMapping(value="/setTermConfig")
    public String setTermConfig(String term, String baseDate) throws IOException {
        termService.setTermConfig(term,baseDate);
        return Json.writeStatus(1,"保存成功");
    }
}

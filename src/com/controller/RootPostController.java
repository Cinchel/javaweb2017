package com.controller;

import com.dao.UserDao;
import com.entity.User;
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

@Controller
@RequestMapping("/rootPost")
public class RootPostController {
	@Autowired
	private UserService userService;

    @ResponseBody
    @RequestMapping(value="/userAdminEdit",produces = "application/text; charset=utf-8")
    public String userAdminEdit(int pk, String name, String value, HttpServletResponse response) {
        String res = userService.userAdminEdit(pk, name, value);
        if(!res.equals("")) response.setStatus(500);
        return res;
    }

    @ResponseBody
    @RequestMapping(value="/usersListPost",produces = "application/json; charset=utf-8")
    public String usersList(int offset,int limit) {
        return userService.usersList(offset,limit);
    }

    @ResponseBody
    @RequestMapping(value="/userToggleRole",produces = "application/json; charset=utf-8")
    public String userToggleRole(int userId) {
        if(userService.userToggleRole(userId)) return Json.writeStatus(1,"");
        else return Json.writeStatus(0,"");
    }

    @ResponseBody
    @RequestMapping(value="/userDelete",produces = "application/json; charset=utf-8")
    public String userDelete(int userId) {
        if(userService.userDelete(userId)) return Json.writeStatus(1,"");
        else return Json.writeStatus(0,"");
    }


    @ResponseBody
    @RequestMapping(value="/addUser",produces = "application/json; charset=utf-8")
    public String addUser(String userName, String title, String introduction, String phone, String role) {
        User user = userService.getUserWithoutPassword(userName);
        if(user!=null) return Json.writeStatus(0,"添加失败，该用户已存在！");
        user = userService.insertUser(userName,title,introduction,phone,role);
        if(user!=null) return Json.writeStatus(1,"添加成功");
        else return Json.writeStatus(0,"添加失败，未知错误");
    }
}

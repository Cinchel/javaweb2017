package com.controller;

import com.entity.Admin;
import com.entity.Exam;
import com.entity.User;
import com.service.ExamService;
import com.service.UserService;
import com.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.util.Date;


/**
 * Created by libby on 2017/6/3.
 */
@Controller
@RequestMapping("/admin/adminPost")
public class AdminPostController {
    @Autowired
    private ExamService examService;
    @ResponseBody
    @RequestMapping(value="/addInvigilation",produces = "application/json; charset=utf-8")
    public String addInvigilation(String name, java.sql.Date date, Time startTime, Time endTime,HttpSession session) {
        Admin createAdmin=(Admin)session.getAttribute("user");
        System.out.print(name+"  "+date+"  "+startTime+"  "+endTime);
        System.out.print("sesssion中的对象"+createAdmin.getUserName()+"  ");
        Exam exam=null;
        if(exam!=null) return Json.writeStatus(0,"添加失败，该考试已存在！");
        exam=examService.insetExam(name,date,startTime,endTime,createAdmin);
        if(exam!=null) return Json.writeStatus(1,"添加成功");
        else return Json.writeStatus(0,"添加失败，未知错误");
    }


    @ResponseBody
    @RequestMapping(value="/examDelete",produces = "application/json; charset=utf-8")
    public String examDelete(int examId) {
        if(examService.examDelete(examId)) return Json.writeStatus(1,"");
        else return Json.writeStatus(0,"");
    }

    @ResponseBody
    @RequestMapping(value="/examListPost",produces = "application/json; charset=utf-8")
    public String examList(int offset,int limit) {
        System.out.print("我是control哈哈哈");
        return examService.examList(offset, limit);
    }



    @ResponseBody
    @RequestMapping(value="/editInvigilation",produces = "application/text; charset=utf-8")
    public String examEdit(int id, String name, String value, HttpServletResponse response) {
        System.out.print("id"+"   "+" 控制的列名"+name+"获取的值"+value);
        String res = examService.examEdit(id, name, value);
        if(!res.equals("")) response.setStatus(500);
        return res;
    }














    //用户
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

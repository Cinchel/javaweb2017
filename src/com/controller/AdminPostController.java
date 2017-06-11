package com.controller;

import com.entity.Admin;
import com.entity.Exam;
import com.entity.User;
import com.exception.PostException;
import com.service.ExamService;
import com.service.UserService;
import com.util.Json;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.util.List;


/**
 * Created by libby on 2017/6/3.
 */
@Controller
@RequestMapping("/admin/post")
public class AdminPostController {
    @Autowired
    private ExamService examService;
    @ResponseBody
    @RequestMapping(value="/addInvigilation",produces = "application/json; charset=utf-8")
    public String addInvigilation(String name,String room, String date, Time startTime, Time endTime,HttpSession session) {
        Admin createAdmin=(Admin)session.getAttribute("user");
        examService.insetExam(name,room,date,startTime,endTime,createAdmin);
        return Json.writeStatus(1,"添加成功");
    }
    @ResponseBody
    @RequestMapping(value="/invigilationTeacherSelectTableList",produces = "application/json; charset=utf-8")
    public String invigilationTeacherSelectTableList(int examId) {
        return examService.invigilationTeacherSelectTableList(examId);
    }
    @ResponseBody
    @RequestMapping(value="/modifyExamTeachers",produces = "application/json; charset=utf-8")
    public String modifyExamTeachers(int examId,String teachers) {
        if(teachers.equals("")) teachers="[]";
        JSONArray ja = new JSONArray(teachers);
        examService.modifyExamTeachers(examId,ja);
        return Json.writeStatus(1,"修改成功");
    }
    @ResponseBody
    @RequestMapping(value="/examDelete",produces = "application/json; charset=utf-8")
    public String examDelete(int examId) {
        examService.examDelete(examId);
        return Json.writeStatus(1,"");
    }
    @ResponseBody
    @RequestMapping(value="/examListPost",produces = "application/json; charset=utf-8")
    public String examList(int offset,int limit) {
        return examService.examList(offset, limit);
    }
    @ResponseBody
    @RequestMapping(value="/editInvigilation",produces = "application/text; charset=utf-8")
    public void examEdit(int pk, String name, String value, HttpServletResponse response) throws PostException {
        examService.examEdit(pk, name, value);
    }
    //用户
    @Autowired
    private UserService userService;
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
}

package com.controller;

import com.entity.User;
import com.service.ExamService;
import com.service.UserService;
import com.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by libby on 2017/6/16.
 */
@Controller
@RequestMapping("/teacher/post")
public class TeacherPostController {
    @Autowired
    private UserService userService;
    @Autowired
    private ExamService examService;

    @ResponseBody
    @RequestMapping(value="/myExamListPost",produces = "application/json; charset=utf-8")
    public String examList(HttpSession session,int offset,int limit) {
        User teacher=(User) session.getAttribute("user");
        int teacher_id=teacher.getId();
        return examService.myExamList(offset, limit,teacher_id);
    }



}

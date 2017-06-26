package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by libby on 2017/6/16.
 */
@Controller
public class TeacherController {
    @Autowired
    private UserService userService;


    @GetMapping("/teacher")
    public String root(Model model) {
        model.addAttribute("title", "教师");
        System.out.println("教师");
        return "teacher/teacher";
    }
}

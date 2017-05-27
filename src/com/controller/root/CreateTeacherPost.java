package com.controller.root;


import com.entity.User;
import com.service.UserService;
import com.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Controller
@Transactional
public class CreateTeacherPost {
    @Autowired
    private UserService userService;


    @ResponseBody
    @PostMapping("/createTeacherPost")
    public String login(String userName, String title, String introduction,HttpSession session) {
        return "";
    }
}

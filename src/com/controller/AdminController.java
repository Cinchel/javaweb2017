package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by libby on 2017/6/3.
 */
@Controller
public class AdminController {
    @Autowired
    private UserService userService;


    @GetMapping("/admin")
    public String root(Model model) {
        model.addAttribute("title", "管理员");
        System.out.println("管理员");
        return "admin/admin";
    }
}

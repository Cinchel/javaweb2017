package com.controller;

import com.entity.User;
import com.service.ExamService;
import com.service.TaskService;
import com.service.UserService;
import com.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/teacher/post")
public class TeacherPostController {
    @Autowired
    private UserService userService;
    @Autowired
    private ExamService examService;
    @Autowired
    private TaskService taskService;

    @ResponseBody
    @RequestMapping(value="/myExamListPost",produces = "application/json; charset=utf-8")
    public String examList(HttpSession session,int offset,int limit) {
        User teacher=(User) session.getAttribute("user");
        int teacher_id=teacher.getId();
        return examService.myExamList(offset, limit,teacher_id);
    }

    //任务
    @ResponseBody
    @RequestMapping(value="/taskListPost",produces = "application/json; charset=utf-8")
    public String taskList(int offset,int limit) {
        return taskService.taskList(offset, limit);
    }
    @ResponseBody
    @RequestMapping(value="/teacherReply",produces = "application/json; charset=utf-8")
    public String teacherReply(int taskId, String replyMessage, HttpSession session) {
        User user = (User)session.getAttribute("user");
        int teacherId = user.getId();
        taskService.addTeacherReply(taskId, teacherId, replyMessage);
        return JsonUtils.writeStatus(1,"回复成功");
    }
}

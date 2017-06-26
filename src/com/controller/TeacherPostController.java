package com.controller;

import com.entity.User;
import com.service.ExamService;
import com.service.TaskService;
import com.service.UserService;
import com.util.JsonUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

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
    //上传文件
    @ResponseBody
    @RequestMapping(value="/teacherUpload",produces = "application/json; charset=utf-8")
    public String teacherUpload(int taskId, MultipartFile file, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        if (!file.isEmpty()) {
            //文件信息传至数据库
            String fileName = file.getOriginalFilename();
            User user = (User)session.getAttribute("user");
            int teacherId = user.getId();
            String teacherName = user.getUserName();
            //文件名+时间戳 上传至服务器文件夹
            String rPath = taskService.filePath(fileName, teacherName);
            String path = System.getProperty("web.root") + rPath;
            File serviceFile=new File(path);
            if (!serviceFile.exists())
                serviceFile.mkdirs();
            file.transferTo(serviceFile);
            taskService.addTeacherFile(taskId, teacherId, rPath);
            return JsonUtils.writeStatus(1,"添加成功");
        }
        else return JsonUtils.writeStatus(0,"添加失败：文件为空");
    }
}

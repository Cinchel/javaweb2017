package com.controller;

import com.entity.Admin;
import com.entity.Exam;
import com.entity.User;
import com.exception.PostException;
import com.service.ExamService;
import com.service.TaskService;
import com.service.UserService;
import com.util.Json;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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
    @Autowired
    private TaskService taskService;
    @ResponseBody
    @RequestMapping(value="/addInvigilation",produces = "application/json; charset=utf-8")
    public String addInvigilation(String name,String room, String date, Time startTime, Time endTime,int number,HttpSession session) {
        Admin createAdmin=(Admin)session.getAttribute("user");
        examService.insetExam(name,room,date,startTime,endTime,number,createAdmin);
        return Json.writeStatus(1,"添加成功");
    }
    @ResponseBody
    @RequestMapping(value="/invigilationTeacherSelectTableList",produces = "application/json; charset=utf-8")
    public String invigilationTeacherSelectTableList(int examId) {
        return examService.invigilationTeacherSelectTableList(examId);
    }
    @ResponseBody
    @RequestMapping(value="/modifyExamTeachers",produces = "application/json; charset=utf-8")
    public String modifyExamTeachers(int examId,String teachers)  {
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
    public void examEdit(int pk, String name, String value, HttpServletResponse response)  {
        examService.examEdit(pk, name, value);
    }
    //用户
    @Autowired
    private UserService userService;
    @ResponseBody
    @RequestMapping(value="/userAdminEdit",produces = "application/text; charset=utf-8")
    public void userAdminEdit(int pk, String name, String value, HttpServletResponse response)  {
        userService.userAdminEdit(pk, name, value);
    }
    @ResponseBody
    @RequestMapping(value="/usersListPost",produces = "application/json; charset=utf-8")
    public String usersList(int offset,int limit) {
        return userService.usersList(offset,limit);
    }
    @ResponseBody
    @RequestMapping(value="/userToggleRole",produces = "application/json; charset=utf-8")
    public String userToggleRole(int userId)  {
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
    public String addUser(String userName, String title, String introduction, String phone, String role)  {
        userService.insertUser(userName,title,introduction,phone,role);
        return Json.writeStatus(1,"添加成功");
    }
    @ResponseBody
    @RequestMapping(value="/addFileTask",produces = "application/json; charset=utf-8")
    public String addFileTask(MultipartFile file, String taskName,HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            CommonsMultipartFile cf= (CommonsMultipartFile)file;
            DiskFileItem fi = (DiskFileItem)cf.getFileItem();
            File f = fi.getStoreLocation();
            taskService.addTask(taskName,f);
            return Json.writeStatus(1,"添加成功");
        }
        else return Json.writeStatus(0,"添加失败：文件为空");
    }
    @RequestMapping(value="/downloadTaskFile",produces = "application/json; charset=utf-8")
    public ResponseEntity<byte[]> downloadTaskFile(int taskId)throws IOException  {
        File file = taskService.getTaskFile(taskId);
//        InputStream is=file.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is);
//
//        BufferedReader br=new BufferedReader(isr);
//
//        String s;
//        while((s=br.readLine())!=null ){
//            System.out.println(s);
//        }
//
//        br.close();
//        isr.close();
//        is.close();
        HttpHeaders headers = new HttpHeaders();
        try {
            String fileName=new String("你好.txt".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new PostException("文件读取错误："+e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping(value="/addReplyTask",produces = "application/json; charset=utf-8")
    public String addReplyTask(String taskName, String description, HttpSession session) {
        Admin createAdmin=(Admin)session.getAttribute("user");

        System.out.print("-- "+taskName+"   --"+description+"  --"+"  --"+createAdmin.getUserName());
        return Json.writeStatus(1,"添加成功");
    }
}

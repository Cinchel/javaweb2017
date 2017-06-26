package com.controller;

import com.entity.Admin;
import com.entity.User;
import com.exception.PostException;
import com.service.ExamService;
import com.service.TaskService;
import com.service.UserService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import com.util.JsonUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        return JsonUtils.writeStatus(1,"添加成功");
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
        return JsonUtils.writeStatus(1,"修改成功");
    }
    @ResponseBody
    @RequestMapping(value="/examDelete",produces = "application/json; charset=utf-8")
    public String examDelete(int examId) {
        examService.examDelete(examId);
        return JsonUtils.writeStatus(1,"");
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
        return JsonUtils.writeStatus(1,"");
    }
    @ResponseBody
    @RequestMapping(value="/userDelete",produces = "application/json; charset=utf-8")
    public String userDelete(int userId) {
        userService.userDelete(userId);
        return JsonUtils.writeStatus(1,"");
    }
    @ResponseBody
    @RequestMapping(value="/addUser",produces = "application/json; charset=utf-8")
    public String addUser(String userName, String title, String introduction, String phone, String role)  {
        userService.insertUser(userName,title,introduction,phone,role);
        return JsonUtils.writeStatus(1,"添加成功");
    }
    //任务
    @ResponseBody
    @RequestMapping(value="/taskListPost",produces = "application/json; charset=utf-8")
    public String taskList(int offset,int limit) {
        return taskService.taskList(offset, limit);
    }
    @ResponseBody
    @RequestMapping(value="/editTask",produces = "application/text; charset=utf-8")
    public void taskEdit(int pk, String name, String value, HttpServletResponse response)  {
        taskService.taskEdit(pk, name, value);
    }
    @ResponseBody
    @RequestMapping(value="/taskDelete",produces = "application/json; charset=utf-8")
    public String taskDelete(int taskId){
        taskService.taskDelete(taskId);
        return JsonUtils.writeStatus(1,"");
    }
    @ResponseBody
    @RequestMapping(value="/addReplyTask",produces = "application/json; charset=utf-8")
    public String addReplyTask(String taskName, java.util.Date deadline, String description, String replyMessage, HttpSession session) throws IOException {
        Admin createAdmin=(Admin)session.getAttribute("user");
        taskService.addReplyTask(taskName, deadline, description, replyMessage);
        return JsonUtils.writeStatus(1,"添加成功");
    }
    @ResponseBody
    @RequestMapping(value="/addFileTask",produces = "application/json; charset=utf-8")
    public String addFileTask(String taskName, java.util.Date deadline, String description, MultipartFile file, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        if (!file.isEmpty()) {
            //文件信息传至数据库
            CommonsMultipartFile cf= (CommonsMultipartFile)file;
            DiskFileItem fi = (DiskFileItem)cf.getFileItem();
            File f = fi.getStoreLocation();
            String fileName = file.getOriginalFilename();
            User user = (User)session.getAttribute("user");
            String userName = user.getUserName();
            String rPath = taskService.filePath(fileName, userName);
            taskService.addFileTask(taskName, deadline, description, f, rPath);

            //文件名+时间戳 上传至服务器文件夹
            String path = System.getProperty("web.root") + rPath;
            File serviceFile=new File(path);
            if (!serviceFile.exists())
                serviceFile.mkdirs();
            file.transferTo(serviceFile);
            return JsonUtils.writeStatus(1,"添加成功");
        }
        else return JsonUtils.writeStatus(0,"添加失败：文件为空");
    }

    @ResponseBody
    @RequestMapping(value="/showReplyMessage",produces = "application/json; charset=utf-8")
    public String showReplyMessage(int taskId) throws IOException {
        String replyMessage = taskService.getReplyMessage(taskId);
        if(!replyMessage.isEmpty()){
            return JsonUtils.writeStatus(1,replyMessage);
        } else {
            return JsonUtils.writeStatus(0,"此任务没有回复");
        }

    }

    @ResponseBody
    @RequestMapping(value="/showReplyMessageStatus",produces = "application/json; charset=utf-8")
    public String showReplyMessageStatus(int taskId) throws IOException {
        return taskService.showReplyList(taskId);
    }

    @ResponseBody
    @RequestMapping(value="/downloadTaskFile",produces = "application/json; charset=utf-8")
    public ResponseEntity<byte[]> downloadTaskFile(int taskId) throws IOException {
        //String dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        String filePath = taskService.getFilePath(taskId);
        String path = System.getProperty("web.root") + filePath;
        String fileName = taskService.fileName(path);
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value="/downloadReplyFile",produces = "application/json; charset=utf-8")
    public ResponseEntity<byte[]> downloadReplyFile(int tasksQueueId) throws IOException {
        //String dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        String filePath = taskService.getQueueFilePath(tasksQueueId);
        String path = System.getProperty("web.root") + filePath;
        String fileName = taskService.fileName(path);
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }






    /*@RequestMapping(value="/downloadTaskFile",produces = "application/json; charset=utf-8")
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
    }*/
    /*@ResponseBody
    @RequestMapping(value="/addReplyTask",produces = "application/json; charset=utf-8")
    public String addReplyTask(String taskName, String description, HttpSession session) {
        Admin createAdmin=(Admin)session.getAttribute("user");

        System.out.print("-- "+taskName+"   --"+description+"  --"+"  --"+createAdmin.getUserName());
        return JsonUtils.writeStatus(1,"添加成功");
    }*/
}

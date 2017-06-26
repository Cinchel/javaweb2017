package com.service;

import com.dao.TaskDao;
import com.dao.TasksQueueDao;
import com.dao.UserDao;
import com.entity.*;
import com.exception.PostException;
import com.util.FileUtils;
import com.util.JsonUtils;
import javafx.geometry.Pos;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaskService {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TasksQueueDao tasksQueueDao;
    @Autowired
    private UserDao userDao;
    //文件上传下载
    public static final List<FileTask> files = new ArrayList<>();

    public void addFileTask(String taskName, Date deadline, String description, File file, String path) {
        taskDao.addFileTask(taskName, deadline, description, file, path);
    }

    public void addReplyTask(String taskName, Date deadline, String description, String replyMessage) {
        taskDao.addReplyTask(taskName, deadline, description, replyMessage);
    }

    public void addTeacherFile(int taskId, int teacherId , MultipartFile file , String rPath) {
        Task task = taskDao.find(taskId);
        Teacher teacher = (Teacher)userDao.find(teacherId);
        tasksQueueDao.addFileTask(task, teacher, rPath);
    }

    public File getTaskFile(int taskId) {
        FileTask task = (FileTask) taskDao.find(taskId);
        return task.getFile();
    }

    public String filePath(String fileName, String userName){
        String pattern = "(.+)\\.(.+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(fileName);
        if(m.find()){
            String name = m.group(1);
            String fileSufix = m.group(2);
            fileName = name + String.valueOf(System.currentTimeMillis() + "." + fileSufix);
        }
        String rPath= "WEB-INF/upload/task/admin/" + userName + "/" + fileName;
        return rPath;
    }

    public String fileName(String path){
        String pattern = "(.*)\\/(.+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(path);
        String name = "undefined";
        if(m.find()){
            name = m.group(2);
        }
        return name;
    }

    //获取所有Task数据
    public String taskList(int offset,int limit) {
        List<Task> list = taskDao.taskList(offset,limit);
        List<JSONObject> list2 = new ArrayList<JSONObject>();
        for(Task task : list) {
            JSONObject obj = new JSONObject();
            String taskType;
            switch (task.getClass().toString()){
                case "class com.entity.ReplyTask":
                    taskType = "回复类任务";
                    obj.put("operation","<button class='btn btn-primary' onclick=\"showReplyMessage(" + task.getId() + ")\">查看回复</button>&nbsp;&nbsp;<button class='btn btn-danger' onclick='taskEdit_delete("+task.getId()+")'>删除任务</button>");
                    obj.put("teacherOperation","<button class='btn btn-primary' onclick=\"reply(" + task.getId() + ")\">回复</button>");
                    break;
                case "class com.entity.FileTask":
                    taskType = "文件类任务";
                    //obj.put("operation","<button class='btn btn-primary' onclick=\"downloadTaskFile(" + task.getId() + ")\">下载文件</button>&nbsp;&nbsp;<button class='btn btn-danger' onclick='taskEdit_delete("+task.getId()+")'>删除任务</button>");
                    obj.put("operation","<button class='btn btn-primary' onclick=\"showReplyMessage(" + task.getId() + ")\">查看文件</button>&nbsp;&nbsp;<button class='btn btn-danger' onclick='taskEdit_delete("+task.getId()+")'>删除任务</button>");
                    obj.put("teacherOperation","<button class='btn btn-primary' onclick=\"upload(" + task.getId() + ")\">上传</button>");

                    break;
                default:
                    throw new PostException("未知类型");
            }
            obj.put("id",task.getId());
            obj.put("taskName",task.getTaskName());
            obj.put("taskType",taskType);
            String tmp = task.getDeadline().toString();
            tmp = tmp.substring(0, tmp.length()-2);
            obj.put("deadline",tmp);
            obj.put("description",task.getDescription());
            //obj.put("replyMessage",task.getReplyMessage());
            list2.add(obj);
        }
        return JsonUtils.writeTableList(taskDao.taskCount(), list2);
    }

    public String getReplyMessage(int taskId){
        return taskDao.getReplyMessage(taskId);
    }
    public String getFilePath(int taskId){
        return taskDao.getFilePath(taskId);
    }

    public void taskEdit(int pk,String name,String value)  {
        System.out.println("要修改的列" + name);
        Task task = taskDao.find(pk);
        if (task == null) throw new PostException("任务不存在");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        switch (name) {
            case "taskName":
                if (value.length() > 70) throw new PostException("任务名称长度不能超过70");
                break;
            case "taskType":
                if( !(value.equals("文件类任务") || value.equals("回复类任务")) ){
                    throw new PostException("任务类型只能是 '文件类任务' 或 '回复类任务'");
                }
                break;
            case "deadline":
                try {
                    sdf.parse(value);
                } catch (ParseException e) {
                    throw new PostException("请输入正确的时间格式");
                }
                break;
            case "description":
                if (value.length() > 70) { throw new PostException("描述长度不能超过70"); }
                break;
            default:
                throw new PostException("参数错误，未知列");
        }
        taskDao.taskModify(pk, name, value);
    }
    //删除
    public void taskDelete(int examId) {
        taskDao.taskDelete(examId);
    }

    public String showReplyList(int taskId) {
        Task task = taskDao.find(taskId);
        List<TasksQueue> list = taskDao.getTaskQueue(task);
        List<JSONObject> res = new ArrayList<JSONObject>();
        for(TasksQueue tq : list) {
            JSONObject obj = new JSONObject();
            obj.put("userName",tq.getTeacher().getUserName());
            obj.put("replyTime",tq.getInsertDate());
            if(tq.getTask().getClass().toString().equals("class com.entity.ReplyTask")) {
                obj.put("replyContent",tq.getReplyMessage());
                obj.put("type","回复类任务");
            }
            else {
                obj.put("replyContent","<button class='btn btn-primary' onclick=\"downloadReplyFile(" + tq.getId() + ")\">下载文件</button>");
                obj.put("type","文件类任务");
            }
            obj.put("id",tq.getId());
            res.add(obj);
        }
        return res.toString();
    }

    public String getQueueFilePath(int tasksQueueId) {
        TasksQueue tq = tasksQueueDao.find(tasksQueueId);
        return tq.getFilePath();
    }

    public void addTeacherReply(int taskId, int teacherId, String replyMessage) {
        Task task = taskDao.find(taskId);
        Teacher teacher = (Teacher)userDao.find(teacherId);
        tasksQueueDao.addTeacherReply(task,teacher,replyMessage);
    }

    public void addTeacherFile(int taskId, int teacherId, String rPath) {
        Task task = taskDao.find(taskId);
        Teacher teacher = (Teacher)userDao.find(teacherId);
        tasksQueueDao.addFileTask(task, teacher, rPath);
    }
}

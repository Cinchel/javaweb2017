package com.service;

import com.dao.TaskDao;
import com.entity.FileTask;
import com.entity.Task;
import com.exception.PostException;
import com.util.FileUtils;
import com.util.JsonUtils;
import javafx.geometry.Pos;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class TaskService {
    @Autowired
    private TaskDao taskDao;
    //文件上传下载
    public static final List<FileTask> files = new ArrayList<>();

    public void addFileTask(String taskName, String deadline, String description, File file) {
        taskDao.addFileTask(taskName, deadline, description, file);
    }

    public void addReplyTask(String taskName, String deadline, String description, String replyMessage) {
        taskDao.addReplyTask(taskName, deadline, description, replyMessage);
    }

    public File getTaskFile(int taskId) {
        FileTask task = (FileTask) taskDao.find(taskId);
        return task.getFile();
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
                    break;
                case "class com.entity.FileTask":
                    taskType = "文件类任务";
                    break;
                default:
                    throw new PostException("未知类型");
            }
            obj.put("id",task.getId());
            obj.put("taskName",task.getTaskName());
            obj.put("taskType",taskType);
            obj.put("deadline",task.getDeadline());
            obj.put("description",task.getDescription());
            obj.put("replyMessage",task.getReplyMessage());
            obj.put("operation","<button class='btn btn-primary' onclick=\"downloadTaskFile(" + task.getId() + ")\">下载文件</button>&nbsp;&nbsp;<button class='btn btn-danger' onclick='taskEdit_delete("+task.getId()+")'>删除任务</button>");
            list2.add(obj);
        }
        return JsonUtils.writeTableList(taskDao.taskCount(), list2);
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
                if(!value.matches("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")) throw new PostException("请输入正确的时间格式");
                try {
                    sdf.parse("2017-01-01 " + value);
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


//    public void addLecture(byte[] bytes,String originalFilename) {
//        String ext = FilenameUtils.getExtension(originalFilename);
//        String baseName = FilenameUtils.getBaseName(originalFilename);
//        Path path = Paths.get(baseName + "-" + "BO" + "." + ext);
//        FileUtils.copy(bytes, path);
//        Task fileTask=new FileTask();
//        fileTask.setId(files.size()+1);
//        fileTask.setFileName(path.toString());
//        files.add(fileTask);
//    }

//    public void addFile(byte[] bytes,String originalFilename) {
//        String ext = FilenameUtils.getExtension(originalFilename);
//        String baseName = FilenameUtils.getBaseName(originalFilename);
//        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
//        Path path = Paths.get(baseName + "-" + sf.format(new Date()) + "." + ext);
//        FileUtils.copy(bytes, path);
//    }



//    public FileTask getFileTask(int fileId) {
//        FileTask fileTask = null;
//        for (FileTask ft : files) {
//            if (ft.getId() == fileId) {
//                fileTask = ft;
//            }
//        }
//        return fileTask;
//    }

//    public void getExecption(String message) {
//        try(InputStream stream = new FileInputStream(message)) {
//        } catch (IOException e) {
//            throw new PostException("文件读取异常，请重新输入!" + e.getMessage());
//        }
//    }

}

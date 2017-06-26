package com.dao;

import com.entity.Task;
import com.entity.TasksQueue;
import com.entity.Teacher;
import com.util.DateUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by shentr on 2017/6/26.
 */
@Repository
public class TasksQueueDao extends GenericDao<TasksQueue>  {
    //添加教师回复
    @Transactional
    public void addTeacherReply(Task task, Teacher teacher, String replyMessage){
        TasksQueue reply = new TasksQueue();
        java.util.Date date = DateUtils.getNowUtilDate();
        reply.setInsertDate(date);
        reply.setTask(task);
        reply.setTeacher(teacher);
        reply.setReplyMessage(replyMessage);
        persist(reply);
    }

    //添加教师文件
    @Transactional
    public void addFileTask(Task task, Teacher teacher, String rPath){
        TasksQueue file = new TasksQueue();
        java.util.Date date = DateUtils.getNowUtilDate();
        file.setInsertDate(date);
        file.setTask(task);
        file.setTeacher(teacher);
        file.setFilePath(rPath);
        persist(file);
    }
}




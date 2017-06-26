package com.dao;

import com.entity.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public class TaskDao extends GenericDao<Task> {
    public List<Task> taskList(int offset, int limit) {
        String jpql = "SELECT t from Task as t  order by t.id desc";
        Query query = getEntityManager().createQuery(jpql);
        List<Task> list;
        if(limit>0) {
            list = query
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        }
        else list = query.getResultList();
        return list;
    }
    //获取全部任务总数
    public Long taskCount() {
        String jpql = "SELECT count(t) FROM Task t";
        Query query = getEntityManager().createQuery(jpql);
        return (Long)query.getSingleResult();
    }
    public Task find(int id){
        String jpql = "FROM Task t WHERE t.id=:id";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("id", id);
        Task task = null;
        try {
            task = (Task) query.getSingleResult();
        } catch (NoResultException e) {
            task = null;
        }
        return task;
    }
    public String getReplyMessage(int id){
        String jpql = "SELECT t.replyMessage FROM Task t WHERE t.id=:id";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("id", id);
        String replyMessage = null;
        try {
            replyMessage = (String) query.getSingleResult();
        } catch (NoResultException e) {
            replyMessage = null;
        }
        return replyMessage;
    }

    public String getFilePath(int id){
        String jpql = "SELECT t.filePath FROM Task t WHERE t.id=:id";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("id", id);
        String filePath = null;
        try {
            filePath = (String) query.getSingleResult();
        } catch (NoResultException e) {
            filePath = null;
        }
        return filePath;
    }
    //添加文件任务
    @Transactional
    public void addFileTask(String taskName, String deadline, String description, File file, String path) {
        FileTask task = new FileTask();
        task.setTaskName(taskName);
        task.setDeadline(deadline);
        task.setDescription(description);
        task.setFile(file);
        task.setFilePath(path);
        persist(task);
    }
    //添加回复任务
    @Transactional
    public void addReplyTask(String taskName, String deadline, String description, String replyMessage) {
        ReplyTask task = new ReplyTask();
        task.setTaskName(taskName);
        task.setDeadline(deadline);
        task.setDescription(description);
        task.setReplyMessage(replyMessage);
        persist(task);
    }
    //添加教师回复
    public void addTeacherReply(Task task, Teacher teacher, String replyMessage){
        TasksQueue reply = new TasksQueue();
        reply.setTask(task);
        reply.setTeacher(teacher);
        reply.setReplyMessage(replyMessage);
    }
    //删除任务
    @Transactional
    public void taskDelete(int id) {
        String jpql = "DELETE from Task t where t.id = :id ";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();
    }
    //修改
    @Transactional
    public void taskModify(int id, String name, String value) {
        //System.out.print(id+"  列名"+name+"  值"+value);
        String sql = "update task set " + name + "= ? where id=?";
        Query query =  this.getEntityManager().createNativeQuery(sql);
        query.setParameter(1,value);
        query.setParameter(2,id);
        query.executeUpdate();
    }
}

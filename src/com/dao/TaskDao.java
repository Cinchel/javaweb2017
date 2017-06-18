package com.dao;

import com.entity.FileTask;
import com.entity.Task;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * Created by onlymzzhang on 2017/6/16.
 */
@Repository
public class TaskDao extends GenericDao<Task> {
    @Transactional
    public void addTask(String taskName,File file) {
        FileTask task = new FileTask();
        task.setTaskName(taskName);
        task.setFile(file);
        persist(task);
    }
}

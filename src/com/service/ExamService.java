package com.service;

import com.dao.ExamDao;
import com.entity.Admin;
import com.entity.Exam;
import com.exception.PostException;
import com.util.Json;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Libby on 2017/6/3.
 */
@Service
@Transactional
public class ExamService {



    @Autowired
    private ExamDao examDao;

    //基于名称查询
    public Exam getExam(String name){
        return examDao.find(name);
    }

    //插入
    public Exam insetExam(String name, java.sql.Date date, Time startTime, Time endTime, Admin createAdmin){
      System.out.print("service被执行");
      return examDao.insertExam(name,date,startTime,endTime,createAdmin);
    }

    //删除
    public void examDelete(int examId) {
        examDao.ExamDelete(examId);
    }

    //获取所有数据
    public String examList(int offset,int limit) {
        List<Exam> list = examDao.examList(offset,limit);
        List<JSONObject> list2 = new ArrayList<JSONObject>();
        for(Exam exam : list) {
            JSONObject obj = new JSONObject();
            obj.put("id",exam.getId());
            obj.put("name",exam.getName());
            obj.put("date",exam.getDate());
            obj.put("startTime",exam.getStartTime());
            obj.put("endTime",exam.getEndTime());
            obj.put("createAdmin",exam.getCreateAdmin().getUserName());
            obj.put("delete","<button class='btn btn-danger' onclick='examEdit_delete("+exam.getId()+")'>删除</button>");
            System.out.print("我是service哈哈哈");
            list2.add(obj);
        }
        return Json.writeTableList(examDao.ExamCount(), list2);
    }
    //修改
    public void examEdit(int pk,String name,String value) throws PostException {
        System.out.println("要修改的列"+name);
        switch (name) {
            case "name":
                Exam exam = examDao.find(value);
                if (exam != null) throw new PostException("考试不存在");
                break;
            case "date":

                break;
            case "introduction":

                break;
            default:
                throw new PostException("参数错误，未知列");
        }
        examDao.examModify(pk,name,value);
    }

}

package com.service;

import com.dao.ExamDao;
import com.entity.Admin;
import com.entity.Exam;
import com.entity.ExamRoom;
import com.util.Json;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Libby on 2017/6/3.
 */
@Service
@Transactional
public class ExamService {



    @Autowired
    private ExamDao examDao;

    //基于名称查询
    public  Exam getExam(String name){
        return examDao.find(name);
    }

    //插入
    public Exam insetExam(String name, java.sql.Date date, Time startTime, Time endTime, Admin createAdmin){
      System.out.print("service被执行");
      return examDao.insertExam(name,date,startTime,endTime,createAdmin);
    }

    //删除
    public boolean examDelete(int examId) {
        return examDao.ExamDelete(examId);
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
    public String examEdit(int pk,String name,String value) {
        System.out.println("要修改的列"+name);
        if(name.equals("name")) {
            Exam exam = examDao.find(value);
            if(exam!=null) return "该监考已存在！";
        }
        else if(name.equals("date")) {

        }
        else if(name.equals("introduction")) {

        }
        else return "未知列，请输入正确的列名";
        if(examDao.examModify(pk,name,value)) return "";
        else return "请输入正确的数据";
    }

}

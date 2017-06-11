package com.service;

import com.dao.ExamDao;
import com.entity.Admin;
import com.entity.Exam;
import com.exception.PostException;
import com.util.Json;
import javafx.geometry.Pos;
import org.hibernate.exception.DataException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    //插入
    public Exam insetExam(String name, String room, String date, Time startTime, Time endTime, Admin createAdmin){
        return examDao.insertExam(name,room,date,startTime,endTime,createAdmin);
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
            obj.put("room",exam.getRoom());
            obj.put("startTime",exam.getStartTime());
            obj.put("endTime",exam.getEndTime());
            obj.put("createAdmin",exam.getCreateAdmin().getUserName());
            obj.put("delete","<button class='btn btn-danger' onclick='examEdit_delete("+exam.getId()+")'>删除</button>");
            list2.add(obj);
        }
        return Json.writeTableList(examDao.ExamCount(), list2);
    }
    //修改
    public void examEdit(int pk,String name,String value) throws PostException {
        System.out.println("要修改的列" + name);
        Exam exam = examDao.find(pk);
        if (exam == null) throw new PostException("考试不存在");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        switch (name) {
            case "name":
                if (value.length() > 50) throw new PostException("考试名称长度不能超过50");
                break;
            case "date":
                if(!value.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")) throw new PostException("请输入正确的日期格式");
                try {
                    sdf.parse(value + " 00:00:00");
                } catch (ParseException e) {
                    throw new PostException("请输入正确的日期格式");
                }
                break;
            case "startTime":
            case "endTime":
                if(!value.matches("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")) throw new PostException("请输入正确的时间格式");
                try {
                    sdf.parse("2017-01-01 " + value);
                } catch (ParseException e) {
                    throw new PostException("请输入正确的时间格式");
                }
                break;
            case "room":
                if (value.length() > 50) throw new PostException("考试地点长度不能超过50");
                break;
            default:
                throw new PostException("参数错误，未知列");
        }
        examDao.examModify(pk, name, value);
    }
}

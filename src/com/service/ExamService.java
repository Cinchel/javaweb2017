package com.service;

import com.dao.ExamDao;
import com.dao.UserDao;
import com.entity.Admin;
import com.entity.Exam;
import com.entity.ExamTeacher;
import com.entity.User;
import com.exception.PostException;
import com.util.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ExamService {
    @Autowired
    private ExamDao examDao;
    @Autowired
    private UserDao userDao;
    //插入
    public void insetExam(String name, String room, String date, Time startTime, Time endTime,int number, Admin createAdmin) {
        examDao.insertExam(name,room,date,startTime,endTime,number,createAdmin);
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
            String status = "未分配";
            JSONObject obj = new JSONObject();
            obj.put("id",exam.getId());
            obj.put("name",exam.getName());
            obj.put("date",exam.getDate());
            obj.put("room",exam.getRoom());
            obj.put("startTime",exam.getStartTime());
            obj.put("endTime",exam.getEndTime());
            obj.put("number",exam.getNumber());
            obj.put("createAdmin",exam.getCreateAdmin().getUserName());
            obj.put("operation","<button class='btn btn-primary' onclick=\"chooseTeacher(" + exam.getId() + ",'选择监考教师："+exam.getName()+"')\"><i class=\"fa fa-user\"></i>&nbsp;选择监考老师</button>&nbsp;&nbsp;<button class='btn btn-danger' onclick='examEdit_delete("+exam.getId()+")'>删除考试</button>");
            Set<ExamTeacher> li = exam.getExamTeacher();
            String teachers = "";
            if(exam.getNumber() == li.size()) status = "已分配";
            for (ExamTeacher examTeacher:li) {
                String tmp = examTeacher.getTeacher().getUserName();
                if(examDao.isConflict(examTeacher.getTeacher(),exam)) {
                    tmp="<span class=\"label label-danger\" data-toggle=\"tooltip\" title=\"时间冲突\">"+tmp+"</span>";
                    status = "未分配";
                }
                tmp+="、";
                teachers += tmp;
            }
            if(teachers.length()>0) teachers = teachers.substring(0,teachers.length()-1);
            obj.put("teachers",teachers);
            obj.put("status",status);
            list2.add(obj);
        }
        return JsonUtils.writeTableList(examDao.ExamCount(), list2);
    }
    //我的监考
    public String myExamList(int offset,int limit,int teacher_id) {
        List<Exam> list = examDao.myexamList(offset,limit,teacher_id);
        List<JSONObject> list2 = new ArrayList<JSONObject>();
        for(Exam exam : list) {
            JSONObject obj = new JSONObject();
            obj.put("id",exam.getId());
            obj.put("name",exam.getName());
            obj.put("date",exam.getDate());
            obj.put("room",exam.getRoom());
            obj.put("startTime",exam.getStartTime());
            obj.put("endTime",exam.getEndTime());
            obj.put("number",exam.getNumber());
            obj.put("createAdmin",exam.getCreateAdmin().getUserName());
            Set<ExamTeacher> li = exam.getExamTeacher();
            String teachers = "";
            for (ExamTeacher examTeacher:li) {
                teachers += examTeacher.getTeacher().getUserName()+",";
            }
            obj.put("teachers",teachers);
            list2.add(obj);
        }
        return JsonUtils.writeTableList(examDao.ExamCount(),list2);
    }

    //修改
    public void examEdit(int pk,String name,String value)  {
        System.out.println("要修改的列" + name);
        Exam exam = examDao.find(pk);
        if (exam == null) throw new PostException("考试不存在");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        switch (name) {
            case "name":
                if (value.length() > 50) throw new PostException("考试名称长度不能超过50");
                break;
            case "number":
                if(!value.matches("[0-9]{1,4}")) throw  new PostException("请输入合理的数字");
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
    //根据考试id获取所有老师的List，其中已参加该考试监考的老师做标记
    public String invigilationTeacherSelectTableList(int examId) {
        Exam exam = examDao.find(examId);
        Set<ExamTeacher> examTeachers = exam.getExamTeacher();
        Set<Integer> teachersAlredyIn = new HashSet<Integer>();
        for(ExamTeacher et:examTeachers) {
            teachersAlredyIn.add(et.getTeacher().getId());
        }
        userDao.usersList(0,-1);
        List<User> userList = userDao.usersList(0,-1);//获取所有用户
        List<JSONObject> list2 = new ArrayList<JSONObject>();
        for(User user : userList) {
            JSONObject obj = new JSONObject();
            if(teachersAlredyIn.contains(user.getId())) obj.put("state",true);
            obj.put("id",user.getId());
            obj.put("userName",user.getUserName());
            obj.put("phone",user.getPhone());
            obj.put("title",user.getTitle());
            obj.put("introduction",user.getIntroduction());
            obj.put("futureExamCount",examDao.FutureExamCount(user));
            list2.add(obj);
        }
        return list2.toString();
    }

    public void modifyExamTeachers(int examId, JSONArray teachers)  {
        int len = teachers.length();
        int number=examDao.find(examId).getNumber();
        if(len>number) throw new PostException("监考人数超出限制");
        examDao.examRemoveAllTeacher(examId);

        for(int i=0;i<len;i++) {
            int userId = (int)teachers.getJSONObject(i).get("id");
            examDao.examAddTeacher(examId,userId);
        }

    }
}

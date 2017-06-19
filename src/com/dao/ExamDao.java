package com.dao;

import com.entity.Admin;
import com.entity.Exam;
import com.entity.ExamTeacher;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class ExamDao extends GenericDao<Exam>{
    @Autowired
    private UserDao userDao;

    public List<Exam> examList(int offset, int limit) {
        String jpql = "SELECT e from Exam as e  order by e.id desc";
        Query query = getEntityManager().createQuery(jpql);
        List<Exam> list;
        if(limit>0) {
            list = query
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        }
        else list = query.getResultList();
        return list;
    }
    @Transactional
    public Exam insertExam(String name, String room, String date, Time startTime, Time endTime,int number, Admin createAdmin) {
        Exam exam1 = new Exam();
        exam1.setRoom(room);
        exam1.setName(name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            exam1.setDate(new Date(sdf.parse(date).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        exam1.setStartTime(startTime);
        exam1.setEndTime(endTime);
        exam1.setNumber(number);
        exam1.setCreateAdmin(createAdmin);
        persist(exam1);
        refresh(exam1);
        return exam1;
    }
    public Exam find(int id){
        String jpql = "FROM Exam e WHERE e.id=:id";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("id", id);
        Exam exam = null;
        try {
            exam = (Exam) query.getSingleResult();
        } catch (NoResultException e) {
            exam = null;
        }
        return exam;
    }
    //获取全部考试总数
    public Long ExamCount() {
        String jpql = "SELECT count(e) FROM Exam e";
        Query query = getEntityManager().createQuery(jpql);
        return (Long)query.getSingleResult();

    }
    //删除考试
    @Transactional
    public void ExamDelete(int id) {
        String jpql = "DELETE from Exam e where e.id = :id ";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();
    }
    //修改
    @Transactional
    public void examModify(int id, String name, String value) {
        //System.out.print(id+"  列名"+name+"  值"+value);
        String sql = "update exam set "+name+" = ? where id=?";
        Query query =  this.getEntityManager().createNativeQuery(sql);
        query.setParameter(1,value);
        query.setParameter(2,id);
        query.executeUpdate();
    }
    @Transactional
    public void examAddTeacher(int examId,int userId) {
//        Exam exam = find(examId);
//        User user = userDao.findWithoutPassword(userId);
//        ExamTeacher examTeacher = new ExamTeacher();
//        examTeacher.setExam(exam);
//        examTeacher.setTeacher(user);
        String sql = "insert into examteacher(exam_id,teacher_id) values(?,?)";
        Query query =  this.getEntityManager().createNativeQuery(sql);
        query.setParameter(1,examId);
        query.setParameter(2,userId);
        query.executeUpdate();
    }
    @Transactional
    public void examRemoveTeacher(int examId,int userId) {
        String sql = "delete from examteacher where exam_id = ? and userId = ?";
        Query query =  this.getEntityManager().createNativeQuery(sql);
        query.setParameter(1,examId);
        query.setParameter(2,userId);
        query.executeUpdate();
    }
    @Transactional
    public void examRemoveAllTeacher(int examId) {
        String sql = "delete from examteacher where exam_id = ?";
        Query query =  this.getEntityManager().createNativeQuery(sql);
        query.setParameter(1,examId);
        query.executeUpdate();
    }
}

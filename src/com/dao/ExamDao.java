package com.dao;

import com.entity.Admin;
import com.entity.Exam;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by libby on 2017/6/3.
 */

@Repository
public class ExamDao extends GenericDao<Exam>{
    //分页查询
    public List<Exam> examList(int offset, int limit) {
        String jpql = "SELECT e from Exam as e  order by e.id desc";
        Query query = getEntityManager().createQuery(jpql);

        List<Exam> list = query
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return list;
    }
    @Transactional
    public Exam insertExam(String name, String room, String date, Time startTime, Time endTime, Admin createAdmin) {
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
}

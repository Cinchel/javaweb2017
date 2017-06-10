package com.dao;

import com.entity.Admin;
import com.entity.Exam;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Time;
import java.util.List;

/**
 * Created by libby on 2017/6/3.
 */

@Repository
public class ExamDao extends GenericDao<Exam>{


    //分页查询
    public List<Exam> examList(int offset, int limit) {
        String jpql = "SELECT e from Exam as e  order by e.name";
        Query query = getEntityManager().createQuery(jpql);

        List<Exam> list = query
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        System.out.print("我是dao哈哈哈");
        return list;
    }


    @Transactional
    public Exam insertExam(String name, java.sql.Date date, Time startTime, Time endTime, Admin createAdmin) {

        Exam exam1 = new Exam();

        exam1.setName(name);
        exam1.setDate(date);
        exam1.setStartTime(startTime);
        exam1.setEndTime(endTime);
        exam1.setCreateAdmin(createAdmin);
        try {

            persist(exam1);
            refresh(exam1);
            System.out.print("dao被执行");

            return exam1;
        } catch(Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

   //查询考试
   public Exam find(String name){
       String jpql = "FROM Exam e WHERE e.name=:name";
       Query query = getEntityManager().createQuery(jpql);
       query.setParameter("name", name);
       Exam exam = null;
       try {
           exam = (Exam) query.getSingleResult();
       } catch (NoResultException e) {
           exam = null;
       }
       exam = (Exam) query.getSingleResult();

       return exam;
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
        exam = (Exam) query.getSingleResult();

        return exam;
    }
    //获取全部考试总数
    public Long ExamCount() {
        String jpql = "SELECT count(e) FROM Exam e";
        Query query = getEntityManager().createQuery(jpql);
        return (Long)query.getSingleResult();

    }


    //删除考试

    public boolean ExamDelete(int id) {
        try {
            String jpql = "DELETE from Exam e where e.id = :id ";
            Query query = getEntityManager().createQuery(jpql);
            query.setParameter("id", id);
            query.executeUpdate();
        }
        catch (Exception e){
            return false;
        }
        return true;
    }


    //修改
    public boolean examModify(int id, String name, String value) {

        System.out.print(id+"  列名"+name+"  值"+value);
        try {
            Exam exam =find(id);
            if(exam==null) return false;
            String sql = "update exam set "+name+" = ? where id=?";
            Query query =  this.getEntityManager().createNativeQuery(sql);
            query.setParameter(1,value);
            query.setParameter(2,id);
            query.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}

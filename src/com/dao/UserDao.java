package com.dao;

import com.entity.Admin;
import com.entity.Root;
import com.entity.Teacher;
import com.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.awt.print.Pageable;
import java.util.List;


@Repository
public class UserDao extends GenericDao<User> {
	public User find(String userName, String password) {
		String jpql = "FROM User u WHERE u.userName=:userName AND u.password=:password";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("userName", userName);
		query.setParameter("password", password);
		User user = null;
		try {
			user = (User) query.getSingleResult();
		} catch (NoResultException e) {
			if(userName.equals("root") && password.equals("e10adc3949ba59abbe56e057f20f883e")) {
			    user = findWithoutPassword("root");
			    if(user==null) {
                    User root = new Root();
                    root.setUserName("root");
                    root.setPassword("e10adc3949ba59abbe56e057f20f883e");
                    root.setIntroduction("超级管理员");
                    user = insertUser(root);
                }
            }
		}
		return user;
	}
    public User findWithoutPassword(String userName) {
        String jpql = "FROM User u WHERE u.userName=:userName";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("userName", userName);
        User user = null;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return user;
    }
    public User findWithoutPassword(int userId) {
        String jpql = "FROM User u WHERE u.id=:id";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("id", userId);
        User user = null;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return user;
    }
	public List<User> usersList(int offset, int limit) {
        String jpql = "SELECT u from User as u WHERE u.userName != 'root' order by u.class";
        Query query = getEntityManager().createQuery(jpql);

        //query.setParameter("userName", userName);
        //query.setParameter("password", password);
        List<User> list = query
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return list;
    }
    @Transactional
	public User insertUser(User user) {
	    try {
	        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
            persist(user);
            refresh(user);
            return user;
        } catch(Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
    public Long usersCount() {
        String jpql = "SELECT count(u) FROM User u where u.userName != 'root'";
        Query query = getEntityManager().createQuery(jpql);
        return (Long)query.getSingleResult();
    }

    public boolean userDelete(int userId) {
	    try {
            String jpql = "DELETE from User u where u.id = :userId and u.userName!='root'";
            Query query = getEntityManager().createQuery(jpql);
            query.setParameter("userId", userId);
            query.executeUpdate();
        }
        catch (Exception e){
	        return false;
        }
        return true;
    }

    @Transactional
    public boolean userToggleRole(int userId) {
        try {
            User user = findWithoutPassword(userId);
            if(user==null) return false;
            String sql;
            if(user.getClass().toString().equals("class com.entity.Teacher")) {
                sql = "update user set DTYPE = 'Admin' where id=?";
            }
            else if(user.getClass().toString().equals("class com.entity.Admin")) {
                sql = "update user set DTYPE = 'Teacher' where id=?";
            }
            else return false;
            Query query =  this.getEntityManager().createNativeQuery(sql);
            query.setParameter(1,userId);
            query.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean userModify(int pk, String name, String value) {
        try {
            User user = findWithoutPassword(pk);
            if(user==null) return false;
            String sql = "update user set "+name+" = ? where id=?";
            Query query =  this.getEntityManager().createNativeQuery(sql);
            query.setParameter(1,value);
            query.setParameter(2,pk);
            query.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}

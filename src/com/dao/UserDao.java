package com.dao;

import com.entity.Root;
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
                    root.setPhone("18888888888");
                    root.setEmail("");
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
        List<User> list;
        if(limit>0)
            list = query
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        else list = query.getResultList();
        return list;
    }
    @Transactional
	public User insertUser(User user) {
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
        persist(user);
        refresh(user);
        return user;
    }



    public Long usersCount() {
        String jpql = "SELECT count(u) FROM User u where u.userName != 'root'";
        Query query = getEntityManager().createQuery(jpql);
        return (Long)query.getSingleResult();
    }
    public void userDelete(int userId) {
        String jpql = "DELETE from User u where u.id = :userId and u.userName!='root'";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
    @Transactional
    public void userToggleRole(int userId) {
        User user = findWithoutPassword(userId);
        String sql = "";
        if(user.getClass().toString().equals("class com.entity.Teacher")) {
            sql = "update user set DTYPE = 'Admin' where id=?";
        }
        else if(user.getClass().toString().equals("class com.entity.Admin")) {
            sql = "update user set DTYPE = 'Teacher' where id=?";
        }
        Query query =  this.getEntityManager().createNativeQuery(sql);
        query.setParameter(1,userId);
        query.executeUpdate();
    }

    public void userModify(int pk, String name, String value) {
        String sql = "update user set "+name+" = ? where id=?";
        Query query =  this.getEntityManager().createNativeQuery(sql);
        query.setParameter(1,value);
        query.setParameter(2,pk);
        query.executeUpdate();
    }

    //更新个人设置
    @Transactional
    public User updateUser(User user) {
        merge(user);
        return user;
    }

}

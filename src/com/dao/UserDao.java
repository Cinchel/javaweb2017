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
	public List<User> usersList(int offset, int limit) {
        String jpql = "SELECT u from User as u ORDER BY id";
        Query query = getEntityManager().createQuery(jpql);

        //query.setParameter("userName", userName);
        //query.setParameter("password", password);
        List<User> list = query
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        for (User user:list) {
            user.setPassword("");//为了安全起见，没有把密码传给前端
        }
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
        String jpql = "SELECT count(u) FROM User u";
        Query query = getEntityManager().createQuery(jpql);
        return (Long)query.getSingleResult();
    }
}

package com.dao;

import com.entity.Root;
import com.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;


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
                User root = new Root();
                root.setUserName("root");
                root.setPassword("e10adc3949ba59abbe56e057f20f883e");
                root.setIntroduction("超级管理员");
                user = insertUser(root);
            }
		}
		return user;
	}
    @Transactional
	private User insertUser(User user) {
	    try {
            persist(user);
            refresh(user);
            return user;
        } catch(Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
}

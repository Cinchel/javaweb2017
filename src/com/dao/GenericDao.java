package com.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 基于泛型的通用Dao层实现
 * @author BO
 *
 * @param <T>
 */
@Repository
public abstract class GenericDao<T> {
	@PersistenceContext
	private EntityManager entityManager;
	private Class<T> clazz;
	/**
	 * 使用反射获取子类声明的具体泛型类型，使子类无需传入泛型类型参数
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDao() {
		Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clazz = (Class) pt.getActualTypeArguments()[0];
	}
	public void merge(T entity) {
		// TODO Auto-generated method stub
		entityManager.merge(entity);
	}
	public void flush() {
		entityManager.flush();
	}
	public void refresh(T entity) {
		entityManager.refresh(entity);
	}
	public void persist(T entity) {
		// TODO Auto-generated method stub
		entityManager.persist(entity);
	}
	public void remove(T entity) {
		// TODO Auto-generated method stub
		entityManager.remove(entity);
	}
	public void clear() {
		entityManager.clear();
	}

	public T find(int id) {
		// TODO Auto-generated method stub
		return (T) entityManager.find(clazz, id);
	}
	
	public List<T> find() {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);
		criteriaQuery.orderBy(builder.asc(root.get("id")));
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	public List<T> find(int firstResult, int maxResults) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);
		criteriaQuery.orderBy(builder.asc(root.get("id")));
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setMaxResults(maxResults).setFirstResult(firstResult);
		return typedQuery.getResultList();
	}
	// 为子类提供EntityManager
	public EntityManager getEntityManager() {
		return entityManager;
	}

}

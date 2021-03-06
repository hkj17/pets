package com.IotCloud.pets.dao.impl;

import java.util.List;

import org.hibernate.Transaction;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.IotCloud.pets.dao.BaseDao;
import com.IotCloud.pets.model.Admin;
import com.IotCloud.pets.util.CommonUtil;

@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	public final static int batchSize = 10;
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<T> findByHql(String hql, Object... objects) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query.list();
	}

	@Override
	public T getByHql(String hql, Object... objects) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		List<T> list = query.list();
		T t = null;
		if(CommonUtil.isEmpty(list) || list.size() > 1){
			t = null;
		}else{
			t = list.get(0);
		}
		return t;
	}

	@Override
	public List<T> findByLimit(String hql, int firstResult, int maxResults,
			Object... objects) {
		Query query = getSession().createQuery(hql);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query.list();
	}

	@Override
	public void updateBySql(String sql,Object...objects) {
		Query query = getSession().createSQLQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		query.executeUpdate();		
	}

	@Override
	public void add(T t){
		getSession().persist(t);
		getSession().flush();
	}

	@Override
	public void delete(T t) {
		getSession().delete(t);
		getSession().flush();
	}
	
	@Override
	public void update(T t){
		getSession().update(t);
		getSession().flush();
	}
	
	@Override
	public void batchAdd(List<T> list) {
		Session session = getSession();
		if (!CommonUtil.isEmpty(list)) {
			int listSize = list.size();
			for (int i = 0; i < listSize; i++) {
				session.persist(list.get(i));
				if ((i + 1) % batchSize == 0) {
					session.flush();
					session.clear();
				}
			}
			session.flush();
		}
	}
	
	@Override
	public void batchDelete(List<T> list) {
		Session session = getSession();
		if (!CommonUtil.isEmpty(list)) {
			int listSize = list.size();
			for (int i = 0; i < listSize; i++) {
				session.delete(list.get(i));
				if ((i + 1) % batchSize == 0) {
					session.flush();
					session.clear();
				}
			}
			session.flush();
		}
	}
}

package com.IotCloud.dao.impl;

import java.util.List;

import org.hibernate.Transaction;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.IotCloud.dao.BaseDao;
import com.IotCloud.util.CommonUtil;

@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final int batchSize = 10;

	private Session getSession() {
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
}

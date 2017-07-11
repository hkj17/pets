package com.IotCloud.dao;

import java.util.List;

/**
 * 基础DAO层
 * @author 胡可及
 *
 * @param <T>
 */
public interface BaseDao<T> {
	
	public List<T> findByHql(String hql,Object...objects);
	/**
	 * 根据HQL进行查询唯一一条符合条件的记录
	 */
	public T getByHql(String hql,Object...objects);
	
	public List<T> findByLimit(String hql,int firstResult,int maxResults,Object...objects);

	public void updateBySql(String sql, Object...objects);
}

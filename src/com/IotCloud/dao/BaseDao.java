package com.IotCloud.dao;

import java.util.List;


/**
 * 基础DAO层
 * @author 胡可及
 *
 * @param <T>
 */
public interface BaseDao<T> {
	
	/**
	 * 根据HQL进行查询，返回所有符合条件的记录
	 */
	public List<T> findByHql(String hql,Object...objects);
	/**
	 * 根据HQL进行查询唯一一条符合条件的记录
	 */
	public T getByHql(String hql,Object...objects);
	
	public List<T> findByLimit(String hql,int firstResult,int maxResults,Object...objects);

	/**
	 * 按sql语句更新
	 */
	public void updateBySql(String sql, Object...objects);
	
	/**
	 * 按对象添加
	 */
	public void add(T t);
	
	/**
	 * 按对象删除
	 */
	public void delete(T t);
	
	/**
	 * 按对象更新
	 */
	public void update(T t);
	
	/**
	 * 批量添加
	 */
	public void batchAdd(List<T> list);
	
	/**
	 * 批量删除
	 */
	public void batchDelete(List<T> list);
}

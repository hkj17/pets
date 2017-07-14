package com.IotCloud.dao;

import java.util.List;

import com.IotCloud.model.Admin;

/**
 * 记录跟管理员功能相关的DAO层
 * @author 胡可及
 *
 */
public interface AdminDao {
	/**
	 * 按用户名查找
	 */
	public Admin getAdminByUserName(String userName);
	
	/**
	 * 插入数据到管理员表
	 */
	public boolean insertAdmin(String userName, String userPasswd, int authority, String orgName, String areaCode);
	
	/**
	 * 按名字删除管理员
	 */
	public boolean deleteAdmin(String userName);
	
	/**
	 * 获得普通管理员列表
	 */
	public List<Admin> getAdminList();
	
	/**
	 * 更新管理员密码
	 */
	public int updatePassword(String userName, String oldPasswd, String newPasswd);
	
	/**
	 * 重置管理员密码
	 */
	public int resetPassword(String userName);
}

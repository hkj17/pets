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
	 * 按管理员id查找
	 */
	public Admin getAdminByUserName(String userName);
	
	/**
	 * 按管理员id查找
	 */
	public Admin getAdminById(String adminId);
	
	/**
	 * 插入数据到管理员表
	 */
	public boolean insertAdmin(String adminId, String userName, String userPasswd, int authority, String orgName, String areaCode);
	
	/**
	 * 按名字删除管理员
	 */
	//public boolean deleteAdmin(String userName);
	
	/**
	 * 批量删除管理员
	 */
	public boolean deleteAdmins(List<Admin> adminList);
	
	/**
	 * 获得普通管理员列表
	 */
	public List<Admin> getAdminList(String adminId);
	
	/**
	 * 更新管理员密码
	 */
	public int updatePassword(String userName, String oldPasswd, String newPasswd);
	
	/**
	 * 重置管理员密码
	 */
	public int resetPassword(String userName);
}
